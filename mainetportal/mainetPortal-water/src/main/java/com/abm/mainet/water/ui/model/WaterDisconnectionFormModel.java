package com.abm.mainet.water.ui.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
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
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.IDisconnectWaterConnectionService;
import com.abm.mainet.water.service.IWaterBRMSService;
import com.abm.mainet.water.ui.validator.WaterDisconnectionValidator;

@Component
@Scope("session")
public class WaterDisconnectionFormModel extends AbstractFormModel implements Serializable {

	private static final long serialVersionUID = -1328715626050585603L;
	private static final String CHARGE_ERROR = "Problem while checking Application level charges applicable or not.";

	@Resource
	private IPortalServiceMasterService iPortalService;

	@Autowired
	private IWaterBRMSService checklistAndChageService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private IDisconnectWaterConnectionService iDisconnectWaterConnectionService;

	private static Logger log = Logger.getLogger(WaterDisconnectionFormModel.class);

	private TBWaterDisconnectionDTO disconnectionEntity = new TBWaterDisconnectionDTO();
	private List<CustomerInfoDTO> connectionList = new ArrayList<>();
	
	public List<PlumberDTO> plumberList = new ArrayList<>();
	private String connectionNo;
	private String ulbPlumber;
	private String areaName;
	private String consumerName;
	private String plumberLicence;

	private List<DocumentDetailsVO> checkList;
	private PaymentRequestDTO payURequestDTO = new PaymentRequestDTO();
	private Long applicationId;
	private Long deptId;

	// to identify whether service is free or not
	private boolean payable;
	private boolean enableSubmit;
	private double amountToPay;
	private boolean resultFound;
	private double actualAmount;
	private boolean isFree;

	private WaterDeconnectionRequestDTO waterRequestDto = new WaterDeconnectionRequestDTO();
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
	/**
	 * @return the customerInfoDTO
	 */
	public CustomerInfoDTO getCustomerInfoDTO() {
		return customerInfoDTO;
	}

	/**
	 * @param customerInfoDTO the customerInfoDTO to set
	 */
	public void setCustomerInfoDTO(CustomerInfoDTO customerInfoDTO) {
		this.customerInfoDTO = customerInfoDTO;
	}

	/**
	 * @return the waterRequestDto
	 */
	public WaterDeconnectionRequestDTO getWaterRequestDto() {
		return waterRequestDto;
	}

	/**
	 * @param waterRequestDto the waterRequestDto to set
	 */
	public void setWaterRequestDto(WaterDeconnectionRequestDTO waterRequestDto) {
		this.waterRequestDto = waterRequestDto;
	}

	@Override
	public boolean saveForm() {

		boolean result = false;

		validateApplicantDetail();
		validateConnectionDetail();
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		CommonChallanDTO offline = getOfflineDTO();
		if (getAmountToPay() > 0) {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}
		if ((null != ulbPlumber) && ulbPlumber.equals(MainetConstants.NO)) {
			if ((null == plumberLicence) || plumberLicence.isEmpty()) {
				addValidationError(getAppSession().getMessage("water.enter.plumberLicence"));
			}

			if (hasValidationErrors()) {
				return result;
			}

			{
				final Object response = iDisconnectWaterConnectionService.validatePlumbetValidity(plumberLicence);
				Long outsideUlbPlumber = null;
				if (response != null) {
					outsideUlbPlumber = Long.valueOf(response.toString());
				}
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


			for (final CustomerInfoDTO tbKCsmrInfoMH : connectionList) {
				if ((tbKCsmrInfoMH.getCsCcn() != null)
						&& tbKCsmrInfoMH.getCsCcn().equalsIgnoreCase(getConnectionNo())) {
					getDisconnectionEntity().setCsIdn(tbKCsmrInfoMH.getCsIdn());
					break;
				}
			}

			getDisconnectionEntity().setDiscMethod(
					CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APP,
							MainetConstants.NewWaterServiceConstants.CAN).getLookUpId());
			waterRequestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			waterRequestDto.setApplicantDetailsDto(getApplicantDetailDto());
			waterRequestDto.setDisconnectionDto(getDisconnectionEntity());
			waterRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			getApplicantDetailDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			waterRequestDto.setServiceId(getServiceId());
			waterRequestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			waterRequestDto
					.setUploadDocument(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
			waterRequestDto.setDeptId(deptId);
			waterRequestDto.setFreeService(isFree());
			waterRequestDto.setConnectionNo(getConnectionNo());

			final WaterDisconnectionResponseDTO waterResponseDto = iDisconnectWaterConnectionService
					.saveOrUpdateDisconnection(waterRequestDto);
			
			if (waterResponseDto.getStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				setApplicationId(waterResponseDto.getApplicationNo());
				final ApplicationPortalMaster applicationMaster = saveApplcationMaster(getServiceId(), applicationId,
						FileUploadUtility.getCurrent().getFileMap().entrySet().size());
				setSuccessMessage(getAppSession().getMessage("water.disconnect.success",
						new Object[] { applicationId.toString() }));
				if(getAmountToPay() > 0d) {
					iPortalService.saveApplicationMaster(applicationMaster, amountToPay,
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					offline.setDocumentUploaded(!FileUploadUtility.getCurrent().getFileMap().isEmpty());
					final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
					offline.setOfflinePaymentText(modeDesc);
					offline.setApplNo(applicationId);
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
					final PortalService portalServiceMaster = iPortalService.getService(getServiceId(),
							UserSession.getCurrent().getOrganisation().getOrgid());
					offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
					offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
							offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
					if ((offline.getOnlineOfflineCheck() != null)
							&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
						offline = iChallanService.generateChallanNumber(offline);
						setOfflineDTO(offline);
					}
					
					}
				
			if(null!=waterRequestDto.getTenant() && null!=waterResponseDto.getApplicationNo()){
				try{
				logger.error("Aaple Sarkar: " + waterRequestDto.getTenant() + "ApplicationId:" + waterResponseDto.getApplicationNo());
				String request= waterRequestDto.getTenant();
				
				logger.error("Before Updating Status: " + request);
				
				String replace = request.replace("appId", waterResponseDto.getApplicationNo().toString());
				
				String replace1= replace.replace("appStatus", MainetConstants.Common_Constant.NUMBER.THREE);
				
				String replace2= replace1.replace("remark", "Dept Scrutiny");
				
				logger.error("Final Request: " + replace2);
	
				EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
				
				encryptDecrypt.getUpdateStatus(replace2);
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			 } 
			result = true;
			}else {
				throw new RuntimeException("Exception found while saving Disconnection Details");
			}
		} catch (final Exception e) {
			log.error("Exception found while saving Disconnection Details : ", e);
		}
		return result;

	}

	public void validateApplicantDetail() {
		getApplicantDetailDto().setWardZoneValidFlag(MainetConstants.FlagN);
		validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
		if (getConnectionNo().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.connEmptyErr"));
			
		}
		/*
		 * if (getApplicantDetailDto().getAreaName().isEmpty()) {
		 * addValidationError(getAppSession().getMessage("water.changeowner.areaname"));
		 * } if (getApplicantDetailDto().getVillageTownSub().isEmpty()) {
		 * addValidationError(getAppSession().getMessage("water.changeowner.vtc")); }
		 */
	}

	public void validateConnectionDetail() {

		validateBean(this, WaterDisconnectionValidator.class);

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
			final CustomerInfoDTO connectionInfo = connectionList.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setDeptCode(PrefixConstants.WATERMODULEPREFIX.WT);
			checkListModel.setServiceCode(MainetConstants.ServiceCode.WATER_DISCONNECTION);
			checkListModel.setIsBPL(getApplicantDetailDto().getIsBPL());
			checkListModel.setDisConnectionType(CommonMasterUtility.getCPDDescription(disconnectionEntity.getDiscType(),
					MainetConstants.D2KFUNCTION.ENGLISH_DESC));
			/*
			 * checkListModel.setApplicantType(CommonMasterUtility.getCPDDescription(
			 * connectionInfo.getApplicantType(),
			 * MainetConstants.D2KFUNCTION.ENGLISH_DESC));
			 */
			if (null != connectionInfo.getTrmGroup1()) {
				checkListModel.setUsageSubtype1(
						CommonMasterUtility.getHierarchicalLookUp(connectionInfo.getTrmGroup1()).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup2()) {
				checkListModel.setUsageSubtype2(
						CommonMasterUtility.getHierarchicalLookUp(connectionInfo.getTrmGroup2()).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup3()) {
				checkListModel.setUsageSubtype3(
						CommonMasterUtility.getHierarchicalLookUp(connectionInfo.getTrmGroup3()).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup4()) {
				checkListModel.setUsageSubtype4(
						CommonMasterUtility.getHierarchicalLookUp(connectionInfo.getTrmGroup4()).getDescLangFirst());
			}
			if (null != connectionInfo.getTrmGroup5()) {
				checkListModel.setUsageSubtype5(
						CommonMasterUtility.getHierarchicalLookUp(connectionInfo.getTrmGroup5()).getDescLangFirst());
			}
			final List<DocumentDetailsVO> docList = iCommonBRMSService.getChecklist(checkListModel);
			long cnt = 1;
			for (final DocumentDetailsVO doc : docList) {
				doc.setDocumentSerialNo(cnt);
				cnt++;
			}
			setCheckList(docList);

			response = checklistAndChageService.getApplicableTaxes(waterRateMaster, orgId,
					MainetConstants.ServiceCode.WATER_DISCONNECTION);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				if (response.isFree()) {
					setFree(true);
					setPayable(false);
					setAmountToPay(0.0d);

				} else {

					final List<?> rates = JersyCall.castResponse(response, WaterRateMaster.class);
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();

					for (final Object rate : rates) {
						final WaterRateMaster rateMaster = (WaterRateMaster) rate;
						rateMaster.setOrgId(orgId);
						rateMaster.setServiceCode(MainetConstants.ServiceCode.WATER_DISCONNECTION);
						rateMaster.setDeptCode(PrefixConstants.WATERMODULEPREFIX.WT);
						rateMaster.setIsBPL(getApplicantDetailDto().getIsBPL());
						rateMaster.setDisConnectionType(CommonMasterUtility.getCPDDescription(
								disconnectionEntity.getDiscType(), MainetConstants.D2KFUNCTION.ENGLISH_DESC));
						if (null != connectionInfo.getTrmGroup1()) {
							rateMaster.setUsageSubtype1(CommonMasterUtility
									.getHierarchicalLookUp(connectionInfo.getTrmGroup1()).getDescLangFirst());
						}
						if (null != connectionInfo.getTrmGroup2()) {
							rateMaster.setUsageSubtype2(CommonMasterUtility
									.getHierarchicalLookUp(connectionInfo.getTrmGroup2()).getDescLangFirst());
						}
						if (null != connectionInfo.getTrmGroup3()) {
							rateMaster.setUsageSubtype3(CommonMasterUtility
									.getHierarchicalLookUp(connectionInfo.getTrmGroup3()).getDescLangFirst());
						}
						if (null != connectionInfo.getTrmGroup4()) {
							rateMaster.setUsageSubtype4(CommonMasterUtility
									.getHierarchicalLookUp(connectionInfo.getTrmGroup4()).getDescLangFirst());
						}
						if (null != connectionInfo.getTrmGroup5()) {
							rateMaster.setUsageSubtype5(CommonMasterUtility
									.getHierarchicalLookUp(connectionInfo.getTrmGroup5()).getDescLangFirst());
						}
						/*
						 * rateMaster.setTaxSubCategory(getTaxSubCategoryDesc(rateMaster.
						 * getTaxSubCategory(), UserSession.getCurrent().getOrganisation()));
						 */
						rateMaster.setRateStartDate(new Date().getTime());
						if (connectionInfo.getCsCcnsize() != null) {
							rateMaster.setConnectionSize(Double.valueOf(CommonMasterUtility
									.getNonHierarchicalLookUpObject(connectionInfo.getCsCcnsize().longValue())
									.getDescLangFirst()));
						}
						if (connectionInfo.getCsMeteredccn() != null) {
							rateMaster.setMeterType(
									CommonMasterUtility.getNonHierarchicalLookUpObject(connectionInfo.getCsMeteredccn())
											.getDescLangFirst());
						}
						requiredCHarges.add(rateMaster);
					}

					final List<ChargeDetailDTO> charges = checklistAndChageService
							.getApplicableCharges(requiredCHarges);
					setChargesInfo(charges);
					setAmountToPay(checklistAndChageService.chargesToPay(charges));
					getOfflineDTO().setAmountToShow(getAmountToPay());
					setPayable(true);
					setFree(false);
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

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		String userName = (getApplicantDetailDto().getApplicantFirstName() == null ? " "
				: getApplicantDetailDto().getApplicantFirstName().trim()) + MainetConstants.WHITE_SPACE;
		userName += getApplicantDetailDto().getApplicantMiddleName() == null ? " "
				: getApplicantDetailDto().getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += getApplicantDetailDto().getApplicantLastName() == null ? " "
				: getApplicantDetailDto().getApplicantLastName().trim();
		payURequestDTO.setServiceId(getServiceId());
		payURequestDTO.setApplicantName(userName);
		setOwnerName(userName.trim());
		payURequestDTO.setMobNo(getApplicantDetailDto().getMobileNo());
		setApmApplicationId(getApplicationId());

		final PortalService portalServiceMaster = iPortalService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		setServiceId(portalServiceMaster.getServiceId());

		payURequestDTO.setUdf1(getServiceId().toString());
		payURequestDTO.setUdf2(getApplicationId().toString());
		payURequestDTO.setUdf3("CitizenHome.html");

		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf6(String.valueOf(UserSession.getCurrent().getEmployee().getEmpId()));
		payURequestDTO.setUdf7(getApplicationId().toString());
		payURequestDTO.setDueAmt(new BigDecimal(getAmountToPay()));
		payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
		payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());

		payURequestDTO.setApplicationId(getApplicationId().toString());
		setPayURequestDTO(payURequestDTO);

		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}

	}

	private ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
			final int documentListSize) throws Exception {

		iPortalService.getService(serviceId, UserSession.getCurrent().getOrganisation().getOrgid());

		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();

		applicationMaster.setPamApplicationId(applicationNo);
		applicationMaster.setSmServiceId(serviceId);
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.updateAuditFields();
		return applicationMaster;

	}

	public TBWaterDisconnectionDTO getDisconnectionEntity() {

		return disconnectionEntity;
	}

	public void setDisconnectionEntity(final TBWaterDisconnectionDTO disconnectionEntity) {
		this.disconnectionEntity = disconnectionEntity;
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

	public PaymentRequestDTO getPayURequestDTO() {
		return payURequestDTO;
	}

	public void setPayURequestDTO(final PaymentRequestDTO payURequestDTO) {
		this.payURequestDTO = payURequestDTO;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getUlbPlumber() {
		return ulbPlumber;
	}

	public void setUlbPlumber(final String ulbPlumber) {
		this.ulbPlumber = ulbPlumber;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(final String areaName) {
		this.areaName = areaName;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(final String consumerName) {
		this.consumerName = consumerName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public String getPlumberLicence() {
		return plumberLicence;
	}

	public void setPlumberLicence(final String plumberLicence) {
		this.plumberLicence = plumberLicence;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public boolean isResultFound() {
		return resultFound;
	}

	public void setResultFound(final boolean resultFound) {
		this.resultFound = resultFound;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(final double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public List<PlumberDTO> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberDTO> plumberList) {
		this.plumberList = plumberList;
	}

	
	
}
