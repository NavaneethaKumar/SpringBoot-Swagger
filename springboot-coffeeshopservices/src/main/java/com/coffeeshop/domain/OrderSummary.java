package com.coffeeshop.domain;

public class OrderSummary {

	private String orderedDate;
	private String orderId;
	private Customer customer;
	private String itemOrdered;
	private String itemPrice;

	public String getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(String orderedDate) {
		this.orderedDate = orderedDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemOrdered() {
		return itemOrdered;
	}

	public void setItemOrdered(String itemOrdered) {
		this.itemOrdered = itemOrdered;
	}

}
