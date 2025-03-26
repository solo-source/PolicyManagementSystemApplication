package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.PropertyInfo;
import com.exception.InvalidAgencyException;
import com.exception.InvalidPropertyException;
import com.service.IPropertyService;

//Provide necessary Annotation
@RestController
public class PropertyController {

	// Provide necessary Annotation
	@Autowired
	private IPropertyService propertyService;

	// Provide necessary Annotation for the below methods and fill the code
	@PostMapping("/addProperty/{agecyId}")
	public PropertyInfo addProperty(@RequestBody @Validated PropertyInfo propertyObj, @PathVariable String agecyId) throws InvalidAgencyException {
		//fill code
		return propertyService.addProperty(propertyObj, agecyId);
	}
	
	@PutMapping("/updatePrice/{propertyId}/{price}")
	public PropertyInfo updatePrice(@PathVariable String propertyId, @PathVariable double price) throws InvalidPropertyException {
		//fill code
		return propertyService.updatePrice(propertyId, price);
	}
	
	@GetMapping("/viewPropertyById/{propertyId}")
	public PropertyInfo viewPropertyById(@PathVariable String propertyId) throws InvalidPropertyException {
		//fill code
		return propertyService.viewPropertyById(propertyId);		
	}
	
	@GetMapping("/viewPropertiesByAgencyName/{agencyName}")
	public List<PropertyInfo> viewPropertiesByAgencyName(@PathVariable String agencyName) {
		//fill code
		return propertyService.viewPropertiesByAgencyName(agencyName);		
	}
	
	@GetMapping("/viewPropertiesByTypeAndLocation/{propertyType}/{location}")
	public List<PropertyInfo> viewPropertiesByTypeAndLocation(@PathVariable String propertyType, @PathVariable String location) {
		//fill code
		return propertyService.viewPropertiesByTypeAndLocation(propertyType, location);		
	}
	
	@DeleteMapping("/deleteProperty/{propertyId}")
	public PropertyInfo deleteProperty(@PathVariable String propertyId) throws InvalidPropertyException {
		//fill code
		return propertyService.deleteProperty(propertyId);		
	}

}
