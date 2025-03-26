package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.IPropertyDAO;
import com.entities.PropertyInfo;
import com.exception.InvalidAgencyException;
import com.exception.InvalidPropertyException;

//Provide necessary annotation
@Service
public class PropertyServiceImpl implements IPropertyService {

	// Provide necessary annotation
	@Autowired
	IPropertyDAO propertyDAO;

	public PropertyInfo addProperty(PropertyInfo propertyObj, String agecyId) throws InvalidAgencyException {
		//fill code
		return propertyDAO.addProperty(propertyObj, agecyId);
	}
		
	public PropertyInfo updatePrice(String propertyId, double price) throws InvalidPropertyException {
		//fill code
		return propertyDAO.updatePrice(propertyId, price);
	}
	
	public PropertyInfo viewPropertyById(String propertyId) throws InvalidPropertyException {
		//fill code
		return propertyDAO.viewPropertyById(propertyId);		
	}

	public List<PropertyInfo> viewPropertiesByTypeAndLocation(String propertyType, String location) {
		//fill code
		return propertyDAO.viewPropertiesByTypeAndLocation(propertyType, location);		
	}
	
	public List<PropertyInfo> viewPropertiesByAgencyName(String agencyName) {
		//fill code
		return propertyDAO.viewPropertiesByAgencyName(agencyName);		
	}
		
	public PropertyInfo deleteProperty(String propertyId) throws InvalidPropertyException {
		//fill code
		return propertyDAO.deleteProperty(propertyId);		
	}

}
