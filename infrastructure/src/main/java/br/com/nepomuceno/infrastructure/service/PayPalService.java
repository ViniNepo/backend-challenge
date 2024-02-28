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
    private static final String CLIENT_ID = "AcW77BpZreje1rKlSGY7O4pvx8FjwE8lAqiwy7H1vd_qMxtoEZapcOKKdpouQYIEFaMjxeIbk5f5jDVr";

    private static final String CLIENT_SECRET = "EP8Hm5phAXl08wUUORVZyl4h3onXFpAaB-Wx-ajhkiPVcndUjM0VBR7oJrtXDm_qzM4MTmVmcyvLaxkD";
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
                .setEmail("vi.nepomuceno@outlook.com");

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
