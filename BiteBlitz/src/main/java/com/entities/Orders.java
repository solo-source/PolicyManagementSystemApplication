package com.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Provide necessary Annotation wherever necessary
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
	
    //Provide necessary Annotation 
    @Id	
    @Positive(message = "Order id should be greater than 0")
	private int orderId;
    @NotEmpty(message = "Provide value for delivery address")
    @Column(length=50)
	private String deliveryAddress;
    @Column(length=25)

    @NotEmpty(message = "Provide value for payment method")
	private String paymentMethod;
    @NotEmpty(message = "Provide value for status")
    @Column(length=25)

	private String status;
	@Positive(message = "Total amount should be greater than 0")
	private double totalAmount;
	
    //Provide necessary Annotation 
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	@JsonIgnoreProperties
	private Restaurant restaurantObj;
	
//	public Orders() {	 	  	  	 		     	   	      	 	
//		super();
//	}
	
}
