package de.wkss.addisonpayment.service.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
@Component
public class PaypalPayment extends PaypalPayments {
    private static final Logger logger = LoggerFactory.getLogger(PaypalPayment.class);

    public PaypalPayment() throws PayPalRESTException {
        super();
    }

    public Payment executePayment(String paymentID, String payerID) throws PayPalRESTException {
        APIContext apiContext = new APIContext(accessToken);

        logger.info("accessToken {}", accessToken);

        Payment paymentSucced = new Payment();
        paymentSucced.setId(paymentID);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerID);


        Payment executedPayment = paymentSucced.execute(apiContext, paymentExecute);
        System.out.println(executedPayment.getState());
        return executedPayment;

    }

    public Payment openPayment(String total, String cancelUrl, String returnUrl) throws PayPalRESTException {
        APIContext apiContext = new APIContext(accessToken);

        logger.info("accessToken {}", accessToken);
        logger.info("requestId {}", apiContext.getRequestId());
        logger.info("configurationMap {}", apiContext.getConfigurationMap());

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(total);

        Transaction transaction = new Transaction();
        transaction.setDescription("Lunchbox: there is an none payed lunch bill");
        transaction.setAmount(amount);

        Item item = new Item();
        item.setName("LunchBox");
        item.setPrice(total);
        item.setCurrency("EUR");
        item.setQuantity("1");
        List<Item> items = new LinkedList<>();
        items.add(item);
        ItemList itemList = new ItemList();
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);

        payment.setRedirectUrls(redirectUrls);

        //here is the link
        Payment createdPayment = payment.create(apiContext);
        System.out.println(createdPayment.getLinks().get(1).getHref());
        return createdPayment;
    }
}
