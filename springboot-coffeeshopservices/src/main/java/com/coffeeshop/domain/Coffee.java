package com.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coffee {

	private String name;
	@JsonProperty("price(USD)")
	private Integer price;

	public Coffee() {
	}

	public Coffee(final String name, final String description, final Integer totalAvailablity,final Integer price) {
		super();
		this.name = name;
		this.description = description;
		this.totalAvailablity = totalAvailablity;
		this.price = price;
	}

	private String description;
	private Integer totalAvailablity;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getTotalAvailablity() {
		return totalAvailablity;
	}

	public void setTotalAvailablity(final Integer totalAvailablity) {
		this.totalAvailablity = totalAvailablity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
