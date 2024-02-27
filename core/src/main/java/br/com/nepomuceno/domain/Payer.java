package br.com.nepomuceno.domain;

import br.com.nepomuceno.domain.enums.PaymentMethodEnum;

public class Payer {

    private PaymentMethodEnum paymentMethod;
    private Double amount;

    public Payer(PaymentMethodEnum paymentMethod, Double amount) {
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
