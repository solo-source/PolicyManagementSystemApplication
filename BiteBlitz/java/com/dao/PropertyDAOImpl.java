package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.entities.Agency;
import com.entities.PropertyInfo;
import com.exception.InvalidPropertyException;
import com.exception.InvalidAgencyException;
import com.repository.PropertyRepository;
import com.service.IPropertyService;
import com.repository.AgencyRepository;

@Component
public class PropertyDAOImpl implements IPropertyDAO {

	// Provide necessary Annotation
	@Autowired
	private PropertyRepository propertyRepository;

	// Provide necessary Annotation
	@Autowired
	private AgencyRepository agencyRepository;

	public PropertyInfo addProperty(PropertyInfo propertyObj, String agencyId) throws InvalidAgencyException {
		//fill code
		Agency agency = agencyRepository.findById(agencyId).orElse(null);
		if (agency == null) {
			throw new InvalidAgencyException();
		}
		propertyObj.setAgencyObj(agency);
		return propertyRepository.save(propertyObj);
	}
		
	public PropertyInfo updatePrice(String propertyId, double price) throws InvalidPropertyException {
		//fill code
		PropertyInfo obj = propertyRepository.findById(propertyId).orElse(null);
		if(obj==null)
			throw new InvalidPropertyException();
		else {
			obj.setPrice(price);
			return obj;
		}
	}
	
	public PropertyInfo viewPropertyById(String propertyId) throws InvalidPropertyException {
		//fill code
		PropertyInfo obj = propertyRepository.findById(propertyId).orElse(null);
		if(obj==null)
			throw new InvalidPropertyException();
		else 
			return obj;		
	}

	public List<PropertyInfo> viewPropertiesByTypeAndLocation(String propertyType, String location) {
		//fill code
		return propertyRepository.findByPropertyTypeAndLocation(propertyType, location);		
	}
	
	public List<PropertyInfo> viewPropertiesByAgencyName(String agencyName) {
		//fill code
		return propertyRepository.findByAgencyObjAgencyName(agencyName);		
	}

		
	public PropertyInfo deleteProperty(String propertyId) throws InvalidPropertyException {
		//fill code
		Optional<PropertyInfo> obj=propertyRepository.findById(propertyId);
		if(obj.isPresent())
		{
			propertyRepository.delete(obj.get());
			return obj.get();
		}
		else
			throw new InvalidPropertyException();		
	}
}
