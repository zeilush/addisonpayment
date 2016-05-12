package de.wkss.addisonpayment.service.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class LunchBoxPayment extends LunchBoxPayments {
    public LunchBoxPayment() throws PayPalRESTException {
        super();
    }

    public void executePayment(String paymentID, String payerID){
        APIContext apiContext = new APIContext(accessToken);

        Payment paymentSucced = new Payment();
        paymentSucced.setId(paymentID);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerID);


        try {
            Payment payment1 = paymentSucced.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

    public void openPayment(String total, String cancelLink, String successLink){

        try {

            APIContext apiContext = new APIContext(accessToken);

            Amount amount = new Amount();
            amount.setCurrency("USD");
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
//            redirectUrls.setCancelUrl("https://devtools-paypal.com/guide/pay_paypal?cancel=true");
//            redirectUrls.setReturnUrl("https://devtools-paypal.com/guide/pay_paypal?success=true");
            redirectUrls.setCancelUrl(cancelLink);
            redirectUrls.setReturnUrl(successLink);


            payment.setRedirectUrls(redirectUrls);

            //here is the link
            Payment createdPayment = payment.create(apiContext);
            System.out.println(createdPayment.getLinks().get(1).getHref());



        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }
}
