package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.ProAssDetailHisEntity;
import com.abm.mainet.property.domain.ProAssFactlHisEntity;
import com.abm.mainet.property.domain.ProAssMstHisEntity;
import com.abm.mainet.property.domain.ProAssOwnerHisEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;

@Repository
public interface ProvisionalAssesmentMstRepository extends JpaRepository<ProvisionalAssesmentMstEntity, Long> {

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.proAssId=:proAssId and m.assActive=:activeSatus")
    ProvisionalAssesmentMstEntity getProvisionalAssessmentMstById(Long proAssId, Long orgId, String activeSatus);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.apmApplicationId=:applicationId "
            + "and m.assActive='A' order by proAssId")
    List<ProvisionalAssesmentMstEntity> getProvisionalAssessmentMstByApplicationId(@Param("orgId") Long orgId,
            @Param("applicationId") Long applicationId);

    @Query("SELECT proAssId FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.apmApplicationId=:applicationId")
    Long getProAssIdByApplicationId(Long orgId, Long applicationId);

    @Query("SELECT proAssId FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.assOldpropno=:oldPropertyNo")
    Long getProAssIdByOldPropertyNo(Long orgId, String oldPropertyNo);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive='A' order by proAssId")
    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByPropNoOrOldPropNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive=:activeSatus order by proAssId")
    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByPropNo(@Param("orgId") Long orgId,
            @Param("propertyNo") String propertyNo, @Param("activeSatus") String activeSatus);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE  "
            + " m.assNo=:propertyNo and m.assActive='A' order by proAssId")
    List<ProvisionalAssesmentMstEntity> getPropDetailByPropNoOnly(
            @Param("propertyNo") String propertyNo);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.assWard1=:ward1 and"
            + " m.assWard2=:ward2 and m.assWard3=:ward3 and m.assWard4=:ward4 and m.assWard5=:ward5")
    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByWard(Long orgId, Long ward1, Long ward2, Long ward3, Long ward4,
            Long ward5, String activeSatus);

    // many to one example
    @Query("SELECT o FROM  ProvisionalAssesmentOwnerDtlEntity o  WHERE o.orgId=:orgId and"
            + " o.assoOwnerName=:ownerName")
    List<ProvisionalAssesmentOwnerDtlEntity> getPropDetailFromProvAssByOwnerName(Long orgId, String ownerName,
            String activeSatus);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.assAutStatus=:autStatus")
    ProvisionalAssesmentMstEntity getNonAuthorizedPropFromProvAssesmentMst(Long orgId, String autStatus, String activeSatus);

    @Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
            + " m.parentProp=:parentProp")
    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByParentPropNo(@Param("orgId") long orgId,
            @Param("parentProp") String assNo);

    @Query("SELECT p FROM  ProvisionalAssesmentMstEntity p WHERE"
            + " p.proAssId in (select max(m.proAssId) from ProvisionalAssesmentMstEntity m where "
            + " m.assNo=:assNo)")
    ProvisionalAssesmentMstEntity fetchPropertyByPropNo(@Param("assNo") String propNo);

    @Query("SELECT p FROM  ProvisionalAssesmentMstEntity p WHERE"
            + " p.proAssId in (select max(m.proAssId) from ProvisionalAssesmentMstEntity m where "
            + " m.assOldpropno=:assOldpropno)")
    ProvisionalAssesmentMstEntity fetchPropertyByOldPropNo(@Param("assOldpropno") String assOldpropno);

    @Query("SELECT distinct(apmApplicationId) FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.assNo=:assNo")
    List<Long> fetchApplicationAgainstProperty(@Param("orgId") long orgId, @Param("assNo") String proertyNo);

    @Query("SELECT count(p) FROM  ProvisionalAssesmentMstEntity p WHERE"
            + " p.assNo=:assNo and p.smServiceId  !=:serviceId  and p.orgId=:orgId")
    int getCountOfAssessmentWithoutDES(@Param("assNo") String propNo, @Param("orgId") Long orgId,
            @Param("serviceId") Long serviceId);

    @Query("SELECT m FROM  ProvisionalAssesmentOwnerDtlEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertryNo and m.assoOType='P' and m.assoActive='A'")
    List<ProvisionalAssesmentOwnerDtlEntity> getPrimaryOwnerDetailByPropertyNo(@Param("orgId") long orgId,
            @Param("propertryNo") String propertryNo);

    @Query("select a from ProvisionalAssesmentMstEntity a " +
            "where (a.assNo,a.proAssId) in ( " +
            "select  b.assNo,MAX(b.proAssId) from ProvisionalAssesmentMstEntity b where b.assNo in(:propNoList) " +
            "and b.orgId=:orgId group by b.assNo )")
    List<ProvisionalAssesmentMstEntity> getListOfPropertyByListOfPropNos(@Param("orgId") Long orgId,
            @Param("propNoList") List<String> propNoList);

    @Modifying(clearAutomatically = true)
    @Query("update ProvisionalAssesmentMstEntity m set m.assActive =:status where m.proAssId in (:propPrimKeyList)")
    void updatePropMasStatus(@Param("propPrimKeyList") List<Long> propPrimKeyList,
            @Param("status") String status);

    @Modifying
    @Query("update ProvisionalAssesmentDetailEntity m set m.assdActive =:status where m.proAssdId in (:propUnitDetailPrimKeyList)")
    void updatePropUnitDetailStatus(@Param("propUnitDetailPrimKeyList") List<Long> propUnitDetailPrimKeyList,
            @Param("status") String status);

    @Query("SELECT count(*) FROM  ProvisionalAssesmentMstEntity m  WHERE m.assNo=:propNo and m.orgId=:orgId and m.assActive='A'")
    Long getCountBypropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT count(*) FROM  ProvisionalAssesmentMstEntity m  WHERE m.assOldpropno=:oldpropno and m.orgId=:orgId and m.assActive='A'")
    Long getCountByOldPropNo(@Param("oldpropno") String oldpropno, @Param("orgId") Long orgId);

    @Query("SELECT c.proAssId FROM ProvisionalAssesmentMstEntity c WHERE c.assNo =:assNo and c.orgId =:orgId")
    List<Long> fetchProAssdIdListbyPropNo(@Param("assNo") String propNo, @Param("orgId") Long orgId);

    @Modifying(clearAutomatically = true)
    @Query("update ProvisionalAssesmentMstEntity a set a.updatedBy=:empId,"
            + "a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE, a.assAddress=:assAddress, a.locId=:locId, a.assPincode=:assPincode,a.assOldpropno=:oldPropNo, a.tppPlotNo=:houseNo,a.newHouseNo=:newHouseNo, a.updateDataEntryFlag=:updateDataEntryFlag"
            + " where a.orgId=:orgId and  a.assNo =:propNo ")
    int updateDataEntryDetails(@Param("orgId") Long orgId,
            @Param("propNo") String propNo,
            @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress,
            @Param("assAddress") String assAddress,
            @Param("locId") Long locId, @Param("assPincode") Long assPincode,
            @Param("updateDataEntryFlag") String updateDataEntryFlag,@Param("oldPropNo") String oldPropNo, @Param("houseNo") String houseNo,@Param("newHouseNo") String newHouseNo);

    @Query("SELECT c.assAutStatus FROM ProvisionalAssesmentMstEntity c WHERE c.assNo =:assNo ")
    List<String> fetchProAssesmentByPropNo(@Param("assNo") String propNo);
	
	@Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
            + " m.assNo=:propertyNo and m.assActive=:activeSatus and m.flatNo=:flatNo order by proAssId")
	List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByPropNoAndFlatNo(@Param("orgId") long orgId,
            @Param("propertyNo") String propertyNo,@Param("flatNo") String flatNo, @Param("activeSatus") String activeSatus);

	@Query("SELECT distinct(apmApplicationId) FROM  ProvisionalAssesmentMstEntity m  WHERE m.orgId=:orgId and m.assNo=:assNo and m.flatNo=:flatNo")
	List<Long> fetchApplicationAgainstPropertyWithFlatNo(@Param("orgId") long orgId, @Param("assNo") String proertyNo,
			@Param("flatNo") String flatNo);
	
	@Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
            + " m.assOldpropno=:oldPropNo and m.assActive='A' order by proAssId")
	List<ProvisionalAssesmentMstEntity> getProAssIdByOldPropertyNoActive(@Param("orgId") Long orgId, @Param("oldPropNo") String oldPropertyNo);
	
	@Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.orgId=:orgId and"
			+ " m.assOldpropno=:assOldpropno and m.assActive=:activeSatus and m.flatNo=:flatNo order by proAssId")
	List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByOldPropNoAndFlatNo(@Param("orgId") long orgId,
			@Param("assOldpropno") String assOldpropno, @Param("flatNo") String flatNo,
			@Param("activeSatus") String activeSatus);

	@Query("SELECT m FROM  ProvisionalAssesmentMstEntity m WHERE m.assOldpropno=:assOldpropno and m.orgId=:orgId and m.assActive='A' order by proAssId")
	List<ProvisionalAssesmentMstEntity> getPropDetailByOldPropNoAndOrgId(@Param("assOldpropno") String assOldpropno,
			@Param("orgId") Long orgId);
	
	@Modifying
	@Query("update ProvisionalAssesmentMstEntity m set m.billMethodChngFlag =:billMethodChngFlag where m.assNo=:assNo and m.orgId=:orgId")
	void updateBillMethodChangeFlag(@Param("assNo") String proertyNo, @Param("billMethodChngFlag") String billMethodChngFlag,
			@Param("orgId") Long orgId);
	
	@Query("SELECT p FROM  ProAssMstHisEntity p WHERE"
            + " p.proAssId in (select max(m.proAssId) from ProAssMstHisEntity m where "
            + " m.assNo=:assNo)")
	ProAssMstHisEntity fetchPropertyHistByPropNo(@Param("assNo") String propNo);
	
	@Query("SELECT c FROM ProAssDetailHisEntity c WHERE c.assId =:findOne")
    List<ProAssDetailHisEntity> fetchPropertyDetailHist(@Param("findOne") Long findOne);
	
	@Query("SELECT c FROM ProAssOwnerHisEntity c WHERE c.proAssId =:findOne")
	List<ProAssOwnerHisEntity> fetchPropertyOwnerDetailHist(@Param("findOne") Long findOne);
	
	@Query("SELECT c FROM ProAssFactlHisEntity c WHERE c.proAssdId =:findOne")
    List<ProAssFactlHisEntity> fetchPropertyFactorDetailHist(@Param("findOne") Long findOne);
	
	@Query("SELECT c.assNo FROM ProvisionalAssesmentMstEntity c WHERE c.assOldpropno =:oldPropNo and c.orgId =:orgId")
    String getPropNoByOldPropNo(@Param("oldPropNo") String oldPropNo, @Param("orgId") Long orgId);
	

}
