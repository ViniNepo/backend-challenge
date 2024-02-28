package br.com.nepomuceno.application.usecaseimpl;

import br.com.nepomuceno.application.gateway.CreatePaymentLinkGateway;
import br.com.nepomuceno.domain.*;
import br.com.nepomuceno.domain.enums.TaxTypeEnum;
import br.com.nepomuceno.usecase.CreatePaymentLinkUseCase;

import java.util.ArrayList;
import java.util.List;

public class CreatePaymentLinkUseCaseImpl implements CreatePaymentLinkUseCase {

    private final CreatePaymentLinkGateway createPaymentLinkGateway;

    public CreatePaymentLinkUseCaseImpl(CreatePaymentLinkGateway createPaymentLinkGateway) {
        this.createPaymentLinkGateway = createPaymentLinkGateway;
    }

    @Override
    public List<String> createLink(Payload payload) throws Exception {
        List<OrderDetail> orders = calculateAmountByPayer(payload);

        return createPaymentLinkGateway.createPaymentLink(orders);
    }

    private List<OrderDetail> calculateAmountByPayer(Payload payload) {
        List<OrderDetail> orders = new ArrayList<>();
        double serviceTax = getProporcionalValue(payload.getTotalAmount(), payload.getServiceTax().getValue(), payload.getServiceTax().getTaxTypeEnum());
        double deliveryTax = getProporcionalValue(payload.getTotalAmount(), payload.getDeliveryTax().getValue(), payload.getDeliveryTax().getTaxTypeEnum());
        double voucherTax = getProporcionalValue(payload.getTotalAmount(), payload.getVoucher().getValue(), payload.getVoucher().getTaxTypeEnum());

        for (Payer p : payload.getPayers()) {
            double relativePurchase = 0.0;
            if (payload.getVoucher().getValue() > 0) {
                relativePurchase = getProporcionalAmount(p.getAmount(), payload.getTotalAmount());
            }

            double relativeAmount = relativePurchase * (payload.getTotalAmount() + serviceTax + deliveryTax - voucherTax);
            double relativeDeliveryTax = relativePurchase * deliveryTax;
            double relativeServiceTax = relativePurchase * serviceTax;
            double foodValue = relativeAmount - relativeDeliveryTax - relativeServiceTax;

            OrderDetail orderDetail = new OrderDetail(p.getPaymentMethod().getType(), "Food Service", foodValue, relativeDeliveryTax, relativeServiceTax, relativeAmount);
            orders.add(orderDetail);
        }

        return orders;
    }

    private Double getProporcionalValue(Double totalAmount, Double taxValue, TaxTypeEnum taxTypeEnum) {
        double value = 0.0;
        if (taxValue > 0) {
            if ("real".equalsIgnoreCase(taxTypeEnum.getType())) {
                value = taxValue;
            } else {
                value = (taxValue / totalAmount) * totalAmount;
            }
        }

        return value;
    }

    private Double getProporcionalAmount(Double amount, Double totalAmount) {
        return amount / totalAmount;
    }

}
