package de.wkss.addisonpayment.resource;

import de.wkss.addisonpayment.service.PayPalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Payment {

    private static final Logger logger = LoggerFactory.getLogger(Payment.class);

    @Inject
    private PayPalService payPalService;

    @GET
    @Path("/list")
    public String listPayments() {
        logger.info("list payment");
        return payPalService.listPayments();
    }
}
