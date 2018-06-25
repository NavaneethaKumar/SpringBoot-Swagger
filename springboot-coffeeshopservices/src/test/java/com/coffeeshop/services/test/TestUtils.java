package com.coffeeshop.services.test;

import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.MediaType;
import com.coffeeshop.domain.Coffee;
import com.coffeeshop.domain.CoffeeDetails;
import com.coffeeshop.domain.Customer;
import com.coffeeshop.domain.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Navaneeth
 *
 */
public class TestUtils {

	public static Customer buildCustomer() {
		Customer cust = new Customer();
		cust.setAddress("INDIA");
		cust.setCustomerName("navaneeth");
		cust.setPhoneNumber("9986752467");
		cust.setState("KA");
		return cust;
	}

	public static Coffee buildCoffee() {
		Coffee coffee = new Coffee();
		coffee.setDescription("capacino");
		coffee.setName("capacino");
		coffee.setPrice(10);
		coffee.setTotalAvailablity(100);
		return coffee;
	}

	public static Order buildOrder() {
		Order order = new Order();
		CoffeeDetails coffeeDetails = new CoffeeDetails();
		coffeeDetails.setQuantity(2);
		coffeeDetails.setVariety("capacino");
		order.setCoffeedetails(coffeeDetails);
		order.setCustomerName("navaneeth");
		return order;
	}

	public static Order buildWrongOrder() {
		Order order = new Order();
		CoffeeDetails coffeeDetails = new CoffeeDetails();
		coffeeDetails.setQuantity(2);
		coffeeDetails.setVariety("capacino");
		order.setCoffeedetails(coffeeDetails);
		order.setCustomerName("john");
		return order;
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

	public static String convertRequestObjectoString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(obj);
	}

}
