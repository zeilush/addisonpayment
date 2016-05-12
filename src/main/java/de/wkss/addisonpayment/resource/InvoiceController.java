package de.wkss.addisonpayment.resource;

import de.wkss.addisonpayment.domain.Invoice;
import de.wkss.addisonpayment.service.PayPalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private PayPalService payPalService;

    @RequestMapping("/list")
    public List<Invoice> listPayments() {
        logger.info("list payment");
        return payPalService.listInvoices();
    }
}
