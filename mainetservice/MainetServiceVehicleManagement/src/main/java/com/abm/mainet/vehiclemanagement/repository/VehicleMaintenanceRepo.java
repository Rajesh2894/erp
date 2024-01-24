package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceDetails;

/**
 * The Interface VehicleMaintenanceRepository.
 * @author Lalit.Prusti Created Date : 22-May-2018
 */
@Repository
public interface VehicleMaintenanceRepo extends JpaRepository<VehicleMaintenanceDetails, Long> {

    /**
     * Find all vehicle by vehicle type.
     *
     * @param vehicleType the vehicle type
     * @param orgId the org id
     * @return the list
     */
    List<VehicleMaintenanceDetails> findAllByVeVetypeAndOrgid(Long vehicleType, Long orgId);

    /**
     * find Last Meter Reading
     * @param orgId
     * @param veheicleId
     * @return
     */
    @Query(" SELECT MAX(f.vemReading) FROM VehicleMaintenanceDetails f WHERE f.orgid=:orgId AND f.veId=:veheicleId ")
    Long findLastMeterReading(@Param("orgId") Long orgId, @Param("veheicleId") Long veheicleId);
    
    @Query(" SELECT f FROM VehicleMaintenanceDetails f WHERE f.orgid=:orgId AND f.veId=:veheicleId ")
    List<VehicleMaintenanceDetails> findData(@Param("orgId") Long orgId, @Param("veheicleId") Long veheicleId);
    
    @Query("select f from VehicleMaintenanceDetails f where f.requestNo=:requestNo and f.orgid=:orgid ")
	VehicleMaintenanceDetails getdData(@Param("requestNo")String requestNo,@Param("orgid") Long orgid);
    
    @Modifying
	@Query("update VehicleMaintenanceDetails d set d.wfStatus=:wfStatus where d.vemId=:vemId and d.orgid=:orgId")
	void updateWorkFlowStatus(@Param("vemId")Long vemId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus);

	long countByVeIdAndOrgidAndWfStatus(Long veNo, long orgId, String status);

	@Query(value = "SELECT wa.DECISION from tb_workflow_action wa join tb_workflow_task wt on wa.WFTASK_ID=wt.WFTASK_ID where wt.REFERENCE_ID=:REFERENCE_ID and wt.ORGID=:orgId and wt.SMFACTION=:SMFACTION ", nativeQuery = true)
	public String findWorkflowTaskDecisionByAppId(@Param("REFERENCE_ID") String requestNo,  @Param("orgId") Long orgId, @Param("SMFACTION") String maintInspApprUrl);

	@Query(value = "Select md from VehicleMaintenanceDetails md where md.veId in (:activeMaintMasVehicleIdList) and md.orgid=:orgId "
			+ " and md.createdDate=(SELECT MAX(md2.createdDate) from VehicleMaintenanceDetails md2 where md2.veId=md.veId)")
	List<VehicleMaintenanceDetails> getMaintenanceDataForMaintenanceAlert(@Param("activeMaintMasVehicleIdList") List<Long> activeMaintMasVehicleIdList,
			@Param("orgId") Long orgId);

}
