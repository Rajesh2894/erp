/**
 * 
 */
package com.abm.mainet.tradeLicense.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
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
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.validator.TradeApplicationFormValidator;

/**
 * @author Gayatri.Kokane
 *
 */

@Component
@Scope("session")
public class TradeApplicationFormModel extends AbstractFormModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeApplicationFormModel.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 7525339717578646695L;

	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private String ownershipPrefix;
	private String propertyActiveStatus;
	private String openMode;
	private Long length;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String paymentCheck;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private PortalService serviceMaster = new PortalService();
	private String isValidationError;
	private String edit;
	private String viewMode;
	private String scrutunyEditMode;
	private String saveMode;
	private String hideshowAddBtn;
	private String hideshowDeleteBtn;
	private String downloadMode;
	private String checkListApplFlag = null;
	private String AppChargeFlag = null;

	private Map<Long, List<String>> fileNames = new HashMap<>();
	private String backBtn;
	private String isfireNOC;
	private String assessmentCheckFlag;
	
	private String kdmcEnv;
	private List<String> flatNoList = new ArrayList<>();
	private String sudaEnv;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Long applicationId;
	private String applicantName;
	private String serviceName;
	

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private ICommonBRMSService brmsCommonService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Override
	public boolean saveForm() {

		setCommonFields(this);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());
		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		if (masDto.getCreatedBy() == null) {
			masDto.setTrdEty(MainetConstants.FlagS);
			masDto.setTrdStatus(lookUp.getLookUpId());
			masDto.setCreatedBy(createdBy);
			masDto.setCreatedDate(newDate);
			masDto.setOrgid(orgId);
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.setSource(MainetConstants.MENU.P);
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
			masDto.setTrdStatus(lookUp.getLookUpId());
			masDto.setTrdEty(MainetConstants.FlagS);
			masDto.setUpdatedBy(createdBy);
			masDto.setUpdatedDate(newDate);
			masDto.setOrgid(orgId);
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.setSource(MainetConstants.MENU.P);
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

			masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				if (itemDto.getCreatedBy() == null) {
					itemDto.setCreatedBy(createdBy);
					itemDto.setCreatedDate(newDate);
					itemDto.setOrgid(orgId);
					itemDto.setLgIpMac(lgIp);
				} else {
					itemDto.setUpdatedBy(createdBy);
					itemDto.setUpdatedDate(newDate);
					itemDto.setOrgid(orgId);
					itemDto.setLgIpMac(lgIp);
				}
			});

		}

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO.setDepartmentName(MainetConstants.TradeLicense.SERVICE_SHORT_CODE);
		masDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		masDto.setUserId((UserSession.getCurrent().getEmployee().getEmpId()));

		if (MainetConstants.FlagY.equalsIgnoreCase(getAppChargeFlag())) {

			masDto.setFree(false);
		} else {
			masDto.setFree(true);

		}
		masDto = ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class)
				.saveAndUpdateApplication(masDto);
		this.setTradeMasterDetailDTO(masDto);
		if(null!=masDto.getTenant() && null!=masDto.getApmApplicationId()){
			try{
				ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class).aapaleSarakarPortalEntry(masDto);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		final CommonChallanDTO offline = getOfflineDTO();
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
        //125445
		final Long serviceId = iPortalServiceMasterService.getServiceId(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,
				orgId);
		/*
		 * for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
		 * offline.getFeeIds().put(entry.getKey(), entry.getValue()); }
		 */	
		CommonChallanDTO dto  = ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class)
				.getFeesId(masDto);
		if (!dto.getFeeIds().isEmpty()) {
       	offline.setFeeIds(dto.getFeeIds());
		this.getTradeMasterDetailDTO().setFeeIds(dto.getFeeIds());
		}
		masDto.setFeeIds(offline.getFeeIds());
		if (MainetConstants.FlagY.equalsIgnoreCase(getAppChargeFlag())) {
			setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
			masDto.setFree(false);
		} else {
			masDto.setFree(true);

		}
		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.SERVICE_SHORT_CODE);
		requestDTO1.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
		if (getTradeMasterDetailDTO().getApmApplicationId() != null)
		requestDTO1.setIdfId(getTradeMasterDetailDTO().getApmApplicationId().toString());
		requestDTO1.setDepartmentName("ML");
		requestDTO1.setApplicationId(getTradeMasterDetailDTO().getApmApplicationId());
		requestDTO1.setDeptId(getServiceMaster().getPsmDpDeptid());
		requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		requestDTO1.setServiceId(getServiceMaster().getServiceId());
		requestDTO1.setReferenceId(getTradeMasterDetailDTO().getApmApplicationId().toString());
		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());

		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
	
		final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
		setServiceMaster(service);
		smsDto.setServName(service.getServiceName());
		smsDto.setEmail(masDto.getEmail());
		String url = "TradeApplicationForm.html";
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = UserSession.getCurrent().getLanguageId();
		// smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		/*
		 * iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.
		 * ONLINE_SERVICE, url, MainetConstants.SMS_EMAIL.GENERAL_MSG, smsDto, org,
		 * langId);
		 */

		return true;

	}

	private void setCommonFields(TradeApplicationFormModel model) {
		final Date sysDate = new Date();
		RequestDTO requestDTO = new RequestDTO();
		final TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgid(orgId);
		final Long serviceId = iPortalServiceMasterService.getServiceId(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,
				orgId);
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
			checkListModel.setServiceCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE);
			final WSRequestDTO checkRequestDto = new WSRequestDTO();
			
			
	        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
	        	List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
				lookupListLevel1 = CommonMasterUtility.getSecondLevelData("ITC", 1);
				TradeLicenseItemDetailDTO tradeLicenseItemDetailDTO = getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().get(0);
				
				List<LookUp> level1 = lookupListLevel1.parallelStream().filter(clList -> clList != null && clList.getLookUpId() == tradeLicenseItemDetailDTO.getTriCod1()).collect(Collectors.toList());
				String categoryShortCode = level1.get(0).getLookUpCode();
	        	checkListModel.setUsageSubtype1(categoryShortCode);
	        }
			// adding code for setting in userSubtype1 with subcatagory value
			// start
			try {

				List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.TradeLicense.TRD_ENV,
						UserSession.getCurrent().getOrganisation());
				if (lookupList != null && !lookupList.isEmpty()) {
					String env = lookupList.get(0).getLookUpCode();
					if (env != null && !env.isEmpty()) {
						if (env.equals(MainetConstants.ORG_CODE.SKDCL) || env.equals(MainetConstants.ORG_CODE_ASCL)) {
							if (this.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO() != null
									&& !this.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().isEmpty()) {
								LookUp lookUpList = CommonMasterUtility.getHierarchicalLookUp(this
										.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().get(0).getTriCod1(),
										UserSession.getCurrent().getOrganisation());
								checkListModel.setUsageSubtype1(lookUpList.getLookUpCode());
							}

						}
					}
				}
			} catch (Exception e) {

			}
			
			// end
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

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		// setServiceMaster(portalServiceMaster);
	    if (CollectionUtils.isNotEmpty(tradeMaster.getTradeLicenseOwnerdetailDTO())) {
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
		offline.setEmailId(ownDtlDto.getTroEmailid());		
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
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		/*
		 * offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		 * offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
		 * offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		 * offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		 */
		//124303
		StringBuilder ownName = new StringBuilder();
		String fName = null;
		tradeMaster.getTradeLicenseOwnerdetailDTO().forEach(dto -> {
			if (dto.getTroName() != null) {
				ownName.append(dto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
		});
		fName = ownName.deleteCharAt(ownName.length() - 1).toString();
		if (fName != null) {
			offline.setApplicantName(fName);
			offline.setApplicantFullName(fName);	
			this.setOwnerName(fName);
		}
	
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(portalServiceMaster.getServiceId());
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
		offline.setServiceName(portalServiceMaster.getServiceName());
		offline.setServiceNameMar(portalServiceMaster.getServiceNameReg());
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

	public boolean validateInputs() {
		validateBean(this, TradeApplicationFormValidator.class);
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		if (MainetConstants.FlagY.equalsIgnoreCase(getAppChargeFlag())) {
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		}
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public void getdataOfUploadedImage() {
		getFileNames().clear();
		List<String> fileNameList = null;
		Long count = 100L;
		Map<Long, List<String>> fileNames = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				fileNameList = new ArrayList<>();
				if (entry.getKey() >= 100) {
				for (final File file : entry.getValue()) {
					String fileName = null;

					try {
						final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
								MainetConstants.operator.FORWARD_SLACE);
						fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
					} catch (final Exception e) {
						throw new FrameworkException(e);
					}

					fileNameList.add(fileName);
				}
				fileNames.put(entry.getKey()-count, fileNameList);
				
				}
		  }
	 }
		setFileNames(fileNames);
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public String getOwnershipPrefix() {
		return ownershipPrefix;
	}

	public void setOwnershipPrefix(String ownershipPrefix) {
		this.ownershipPrefix = ownershipPrefix;
	}

	public String getPropertyActiveStatus() {
		return propertyActiveStatus;
	}

	public void setPropertyActiveStatus(String propertyActiveStatus) {
		this.propertyActiveStatus = propertyActiveStatus;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getIsValidationError() {
		return isValidationError;
	}

	public void setIsValidationError(String isValidationError) {
		this.isValidationError = isValidationError;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getScrutunyEditMode() {
		return scrutunyEditMode;
	}

	public void setScrutunyEditMode(String scrutunyEditMode) {
		this.scrutunyEditMode = scrutunyEditMode;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
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

	public String getDownloadMode() {
		return downloadMode;
	}

	public void setDownloadMode(String downloadMode) {
		this.downloadMode = downloadMode;
	}

	public Map<Long, List<String>> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Map<Long, List<String>> fileNames) {
		this.fileNames = fileNames;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getAppChargeFlag() {
		return AppChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		AppChargeFlag = appChargeFlag;
	}

	public List<DocumentDetailsVO> prepareFileUploadForImg(List<DocumentDetailsVO> img) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						// LOGGER.error("Exception has been occurred in file byte to string
						// conversions", e);
						throw new FrameworkException(e);
					}
				}
			}
		}
		if (img != null && !img.isEmpty() && !listOfString.isEmpty()) {
			long count = 100;
			for (final DocumentDetailsVO d : img) {
				if (d.getDocumentSerialNo() != null) {
					count = d.getDocumentSerialNo() - 1;

				}
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
				count++;
			}
		}

		return img;
	}

	public String getBackBtn() {
		return backBtn;
	}

	public void setBackBtn(String backBtn) {
		this.backBtn = backBtn;
	}

	public String getIsfireNOC() {
		return isfireNOC;
	}

	public void setIsfireNOC(String isfireNOC) {
		this.isfireNOC = isfireNOC;
	}

	public String getAssessmentCheckFlag() {
		return assessmentCheckFlag;
	}

	public void setAssessmentCheckFlag(String assessmentCheckFlag) {
		this.assessmentCheckFlag = assessmentCheckFlag;
	}

	public String getKdmcEnv() {
		return kdmcEnv;
	}

	public void setKdmcEnv(String kdmcEnv) {
		this.kdmcEnv = kdmcEnv;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public String getSudaEnv() {
		return sudaEnv;
	}

	public void setSudaEnv(String sudaEnv) {
		this.sudaEnv = sudaEnv;
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
	
}
