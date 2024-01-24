package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.BIDMasterEntity;
import com.abm.mainet.workManagement.domain.MeasurementBookMaster;
@Repository
public interface BIDDetailRepository  extends JpaRepository<BIDMasterEntity,Long>{
	
	@Query("select count(e.bidNo) from BIDMasterEntity e where e.bidNo=:bidNo and e.orgId=:orgId")
	 Long  BidCount(@Param("bidNo") String bidNo,@Param("orgId") Long orgId);
	
	 @Query("select mb from BIDMasterEntity mb where mb.orgId = :orgId")
	    List<BIDMasterEntity> getAllBidListByOrgId(@Param("orgId") Long orgId);

}
