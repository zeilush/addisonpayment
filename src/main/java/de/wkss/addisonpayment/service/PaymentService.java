package de.wkss.addisonpayment.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.wkss.addisonpayment.common.PaymentServiceType;
import de.wkss.addisonpayment.dal.BillInvoice;
import de.wkss.addisonpayment.dal.PayPalService;
import de.wkss.addisonpayment.dal.Person;
import de.wkss.addisonpayment.repository.BillRepository;
import de.wkss.addisonpayment.common.InvoiceDto;
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

    @Transactional
    public BillInvoice createPayPalPayment(InvoiceDto invoice) throws PayPalRESTException {
        logger.info("create payments, data: {}", invoice);

        Map<Person, Payment> paymentMap = new HashMap<>();

        for(Person payer : invoice.getPayer()) {
            logger.info("create payment for payer: {}", payer);

            Payment payment = paypalPayment.openPayment(invoice.getAmount());

            logger.info("payment created: {}", ReflectionToStringBuilder.toString(payment));

            paymentMap.put(payer, payment);
        }

        BillInvoice result = new BillInvoice();
        result.setAmount(invoice.getAmount());
        result.setBiller(invoice.getBiller());
        result.setDescription(invoice.getDescription());
        result.setTimestamp(LocalDate.now());
        result.setPaymentServiceData(new PayPalService());

        repo.save(result);

        return result;
    }

}
