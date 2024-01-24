package com.abm.mainet.water.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.ResponseDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonApplicantDetailValidator;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.ChangeOfOwnershipDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterApplicationAcknowledgementDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.ChangeOfOwnerShipService;
import com.abm.mainet.water.service.WaterCommonService;

/**
 * @author Vivek.Kumar
 * @since 05-May-2016
 */
@Component
@Scope("session")
public class ChangeOfOwnerShipModel extends AbstractFormModel {

	private static final long serialVersionUID = 5640339358450331225L;

	@Autowired
	private ChangeOfOwnerShipService changeOfOwnershipService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private BRMSCommonService brmsCommonService;
	
    @Autowired
    private WaterCommonService waterCommonService;

	private ChangeOfOwnershipDTO ownerDTO;
	private List<CFCAttachment> documentList;
	private CFCApplicationAddressEntity cfcAddressEntity;
	private TbCfcApplicationMstEntity cfcEntity;
	private boolean scrutinyApplicable;
	private ApplicantDetailDTO applicantDetailDto;
	private List<AdditionalOwnerInfoDTO> additionalOwners;

	private long deptId;
	private boolean enableSubmit;
	private String validConnectionNo;
	private List<DocumentDetailsVO> checkList;
	private String isFree;
	private String checkFlag;
	private double amountToPay;

	private ChangeOfOwnerRequestDTO changeOwnerMaster;
	private ChangeOfOwnerResponseDTO changeOwnerResponse;
	
	private WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO;
	private ServiceMaster serviceMaster;

	@Resource
	private ChangeOfOwnerShipService iChangeOfOwnerShipService;

	@Autowired
	private IChallanService iChallanService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private BRMSWaterService brmsWaterService;

	@Override
	public void populateApplicationData(final long applicationId) {

		setOwnerDTO(changeOfOwnershipService.findWaterConnectionChangeOfOwnerDetail(applicationId));
		setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, getOwnerDTO().getOrgId()));
		applicantDetailDto = new ApplicantDetailDTO();
		cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationId, getOwnerDTO().getOrgId());
		cfcAddressEntity = cfcService.getApplicantsDetails(applicationId);
		applicantDetailDto = changeOfOwnershipService.populateChangeOfOwnerShipApplicantInfo(getApplicantDetailDto(),
				cfcEntity, getOwnerDTO(), cfcAddressEntity);
		getApplicantDetailDto().setGender(getGenderId(cfcEntity.getApmSex()));
		ownerDTO.setOwnerFullName(ownerDTO.getCsmrInfoDTO().getCsName());
		final ServiceMaster service = cfcEntity.getTbServicesMst();
		setAdditionalOwners(getOwnerDTO().getAdditionalOwners());
		if ((service.getSmScrutinyApplicableFlag() != null)
				&& service.getSmScrutinyApplicableFlag().equals(MainetConstants.MENU.Y)) {
			setScrutinyApplicable(true);
		}
	}

	@Override
	public boolean saveForm() {
		boolean status = false;

		if (MainetConstants.MENU.Y.equalsIgnoreCase(getCheckFlag())) {

			final boolean result = validateFormData(getChangeOwnerMaster());

			final CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			if ((offline.getOnlineOfflineCheck() != null) && !offline.getOnlineOfflineCheck().isEmpty()) {
				validateBean(offline, CommonOfflineMasterValidator.class);
			}
			if ((getCheckList() != null) && !getCheckList().isEmpty()) {
				List<DocumentDetailsVO> docDetailsList = mapCheckList(getCheckList(), getBindingResult());
				getChangeOwnerMaster().setUploadedDocList(docDetailsList);
			}

			if (result || hasValidationErrors()) {
				setCustomViewName("ChangeOfOwnerFromDept");
				return false;
			}
			setGender(getApplicantDetailDto());
			getChangeOwnerMaster().setApplicant(getApplicantDetailDto());
			getChangeOwnerMaster().setAdditionalOwners(getAdditionalOwners());
			getChangeOwnerMaster().setOldOwnerInfo(getChangeOwnerResponse());
			getChangeOwnerMaster().setDepartmenttId(getDeptId());
			getChangeOwnerMaster().setAmount(getAmountToPay());

			final ResponseDTO outPutObject = iChangeOfOwnerShipService.saveChangeData(getChangeOwnerMaster());
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(outPutObject.getStatus())) {
				if (outPutObject.getApplicationNo() != null) {
					waterCommonService.updateConnectionAppNoByCsIdn(getChangeOwnerMaster().getCsIdn(), outPutObject.getApplicationNo());
					getChangeOwnerMaster().setApmApplicationId(outPutObject.getApplicationNo());
					iChangeOfOwnerShipService.initiateWorkFlowForFreeService(getChangeOwnerMaster());

					if (getChangeOwnerMaster().getAmount() > 0d) {
						proceedForChallan(offline, outPutObject);
					}
					setSuccessMessage(getAppSession().getMessage("water.changeOwnerShip.success",
							new Object[] { String.valueOf(getChangeOwnerMaster().getApmApplicationId()) }));
					if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
						waterApplicationAcknowledgementDTO = new WaterApplicationAcknowledgementDTO();
						waterApplicationAcknowledgementDTO.setApplicationId(outPutObject.getApplicationNo().toString());
						
						StringBuilder applicantName = new StringBuilder();
						if(getChangeOwnerMaster().getCooNoname() != null)
							applicantName.append(getChangeOwnerMaster().getCooNoname());
						
						if(getChangeOwnerMaster().getCooNomname() != null) {
							applicantName.append(MainetConstants.WHITE_SPACE);
							applicantName.append(getChangeOwnerMaster().getCooNomname());
						}
						if(getChangeOwnerMaster().getCooNolname() != null) {
							applicantName.append(MainetConstants.WHITE_SPACE);
							applicantName.append(getChangeOwnerMaster().getCooNolname());
						}
							
						waterApplicationAcknowledgementDTO.setApplicantName(applicantName.toString());
						
						cfcEntity = cfcService.getCFCApplicationByApplicationId(outPutObject.getApplicationNo(), UserSession.getCurrent().getOrganisation().getOrgid());
						serviceMaster = cfcEntity.getTbServicesMst();
						waterApplicationAcknowledgementDTO.setDepartmentName(serviceMaster.getTbDepartment().getDpDeptdesc());
						setDepartmentCode(serviceMaster.getTbDepartment().getDpDeptcode());
						waterApplicationAcknowledgementDTO.setServiceName(serviceMaster.getSmServiceName());
						waterApplicationAcknowledgementDTO.setAppTime(new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(new Date()));
						if ((getCheckList() != null) && !getCheckList().isEmpty()) {
							waterApplicationAcknowledgementDTO.setCheckList(getCheckList());
						}
						long duration = 0;
						if (serviceMaster.getSmServiceDuration() != null) {
							duration = serviceMaster.getSmServiceDuration();
							waterApplicationAcknowledgementDTO.setWtConnDueDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(Utility.getAddedDateBy2(new Date(), (int)duration)));
						}
					}
				} else {
					logger.error("Exception occured while saving application");
					setSuccessMessage(getAppSession().getMessage("water.chnageowner.error"));
				}
			} else {
				throw new FrameworkException("web service call failed to save change of ownership");
			}

			return true;

		} else {
			validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
			if (!hasValidationErrors()) {
				changeOfOwnershipService.saveFormDataModifiedByDepartment(this);
				status = true;
				setSuccessMessage(status == true ? getAppSession().getMessage("water.plumberUpdate")
						: getAppSession().getMessage("water.edit.fail"));
			}
		}

		return status;
	}

	private String getOldOwnerFullName(final ChangeOfOwnershipDTO ownerDTO) {

		final StringBuilder builder = new StringBuilder();
		if (ownerDTO.getChOldTitle() != null) {
			final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.LookUp.TITLE,
					UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : lookUps) {
				if (lookUp.getLookUpId() == ownerDTO.getChOldTitle()) {

					builder.append(lookUp.getLookUpDesc() != null ? lookUp.getLookUpDesc() : MainetConstants.BLANK);
					builder.append(MainetConstants.WHITE_SPACE);
					builder.append(ownerDTO.getChOldName() != null ? ownerDTO.getChOldName() : MainetConstants.BLANK);
					builder.append(MainetConstants.WHITE_SPACE);
					builder.append(ownerDTO.getChOldMnNme() != null ? ownerDTO.getChOldMnNme() : MainetConstants.BLANK);
					builder.append(MainetConstants.WHITE_SPACE);
					builder.append(ownerDTO.getChOldLName() != null ? ownerDTO.getChOldLName() : MainetConstants.BLANK);

					break;
				}
			}
		} else {
			throw new FrameworkException("Title not found of Old Owner");
		}

		return builder.toString();
	}

	private String getGenderId(final String lookUpCode) {
		String genderId = StringUtils.EMPTY;
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((lookUpCode != null) && !lookUpCode.isEmpty()) {
				if (lookUpCode.equalsIgnoreCase(lookUp.getLookUpCode())) {
					genderId = Long.toString(lookUp.getLookUpId());
					break;
				}
			}
		}

		return genderId;
	}

	@Override
	public void initializeApplicantDetail() {
		setApplicantDetailDto(new ApplicantDetailDTO());
		setOwnerDTO(new ChangeOfOwnershipDTO());
		setChangeOwnerMaster(new ChangeOfOwnerRequestDTO());
		getOwnerDTO().setCsmrInfoDTO(new TbCsmrInfoDTO());
	}

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {

		case PrefixConstants.NewWaterServiceConstants.TRF:
			return "commonDto.csmrInfoDTO.trmGroup";

		case PrefixConstants.NewWaterServiceConstants.WWZ:
			return "applicantDetailDto.dwzid";
		case PrefixConstants.WATERMODULEPREFIX.CSZ:
			return "commonDto.csmrInfoDTO.csCcnsize";

		}
		return null;
	}

	/**
	 * validate all mandatory fields
	 * 
	 * @param bindingResult
	 */

	private void validateBean() {
		final String emailPattern = MainetConstants.EMAIL_PATTERN;
		final String mobileNoValidPattern = MainetConstants.MOB_PATTERN;
		final String pincodeValidPattern = MainetConstants.PIN_CODE;

		final ApplicantDetailDTO applicantDetailDTO = getApplicantDetailDto();

		if ((applicantDetailDTO.getApplicantTitle() == null) || (applicantDetailDTO.getApplicantTitle() == 0)) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantNameTitle"));
		}

		if ((applicantDetailDTO.getApplicantFirstName() == null)
				|| applicantDetailDTO.getApplicantFirstName().trim().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantFirstName"));
		}

		if ((applicantDetailDTO.getApplicantLastName() == null)
				|| applicantDetailDTO.getApplicantLastName().trim().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantLastName"));
		}

		if ((applicantDetailDTO.getGender() == null) || applicantDetailDTO.getGender().equals(MainetConstants.ZERO)) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantGender"));
		}

		if ((applicantDetailDTO.getMobileNo() != null) && !applicantDetailDTO.getMobileNo().isEmpty()) {
			if (!Pattern.matches(mobileNoValidPattern, applicantDetailDTO.getMobileNo())) {
				addValidationError(getAppSession().getMessage("water.validation.ApplicantInvalidmobile"));
			}
		} else {
			addValidationError(getAppSession().getMessage("water.validation.Applicantentermobileno"));
		}
		if ((applicantDetailDTO.getEmailId() != null) && !applicantDetailDTO.getEmailId().trim().isEmpty()) {
			if (!Pattern.matches(emailPattern, applicantDetailDTO.getEmailId().trim())) {
				addValidationError(getAppSession().getMessage("emailId.invalid"));
			}
		}

		if ((applicantDetailDTO.getBlockName() == null) || applicantDetailDTO.getBlockName().trim().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantBlockName"));
		}

		if ((applicantDetailDTO.getVillageTownSub() == null)
				|| applicantDetailDTO.getVillageTownSub().trim().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.validation.ApplicantcityVill"));
		}

		if ((applicantDetailDTO.getPinCode() != null) && !applicantDetailDTO.getPinCode().isEmpty()) {
			if (!Pattern.matches(pincodeValidPattern, applicantDetailDTO.getPinCode())) {
				addValidationError(getAppSession().getMessage("water.validation.ApplicantInvalidPincode"));
			}
		} else {
			addValidationError(getAppSession().getMessage("water.validation.applicantPinCode"));
		}

		if ((applicantDetailDTO.getIsBPL() == null) || applicantDetailDTO.getIsBPL().equals(MainetConstants.ZERO)) {
			addValidationError(getAppSession().getMessage("water.validation.selpoverty"));
		}
	}

	public ChangeOfOwnershipDTO getOwnerDTO() {
		return ownerDTO;
	}

	public void setOwnerDTO(final ChangeOfOwnershipDTO ownerDTO) {
		this.ownerDTO = ownerDTO;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(final List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public boolean isScrutinyApplicable() {
		return scrutinyApplicable;
	}

	public void setScrutinyApplicable(final boolean scrutinyApplicable) {
		this.scrutinyApplicable = scrutinyApplicable;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public List<AdditionalOwnerInfoDTO> getAdditionalOwners() {
		return additionalOwners;
	}

	public void setAdditionalOwners(final List<AdditionalOwnerInfoDTO> additionalOwners) {
		this.additionalOwners = additionalOwners;
	}

	/*---------------- New Added  ---------------*/

	/**
	 * @param changeOwnerMaster2
	 * @return
	 */
	public boolean validateFormData(final ChangeOfOwnerRequestDTO changeOwnerMasterData) {

		boolean status = false;

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
					getAppSession().getMessage("water.clickSearch" + getChangeOwnerResponse().getConnectionNumber()));
			status = true;
		}
		return status;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(final long deptId) {
		this.deptId = deptId;
	}

	public ChangeOfOwnerRequestDTO getChangeOwnerMaster() {
		return changeOwnerMaster;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(final String isFree) {
		this.isFree = isFree;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(final String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(final double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public void setChangeOwnerMaster(final ChangeOfOwnerRequestDTO changeOwnerMaster) {
		this.changeOwnerMaster = changeOwnerMaster;
	}

	public ChangeOfOwnerResponseDTO getChangeOwnerResponse() {
		return changeOwnerResponse;
	}

	public void setChangeOwnerResponse(final ChangeOfOwnerResponseDTO changeOwnerResponse) {
		this.changeOwnerResponse = changeOwnerResponse;
	}

	public String getValidConnectionNo() {
		return validConnectionNo;
	}

	public void setValidConnectionNo(final String validConnectionNo) {
		this.validConnectionNo = validConnectionNo;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(final List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	

	public String titleDesc(final long title) {
		String titleDesc = StringUtils.EMPTY;
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.LookUp.TITLE,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == title) {
				titleDesc = lookUp.getLookUpDesc();
				break;
			}
		}

		return titleDesc;
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

	private void proceedForChallan(final CommonChallanDTO offline, final ResponseDTO responseDTO) {

		offline.setApplNo(responseDTO.getApplicationNo());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(getChangeOwnerMaster().getServiceId());
		offline.setApplicantName(getApplicantDetailDto().getApplicantFirstName());
		offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(getChangeOwnerResponse().getCsAddress());
		offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("water.ConnectionNo"));
		offline.setPropNoConnNoEstateNoV(changeOwnerResponse.getConnectionNumber());
		offline.setReferenceNo(changeOwnerResponse.getCsOldNo());
		StringBuilder applicantName = new StringBuilder();
		applicantName.append(applicantDetailDto.getApplicantFirstName());
		if (StringUtils.isNotBlank(applicantDetailDto.getApplicantMiddleName())) {
			applicantName.append(MainetConstants.WHITE_SPACE);
			applicantName.append(applicantDetailDto.getApplicantMiddleName());
		}
		if (StringUtils.isNotBlank(applicantDetailDto.getApplicantLastName())) {
			applicantName.append(MainetConstants.WHITE_SPACE);
			applicantName.append(applicantDetailDto.getApplicantLastName());
		}
		offline.setApplicantFullName(applicantName.toString());
		for (final ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}
		if ((getCheckList() != null) && (getCheckList().size() > 0)) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(getDeptId());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = iChallanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, getServiceName());
			setReceiptDTO(printDto);
			setSuccessMessage(getAppSession().getMessage("water.receipt"));
		}
	}

	/**
	 * 
	 * @param serviceId
	 * @param orgId
	 */

	@SuppressWarnings("unchecked")
	public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId) {

		// [START] BRMS call initialize model

		final WSRequestDTO dto = new WSRequestDTO();
		final WSRequestDTO initReqdto = new WSRequestDTO();
		initReqdto.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);

		final WSResponseDTO response = brmsCommonService.initializeModel(initReqdto);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

			final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);

			CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);

			checkListModel2.setOrgId(orgId);
			checkListModel2.setServiceCode(MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER);
			checkListModel2.setIsBPL(getApplicantDetailDto().getIsBPL());
			/*
			 * checkListModel2.setApplicantType(CommonMasterUtility.getCPDDescription(
			 * changeOwnerResponse.getApplicantType(),
			 * PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
			 */
			checkListModel2 = settingUsageSubtype(checkListModel2, changeOwnerResponse,
					UserSession.getCurrent().getOrganisation());
			dto.setDataModel(checkListModel2);

			// final List<DocumentDetailsVO> checklistResponse =
			// checklistAndChargeService.getChecklist(checkListModel2);
			// setCheckList(checklistResponse); // checklist done
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel2);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (checklistRespDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				List<DocumentDetailsVO> checkListList = Collections.emptyList();
				checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

				long cnt = 1;
				for (final DocumentDetailsVO doc : checkListList) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
				if ((checkListList != null) && !checkListList.isEmpty()) {
					setCheckList(checkListList);
				}
			}

			// water rate start
			WaterRateMaster.setOrgId(orgId);
			WaterRateMaster.setServiceCode(MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER);
			WaterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL, PrefixConstants.LookUpPrefix.CAA)
					.getLookUpId()));
			dto.setDataModel(WaterRateMaster);

			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(WaterRateMaster);
			WSResponseDTO res = brmsWaterService.getApplicableTaxes(taxRequestDto);

			/*
			 * final WSResponseDTO res =
			 * checklistAndChargeService.getApplicableTaxes(WaterRateMaster,
			 * UserSession.getCurrent().getOrganisation().getOrgid(), serviceCode);
			 */
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
				if (!res.isFree()) {
					// final List<?> rates = RestClient.castResponse(res, WaterRateMaster.class);
					final List<Object> rates = (List<Object>) res.getResponseObj();
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						final WaterRateMaster master1 = (WaterRateMaster) rate;
						master1.setOrgId(orgId);
						master1.setServiceCode(MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER);
						master1.setDeptCode(MainetConstants.DeptCode.WATER);
						
						
						master1.setMeterType(CommonMasterUtility.getCPDDescription(changeOwnerResponse.getMeterType(),
								PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
						if(CollectionUtils.isNotEmpty(master1.getDependsOnFactorList())) {
						    
						    for (String dependFactor :  master1.getDependsOnFactorList()) {
							
							if(StringUtils.equalsIgnoreCase(dependFactor, "BPL")) {
							    master1.setIsBPL(getApplicantDetailDto().getIsBPL());
							}
							
							if(StringUtils.equalsIgnoreCase(dependFactor, "CON")) {
							    master1.setConnectionSize(Double.parseDouble(CommonMasterUtility.getCPDDescription(
									changeOwnerResponse.getConSize(), PrefixConstants.D2KFUNCTION.ENGLISH_DESC)));
							}
							
							if(StringUtils.equalsIgnoreCase(dependFactor, "WTC")) {
							    master1.setUsageSubtype1(checkListModel2.getUsageSubtype1());
								master1.setUsageSubtype2(checkListModel2.getUsageSubtype2());
							}
							
						    }
						}
						
						master1.setRateStartDate(new Date().getTime());
						/*
						 * master1.setTaxSubCategory( getSubCategoryDesc(master1.getTaxSubCategory(),
						 * UserSession.getCurrent().getOrganisation()));
						 */
						
						master1.setTransferMode(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.TFM,
								UserSession.getCurrent().getOrganisation().getOrgid(),
								getChangeOwnerMaster().getOwnerTransferMode()));
						master1.setChargeApplicableAt(CommonMasterUtility.findLookUpDesc(
								PrefixConstants.LookUpPrefix.CAA, UserSession.getCurrent().getOrganisation().getOrgid(),
								Long.parseLong(WaterRateMaster.getChargeApplicableAt())));

						requiredCHarges.add(master1);
					}
					dto.setDataModel(requiredCHarges);
					/*
					 * final List<ChargeDetailDTO> output =
					 * checklistAndChargeService.getApplicableCharges(requiredCHarges);
					 * setChargesInfo(output);
					 */
					WSRequestDTO chargeReqDto = new WSRequestDTO();
					chargeReqDto.setDataModel(requiredCHarges);
					WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
					// final List<ChargeDetailDTO> charges =
					// checklistAndChageService.getApplicableCharges(requiredCHarges);
					setChargesInfo((List<ChargeDetailDTO>) applicableCharges.getResponseObj());
					setAmountToPay(chargesToPay((List<ChargeDetailDTO>) applicableCharges.getResponseObj()));
					setIsFree(MainetConstants.Common_Constant.NO);
					getOfflineDTO().setAmountToShow(getAmountToPay());
					if (getAmountToPay() == 0.0d) {
						throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
								+ " if service configured as Chageable");
					}
				} else {
					setIsFree(MainetConstants.Common_Constant.FREE);
				}

			} else {
				throw new FrameworkException("Problem while checking dependent param for water rate .");
			}
		} else {
			throw new FrameworkException("Problem while initializing CheckList and WaterRateMaster Model");
		}

	}

	// [END]

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	/**
	 * 
	 * @param waterRateMaster
	 * @param enity
	 * @param organisation
	 * @return
	 */
	private CheckListModel settingUsageSubtype(final CheckListModel checkListModel2,
			final ChangeOfOwnerResponseDTO responseDTO, final Organisation organisation) {

		final List<LookUp> usageSubtype1 = CommonMasterUtility.getLevelData(
				PrefixConstants.NewWaterServiceConstants.TRF, 1, UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : usageSubtype1) {
			if (responseDTO.getTrmGroup1() != null) {
				if (lookUp.getLookUpId() == responseDTO.getTrmGroup1()) {
					checkListModel2.setUsageSubtype1(lookUp.getDescLangFirst());//#139291 to set usagetype in both language reg and Eng 
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

	private List<DocumentDetailsVO> mapCheckList(final List<DocumentDetailsVO> docs,
			final BindingResult bindingResult) {

		final List<DocumentDetailsVO> docList = fileUpload.prepareFileUpload(docs);

		if ((docList != null) && !docList.isEmpty()) {
			for (final DocumentDetailsVO doc : docList) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
					if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
						bindingResult.addError(new ObjectError(MainetConstants.BLANK,
								ApplicationSession.getInstance().getMessage("water.fileuplaod.validtnMsg")));

						break;
					}
				}
			}
		}

		return docList;

	}

	public WaterApplicationAcknowledgementDTO getWaterApplicationAcknowledgementDTO() {
		return waterApplicationAcknowledgementDTO;
	}

	public void setWaterApplicationAcknowledgementDTO(
			WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO) {
		this.waterApplicationAcknowledgementDTO = waterApplicationAcknowledgementDTO;
	}
	

}
