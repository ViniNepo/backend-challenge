package br.com.nepomuceno.exceptions;

public class PaymentException extends Exception {
    private String code;

    public PaymentException(String message, String code) {
        super(message);
        this.code = code;
    }
}
