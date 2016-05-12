package de.wkss.addisonpayment.common;

/**
 * Created by Julian.donauer on 12.05.2016.
 */
public class PaypalEvent {

    private String id;
    private String event_type;
    private PaypalResource resource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public PaypalResource getResource() {
        return resource;
    }

    public void setResource(PaypalResource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "PaypalEvent{" +
                "id='" + id + '\'' +
                ", event_type='" + event_type + '\'' +
                ", resource=" + resource +
                '}';
    }
}
