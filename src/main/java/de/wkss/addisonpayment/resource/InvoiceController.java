package de.wkss.addisonpayment.resource;

import de.wkss.addisonpayment.dal.Person;
import de.wkss.addisonpayment.dal.StateBill;
import de.wkss.addisonpayment.dal.StatePayment;
import de.wkss.addisonpayment.domain.Invoice;
import de.wkss.addisonpayment.resource.contracts.BillInvoiceContract;
import de.wkss.addisonpayment.resource.contracts.InvoiceContract;
import de.wkss.addisonpayment.resource.contracts.PaymentInvoiceContract;
import de.wkss.addisonpayment.service.PayPalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private PayPalService payPalService;

    @RequestMapping("/list")
    public List<Invoice> listPayments() {
        logger.info("list payment");
        return payPalService.listInvoices();
    }

    @RequestMapping("/invoice/{id}")
    public InvoiceContract getInvoice(@PathVariable String id){
        InvoiceContract invoiceContract = new InvoiceContract();
        invoiceContract.setName("<Name Billder>");
        invoiceContract.setAmount(42);
        invoiceContract.setDescription("This is a Description");
        return invoiceContract;
    }

    @RequestMapping("/getAllBillInvoice/{id}")
    public BillInvoiceContract getBillInvoice(@PathVariable String id){
        BillInvoiceContract contract = new BillInvoiceContract();
        contract.setAmount(10);
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

        return contract;

    }

}
