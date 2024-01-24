package com.abm.mainet.water.ui.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.service.IWaterBRMSService;
import com.abm.mainet.water.service.IWaterReconnectionFormService;
import com.abm.mainet.water.ui.validator.WaterReconnectionValidator;

@Component
@Scope("session")
public class WaterReconnectionFormModel extends AbstractFormModel implements Serializable {

	private static final long serialVersionUID = 5285747173176245223L;

	@Autowired
	private IChallanService iChallanService;
	@Autowired
	private IPortalServiceMasterService iPortalService;
	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	private IWaterReconnectionFormService iWaterReconnectionFormService;

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterReconnectionFormModel.class);

	// to identify whether service is free or not
	private String isFree;

	private List<DocumentDetailsVO> checkList;
	private double amountToPay;
	private double actualAmount;
	private String radio = null;
	private String plumber;
	private Long deptId;
	private String checkListNCharges;
	private Long connectionSize;
	private WaterReconnectionRequestDTO reconnRequestDTO = new WaterReconnectionRequestDTO();

	WaterReconnectionResponseDTO reconnResponseDTO = new WaterReconnectionResponseDTO();

	@Autowired
	private IWaterBRMSService checklistAndChargeService;

	private Map<Long, Double> chargesMap = new HashMap<>();

	private Double charges = 0.0d;
	public List<PlumberDTO> plumberList = new ArrayList<>();

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {

		case MainetConstants.NewWaterServiceConstants.WWZ:

			return "applicantDetailDto.dwzid";

		default:
			return null;
		}
	}

	/**
	 * 
	 * @param serviceId
	 * @param orgId
	 */
	public void findApplicableCheckListAndCharges(final long serviceId, final long orgId) {
		final WSRequestDTO initDTO = new WSRequestDTO();
		initDTO.setModelName(MainetConstants.MODEL_NAME);
		final WSResponseDTO response = iCommonBRMSService.initializeModel(initDTO);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			setCheckListNCharges(MainetConstants.YES);
			final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			checkListModel2.setOrgId(orgId);
			checkListModel2.setServiceCode(MainetConstants.ServiceShortCode.WATER_RECONN);
			populateChecklistModel(checkListModel2, getReconnResponseDTO());
			checkListList = iCommonBRMSService.getChecklist(checkListModel2);
			if ((checkListList != null) && !checkListList.isEmpty()) {

				long cnt = 1;
				for (final DocumentDetailsVO doc : checkListList) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
				if ((checkListList != null) && !checkListList.isEmpty()) {
					setCheckList(checkListList);
				}

				final WSResponseDTO res = checklistAndChargeService.getApplicableTaxes(WaterRateMaster,
						UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.ServiceShortCode.WATER_RECONN);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
					if (!res.isFree()) {
						final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
						final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							final WaterRateMaster master1 = (WaterRateMaster) rate;
							populateChargeMaster(master1, getReconnResponseDTO());
							master1.setOrgId(orgId);
							master1.setServiceCode(MainetConstants.ServiceShortCode.WATER_RECONN);
							requiredCHarges.add(master1);
						}
						final List<ChargeDetailDTO> detailDTOs = checklistAndChargeService
								.getApplicableCharges(requiredCHarges);
						setIsFree(MainetConstants.NewWaterServiceConstants.NO);
						setChargesInfo(detailDTOs);
						setCharges((chargesToPay(detailDTOs)));
						setAmountToPay(getCharges());
						setChargeMap(detailDTOs);
						getOfflineDTO().setAmountToShow(getCharges());
						getReconnRequestDTO().setAmount(getAmountToPay());
					} else {
						setIsFree(MainetConstants.NewWaterServiceConstants.YES);
					}
				} else {
					throw new FrameworkException("Problem while checking Application level charges applicable or not.");
				}
			} else {
				throw new FrameworkException(
						"Problem while checking Application level charges and Checklist applicable or not.");
			}
		} else {
			throw new FrameworkException(
					"Problem while checking Application level charges and Checklist applicable or not.");
		}
		// [END]
	}

	private void populateChecklistModel(final CheckListModel checkListModel2,
			final WaterReconnectionResponseDTO reconnResponseDTO2) {
		checkListModel2.setIsBPL(MainetConstants.NO);
		checkListModel2.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		/* checkListModel2.setApplicantType(reconnResponseDTO2.getApplicantType()); */
		checkListModel2.setUsageSubtype1(reconnResponseDTO2.getTarrifCategory());
		// checkListModel2.setUsageSubtype2(reconnResponseDTO2.getPremiseType());

	}

	private void populateChargeMaster(final WaterRateMaster master1,
			final WaterReconnectionResponseDTO reconnResponseDTO2) {
		master1.setUsageSubtype1(reconnResponseDTO2.getTarrifCategory());
		master1.setUsageSubtype2(MainetConstants.NewWaterServiceConstants.NA);
		master1.setIsBPL(MainetConstants.NO);
		master1.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		master1.setMeterType(reconnResponseDTO2.getMeterType());
		master1.setConnectionSize(Double.parseDouble(CommonMasterUtility.getCPDDescription(
				reconnResponseDTO2.getConnectionSize().longValue(), MainetConstants.D2KFUNCTION.REG_DESC)));
		master1.setTaxCode(master1.getTaxCode());
		master1.setRateStartDate(new Date().getTime());
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	@Override
	public boolean saveForm() {
		try {
			final ApplicantDetailDTO applicantDetailDTO = getApplicantDetailDto();
			final WaterReconnectionRequestDTO reconnectionRequestDTO = getReconnRequestDTO();
			reconnectionRequestDTO.setApplicantTitle(applicantDetailDTO.getApplicantTitle());
			reconnectionRequestDTO.setFirstName(applicantDetailDTO.getApplicantFirstName());
			reconnectionRequestDTO.setLastName(applicantDetailDTO.getApplicantLastName());
			reconnectionRequestDTO.setAadharNo(applicantDetailDTO.getAadharNo());
			reconnectionRequestDTO.setPincode(applicantDetailDTO.getPinCode());
			reconnectionRequestDTO.setAreaName(applicantDetailDTO.getAreaName());
			reconnectionRequestDTO.setMobileNo(applicantDetailDTO.getMobileNo());
			reconnectionRequestDTO.setEmailId(applicantDetailDTO.getEmailId());
			List<DocumentDetailsVO> docs = getCheckList();
			docs = setFileUploadMethod(docs);
			reconnectionRequestDTO.setDocumentList(docs);
			validateBean(reconnectionRequestDTO, WaterReconnectionValidator.class);
			final CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			if ((offline.getOnlineOfflineCheck() == null) || offline.getOnlineOfflineCheck().isEmpty()) {
				validateBean(offline, CommonOfflineMasterValidator.class);
			}

			if (hasValidationErrors()) {
				return false;
			}

			reconnectionRequestDTO.setApplicant(getApplicantDetailDto());
			setUpdateFields(reconnectionRequestDTO);
			reconnectionRequestDTO.setServiceId(getServiceId());
			reconnectionRequestDTO.setDeptId(getDeptId());
			reconnectionRequestDTO.setAmount(getAmountToPay());

			final WaterReconnectionResponseDTO outPutObject = iWaterReconnectionFormService
					.saveOrUpdateReconnection(reconnectionRequestDTO);

			if (outPutObject != null && outPutObject.getApplicationId() != null) {
				reconnectionRequestDTO.setApplicationId(outPutObject.getApplicationId());
				ApplicationPortalMaster applicationMaster = null;

				applicationMaster = saveApplcationMaster(getServiceId(), outPutObject.getApplicationId(),
						FileUploadUtility.getCurrent().getFileMap().entrySet().size());
				iPortalService.saveApplicationMaster(applicationMaster, getAmountToPay(),
						FileUploadUtility.getCurrent().getFileMap().entrySet().size());
				if (getAmountToPay() > 0d) {
					setChallanDToandSaveChallanData(offline, getApplicantDetailDto(), outPutObject);
				}
				setSuccessMessage(ApplicationSession.getInstance().getMessage("water.reconnection.success",
						new Object[] { outPutObject.getApplicationId() }));
			} else {
				throw new FrameworkException(
						"Sorry,Your application for water reconnection has not been saved due to some technical problem.");
			}
		} catch (final Exception e) {
			logger.error("Exception occured in water reconnection  save data:", e);
			return false;
		}
		return true;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final WaterReconnectionRequestDTO dto = getReconnRequestDTO();
		final ApplicantDetailDTO applicant = getApplicantDetailDto();
		String userName = (applicant.getApplicantFirstName() == null ? ""
				: applicant.getApplicantFirstName().trim() + MainetConstants.WHITE_SPACE);
		userName += applicant.getApplicantMiddleName() == null ? ""
				: applicant.getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += applicant.getApplicantLastName() == null ? "" : applicant.getApplicantLastName().trim();
		final PortalService portalServiceMaster = iPortalService.getService(dto.getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(dto.getApplicationId()));
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf6(applicant.getCfcCitizenId());
		payURequestDTO.setApplicationId(dto.getApplicationId().toString());
		payURequestDTO.setDueAmt(new BigDecimal(getAmountToPay()));
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		payURequestDTO.setApplicantName(userName.trim());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(String.valueOf(portalServiceMaster.getPsmDpDeptid()));
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	public ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
			final int documentListSize) throws Exception {
		final PortalService appealMaster = iPortalService.getService(serviceId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		calculateDate(appealMaster, applicationMaster, documentListSize);
		applicationMaster.setPamApplicationId(applicationNo);
		applicationMaster.setSmServiceId(serviceId);
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.updateAuditFields();
		return applicationMaster;
	}

	public ApplicationPortalMaster calculateDate(final PortalService portalService,
			final ApplicationPortalMaster applicationMaster, final int documentListSize) throws Exception {

		final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		if (documentListSize > 0) {
			applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.YES); // Y
		} else {
			applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.NO); // N

			calendar.add(Calendar.DATE, 0);
			final String output1 = sdf.format(calendar.getTime());
			applicationMaster.setPamDocVerificationDate(sdf.parse(output1));

			calendar.setTime(sdf.parse(output1));
			calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSlaDays().toString()));
			final String output2 = sdf.format(calendar.getTime());
			applicationMaster.setPamSlaDate(sdf.parse(output2));

			calendar.setTime(sdf.parse(output2));
			calendar.add(Calendar.DATE, Integer.parseInt(portalService.getFirstAppealDuration().toString()));
			final String output3 = sdf.format(calendar.getTime());
			applicationMaster.setPamFirstAppealDate(sdf.parse(output3));

			calendar.setTime(sdf.parse(output3));
			calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSecondAppealDuration().toString()));
			final String output4 = sdf.format(calendar.getTime());
			applicationMaster.setPamSecondAppealDate(sdf.parse(output4));
		}

		return applicationMaster;

	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final ApplicantDetailDTO applicantDetailDto,
			final WaterReconnectionResponseDTO outPutObject) {
		offline.setApplNo(outPutObject.getApplicationId());
		offline.setAmountToPay(Double.toString(getAmountToPay()));

		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		offline.setEmailId(applicantDetailDto.getEmailId());
		offline.setApplicantName((applicantDetailDto.getApplicantFirstName() != null
				? applicantDetailDto.getApplicantFirstName() + MainetConstants.WHITE_SPACE
				: MainetConstants.BLANK)
				+ (applicantDetailDto.getApplicantMiddleName() != null
						? applicantDetailDto.getApplicantMiddleName() + " "
						: MainetConstants.WHITE_SPACE)
				+ (applicantDetailDto.getApplicantLastName() != null ? applicantDetailDto.getApplicantLastName()
						: MainetConstants.BLANK));
		offline.setMobileNumber(applicantDetailDto.getMobileNo());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(getServiceId());
		if (outPutObject.getUploadedDocSize() > 0) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}

		for (final ChargeDetailDTO charges : getChargesInfo()) {
			offline.getFeeIds().put(charges.getChargeCode(), charges.getChargeAmount());
		}
		offline.setDeptId(getDeptId());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			offline = iChallanService.generateChallanNumber(offline);
		}
		setOfflineDTO(offline);
	}

	private void setUpdateFields(final WaterReconnectionRequestDTO reconnectionRequestDTO) {

		final UserSession session = UserSession.getCurrent();
		reconnectionRequestDTO.setUserId(session.getEmployee().getEmpId());
		reconnectionRequestDTO.setLangId((long) session.getLanguageId());
		reconnectionRequestDTO.setOrgId(session.getOrganisation().getOrgid());
		reconnectionRequestDTO.setLgIpMac(session.getEmployee().getEmppiservername());

	}

	private List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
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
						logger.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		if (!docs.isEmpty() && !listOfString.isEmpty()) {
			for (final DocumentDetailsVO d : docs) {
				final long count = d.getDocumentSerialNo() - 1;
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
			}
		}
		return docs;
	}

	public String applicantFullName() {
		final StringBuilder builder = new StringBuilder();
		builder.append(titleDesc(getApplicantDetailDto().getApplicantTitle()));
		builder.append(" ");
		builder.append(getApplicantDetailDto().getApplicantFirstName() == null ? ""
				: getApplicantDetailDto().getApplicantFirstName());
		builder.append(" ");
		builder.append(getApplicantDetailDto().getApplicantMiddleName() == null ? ""
				: getApplicantDetailDto().getApplicantMiddleName());
		builder.append(" ");
		builder.append(getApplicantDetailDto().getApplicantLastName() == null ? ""
				: getApplicantDetailDto().getApplicantLastName());
		return builder.toString();
	}

	public String titleDesc(final long title) {
		String titleDesc = StringUtils.EMPTY;
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.TITLE,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == title) {
				titleDesc = lookUp.getLookUpDesc();
				break;
			}
		}
		return titleDesc;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(final String isFree) {
		this.isFree = isFree;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(final List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(final double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(final double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public WaterReconnectionRequestDTO getReconnRequestDTO() {
		return reconnRequestDTO;
	}

	public void setReconnRequestDTO(final WaterReconnectionRequestDTO reconnRequestDTO) {
		this.reconnRequestDTO = reconnRequestDTO;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(final String radio) {
		this.radio = radio;
	}

	public String getPlumber() {
		return plumber;
	}

	public void setPlumber(final String plumber) {
		this.plumber = plumber;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public String getCheckListNCharges() {
		return checkListNCharges;
	}

	public void setCheckListNCharges(final String checkListNCharges) {
		this.checkListNCharges = checkListNCharges;
	}

	private void setChargeMap(final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		setChargesMap(chargesMap);
	}

	/**
	 * @return the chargesMap
	 */
	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	/**
	 * @param chargesMap
	 *            the chargesMap to set
	 */
	public void setChargesMap(final Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	/**
	 * @return the charges
	 */
	public Double getCharges() {
		return charges;
	}

	/**
	 * @param charges
	 *            the charges to set
	 */
	public void setCharges(final Double charges) {
		this.charges = charges;
	}

	/**
	 * @return the reconnResponseDTO
	 */
	public WaterReconnectionResponseDTO getReconnResponseDTO() {
		return reconnResponseDTO;
	}

	/**
	 * @param reconnResponseDTO
	 *            the reconnResponseDTO to set
	 */
	public void setReconnResponseDTO(final WaterReconnectionResponseDTO reconnResponseDTO) {
		this.reconnResponseDTO = reconnResponseDTO;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = MainetConstants.BLANK;
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.WATERMODULEPREFIX.TAC,
				MainetConstants.INDEX.TWO, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
			}
		}
		return subCategoryDesc;
	}

	public Long getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(Long connectionSize) {
		this.connectionSize = connectionSize;
	}

	public List<PlumberDTO> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberDTO> plumberList) {
		this.plumberList = plumberList;
	}
}
