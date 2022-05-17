package com.swa.application.domain;

public class OrderLine {

    private String productNumber;

    private int quantity;

    private double price;

    public OrderLine() {
    }

    public OrderLine(String productNumber, int quantity, double price) {
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartLine{" +
                "productNumber='" + productNumber + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
