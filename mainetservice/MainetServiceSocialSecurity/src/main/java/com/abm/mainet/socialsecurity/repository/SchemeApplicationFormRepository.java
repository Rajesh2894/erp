/**
 * 
 */
package com.abm.mainet.socialsecurity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.socialsecurity.domain.SchemeApplicantFamilyDetEntity;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;

/**
 * @author satish.rathore
 *
 */

@Repository
public interface SchemeApplicationFormRepository extends PagingAndSortingRepository<SocialSecurityApplicationForm, Long> {

    @Query("from SocialSecurityApplicationForm ssa where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    SocialSecurityApplicationForm findApplicationdetails(@Param("applicationId") String applicationId,
            @Param("orgId") Long orgId);

    @Modifying
    @Query("update SocialSecurityApplicationForm ssa set ssa.sapiStatus=:status where ssa.apmApplicationId=:applicationId and ssa.orgId=:parentOrgId")
    void updateApprovalFlag(@Param("applicationId") String applicationId, @Param("parentOrgId") Long parentOrgId,
            @Param("status") String status);

    @Modifying
    @Transactional
    @Query("update SocialSecurityApplicationForm ssa set ssa.lastDateofLifeCerti=:lastDateofLifeCerti where ssa.beneficiarynumber=:beneficiarynumber")
    void updateLastDateofLifeCerti(@Param("beneficiarynumber") String beneficiarynumber,
            @Param("lastDateofLifeCerti") Date lastDateofLifeCerti);

    @Query("from SocialSecurityApplicationForm ssa where ssa.apmApplicationId=:applicationId and ssa.beneficiarynumber=:beneficiarynumber and ssa.orgId=:orgId")
    SocialSecurityApplicationForm findDatabyBenef(@Param("applicationId") String applicationId,
            @Param("beneficiarynumber") String beneficiarynumber, @Param("orgId") Long orgId);

    @Query("from SocialSecurityApplicationForm ssa where ssa.beneficiarynumber=:beneficiarynumber and ssa.orgId=:orgId")
    SocialSecurityApplicationForm fetchDataOnBenef(@Param("beneficiarynumber") String beneficiarynumber,
            @Param("orgId") Long orgId);

    @Modifying
    @Transactional
    @Query("update SocialSecurityApplicationForm ssa set ssa.validtoDate=:validtoDate where ssa.beneficiarynumber=:beneficiarynumber")
    void updateValidtoDate(@Param("beneficiarynumber") String beneficiarynumber, @Param("validtoDate") Date validtoDate);

    @Query("from SocialSecurityApplicationForm ssa where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    SocialSecurityApplicationForm fetchAllData(@Param("applicationId") String applicationId, @Param("orgId") Long orgId);

    @Query("from CFCAttachment ssa where ssa.applicationId=:applicationId and ssa.orgid=:orgid")
    SocialSecurityApplicationForm getUploadedfile(@Param("applicationId") Long applicationId, @Param("orgid") Long orgid);

    @Query("from SocialSecurityApplicationForm ssa where ssa.orgId=:orgId ")
    List<SocialSecurityApplicationForm> fetchAlldata(@Param("orgId") Long orgId);
    
    @Query("from SocialSecurityApplicationForm ssa where ssa.beneficiarynumber=:beneficiarynumber and ssa.orgId=:orgId")
    SocialSecurityApplicationForm findPensionDetails(@Param("beneficiarynumber") String beneficiarynumber, @Param("orgId") Long orgId);
    
    @Modifying
    @Query("update SocialSecurityApplicationForm ssa set ssa.sapiStatus='C', ssa.pensionCancelReason=:pensionCancelReason, ssa.pensionCancelDate=:pensionCancelDate where ssa.beneficiarynumber=:beneficiarynumber")
    void updateClosePension( @Param("pensionCancelReason") String pensionCancelReason,
            @Param("pensionCancelDate") Date pensionCancelDate, @Param("beneficiarynumber") String beneficiarynumber);
    
    @Modifying
    @Query("update SocialSecurityApplicationForm ssa set ssa.sapiStatus='R' where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    void rejectPension(@Param("applicationId") String applicationId,@Param("orgId") Long orgId);
    
    //query added by Rahul.Chaubey
    //gets total number of applicant forms that has been apoorved till date
	@Query(value = "SELECT COUNT(ap.selectSchemeName) FROM SocialSecurityApplicationForm ap , ConfigurationMasterEntity sn"
			+ " WHERE ap.selectSchemeName = sn.schemeMstId AND ap.selectSchemeName =:schemeMstId AND ap.sapiStatus = 'A' AND ap.orgId=:orgId group by ap.selectSchemeName")
	Object getApprovedCount(@Param("schemeMstId") Long schemeMstId, @Param("orgId") Long orgId);
	
	@Query("FROM SocialSecuritySchemeMaster sm where sm.schemeNameId=:serviceId AND sm.orgId=:orgId AND isSchmeActive ='Y'")
	SocialSecuritySchemeMaster getSchemeEntity(@Param("serviceId")Long serviceId ,  @Param("orgId") Long orgId);
	
	//rajesh.das for getting list of active scheme according to orgid
	@Query("FROM SocialSecuritySchemeMaster sm where  sm.orgId=:orgId AND isSchmeActive ='Y'")
	List<SocialSecuritySchemeMaster> getActiveScheme( @Param("orgId") Long orgId);
	
 
	 @Query("FROM SocialSecuritySchemeEligibilty sm where sm.sosecuritySchmMasEligibility.schemeMstId =:sdsch_id "
	 		+ " AND sm.orgId =:orgId AND sm.factorApplicableId IN (:factors) "
	 		+ " AND sm.criteriaId IN (:criterias)")
	 List<SocialSecuritySchemeEligibilty> getschemeEligibilityEntity(@Param("sdsch_id")Long sdsch_id ,  @Param("orgId") Long orgId, 
				@Param("factors")List factors, @Param("criterias")List criterias);
	 
	 
	 @Query("FROM SocialSecuritySchemeMaster sm where sm.schemeNameId=:serviceId AND sm.orgId=:orgId")
		SocialSecuritySchemeMaster isSchemeActive(@Param("serviceId")Long serviceId ,  @Param("orgId") Long orgId);
	 // D#13819
	  @Query("from SocialSecurityApplicationForm ssa where ssa.aadharCard=:aadharCard and orgId=:orgId")
	    List<SocialSecurityApplicationForm> fetchAlldataByAadhar(@Param("orgId") Long orgId,@Param("aadharCard") String aadharCard);
	  
	  @Query("select case when count(tm)>0 THEN true ELSE false END from SocialSecurityApplicationForm  tm where tm.selectSchemeName =:serviceId AND tm.orgId =:orgId")
	  Boolean checkAppPresentAgainstScheme(@Param("serviceId") Long serviceId,@Param("orgId") Long orgId);
	  
	  @Query("from SchemeApplicantFamilyDetEntity ssa where ssa.applicationId.applicationId=:applicationId and ssa.orgId=:orgId")
      List<SchemeApplicantFamilyDetEntity> getFamilyDetById(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

	  
	@Query("SELECT sm.smServiceId, sm.smServiceName, sm.smShortdesc, sm.smServiceNameMar FROM ServiceMaster sm "
			+ "WHERE sm.orgid=:orgId AND sm.tbDepartment.dpDeptid=:depId AND sm.smServActive=:activeStatusId and sm.comV1=:notActualFlag AND sm.smServiceName IS NOT NULL AND sm.smServiceId not in (select distinct schemeNameId from SocialSecuritySchemeMaster where orgId=:orgId AND isSchmeActive='Y') order by sm.smServiceName asc")
	List<Object[]> findAllActiveServicesNotAddedInSchemeMst(@Param("orgId") Long orgId, @Param("depId") Long depId,
			@Param("activeStatusId") Long activeStatusId, @Param("notActualFlag") String notActualFlag);
}
