package br.com.nepomuceno.domain.enums;

public enum TaxTypeEnum {
    REAL("real"),
    PERCENTAGE("percentage");

    private String type;
    TaxTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
