package com.coffeeshop.services.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.coffeeshop.controllers.CoffeeInventoryController;
import com.coffeeshop.domain.Customer;
import com.coffeeshop.domain.OrderSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author Navaneeth
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = CoffeeInventoryController.class, secure = true)
public class CoffeeInventoryTest {

	@Mock
	private HttpServletRequest testRequest;

	@Mock
	private HttpServletResponse testResponse;

	@InjectMocks
	private CoffeeInventoryController coffeeInventory;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void clear() {
		this.mockMvc = null;
	}

	@Test
	public void test_AddCustomer_Return_unauthorized_401() throws Exception {

		mockMvc.perform(post("/customer").content(TestUtils.convertObjectToJsonBytes(TestUtils.buildCustomer())))
				.andExpect(status().isUnauthorized());

	}

	@Test
	public void test_Login() throws Exception {
		this.mockMvc.perform(post("/customer").with(httpBasic("Rob", "pwd#"))).andExpect(authenticated());
	}

	@Test
	public void test_AddCustomer_Return_Success() throws Exception {
		Customer cust = TestUtils.buildCustomer();
		cust.setCustomerName("John");
		cust.setPhoneNumber("9986752468");
		String requestJson = TestUtils.convertRequestObjectoString(cust);
		this.mockMvc
				.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void test_AddCustomer_Return_failure_withInvalidPhone() throws Exception {
		Customer cust = TestUtils.buildCustomer();
		cust.setPhoneNumber("1234");
		String requestJson = TestUtils.convertRequestObjectoString(cust);
		this.mockMvc
				.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void test_ShowCustomer_Return_Success() throws Exception {
		String requestJson = TestUtils.convertRequestObjectoString(TestUtils.buildCustomer());
		this.mockMvc.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJson));
		this.mockMvc
				.perform(get("/coffeeshop/customer").contentType(MediaType.APPLICATION_JSON)
						.with(httpBasic("Rob", "pwd#")).param("customerName", "navaneeth"))
				.andExpect(status().is2xxSuccessful());

	}

	@Test
	public void test_UpdateCustomer_Return_Success() throws Exception {
		String requestJson = TestUtils.convertRequestObjectoString(TestUtils.buildCustomer());
		this.mockMvc.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJson));
		this.mockMvc
				.perform(put("/coffeeshop/customer/{customerName:.+}", "navaneeth")
						.contentType(MediaType.APPLICATION_JSON).with(httpBasic("Rob", "pwd#")).content(requestJson))
				.andExpect(status().is2xxSuccessful());

	}

	@Test
	public void test_UpdateCustomer_failure_WithNoPermission() throws Exception {
		String requestJson = TestUtils.convertRequestObjectoString(TestUtils.buildCustomer());
		this.mockMvc.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJson));
		this.mockMvc
				.perform(put("/coffeeshop/customer/{customerName:.+}", "navaneeth")
						.contentType(MediaType.APPLICATION_JSON).with(httpBasic("Nav", "pwd#")).content(requestJson))
				.andExpect(status().is4xxClientError());

	}

	@Test
	public void test_AddCoffee_Return_Success() throws Exception {
		String requestJson = TestUtils.convertRequestObjectoString(TestUtils.buildCoffee());
		this.mockMvc
				.perform(post("/coffeeshop/coffee").with(httpBasic("Rob", "pwd#"))
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().is2xxSuccessful());

	}

	@Test
	public void test_Order_Return_Success() throws Exception {
		String requestJsonCustomer = TestUtils.convertRequestObjectoString(TestUtils.buildCustomer());
		this.mockMvc.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJsonCustomer));
		String requestJsonCoffee = TestUtils.convertRequestObjectoString(TestUtils.buildCoffee());
		this.mockMvc.perform(post("/coffeeshop/coffee").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJsonCoffee));
		String requestJsonOrder = TestUtils.convertRequestObjectoString(TestUtils.buildOrder());
		MvcResult mvcResult = mockMvc
				.perform(post("/coffeeshop/order").with(httpBasic("Rob", "pwd#"))
						.contentType(MediaType.APPLICATION_JSON).content(requestJsonOrder))
				.andExpect(status().is2xxSuccessful()).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		ObjectMapper mapper = new ObjectMapper();
		OrderSummary responseObj = mapper.readValue(response.getContentAsString(), OrderSummary.class);
		Assert.assertEquals("navaneeth", responseObj.getCustomer().getCustomerName());

	}

	@Test
	public void test_Order_Return_Failure() throws Exception {
		String requestJsonCustomer = TestUtils.convertRequestObjectoString(TestUtils.buildCustomer());
		this.mockMvc.perform(post("/coffeeshop/customer").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJsonCustomer));
		String requestJsonCoffee = TestUtils.convertRequestObjectoString(TestUtils.buildCoffee());
		this.mockMvc.perform(post("/coffeeshop/coffee").with(httpBasic("Rob", "pwd#"))
				.contentType(MediaType.APPLICATION_JSON).content(requestJsonCoffee));
		String requestJsonOrder = TestUtils.convertRequestObjectoString(TestUtils.buildWrongOrder());
		mockMvc.perform(post("/coffeeshop/order").with(httpBasic("Rob", "pwd#")).contentType(MediaType.APPLICATION_JSON)
				.content(requestJsonOrder)).andExpect(status().is4xxClientError()).andReturn();

	}

}
