package com.swa.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerDto {
    private String orderNumber;
    private String customerNumber;
}
