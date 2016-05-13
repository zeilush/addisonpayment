package de.wkss.addisonpayment.resource.contracts;

import de.wkss.addisonpayment.domain.BillInvoice;
import de.wkss.addisonpayment.domain.PaymentServiceData;
import de.wkss.addisonpayment.domain.Person;
import de.wkss.addisonpayment.domain.StateBill;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class BillInvoiceContract {

    private String id;
    private Person biller;
    private String amount;
    private String description;
    private StateBill state;
    private PaymentServiceData paymentServiceData;
    private String createDate;
    private String invoiceImageUrl;

    private List<PaymentInvoiceContract> invoices = new LinkedList<>();

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

    public void setId(String id) {
        this.id = id;
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

    public List<PaymentInvoiceContract> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<PaymentInvoiceContract> invoices) {
        this.invoices = invoices;
    }

    public void read(BillInvoice invoice){
        this.amount = invoice.getAmount();
        this.biller = invoice.getBiller();
        this.description = invoice.getDescription();
        this.id = invoice.getId();
        this.state = invoice.getState();
        this.createDate = invoice.getCreateDate();
        this.invoiceImageUrl = invoice.getInvoiceImageUrl();
        this.paymentServiceData = invoice.getPaymentServiceData();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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
