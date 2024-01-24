package com.abm.mainet.water.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonApplicantDetailValidator;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterApplicationAcknowledgementDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.ChangeOfUsageService;
import com.abm.mainet.water.service.WaterChecklistAndChargeService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.validator.ChangeOfUsageValidator;

@Component
@Scope("session")
public class ChangeOfUsageModel extends AbstractFormModel {

    /** CHARGE_ERROR. */
    private static final String CHARGE_ERROR = "Problem while checking Application level charges applicable or not.";

    /** CONNECTION_SIZE. */
    private static final String CONNECTION_SIZE = "requestDTO.connectionSize";

    /** WWZID. */
    private static final String WWZID = "applicantDetailDto.dwzid";

    /** OLD_TRM_GROUP. */
    private static final String OLD_TRM_GROUP = "requestDTO.changeOfUsage.oldTrmGroup";

    /** Trm group new. */
    private static final String NEW_TRM_GROUP = "requestDTO.changeOfUsage.newTrmGroup";

    /** The Constant WATER_CONN_EMPTY_ERR. */
    private static final String WATER_CONN_EMPTY_ERR = "water.connEmptyErr";

    /** Error Message. */
    private static final String ERROR = "web service call failed to save change of Usage";

    /** Fail Message. */
    private static final String FAIL = "water.changeusage.fail";

    /** Success Message. */
    private static final String SUCCESS = "water.changeusage.success";

    private static final long serialVersionUID = -3307269739839482467L;

    private static final String OLD_METER_TYPE ="entity.csMeteredccn";

    private static final String NEW_METER_TYPE ="requestDTO.changeOfUsage.newCsMeteredccn";

    private TbCsmrInfoDTO entity = null;

    @Autowired
    private IChallanService challanService;

    @Resource
    private CommonService commonService;

    @Resource
    private ChangeOfUsageService changeOfUsageService;

    @Autowired
    private WaterChecklistAndChargeService checklistAndChageService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private IChecklistVerificationService checklistVerificationService;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private BRMSWaterService brmsWaterService;
    
    @Autowired
    private WaterCommonService waterCommonService;

    private ChangeOfUsageRequestDTO requestDTO = new ChangeOfUsageRequestDTO();
    private ChangeOfUsageResponseDTO responseDTO = new ChangeOfUsageResponseDTO();
    private CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

    private String radio = null;
    private List<DocumentDetailsVO> checkList;
    private List<CFCAttachment> documentList = new ArrayList<>();

    // to identify whether service is free or not
    private boolean payable;
    private double amountToPay;
    private double actualAmount;
    private String validConnectionNo;
    private boolean enableSubmit;
    private boolean resultFound;
    private boolean dualReturn;
    private List<ChargeDetailDTO> chargesInfo;

    private Long deptId;
    
    @Autowired
    private ICFCApplicationMasterService cfcService;
	
    private WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO;
    private TbCfcApplicationMstEntity cfcEntity;
	private ServiceMaster serviceMaster;

    @Override
    protected void initializeModel() {

	initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.TRF);
	initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.WWZ);
	initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.CSZ);
    }

    @Override
    public boolean saveForm() {
    if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)){
    	validateConnectionNo();
    }else {
    	validateApplicantDetail();
    }
	validateChangeDetail();
	final CommonChallanDTO offline = getOfflineDTO();
	final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
	offline.setOfflinePaymentText(modeDesc);
	fileUploadService.validateUpload(getBindingResult());
	if (getAmountToPay() > 0d) {
	    validateBean(offline, CommonOfflineMasterValidator.class);
	}

	if (hasValidationErrors()) {
	    return false;
	}
	// used to set doc is uploaded or not
	offline.setDocumentUploaded(!FileUploadUtility.getCurrent().getFileMap().isEmpty());
	setGender(getApplicantDetailDto());
	getRequestDTO().setApplicant(getApplicantDetailDto());
	getRequestDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	getRequestDTO().setServiceId(getServiceId());
	getRequestDTO().setLangId((long) UserSession.getCurrent().getLanguageId());
	getRequestDTO().setDocumentList(checklistAndChageService.getFileUploadList(getCheckList(),
		FileUploadUtility.getCurrent().getFileMap()));
	getRequestDTO().setDeptId(deptId);
	getRequestDTO().setUserId(UserSession.getCurrent().getEmployee().getEmpId());

	getRequestDTO().getChangeOfUsage().setOrgId(getRequestDTO().getOrgId());
	getRequestDTO().getChangeOfUsage().setLmoddate(new Date());
	getRequestDTO().getChangeOfUsage().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	getRequestDTO().getChangeOfUsage().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
	getRequestDTO().getChangeOfUsage().setLangId((long) UserSession.getCurrent().getLanguageId());
	getRequestDTO().getChangeOfUsage().setCsIdn(getCustomerInfoDTO().getCsIdn());
	getRequestDTO().setPayAmount(getAmountToPay());

	final ChangeOfUsageDTO dto = changeOfUsageService.saveOrUpdateChangeOfUses(requestDTO);
	if ((null != dto) && (null != dto.getApmApplicationId())) {
		waterCommonService.updateConnectionAppNoByCsIdn(dto.getCsIdn(), dto.getApmApplicationId());
	    responseDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
	    responseDTO.setApplicationNo(dto.getApmApplicationId());
	    changeOfUsageService.initiateWorkflowForFreeService(requestDTO);
	} else {
	    responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
	}

	if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getStatus())) {
	    if (responseDTO.getApplicationNo() != null) {
		setApmApplicationId(responseDTO.getApplicationNo());
		getRequestDTO().setApplicationId(responseDTO.getApplicationNo());

		if (getAmountToPay() > 0D) {
		    proceedForChallan(offline, responseDTO);
		}

		setSuccessMessage(getAppSession().getMessage(SUCCESS,
			new Object[] { responseDTO.getApplicationNo().toString() }));
		
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			waterApplicationAcknowledgementDTO = new WaterApplicationAcknowledgementDTO();
			waterApplicationAcknowledgementDTO.setApplicationId(dto.getApmApplicationId().toString());
			
			TbKCsmrInfoMH csmrInfo = changeOfUsageService.getConnDetailsByChangeOfUsageConnId(dto.getApmApplicationId(), 
					UserSession.getCurrent().getOrganisation().getOrgid());
			if(csmrInfo!=null) {
				waterApplicationAcknowledgementDTO.setApplicantName(Stream.of(csmrInfo.getCsName(), csmrInfo.getCsMname(),
					csmrInfo.getCsLname()).filter(nameString -> nameString!=null && !nameString.isEmpty()).collect(Collectors.joining(" ")));
			}
			
			cfcEntity = cfcService.getCFCApplicationByApplicationId(dto.getApmApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
	        serviceMaster = cfcEntity.getTbServicesMst();
	        waterApplicationAcknowledgementDTO.setServiceName(getServiceName());
			waterApplicationAcknowledgementDTO.setDepartmentName(serviceMaster.getTbDepartment().getDpDeptdesc());
			waterApplicationAcknowledgementDTO.setAppTime(new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(new Date()));
			setDepartmentCode(serviceMaster.getTbDepartment().getDpDeptcode());
			if ((getCheckList() != null) && !getCheckList().isEmpty()) {
				List<DocumentDetailsVO> mandatoryDocuments = getCheckList().stream().filter(
						doc->MainetConstants.Y_FLAG.equals(doc.getCheckkMANDATORY())).collect(Collectors.toList());
				waterApplicationAcknowledgementDTO.setCheckList(mandatoryDocuments);
			}
			long duration = 0;
			if (serviceMaster.getSmServiceDuration() != null) {
				duration = serviceMaster.getSmServiceDuration();
				waterApplicationAcknowledgementDTO.setWtConnDueDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(Utility.getAddedDateBy2(new Date(), (int)duration)));
			}
		}
		
	    } else {
		setSuccessMessage(getAppSession().getMessage(FAIL));
	    }
	} else {
	    throw new FrameworkException(ERROR);
	}

	return true;

    }

    public boolean saveScrutinyForm() {
	changeOfUsageService.saveChangeOfUses(requestDTO.getChangeOfUsage());
	return true;

    }

    public void validateApplicantDetail() {
	validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
	if ((requestDTO.getConnectionNo() == null) || requestDTO.getConnectionNo().isEmpty()) {
	    addValidationError(getAppSession().getMessage(WATER_CONN_EMPTY_ERR));
		}
    }
    
    public void validateConnectionNo() {
    	if ((requestDTO.getConnectionNo() == null) || requestDTO.getConnectionNo().isEmpty()) {
    	    addValidationError(getAppSession().getMessage(WATER_CONN_EMPTY_ERR));
    	}
    }

    public void validateChangeDetail() {
	validateBean(getRequestDTO(), ChangeOfUsageValidator.class);
    }

    private void proceedForChallan(final CommonChallanDTO offline, final ChangeOfUsageResponseDTO responseDTO) {

	offline.setApplNo(responseDTO.getApplicationNo());
	offline.setAmountToPay(Double.toString(getAmountToPay()));
	offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	offline.setLangId(UserSession.getCurrent().getLanguageId());
	offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
	offline.setFaYearId(UserSession.getCurrent().getFinYearId());
	offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
	offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
	offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("water.ConnectionNo"));
	offline.setPropNoConnNoEstateNoV(customerInfoDTO.getCsCcn());
	offline.setReferenceNo(customerInfoDTO.getCsOldccn());
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
	offline.setServiceId(getServiceId());
	String userName = (getApplicantDetailDto().getApplicantFirstName() == null ? MainetConstants.BLANK
		: getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE);
	userName += getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
		: getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
	userName += getApplicantDetailDto().getApplicantLastName() == null ? MainetConstants.BLANK
		: getApplicantDetailDto().getApplicantLastName();
	offline.setApplicantName(userName);
	offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
	offline.setEmailId(getApplicantDetailDto().getEmailId());
	offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
	for (final ChargeDetailDTO dto : getChargesInfo()) {
	    offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
	}
	offline.setApplicantAddress(getApplicantDetailDto().getAreaName());
	offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
	offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
	offline.setDeptId(getDeptId());
	offline.setOfflinePaymentText(CommonMasterUtility
		.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
		.getLookUpCode());
	if ((offline.getOnlineOfflineCheck() != null)
		&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {

	    final ChallanMaster challanNumber = challanService.InvokeGenerateChallan(offline);
	    offline.setChallanNo(challanNumber.getChallanNo());
	    offline.setChallanValidDate(challanNumber.getChallanValiDate());

	    setOfflineDTO(offline);
	} else if ((offline.getOnlineOfflineCheck() != null)
		&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
	    final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline, getServiceName());
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
    public void findApplicableCheckListAndCharges(final long serviceId, final long orgId) {

	WSResponseDTO response = null;
	final WSRequestDTO initReqdto = new WSRequestDTO();
	initReqdto.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
	response = brmsCommonService.initializeModel(initReqdto);
	// response = checklistAndChageService.initializeModel();
	if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
	    final List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
	    final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
	    final CheckListModel checkListModel = (CheckListModel) checklist.get(0);
	    final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);

	    checkListModel.setOrgId(orgId);
	    checkListModel.setDeptCode(MainetConstants.WATER_DEPT);
	    checkListModel.setServiceCode(WaterServiceShortCode.CHANGE_OF_USAGE);
	    checkListModel.setIsBPL(getApplicantDetailDto().getIsBPL());
	    /*
	     * checkListModel.setApplicantType(CommonMasterUtility.getCPDDescription(
	     * customerInfoDTO.getApplicantType(),
	     * PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
	     */
	    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup1()) {
		checkListModel.setUsageSubtype1(CommonMasterUtility
			.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup1(), orgId)
			.getDescLangFirst());
	    }
	    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup2()) {
		checkListModel.setUsageSubtype2(CommonMasterUtility
			.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup2(), orgId)
			.getDescLangFirst());
	    }
	    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup3()) {
		checkListModel.setUsageSubtype3(CommonMasterUtility
			.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup3(), orgId)
			.getDescLangFirst());
	    }
	    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup4()) {
		checkListModel.setUsageSubtype4(CommonMasterUtility
			.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup4(), orgId)
			.getDescLangFirst());
	    }
	    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup5()) {
		checkListModel.setUsageSubtype5(CommonMasterUtility
			.getHierarchicalLookUp(requestDTO.getChangeOfUsage().getNewTrmGroup5(), orgId)
			.getDescLangFirst());
	    }
	    WSRequestDTO checklistReqDto = new WSRequestDTO();
	    checklistReqDto.setDataModel(checkListModel);
	    WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
	    /*
	     * if (checklistRespDto.getWsStatus().equals(MainetConstants.WebServiceStatus.
	     * SUCCESS)) { setCheckList((List<DocumentDetailsVO>)
	     * checklistRespDto.getResponseObj()); }
	     */
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
	    final WSRequestDTO taxRequestDto = new WSRequestDTO();
	    waterRateMaster.setOrgId(orgId);
	    waterRateMaster.setServiceCode(WaterServiceShortCode.CHANGE_OF_USAGE);

	    Organisation lookupOrg = new Organisation();
	    lookupOrg.setOrgid(orgId);

	    waterRateMaster
		    .setChargeApplicableAt(
			    Long.toString(
				    CommonMasterUtility
					    .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
						    MainetConstants.NewWaterServiceConstants.CAA, lookupOrg)
					    .getLookUpId()));
	    taxRequestDto.setDataModel(waterRateMaster);

	    response = brmsWaterService.getApplicableTaxes(taxRequestDto);
	    // response = checklistAndChageService.getApplicableTaxes(waterRateMaster,
	    // orgId,
	    // WaterServiceShortCode.CHANGE_OF_USAGE);

	    if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
		if (response.isFree()) {
		    requestDTO.setFree(true);
		    setPayable(false);
		    setAmountToPay(0.0d);

		} else {
		    // final List<?> rates = RestClient.castResponse(response,
		    // WaterRateMaster.class);
		    final List<Object> rates = (List<Object>) response.getResponseObj();
		    final List<WaterRateMaster> requiredCHarges = new ArrayList<>();

		    for (final Object rate : rates) {
			final WaterRateMaster rateMaster = (WaterRateMaster) rate;
			rateMaster.setOrgId(orgId);
			rateMaster.setDeptCode(MainetConstants.WATER_DEPT);
			rateMaster.setServiceCode(WaterServiceShortCode.CHANGE_OF_USAGE);
			

			if (CollectionUtils.isNotEmpty(rateMaster.getDependsOnFactorList())) {
			    rateMaster.getDependsOnFactorList().forEach(dependFactor -> {
				
				if(StringUtils.equalsIgnoreCase(dependFactor, "BPL")) {
				    rateMaster.setIsBPL(getApplicantDetailDto().getIsBPL());
				    }
				if (StringUtils.equalsIgnoreCase(dependFactor, "WTC")) {
				    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup1()) {
					rateMaster
						.setUsageSubtype1(CommonMasterUtility
							.getHierarchicalLookUp(
								requestDTO.getChangeOfUsage().getNewTrmGroup1(), orgId)
							.getDescLangFirst());
				    }
				    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup2()) {
					rateMaster
						.setUsageSubtype2(CommonMasterUtility
							.getHierarchicalLookUp(
								requestDTO.getChangeOfUsage().getNewTrmGroup2(), orgId)
							.getDescLangFirst());
				    }
				    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup3()) {
					rateMaster
						.setUsageSubtype3(CommonMasterUtility
							.getHierarchicalLookUp(
								requestDTO.getChangeOfUsage().getNewTrmGroup3(), orgId)
							.getDescLangFirst());
				    }
				    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup4()) {
					rateMaster
						.setUsageSubtype4(CommonMasterUtility
							.getHierarchicalLookUp(
								requestDTO.getChangeOfUsage().getNewTrmGroup4(), orgId)
							.getDescLangFirst());
				    }
				    if (null != requestDTO.getChangeOfUsage().getNewTrmGroup5()) {
					rateMaster
						.setUsageSubtype5(CommonMasterUtility
							.getHierarchicalLookUp(
								requestDTO.getChangeOfUsage().getNewTrmGroup5(), orgId)
							.getDescLangFirst());
				    }
				}
			    });
			}

			rateMaster.setRateStartDate(new Date().getTime());
			if (customerInfoDTO.getCsMeteredccn() != null) {
			    rateMaster.setMeterType(CommonMasterUtility
				    .getNonHierarchicalLookUpObject(customerInfoDTO.getCsMeteredccn(), lookupOrg)
				    .getDescLangFirst());
			}
			requiredCHarges.add(rateMaster);
		    }

		    WSRequestDTO chargeReqDto = new WSRequestDTO();
		    chargeReqDto.setDataModel(requiredCHarges);
		    WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
		    // final List<ChargeDetailDTO> charges =
		    // checklistAndChageService.getApplicableCharges(requiredCHarges);
		    setChargesInfo((List<ChargeDetailDTO>) applicableCharges.getResponseObj());
		    setAmountToPay(checklistAndChageService
			    .chargesToPay((List<ChargeDetailDTO>) applicableCharges.getResponseObj()));
		    if (getAmountToPay() == 0.0d) {
				throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
						+ " if service configured as Chargeable");
			}
		    getOfflineDTO().setAmountToShow(getAmountToPay());
		    setPayable(true);
		}

	    } else {

		throw new FrameworkException(CHARGE_ERROR);
	    }
	}
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

    public String getRadio() {
	return radio;
    }

    public void setRadio(final String radio) {
	this.radio = radio;
    }

    @Override
    public void populateApplicationData(final long applicationId) {

	requestDTO = changeOfUsageService.getApplicantData(applicationId,
		UserSession.getCurrent().getOrganisation().getOrgid());
	applicantDetailDto = requestDTO.getApplicant();

	setDocumentList(checklistVerificationService.getDocumentUploaded(applicationId,
		UserSession.getCurrent().getOrganisation().getOrgid()));
	setDualReturn(false);
    }

    @Override
    public String getConnectioNo(final long applicationId, final long serviceid) {

	final TbKCsmrInfoMH master = changeOfUsageService.getConnDetailsByChangeOfUsageConnId(applicationId,
		UserSession.getCurrent().getOrganisation().getOrgid());
	return master.getCsCcn() + MainetConstants.operator.COMMA + master.getPlumId();
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
	String result = MainetConstants.BLANK;
	switch (parentCode) {

	case PrefixConstants.WATERMODULEPREFIX.TRF:

	    if (dualReturn) {
		dualReturn = false;
		result = NEW_TRM_GROUP;
	    } else {
		dualReturn = true;
		result = OLD_TRM_GROUP;
	    }
	    break;
	case PrefixConstants.WATERMODULEPREFIX.WWZ:
	    result = WWZID;
	    break;
	case PrefixConstants.WATERMODULEPREFIX.CSZ:
	    result = CONNECTION_SIZE;
	    break;
	case PrefixConstants.NewWaterServiceConstants.WMN :
		result = dualReturn? OLD_METER_TYPE: NEW_METER_TYPE;
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

    public ApplicantDetailDTO getApplicantDetailDto() {
	return applicantDetailDto;
    }

    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
	this.applicantDetailDto = applicantDetailDto;
    }

    @Override
    public List<ChargeDetailDTO> getChargesInfo() {
	return chargesInfo;
    }

    @Override
    public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
	this.chargesInfo = chargesInfo;
    }

    public List<CFCAttachment> getDocumentList() {
	return documentList;
    }

    public void setDocumentList(final List<CFCAttachment> documentList) {
	this.documentList = documentList;
    }

    public boolean isPayable() {
	return payable;
    }

    public void setPayable(final boolean payable) {
	this.payable = payable;
    }

	public TbCsmrInfoDTO getEntity() {
		return entity;
	}

	public void setEntity(TbCsmrInfoDTO entity) {
		this.entity = entity;
	}

	public WaterApplicationAcknowledgementDTO getWaterApplicationAcknowledgementDTO() {
		return waterApplicationAcknowledgementDTO;
	}

	public void setWaterApplicationAcknowledgementDTO(
			WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO) {
		this.waterApplicationAcknowledgementDTO = waterApplicationAcknowledgementDTO;
	}
    
}
