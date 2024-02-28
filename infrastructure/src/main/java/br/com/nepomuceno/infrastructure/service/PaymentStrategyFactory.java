package br.com.nepomuceno.infrastructure.service;

import br.com.nepomuceno.application.gateway.PaymentService;
import br.com.nepomuceno.exceptions.PaymentException;
import org.springframework.stereotype.Service;

import static br.com.nepomuceno.domain.enums.ErrorCodeEnum.P002;

@Service
public class PaymentStrategyFactory {

    public PaymentService getPaymentService(String paymentType) throws PaymentException {
        if ("paypal".equalsIgnoreCase(paymentType)) {
            return new PayPalService();
        } else {
            throw new PaymentException("Invalid option " + paymentType, P002.getCode());
        }
    }
}
