package com.abm.mainet.materialmgmt.ui.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;

import com.abm.mainet.common.master.service.TbAcVendormasterService;

import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.DeptReturnDTO;
import com.abm.mainet.materialmgmt.dto.IndentIssueDto;
import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;
import com.abm.mainet.materialmgmt.dto.IndentProcessItemDto;
import com.abm.mainet.materialmgmt.service.DepartmentalReturnService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;


@Component
@Scope("session")
public class DeptReturnModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private IndentProcessDTO indentProcessDTO = new IndentProcessDTO();

	private IndentProcessItemDto indentProcessItemDTO = new IndentProcessItemDto();

	private List<IndentProcessDTO> listIndentProcessDTO = new ArrayList<>();

    List<Department> departmentList =new ArrayList<>();
	
	private boolean lastChecker;

	private long levelcheck;

	private String saveMode;

	private int indexCount;
	
	private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();

	private List<BinLocMasDto> binLocList = new ArrayList<>();

	

	private List<IndentIssueDto> listIndentIssueDtos = new ArrayList<>();

	private IndentIssueDto indentIssueDto = new IndentIssueDto();
	
	private List<Object[]> employees;
	
	private List<Object[]> storeIdNameList;

	private List<Object[]> itemIdNameList;
	
	private DeptReturnDTO deptReturnDto= new DeptReturnDTO();
	
	private List<DeptReturnDTO> listDeptReturnDTO = new ArrayList<>();
	 private String completedFlag;
	 private String approvalLastFlag;
	
	
		
	@Autowired
	private DepartmentalReturnService departmentalReturnService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private IWorkflowRequestService iWorkflowRequestService;
	

	
	@Override
	public boolean saveForm() {
		boolean status = false;

		if (0L != this.levelcheck && !this.lastChecker)
			validateBean(this.getWorkflowActionDto(), CheckerActionValidator.class);
		if (hasValidationErrors())
			return false;
		else {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long empID = UserSession.getCurrent().getEmployee().getEmpId();
			Date sysDate = new Date();
			Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
			String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();

			if (null == deptReturnDto.getDreturnid()) {
				deptReturnDto.setOrgid(orgId);
				deptReturnDto.setLgIpMac(macAddr);
				deptReturnDto.setLangId(langId);
				deptReturnDto.setUserid(empID);
				deptReturnDto.setLmoddate(sysDate);
				deptReturnDto.setStatus(MainetConstants.CommonConstants.CHAR_Y); /** N==Pending For Approval */
				deptReturnDto.setWfFlag(MainetConstants.WorkFlow.Status.PENDING);

				deptReturnDto.getDeptItemDetailsDTOList().forEach(dto -> {
					dto.setOrgid(orgId);
					dto.setUserid(empID);
					dto.setLgIpMac(macAddr);
					dto.setLangId(langId);
					dto.setLmoddate(sysDate);
					dto.setStatus(MainetConstants.CommonConstants.CHAR_Y);

				});

				generateDeptReturnNo();
			} else {
				deptReturnDto.setUpdatedBy(empID);
				deptReturnDto.setUpdatedDate(sysDate);
				deptReturnDto.setLgIpMacUpd(macAddr);

				deptReturnDto.getDeptItemDetailsDTOList().forEach(dto -> {
					dto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
					dto.setUpdatedBy(empID);
					dto.setUpdatedDate(sysDate);
					dto.setLgIpMacUpd(macAddr);
					dto.setLangId(langId);
				});

				if (this.lastChecker) {
					this.getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
					this.getWorkflowActionDto().setComments("AUTO APPROVED");
				}
			}
			

			if (this.getLevelcheck() >= 1L) {
				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
					setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.application.rejected.success"));
				else if (this.lastChecker)
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.indent.return.msg"));
				else
					this.setSuccessMessage(
							ApplicationSession.getInstance().getMessage("application.Approved.Successfully"));
			}

			departmentalReturnService.saveDepartmentalReturn(deptReturnDto, this.levelcheck,
					this.getWorkflowActionDto());
			status = true;
			if (0L != this.levelcheck)
				setWorkFlowStatus();
			if (0L == this.levelcheck) {
				this.setSuccessMessage(ApplicationSession.getInstance().getMessage("department.indent.message.indent")
						+ MainetConstants.WHITE_SPACE + deptReturnDto.getDreturnno() + MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("department.indent.message.item.indent.return"));
			}
			
		}

		return status;
	}
	
	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		this.getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE 
				+ UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(this.getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;
	}
	
	
	private void generateDeptReturnNo() {
		
		String date = CommonUtility.dateToString(new Date());
		
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("MMM", "mm_deptreturn", "dreturnid", 
									UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String DepReturnNo =MainetConstants.MaterialManagement.DeptIndentShortCODE+MainetConstants.SLASH+ MainetConstants.PROJECT_SHORTCODE.PRAYAGRAJ +  MainetConstants.SLASH  + MainetMMConstants.MmItemMaster.STR+MainetConstants.SLASH
				 + date +MainetConstants.SLASH+ String.format(MainetConstants.CommonMasterUi.PADDING_THREE, sequence);
		deptReturnDto.setDreturnno(DepReturnNo);
		
	}
	
	private void setWorkFlowStatus() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getWorkflowActionDto().setReferenceId(this.deptReturnDto.getDreturnno());		
		WorkflowRequest workflowRequest = iWorkflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
				this.getWorkflowActionDto().getReferenceId(), orgId);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			departmentalReturnService.updateIndentReturnStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.CommonConstants.CHAR_R, MainetConstants.WorkFlow.Status.CLOSED);  /** R==Rejected */
			departmentalReturnService.updateIndentItemReturnStatus(orgId, this.deptReturnDto.getDreturnid(), MainetConstants.CommonConstants.CHAR_N);
		}

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.PENDING)&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED )) {
			departmentalReturnService.updateIndentReturnStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.CommonConstants.CHAR_P, MainetConstants.WorkFlow.Status.PENDING); /** P==Pending For Return, Prefix: IDS */
			departmentalReturnService.updateIndentItemReturnStatus(orgId, this.deptReturnDto.getDreturnid(), MainetConstants.CommonConstants.CHAR_Y);
		}
		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) { //on issue complete WF_flag
			departmentalReturnService.updateIndentReturnStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.CommonConstants.CHAR_A,  MainetConstants.WorkFlow.Status.CLOSED); /** A==Return */
			departmentalReturnService.updateIndentItemReturnStatus(orgId, this.deptReturnDto.getDreturnid(), MainetConstants.CommonConstants.CHAR_Y);
		}
	}
	
	
	
	
	public IndentProcessDTO getIndentProcessDTO() {
		return indentProcessDTO;
	}

	public void setIndentProcessDTO(IndentProcessDTO indentProcessDTO) {
		this.indentProcessDTO = indentProcessDTO;
	}

	public List<IndentProcessDTO> getListIndentProcessDTO() {
		return listIndentProcessDTO;
	}

	public void setListIndentProcessDTO(List<IndentProcessDTO> listIndentProcessDTO) {
		this.listIndentProcessDTO = listIndentProcessDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public WorkflowTaskAction getWorkflowActionDto() {
		return workflowActionDto;
	}

	public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
		this.workflowActionDto = workflowActionDto;
	}

	public IndentProcessItemDto getIndentProcessItemDTO() {
		return indentProcessItemDTO;
	}

	public void setIndentProcessItemDTO(IndentProcessItemDto indentProcessItemDTO) {
		this.indentProcessItemDTO = indentProcessItemDTO;
	}

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	

	public List<IndentIssueDto> getListIndentIssueDtos() {
		return listIndentIssueDtos;
	}

	public void setListIndentIssueDtos(List<IndentIssueDto> listIndentIssueDtos) {
		this.listIndentIssueDtos = listIndentIssueDtos;
	}

	public IndentIssueDto getIndentIssueDto() {
		return indentIssueDto;
	}

	public void setIndentIssueDto(IndentIssueDto indentIssueDto) {
		this.indentIssueDto = indentIssueDto;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public List<Object[]> getEmployees() {
		return employees;
	}


	public void setEmployees(List<Object[]> employees) {
		this.employees = employees;
	}


	public List<Object[]> getStoreIdNameList() {
		return storeIdNameList;
	}


	public void setStoreIdNameList(List<Object[]> storeIdNameList) {
		this.storeIdNameList = storeIdNameList;
	}


	public List<Object[]> getItemIdNameList() {
		return itemIdNameList;
	}


	public void setItemIdNameList(List<Object[]> itemIdNameList) {
		this.itemIdNameList = itemIdNameList;
	}

	public DeptReturnDTO getDeptReturnDto() {
		return deptReturnDto;
	}

	public void setDeptReturnDto(DeptReturnDTO deptReturnDto) {
		this.deptReturnDto = deptReturnDto;
	}

	public List<DeptReturnDTO> getListDeptReturnDTO() {
		return listDeptReturnDTO;
	}

	public void setListDeptReturnDTO(List<DeptReturnDTO> listDeptReturnDTO) {
		this.listDeptReturnDTO = listDeptReturnDTO;
	}

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}

	public String getApprovalLastFlag() {
		return approvalLastFlag;
	}

	public void setApprovalLastFlag(String approvalLastFlag) {
		this.approvalLastFlag = approvalLastFlag;
	}
	

}
