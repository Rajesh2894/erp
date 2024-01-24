package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;

@Repository
public interface AssesmentMstRepository extends JpaRepository<AssesmentMastEntity, Long> {

    @Query("SELECT a.assNo FROM  AssesmentMastEntity a where a.assOldpropno in :oldPropNoList "
            + " and a.assAutStatus='Y' and a.orgId=:orgId group by a.assNo)")
    List<String> getAllPropBillGeneByOldPropNo(@Param("orgId") Long orgId,
            @Param("oldPropNoList") List<String> oldPropNoList);

    @Query("select a from AssesmentMastEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:propNoList) " +
            "and b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SA' group by b.assNo )")
    List<AssesmentMastEntity> getAllPropWithAuthChangeByPropNo(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList);

    @Query("select a from AssesmentMastEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assOldpropno in :oldPropNoList " +
            "and b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SA' group by b.assNo )")
    List<AssesmentMastEntity> getAllPropWithAuthChangeByOldPropNo(@Param("orgId") Long orgId,
            @Param("oldPropNoList") List<String> oldPropNoList);

    @Query("select a from AssesmentMastEntity a " +
            " where (a.assNo,a.proAssId) in ( " +
            " select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in (:propNo )" +
            " and b.faYearId not in (:finId) and b.assAutStatus='Y' and b.assActive='A' " +
            " group by b.assNo ) ")
    List<AssesmentMastEntity> fetchAllAssessmentForBillGneeration(@Param("finId") Long finId,
            @Param("propNo") List<String> propNos);

    @Query("select a from AssesmentMastEntity a,AssessNoticeMasterEntity n " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:propNoList) " +
            "and b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SN' group by b.assNo ) and a.assNo=n.mnAssNo ")
    List<AssesmentMastEntity> getAllSpecNotPropByPropNo(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList);

    @Query("select a from AssesmentMastEntity a,AssessNoticeMasterEntity n " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assOldpropno in(:oldPropNoList ) " +
            "and b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SN' group by b.assNo ) and a.assNo=n.mnAssNo")
    List<AssesmentMastEntity> getAllSpecNotPropByOldPropNo(@Param("orgId") Long orgId,
            @Param("oldPropNoList") List<String> oldPropNoList);

    @Query("select a from AssesmentMastEntity a,NoticeMasterEntity n " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:propNoList) " +
            "and b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SN' group by b.assNo ) and a.assNo=n.refNo ")
    List<AssesmentMastEntity> getAllObjNotPropByPropNo(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList);

    @Query("select a from AssesmentMastEntity a,NoticeMasterEntity n " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assOldpropno in(:oldPropNoList ) " +
            "and b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SN' group by b.assNo ) and a.assNo=n.refNo ")
    List<AssesmentMastEntity> getAllObjNotPropOldPropNo(@Param("orgId") Long orgId,
            @Param("oldPropNoList") List<String> oldPropNoList);

    @Modifying
    @Transactional
    @Query("update AssesmentMastEntity a set a.assStatus=:assStatus where a.orgId=:orgId and  a.assNo in :propNoList")
    void updateAssessFlagOfPropList(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList,
            @Param("assStatus") String assStatus);

    @Modifying
    @Transactional
    @Query("update AssesmentMastEntity a set a.assStatus=:assStatus where a.orgId=:orgId and  a.assNo =:propNo")
    void updateAssessFlagOfProperty(@Param("orgId") Long orgId,
            @Param("propNo") String propNo,
            @Param("assStatus") String assStatus);

    @Query("SELECT m FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId and m.assNo=:propNo "
            + "and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getAssessmentMstByPropNo(@Param("orgId") Long orgId,
            @Param("propNo") String propNo);

    @Query("select a from AssesmentMastEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:assNo) " +
            "and b.orgId=:orgId group by b.assNo )")
    List<AssesmentMastEntity> fetchAssessmentMstByPropNo(@Param("orgId") Long orgId, @Param("assNo") List<String> propId);

    @Query("select a.assPlotArea from AssesmentMastEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:assNo) " +
            "and b.orgId=:orgId group by b.assNo )")
    List<Double> fetchPlotAreaByPropNo(@Param("orgId") Long orgId, @Param("assNo") List<String> propId);

    @Query("select a from AssesmentMastEntity a " + "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:assNo) " +
            "and b.orgId=:orgId and b.faYearId=:faYearId and b.assActive='A' group by b.assNo )  ")
    List<AssesmentMastEntity> fetchAssessmentMstForServicesByPropNo(@Param("orgId") Long orgId, @Param("faYearId") Long faYearId,
            @Param("assNo") List<String> propId);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromAssByPropNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo);

    @Query("select a from AssesmentMastEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo =:assNo " +
            "and b.orgId=:orgId group by b.assNo )")
    AssesmentMastEntity fetchAssessmentMasterByPropNo(@Param("orgId") Long orgId, @Param("assNo") String propNo);

    @Query("select a from AssesmentMastEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assOldpropno =:oldPropNo " +
            "and b.orgId=:orgId group by b.assNo )")
    AssesmentMastEntity fetchAssessmentMasterByOldPropNo(@Param("orgId") Long orgId, @Param("oldPropNo") String oldPropNo);

    @Query("SELECT a FROM AssesmentMastEntity a WHERE (a.assNo,a.proAssId) IN ( " +
            " SELECT B.assNo,MAX(B.proAssId) FROM MainBillMasEntity A,AssesmentMastEntity B " +
            " WHERE A.bmYear=:bmYear AND A.bmPaidFlag='Y' AND A.orgid=:orgid " +
            " AND A.propNo IN (:propNo) and a.assActive='A' " +
            " AND A.propNo=B.assNo " +
            " GROUP BY B.assNo) order by a.proAssId")
    List<AssesmentMastEntity> fetchAssessmentMasterForAmalgamation(@Param("orgid") long orgId,
            @Param("propNo") List<String> propNoList,
            @Param("bmYear") Long finYearId);

    @Query("SELECT a FROM AssesmentMastEntity a WHERE (a.assNo,a.proAssId) IN ( " +
            " SELECT B.assNo,MAX(B.proAssId) FROM MainBillMasEntity A,AssesmentMastEntity B " +
            " WHERE A.bmYear=:bmYear AND A.bmPaidFlag='Y' AND A.orgid=:orgid " +
            " AND  A.propNo IN (SELECT m.assNo FROM AssesmentMastEntity m  " +
            " WHERE m.assOldpropno IN (:assOldpropno)) and  a.assActive='A' " +
            " AND A.propNo=B.assNo " +
            " GROUP BY B.assNo) order by a.proAssId ")
    List<AssesmentMastEntity> fetchAssessmentMasterForAmalgamationByOldProp(@Param("orgid") long orgId,
            @Param("assOldpropno") List<String> oldPropNoList,
            @Param("bmYear") Long finYearId);

    @Query("SELECT a.apmApplicationId FROM AssesmentMastEntity a WHERE (a.assNo,a.proAssId) IN ( " +
            " SELECT B.assNo,MAX(B.proAssId) FROM AssesmentMastEntity B " +
            " WHERE  B.orgId=:orgid and B.smServiceId=:serviceId " +
            " AND  B.assNo IN (:propNo) and  B.assActive='A' " +
            " GROUP BY B.assNo) and a.assAutStatus='AC' "
            + "order by a.proAssId")
    Long getApplicationNoByPropNoForObjection(@Param("orgid") Long orgId,
            @Param("propNo") String propNo, @Param("serviceId") Long serviceId);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromMainAssByPropNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo);
    
    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.tppPlotNo=:houseNo and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromHouseNo(@Param("orgId") Long orgId,
            @Param("houseNo") String houseNo);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.assOldpropno=:oldPropertyNo and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromMainAssByOldPropNo(@Param("orgId") Long orgId,
            @Param("oldPropertyNo") String oldPropertyNo);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE "
            + " m.assNo=:propertyNo and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getPropDetailByPropNoOnly(@Param("propertyNo") String propertyNo);

    @Query("SELECT p FROM  AssesmentMastEntity p WHERE"
            + " p.proAssId in (select max(m.proAssId) from AssesmentMastEntity m where "
            + " m.assNo=:assNo)")
    AssesmentMastEntity fetchPropertyByPropNo(@Param("assNo") String propNo);

    @Query("SELECT p FROM  AssesmentMastEntity p WHERE"
            + " p.proAssId in (select max(m.proAssId) from AssesmentMastEntity m where "
            + " m.uniquePropertyId=:uniquePropertyId)")
    AssesmentMastEntity fetchPropertyByUniquePropId(@Param("uniquePropertyId") String uniquePropertyId);

    @Query("SELECT distinct(apmApplicationId) FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId and m.assNo=:assNo")
    List<Long> fetchApplicationAgainstProperty(@Param("orgId") long orgId, @Param("assNo") String proertyNo);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive=:activeSatus order by proAssId")
    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByPropNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo, @Param("activeSatus") String activeSatus);

    @Query("SELECT count(a) FROM  AssesmentMastEntity a WHERE"
            + " a.assNo=:assNo and a.smServiceId !=:serviceId  and a.orgId=:orgId")
    int getCountOfAssessmentWithoutDES(@Param("assNo") String propNo, @Param("orgId") Long orgId,
            @Param("serviceId") Long serviceId);

    @Query("SELECT m FROM  AssesmentOwnerDtlEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertryNo and m.assoOType='P' and m.assoActive='A'")
    List<AssesmentOwnerDtlEntity> getPrimaryOwnerDetailByPropertyNo(@Param("orgId") long orgId,
            @Param("propertryNo") String propertryNo);

    @Query("SELECT m FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId and m.apmApplicationId=:applicationId "
            + "and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getAssessmentMstListByApplicationId(@Param("orgId") Long orgId,
            @Param("applicationId") Long applicationId);

    @Query("SELECT m FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId and m.proAssId in(:assIdList) "
            + "and m.assActive='A' order by proAssId")
    List<AssesmentMastEntity> getAssessmentMstListByAssIds(@Param("orgId") Long orgId,
            @Param("assIdList") List<Long> assIdList);

    @Query(value = "select * from TB_AS_ASSESMENT_MAST r , tb_financialyear q where MN_ASS_id in (\r\n" +
            "select max(MN_ASS_id) from TB_AS_ASSESMENT_MAST   p  where p.orgid=:orgId \r\n" +
            "group by  p.MN_ASS_no) and  r.fa_yearid = q.FA_YEARID and q.FA_YEARID <> (SELECT FA_YEARID\r\n" +
            "  FROM   tb_financialyear WHERE  now()  BETWEEN FA_FROMDATE AND FA_TODATE)\r\n" +
            "  AND MN_ASS_no NOT IN (select PRO_ASS_NO from TB_AS_PRO_ASSESMENT_MAST t , tb_financialyear w where PRO_ASS_ID in (\r\n"
            +
            "select max(PRO_ASS_ID) from TB_AS_PRO_ASSESMENT_MAST   s  where s.orgid=:orgId \r\n" +
            "group by s.PRO_ASS_NO) and  t.fa_yearid = w.FA_YEARID and w.FA_YEARID = (SELECT FA_YEARID\r\n" +
            "  FROM   tb_financialyear WHERE  now()  BETWEEN FA_FROMDATE AND FA_TODATE))\r\n" +
            "union  \r\n" +
            "  select * from TB_AS_PRO_ASSESMENT_MAST t , tb_financialyear w where PRO_ASS_ID in (\r\n" +
            "select max(PRO_ASS_ID) from TB_AS_PRO_ASSESMENT_MAST   s  where s.orgid=:orgId \r\n" +
            "group by s.PRO_ASS_NO) and  t.fa_yearid = w.FA_YEARID and w.FA_YEARID <> (SELECT FA_YEARID\r\n" +
            "  FROM   tb_financialyear WHERE  now()  BETWEEN FA_FROMDATE AND FA_TODATE)", nativeQuery = true)
    List<AssesmentMastEntity> getAssMstListForProvisionalDemandByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT c.assActive FROM AssesmentMastEntity c WHERE c.assNo =:assNo and c.orgId =:orgId")
    List<String> checkActiveFlag(@Param("assNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT c.assActive FROM AssesmentMastEntity c WHERE c.assOldpropno =:OldPropNo and c.orgId =:orgId")
    List<String> checkActiveFlagByOldPropNo(@Param("OldPropNo") String OldPropNo, @Param("orgId") Long orgId);

    @Query("SELECT c.proAssId FROM AssesmentMastEntity c WHERE c.assNo =:assNo and c.orgId =:orgId")
    Long fetchAssdIdbyPropNo(@Param("assNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT c.proAssId FROM AssesmentMastEntity c WHERE c.assNo =:assNo and c.orgId =:orgId")
    List<Long> fetchAssdIdListbyPropNo(@Param("assNo") String propNo, @Param("orgId") Long orgId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update AssesmentMastEntity a set a.assActive=:assActive where a.orgId=:orgId and  a.assNo =:propNo")
    void updateActiveFlagOfProperty(@Param("orgId") Long orgId,
            @Param("propNo") String propNo,
            @Param("assActive") String assActive);

    @Query("SELECT count(*) FROM  AssesmentMastEntity m  WHERE m.assNo=:propNo and m.orgId=:orgId and m.assActive='A'")
    Long getCountBypropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT count(*) FROM  AssesmentMastEntity m  WHERE m.assOldpropno=:oldpropno and m.orgId=:orgId and m.assActive='A'")
    Long getCountByOldPropNo(@Param("oldpropno") String oldpropno, @Param("orgId") Long orgId);

    @Modifying(clearAutomatically = true)
    // @Transactional
    @Query("update AssesmentMastEntity a set a.assOwnerType=:assOwnerType,a.updatedBy=:empId,"
            + "a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.assNo =:propNo ")
    int updateAssesmentDetails(@Param("orgId") Long orgId,
            @Param("propNo") String propNo,
            @Param("assOwnerType") Long assOwnerType, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentMastEntity a set a.assOwnerType=:assOwnerType,a.updatedBy=:empId,"
            + "a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE, a.assAddress=:assAddress, a.locId=:locId, a.assPincode=:assPincode,a.assOldpropno=:oldPropNo, a.tppPlotNo=:houseNo,a.newHouseNo=:newHouseNo, a.updateDataEntryFlag=:updateDataEntryFlag"
            + " where a.orgId=:orgId and  a.assNo =:propNo ")
    int updateDataEntryDetails(@Param("orgId") Long orgId,
            @Param("propNo") String propNo,
            @Param("assOwnerType") Long assOwnerType, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress,
            @Param("assAddress") String assAddress,
            @Param("locId") Long locId, @Param("assPincode") Long assPincode,
            @Param("updateDataEntryFlag") String updateDataEntryFlag, @Param("oldPropNo") String oldPropNo,
            @Param("houseNo") String houseNo,@Param("newHouseNo") String newHouseNo);

    @Query("SELECT c.receiptDelFlag FROM TbServiceReceiptMasEntity c WHERE c.rmRcptid in (select max(c.rmRcptid) FROM TbServiceReceiptMasEntity c where c.additionalRefNo =:assNo and c.orgId =:orgId )")
    String checkReceiptDelFlag(@Param("assNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE "
            + " m.assNo=:propertyNo order by proAssId")
    List<AssesmentMastEntity> getPropDetailByPropNoWithoutActiveCond(@Param("propertyNo") String propertyNo);

    @Query("SELECT c.updateDataEntryFlag FROM AssesmentMastEntity c WHERE c.assNo =:assNo and c.orgId =:orgId")
    List<String> fetchUpdateDataEntryFlag(@Param("assNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive='A' and m.logicalPropNo=:logicalPropNo order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromMainAssByPropNoByFlatNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo, @Param("logicalPropNo") String logicalPropNo);

    @Modifying
    @Transactional
    @Query("update AssesmentMastEntity a set a.splNotDueDate=:splNotDueDate where a.orgId=:orgId and  a.assNo in :propNoList")
    void updateAssessSplNoticeDueDate(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList,
            @Param("splNotDueDate") Date splNotDueDate);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity own set own.assoMobileno=:mobileNo, own.eMail=:emailId where own.proAssoId=:ownerId and own.assoOType='P'")
    void updatemobNoAndEmailIdInOwner(@Param("mobileNo") String mobileNo, @Param("emailId") String emailId,
            @Param("ownerId") Long ownerId);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentDetailEntity det set det.occupierMobNo=:mobileNo, det.occupierEmail=:emailId where det.proAssdId=:detailId")
    void updatemobNoAndEmailIdInDetail(@Param("mobileNo") String mobileNo, @Param("emailId") String emailId,
            @Param("detailId") Long detailId);

    @Query(value = "select a.LM_LANDRT from tb_area_land_rate a  where a.PM_SURVEY_NUMBER=:surveryNo AND a.AS_AREADESC =:areaName", nativeQuery = true)
    Double fetchLandRate(@Param("surveryNo") String surveyNo, @Param("areaName") String areaName);

    /*
     * @Modifying
     * @Transactional
     * @Query("update AssesmentMastEntity a set a.groupPropNo=:groupPropNo,a.parentPropNo=:parentPropNo where a.assNo in :PropNoList"
     * ) void updateGroupPropNo(@Param("groupPropNo") String groupPropNo, @Param("parentPropNo") String parentPropNo,
     * @Param("PropNoList") List<String> propNoList);
     * @Modifying
     * @Transactional
     * @Query("update AssesmentMastEntity a set a.groupPropNo=null,a.parentPropNo=null where a.assNo in :PropNoList" ) void
     * inactiveGroupPropNo(@Param("PropNoList") List<String> propNoList);
     * @Modifying
     * @Query("update TbCfcApplicationMstEntity am set am.apmAppRejFlag =:approvalStatus ,am.apmApprovedBy =:approvedBy where am.apmApplicationId =:applicationId"
     * ) void updateAgencyApprovalWorkflow(@Param("approvalStatus") String approvalStatus,
     * @Param("approvedBy") Long approvedBy, @Param("applicationId") Long applicationId);
     */

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive='A' and m.flatNo=:flatNo order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromAssByPropNoFlatNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo, @Param("flatNo") String flatNo);

    @Query("SELECT p FROM  AssesmentMastEntity p WHERE"
            + " p.proAssId in (select max(m.proAssId) from AssesmentMastEntity m where "
            + " m.assNo=:assNo and m.flatNo=:flatNo and m.orgId=:orgId )")
    AssesmentMastEntity fetchPropertyByPropNoAndFlatNo(@Param("assNo") String assNo, @Param("flatNo") String flatNo,
            @Param("orgId") long orgId);

    @Query("SELECT distinct(apmApplicationId) FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId and m.assNo=:assNo and m.flatNo=:flatNo")
    List<Long> fetchApplicationAgainstPropertyWithFlatNo(@Param("orgId") long orgId, @Param("assNo") String proertyNo,
            @Param("flatNo") String flatNo);

    @Query("SELECT a.apmApplicationId FROM AssesmentMastEntity a WHERE (a.assNo,a.proAssId) IN ( " +
            " SELECT B.assNo,MAX(B.proAssId) FROM AssesmentMastEntity B " +
            " WHERE  B.orgId=:orgid and B.smServiceId=:serviceId " +
            " AND  B.logicalPropNo IN (:propNo) and  B.assActive='A' " +
            " GROUP BY B.assNo) and a.assAutStatus='AC' "
            + "order by a.proAssId")
    Long getApplicationNoByLogicalPropNoForObjection(@Param("orgid") Long orgId,
            @Param("propNo") String propNo, @Param("serviceId") Long serviceId);

    @Query(value = "select * from TB_AS_ASSESMENT_MAST r , tb_financialyear q where MN_ASS_id in (\r\n" +
            "select max(MN_ASS_id) from TB_AS_ASSESMENT_MAST   p  where p.orgid=:orgId and p.MN_ASS_NO in (:propNoList)\r\n" +
            "group by  p.MN_ASS_no) and  r.fa_yearid = q.FA_YEARID and q.FA_YEARID <> (SELECT FA_YEARID\r\n" +
            "  FROM   tb_financialyear WHERE  now()  BETWEEN FA_FROMDATE AND FA_TODATE)\r\n" +
            "  AND MN_ASS_no NOT IN (select PRO_ASS_NO from TB_AS_PRO_ASSESMENT_MAST t , tb_financialyear w where PRO_ASS_ID in (\r\n"
            +
            "select max(PRO_ASS_ID) from TB_AS_PRO_ASSESMENT_MAST   s  where s.orgid=:orgId and s.PRO_ASS_NO in (:propNoList)\r\n"
            +
            "group by s.PRO_ASS_NO) and  t.fa_yearid = w.FA_YEARID and w.FA_YEARID = (SELECT FA_YEARID\r\n" +
            "  FROM   tb_financialyear WHERE  now()  BETWEEN FA_FROMDATE AND FA_TODATE))\r\n" +
            "union  \r\n" +
            "  select * from TB_AS_PRO_ASSESMENT_MAST t , tb_financialyear w where PRO_ASS_ID in (\r\n" +
            "select max(PRO_ASS_ID) from TB_AS_PRO_ASSESMENT_MAST   s  where s.orgid=:orgId and s.PRO_ASS_NO in (:propNoList) \r\n"
            +
            "group by s.PRO_ASS_NO) and  t.fa_yearid = w.FA_YEARID and w.FA_YEARID <> (SELECT FA_YEARID\r\n" +
            "  FROM   tb_financialyear WHERE  now()  BETWEEN FA_FROMDATE AND FA_TODATE)", nativeQuery = true)
    List<AssesmentMastEntity> getAssMstListForProvisionalDemandByOrgIdAndPropNoList(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList);

    @Query("select a from AssesmentMastEntity a " + "where (a.assNo,a.proAssId) in ( "
            + "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where b.assNo in(:assNo) "
            + "and b.orgId=:orgId  and b.assActive='A' group by b.assNo )  ")
    List<AssesmentMastEntity> fetchAssessmentMstForServicesByPropNoOnly(@Param("orgId") Long orgId,
            @Param("assNo") List<String> propId);

    @Query("select a from AssesmentMastEntity a " + "where (a.assOldpropno,a.proAssId) in ( "
            + "select  b.assOldpropno,MAX(b.proAssId) from AssesmentMastEntity b where b.assOldpropno in(:assOldpropno) "
            + "and b.orgId=:orgid  and b.assActive='A' group by b.assOldpropno )  ")
    List<AssesmentMastEntity> fetchAssessmentMasterForAmalgamationByOldPropOnly(@Param("orgid") long orgId,
            @Param("assOldpropno") List<String> oldPropNoList);

    @Query("SELECT m FROM  AssesmentOwnerDtlEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertryNo  and m.assoActive='A'")
    List<AssesmentOwnerDtlEntity> getOwnerDetailsByPropertyNo(@Param("orgId") long orgId,
            @Param("propertryNo") String propertryNo);

    @Modifying
    @Query("update AssesmentDetailEntity a set a.occupierName=:occupierName,a.occupierNameReg=:occupierNameReg,a.occupierMobNo=:occupierMobNo,a.occupierEmail=:occupierEmail,a.updatedBy=:empId,"
            + " a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.proAssdId in (:proAssdId) ")
    void updateAssesmentDetailtableOwners(@Param("orgId") Long orgId, @Param("occupierName") String occupierName,
            @Param("occupierNameReg") String occupierNameReg, @Param("occupierMobNo") String occupierMobNo,
            @Param("occupierEmail") String occupierEmail, @Param("empId") Long empId,
            @Param("proAssdId") List<Long> proAssdId, @Param("clientIpAddress") String clientIpAddress);

    @Query("SELECT m FROM  AssesmentMastEntity m WHERE m.orgId=:orgId and"
            + " m.assOldpropno =:assOldpropno and m.assActive='A' and m.flatNo=:flatNo order by proAssId")
    List<AssesmentMastEntity> getPropDetailFromAssByOldPropNoNFlatNo(@Param("orgId") long orgId,
            @Param("assOldpropno") String assOldpropno, @Param("flatNo") String flatNo);

    @Query("SELECT c.assActive FROM AssesmentMastEntity c WHERE c.assOldpropno =:OldPropNo and  c.flatNo =:flatNo and c.orgId =:orgId")
    List<String> checkActiveFlagByOldPropNoNFlatNo(@Param("OldPropNo") String OldPropNo, @Param("flatNo") String flatNo,
            @Param("orgId") Long orgId);

    @Query("SELECT c.assActive FROM AssesmentMastEntity c WHERE c.assNo =:assNo and  c.flatNo =:flatNo and c.orgId =:orgId")
    List<String> checkActiveFlagByPropNFlatNO(@Param("assNo") String propNo, @Param("flatNo") String flatNo,
            @Param("orgId") Long orgId);

    @Modifying
    @Query("update AssesmentMastEntity a set a.assOwnerType=:assOwnerType,a.updatedBy=:empId,"
            + " a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.assNo =:propNo and a.flatNo=:flatNo")
    int updateAssesmentDetailsWithFlat(@Param("orgId") Long orgId, @Param("propNo") String propNo,
            @Param("assOwnerType") Long assOwnerType, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress, @Param("flatNo") String flatNo);

    @Modifying
    @Query("update AssesmentMastEntity a set a.assActive=:assActive  where  a.assNo =:assNo and a.orgId=:orgId ")
    void updateAssessmentMasterStatus(@Param("assNo") String assNo, @Param("assActive") String assActive,
            @Param("orgId") Long orgId);

    @Query("SELECT c.assNo FROM AssesmentMastEntity c WHERE c.assOldpropno =:oldPropNo and c.orgId =:orgId")
    String getPropNoByOldPropNo(@Param("oldPropNo") String oldPropNo, @Param("orgId") Long orgId);

    @Query("select a from AssesmentMastHistEntity a  where (a.assNo,a.mnAssHisId) in ( "
            + " select  b.assNo,MAX(b.mnAssHisId) from AssesmentMastHistEntity b where b.assNo =:assNo "
            + " and b.orgId=:orgId group by b.assNo )")
    AssesmentMastHistEntity fetchAssessmentHistMasterByPropNo(@Param("orgId") Long orgId,
            @Param("assNo") String propNo);

    @Query("select a from AssesmentMastHistEntity a  where (a.assNo,a.mnAssHisId) in ( "
            + " select  b.assNo,MAX(b.mnAssHisId) from AssesmentMastHistEntity b where b.assNo =:assNo "
            + " and b.flatNo=:flatNo and b.orgId=:orgId  group by b.assNo )")
    AssesmentMastHistEntity fetchAssessmentHistMasterByPropNoNFlatNo(@Param("orgId") Long orgId,
            @Param("assNo") String propNo, @Param("flatNo") String flatNo);

    @Query("select b from AssesmentOwnerDtlHistEntity b  where b.apmApplicationId=:apmApplicationId and b.orgId=:orgId )")
    List<AssesmentOwnerDtlHistEntity> fetchAssOwnerHistByPropNoNApplId(@Param("orgId") Long orgId,
            @Param("apmApplicationId") Long apmApplicationId);

    @Query("select a from AssesmentDetailHistEntity a  where  a.apmApplicationId=:apmApplicationId and a.orgId=:orgId  ")
    List<AssesmentDetailHistEntity> fetchAssDetailHistByApplicationId(@Param("orgId") Long orgId,
            @Param("apmApplicationId") Long apmApplicationId);

    @Query("SELECT distinct(groupPropName) FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId  and m.assActive='A'  and m.isGroup='Y' ")
    List<String> fetchAssessmentByGroupPropNo(@Param("orgId") Long orgId);

    @Query("SELECT distinct(parentPropName) FROM  AssesmentMastEntity m  WHERE m.orgId=:orgId  and m.assActive='A' and m.isGroup='Y' ")
    List<String> fetchAssessmentByParentPropNo(@Param("orgId") Long orgId);
    
    @Query("select b from AssesmentOwnerDtlHistEntity b  where b.mnAssId=:mnAssId and b.orgId=:orgId order by b.proAssoHisId desc")
    List<AssesmentOwnerDtlHistEntity> fetchAssOwnerHistory(@Param("orgId") Long orgId,
            @Param("mnAssId") Long mnAssId);
    
    @Query("SELECT distinct(assNo) FROM  AssesmentMastEntity m  WHERE m.parentPropNo=:parentPropNo  and m.assActive='A'")
    List<String> fetchPropertyNosByParentPropNo(@Param("parentPropNo") String parentPropNo);
    
    @Query("select assd from AssesmentDetailEntity assd where assd.mnAssId in (select proAssId from AssesmentMastEntity where parentPropNo=:parentPropNo)")
    List<AssesmentDetailEntity> getassesmentDetailListByParentPropNo(@Param("parentPropNo") String parentPropNo);
    
    @Query("select a from AssesmentMastEntity a where a.parentPropNo=:parentPropNo")
    List<AssesmentMastEntity> getassesmentMasterListByParentPropNo(@Param("parentPropNo") String parentPropNo);

    @Query("SELECT a.assAddress from AssesmentMastEntity a  where a.assNo=:propNo")
    String getAddressByPropNo(@Param("propNo") String propNo);
    
    @Query("SELECT b.assoOwnerName from AssesmentOwnerDtlEntity b  where b.mnAssId in (select proAssId from AssesmentMastEntity  where assNo =:propNo)")
    String getOwnerByPropNo(@Param("propNo") String propNo);
    
    @Query("SELECT count(b) from AssesmentDetailEntity b  where b.mnAssId in (select proAssId from AssesmentMastEntity  where assNo =:propNo)")
    Long getdetailCountPropNo(@Param("propNo") String propNo);
    
    @Query("select a from AssesmentMastEntity a where a.faYearId in(:finYearId) and a.assNo=:propNo and a.assActive='A'")
    List<AssesmentMastEntity> getCurrentYearAssesment(@Param("finYearId") List<Long> finYearId, @Param("propNo") String propertyNo);
    
    @Query("SELECT count(*) FROM  AssesmentMastEntity m  WHERE m.assOldpropno=:oldPropNo  and m.assActive='A'")
    Long getOldPropNoCount(@Param("oldPropNo") String oldPropNo);
    
    
    @Query("select a from AssesmentFactorDetailHistEntity a  where  a.mnAssdId=:mnAssdId")
    List<AssesmentFactorDetailHistEntity> getAssesmentFactorHistById(@Param("mnAssdId") Long mnAssId);
    
    @Query("select a from AssesmentMastEntity a where  a.assNo=:propNo and a.assActive='A'")
    List<AssesmentMastEntity> getAllAssesment(@Param("propNo") String propertyNo);
    
    @Query("select a from AssesmentMastHistEntity a where a.faYearId=:finYearId and a.assNo=:propNo and a.apmApplicationId=:applicationId and a.assActive='A'")
    AssesmentMastHistEntity getCurrentYearAssesmentHistByFinIdAndApplId(@Param("finYearId") Long finYearId, @Param("propNo") String propertyNo, @Param("applicationId") Long applicationId);
    
    @Query("select a from AssesmentDetailHistEntity a where  a.proAssMastHisId=:proAssMastHisId")
    List<AssesmentDetailHistEntity> getAssesmentDetailHistByMnassId(@Param("proAssMastHisId") Long proAssMastHisId);
    
    @Query("select a from AssesmentOwnerDtlHistEntity a where  a.proAssMastHisId=:proAssMastHisId")
    List<AssesmentOwnerDtlHistEntity> getAssesmentOwnerHistByMnassId(@Param("proAssMastHisId") Long proAssMastHisId);
    
    @Query("SELECT c.assNo FROM AssesmentMastEntity c WHERE c.assOldpropno =:oldPropNo")
    String getPropNoByOldPropNoWithoutOrgId(@Param("oldPropNo") String oldPropNo);
    
    @Query("SELECT c.orgId FROM AssesmentMastEntity c WHERE c.assNo =:assNo")
    Long getOrgIdByPropertyNo(@Param("assNo") String propNo);
    
    @Query("SELECT m.orgId FROM  AssesmentMastEntity m WHERE m.proAssId in (SELECT max(p.proAssId) FROM  AssesmentMastEntity p WHERE p.assNo=:propNo )")
    Long fetchOrgId(@Param("propNo") String propNo);
    
    @Query("SELECT m.smServiceId FROM  AssesmentMastEntity m WHERE m.proAssId in (SELECT max(p.proAssId) FROM  AssesmentMastEntity p WHERE p.assNo=:propNo )")
    Long fetchServiceId(@Param("propNo") String propNo);
    
    @Query("SELECT m.assWard1, m.assWard2 FROM  AssesmentMastEntity m WHERE m.proAssId in (SELECT max(p.proAssId) FROM  AssesmentMastEntity p WHERE p.assNo=:propNo )")
    Object[] fetchWardZoneId(@Param("propNo") String propNo);

    @Query(value ="select (select cod_desc from tb_comparent_det where cod_id=MN_assd_usagetype1) type1,\r\n" + 
    		"count(distinct MN_ASS_no) cnts from tb_as_assesment_mast x,tb_as_assesment_detail y\r\n" + 
    		"where x.MN_ass_id=y.MN_ass_id and x.orgid=:orgId and x.updated_date=:dateN \r\n" + 
    		"group by MN_assd_usagetype1",nativeQuery=true)  
	List<Object[]> getAssessedProperties(@Param("orgId") Long orgId, @Param("dateN") String dateSet);

	 @Query(value ="select (select concat(year(fa_fromdate),'-',date_format(fa_todate,\"%y\")) from tb_financialyear where  fa_yearid=x.fa_yearid) yr,\r\n" +
	 		"count(distinct MN_ASS_no) from tb_as_assesment_mast x where x.orgid=:orgId group by fa_yearid, orgid",nativeQuery=true)  
	List<Object[]> getPropertiesRegisteredList(@Param("orgId") Long orgId);

	
	 @Query(value ="select PD_USAGETYPE1,sum(rd_amount),count(1)  from (\r\n" + 
	 		"select b.orgid,sum(rd_amount) rd_amount,(select group_concat(distinct (select cod_desc from \r\n" + 
	 		"tb_comparent_det where cod_id=PD_USAGETYPE1)) \r\n" + 
	 		"from  tb_as_prop_mas a,tb_as_prop_det b where a.PM_PROPID=b.PM_PROPID \r\n" + 
	 		"and a.PM_PROP_NO=b.ADDITIONAL_REF_NO) PD_USAGETYPE1,count(1) \r\n" + 
	 		"from tb_receipt_mode a,tb_receipt_mas b where a.rm_rcptid=b.rm_rcptid and dp_deptid=:deptId\r\n" + 
	 		" and date(rm_date)=:dateN \r\n" + 
	 		"and b.orgid=:orgId group by PD_USAGETYPE1,b.orgid) z\r\n" + 
	 		"group by PD_USAGETYPE1,orgid",nativeQuery=true)  
	 List<Object[]> getTransactionsProp(@Param("orgId") Long orgId, @Param("deptId") Long deptId, @Param("dateN") String dateSet);
	 
	 @Query(value ="select (select cpd_desc from tb_comparam_det where cpd_id=CPD_FEEMODE) modes,sum(rd_amount) rd_amount,count(1) \r\n" + 
	 		"from tb_receipt_mode a,tb_receipt_mas b where a.rm_rcptid=b.rm_rcptid and dp_deptid=:deptId\r\n" + 
	 		" and date(rm_date)=:dateN\r\n" + 
	 		"and b.orgid=:orgId group by modes,b.orgid",nativeQuery=true) 
	 List<Object[]> getPaymentModeWiseColln(@Param("orgId") Long orgId,@Param("deptId")  Long deptId, @Param("dateN") String dateSet);
	 
	
}
