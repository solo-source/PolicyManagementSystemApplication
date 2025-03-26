package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Orders;

//Provide necessary annotation

public interface OrderRepository extends JpaRepository<Orders, Integer>  {

	public List<Orders> findByStatus(String status);
	public List<Orders> findByRestaurantObjRestaurantName(String restaurantName);
    
    // Provider necessary methods to view orders by status and view orders by the
	// given restaurant name



}
