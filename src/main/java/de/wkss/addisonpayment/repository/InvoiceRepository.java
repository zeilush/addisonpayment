package de.wkss.addisonpayment.repository;

import de.wkss.addisonpayment.domain.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InvoiceRepository {

    @Autowired
    private RedisTemplate<String, Invoice> redisTemplate;

    public void save(Invoice invoice) {
        redisTemplate.opsForValue().set(invoice.getId(), invoice);
    }

    public Invoice findById(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<Invoice> findAll() {
        List<Invoice> books = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            books.add(findById(it.next()));
        }

        return books;
    }

}
