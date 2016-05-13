package de.wkss.addisonpayment.service;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.wkss.addisonpayment.domain.*;
import de.wkss.addisonpayment.dto.ExcecutePayPalPaymentDto;
import de.wkss.addisonpayment.dto.InvoiceDto;
import de.wkss.addisonpayment.dto.PaymentInvoiceDto;
import de.wkss.addisonpayment.dto.PayoutResponseDto;
import de.wkss.addisonpayment.repository.BillRepository;
import de.wkss.addisonpayment.repository.PaymentInvoiceRepository;
import de.wkss.addisonpayment.resource.PaymentController;
import de.wkss.addisonpayment.resource.contracts.BillInvoiceContract;
import de.wkss.addisonpayment.resource.contracts.InvoiceContract;
import de.wkss.addisonpayment.resource.contracts.PaymentInvoiceContract;
import de.wkss.addisonpayment.service.paypal.PaypalPayment;
import de.wkss.addisonpayment.service.paypal.PaypalPayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private BillRepository repo;

    @Autowired
    private PaypalPayment paypalPayment;

    @Autowired
    private PaypalPayout paypalPayout;

    @Autowired
    private PersonService personService;

    @Autowired
    private PaymentInvoiceRepository paymentInvoiceRepository;


    public PayoutResponseDto executePayment(ExcecutePayPalPaymentDto dto) throws PayPalRESTException {
        PaymentInvoice paymentInvoice = paymentInvoiceRepository.findByPaymentId(dto.getPaymentId());
        BillInvoice billInvoice = repo.findById(paymentInvoice.getBillInvoiceId());

        if(paymentInvoice.getState().equals(StatePayment.OPEN)) {
            logger.info("execute PayPal payment for payer {}, paymentId {}" + paymentInvoice.getPayer(), dto.getPaymentId());
            paypalPayment.executePayment(dto.getPaymentId(), dto.getPayerId());

            paymentInvoice.setState(StatePayment.PAYED);

            paymentInvoiceRepository.save(paymentInvoice);

            //check if payout needs to be executed
            executePayout(billInvoice);
        }
        else {
            logger.info("payment for payer {} with paymentId {} already done", paymentInvoice.getPayer(), dto.getPaymentId());
        }

        PayoutResponseDto response = new PayoutResponseDto();
        response.setPayer(paymentInvoice.getPayer());
        response.setBiller(billInvoice.getBiller());
        response.setBillInvoiceId(billInvoice.getId());
        response.setAmout(paymentInvoice.getAmount());

        return response;
    }

    private void executePayout(BillInvoice billInvoice) throws PayPalRESTException {
        logger.info("check execute payout for bill invoice id {}", billInvoice.getId());

        if(billInvoice.getState().equals(StateBill.OPEN)) {
            //check if all payments associated with the bill are paid
            boolean doPayout = paymentInvoiceRepository.findPayments(billInvoice.getId()).stream().filter(p -> p.getState() == StatePayment.OPEN).count() == 0;

            if(doPayout) {
                logger.info("execute payout for bill invoice id {}", billInvoice.getId());

                paypalPayout.payOut(billInvoice.getBiller().getEmail(), billInvoice.getAmount());

                billInvoice.setState(StateBill.COMPLETELY_COLLECTED);

                repo.update(billInvoice);

                logger.info("payout executed for bill invoice id {}", billInvoice.getId());
            }
            else {
                logger.info("not all payments for bill {} are paid. payout can't be executed", billInvoice.getId());
            }
        }
        else {
            logger.info("payout for bill {} already done", billInvoice.getId());
        }
    }

    public List<PaymentInvoiceDto> createPayPalPayment(InvoiceDto invoice) throws PayPalRESTException {
        logger.info("create payments, data: {}", invoice);

        BillInvoice billInvoice = createBillInvoice(invoice);

        List<PaymentInvoiceDto> paymentInvoiceDto = new ArrayList<>(invoice.getPayer().size());

        Double amount = Double.valueOf(billInvoice.getAmount());
        if(amount != null){
            amount = amount / invoice.getPayer().size();
        }

        for(Person payer : invoice.getPayer()) {

            //create PayPal Payment
            Payment payment = createPayPalPayment(invoice, payer, String.format(Locale.US, "%.2f", amount));

            //create payment invoice
            createPaymentInvoice(billInvoice, payer, payment);

            PaymentInvoiceDto dto = new PaymentInvoiceDto();
            dto.setPayer(payer);
            dto.setPaymentId(payment.getId());

            paymentInvoiceDto.add(dto);
        }

        return paymentInvoiceDto;
    }

    private Payment createPayPalPayment(InvoiceDto invoice, Person payer, String amount) throws PayPalRESTException {
        logger.info("create payment for payer: {}", payer);

        Payment payment = paypalPayment.openPayment(amount, invoice.getCancelUrl(), invoice.getReturnUrl());

        logger.info("payment created: {}", payment.getId());
        return payment;
    }

    private void createPaymentInvoice(BillInvoice billInvoice, Person payer, Payment payment) {
        personService.createPerson(payer);

        String approvalUrl = null;
        for(Links link : payment.getLinks()) {
            if(link.getRel().equals("approval_url")) {
                approvalUrl = link.getHref();
                break;
            }
        }

        PaymentInvoice paymentInvoice = new PaymentInvoice(billInvoice.getId() + "_" + payment.getId(), payer, payment.getTransactions().get(0).getAmount().getTotal(), billInvoice.getId(), billInvoice.getPaymentServiceData());
        paymentInvoice.setApprovalLink(approvalUrl);
        paymentInvoiceRepository.save(paymentInvoice);
    }

    private BillInvoice createBillInvoice(InvoiceDto invoice) {
        BillInvoice result = new BillInvoice();
        result.setId(invoice.getBiller().getReferenceId());
        result.setAmount(invoice.getAmount());
        result.setBiller(invoice.getBiller());
        result.setDescription(invoice.getDescription());
        result.setCreateDate(LocalDate.now().toString());
        result.setPaymentServiceData(new PayPalService());
        result.setInvoiceImageUrl(invoice.getInvoiceImageUrl());
        result.setState(StateBill.OPEN);
        repo.save(result);
        return result;
    }

    public BillInvoiceContract lookUpBillInvoiceById(String id){
        BillInvoice billInvoice = repo.findById(id);
        BillInvoiceContract billInvoiceContract = createBillInvoiceContract(billInvoice);

        return billInvoiceContract;
    }

    private BillInvoiceContract createBillInvoiceContract(BillInvoice billInvoice) {
        BillInvoiceContract billInvoiceContract = new BillInvoiceContract();
        billInvoiceContract.read(billInvoice);

        List<PaymentInvoice> payments = paymentInvoiceRepository.findPayments(billInvoice.getId());

        List<PaymentInvoiceContract> collect = payments.stream().map(invoice -> {
            PaymentInvoiceContract pContract = new PaymentInvoiceContract();
            pContract.read(invoice);
            return pContract;
        }).collect(Collectors.toList());
        billInvoiceContract.setInvoices(collect);
        return billInvoiceContract;
    }
//
//    public BillInvoiceContract lookUpBillInvoiceByBiller(String id){
//        BillInvoice billInvoice = repo.findByBiller(id);
//        BillInvoiceContract billInvoiceContract = createBillInvoiceContract(billInvoice);
//
//        return billInvoiceContract;
//    }

    public InvoiceContract lookUpPaymentInvoice(String id){
        PaymentInvoice singlePayment = paymentInvoiceRepository.findByPaymentId(id);
        if(singlePayment == null){
            return null;
        }

        BillInvoice billInvoice = repo.findById(singlePayment.getBillInvoiceId());

        if(billInvoice != null){
            InvoiceContract contract = new InvoiceContract();
            contract.read(singlePayment, billInvoice);
            return contract;
        }

        return null;
    }
}
