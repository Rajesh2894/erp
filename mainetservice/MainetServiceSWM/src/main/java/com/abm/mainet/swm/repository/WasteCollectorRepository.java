/**
 * 
 */
package com.abm.mainet.swm.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.swm.domain.WasteCollector;

/**
 * @author sarojkumar.yadav
 *
 */
public interface WasteCollectorRepository extends JpaRepository<WasteCollector, Long> {

    @Modifying
    @Query("update TbCfcApplicationMstEntity cfcApp set cfcApp.apmApplSuccessFlag ='Y',cfcApp.apmApplClosedFlag='Y' where cfcApp.apmApplicationId=:appId")
    int updateAppliactionMst(@Param("appId") Long appId);

    @Query("select cpdDesc from TbComparamDetEntity cd where cd.cpdId=:cpdId")
    String getComParamDetById(@Param("cpdId") Long cpdId);

    @Modifying
    @Query("update WasteCollector wc set wc.collectionStatus =:collectionStatus, wc.payFlag =:payFlag, wc.updatedDate = CURRENT_DATE where wc.applicationId =:applicationId")
    int updateWasteCollectorStatus(@Param("applicationId") Long applicationId, @Param("collectionStatus") String collectionStatus, @Param("payFlag") Boolean payFlag);

    @Query("select wc from WasteCollector wc where wc.applicationId =:applicationId and wc.orgId=:orgId")
    WasteCollector getWasteDetails(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

    @Modifying
    @Query("update WasteCollector wc set wc.mrfId =:mrfId, wc.vehicleNo=:vehicleNo ,wc.empName=:empName,wc.pickUpDate=:pickUpDate, wc.updatedBy=:updatedBy ,wc.lgIpMacUpd=:lgIpMacUpd, wc.updatedDate = CURRENT_DATE where wc.applicationId=:applicationId and wc.orgId=:orgId")
    int updateWasteCollector(@Param("mrfId") Long mrfId, @Param("vehicleNo") Long vehicleNo, @Param("empName") String empName, @Param("pickUpDate") Date pickUpDate,
            @Param("applicationId") Long applicationId, @Param("orgId") Long orgId, @Param("updatedBy") Long updatedBy, @Param("lgIpMacUpd") String lgIpMacUpd);

}
