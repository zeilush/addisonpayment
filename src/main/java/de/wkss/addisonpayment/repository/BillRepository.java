package de.wkss.addisonpayment.repository;

import de.wkss.addisonpayment.domain.BillInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Repository
public class BillRepository {
    private static final String OBJECT_NAME = "BILL_INVOICE_";

    @Autowired
    @Qualifier(value = "redisTemplateBillInvoice")
    private RedisTemplate<String, BillInvoice> redisTemplate;

    @Autowired
    private PaymentInvoiceRepository invoiceRepository;

    public BillInvoice save(BillInvoice invoice) {
        //delete existing references if invoice already exists
        BillInvoice billInvoice = findById(invoice.getId());
        if(billInvoice != null) {
            invoiceRepository.deleteByInvoiceId(billInvoice.getId());
        }

        redisTemplate.opsForValue().set(OBJECT_NAME + invoice.getId(), invoice);
        return findById(invoice.getId());
    }

    public BillInvoice update(BillInvoice invoice) {
        redisTemplate.opsForValue().set(OBJECT_NAME + invoice.getId(), invoice);
        return findById(invoice.getId());
    }

    public BillInvoice findById(String key) {
        return redisTemplate.opsForValue().get(OBJECT_NAME + key);
    }
//
//    public BillInvoice findByBiller(String billerId) {
//        System.out.println(findAll());
//        return redisTemplate.opsForValue().get(billerId + "_*");
//    }


    public List<BillInvoice> findAll() {
        List<BillInvoice> books = new ArrayList<>();

        Set<String> keys = redisTemplate.keys(OBJECT_NAME + "*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            books.add(findById(it.next()));
        }

        return books;
    }

}
