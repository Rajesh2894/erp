package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.PetrolRequisitionDetails;


@Repository
public interface PetrolRequestRepository extends JpaRepository<PetrolRequisitionDetails, Long> {
	
	@Query("select h.deptId,h.veVetype, h.veNo from VeVehicleMaster h where h.orgid = 1 group by h.deptId, h.veVetype, h.veNo order by 1 desc")
	List<Object[]> vehicleData(@Param("orgid")Long orgid);

	List<PetrolRequisitionDetails> findByOrgid(Long orgId);

	@Modifying
	@Query("update PetrolRequisitionDetails d set d.PetrolWFStatus=:wfStatus,d.puId=:puId where d.requestId=:requestId and d.orgid=:orgid")
	void updateWorkFlowStatus(@Param("requestId")Long requestId,@Param("orgid")Long orgId, @Param("puId")Long puId,@Param("wfStatus")String wfStatus);

	@Modifying
	@Query("update PetrolRequisitionDetails d set d.PetrolWFStatus=:wfStatus where d.requestId=:requestId and d.orgid=:orgid")
	void updateWorkFlowStatus(@Param("requestId")Long requestId,@Param("orgid")Long orgId,@Param("wfStatus")String wfStatus);

	@Modifying
    @Query("UPDATE PetrolRequisitionDetails a SET a.requestStatus =:requestStatus where a.requestId =:requestId")
    void updatePetrolStatus(@Param("requestId") Long referenceId, @Param("requestStatus") String flaga);

	
	@Modifying
    @Query("UPDATE PetrolRequisitionDetails a SET a.requestStatus =:flagr where a.requestId =:requestId")
    void updateComplainStatus(@Param("requestId") Long requestId, @Param("flagr") String flagr);
	//void updateComplainStatus(Long requestId, String flagr);

	
	/*
	 * @Query("SELECT d.requestStatus FROM PetrolRequisition d WHERE d.veId=:veId")
	 * void getRequestStatus(@Param("veId") Long veId);
	 * 
	 */
	
	/*
	 * @Query("select COUNT(*) FROM PetrolRequisition WHERE d.veId=:veId") String
	 * getRequestStatus1(@Param("veId")Long veId);
	 */
	
	@Query("SELECT d.requestStatus FROM PetrolRequisitionDetails d WHERE d.veId=:veId and d.requestStatus=:requestStatus")
	String getRequestStatus(@Param("veId")Long veId,@Param("requestStatus") String requestStatus);
	
	@Query(value = "select * from tb_workflow_action where reference_id=:refId and orgid=:orgId order by WORKFLOW_ACT_ID DESC ", nativeQuery = true)
	List<Object[]> findWorkFlowTaskByRefId(@Param("refId") String refId, @Param("orgId") Long orgId);


}
