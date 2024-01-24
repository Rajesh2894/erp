package com.abm.mainet.water.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

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
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.IChangeOfOwnershipService;
import com.abm.mainet.water.service.IWaterBRMSService;

@Component
@Scope("session")
public class ChangeOfOwnershipModel<TbCsmrInfoEntity> extends AbstractFormModel {

	@Autowired
	private IPortalServiceMasterService portalServiceMasterService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private IChangeOfOwnershipService changeOfOwnerShipService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	private static final long serialVersionUID = -3307269739839482467L;

	private ChangeOfOwnerRequestDTO changeOwnerMaster = new ChangeOfOwnerRequestDTO();

	private ChangeOfOwnerResponseDTO changeOwnerResponse = new ChangeOfOwnerResponseDTO();

	/**
	 * @return the changeOwnerResponse
	 */
	public ChangeOfOwnerResponseDTO getChangeOwnerResponse() {
		return changeOwnerResponse;
	}

	/**
	 * @param changeOwnerResponse
	 *            the changeOwnerResponse to set
	 */
	public void setChangeOwnerResponse(ChangeOfOwnerResponseDTO changeOwnerResponse) {
		this.changeOwnerResponse = changeOwnerResponse;
	}

	private String radio = null;

	private List<DocumentDetailsVO> checkList;
	// to identify whether service is free or not
	private String isFree;
	private boolean enableSubmit;
	private double amountToPay;
	private double actualAmount;
	private String validConnectionNo;
	private List<AdditionalOwnerInfoDTO> additionalOwners = new ArrayList<>();
	@Autowired
	private IWaterBRMSService checklistAndChargeService;

	@Override
	public boolean saveForm() {

		try {

			validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
			final boolean result = validateFormData(this.getChangeOwnerMaster());

			final CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			if ((offline.getOnlineOfflineCheck() != null) && !offline.getOnlineOfflineCheck().isEmpty()) {
				validateBean(offline, CommonOfflineMasterValidator.class);
			}
			if ((this.getCheckList() != null) && !this.getCheckList().isEmpty()) {
				List<DocumentDetailsVO> docs = getCheckList();
				docs = setFileUploadMethod(docs);
				validateInputs(docs, getBindingResult());
				/*
				 * changeOfOwnerShipService.mapCheckList(this.getChangeOwnerMaster(),
				 * this.getCheckList(), getBindingResult());
				 */
				this.getChangeOwnerMaster().setUploadedDocList(docs);
			}

			if (result || hasValidationErrors()) {
				return false;
			}
			this.setGender(getApplicantDetailDto());
			this.getChangeOwnerMaster().setApplicant(getApplicantDetailDto());
			this.getChangeOwnerMaster().setAdditionalOwners(this.getAdditionalOwners());
			this.getChangeOwnerMaster().setOldOwnerInfo(this.getChangeOwnerResponse());
			this.getChangeOwnerMaster().setDepartmenttId(
					portalServiceMasterService.getServiceDeptIdId(this.getChangeOwnerMaster().getServiceId()));

			final ChangeOfOwnerResponseDTO outPutObject = changeOfOwnerShipService
					.saveOrUpdateChangeOfOwnerForm(this.getChangeOwnerMaster());

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(outPutObject.getStatus())) {
				if (outPutObject.getApplicationNo() != null) {
					getChangeOwnerMaster().setApmApplicationId(outPutObject.getApplicationNo());
					/*
					 * if (((offline.getOnlineOfflineCheck() != null) &&
					 * offline.getOnlineOfflineCheck().equals(MainetConstants.NO))) {
					 * proceedForChallan(offline, outPutObject); }
					 */

					this.savePortalMaster(this.getChangeOwnerMaster());
					/*
					 * if (this.getChangeOwnerMaster().getAmount() > 0d) {
					 * setChallanDToandSaveChallanData(offline, getApplicantDetailDto(),
					 * this.getChangeOwnerMaster()); }
					 */
					if (getAmountToPay() > 0d) {
						proceedForChallan(offline, outPutObject);
					}
					setSuccessMessage(ApplicationSession.getInstance().getMessage("water.changeOwnerShip.success",
							new Object[] { String.valueOf(outPutObject.getApplicationNo()) }));
				} else {
					logger.error("Exception occured while saving application");
					setSuccessMessage(ApplicationSession.getInstance().getMessage("water.chnageowner.error"));
				}
			} else {
				throw new FrameworkException("web service call failed to save change of ownership");
			}

		} catch (final IOException e) {
			logger.error("Exception occured in change of ownership save form:", e);
			return false;
		}

		return true;

	}

	private void validateInputs(final List<DocumentDetailsVO> dto, BindingResult bindingResult) {
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
					if (doc.getDocumentByteCode() == null) {
						bindingResult.addError(new ObjectError(MainetConstants.BLANK, ApplicationSession.getInstance()
								.getMessage("water.plumberLicense.valMsg.uploadDocument")));
						break;
					}
				}
			}

		}

	}

	private List<DocumentDetailsVO> setFileUploadMethod(List<DocumentDetailsVO> docs) {
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

	private void proceedForChallan(CommonChallanDTO offline, final ChangeOfOwnerResponseDTO responseDTO) {

		offline.setApplNo(responseDTO.getApplicationNo());
		offline.setAmountToPay(Double.toString(this.getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(this.getChangeOwnerMaster().getServiceId());
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
		final PortalService portalServiceMaster = portalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals(MainetConstants.NO)) {
			offline = iChallanService.generateChallanNumber(offline);
			setOfflineDTO(offline);
		}

	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final ApplicantDetailDTO applicantDetailDto,
			final ChangeOfOwnerRequestDTO outPutObject) {
		offline.setApplNo(outPutObject.getApmApplicationId());
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
		offline.setServiceId(outPutObject.getServiceId());
		if (outPutObject.getDocumentSize() > 0) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		/*
		 * offline.getFeeIds().put(1l, 10d); offline.getFeeIds().put(2l, 20d);
		 */
		for (final ChargeDetailDTO charges : getChargesInfo()) {
			offline.getFeeIds().put(charges.getChargeCode(), charges.getChargeAmount());
		}
		final PortalService portalServiceMaster = portalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			offline = iChallanService.generateChallanNumber(offline);
		}
		setOfflineDTO(offline);
	}

	public boolean validateFormData(final ChangeOfOwnerRequestDTO changeOwnerMasterData) {

		boolean status = false;

		validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);

		if ((changeOwnerMasterData.getCooNotitle() == null)
				|| (changeOwnerMasterData.getCooNotitle().longValue() == 0l)) {

			addValidationError(getAppSession().getMessage("water.validation.ApplicantNameTitle"));
			status = true;
		}
		if ((changeOwnerMasterData.getCooNoname() == null) || changeOwnerMasterData.getCooNoname().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantFirstName"));
			status = true;
		}
		if ((changeOwnerMasterData.getCooNolname() == null) || changeOwnerMasterData.getCooNolname().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantLastName"));
			status = true;
		}
		if ((getValidConnectionNo() != null)
				&& !getValidConnectionNo().equalsIgnoreCase(getChangeOwnerResponse().getConnectionNumber())) {
			addValidationError(getAppSession().getMessage(
					"water.connecNo" + getChangeOwnerResponse().getConnectionNumber() + "water.invalidConnecNo"));
			status = true;

		} else if (getValidConnectionNo() == null) {
			addValidationError(
					getAppSession().getMessage("water.clickSearch") + getChangeOwnerResponse().getConnectionNumber());
			status = true;
		}
		if (hasValidationErrors()) {
			status = true;
		}

		return status;

	}

	/**
	 * 
	 * @param serviceId
	 * @param orgId
	 */
	public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId) {

		// [START] BRMS call initialize model
		final WSRequestDTO dto = new WSRequestDTO();
		final WSRequestDTO initDTO = new WSRequestDTO();
		initDTO.setModelName(MainetConstants.MODEL_NAME);
		final WSResponseDTO response = iCommonBRMSService.initializeModel(initDTO);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

			final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);

			CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);

			checkListModel2.setOrgId(orgId);
			checkListModel2.setServiceCode(MainetConstants.ServiceShortCode.WATER_CHANGE_OWNER);
			checkListModel2.setIsBPL(getApplicantDetailDto().getIsBPL());
			/*
			 * checkListModel2.setApplicantType(CommonMasterUtility.getCPDDescription(this.
			 * changeOwnerResponse.getApplicantType(),
			 * MainetConstants.D2KFUNCTION.ENGLISH_DESC));
			 */
			checkListModel2 = settingUsageSubtype(checkListModel2, changeOwnerResponse,
					UserSession.getCurrent().getOrganisation());
			dto.setDataModel(checkListModel2);

			final List<DocumentDetailsVO> checkListList = iCommonBRMSService.getChecklist(checkListModel2);
			// this.setCheckList(checklistResponse); // checklist done
			if ((checkListList != null) && !checkListList.isEmpty()) {
				long cnt = 1;
				for (final DocumentDetailsVO doc : checkListList) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
				if ((checkListList != null) && !checkListList.isEmpty()) {
					setCheckList(checkListList);
				}
			}

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

				WaterRateMaster.setOrgId(orgId);
				WaterRateMaster.setServiceCode(MainetConstants.ServiceShortCode.WATER_CHANGE_OWNER);
				dto.setDataModel(WaterRateMaster);

				final WSResponseDTO res = checklistAndChargeService.getApplicableTaxes(WaterRateMaster,
						UserSession.getCurrent().getOrganisation().getOrgid(), serviceCode);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {

					if (!res.isFree()) {
						final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
						final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							final WaterRateMaster master1 = (WaterRateMaster) rate;
							master1.setOrgId(orgId);
							master1.setServiceCode(MainetConstants.ServiceShortCode.WATER_CHANGE_OWNER);
							master1.setDeptCode(MainetConstants.DeptCode.WATER);
							master1.setIsBPL(getApplicantDetailDto().getIsBPL());
							master1.setUsageSubtype1(checkListModel2.getUsageSubtype1());
							master1.setUsageSubtype2(checkListModel2.getUsageSubtype2());
							master1.setMeterType(CommonMasterUtility.getCPDDescription(
									this.changeOwnerResponse.getMeterType(), MainetConstants.D2KFUNCTION.ENGLISH_DESC));
							master1.setRateStartDate(new Date().getTime());
							/*
							 * master1.setTaxSubCategory( getSubCategoryDesc(master1.getTaxSubCategory(),
							 * UserSession.getCurrent().getOrganisation()));
							 */
							master1.setConnectionSize(Double.parseDouble(CommonMasterUtility.getCPDDescription(
									this.changeOwnerResponse.getConSize(), MainetConstants.D2KFUNCTION.ENGLISH_DESC)));
							master1.setTransferMode(
									CommonMasterUtility.findLookUpDesc(MainetConstants.NewWaterServiceConstants.TFM,
											UserSession.getCurrent().getOrganisation().getOrgid(),
											this.getChangeOwnerMaster().getOwnerTransferMode()));
							requiredCHarges.add(master1);
						}
						dto.setDataModel(requiredCHarges);
						final List<ChargeDetailDTO> output = checklistAndChargeService
								.getApplicableCharges(requiredCHarges);
						setChargesInfo(output);
						this.setAmountToPay(chargesToPay(output));
						getChangeOwnerMaster().setAmount(getAmountToPay());
						this.setIsFree(MainetConstants.NO);
						getOfflineDTO().setAmountToShow(this.getAmountToPay());
						if (getAmountToPay() == 0.0d) {
							throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
									+ " if service configured as Chageable");
						}
					} else {
						this.setIsFree(MainetConstants.Common_Constant.FREE);
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

	}

	private CheckListModel settingUsageSubtype(final CheckListModel checkListModel2,
			final ChangeOfOwnerResponseDTO responseDTO, final Organisation organisation) {

		final List<LookUp> usageSubtype1 = CommonMasterUtility.getLevelData(
				MainetConstants.NewWaterServiceConstants.TRF, 1, UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : usageSubtype1) {
			if (responseDTO.getTrmGroup1() != null) {
				if (lookUp.getLookUpId() == responseDTO.getTrmGroup1()) {
					checkListModel2.setUsageSubtype1(lookUp.getLookUpDesc());
					break;
				}
			}

		}
		return checkListModel2;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = MainetConstants.BLANK;
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.WATERMODULEPREFIX.TAC,
				MainetConstants.INDEX.TWO, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
				break;
			}
		}
		return subCategoryDesc;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private void setGender(final ApplicantDetailDTO applicant) {

		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(applicant.getGender())) {
					applicant.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
	}

	private String applicantFullName() {
		final StringBuilder builder = new StringBuilder();
		builder.append(titleDesc(getApplicantDetailDto().getApplicantTitle()));
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(getApplicantDetailDto().getApplicantFirstName() == null ? MainetConstants.BLANK
				: getApplicantDetailDto().getApplicantFirstName());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
				: getApplicantDetailDto().getApplicantMiddleName());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(getApplicantDetailDto().getApplicantLastName() == null ? MainetConstants.BLANK
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

	public ChangeOfOwnerRequestDTO getChangeOwnerMaster() {
		return changeOwnerMaster;
	}

	public void setChangeOwnerMaster(final ChangeOfOwnerRequestDTO changeOwnerMaster) {
		this.changeOwnerMaster = changeOwnerMaster;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(final String radio) {
		this.radio = radio;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final ChangeOfOwnerRequestDTO dto = this.getChangeOwnerMaster();
		final ApplicantDetailDTO applicant = getApplicantDetailDto();
		String userName = (applicant.getApplicantFirstName() == null ? MainetConstants.BLANK
				: applicant.getApplicantFirstName().trim() + MainetConstants.WHITE_SPACE);
		userName += applicant.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: applicant.getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += applicant.getApplicantLastName() == null ? MainetConstants.BLANK
				: applicant.getApplicantLastName().trim();
		final PortalService portalServiceMaster = portalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(dto.getApmApplicationId().toString());
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf6(applicant.getCfcCitizenId());
		payURequestDTO.setApplicationId(dto.getApmApplicationId().toString());
		payURequestDTO.setDueAmt(new BigDecimal(this.getAmountToPay()));
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		payURequestDTO.setApplicantName(userName.trim());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	private void savePortalMaster(final ChangeOfOwnerRequestDTO outPutObject) {
		UserSession session = UserSession.getCurrent();
		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		applicationMaster.setPamApplicationId(outPutObject.getApmApplicationId());
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.setSmServiceId(outPutObject.getServiceId());
		applicationMaster.setDeleted(false);
		applicationMaster.setOrgId(session.getOrganisation());
		applicationMaster.setLangId(session.getLanguageId());
		applicationMaster.setUserId(session.getEmployee());
		applicationMaster.setLgIpMac(session.getEmployee().getEmppiservername());
		applicationMaster.setLmodDate(new Date());
		portalServiceMasterService.saveApplicationMaster(applicationMaster, outPutObject.getAmount(),
				outPutObject.getDocumentSize());

	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(final List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(final String isFree) {
		this.isFree = isFree;
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

	public List<AdditionalOwnerInfoDTO> getAdditionalOwners() {
		return additionalOwners;
	}

	public void setAdditionalOwners(final List<AdditionalOwnerInfoDTO> additionalOwners) {
		this.additionalOwners = additionalOwners;
	}

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {

		case MainetConstants.NewWaterServiceConstants.TRF:
			return "changeOwnerResponse.trmGroup";

		case MainetConstants.NewWaterServiceConstants.WWZ:
			return "applicantDetailDto.dwzid";
		case MainetConstants.NewWaterServiceConstants.CSZ:
			return "changeOwnerResponse.conSize";

		}
		return null;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

}
