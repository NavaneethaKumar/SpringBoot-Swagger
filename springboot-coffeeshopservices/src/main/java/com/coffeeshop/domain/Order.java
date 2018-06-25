package com.coffeeshop.domain;

public class Order {
	
	private String customerName;
	private CoffeeDetails coffeedetails;
	public Order()
	{}
	public Order(final String customerName, final CoffeeDetails coffeedetails
			) {
		super();		
		this.customerName = customerName;
		this.coffeedetails = coffeedetails;		
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public CoffeeDetails getCoffeedetails() {
		return coffeedetails;
	}
	public void setCoffeedetails(CoffeeDetails coffeedetails) {
		this.coffeedetails = coffeedetails;
	}
	
	

}
