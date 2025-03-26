package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.PropertyInfo;
import com.exception.InvalidPropertyException;

//Provide necessary annotation
@Repository
public interface PropertyRepository extends JpaRepository<PropertyInfo, String> {

	// Provide necessary method to view properties by property type and location
	public List<PropertyInfo> findByPropertyTypeAndLocation(String propertyType, String location);
	
	public List<PropertyInfo> findByAgencyObjAgencyName(String agencyName);
	
}