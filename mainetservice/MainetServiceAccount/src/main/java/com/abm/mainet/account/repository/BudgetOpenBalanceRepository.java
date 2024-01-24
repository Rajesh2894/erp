package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity;

public interface BudgetOpenBalanceRepository extends PagingAndSortingRepository<AccountBudgetOpenBalanceEntity, Long> {

    @Query("select e from AccountBudgetOpenBalanceEntity e  where e.orgid=:orgid and e.faYearid =:faYearid order by 1 desc")
    List<AccountBudgetOpenBalanceEntity> findByFinancialId(@Param("faYearid") Long faYearid, @Param("orgid") Long orgId);

    @Query("select e from AccountBudgetOpenBalanceEntity e  where e.orgid=:orgid order by 1 desc")
    List<AccountBudgetOpenBalanceEntity> findAllAccountHeadsData(@Param("orgid") Long orgId);

    @Modifying
    @Query("update AccountBudgetOpenBalanceEntity as e  set e.updatedBy =:updatedBy,e.updatedDate =:updatedDate,e.lgIpMacUpd =:lgIpMacUpd,e.cloBalAmt =:closingDrAmt,e.cloBalType =:drCrId  where e.orgid=:orgid and e.tbAcSecondaryheadMaster.sacHeadId=:sacHeadId and e.faYearid=:faYearid")
    void updateYearEndProcessClosingDrAmtData(@Param("updatedBy") Long updatedBy, @Param("updatedDate") Date updatedDate,
            @Param("lgIpMacUpd") String lgIpMacUpd, @Param("sacHeadId") Long sacHeadId,
            @Param("closingDrAmt") BigDecimal closingDrAmt, @Param("drCrId") Long drCrId, @Param("orgid") Long orgid,
            @Param("faYearid") Long faYearid);

    @Modifying
    @Query("update AccountBudgetOpenBalanceEntity as e  set e.updatedBy =:updatedBy,e.updatedDate =:updatedDate,e.lgIpMacUpd =:lgIpMacUpd,e.openbalAmt =:closingDrAmt,e.tbComparamDet.cpdId =:drCrId  where e.orgid=:orgid and e.tbAcSecondaryheadMaster.sacHeadId=:sacHeadId and e.faYearid=:faYearid")
    void updateYearEndProcessNextYearOpeningAmtData(@Param("updatedBy") Long updatedBy, @Param("updatedDate") Date updatedDate,
            @Param("lgIpMacUpd") String lgIpMacUpd, @Param("sacHeadId") Long sacHeadId,
            @Param("closingDrAmt") String closingDrAmt, @Param("drCrId") Long drCrId, @Param("orgid") Long orgid,
            @Param("faYearid") Long faYearid);

    @Query("select e from AccountBudgetOpenBalanceEntity e  where e.orgid=:orgid and e.faYearid =:faYearid and e.tbAcSecondaryheadMaster.sacHeadId=:sacHeadId")
    List<AccountBudgetOpenBalanceEntity> checkBugOpenBalRecordExists(@Param("orgid") Long orgId, @Param("faYearid") Long faYearid,
            @Param("sacHeadId") Long sacHeadId);

    @Query("select sa.tbAcPrimaryheadMaster.cpdIdAcHeadTypes from AccountHeadSecondaryAccountCodeMasterEntity sa  where sa.orgid=:orgid and sa.sacHeadId=:sacHeadId")
    Long checkHeadCatedoryTypeId(@Param("orgid") Long orgId, @Param("sacHeadId") Long sacHeadId);

}
