package de.wkss.addisonpayment.service.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.io.InputStream;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public abstract class PaypalPayments {

    protected String accessToken;

    public PaypalPayments() throws PayPalRESTException {

        InputStream is = PaypalPayments.class.getResourceAsStream("/paypal.properties");
        OAuthTokenCredential tokenCredential = Payment.initConfig(is);

        accessToken = tokenCredential.getAccessToken();
        System.out.println(accessToken);

    }
}
