package br.com.nepomuceno.domain;

import br.com.nepomuceno.domain.enums.FeeTypeEnum;

public class Fee {

    private FeeTypeEnum feeType;
    private Double amount;

    public Fee(FeeTypeEnum feeType, Double amount) {
        this.feeType = feeType;
        this.amount = amount;
    }

    public FeeTypeEnum getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeTypeEnum feeType) {
        this.feeType = feeType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
