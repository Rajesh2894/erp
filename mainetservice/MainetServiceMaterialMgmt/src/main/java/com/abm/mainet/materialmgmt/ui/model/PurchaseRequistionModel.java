package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.IPurchaseRequistionService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;


@Component
@Scope("session")
public class PurchaseRequistionModel extends AbstractFormModel {
	
	private static final long serialVersionUID = -539816885981645321L;
	
	private PurchaseRequistionDto purchaseRequistionDto = new PurchaseRequistionDto();
		
	List<StoreMasterDTO> storeNameList =new ArrayList<StoreMasterDTO>();
	
	List<ItemMasterDTO> itemNameList=new ArrayList<>();
	
	List<PurchaseRequistionDto> purchaseRequistionList= new ArrayList<>();
	
	Map<Long, String> vendorCodeMap =new HashMap<>();
	
	Map<Long, String> prNoMap=new HashMap<>();
	
	private String successFlag;

    private String saveMode;
    
	private String isTenderViewEstimate;

	private String cpdMode;
	private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
	private List<TbFinancialyear> faYears = new ArrayList<>();
	private String formMode;
	private String removeDetailIds;
	private String removeYearIds;
	private Long locationId;

	private long levelCheck;
    
    private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();

	private List<Object[]> storeIdNameList;

	private List<Object[]> itemIdNameList;
	
	@Autowired
	private DepartmentService departmentService;
    
    @Autowired
    private IPurchaseRequistionService purchaseRequistionService;
    
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;
    	
	@Autowired
	private TbFinancialyearService financialyearService;
    
    @Resource
    private ServiceMasterService serviceMasterService;
    
    @Resource
    private IWorkflowRequestService workflowRequestService;
	
    @SuppressWarnings("deprecation")
	@Override
    public boolean saveForm() {
    	List<Long> removeYearIds = new ArrayList<>();
    	List<Long> removeDetailsIds =  new ArrayList<>();

    	if(0L == this.levelCheck && MainetConstants.FlagP != purchaseRequistionDto.getStatus()) {
			purchaseRequistionDto.setStatus(MainetConstants.FlagD);   //** Save As Draft  */
    		purchaseRequistionDto.setWfFlag(MainetConstants.MENU.FALSE);
    	}
    	purchaseRequistionDto.setUserName(UserSession.getCurrent().getEmployee().getEmpname());
	    if (null == purchaseRequistionDto.getPrId()) {
	    	purchaseRequistionDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			purchaseRequistionDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			purchaseRequistionDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			purchaseRequistionDto.setLmoDate(new Date());
			purchaseRequistionDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			purchaseRequistionDto.setDepartment(departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.STORE));
			purchaseRequistionDto.setRequestedBy(UserSession.getCurrent().getEmployee().getEmpId());
			purchaseRequistionDto.getPurchaseRequistionDetDtoList().forEach(purReqDetails->{
				purReqDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				purReqDetails.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				purReqDetails.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
				purReqDetails.setLmoDate(new Date());
			});
			
			generatePurchaseReuestNo(purchaseRequistionDto);
			
			if(MainetConstants.FlagD.equalsIgnoreCase(purchaseRequistionDto.getStatus()))
					this.setSuccessMessage(getAppSession().getMessage("material.management.draft.submit")+ " " + purchaseRequistionDto.getPrNo());
			else
				this.setSuccessMessage(getAppSession().getMessage("material.management.Proceed.for.Approval") + " " + purchaseRequistionDto.getPrNo());
		} else {			
			purchaseRequistionDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			purchaseRequistionDto.setUpdatedDate(new Date());
			purchaseRequistionDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			purchaseRequistionDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			purchaseRequistionDto.getPurchaseRequistionDetDtoList().forEach(purReqDetails->{
				purReqDetails.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				purReqDetails.setUpdatedBy(purReqDetails.getUserId());
				purReqDetails.setUpdatedDate(new Date());
				purReqDetails.setLgIpMacUpd(Utility.getMacAddress());
				purReqDetails.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			});
			
			if (0L == this.getLevelCheck()) {			
				final String yearIds = getRemoveYearIds();
				if (null != yearIds && !yearIds.isEmpty()) {
	                final String array[] = yearIds.split(MainetConstants.operator.COMMA);
	                for (final String string : array)
	                	removeYearIds.add(Long.valueOf(string));
	            }
				final String  detilIds   =getRemoveDetailIds();
				if (null != detilIds && !detilIds.isEmpty()) {
	                final String array[] = detilIds.split(MainetConstants.operator.COMMA);
	                for (final String string : array)
	                	removeDetailsIds.add(Long.valueOf(string));
	            }
				this.setSuccessMessage(getAppSession().getMessage("material.management.Proceed.for.Approval") + " " + purchaseRequistionDto.getPrNo());
			} else {
				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision())) 
					setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.application.rejected.success"));
				else 
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("application.Approved.Successfully"));
			}
		}
	    
		purchaseRequistionService.purchaseReqSave(purchaseRequistionDto, this.levelCheck, removeDetailsIds, removeYearIds, 
									this.getWorkflowActionDto());
		
		if( 1L == this.levelCheck ) 
			setWorkFlowStatus();

	    return true;
    }

    private void generatePurchaseReuestNo(PurchaseRequistionDto purchaseRequistionDto) {    	
    	FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final String year[] = finacialYear.split(MainetConstants.operator.HIPHEN);
	    final String finYear = year[0].substring(2) + year[1];
	    final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetMMConstants.MmItemMaster.STR, MainetMMConstants.MmItemMaster.MM_PURREQTABLE, 
	    		MainetMMConstants.MmItemMaster.MM_STORECODE, UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
        purchaseRequistionDto.setPrNo("PR"+MainetMMConstants.MmItemMaster.STR+finYear+ String.format(MainetConstants.LOI.LOI_NO_FORMAT, sequence));
    }
        
    private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
        this.getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + " " + UserSession.getCurrent().getEmployee().getEmplname());
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
		this.getWorkflowActionDto().setReferenceId(this.getPurchaseRequistionDto().getPrNo());
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowRequest workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
				getWorkflowActionDto().getReferenceId(), orgId);		
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED))			
		 	purchaseRequistionService.updatePurchaseStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.FlagR);
		else if (workflowRequest != null  && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED))
			purchaseRequistionService.updatePurchaseStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.FlagA);	
	}
	

	public PurchaseRequistionDto getPurchaseRequistionDto() {
		return purchaseRequistionDto;
	}

	public void setPurchaseRequistionDto(PurchaseRequistionDto purchaseRequistionDto) {
		this.purchaseRequistionDto = purchaseRequistionDto;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<StoreMasterDTO> getStoreNameList() {
		return storeNameList;
	}


	public void setStoreNameList(List<StoreMasterDTO> storeNameList) {
		this.storeNameList = storeNameList;
	}

	public List<ItemMasterDTO> getItemNameList() {
		return itemNameList;
	}

	public void setItemNameList(List<ItemMasterDTO> itemNameList) {
		this.itemNameList = itemNameList;
	}

	public List<PurchaseRequistionDto> getPurchaseRequistionList() {
		return purchaseRequistionList;
	}

	public void setPurchaseRequistionList(List<PurchaseRequistionDto> purchaseRequistionList) {
		this.purchaseRequistionList = purchaseRequistionList;
	}

	public Map<Long, String> getVendorCodeMap() {
		return vendorCodeMap;
	}

	public void setVendorCodeMap(Map<Long, String> vendorCodeMap) {
		this.vendorCodeMap = vendorCodeMap;
	}

	public Map<Long, String> getPrNoMap() {
		return prNoMap;
	}

	public void setPrNoMap(Map<Long, String> prNoMap) {
		this.prNoMap = prNoMap;
	}

	public String getCpdMode() {
		return cpdMode;
	}

	public void setCpdMode(String cpdMode) {
		this.cpdMode = cpdMode;
	}

	public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
		this.budgetList = budgetList;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public String getFormMode() {
		return formMode;
	}

	public void setFormMode(String formMode) {
		this.formMode = formMode;
	}

	public String getRemoveDetailIds() {
		return removeDetailIds;
	}

	public void setRemoveDetailIds(String removeDetailIds) {
		this.removeDetailIds = removeDetailIds;
	}

	public String getRemoveYearIds() {
		return removeYearIds;
	}

	public void setRemoveYearIds(String removeYearIds) {
		this.removeYearIds = removeYearIds;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getIsTenderViewEstimate() {
		return isTenderViewEstimate;
	}

	public void setIsTenderViewEstimate(String isTenderViewEstimate) {
		this.isTenderViewEstimate = isTenderViewEstimate;
	}

	public long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(long levelCheck) {
		this.levelCheck = levelCheck;
	}

    public WorkflowTaskAction getWorkflowActionDto() {
		return workflowActionDto;
	}

	public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
		this.workflowActionDto = workflowActionDto;
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
}
