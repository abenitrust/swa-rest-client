package swa.application.swarestclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartProduct {
    private Long productNumber;
    private int quantity;
    private double price;
}
