package br.com.nepomuceno.infrastructure.config;

import br.com.nepomuceno.application.gateway.CreatePaymentLinkGateway;
import br.com.nepomuceno.infrastructure.service.PaymentStrategyFactory;
import br.com.nepomuceno.application.usecaseimpl.CreatePaymentLinkUseCaseImpl;
import br.com.nepomuceno.usecase.CreatePaymentLinkUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Bean
    public CreatePaymentLinkUseCase createPaymentLinkUseCase(CreatePaymentLinkGateway createPaymentLinkGateway) {
        return new CreatePaymentLinkUseCaseImpl(createPaymentLinkGateway);
    }
}
