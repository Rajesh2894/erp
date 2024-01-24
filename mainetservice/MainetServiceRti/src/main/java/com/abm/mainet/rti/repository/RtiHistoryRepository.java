package com.abm.mainet.rti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rti.domain.TbRtiApplicationDetailsHistory;
@Repository
public interface RtiHistoryRepository extends JpaRepository<TbRtiApplicationDetailsHistory, Long> {

	@Query("select a from  TbRtiApplicationDetailsHistory a where  a.apmApplicationId=:apmApplicationId ")
	List<TbRtiApplicationDetailsHistory> findByAppId(@Param("apmApplicationId") Long apmApplicationId);
}
