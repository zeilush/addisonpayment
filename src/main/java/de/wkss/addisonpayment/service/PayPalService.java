package de.wkss.addisonpayment.service;

import de.wkss.addisonpayment.domain.Payment;
import de.wkss.addisonpayment.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
public class PayPalService {

    @Inject
    private PaymentRepository paymentRepo;

    public List<Payment> listPayments() {
        Payment o = new Payment();
        o.setId(new BigInteger(130, new SecureRandom()).toString(32));
        paymentRepo.save(o);

        return paymentRepo.findAll();
    }

}
