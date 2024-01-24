package com.abm.mainet.account.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;

/**
 * Repository : TbAcChequebookleafDet.
 */
public interface TbAcChequebookleafDetJpaRepository extends PagingAndSortingRepository<TbAcChequebookleafDetEntity, Long> {

    @Query(" SELECT a From TbAcChequebookleafDetEntity a WHERE a.chequebookDetid =:chequebookDetid ")
    TbAcChequebookleafDetEntity findById(@Param("chequebookDetid") Long chequebookDetid);

    @Modifying
    @Transactional
    @Query("UPDATE TbAcChequebookleafDetEntity set cpdIdstatus =:cpdIdstatus, stopPayRemark =:stopPayRemark,stoppayDate =:stoppayDate,updatedBy =:updatedBy,updatedDate =:updatedDate,lgIpMacUpd =:lgIpMacUpd where chequebookDetid =:chequeNo and orgId =:orgId ")
    void updateStopPaymentEntry(@Param("chequeNo") Long chequeNo, @Param("cpdIdstatus") Long cpdIdstatus,
            @Param("stopPayRemark") String stopPayRemark,
            @Param("stoppayDate") Date stoppayDate,
            @Param("updatedBy") Long updatedBy,
            @Param("updatedDate") Date updatedDate,
            @Param("lgIpMacUpd") String lgIpMacUpd,
            @Param("orgId") Long orgId);
    
    @Query(" SELECT a.cpdIdstatus From TbAcChequebookleafDetEntity a WHERE a.chequebookDetid =:chequebookDetid AND a.orgid=:orgId")
    Long getCheckSatatus(@Param("chequebookDetid") Long checkBookDetId, @Param("orgId") Long orgId);

}
