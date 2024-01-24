package com.abm.mainet.care.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.care.domain.CareRequest;

@Repository
public interface CareRequestRepository extends JpaRepository<CareRequest, Long> {

    /**
     * To find CareRequest by ApplicationId
     * 
     * @param requestNo
     * @return
     */
    @Query("select c from CareRequest c where c.applicationId =:applicationId")
    CareRequest findByApplicationId(@Param("applicationId") Long applicationId);

    CareRequest findByComplaintId(String complaintId);

    CareRequest findByComplaintIdOrApplicationId(String complaintId, Long applicationId);

    /**
     * To find CareRequest by ApplicationId and OrgId
     * 
     * @param applicationId
     * @param orgId
     * @return
     */
    Optional<CareRequest> findByApplicationIdAndOrgId(Long applicationId, Long orgId);

    /**
     * 
     * @param id
     * @param status
     * @return
     */
    @Query("select c from CareRequest c where c.createdBy =:CitizenId")
    List<CareRequest> findByCreatedByCitizen(@Param("CitizenId") long CitizenId);

    /**
     * Example - select fn_sq_generation('CARE',''TB_WORKFLOW_REQUEST,'REQUEST_NO',81,'C',null);
     * 
     * @param module
     * @param table
     * @param field
     * @param orgId
     * @param rst
     * @param ctrId
     * @return
     */
    @Query(value = "select fn_sq_generation(:module,:table,:field,:orgId,:rst,:ctrId)", nativeQuery = true)
    Long getSequnceNumberFromDatabase(
            @Param("module") String module,
            @Param("table") String table,
            @Param("field") String field,
            @Param("orgId") long orgId,
            @Param("rst") String rst,
            @Param("ctrId") String ctrId);
    
    @Query(value = "select max(COM_LEVEL)  from tb_comparent_mas where CPM_ID=(select CPM_ID from tb_comparam_mas where CPM_PREFIX=:prefix) and ORGID=:orgId", nativeQuery = true)
    Long getPrefixLevelCount(@Param("prefix") String prefix, @Param("orgId") Long orgId);
    
	@Modifying
	@Query("update CareRequest d set d.extReferNumber=:SwacchataComplaintId where d.applicationId=:applicationId and complaintId=:complaintNo and d.orgId=:orgId")
	void updateExtRefNo(@Param("SwacchataComplaintId") String SwacchataComplaintId, @Param("applicationId") Long applicationId, @Param("complaintNo") String complaintNo, @Param("orgId") Long orgId);

}
