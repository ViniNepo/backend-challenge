package br.com.nepomuceno.infrastructure.dto;

import br.com.nepomuceno.domain.enums.PaymentMethodEnum;

public class PayerDTO {

    private PaymentMethodEnum paymentMethod;
    private Double amount;

    public PayerDTO(PaymentMethodEnum paymentMethod, Double amount) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
