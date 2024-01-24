package com.abm.mainet.tradeLicense.ui.model;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.RenewalMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.validator.RenewalLicenseApplicationValidator;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

@Component
@Scope("session")
public class RenewalLicenseFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private String paymentCheck;
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String immediateServiceMode;
	private Long licMaxTenureDays;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String checkListApplFlag;
	private String applicationchargeApplFlag;
	private String scrutinyAppFlag;
	private String ownerName;
	private TbCfcApplicationMstEntity cfcEntity;
	private Long applicationId;
	private String applicantName;
	private String scrutunyEditMode;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private String sudaEnv;
	
	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Resource

	@Autowired
	private ServiceMasterService serviceMasterService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	IRenewalLicenseApplicationService iRenewalLicenseApplicationService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private ILicenseValidityMasterService licenseValidityMasterService;
	@Autowired
	TbTaxMasService tbTaxMasService;
	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	public void getRenewalDate(Date tradeLicenseTodate, int noofdays) {

		Date newDate = new Date();
		Date renwalFromDate = Utility.getSubtractedDateBy(tradeLicenseTodate, noofdays);
		Date sysdate = Utility.getSubtractedDateBy(newDate, noofdays);
		Date renwalToDate = Utility.getAddedDateBy2(tradeLicenseTodate, noofdays);
		Date sysdate1 = Utility.getAddedDateBy2(newDate, noofdays);

		if (tradeLicenseTodate.compareTo(sysdate) >= 0 && tradeLicenseTodate.compareTo(sysdate1) <= 0) {
			if ((Utility.comapreDates(tradeLicenseTodate, newDate)) || tradeLicenseTodate.after(newDate)) {
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
						.setTreLicfromDate(Utility.getAddedDateBy2(newDate, 1));
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
						.setRenewalFromDateDesc(Utility.dateToString(Utility.getAddedDateBy2(tradeLicenseTodate, 1)));
				// getRenewalMasterDetailDTO().setTreLicfromDate(new Date(newDate.getTime() + 1
				// * 24 * 3600 * 1000));
				// getRenewalMasterDetailDTO().setRenewalFromDateDesc(Utility.dateToString(new
				// Date(newDate.getTim() + 1 * 24 *
				// 3600 * 1000)));
				// renewal to date is newDate+1
			} else if (tradeLicenseTodate.before(newDate)) {
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO().setTreLicfromDate(newDate);
				// getRenewalMasterDetailDTO().setRenewalFromDateDesc(Utility.dateToString(newDate));
				getTradeMasterDetailDTO().getRenewalMasterDetailDTO()
						.setRenewalFromDateDesc(Utility.dateToString(Utility.getAddedDateBy2(newDate, 1)));
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

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date newDate = new Date();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		String empServerName = UserSession.getCurrent().getEmployee().getEmppiservername();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());
		//User Story #147721
  		try {
  			if(getOfflineDTO().getPayModeIn()!=null) {
  			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(getOfflineDTO().getPayModeIn());
  			if (lookup != null && lookup.getLookUpCode().equals("POS")) {
  				CommonChallanDTO dto=tradeLicenseApplicationService.createPushToPayApiRequest(getOfflineDTO(), empId, orgId,MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE , getOfflineDTO().getAmountToPay().toString());
  				if(dto!=null && dto.getPushToPayErrMsg()!=null) {
  					this.addValidationError(dto.getPushToPayErrMsg());
  					return false;
  				}
  			}}
  		} catch (Exception e) {
  			// TODO: handle exception
  		}

		List<DocumentDetailsVO> docs = getCheckList();

		if (docs != null) {

			docs = fileUpload.prepareFileUpload(docs);
		}

		getTradeMasterDetailDTO().setDocumentList(docs);

		TradeMasterDetailDTO tradeMasterDetailDTO = getTradeMasterDetailDTO();
		if (tradeMasterDetailDTO.getRenewalMasterDetailDTO().getCreatedBy() == null) {
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setCreatedBy(createdBy);
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setTreStatus("R");
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setOrgid(orgId);
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setLangId((long) UserSession.getCurrent().getLanguageId());
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setLgIpMac(empServerName);
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setCreatedDate(newDate);
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setLgIpMac(empServerName);
			tradeMasterDetailDTO.setLangId((long) UserSession.getCurrent().getLanguageId());

		} else {
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setUpdatedBy(empId);
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setUpdatedDate(newDate);
			tradeMasterDetailDTO.getRenewalMasterDetailDTO().setLgIpMacUpd(empServerName);
			tradeMasterDetailDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
		}

		// TradeMasterDetailDTO tradeDto = getTradeMasterDetailDTO();

		ServiceMaster service = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		getTradeMasterDetailDTO().setServiceId(service.getSmServiceId());
		BigDecimal paymentAmount = getTradeMasterDetailDTO().getTotalApplicationFee();
		if (paymentAmount != null) {
			getTradeMasterDetailDTO().setFree(false);

		} else {
			getTradeMasterDetailDTO().setFree(true);
		}

		getTradeMasterDetailDTO().setChecklistAppFlag(getCheckListApplFlag());

		getTradeMasterDetailDTO().setTrdStatus(lookUp.getLookUpId());
		//getTradeMasterDetailDTO().setSource(MainetConstants.FlagS);

		// renewalDto.setApmApplicationId(tradeDto.getApmApplicationId());
		iRenewalLicenseApplicationService.saveAndUpdateApplication(getTradeMasterDetailDTO());
		setTradeMasterDetailDTO(getTradeMasterDetailDTO());

		if (service.getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.Y_FLAG)) {
			final CommonChallanDTO offline = getOfflineDTO();
			Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
			
			RequestDTO requestDTO1 = new RequestDTO();
			requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			requestDTO1.setDepartmentName(MainetConstants.TradeLicense.SERVICE_CODE);
			requestDTO1.setStatus(MainetConstants.FlagA);
			requestDTO1.setIdfId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId().toString());
			requestDTO1.setDepartmentName(MainetConstants.TradeLicense.MARKET_LICENSE);
			requestDTO1.setApplicationId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId());
			requestDTO1.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
			requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
			requestDTO1.setServiceId(getServiceMaster().getSmServiceId());
			requestDTO1.setReferenceId(
					getTradeMasterDetailDTO().getRenewalMasterDetailDTO().getApmApplicationId().toString());

		}
		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg")
				+ tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId());

		return true;
	}

	

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE,
						session.getOrganisation().getOrgid());
		setServiceMaster(sm);
            //Defect #131715
		List<TradeLicenseOwnerDetailDTO> ownerDetails = tradeMaster.getTradeLicenseOwnerdetailDTO().stream()
				.filter(trd -> trd.getTroPr().equals(MainetConstants.FlagA) || trd.getTroPr().equals(MainetConstants.FlagD))
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

		offline.setApplicantName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagA));
		offline.setApplicantFullName(tradeLicenseApplicationService.getOwnersName(tradeMaster, MainetConstants.FlagA));
		offline.setApplNo(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId());
		offline.setUniquePrimaryId(tradeMasterDetailDTO.getRenewalMasterDetailDTO().getApmApplicationId().toString());
		if (CollectionUtils.isNotEmpty(ownerDetails)) {
			offline.setApplicantAddress(ownerDetails.get(0).getTroAddress());
			offline.setMobileNumber(ownerDetails.get(0).getTroMobileno());
			offline.setEmailId(ownerDetails.get(0).getTroEmailid());
		}
		offline.setDeptId(sm.getTbDepartment().getDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(sm.getSmServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		/*
		 * // #130728 if
		 * (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.ENV_SKDCL)) { //Defect #139300 Date renwalToDate = null; if
		 * (tradeMaster.getRenewCycle() != null) { renwalToDate =
		 * Utility.getAddedYearsBy(tradeMaster.getTrdLictoDate(),
		 * tradeMaster.getRenewCycle().intValue()); } offline.setFromedate(new
		 * Date(tradeMaster.getTrdLictoDate().getTime() + (1000 * 60 * 60 * 24)));
		 * offline.setToDate(renwalToDate); } else {
		 */
			offline.setFromedate(tradeMaster.getRenewalMasterDetailDTO().getTreLicfromDate());
			offline.setToDate(tradeMaster.getRenewalMasterDetailDTO().getTreLictoDate());
		/* } */

		offline.setServiceName(sm.getSmServiceName());
		offline.setServiceNameMar(sm.getSmServiceNameMar());
		offline.setPosPayApplicable(true);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		offline.setFeeIds(tradeMaster.getFeeIds());
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, session.getOrganisation());

		/*
		 * final List<TbTaxMas> taxesMaster =
		 * ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.
		 * class) .findAllTaxesForBillGeneration(session.getOrganisation().getOrgid(),
		 * sm.getTbDepartment().getDpDeptid(), chargeApplicableAtScrutiny.getLookUpId(),
		 * null);
		 */

		final List<TbTaxMasEntity> taxesMaster = tbTaxMasService.fetchAllApplicableServiceCharge(sm.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2,
				UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpId();

		/*
		 * for (TbTaxMasEntity tbTaxMas : taxesMaster) { if ((tbTaxMas.getTaxCategory2()
		 * == appChargetaxId)) { offline.getFeeIds().put(tbTaxMas.getTaxId(),
		 * Double.valueOf(tradeMaster.getApplicationCharge())); } }
		 */
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

	public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licFromDate, Date licToDate)
			throws Exception {
		Long licenseMaxTenureDays = 0l;
		Date currentDate = new Date();

		if (licToDate != null && Utility.compareDate(new Date(), licToDate)) {
			currentDate = licToDate;
		}
		List<LicenseValidityMasterDto> licValidityMster = licenseValidityMasterService.searchLicenseValidityData(
				UserSession.getCurrent().getOrganisation().getOrgid(), deptId, serviceId, MainetConstants.ZERO_LONG,
				MainetConstants.ZERO_LONG);
		if (CollectionUtils.isNotEmpty(licValidityMster)) {
			LicenseValidityMasterDto licValMasterDto = licValidityMster.get(0);
			LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					licValMasterDto.getLicDependsOn(), UserSession.getCurrent().getOrganisation());
			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
					UserSession.getCurrent().getOrganisation());
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
					TbFinancialyear financialYear;
					if (tenure > 1) {
						Long finYearId = financialyearService.getFinanciaYearIdByFromDate(new Date());
						ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class)
								.getFinincialYearsById(finYearId + tenure,
										UserSession.getCurrent().getOrganisation().getOrgid());
						financialYear = financialyearService.findYearById(finYearId + tenure,
								UserSession.getCurrent().getOrganisation().getOrgid());
					} else {
						financialYear = financialyearService.findFinancialYear(new Date());
					}
					if (financialYear != null) {
						licenseMaxTenureDays = Long
								.valueOf(Utility.getDaysBetweenDates(currentDate, financialYear.getFaToDate()));
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
					LocalDate currLocalDate = LocalDate.now();
					Instant instant1 = LocalDate
							.of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
									currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth())
							.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Date from1 = Date.from(instant1);
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1));
				}
			}
		}
		return licenseMaxTenureDays;
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
			checkListModel2.setServiceCode("RTL");

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

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
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

	public Long getLicMaxTenureDays() {
		return licMaxTenureDays;
	}

	public void setLicMaxTenureDays(Long licMaxTenureDays) {
		this.licMaxTenureDays = licMaxTenureDays;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public String getScrutinyAppFlag() {
		return scrutinyAppFlag;
	}

	public void setScrutinyAppFlag(String scrutinyAppFlag) {
		this.scrutinyAppFlag = scrutinyAppFlag;
	}

	public boolean updateForm() throws FrameworkException {

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();

		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();

		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();

		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgId);
		masDto.setLgIpMacUpd(lgIpMacUpd);
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

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());

		getTradeMasterDetailDTO().setTrdStatus(lookUp.getLookUpId());
		masDto.getRenewalMasterDetailDTO().setTreStatus("R");
		masDto.getRenewalMasterDetailDTO().setUpdatedBy(createdBy);
		masDto.getRenewalMasterDetailDTO().setUpdatedDate(new Date());
		masDto.getRenewalMasterDetailDTO().setLgIpMacUpd(lgIp);
		masDto.getRenewalMasterDetailDTO().setOrgid(orgId);

		try {
			iRenewalLicenseApplicationService.updateRenewalFormData(masDto);
		} catch (FrameworkException e) {

			throw new FrameworkException("unable to process request for Trade Licence Fee");
		}

		// masDto.setTrdLictoDate();

		return true;
	}

	@Override
	public void populateApplicationData(long applicationId) {

		this.setTradeMasterDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
		RenewalMasterDetailDTO renewalLicenseDetailsDTO = iRenewalLicenseApplicationService
				.getRenewalLicenseDetailsByApplicationId(applicationId);
		getTradeMasterDetailDTO().setRenewalMasterDetailDTO(renewalLicenseDetailsDTO);
		this.setScrutunyEditMode(null);
		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		// Defect #110792
		if (!this.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
			StringBuilder ownName = new StringBuilder();
			String ownerName = "";
			List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = null;
			ownerDetailDTOList = this.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().parallelStream()
					.filter(ownDto -> ownDto != null && (ownDto.getTroPr() != null)
							&& ownDto.getTroPr().equalsIgnoreCase(MainetConstants.FlagA))
					.collect(Collectors.toList());
			for (TradeLicenseOwnerDetailDTO ownDto : ownerDetailDTOList) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (ownName.length() > 0) {
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			}
			if (StringUtils.isNotBlank(ownerName)) {
				this.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).setTroName(ownerName);
			}
		}

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

		this.documentList = checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());

	}

	public boolean validateInputs() {

		validateBean(this, RenewalLicenseApplicationValidator.class);

		if (getServiceMaster().getSmAppliChargeFlag().equals("Y")) {
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		}
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
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

	public String getScrutunyEditMode() {
		return scrutunyEditMode;
	}

	public void setScrutunyEditMode(String scrutunyEditMode) {
		this.scrutunyEditMode = scrutunyEditMode;
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

	public String getSudaEnv() {
		return sudaEnv;
	}

	public void setSudaEnv(String sudaEnv) {
		this.sudaEnv = sudaEnv;
	}

	}
