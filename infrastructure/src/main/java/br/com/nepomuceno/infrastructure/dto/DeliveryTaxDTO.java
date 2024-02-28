package br.com.nepomuceno.infrastructure.dto;

import br.com.nepomuceno.domain.enums.TaxTypeEnum;

public class DeliveryTaxDTO {

    private TaxTypeEnum taxTypeEnum;
    private Double amount;

    public DeliveryTaxDTO(TaxTypeEnum taxTypeEnum, Double amount) {
        this.taxTypeEnum = taxTypeEnum;
        this.amount = amount;
    }

    public TaxTypeEnum getTaxTypeEnum() {
        return taxTypeEnum;
    }

    public void setTaxTypeEnum(TaxTypeEnum taxTypeEnum) {
        this.taxTypeEnum = taxTypeEnum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
