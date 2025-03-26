package com.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class Agency {
	
	// Provide necessary Annotation
	@Id
	@NotEmpty(message = "Provide value for agency Id")	
	private String agencyId;
	@NotEmpty(message = "Provide value for agency name")	
	private String agencyName;
	@NotEmpty(message = "Provide value for contact number")
	private String contactNumber;
	@Min(value = 0, message = "experience should be zero or greater")
	private int experience;
	
	@Min(value = 0, message = "star rating should be between zero and five")
	@Max(value = 5, message = "star rating should be between zero and five")
	private float starRating;
	@Positive(message = "commission rate should be positive and nonzero")
	private float commissionRate;
	
	@OneToMany(mappedBy = "agencyObj", cascade =CascadeType.ALL)
	@JsonIgnoreProperties("agencyObj")	
	private List<PropertyInfo> propertyList;
	
	public Agency() {
		
	}
}
