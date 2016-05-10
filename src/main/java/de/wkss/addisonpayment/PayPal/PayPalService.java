package de.wkss.addisonpayment.PayPal;

import de.wkss.addisonpayment.domain.Payment;
import de.wkss.addisonpayment.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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
        o.setId("1");
        paymentRepo.save(o);

        return paymentRepo.findAll();
    }

}
