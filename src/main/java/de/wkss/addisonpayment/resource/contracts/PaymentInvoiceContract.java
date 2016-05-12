package de.wkss.addisonpayment.resource.contracts;

import de.wkss.addisonpayment.domain.PaymentInvoice;
import de.wkss.addisonpayment.domain.Person;
import de.wkss.addisonpayment.domain.StatePayment;

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

    public void read(PaymentInvoice invoice){
        this.payer = invoice.getPayer();
        this.state = invoice.getState();
    }
}
