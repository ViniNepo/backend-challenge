package br.com.nepomuceno.domain.enums;

public enum PaymentMethodEnum {
    PAYPAL("paypal");

    private String type;
    PaymentMethodEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
