package com.abm.mainet.bill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.domain.AdjustmentBillDetailMappingEntity;

public interface AdjustmentBillDetailMappingRepository extends JpaRepository<AdjustmentBillDetailMappingEntity, Long> {
	
	 @Query("select a from AdjustmentBillDetailMappingEntity a where a.adjbmId =:adjbmId and a.orgId=:orgId")
	 List<AdjustmentBillDetailMappingEntity> getAdjustmentBillDetailsByBillDetId(@Param("adjbmId")Long adjbmId,
			@Param("orgId") Long orgId);

	 @Modifying
     @Transactional
	 @Query("update AdjustmentBillDetailMappingEntity m set m.adjbmId=:adjbmId where m.adjId=:adjId and m.orgId=:orgId")
	 void update(@Param("adjId")Long adjId, @Param("adjbmId")Long adjbmId, @Param("orgId") Long orgId);
}
