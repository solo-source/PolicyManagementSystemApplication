package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entities.Agency;

//Provide necessary annotation
@Repository
public interface AgencyRepository extends JpaRepository<Agency,String> {

	//Provide necessary method to view agencies based on star rating and 
	//view agencies based on property location  and 
	//get the total property price agency wise
	public List<Agency> findByStarRatingGreaterThanEqual(float starRating);
	public List<Agency> findDistinctByPropertyList_Location(String location);
	
	@Query(value = "SELECT a.agency_id AS agencyId, SUM(p.price) AS totalPrice " +
             "FROM agency a JOIN property_info p ON a.agency_id = p.agency_id " +
             "GROUP BY a.agency_id", nativeQuery = true)
	List<Object[]> getAgencyWiseTotalPropertyPrice();
}