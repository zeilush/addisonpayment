package de.wkss.addisonpayment.resource;

import com.paypal.base.rest.PayPalRESTException;
import de.wkss.addisonpayment.dto.ExcecutePayPalPaymentDto;
import de.wkss.addisonpayment.dto.InvoiceDto;
import de.wkss.addisonpayment.dto.PaymentInvoiceDto;
import de.wkss.addisonpayment.dto.PayoutResponseDto;
import de.wkss.addisonpayment.resource.contracts.BillInvoiceContract;
import de.wkss.addisonpayment.resource.contracts.InvoiceContract;
import de.wkss.addisonpayment.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Component
@RestController
@RequestMapping("/invoice")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/{id}")
    public InvoiceContract getInvoice(@PathVariable String id){
        logger.info("REST API: get payment invoice, id: {}" + id);

        InvoiceContract invoiceContract = paymentService.lookUpPaymentInvoice(id);

        logger.info("REST API: get payment invoice: {}" + invoiceContract);

        return invoiceContract;
    }

    @RequestMapping("/bill/{id}")
    public BillInvoiceContract getBillInvoice(@PathVariable String id){
        logger.info("REST API: get bill invoice, id: {}" + id);

        BillInvoiceContract contract = paymentService.lookUpBillInvoiceById(id);

        logger.info("REST API: get bill invoice response: {}" + contract);

        return contract;
    }

    @ApiOperation(value = "createInvoice")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PaymentInvoiceDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.POST)
    public List<PaymentInvoiceDto> createPayment(@RequestBody InvoiceDto invoice) throws PayPalRESTException {
        logger.info("REST API: create PayPal Payment: " + invoice);

        List<PaymentInvoiceDto> dto = paymentService.createPayPalPayment(invoice);

        logger.info("REST API: create PayPal Payment comnpleted, response: {}", dto);

        return dto;
    }

    @ApiOperation(value = "execute PayPal Payout")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value="/payout/paypal" , method = RequestMethod.POST)
    public PayoutResponseDto executePayPalPayout(@RequestBody ExcecutePayPalPaymentDto dto) throws PayPalRESTException {
        logger.info("REST API: execute PayPal Payout: " + dto);

        PayoutResponseDto response = paymentService.executePayment(dto);

        logger.info("REST API: execute completed, response: {}", response);

        return response;
    }
}