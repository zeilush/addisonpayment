package de.wkss.addisonpayment.dto;

import de.wkss.addisonpayment.domain.Person;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class PaymentInvoiceDto {
    private Person payer;
    private String paymentId;

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
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
