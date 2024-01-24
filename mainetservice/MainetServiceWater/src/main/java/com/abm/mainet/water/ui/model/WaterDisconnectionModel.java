package com.abm.mainet.water.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
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
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.WaterApplicationAcknowledgementDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.WaterChecklistAndChargeService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterDisconnectionService;
import com.abm.mainet.water.ui.validator.WaterDisconnectionValidator;

@Component
@Scope("session")
public class WaterDisconnectionModel extends AbstractFormModel {

	private static final long serialVersionUID = -6056343326068062916L;
	private static final String CHARGE_ERROR = "Problem while checking Application level charges applicable or not.";

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private WaterDisconnectionService disconnectionService;

	@Autowired
	private IChallanService challanService;

	@Resource
	private CommonService commonService;

	@Autowired
	private WaterChecklistAndChargeService checklistAndChageService;

	@Resource
	private IFileUploadService fileUploadService;
	@Autowired
	private BRMSCommonService brmsCommonService;
	@Autowired
	private WaterCommonService waterCommonService;

	@Autowired
	private BRMSWaterService brmsWaterService;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private TBWaterDisconnectionDTO disconnectionEntity = new TBWaterDisconnectionDTO();
	private List<CustomerInfoDTO> connectionList = new ArrayList<>();
	private String connectionNo;
	private String ulbPlumber;
	private String areaName;
	private String consumerName;
	private String plumberLicence;
	private List<DocumentDetailsVO> checkList;
	private boolean payable;
	private double amountToPay;
	private Long applicationId;
	private boolean enableSubmit;
	private String csOldCcn;

	private Long deptId;
	private static final Logger LOGGER = LoggerFactory.getLogger(WaterDisconnectionModel.class);
	private List<CFCAttachment> documentList = new ArrayList<>();
	private List<ChargeDetailDTO> chargesInfo;

	public List<PlumberMaster> plumberList = new ArrayList<>();
	public List<PlumberMaster> ulbPlumberList = new ArrayList<>();
	
	@Autowired
    private ICFCApplicationMasterService cfcService;
	
	private WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO; 
	private TbCfcApplicationMstEntity cfcEntity; 
	private ServiceMaster serviceMaster; 
	private String checkerror ;

	@Override
	public void populateApplicationData(final long applicationId) {
		final WaterDeconnectionRequestDTO disconnectionDetail = disconnectionService
				.getAppDetailsForDisconnection(UserSession.getCurrent().getOrganisation().getOrgid(), applicationId);
		documentList = checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		connectionNo = disconnectionDetail.getConnectionInfo().getCsCcn();
		areaName = disconnectionDetail.getConnectionInfo().getCsAdd();
		consumerName = disconnectionDetail.getConnectionInfo().getConsumerName();
		applicantDetailDto = disconnectionDetail.getApplicantDetailsDto();
		disconnectionEntity = disconnectionDetail.getDisconnectionDto();

		if (null != disconnectionEntity.getPlumId()) {
			plumberLicence = waterCommonService.getPlumberLicenceName(disconnectionEntity.getPlumId());
		}

		if (null == disconnectionEntity.getPlumId()) {
			setUlbPlumber(MainetConstants.Common_Constant.YES);
		} else {
			setUlbPlumber(MainetConstants.Common_Constant.NO);
		}
	}

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {
		case PrefixConstants.NewWaterServiceConstants.WWZ:
			return "applicantDetailDto.dwzid";

		}
		return null;
	}

	@Override
	public boolean saveForm() {
		final WaterDeconnectionRequestDTO requestDTO = new WaterDeconnectionRequestDTO();
		requestDTO.setDisconnectionDto(disconnectionEntity);
		disconnectionService.updateDisconnectionDetails(requestDTO);
		setSuccessMessage(getAppSession().getMessage("water.plumberUpdate"));
		return true;
	};

	public boolean saveWaterForm() {

		boolean result = false;

		validateApplicantDetail();
		validateConnectionDetail();
		fileUploadService.validateUpload(getBindingResult());
		final CommonChallanDTO offline = getOfflineDTO();
		if (getAmountToPay() > 0) {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}
		if ((null != ulbPlumber) && ulbPlumber.equals(MainetConstants.MENU.N)) {
			if ((null == plumberLicence) || plumberLicence.isEmpty()) {
				addValidationError(getAppSession().getMessage("water.enter.plumberLicence"));
			}

			if (hasValidationErrors()) {
				return result;
			}

			{

				final Long outsideUlbPlumber = disconnectionService.validateOtherUlbLicence(plumberLicence);
				if (null == outsideUlbPlumber) {

					addValidationError(getAppSession().getMessage("water.invalid.plumberLicence",
							new Object[] { plumberLicence }));
				} else {

					disconnectionEntity.setPlumId(outsideUlbPlumber);
				}

			}
		}
		if (hasValidationErrors()) {
			return result;
		}
		getDisconnectionEntity().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		getDisconnectionEntity().setDiscAppdate(new Date());
		getDisconnectionEntity().setLmodDate(new Date());
		getDisconnectionEntity().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		getDisconnectionEntity().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		try {

			final WaterDeconnectionRequestDTO waterRequestDto = new WaterDeconnectionRequestDTO();

			for (final CustomerInfoDTO tbKCsmrInfoMH : connectionList) {
				if ((tbKCsmrInfoMH.getCsCcn() != null)
						&& tbKCsmrInfoMH.getCsCcn().equalsIgnoreCase(getConnectionNo())) {
					getDisconnectionEntity().setCsIdn(tbKCsmrInfoMH.getCsIdn());
					break;
				}
			}

			getDisconnectionEntity().setDiscMethod(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.APP,
							PrefixConstants.WATERMODULEPREFIX.CAN, UserSession.getCurrent().getOrganisation())
					.getLookUpId());
			waterRequestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			waterRequestDto.setApplicantDetailsDto(getApplicantDetailDto());
			waterRequestDto.setDisconnectionDto(getDisconnectionEntity());
			waterRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			getApplicantDetailDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			waterRequestDto.setServiceId(getServiceId());
			waterRequestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			// waterRequestDto.setDocumentList(checklistAndChageService.getFileUploadList(getCheckList(),
			// FileUploadUtility.getCurrent().getFileMap()));
			if ((getCheckList() != null) && !getCheckList().isEmpty()) {
				List<DocumentDetailsVO> docDetailsList = mapCheckList(getCheckList(), getBindingResult());
				waterRequestDto.setUploadDocument(docDetailsList);
			}
			waterRequestDto.setDeptId(deptId);
			if (getAmountToPay() > 0D) {
				waterRequestDto.setFreeService(false);
			} else {
				waterRequestDto.setFreeService(true);
			}
			final WaterDisconnectionResponseDTO waterResponseDto = disconnectionService
					.saveDisconnectionDetails(waterRequestDto);
			disconnectionService.initiateWorkflowForFreeService(waterRequestDto);
			setApplicationId(waterResponseDto.getApplicationNo());
			waterCommonService.updateConnectionAppNoByCsIdn(getDisconnectionEntity().getCsIdn(), waterResponseDto.getApplicationNo());

			// used to set doc is uploaded or not
			offline.setDocumentUploaded(!FileUploadUtility.getCurrent().getFileMap().isEmpty());
			setSuccessMessage(
					getAppSession().getMessage("water.disconnect.success", new Object[] { applicationId.toString() }));
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			if (getAmountToPay() > 0D) {
				offline.setApplNo(applicationId);
				offline.setAmountToPay(Double.toString(getAmountToPay()));
				offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				offline.setLangId(UserSession.getCurrent().getLanguageId());
				offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
				offline.setFaYearId(UserSession.getCurrent().getFinYearId());
				offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
				offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
				offline.setServiceId(getServiceId());
				offline.setApplicantName(getApplicantDetailDto().getApplicantFirstName());
				offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
				offline.setEmailId(getApplicantDetailDto().getEmailId());
				offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
				offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("water.ConnectionNo"));
				offline.setPropNoConnNoEstateNoV(connectionNo);
				offline.setReferenceNo(csOldCcn);
				offline.setApplicantAddress(areaName);
				StringBuilder applicantName = new StringBuilder();
				applicantName.append(getApplicantDetailDto().getApplicantFirstName());
				if (StringUtils.isNotBlank(getApplicantDetailDto().getApplicantMiddleName())) {
					applicantName.append(MainetConstants.WHITE_SPACE);
					applicantName.append(getApplicantDetailDto().getApplicantMiddleName());
				}
				if (StringUtils.isNotBlank(getApplicantDetailDto().getApplicantLastName())) {
					applicantName.append(MainetConstants.WHITE_SPACE);
					applicantName.append(getApplicantDetailDto().getApplicantLastName());
				}
				offline.setApplicantFullName(applicantName.toString());
				for (final ChargeDetailDTO dto : getChargesInfo()) {
					offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
				}
				offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
				offline.setDeptId(deptId);
				offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
						offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
				if ((offline.getOnlineOfflineCheck() != null)
						&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
					final ChallanMaster challanNumber = challanService.InvokeGenerateChallan(offline);
					offline.setChallanNo(challanNumber.getChallanNo());
					offline.setChallanValidDate(challanNumber.getChallanValiDate());
					setOfflineDTO(offline);
				} else if ((offline.getOnlineOfflineCheck() != null)
						&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
					final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
							getServiceName());
					setReceiptDTO(printDto);
					
					//US#102672					 // pushing document to DMS		
		            String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
							+ ApplicationSession.getInstance().getMessage("birtName.mutationReceipt")
							+ "&__format=pdf&RP_ORGID=" + offline.getOrgId() + "&RP_RCPTNO=" + printDto.getReceiptId();
		            Utility.pushDocumentToDms(URL, getConnectionNo(), MainetConstants.DEPT_SHORT_NAME.WATER,fileUploadService);
					
					// setSuccessMessage(getAppSession().getMessage("water.receipt"));
				}
			}
			result = true;
			if(result && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				waterApplicationAcknowledgementDTO = new WaterApplicationAcknowledgementDTO();
				waterApplicationAcknowledgementDTO.setServiceName(getServiceName());
				
				StringBuilder applicantName = new StringBuilder();
				if(getApplicantDetailDto().getApplicantFirstName() != null)
					applicantName.append(getApplicantDetailDto().getApplicantFirstName());
				
				if(getApplicantDetailDto().getApplicantMiddleName() != null) {
					applicantName.append(MainetConstants.WHITE_SPACE);
					applicantName.append(getApplicantDetailDto().getApplicantMiddleName());
				}
				if(getApplicantDetailDto().getApplicantLastName() != null) {
					applicantName.append(MainetConstants.WHITE_SPACE);
					applicantName.append(getApplicantDetailDto().getApplicantLastName());
				}
					
				waterApplicationAcknowledgementDTO.setApplicantName(applicantName.toString());
				
				waterApplicationAcknowledgementDTO.setAppTime(new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(new Date()));
				waterApplicationAcknowledgementDTO.setCheckList(getCheckList());
				waterApplicationAcknowledgementDTO.setApplicationId(applicationId.toString());
				cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationId, UserSession.getCurrent().getOrganisation().getOrgid());
		        serviceMaster = cfcEntity.getTbServicesMst();
				waterApplicationAcknowledgementDTO.setDepartmentName(serviceMaster.getTbDepartment().getDpDeptdesc());
				setDepartmentCode(serviceMaster.getTbDepartment().getDpDeptcode());
				long duration = 0;
				if (serviceMaster.getSmServiceDuration() != null) {
					duration = serviceMaster.getSmServiceDuration();
					waterApplicationAcknowledgementDTO.setWtConnDueDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(Utility.getAddedDateBy2(new Date(), (int)duration)));
				}
				
			}

		} catch (final Exception e) {
			LOGGER.error("Exception found while saving Disconnection Details : ", e);
			throw new FrameworkException("Exception found while saving Disconnection Details : ", e);
		}
		return result;

	}

	public void validateApplicantDetail() {
		if((!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) && getConnectionNo().isEmpty()) {
			/* if(getConnectionNo().isEmpty()){ */
				//addValidationError(getAppSession().getMessage("water.connEmptyErr"));
			/* } */
		}
		validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
	}

	public void validateConnectionDetail() {
		validateBean(this, WaterDisconnectionValidator.class);
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
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel = (CheckListModel) checklist.get(0);
			final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			if(CollectionUtils.isNotEmpty(connectionList)) {
			final CustomerInfoDTO connectionInfo = connectionList.get(0);
			TbKCsmrInfoMH master = waterCommonService.getWaterConnectionDetailsById(connectionInfo.getCsIdn(), orgId);
			checkListModel.setOrgId(orgId);
			checkListModel.setDeptCode(MainetConstants.WATER_DEPT);
			checkListModel.setServiceCode(WaterServiceShortCode.WATER_DISCONNECTION);
			checkListModel.setIsBPL(getApplicantDetailDto().getIsBPL());
			checkListModel.setDisConnectionType(CommonMasterUtility.getCPDDescription(disconnectionEntity.getDiscType(),
					PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
			if (null != connectionInfo.getTrmGroup1()) {
				checkListModel.setUsageSubtype1(CommonMasterUtility
						.getHierarchicalLookUp(connectionInfo.getTrmGroup1(), orgId).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup2()) {
				checkListModel.setUsageSubtype2(CommonMasterUtility
						.getHierarchicalLookUp(connectionInfo.getTrmGroup2(), orgId).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup3()) {
				checkListModel.setUsageSubtype3(CommonMasterUtility
						.getHierarchicalLookUp(connectionInfo.getTrmGroup3(), orgId).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup4()) {
				checkListModel.setUsageSubtype4(CommonMasterUtility
						.getHierarchicalLookUp(connectionInfo.getTrmGroup4(), orgId).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup5()) {
				checkListModel.setUsageSubtype5(CommonMasterUtility
						.getHierarchicalLookUp(connectionInfo.getTrmGroup5(), orgId).getDescLangFirst());
			}
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel);
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

			Organisation lookupOrg = new Organisation();
			lookupOrg.setOrgid(orgId);

			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			waterRateMaster.setOrgId(orgId);
			waterRateMaster.setServiceCode(WaterServiceShortCode.WATER_DISCONNECTION);
			waterRateMaster
					.setChargeApplicableAt(
							Long.toString(
									CommonMasterUtility
											.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
													MainetConstants.NewWaterServiceConstants.CAA, lookupOrg)
											.getLookUpId()));
			taxRequestDto.setDataModel(waterRateMaster);

			response = brmsWaterService.getApplicableTaxes(taxRequestDto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				if (response.isFree()) {

					setPayable(false);
					setAmountToPay(0.0d);

				} else {
					final List<Object> rates = (List<Object>) response.getResponseObj();
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();

					for (final Object rate : rates) {
						final WaterRateMaster rateMaster = (WaterRateMaster) rate;
						rateMaster.setOrgId(orgId);
						rateMaster.setServiceCode(WaterServiceShortCode.WATER_DISCONNECTION);
						rateMaster.setDeptCode(MainetConstants.WATER_DEPT);
						rateMaster.setDisConnectionType(CommonMasterUtility.getCPDDescription(
								disconnectionEntity.getDiscType(), PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
						if (CollectionUtils.isNotEmpty(rateMaster.getDependsOnFactorList())) {
							for (String dependFactor : rateMaster.getDependsOnFactorList()) {
								if (StringUtils.equalsIgnoreCase(dependFactor, "TAP")) {
									if (master.getCsTaxPayerFlag() != null)
										rateMaster.setTaxPayer(master.getCsTaxPayerFlag());
								}

								if (StringUtils.equalsIgnoreCase(dependFactor, "BPL")) {
									rateMaster.setIsBPL(getApplicantDetailDto().getIsBPL());
								}

								if (StringUtils.equalsIgnoreCase(dependFactor, "CON")) {
									if (connectionInfo.getCsCcnsize() != null) {
										rateMaster.setConnectionSize(Double.valueOf(CommonMasterUtility
												.getNonHierarchicalLookUpObject(
														connectionInfo.getCsCcnsize().longValue(), lookupOrg)
												.getDescLangFirst()));
									}
								}

								if (StringUtils.equalsIgnoreCase(dependFactor, "WTC")) {
									if (null != connectionInfo.getTrmGroup1()) {
										rateMaster.setUsageSubtype1(CommonMasterUtility
												.getHierarchicalLookUp(connectionInfo.getTrmGroup1(), orgId)
												.getDescLangFirst());
									}
									if (null != connectionInfo.getTrmGroup2()) {
										rateMaster.setUsageSubtype2(CommonMasterUtility
												.getHierarchicalLookUp(connectionInfo.getTrmGroup2(), orgId)
												.getDescLangFirst());
									}
									if (null != connectionInfo.getTrmGroup3()) {
										rateMaster.setUsageSubtype3(CommonMasterUtility
												.getHierarchicalLookUp(connectionInfo.getTrmGroup3(), orgId)
												.getDescLangFirst());
									}
									if (null != connectionInfo.getTrmGroup4()) {
										rateMaster.setUsageSubtype4(CommonMasterUtility
												.getHierarchicalLookUp(connectionInfo.getTrmGroup4(), orgId)
												.getDescLangFirst());
									}
									if (null != connectionInfo.getTrmGroup5()) {
										rateMaster.setUsageSubtype5(CommonMasterUtility
												.getHierarchicalLookUp(connectionInfo.getTrmGroup5(), orgId)
												.getDescLangFirst());
									}
								}

							}
						}

						rateMaster.setRateStartDate(new Date().getTime());

						if (connectionInfo.getCsMeteredccn() != null) {
							rateMaster.setMeterType(CommonMasterUtility
									.getNonHierarchicalLookUpObject(connectionInfo.getCsMeteredccn(), lookupOrg)
									.getDescLangFirst());
						}

						requiredCHarges.add(rateMaster);
					}

					WSRequestDTO chargeReqDto = new WSRequestDTO();
					chargeReqDto.setDataModel(requiredCHarges);
					WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
					setChargesInfo((List<ChargeDetailDTO>) applicableCharges.getResponseObj());
					setAmountToPay(checklistAndChageService
							.chargesToPay((List<ChargeDetailDTO>) applicableCharges.getResponseObj()));
					if (getAmountToPay() == 0.0d) {
						throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
								+ " if service configured as Chageable");
					}
					getOfflineDTO().setAmountToShow(getAmountToPay());
					setPayable(true);
				}

			} else {

				throw new FrameworkException(CHARGE_ERROR);
			}
		}
		}
	}

	private List<DocumentDetailsVO> mapCheckList(final List<DocumentDetailsVO> docs,
			final BindingResult bindingResult) {

		final List<DocumentDetailsVO> docList = fileUploadService.prepareFileUpload(docs);

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

	@Override
	public String getConnectioNo(final long applicationId, final long serviceid) {

		final TbKCsmrInfoMH master = disconnectionService.getAppDetailsAppliedForDisConnection(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		return master.getCsCcn() + MainetConstants.operator.COMMA + master.getPlumId();
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public TBWaterDisconnectionDTO getDisconnectionEntity() {
		return disconnectionEntity;
	}

	public void setDisconnectionEntity(final TBWaterDisconnectionDTO disconnectionEntity) {
		this.disconnectionEntity = disconnectionEntity;
	}

	/**
	 * @return the documentList
	 */
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList
	 *            the documentList to set
	 */
	public void setDocumentList(final List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public List<CustomerInfoDTO> getConnectionList() {
		return connectionList;
	}

	public void setConnectionList(final List<CustomerInfoDTO> connectionList) {
		this.connectionList = connectionList;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(final String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public WaterDisconnectionService getDisconnectionService() {
		return disconnectionService;
	}

	public void setDisconnectionService(final WaterDisconnectionService disconnectionService) {
		this.disconnectionService = disconnectionService;
	}

	public String getUlbPlumber() {
		return ulbPlumber;
	}

	public void setUlbPlumber(final String ulbPlumber) {
		this.ulbPlumber = ulbPlumber;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(final String consumerName) {
		this.consumerName = consumerName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(final String areaName) {
		this.areaName = areaName;
	}

	public String getPlumberLicence() {
		return plumberLicence;
	}

	public void setPlumberLicence(final String plumberLicence) {
		this.plumberLicence = plumberLicence;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(final List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public boolean isPayable() {
		return payable;
	}

	public void setPayable(final boolean payable) {
		this.payable = payable;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(final double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	@Override
	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	@Override
	public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public List<PlumberMaster> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberMaster> plumberList) {
		this.plumberList = plumberList;
	}

	public List<PlumberMaster> getUlbPlumberList() {
		return ulbPlumberList;
	}

	public void setUlbPlumberList(List<PlumberMaster> ulbPlumberList) {
		this.ulbPlumberList = ulbPlumberList;
	}

	public WaterApplicationAcknowledgementDTO getWaterApplicationAcknowledgementDTO() {
		return waterApplicationAcknowledgementDTO;
	}

	public void setWaterApplicationAcknowledgementDTO(
			WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO) {
		this.waterApplicationAcknowledgementDTO = waterApplicationAcknowledgementDTO;
	}

	public String getCsOldCcn() {
		return csOldCcn;
	}

	public void setCsOldCcn(String csOldCcn) {
		this.csOldCcn = csOldCcn;
	}

	public String getCheckerror() {
		return checkerror;
	}

	public void setCheckerror(String checkerror) {
		this.checkerror = checkerror;
	}

	
}
