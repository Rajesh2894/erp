package com.abm.mainet.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.care.domain.CareDepartmentAction;

@Repository
public interface CareDepartmentActionRepository extends JpaRepository<CareDepartmentAction, Long> {
	
	@Query("select c from CareDepartmentAction c where c.careRequest.id =:id")
	List<CareDepartmentAction> findByCareId(@Param("id") Long id);


}
