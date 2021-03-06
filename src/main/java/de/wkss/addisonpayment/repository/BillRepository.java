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

    @Autowired
    @Qualifier(value = "redisTemplateBillInvoice")
    private RedisTemplate<String, BillInvoice> redisTemplate;

    public BillInvoice save(BillInvoice invoice) {
        redisTemplate.opsForValue().set(invoice.getId(), invoice);
        return findById(invoice.getId());
    }

    public BillInvoice findById(String key) {
        return redisTemplate.opsForValue().get(key);
    }
//
//    public BillInvoice findByBiller(String billerId) {
//        System.out.println(findAll());
//        return redisTemplate.opsForValue().get(billerId + "_*");
//    }


    public List<BillInvoice> findAll() {
        List<BillInvoice> books = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            books.add(findById(it.next()));
        }

        return books;
    }

}
