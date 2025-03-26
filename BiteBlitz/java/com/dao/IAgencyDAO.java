package com.dao;

import java.util.List;
import java.util.Map;

import com.entities.Agency;
import com.exception.InvalidAgencyException;

public interface IAgencyDAO {

	public Agency addAgency(Agency agencyObj);
	public Agency updateCommissionRate(String agencyId, float commissionRate) throws InvalidAgencyException;
	public List<Agency> viewAgenciesByStarRating(float starRating);
	public List<Agency> viewAgenciesByPropertyLocation(String location);
	public Map<String,Double> getAgencyWiseTotalPropertyPrice();
		
}