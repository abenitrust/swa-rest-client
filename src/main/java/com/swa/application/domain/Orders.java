package com.swa.application.domain;

import java.util.List;

public class Orders {
	List<Order> orders;

	public Orders() {
	}

	public Orders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Orders{" +
				"orders=" + orders +
				'}';
	}
}
