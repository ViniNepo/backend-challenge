package br.com.nepomuceno.domain;

import br.com.nepomuceno.domain.enums.VoucherTypeEnum;

public class Voucher {

    private VoucherTypeEnum voucherType;
    private Double amount;

    public Voucher(VoucherTypeEnum voucherType, Double amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherTypeEnum getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherTypeEnum voucherType) {
        this.voucherType = voucherType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
