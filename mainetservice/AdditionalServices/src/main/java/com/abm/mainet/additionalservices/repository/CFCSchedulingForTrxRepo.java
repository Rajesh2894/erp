package com.abm.mainet.additionalservices.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.additionalservices.domain.CFCCollectionMasterEntity;

@Repository
public interface CFCSchedulingForTrxRepo extends JpaRepository<CFCCollectionMasterEntity, Serializable> {
	
	@Query("select cm.cuCountcentreno from CFCCounterMasterEntity cm where cm.cfcCollectionMasterEntity.cmCollncentreno=:collectionNo")
    List<String> getCounterNosByCollectionNo(@Param("collectionNo") String collectionNo);
	
	@Query("select cm.cmCollncentreno from CFCCollectionMasterEntity cm where cm.orgId=:orgId")
    List<String> getCollectionNoByOrgId(@Param("orgId") Long orgId);
	
	@Query("select cm from CFCCollectionMasterEntity cm where cm.cmCollnid=:collectionId")
	CFCCollectionMasterEntity getCFCCollectionInfoById(@Param("collectionId") Long collectionId);
}
