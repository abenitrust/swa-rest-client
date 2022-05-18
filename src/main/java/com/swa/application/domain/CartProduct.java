package com.swa.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct {
    private String productNumber;
    private String cartNumber;
    private int quantity;
}
