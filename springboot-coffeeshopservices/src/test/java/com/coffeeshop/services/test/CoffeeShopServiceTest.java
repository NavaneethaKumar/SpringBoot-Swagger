package com.coffeeshop.services.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import com.coffeeshop.domain.Customer;
import com.coffeeshop.domain.OrderSummary;
import com.coffeeshop.exception.CoffeeShopException;
import com.coffeeshop.services.CoffeeShopServiceImpl;
/**
 * @author Navaneeth
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeShopServiceTest {
	
	@InjectMocks
	private CoffeeShopServiceImpl coffeeShopService;
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	
	@Before
	public void setup() {
	    MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void test_AddCustomer_success() throws CoffeeShopException
	{
		Customer customerRequest=TestUtils.buildCustomer();
		coffeeShopService.saveCustomerDetails(customerRequest);
		Customer customer=coffeeShopService.findCustomerByName(customerRequest.getCustomerName());
		Assert.assertEquals("9986752467", customer.getPhoneNumber());
	}
	
	@Test(expected = CoffeeShopException.class)
	public void test_AddCustomer_Failure_AlreadyExistis() throws CoffeeShopException
	{
		Customer customerRequest=TestUtils.buildCustomer();
		coffeeShopService.saveCustomerDetails(customerRequest);
		coffeeShopService.saveCustomerDetails(customerRequest);
	}
	
	@Test
	public void test_ListAddedCustomers() throws CoffeeShopException
	{
		Customer customerRequest=TestUtils.buildCustomer();
		coffeeShopService.saveCustomerDetails(customerRequest);
		Assert.assertEquals(1, coffeeShopService.listAllCustomers().size());			
	}
	
	@Test
	public void test_AddCoffee_succeess()
	{
		coffeeShopService.saveCoffeeVariety(TestUtils.buildCoffee());	
	}
	
	@Test
	public void test_ProcessOrder_succeess() throws CoffeeShopException
	{
		Customer customerRequest=TestUtils.buildCustomer();
		coffeeShopService.saveCustomerDetails(customerRequest);
		coffeeShopService.saveCoffeeVariety(TestUtils.buildCoffee());	
		OrderSummary orderSummary=coffeeShopService.processOrder(TestUtils.buildOrder());
		Assert.assertEquals("navaneeth",orderSummary.getCustomer().getCustomerName());
	}
	
	@Test(expected = CoffeeShopException.class)
	public void test_ProcessOrder_WithNoCustomer_VarietyExist() throws CoffeeShopException
	{		
		coffeeShopService.saveCoffeeVariety(TestUtils.buildCoffee());	
		coffeeShopService.processOrder(TestUtils.buildOrder());
	}
	
	@Test
	public void test_SoldOut_succeess() throws CoffeeShopException 
	{
		Customer customerRequest=TestUtils.buildCustomer();
		coffeeShopService.saveCustomerDetails(customerRequest);
		coffeeShopService.saveCoffeeVariety(TestUtils.buildCoffee());	
		coffeeShopService.processOrder(TestUtils.buildOrder());
		Assert.assertEquals("2",String.valueOf(coffeeShopService.getTotalSoldOut().get(0).getTotalSoldOut()));
	}
	
	
	
	
}
