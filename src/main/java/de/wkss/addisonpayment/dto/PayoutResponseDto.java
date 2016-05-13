package de.wkss.addisonpayment.dto;

import de.wkss.addisonpayment.domain.Person;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class PayoutResponseDto {
    private Person biller;
    private Person payer;
    private String billInvoiceId;
    private String amout;

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public String getBillInvoiceId() {
        return billInvoiceId;
    }

    public void setBillInvoiceId(String billInvoiceId) {
        this.billInvoiceId = billInvoiceId;
    }

    public Person getBiller() {
        return biller;
    }

    public void setBiller(Person biller) {
        this.biller = biller;
    }

    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
