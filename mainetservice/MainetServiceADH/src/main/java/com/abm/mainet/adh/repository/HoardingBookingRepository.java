package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.HoardingBookingEntity;



@Repository
public interface HoardingBookingRepository extends JpaRepository<HoardingBookingEntity, Long>  {
	
	  @Query("SELECT a FROM HoardingBookingEntity a join fetch a.hoardingMasterEntity WHERE a.newAdvertisement.adhId=:adhId  AND a.orgId =:orgId")
	    List<HoardingBookingEntity> findHoardingDetailsByAdhIdAndOrgId(@Param("adhId") Long adhId, @Param("orgId") Long orgId);

	
	  @Query("SELECT a FROM HoardingBookingEntity a WHERE a.orgId =:orgId")
	    List<HoardingBookingEntity> fetchHoardingDetailsByOrgId(@Param("orgId") Long orgId);
	  
}
