package br.com.nepomuceno.application.gateway;

import br.com.nepomuceno.domain.OrderDetail;

public interface PaymentService {
    String process(OrderDetail order) throws Exception;
}
