package de.wkss.addisonpayment.service;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.wkss.addisonpayment.dal.*;
import de.wkss.addisonpayment.dto.PaymentInvoiceDto;
import de.wkss.addisonpayment.repository.BillRepository;
import de.wkss.addisonpayment.dto.InvoiceDto;
import de.wkss.addisonpayment.repository.PaymentInvoiceRepository;
import de.wkss.addisonpayment.resource.InvoiceController;
import de.wkss.addisonpayment.service.paypal.PaypalPayment;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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

        for(Person payer : invoice.getPayer()) {

            //create PayPal Payment
            Payment payment = createPayPalPayment(invoice, payer);

            //create payment invoice
            createPaymentInvoice(billInvoice, payer, payment);

//            String approvalUrl = null;
//            for(Links link : payment.getLinks()) {
//                if(link.getRel().equals("approval_url")) {
//                    approvalUrl = link.getHref();
//                    break;
//                }
//            }

            PaymentInvoiceDto dto = new PaymentInvoiceDto();
            dto.setPayer(payer);
            dto.setPaymentId(payment.getId());

            paymentInvoiceDto.add(dto);

        }

        return paymentInvoiceDto;
    }

    private Payment createPayPalPayment(InvoiceDto invoice, Person payer) throws PayPalRESTException {
        logger.info("create payment for payer: {}", payer);

        Payment payment = paypalPayment.openPayment(invoice.getAmount(), invoice.getCancelUrl(), invoice.getReturnUrl());

        logger.info("payment created: {}", ReflectionToStringBuilder.toString(payment));
        return payment;
    }

    private void createPaymentInvoice(BillInvoice billInvoice, Person payer, Payment payment) {
        personService.createPerson(payer);

        PaymentInvoice paymentInvoice = new PaymentInvoice(billInvoice.getId() + "_" + payment.getId(), payer, payment.getTransactions().get(0).getAmount().getTotal(), billInvoice.getId(), billInvoice.getPaymentServiceData());
        paymentInvoiceRepository.save(paymentInvoice);
    }

    private BillInvoice createBillInvoice(InvoiceDto invoice) {
        BillInvoice result = new BillInvoice();
        result.setAmount(invoice.getAmount());
        result.setBiller(invoice.getBiller());
        result.setDescription(invoice.getDescription());
        result.setCreateDate(LocalDate.now().toString());
        result.setPaymentServiceData(new PayPalService());
        result.setState(StateBill.TRANSFERRED_TO_BILLER);
        repo.save(result);
        return result;
    }

}
