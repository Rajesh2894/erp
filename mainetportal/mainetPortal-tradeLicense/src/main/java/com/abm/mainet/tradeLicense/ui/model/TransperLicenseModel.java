package com.abm.mainet.tradeLicense.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
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
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.pg.dto.TbLoiMas;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.iTransferLicenseService;
import com.aspose.p2cbca448.i;

@Component
@Scope("session")
public class TransperLicenseModel extends AbstractFormModel {
	private static final long serialVersionUID = -4826562452521767777L;

	private List<TradeMasterDetailDTO> tradeMasterDetailDTOList = new ArrayList<>();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String licenseDetails;
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private String licFromDateDesc;
	private String licToDateDesc;
	private String checklistCheck = null;
	private String viewMode;
	private List<CFCAttachment> attachments = new ArrayList<>();
	private List<CFCAttachment> documentList = new ArrayList<>();
	private TradeMasterDetailDTO tradeMasterDetailDto = new TradeMasterDetailDTO();
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private String paymentCheck = null;
	private String checkListApplFlag;
	private String scrutunyEditMode;
	private String saveMode;
	private String ownershipPrefix;
	private String trdWard1Desc;
	private String trdWard2Desc;
	private String trdWard3Desc;
	private String trdWard4Desc;
	private String trdWard5Desc;
	private String ward1Level;
	private String ward2Level;
	private String ward3Level;
	private String ward4Level;
	private String ward5Level;
	private String licenseFromDateDesc;
	private String imagePath;
	private String dateDesc;
	private String categoryDesc;
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private String buttonViewMode = null;
	private Long appid;
	private Long length;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Long applicationId;
	private String applicantName;
	private String serviceName;
	private PortalService serviceMaster = new PortalService();
	private String tenant;

	private Long labelid;

	private Long serviceid;

	private String openMode;

	private Map<Long, List<String>> fileNames = new HashMap<>();
	private String downloadMode;
	private Map<Long, String> uploadedfile = new HashMap<>(0);
	private Map<String, File> UploadMap = new HashMap<>();
	private String hideshowAddBtn;
	private String hideshowDeleteBtn;
	private String temporaryDateHide;
	private String fileMode;
	private String edit;
	private String appChargeFlag;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private ICommonBRMSService brmsCommonService;
	@Autowired
	private iTransferLicenseService iTransferLicenseService;

	@Override
	public boolean saveForm() {
		setCommonFields(this);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();

		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();

		Date newDate = new Date();

		TradeMasterDetailDTO masDto = getTradeDetailDTO();
		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgId);
		masDto.setLgIpMacUpd(lgIpMacUpd);
		masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		List<DocumentDetailsVO> docs = getCheckList();

		if (docs != null) {

			docs = fileUpload.prepareFileUpload(docs);
		}

		masDto.setDocumentList(docs);
		masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		ServiceMaster service = null;
		// serviceMasterService.getServiceMasterByShortCode("TLA",
		// UserSession.getCurrent().getOrganisation().getOrgid());

		masDto.setFree(false);

		if (!this.getAppChargeFlag().equals(MainetConstants.FlagY)) {

			masDto.setFree(true);

		}

		masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
			if (ownDto.getCreatedBy() == null) {
				ownDto.setCreatedBy(createdBy);
				ownDto.setCreatedDate(newDate);
				ownDto.setOrgid(orgId);
				ownDto.setLgIpMac(lgIp);
			}
			// added code for change the owner status value as R when rejected at Checklist
			// verification time
			else if (ownDto.getTroPr() != null && ownDto.getTroPr().equals(MainetConstants.FlagY)) {
				ownDto.setTroPr(MainetConstants.FlagR);
			} else {
				ownDto.setUpdatedBy(createdBy);
				ownDto.setUpdatedDate(newDate);
				ownDto.setOrgid(orgId);
				ownDto.setLgIpMacUpd(lgIp);
				ownDto.setTroPr(MainetConstants.FlagD);

			}

		});
		// to add new owners only

		getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {

			if (ownDto.getCreatedBy() == null) {
				ownDto.setCreatedBy(createdBy);
				ownDto.setCreatedDate(newDate);
				ownDto.setOrgid(orgId);
				ownDto.setLgIpMac(lgIp);
				ownDto.setTroPr(MainetConstants.FlagY);
			} else {
				ownDto.setUpdatedBy(createdBy);
				ownDto.setUpdatedDate(newDate);
				ownDto.setOrgid(orgId);
				ownDto.setLgIpMac(lgIp);
				ownDto.setTroPr(MainetConstants.FlagD);

			}

			masDto.getTradeLicenseOwnerdetailDTO().add(ownDto);

		});

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());
		masDto.setTrdStatus(lookUp.getLookUpId());
		TradeMasterDetailDTO tradeMasterDetailDTO = iTransferLicenseService.saveTransferLicenseService(masDto);
		if(null!=tradeMasterDetailDTO.getTenant() && null!=tradeMasterDetailDTO.getApmApplicationId()){
			try{
				ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class).aapaleSarakarPortalEntry(tradeMasterDetailDTO);
				}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		this.setTradeMasterDetailDTO(tradeMasterDetailDTO);

		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + " "
				+ tradeMasterDetailDTO.getApmApplicationId());
		if (this.getAppChargeFlag().equals(MainetConstants.FlagY)) {
			masDto.setApplicationCharge(this.getTradeMasterDetailDto().getApplicationCharge());
			setTradeDetailDTO(masDto);
			final CommonChallanDTO offline = getOfflineDTO();
			Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());

			this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + " "
					+ tradeMasterDetailDTO.getApmApplicationId());
		}

		return true;
	}

	

	public Date getAppDate() {
		return appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public String getServiceName() {
		return serviceName;
	}


	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		final Long serviceId = iPortalServiceMasterService.getServiceId(MainetConstants.TradeLicense.TLA,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(serviceId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		// setServiceMaster(portalServiceMaster);
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
		offline.setAmountToPay(this.getTradeMasterDetailDto().getTotalApplicationFee().toString());
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
		offline.setEmailId(ownDtlDto.getTroEmailid());
		offline.setApplicantName(this.getOwnerName());
		offline.setApplicantFullName(this.getOwnerName());
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
		offline.setApplicantAddress(ownDtlDto.getTroAddress());
		offline.setMobileNumber(ownDtlDto.getTroMobileno());
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
			offline = iChallanService.generateChallanNumber(offline);
			/*
			 * offline.setChallanValidDate(master .getChallanValiDate());
			 * offline.setChallanNo(master.getChallanNo());
			 */
		}
		setOfflineDTO(offline);
	}

	public boolean validateInputs() {
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		if (this.getAppChargeFlag().equals("Y")) {
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		}

		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public void getCheckListFromBrms() throws FrameworkException {
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
			checkListModel.setServiceCode(MainetConstants.TradeLicense.TLA);
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

	public String getLicenseDetails() {
		return licenseDetails;
	}

	public void setLicenseDetails(String licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public String getLicFromDateDesc() {
		return licFromDateDesc;
	}

	public String getLicToDateDesc() {
		return licToDateDesc;
	}

	public void setLicFromDateDesc(String licFromDateDesc) {
		this.licFromDateDesc = licFromDateDesc;
	}

	public void setLicToDateDesc(String licToDateDesc) {
		this.licToDateDesc = licToDateDesc;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<CFCAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDto() {
		return tradeMasterDetailDto;
	}

	public void setTradeMasterDetailDto(TradeMasterDetailDTO tradeMasterDetailDto) {
		this.tradeMasterDetailDto = tradeMasterDetailDto;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
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

	public String getTrdWard1Desc() {
		return trdWard1Desc;
	}

	public void setTrdWard1Desc(String trdWard1Desc) {
		this.trdWard1Desc = trdWard1Desc;
	}

	public String getTrdWard2Desc() {
		return trdWard2Desc;
	}

	public void setTrdWard2Desc(String trdWard2Desc) {
		this.trdWard2Desc = trdWard2Desc;
	}

	public String getTrdWard3Desc() {
		return trdWard3Desc;
	}

	public void setTrdWard3Desc(String trdWard3Desc) {
		this.trdWard3Desc = trdWard3Desc;
	}

	public String getTrdWard4Desc() {
		return trdWard4Desc;
	}

	public void setTrdWard4Desc(String trdWard4Desc) {
		this.trdWard4Desc = trdWard4Desc;
	}

	public String getTrdWard5Desc() {
		return trdWard5Desc;
	}

	public void setTrdWard5Desc(String trdWard5Desc) {
		this.trdWard5Desc = trdWard5Desc;
	}

	public String getWard1Level() {
		return ward1Level;
	}

	public void setWard1Level(String ward1Level) {
		this.ward1Level = ward1Level;
	}

	public String getWard2Level() {
		return ward2Level;
	}

	public void setWard2Level(String ward2Level) {
		this.ward2Level = ward2Level;
	}

	public String getWard3Level() {
		return ward3Level;
	}

	public void setWard3Level(String ward3Level) {
		this.ward3Level = ward3Level;
	}

	public String getWard4Level() {
		return ward4Level;
	}

	public void setWard4Level(String ward4Level) {
		this.ward4Level = ward4Level;
	}

	public String getWard5Level() {
		return ward5Level;
	}

	public void setWard5Level(String ward5Level) {
		this.ward5Level = ward5Level;
	}

	public String getLicenseFromDateDesc() {
		return licenseFromDateDesc;
	}

	public void setLicenseFromDateDesc(String licenseFromDateDesc) {
		this.licenseFromDateDesc = licenseFromDateDesc;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ITradeLicenseApplicationService getTradeLicenseApplicationService() {
		return tradeLicenseApplicationService;
	}

	public void setTradeLicenseApplicationService(ITradeLicenseApplicationService tradeLicenseApplicationService) {
		this.tradeLicenseApplicationService = tradeLicenseApplicationService;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbLoiMas> getTbLoiMas() {
		return tbLoiMas;
	}

	public void setTbLoiMas(List<TbLoiMas> tbLoiMas) {
		this.tbLoiMas = tbLoiMas;
	}

	public String getButtonViewMode() {
		return buttonViewMode;
	}

	public void setButtonViewMode(String buttonViewMode) {
		this.buttonViewMode = buttonViewMode;
	}

	public List<TradeMasterDetailDTO> getTradeMasterDetailDTOList() {
		return tradeMasterDetailDTOList;
	}

	public void setTradeMasterDetailDTOList(List<TradeMasterDetailDTO> tradeMasterDetailDTOList) {
		this.tradeMasterDetailDTOList = tradeMasterDetailDTOList;
	}

	public Long getAppid() {
		return appid;
	}

	public void setAppid(Long appid) {
		this.appid = appid;
	}

	public Long getLabelid() {
		return labelid;
	}

	public void setLabelid(Long labelid) {
		this.labelid = labelid;
	}

	public Long getServiceid() {
		return serviceid;
	}

	public void setServiceid(Long serviceid) {
		this.serviceid = serviceid;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getOwnershipPrefix() {
		return ownershipPrefix;
	}

	public void setOwnershipPrefix(String ownershipPrefix) {
		this.ownershipPrefix = ownershipPrefix;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public Map<Long, List<String>> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Map<Long, List<String>> fileNames) {
		this.fileNames = fileNames;
	}

	public String getDownloadMode() {
		return downloadMode;
	}

	public void setDownloadMode(String downloadMode) {
		this.downloadMode = downloadMode;
	}

	public Map<Long, String> getUploadedfile() {
		return uploadedfile;
	}

	public void setUploadedfile(Map<Long, String> uploadedfile) {
		this.uploadedfile = uploadedfile;
	}

	public Map<String, File> getUploadMap() {
		return UploadMap;
	}

	public void setUploadMap(Map<String, File> uploadMap) {
		UploadMap = uploadMap;
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

	public String getFileMode() {
		return fileMode;
	}

	public void setFileMode(String fileMode) {
		this.fileMode = fileMode;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
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

	public String getAppChargeFlag() {
		return appChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		this.appChargeFlag = appChargeFlag;
	}

	private void setCommonFields(TransperLicenseModel model) {
		final Date sysDate = new Date();
		RequestDTO requestDTO = new RequestDTO();
		final TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgid(orgId);
		final Long serviceId = iPortalServiceMasterService.getServiceId(MainetConstants.TradeLicense.TLA, orgId);
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



	public String getTenant() {
		return tenant;
	}



	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
}
