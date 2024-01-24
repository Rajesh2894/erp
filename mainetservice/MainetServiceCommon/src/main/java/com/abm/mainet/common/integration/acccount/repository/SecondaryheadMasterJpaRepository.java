package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

/**
 * Repository : TbAcSecondaryheadMaster.
 */
public interface SecondaryheadMasterJpaRepository
        extends PagingAndSortingRepository<AccountHeadSecondaryAccountCodeMasterEntity, Long> {

    /**
     * @param sacHeadId
     * @return
     */
    @Override
    @Query("select d from AccountHeadSecondaryAccountCodeMasterEntity d where d.sacHeadId=:sacHeadId")
    AccountHeadSecondaryAccountCodeMasterEntity findOne(@Param("sacHeadId") Long sacHeadId);

    @Query("select d from AccountHeadSecondaryAccountCodeMasterEntity d where d.orgid=:orgid order by d.sacHeadCode,d.tbAcPrimaryheadMaster.primaryAcHeadCompcode desc")
    List<AccountHeadSecondaryAccountCodeMasterEntity> findAll(@Param("orgid") Long orgid);

    /**
     * @param sacHeadId
     * @return
     */
    @Query("select d from AccountHeadSecondaryAccountCodeMasterEntity d where d.tbAcPrimaryheadMaster.primaryAcHeadId=:primaryAcHeadId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> findAllById(@Param("primaryAcHeadId") Long primaryAcHeadId);

    /**
     * @return
     */
    @Query("select d from AccountHeadSecondaryAccountCodeMasterEntity d")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getLevelCode();

    @Query("select s.sacHeadId,s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc from AccountHeadSecondaryAccountCodeMasterEntity s where s.orgid=:orgId order by s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> findPrimarySecondaryHead(@Param("orgId") Long orgId);

    @Query("select d.tbAcPrimaryheadMaster.primaryAcHeadCompcode from AccountHeadSecondaryAccountCodeMasterEntity d where d.sacHeadId =:sacHeadId and d.orgid=:orgid")
    String findByPacHeadId(@Param("sacHeadId") Long sacHeadId, @Param("orgid") Long orgid);

    @Query("select d.sacHeadCode,d.sacHeadDesc,d.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity d where d.sacHeadId =:sacHeadId and d.orgid=:orgid")
    List<Object[]> findBysacHeadCodeDesc(@Param("sacHeadId") Long sacHeadId, @Param("orgid") Long orgid);

    @Query("select count(d.sacHeadId) from AccountHeadSecondaryAccountCodeMasterEntity d where d.vmVendorid =:vmVendorid and d.orgid=:orgid")
    Long findByCountVendor(@Param("vmVendorid") Long sacHeadId, @Param("orgid") Long orgid);
    
    @Query("select d.sacHeadId from AccountHeadSecondaryAccountCodeMasterEntity d where d.vmVendorid =:vmVendorid and d.orgid=:orgid")
    Long findSacHeadId(@Param("vmVendorid") Long sacHeadId, @Param("orgid") Long orgid);

    @Query("select p.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc from AccountHeadPrimaryAccountCodeMasterEntity p,AccountHeadSecondaryAccountCodeMasterEntity s where p.primaryAcHeadId = s.tbAcPrimaryheadMaster.primaryAcHeadId and s.sacHeadId=:sacHeadId order by p.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> findByPrimarySacHeadCodeDesc(@Param("sacHeadId") Long sacHeadId);

    @Query("select s.sacHeadId,s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc,s.tbAcPrimaryheadMaster.pacStatusCpdId,s.sacStatusCpdId,s.sacLeddgerTypeCpdId from AccountHeadSecondaryAccountCodeMasterEntity s where s.orgid=:orgId order by s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> findStatusPrimarySecondaryHeadData(@Param("orgId") Long orgId);
    
    @Query("select s.sacHeadId,s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc,s.tbAcPrimaryheadMaster.pacStatusCpdId,s.sacStatusCpdId,s.sacLeddgerTypeCpdId,s.acHeadCode"
    		+ " from AccountHeadSecondaryAccountCodeMasterEntity s,TbComparamDetEntity d"
    		+ " where s.orgid=:orgId and s.tbAcPrimaryheadMaster.cpdIdPayMode=d.cpdId AND d.tbComparamMas.cpmId IN (select c.cpmId from TbComparamMasEntity c where c.cpmPrefix='DTY') order by s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> findStatusPrimarySecondaryHeadDataForDeposit(@Param("orgId") Long orgId);
    
    @Query("select s.sacHeadId,s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc,s.tbAcPrimaryheadMaster.pacStatusCpdId,s.sacStatusCpdId,s.sacLeddgerTypeCpdId from AccountHeadSecondaryAccountCodeMasterEntity s where s.orgid=:orgId and s.tbAcFunctionMaster.functionId=:functionId order by s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> findStatusPrimarySecondaryHeadDataFuntId(@Param("orgId") Long orgId,@Param("functionId") Long functionId);

    /**
     * @param sacHeadId
     * @return
     */
    @Query("select d from AccountHeadSecondaryAccountCodeMasterEntity d where d.sacHeadId=:sacHeadId and d.orgid=:orgId order by 1 desc")
    AccountHeadSecondaryAccountCodeMasterEntity findBySacHeadId(@Param("sacHeadId") Long sacHeadId,
            @Param("orgId") Long orgId);

    @Query("select s.sacHeadId,s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc from AccountHeadSecondaryAccountCodeMasterEntity s where s.tbAcPrimaryheadMaster.cpdIdAccountType =:depTypeId and s.tbAcPrimaryheadMaster.pacStatusCpdId =:statusId and s.orgid =:orgId order by s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> findPrimarySecondaryHeadDepTypes(@Param("depTypeId") Long depTypeId,
            @Param("statusId") Long statusId, @Param("orgId") Long orgId);

    @Query(" SELECT distinct shm.sacHeadId,shm.acHeadCode FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.tbAcPrimaryheadMaster.cpdIdAccountType =:depTypeId and shm.tbAcPrimaryheadMaster.cpdIdPayMode =:cpdDepositType and shm.tbAcPrimaryheadMaster.pacStatusCpdId =:statusId and shm.orgid =:orgId ORDER BY shm.acHeadCode ASC")
    List<Object[]> findBudgetHeadDepTypes(@Param("depTypeId") Long depTypeId,
            @Param("cpdDepositType") Long cpdDepositType, @Param("statusId") Long statusId, @Param("orgId") Long orgId);

    @Query(" SELECT distinct shm.sacHeadId,shm.acHeadCode FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.tbAcPrimaryheadMaster.cpdIdAccountType =:depTypeId and shm.tbAcPrimaryheadMaster.cpdIdPayMode=:depSubTypeId and shm.tbAcPrimaryheadMaster.pacStatusCpdId =:statusId and shm.orgid =:orgId ORDER BY shm.acHeadCode ASC")
    List<Object[]> findAccountHeadDepTypes(@Param("depTypeId") Long depTypeId, @Param("depSubTypeId") Long depSubTypeId,
            @Param("statusId") Long statusId, @Param("orgId") Long orgId);

    @Query(" SELECT shm.sacHeadId, shm.acHeadCode  FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.orgid=:orgId ORDER BY shm.acHeadCode ASC")
    List<Object[]> findAccountHeadsByOrgId(@Param("orgId") Long orgId);

    @Query(" SELECT shm.sacHeadId,shm.acHeadCode,shm.tbAcPrimaryheadMaster.primaryAcHeadCompcode,shm.sacLeddgerTypeCpdId FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.sacStatusCpdId =:statusId and shm.orgid=:orgId ORDER BY shm.sacHeadCode,shm.sacHeadDesc ASC")
    List<Object[]> findSacHeadIdDescAllData(@Param("statusId") Long statusId, @Param("orgId") Long orgId);

    @Query("SELECT sm.sacHeadId, sm.acHeadCode, sm.sacStatusCpdId FROM AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " WHERE sm.sacLeddgerTypeCpdId IN(SELECT cd.cpdId FROM TbComparamDetEntity cd WHERE cd.cpdValue in ('OT','VD') AND cd.tbComparamMas.cpmId"
            + " IN(SELECT cm.cpmId FROM TbComparamMasEntity cm WHERE cm.cpmPrefix='FTY')) AND sm.orgid=:orgId AND sm.tbAcPrimaryheadMaster.cpdIdAcHeadTypes"
            + " IN(SELECT d.cpdId FROM TbComparamDetEntity d WHERE d.cpdValue IN('E','L','A') AND d.tbComparamMas.cpmId"
            + " IN(SELECT m.cpmId FROM TbComparamMasEntity m WHERE m.cpmPrefix='COA')) ORDER BY sm.acHeadCode ASC")
    List<Object[]> findExpenditureHeadsByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT sm.sacHeadId, sm.acHeadCode, sm.sacStatusCpdId FROM AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " WHERE sm.sacLeddgerTypeCpdId IN(SELECT cd.cpdId FROM TbComparamDetEntity cd WHERE cd.cpdValue in ('OT') AND cd.tbComparamMas.cpmId"
            + " IN(SELECT cm.cpmId FROM TbComparamMasEntity cm WHERE cm.cpmPrefix='FTY')) AND sm.orgid=:orgId AND sm.tbAcPrimaryheadMaster.cpdIdAcHeadTypes"
            + " IN(SELECT d.cpdId FROM TbComparamDetEntity d WHERE d.cpdValue IN('E','L','A') AND d.tbComparamMas.cpmId"
            + " IN(SELECT m.cpmId FROM TbComparamMasEntity m WHERE m.cpmPrefix='COA')) ORDER BY sm.acHeadCode ASC")
    List<Object[]> findExpenditureHeadsByOrgIdAccountTypeOthers(@Param("orgId") Long orgId);

    /*
     * @Query("SELECT sm.sacHeadId, sm.acHeadCode FROM AccountHeadPrimaryAccountCodeMasterEntity pm ,AccountHeadSecondaryAccountCodeMasterEntity sm "
     * + " WHERE pm.primaryAcHeadId=sm.tbAcPrimaryheadMaster.primaryAcHeadId" +
     * " AND pm.cpdIdPayMode IN(SELECT cd.cpdId FROM TbComparamDetEntity cd WHERE cd.tbComparamMas.cpmId " +
     * " IN(SELECT cm.cpmId FROM TbComparamMasEntity cm WHERE cm.cpmPrefix='TDS'))" + " AND sm.orgid=:orgId") List<Object[]>
     * findDeductionHeadsByOrgId(@Param("orgId") Long orgId);
     */
    @Query("SELECT DISTINCT sm.sacHeadId, sm.acHeadCode  FROM "
            + "AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "WHERE sm.sacLeddgerTypeCpdId IN(SELECT cd.cpdId FROM TbComparamDetEntity cd WHERE cd.cpdValue in ('OT','VD') AND cd.tbComparamMas.cpmId"
            + " IN(SELECT cm.cpmId FROM TbComparamMasEntity cm WHERE cm.cpmPrefix='FTY')) AND pm.cpdIdAcHeadTypes"
            + " IN(SELECT d.cpdId FROM TbComparamDetEntity d WHERE d.cpdValue <> 'E' AND d.tbComparamMas.cpmId"
            + " IN(SELECT m.cpmId FROM TbComparamMasEntity m WHERE m.cpmPrefix='COA')) AND pm.primaryAcHeadId = sm.tbAcPrimaryheadMaster.primaryAcHeadId AND sm.orgid =:orgId AND sm.sacStatusCpdId =:lookUpId")
    List<Object[]> findDeductionHeadsByOrgId(@Param("lookUpId") Long lookUpId, @Param("orgId") Long orgId);

    @Query(" SELECT shm.sacHeadId,shm.acHeadCode,shm.tbAcPrimaryheadMaster.primaryAcHeadCompcode,shm.tbAcPrimaryheadMaster.cpdIdAccountType FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.sacStatusCpdId =:activeStatusId and shm.orgid=:orgId and shm.sacLeddgerTypeCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet WHERE cpdValue IN ('OT','VD') AND comdet.tbComparamMas.cpmPrefix='FTY') ORDER BY shm.sacHeadCode,shm.sacHeadDesc ASC")
    List<Object[]> findAcHeadCodeInReceieptEntry(@Param("activeStatusId") Long activeStatusId,
            @Param("orgId") Long orgId);

    @Query("SELECT sm.acHeadCode FROM AccountHeadSecondaryAccountCodeMasterEntity sm WHERE sm.sacHeadId=:sacHeadId ")
    String findSacHeadCodeBySacHeadId(@Param("sacHeadId") Long sacHeadId);

    @Query("select d.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity d where d.sacHeadId=:sacHeadId order by d.acHeadCode asc")
    String getAccountHeadCodeInReceieptDetEntry(@Param("sacHeadId") Long sacHeadId);

    @Query("select d.tbAcFunctionMaster.functionId from AccountHeadSecondaryAccountCodeMasterEntity d where d.vmVendorid=:VmVendorid")
    Long vendorIdWiseGetFunctionIdValue(@Param("VmVendorid") Long VmVendorid);

    @Query("SELECT sm FROM AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "WHERE sm.sacLeddgerTypeCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet WHERE cpdValue='OT' AND comdet.tbComparamMas.cpmPrefix='FTY') "
            + "AND sm.sacStatusCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet1 WHERE cpdValue='A' AND comdet1.tbComparamMas.cpmPrefix='ACN') "
            + "AND sm.tbAcPrimaryheadMaster.cpdIdAcHeadTypes IN (SELECT cpdId FROM TbComparamDetEntity comdet2 WHERE comdet2.tbComparamMas.cpmPrefix='COA') "
            + "AND sm.orgid=:orgId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesForTax(@Param("orgId") Long orgId);

    @Query(" SELECT  shm.acHeadCode FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.sacHeadId=:accountHeadId")
    String findByAccountHead(@Param("accountHeadId") Long accountHeadId);

    @Query(" SELECT shm.sacHeadId, shm.acHeadCode  FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.orgid=:orgId and tbBankaccount!= null ORDER BY shm.sacHeadDesc ASC")
    List<Object[]> findAccountHeadsByOrgIdBankBook(@Param("orgId") Long orgId);

    @Query("select s from AccountHeadSecondaryAccountCodeMasterEntity s where s.orgid =:orgId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSacHeadCode(@Param("orgId") Long orgId);

    @Query("SELECT fy.faYear,fy.faFromDate,fy.faToDate FROM FinancialYear fy Where fy.faFromDate <= CURRENT_DATE order by fy.faFromDate desc")
    List<Object[]> getAllFinincialYear();

    @Query(" SELECT shm.sacHeadId, shm.acHeadCode  FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.orgid=:orgId  ORDER BY shm.sacHeadDesc ASC")
    List<Object[]> findAccountHeadsByOrgIdCashHead(@Param("orgId") Long orgId);

    @Query("SELECT sm FROM AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "WHERE sm.sacLeddgerTypeCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet WHERE cpdValue='CA' AND comdet.tbComparamMas.cpmPrefix='FTY') "
            + "AND sm.sacStatusCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet1 WHERE cpdValue='A' AND comdet1.tbComparamMas.cpmPrefix='ACN') "
            + "AND sm.orgid=:orgId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesForIncomeAndExpenditure(
            @Param("orgId") Long orgId);

    @Query("SELECT sm.sacHeadId FROM AccountHeadSecondaryAccountCodeMasterEntity sm, AccountBudgetCodeEntity bc where bc.tbAcFunctionMaster.functionId=sm.tbAcFunctionMaster.functionId and bc.tbAcPrimaryheadMaster.primaryAcHeadId=sm.tbAcPrimaryheadMaster.primaryAcHeadId and sm.orgid = bc.orgid and bc.prBudgetCodeid =:prBudgetCodeid and bc.orgid =:orgId")
    Long getSacHeadIdByBudgetCodeId(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);

    @Query(" SELECT shm.sacHeadId, shm.acHeadCode  FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.orgid=:orgId and shm.sacStatusCpdId=:activeStatusId and shm.tbAcPrimaryheadMaster.cpdIdAccountType=:accountTypeId ORDER BY shm.acHeadCode ASC")
    List<Object[]> getAcHeadCodeInReceieptCategoryType(@Param("accountTypeId") Long accountTypeId,
            @Param("activeStatusId") Long activeStatusId, @Param("orgId") Long orgId);

    @Query("select d.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity d where d.vmVendorid=:VmVendorid")
    String vendorIdWiseGetAcHeadCodeValue(@Param("VmVendorid") Long VmVendorid);

    @Query("SELECT sm FROM AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "WHERE sm.sacLeddgerTypeCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet WHERE cpdValue='OT' AND comdet.tbComparamMas.cpmPrefix='FTY') "
            + "AND sm.sacStatusCpdId IN (SELECT cpdId FROM TbComparamDetEntity comdet1 WHERE cpdValue='A' AND comdet1.tbComparamMas.cpmPrefix='ACN') "
            + "AND sm.tbAcPrimaryheadMaster.cpdIdAcHeadTypes IN (SELECT cpdId FROM TbComparamDetEntity comdet2 WHERE cpdValue <> 'I' AND comdet2.tbComparamMas.cpmPrefix='COA') "
            + "AND sm.orgid=:orgId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesForWorks(@Param("orgId") Long orgId);

    @Query("SELECT shm.sacHeadId, shm.acHeadCode  FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.orgid=:orgId and shm.sacStatusCpdId=:statusId  ORDER BY shm.acHeadCode ASC")
    List<Object[]> findAccountHeadsByOrgIdAndStatusId(@Param("orgId") Long orgId, @Param("statusId") Long statusId);

    @Query("SELECT sm.sacHeadId , sm.acHeadCode\n" +
            "  FROM AccountHeadSecondaryAccountCodeMasterEntity sm , AccountHeadPrimaryAccountCodeMasterEntity pm \n" +
            " WHERE sm.sacLeddgerTypeCpdId =:sacLedType AND sm.sacStatusCpdId =:statusCpdId and pm.cpdIdAcHeadTypes =:cpdIdAcheadTypes  and sm.orgid =:orgId and sm.tbAcPrimaryheadMaster.primaryAcHeadId=pm.primaryAcHeadId \n"
            +
            "")
    List<Object[]> findByLedgerHeadTypeAndAccountHead(@Param("orgId") Long orgId,
            @Param("statusCpdId") Long statusCpdId, @Param("sacLedType") Long sacLedType,
            @Param("cpdIdAcheadTypes") Long cpdIdAcheadTypes);

    @Query(" SELECT shm FROM AccountHeadSecondaryAccountCodeMasterEntity shm  WHERE shm.orgid=:orgId and shm.sacStatusCpdId=:activeStatusId and shm.tbAcFunctionMaster.functionId IS NOT NULL ORDER BY 1 ASC")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getActiveSacHeadCodeDeatails(@Param("orgId") Long orgId,
            @Param("activeStatusId") Long activeStatusId);

    @Query("select distinct a.sacHeadId,b.acHeadCode from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b where a.taxId in (select t.taxId from TbTaxMasEntity t "
            + "where t.orgid=:orgId and t.taxCategory1=:taxMasLookUpId) and a.orgId=:orgId and a.sacHeadId=b.sacHeadId and a.orgId=b.orgid")
    List<Object[]> getTaxMasBillDeductionAcHeadAllDetails(@Param("orgId") Long orgid,
            @Param("taxMasLookUpId") Long taxMasLookUpId);
    
    @Query("select distinct a.sacHeadId,b.acHeadCode from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b where a.taxId in (select t.taxId from TbTaxMasEntity t "
            + "where t.orgid=:orgId and t.taxCategory1=:taxMasLookUpId and t.taxCategory2=:taxMasLookUpSubId and t.taxActive='Y') and a.orgId=:orgId and a.sacHeadId=b.sacHeadId and a.orgId=b.orgid")
    List<Object[]> getTaxMasBillDeductionAcHeadAllDetails(@Param("orgId") Long orgid,
            @Param("taxMasLookUpId") Long taxMasLookUpId,@Param("taxMasLookUpSubId") Long taxMasLookUpSubId);

    @Query("select shm from AccountHeadSecondaryAccountCodeMasterEntity shm where shm.orgid=:orgId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadDesc(@Param("orgId") Long orgId);

    @Query("select distinct a.sacHeadId,b.acHeadCode from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b where a.taxId in (select t.taxId from TbTaxMasEntity t "
            + "where t.orgid=:orgId and t.taxCategory2=:taxMasLookUpId) and a.orgId=:orgId and a.sacHeadId=b.sacHeadId and a.orgId=b.orgid")
    List<Object[]> getTaxMasBillPaymentAcHeadAllDetails(@Param("orgId") Long orgid,
            @Param("taxMasLookUpId") Long taxMasLookUpId);

    @Query("select distinct b.sacHeadId,c.taxDesc from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b,TbTaxMasEntity c where c.orgid=:orgid and c.taxCategory1=:taxMasLookUpId and a.sacHeadId=b.sacHeadId and c.taxId = a.taxId and a.orgId=:orgid and a.orgId = b.orgid")
    List<Object[]> getTaxMasBillDeductionAcHeadDescAllDetails(@Param("orgid") Long orgid,
            @Param("taxMasLookUpId") Long taxMasLookUpId);

    @Query("select distinct sac.tbAcPrimaryheadMaster.primaryAcHeadId from AccountHeadSecondaryAccountCodeMasterEntity sac where sac.sacHeadId=:sacHeadId and sac.orgid=:orgid")
    Long getPrimaryHeadIdByPassSacHeadId(@Param("sacHeadId") Long sacHeadId,
            @Param("orgid") Long orgid);

    @Query("select shm from AccountHeadSecondaryAccountCodeMasterEntity shm where shm.orgid=:orgId and shm.sacStatusCpdId=:statusId and shm.tbAcFunctionMaster.functionId=:functionId and shm.tbAcPrimaryheadMaster.primaryAcHeadId=:pacHeadId and shm.sacLeddgerTypeCpdId=:sacLeddgerTypeCpdId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getDupDetails(@Param("orgId") Long orgid,
            @Param("statusId") Long activeStatusId, @Param("functionId") Long functionId,
            @Param("pacHeadId") Long pacHeadId, @Param("sacLeddgerTypeCpdId") Long sacLeddgerTypeCpdId);

    @Query("select sm.sacHeadId, sm.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity sm, AccountHeadPrimaryAccountCodeMasterEntity pm where sm.tbAcPrimaryheadMaster.primaryAcHeadId = pm.primaryAcHeadId and sm.orgid =:orgId and pm.cpdIdAcHeadTypes =:coaLookupId")
    List<Object[]> findExpenditureAccountHeadOnly(@Param("orgId") Long orgId, @Param("coaLookupId") Long coaLookupId);

    @Query("select v.pacHeadId,sm.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity sm, AdvanceEntryEntity v where v.pacHeadId=sm.sacHeadId and v.orgid=:orgId and v.prAdvEntryId=:advanceId")
    List<Object[]> getDeductionHeadForAdvAdjustment(@Param("orgId") Long orgId, @Param("advanceId") Long advanceId);

    
    @Query("select distinct a.taxId, b.acHeadCode, a.sacHeadId  from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b where a.taxId in (select t.taxId from TbTaxMasEntity t "
            + "where t.orgid=:orgId and t.taxCategory1=:taxMasLookUpId) and a.orgId=:orgId and a.sacHeadId=b.sacHeadId and a.orgId=b.orgid")
    List<Object[]> getTaxMasBillDeductionTaxId(@Param("orgId") Long orgid,
            @Param("taxMasLookUpId") Long taxMasLookUpId);
    
    @Query("select be.tbAcSecondaryheadMaster.sacHeadId from AccountBudgetCodeEntity be where be.prBudgetCodeid =:prBudgetCodeid and be.orgid =:orgId")
    Long getSacHeadIdByBudgetId(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);
    
    @Modifying //update
	@Query("update AccountBudgetCodeEntity as te set te.prBudgetCode =:prBudgetCode where te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId and te.orgid =:orgid")
	  void updateBudgetCodeDesc(@Param("prBudgetCode") String prBudgetCode,
	            @Param("sacHeadId") Long sacHeadId, @Param("orgid") Long orgid);
    
    @Query("select distinct a.taxId,b.acHeadCode from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b where a.taxId in (select t.taxId from TbTaxMasEntity t "
            + "where t.orgid=:orgid and t.taxCategory1=:taxCatId and t.smServiceId=:smServiceId and taxActive='Y') and a.orgId=:orgid and a.taxbActive='A'  and a.sacHeadId=b.sacHeadId and a.orgId=b.orgid")
    List<Object[]> getTaxMasterByTaxCategoryIdByServiceId(@Param("smServiceId") Long smServiceId, @Param("taxCatId") Long taxCatId,
            @Param("orgid") long orgid);
    
    @Query("select b.acHeadCode from TbTaxAcMappingEntity a, AccountHeadSecondaryAccountCodeMasterEntity b, TbTaxMasEntity tm where  tm.taxId=:taxId and tm.smServiceId=:smServiceId and a.orgId=:orgid and a.taxbActive='A' and tm.taxId=a.taxId and a.sacHeadId=b.sacHeadId and a.orgId=b.orgid")
    String getAcHeadFromTaxMaster(@Param("taxId") Long taxId,@Param("smServiceId") Long smServiceId, @Param("orgid") long orgid);


	
}
