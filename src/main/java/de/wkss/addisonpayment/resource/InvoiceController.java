package de.wkss.addisonpayment.resource;

import com.paypal.base.rest.PayPalRESTException;
import de.wkss.addisonpayment.common.PaypalEvent;
import de.wkss.addisonpayment.dal.BillInvoice;
import de.wkss.addisonpayment.dal.Person;
import de.wkss.addisonpayment.dal.StateBill;
import de.wkss.addisonpayment.dal.StatePayment;
import de.wkss.addisonpayment.dto.ExcecutePayPalPaymentDto;
import de.wkss.addisonpayment.dto.InvoiceDto;
import de.wkss.addisonpayment.dto.PaymentInvoiceDto;
import de.wkss.addisonpayment.resource.contracts.BillInvoiceContract;
import de.wkss.addisonpayment.resource.contracts.InvoiceContract;
import de.wkss.addisonpayment.resource.contracts.PaymentInvoiceContract;
import de.wkss.addisonpayment.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/invoice/{id}")
    public InvoiceContract getInvoice(@PathVariable String id){

        InvoiceContract invoiceContract = paymentService.lookUpPaymentInvoice(id);

        if(invoiceContract == null){
            invoiceContract = new InvoiceContract();
            invoiceContract.setName("<Name Billder>");
            invoiceContract.setAmount("42");
            invoiceContract.setDescription("This is a Description");
        }

        return invoiceContract;
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public void approveInvoicePost(@RequestBody PaypalEvent event){

        logger.info(event.toString());

        switch (event.getEvent_type()){
            case "PAYMENT.SALE.COMPLETED" : {
                //payment success

                break;
            }
            case "PAYMENT.PAYOUTSBATCH.SUCCESS" : {
                //payout success
                break;
            }
        }
    }


    @RequestMapping("/getAllBillInvoice/{id}")
    public BillInvoiceContract getBillInvoice(@PathVariable String id){


        BillInvoiceContract contract = paymentService.lookUpBillInvoice(id);
        if(contract == null){
            contract = new BillInvoiceContract();
            contract.setAmount("10");
            contract.setId(id);
            Person person = new Person();
            person.setName("Julian");
            person.setReferenceId(UUID.randomUUID().toString());
            contract.setBiller(person);

            List<PaymentInvoiceContract> payers = new LinkedList<>();
            person = new Person();
            person.setName("DönerTier");
            PaymentInvoiceContract invoiceContract = new PaymentInvoiceContract();
            invoiceContract.setPayer(person);
            invoiceContract.setState(StatePayment.OPEN);
            payers.add(invoiceContract);

            person = new Person();
            person.setName("Salat");
            invoiceContract = new PaymentInvoiceContract();
            invoiceContract.setPayer(person);
            invoiceContract.setState(StatePayment.PAYED);
            payers.add(invoiceContract);

            contract.setInvoices(payers);
            contract.setState(StateBill.PARTIALLY_COLLECTED);
        }
        return contract;

    }

    @ApiOperation(value = "createInvoce")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PaymentInvoiceDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.POST)
    public List<PaymentInvoiceDto> createInvoice(@RequestBody InvoiceDto invoice) throws PayPalRESTException {
        logger.info("REST API create invoice: " + invoice);

        return paymentService.createPayPalPayment(invoice);
    }

    @ApiOperation(value = "execute PayPal payment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value="/payment/paypal" , method = RequestMethod.POST)
    public void executePayPalPayment(@RequestBody ExcecutePayPalPaymentDto dto) throws PayPalRESTException {
        logger.info("REST API execute PayPal: " + dto);

        paymentService.executePayment(dto);
    }

}
