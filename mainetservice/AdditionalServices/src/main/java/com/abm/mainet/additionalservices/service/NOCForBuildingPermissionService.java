package com.abm.mainet.additionalservices.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface NOCForBuildingPermissionService {
	
	public NOCForBuildingPermissionDTO saveData(NOCForBuildingPermissionDTO nocBuildingPermissionDto);
	
	Boolean checkEmployeeRole(UserSession ses);
	

	public NOCForBuildingPermissionDTO getRegisteredAppliDetail(Long applicationId, Long orgId);

	public void saveRegDet(NOCForBuildingPermissionDTO birthRegDto);

	public void updateApproveStatusBR(NOCForBuildingPermissionDTO birthRegDTO, String status, String lastDecision);

	void updateWorkFlowStatusBR(Long brId, String taskNamePrevious, Long orgId, String brStatus);

	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction,NOCForBuildingPermissionDTO nocBuildingPermissionDto);
	
	public List<NOCForBuildingPermissionDTO> getAppliDetail(Long apmApplicationId, Date fromDate, Date toDate,Long orgId);
	
	public List<NOCForBuildingPermissionDTO> getAllData();
	
	public NOCForBuildingPermissionDTO getDataById(Long bpId);
	
	public NOCForBuildingPermissionDTO findByRefNo(String refNo);

	List<NOCForBuildingPermissionDTO> getAppliDetail(Long apmApplicationId, Date fromDate, Date toDate, Long orgId,
			String refNo);

}
