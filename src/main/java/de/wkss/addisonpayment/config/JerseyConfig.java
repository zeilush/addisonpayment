package de.wkss.addisonpayment.config;

import de.wkss.addisonpayment.resource.Payment;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
       register(Payment.class);
    }
}