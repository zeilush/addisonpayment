package de.wkss.addisonpayment.dal;

import java.io.Serializable;

/**
 * Created by jan.plitschka on 12.05.2016.
 */
public class Person implements Serializable {
    private String referenceId;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
