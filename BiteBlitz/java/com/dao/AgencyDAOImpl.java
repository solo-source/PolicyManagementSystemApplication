package com.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entities.Agency;
import com.exception.InvalidAgencyException;
import com.repository.AgencyRepository;

@Component
public class AgencyDAOImpl implements IAgencyDAO {

	// Provide necessary Annotation
	@Autowired
	private AgencyRepository agencyRepository;

	public Agency addAgency(Agency agencyObj) {
		//fill code
		return agencyRepository.save(agencyObj);
	}
	
	public Agency updateCommissionRate(String agencyId, float commissionRate) throws InvalidAgencyException {
		//fill code
		Agency obj = agencyRepository.findById(agencyId).orElse(null);
		if(obj==null)
			throw new InvalidAgencyException();
		else {
			obj.setCommissionRate(commissionRate);
			return obj;
		}	
	}
	
	public List<Agency> viewAgenciesByStarRating(float starRating) {
		//fill code
		return agencyRepository.findByStarRatingGreaterThanEqual(starRating);		
	}
	
	public List<Agency> viewAgenciesByPropertyLocation(String location) {
		//fill code
		return agencyRepository.findDistinctByPropertyList_Location(location);		
	}
	
	public Map<String,Double> getAgencyWiseTotalPropertyPrice() {
		//fill code
		List<Object[]> results = agencyRepository.getAgencyWiseTotalPropertyPrice();
        return results.stream()
                .collect(Collectors.toMap(
                    result -> ((String) result[0]).toString(),
                    result -> ((Double) result[1]).doubleValue()
                ));

	}
}
