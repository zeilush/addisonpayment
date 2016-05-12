package de.wkss.addisonpayment.repository;

import de.wkss.addisonpayment.dal.PaymentInvoice;
import de.wkss.addisonpayment.dal.Person;
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
public class PaymentInvoiceRepository {

    @Autowired
    @Qualifier(value = "redisTemplatePaymentInvoice")
    private RedisTemplate<String, PaymentInvoice> redisTemplate;

    public PaymentInvoice save(PaymentInvoice person) {
        redisTemplate.opsForValue().set(person.getPaymentId(), person);
        return findById(person.getPaymentId());
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
