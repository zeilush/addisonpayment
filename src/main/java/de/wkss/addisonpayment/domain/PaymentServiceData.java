package de.wkss.addisonpayment.domain;

import de.wkss.addisonpayment.common.PaymentServiceType;

import java.io.Serializable;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class PaymentServiceData implements Serializable {
    private PaymentServiceType paymentServiceType;

    public PaymentServiceType getPaymentServiceType() {
        return paymentServiceType;
    }

    public void setPaymentServiceType(PaymentServiceType paymentServiceType) {
        this.paymentServiceType = paymentServiceType;
    }
}
