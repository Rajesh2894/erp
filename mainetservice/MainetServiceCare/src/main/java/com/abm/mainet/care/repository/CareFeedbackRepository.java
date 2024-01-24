package com.abm.mainet.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.care.domain.CareFeedback;

@Repository
public interface CareFeedbackRepository extends JpaRepository<CareFeedback, Long>{

	@Query("select fb from CareFeedback fb where fb.tokenNumber =:applicationID")
	CareFeedback getFeedbackByApplicationId(@Param("applicationID")String applicationID);
}
