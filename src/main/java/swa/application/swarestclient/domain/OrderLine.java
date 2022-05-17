package swa.application.swarestclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

    private String productNumber;

    private int quantity;

    private double price;



}
