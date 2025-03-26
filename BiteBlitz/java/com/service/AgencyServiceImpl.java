package com.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.IAgencyDAO;
import com.entities.Agency;
import com.exception.InvalidAgencyException;

//Provide necessary annotation
@Service
public class AgencyServiceImpl implements IAgencyService {

	// Provide necessary annotation
	@Autowired
	private IAgencyDAO agencyDAO;

	public Agency addAgency(Agency agencyObj) {
		//fill code
		return agencyDAO.addAgency(agencyObj);
	}
	
	public Agency updateCommissionRate(String agencyId, float commissionRate) throws InvalidAgencyException {
		//fill code
		return agencyDAO.updateCommissionRate(agencyId, commissionRate);
	}
	
	public List<Agency> viewAgenciesByStarRating(float starRating) {
		//fill code
		return agencyDAO.viewAgenciesByStarRating(starRating);		
	}
	
	public List<Agency> viewAgenciesByPropertyLocation(String location) {
		//fill code
		return agencyDAO.viewAgenciesByPropertyLocation(location);		
	}
	
	public Map<String,Double> getAgencyWiseTotalPropertyPrice() {
		//fill code
		return agencyDAO.getAgencyWiseTotalPropertyPrice();		
	}
}
