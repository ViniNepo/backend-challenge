package br.com.nepomuceno.application.gateway;

import br.com.nepomuceno.domain.OrderDetail;

import java.util.List;

public interface CreatePaymentLinkGateway {
    List<String> createPaymentLink(List<OrderDetail> payload) throws Exception;
}
