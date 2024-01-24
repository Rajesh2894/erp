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
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeInBusinessNameService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class ChangeInBusinessNameFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String paymentCheck;
	private String licenseDetails;
	private String checklistCheck;
	private String licFromDateDesc;
	private String licToDateDesc;
	private String userOtp;
	private String otp;
	private PortalService serviceMaster = new PortalService();
	private String issuanceDateDesc;
	private String viewMode;
	private String licenseBtnFlag;
	private String otpBtnShowFlag;
	private String immediateServiceMode;
	private String valueEditableCheckFlag;
	private String hideshowAddBtn;
	private String hideshowDeleteBtn;
	Map<Long, TradeLicenseItemDetailDTO> oldItemListMap = new HashMap<>();
	private String envFlag;
	private String tenant;
	
	@Autowired
	private ICommonBRMSService brmsCommonService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private IChangeInBusinessNameService changeInBusinessNameService;
	
	
	@Override
	public boolean saveForm() {

		setCommonFields(this);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		//124087
		if (masDto.getCreatedBy() == null) {
			masDto.setCreatedBy(createdBy);
			masDto.setCreatedDate(newDate);
			masDto.setOrgid(orgId);
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
				ownDto.setCreatedBy(createdBy);
				ownDto.setCreatedDate(newDate);
				ownDto.setOrgid(orgId);
				ownDto.setLgIpMac(lgIp);
			});

			masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				itemDto.setCreatedBy(createdBy);
				itemDto.setCreatedDate(newDate);
				itemDto.setOrgid(orgId);
				itemDto.setLgIpMac(lgIp);
			});

		} else {
			masDto.setUpdatedBy(createdBy);
			masDto.setUpdatedDate(newDate);
			masDto.setOrgid(orgId);
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
				if (ownDto.getCreatedBy() == null) {
					ownDto.setCreatedBy(createdBy);
					ownDto.setCreatedDate(newDate);
					ownDto.setOrgid(orgId);
					ownDto.setLgIpMac(lgIp);
				} else {
					ownDto.setUpdatedBy(createdBy);
					ownDto.setUpdatedDate(newDate);
					ownDto.setOrgid(orgId);
					ownDto.setLgIpMac(lgIp);
				}
			});

			getTradeDetailDTO().getTradeLicenseItemDetailDTO().forEach(olditemDto -> {

				getOldItemListMap().put(olditemDto.getTriId(), olditemDto);

			});

			getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().forEach(newitemDto -> {

				if (newitemDto.getCreatedBy() == null) {
					newitemDto.setCreatedBy(createdBy);
					newitemDto.setCreatedDate(newDate);
					newitemDto.setOrgid(orgId);
					newitemDto.setLgIpMac(lgIp);
					newitemDto.setTriStatus(MainetConstants.FlagY);

				} else {

					if (newitemDto.getTriId() != null) {

						TradeLicenseItemDetailDTO tradeLicenseItemDetailDTO = getOldItemListMap()
								.get(newitemDto.getTriId());

						if (tradeLicenseItemDetailDTO != null) {
							if (tradeLicenseItemDetailDTO.getTrdUnit() != newitemDto.getTrdUnit()) {

								newitemDto.setTriStatus(MainetConstants.FlagM);
							}
						}

					}

					newitemDto.setUpdatedBy(createdBy);
					newitemDto.setUpdatedDate(newDate);
					newitemDto.setOrgid(orgId);
					newitemDto.setLgIpMac(lgIp);
				}
			});

		}
		//124308 to set new business name 
		if (masDto.getTrdNewBusnm() != null) {
			masDto.setTrdBusnm(masDto.getTrdNewBusnm());
		}

		
		//124087
		masDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		masDto = changeInBusinessNameService.saveChangeBusinessNameService(masDto);
		if(null!=masDto.getTenant() && null!=masDto.getApmApplicationId()){
			try{
				ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class).aapaleSarakarPortalEntry(masDto);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		setTradeMasterDetailDTO(masDto);

		final CommonChallanDTO offline = getOfflineDTO();
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
         //125445
		CommonChallanDTO dto  = ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class)
				.getFeesId(masDto);
       	offline.setFeeIds(dto.getFeeIds());
		this.getTradeMasterDetailDTO().setFeeIds(dto.getFeeIds());
		setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.CHANGE_IN_BUSINESS_NAME_SHORT_CODE);
		requestDTO1.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
		requestDTO1.setIdfId(masDto.getApmApplicationId().toString());
		requestDTO1.setDepartmentName("ML");
		requestDTO1.setApplicationId(masDto.getApmApplicationId());
		requestDTO1.setDeptId(getServiceMaster().getPsmDpDeptid());
		requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		requestDTO1.setServiceId(getServiceMaster().getServiceId());
		requestDTO1.setReferenceId(masDto.getApmApplicationId().toString());
		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());
		return true;

	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		// setServiceMaster(portalServiceMaster);
		if(tradeMaster.getTradeLicenseOwnerdetailDTO() != null ) {
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
		offline.setEmailId(ownDtlDto.getTroEmailid());
		offline.setApplicantName(this.getOwnerName());
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
		offline.setApplicantAddress(ownDtlDto.getTroAddress());
		offline.setMobileNumber(ownDtlDto.getTroMobileno());
		}
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
	
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(portalServiceMaster.getServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		offline.setFeeIds(tradeMaster.getFeeIds());
		tradeMaster.setFeeIds(tradeMaster.getFeeIds());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
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
	
	private void setCommonFields(ChangeInBusinessNameFormModel model) {
		final Date sysDate = new Date();
		RequestDTO requestDTO = new RequestDTO();
		final TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgid(orgId);
		final Long serviceId = iPortalServiceMasterService
				.getServiceId(MainetConstants.TradeLicense.CHANGE_IN_BUSINESS_NAME_SHORT_CODE, orgId);
		final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getServiceId());
		model.getTradeMasterDetailDTO().setServiceId(service.getServiceId());
		dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.setPrimaryOwnerName(dto.getPrimaryOwnerName());
		dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		dto.setLgIpMac(dto.getLgIpMac());
		requestDTO.setUserId(dto.getCreatedBy());
		dto.setCreatedDate(sysDate);
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
		payURequestDTO.setUdf7(String.valueOf(tradeMasterDetailDTO.getApmApplicationId()));
		// String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(),
		// reqDTO.getmName(), reqDTO.getlName()));
		payURequestDTO.setApplicantName(this.getOwnerName());
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(tradeMasterDetailDTO.getApmApplicationId()));
		payURequestDTO
				.setMobNo(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno().toString());
		payURequestDTO.setDueAmt(tradeMasterDetailDTO.getTotalApplicationFee());
		payURequestDTO.setEmail(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		payURequestDTO.setApplicationId(tradeMasterDetailDTO.getApmApplicationId().toString());
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
			checkListModel.setServiceCode(MainetConstants.TradeLicense.CHANGE_IN_BUSINESS_NAME_SHORT_CODE);
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
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public String getLicenseDetails() {
		return licenseDetails;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public String getLicFromDateDesc() {
		return licFromDateDesc;
	}

	public String getLicToDateDesc() {
		return licToDateDesc;
	}

	public String getUserOtp() {
		return userOtp;
	}

	public String getOtp() {
		return otp;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public String getViewMode() {
		return viewMode;
	}

	public String getLicenseBtnFlag() {
		return licenseBtnFlag;
	}

	public String getOtpBtnShowFlag() {
		return otpBtnShowFlag;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public void setLicenseDetails(String licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public void setLicFromDateDesc(String licFromDateDesc) {
		this.licFromDateDesc = licFromDateDesc;
	}

	public void setLicToDateDesc(String licToDateDesc) {
		this.licToDateDesc = licToDateDesc;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public void setLicenseBtnFlag(String licenseBtnFlag) {
		this.licenseBtnFlag = licenseBtnFlag;
	}

	public void setOtpBtnShowFlag(String otpBtnShowFlag) {
		this.otpBtnShowFlag = otpBtnShowFlag;
	}

	public String getImmediateServiceMode() {
		return immediateServiceMode;
	}

	public void setImmediateServiceMode(String immediateServiceMode) {
		this.immediateServiceMode = immediateServiceMode;
	}

	public String getValueEditableCheckFlag() {
		return valueEditableCheckFlag;
	}

	public void setValueEditableCheckFlag(String valueEditableCheckFlag) {
		this.valueEditableCheckFlag = valueEditableCheckFlag;
	}

	public String getHideshowAddBtn() {
		return hideshowAddBtn;
	}

	public void setHideshowAddBtn(String hideshowAddBtn) {
		this.hideshowAddBtn = hideshowAddBtn;
	}

	public String getHideshowDeleteBtn() {
		return hideshowDeleteBtn;
	}

	public void setHideshowDeleteBtn(String hideshowDeleteBtn) {
		this.hideshowDeleteBtn = hideshowDeleteBtn;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public Map<Long, TradeLicenseItemDetailDTO> getOldItemListMap() {
		return oldItemListMap;
	}

	public void setOldItemListMap(Map<Long, TradeLicenseItemDetailDTO> oldItemListMap) {
		this.oldItemListMap = oldItemListMap;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
}
