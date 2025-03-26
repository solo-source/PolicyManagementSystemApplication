package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Orders;
import com.exception.InvalidOrderException;
import com.exception.InvalidRestaurantException;
import com.service.IOrderService;

//Provide necessary Annotation
@RestController
public class OrderController {
	
	//Provide necessary Annotation
	@Autowired
	private IOrderService orderService;
	
	// Provide necessary Annotation for the below methods and fill the code
	
	@PostMapping("/addOrder/{restaurantId}")
	public ResponseEntity<Orders>  addOrder(@RequestBody @Validated Orders order, @PathVariable int restaurantId) throws InvalidRestaurantException {
		return new ResponseEntity<>(orderService.addOrder(order, restaurantId),HttpStatus.OK);
	}

	@PutMapping("/updatePaymentMethod/{orderId}/{paymentMethod}")
	public ResponseEntity<Orders> updatePaymentMethod(@PathVariable int orderId,@PathVariable String paymentMethod) throws InvalidOrderException {
		return new ResponseEntity<>(orderService.updatePaymentMethod(orderId, paymentMethod),HttpStatus.OK);
	}	 	  	  	 		     	   	      	 	
	@GetMapping("/viewOrdersByStatus/{status}")
	public ResponseEntity<List<Orders>> viewOrdersByStatus(@PathVariable String status) {
		return new ResponseEntity<>(orderService.viewOrdersByStatus(status),HttpStatus.OK);
	}
	@GetMapping("/viewOrdersByRestaurantName/{restaurantName}")
	public ResponseEntity<List<Orders>> viewOrdersByRestaurantName(@PathVariable String restaurantName) {
		return new ResponseEntity<>(orderService.viewOrdersByRestaurantName(restaurantName),HttpStatus.OK);
	}

	@DeleteMapping("/cancelOrder/{orderId}")
	public ResponseEntity<Orders> cancelOrder(@PathVariable int orderId) throws InvalidOrderException {
		return new ResponseEntity<>(orderService.cancelOrder(orderId),HttpStatus.OK);
	}

}
