package com.example.demo;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.entities.Orders;
import com.entities.Restaurant;
import com.exception.InvalidOrderException;
import com.exception.InvalidRestaurantException;
import com.service.OrderServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
public class Functional2Test {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private OrderServiceImpl serviceImpl;

	@PersistenceContext
	private EntityManager em;

	
	

	@Transactional(propagation = Propagation.REQUIRED)
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
			fail("Check the logic of addOrder method in the OrderServiceImpl class");
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
	public static int getOrderId(Orders orders) {
	    Class<?> ordersObj = null;
	    int id = -1;
	    try {
	        ordersObj = Class.forName("com.entities.Orders");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Orders as per the problem statement");
	    }
	    try {
	        Method mobj = ordersObj.getDeclaredMethod("getOrderId");
	        id = (int) mobj.invoke(orders);
	    } catch (NoSuchMethodException e) {
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for orderId attribute in Orders class");
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for the attributes in Orders class");
	    }
	    return id;
	}

	
	public static String getPaymentMethod(Orders orders) {
	    Class<?> ordersObj = null;
	    String paymentMethod = "";
	    try {
	        ordersObj = Class.forName("com.entities.Orders");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Orders as per the problem statement");
	    }
	    try {
	        Method mobj = ordersObj.getDeclaredMethod("getPaymentMethod");
	        paymentMethod = (String) mobj.invoke(orders);
	    } catch (NoSuchMethodException e) {
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for paymentMethod attribute in Orders class");
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for the attributes in Orders class");
	    }
	    return paymentMethod;
	}

	
	public static Restaurant getRestaurantObj(Orders orders) {
	    Class<?> ordersObj = null;
	    Restaurant restaurantObj = null;
	    try {
	        ordersObj = Class.forName("com.entities.Orders");
	    } catch (ClassNotFoundException e) {
	        fail("No class with the name Orders as per the problem statement");
	    }
	    try {
	        // Assuming you have a getter method for restaurantObj in Orders class
	        Method mobj = ordersObj.getDeclaredMethod("getRestaurantObj");
	        restaurantObj = (Restaurant) mobj.invoke(orders);
	    } catch (NoSuchMethodException e) {
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for restaurantObj attribute in Orders class");
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Check the availability of appropriate Lombok annotation to generate getter method for the attributes in Orders class");
	    }
	    return restaurantObj;
	}

	
	public static int getRestaurantId(Restaurant restaurant) {
	    Class<?> restaurantObj = null;
	    int id = 2;
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
	
	

	@Transactional
	@Test
	@Order(1)
	public void test11AddOrder() {
		TransactionStatus transaction = null;
		try {
			setUp();

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			Restaurant restaurant = getRestaurant(1001, "TBites", "Italian", 4.2, "Downtown",null);
			em.persist(restaurant);
			em.flush();

			Orders order = getOrders(111, "456 Oak Avenue, Townsville", "Cash", "Paid", 75.0,null);
			
			if(!msgCons.equals("")) {
				fail();
			}
			serviceImpl.addOrder(order, 1001);
			transactionManager.commit(transaction);

			Orders result = em.find(Orders.class, 111);

			assertTrue(result != null && 
					getOrderId(result)==111 && getRestaurantId(getRestaurantObj(result))==1001);

		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of addOrder method in the OrderServiceImpl class");

			}
			e.printStackTrace();

			fail("Check the logic of addOrder method in the OrderServiceImpl class");
		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of addOrder method in the OrderServiceImpl class");
		}
	}

	@Transactional
	@Test
	@Order(2)
	public void test12AddOrderForInvalidRestaurant() {
		TransactionStatus transaction = null;
		try {
		    	
			if(!msgCons.equals("")) {
				fail();
			}
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

//            Query query = em.createNativeQuery("DELETE FROM restaurant");
//            query.executeUpdate();

			Orders order = getOrders(112, "45 Oak Avenue, Townsville", "Card", "Paid", 70.0,null);

			serviceImpl.addOrder(order, 898);
//            System.out.println("&&&&&&&&&&&&");
			transactionManager.commit(transaction);
//            System.out.println("$$$$$$$$$$$$$$$$$$$$");
			assertTrue(false);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
		} catch (InvalidRestaurantException e) {
			assertTrue(true);
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of addOrder method in the OrderServiceImpl class for an invalid restaurant id ");

			}
			e.printStackTrace();

			fail("Check the logic of addOrder method in the OrderServiceImpl class for an invalid restaurant id ");
		} catch (Error e) {
			e.printStackTrace();
				if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of addOrder method in the OrderServiceImpl class for an invalid restaurant id ");
		}
	}

	@Transactional
	@Test
	@Order(3)
	public void test13UpdatePaymentMethod() {
		TransactionStatus transaction = null;
		try {
			if(!msgCons.equals("")) {
				fail();
			}
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

//			Restaurant restaurant = getRestaurant(22, "TBites", "Italian", 4.2, "Downtown",null);
//			em.persist(restaurant);

			Orders order = getOrders(119, "456 Oak Avenue, Townsville", "Cash", "Paid", 75.0,null);
			em.persist(order);
			em.flush();
			serviceImpl.updatePaymentMethod(119, "Online Payment");
			transactionManager.commit(transaction);

			Orders result = em.find(Orders.class, 119);
			assertTrue(result != null && getOrderId(result) == 119
					&& getPaymentMethod(result).equalsIgnoreCase("Online Payment"));// &&
																						// result.getStatus().equals("Card")
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of updatePaymentMethod method in the OrderServiceImpl class");

			}
			e.printStackTrace();

			fail("Check the logic of updatePaymentMethod method in the OrderServiceImpl class");
		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of updatePaymentMethod method in the OrderServiceImpl class");
		}
	}

	@Transactional
	@Test
	@Order(4)
	public void test14UpdatePaymentMethodForInvalidOrderId() {
		TransactionStatus transaction = null;
		try {
			
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			serviceImpl.updatePaymentMethod(17, "Card");
			transactionManager.commit(transaction);
			assertTrue(false);
		} catch (InvalidOrderException e) {
			assertTrue(true);
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of updatePaymentMethod in the OrderServiceImpl class for a OrderId that does not exist");

			}
			e.printStackTrace();

			fail("Check the logic of updatePaymentMethod in the OrderServiceImpl class for a OrderId that does not exist");
		} catch (Error e) {
			e.printStackTrace();
			fail("Check the logic of updatePaymentMethod in the OrderServiceImpl class for a OrderId that does not exist");
		}
	}

	@Transactional
	@Test
	@Order(5)
	public void test15ViewOrdersByStatus() {
		TransactionStatus transaction = null;
		try {
//            setUp();
			if(!msgCons.equals("")) {
				fail();
			}

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			Restaurant restaurant1 = getRestaurant(3001, "Tasty", "Italian", 4, "Downtown",null);
			Restaurant restaurant2 = getRestaurant(3002, "Bill", "Arabian", 3, "Down Hills",null);

			em.persist(restaurant1);
			em.persist(restaurant2);
			em.flush();

			Orders order1 = getOrders(113, "Oak Avenue, Townsville", "Card", "Not Paid", 80.0,restaurant2);
			

			Orders order2 = getOrders(114, "Rock Avenue, Towns", "Card", "On Process", 80.0,restaurant2);
		
			Orders order3 = getOrders(115, "Hill Avenue, Townsville", "Card", "Not Paid", 90.0,restaurant1);
		

			Orders order4 = getOrders(116, "Rock Avenue, Towns", "Card", "On Process", 100.0,restaurant1);
			
			em.persist(order1);
			em.persist(order2);
			em.persist(order3);
			em.persist(order4);

			em.flush();

			List<Orders> orderList = serviceImpl.viewOrdersByStatus("On Process");
//            System.out.println("@@@@@@@@@@@@@@@@@@"+orderList.size());
			
			transactionManager.commit(transaction);
			int f1 = 0, f2 = 0, f3 = 0, f4 = 0, f5 = 0;

			if (orderList.size() == 2) {
				for (Orders o : orderList) {
					if (getOrderId(o) == 116 && getRestaurantId(getRestaurantObj(o))==3001) {
						f1 = 1;
					} else if (getOrderId(o) == 114 && getRestaurantId(getRestaurantObj(o))==3002)
						f2 = 1;
					
//                  
				}
//                System.out.println("111111111111 " + f1 + " " + f2 + " " + f3 + " " + f4+ " " + f5);
				assertTrue(f1 == 1 && f2 == 1);
			} else
				fail("Check the logic of viewOrdersByStatus method in the OrderServiceImpl class");
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of viewOrdersByStatus method in the OrderServiceImpl class");

			}
			e.printStackTrace();

			fail("Check the logic of viewOrdersByStatus method in the OrderServiceImpl class");
		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of viewOrdersByStatus method in the OrderServiceImpl class");
		}
	}

	@Transactional
	@Test
	@Order(6)
	public void test16ViewOrdersByRestaurantName() {
		TransactionStatus transaction = null;
		try {
//            setUp();
			if(!msgCons.equals("")) {
				fail();
			}

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			Restaurant restaurant1 = getRestaurant(2003, "Alzen", "Italian", 4, "Downtown",null);
			Restaurant restaurant2 = getRestaurant(2004, "Bill", "Arabian", 3, "Down Hills",null);

			em.persist(restaurant1);
			em.persist(restaurant2);
			em.flush();

			Orders order1 = getOrders(117, "Oak Avenue, Townsville", "Card", "Not Paid", 80.0,restaurant2);
			

			Orders order2 = getOrders(118, "Rock Avenue, Towns", "Card", "Paid", 80.0,restaurant2);
			

			Orders order3 = getOrders(199, "Hill Avenue, Townsville", "Card", "Not Paid", 90.0,restaurant1);
			
			Orders order4 = getOrders(120, "Rock Avenue, Towns", "Card", "Paid", 100.0,restaurant1);
		

			em.persist(order1);
			em.persist(order2);
			em.persist(order3);
			em.persist(order4);

			em.flush();

			List<Orders> orderList = serviceImpl.viewOrdersByRestaurantName("Alzen");
			transactionManager.commit(transaction);
			int f1 = 0, f2 = 0, f4 = 0, f3 = 0;

			if (orderList.size() == 2) {
				for (Orders o : orderList) {
					if (getOrderId(o) == 120 && getRestaurantId(getRestaurantObj(o))==2003) {
						f1 = 1;
					} else if (getOrderId(o) == 199 && getRestaurantId(getRestaurantObj(o))==2003)
						f2 = 1;
					
				}
				assertTrue(f1 == 1 && f2 == 1 );
			} else
				fail("Check the logic of viewOrdersByRestaurantName method in the OrderServiceImpl class");
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of viewOrdersByRestaurantName method in the OrderServiceImpl class");

			}
			e.printStackTrace();

			fail("Check the logic of viewOrdersByRestaurantName method in the OrderServiceImpl class");
		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of viewOrdersByRestaurantName method in the OrderServiceImpl class");
		}
	}

	@Transactional
	@Order(7)
	@Test
	public void test17CancelOrder() {
		TransactionStatus transaction = null;
		try {
//            setUp();
			if(!msgCons.equals("")) {
				fail();
			}

			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transaction = transactionManager.getTransaction(transactionDefinition);

			Restaurant restaurant = getRestaurant(2009, "Bill", "Arabian", 3, "Down Hills",null);
			em.persist(restaurant);
			em.flush();

			Orders order = getOrders(121, "Rock Avenue, Towns", "Card", "Not Paid", 100.0,restaurant);
		
			em.persist(order);
			em.flush();

			serviceImpl.cancelOrder(121);
			transactionManager.commit(transaction);

			Orders result = em.find(Orders.class, 121);
			assertTrue(result == null);
		} catch (Exception e) {
			try {
				if (transactionManager != null && transaction != null)
					transactionManager.commit(transaction);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Check the logic of cancelOrder method in the OrderServiceImpl class");

			}
			e.printStackTrace();

			fail("Check the logic of cancelOrder method in the OrderServiceImpl class");
		} catch (Error e) {
			e.printStackTrace();
			if(!msgCons.equals("")) {
				   fail(msgCons);	
				}
				else
			fail("Check the logic of cancelOrder method in the OrderServicehImpl class");
		}
	}

	@Transactional
	@Test
	@Order(8)
	public void test18CancelOrderForInvalidOrderId() {
	    	    		TransactionStatus transaction = null;

		try {
			DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
			transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			 transaction = transactionManager.getTransaction(transactionDefinition);

			
			
			
			
			
			 serviceImpl.cancelOrder(2000);
			transactionManager.commit(transaction);
	
		
			assertTrue(false);

		}
		catch(InvalidOrderException e) {
			assertTrue(true);
		}
			catch(Exception e) {
		    try{
		    	if(transactionManager!=null && transaction!=null)
			   transactionManager.commit(transaction);
			  } catch(Exception ex){
			       ex.printStackTrace();
			       fail("Check the logic of cancelOrder method for an invalid orderId in the OrderServiceImpl class");

			   }
			e.printStackTrace();
			fail("Check the logic of cancelOrder method for an invalid orderId in the OrderServiceImpl class");	

		}
		catch(Error e) {
			e.printStackTrace();
			fail("Check the logic of cancelOrder method for an invalid orderId in the OrderServiceImpl class");	

		}


	}
	
}