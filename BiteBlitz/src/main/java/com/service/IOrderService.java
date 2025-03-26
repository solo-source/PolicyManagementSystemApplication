package com.service;

import java.util.List;
import com.entities.Orders;
import com.exception.InvalidOrderException;
import com.exception.InvalidRestaurantException;

public interface IOrderService {
	
	public Orders addOrder(Orders order, int restaurantId) throws InvalidRestaurantException;
	public Orders updatePaymentMethod(int orderId, String paymentMethod) throws InvalidOrderException;
	public List<Orders> viewOrdersByStatus(String status);
	public List<Orders> viewOrdersByRestaurantName(String restaurantName);
	public Orders cancelOrder(int orderId) throws InvalidOrderException;
}