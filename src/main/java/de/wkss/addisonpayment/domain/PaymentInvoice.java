package de.wkss.addisonpayment.domain;

import java.io.Serializable;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class PaymentInvoice implements Serializable {
    private String paymentId;
    private Person payer;
    private String amount;
    private String billInvoiceId;
    private PaymentServiceData serviceData;
    private StatePayment state = StatePayment.OPEN;
    private String approvalLink;
    private String providerPaymentId;

    public PaymentInvoice() {

    }

    public PaymentInvoice(String paymentId, Person payer, String amount, String billInvoiceId, PaymentServiceData serviceData) {
        this.paymentId = paymentId;
        this.payer = payer;
        this.amount = amount;
        this.billInvoiceId = billInvoiceId;
        this.serviceData = serviceData;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public PaymentServiceData getServiceData() {
        return serviceData;
    }

    public void setServiceData(PaymentServiceData serviceData) {
        this.serviceData = serviceData;
    }

    public StatePayment getState() {
        return state;
    }

    public void setState(StatePayment state) {
        this.state = state;
    }

    public String getApprovalLink() {
        return approvalLink;
    }

    public void setApprovalLink(String approvalLink) {
        this.approvalLink = approvalLink;
    }

}
