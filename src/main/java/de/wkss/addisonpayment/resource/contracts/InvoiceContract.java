package de.wkss.addisonpayment.resource.contracts;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class InvoiceContract {

    private String name;
    private String description;
    private double amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaypalLink() {
        return paypalLink;
    }

    public void setPaypalLink(String paypalLink) {
        this.paypalLink = paypalLink;
    }
}
