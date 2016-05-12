package de.wkss.addisonpayment.resource.contracts;

import de.wkss.addisonpayment.dal.PaymentServiceData;
import de.wkss.addisonpayment.dal.Person;
import de.wkss.addisonpayment.dal.StateBill;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class BillInvoiceContract {

    private String id;
    private Person biller;
    private double amount;
    private String description;
    private StateBill state;
    private PaymentServiceData paymentServiceData;
    private LocalDate timestamp;

    private List<PaymentInvoiceContract> invoices = new LinkedList<>();

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

    public List<PaymentInvoiceContract> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<PaymentInvoiceContract> invoices) {
        this.invoices = invoices;
    }
}
