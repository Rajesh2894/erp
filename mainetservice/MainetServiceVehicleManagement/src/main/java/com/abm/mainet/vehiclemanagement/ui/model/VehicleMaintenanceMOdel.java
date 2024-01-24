package com.abm.mainet.vehiclemanagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDetDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;
import com.abm.mainet.vehiclemanagement.service.IVehicleMaintenanceService;
import com.abm.mainet.vehiclemanagement.ui.validator.OEMMaintenanceValidator;
import com.abm.mainet.vehiclemanagement.ui.validator.VehicleMaintenanceValidatr;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleMaintenanceMOdel extends AbstractFormModel {

	private static final long serialVersionUID = 1340684254502225688L;

	@Autowired
	private IVehicleMaintenanceService vehicleMaintenanceService;

	private VehicleMaintenanceDTO vehicleMaintenanceDTO = new VehicleMaintenanceDTO();

	private GenVehicleMasterDTO vehicleMasterDTO = new GenVehicleMasterDTO();

	private List<VehicleMaintenanceDTO> vehicleMaintenanceList;

	private List<DocumentDetailsVO> documents = new ArrayList<>();

	@Autowired
	private VehicleMaintenanceValidatr vehicleMaintenanceValidator;
	
	@Resource
	private TbFinancialyearService financialyearService;
	
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	

	private OEMWarrantyDTO oemWarrantyDto = new OEMWarrantyDTO();
	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();
	@Autowired
	private CommonService commonService;

	private String saveMode;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<SLRMEmployeeMasterDTO> drivers;
	List<GenVehicleMasterDTO> vehicles;
	List<TbLocationMas> locations;
	List<Object[]> employees;
	private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
	
	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ILocationMasService locMasService;

	@Resource
	private DepartmentService departmentService;

	private List<TbAcVendormaster> vendors;

	private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();

	private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();

	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();

	private List<LookUp> valueTypeList = new ArrayList<LookUp>();

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;
	
	private long levelcheck;

	private List<Object[]> empList;

	public List<VendorBillExpDetailDTO> getExpDetListDto() {
		return expDetListDto;
	}

	public void setExpDetListDto(List<VendorBillExpDetailDTO> expDetListDto) {
		this.expDetListDto = expDetListDto;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
	}

	public VehicleMaintenanceValidatr getVehicleMaintenanceValidator() {
		return vehicleMaintenanceValidator;
	}

	public void setVehicleMaintenanceValidator(VehicleMaintenanceValidatr vehicleMaintenanceValidator) {
		this.vehicleMaintenanceValidator = vehicleMaintenanceValidator;
	}

	public GenVehicleMasterDTO getVehicleMasterDTO() {
		return vehicleMasterDTO;
	}

	public void setVehicleMasterDTO(GenVehicleMasterDTO vehicleMasterDTO) {
		this.vehicleMasterDTO = vehicleMasterDTO;
	}

	public VehicleMaintenanceDTO getVehicleMaintenanceDTO() {
		return vehicleMaintenanceDTO;
	}

	public void setVehicleMaintenanceDTO(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
		this.vehicleMaintenanceDTO = vehicleMaintenanceDTO;
	}

	public List<VehicleMaintenanceDTO> getVehicleMaintenanceList() {
		return vehicleMaintenanceList;
	}

	public void setVehicleMaintenanceList(List<VehicleMaintenanceDTO> vehicleMaintenanceList) {
		this.vehicleMaintenanceList = vehicleMaintenanceList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<TbAcVendormaster> getVendors() {
		return vendors;
	}

	public void setVendors(List<TbAcVendormaster> vendors) {
		this.vendors = vendors;
	}

	public IVehicleMaintenanceService getVehicleMaintenanceService() {
		return vehicleMaintenanceService;
	}

	public void setVehicleMaintenanceService(IVehicleMaintenanceService vehicleMaintenanceService) {
		this.vehicleMaintenanceService = vehicleMaintenanceService;
	}

	public VendorBillExpDetailDTO getBillExpDetailDTO() {
		return billExpDetailDTO;
	}

	public void setBillExpDetailDTO(VendorBillExpDetailDTO billExpDetailDTO) {
		this.billExpDetailDTO = billExpDetailDTO;
	}

	public List<LookUp> getValueTypeList() {
		return valueTypeList;
	}

	public void setValueTypeList(List<LookUp> valueTypeList) {
		this.valueTypeList = valueTypeList;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public List<Object[]> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Object[]> empList) {
		this.empList = empList;
	}

	private String maintCatLookupCode;
	private String maintApprovalDecision;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean saveForm() {
		boolean status = false;
		ServiceMaster service = null;
		boolean isPsclEnv = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
				MainetConstants.ENV_PSCL);
		if (isPsclEnv) {
			maintCatLookupCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(vehicleMaintenanceDTO.getMaintCategory(), UserSession.getCurrent().getOrganisation().getOrgid(), Constants.VEHICLE_MAINT_CATEGORY).getLookUpCode();

			
			service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(Constants.MAINT_SERVICE_CODE,
							UserSession.getCurrent().getOrganisation().getOrgid());
			
			if (0L == this.levelcheck) {
				boolean checkerForVehicle = vehicleMaintenanceService.isForVehicleWorkFlowInProgress(vehicleMaintenanceDTO.getVeId(),UserSession.getCurrent().getOrganisation().getOrgid());
				if(checkerForVehicle) {
					this.addValidationError(ApplicationSession.getInstance().getMessage("Vehicle.number.already.in.progress.wait.till.exist.from.Maintenance"));
				}	
			}
			if (vehicleMaintenanceDTO.getVemId() != null) {
				if (3 == this.levelcheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_AMC) || 5 == this.levelcheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_WORKSHOP) ) {
						validateBean(oemWarrantyDto, OEMMaintenanceValidator.class);
				}
			}
			if (1L == this.levelcheck || 2L == this.levelcheck || (3L == this.levelcheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_WORKSHOP)) || 4L == this.levelcheck) {
				validateBean(this.getWorkflowActionDto(), CheckerActionValidator.class);
			}
			
		}

		if (hasValidationErrors()) {
			return false;
		} else {
			if (vehicleMaintenanceDTO.getVemId() == null) {
				vehicleMaintenanceDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				vehicleMaintenanceDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				vehicleMaintenanceDTO.setCreatedDate(new Date());
				vehicleMaintenanceDTO.setLgIpMac(Utility.getMacAddress());
				setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicle.maintenance.add.success"));
			} else {
				vehicleMaintenanceDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				vehicleMaintenanceDTO.setLgIpMacUpd(Utility.getMacAddress());
				if (isPsclEnv)
					setMaintCatLookupCode(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(vehicleMaintenanceDTO.getMaintCategory(), vehicleMaintenanceDTO.getOrgid(), Constants.VEHICLE_MAINT_CATEGORY).getLookUpCode());
				if (isPsclEnv && 3 == this.levelcheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_AMC) || 5 == this.levelcheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_WORKSHOP) ) {
					oemWarrantyDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					oemWarrantyDto.setCreatedDate(new Date());
					oemWarrantyDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
					oemWarrantyDto.setVeId(vehicleMaintenanceDTO.getVeId());
					oemWarrantyDto.setVehicleType(vehicleMaintenanceDTO.getVeVetype());
					oemWarrantyDto.setRefNo(vehicleMaintenanceDTO.getRequestNo());
					this.getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
					this.getWorkflowActionDto().setComments("AUTO APPROVED");
					for (OEMWarrantyDetDTO oemDetails : oemWarrantyDto.getTbvmoemwarrantydetails()) {
						oemDetails.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
						oemDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						oemDetails.setCreatedDate(new Date());
						oemDetails.setLgIpMac(Utility.getMacAddress());
						oemDetails.setTboemwarranty(oemWarrantyDto);
					}
				}
				setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicle.maintenance.edit.success"));
			}

			if (isPsclEnv && this.levelcheck == 0L) {
				genrateRefNo(vehicleMaintenanceDTO);
			}
			
			if (isPsclEnv) {
				if (this.getLevelcheck() >= 1L) {
					prepareWorkFlowTaskAction(this.getWorkflowActionDto());
					if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision())) {
						setSuccessMessage(
								ApplicationSession.getInstance().getMessage("NOCBuildingPermission.submit.reject"));
					} else {
						setSuccessMessage(
								ApplicationSession.getInstance().getMessage("NOCBuildingPermission.submit.approve"));
					}
				} else {
					vehicleMaintenanceDTO.setWfStatus(MainetConstants.WorkFlow.Decision.PENDING);
				}				
				vehicleMaintenanceDTO = vehicleMaintenanceService.saveVehicleMaintenance(vehicleMaintenanceDTO, oemWarrantyDto, this.levelcheck,
						this.getWorkflowActionDto(), setApplicantDetailDTOData(service));
				this.setVehicleMaintenanceDTO(vehicleMaintenanceDTO);
				setWorkFlowStatusOnVendor();
				status = true;
			} else {			
				vehicleMaintenanceDTO = vehicleMaintenanceService.saveVehicleMaintenance(vehicleMaintenanceDTO);
				status = true;
			}

			List<Long> removeFileById = null;
			String fileId = vehicleMaintenanceDTO.getRemoveFileById();
			if (fileId != null && !fileId.isEmpty()) {
				removeFileById = new ArrayList<>();
				String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
				for (String fields : fileArray)
					removeFileById.add(Long.valueOf(fields));
			}
			if (removeFileById != null && !removeFileById.isEmpty())
				tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, vehicleMaintenanceDTO.getUpdatedBy());

			FileUploadDTO requestDTO = new FileUploadDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + vehicleMaintenanceDTO.getVemId().toString());
			requestDTO.setDepartmentName(Constants.SHORT_CODE);
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			List<DocumentDetailsVO> dto = getDocuments();
			setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
			setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
			fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
			status = true;
			int i = 0;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				getDocuments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
				i++;
			}
		}
		return status;
	}
	
	private void saveBillApprval(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
		ResponseEntity<?> responseEntity = null;
		List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
		VendorBillDedDetailDTO billDedDetailDTO = new VendorBillDedDetailDTO();
		deductionDetList.add(billDedDetailDTO);
		approvalDTO.setBillEntryDate(Utility.dateToString(new Date()));
		approvalDTO.setBillTypeId(
				CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("MI", "ABT", vehicleMaintenanceDTO.getOrgid()));
		approvalDTO.setOrgId(vehicleMaintenanceDTO.getOrgid());
		approvalDTO.setNarration("Bill Against MB no -" + "Maintenance Tax");
		approvalDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		approvalDTO.setCreatedDate(Utility.dateToString(new Date()));
		approvalDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
		approvalDTO.setVendorId(vehicleMaintenanceDTO.getVendorId());
		approvalDTO.setInvoiceNumber(vehicleMaintenanceDTO.getVemReceiptno().toString());
		approvalDTO.setInvoiceAmount(vehicleMaintenanceDTO.getVemCostincurred());
		billExpDetailDTO.setBudgetCodeId(vehicleMaintenanceDTO.getExpenditureId());
		billExpDetailDTO.setAmount(vehicleMaintenanceDTO.getVemCostincurred());
		billExpDetailDTO.setSanctionedAmount(vehicleMaintenanceDTO.getVemCostincurred());
		expDetListDto.add(billExpDetailDTO);
		approvalDTO.setExpDetListDto(expDetListDto);
		billDedDetailDTO.setBchId(vehicleMaintenanceDTO.getExpenditureId());
		billDedDetailDTO.setBudgetCodeId(vehicleMaintenanceDTO.getDedAcHeadId());
		billDedDetailDTO.setDeductionAmount(vehicleMaintenanceDTO.getDedAmt());
		deductionDetList.add(billDedDetailDTO);
		approvalDTO.setDedDetListDto(deductionDetList);
		long fieldId = 0;
		if (UserSession.getCurrent().getLoggedLocId() != null) {
			final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
			if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
				fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
			}
		}
		if (fieldId == 0) {
			throw new NullPointerException("fieldId is not linked with Location Master for[locId="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
		}
		approvalDTO.setFieldId(fieldId);
		Department entities = departmentService.getDepartment(Constants.SHORT_CODE,
				MainetConstants.CommonConstants.ACTIVE);
		approvalDTO.setDepartmentId(entities.getDpDeptid());
		try {
			responseEntity = RestClient.callRestTemplateClient(approvalDTO, ServiceEndpoints.SALARY_POSTING);
			if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				// bookService.updateBillNumberByMbId(responseEntity.getBody().toString(),
				// workMbId);
				// setWorkBillNumber(responseEntity.getBody().toString());
			}
		} catch (Exception exception) {

			throw new FrameworkException("error occured while bill posting to account module ", exception);
		}
	}

	@Transactional
	private void initializeWorkFlowForService(VehicleMaintenanceDTO vehicleMaintenanceDTO, ServiceMaster serviceMas) {
		// boolean checkList = false;
		// ServiceMaster serviceMas =
		// iServiceMasterDAO.getServiceMasterByShortCode("PRA",purchaseRequistionDto.getOrgId());
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(UserSession.getCurrent().getEmployee().getEmpname());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		applicationMetaData.setReferenceId(vehicleMaintenanceDTO.getRequestNo());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(vehicleMaintenanceDTO.getOrgid());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
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
		workflowActionDto.setReferenceId(this.getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;

	}

	private void setWorkFlowStatusOnVendor() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getWorkflowActionDto().setReferenceId(this.vehicleMaintenanceDTO.getRequestNo());
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class).getWorkflowRequestByAppIdOrRefId(null, this.getWorkflowActionDto().getReferenceId(), orgId);
		
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			vehicleMaintenanceService.updateWorkFlowStatus(this.vehicleMaintenanceDTO.getVemId(), orgId, MainetConstants.WorkFlow.Decision.REJECTED);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING))
			vehicleMaintenanceService.updateWorkFlowStatus(this.vehicleMaintenanceDTO.getVemId(), orgId, MainetConstants.WorkFlow.Decision.PENDING);

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			vehicleMaintenanceService.updateWorkFlowStatus(this.vehicleMaintenanceDTO.getVemId(), orgId, MainetConstants.WorkFlow.Status.CLOSED);
	}

	private void genrateRefNo(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
		String maintCategoryCode;
		if(Constants.MAINT_BY_AMC.equalsIgnoreCase(this.getMaintCatLookupCode()))
			maintCategoryCode = Constants.MAINT_BY_AMC;
		else
			maintCategoryCode = Constants.SEQ_WORKSHOP;
		
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("VM", Constants.MAINT_CALL_REG_TABLE, "VEM_ID",
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		
		String requestNo = Constants.VEHICLE_MAINT_DRPT_CODE  + MainetConstants.WINDOWS_SLASH + maintCategoryCode + MainetConstants.WINDOWS_SLASH 
				+ finacialYear + MainetConstants.WINDOWS_SLASH + String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		vehicleMaintenanceDTO.setRequestNo(requestNo);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("NOCBuildingPermission.succes.msg") + " " + requestNo);
	}

	public OEMWarrantyDTO getOemWarrantyDto() {
		return oemWarrantyDto;
	}

	public void setOemWarrantyDto(OEMWarrantyDTO oemWarrantyDto) {
		this.oemWarrantyDto = oemWarrantyDto;
	}

	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}

	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}

	public List<SLRMEmployeeMasterDTO> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<SLRMEmployeeMasterDTO> drivers) {
		this.drivers = drivers;
	}

	public List<GenVehicleMasterDTO> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<GenVehicleMasterDTO> vehicles) {
		this.vehicles = vehicles;
	}

	public List<TbLocationMas> getLocations() {
		return locations;
	}

	public void setLocations(List<TbLocationMas> locations) {
		this.locations = locations;
	}

	public List<Object[]> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Object[]> employees) {
		this.employees = employees;
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

	public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
		this.budgetList = budgetList;
	}

	public String getMaintCatLookupCode() {
		return maintCatLookupCode;
	}

	public void setMaintCatLookupCode(String maintCatLookupCode) {
		this.maintCatLookupCode = maintCatLookupCode;
	}

	public String getMaintApprovalDecision() {
		return maintApprovalDecision;
	}

	public void setMaintApprovalDecision(String maintApprovalDecision) {
		this.maintApprovalDecision = maintApprovalDecision;
	}
	
}
