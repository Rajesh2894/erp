package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.validator.RenewalLicenseApplicationValidator;

@Component
@Scope("session")
public class RenewalLicenseFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private ServiceMaster serviceMast = new ServiceMaster();
	private String paymentCheck;
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String immediateServiceMode;
	private String checklistCheck;
	private String appChargeFlag;
	private String checkListApplFlag;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String sudaEnv;
	List<TradeMasterDetailDTO> tradeMasterDtoList= new ArrayList<>();
	private String viewDetFlag;
	private String viewPaymentFlag;
	private PortalService  serviceMaster = new PortalService();
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Long applicationId;
	private String applicantName;
	private String serviceName;
	private String tenant;
	
	@Autowired
	private IChallanService challanService;

	@Autowired
	private IRenewalLicenseApplicationService renewalLicenseApplicationService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private ICommonBRMSService brmsCommonService;
	
	@Autowired
	private	ITradeLicenseApplicationService tradeLicenseApplicationService;
	
	public void getRenewalDate(Date tradeLicenseTodate, int noofdays) {

		Date newDate = new Date();
		Date renwalFromDate = Utility.getSubtractedDateBy(tradeLicenseTodate, noofdays);
		Date sysdate = Utility.getSubtractedDateBy(newDate, noofdays);
		Date renwalToDate = Utility.getAddedDateBy(tradeLicenseTodate, noofdays);
		Date sysdate1 = Utility.getAddedDateBy(newDate, noofdays);

		if (tradeLicenseTodate.compareTo(sysdate) >= 0 && tradeLicenseTodate.compareTo(sysdate1) <= 0) {
			if ((Utility.comapreDates(tradeLicenseTodate, newDate)) || tradeLicenseTodate.after(newDate)) {
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
						.setTreLicfromDate(Utility.getAddedDateBy(newDate, 1));
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
						.setRenewalFromDateDesc(Utility.dateToString(Utility.getAddedDateBy(tradeLicenseTodate, 1)));
				// getRenewalMasterDetailDTO().setTreLicfromDate(new Date(newDate.getTime() + 1
				// * 24 * 3600 * 1000));
				// getRenewalMasterDetailDTO().setRenewalFromDateDesc(Utility.dateToString(new
				// Date(newDate.getTime() + 1 * 24 * 3600 * 1000)));
				// renewal to date is newDate+1
			} else if (tradeLicenseTodate.before(newDate)) {
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO().setTreLicfromDate(newDate);
				// getRenewalMasterDetailDTO().setRenewalFromDateDesc(Utility.dateToString(newDate));
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
						.setRenewalFromDateDesc(Utility.dateToString(Utility.getAddedDateBy(newDate, 1)));
				// tradeLicenseTodate +1;
			}
			getTradeMasterDetailDTO().getRenewalMasterDetailDTO().setTreLictoDate(Utility.getFullYearByDate(newDate));
			getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
					.setRenewalTodDateDesc(Utility.dateToString(Utility.getFullYearByDate(newDate)));
		} else {
			addValidationError(ApplicationSession.getInstance().getMessage("renewal.license.not.valid"));

		}

	}

	@Override
	public boolean saveForm() {

		setCommonFields(this);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date newDate = new Date();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();

		TradeMasterDetailDTO tradeMasterDetailDTO = getTradeMasterDetailDTO();
		tradeMasterDetailDTO.getRenewalMasterDetailDTO().setCreatedBy(createdBy);
		tradeMasterDetailDTO.getRenewalMasterDetailDTO().setTreStatus("N");
		tradeMasterDetailDTO.getRenewalMasterDetailDTO().setOrgid(orgId);
		tradeMasterDetailDTO.getRenewalMasterDetailDTO().setLangId((long) UserSession.getCurrent().getLanguageId());
		tradeMasterDetailDTO.getRenewalMasterDetailDTO()
				.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		tradeMasterDetailDTO.getRenewalMasterDetailDTO().setCreatedDate(newDate);
		tradeMasterDetailDTO.getRenewalMasterDetailDTO()
				.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		TradeMasterDetailDTO tradeDto = getTradeMasterDetailDTO();
		tradeDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		tradeDto.setChecklistAppFlag(this.getCheckListApplFlag());
		tradeDto.setUserId(empId);
		tradeDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		tradeDto.setOrgid(orgId);
		if (!this.getAppChargeFlag().equals(MainetConstants.FlagY)) {
			tradeDto.setFree(true);
		}
		// renewalDto.setApmApplicationId(tradeDto.getApmApplicationId());
		tradeDto = ApplicationContextProvider.getApplicationContext().getBean(IRenewalLicenseApplicationService.class)
				.saveAndUpdateApplication(tradeDto);
		// add code for 111813
		if(null!=tradeDto.getTenant() && null!=tradeDto.getApmApplicationId()){
			try{
				ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class).aapaleSarakarPortalEntry(tradeDto);
				}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + " "
				+ tradeDto.getRenewalMasterDetailDTO().getApmApplicationId());
		setTradeMasterDetailDTO(tradeDto);
		//127361 to get feesId 
		//CommonChallanDTO dto  = tradeLicenseApplicationService.getFeesId(tradeDto);
		
		this.getTradeMasterDetailDTO().setFeeIds(tradeMasterDetailDTO.getFeeIds());
		// add code for 111813
		if (this.getAppChargeFlag().equals(MainetConstants.FlagY)) {
			final CommonChallanDTO offline = getOfflineDTO();
			Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
			RequestDTO requestDTO1 = new RequestDTO();
			requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			requestDTO1.setDepartmentName(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE);
			requestDTO1.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
			requestDTO1.setIdfId(getTradeMasterDetailDTO().getApmApplicationId().toString());
			requestDTO1.setDepartmentName("ML");
			requestDTO1.setApplicationId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId());
			// requestDTO1.setDeptId(getServiceMaster().getPsmDpDeptid());
			requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
			requestDTO1.setServiceId(getServiceMast().getSmServiceId());
			// requestDTO1.setReferenceId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId().toString());
		}
		return true;

	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		// setServiceMaster(portalServiceMaster);
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
		offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		/*
		 * offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		 * offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
		 * offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		 * offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		 */
		offline.setEmailId(ownDtlDto.getTroEmailid());
		offline.setApplicantName(this.getOwnerName());
		offline.setApplNo(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId());
		offline.setUniquePrimaryId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId().toString());
		offline.setApplicantAddress(ownDtlDto.getTroAddress());
		offline.setMobileNumber(ownDtlDto.getTroMobileno());
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(portalServiceMaster.getServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		offline.setFromedate(tradeMaster.getTrdLicfromDate());
		offline.setToDate(tradeMaster.getTrdLictoDate());
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		offline.setFeeIds(tradeMaster.getFeeIds());
		tradeMaster.setFeeIds(tradeMaster.getFeeIds());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setServiceName(portalServiceMaster.getServiceName());
		offline.setServiceNameMar(portalServiceMaster.getServiceNameReg());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
			offline = challanService.generateChallanNumber(offline);
			/*
			 * offline.setChallanValidDate(master .getChallanValiDate());
			 * offline.setChallanNo(master.getChallanNo());
			 */
		}
		setOfflineDTO(offline);
	}

	public ServiceMaster getServiceMast() {
		return serviceMast;
	}

	public void setServiceMast(ServiceMaster serviceMast) {
		this.serviceMast = serviceMast;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	private void setCommonFields(RenewalLicenseFormModel model) {
		final Date sysDate = new Date();
		RequestDTO requestDTO = new RequestDTO();
		final TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
		// final RenewalMasterDetailDTO dto = model.getRenewalMasterDetailDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.getRenewalMasterDetailDTO().setOrgid(orgId);
		final Long serviceId = iPortalServiceMasterService
				.getServiceId(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE, orgId);
		final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
		// model.setServiceMaster(service);
		model.setServiceId(service.getServiceId());
		model.getTradeMasterDetailDTO().setServiceId(service.getServiceId());
		dto.getRenewalMasterDetailDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		dto.getRenewalMasterDetailDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.getRenewalMasterDetailDTO().setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		dto.getRenewalMasterDetailDTO().setLgIpMac(dto.getRenewalMasterDetailDTO().getLgIpMac());
		requestDTO.setUserId(dto.getRenewalMasterDetailDTO().getCreatedBy());
		dto.getRenewalMasterDetailDTO().setCreatedDate(sysDate);
		long deptId = service.getPsmDpDeptid();
		requestDTO.setDeptId(deptId);
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final TradeMasterDetailDTO tradeMasterDetailDTO = this.getTradeMasterDetailDTO();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId()));
		// String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(),
		// reqDTO.getmName(), reqDTO.getlName()));
		payURequestDTO.setApplicantName(this.getOwnerName());
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId()));
		payURequestDTO
				.setMobNo(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno().toString());
		payURequestDTO.setDueAmt(tradeMasterDetailDTO.getTotalApplicationFee());
		payURequestDTO.setEmail(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		payURequestDTO
				.setApplicationId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId().toString());
		payURequestDTO.setFeeIds(tradeMasterDetailDTO.getFeeIds().toString());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	
	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public String getImmediateServiceMode() {
		return immediateServiceMode;
	}

	public void setImmediateServiceMode(String immediateServiceMode) {
		this.immediateServiceMode = immediateServiceMode;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public String getAppChargeFlag() {
		return appChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		this.appChargeFlag = appChargeFlag;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public IChallanService getChallanService() {
		return challanService;
	}

	public void setChallanService(IChallanService challanService) {
		this.challanService = challanService;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	
	

	public String getSudaEnv() {
		return sudaEnv;
	}

	public void setSudaEnv(String sudaEnv) {
		this.sudaEnv = sudaEnv;
	}


	public List<TradeMasterDetailDTO> getTradeMasterDtoList() {
		return tradeMasterDtoList;
	}

	public void setTradeMasterDtoList(List<TradeMasterDetailDTO> tradeMasterDtoList) {
		this.tradeMasterDtoList = tradeMasterDtoList;
	}
	
	

	public String getViewDetFlag() {
		return viewDetFlag;
	}

	public void setViewDetFlag(String viewDetFlag) {
		this.viewDetFlag = viewDetFlag;
	}

	public String getViewPaymentFlag() {
		return viewPaymentFlag;
	}

	public void setViewPaymentFlag(String viewPaymentFlag) {
		this.viewPaymentFlag = viewPaymentFlag;
	}

	public void getCheckListFromBrms() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		initRequestDto.setModelName(MainetConstants.TradeLicense.CHECKLIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
			CheckListModel checkListModel = (CheckListModel) checklist.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE);
			final WSRequestDTO checkRequestDto = new WSRequestDTO();
			checkRequestDto.setDataModel(checkListModel);
			checkListList = brmsCommonService.getChecklist(checkListModel);
			if (checkListList != null && !checkListList.isEmpty()) {
				Long fileSerialNo = 1L;
				for (final DocumentDetailsVO docSr : checkListList) {
					docSr.setDocumentSerialNo(fileSerialNo);
					fileSerialNo++;
				}
				setCheckList(checkListList);

			}
		}
	}

	public boolean validateInputs() {
		validateBean(this, RenewalLicenseApplicationValidator.class);
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getHelpLine() {
		return helpLine;
	}

	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

}
