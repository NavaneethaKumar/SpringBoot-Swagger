package com.coffeeshop.domain;

public class TotalSoldOut {
	
	private String coffeeVariety;
	private Integer totalServing;
	private Integer totalSoldOut;
	public  String getCoffeeVariety() {
		return coffeeVariety;
	}
	public void setCoffeeVariety(String coffeeVariety) {
		this.coffeeVariety = coffeeVariety;
	}
	public Integer getTotalServing() {
		return totalServing;
	}
	public void setTotalServing(Integer totalServing) {
		this.totalServing = totalServing;
	}
	public Integer getTotalSoldOut() {
		return totalSoldOut;
	}
	public void setTotalSoldOut(Integer totalSoldOut) {
		this.totalSoldOut = totalSoldOut;
	}

}
