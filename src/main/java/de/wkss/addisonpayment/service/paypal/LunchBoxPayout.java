package de.wkss.addisonpayment.service.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class LunchBoxPayout extends LunchBoxPayments {
    public LunchBoxPayout() throws PayPalRESTException {
        super();
    }


    public void payOut(){
        Payout payout = new Payout();

        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();

        Random random = new Random();
        senderBatchHeader.setSenderBatchId(
                new Double(random.nextDouble()).toString()).setEmailSubject(
                "You have a Payout!");

        Currency amount = new Currency();
        amount.setValue("25.00").setCurrency("USD");

        PayoutItem senderItem = new PayoutItem();
        senderItem.setRecipientType("Email")
                .setNote("Thanks for your patronage")
                .setReceiver("Julian.Donauer-buyer@t-online.de")
                .setSenderItemId("201404324234").setAmount(amount);

        List<PayoutItem> items = new ArrayList<PayoutItem>();
        items.add(senderItem);

        payout.setSenderBatchHeader(senderBatchHeader).setItems(items);

        PayoutBatch batch = null;
        try {

            APIContext apiContext = new APIContext(accessToken);

            batch = payout.createSynchronous(apiContext);

            System.out.println("Payout Batch With ID: "
                    + batch.getBatchHeader().getPayoutBatchId());
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

}
