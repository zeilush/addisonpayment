package de.wkss.addisonpayment.common;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class PaypalResource {
    private String state;
    private String parent_payment;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParent_payment() {
        return parent_payment;
    }

    public void setParent_payment(String parent_payment) {
        this.parent_payment = parent_payment;
    }

    @Override
    public String toString() {
        return "PaypalResource{" +
                "state='" + state + '\'' +
                ", parent_payment='" + parent_payment + '\'' +
                '}';
    }
}
