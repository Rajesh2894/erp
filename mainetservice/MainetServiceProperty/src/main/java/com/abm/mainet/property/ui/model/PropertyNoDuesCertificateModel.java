package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.google.common.util.concurrent.AtomicDouble;

@Component
@Scope("session")
public class PropertyNoDuesCertificateModel extends AbstractFormModel {
	
    private static final Logger LOGGER = Logger.getLogger(PropertyNoDuesCertificateModel.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;

	@Autowired
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IChallanService iChallanService;
	
	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
    @Autowired
    private DepartmentService departmentService;
    
	@Autowired
	private PropertyMainBillService propertyMainBillService;
	
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = null;

	private NoDuesCertificateDto noDuesCertificateDto = null;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<LookUp> documentsList = new ArrayList<>();

	private String appliChargeFlag;

	private List<BillDisplayDto> charges = new ArrayList<>();

	private List<LookUp> location = new ArrayList<>(0);

	private Long orgId;

	private Long deptId;

	private String serviceName;

	private String scrutinyAppliFlag;

	private String allowToGenerate;

	private String date;

	private String finYear;

	private String checkListApplFlag;

	private boolean isFree;

	private String authFlag;

	private ServiceMaster serviceMaster = new ServiceMaster();

	private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();

	private String successFlag;

	private String payableFlag;

	private Double totalLoiAmount;

	private String serviceShrtCode;

	private List<TbLoiDet> loiDetail = new ArrayList<>();

	private ProperySearchDto searchDto = new ProperySearchDto();

	private List<ProperySearchDto> searchDtoResult = new ArrayList<>();

	private Map<Long, String> financialYearMap = null;

	private boolean lastApproval;

	private boolean isNeedToCheckOutstanding;

	private String deptName;// to print of acknowledgement
	
	private String dueDate;
	
	private String time;
	
	private String billMethod;

	private NoDuesPropertyDetailsDto noDuesPropertyDetailsDto;

	@Override
	public boolean saveForm() {

		final CommonChallanDTO offline = getOfflineDTO();
		List<DocumentDetailsVO> docs = getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		validateData(offline, docs);
		if (hasValidationErrors()) {
			return false;
		}

		noDuesCertificateDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		if (!checkChequeClearanceStatus()
				|| (isNeedToCheckOutstanding && !checkOutstandingStatus())) {
			return false;
		}
		noDuesCertificateDto.setDocs(docs);
		noDuesCertificateDto.setDeptId(getDeptId());
		noDuesCertificateDto.setLangId(UserSession.getCurrent().getLanguageId());
		noDuesCertificateDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		noDuesCertificateDto.setOrgId(getOrgId());
		noDuesCertificateDto.setSmServiceId(getServiceId());
		noDuesCertificateDto.setMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
		noDuesCertificateDto.setApplicantDto(getApplicantDetailDto());
		// Generate certificate no
		Organisation org = UserSession.getCurrent().getOrganisation();
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SKDCL)) {
			try {
				SequenceConfigMasterDTO configMasterDTO = null;
				Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
						PrefixConstants.STATUS_ACTIVE_PREFIX);
				configMasterDTO = seqGenFunctionUtility.loadSequenceData(org.getOrgid(), deptId,
						MainetConstants.Property.TB_AS_NO_DUES_PROP_DETAILS, MainetConstants.Property.OUTWARD_NO);
				String outwardNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO,
						new CommonSequenceConfigDto());
				outwardNo = outwardNo.replaceFirst("^0+(?!$)", "");
				if (outwardNo.length() == 1) {
					outwardNo = MainetConstants.NUMBERS.ZERO + outwardNo;
				}
				noDuesCertificateDto.setOutwardNo(outwardNo);
			} catch (Exception e) {
				LOGGER.error("Exception occurred while generating outward number :" + e.getMessage());
				addValidationError(getAppSession().getMessage("property.outwardNoValidn"));
				return false;
			}
		}
		//
		propertyNoDuesCertificate.generateNoDuesCertificate(noDuesCertificateDto);
		if (!isFree) {

			setChallanDToandSaveChallanData(offline, charges, noDuesCertificateDto, provisionalAssesmentMstDto);
		}
		setSuccessMessage(getAppSession().getMessage("prop.no.dues.save1") + noDuesCertificateDto.getApplicationNo()
				+ getAppSession().getMessage("prop.no.dues.save2"));
		if (MainetConstants.Y_FLAG.equals(getScrutinyAppliFlag()) && isFree) {
			callWorkFlow(noDuesCertificateDto, getServiceId(), deptId);
		}
		return true;
	}

	private void validateData(final CommonChallanDTO offline, List<DocumentDetailsVO> docs) {
		if ((docs != null) && !docs.isEmpty()) {
			for (final DocumentDetailsVO doc : docs) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
					if (doc.getDocumentByteCode() == null) {
						this.addValidationError(ApplicationSession.getInstance().getMessage("property.mandtory.docs"));
						break;
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(noDuesCertificateDto.getPropertyDetails()) && isFree) {
			boolean billExist = isNoBillExist(noDuesCertificateDto.getPropertyDetails().get(0));
			if(billExist) {
				addValidationError(ApplicationSession.getInstance().getMessage("property.no.bill.exist"));
			}
		}
		if (MainetConstants.N_FLAG.equals(appliChargeFlag) && !isFree) {
				validateBean(offline, CommonOfflineMasterValidator.class);
		}
	}

	public boolean checkOutstandingStatus() {
		double outstanding = 0d;
		if (noDuesCertificateDto.getPropertyDetails() != null && !noDuesCertificateDto.getPropertyDetails().isEmpty()) {
			for (NoDuesPropertyDetailsDto dto : noDuesCertificateDto.getPropertyDetails()) {
				outstanding = outstanding + dto.getTotalOutsatandingAmt();
			}
		}
		if (outstanding > 0) {
			addValidationError(getAppSession().getMessage("prop.no.dues.outstanding"));
			return false;
		}

		return true;
	}
	
	public boolean isNoBillExist(NoDuesPropertyDetailsDto detailsDto) {
		int count = 0;
		if(StringUtils.equals(getBillMethod(), MainetConstants.FlagI)) {
			 count = propertyMainBillService.getBillExistByPropNoFlatNoAndYearId(detailsDto.getPropNo(),
					 UserSession.getCurrent().getOrganisation().getOrgid(), detailsDto.getFinacialYearId(), detailsDto.getFlatNo());
		}else {
			count = propertyMainBillService.getBillExistByPropNoAndYearId(detailsDto.getPropNo(),
					UserSession.getCurrent().getOrganisation().getOrgid(), detailsDto.getFinacialYearId());
		}
		if(count == 0) {
			return true;
		}
		return false;
	}

	public boolean checkChequeClearanceStatus() {
		if (!propertyNoDuesCertificate.getChequeClearanceStatus(noDuesCertificateDto)) {
			addValidationError(getAppSession().getMessage("prop.no.dues.cheque.not.clear"));
			return false;
		}
		return true;
	}

	public boolean updateForm() {
		try {
			noDuesCertificateDto.getPropertyDetails().forEach(tr -> {
				if (tr.getPropDetId() == null) {
					tr.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					tr.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					tr.setCreatedDate(new Date());
					tr.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					tr.setApplicationId(getApmApplicationId());
					tr.setIsDeleted(MainetConstants.FlagN);
					tr.setNoOfCopies(noDuesCertificateDto.getNoOfCopies());
				} else {
					tr.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					tr.setUpdatedDate(new Date());
					tr.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				}
			});

			propertyNoDuesCertificate.updateNoDuesProprtyDetails(noDuesCertificateDto);
			return true;
		} catch (Exception e) {
			addValidationError("Exception occured while saving no dues property details:" + e);
			return false;
		}

	}

	public List<LookUp> getDocumentsList() {
		final List<LookUp> documentDetailsList = new ArrayList<>(0);
		setPath(getAttachmentList().get(0).getAttPath());
		LookUp lookUp = null;
		for (final CFCAttachment temp : getAttachmentList()) {
			lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
			lookUp.setDescLangFirst(temp.getClmDescEngl());
			lookUp.setDescLangSecond(temp.getClmDesc());
			lookUp.setLookUpId(temp.getClmId());
			lookUp.setDefaultVal(temp.getAttPath());
			lookUp.setLookUpCode(temp.getAttFname());
			lookUp.setLookUpType(temp.getDmsDocId());
			lookUp.setLookUpParentId(temp.getAttId());
			lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
			lookUp.setOtherField(temp.getMandatory());
			lookUp.setExtraStringField1(temp.getClmRemark());// reject message
			documentDetailsList.add(lookUp);
		}
		return documentDetailsList;
	}

	void callWorkFlow(NoDuesCertificateDto dto, Long serviceid, Long deptId) {
		ApplicationMetadata applicationData = new ApplicationMetadata();
		final ApplicantDetailDTO applicantDetailDto = getApplicantDetailDto();
		applicantDetailDto.setOrgId(dto.getOrgId());
		applicationData.setApplicationId(noDuesCertificateDto.getApmApplicationId());
		applicationData.setReferenceId(noDuesCertificateDto.getApplicationNo());
		applicationData.setIsCheckListApplicable(
				(getCheckListApplFlag() != null && MainetConstants.FlagY.equals(getCheckListApplFlag())) ? true
						: false);
		applicationData.setOrgId(dto.getOrgId());
		applicantDetailDto.setServiceId(serviceid);
		applicantDetailDto.setDepartmentId(deptId);
		applicantDetailDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		applicantDetailDto.setOrgId(dto.getOrgId());
		applicantDetailDto.setDwzid1(getApplicantDetailDto().getDwzid1());
		applicantDetailDto.setDwzid2(getApplicantDetailDto().getDwzid2());
		applicantDetailDto.setDwzid3(getApplicantDetailDto().getDwzid3());
		applicantDetailDto.setDwzid4(getApplicantDetailDto().getDwzid4());
		applicantDetailDto.setDwzid5(getApplicantDetailDto().getDwzid5());
		commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final List<BillDisplayDto> charges,
			NoDuesCertificateDto noDuesCertificateDto, ProvisionalAssesmentMstDto prevAssessDetail) {
		final UserSession session = UserSession.getCurrent();
		offline.setAmountToPay(Double.toString(noDuesCertificateDto.getTotalPaybleAmt()));
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		noDuesCertificateDto.getCharges().forEach(charge -> {
			offline.getFeeIds().put(charge.getTaxId(), charge.getTotalTaxAmt().doubleValue());
		});
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setEmailId(noDuesCertificateDto.getApplicantDto().getEmailId());
		offline.setApplicantName(noDuesCertificateDto.getApplicantDto().getApplicantFirstName()
				+ MainetConstants.WHITE_SPACE + noDuesCertificateDto.getApplicantDto().getApplicantMiddleName()
				+ MainetConstants.WHITE_SPACE + noDuesCertificateDto.getApplicantDto().getApplicantLastName());
		offline.setApplNo(noDuesCertificateDto.getApmApplicationId());
		offline.setApplicantAddress(noDuesCertificateDto.getApplicantDto().getBuildingName());
		offline.setMobileNumber(noDuesCertificateDto.getApplicantDto().getMobileNo());
		offline.setServiceId(getServiceId());
		offline.setDeptId(getDeptId());
		offline.setReferenceNo(noDuesCertificateDto.getApplicationNo());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		if (getCheckList() != null && !getCheckList().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& MainetConstants.PAYMENT_TYPE.OFFLINE.equals(offline.getOnlineOfflineCheck())) {
			final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());
			setSuccessMessage(getAppSession().getMessage("property.noDues.sccessMsg") + " "
					+ noDuesCertificateDto.getApmApplicationId());
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER.equals(offline.getOnlineOfflineCheck())) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, serviceName);
			String regex = "\\d+";
			if (Pattern.matches(regex, noDuesCertificateDto.getApplicationNo()))
				printDto.setApplicationNumber(Long.valueOf(noDuesCertificateDto.getApplicationNo()));
			setReceiptDTO(printDto);

			setSuccessMessage(getAppSession().getMessage("property.noDues.sccessMsg") + " "
					+ noDuesCertificateDto.getApmApplicationId());
		}
		setOfflineDTO(offline);
	}

	public boolean isWorkflowDefined() {

		WorkflowMas mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(getOrgId(), getDeptId(),
				getServiceId(), getApplicantDetailDto().getDwzid1(), getApplicantDetailDto().getDwzid2(),
				getApplicantDetailDto().getDwzid3(), getApplicantDetailDto().getDwzid4(),
				getApplicantDetailDto().getDwzid5());
		if (mas == null) {
			return false;
		}
		return true;
	}

	public boolean AgencyRegistrationApprovalAction() {
		WorkflowTaskAction taskAction = getWorkflowActionDto();
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setCreatedDate(new Date());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setDateOfAction(new Date());
		taskAction.setIsFinalApproval(MainetConstants.FAILED);
		taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
		taskAction.setDecision(getWorkflowActionDto().getDecision());
		taskAction.setApplicationId(getApmApplicationId());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());

		return propertyNoDuesCertificate.executeApprovalWorkflowAction(taskAction, getServiceMaster().getSmShortdesc(),
				getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc());

	}

	public boolean closeWorkFlowTask() {
		boolean status = false;
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setIsFinalApproval(true);
		taskAction.setIsObjectionAppealApplicable(false);
		if (StringUtils.isNotBlank(UserSession.getCurrent().getEmployee().getEmpemail())) {
			taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		}
		taskAction.setApplicationId(getApmApplicationId());
		taskAction.setDecision(getWorkflowActionDto().getDecision());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		LookUp workProcessLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				getServiceMaster().getSmProcessId(), UserSession.getCurrent().getOrganisation());
		workflowProcessParameter.setProcessName(workProcessLookUp.getDescLangFirst());
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
			status = true;
		} catch (Exception exception) {
			throw new FrameworkException("Exception occured while updating work flow", exception);
		}
		return status;
	}

	public boolean saveLoiData() {
		boolean status = false;

		final UserSession session = UserSession.getCurrent();
		Map<Long, Double> loiCharges = propertyNoDuesCertificate.getLoiCharges(session.getOrganisation(),
				serviceMaster.getTbDepartment().getDpDeptid(), serviceMaster.getSmShortdesc(), "SCL");

		if (MapUtils.isNotEmpty(loiCharges)) {
			ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class).saveLOIAppData(
					loiCharges, getServiceMaster().getSmServiceId(), loiDetail,
					false/* approvalLetterGenerationApplicable */, getWorkflowActionDto());
			setLoiDetail(loiDetail);
			status = true;
		}

		return status;

	}

	public  Date dueDate(Date date) {
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(getServiceMaster().getSmDurationUnit(),
				UserSession.getCurrent().getOrganisation());
		Date dueDate = null;
		if(lookUp != null) {
			if(StringUtils.equals(MainetConstants.FlagD, lookUp.getLookUpCode())) {
				dueDate = Utility.getAddedDateBy2(date, getServiceMaster().getSmServiceDuration().intValue());
			}
			if(StringUtils.equals(MainetConstants.FlagM, lookUp.getLookUpCode())) {
				dueDate = Utility.getAddedMonthsBy(date, getServiceMaster().getSmServiceDuration().intValue());
			}
			if(StringUtils.equals(MainetConstants.FlagY, lookUp.getLookUpCode())) {
				dueDate = Utility.getAddedYearsBy(date, getServiceMaster().getSmServiceDuration().intValue());
			}
			if(StringUtils.equals(MainetConstants.FlagH, lookUp.getLookUpCode())) {
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(date);
			    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + getServiceMaster().getSmServiceDuration().intValue());
				dueDate = calendar.getTime();
			}
		}
		return dueDate;
	}
	
	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public NoDuesCertificateDto getNoDuesCertificateDto() {
		return noDuesCertificateDto;
	}

	public void setNoDuesCertificateDto(NoDuesCertificateDto noDuesCertificateDto) {
		this.noDuesCertificateDto = noDuesCertificateDto;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getAppliChargeFlag() {
		return appliChargeFlag;
	}

	public void setAppliChargeFlag(String appliChargeFlag) {
		this.appliChargeFlag = appliChargeFlag;
	}

	public PropertyNoDuesCertificateService getPropertyNoDuesCertificate() {
		return propertyNoDuesCertificate;
	}

	public void setPropertyNoDuesCertificate(PropertyNoDuesCertificateService propertyNoDuesCertificate) {
		this.propertyNoDuesCertificate = propertyNoDuesCertificate;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getScrutinyAppliFlag() {
		return scrutinyAppliFlag;
	}

	public void setScrutinyAppliFlag(String scrutinyAppliFlag) {
		this.scrutinyAppliFlag = scrutinyAppliFlag;
	}

	public String getAllowToGenerate() {
		return allowToGenerate;
	}

	public void setAllowToGenerate(String allowToGenerate) {
		this.allowToGenerate = allowToGenerate;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<DocumentDetailsVO> getApprovalDocumentAttachment() {
		return approvalDocumentAttachment;
	}

	public void setApprovalDocumentAttachment(List<DocumentDetailsVO> approvalDocumentAttachment) {
		this.approvalDocumentAttachment = approvalDocumentAttachment;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}

	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}

	public List<TbLoiDet> getLoiDetail() {
		return loiDetail;
	}

	public void setLoiDetail(List<TbLoiDet> loiDetail) {
		this.loiDetail = loiDetail;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public List<LookUp> getLocation() {
		return location;
	}

	public void setLocation(List<LookUp> location) {
		this.location = location;
	}

	public String getServiceShrtCode() {
		return serviceShrtCode;
	}

	public void setServiceShrtCode(String serviceShrtCode) {
		this.serviceShrtCode = serviceShrtCode;
	}

	public ProperySearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(ProperySearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public List<ProperySearchDto> getSearchDtoResult() {
		return searchDtoResult;
	}

	public void setSearchDtoResult(List<ProperySearchDto> searchDtoResult) {
		this.searchDtoResult = searchDtoResult;
	}

	public Map<Long, String> getFinancialYearMap() {
		return financialYearMap;
	}

	public void setFinancialYearMap(Map<Long, String> financialYearMap) {
		this.financialYearMap = financialYearMap;
	}

	public void setDocumentsList(List<LookUp> documentsList) {
		this.documentsList = documentsList;
	}

	public boolean isLastApproval() {
		return lastApproval;
	}

	public void setLastApproval(boolean lastApproval) {
		this.lastApproval = lastApproval;
	}

	public boolean isNeedToCheckOutstanding() {
		return isNeedToCheckOutstanding;
	}

	public void setNeedToCheckOutstanding(boolean isNeedToCheckOutstanding) {
		this.isNeedToCheckOutstanding = isNeedToCheckOutstanding;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBillMethod() {
		return billMethod;
	}

	public void setBillMethod(String billMethod) {
		this.billMethod = billMethod;
	}

	public NoDuesPropertyDetailsDto getNoDuesPropertyDetailsDto() {
		return noDuesPropertyDetailsDto;
	}

	public void setNoDuesPropertyDetailsDto(
			NoDuesPropertyDetailsDto noDuesPropertyDetailsDto) {
		this.noDuesPropertyDetailsDto = noDuesPropertyDetailsDto;
	}

}
