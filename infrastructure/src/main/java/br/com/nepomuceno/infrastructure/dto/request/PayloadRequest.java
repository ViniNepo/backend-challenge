package br.com.nepomuceno.infrastructure.dto.request;

import br.com.nepomuceno.infrastructure.dto.*;

import java.util.List;

public class PayloadRequest {

    private List<PayerDTO> payersDTO;
    private ServiceTaxDTO serviceTaxDTO;
    private DeliveryTaxDTO deliveryTaxDTO;
    private VoucherDTO voucherDTO;
    private Double totalAmount;

    public PayloadRequest(List<PayerDTO> payersDTO, ServiceTaxDTO serviceTaxDTO, DeliveryTaxDTO deliveryTaxDTO, VoucherDTO voucherDTO, Double totalAmount) {
        this.payersDTO = payersDTO;
        this.serviceTaxDTO = serviceTaxDTO;
        this.deliveryTaxDTO = deliveryTaxDTO;
        this.voucherDTO = voucherDTO;
        this.totalAmount = totalAmount;
    }

    public List<PayerDTO> getPayersDTO() {
        return payersDTO;
    }

    public void setPayersDTO(List<PayerDTO> payersDTO) {
        this.payersDTO = payersDTO;
    }

    public VoucherDTO getVoucherDTO() {
        return voucherDTO;
    }

    public void setVoucherDTO(VoucherDTO voucherDTO) {
        this.voucherDTO = voucherDTO;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ServiceTaxDTO getServiceTaxDTO() {
        return serviceTaxDTO;
    }

    public void setServiceTaxDTO(ServiceTaxDTO serviceTaxDTO) {
        this.serviceTaxDTO = serviceTaxDTO;
    }

    public DeliveryTaxDTO getDeliveryTaxDTO() {
        return deliveryTaxDTO;
    }

    public void setDeliveryTaxDTO(DeliveryTaxDTO deliveryTaxDTO) {
        this.deliveryTaxDTO = deliveryTaxDTO;
    }
}
