package com.coffeeshop.domain;

public class CoffeeDetails {
	
	private String variety;
	public CoffeeDetails() {}
	public CoffeeDetails(String variety, Integer quantity) {
		super();
		this.variety = variety;
		this.quantity = quantity;
	}
	private Integer quantity;
	public String getVariety() {
		return variety;
	}
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
