package com.abm.mainet.tradeLicense.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeBusinessNameService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class ChangeInBusinessNameFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private TradeMasterDetailDTO TradeMasterDetailDTO = new TradeMasterDetailDTO();
	private String immediateServiceMode;
	private String licenseDetails;
	private String licFromDateDesc;
	private String licToDateDesc;
	private String checklistCheck;
	private String paymentCheck;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private String viewMode;
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	private String issuanceDateDesc;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String imagePath;
	private String checklistFlag;
	private String scrutunyEditMode;
	private String hideshowAddBtn;
	private String hideshowDeleteBtn;
	private String valueEditableCheckFlag;
	Map<Long, TradeLicenseItemDetailDTO> oldItemListMap = new HashMap<>();
	private Long applicationId;
	private String applicantName;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private boolean valueEdit;
	private Long appid;
	private Long labelid;
	private Long serviceid;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private TbCfcApplicationMstService cfcApplicationMasterService;

	@Autowired
	TbTaxMasService tbTaxMasService;

	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
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
			// Defect #133748
			if (getTradeDetailDTO() != null
					&& CollectionUtils.isNotEmpty(getTradeDetailDTO().getTradeLicenseItemDetailDTO())) {
				getTradeDetailDTO().getTradeLicenseItemDetailDTO().forEach(olditemDto -> {

					getOldItemListMap().put(olditemDto.getTriId(), olditemDto);

				});
			}
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

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(service);

		if (getScrutunyEditMode() != null && ("SM".equals(getScrutunyEditMode()) && !this.isValueEdit())
				&& MainetConstants.FlagY.equals(service.getSmScrutinyChargeFlag())) {

			masDto = ApplicationContextProvider.getApplicationContext().getBean(IChangeBusinessNameService.class)
					.getScrutinyChargesFromBrmsRule(masDto);
		}

		BigDecimal paymentAmount = masDto.getTotalApplicationFee();
		if (paymentAmount != null) {
			this.getTradeMasterDetailDTO().setFree(false);

		} else {
			this.getTradeMasterDetailDTO().setFree(true);
		}
		// Defect #133748
		if (this.isValueEdit()) {
			masDto.setFree(false);
		}

		if (masDto.getTrdNewBusnm() != null) {
			masDto.setTrdBusnm(masDto.getTrdNewBusnm());
		}
		masDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		masDto.setServiceId(service.getSmServiceId());
		masDto.setDeptId(service.getTbDepartment().getDpDeptid());
		masDto = ApplicationContextProvider.getApplicationContext().getBean(IChangeBusinessNameService.class)
				.saveChangeBusinessNameService(masDto);
		setTradeMasterDetailDTO(masDto);

		final CommonChallanDTO offline = getOfflineDTO();
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		if ((MainetConstants.FlagY.equals(service.getSmAppliChargeFlag())) && (this.getScrutunyEditMode() == null)) {
			setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
		}
		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE);
		requestDTO1.setStatus(MainetConstants.FlagA);
		requestDTO1.setIdfId(masDto.getApmApplicationId().toString());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.MARKET_LICENSE);
		requestDTO1.setApplicationId(masDto.getApmApplicationId());
		requestDTO1.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
		requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		requestDTO1.setServiceId(getServiceMaster().getSmServiceId());
		requestDTO1.setReferenceId(getTradeMasterDetailDTO().getApmApplicationId().toString());

		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());
		if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")) {
			this.setSuccessMessage(getAppSession().getMessage("trade.successMessage"));
		}
		return true;
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE,
						session.getOrganisation().getOrgid());
		setServiceMaster(sm);
//Defect #131715
		List<TradeLicenseOwnerDetailDTO> ownerNameDto = tradeMaster.getTradeLicenseOwnerdetailDTO().stream()
				.filter(ownDto -> ownDto != null && (ownDto.getTroPr().equals(MainetConstants.FlagY)
						|| ownDto.getTroPr().equals(MainetConstants.FlagA)
						|| ownDto.getTroPr().equals(MainetConstants.FlagD)))
				.collect(Collectors.toList());
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
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
            //Defect #131715
		if (CollectionUtils.isNotEmpty(ownerNameDto)) {
			offline.setApplicantAddress(ownerNameDto.get(0).getTroAddress());
			offline.setMobileNumber(ownerNameDto.get(0).getTroMobileno());
			offline.setEmailId(ownerNameDto.get(0).getTroEmailid());
		}
		// comma seprated name Defect #111802

		// offline.setApplicantName(ownDtlDto.getTroName());
		offline.setApplicantName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagA));
		offline.setApplicantFullName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagA));
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());

		offline.setDeptId(sm.getTbDepartment().getDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(sm.getSmServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		offline.setServiceName(sm.getSmServiceName());
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
			if (tbTaxMas.getTaxCategory2() == appChargetaxId) {
				offline.getFeeIds().put(tbTaxMas.getTaxId(), Double.valueOf(tradeMaster.getApplicationCharge()));
			}
		}

		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			final ChallanMaster master = ApplicationContextProvider.getApplicationContext()
					.getBean(IChallanService.class).InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = ApplicationContextProvider.getApplicationContext()
					.getBean(IChallanService.class).savePayAtUlbCounter(offline, sm.getSmServiceName());
			setReceiptDTO(printDto);
		}
		setOfflineDTO(offline);
	}

	@SuppressWarnings("unchecked")
	public void getCheckListFromBrms() {

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			checkListModel2.setServiceCode("CBN");

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

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {

		if (getServiceMaster().getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.FlagY))
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}
	/* end of validation method */

	@Override
	public void populateApplicationData(long applicationId) {

		TbCfcApplicationMst entity = cfcApplicationMasterService.findById(applicationId);
		// code updated to show LOI Data on portal Dashboard
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
		// .getLicenseDetailsByLicenseNo(entity.getRefNo(),
		// UserSession.getCurrent().getOrganisation().getOrgid());
		this.setLicFromDateDesc(Utility.dateToString(tradeDetail.getTrdLicfromDate()));
		this.setLicToDateDesc(Utility.dateToString(tradeDetail.getTrdLictoDate()));
		setTradeMasterDetailDTO(tradeDetail);
		getTradeMasterDetailDTO().setTrdOwnerNm(entity.getApmFname());
       //Defect #135810
		setTradeDetailDTO(tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(tradeDetail.getTrdLicno(),
				UserSession.getCurrent().getOrganisation().getOrgid()));
		this.setViewMode("Y");
		this.setValueEdit(false);
		this.setScrutunyEditMode(null);
		TradeMasterDetailDTO masDto = this.getTradeMasterDetailDTO();
		this.documentList = checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());

	}

	public String getImmediateServiceMode() {
		return immediateServiceMode;
	}

	public void setImmediateServiceMode(String immediateServiceMode) {
		this.immediateServiceMode = immediateServiceMode;
	}

	public String getLicenseDetails() {
		return licenseDetails;
	}

	public void setLicenseDetails(String licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public String getLicFromDateDesc() {
		return licFromDateDesc;
	}

	public void setLicFromDateDesc(String licFromDateDesc) {
		this.licFromDateDesc = licFromDateDesc;
	}

	public String getLicToDateDesc() {
		return licToDateDesc;
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

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getChecklistFlag() {
		return checklistFlag;
	}

	public void setChecklistFlag(String checklistFlag) {
		this.checklistFlag = checklistFlag;
	}

	public String getScrutunyEditMode() {
		return scrutunyEditMode;
	}

	public void setScrutunyEditMode(String scrutunyEditMode) {
		this.scrutunyEditMode = scrutunyEditMode;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
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

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return TradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		TradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public Map<Long, TradeLicenseItemDetailDTO> getOldItemListMap() {
		return oldItemListMap;
	}

	public void setOldItemListMap(Map<Long, TradeLicenseItemDetailDTO> oldItemListMap) {
		this.oldItemListMap = oldItemListMap;
	}

	public String getValueEditableCheckFlag() {
		return valueEditableCheckFlag;
	}

	public void setValueEditableCheckFlag(String valueEditableCheckFlag) {
		this.valueEditableCheckFlag = valueEditableCheckFlag;
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

}
