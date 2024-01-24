package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.materialmgmt.dto.StoreIndentDto;
import com.abm.mainet.materialmgmt.service.IStoreIndentService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class StoreIndentModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private StoreIndentDto storeIndentDto = new StoreIndentDto();

	private List<StoreIndentDto> storeIndentDtoList = new ArrayList<>();

	private List<Object[]> storeIdNameList;

	private List<Object[]> itemIdNameList;

	private String saveMode;

	private static Logger LOGGER = Logger.getLogger(StoreIndentModel.class);

	private long levelCheck;

	@Resource
	private IStoreIndentService storeIndentService;

	@Resource
	private TbFinancialyearService financialyearService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Resource
	private IWorkflowRequestService iWorkflowRequestService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
		
	
	@Override
	public boolean saveForm() {
		boolean status = false;

		if ( 1L == this.levelCheck )
			validateBean(this.getWorkflowActionDto(), CheckerActionValidator.class);
		if (hasValidationErrors())
			return false;
		else {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long userId = UserSession.getCurrent().getEmployee().getEmpId();
			Date sysDate = new Date();
			Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
			String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();
			
			if (null == storeIndentDto.getStoreIndentId()) {
				storeIndentDto.setOrgId(orgId);
				storeIndentDto.setUserId(userId);
				storeIndentDto.setUserName(UserSession.getCurrent().getEmployee().getEmpname());
				storeIndentDto.setCreatedDate(sysDate);
				storeIndentDto.setLangId(langId);
				storeIndentDto.setLgIpMac(macAddr);
				storeIndentDto.setWfFlag(MainetConstants.WorkFlow.Status.PENDING);
				storeIndentDto.setStatus(MainetConstants.FlagN);  /** N == Pending for Approval, Prefix: IDS */
				
				storeIndentDto.getStoreIndentItemDtoList().forEach(indentItem -> {
					indentItem.setOrgId(orgId);
					indentItem.setUserId(userId);
					indentItem.setCreatedDate(sysDate);
					indentItem.setLangId(langId);
					indentItem.setLgIpMac(macAddr);
					indentItem.setStatus(MainetConstants.CommonConstants.CHAR_Y);
				});
				
				genrateStoreIndentNo(storeIndentDto);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.store.indent.save.success") 
						+ MainetConstants.WHITE_SPACE + storeIndentDto.getStoreIndentNo());
			} else {
				storeIndentDto.setOrgId(orgId);
				storeIndentDto.setUpdatedBy(userId);
				storeIndentDto.setUpdatedDate(sysDate);
				storeIndentDto.setLgIpMacUpd(macAddr);

				storeIndentDto.getStoreIndentItemDtoList().forEach(indentItem -> {
					indentItem.setOrgId(orgId);
					indentItem.setUpdatedBy(userId);
					indentItem.setUpdatedDate(sysDate);
					indentItem.setLgIpMacUpd(macAddr);
				});
							
				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
					setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.application.rejected.success"));
				else
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("application.Approved.Successfully"));
			}

			storeIndentService.saveStoreIndentData(storeIndentDto);
			
			ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(
					MainetConstants.MaterialManagement.STORE_Indent_SERVICE_CODE, storeIndentDto.getOrgId());
			if(0L == levelCheck)
				storeIndentService.initializeWorkFlowForFreeService(storeIndentDto, serviceMas);
			else if( 1L == levelCheck) {
				storeIndentService.updateWorkFlowService(this.getWorkflowActionDto(), serviceMas);
				setWorkFlowStatus();
			}
			
			status = true;			
		}
		return status;
	}

	private void genrateStoreIndentNo(StoreIndentDto storeIndentDto) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(storeIndentDto.getCreatedDate());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("MMM", "mm_storeindent", "siid",
				storeIndentDto.getOrgId(), MainetConstants.FlagF, null);
		String storeIndentNo = "SI" + MainetMMConstants.MmItemMaster.STR + finacialYear
				+ String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		storeIndentDto.setStoreIndentNo(storeIndentNo);
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
	
	
	private void setWorkFlowStatus() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getWorkflowActionDto().setReferenceId(this.storeIndentDto.getStoreIndentNo());		
		WorkflowRequest workflowRequest = iWorkflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
				this.getWorkflowActionDto().getReferenceId(), orgId);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			storeIndentService.updateStoreIndentStatus(orgId, this.getWorkflowActionDto().getReferenceId(),
					MainetConstants.FlagR, MainetConstants.WorkFlow.Status.CLOSED); /** R==Rejected, Prefix: IDS */

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			storeIndentService.updateStoreIndentStatus(orgId, this.getWorkflowActionDto().getReferenceId(),
					MainetConstants.FlagP, MainetConstants.WorkFlow.Status.CLOSED); /** P == Pending For Issuance, Prefix: IDS */
	}

	public StoreIndentDto getStoreIndentDto() {
		return storeIndentDto;
	}

	public void setStoreIndentDto(StoreIndentDto storeIndentDto) {
		this.storeIndentDto = storeIndentDto;
	}

	public List<StoreIndentDto> getStoreIndentDtoList() {
		return storeIndentDtoList;
	}

	public void setStoreIndentDtoList(List<StoreIndentDto> storeIndentDtoList) {
		this.storeIndentDtoList = storeIndentDtoList;
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

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	public long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}