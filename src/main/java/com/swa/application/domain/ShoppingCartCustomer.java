package com.swa.application.domain;


public class ShoppingCartCustomer {
    private String customerId;
    private Long cartNumber;

    public ShoppingCartCustomer(String customerId, Long cartNumber) {
        this.customerId = customerId;
        this.cartNumber = cartNumber;
    }

    public ShoppingCartCustomer() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(Long cartNumber) {
        this.cartNumber = cartNumber;
    }
}
