package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetRequisition;

@Repository
public interface AssetRequisitionRepo extends JpaRepository<AssetRequisition, Long> {

    AssetRequisition findByAssetRequisitionId(Long astReqId);
    
    @Modifying
    @Query("UPDATE AssetRequisition ai SET ai.requisitionNumber=:requisitionNumber where ai.assetRequisitionId =:rfiId and ai.orgId =:orgId" )
    void updateRequisitionNumber(@Param("rfiId") Long rfiId, @Param("requisitionNumber") String requisitionNumber,@Param("orgId") Long orgId );


}
