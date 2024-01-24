package com.abm.mainet.water.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.abm.mainet.common.ui.validator.CommonApplicantDetailValidator;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.IChangeOfUsageService;
import com.abm.mainet.water.service.IWaterBRMSService;
import com.abm.mainet.water.ui.validator.ChangeOfUsageValidator;

@Component
@Scope("session")
public class ChangeOfUsageModel extends AbstractFormModel {

	private static final long serialVersionUID = -3307269739839482467L;
	/** CHARGE_ERROR. */
	private static final String CHARGE_ERROR = "Problem while checking Application level charges applicable or not.";

	@Autowired
	private IPortalServiceMasterService portalMasterService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private IWaterBRMSService checklistAndChageService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	private IChangeOfUsageService iChangeOfUsageService;

	private ChangeOfUsageRequestDTO requestDTO = new ChangeOfUsageRequestDTO();
	private ChangeOfUsageResponseDTO responseDTO = new ChangeOfUsageResponseDTO();
	private CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
	private String radio;
	private List<DocumentDetailsVO> checkList;
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	/**
	 * @return the applicantDetailDto
	 */
	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	/**
	 * @param applicantDetailDto
	 *            the applicantDetailDto to set
	 */
	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	// to identify whether service is free or not
	private boolean payable;
	private boolean enableSubmit;
	private double amountToPay;
	private boolean resultFound;
	private double actualAmount;

	private String validConnectionNo;
	private boolean dualReturn;

	private Long deptId;

	@Override
	protected void initializeModel() {

		initializeLookupFields(MainetConstants.NewWaterServiceConstants.TRF);
		initializeLookupFields(MainetConstants.NewWaterServiceConstants.WWZ);
		initializeLookupFields(MainetConstants.NewWaterServiceConstants.CSZ);
	}

	@Override
	public boolean saveForm() {
		boolean result = false;
		try {
			validateApplicantDetail();
			validateChangeDetail();
			final CommonChallanDTO offline = getOfflineDTO();

			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
			if (getAmountToPay() > 0) {
				validateBean(offline, CommonOfflineMasterValidator.class);
			}

			if (hasValidationErrors()) {
				return result;
			}

			// used to set doc is uploaded or not
			offline.setDocumentUploaded(!FileUploadUtility.getCurrent().getFileMap().isEmpty());
			setUPRequest();

			final ChangeOfUsageResponseDTO outPutObject = iChangeOfUsageService
					.saveOrUpdateChangeUsage(getRequestDTO());

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(outPutObject.getStatus())) {
				if (outPutObject.getApplicationNo() == null) {
					logger.error("Exception occured while saving application");
					setSuccessMessage(getAppSession().getMessage("water.changeusage.fail"));
				} else {

					setApmApplicationId(outPutObject.getApplicationNo());
					getRequestDTO().setApplicationId(outPutObject.getApplicationNo());

					if (getAmountToPay() > 0D) {
						proceedForChallan(offline, outPutObject);
					}
					savePortalMaster(getRequestDTO());
					setSuccessMessage(getAppSession().getMessage("water.changeusage.success",
							new Object[] { outPutObject.getApplicationNo().toString() }));

				}
			} else {
				throw new FrameworkException("web service call failed to save change of Usage");
			}

		} catch (final IOException e) {
			logger.error("Exception occured in change of usage save form:", e);
			result = false;
		}

		result = true;
		return result;

	}

	private void setUPRequest() {
		setGender(getApplicantDetailDto());
		getRequestDTO().setApplicant(getApplicantDetailDto());
		getRequestDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		getRequestDTO().setServiceId(getServiceId());
		getRequestDTO().setLangId((long) UserSession.getCurrent().getLanguageId());
		getRequestDTO().setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		getRequestDTO().setDeptId(deptId);
		getRequestDTO().setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		getRequestDTO().getChangeOfUsage().setOrgId(getRequestDTO().getOrgId());
		getRequestDTO().getChangeOfUsage().setLmoddate(new Date());
		getRequestDTO().getChangeOfUsage().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		getRequestDTO().getChangeOfUsage().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		getRequestDTO().getChangeOfUsage().setLangId((long) UserSession.getCurrent().getLanguageId());
		getRequestDTO().getChangeOfUsage().setCsIdn(getCustomerInfoDTO().getCsIdn());
		getRequestDTO().setPayAmount(getAmountToPay());
	}

	public void validateApplicantDetail() {
		validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
		if ((requestDTO.getConnectionNo() == null) || requestDTO.getConnectionNo().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.connEmptyErr"));
		}
		if ((getApplicantDetailDto().getAreaName() == null) || getApplicantDetailDto().getAreaName().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.changeowner.areaname"));
		}
		/*
		 * if ((getApplicantDetailDto().getVillageTownSub() == null) ||
		 * getApplicantDetailDto().getVillageTownSub().isEmpty()) {
		 * addValidationError(getAppSession().getMessage("water.changeowner.vtc")); }
		 */

	}

	public void validateChangeDetail() {
		validateBean(getRequestDTO(), ChangeOfUsageValidator.class);
	}

	private void proceedForChallan(CommonChallanDTO offline, final ChangeOfUsageResponseDTO responseDTO) {

		offline.setApplNo(responseDTO.getApplicationNo());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);

		offline.setServiceId(getServiceId());
		offline.setApplicantName((getApplicantDetailDto().getApplicantFirstName() != null
				? getApplicantDetailDto().getApplicantFirstName() + " "
				: MainetConstants.BLANK)
				+ (getApplicantDetailDto().getApplicantMiddleName() != null
						? getApplicantDetailDto().getApplicantMiddleName() + " "
						: MainetConstants.WHITE_SPACE)
				+ (getApplicantDetailDto().getApplicantLastName() != null
						? getApplicantDetailDto().getApplicantLastName()
						: MainetConstants.BLANK));
		offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		for (final ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}
		offline.setDeptId(getDeptId());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals(MainetConstants.NO)) {
			offline = iChallanService.generateChallanNumber(offline);

			setOfflineDTO(offline);
		}

	}

	/**
	 *
	 * @param serviceId
	 * @param orgId
	 */
	public void findApplicableCheckListAndCharges(final long serviceId, final long orgId) {

		WSResponseDTO response = null;
		final WSRequestDTO initDTO = new WSRequestDTO();
		initDTO.setModelName(MainetConstants.MODEL_NAME);
		response = iCommonBRMSService.initializeModel(initDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel = (CheckListModel) checklist.get(0);
			final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);

			checkListModel.setOrgId(orgId);
			checkListModel.setDeptCode(PrefixConstants.WATERMODULEPREFIX.WT);
			checkListModel.setServiceCode(MainetConstants.ServiceCode.CHANGE_OF_USAGE);
			checkListModel.setIsBPL(getApplicantDetailDto().getIsBPL());
			/*
			 * checkListModel.setApplicantType(CommonMasterUtility.getCPDDescription(
			 * customerInfoDTO.getApplicantType(),
			 * MainetConstants.D2KFUNCTION.ENGLISH_DESC));
			 */
			if (null != requestDTO.getChangeOfUsage().getNewTrmGroup1()) {
				checkListModel.setUsageSubtype1(CommonMasterUtility
						.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup1()).getDescLangFirst());
			}
			if (null != requestDTO.getChangeOfUsage().getNewTrmGroup2()) {
				checkListModel.setUsageSubtype2(CommonMasterUtility
						.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup2()).getDescLangFirst());
			}
			if (null != requestDTO.getChangeOfUsage().getNewTrmGroup3()) {
				checkListModel.setUsageSubtype3(CommonMasterUtility
						.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup3()).getDescLangFirst());
			}
			if (null != requestDTO.getChangeOfUsage().getNewTrmGroup4()) {
				checkListModel.setUsageSubtype4(CommonMasterUtility
						.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup4()).getDescLangFirst());
			}
			if (null != requestDTO.getChangeOfUsage().getNewTrmGroup5()) {
				checkListModel.setUsageSubtype5(CommonMasterUtility
						.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup5()).getDescLangFirst());
			}
			final List<DocumentDetailsVO> docList = iCommonBRMSService.getChecklist(checkListModel);
			long cnt = 1;
			for (final DocumentDetailsVO doc : docList) {
				doc.setDocumentSerialNo(cnt);
				cnt++;
			}
			setCheckList(docList);

			response = checklistAndChageService.getApplicableTaxes(waterRateMaster, orgId,
					MainetConstants.ServiceCode.CHANGE_OF_USAGE);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				if (response.isFree()) {
					getRequestDTO().setFree(true);
					setPayable(false);
					setAmountToPay(0.0d);

				} else {

					final List<?> rates = JersyCall.castResponse(response, WaterRateMaster.class);
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();

					for (final Object rate : rates) {
						final WaterRateMaster rateMaster = (WaterRateMaster) rate;
						rateMaster.setOrgId(orgId);
						rateMaster.setDeptCode(PrefixConstants.WATERMODULEPREFIX.WT);
						rateMaster.setServiceCode(MainetConstants.ServiceCode.CHANGE_OF_USAGE);
						rateMaster.setIsBPL(getApplicantDetailDto().getIsBPL());
						if (null != requestDTO.getChangeOfUsage().getNewTrmGroup1()) {
							rateMaster.setUsageSubtype1(CommonMasterUtility
									.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup1())
									.getDescLangFirst());
						}
						if (null != requestDTO.getChangeOfUsage().getNewTrmGroup2()) {
							rateMaster.setUsageSubtype2(CommonMasterUtility
									.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup2())
									.getDescLangFirst());
						}
						if (null != requestDTO.getChangeOfUsage().getNewTrmGroup3()) {
							rateMaster.setUsageSubtype3(CommonMasterUtility
									.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup3())
									.getDescLangFirst());
						}
						if (null != requestDTO.getChangeOfUsage().getNewTrmGroup4()) {
							rateMaster.setUsageSubtype4(CommonMasterUtility
									.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup4())
									.getDescLangFirst());
						}
						if (null != requestDTO.getChangeOfUsage().getNewTrmGroup5()) {
							rateMaster.setUsageSubtype5(CommonMasterUtility
									.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup5())
									.getDescLangFirst());
						}
						/*
						 * rateMaster.setTaxSubCategory(getTaxSubCategoryDesc(rateMaster.
						 * getTaxSubCategory(), UserSession.getCurrent().getOrganisation()));
						 */
						rateMaster.setRateStartDate(new Date().getTime());
						if (customerInfoDTO.getCsMeteredccn() != null) {
							rateMaster.setMeterType(CommonMasterUtility
									.getNonHierarchicalLookUpObject(customerInfoDTO.getCsMeteredccn())
									.getDescLangFirst());
						}
						requiredCHarges.add(rateMaster);
					}

					final List<ChargeDetailDTO> charges = checklistAndChageService
							.getApplicableCharges(requiredCHarges);
					setChargesInfo(charges);
					setAmountToPay(checklistAndChageService.chargesToPay(charges));
					getOfflineDTO().setAmountToShow(getAmountToPay());
					getRequestDTO().setFree(false);
					setPayable(true);
				}

			} else {

				throw new FrameworkException(CHARGE_ERROR);
			}
		}
	}

	private String getTaxSubCategoryDesc(final String taxsubCategory, final Organisation org) {
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

	private void setGender(final ApplicantDetailDTO applicant) {

		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()
					&& (lookUp.getLookUpId() == Long.parseLong(applicant.getGender()))) {

				applicant.setGender(lookUp.getLookUpCode());
				break;
			}
		}

	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(final String radio) {
		this.radio = radio;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest rquest, final PaymentRequestDTO payURequestDTO) {
		final ChangeOfUsageRequestDTO dto = getRequestDTO();
		final ApplicantDetailDTO applicant = getApplicantDetailDto();
		final StringBuilder userName = new StringBuilder(
				applicant.getApplicantFirstName() == null ? MainetConstants.BLANK
						: applicant.getApplicantFirstName().trim()).append(MainetConstants.WHITE_SPACE);
		userName.append(applicant.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: applicant.getApplicantMiddleName().trim()).append(MainetConstants.WHITE_SPACE)
				.append(applicant.getApplicantLastName() == null ? MainetConstants.BLANK
						: applicant.getApplicantLastName().trim());
		final PortalService portalService = portalMasterService.getService(dto.getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalService.getShortName());
		payURequestDTO.setUdf7(dto.getApplicationId().toString());
		payURequestDTO.setServiceId(portalService.getServiceId());
		payURequestDTO.setUdf6(applicant.getCfcCitizenId());
		payURequestDTO.setApplicationId(dto.getApplicationId().toString());
		payURequestDTO.setDueAmt(new BigDecimal(this.getAmountToPay()));
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		payURequestDTO.setApplicantName(userName.toString().trim());
		if (portalService != null) {
			payURequestDTO.setUdf10(portalService.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalService.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalService.getServiceNameReg());
			}
		}
	}

	public void savePortalMaster(final ChangeOfUsageRequestDTO outPutObject) {

		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		applicationMaster.setPamApplicationId(outPutObject.getApplicationId());
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.setSmServiceId(outPutObject.getServiceId());
		applicationMaster.setDeleted(false);
		applicationMaster.setOrgId(UserSession.getCurrent().getOrganisation());
		applicationMaster.setLangId(UserSession.getCurrent().getLanguageId());
		applicationMaster.setUserId(UserSession.getCurrent().getEmployee());
		applicationMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		applicationMaster.setLmodDate(new Date());
		portalMasterService.saveApplicationMaster(applicationMaster, outPutObject.getPayAmount(),
				outPutObject.getDocumentList().size());

	}

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		String result;
		switch (parentCode) {

		case MainetConstants.NewWaterServiceConstants.TRF:

			if (dualReturn) {
				dualReturn = false;
				result = "requestDTO.changeOfUsage.newTrmGroup";
			} else {
				dualReturn = true;
				result = "requestDTO.changeOfUsage.oldTrmGroup";
			}
			break;
		case MainetConstants.NewWaterServiceConstants.WWZ:
			result = "applicantDetailDto.dwzid";
			break;
		case MainetConstants.NewWaterServiceConstants.CSZ:
			result = "requestDTO.connectionSize";
			break;
		default:
			result = MainetConstants.BLANK;
			break;

		}
		return result;
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

	public String getValidConnectionNo() {
		return validConnectionNo;
	}

	public void setValidConnectionNo(final String validConnectionNo) {
		this.validConnectionNo = validConnectionNo;
	}

	public ChangeOfUsageRequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(final ChangeOfUsageRequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public ChangeOfUsageResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(final ChangeOfUsageResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public CustomerInfoDTO getCustomerInfoDTO() {
		return customerInfoDTO;
	}

	public void setCustomerInfoDTO(final CustomerInfoDTO customerInfoDTO) {
		this.customerInfoDTO = customerInfoDTO;
	}

	public boolean isResultFound() {
		return resultFound;
	}

	public void setResultFound(final boolean resultFound) {
		this.resultFound = resultFound;
	}

	public void setDualReturn(final boolean dualReturn) {
		this.dualReturn = dualReturn;

	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public boolean isPayable() {
		return payable;
	}

	public void setPayable(final boolean payable) {
		this.payable = payable;
	}

}
