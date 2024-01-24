package com.abm.mainet.securitymanagement.service;

import java.util.List;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.dto.DeploymentOfStaffDTO;

public interface IDeploymentOfStaffService {

	public List<DeploymentOfStaffDTO> findAll(Long orgId);
	
	public List<ContractualStaffMasterDTO> findEmployeeNameList(Long orgId);
	
	public DeploymentOfStaffDTO saveOrUpdate(DeploymentOfStaffDTO dto);
	
	public List<DeploymentOfStaffDTO> findStaffDetails(Long empTypeId,Long vendorId,Long cpdShiftId, Long locId,Long orgId);
	
	public List<DeploymentOfStaffDTO> getList( List<DeploymentOfStaffDTO> list,List<TbLocationMas> localist,List<TbAcVendormaster> loadVendor);

	public DeploymentOfStaffDTO findById(Long contStaffIdNo);
	
	public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url, String workFlowFlag);

	public String updateWorkFlowSecurityService(WorkflowTaskAction workflowActionDto);

	public DeploymentOfStaffDTO findByDeplSeq(String complainNo, Long orgid);
	
	public void updateDeploymentStaffApproveStatus(DeploymentOfStaffDTO entity, String status,String lastDecision);

	void updateWfStatus(String complainNo, String status);
	
	public void updateWfStatusDepl(Long deplId, String pending, Long orgId);
	
	public List<DeploymentOfStaffDTO> getStaffNameByVendorId(Long vendorId,String empType, Long orgId);

	public ContractualStaffMasterDTO findEmpByEmpId(String contStaffIdNo, Long vendorId, Long orgId);

	void saveOrUpdateAfterWfApproval(Long deplId, Long orgId);
	
}
