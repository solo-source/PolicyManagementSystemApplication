package com.example.demo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
//import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
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

import com.entities.Orders;
import com.entities.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.RestaurantServiceImpl;
import com.service.IRestaurantService;


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Spring1Test {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IRestaurantService service;
	
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
	public void test1RestApiCallForAddRestaurant() {

		try {

			Restaurant RestaurantObj=getRestaurant(1001,"Tasty Bites","Italian",4.2,"Downtown",null);
			if(!msgCons.equals("")) {
				fail();
			}
			Mockito.when(service.addRestaurant(Mockito.any(Restaurant.class))).thenReturn(RestaurantObj);		
			
			ObjectMapper objectMapper = new ObjectMapper();
	        String RestaurantJson = objectMapper.writeValueAsString(RestaurantObj);

	        // Build the request with the JSON data
	        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addRestaurant")
	                .content(RestaurantJson)
	                .contentType(MediaType.APPLICATION_JSON);

	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	        assertTrue(result.getResponse().getStatus() == 200);

		}
		catch(AssertionError e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
            	fail(msgCons);
            }
            else
			fail("Rest Service call for addRestaurant is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}            	
		catch (Exception e) { 
			e.printStackTrace();
			fail("Rest Service call for addRestaurant is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}


	@Test
	@Order(2)
	public void test2RestApiCallForViewRestaurantById() {


		try {
			

			mockMvc
			.perform(get("/viewRestaurantById/1001").contentType(APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
			
		}
		catch(AssertionError e) {
			e.printStackTrace();
			fail("Rest Service call for viewRestaurantById is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}            	
		catch (Exception e) { 
			e.printStackTrace();
			fail("Rest Service call for viewRestaurantById is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}
	
	@Test
	@Order(3)
	public void test3RestApiCallForViewRestaurantsByRatingAndLocation() {


		try {

			mockMvc
			.perform(get("/viewRestaurantsByRatingAndLocation/6/Italian").contentType(APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
		}
		catch(AssertionError e) {
			e.printStackTrace();
			fail("Rest Service call for viewRestaurantsByRatingAndLocation is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}            	
		catch (Exception e) { 
			e.printStackTrace();
			fail("Rest Service call for viewRestaurantsByRatingAndLocation is not as per the requirement- Check whether the request method/service name is as per the requirement");
		}
	}

	
	 
	    
	@Test
	@Order(4)
	public void test4RestApiCallForUpdateRestaurantRating() {
	    try {
	    	if(!msgCons.equals("")) {
				fail();
			}
	        Restaurant restaurant = getRestaurant(2001, "Restaurant1", "Cuisine1", 4.5, "Location1", null);
	        when(service.updateRestaurantRating(2001, 4.5)).thenReturn(restaurant);

	        RequestBuilder requestBuilder = put("/updateRestaurantRating/2001/4.5");

	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	        int status = result.getResponse().getStatus();
	        String content = result.getResponse().getContentAsString();

	        assertEquals(200, status);
	        assertNotNull(content);
	    } catch (AssertionError e) {
	        e.printStackTrace();
	        if(!msgCons.equals("")) {
            	fail(msgCons);
            }
            else
	        fail("Rest Service call for updateRestaurantRating is not as per the requirement- Check whether the request method/service name is as per the requirement");

	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Rest Service call for updateRestaurantRating is not as per the requirement- Check whether the request method/service name is as per the requirement");
	    }
	}

	    @Test
	    @Order(5)
	    public void test5RestApiCallForGetOrderCountRestaurantWise() {
	        try {
	        	if(!msgCons.equals("")) {
					fail();
				}
	            Restaurant restaurant1 = getRestaurant(2001, "Restaurant1", "Cuisine1", 4.5, "Location1", null);
	            Restaurant restaurant2 = getRestaurant(2002, "Restaurant2", "Cuisine2", 4.0, "Location2", null);
	            // Create a sample map of restaurant ID to order count
	            Map<Integer, Integer> orderCountMap = new HashMap<>();
	            orderCountMap.put(2001, 15);
	            orderCountMap.put(2002, 20);

	            // Mock the service method call
	            when(service.getOrderCountRestaurantWise()).thenReturn(orderCountMap);

	            // Build the request
	            RequestBuilder requestBuilder = get("/getOrderCountRestaurantWise");

	            // Perform the request and get the result
	            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	            int status = result.getResponse().getStatus();
	            String content = result.getResponse().getContentAsString();

	            // Assertions
	            assertEquals(200, status);
	            assertNotNull(content);
	        } catch (AssertionError e) {
	            e.printStackTrace();
	            if(!msgCons.equals("")) {
	            	fail(msgCons);
	            }
	            else
	            fail("Rest Service call for getOrderCountRestaurantWise is not as per the requirement - Check whether the request method/service name is as per the requirement");
	        } catch (Exception e) {
	            e.printStackTrace();
	            fail("Rest Service call for getOrderCountRestaurantWise is not as per the requirement - Check whether the request method/service name is as per the requirement");
	        }
	    }


	    static String testResult = "";
	    
	    @Test
	    @Order(6)
	    public void test6PredefinedValidationInRestAPIAddRestaurant() {
	        String msg = "Predefined Validation for /addRestaurant rest api call ";
	        try {
	            final String restaurantJson = "{\"restaurantId\": \"-1\"}";

	            MvcResult result = mockMvc
	                    .perform(post("/addRestaurant").content(restaurantJson).contentType(APPLICATION_JSON_UTF8))
	                    .andExpect(status().isBadRequest())
	                    .andReturn();

	            testResult = result.getResponse().getContentAsString();

	            if (testResult == null) {
	                msg = "Predefined Validation for /addRestaurant rest api call is not correct";
	            } else {
	                
	                if (!testResult.toLowerCase().replace(" ", "").contains("providevalueforrestaurantname")) {
	                    msg = msg + "for restaurantName is not correct, ";
	                }
	                if (!testResult.toLowerCase().replace(" ", "").contains("providevalueforcuisine")) {
	                    msg = msg + "for cuisine is not correct, ";
	                }
	                if (!testResult.toLowerCase().replace(" ", "").contains("providevalueforlocation")) {
	                    msg = msg + "for location is not correct";
	                }
	            }

	            if (!msg.equals("Predefined Validation for /addRestaurant rest api call ")) {
	                fail(msg);
	            }
	        } catch (AssertionError e) {
	            e.printStackTrace();
	            fail(msg);
	        } catch (Exception e) {
	            e.printStackTrace();
	            fail("Predefined Validation for /addRestaurant rest api call is not correct");
	        }
	    }


	    @Test
	    @Order(7)
	    public void test7PredefinedValidRestaurantIdInRestAPIAddRestaurant() {
	        String msg = "Predefined Validation for /addRestaurant rest api call ";
	        try {

	            if (testResult == null) {
	                msg = "Predefined Validation for /addRestaurant rest api call is not correct";
	            } else if (!testResult.toLowerCase().replace(" ", "").contains("restaurantidshouldbegreaterthan0")) {
	                msg = msg + "for restaurantId is not correct";
	            }

	            if (!msg.equals("Predefined Validation for /addRestaurant rest api call ")) {
	                fail(msg);
	            }
	        } catch (AssertionError e) {
	            e.printStackTrace();
	            fail(msg);
	        } catch (Exception e) {
	            e.printStackTrace();
	            fail("Predefined Validation for /addRestaurant rest api call is not correct");
	        }
	    }

	    @Test
	    @Order(8)
	    public void test8PredefinedValidRatingInRestAPIAddRestaurant() {
	        String msg = "Predefined Validation for /addRestaurant rest api call ";
	        try {

	            if (testResult == null) {
	                msg = "Predefined Validation for /addRestaurant rest api call is not correct";
	            } else if (!testResult.toLowerCase().replace(" ", "").contains("ratingshouldbebetween1and10")) {
	                msg = msg + "for rating is not correct";
	            }

	            if (!msg.equals("Predefined Validation for /addRestaurant rest api call ")) {
	                fail(msg);
	            }
	        } catch (AssertionError e) {
	            e.printStackTrace();
	            fail(msg);
	        } catch (Exception e) {
	            e.printStackTrace();
	            fail("Predefined Validation for /addRestaurant rest api call is not correct");
	        }
	    }

}