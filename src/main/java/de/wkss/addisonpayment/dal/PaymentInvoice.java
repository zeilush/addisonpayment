package de.wkss.addisonpayment.dal;

import java.io.Serializable;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class PaymentInvoice implements Serializable {
    private String paymentId;
    private Person payer;
    private double amount;
    private String billInvoiceId;
    private PaymentServiceData remittee;
    private StatePayment state;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBillInvoiceId() {
        return billInvoiceId;
    }

    public void setBillInvoiceId(String billInvoiceId) {
        this.billInvoiceId = billInvoiceId;
    }

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

    public PaymentServiceData getRemittee() {
        return remittee;
    }

    public void setRemittee(PaymentServiceData remittee) {
        this.remittee = remittee;
    }

    public StatePayment getState() {
        return state;
    }

    public void setState(StatePayment state) {
        this.state = state;
    }

}
