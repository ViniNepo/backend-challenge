package br.com.nepomuceno.infrastructure.service;

import br.com.nepomuceno.application.gateway.PaymentService;
import br.com.nepomuceno.exceptions.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.nepomuceno.domain.enums.ErrorCodeEnum.P002;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class PaymentStrategyFactoryTest {

    PaymentStrategyFactory paymentStrategyFactory;

    @BeforeEach
    public void setUp() {
        this.paymentStrategyFactory = new PaymentStrategyFactory();
    }

    @Test
    @DisplayName("Success - getting strategy")
    public void createStrategy() throws PaymentException {
        PaymentService paymentService = paymentStrategyFactory.getPaymentService("paypal");

        assertEquals(paymentService.getClass(), PayPalService.class);
    }

    @Test
    @DisplayName("Error - error getting strategy")
    public void createWrongStrategy() {
        assertThrows(
                PaymentException.class,
                () -> paymentStrategyFactory.getPaymentService("test"),
                "Invalid option " + "test"
        );
    }

}
