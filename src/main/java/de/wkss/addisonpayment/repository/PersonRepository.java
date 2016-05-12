package de.wkss.addisonpayment.repository;

import de.wkss.addisonpayment.dal.BillInvoice;
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
public class PersonRepository {

    @Autowired
    @Qualifier(value = "redisTemplatePerson")
    private RedisTemplate<String, Person> redisTemplate;

    public Person save(Person person) {
        redisTemplate.opsForValue().set(person.getReferenceId(), person);
        return findById(person.getReferenceId());
    }

    public Person findById(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<Person> findAll() {
        List<Person> books = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            books.add(findById(it.next()));
        }

        return books;
    }

}
