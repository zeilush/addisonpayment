package de.wkss.addisonpayment.repository;

import de.wkss.addisonpayment.domain.PaymentInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Repository
public class PaymentInvoiceRepository {

    @Autowired
    @Qualifier(value = "redisTemplatePaymentInvoice")
    private RedisTemplate<String, PaymentInvoice> redisTemplate;

    public PaymentInvoice save(PaymentInvoice object) {
        redisTemplate.opsForValue().set(object.getId(), object);
        return findById(object.getId());
    }


    public List<PaymentInvoice> findPayments(String key){
        List<PaymentInvoice> invoices = new LinkedList<>();

        Set<String> keys = redisTemplate.keys(key + "_*");

        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            invoices.add(findById(it.next()));
        }

        return invoices;
    }

    public PaymentInvoice findByPaymentId(String key){
        Set<String> keys = redisTemplate.keys("*_" + key);
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            return findById(it.next());
        }

        return null;
    }

    public PaymentInvoice findById(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<PaymentInvoice> findAll() {
        List<PaymentInvoice> books = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            books.add(findById(it.next()));
        }

        return books;
    }

}
