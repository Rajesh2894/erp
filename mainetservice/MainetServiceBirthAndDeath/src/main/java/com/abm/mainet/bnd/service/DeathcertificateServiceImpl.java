package com.abm.mainet.bnd.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bnd.constant.BndConstants;

import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.domain.BirthCertificateEntity;
import com.abm.mainet.bnd.domain.DeathCertEntity;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.dto.ViewBirthCertiDetailRequestDto;
import com.abm.mainet.bnd.dto.ViewDeathCertiDetailRequestDto;
import com.abm.mainet.bnd.repository.DeathCertificateRepository;
import com.abm.mainet.bnd.ui.model.DeathCertificateModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

@Service("bndDeathcertificateService")
@WebService(endpointInterface = "com.abm.mainet.bnd.service.IDeathCertificateServices")
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Api(value = "/deathCertificateApply")
@Path(value = "/deathCertificateApply")
public class DeathcertificateServiceImpl implements IDeathCertificateServices{
	
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private static final Logger LOGGER = LoggerFactory.getLogger(DeathcertificateServiceImpl.class);
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private TbTaxMasService taxMasService;
	
	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Resource
	private DeathCertificateRepository deathCertificateRepositorys;
	
	@Resource
	private ApplicationService applicationService;

	@Resource
	private IFileUploadService fileUploadService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private IChallanService challanService;
	
	@Autowired
	private IOrganisationDAO organisationDAO;
	
	@Autowired
	TbDepartmentService tbDepartmentService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeathCertificateDTO saveDeathCertificate(DeathCertificateDTO deathCertificateDTO, DeathCertificateModel tbDeathregModel) {
		final RequestDTO requestDTO = tbDeathregModel.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RDC, deathCertificateDTO.getOrgId());
		DeathCertEntity deathCertificateEntity= new DeathCertEntity();
		
		// RequestDTO entry
				requestDTO.setOrgId(deathCertificateDTO.getOrgId());
				requestDTO.setServiceId(serviceMas.getSmServiceId());
				requestDTO.setApmMode(BndConstants.FLAGF);
				  if(serviceMas.getSmFeesSchedule()==0)
					{
					  requestDTO.setPayStatus(BndConstants.PAYSTSTUSFREE);
					}else {
						requestDTO.setPayStatus(BndConstants.PAYSTATUSCHARGE);
					}
				requestDTO.setUserId(deathCertificateDTO.getUserId());
				requestDTO.setfName(requestDTO.getfName());
				// Generate the Application Number #116427 By Bhagyashri
				requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,
						PrefixConstants.STATUS_ACTIVE_PREFIX));
				requestDTO.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
				requestDTO.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
				LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String monthStr = localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue()
						: String.valueOf(localDate.getMonthValue());
				String dayStr = localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth()
						: String.valueOf(localDate.getDayOfMonth());
				requestDTO.setCustomField(String.valueOf(monthStr + "" + dayStr));
				final Long applicationId = applicationService.createApplication(requestDTO);
				if (null == applicationId) {
					throw new FrameworkException("Application Not Generated");
				}
				requestDTO.setReferenceId(String.valueOf(applicationId));
				requestDTO.setApplicationId(applicationId);
				if ((applicationId != null) && (applicationId != 0)) {
					deathCertificateDTO.setApplnId(applicationId);
					deathCertificateDTO.setApplicationNo(applicationId);
				}
				BeanUtils.copyProperties(deathCertificateDTO, deathCertificateEntity);
				deathCertificateEntity.setDrStatus(BndConstants.STATUSOPEN);
				deathCertificateEntity.setApplicationNo(deathCertificateDTO.getApplicationNo());
				deathCertificateEntity.setSmServiceId(serviceMas.getSmServiceId());
				deathCertificateRepositorys.save(deathCertificateEntity);
				
				if ((deathCertificateDTO.getDocumentList() != null) && !deathCertificateDTO.getDocumentList().isEmpty()) {
					 fileUploadService.doFileUpload(deathCertificateDTO.getDocumentList(), requestDTO);
				}
				
				deathCertificateDTO.setApplicantMobilno(requestDTO.getMobileNo());
				deathCertificateDTO.setApplicantEmail(requestDTO.getEmail());
				deathCertificateDTO.setApplicantFname(requestDTO.getfName());
				deathCertificateDTO.setApplicantAddress(requestDTO.getCityName());
				
				if(serviceMas.getSmFeesSchedule()==0) {
					//initializeWorkFlowForFreeService
				 deathCertificateDTO.setSmServiceId(serviceMas.getSmServiceId());
				 initializeWorkFlowForFreeService(deathCertificateDTO);
				}else {
					//workflow for paid service with charges and challan
					setAndSaveChallanDtoOffLine(tbDeathregModel.getOfflineDTO(),tbDeathregModel);
				}
				//smsAnd EmailService
				Organisation organisation = UserSession.getCurrent().getOrganisation();
				smsAndEmail(deathCertificateDTO,organisation);
		return deathCertificateDTO;
	}


	
	@Transactional(rollbackFor = Exception.class)
	private void initializeWorkFlowForFreeService(DeathCertificateDTO requestDto) {
		boolean checkList = false;
		
		if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
			checkList = true;
		}
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();

		applicantDto.setApplicantFirstName(requestDto.getApplicantFname());
		applicantDto.setServiceId(requestDto.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(BndConstants.RTS));
		applicantDto.setMobileNo(requestDto.getApplicantMobilno());
		applicantDto.setUserId(requestDto.getUserId());

		applicationMetaData.setApplicationId(requestDto.getApplnId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathCertificateModel tbDeathregModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RDC, tbDeathregModel.getDeathCertificateDTO().getOrgId());
		offline.setApplNo(tbDeathregModel.getDeathCertificateDTO().getApplicationNo());
		offline.setAmountToPay(String.valueOf(tbDeathregModel.getDeathCertificateDTO().getAmount()));
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ",
				Arrays.asList(tbDeathregModel.getRequestDTO().getfName(),tbDeathregModel.getRequestDTO().getmName(), tbDeathregModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		offline.setMobileNumber(tbDeathregModel.getDeathCertificateDTO().getApplicantMobilno());
		offline.setEmailId(tbDeathregModel.getDeathCertificateDTO().getApplicantEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		if(tbDeathregModel.getRequestDTO().getAssWard1()!= null) {
			offline.getDwzDTO().setAreaDivision1(tbDeathregModel.getRequestDTO().getAssWard1());
			}
		String applicantAddress = String.join(" ", Arrays.asList(tbDeathregModel.getRequestDTO().getBldgName(),
				tbDeathregModel.getRequestDTO().getBlockName(), tbDeathregModel.getRequestDTO().getRoadName(), tbDeathregModel.getRequestDTO().getCityName()));
		offline.setApplicantAddress(applicantAddress);
		if(tbDeathregModel.getChargesInfo()!=null) { 
			for (ChargeDetailDTO dto : tbDeathregModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(tbDeathregModel.getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setDeptCode(tbDepartmentService.findDepartmentShortCodeByDeptId(offline.getDeptId(), offline.getOrgId()));
		
		 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_ASCL) && StringUtils.isNotEmpty(offline.getDeptCode()) && ( offline.getDeptCode().equals("BND"))) {
				offline.setLoggedLocId(tbDeathregModel.getRequestDTO().getWardNo());
				}
		offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			tbDeathregModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			tbDeathregModel.setReceiptDTO(printDto);
			tbDeathregModel.setSuccessMessage(tbDeathregModel.getAppSession().getMessage("adh.receipt"));
		}		
	}


	@Override
	@POST
	@Path(value = "/getApplicableTaxes")
	@Transactional(rollbackFor = Exception.class)
	public WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms RTS getApplicableTaxes execution start..");
		try {
			if (requestDTO.getDataModel() == null) {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
			} else {
				BndRateMaster BndRateMaster = (BndRateMaster) CommonMasterUtility
						.castRequestToDataModel(requestDTO, BndRateMaster.class);
				  validateDataModel(BndRateMaster, responseDTO);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					responseDTO = populateOtherFieldsForServiceCharge(BndRateMaster, responseDTO);
				}
			}
		} catch (CloneNotSupportedException | FrameworkException ex) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
		}
		LOGGER.info("brms RTS getApplicableTaxes execution end..");
		
		return responseDTO;
	}
	
	private WSResponseDTO validateDataModel(BndRateMaster bndRateMaster, WSResponseDTO responseDTO) {
		LOGGER.info("validateDataModel execution start..");
		StringBuilder builder = new StringBuilder();
		if (bndRateMaster.getServiceCode() == null || bndRateMaster.getServiceCode().isEmpty()) {
			builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
		}
		if (bndRateMaster.getOrgId() == 0l) {
			builder.append(ORG_ID_CANT_BE_ZERO).append(",");
		}
		if (bndRateMaster.getChargeApplicableAt() == null
				|| bndRateMaster.getChargeApplicableAt().isEmpty()) {
			builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
		} else if (!StringUtils.isNumeric(bndRateMaster.getChargeApplicableAt())) {
			builder.append(CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC);
		}
		if (builder.toString().isEmpty()) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		} else {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(builder.toString());
		}

		return responseDTO;
	}
	
	
	private WSResponseDTO populateOtherFieldsForServiceCharge(@RequestBody BndRateMaster bndRateMaster,
			WSResponseDTO responseDTO) throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<BndRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(bndRateMaster.getServiceCode(),
				bndRateMaster.getOrgId());
		if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), bndRateMaster.getOrgId(),
					Long.parseLong(bndRateMaster.getChargeApplicableAt()));
			Organisation organisation = new Organisation();
			organisation.setOrgid(bndRateMaster.getOrgId());
			listOfCharges = settingAllFields(applicableCharges, bndRateMaster, organisation);
			responseDTO.setResponseObj(listOfCharges);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		} else {
			responseDTO.setFree(true);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		}
		LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
		return responseDTO;
	}
	
	private List<BndRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges,
			BndRateMaster bndRateMaster, Organisation organisation) throws CloneNotSupportedException {
		LOGGER.info("settingAllFields execution start..");
		List<BndRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			BndRateMaster BNDRateMasters = (BndRateMaster) bndRateMaster.clone();
			// SLD for dependsOnFactor
			String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
					bndRateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
			String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
					entity.getOrgid(), entity.getTaxApplicable());
			BNDRateMasters.setTaxType(taxType);
			BNDRateMasters.setTaxCode(entity.getTaxCode());
			BNDRateMasters.setChargeApplicableAt(chargeApplicableAt);
			settingTaxCategories(BNDRateMasters, entity, organisation);
			list.add(BNDRateMasters);
		}
		LOGGER.info("settingAllFields execution end..");
		return list;
	}
	private BndRateMaster settingTaxCategories(BndRateMaster bndRateMaster,
			TbTaxMasEntity enity, Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				bndRateMaster.setTaxCategory(lookUp.getDescLangFirst().trim());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				bndRateMaster.setTaxSubCategory(lookUp.getDescLangFirst().trim());
				break;
			}

		}
		return bndRateMaster;

	}
	
	@Override
	@POST
	@Path(value = "/getDeathCertificateCharges")
	@Transactional(rollbackFor = Exception.class)
	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO) {
		LOGGER.info("brms RTS getcertificateCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(wSRequestDTO,ServiceEndpoints.BRMSMappingURL.BND_RATE_MASTER_URL);

			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				return responseDTO;
			} 
		} catch (Exception ex) {
			throw new FrameworkException("UNBALE TO REQUEST FOR BND RATE CHARGES", ex);
		}
		LOGGER.info("brms RTS getcertificateCharges execution End..");
		
	return null;
	}

	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(DeathCertificateDTO deathCertificateDTO,Organisation organisation)
	{
		SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setAppNo(String.valueOf(deathCertificateDTO.getApplicationNo()));
		dto.setMobnumber(deathCertificateDTO.getApplicantMobilno());
		dto.setEmail(deathCertificateDTO.getApplicantEmail());
		dto.setAppDate(Utility.dateToString(deathCertificateDTO.getDrCertiGen()));
		dto.setUserId(deathCertificateDTO.getUserId());
		String fullName = String.join(" ", Arrays.asList(deathCertificateDTO.getRequestDTO().getfName(),
				deathCertificateDTO.getRequestDTO().getmName(), deathCertificateDTO.getRequestDTO().getlName()));
		dto.setAppName(fullName);
		dto.setAmount(deathCertificateDTO.getAmount());
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, BndConstants.APPLYFORDEATHCERTIFICATE,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, deathCertificateDTO.getLangId());

		} else {
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, BndConstants.DEATHAPPROVAL_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG,
					dto, organisation, deathCertificateDTO.getLangId());

		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	@POST
	@Path(value = "/saveDeathCertificateDeails")
	public DeathCertificateDTO saveDeathCertificateDeails(DeathCertificateDTO deathCertificateDTO) {
		final RequestDTO requestDTO = deathCertificateDTO.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RDC, deathCertificateDTO.getOrgId());
		DeathCertEntity deathCertificateEntity= new DeathCertEntity();
		
		// RequestDTO entry
				requestDTO.setOrgId(deathCertificateDTO.getOrgId());
				requestDTO.setServiceId(serviceMas.getSmServiceId());
				requestDTO.setApmMode(BndConstants.FLAGF);
				  if(serviceMas.getSmFeesSchedule()==0)
					{
					  requestDTO.setPayStatus(BndConstants.PAYSTSTUSFREE);
					}else {
					  requestDTO.setPayStatus(BndConstants.PAYSTATUSCHARGE);
					}
				requestDTO.setUserId(deathCertificateDTO.getUserId());
				// Generate the Application Number #116427 By Bhagyashri
				requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,
						PrefixConstants.STATUS_ACTIVE_PREFIX));
				requestDTO.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
				requestDTO.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
				LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String monthStr = localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue()
						: String.valueOf(localDate.getMonthValue());
				String dayStr = localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth()
						: String.valueOf(localDate.getDayOfMonth());
				requestDTO.setCustomField(String.valueOf(monthStr + "" + dayStr));
				final Long applicationId = applicationService.createApplication(requestDTO);
				if (null == applicationId) {
					throw new FrameworkException("Application Not Generated");
				}
				requestDTO.setReferenceId(String.valueOf(applicationId));
				requestDTO.setApplicationId(applicationId);
				if ((applicationId != null) && (applicationId != 0)) {
					deathCertificateDTO.setApplnId(applicationId);
					deathCertificateDTO.setApplicationNo(applicationId);
				}
				BeanUtils.copyProperties(deathCertificateDTO, deathCertificateEntity);
				deathCertificateEntity.setDrStatus(BndConstants.STATUSOPEN);
				deathCertificateEntity.setApplicationNo(deathCertificateDTO.getApplicationNo());
				deathCertificateEntity.setSmServiceId(serviceMas.getSmServiceId());
				deathCertificateEntity.setLmoddate(new Date());
				deathCertificateRepositorys.save(deathCertificateEntity);
				List<TbTaxMasEntity> taxList =taxMasService.fetchAllApplicableServiceCharge(serviceMas.getSmServiceId(), deathCertificateDTO.getOrgId(), deathCertificateDTO.getChargeApplicableAt());
				if(!taxList.isEmpty())
				{
				deathCertificateDTO.getChargesInfo().get(0).setChargeCode(taxList.get(0).getTaxId());
				deathCertificateDTO.getChargesInfo().get(0).setTaxCode(taxList.get(0).getTaxCode());
				}
				if ((deathCertificateDTO.getDocumentList() != null) && !deathCertificateDTO.getDocumentList().isEmpty()) {
					fileUploadService.doFileUpload(deathCertificateDTO.getDocumentList(), requestDTO);
				}
				deathCertificateDTO.setApplicantMobilno(requestDTO.getMobileNo());
				deathCertificateDTO.setApplicantEmail(requestDTO.getEmail());
				deathCertificateDTO.setApplicantFname(requestDTO.getfName());
				deathCertificateDTO.setApplicantAddress(requestDTO.getCityName());
				deathCertificateDTO.setSmServiceId(serviceMas.getSmServiceId());
				if(serviceMas.getSmFeesSchedule()==0) {
					//initializeWorkFlowForFreeService
				 deathCertificateDTO.setSmServiceId(serviceMas.getSmServiceId());
				 initializeWorkFlowForFreeService(deathCertificateDTO);
				}else {
				
				setAndSaveChallanDtoOffLine(deathCertificateDTO.getOfflineDTO(),deathCertificateDTO);
				}
				//smsAnd EmailService
				Organisation organisation = organisationDAO.getOrganisationById(deathCertificateDTO.getOrgId(), MainetConstants.STATUS.ACTIVE);
				smsAndEmail(deathCertificateDTO,organisation);
		return deathCertificateDTO;
	}

	private void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathCertificateDTO deathCertificateDTO) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RDC, deathCertificateDTO.getOrgId());
		offline.setApplNo(deathCertificateDTO.getApplicationNo());
		offline.setAmountToPay(String.valueOf(deathCertificateDTO.getAmount()));
		offline.setOrgId(deathCertificateDTO.getOrgId());
		offline.setUserId(deathCertificateDTO.getUserId());
		offline.setLangId(deathCertificateDTO.getLangId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		offline.setApplicantName(deathCertificateDTO.getRequestDTO().getfName());
		offline.setMobileNumber(deathCertificateDTO.getRequestDTO().getMobileNo());
		offline.setEmailId(deathCertificateDTO.getRequestDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(deathCertificateDTO.getRequestDTO().getCityName());
				
		if(deathCertificateDTO.getChargesInfo()!=null) { 
			for (ChargeDetailDTO dto : deathCertificateDTO.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(deathCertificateDTO.getUploadDocument())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		//offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			deathCertificateDTO.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			deathCertificateDTO.setReceiptDTO(printDto);
			//tbDeathregModel.setSuccessMessage(tbDeathregModel.getAppSession().getMessage("adh.receipt"));
		}		
	}
	
	@Override
	@POST
	@Path("/searchDeathCertificate")
	@Transactional
	public DeathCertificateDTO getDeathCertificate(DeathCertificateDTO deathCertificateDto) {
		DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
		List<DeathCertEntity> deathCertificateEntityList = deathCertificateRepositorys.FinddeathCertDatabyApplicationNO(deathCertificateDto.getApplicationNo(),deathCertificateDto.getOrgId());
		BeanUtils.copyProperties(deathCertificateEntityList.get(0), deathCertificateDTO);
		return deathCertificateDTO;
	}
	
	@Override
	@POST
	@Path("/viewDeathCertificate")
	@Transactional
	public List<DeathCertificateDTO> viewDeathRegisteredAppliDetails(@RequestBody ViewDeathCertiDetailRequestDto deathViewRequestDto) {
		List<DeathCertificateDTO> deathCertificateDtoList = new ArrayList<>();
		List<String> attachList = new ArrayList<>();

		if (deathViewRequestDto.getDeathSearchDto().getApplicationNo() != null
				&& deathViewRequestDto.getDeathSearchDto().getDrDod() != null) {
			List<DeathCertEntity> deathCertificateEntities = deathCertificateRepositorys
					.findByApplicationNoAndDrDodAndOrgIdAndDrStatus(deathViewRequestDto.getDeathSearchDto().getApplicationNo(),
							deathViewRequestDto.getDeathSearchDto().getDrDod(),
							deathViewRequestDto.getDeathSearchDto().getOrgId(), MainetConstants.FlagA);
			if (deathCertificateEntities != null && deathCertificateEntities.size() > 0) {
				for (DeathCertEntity entity : deathCertificateEntities) {
					DeathCertificateDTO deathDto = new DeathCertificateDTO();
					BeanUtils.copyProperties(entity, deathDto);
					final List<CFCAttachment> attachDocs = iChecklistVerificationService
							.findAttachmentsForAppId(deathDto.getApplicationNo(), null, deathDto.getOrgId());
					if (attachDocs != null)
						deathDto.setUploadDocument(new ArrayList<>());
					List<DocumentDetailsVO> documentList = new ArrayList<>();
					DocumentDetailsVO doc = null;
					for (CFCAttachment attachDoc : attachDocs) {
						doc = new DocumentDetailsVO();
						doc.setDoc_DESC_ENGL(attachDoc.getAttFname());
						doc.setUploadedDocumentPath(attachDoc.getAttPath());
						doc.setAttachmentId(attachDoc.getAttId());
						doc.setClmAprStatus(attachDoc.getClmAprStatus());
						documentList.add(doc);
					}

					deathDto.setUploadDocument(documentList);

					deathCertificateDtoList.add(deathDto);
				}
			}

		} else if(deathViewRequestDto.getDeathSearchDto().getDrDod() != null) {
			List<DeathCertEntity> deathCertificateEntities = deathCertificateRepositorys.findByDrDodAndOrgIdAndDrStatus(
					deathViewRequestDto.getDeathSearchDto().getDrDod(), deathViewRequestDto.getDeathSearchDto().getOrgId(), MainetConstants.FlagA);
			if (deathCertificateEntities != null && deathCertificateEntities.size() > 0) {
				for (DeathCertEntity entity : deathCertificateEntities) {
					DeathCertificateDTO deathDto = new DeathCertificateDTO();
					BeanUtils.copyProperties(entity, deathDto);

					final List<CFCAttachment> attachDocs = iChecklistVerificationService
							.findAttachmentsForAppId(deathDto.getApplicationNo(), null, deathDto.getOrgId());
					if (attachDocs != null)
						deathDto.setUploadDocument(new ArrayList<>());
					List<DocumentDetailsVO> documentList = new ArrayList<>();
					DocumentDetailsVO doc = null;
					for (CFCAttachment attachDoc : attachDocs) {
						doc = new DocumentDetailsVO();
						doc.setDoc_DESC_ENGL(attachDoc.getAttFname());
						doc.setUploadedDocumentPath(attachDoc.getAttPath());
						doc.setAttachmentId(attachDoc.getAttId());
						doc.setClmAprStatus(attachDoc.getClmAprStatus());
						documentList.add(doc);
					}

					deathDto.setUploadDocument(documentList);
					deathCertificateDtoList.add(deathDto);
				}
			}


		}else {
			DeathCertEntity DetailEntity = deathCertificateRepositorys.findByApplicationNoAndOrgIdAndDrStatus(
					deathViewRequestDto.getDeathSearchDto().getApplicationNo(),
					deathViewRequestDto.getDeathSearchDto().getOrgId(), MainetConstants.FlagA);
			DeathCertificateDTO deathDto = new DeathCertificateDTO();
			BeanUtils.copyProperties(DetailEntity, deathDto);
			final List<CFCAttachment> attachDocs = iChecklistVerificationService
					.findAttachmentsForAppId(deathDto.getApplicationNo(), null, deathDto.getOrgId());
			if (attachDocs != null)
				deathDto.setUploadDocument(new ArrayList<>());
			List<DocumentDetailsVO> documentList = new ArrayList<>();
			DocumentDetailsVO doc = null;
			for (CFCAttachment attachDoc : attachDocs) {
				doc = new DocumentDetailsVO();
				doc.setDoc_DESC_ENGL(attachDoc.getAttFname());
				doc.setUploadedDocumentPath(attachDoc.getAttPath());
				doc.setAttachmentId(attachDoc.getAttId());
				doc.setClmAprStatus(attachDoc.getClmAprStatus());
				documentList.add(doc);
			}

			deathDto.setUploadDocument(documentList);

			deathCertificateDtoList.add(deathDto);
		}
		return deathCertificateDtoList;

	}
}
	

