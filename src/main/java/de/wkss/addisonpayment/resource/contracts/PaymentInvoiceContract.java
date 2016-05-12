package de.wkss.addisonpayment.resource.contracts;

import de.wkss.addisonpayment.dal.Person;
import de.wkss.addisonpayment.dal.StatePayment;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class PaymentInvoiceContract {
    private Person payer;
    private StatePayment state;

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public StatePayment getState() {
        return state;
    }

    public void setState(StatePayment state) {
        this.state = state;
    }
}
