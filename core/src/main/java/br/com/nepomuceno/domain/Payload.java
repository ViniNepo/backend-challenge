package br.com.nepomuceno.domain;

import java.util.List;

public class Payload {

    private List<Payer> payers;
    private ServiceTax serviceTax;
    private DeliveryTax deliveryTax;
    private Voucher voucher;
    private Double totalAmount;

    public Payload(List<Payer> payers, ServiceTax serviceTax, DeliveryTax deliveryTax, Voucher voucher, Double totalAmount) {
        this.payers = payers;
        this.serviceTax = serviceTax;
        this.deliveryTax = deliveryTax;
        this.voucher = voucher;
        this.totalAmount = totalAmount;
    }

    public List<Payer> getPayers() {
        return payers;
    }

    public void setPayers(List<Payer> payers) {
        this.payers = payers;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ServiceTax getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(ServiceTax serviceTax) {
        this.serviceTax = serviceTax;
    }

    public DeliveryTax getDeliveryTax() {
        return deliveryTax;
    }

    public void setDeliveryTax(DeliveryTax deliveryTax) {
        this.deliveryTax = deliveryTax;
    }
}
