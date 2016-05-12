package de.wkss.addisonpayment.service;

import de.wkss.addisonpayment.domain.Invoice;
import de.wkss.addisonpayment.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
public class PayPalService {

    @Autowired
    private InvoiceRepository repo;

    public List<Invoice> listInvoices() {
        Invoice o = new Invoice();
        o.setId(new BigInteger(130, new SecureRandom()).toString(32));
        repo.save(o);

        return repo.findAll();
    }

}
