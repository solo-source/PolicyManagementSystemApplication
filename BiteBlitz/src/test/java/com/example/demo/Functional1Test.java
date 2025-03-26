package com.example.demo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.service.RestaurantServiceImpl;
import com.entities.Orders;

import com.entities.Restaurant;

import com.exception.InvalidRestaurantException;
import com.service.RestaurantServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Functional1Test {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private RestaurantServiceImpl serviceImpl;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void setUp() {

		try {
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

			Query query1 = em.createNativeQuery("DELETE FROM orders");
			query1.executeUpdate();

			Query query = em.createNativeQuery("DELETE FROM restaurant");
			query.executeUpdate();

			transactionManager.commit(transaction);

		} catch (Exception | Error e) {
			e.printStackTrace();
			fail("Check the logic of addRestaurant method in the RestaurantServiceImpl class");
		}

	}


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
	public static int getRestaurantId(Restaurant restaurant) {
	    Class<?> restaurantObj = null;
	    int id = -1;
	    try {
	        restaurantObj = Class.forName("com.entities.Restaurant");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Restaurant as per the problem statement");
	    }
	    try {
	        Method mobj = restaurantObj.getDeclaredMethod("getRestaurantId");
	        id = (int) mobj.invoke(restaurant);
	    } catch (NoSuchMethodException e) {
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for restaurantId attribute in Restaurant class");
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for the attributes in Restaurant class");
	    }
	    return id;
	}

	
	public static double getRating(Restaurant restaurant) {
	    Class<?> restaurantObj = null;
	    double rating = -1;
	    try {
	        restaurantObj = Class.forName("com.entities.Restaurant");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Restaurant as per the problem statement");
	    }
	    try {
	        Method mobj = restaurantObj.getDeclaredMethod("getRating");
	        rating = (double) mobj.invoke(restaurant);
	    } catch (NoSuchMethodException e) {
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for rating attribute in Restaurant class");
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for the attributes in Restaurant class");
	    }
	    return rating;
	}

	@Transactional
	@Test
	@Order(1)
	public void test11AddRestaurant() {
		TransactionStatus transaction = null;
		try {
//			setUp();
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			Query query1 = em.createNativeQuery("DELETE FROM orders");
			query1.executeUpdate();

			Query query = em.createNativeQuery("DELETE FROM restaurant");
			query.executeUpdate();
			
			


			Restaurant Album = getRestaurant(1001, "Tasty Bites", "Italian", 2.2, "Downtown",null);
			

			if(!msgCons.equals("")) {
				fail();
			}

			serviceImpl.addRestaurant(Album);
			transactionManager.commit(transaction);

			Restaurant result = em.find(Restaurant.class, 1001);

			System.out.println(result);
			assertTrue(result != null && getRestaurantId(result) == 1001
					&& getRating(result)==2.2);

		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of addRestaurant method in the RestaurantServiceImpl class");

			}
			e.printStackTrace();
			fail("Check the logic of addRestaurant method in the RestaurantServiceImpl class");

		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of addRestaurant method in the RestaurantServiceImpl class");

		}
	}

	@Transactional
	@Test
	@Order(2)
	public void test12ViewRestaurantById() {
		TransactionStatus transaction = null;
		try {
			
			
			if(!msgCons.equals("")) {
				fail();
			}

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);
			Restaurant Album = getRestaurant(1002, "Bites", "Chinese", 2.5, "Up Hills",null);

			em.persist(Album);
			em.flush();
			// em.clear();
			// Album AlbumObj= em.find(Album.class,AlbumId);
			serviceImpl.viewRestaurantById(1002);
			transactionManager.commit(transaction);
			Restaurant result = em.find(Restaurant.class, 1002);
			assertTrue(
					result != null &&getRestaurantId(result) == 1002 && getRating(result)==2.5);

		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of viewRestaurantById method in the RestaurantServiceImpl class");

			}
			e.printStackTrace();
			fail("Check the logic of viewRestaurantById method in the RestaurantServiceImpl class");

		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of viewRestaurantById method in the RestaurantServiceImpl class");

		}
	}

	@Transactional
	@Test
	@Order(3)
	public void test13ViewRestaurantsByRatingAndLocation() {
		TransactionStatus transaction = null;
		try {
			
			
			if(!msgCons.equals("")) {
				fail();
			}

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			Restaurant Album1 = getRestaurant(1003, "Tasty", "Italian", 4, "Downtown",null);
			Restaurant Album2 = getRestaurant(1004, "Bill", "Arabian", 3, "Downtown",null);

			em.persist(Album1);
			em.persist(Album2);
			// em.flush();

			List<Restaurant> result = serviceImpl.viewRestaurantsByRatingAndLocation(3,"Downtown");
			transactionManager.commit(transaction);
//			System.out.println("SIZE " + result.size());
			assertTrue(result != null && result.size() == 2);
			int flag3 = 0, flag4 = 0;

			for (Restaurant m : result) {

				if (getRestaurantId(m) == 1004)
					flag3 = 1;
				
				else if(getRestaurantId(m)==1003)
					flag4=1;
				
			}
//			System.out.println("Flag " + flag3 + " " + flag4);
			assertTrue(flag3 == 1 && flag4 ==1);
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of viewRestaurantsByRatingAndLocation method in the RestaurantServiceImpl class");

			}
			e.printStackTrace();
			fail("Check the logic of viewRestaurantsByRatingAndLocation method in the RestaurantServiceImpl class");

		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of viewRestaurantsByRatingAndLocation in the RestaurantServiceImpl class");
		}
	}

	
	
	@Transactional
	@Test
		@Order(4)

	public void test14UpdateRestaurantRating() {
		TransactionStatus transaction = null;

		try {
			
			if(!msgCons.equals("")) {
				fail();
			}

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			 transaction = transactionManager.getTransaction(transactionDefinition);

			 Restaurant t = getRestaurant(13, "Tasty", "Italian", 2, "Downtown",null);
			//=getTeam(102,"TN1234","AC","Chennai","Bangalore",list);
			
			
//			m1.setTeamObj(b);
//			m3.setTeamObj(b);
			
			em.persist(t);
		//	em.flush();
			Restaurant result=null;

			serviceImpl.updateRestaurantRating(13, 2.5);
			transactionManager.commit(transaction);
			result=em.find(Restaurant.class, 13);

			

			assertTrue(result!=null && getRestaurantId(result)==13 && getRating(result)==2.5);

		}
			catch(Exception e) {
		    try{
		    	if(transactionManager!=null && transaction!=null)
			   transactionManager.commit(transaction);
			  } catch(Exception ex){
			       ex.printStackTrace();
			       fail("Check the logic of updateRestaurantRating method in the RestaurantserviceImpl class");

			   }
			e.printStackTrace();
			fail("Check the logic of updateRestaurantRating method in the RestaurantserviceImpl class");	

		}
		catch(Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of updateRestaurantRating method in the RestaurantserviceImpl class");	

		}


	}
	
	@Transactional
	@Test
		@Order(5)

	public void test15UpdateRestaurantRatingInvalidRestaurantId() {
		TransactionStatus transaction = null;

		try {
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			 transaction = transactionManager.getTransaction(transactionDefinition);


			Restaurant result=null;
			serviceImpl.updateRestaurantRating(22, 1.7);
			transactionManager.commit(transaction);
			//result=em.find(Mobile.class, "IMEI104");

			assertTrue(false);

		}
		catch(InvalidRestaurantException e) {
			assertTrue(true);
		}
		catch(Exception e) {
		    try{
		    	if(transactionManager!=null && transaction!=null)
			   transactionManager.commit(transaction);
			  } catch(Exception ex){
			       ex.printStackTrace();
			       fail("Check the logic of updateRestaurantRating method when the restaurantId is invalid in the RestaurantserviceImpl class");

			   }
			e.printStackTrace();
			fail("Check the logic of updateRestaurantRating method when the restaurantId is invalid in the RestaurantserviceImpl class");	

		}
		catch(Error e) {
			e.printStackTrace();
			fail("Check the logic of updateRestaurantRating method when the restaurantId is invalid in the RestaurantserviceImpl class");	

		}

		


	}

	
	
	@Transactional
	@Test
	@Order(6)
	public void test16GetOrderCountRestaurantWise() {
	    TransactionStatus transaction = null;

	    try {
	    	
	    	
			if(!msgCons.equals("")) {
				fail();
			}

	        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	        transaction = transactionManager.getTransaction(transactionDefinition);

	        Query query = em.createNativeQuery("DELETE FROM orders");
	        query.executeUpdate();
	        
	        Query query1 = em.createNativeQuery("DELETE FROM restaurant");
	        query1.executeUpdate();

	        Restaurant restaurant1 = getRestaurant(1, "Restaurant One", "Italian", 8.5, "Location A", null);
	        Restaurant restaurant2 = getRestaurant(2, "Restaurant Two", "Chinese", 7.5, "Location B", null);
	        Restaurant restaurant3 = getRestaurant(3, "Restaurant Three", "Mexican", 9.0, "Location C", null);

	        Orders order1 = getOrders(101, "123 Main St", "Credit Card", "Delivered", 30.0, restaurant1);
	        Orders order2 = getOrders(102, "456 Elm St", "Cash", "Processing", 20.0, restaurant2);
	        Orders order3 = getOrders(103, "789 Oak St", "Credit Card", "Delivered", 25.0, restaurant1);
	        Orders order4 = getOrders(104, "101 Pine St", "Credit Card", "Cancelled", 15.0, restaurant3);

	        em.persist(restaurant1);
	        em.persist(restaurant2);
	        em.persist(restaurant3);
	        em.persist(order1);
	        em.persist(order2);
	        em.persist(order3);
	        em.persist(order4);

	        System.out.println("Saved entries");

	        Map<Integer, Integer> result = serviceImpl.getOrderCountRestaurantWise();
	        transactionManager.commit(transaction);

	        // Verify the result
	        assertTrue(result != null && result.size() == 3);

	        // Initialize flags for each restaurant
	        int flag1 = 0, flag2 = 0, flag3 = 0;

	        // Check order counts for each restaurant
	        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
	            Integer restaurantId = entry.getKey();
	            Integer orderCount = entry.getValue();
	            if (restaurantId == 1 && orderCount == 2) {
	                flag1 = 1;
	            } else if (restaurantId == 2 && orderCount == 1) {
	                flag2 = 1;
	            } else if (restaurantId == 3 && orderCount == 1) {
	                flag3 = 1;
	            }
	        }

	        assertTrue(flag1 == 1 && flag2 == 1 && flag3 == 1);

	    } catch (Exception e) {
	        try {
	            if (transactionManager != null && transaction != null)
	                transactionManager.commit(transaction);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            fail("Check the logic of getOrderCountRestaurantWise method in the RestaurantserviceImpl class");
	        }
	        e.printStackTrace();
	        fail("Check the logic of getOrderCountRestaurantWise method in the RestaurantserviceImpl class");
	    } catch (Error e) {
	        e.printStackTrace();
	    	if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
	        fail("Check the logic of getOrderCountRestaurantWise method in the RestaurantserviceImpl class");
	    }
	}

	

	@Transactional
	@Test
		@Order(7)

	public void test17ViewRestaurantByIdForInvalidRestaurant() {
				TransactionStatus transaction = null;

		try {
		//	setUp();
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			 transaction = transactionManager.getTransaction(transactionDefinition);

			Restaurant result = serviceImpl.viewRestaurantById(99);
			transactionManager.commit(transaction);
			
			assertTrue(false);

		}
		catch(InvalidRestaurantException e) {
			assertTrue(true);
		}
		catch(Exception e) {
		    try{
		    	if(transactionManager!=null && transaction!=null)
			   transactionManager.commit(transaction);
			  } catch(Exception ex){
			       ex.printStackTrace();
			       fail("Check the logic of viewRestaurantById method in the RestaurantserviceImpl class when the restaurantId is invalid");

			   }
			e.printStackTrace();
			fail("Check the logic of viewRestaurantById method in the RestaurantserviceImpl class when the restaurantId is invalid");	

		}
		catch(Error e) {
			e.printStackTrace();
			fail("Check the logic of viewRestaurantById method in the RestaurantserviceImpl class when the restaurantId is invalid");	

		}
	}
	
	
}
