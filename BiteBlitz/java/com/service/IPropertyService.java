package com.service;

import java.util.List;

import com.entities.PropertyInfo;
import com.exception.InvalidAgencyException;
import com.exception.InvalidPropertyException;

public interface IPropertyService {

	public PropertyInfo addProperty(PropertyInfo propertyObj, String agecyId) throws InvalidAgencyException;
	public PropertyInfo updatePrice(String propertyId, double price) throws InvalidPropertyException;
	public PropertyInfo viewPropertyById(String propertyId) throws InvalidPropertyException;
	public List<PropertyInfo> viewPropertiesByTypeAndLocation(String propertyType, String location);
	public List<PropertyInfo> viewPropertiesByAgencyName(String agencyName);
	public PropertyInfo deleteProperty(String propertyId) throws InvalidPropertyException;
	
}
	 	  	      	 		     	     	        	 	
