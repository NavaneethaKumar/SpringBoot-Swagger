package com.coffeeshop.domain;

import javax.validation.constraints.Pattern;

import com.coffeeshop.validators.NotNullOrEmpty;


public class Customer {
	
	@NotNullOrEmpty
	private String customerName;	
	private String state;
	private String address;
	@NotNullOrEmpty
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phoneNumber;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj== this) return true;
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer user = (Customer) obj;
        return user.customerName.equals(customerName) &&
                user.phoneNumber.equals(phoneNumber);
	}
	@Override
    public int hashCode(){
		int result = 17;
        result = 31 * result + customerName.hashCode();
        result=31 * result + phoneNumber.hashCode();
        return result;        
	}




}
