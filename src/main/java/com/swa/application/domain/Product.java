package com.swa.application.domain;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private String productNumber;
    private String name;
    private double price;
    private String description;
    private int numberInStock;
}
