package com.swa.application.domain;

public class ShoppingCartProduct {
    private Long productNumber;
    private int quantity;
    private double price;

    public ShoppingCartProduct() {
    }

    public ShoppingCartProduct(Long productNumber, int quantity, double price) {
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public Long getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Long productNumber) {
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
}
