package com.abm.mainet.cfc.loi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.loi.domain.DishonourChargeEntity;

@Repository
public interface DishonurChargeEntryRepository extends JpaRepository<DishonourChargeEntity, Long> {

	@Query("select dis from DishonourChargeEntity dis  where   dis.apmApplicationId=:apmApplicationId and isDishnrChgPaid='N'")
	DishonourChargeEntity getDishonourChargeData(@Param("apmApplicationId") Long apmApplicationId);

}
