package com.swa.application.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Order {

	@Id
	private String orderNumber;
	private String customerID;
	private CustomerInfo customerInfo;
	private List<OrderLine> orderLines;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerId) {
		this.customerID = customerId;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderNumber='" + orderNumber + '\'' +
				", customerID='" + customerID + '\'' +
				", orderLines=" + orderLines +
				'}';
	}
}
