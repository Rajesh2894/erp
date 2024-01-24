package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.MainBillDetHistEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.MainBillMasHistEntity;

public interface PropertyMainBillRepository extends JpaRepository<MainBillMasEntity, Long> {

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo and am.bmPaidFlag=:bmPaidFlag order by bmIdno ")
    List<MainBillMasEntity> fetchNotPaidBillForAssessment(@Param("propNo") String assNo, @Param("orgId") long orgId,
            @Param("bmPaidFlag") String paidFlag);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo order by bmIdno ")
    List<MainBillMasEntity> fetchAllBillByPropNo(@Param("propNo") String assNo, @Param("orgId") long orgId);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo and am.assId in(:assIds) order by bmIdno ")
    List<MainBillMasEntity> fetchAllBillByPropNoAndAssIds(@Param("propNo") String assNo, @Param("assIds") List<Long> assIds,
            @Param("orgId") long orgId);

    @Query("from MainBillMasEntity "
            + "  m  WHERE (propNo,bmIdno) in (select propNo ,max(bmIdno) from MainBillMasEntity "
            + "where  bmGenDes is null and assId is null group by propNo ) and propNo=:propNo  and orgid=:orgId  ")
    List<MainBillMasEntity> fetchBillByPropNo(@Param("propNo") String assNo, @Param("orgId") long orgId);

    @Query("select m.bmFromdt from MainBillMasEntity "
            + "  m  WHERE (propNo,bmIdno) in (select propNo ,max(bmIdno) from MainBillMasEntity "
            + "where  bmPaidFlag='Y' group by propNo ) and propNo=:propNo and orgid=:orgId  ")
    Date fetchLastPaidBillFromDate(@Param("propNo") String assNo, @Param("orgId") long orgId);

    /*
     * @Query("select count(m) from MainBillMasEntity " +
     * "  m  WHERE bmPaidFlag='Y' and propNo=:propNo and orgid=:orgId and bmYear=:finYearId  ") int
     * getCountOfBillByPropNoAndCurFinId(@Param("propNo") String assNo, @Param("finYearId") Long finYearId,
     * @Param("orgId") long orgId);
     */

    @Query("select count(m) from MainBillMasEntity "
            + "  m  WHERE bmPaidFlag='Y' and orgid=:orgId and bmYear=:finYearId and propNo=:propNo")
    int getCountOfBillByOldPropNoOrPropNoAndFinId(@Param("propNo") String propNo,
            @Param("finYearId") Long finYearId,
            @Param("orgId") long orgId);

    /*
     * @Query("select m from MainBillMasEntity " +
     * "  m  WHERE (propNo,bmIdno) in (select a.propNo ,a.max(bmIdno) as bmIdno from (select bmIdno as bmIdno ,propNo as propNo from ProvisionalBillMasEntity union"
     * + " select bmIdno,propNo from MainBillMasEntity ) a  " + " group by a.propNo ) and propNo=:propNo and orgid=:orgId ")
     * List<MainBillMasEntity> fetchMainBillIfItExistMaxBillByPropNo(@Param("propNo") String assNo, @Param("orgId") long orgId);
     */

    @Query("select m.propNo,max(m.bmIdno) from MainBillMasEntity m " +
            " where m.propNo in (:porpNo) " +
            "group by  m.propNo")
    List<Object[]> fetchCurrentBillByPropNo(@Param("porpNo") List<String> propNo);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.bmIdno in (:bmIdno)")
    List<MainBillMasEntity> fetchBillsFromBmIdNo(@Param("bmIdno") List<Long> billId);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.bmIdno =:bmIdno")
    MainBillMasEntity fetchBillFromBmIdNo(@Param("bmIdno") Long bmIdno);

    @Query("from MainBillMasEntity "
            + "  am  WHERE am.propNo=:propNo order by bmIdno ")
    List<MainBillMasEntity> fetchBillSForViewProperty(@Param("propNo") String propNo);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.bmNo=:bmIdno")
    MainBillMasEntity fetchBillSForView(long bmIdNo);

    @Query("select count(am) from MainBillMasEntity "
            + "  am  WHERE  am.propNo=:propNo and am.orgid=:orgId and am.bmGenDes is  null")
    int getCountOfBillWithoutDESByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("select count(am) from MainBillMasEntity "
            + "  am  WHERE  am.propNo in (:propNoList) and am.orgid=:orgId and am.bmYear=:bmYear and am.bmPaidFlag='N'")
    int getPaidBillCountByPropNoList(@Param("propNoList") List<String> propNoList, @Param("orgId") Long orgId,
            @Param("bmYear") Long bmYear);

    @Query("from MainBillMasEntity am  WHERE  am.propNo=:propNo and am.bmNo=:billNo and am.orgid=:orgId")
    List<MainBillMasEntity> fetchBillByBillNoAndPropertyNo(@Param("propNo") String propNo, @Param("billNo") String billNo,
            @Param("orgId") Long orgId);

    @Query("select count(am) from MainBillMasEntity "
            + "  am  WHERE  am.propNo in (:propNo) and am.orgid=:orgId and am.bmYear=:bmYear and am.bmPaidFlag='N'")
    int getPaidBillCountByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId,
            @Param("bmYear") Long bmYear);

    @Query("from MainBillMasEntity am  WHERE  am.orgid=:orgId and am.propNo in(:propNo))")
    List<MainBillMasEntity> fetchNotPaidBillsByPropNo(@Param("propNo") List<String> assNo, @Param("orgId") long orgId);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo and am.bmPaidFlag=:bmPaidFlag and am.flatNo=:flatNo order by bmIdno ")
    List<MainBillMasEntity> fetchNotPaidBillForAssessmentByFlatNo(@Param("propNo") String assNo, @Param("orgId") long orgId,
            @Param("bmPaidFlag") String paidFlag, @Param("flatNo") String flatNo);

    @Query("from MainBillMasEntity  am  WHERE am.propNo=:propNo and am.flatNo=:flatNo and am.orgid=:orgid order by bmIdno ")
    List<MainBillMasEntity> fetchBillSForViewPropertyByPropAndFlatNo(@Param("propNo") String propNo,
            @Param("flatNo") String flatNo, @Param("orgid") long orgid);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo and am.flatNo=:flatNo order by bmIdno ")
    List<MainBillMasEntity> fetchAllBillByPropNoAndFlatNo(@Param("propNo") String assNo, @Param("flatNo") String flatNo,
            @Param("orgId") long orgId);

    @Modifying
    @Query("update MainBillMasEntity a set a.billDistrDate=:serveDate,a.bmDuedate=:dueDate where a.bmIdno=:bmIdNo")
    void updateBillServeDateAndDueDate(@Param("serveDate") Date servedate, @Param("dueDate") Date dueDate,
            @Param("bmIdNo") Long bmIdNo);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo  and am.bmYear >=:finId order by bmIdno ")
    List<MainBillMasEntity> fetchAllBillByPropNoAndFinId(@Param("propNo") String assNo, @Param("orgId") long orgId,
            @Param("finId") Long finId);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.parentPropNo=:parentPropNo and am.bmPaidFlag=:bmPaidFlag order by bmIdno ")
    List<MainBillMasEntity> fetchNotPaidBillForAssessmentByParentPropNo(@Param("parentPropNo") String assNo,
            @Param("orgId") long orgId,
            @Param("bmPaidFlag") String paidFlag);

    @Query("from MainBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.propNo=:propNo and am.bmYear=:finId order by bmIdno ")
    List<MainBillMasEntity> fetchAllBillByPropNoAndCurrentFinId(@Param("propNo") String assNo, @Param("orgId") long orgId,
            @Param("finId") Long finId);

    /*
     * @Modifying
     * @Transactional
     * @Query("update MainBillMasEntity a set a.groupPropNo=:groupPropNo,a.parentPropNo=:parentPropNo where a.propNo in :PropNoList"
     * ) void updateGroupPropNo(@Param("groupPropNo") String groupPropNo, @Param("parentPropNo") String parentPropNo,
     * @Param("PropNoList") List<String> propNo);
     * @Modifying
     * @Transactional
     * @Query("update MainBillMasEntity a set a.groupPropNo=null,a.parentPropNo=null where a.propNo in :PropNoList" ) void
     * inactiveGroupPropNo(@Param("PropNoList") List<String> propNo);
     */

    @Query("select m.propNo, m.bmTotalOutstanding,m.bmYear,m.bmDuedate from MainBillMasEntity "
            + "  m  WHERE (propNo,bmIdno) in (select propNo ,max(bmIdno) from MainBillMasEntity "
            + "where propNo in (:propNo)  and orgid=:orgId  group by propNo)")
    List<Object[]> fetchAllBillByPropNoBetweenOutStanding(@Param("propNo") List<String> assNo,
            @Param("orgId") long orgId);

    @Query("select count(am) from MainBillMasEntity "
            + "  am  WHERE  am.propNo in (:propNo) and am.orgid=:orgId and am.bmYear=:bmYear and am.flatNo=:flatNo and am.bmPaidFlag='N'")
    int getPaidBillCountByPropNoAndFlatNo(@Param("propNo") String propNo, @Param("orgId") Long orgId,
            @Param("bmYear") Long bmYear, @Param("flatNo") String flatNo);
    
    @Query("select count(am) from MainBillMasEntity "
            + "  am  WHERE  am.propNo =:propNo and am.orgid=:orgId and am.bmYear=:bmYear and am.flatNo=:flatNo")
    int getBillExistByPropNoFlatNoAndYearId(@Param("propNo") String propNo, @Param("orgId") Long orgId,
            @Param("bmYear") Long bmYear, @Param("flatNo") String flatNo);
    
    @Query("select count(am) from MainBillMasEntity "
            + "  am  WHERE  am.propNo =:propNo and am.orgid=:orgId and am.bmYear=:bmYear")
    int getBillExistByPropNoAndYearId(@Param("propNo") String propNo, @Param("orgId") Long orgId, @Param("bmYear") Long bmYear);
    
    @Query("select max(m.bmYear) from  MainBillMasEntity m where m.propNo=:propNo and m.orgid=:orgId")
    Long getMaxFinYearIdByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);
    
    @Query("select m from  MainBillMasHistEntity m where m.propNo=:propNo and m.apmApplicationId=:applicationId")
    List<MainBillMasHistEntity> getMainBillHistoryByApplicationId(@Param("propNo") String propNo, @Param("applicationId") Long applicationId);
    
    @Query("select m from  MainBillDetHistEntity m where m.bmIdnoHistId=:bmIdnoHistId and m.bmIdno=:bmIdNo and m.apmApplicationId=:applicationId")
    List<MainBillDetHistEntity> getMainBillDetHistByBmIdNo(@Param("bmIdnoHistId") Long bmIdnoHistId, @Param("bmIdNo") Long bmIdno, @Param("applicationId") Long applicationId);
    
    @Modifying
    @Query("delete from MainBillDetEntity c WHERE c.bdBilldetid in(:detId)")
    void deleteBillDet(@Param("detId") List<Long> detIdList);
    
    @Query("select min(m.bmIdno) from  MainBillMasEntity m where m.propNo=:propNo")
    Long getFirstBmIdNoByPropNo(@Param("propNo") String propNo);
    
    @Query("select count(m) from  MainBillMasEntity m where m.propNo=:propNo and m.bmYear in (:bmYear)")
	Long getCountOfBillExistYearByPropNo(@Param("propNo") String propNo, @Param("bmYear") List<Long> bmYear);
	
	@Query("select count(m) from  MainBillMasEntity m where m.propNo=:propNo and m.flatNo=:flatNo and m.bmYear in(:bmYear)")
	Long getCountOfBillExistYearByPropNoAndFlatNo(@Param("propNo") String propNo,@Param("flatNo") String flatNo, @Param("bmYear") List<Long> bmYear);
}
