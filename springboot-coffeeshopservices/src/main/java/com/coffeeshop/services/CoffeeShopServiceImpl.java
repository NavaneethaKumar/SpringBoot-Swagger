package com.coffeeshop.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coffeeshop.domain.Coffee;
import com.coffeeshop.domain.Customer;
import com.coffeeshop.domain.Order;
import com.coffeeshop.domain.OrderSummary;
import com.coffeeshop.domain.TotalSoldOut;
import com.coffeeshop.exception.CoffeeShopException;

/**
 * @author Navaneeth
 *
 */
@Component
public class CoffeeShopServiceImpl implements CoffeeShopService {
	
	private static final Logger logger = LoggerFactory.getLogger(CoffeeShopServiceImpl.class);

	/*
	 * InMemory Cache
	 */
	private Map<String, Customer> customerNameMap = new ConcurrentHashMap<>();
	private Map<String, Customer> customerPhoneNumberMap = new ConcurrentHashMap<>();
	private Map<String, Coffee> cofeeVarietyMap = new ConcurrentHashMap<>();
	private Map<String, Integer> coffeeSoldOutMap = new ConcurrentHashMap<>();

	@Override
	public void saveCustomerDetails(Customer customer) throws CoffeeShopException {
		
		if (customerNameMap.containsKey(customer.getCustomerName())
				|| customerPhoneNumberMap.containsKey(customer.getPhoneNumber())) {
			logger.error("Customer Already Exists with this Information");
			throw new CoffeeShopException(HttpStatus.CONFLICT, "Customer Already Exists with this Information");
		}
		customerNameMap.put(customer.getCustomerName(), customer);
		customerPhoneNumberMap.put(customer.getPhoneNumber(), customer);
	}

	@Override
	public Customer findCustomerByName(String name) {
		return customerNameMap.get(name);
	}

	@Override
	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		return customerPhoneNumberMap.get(phoneNumber);
	}

	@Override
	public Collection<Customer> listAllCustomers() {
		return customerNameMap.values();
	}

	@Override
	public void saveCoffeeVariety(Coffee coffee) {
		cofeeVarietyMap.put(coffee.getName(), coffee);
	}

	@Override
	public OrderSummary processOrder(Order order) throws CoffeeShopException {
		Optional<?> optName = Optional.ofNullable(customerNameMap);
		Optional<?> optCofeeVariety = Optional.ofNullable(cofeeVarietyMap);
		if (!optName.isPresent()||!optCofeeVariety.isPresent()) {
			logger.error("Customer Already Exists with this Information");
			throw new CoffeeShopException(HttpStatus.BAD_REQUEST,
					"Customer /Variety details not oboarded yet to process order");
		}
		if (!customerNameMap.containsKey(order.getCustomerName())) {
			throw new CoffeeShopException(HttpStatus.BAD_REQUEST,
					"Customer details not found  to process order,please add customer details");
		}
		if (!cofeeVarietyMap.containsKey(order.getCoffeedetails().getVariety())) {
			throw new CoffeeShopException(HttpStatus.BAD_REQUEST, "Requested variety coffee not available");
		}
		if (coffeeSoldOutMap.containsKey(order.getCoffeedetails().getVariety())) {
			coffeeSoldOutMap.put(order.getCoffeedetails().getVariety(),
					coffeeSoldOutMap.get(order.getCoffeedetails().getVariety())
							+ order.getCoffeedetails().getQuantity());
		} else {
			coffeeSoldOutMap.put(order.getCoffeedetails().getVariety(), order.getCoffeedetails().getQuantity());
		}
		Customer cutomerDetails = customerNameMap.get(order.getCustomerName());
		OrderSummary orderSummary = new OrderSummary();
		orderSummary.setCustomer(cutomerDetails);
		orderSummary.setOrderId(UUID.randomUUID().toString());
		orderSummary.setItemPrice(String.valueOf(getTotalPrice(getVarietyPrice(order.getCoffeedetails().getVariety()),
				order.getCoffeedetails().getQuantity()) + "USD"));
		orderSummary.setItemOrdered(order.getCoffeedetails().getVariety() + "," + order.getCoffeedetails().getQuantity()
				+ " " + "Quantity");
		orderSummary.setOrderedDate(getTimeStamp());
		return orderSummary;
	}

	private Integer getTotalPrice(Integer itemPrice, Integer quantity) {
		return itemPrice * quantity;
	}

	private Integer getVarietyPrice(String coffeeName) {
		return cofeeVarietyMap.get(coffeeName).getPrice();
	}

	@Override
	public List<TotalSoldOut> getTotalSoldOut() {
		List<TotalSoldOut> listSold = new ArrayList<>();
		cofeeVarietyMap.entrySet().forEach(entry -> {
			TotalSoldOut totalSold = new TotalSoldOut();
			totalSold.setCoffeeVariety(entry.getValue().getName());
			totalSold.setTotalServing(entry.getValue().getTotalAvailablity());
			totalSold.setTotalSoldOut(coffeeSoldOutMap.get(entry.getValue().getName()) == null ? 0
					: coffeeSoldOutMap.get(entry.getValue().getName()));
			listSold.add(totalSold);
		});
		return listSold;
	}

	private String getTimeStamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return now.format(formatter);
	}

	@Override
	public void updateCustomer(String customerName,Customer customer) throws CoffeeShopException {
		if (!customerNameMap.containsKey(customerName)) {
			throw new CoffeeShopException(HttpStatus.NOT_FOUND,
					"Customer not found");
		}
		customerNameMap.put(customerName, customer);
		
	}

}
