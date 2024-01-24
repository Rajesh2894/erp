package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;

/**
 * Repository : TbTaxMas.
 */
public interface TbTaxMasJpaRepository extends
        PagingAndSortingRepository<TbTaxMasEntity, Long> {

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.orgid=:orgid")
    List<TbTaxMasEntity> findAllByOrgId(
            @Param("orgid") Long orgid);

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.taxId=:taxId and taxMasEntity.orgid=:orgid")
    List<TbTaxMasEntity> findAllByTaxOrgId(
            @Param("taxId") Long taxId,
            @Param("orgid") Long orgid);

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.taxId=:taxId and taxMasEntity.orgid=:orgid and taxMasEntity.department.dpDeptid=:dpDeptId")
    List<TbTaxMasEntity> findAllByTaxDeptOrgId(
            @Param("taxId") Long taxId,
            @Param("orgid") Long orgid,
            @Param("dpDeptId") Long dpDeptId);

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.orgid=:orgid and taxMasEntity.department.dpDeptid=:dpDeptId")
    List<TbTaxMasEntity> findAllByDeptOrgId(
            @Param("dpDeptId") Long dpDeptId,
            @Param("orgid") Long orgid);

    @Query("select count(taxMasEntity) from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.orgid=:orgid and taxMasEntity.department.dpDeptid=:dpDeptId and taxMasEntity.taxDisplaySeq=:seqNum and taxMasEntity.taxApplicable=:taxApplicable")
    Long validateSequence(@Param("seqNum") Long seqNum,
            @Param("dpDeptId") Long dpDeptId,
            @Param("orgid") Long orgid, @Param("taxApplicable") Long taxApplicableAt);

    @Query("select count(taxMasEntity) from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.orgid=:orgid and taxMasEntity.department.dpDeptid=:dpDeptId and taxMasEntity.collSeq=:seqNum and taxMasEntity.taxApplicable=:taxApplicable")
    Long validateCollSequence(@Param("seqNum") Long seqNum,
            @Param("dpDeptId") Long dpDeptId,
            @Param("orgid") Long orgid, @Param("taxApplicable") Long taxApplicableAt);

    @Query("SELECT e.taxCode, e.taxMethod FROM TbTaxMasEntity e WHERE e.orgid=:orgId "
            + " AND e.department.dpDeptid=:deptId AND e.taxCategory1=:taxCategory AND e.taxCategory2=:taxSubCategory ")
    Object[] getTaxCodeByTaxCatagory(
            @Param("orgId") Long orgId,
            @Param("deptId") Long dpDeptId,
            @Param("taxCategory") Long taxCategory,
            @Param("taxSubCategory") Long taxSubCategory);

    @Query("SELECT COUNT(m.taxId) FROM TbTaxMasEntity m WHERE m.orgid=:orgId AND m.department.dpDeptid=:deptId AND m.smServiceId=:serviceId AND  m.taxGroup=:taxGroup AND m.taxCategory1=:taxCat AND  m.taxCategory2=:taxSubCat")
    Long validateServiceTax(
            @Param("orgId") Long orgId,
            @Param("deptId") Long deptId,
            @Param("serviceId") Long serviceId,
            @Param("taxGroup") String taxGroup,
            @Param("taxCat") Long taxCat,
            @Param("taxSubCat") Long taxSubCat);

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity where taxMasEntity.taxId=:taxId and taxMasEntity.orgid=:orgid ")
    TbTaxMasEntity findTaxByTaxIdAndOrgId(
            @Param("taxId") Long taxId,
            @Param("orgid") long orgid);

    /**
     * @param deptId
     * @param advanceId
     * @param orgid
     * @param taxApplicableAt
     * @param string
     * @return
     */
    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity where taxMasEntity.department.dpDeptid=:dpDeptid and taxMasEntity.orgid=:orgid "
            + " and taxMasEntity.taxCategory1=:taxCatId  and taxMasEntity.taxApplicable=:taxApplicableAt and taxActive='Y' ")
    List<TbTaxMasEntity> getTaxMasterByTaxCategoryId(@Param("dpDeptid") Long deptId, @Param("taxCatId") Long taxCatId,
            @Param("orgid") long orgid, @Param("taxApplicableAt") long taxApplicableAt);

    /**
     * @param orgid
     * @param deptId
     * @param chargeApplicableAt
     * @param time
     * @return
     */
    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt and tm.taxActive='Y' "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllTaxesForBillGeneration(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt);

    /**
     * @param taxId
     * @param orgid
     * @return
     */
    @Query("select taxMasEntity.taxDesc from TbTaxMasEntity taxMasEntity where taxMasEntity.taxId=:taxId and "
            + " taxMasEntity.orgid=:orgid ")
    String findTaxDescByTaxIdAndOrgId(@Param("taxId") Long taxId, @Param("orgid") long orgid);

    @Query("SELECT DISTINCT tm.taxDescId FROM TbTaxMasEntity tm WHERE tm.orgid=:orgId AND tm.department.dpDeptid=:deptId")
    List<Long> findTaxByDeptIdAndOrgId(@Param("orgId") Long orgId, @Param("deptId") Long deptId);

    @Query("select b.prBudgetCodeid from AccountHeadPrimaryAccountCodeMasterEntity p,AccountHeadSecondaryAccountCodeMasterEntity s,"
            + " AccountBudgetCodeEntity b where p.primaryAcHeadId=s.tbAcPrimaryheadMaster.primaryAcHeadId "
            + " and s.sacHeadId=b.tbAcSecondaryheadMaster.sacHeadId"
            + " and p.cpdIdPayMode=:cpdIdPayMode and s.orgid=:orgid and p.pacStatusCpdId=:cpdIdStatusFlag")
    Long fetchBudgetCodeIdForReceiptMode(@Param("orgid") Long orgId, @Param("cpdIdPayMode") Long cpdFeemode,
            @Param("cpdIdStatusFlag") Long activePrefix);

    @Query("select b.sacHeadId from TbTaxMasEntity t,TbTaxAcMappingEntity b where"
            + " t.orgid = b.orgId and t.orgid=:orgid and t.taxId = b.taxId and t.taxId=:taxId and b.taxbActive=:activePrefix")
    Long fetchSacHeadIdForReceiptDet(@Param("orgid") Long orgId, @Param("taxId") Long taxId,
            @Param("activePrefix") String activePrefix);

    @Query("select b.sacHeadId from TbTaxMasEntity t,TbTaxAcMappingEntity b where"
            + " t.orgid = b.orgId and t.orgid=:orgid and t.taxId = b.taxId and t.taxId=:taxId"
            + " and b.taxbActive=:activePrefix and b.dmdClass=:dmdClassId ")
    Long fetchSacHeadIdForReceiptDetByDemandClass(@Param("orgid") Long orgId, @Param("taxId") Long taxId,
            @Param("activePrefix") String activePrefix, @Param("dmdClassId") Long dmdClassId);

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.taxDescId=:taxDescId and taxMasEntity.orgid=:orgid and taxMasEntity.department.dpDeptid=:deptId")
    List<TbTaxMasEntity> findAllByDescId(
            @Param("taxDescId") Long taxDescId,
            @Param("orgid") Long orgid,
            @Param("deptId") Long deptId);

    @Query("SELECT m FROM TbTaxMasEntity m WHERE m.orgid=:orgId "
            + "AND m.department.dpDeptid=:deptId "
            + "AND m.taxDescId=:descId "
            + "AND m.taxApplicable=:taxAppl "
            + "AND m.taxMethod=:taxMethod "
            + "AND m.taxGroup=:taxGroup "
            + "AND m.taxCategory1=:taxCat "
            + "AND m.taxCategory2=:taxSubCat")
    List<TbTaxMasEntity> validateTax(
            @Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("descId") Long taxDescId, @Param("taxAppl") Long taxAppl,
            @Param("taxMethod") String taxMethod, @Param("taxGroup") String taxGroup,
            @Param("taxCat") Long taxCat, @Param("taxSubCat") Long taxSubCat);

    @Query("select b from AccountBudgetCodeEntity b where b.prBudgetCodeid=:budgetCodeId")
    AccountBudgetCodeEntity fetchBudgetCodeMas(@Param("budgetCodeId") Long budgetCodeId);

    @Query("select t from TbTaxMasEntity t where t.orgid = ?1 and t.taxCode = ?2")
    TbTaxMasEntity findTaxDetails(Long orgId, String taxCode);

    @Query("select b.prBudgetCodeid from AccountHeadPrimaryAccountCodeMasterEntity p,"
            + " AccountBudgetCodeEntity b where p.primaryAcHeadId=b.tbAcPrimaryheadMaster.primaryAcHeadId "
            + " and p.cpdIdPayMode=:cpdIdPayMode and p.orgid=:orgid and p.pacStatusCpdId=:cpdIdStatusFlag")
    Long fetchBudgetCodeIdForPrimaryReceiptMode(@Param("orgid") Long orgId, @Param("cpdIdPayMode") Long cpdFeemode,
            @Param("cpdIdStatusFlag") Long activePrefix);

    @Query("SELECT m FROM TbTaxMasEntity m WHERE m.orgid=:orgId "
            + "AND m.department.dpDeptid=:deptId "
            + "AND m.taxDescId=:descId "
            + "AND m.taxApplicable=:taxAppl "
            + "AND m.taxMethod=:taxMethod "
            + "AND m.taxGroup=:taxGroup "
            + "AND m.taxCategory1=:taxCat "
            + "AND m.taxCategory2=:taxSubCat "
            + "AND m.smServiceId=:serviceId")
    List<TbTaxMasEntity> validateTaxByServiceId(
            @Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("descId") Long taxDescId, @Param("taxAppl") Long taxAppl,
            @Param("taxMethod") String taxMethod, @Param("taxGroup") String taxGroup,
            @Param("taxCat") Long taxCat, @Param("taxSubCat") Long taxSubCat,
            @Param("serviceId") Long serviceId);

    /**
     * @param orgid
     * @param deptId
     * @param chargeApplicableAt
     * @param taxCategory for not in
     * @return
     */
    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt and tm.taxCategory2 <> :taxSubCategory and tm.taxActive='Y' "
            + " ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllTaxesForBillGeneration(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt, @Param("taxSubCategory") Long taxSubCategory);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllTaxesForBillPayment(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt);

    @Query("select t.taxId,t.taxDesc,t.taxCode,t.taxActive from TbTaxMasEntity t where t.orgid =?1 and  t.department.dpDeptid =?2")
    List<Object[]> getAllTaxesBasedOnDept(Long orgId, Long deptId);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND (tm.taxApplicable=:chargeApplicableAt OR tm.taxApplicable=:chargeApplicableAtBillRcpt) and tm.parentCode is null and tm.taxActive='Y' "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> fetchAllIndependentTaxes(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt,@Param("chargeApplicableAtBillRcpt") long chargeApplicableAtBillRcpt);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND (tm.taxApplicable=:chargeApplicableAt OR tm.taxApplicable=:chargeApplicableAtBillRcpt) and tm.taxActive='Y' and  tm.parentCode is not null "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> fetchAllDepenentTaxes(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt,@Param("chargeApplicableAtBillRcpt") long chargeApplicableAtBillRcpt);

    @Query("select max(tx.taxDisplaySeq)+1 from TbTaxMasEntity tx where tx.orgid=:orgId and tx.department.dpDeptid=:deptId and tx.taxApplicable=:taxApplicableAt")
    Integer getNextDisplaySequence(@Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("taxApplicableAt") Long taxApllicableType);

    @Query("select max(tx.collSeq)+1 from TbTaxMasEntity tx where tx.orgid=:orgId and tx.department.dpDeptid=:deptId and tx.taxApplicable=:taxApplicableAt")
    Integer getNextCollectionSequence(@Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("taxApplicableAt") Long taxApllicableType);

    @Query("select distinct taxCategory1 from TbTaxMasEntity tx where tx.orgid=:orgId and tx.department.dpDeptid=:deptId and tx.taxApplicable in :taxApplicableAt")
    List<Long> getDistinctTaxCatByDept(@Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("taxApplicableAt") List<Long> taxApllicableType);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxActive='Y' and tm.taxCode= :taxCode")
    TbTaxMasEntity getTaxMasterByTaxCode(@Param("orgId") Long orgid, @Param("deptId") Long deptId,
            @Param("taxCode") String taxCode);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.smServiceId=:smServiceId AND tm.orgid=:orgId"
            + " AND tm.taxApplicable=:taxApplicableAt and tm.taxActive=:taxActive ORDER BY tm.taxDisplaySeq ASC")
    List<TbTaxMasEntity> fetchAllApplicableTaxes(@Param("smServiceId") long serviceId, @Param("orgId") long orgId,
            @Param("taxApplicableAt") long taxApplicableAt, @Param("taxActive") String taxActive);

    @Query("SELECT  tm.taxId FROM TbTaxMasEntity tm WHERE tm.orgid=:orgId AND tm.department.dpDeptid=:deptId AND tm.taxGroup=:taxGroup")
    List<Long> fetchTaxIdByDeptIdForTaxGroup(@Param("taxGroup") String govtTaxGrp, @Param("orgId") Long orgid,
            @Param("deptId") Long dpDeptid);

    @Query("SELECT  count(t) FROM TbTaxMasEntity t WHERE t.orgid=:orgId AND t.department.dpDeptid=:deptId AND t.taxCategory1=:taxCategory1")
    int getCountOfTaxesByTaxCat(@Param("taxCategory1") Long taxCategory1, @Param("orgId") Long orgid,
            @Param("deptId") Long dpDeptid);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND (tm.taxApplicable=:chargeApplicableAt OR tm.taxApplicable=:chargeApplicableAtBillRcpt) and tm.parentCode is not null and tm.taxCategory2 <> :taxSubCategory "
            + " and tm.taxActive='Y' ")
    List<TbTaxMasEntity> fetchAllDepenentTaxes(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt, @Param("taxSubCategory") Long taxSubCategory,@Param("chargeApplicableAtBillRcpt") long chargeApplicableAtBillRcpt);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND (tm.taxApplicable=:chargeApplicableAt OR tm.taxApplicable=:chargeApplicableAtBillRcpt) and tm.parentCode is null "
            + " and tm.taxCategory2 <> :taxSubCategory and tm.taxActive='Y' ")
    List<TbTaxMasEntity> fetchAllIndependentTaxes(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt, @Param("taxSubCategory") Long taxSubCategory, @Param("chargeApplicableAtBillRcpt") long chargeApplicableAtBillRcpt);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt and tm.taxCategory2 =:taxSubCategory and tm.taxActive='Y' "
            + " ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllTaxesByChargeAppAtAndTaxSubCat(@Param("orgId") Long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") Long chargeApplicableAt, @Param("taxSubCategory") Long taxSubCategory);

    @Query("SELECT  t.taxId FROM TbTaxMasEntity t WHERE  t.taxApplicable=:appicableAt and t.orgid=:orgId and t.department.dpDeptid=:deptId  and t.taxCategory1=:taxCategory and t.taxCategory2=:taxSubCategory")
    Long getTaxId(@Param("appicableAt") Long appicableAt, @Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("taxCategory") Long taxCategory, @Param("taxSubCategory") Long taxSubCategory);
    
    @Query("SELECT  t.taxId FROM TbTaxMasEntity t WHERE  t.taxApplicable=:appicableAt and t.orgid=:orgId and t.department.dpDeptid=:deptId  and t.taxCategory1=:taxCategory and t.taxCategory2=:taxSubCategory and t.smServiceId=:smServiceId and t.taxActive=:taxActive")
    Long getTaxIdByServiceId(@Param("appicableAt") Long appicableAt, @Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("taxCategory") Long taxCategory, @Param("taxSubCategory") Long taxSubCategory,@Param("smServiceId") long serviceId,@Param("taxActive") String taxActive);
   
    @Query("select t from TbTaxMasEntity t where t.taxActive='Y' and t.taxApplicable=:appicableAt and t.department.dpDeptid=:deptId and t.orgid=:orgid")
    List<TbTaxMasEntity> getAllTaxForSupplementryBill(@Param("appicableAt") Long appicableAt, @Param("deptId") Long deptId,
            @Param("orgid") Long orgid);

    @Query("select b.sacHeadId from TbTaxMasEntity t,TbTaxAcMappingEntity b where"
            + " t.orgid = b.orgId and t.orgid=:orgid and t.taxId = b.taxId and b.taxbActive='A' and t.taxId=:taxId and t.taxApplicable=:appicableAt")
    Long fetchSacHeadIdForSupplementryBill(@Param("orgid") Long orgId, @Param("taxId") Long taxId,
            @Param("appicableAt") Long appicableAt);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt and tm.taxCategory2 <> :taxSubCategory and tm.taxActive='N' "
            + " ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllNotActiveTaxesForBillGeneration(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt,
            @Param("taxSubCategory") Long taxSubCategory);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt and tm.taxActive='N' "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllNotActiveTaxesForBillGeneration(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt);

    @Query("select t.taxId,t.taxCategory2 from TbTaxMasEntity t where t.orgid=:orgId and t.department.dpDeptid=:deptId")
    List<Object[]> getTaxIdAndTaxSubCategory(@Param("orgId") long orgId, @Param("deptId") Long deptId);
    
    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt and tm.taxActive='Y' "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllActiveTaxList(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt);
    
    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.taxApplicable=:chargeApplicableAt AND tm.smServiceId=:smServiceId and tm.taxActive='Y' "
            + "  ORDER BY tm.collSeq asc")
    List<TbTaxMasEntity> findAllTaxesForBillGenerationByServiceId(@Param("orgId") long orgid, @Param("deptId") Long deptId,
            @Param("chargeApplicableAt") long chargeApplicableAt,@Param("smServiceId") Long smServiceId);
    
    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.orgid=:orgid and taxDesc like '%Withheld%'")
    List<TbTaxMasEntity> findAllByOrgIdAdnTaxDesc(@Param("orgid") Long orgid);

    
    @Query("select cd.cpdDescMar from TbTaxMasEntity tm , TbComparamDetEntity cd where tm.taxDescId=cd.cpdId  and tm.taxId=:taxId and "
            + " tm.orgid=:orgid ")
	String findTaxDescRegByTaxIdAndOrgId(@Param("taxId") Long taxId, @Param("orgid") long orgid);
    
    @Query("select taxMasEntity.taxDescId from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.taxId=:taxId")
    Long getTaxDescIdByTaxId(@Param("taxId") Long taxId);

    @Query("select taxMasEntity from TbTaxMasEntity taxMasEntity "
            + " where taxMasEntity.taxDesc=:taxDesc and taxMasEntity.orgid=:orgid")
    List<TbTaxMasEntity> getTaxesByDescAndOrgId(@Param("taxDesc") String taxDesc,  @Param("orgid") long orgid);

    @Query("FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId and tm.department.dpDeptid=:deptId"
            + " AND tm.smServiceId=:smServiceId and tm.taxActive='Y' ")
	TbTaxMasEntity getTaxIdByServiceIdOrgIdDeptId(@Param("smServiceId") Long smServiceId, @Param("orgId") long orgid, @Param("deptId") Long deptId);

    @Query("select b.taxDisplaySeq from TbTaxMasEntity b where b.taxId=:taxId")
    Long getDisplaySeqByTaxId(@Param("taxId") Long taxId);
    
    TbTaxMasEntity findByTaxDescIdAndOrgid(@Param("taxDescId") Long taxDescId,@Param("orgId") long orgid);
    
}
