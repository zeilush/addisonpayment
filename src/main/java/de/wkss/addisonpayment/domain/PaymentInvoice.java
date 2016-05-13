package de.wkss.addisonpayment.domain;

import java.io.Serializable;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class PaymentInvoice implements Serializable {
    private String id;
    private Person payer;
    private String amount;
    private String billInvoiceId;
    private PaymentServiceData serviceData;
    private StatePayment state = StatePayment.OPEN;
    private String approvalLink;

    public PaymentInvoice() {

    }

    public PaymentInvoice(String id, Person payer, String amount, String billInvoiceId, PaymentServiceData serviceData) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
