package de.wkss.addisonpayment.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class ExcecutePayPalPaymentDto {
    private String paymentId;
    private String payerId;


    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
