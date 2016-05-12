package de.wkss.addisonpayment.dto;

import de.wkss.addisonpayment.dal.Person;

/**
 * Created by Artur.Zeiler on 12.05.2016.
 */
public class PaymentInvoiceDto {
    private Person payer;
    private String approvalUrl;

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }
}
