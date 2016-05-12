package de.wkss.addisonpayment.common;

import de.wkss.addisonpayment.dal.Biller;
import de.wkss.addisonpayment.dal.Person;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class InvoiceDto {
    private List<Person> payer;
    private String description;
    private String amount;
    private PaymentServiceType paymentServiceType;
    private String email;
    private String cancelLink;
    private String successLink;
    private Biller biller;

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCancelLink() {
        return cancelLink;
    }

    public void setCancelLink(String cancelLink) {
        this.cancelLink = cancelLink;
    }

    public String getSuccessLink() {
        return successLink;
    }

    public void setSuccessLink(String successLink) {
        this.successLink = successLink;
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
}
