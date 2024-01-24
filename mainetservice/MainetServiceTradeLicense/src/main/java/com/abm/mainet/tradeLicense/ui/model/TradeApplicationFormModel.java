package com.abm.mainet.tradeLicense.ui.model;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.validator.TradeApplicationFormValidator;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

@Component
@Scope("session")
public class TradeApplicationFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private String ownershipPrefix;
	private String propertyActiveStatus;
	private Long length;
	private String openMode;
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String paymentCheck = null;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String edit;
	private String viewMode;
	private String scrutunyEditMode;
	private String saveMode;
	private Map<Long, List<String>> fileNames = new HashMap<>();
	private String downloadMode;
	private Map<Long, String> uploadedfile = new HashMap<>(0);
	private Map<String, File> UploadMap = new HashMap<>();
	private String hideshowAddBtn;
	private String hideshowDeleteBtn;
	private String temporaryDateHide;
	private List<CFCAttachment> attachments = new ArrayList<>();
	private String fileMode;
	private String checkListApplFlag = null;
	private String serviceName;
	private Long applicationId;
	private String applicantName;
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	private Long licMaxTenureDays;
	private String propertyActive;
	private Long appid;
	private Long labelid;
	private Long serviceid;
	private String isfireNOC;
	private String assessmentCheckFlag;
	private String kdmcEnv;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private boolean valueEdit;
	private List<String> flatNoList = new ArrayList<>();
    private String sudaEnv;
    private String hideFlag;
    private int itcLevel;


	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private ILicenseValidityMasterService licenseValidityMasterService;
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;
	@Autowired
	TbTaxMasService tbTaxMasService;

	@Override
	public boolean saveForm() throws FrameworkException {

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
			masDto.setSource(MainetConstants.FlagS);

		
			 boolean  envFlag=Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL);
	            masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
	                if(envFlag) {
	                    String ownwrName ="";
	                    String ownwrFathName = "";
	                    ownwrName=(ownDto.getOwnFname()!=null?ownDto.getOwnFname():"");
	                    ownwrName+=(ownDto.getOwnMname()!=null?" "+ownDto.getOwnMname():"");
	                    ownwrName+=(ownDto.getOwnLname()!=null?" "+ownDto.getOwnLname():"");
	                    ownwrFathName=(ownDto.getGurdFname()!=null?ownDto.getGurdFname():"");
	                    ownwrFathName+=(ownDto.getGurdMname()!=null?" "+ownDto.getGurdMname():"");
	                    ownwrFathName+=(ownDto.getGurdLname()!=null?" "+ownDto.getGurdLname():"");
	                    ownDto.setTroName(ownwrName);
	                    ownDto.setTroMname(ownwrFathName);                
	                }
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
			masDto.setSource(MainetConstants.FlagS);
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

		tradeMasterDetailDTO.setFree(false);
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());

		if (MainetConstants.FlagY.equals(service.getSmScrutinyApplicableFlag())) {
			tradeMasterDetailDTO.setFree(true);
		}

		if (service.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			tradeMasterDetailDTO.setFree(false);
			this.getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}

		if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")) {

			tradeMasterDetailDTO.setFree(true);
		}
 //Defect #133748
		if (masDto.isEditValue()) {
			tradeMasterDetailDTO.setFree(false);
		}
		masDto.setUserId(createdBy);

		// save or update application
		masDto = ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class)
				.saveAndUpdateApplication(masDto);

		setTradeMasterDetailDTO(masDto);

		if (service.getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.Y_FLAG)) {
			if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")
					&& !service.getSmFeesSchedule().equals(0l)) {
			} else {
				final CommonChallanDTO offline = getOfflineDTO();
				Map<Long, Double> details = new HashMap<>(0);
				final Map<Long, Long> billDetails = new HashMap<>(0);
				setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
			}
		}

		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());
		if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")) {
			this.setSuccessMessage(getAppSession().getMessage("trade.successMessage"));
		}
		return true;
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
			checkListModel2.setServiceCode(MainetConstants.TradeLicense.SERVICE_CODE);

			List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
			lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
					UserSession.getCurrent().getOrganisation().getOrgid());

			TradeLicenseItemDetailDTO tradeLicenseItemDetailDTO = getTradeMasterDetailDTO()
					.getTradeLicenseItemDetailDTO().get(0);

			List<LookUp> level1 = lookupListLevel1.parallelStream()
					.filter(clList -> clList != null && clList.getLookUpId() == tradeLicenseItemDetailDTO.getTriCod1())
					.collect(Collectors.toList());
			// adding code for setting in userSubtype1 with subcatagory value
			// start
			try {

				List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.TradeLicense.TRD_ENV,
						UserSession.getCurrent().getOrganisation());
				if (lookupList != null && !lookupList.isEmpty()) {
					String env = lookupList.get(0).getLookUpCode();
					if (env != null && !env.isEmpty()) {
						if (env.equals(MainetConstants.ENV_SKDCL) || env.equals(MainetConstants.ENV_ASCL)) {
							if (tradeLicenseItemDetailDTO != null) {
								LookUp lookUpList = CommonMasterUtility.getHierarchicalLookUp(
										tradeLicenseItemDetailDTO.getTriCod1(),
										UserSession.getCurrent().getOrganisation().getOrgid());
								checkListModel2.setUsageSubtype1(lookUpList.getLookUpCode());
							}

						}
					}
				}
			} catch (Exception e) {

			}
//end	
			String categoryShortCode = level1.get(0).getLookUpCode();
			if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
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

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,
						session.getOrganisation().getOrgid());
		setServiceMaster(sm);
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
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
		offline.setEmailId(ownDtlDto.getTroEmailid());
		offline.setApplicantName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagA));
		offline.setApplicantFullName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagA));
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
		offline.setApplicantAddress(ownDtlDto.getTroAddress());
		offline.setMobileNumber(ownDtlDto.getTroMobileno());
		offline.setDeptId(sm.getTbDepartment().getDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(sm.getSmServiceId());
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

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {

		validateBean(this, TradeApplicationFormValidator.class);
		if (MainetConstants.FlagY.equals(this.getServiceMaster().getSmAppliChargeFlag())) {
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		}
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}
	/* end of validation method */

	@Override
	public void populateApplicationData(long applicationId) {
		this.setTradeMasterDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
		getAttachments().clear();
		this.setScrutunyEditMode(null);
		// D#130231 for editing value at the time of scrutiny view edit
		this.setValueEdit(false);
		this.setOpenMode("D");
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
		this.setFileMode("G");
		//#142492
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			LookUp lookUp = null;
			this.setSudaEnv(MainetConstants.FlagY);
			if (masDto.getTrdLictype() != null)
				lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
					UserSession.getCurrent().getOrganisation());
		   if (lookUp != null && lookUp.getLookUpCode() != null)
		   if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.FlagT)) 
			this.setHideFlag(MainetConstants.FlagY);
		   else
			   this.setHideFlag(MainetConstants.FlagN);
		} else {
			this.setSudaEnv(MainetConstants.FlagN);
		}
	
		this.documentList = checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());

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
							e.printStackTrace();
						}

						fileNameList.add(fileName);
					}
					fileNames.put(entry.getKey() - count, fileNameList);

				}
			}
		}
		setFileNames(fileNames);
	}

	public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, long triCod1) {

		Long licenseMaxTenureDays = 0l;
		Date currentDate = new Date();
		if (licToDate != null && Utility.compareDate(new Date(), licToDate)) {
			currentDate = licToDate;
		}
		List<LicenseValidityMasterDto> licValMasterDtoList = new ArrayList<>();
		// The system should have provision to define Item Category and Sub-category in
		// the license validity master User Story #113614
		List<LicenseValidityMasterDto> licValidityMster = new ArrayList<LicenseValidityMasterDto>();
		String skdclEnv = ApplicationContextProvider.getApplicationContext()
				.getBean(ITradeLicenseApplicationService.class).checkEnviorement();
		if (skdclEnv != null && skdclEnv.equals(MainetConstants.ENV_SKDCL)) {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId, triCod1,
					MainetConstants.ZERO_LONG);
		} else {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId,
					MainetConstants.ZERO_LONG, MainetConstants.ZERO_LONG);
		}
		// end
		if (CollectionUtils.isNotEmpty(licValidityMster)) {

			licValMasterDtoList = licValidityMster.stream()
					.filter(k -> (k.getLicType() == this.getTradeMasterDetailDTO().getTrdLictype().longValue()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(licValMasterDtoList) || licValMasterDtoList.size() <= 0) {

				return null;
			}

			LicenseValidityMasterDto licValMasterDto = licValMasterDtoList.get(0);

			Organisation organisationById = ApplicationContextProvider.getApplicationContext()
					.getBean(IOrganisationService.class).getOrganisationById(orgId);
			LookUp dependsOnLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(licValMasterDto.getLicDependsOn(), organisationById);

			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
					organisationById);

			Long tenure = Long.valueOf(licValMasterDto.getLicTenure());
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagD)) {
				licenseMaxTenureDays = tenure - 1;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagH)) {
				licenseMaxTenureDays = 1l;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagM)) {
				int currentYear = Integer.valueOf(Year.now().toString());
				Month monthObject = Month.from(LocalDate.now());
				int month = monthObject.getValue();
				licenseMaxTenureDays = Long.valueOf(YearMonth.of(currentYear, month).lengthOfMonth());
				if (tenure > 1) {
					for (int i = 2; i <= tenure; i++) {
						licenseMaxTenureDays = licenseMaxTenureDays
								+ Long.valueOf(YearMonth.of(currentYear, ++month).lengthOfMonth());
						if (month == 12) {
							month = 0;
							currentYear++;
						}
					}
				}
			}

			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagY)) {
				if (StringUtils.equalsIgnoreCase(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagF)) {
					int month = 0;
					int currentYear = Integer.valueOf(Year.now().toString());
					TbFinancialyear financialYear;
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					int monthValue = currLocalDate.getMonthValue();
					int currentMonthValue = currLocalDate.getMonthValue();

					if (monthValue > 3 && monthValue <= 12) {

						for (int i = monthValue; i <= 15; i++) {

							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;

								monthValue++;

							} else {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}

							}
						}

					} else {
						for (int i = monthValue; i <= 3; i++) {
							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;
								monthValue++;
								// Long currMonthDays = Long.valueOf(YearMonth.of(currentYear,
								// monthValue).lengthOfMonth());
							} else {
								licenseMaxTenureDays = licenseMaxTenureDays
										+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
								monthValue++;
							}

						}
					}
					if (tenure > 1) {
						for (int i = 2; i <= tenure; i++) {
							monthValue = 4;
							currentYear++;
							month = 0;
							for (int j = monthValue; j <= 15; j++) {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}
							}
						}
					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagC)) {
					int currentYear = Integer.valueOf(Year.now().toString());
					LocalDate currLocalDate = LocalDate.now();
					LocalDate with = currLocalDate.with(lastDayOfYear());

					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
							Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));
					if (tenure > 1) {

						for (int i = 2; i <= tenure; i++) {
							Year year = Year.of(++currentYear);
							licenseMaxTenureDays = licenseMaxTenureDays + Long.valueOf(year.length());
						}

					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagA)) {
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					// Defect #36796
					int leapYear = 0;

					if (currLocalDate.getMonthValue() == 2 && currLocalDate.getDayOfMonth() == 29) {
						leapYear = 1;
					}
					Instant instant1 = LocalDate
							.of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
									currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth() - leapYear)
							.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Date from1 = Date.from(instant1);
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1));

				}
			}
		}

		return licenseMaxTenureDays;

	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
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

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
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

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
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

	public String getTemporaryDateHide() {
		return temporaryDateHide;
	}

	public void setTemporaryDateHide(String temporaryDateHide) {
		this.temporaryDateHide = temporaryDateHide;
	}

	public List<CFCAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<CFCAttachment> attachments) {
		this.attachments = attachments;
	}

	public String getFileMode() {
		return fileMode;
	}

	public void setFileMode(String fileMode) {
		this.fileMode = fileMode;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getServiceName() {
		return serviceName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public Long getLicMaxTenureDays() {
		return licMaxTenureDays;
	}

	public void setLicMaxTenureDays(Long licMaxTenureDays) {
		this.licMaxTenureDays = licMaxTenureDays;
	}

	public String getPropertyActive() {
		return propertyActive;
	}

	public void setPropertyActive(String propertyActive) {
		this.propertyActive = propertyActive;
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

	public String getHideFlag() {
		return hideFlag;
	}

	public void setHideFlag(String hideFlag) {
		this.hideFlag = hideFlag;
	}

	public int getItcLevel() {
		return itcLevel;
	}

	public void setItcLevel(int itcLevel) {
		this.itcLevel = itcLevel;
	}

}
