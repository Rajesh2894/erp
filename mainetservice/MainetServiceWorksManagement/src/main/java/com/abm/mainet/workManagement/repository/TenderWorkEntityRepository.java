package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.workManagement.domain.TenderMasterEntity;
import com.abm.mainet.workManagement.domain.TenderWorkEntity;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;

public interface TenderWorkEntityRepository extends JpaRepository<TenderWorkEntity, Long>{
	
	List<TenderWorkEntity>   findByTenderMasEntity(TenderMasterEntity tt);
	
    @Query("select tw from TenderWorkEntity tw where tw.workDefinationEntity.workId=:workId and tw.orgId=:orgId")
    TenderWorkEntity getTenderData(@Param("workId") Long workId, @Param("orgId") Long orgId);
    
    @Query("select tw.workDefinationEntity.workId from TenderWorkEntity tw where tw.tenderMasEntity.tndId=:tndId and tw.orgId=:orgId")
    Long getTenderworkid(@Param("tndId") Long tndId, @Param("orgId") Long orgId);
    
    @Query("select tw from WorkEstimateMaster tw where tw.workId=:workId and tw.orgId=:orgId")
    List<WorkEstimateMaster> getWorkEstimateDate(@Param("workId") Long workId, @Param("orgId") Long orgId);
    
}
