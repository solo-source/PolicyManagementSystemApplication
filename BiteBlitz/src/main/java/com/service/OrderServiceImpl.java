//package com.dao;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.entities.Restaurant;
//import com.entities.Orders;
//import com.exception.InvalidOrderException;
//import com.exception.InvalidRestaurantException;
//import com.repository.RestaurantRepository;
//
//import com.repository.OrderRepository;
//
//@Component
//public class OrderDAOImpl implements IOrderDAO {
//	
//	// Provide necessary Annotation
//	@Autowired
//	private OrderRepository orderRepository;
//	
//	//Provide necessary Annotation
//	@Autowired
//	private RestaurantRepository restaurantRepository;
//
//	public Orders addOrder(Orders order, int restaurantId) throws InvalidRestaurantException {
//	// fill code
//
//		return null;
//	}
//	
//	public Orders updatePaymentMethod(int orderId, String paymentMethod) throws InvalidOrderException {
//	    // fill code
//
//		return null;
//	}	 	  	  	 		     	   	      	 	
//
//
//	public List<Orders> viewOrdersByStatus(String status) {
//		// fill code
//
//		return null;
//	}
//
//
//	public List<Orders> viewOrdersByRestaurantName(String restaurantName) {
//		// fill code
//
//		return null;
//	}
//
//    public Orders cancelOrder(int orderId) throws InvalidOrderException {
//       	// fill code
//
//		return null;
//    }
//
//}

package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entities.Restaurant;
import com.entities.Orders;
import com.exception.InvalidOrderException;
import com.exception.InvalidRestaurantException;
import com.repository.RestaurantRepository;
import com.repository.OrderRepository;

@Component
public class OrderServiceImpl implements IOrderService {	 	  	  	 		     	   	      	 	

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public Orders addOrder(Orders order, int restaurantId) throws InvalidRestaurantException {
		Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
		if (!restaurantOpt.isPresent()) {
			throw new InvalidRestaurantException();
		}
		Restaurant restaurant = restaurantOpt.get();
		order.setRestaurantObj(restaurant);
		return orderRepository.save(order);
	}

	@Override
	public Orders updatePaymentMethod(int orderId, String paymentMethod) throws InvalidOrderException {
		Optional<Orders> orderOpt = orderRepository.findById(orderId);
		if (!orderOpt.isPresent()) {
			throw new InvalidOrderException();
		}
		Orders order = orderOpt.get();
		order.setPaymentMethod(paymentMethod);
		return orderRepository.save(order);
	}

	@Override
	public List<Orders> viewOrdersByStatus(String status) {
		return orderRepository.findByStatus(status);
	}

	@Override
	public List<Orders> viewOrdersByRestaurantName(String restaurantName) {	 	  	  	 		     	   	      	 	
		return orderRepository.findByRestaurantObjRestaurantName(restaurantName);
	}

	@Override
	public Orders cancelOrder(int orderId) throws InvalidOrderException {
//        Optional<Orders> orderOpt = orderRepository.findById(orderId);
//        if (!orderOpt.isPresent()) {
//            throw new InvalidOrderException();
//        }
//        Orders order = orderOpt.get();
//        order.setStatus("Cancelled");
//        return orderRepository.save(order);
		Optional<Orders> orderOpt = orderRepository.findById(orderId);
		if (!orderOpt.isPresent()) {
			throw new InvalidOrderException();
		}
		Orders order = orderOpt.get();
		orderRepository.delete(order);
		return order;
	}
}
