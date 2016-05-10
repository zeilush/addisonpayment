package de.wkss.addisonpayment.resource;

import de.wkss.addisonpayment.PayPal.PayPalService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class Payment {

    private static final Logger logger = LoggerFactory.getLogger(Payment.class);

    @Autowired
    private PayPalService payPalService;

    @GET
    @Path("/list")
    public String getOrder() {
        logger.info("do payment");
        return payPalService.createPayment();
    }
}
