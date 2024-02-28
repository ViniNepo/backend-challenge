package br.com.nepomuceno.domain;

import br.com.nepomuceno.domain.enums.TaxTypeEnum;

public class DeliveryTax {

    private TaxTypeEnum taxTypeEnum;
    private Double value;

    public DeliveryTax(TaxTypeEnum voucherType, Double amount) {
        this.taxTypeEnum = voucherType;
        this.value = amount;
    }

    public TaxTypeEnum getTaxTypeEnum() {
        return taxTypeEnum;
    }

    public void setTaxTypeEnum(TaxTypeEnum taxTypeEnum) {
        this.taxTypeEnum = taxTypeEnum;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
