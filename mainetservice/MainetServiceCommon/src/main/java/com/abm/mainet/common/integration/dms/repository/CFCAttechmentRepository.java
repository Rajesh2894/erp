/**
 *
 */
package com.abm.mainet.common.integration.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

/**
 * @author Lalit.Prusti
 *
 */
@Repository
public interface CFCAttechmentRepository extends CrudRepository<CFCAttachment, Long> {

    @Query("FROM CFCAttachment s WHERE s.orgid=:orgid and s.applicationId=:applicationId and s.smScrutinyId is null ")
    public List<CFCAttachment> findAllAttachmentsByAppId(@Param("orgid") long orgId, @Param("applicationId") long applicationId);
    
	@Modifying
	@Query(" update CFCAttachment a set a.clmAprStatus=:clmAprStatus where a.orgid=:orgid and  a.applicationId =:applicationId ")
	public void updateClmAprStatus(@Param("clmAprStatus") String clmAprStatus, @Param("orgid") long orgId,
			@Param("applicationId") long applicationId);
	
	@Query("FROM CFCAttachment s WHERE s.applicationId=:apmApplicationId and s.clmId=:clmId")
    public CFCAttachment getDocumentUploadedListByAppId(@Param("apmApplicationId") Long apmApplicationId, @Param("clmId") Long clmId);
	
	@Query("FROM CFCAttachment s WHERE s.applicationId=:apmApplicationId and s.referenceId=:referenceId")
    public List<CFCAttachment> getDocumentByAppIdAndReferenceId(@Param("apmApplicationId") Long apmApplicationId, @Param("referenceId") String referenceId);

	@Query("FROM CFCAttachment s WHERE s.applicationId=:apmApplicationId and s.clmId=:clmId and s.referenceId=:refId")
	public CFCAttachment getDocumentUploadedListByClmIdRefId(@Param("apmApplicationId") Long apmApplicationId, @Param("clmId") Long clmId, @Param("refId") String refId);
	
	@Modifying
	@Transactional
	@Query("delete from CFCAttachment cfc where cfc.applicationId=:applicationId and cfc.serviceId=:serviceId and cfc.orgid=:orgId and cfc.clmId=:clmId and cfc.referenceId=:refId")
	void deleteDocumentByRefIdAndClmId(@Param("applicationId")Long applicationId,@Param("serviceId") Long serviceId ,@Param("orgId") Long orgId, @Param("clmId") Long clmId, @Param("refId") String refId);

}
