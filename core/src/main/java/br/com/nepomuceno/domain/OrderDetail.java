package br.com.nepomuceno.domain;

public class OrderDetail {
    private String paymentMethod;
    private String productName;
    private Double subtotal;
    private Double shipping;
    private Double tax;
    private Double total;

    public OrderDetail(String paymentMethod, String productName, Double subtotal,
                       Double shipping, Double tax, Double total) {
        this.paymentMethod = paymentMethod;
        this.productName = productName;
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.tax = tax;
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getProductName() {
        return productName;
    }

    public String getSubtotal() {
        return String.format("%.2f", subtotal).replace(',','.');
    }

    public String getShipping() {
        return String.format("%.2f", shipping).replace(',','.');
    }

    public String getTax() {
        return String.format("%.2f", tax).replace(',','.');
    }

    public String getTotal() {
        return String.format("%.2f", total).replace(',','.');
    }

}
