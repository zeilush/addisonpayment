package de.wkss.addisonpayment.dal;

import de.wkss.addisonpayment.common.PaymentServiceType;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class PayPalService extends PaymentServiceData {
    public PayPalService() {
        setPaymentServiceType(PaymentServiceType.PAYPAL);
    }
}
