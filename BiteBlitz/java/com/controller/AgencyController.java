package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Agency;
import com.exception.InvalidAgencyException;
import com.service.IAgencyService;

//Provide necessary Annotation
@RestController
public class AgencyController {

	// Provide necessary Annotation
	@Autowired
	private IAgencyService agencyService;

	// Provide necessary Annotation for the below methods and fill the code
	@PostMapping("/addAgency")
	public Agency addAgency(@RequestBody @Validated  Agency agencyObj) {
		//fill code
		return agencyService.addAgency(agencyObj);
	}
	
	@PutMapping("/updateCommissionRate/{agencyId}/{commissionRate}")
	public Agency updateCommissionRate(@PathVariable String agencyId, @PathVariable float commissionRate) throws InvalidAgencyException {
		//fill code
		return agencyService.updateCommissionRate(agencyId, commissionRate);
	}
	
	@GetMapping("/viewAgenciesByStarRating/{starRating}")
	public List<Agency> viewAgenciesByStarRating(@PathVariable float starRating) {
		//fill code
		return agencyService.viewAgenciesByStarRating(starRating);		
	}
	
	@GetMapping("/viewAgenciesByPropertyLocation/{location}")
	public List<Agency> viewAgenciesByPropertyLocation(@PathVariable String location) {
		//fill code
		return agencyService.viewAgenciesByPropertyLocation(location);		
	}
	
	@GetMapping("/getAgencyWiseTotalPropertyPrice")
	public Map<String,Double> getAgencyWiseTotalPropertyPrice() {
		//fill code
		return agencyService.getAgencyWiseTotalPropertyPrice();		
	}
}
