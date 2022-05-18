package com.swa.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private String orderNumber;
	private String customerID;
	private Customer customer;
	private List<OrderLine> orderLines;
	private OrderStatus orderStatus;
}
