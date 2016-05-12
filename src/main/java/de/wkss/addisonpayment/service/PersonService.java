package de.wkss.addisonpayment.service;

import de.wkss.addisonpayment.domain.Person;
import de.wkss.addisonpayment.repository.PersonRepository;
import de.wkss.addisonpayment.resource.PaymentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
@Component
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {
        Person personToCreate = personRepository.findById(person.getReferenceId());

        if (personToCreate == null) {
            logger.info("create person:{}", person);
            personToCreate = personRepository.save(person);
        }

        return personToCreate;
    }
}
