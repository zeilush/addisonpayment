package de.wkss.addisonpayment.dto;

import de.wkss.addisonpayment.common.PaymentServiceType;
import de.wkss.addisonpayment.domain.Biller;
import de.wkss.addisonpayment.domain.Person;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class InvoiceDto {
    private Biller biller;
    private List<Person> payer;
    private String description;
    private String amount;
    private PaymentServiceType paymentServiceType;
    private String cancelUrl;
    private String returnUrl;

    public List<Person> getPayer() {
        return payer;
    }

    public void setPayer(List<Person> payer) {
        this.payer = payer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Biller getBiller() {
        return biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public PaymentServiceType getPaymentServiceType() {
        return paymentServiceType;
    }

    public void setPaymentServiceType(PaymentServiceType paymentServiceType) {
        this.paymentServiceType = paymentServiceType;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
