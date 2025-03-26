package com.service;

import java.util.List;
import java.util.Map;
import com.entities.Restaurant;
import com.exception.InvalidRestaurantException;

public interface IRestaurantService {
	
	public Restaurant addRestaurant(Restaurant restaurant);
	public Restaurant viewRestaurantById(int restaurantId) throws InvalidRestaurantException;
	public Restaurant updateRestaurantRating(int restaurantId, double rating) throws InvalidRestaurantException;
	public List<Restaurant> viewRestaurantsByRatingAndLocation(double rating, String location);
	public Map<Integer, Integer> getOrderCountRestaurantWise();
}
