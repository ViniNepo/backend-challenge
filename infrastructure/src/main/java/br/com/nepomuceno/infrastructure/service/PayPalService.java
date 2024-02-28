package br.com.nepomuceno.infrastructure.service;

import br.com.nepomuceno.application.gateway.PaymentService;
import br.com.nepomuceno.domain.OrderDetail;
import br.com.nepomuceno.exceptions.PaymentException;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.nepomuceno.domain.enums.ErrorCodeEnum.P001;

@Service
public class PayPalService implements PaymentService {
    private static final String CLIENT_ID = "AZnMZX723R-pvYmC_zzRD9WHVKxRDdi7noPfWBBIbKCcHIntG3VjPDRgYsr84i6waGYnKuy58_N4uazz"; //add client id here
    private static final String CLIENT_SECRET = "ELUWW_G8GW0Cch65vwyo0qgFZ9ZRucpmXE7QR7Yft6lqPH0NYBz39Kl2mwWSkLcbZ5uu9hLevxFqREZp"; //add client secret here
    private static final String MODE = "sandbox";
    @Override
    public String process(OrderDetail order) throws Exception {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(order);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment;
        try {
            approvedPayment = requestPayment.create(apiContext);
        } catch (Exception e) {
            throw new PaymentException(e.getMessage(), P001.getCode());
        }

        return getApprovalLink(approvedPayment);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Vinicius")
                .setLastName("Nepomuceno")
                .setEmail("sb-qp1s829711616@personal.example.com");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/PaypalTest/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:8080/PaypalTest/review_payment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
        Details details = new Details();
        details.setShipping(orderDetail.getShipping());
        details.setSubtotal(orderDetail.getSubtotal());
        details.setTax(orderDetail.getTax());

        Amount amount = new Amount();
        amount.setCurrency("BRL");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("BRL");
        item.setName(orderDetail.getProductName());
        item.setPrice(orderDetail.getSubtotal());
        item.setTax(orderDetail.getTax());
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}
