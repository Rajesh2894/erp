/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.service.AllocationOfBlocksService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ChangeofBlockApprovalModel extends AbstractFormModel {

	@Autowired
	private AllocationOfBlocksService alocOfBlocksService;

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121063868789977285L;

	BlockAllocationDto blockAllocationDto = new BlockAllocationDto();

	private BlockAllocationDto newBlockAllocationDto = new BlockAllocationDto();

	List<BlockAllocationDto> inactiveBlockDtoList = new ArrayList<>();

	List<BlockAllocationDto> changedBlockDtoList = new ArrayList<>();

	List<TbOrganisation> orgList = new ArrayList<>();

	List<LookUp> stateList = new ArrayList<>();

	List<LookUp> districtList = new ArrayList<>();
	List<LookUp> blockList = new ArrayList<>();

	private List<TbDepartment> departmentList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private List<AttachDocs> attachDocsList = new ArrayList<>();
	List<CommonMasterDto> commonMasterDtoList = new ArrayList<>();

	List<LookUp> allocationCatgList = new ArrayList<>();
	List<LookUp> allocationSubCatgList = new ArrayList<>();
	List<LookUp> allcSubCatgTargetList = new ArrayList<>();

	List<CBBOMasterDto> cbboMasterList = new ArrayList<>();

	/**
	 * @return the blockAllocationDto
	 */
	public BlockAllocationDto getBlockAllocationDto() {
		return blockAllocationDto;
	}

	/**
	 * @param blockAllocationDto the blockAllocationDto to set
	 */
	public void setBlockAllocationDto(BlockAllocationDto blockAllocationDto) {
		this.blockAllocationDto = blockAllocationDto;
	}

	/**
	 * @return the inactiveBlockDtoList
	 */
	public List<BlockAllocationDto> getInactiveBlockDtoList() {
		return inactiveBlockDtoList;
	}

	/**
	 * @param inactiveBlockDtoList the inactiveBlockDtoList to set
	 */
	public void setInactiveBlockDtoList(List<BlockAllocationDto> inactiveBlockDtoList) {
		this.inactiveBlockDtoList = inactiveBlockDtoList;
	}

	/**
	 * @return the changedBlockDtoList
	 */
	public List<BlockAllocationDto> getChangedBlockDtoList() {
		return changedBlockDtoList;
	}

	/**
	 * @param changedBlockDtoList the changedBlockDtoList to set
	 */
	public void setChangedBlockDtoList(List<BlockAllocationDto> changedBlockDtoList) {
		this.changedBlockDtoList = changedBlockDtoList;
	}

	/**
	 * @return the orgList
	 */
	public List<TbOrganisation> getOrgList() {
		return orgList;
	}

	/**
	 * @param orgList the orgList to set
	 */
	public void setOrgList(List<TbOrganisation> orgList) {
		this.orgList = orgList;
	}

	/**
	 * @return the departmentList
	 */
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	/**
	 * @param departmentList the departmentList to set
	 */
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	/**
	 * @return the faYears
	 */
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	/**
	 * @param faYears the faYears to set
	 */
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	/**
	 * @return the documentList
	 */
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList the documentList to set
	 */
	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	/**
	 * @return the commonMasterDtoList
	 */
	public List<CommonMasterDto> getCommonMasterDtoList() {
		return commonMasterDtoList;
	}

	/**
	 * @param commonMasterDtoList the commonMasterDtoList to set
	 */
	public void setCommonMasterDtoList(List<CommonMasterDto> commonMasterDtoList) {
		this.commonMasterDtoList = commonMasterDtoList;
	}

	/**
	 * @return the newBlockAllocationDto
	 */
	public BlockAllocationDto getNewBlockAllocationDto() {
		return newBlockAllocationDto;
	}

	/**
	 * @param newBlockAllocationDto the newBlockAllocationDto to set
	 */
	public void setNewBlockAllocationDto(BlockAllocationDto newBlockAllocationDto) {
		this.newBlockAllocationDto = newBlockAllocationDto;
	}

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the blockList
	 */
	public List<LookUp> getBlockList() {
		return blockList;
	}

	/**
	 * @param blockList the blockList to set
	 */
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	/**
	 * @return the allocationCatgList
	 */
	public List<LookUp> getAllocationCatgList() {
		return allocationCatgList;
	}

	/**
	 * @param allocationCatgList the allocationCatgList to set
	 */
	public void setAllocationCatgList(List<LookUp> allocationCatgList) {
		this.allocationCatgList = allocationCatgList;
	}

	/**
	 * @return the allocationSubCatgList
	 */
	public List<LookUp> getAllocationSubCatgList() {
		return allocationSubCatgList;
	}

	/**
	 * @param allocationSubCatgList the allocationSubCatgList to set
	 */
	public void setAllocationSubCatgList(List<LookUp> allocationSubCatgList) {
		this.allocationSubCatgList = allocationSubCatgList;
	}

	/**
	 * @return the allcSubCatgTargetList
	 */
	public List<LookUp> getAllcSubCatgTargetList() {
		return allcSubCatgTargetList;
	}

	/**
	 * @param allcSubCatgTargetList the allcSubCatgTargetList to set
	 */
	public void setAllcSubCatgTargetList(List<LookUp> allcSubCatgTargetList) {
		this.allcSubCatgTargetList = allcSubCatgTargetList;
	}

	/**
	 * @return the cbboMasterList
	 */
	public List<CBBOMasterDto> getCbboMasterList() {
		return cbboMasterList;
	}

	/**
	 * @param cbboMasterList the cbboMasterList to set
	 */
	public void setCbboMasterList(List<CBBOMasterDto> cbboMasterList) {
		this.cbboMasterList = cbboMasterList;
	}

	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		BlockAllocationDto oldMasDto = getBlockAllocationDto();
		BlockAllocationDto newMasDto = getNewBlockAllocationDto();
		if (oldMasDto.getStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			newMasDto.getBlockDetailDto().forEach(dto -> {
				dto.setUpdatedBy(createdBy);
				dto.setUpdatedDate(newDate);
				dto.setOrgId(orgId);
				dto.setLgIpMac(lgIp);
				dto.setStatus("C");
			});
		} else {
			oldMasDto.getBlockDetailDto().forEach(e -> {
				e.setUpdatedBy(createdBy);
				e.setUpdatedDate(newDate);
				e.setOrgId(orgId);
				e.setLgIpMac(lgIp);
				if (e.getStatus() != null && e.getStatus().equals(MainetConstants.FlagI))
				e.setStatus("A");
			});
			
			newMasDto.getBlockDetailDto().forEach(dto -> {
				dto.setUpdatedBy(createdBy);
				dto.setUpdatedDate(newDate);
				dto.setOrgId(orgId);
				dto.setLgIpMac(lgIp);
				if (dto.getStatus() != null && dto.getStatus().equals(MainetConstants.FlagC))
				dto.setStatus("I");
			});
		}
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
		getWorkflowActionDto().setDecision(oldMasDto.getStatus());

		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setIsFinalApproval(true);
		taskAction.setComments(blockAllocationDto.getAuthRemark());
		taskAction.setDecision(oldMasDto.getStatus());
		taskAction.setApplicationId(oldMasDto.getApplicationId());
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}

		alocOfBlocksService.updateApprovalStatusAndRemark(newMasDto, oldMasDto, this);
		this.setSuccessMessage(
				(getAppSession().getMessage("sfac.change.block.approved.msg")) + " " + oldMasDto.getApplicationId());
		return true;

	}

}
