package de.wkss.addisonpayment.dal;

import de.wkss.addisonpayment.common.PaymentServiceType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class BillInvoice implements Serializable {

    private String id;
    private Biller biller;
    private String amount;
    private String description;
    private String invoiceImageUrl;
    private StateBill state;
    private PaymentServiceData paymentServiceData;
    private String createDate;

    public BillInvoice() {
        id = UUID.randomUUID().toString();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
    public Biller getBiller() {
        return biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInvoiceImageUrl() {
        return invoiceImageUrl;
    }

    public void setInvoiceImageUrl(String invoiceImageUrl) {
        this.invoiceImageUrl = invoiceImageUrl;
    }
}
