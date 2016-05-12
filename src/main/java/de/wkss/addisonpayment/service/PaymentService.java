package de.wkss.addisonpayment.service;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.wkss.addisonpayment.dal.*;
import de.wkss.addisonpayment.dto.InvoiceDto;
import de.wkss.addisonpayment.dto.PaymentInvoiceDto;
import de.wkss.addisonpayment.repository.BillRepository;
import de.wkss.addisonpayment.repository.PaymentInvoiceRepository;
import de.wkss.addisonpayment.resource.InvoiceController;
import de.wkss.addisonpayment.resource.contracts.BillInvoiceContract;
import de.wkss.addisonpayment.resource.contracts.InvoiceContract;
import de.wkss.addisonpayment.resource.contracts.PaymentInvoiceContract;
import de.wkss.addisonpayment.service.paypal.PaypalPayment;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private BillRepository repo;

    @Autowired
    private PaypalPayment paypalPayment;

    @Autowired
    private PersonService personService;

    @Autowired
    private PaymentInvoiceRepository paymentInvoiceRepository;

    @Transactional
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

        logger.info("payment created: {}", ReflectionToStringBuilder.toString(payment));
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
        result.setAmount(invoice.getAmount());
        result.setBiller(invoice.getBiller());
        result.setDescription(invoice.getDescription());
        result.setCreateDate(LocalDate.now().toString());
        result.setPaymentServiceData(new PayPalService());
        result.setState(StateBill.OPEN);
        repo.save(result);
        return result;
    }

    @Transactional
    public BillInvoiceContract lookUpBillInvoice(String id){
        BillInvoice billInvoce = repo.findById(id);
        BillInvoiceContract billInvoiceContract = new BillInvoiceContract();
        billInvoiceContract.read(billInvoce);

        List<PaymentInvoice> payments = paymentInvoiceRepository.findPayments(billInvoce.getId());

        List<PaymentInvoiceContract> collect = payments.stream().map(invoice -> {
            PaymentInvoiceContract pContract = new PaymentInvoiceContract();
            pContract.read(invoice);
            return pContract;
        }).collect(Collectors.toList());
        billInvoiceContract.setInvoices(collect);

        return billInvoiceContract;

    }

    public InvoiceContract lookUpPaymentInvoice(String id){
        PaymentInvoice singlePayment = paymentInvoiceRepository.findSinglePayment(id);

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
