package com.coffeeshop.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.coffeeshop.domain.Coffee;
import com.coffeeshop.domain.Customer;
import com.coffeeshop.domain.Order;
import com.coffeeshop.domain.OrderSummary;
import com.coffeeshop.domain.TotalSoldOut;
import com.coffeeshop.exception.CoffeeShopException;

public interface CoffeeShopService {

	void saveCustomerDetails(Customer customer) throws CoffeeShopException;
	Customer findCustomerByName(String name);
	Customer findCustomerByPhoneNumber(String name);
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void updateCustomer(String customerName,Customer customer)throws CoffeeShopException;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Collection<Customer> listAllCustomers();
	void saveCoffeeVariety(Coffee coffee);
	OrderSummary processOrder(Order order) throws CoffeeShopException;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	List<TotalSoldOut> getTotalSoldOut();

}
