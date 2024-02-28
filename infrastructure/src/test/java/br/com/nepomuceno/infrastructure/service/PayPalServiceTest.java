package br.com.nepomuceno.infrastructure.service;

import br.com.nepomuceno.application.gateway.PaymentService;
import br.com.nepomuceno.domain.OrderDetail;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PayPalServiceTest {

    PaymentService paymentService;

    @MockBean
    Payment payment;

    @BeforeEach
    public void setUp() {
        this.paymentService = new PayPalService();
    }

    private OrderDetail createOrderDetail(String paymentMethod, String productName, double subtotal, double shipping, double serviceTax, double total) {
        return new OrderDetail(paymentMethod, productName, subtotal, shipping, serviceTax, total);
    }

    private APIContext createAPIContext(String clientId, String secret, String mode) {
        return new APIContext(clientId, secret, mode);
    }

    @Test
    @DisplayName("Success - example case")
    public void createLinkPayment() throws Exception {
        OrderDetail orderDetail = createOrderDetail("PAYPAL", "Food Service", 42d, 8d, 0d, 50d);

        when(payment.create(createAPIContext("CLIENT_ID", "CLIENT_SECRET", "sandbox"))).thenReturn(payment);

        String link = paymentService.process(orderDetail);

        assertNotNull(link);
    }

}
