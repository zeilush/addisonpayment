package de.wkss.addisonpayment.repository;

import de.wkss.addisonpayment.domain.Payment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Repository
public class PaymentRepository {

    @Inject
    private RedisTemplate<String, Payment> redisTemplate;

    public void save(Payment book) {
        redisTemplate.opsForValue().set(book.getId(), book);
    }

    public Payment findById(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<Payment> findAll() {
        List<Payment> books = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            books.add(findById(it.next()));
        }

        return books;
    }

}
