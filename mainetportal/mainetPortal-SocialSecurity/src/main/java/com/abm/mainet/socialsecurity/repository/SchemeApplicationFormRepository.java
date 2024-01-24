/**
 * 
 */
package com.abm.mainet.socialsecurity.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;

/**
 * @author satish.rathore
 *
 */
public interface SchemeApplicationFormRepository extends PagingAndSortingRepository<SocialSecurityApplicationForm, Long> {

    @Query("from SocialSecurityApplicationForm ssa where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    SocialSecurityApplicationForm findApplicationdetails(@Param("applicationId") String applicationId,
            @Param("orgId") Long orgId);

    @Modifying
    @Query("update SocialSecurityApplicationForm ssa set ssa.sapiStatus=:status where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    void updateApprovalFlag(@Param("applicationId") String applicationId, @Param("orgId") Long orgId,
            @Param("status") String status);
    
    
    @Query("from SocialSecurityApplicationForm ssa where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    SocialSecurityApplicationForm fetchAllData(@Param("applicationId") String applicationId, @Param("orgId") Long orgId);
    
    @Modifying
    @Transactional
    @Query("update SocialSecurityApplicationForm a set a.validtoDate=:validtoDate where a.beneficiarynumber=:beneficiarynumber")
    void updateValidtoDate(@Param("beneficiarynumber") String beneficiarynumber,@Param("validtoDate")Date validtoDate);

    @Query("from SocialSecurityApplicationForm ssa where ssa.beneficiarynumber=:beneficiarynumber and ssa.orgId=:orgId")
    SocialSecurityApplicationForm fetchDataOnBenef(@Param("beneficiarynumber") String beneficiarynumber, @Param("orgId") Long orgId);
}
