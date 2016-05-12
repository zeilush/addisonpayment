package de.wkss.addisonpayment.service;

import de.wkss.addisonpayment.dal.Person;
import de.wkss.addisonpayment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
@Component
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {

        return null;
    }
}
