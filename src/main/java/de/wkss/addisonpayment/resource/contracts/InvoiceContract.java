package de.wkss.addisonpayment.resource.contracts;

import de.wkss.addisonpayment.domain.BillInvoice;
import de.wkss.addisonpayment.domain.PaymentInvoice;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class InvoiceContract {

    private String name;
    private String description;
    private String amount;
    private String paypalLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPaypalLink() {
        return paypalLink;
    }

    public void setPaypalLink(String paypalLink) {
        this.paypalLink = paypalLink;
    }

    public void read(PaymentInvoice invoice, BillInvoice bill){
        this.name = "Mocked";
        this.amount = invoice.getAmount();
        this.paypalLink = invoice.getApprovalLink();
        this.description = bill.getDescription();
        this.name = bill.getBiller().getName();

    }

}
