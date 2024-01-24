package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.ExpiryItemDetailsDto;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.ExpirtyItemService;
import com.abm.mainet.materialmgmt.ui.validator.ExpiryItemValidator;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class ExpiryItemsEntryModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();

	List<StoreMasterDTO> storeNameList = new ArrayList<StoreMasterDTO>();

	List<ExpiryItemDetailsDto> expiryItemDetailsDto = new ArrayList<>();

	List<ExpiryItemsDto> expiryItemDtoList = new ArrayList<>();

	private List<TbAcVendormaster> vendors = new ArrayList<>();
	
	List<ItemMasterDTO> itemNameList=new ArrayList<>();
		
	List<BinLocMasDto> binLocList = new ArrayList<>();
	
	private boolean lastChecker;

	private long levelCheck;

	List<Employee> empList=new ArrayList<>();

	private String saveMode;

	Map<Long, String> bankAccountMap = new HashMap<>();
	
	List<BankMasterEntity> customerBankList = new ArrayList<>();
	
	TbServiceReceiptMasBean tbServiceReceiptMas = new TbServiceReceiptMasBean();

	private List<DocumentDetailsVO> checkList;

	private String isTenderViewEstimate;

	
	@Autowired
    private IChallanService iChallanService;
	
	@Autowired
	private ExpirtyItemService expirtyItemService;

	@Autowired
	private TbAcVendormasterService vendorService;

	@Autowired
	private ApplicationService applicationService;

    @Autowired
    private TbTaxMasService tbTaxMasService;
    
	@Autowired
	private DepartmentService departmentService;
    
	private static Logger LOGGER = Logger.getLogger(ExpiryItemsEntryModel.class);

	@Override
	public boolean saveForm() {
		boolean status = false;
		
		CommonChallanDTO offline = getOfflineDTO();
		
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("DSA", UserSession.getCurrent().getOrganisation().getOrgid());
		
		if( 0L == this.levelCheck ) 
			validateBean(expiryItemsDto, ExpiryItemValidator.class);
		if( 1L == this.levelCheck ) 
				validateBean(this.getWorkflowActionDto(), CheckerActionValidator.class);
		if(hasValidationErrors()) 
			return false;
		else {
			if (expiryItemsDto.getExpiryId() == null) {
				expiryItemsDto.setDepartment(departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.STORE));
				expiryItemsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				expiryItemsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				expiryItemsDto.setLangId(UserSession.getCurrent().getLanguageId());
				expiryItemsDto.setLgIpMac(Utility.getMacAddress());
				expiryItemsDto.setLmodDate(new Date());
				expiryItemsDto.setMovementBy(UserSession.getCurrent().getEmployee().getEmpId());
				expiryItemsDto.getExpiryItemDetailsDtoList().forEach(expiryItemDet->{
					if (null != expiryItemDet.getFlag() && expiryItemDet.getFlag().equalsIgnoreCase(MainetConstants.FlagY)) {
						expiryItemDet.setFlag(MainetConstants.Y_FLAG);
						expiryItemDet.setDisposedFlag(MainetConstants.N_FLAG);
						expiryItemDet.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
						expiryItemDet.setLangId(UserSession.getCurrent().getLanguageId());
						expiryItemDet.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
						expiryItemDet.setLmodDate(new Date());
						expiryItemDet.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						
						expiryItemDetailsDto.add(expiryItemDet);						
					}
				});
				expiryItemsDto.setExpiryItemDetailsDtoList(expiryItemDetailsDto);

				RequestDTO requestDto = setApplicantRequestDto(expiryItemsDto, service);
				expiryItemsDto.setApplicationId(applicationService.createApplication(requestDto));			
				genrateMovementNo(service, expiryItemsDto);
				expiryItemsDto.setStatus(MainetConstants.Y_FLAG);				
			}else {
				expiryItemsDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				expiryItemsDto.setLgIpMacUpd(Utility.getMacAddress());
				expiryItemsDto.setUpdatedDate(new Date());
				expiryItemsDto.getExpiryItemDetailsDtoList().forEach(expiryItemDet->{
					if(MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
						expiryItemDet.setFlag(MainetConstants.N_FLAG);
					if(!this.lastChecker) 
						expiryItemDet.setDisposedFlag(MainetConstants.Y_FLAG);
					expiryItemDet.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					expiryItemDet.setUpdatedDate(new Date());					
					expiryItemDetailsDto.add(expiryItemDet);						
				});
				if (this.lastChecker) {
					if (MainetConstants.WorkFlow.Decision.APPROVED.equals(this.getWorkflowActionDto().getDecision()))
						genrateScrapMovementNo(service);
				} else {
					if (MainetConstants.CommonConstants.CHAR_Y == expiryItemsDto.getPaymentFlag())
						setAndSaveChallanDto(offline, expiryItemsDto, service);
					expiryItemsDto.setStatus(MainetConstants.FlagD);
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.disposal.receipt.success"));
				}
			}
			
			if (this.getLevelCheck() >= 1L) {
				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision())) 
					setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.application.rejected.success"));
			}
			expirtyItemService.saveExpirtyItem(expiryItemsDto, offline, this.levelCheck, this.getWorkflowActionDto(), setApplicantDetailDTOData(service));
			if( 1L == this.levelCheck ) 
				setWorkFlowStatusOnVendor();
			
			status = true;
		}		
		return status;
	}
	
		
	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		this.getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + " "
				+ UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setApplicationId(this.getWorkflowActionDto().getApplicationId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;
	}
	
	
	private void setWorkFlowStatusOnVendor() {		
		this.getWorkflowActionDto().setApplicationId(this.expiryItemsDto.getApplicationId());

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class).findByApplicationId(this.getWorkflowActionDto().getApplicationId());

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			expirtyItemService.updateDispoalStatus(UserSession.getCurrent().getOrganisation().getOrgid(),
					this.getWorkflowActionDto().getApplicationId(), MainetConstants.FlagR);
		}	
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)) {
			expirtyItemService.updateDispoalStatus(UserSession.getCurrent().getOrganisation().getOrgid(),
					this.getWorkflowActionDto().getApplicationId(), MainetConstants.FlagA);
		}
	}
	
	
	private ApplicantDetailDTO setApplicantDetailDTOData(ServiceMaster serviceMas) {
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		applicantDto.setApplicantFirstName(UserSession.getCurrent().getEmployee().getEmpname());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		return applicantDto;
	}
	
	private RequestDTO setApplicantRequestDto(ExpiryItemsDto expiryItemsDto, ServiceMaster service) {
	  	RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(service.getSmServiceId());
		requestDto.setUserId(expiryItemsDto.getUserId());
		requestDto.setOrgId(expiryItemsDto.getOrgId());
		requestDto.setLangId((long) expiryItemsDto.getLangId());
		requestDto.setDeptId(service.getTbDepartment().getDpDeptid());
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		requestDto.setFree(true);
		return requestDto;
	}
	
	
	private void genrateMovementNo(ServiceMaster service, ExpiryItemsDto expiryItemsDto2) {		
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo("MMM", "MM_EXPIRED", "expiryid", UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String movementNO = "EID" + MainetMMConstants.MmItemMaster.STR + finacialYear + String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		expiryItemsDto.setMovementNo(movementNO);	
		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.disposal.success.with.movement.no")+ 
				MainetConstants.WHITE_SPACE + expiryItemsDto.getMovementNo() + MainetConstants.WHITE_SPACE +
				ApplicationSession.getInstance().getMessage("material.management.application.no") + MainetConstants.WHITE_SPACE + expiryItemsDto.getApplicationId() );
	}
	
	
	private void genrateScrapMovementNo(ServiceMaster service) {		
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo("MMM", "MM_DISPOSAL", "SCRAPID", UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String scrapNo = "DDS" + MainetMMConstants.MmItemMaster.STR + finacialYear + String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		expiryItemsDto.setScrapNo(scrapNo);
		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.disposal.success.with.scrap.no") 
				+ MainetConstants.WHITE_SPACE + expiryItemsDto.getScrapNo() + MainetConstants.WHITE_SPACE 
				+ ApplicationSession.getInstance().getMessage("material.management.application.no")	+ MainetConstants.WHITE_SPACE + expiryItemsDto.getApplicationId());
	}
	
	
	/**
	 *  for Receipt Entry
	 */	
	private boolean setAndSaveChallanDto(CommonChallanDTO offline, ExpiryItemsDto expiryItemsDto, ServiceMaster service) {
		offline.setUniquePrimaryId(expiryItemsDto.getExpiryId().toString());
		
		offline.setApplNo(expiryItemsDto.getApplicationId());
		
		offline.setReferenceNo(expiryItemsDto.getScrapNo());
		
		offline.setDeptCode(MainetConstants.DEPT_SHORT_NAME.STORE);
		offline.setAmountToPay(Double.toString(expiryItemsDto.getReceiptAmt()));
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(service.getSmServiceId());
		offline.setApplicantName(vendorService.getVendorNameById(expiryItemsDto.getVendorId(), expiryItemsDto.getOrgId()));			
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		Organisation organisation =UserSession.getCurrent().getOrganisation();
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.STORE);

		final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                organisation);
        final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
                organisation);

        List<TbTaxMas> indepenTaxList = tbTaxMasService.fetchAllIndependentTaxes(organisation.getOrgid(), deptId,
                taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
		
		if (CollectionUtils.isEmpty(indepenTaxList)) {
			addValidationError(ApplicationSession.getInstance().getMessage("EChallan.taxMasterNotConfigured"));
			return false;
		}

		offline.getFeeIds().put(indepenTaxList.get(0).getTaxId(), expiryItemsDto.getReceiptAmt());

		offline.setWorkflowEnable(MainetConstants.FlagN);
		if (CollectionUtils.isNotEmpty(getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(getClientIpAddress());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(deptId);
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {
			final ChallanMaster responseChallan = iChallanService.InvokeGenerateChallan(offline);
			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());
			setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, service.getSmServiceName());
			setReceiptDTO(printDto);
			setSuccessMessage(getAppSession().getMessage("chn.receipt"));
		}
		return true;
	}
	
	
	

	public ExpiryItemsDto getExpiryItemsDto() {
		return expiryItemsDto;
	}

	public void setExpiryItemsDto(ExpiryItemsDto expiryItemsDto) {
		this.expiryItemsDto = expiryItemsDto;
	}

	public List<StoreMasterDTO> getStoreNameList() {
		return storeNameList;
	}

	public void setStoreNameList(List<StoreMasterDTO> storeNameList) {
		this.storeNameList = storeNameList;
	}

	public List<ExpiryItemDetailsDto> getExpiryItemDetailsDto() {
		return expiryItemDetailsDto;
	}

	public void setExpiryItemDetailsDto(List<ExpiryItemDetailsDto> expiryItemDetailsDto) {
		this.expiryItemDetailsDto = expiryItemDetailsDto;
	}

	public List<ExpiryItemsDto> getExpiryItemDtoList() {
		return expiryItemDtoList;
	}

	public void setExpiryItemDtoList(List<ExpiryItemsDto> expiryItemDtoList) {
		this.expiryItemDtoList = expiryItemDtoList;
	}

	public List<TbAcVendormaster> getVendors() {
		return vendors;
	}

	public void setVendors(List<TbAcVendormaster> vendors) {
		this.vendors = vendors;
	}

	public List<ItemMasterDTO> getItemNameList() {
		return itemNameList;
	}

	public void setItemNameList(List<ItemMasterDTO> itemNameList) {
		this.itemNameList = itemNameList;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}

	public long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<BankMasterEntity> getCustomerBankList() {
		return customerBankList;
	}

	public void setCustomerBankList(List<BankMasterEntity> customerBankList) {
		this.customerBankList = customerBankList;
	}

	public Map<Long, String> getBankAccountMap() {
		return bankAccountMap;
	}

	public void setBankAccountMap(Map<Long, String> bankAccountMap) {
		this.bankAccountMap = bankAccountMap;
	}

	public TbServiceReceiptMasBean getTbServiceReceiptMas() {
		return tbServiceReceiptMas;
	}

	public void setTbServiceReceiptMas(TbServiceReceiptMasBean tbServiceReceiptMas) {
		this.tbServiceReceiptMas = tbServiceReceiptMas;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getIsTenderViewEstimate() {
		return isTenderViewEstimate;
	}

	public void setIsTenderViewEstimate(String isTenderViewEstimate) {
		this.isTenderViewEstimate = isTenderViewEstimate;
	}
	

}
