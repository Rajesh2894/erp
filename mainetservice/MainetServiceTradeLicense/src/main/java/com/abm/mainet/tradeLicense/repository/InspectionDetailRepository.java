package com.abm.mainet.tradeLicense.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.TbMtlInspectionReg;

@Repository
public interface InspectionDetailRepository extends CrudRepository<TbMtlInspectionReg, Long> {

	
	@Query("Select a from TbMtlInspectionReg a where a.inspNo =:inspNo and a.orgId =:orgId ")
	TbMtlInspectionReg getInspectionDetByInspNo(@Param("inspNo") Long inspNo, @Param("orgId") Long orgId);

}
