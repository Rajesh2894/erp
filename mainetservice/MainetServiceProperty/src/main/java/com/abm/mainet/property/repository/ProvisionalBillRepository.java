package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.ProBillDetHisEntity;
import com.abm.mainet.property.domain.ProBillMstHisEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;

@Repository
public interface ProvisionalBillRepository extends JpaRepository<ProvisionalBillMasEntity, Long> {

    @Query("select m from ProvisionalBillMasEntity m where m.bmIdno=:bmIdno and m.orgid=:orgId")
    List<ProvisionalBillMasEntity> getProvisionalBillMasById(@Param("bmIdno") Long bmIdno, @Param("orgId") Long orgId);

    @Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.orgid=:orgId order by bmIdno ")
    List<ProvisionalBillMasEntity> getProvisionalBillMasByPropertyNo(@Param("propertyNo") String propertyNo,
            @Param("orgId") Long orgId);

    @Query("select m from ProvisionalBillMasEntity m where m.assId=:propertyId and m.orgid=:orgId")
    List<ProvisionalBillMasEntity> getProvisionalBillMasByPropertyId(@Param("propertyId") Long propertyId,
            @Param("orgId") Long orgId);

    @Query("select m from ProvisionalBillMasEntity m where m.assId=:propertyId and m.orgid=:orgId order by createdDate")
    List<ProvisionalBillMasEntity> getLatestBillByPropNo(@Param("propertyId") Long propertyId, @Param("orgId") Long orgId);

    @Query("select m from ProvisionalBillMasEntity m where m.bmIdno in (:bmIDno) and m.orgid=:orgId and m.flagJvPost is null order by m.bmIdno desc ")
    List<ProvisionalBillMasEntity> fetchListOfBillsByPrimaryKey(@Param("bmIDno") List<Long> bmIDno, @Param("orgId") Long orgid);

    @Modifying
    @Transactional
    @Query("UPDATE ProvisionalBillMasEntity e set e.flagJvPost=:flagJvPost where e.bmIdno in (:bmIDno) ")
    void updateAccountPostingFlag(@Param("bmIDno") List<Long> bmIdNo, @Param("flagJvPost") String flag);

    @Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.orgid=:orgId and m.bmPaidFlag=:bmPaidFlag  order by bmIdno ")
    List<ProvisionalBillMasEntity> fetchNotPaidBillForProAssessment(@Param("propertyNo") String propNo,
            @Param("orgId") Long orgId, @Param("bmPaidFlag") String paidFlag);

	@Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.flatNo=:flatNo and m.orgid=:orgId and m.bmPaidFlag=:bmPaidFlag  order by bmIdno ")
	List<ProvisionalBillMasEntity> fetchNotPaidBillForProAssessmentWithFlatNo(@Param("propertyNo") String propNo,
			@Param("orgId") Long orgId, @Param("bmPaidFlag") String paidFlag, @Param("flatNo") String flatNo);

    @Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.bmIdno not in("
            + "select e.bmIdno FROM MainBillMasEntity e where e.propNo=:propertyNo) order by m.bmIdno ")
    List<ProvisionalBillMasEntity> fetchBillSForViewProperty(@Param("propertyNo") String propNo);

    @Query("select m from ProvisionalBillMasEntity m where m.bmIdno=:bmIdno ")
    ProvisionalBillMasEntity fetchBillSForView(@Param("bmIdno") long bmIdNo);

    @Query("from ProvisionalBillMasEntity "
            + "  am  WHERE  am.bmIdno =:bmIdno")
    ProvisionalBillMasEntity fetchBillFromBmIdNo(@Param("bmIdno") Long bmIdno);

    @Query("select count(am) from ProvisionalBillMasEntity "
            + "  am  WHERE  am.propNo =:propertyNo and am.orgid=:orgId  and am.bmGenDes is  null ")
    int getCountOfBillWithoutDESByPropNo(@Param("propertyNo") String propNo, @Param("orgId") Long orgId);

    @Query("select m.bmPaidFlag from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.orgid=:orgId ")
    List<String> fetchProvisionalNotPaidBillByPropNo(@Param("propertyNo") String propNo,
            @Param("orgId") Long orgId);

	@Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.flatNo=:flatNo and m.orgid=:orgid and m.bmIdno not in("
			+ "select e.bmIdno FROM MainBillMasEntity e where e.propNo=:propertyNo and e.flatNo=:flatNo and e.orgid=:orgid) order by m.bmIdno ")
	List<ProvisionalBillMasEntity> fetchBillSForViewPropertyByPropAndFlatNo(@Param("propertyNo") String propNo,
			@Param("flatNo") String flatNo, @Param("orgid") long orgid);

	@Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.flatNo=:flatNo and m.orgid=:orgId order by bmIdno ")
	List<ProvisionalBillMasEntity> getProvisionalBillMasByPropertyNoFlatNo(@Param("propertyNo") String propertyNo, @Param("flatNo") String flatNo,
			@Param("orgId") long orgId);
	
	 @Query("select m from ProvisionalBillMasEntity m where m.propNo=:propertyNo and m.orgid=:orgId and m.bmYear >=:finId order by bmIdno ")
	    List<ProvisionalBillMasEntity> getProvisionalBillMasByPropertyNoAndBillNo(@Param("propertyNo") String propertyNo,
	            @Param("orgId") Long orgId, @Param("finId") Long finId);
	 
	 @Query("select m from ProvisionalBillMasEntity m where m.parentPropNo=:parentPropertyNo and m.orgid=:orgId and m.bmPaidFlag=:bmPaidFlag  order by bmIdno ")
	    List<ProvisionalBillMasEntity> fetchNotPaidBillForProAssessmentByParentPropNo(@Param("parentPropertyNo") String propNo,
	            @Param("orgId") Long orgId, @Param("bmPaidFlag") String paidFlag);
	
	 @Query("SELECT p FROM  ProBillMstHisEntity p WHERE p.bmIdno in (select max(m.bmIdno) from ProBillMstHisEntity m where m.propNo=:assNo)")
	 ProBillMstHisEntity fetchPropertyBillHistByPropNo(@Param("assNo") String propNo);
	 
	 @Query("SELECT p FROM  ProBillMstHisEntity p WHERE p.assId=:assId)")
	 List<ProBillMstHisEntity> fetchPropertyBillHistListByProAssId(@Param("assId") Long assId);
	 
	 @Query("SELECT p FROM  ProBillMstHisEntity p WHERE p.propNo=:assNo)")
	 List<ProBillMstHisEntity> fetchPropertyBillHistListByPropNo(@Param("assNo") String propNo);
		
	@Query("SELECT c FROM ProBillDetHisEntity c WHERE c.bmIdno =:findOne")
    List<ProBillDetHisEntity> fetchPropertyBillDetailHist(@Param("findOne") Long findOne);
	
	@Query("select min(m.bmIdno) from  ProvisionalBillMasEntity m where m.propNo=:propNo")
    Long getFirstBmIdNoByPropNo(@Param("propNo") String propNo);
		
}
