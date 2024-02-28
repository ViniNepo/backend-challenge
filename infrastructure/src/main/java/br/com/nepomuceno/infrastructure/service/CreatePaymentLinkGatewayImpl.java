package br.com.nepomuceno.infrastructure.service;


import br.com.nepomuceno.application.gateway.CreatePaymentLinkGateway;
import br.com.nepomuceno.application.gateway.PaymentService;
import br.com.nepomuceno.domain.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreatePaymentLinkGatewayImpl implements CreatePaymentLinkGateway {

    private final PaymentStrategyFactory paymentStrategyFactory;

    public CreatePaymentLinkGatewayImpl(PaymentStrategyFactory paymentStrategyFactory) {
        this.paymentStrategyFactory = paymentStrategyFactory;
    }

    @Override
    public List<String> createPaymentLink(List<OrderDetail> orders) throws Exception {

        List<String> links = new ArrayList<>();

        for (OrderDetail order : orders) {
            String link;

            PaymentService paymentService = paymentStrategyFactory.getPaymentService(order.getPaymentMethod());
            link = paymentService.process(order);

            links.add(link);
        }

        return links;
    }
}
