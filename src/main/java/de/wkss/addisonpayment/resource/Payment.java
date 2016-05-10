package de.wkss.addisonpayment.resource;

import de.wkss.addisonpayment.service.PayPalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api
public class Payment {

    private static final Logger logger = LoggerFactory.getLogger(Payment.class);

    @Inject
    private PayPalService payPalService;

    @ApiOperation(value = "listPayments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class)})
    @GET
    @Path("/list")
    public List<de.wkss.addisonpayment.domain.Payment> listPayments() {
        logger.info("list payment");
        return payPalService.listPayments();
    }
}
