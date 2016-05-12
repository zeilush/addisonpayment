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
public class PaypalPayout extends PaypalPayments {
    public PaypalPayout() throws PayPalRESTException {
        super();
    }


    public boolean payOut(String targetPaypalAccount, String value) throws PayPalRESTException {
        Payout payout = new Payout();

        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();

        Random random = new Random();
        senderBatchHeader.setSenderBatchId(
                new Double(random.nextDouble()).toString()).setEmailSubject(
                "There is your payout!");

        Currency amount = new Currency();
        amount.setValue(value).setCurrency("EUR");

        PayoutItem senderItem = new PayoutItem();
        senderItem.setRecipientType("Email")
                .setNote(targetPaypalAccount)
                .setReceiver(targetPaypalAccount)
                .setSenderItemId("201404324234")
                .setAmount(amount);

        List<PayoutItem> items = new ArrayList<>();
        items.add(senderItem);

        payout.setSenderBatchHeader(senderBatchHeader).
                setItems(items);

        PayoutBatch batch = null;

        APIContext apiContext = new APIContext(accessToken);

        batch = payout.createSynchronous(apiContext);

        if("SUCCESS".equals(batch.getBatchHeader().getBatchStatus())){
            return true;
        }
        return false;
    }

}
