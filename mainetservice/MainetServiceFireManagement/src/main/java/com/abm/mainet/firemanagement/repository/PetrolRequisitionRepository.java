/*package com.abm.mainet.firemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FmPetrolRequisition;


@Repository
public interface PetrolRequisitionRepository extends JpaRepository<FmPetrolRequisition, Long> {
	
	//@Query("select h.deptId,h.veVetype, h.veNo from FmVehicleMaster h where h.orgid =:orgid group by h.deptId, h.veVetype, h.veNo order by 1 desc")
    //List<Object[]> vehicleData(@Param("orgid")Long orgid);

	List<FmPetrolRequisition> findByOrgid(Long orgId);

	 Long validateStatus(Long orgid, String requestStatus, Long valueOf); 

//	List<FmVehicleMaster>  findAllMeetingsByMeetingTypeIdAndMeetingStatusAndOrgId(Long department, Long vehicleType,	String activeFlag, Long orgId);
	
	
	 * @Query("select count(*) from FireCallRegister m where m.orgid=:orgid and m.requestId=:requestId and :inputDate between m.date and sysdate()"
	 * ) public Long ValidateDate(@Param("orgid") Long orgid,@Param("requestStatus")
	 * String requestStatus,@Param("requestId") Long requestId);
	 
	
	@Modifying
	@Query("update FmPetrolRequisition d set d.PetrolWFStatus=:wfStatus where d.requestId=:requestId and d.orgid=:orgid")
	void updateWorkFlowStatus(@Param("requestId")Long requestId,@Param("orgid")Long orgId,@Param("wfStatus")String wfStatus);

	@Modifying
    @Query("UPDATE FmPetrolRequisition a SET a.requestStatus =:requestStatus where a.requestId =:requestId")
    void updatePetrolStatus(@Param("requestId") Long referenceId, @Param("requestStatus") String flaga);

	
	@Modifying
    @Query("UPDATE FmPetrolRequisition a SET a.requestStatus =:flagr where a.requestId =:requestId")
    void updateComplainStatus(@Param("requestId") Long requestId, @Param("flagr") String flagr);
	//void updateComplainStatus(Long requestId, String flagr);
	
	
}
*/