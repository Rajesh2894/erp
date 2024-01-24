package com.abm.mainet.rnl.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.TbDepositDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IDepositReceiptService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.TankerBookingDetailsDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.ui.validator.EstateBookingFormValidator;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstateBookingModel extends AbstractFormModel {

	private static final long serialVersionUID = 8013260512013574634L;
	private BookingReqDTO bookingReqDTO = new BookingReqDTO();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private Double charges = 0.0d;
	private String free = "O";
	private boolean enableSubmit;
	private boolean enableCheckList = true;
	private boolean accept;
	private String isFree;
	private List<ChargeDetailDTO> chargesInfo;
	private double amountToPay;
	private Long deptId;
	private ServiceMaster service;
	private List<String> fromAndToDate;
	private PropInfoDTO propInfoDTO;
	private String docName;
	private Long propId;
	private List<EstatePropertyEventDTO> eventDTOsList;
	private ServiceMaster serviceMaster;
	private String acceptAgree;
	private String labelCharge;
	private double chargeGstAmount;
	private double chargeSgstAmouint;
	private double chargeCgstAmouint;
	private double gstPercentage;
	private double cgstPercenatge;
	private double sgtPercenatge;
	private List<String> bookDate;
	private Date fromedate;
	private String orgShowHide;
	private String isBplShowHide;

	private Date toDate;
	private EstatePropReqestDTO estatePropReqestDTO = new EstatePropReqestDTO();
	private List<EstatePropResponseDTO> estatePropResponseDTOs;
	private List<Object[]> driverName;
	private long levelcheck;
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private List<TbApprejMas> remarkList;
	private String payableFlag;
	private String successFlag;
	private List<EstateBookingDTO> masterDtoList = new ArrayList<>();

	public Date getFromedate() {
		return fromedate;
	}

	public void setFromedate(Date fromedate) {
		this.fromedate = fromedate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Autowired
	private IEstateBookingService iEstateBookingService;

	@Autowired
	private IDepositReceiptService depositreceiptservice;

	@Autowired
	private IEstatePropertyService iEstatePropertyService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private TbServicesMstService servicesMstService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ILocationMasService locationMasService;

	private List<TbLocationMas> locationMasList;

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationService;

	public boolean validateInputs() {

		validateBean(this, EstateBookingFormValidator.class);

		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean saveForm() {
		final CommonChallanDTO offline = getOfflineDTO();

		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		List<DocumentDetailsVO> docs = getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		getBookingReqDTO().setDocumentList(docs);
		validateBean(this, EstateBookingFormValidator.class);
		validateBean(offline, CommonOfflineMasterValidator.class);

		if (getAmountToPay() > 0.0)
			if ((getOfflineDTO().getOnlineOfflineCheck() == null)
					|| getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
				addValidationError(getAppSession().getMessage(
						ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.PAYMENT_MODE)));
			}
		/*
		 * if (!accept) { addValidationError(getAppSession()
		 * .getMessage(ApplicationSession.getInstance().
		 * getMessage("Please accept Terms & Conditions"))); }
		 */
		fileUpload.validateUpload(getBindingResult());

		if (hasValidationErrors()) {
			return false;
		}

		final EstateBookingDTO estateBookingDTO = getBookingReqDTO().getEstateBookingDTO();
		estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		estateBookingDTO.setLangId(UserSession.getCurrent().getLanguageId());
		estateBookingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		estateBookingDTO.setCreatedDate(new Date());
		estateBookingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		getBookingReqDTO().setPayAmount(getAmountToPay());
		getBookingReqDTO().setServiceId(getService().getSmServiceId());
		getBookingReqDTO().setDeptId(service.getTbDepartment().getDpDeptid());
		getBookingReqDTO().setServiceName(getService().getSmServiceName());
		if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
				&& (getService().getSmServiceName().equals("Water Tanker Booking")
						|| getService().getSmShortdesc().equals("WTB"))) {
			final BookingResDTO bookingResDTO = iEstateBookingService.saveWaterTanker(getBookingReqDTO(), offline,
					getService().getSmServiceName());
			iEstateBookingService.updateEstateBookingStatus(bookingResDTO.getApplicationNo(),
					UserSession.getCurrent().getEmployee().getEmpId());
			// change here suhel
			setAndSaveChallanDto(offline, bookingResDTO);
			saveSecurityDepositAmount(bookingResDTO);
			//iEstateBookingService.initializeWorkFlow(getBookingReqDTO().getApplicantDetailDto().is);
			setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.APP_NO)
					+ bookingResDTO.getApplicationNo() + MainetConstants.WHITE_SPACE
					+ ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.BOOK_NO)
					+ bookingResDTO.getBookingNo() + MainetConstants.WHITE_SPACE
					+ ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.RECEIPT_STATUS));
			return true;
		} else {
			final BookingResDTO bookingResDTO = iEstateBookingService.saveEstateBooking(getBookingReqDTO(), offline,
					getService().getSmServiceName());
			iEstateBookingService.updateEstateBookingStatus(bookingResDTO.getApplicationNo(),
					UserSession.getCurrent().getEmployee().getEmpId());

			// change here suhel
			setAndSaveChallanDto(offline, bookingResDTO);
			saveSecurityDepositAmount(bookingResDTO);

			setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.APP_NO)
					+ bookingResDTO.getApplicationNo() + MainetConstants.WHITE_SPACE
					+ ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.BOOK_NO)
					+ bookingResDTO.getBookingNo() + MainetConstants.WHITE_SPACE
					+ ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.RECEIPT_STATUS));
			ChallanReceiptPrintDTO printDto = getReceiptDTO();
			printDto.getPaymentList().sort(
					Comparator.comparing(ChallanReportDTO::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
			printDto.setServiceCode(MainetConstants.RNL_ESTATE_BOOKING);
			printDto.setReceiverName(UserSession.getCurrent().getEmployee().getFullName());
			setReceiptDTO(printDto);
			return true;
		}

	}

	

	private void setAndSaveChallanDto(CommonChallanDTO offline, BookingResDTO bookingResDTO) {
		offline.setApplNo(bookingResDTO.getApplicationNo());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(getBookingReqDTO().getServiceId());
		String aplicantName = getBookingReqDTO().getApplicantDetailDto().getApplicantFirstName()
				+ MainetConstants.WHITE_SPACE;
		aplicantName += getBookingReqDTO().getApplicantDetailDto().getApplicantMiddleName() == null
				? MainetConstants.BLANK
				: getBookingReqDTO().getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
		aplicantName += getBookingReqDTO().getApplicantDetailDto().getApplicantLastName();
		offline.setApplicantName(aplicantName);
		offline.setMobileNumber(getBookingReqDTO().getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getBookingReqDTO().getApplicantDetailDto().getEmailId());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(getBookingReqDTO().getApplicantDetailDto().getAreaName() + ","
				+ getBookingReqDTO().getApplicantDetailDto().getVillageTownSub());

		for (ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}

		if (CollectionUtils.isNotEmpty(getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(getClientIpAddress());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(getBookingReqDTO().getDeptId());
		offline.setPropNoConnNoEstateNoV(getBookingReqDTO().getEstatePropResponseDTO().getPropertyNo());
		offline.setPropNoConnNoEstateNoL(getBookingReqDTO().getEstatePropResponseDTO().getEstateCode());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(getPropId(),
					UserSession.getCurrent().getOrganisation().getOrgid());

			/*
			 * change here by suhel date 22-0-2020/ on this decfect Defect #32969
			 */

			String propertyName = estatePropResponseDTO.getPropName();
			offline.setServiceName(propertyName);
			offline.setDeptId(getBookingReqDTO().getDeptId());
			offline.setFromedate(getFromedate());
			offline.setToDate(getToDate());
			// Defect #32656
			if (!StringUtils.equals(offline.getAmountToPay(), "0.0")) {
				final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
						getBookingReqDTO().getServiceName());
				setReceiptDTO(printDto);
				setSuccessMessage(getAppSession().getMessage("receipt genrate"));
			}
		}
	}

	BookingResDTO saveSecurityDepositAmount(BookingResDTO bookingResDTO) {

		EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(getPropId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		String proName = estatePropResponseDTO.getPropName();
		String firstName = getBookingReqDTO().getApplicantDetailDto().getApplicantFirstName();
		String LastName = getBookingReqDTO().getApplicantDetailDto().getApplicantLastName();
		String fullName = firstName + MainetConstants.WHITE_SPACE + LastName;
		TbDepositDto tbDepositDto = new TbDepositDto();
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp depositType = CommonMasterUtility.getValueFromPrefixLookUp("S", "DTY", org);
		tbDepositDto.setDepType(depositType.getLookUpId());
		tbDepositDto.setDepDate(new Date());
		tbDepositDto.setDpDeptId(getBookingReqDTO().getDeptId());
		tbDepositDto.setDepNarration(bookingResDTO.getBookingNo() + MainetConstants.WHITE_SPACE + fullName
				+ MainetConstants.WHITE_SPACE + proName);
		LookUp lookupCode = CommonMasterUtility.getValueFromPrefixLookUp("O", "RBY", org);
		tbDepositDto.setDepStatus(String.valueOf(lookupCode.getLookUpId()));
		tbDepositDto.setVendorId(1l);
		tbDepositDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		tbDepositDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		tbDepositDto.setCreatedDate(new Date());
		tbDepositDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		for (ChargeDetailDTO dto : getChargesInfo()) {
			String securityAmount = dto.getChargeDescEng();
			double dtpAmount = dto.getChargeAmount();

			if (securityAmount.equalsIgnoreCase("Security Deposit")) {
				tbDepositDto.setDepAmount(BigDecimal.valueOf(dtpAmount));
				depositreceiptservice.saveDepositReceiptEntry(tbDepositDto);
			}

		}
		return bookingResDTO;
	}

	@Override
	public void populateApplicationData(final long applicationId) {

		ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
		applicantDetail.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		populateApplicantionDetails(applicantDetail, applicationId);

		List<EstateBookingDTO> estateBookingDTOs = ApplicationContextProvider.getApplicationContext()
				.getBean(IEstateBookingService.class)
				.getDetailByAppIdAndOrgId(applicationId, UserSession.getCurrent().getOrganisation().getOrgid());
		for (EstateBookingDTO estateBookingDTO : estateBookingDTOs) {
			getBookingReqDTO().getEstateBookingDTO().setId(estateBookingDTO.getId());
			getBookingReqDTO().getEstateBookingDTO().setFromDate(estateBookingDTO.getFromDate());
			getBookingReqDTO().getEstateBookingDTO().setToDate(estateBookingDTO.getToDate());
			getBookingReqDTO().getEstateBookingDTO().setShiftId(estateBookingDTO.getShiftId());
			getBookingReqDTO().getEstateBookingDTO().setPropId(estateBookingDTO.getPropId());
			getBookingReqDTO().getEstateBookingDTO().setShiftName(estateBookingDTO.getShiftName());
			getBookingReqDTO().getEstateBookingDTO().setPurpose(estateBookingDTO.getPurpose());
		}
		getBookingReqDTO().setApplicantDetailDto(applicantDetail);

	}

	public ApplicantDetailDTO populateApplicantionDetails(ApplicantDetailDTO detailDto, Long applicationNo) {

		TbCfcApplicationMstEntity masterEntity = cfcApplicationService.getCFCApplicationByApplicationId(applicationNo,
				detailDto.getOrgId());

		if (masterEntity != null) {
			detailDto.setApplicantTitle(masterEntity.getApmTitle());
			detailDto.setApplicantFirstName(masterEntity.getApmFname());
			detailDto.setApplicantLastName(masterEntity.getApmLname());
			detailDto.setGender(masterEntity.getApmSex());
			if (StringUtils.isNotBlank(masterEntity.getApmMname())) {
				detailDto.setApplicantMiddleName(masterEntity.getApmMname());
			}
			if (StringUtils.isNotBlank(masterEntity.getApmBplNo())) {
				detailDto.setIsBPL(MainetConstants.YES);
				detailDto.setBplNo(masterEntity.getApmBplNo());
			} else {
				detailDto.setIsBPL(MainetConstants.NO);
			}

			if (masterEntity.getApmUID() != null && masterEntity.getApmUID() != 0) {
				detailDto.setAadharNo(String.valueOf(masterEntity.getApmUID()));
			}
			/*
			 * if (masterEntity.ge != null && masterEntity.getApmUID() != 0) {
			 * detailDto.setPanNo(panNo); }
			 */
		}

		CFCApplicationAddressEntity addressEntity = cfcApplicationService.getApplicantsDetails(applicationNo);
		if (addressEntity != null) {
			detailDto.setMobileNo(addressEntity.getApaMobilno());
			detailDto.setEmailId(addressEntity.getApaEmail());
			detailDto.setAreaName(addressEntity.getApaAreanm());
			detailDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
			if (addressEntity.getApaZoneNo() != null && addressEntity.getApaZoneNo() != 0) {
				detailDto.setDwzid1(addressEntity.getApaZoneNo());
			}
			if (addressEntity.getApaWardNo() != null && addressEntity.getApaWardNo() != 0) {
				detailDto.setDwzid2(addressEntity.getApaWardNo());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBlockno())) {
				detailDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
			}
			if (StringUtils.isNotBlank(addressEntity.getApaCityName())) {
				detailDto.setVillageTownSub(addressEntity.getApaCityName());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaRoadnm())) {
				detailDto.setRoadName(addressEntity.getApaRoadnm());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaFlatBuildingNo())) {
				detailDto.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBldgnm())) {
				detailDto.setBuildingName(addressEntity.getApaBldgnm());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBlockName())) {
				detailDto.setBlockName(addressEntity.getApaBlockName());
			}
		}
		return detailDto;
	}

	public boolean closeWorkFlowTask() {
		boolean status = false;
		WorkflowTaskAction taskAction = getWorkflowActionDto();
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
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
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

		return iEstateBookingService.executeApprovalWorkflowAction(taskAction, getServiceMaster().getSmShortdesc(),
				getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc());

	}

	public void getDriverData() {
		Long id = getBookingReqDTO().getEstateBookingDTO().getId();
		TankerBookingDetailsDTO tankerBookingDetailsDTO = iEstateBookingService.getDriverData(id);
		getBookingReqDTO().getTankerBookingDetailsDTO().setDriverId(tankerBookingDetailsDTO.getDriverId());
		getBookingReqDTO().getTankerBookingDetailsDTO().setRemark(tankerBookingDetailsDTO.getRemark());
	}

	public BookingReqDTO getBookingReqDTO() {
		return bookingReqDTO;
	}

	public void setBookingReqDTO(final BookingReqDTO bookingReqDTO) {
		this.bookingReqDTO = bookingReqDTO;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(final List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(final Double charges) {
		this.charges = charges;
	}

	public String getFree() {
		return free;
	}

	public void setFree(final String free) {
		this.free = free;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public ServiceMaster getService() {
		return service;
	}

	public void setService(final ServiceMaster service) {
		this.service = service;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(final String isFree) {
		this.isFree = isFree;
	}

	@Override
	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	@Override
	public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	public List<String> getFromAndToDate() {
		return fromAndToDate;
	}

	public void setFromAndToDate(final List<String> fromAndToDate) {
		this.fromAndToDate = fromAndToDate;
	}

	public boolean isEnableCheckList() {
		return enableCheckList;
	}

	public void setEnableCheckList(final boolean enableCheckList) {
		this.enableCheckList = enableCheckList;
	}

	public boolean getAccept() {
		return accept;
	}

	public void setAccept(final boolean accept) {
		this.accept = accept;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(final String docName) {
		this.docName = docName;
	}

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public PropInfoDTO getPropInfoDTO() {
		return propInfoDTO;
	}

	public void setPropInfoDTO(final PropInfoDTO propInfoDTO) {
		this.propInfoDTO = propInfoDTO;
	}

	public List<EstatePropertyEventDTO> getEventDTOsList() {
		if (eventDTOsList.isEmpty()) {
			eventDTOsList = new ArrayList<EstatePropertyEventDTO>();
		}
		return eventDTOsList;
	}

	public void setEventDTOsList(List<EstatePropertyEventDTO> eventDTOsList) {
		this.eventDTOsList = eventDTOsList;
	}

	public String getAcceptAgree() {
		return acceptAgree;
	}

	public void setAcceptAgree(String acceptAgree) {
		this.acceptAgree = acceptAgree;
	}

	public Double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(Double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbLocationMas> getLocationMasList() {
		return locationMasList;
	}

	public void setLocationMasList(List<TbLocationMas> locationMasList) {
		this.locationMasList = locationMasList;
	}

	public String getLabelCharge() {
		return labelCharge;
	}

	public void setLabelCharge(String labelCharge) {
		this.labelCharge = labelCharge;
	}

	public double getChargeGstAmount() {
		return chargeGstAmount;
	}

	public void setChargeGstAmount(double chargeGstAmount) {
		this.chargeGstAmount = chargeGstAmount;
	}

	public double getChargeSgstAmouint() {
		return chargeSgstAmouint;
	}

	public void setChargeSgstAmouint(double chargeSgstAmouint) {
		this.chargeSgstAmouint = chargeSgstAmouint;
	}

	public double getChargeCgstAmouint() {
		return chargeCgstAmouint;
	}

	public void setChargeCgstAmouint(double chargeCgstAmouint) {
		this.chargeCgstAmouint = chargeCgstAmouint;
	}

	public double getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(double gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public double getCgstPercenatge() {
		return cgstPercenatge;
	}

	public void setCgstPercenatge(double cgstPercenatge) {
		this.cgstPercenatge = cgstPercenatge;
	}

	public double getSgtPercenatge() {
		return sgtPercenatge;
	}

	public void setSgtPercenatge(double sgtPercenatge) {
		this.sgtPercenatge = sgtPercenatge;
	}

	public List<String> getBookDate() {
		return bookDate;
	}

	public void setBookDate(List<String> bookDate) {
		this.bookDate = bookDate;
	}

	public String getOrgShowHide() {
		return orgShowHide;
	}

	public void setOrgShowHide(String orgShowHide) {
		this.orgShowHide = orgShowHide;
	}

	public String getIsBplShowHide() {
		return isBplShowHide;
	}

	public void setIsBplShowHide(String isBplShowHide) {
		this.isBplShowHide = isBplShowHide;
	}

	public EstatePropReqestDTO getEstatePropReqestDTO() {
		return estatePropReqestDTO;
	}

	public void setEstatePropReqestDTO(EstatePropReqestDTO estatePropReqestDTO) {
		this.estatePropReqestDTO = estatePropReqestDTO;
	}

	public List<EstatePropResponseDTO> getEstatePropResponseDTOs() {
		return estatePropResponseDTOs;
	}

	public void setEstatePropResponseDTOs(List<EstatePropResponseDTO> estatePropResponseDTOs) {
		this.estatePropResponseDTOs = estatePropResponseDTOs;
	}

	public List<Object[]> getDriverName() {
		return driverName;
	}

	public void setDriverName(List<Object[]> driverName) {
		this.driverName = driverName;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public List<TbApprejMas> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<TbApprejMas> remarkList) {
		this.remarkList = remarkList;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public List<EstateBookingDTO> getMasterDtoList() {
		return masterDtoList;
	}

	public void setMasterDtoList(List<EstateBookingDTO> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

}
