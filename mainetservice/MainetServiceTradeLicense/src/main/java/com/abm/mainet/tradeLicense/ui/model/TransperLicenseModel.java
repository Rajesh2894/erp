package com.abm.mainet.tradeLicense.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITransferLicenseService;
import com.abm.mainet.tradeLicense.ui.validator.TransferLicenseValidator;


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
	private List<TbApprejMas> apprejMasList = new ArrayList<>();
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
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private String buttonViewMode = null;
	private Long appid;
	private Long length;
	private Long licCateg;

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
	private String ownerName;

	private TbCfcApplicationMstEntity cfcEntity;
	private Long applicationId;
	private String applicantName;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private boolean valueEdit;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private ICFCApplicationMasterService cfcService;
	@Resource
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	ITransferLicenseService iTransferLicenseService;

	@Autowired
	TbTaxMasService tbTaxMasService;

	@Override
	public boolean saveForm() {

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

		ServiceMaster service = serviceMasterService.getServiceMasterByShortCode("TLA",
				UserSession.getCurrent().getOrganisation().getOrgid());

		masDto.setFree(false);

		if (service.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)
				&& !service.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {

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

		this.setSuccessMessage(
				getAppSession().getMessage("trade.successMsg") + " " + tradeMasterDetailDTO.getApmApplicationId());
		if (service.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			setTradeDetailDTO(masDto);
			final CommonChallanDTO offline = getOfflineDTO();
			Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			setChallanDToandSaveChallanData(offline, details, billDetails, getTradeDetailDTO());

			this.setSuccessMessage(
					getAppSession().getMessage("trade.successMsg") + " " + tradeMasterDetailDTO.getApmApplicationId());
		}

		return true;
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("TLA",
				session.getOrganisation().getOrgid());
		setServiceMaster(sm);
     //Defect #131715
		List<TradeLicenseOwnerDetailDTO> ownerNameDto = tradeMaster.getTradeLicenseOwnerdetailDTO().stream()
				.filter(ownDto -> ownDto != null && ownDto.getTroPr().equals(MainetConstants.FlagY))
				.collect(Collectors.toList());
		if (tradeMaster.getTotalApplicationFee() != null) {
			offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
		}
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
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setApplicantName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagY));
		offline.setApplicantFullName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagY));
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
    //Defect #131715
		if (CollectionUtils.isNotEmpty(ownerNameDto)) {
			offline.setApplicantAddress(ownerNameDto.get(0).getTroAddress());
			offline.setMobileNumber(ownerNameDto.get(0).getTroMobileno());
			offline.setEmailId(ownerNameDto.get(0).getTroEmailid());
		}
		offline.setDeptId(sm.getTbDepartment().getDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(sm.getSmServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			offline.setDocumentUploaded(true);
		}

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, session.getOrganisation());

		final List<TbTaxMasEntity> taxesMaster = tbTaxMasService.fetchAllApplicableServiceCharge(sm.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2,
				UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpId();

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			if ((tbTaxMas.getTaxCategory2() == appChargetaxId)) {
				offline.getFeeIds().put(tbTaxMas.getTaxId(), Double.valueOf(tradeMaster.getApplicationCharge()));
			}
		}

		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		if (offline.getOflPaymentMode() != 0l) {
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
		}
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, sm.getSmServiceName());
			setReceiptDTO(printDto);
		}
		setOfflineDTO(offline);
	}

	public boolean validateInputs() {
		validateBean(this, TransferLicenseValidator.class);

		if (getServiceMaster().getSmAppliChargeFlag().equals("Y")) {
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

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			checkListModel2.setServiceCode("TLA");
			
			if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
				List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
				lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
						UserSession.getCurrent().getOrganisation().getOrgid());
				
				List<LookUp> level1 = lookupListLevel1.parallelStream()
						.filter(clList -> clList != null && clList.getLookUpId() == getLicCateg())
						.collect(Collectors.toList());
				String categoryShortCode = level1.get(0).getLookUpCode();
				checkListModel2.setUsageSubtype1(categoryShortCode);
			}
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel2);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
					long cnt = 1;
					for (final DocumentDetailsVO doc : checkListList) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					if ((checkListList != null) && !checkListList.isEmpty()) {
						setCheckList(checkListList);
					}
				} else {

					addValidationError(ApplicationSession.getInstance().getMessage("No CheckList Found"));

					throw new FrameworkException("No CheckList Found");

				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@Override
	public void populateApplicationData(long applicationId) {
		this.setTradeMasterDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
		this.setTradeDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));

		List<TradeLicenseOwnerDetailDTO> OldOwnerList = getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().stream()
				.filter(i -> i.getTroPr() != null).filter(k -> k.getTroPr().equalsIgnoreCase(MainetConstants.FlagD))
				.collect(Collectors.toList());
		this.setScrutunyEditMode(null);
		getTradeDetailDTO().setTradeLicenseOwnerdetailDTO(OldOwnerList);
		// Defect #110792
		if (!this.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
			StringBuilder ownName = new StringBuilder();
			String ownerName = "";
			List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = null;
			ownerDetailDTOList = this.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().parallelStream()
					.filter(ownDto -> ownDto != null && (ownDto.getTroPr() != null)
							&& (ownDto.getTroPr().equalsIgnoreCase(MainetConstants.FlagA)
									|| ownDto.getTroPr().equalsIgnoreCase(MainetConstants.FlagD)))
					.collect(Collectors.toList());
			for (TradeLicenseOwnerDetailDTO ownDto : ownerDetailDTOList) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (ownName.length() > 0) {
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			}
			if (StringUtils.isNotBlank(ownerName)) {
				this.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).setTroName(ownerName);
			}
		}

		List<TradeLicenseOwnerDetailDTO> newOwnerList = getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO()
				.stream().filter(i -> i.getTroPr() != null)
				.filter(k -> k.getTroPr().equalsIgnoreCase(MainetConstants.FlagY)).collect(Collectors.toList());

		getTradeMasterDetailDTO().setTradeLicenseOwnerdetailDTO(newOwnerList);

		getAttachments().clear();

		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		List<CFCAttachment> attachment = null;
		for (int i = 0; i < masDto.getTradeLicenseOwnerdetailDTO().size(); i++) {
			attachment = new ArrayList<>();
			attachment = checklistVerificationService.getDocumentUploadedByRefNo(
					masDto.getTradeLicenseOwnerdetailDTO().get(i).getTroId().toString(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (attachment != null) {
				// this.getAttachments().add(attachment.get(0));
				masDto.getTradeLicenseOwnerdetailDTO().get(i).setViewImg(attachment);
			}
		}

		setOpenMode(MainetConstants.FlagD);
		setViewMode(MainetConstants.FlagV);
		setDownloadMode(MainetConstants.FlagN);
		setHideshowAddBtn(MainetConstants.FlagY);
		setHideshowDeleteBtn(MainetConstants.FlagY);
		this.documentList = checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());

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

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
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

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
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

	public boolean updateForm() throws FrameworkException {

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();

		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();

		Date newDate = new Date();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());

		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		
			try {
				masDto = iTransferLicenseService.getTradeLicenceChargesFromBrmsRule(masDto);
			} catch (FrameworkException e) {
				throw new FrameworkException("unable to process request for Trade Licence Fee");
			}

			String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			masDto.setOrgid(orgId);
			masDto.setLgIpMacUpd(lgIpMacUpd);
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.setTrdStatus(lookUp.getLookUpId());
			TbCfcApplicationMstEntity tbCfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(
					masDto.getApmApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
			tbCfcApplicationMstEntity.setApmFname(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());

			TbCfcApplicationMstEntity cfcApplicationMstEntity = tbCfcApplicationMstJpaRepository
					.save(tbCfcApplicationMstEntity);

			cfcApplicationMstEntity.getApmFname();
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
		
		TradeMasterDetailDTO tradeMasterDetailDTO = ApplicationContextProvider.getApplicationContext()
				.getBean(ITransferLicenseService.class).updateTransferLicenseService(masDto);

		this.setSuccessMessage("TRANSFER LICENSE DATA UPDATED SUCCESSFULLY");

		return true;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
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

	public boolean isValueEdit() {
		return valueEdit;
	}

	public void setValueEdit(boolean valueEdit) {
		this.valueEdit = valueEdit;
	}

	public Long getLicCateg() {
		return licCateg;
	}

	public void setLicCateg(Long licCateg) {
		this.licCateg = licCateg;
	}

}
