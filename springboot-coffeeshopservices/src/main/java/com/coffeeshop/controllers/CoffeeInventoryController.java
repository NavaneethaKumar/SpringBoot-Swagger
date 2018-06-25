package com.coffeeshop.controllers;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.coffeeshop.domain.Coffee;
import com.coffeeshop.domain.Customer;
import com.coffeeshop.domain.Order;
import com.coffeeshop.domain.OrderSummary;
import com.coffeeshop.domain.TotalSoldOut;
import com.coffeeshop.exception.CoffeeShopException;
import com.coffeeshop.services.CoffeeShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.spring.web.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Navaneeth
 *
 */
@RequestMapping("/coffeeshop")
@Api(value = "coffeeshop", description = "Operations Pertaining to Products in Coffee Shop")
@RestController
public class CoffeeInventoryController {

	@Autowired
	private CoffeeShopService service;
	private static final Logger logger = LoggerFactory.getLogger(CoffeeInventoryController.class);	

	@ApiOperation(value = "Add a customer")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid customer information"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Customer Already Exists with this Information") })
	@RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Json> saveCustomer(@RequestBody @Valid final Customer customer) throws CoffeeShopException {
		logger.info("Entering save Customer");
		service.saveCustomerDetails(customer);
		logger.info("Exiting save Customer");
		return new ResponseEntity<Json>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Search a customer  with name or phonenumber", response = Customer.class)
	@ApiResponses(value = {
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden") })
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> showCustomer(
			@RequestParam(value = "customerName", required = false) final String customerName,
			@RequestParam(value = "customerPhoneNumber", required = false) final String customerPhoneNumber) {
		logger.info("Entering showCustomer");
		Customer customer = null;
		if (!StringUtils.isEmpty(customerName)) {
			customer = service.findCustomerByName(customerName);
		} else if (!StringUtils.isEmpty(customerPhoneNumber)) {
			customer = service.findCustomerByPhoneNumber(customerPhoneNumber);
		}
		if (customer == null) {
			logger.error("The resource you were trying to reach is not found");
			return new ResponseEntity<String>("The resource you were trying to reach is not found",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Exiting showCustomer");
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@ApiOperation(value = "Display all customers(able to see only who has admin roles)", response = Customer.class)
	@ApiResponses(value = {
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden") })
	@RequestMapping(value = "/customerlist", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> listCustomers() {
		logger.info("Entering listCustomers");
		return new ResponseEntity<Collection<Customer>>(service.listAllCustomers(), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Update a customer (able to do only who has admin roles)")
	@ApiResponses(value =
            { @ApiResponse(code = 400, message = "Invalid customer information"),
            @ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
			 })
	@RequestMapping(method = RequestMethod.PUT,consumes = "application/json", value = { "/customer/{customerName:.+}" })
	public ResponseEntity<Json> updateCustomer(
			@PathVariable  final String customerName,
			@RequestBody @Valid final Customer customer, final HttpServletRequest request,
			final HttpServletResponse response) throws CoffeeShopException {
		logger.info("Entering updateCustomer");
		service.updateCustomer(customerName, customer);
		logger.info("Exiting updateCustomer");
		return new ResponseEntity<Json>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Add a coffee variety details")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid customer information"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/coffee", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Json> saveCoffeeVariety(@RequestBody @Valid final Coffee coffee)
			throws CoffeeShopException {
		logger.info("Entering saveCoffeeVariety");
		service.saveCoffeeVariety(coffee);
		logger.info("Exiting saveCoffeeVariety");
		return new ResponseEntity<Json>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Place a order")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Order placed successfully"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/order", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<OrderSummary> processOrder(@RequestBody @Valid final Order order) throws CoffeeShopException {
		logger.info("Entering processOrder");
		OrderSummary orderSummary = service.processOrder(order);
		logger.info("Exiting processOrder");
		return new ResponseEntity<OrderSummary>(orderSummary, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Display all soldout coffee variety reports(able to see only who has admin roles)", response = TotalSoldOut.class)
	@ApiResponses(value = {
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden") })
	@RequestMapping(value = "/soldout", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> listSoldOut() {
		logger.info("Entering listSoldOut");
		return new ResponseEntity<Collection<TotalSoldOut>>(service.getTotalSoldOut(), HttpStatus.OK);
	}

}
