package br.com.nepomuceno.infrastructure.service;

import br.com.nepomuceno.application.gateway.CreatePaymentLinkGateway;
import br.com.nepomuceno.domain.OrderDetail;
import br.com.nepomuceno.exceptions.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static br.com.nepomuceno.domain.enums.ErrorCodeEnum.P002;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreatePaymentLinkGatewayImplTest {

    @MockBean
    PaymentStrategyFactory paymentStrategyFactory;

    @MockBean
    PayPalService payPalService;

    CreatePaymentLinkGatewayImpl createPaymentLinkGateway;

    @BeforeEach
    public void setUp() {
        this.createPaymentLinkGateway = new CreatePaymentLinkGatewayImpl(paymentStrategyFactory);
    }

    private OrderDetail createOrderDetail(String paymentMethod, String productName, double subtotal, double shipping, double serviceTax, double total) {
        return new OrderDetail(paymentMethod, productName, subtotal, shipping, serviceTax, total);
    }

    @Test
    @DisplayName("Success - process multiple orders")
    public void processOrders() throws Exception {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(createOrderDetail("paypal", "Food Service", 42d, 8d, 0d, 50d));
        orderDetails.add(createOrderDetail("paypal", "Food Service", 10d, 8d, 0d, 18d));

        for(OrderDetail order : orderDetails) {
            when(paymentStrategyFactory.getPaymentService(order.getPaymentMethod())).thenReturn(new PayPalService());
            when(payPalService.process(order)).thenReturn("link");
        }

        List<String> links = createPaymentLinkGateway.createPaymentLink(orderDetails);

        assertNotNull(links);
        assertSame(links.size(), 2);
    }

    @Test
    @DisplayName("Success - process single order")
    public void processSingleOrder() throws Exception {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(createOrderDetail("paypal", "Food Service", 42d, 8d, 0d, 50d));

        for(OrderDetail order : orderDetails) {
            when(paymentStrategyFactory.getPaymentService(order.getPaymentMethod())).thenReturn(new PayPalService());
            when(payPalService.process(order)).thenReturn("link");
        }

        List<String> links = createPaymentLinkGateway.createPaymentLink(orderDetails);

        assertNotNull(links);
        assertSame(links.size(), 1);
    }

    @Test
    @DisplayName("Error - processing order")
    public void errorProcessSingleOrder() throws Exception {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(createOrderDetail("test", "Food Service", 42d, 8d, 0d, 50d));

        for(OrderDetail order : orderDetails) {
            when(paymentStrategyFactory.getPaymentService(order.getPaymentMethod())).thenThrow(new PaymentException("Invalid option " + "test", P002.getCode()));
        }

        assertThrows(
                PaymentException.class,
                () -> createPaymentLinkGateway.createPaymentLink(orderDetails),
                "Invalid option " + "test"
        );
    }
}
