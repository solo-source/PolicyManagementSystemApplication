package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Orders;
import com.entities.Restaurant;
import com.exception.InvalidRestaurantException;
import com.service.IRestaurantService;

import jakarta.validation.Valid;

//Provide necessary Annotation
@RestController
public class RestaurantController {

//Provide necessary Annotation
	@Autowired
	private IRestaurantService restaurantService;
	
	// Provide necessary Annotation for the below methods and fill the code
	@PostMapping("/addRestaurant")
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody @Valid Restaurant restaurant) {
		return new ResponseEntity<>(restaurantService.addRestaurant(restaurant),HttpStatus.OK);

	}

	@GetMapping("/viewRestaurantById/{restaurantId}")
	public ResponseEntity<Restaurant> viewRestaurantById(@PathVariable int restaurantId) throws InvalidRestaurantException {
		return new ResponseEntity<>(restaurantService.viewRestaurantById(restaurantId),HttpStatus.OK);

	}
	
	@PutMapping("/updateRestaurantRating/{restaurantId}/{rating}")
    public ResponseEntity<Restaurant> updateRestaurantRating(@PathVariable int restaurantId, @PathVariable double rating) throws InvalidRestaurantException  {
		return new ResponseEntity<>(restaurantService.updateRestaurantRating(restaurantId, rating),HttpStatus.OK);

	}
	
    @GetMapping("/viewRestaurantsByRatingAndLocation/{rating}/{location}")
    public ResponseEntity<List<Restaurant>> viewRestaurantsByRatingAndLocation(@PathVariable double rating,@PathVariable String location) {	 	  	      	 	    	      	    	      	 	
		return new ResponseEntity<>(restaurantService.viewRestaurantsByRatingAndLocation(rating, location),HttpStatus.OK);

	}
    
    @GetMapping("/getOrderCountRestaurantWise")
    public ResponseEntity<Map<Integer,Integer>> getOrderCountRestaurantWise() {
		return new ResponseEntity<>(restaurantService.getOrderCountRestaurantWise(),HttpStatus.OK);

	}

}
