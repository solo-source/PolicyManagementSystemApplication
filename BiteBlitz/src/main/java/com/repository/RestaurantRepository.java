package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.Restaurant;

//Provide necessary annotation

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

//	List<Restaurant> findByRatingAndLocation(double rating, String location);

	List<Restaurant> findByRatingGreaterThanEqualAndLocation(double rating, String location);

	@Query("SELECT r.restaurantId, COUNT(o.orderId) FROM Restaurant r LEFT JOIN r.ordersList o GROUP BY r.restaurantId")
	List<Object[]> getOrderCountRestaurantWise();

	// Provide necessary methods to view restaurants by rating and location, and to
	// get order count restaurant wise

}
