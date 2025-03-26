package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entities.Restaurant;
import com.exception.InvalidRestaurantException;
import com.repository.RestaurantRepository;

@Component
public class RestaurantServiceImpl implements IRestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant viewRestaurantById(int restaurantId) throws InvalidRestaurantException {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (!restaurantOpt.isPresent()) {
            throw new InvalidRestaurantException();
        }
        return restaurantOpt.get();
    }

    @Override
    public Restaurant updateRestaurantRating(int restaurantId, double rating) throws InvalidRestaurantException {	 	  	  	 		     	   	      	 	
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (!restaurantOpt.isPresent()) {
            throw new InvalidRestaurantException();
        }
        Restaurant restaurant = restaurantOpt.get();
        restaurant.setRating(rating);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> viewRestaurantsByRatingAndLocation(double rating, String location) {
        return restaurantRepository.findByRatingGreaterThanEqualAndLocation(rating, location);
    }

    @Override
    public Map<Integer, Integer> getOrderCountRestaurantWise() {
//        List<Restaurant> restaurants = restaurantRepository.findAll();
//        return restaurants.stream()
//                          .collect(Collectors.toMap(Restaurant::getrestaurantId, restaurant -> restaurant.getOrders().size()));
    	List<Object[]> results = restaurantRepository.getOrderCountRestaurantWise();
        Map<Integer, Integer> orderCountMap = new HashMap<>();
        for (Object[] result : results) {
            Integer restaurantId = (Integer) result[0];
            Long orderCount = (Long) result[1];
            orderCountMap.put(restaurantId, orderCount.intValue());
        }
        return orderCountMap;
    	
    	
//    	return null;
    }
}
	 	  	  	 		     	   	      	 	
