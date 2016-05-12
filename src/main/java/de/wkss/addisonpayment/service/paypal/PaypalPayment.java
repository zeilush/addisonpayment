package de.wkss.addisonpayment.service.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
@Component
public class PaypalPayment extends PaypalPayments {
    public PaypalPayment() throws PayPalRESTException {
        super();
    }

    public Payment executePayment(String paymentID, String payerID) throws PayPalRESTException {
        APIContext apiContext = new APIContext(accessToken);

        Payment paymentSucced = new Payment();
        paymentSucced.setId(paymentID);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerID);


        Payment executedPayment = paymentSucced.execute(apiContext, paymentExecute);
        System.out.println(executedPayment.getState());
        return executedPayment;

    }

    public Payment openPayment(String total) throws PayPalRESTException {
        APIContext apiContext = new APIContext(accessToken);

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(total);

        Transaction transaction = new Transaction();
        transaction.setDescription("Lunchbox: there is an none payed lunch bill");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("https://www.facebook.com/Lunchbot-1713339108927283?cancel=true");
        redirectUrls.setReturnUrl("https://www.facebook.com/Lunchbot-1713339108927283?success=true");

        payment.setRedirectUrls(redirectUrls);

        //here is the link
        Payment createdPayment = payment.create(apiContext);
        System.out.println(createdPayment.getLinks().get(1).getHref());
        return createdPayment;
    }
}
