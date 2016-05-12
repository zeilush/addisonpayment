package de.wkss.addisonpayment.dal;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Base64;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class BillInvoice implements Serializable {

    private String id;
    private Person biller;
    private double amount;
    private String description;
    private Base64 reason;
    private StateBill state;
    private PaymentServiceData paymentServiceData;
    private LocalDate timestamp;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Base64 getReason() {
        return reason;
    }

    public void setReason(Base64 reason) {
        this.reason = reason;
    }

    public PaymentServiceData getPaymentServiceData() {
        return paymentServiceData;
    }

    public void setPaymentServiceData(PaymentServiceData paymentServiceData) {
        this.paymentServiceData = paymentServiceData;
    }

    public StateBill getState() {
        return state;
    }

    public void setState(StateBill state) {
        this.state = state;
    }
    public Person getBiller() {
        return biller;
    }

    public void setBiller(Person biller) {
        this.biller = biller;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
