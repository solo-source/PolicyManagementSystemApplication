package com.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Provide necessary Annotation
@Entity
@Getter
@Setter
@AllArgsConstructor
public class PropertyInfo {
	
	// Provide necessary Annotation
	@Id
	@NotEmpty(message = "Provide value for property Id")	
	private String propertyId; 
	@NotEmpty(message = "Provide value for property type")	
	private String propertyType; 
	@NotEmpty(message = "Provide value for location")	
	private String location; 
	
	@Positive(message = "area should be positive and nonzero")
	private float area; 
	@Positive(message = "price should be positive and nonzero")
	private double price;  
	@NotEmpty(message = "Provide value for status")	
	private String status; 
	@NotEmpty(message = "Provide value for owner name")	
	private String ownerName; 
	@NotEmpty(message = "Provide value for amenities")	
	private String amenities; 
	
	@ManyToOne
    @JoinColumn(name = "agency_id")
	@JsonIgnoreProperties("propertyList")	
	private Agency agencyObj; 

	public PropertyInfo() {
		
	}

//	public PropertyInfo(String propertyId, String propertyType, String location, float area, double price,
//			String status, String ownerName, String amenities, Agency agencyObj) {
//		super();
//		this.propertyId = propertyId;
//		this.propertyType = propertyType;
//		this.location = location;
//		this.area = area;
//		this.price = price;
//		this.status = status;
//		this.ownerName = ownerName;
//		this.amenities = amenities;
//		this.agencyObj = agencyObj;
//	}
	

}

