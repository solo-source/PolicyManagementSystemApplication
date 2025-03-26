package com.example.demo;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.controller.OrderController;
import com.entities.Orders;
import com.entities.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.IOrderService;
import com.service.OrderServiceImpl;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Spring2Test {

	@Autowired 
	private MockMvc mockMvc;

	@Mock
	private IOrderService service;

	@InjectMocks
	private OrderController ctrl;

	@MockBean
	private OrderServiceImpl studService;
	
	static String msgCons="";
	
	public static Restaurant getRestaurant(int restaurantId, String restaurantName, String cuisine, double rating, String location, List<Orders> ordersList) {
	    Class<?> restaurantObj = null;
	    Restaurant bean = null;
	    try {
	        restaurantObj = Class.forName("com.entities.Restaurant");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Restaurant as per the problem statement");
	    }

	    try {
	        Class<?>[] type = {int.class, String.class, String.class, double.class, String.class, List.class};
	        Constructor<?> cons;
	        try {
	            cons = restaurantObj.getConstructor(type);
	            Object[] obj = {restaurantId, restaurantName, cuisine, rating, location, ordersList};
	            Object newRestaurantInstance = cons.newInstance(obj);
	            bean = (Restaurant) newRestaurantInstance;
	        } catch (NoSuchMethodException | SecurityException | InstantiationException
	                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	            e.printStackTrace();
	    		msgCons = "Check the availability of appropriate Lombok annotation to generate all argument constructor in Restaurant class";
	            fail("Check the availability of appropriate Lombok annotation to generate all argument constructor in Restaurant class");
	        }
	    } catch (Exception e) {
			msgCons = "Check the availability of appropriate Lombok annotation to generate all argument constructor in Restaurant class";
	        fail("Check the availability of appropriate Lombok annotation to generate all argument constructor in Restaurant class");
	    }
	    return bean;
	}
	
	
	public static Orders getOrders(int orderId, String deliveryAddress, String paymentMethod, String status, double totalAmount, Restaurant restaurantObj) {
	    Class<?> ordersObj = null;
	    Orders bean = null;
	    try {
	        ordersObj = Class.forName("com.entities.Orders");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Orders as per the problem statement");
	    }

	    try {
	        Class<?>[] type = {int.class, String.class, String.class, String.class, double.class, Restaurant.class};
	        Constructor<?> cons;
	        try {
	            cons = ordersObj.getConstructor(type);
	            Object[] obj = {orderId, deliveryAddress, paymentMethod, status, totalAmount, restaurantObj};
	            Object newOrdersInstance = cons.newInstance(obj);
	            bean = (Orders) newOrdersInstance;
	        } catch (NoSuchMethodException | SecurityException | InstantiationException
	                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	            e.printStackTrace();
	        	msgCons = "Check the availability of appropriate Lombok annotation to generate all argument constructor in Orders class";
	            fail("Check the availability of appropriate Lombok annotation to generate all argument constructor in Orders class");
	        }
	    } catch (Exception e) {
	    	msgCons = "Check the availability of appropriate Lombok annotation to generate all argument constructor in Orders class";
	        fail("Check the availability of appropriate Lombok annotation to generate all argument constructor in Orders class");
	    }
	    return bean;
	}

	@Test
	@Order(1)
	public void test1RestApiCallForAddOrder() {

		try {

			Orders studObj = getOrders(1234, "123 Main Street, Cityville", "Credit Card", "Processing", 50.75, null);
			if(!msgCons.equals("")) {
				fail();
			}
			Mockito.when(studService.addOrder(Mockito.any(Orders.class), anyInt())).thenReturn(studObj);

			ObjectMapper objectMapper = new ObjectMapper();
			String studJson = objectMapper.writeValueAsString(studObj);

			// Build the request with the JSON data
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addOrder/1234").content(studJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			assertTrue(result.getResponse().getStatus() == 200);

		} catch (AssertionError e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
            	fail(msgCons);
            }
            else
			fail("Rest Service call for addOrder is not as per the requirement- Check whether the request method/service name is as per the requirement");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Rest Service call for addOrder is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}

	@Test
	@Order(2)
	public void test2RestApiCallForUpdatePaymentMethod() throws Exception {

		try {
			if(!msgCons.equals("")) {
				fail();
			}
			Orders studObj = getOrders(1234, "123 Main Street, Cityville", "Credit Card", "Processing", 50.75, null);
			Mockito.when(studService.updatePaymentMethod(anyInt(), anyString())).thenReturn(studObj);

			mockMvc.perform(put("/updatePaymentMethod/123/Cash").contentType(APPLICATION_JSON_UTF8))
					.andExpect(status().isOk());// .andExpect(jsonPath("$.modelName", Matchers.equalTo("S101")))
			// .andExpect(jsonPath("$.price", Matchers.equalTo(45000.0)));
		} catch (AssertionError e) {
			e.printStackTrace();
			
			if(!msgCons.equals("")) {
            	fail(msgCons);
            }
            else
			fail("Rest Service call for updatePaymentMethod is not as per the requirement- Check whether the request method/service name is as per the requirement");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Rest Service call for updatePaymentMethod is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}

	@Test
	@Order(3)
	public void test3RestApiCallForViewOrdersByStatus() {

		try {

			List<Orders> studObj = new ArrayList<Orders>();
			Mockito.when(studService.viewOrdersByStatus(anyString())).thenReturn(studObj);

			mockMvc.perform(get("/viewOrdersByStatus/Paid").contentType(APPLICATION_JSON_UTF8))
					.andExpect(status().isOk());

		} catch (AssertionError e) {
			e.printStackTrace();
			fail("Rest Service call for viewOrdersByStatus is not as per the requirement- Check whether the request method/service name is as per the requirement");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Rest Service call for viewOrdersByStatus is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}

	@Test
	@Order(4)
	public void test4RestApiCallForViewOrdersByRestaurantName() {

		try {

			mockMvc.perform(get("/viewOrdersByRestaurantName/Tasty Bites").contentType(APPLICATION_JSON_UTF8))
					.andExpect(status().isOk());
		} catch (AssertionError e) {
			e.printStackTrace();
			fail("Rest Service call for viewOrdersByRestaurantName is not as per the requirement- Check whether the request method/service name is as per the requirement");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Rest Service call for viewOrdersByRestaurantName is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}

	@Test
	@Order(5)
	public void test5RestApiCallForcancelOrder() {

		try {

			mockMvc.perform(delete("/cancelOrder/1234").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} catch (AssertionError e) {
			e.printStackTrace();
			fail("Rest Service call for cancelOrder is not as per the requirement- Check whether the request method/service name is as per the requirement");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Rest Service call for cancelOrder is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}
	
    static String testResult = "";
	
	@Test
	@Order(6)
	public void test6PredefinedValidationInRestAPIAddOrder() {
	    String msg = "Predefined Validation for /addOrder rest api call ";
	    try {
	        final String orderJson = "{\"orderId\": \"-3\"}";

	        MvcResult result = mockMvc
	                .perform(post("/addOrder/2009").content(orderJson).contentType(APPLICATION_JSON_UTF8))
	                .andExpect(status().isBadRequest())
	                .andReturn();

	        testResult = result.getResponse().getContentAsString();

	        if (testResult == null) {
	            msg = "Predefined Validation for /addOrder rest api call is not correct";
	        } else {
	            if (!testResult.toLowerCase().replace(" ", "").contains("providevaluefordeliveryaddress")) {
	                msg = msg + "for deliveryAddress is not correct, ";
	            }
	            if (!testResult.toLowerCase().replace(" ", "").contains("providevalueforpaymentmethod")) {
	                msg = msg + "for paymentMethod is not correct, ";
	            }
	            if (!testResult.toLowerCase().replace(" ", "").contains("providevalueforstatus")) {
	                msg = msg + "for status is not correct";
	            }
	        }

	        if (!msg.equals("Predefined Validation for /addOrder rest api call ")) {
	            fail(msg);
	        }
	    } catch (AssertionError e) {
	        e.printStackTrace();
	        fail(msg);
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Predefined Validation for /addOrder rest api call is not correct");
	    }
	}


	@Test
	@Order(7)
	public void test7PredefinedValidOrderIdInRestAPIAddOrder() {
	    String msg = "Predefined Validation for /addOrder rest api call ";
	    try {

	        if (testResult == null) {
	            msg = "Predefined Validation for /addOrder rest api call is not correct";
	        } else if (!testResult.toLowerCase().replace(" ", "").contains("orderidshouldbegreaterthan0")) {
	            msg = msg + "for orderId is not correct";
	        }

	        if (!msg.equals("Predefined Validation for /addOrder rest api call ")) {
	            fail(msg);
	        }
	    } catch (AssertionError e) {
	        e.printStackTrace();
	        fail(msg);
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Predefined Validation for /addOrder rest api call is not correct");
	    }
	}

	
	@Test
	@Order(8)
	public void test8PredefinedValidTotalAmountInRestAPIAddOrder() {
	    String msg = "Predefined Validation for /addOrder rest api call ";
	    try {

	        if (testResult == null) {
	            msg = "Predefined Validation for /addOrder rest api call is not correct";
	        } else if (!testResult.toLowerCase().replace(" ", "").contains("totalamountshouldbegreaterthan0")) {
	            msg = msg + "for totalAmount is not correct";
	        }

	        if (!msg.equals("Predefined Validation for /addOrder rest api call ")) {
	            fail(msg);
	        }
	    } catch (AssertionError e) {
	        e.printStackTrace();
	        fail(msg);
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Predefined Validation for /addOrder rest api call is not correct");
	    }
	}

}