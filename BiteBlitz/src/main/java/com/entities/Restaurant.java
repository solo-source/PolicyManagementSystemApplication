package com.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class Restaurant {
	
    //Provide necessary Annotation 
    @Id
    @Positive(message = "Restaurant id should be greater than 0")
	private int restaurantId;
    @NotEmpty(message = "Provide value for restaurant name")
    @Column(length=25)

	private String restaurantName;
    @NotEmpty(message = "Provide value for cuisine")
    @Column(length=25)

	private String cuisine;
    @Min(value = 1, message = "Rating should be between 1 and 10")
	@Max(value = 10, message = "Rating should be between 1 and 10")
	private double rating;
	@NotEmpty(message = "Provide value for location")
    @Column(length=25)

	private String location;
	
    //Provide necessary Annotation 
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurantObj")
	@JsonIgnoreProperties
	private List<Orders> ordersList;
	
//	public Restaurant() {	 	  	  	 		     	   	      	 	
//		super();
//	}


}
