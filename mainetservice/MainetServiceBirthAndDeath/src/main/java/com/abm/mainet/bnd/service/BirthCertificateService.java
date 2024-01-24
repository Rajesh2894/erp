package com.abm.mainet.bnd.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.domain.BirthCertificateEntity;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.ViewBirthCertiDetailRequestDto;
import com.abm.mainet.bnd.repository.BirthCertificateRepository;
import com.abm.mainet.bnd.ui.model.BirthCertificateModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
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
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@WebService(endpointInterface = "com.abm.mainet.bnd.service.IBirthCertificateService")
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Api(value = "/birthCertificateService")
@Path("/birthCertificateService")
@Service("ApplyBirthCertificateService")
public class BirthCertificateService implements IBirthCertificateService {

	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private static final Logger LOGGER = LoggerFactory.getLogger(BirthCertificateService.class);
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";

	@Autowired
	@Qualifier("bndBirthCertificate")
	BirthCertificateRepository birthCerRepro;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Autowired
	private IOrganisationDAO organisationDAO;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private GroupMasterService groupMasterService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private TbTaxMasService taxMasService;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	
	@Autowired
	private ServiceMasterService serviceMaster;
	
	@Autowired
	TbDepartmentService tbDepartmentService;
	
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Override
	@POST
	@Path("/saveBirthCertificate")
	@Transactional
	public BirthCertificateDTO saveBirthCertificate(BirthCertificateDTO birthCertificateDto,
			BirthCertificateModel model) {
		BirthCertificateEntity birthcer = new BirthCertificateEntity();
		final RequestDTO commonRequest = model.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RBC, birthCertificateDto.getOrgId());

		commonRequest.setOrgId(birthCertificateDto.getOrgId());
		commonRequest.setApmMode(BndConstants.FLAGF);
		if (serviceMas.getSmFeesSchedule() == 0) {
			commonRequest.setPayStatus(BndConstants.PAYSTSTUSFREE);
		} else {
			commonRequest.setPayStatus(BndConstants.PAYSTATUSCHARGE);
		}
		commonRequest.setUserId(birthCertificateDto.getUserId());
		// Generate the Application Number #116427 By Bhagyashri
		commonRequest.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,
				PrefixConstants.STATUS_ACTIVE_PREFIX));
		commonRequest.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
		commonRequest.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String monthStr = localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue()
				: String.valueOf(localDate.getMonthValue());
		String dayStr = localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth()
				: String.valueOf(localDate.getDayOfMonth());
		commonRequest.setCustomField(String.valueOf(monthStr + "" + dayStr));
		final Long applicationId = applicationService.createApplication(commonRequest);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		commonRequest.setApplicationId(applicationId);
		if (null == applicationId) {
			throw new FrameworkException("Application Not Generated");
		}
		if ((birthCertificateDto.getUploadDocument() != null) && !birthCertificateDto.getUploadDocument().isEmpty()) {
			fileUploadService.doFileUpload(birthCertificateDto.getUploadDocument(), commonRequest);
		}
		birthCertificateDto.setApplicationId(applicationId.toString());
		if ((applicationId != null) && (applicationId != 0)) {
			birthCertificateDto.setApmApplicationId(applicationId);
		}
		birthCertificateDto.setApplicantEmail(commonRequest.getEmail());
		birthCertificateDto.setApplicantMobilno(commonRequest.getMobileNo());
		birthCertificateDto.setApplicantFname(commonRequest.getfName());
		birthCertificateDto.setApplicantAddress(commonRequest.getCityName());
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),birthCertificateDto.getOrgId());
		if (processName == null) {
			
			birthCertificateDto.setBrStatus(BndConstants.BRSTATUSAPPROVED);
			birthCertificateDto.setBirthWfStatus(BndConstants.APPROVED);
		}
		else {
			birthCertificateDto.setBrStatus(BndConstants.STATUSOPEN);
			birthCertificateDto.setBirthWfStatus(BndConstants.PENDING);
		}
		BeanUtils.copyProperties(birthCertificateDto, birthcer);
		
		
		birthCerRepro.save(birthcer);
		if (serviceMas.getSmFeesSchedule() == 0) {
			initializeWorkFlowForFreeService(birthCertificateDto);
		} else {
			setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smsAndEmail(birthCertificateDto,organisation);
		return birthCertificateDto;

	}
	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(BirthCertificateDTO birthCertificateDto,Organisation organisation)
	{
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(birthCertificateDto.getUserId());
		smdto.setAppNo(birthCertificateDto.getApplicationId());
		smdto.setMobnumber(birthCertificateDto.getApplicantMobilno());
		smdto.setEmail(birthCertificateDto.getApplicantEmail());
		smdto.setDate(birthCertificateDto.getBrAcgd());
		smdto.setAppDate(Utility.dateToString(birthCertificateDto.getBrAcgd()));
		String fullName = String.join(" ", Arrays.asList(birthCertificateDto.getRequestDTO().getfName(),
				birthCertificateDto.getRequestDTO().getmName(), birthCertificateDto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		if(birthCertificateDto.getAmount()== null) {
			birthCertificateDto.setAmount(0.0d);
		}
		smdto.setAmount(birthCertificateDto.getAmount());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {

			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, BndConstants.APPLY_FOR_BIRTH_CERTIFICATE,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smdto, organisation,
					birthCertificateDto.getLangId());

		} else {
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, BndConstants.BIRTHCERTIFICATEAPPROVAL,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation,
					birthCertificateDto.getLangId());
		}
	}
	
	
	
	@Override
	@POST
	@Path("/saveBirthCertificates")
	@Transactional
	public BirthCertificateDTO saveBirthCertificates(BirthCertificateDTO birthCertificateDto) {
		BirthCertificateEntity birthcer = new BirthCertificateEntity();
		final RequestDTO commonRequest = birthCertificateDto.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RBC, birthCertificateDto.getOrgId());

		commonRequest.setOrgId(birthCertificateDto.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setApmMode(BndConstants.FLAGF);
		if (serviceMas.getSmFeesSchedule() == 0) {
			commonRequest.setPayStatus(BndConstants.PAYSTSTUSFREE);
		} else {
			commonRequest.setPayStatus(BndConstants.PAYSTATUSCHARGE);
		}
		commonRequest.setUserId(birthCertificateDto.getUserId());

		final Long applicationId = applicationService.createApplication(commonRequest);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		commonRequest.setApplicationId(applicationId);
		if (null == applicationId) {
			throw new FrameworkException("Application Not Generated");
		}
		if ((birthCertificateDto.getUploadDocument() != null) && (!birthCertificateDto.getUploadDocument().isEmpty())) {
			fileUploadService.doFileUpload(birthCertificateDto.getUploadDocument(), commonRequest);
		}
		birthCertificateDto.setApplicationId(applicationId.toString());
		if ((applicationId != null) && (applicationId != 0)) {
			birthCertificateDto.setApmApplicationId(applicationId);
		}
		birthCertificateDto.setApplicantEmail(commonRequest.getEmail());
		birthCertificateDto.setApplicantMobilno(commonRequest.getMobileNo());
		birthCertificateDto.setApplicantFname(commonRequest.getfName());
		birthCertificateDto.setApplicantAddress(commonRequest.getCityName());
		BeanUtils.copyProperties(birthCertificateDto, birthcer);
		birthCerRepro.save(birthcer);
		
		/*
		 * List<TbTaxMasEntity> taxList
		 * =taxMasService.fetchAllApplicableServiceCharge(serviceMas.getSmServiceId(),
		 * birthCertificateDto.getOrgId(), birthCertificateDto.getChargeApplicableAt());
		 * if(!taxList.isEmpty()) {
		 * birthCertificateDto.getChargesInfo().get(0).setChargeCode(taxList.get(0).
		 * getTaxId());
		 * birthCertificateDto.getChargesInfo().get(0).setTaxCode(taxList.get(0).
		 * getTaxCode()); }
		 */
		 if (serviceMas.getSmFeesSchedule() == 0) {
		initializeWorkFlowForFreeService(birthCertificateDto);
		 }
		 else {
		setAndSaveChallanDtoOffLines(birthCertificateDto);
		 }
		 Organisation organisation = organisationDAO.getOrganisationById(birthCertificateDto.getOrgId(),MainetConstants.STATUS.ACTIVE);
		 smsAndEmail(birthCertificateDto,organisation);
		 birthCertificateDto.setSmServiceId(serviceMas.getSmServiceId());
		return birthCertificateDto;

	}

	public void initializeWorkFlowForFreeService(BirthCertificateDTO requestDto) {
		boolean checkList = false;
		if (CollectionUtils.isNotEmpty(requestDto.getUploadDocument())) {
			checkList = true;
		}

		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RBC, requestDto.getOrgId());

		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getPdFathername());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(BndConstants.RTS));
		applicantDto.setMobileNo(requestDto.getApplicantMobilno());
		applicantDto.setUserId(requestDto.getUserId());
		applicationMetaData.setApplicationId(requestDto.getApmApplicationId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	public BirthCertificateDTO getBirthRegisteredAppliDetail(Long applicationId, Long orgId) {
		BirthCertificateEntity DetailEntity = birthCerRepro.findData(applicationId, orgId);
		BirthCertificateDTO birthDto = new BirthCertificateDTO();
		BeanUtils.copyProperties(DetailEntity, birthDto);
		return birthDto;
	}

	@Transactional
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, BirthCertificateModel birthRegModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RBC,
				birthRegModel.getBirthCertificateDto().getOrgId());
		offline.setApplNo(birthRegModel.getBirthCertificateDto().getApmApplicationId());
		offline.setAmountToPay(birthRegModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ",
				Arrays.asList(birthRegModel.getRequestDTO().getfName(),birthRegModel.getRequestDTO().getmName(), birthRegModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		String wardName = "";
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)){
			if((null != birthRegModel.getRequestDTO().getWardNo() && null != birthRegModel.getRequestDTO().getAssWard1() )) {
				wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(birthRegModel.getRequestDTO().getAssWard1())
						.getLookUpDesc();
			}
		} else if (birthRegModel.getRequestDTO().getWardNo() != 0L && birthRegModel.getRequestDTO().getWardNo() != null) {
			wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(birthRegModel.getRequestDTO().getWardNo())
					.getLookUpDesc();
 
		}
		 String pinCode = "";
		 if(birthRegModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(birthRegModel.getRequestDTO().getPincodeNo());
		 }
		String applicantAddress = String.join(" ", Arrays.asList(birthRegModel.getRequestDTO().getBldgName(),
				birthRegModel.getRequestDTO().getBlockName(), birthRegModel.getRequestDTO().getRoadName(),wardName, birthRegModel.getRequestDTO().getCityName(),pinCode));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(birthRegModel.getRequestDTO().getMobileNo());
		offline.setEmailId(birthRegModel.getRequestDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		if(birthRegModel.getRequestDTO().getAssWard1()!= null) {
			offline.getDwzDTO().setAreaDivision1(birthRegModel.getRequestDTO().getAssWard1());
			}


		if (birthRegModel.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : birthRegModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(birthRegModel.getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setDeptCode(tbDepartmentService.findDepartmentShortCodeByDeptId(offline.getDeptId(), offline.getOrgId()));
		
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_ASCL) && StringUtils.isNotEmpty(offline.getDeptCode()) && ( offline.getDeptCode().equals("BND"))) {
		offline.setLoggedLocId(birthRegModel.getRequestDTO().getWardNo());
		}
		
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			birthRegModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			birthRegModel.setReceiptDTO(printDto);
			birthRegModel.setSuccessMessage(birthRegModel.getAppSession().getMessage("adh.receipt"));
		}
	}

	@Transactional
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLines(BirthCertificateDTO requestDto) {
		CommonChallanDTO offline = requestDto.getOfflineDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.RBC, requestDto.getOrgId());
		offline.setApplNo(requestDto.getApmApplicationId());
		offline.setAmountToPay(requestDto.getChargesAmount());
		offline.setOrgId(requestDto.getOrgId());
		offline.setUserId(requestDto.getUserId());
		offline.setLangId(requestDto.getLangId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());

		if (requestDto.getPdFathername() != null) {
			offline.setApplicantName(requestDto.getPdFathername());
		}
		offline.setApplicantAddress(requestDto.getPdParaddress());
		offline.setMobileNumber(requestDto.getRequestDTO().getMobileNo());
		offline.setEmailId(requestDto.getRequestDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		if (requestDto.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : requestDto.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		 if (CollectionUtils.isNotEmpty(requestDto.getUploadDocument())) {
		 offline.setDocumentUploaded(true);
		 } else {
		offline.setDocumentUploaded(false);
		 }
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			requestDto.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
		}
	}

	@Override
	public void saveBirthRegDet(BirthCertificateDTO birthRegDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBirthApproveStatusBR(BirthCertificateDTO birthRegDTO, String status, String lastDecision) {
		TbCfcApplicationMstEntity cfcApplEntiry = new TbCfcApplicationMstEntity();
		ServiceMaster service = new ServiceMaster();
		cfcApplEntiry.setApmApplicationId(Long.valueOf(birthRegDTO.getApplicationId()));
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(Long.valueOf(birthRegDTO.getApplicationId()), birthRegDTO.getOrgId());
		BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
		cfcApplEntiry.setTbOrganisation(UserSession.getCurrent().getOrganisation());
		cfcApplEntiry.setApmApplicationDate(new Date());
		service.setSmServiceId(birthRegDTO.getSmServiceId());
		cfcApplEntiry.setTbServicesMst(service);
		cfcApplEntiry.setUpdatedDate(birthRegDTO.getBrDob());
		cfcApplEntiry.setLmoddate(new Date());
		cfcApplEntiry.setUpdatedBy(birthRegDTO.getUserId());
		cfcApplEntiry.setUserId(birthRegDTO.getUserId());
		cfcApplEntiry.setApmSex(birthRegDTO.getBrSex());
		cfcApplEntiry.setApmFname(birthRegDTO.getApplicantFname());
		

		LookUp lokkup = null;
		if (birthRegDTO.getBrSex() != null) {
			lokkup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(birthRegDTO.getBrSex(), GENDER, birthRegDTO.getLangId());
		}

		if (lastDecision.equals(BndConstants.REJECTED)) {
			cfcApplEntiry.setApmAppRejFlag(BndConstants.APMAPPLICATIOREJFLAG);
			cfcApplEntiry.setAppAppRejBy(birthRegDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag(BndConstants.APMAPPLICATIONCLOSEDFLAG);

		} else if (status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.PENDING)) {
			cfcApplEntiry.setApmApplSuccessFlag(BndConstants.APMAPPLICATIONPENDINGFLAG);
			cfcApplEntiry.setApmApprovedBy(birthRegDTO.getSmServiceId());
			cfcApplEntiry.setApmApplClosedFlag(BndConstants.APMAPPLICATIONCLOSEDOPENFLAG);

		} else if (status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.CLOSED)) {
			cfcApplEntiry.setApmApplSuccessFlag(BndConstants.APMAPPLICATIONCLOSEDFLAG);
			cfcApplEntiry.setApmApprovedBy(birthRegDTO.getSmServiceId());
			cfcApplEntiry.setApmApplClosedFlag(BndConstants.APMAPPLICATIONCLOSEDOPENFLAG);
		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBirthWorkFlowStatusBR(Long brId, String taskNamePrevious, Long orgId, String brStatus) {
		birthCerRepro.updateWorkFlowStatus(brId, orgId, taskNamePrevious, brStatus);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBirthWorkFlowStatus(Long brId, String taskNamePrevious, Long orgId, String brStatus) {
		birthCerRepro.updateWorkFlowBirthReg(brId, orgId, taskNamePrevious, brStatus);
	}

	
	@Override
	@Transactional
	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	@WebMethod(exclude = true)
	@Path(value = "/getBndApplicableTax")
	@ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
	@POST
	public WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms Bnd getApplicableTaxes execution start..");
		try {
			if (requestDTO.getDataModel() == null) {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
			} else {
				BndRateMaster BndRateMaster = (BndRateMaster) CommonMasterUtility.castRequestToDataModel(requestDTO,
						BndRateMaster.class);
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
		LOGGER.info("brms Bnd getApplicableTaxes execution end..");

		return responseDTO;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	@WebMethod(exclude = true)
	@Path(value = "/getBndCharge")
	@POST
	public WSResponseDTO getBndCharge(@RequestBody WSRequestDTO wSRequestDTO) {
		LOGGER.info("brms BND date getcertificateCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(wSRequestDTO, ServiceEndpoints.BRMSMappingURL.BND_RATE_MASTER_URL);

			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				return responseDTO;
			}
		} catch (Exception ex) {
			throw new FrameworkException("UNBALE TO REQUEST FOR BND RATE CHARGES", ex);
		}
		LOGGER.info("brms BND date getcertificateCharges execution End..");

		return null;
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
		if (bndRateMaster.getChargeApplicableAt() == null || bndRateMaster.getChargeApplicableAt().isEmpty()) {
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

	private WSResponseDTO populateOtherFieldsForServiceCharge(BndRateMaster bndRateMaster, WSResponseDTO responseDTO)
			throws CloneNotSupportedException {
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

	private List<BndRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, BndRateMaster bndRateMaster,
			Organisation organisation) throws CloneNotSupportedException {
		LOGGER.info("settingAllFields execution start..");
		List<BndRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			BndRateMaster BNDRateMasters = (BndRateMaster) bndRateMaster.clone();

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

	private BndRateMaster settingTaxCategories(BndRateMaster bndRateMaster, TbTaxMasEntity enity,
			Organisation organisation) {

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
	@Transactional
	public Boolean checkEmployeeRole(UserSession ses) {
		List<LookUp> lookup = CommonMasterUtility.getListLookup(BndConstants.DESIGN_PRIFIX,
				UserSession.getCurrent().getOrganisation());

		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		for (int i = 0; i < lookup.size(); i++) {
			if (lookup.get(i).getOtherField()!=null && lookup.get(i).getOtherField().equals(BndConstants.RTS)) {
				if (lookup.get(i).getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
					checkFinalAproval = true;
				}
			}
		}
		return checkFinalAproval;
	}

	@Override
	public void smsAndEmailApproval(BirthCertificateDTO birthCertificateDto ,String decision) {
			SMSAndEmailDTO smdto = new SMSAndEmailDTO();
			smdto.setUserId(birthCertificateDto.getUserId());
			smdto.setAppNo(birthCertificateDto.getApplicationId());
			smdto.setMobnumber(birthCertificateDto.getApplicantMobilno());
			String fullName = String.join(" ", Arrays.asList(birthCertificateDto.getRequestDTO().getfName(),
					birthCertificateDto.getRequestDTO().getmName(), birthCertificateDto.getRequestDTO().getlName()));
			smdto.setAppName(fullName);
			Organisation organisation =UserSession.getCurrent().getOrganisation();
			smdto.setPlace(birthCertificateDto.getBirthRegremark());
			smdto.setCc(decision);
			smdto.setEmail(birthCertificateDto.getApplicantEmail());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) { 
			if(MainetConstants.WorkFlow.Decision.APPROVED.equals(decision)) {
				smdto.setRegReason("successfully generated with registration");
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						BndConstants.RTS, BndConstants.BIRTHCERTIFICATEAPPROVAL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, birthCertificateDto.getLangId());
			}else if(MainetConstants.WorkFlow.Decision.REJECTED.equals(decision)) { 
				smdto.setRegReason("rejected because of these reasons");
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						BndConstants.RTS, BndConstants.BIRTHCERTIFICATEAPPROVAL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, birthCertificateDto.getLangId());
			}
			}else {
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						BndConstants.RTS, BndConstants.BIRTHCERTIFICATEAPPROVAL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, birthCertificateDto.getLangId());

			}
			
			
	}
		
	@Override
	@POST
	@Path("/searchBirthCertificate")
	@Transactional
	public BirthCertificateDTO getBirthRegisteredAppliDetails(BirthCertificateDTO birthCertificateDto) {
		BirthCertificateEntity DetailEntity = birthCerRepro.findData(birthCertificateDto.getApmApplicationId(), birthCertificateDto.getOrgId());
		BirthCertificateDTO birthDto = new BirthCertificateDTO();
		BeanUtils.copyProperties(DetailEntity, birthDto);
		return birthDto;
	}
	
	
	@Override
	@POST
	@Path("/viewSearchBirthCertificate")
	@Transactional(readOnly = true)
	public List<BirthCertificateDTO> viewBirthRegisteredAppliDetails(
			@RequestBody ViewBirthCertiDetailRequestDto viewRequestDto) {
		List<BirthCertificateDTO> birthCertificateDtoList = new ArrayList<>();
		List<String> attachList = new ArrayList<>();
		if (viewRequestDto.getBirthSearchDto().getApmApplicationId() != null
				&& viewRequestDto.getBirthSearchDto().getBrDob() != null) {
			List<BirthCertificateEntity> birthCertificateEntities = birthCerRepro
					.findByApmApplicationIdAndBrDobAndOrgIdAndBrStatus(viewRequestDto.getBirthSearchDto().getApmApplicationId(),
							viewRequestDto.getBirthSearchDto().getBrDob(),
							viewRequestDto.getBirthSearchDto().getOrgId(), MainetConstants.FlagA);
			if (birthCertificateEntities != null && birthCertificateEntities.size() > 0) {
				for (BirthCertificateEntity entity : birthCertificateEntities) {
					BirthCertificateDTO birthDto = new BirthCertificateDTO();
					BeanUtils.copyProperties(entity, birthDto);
					final List<CFCAttachment> attachDocs = iChecklistVerificationService
							.findAttachmentsForAppId(birthDto.getApmApplicationId(), null, birthDto.getOrgId());
					if (attachDocs != null)
						birthDto.setUploadDocument(new ArrayList<>());
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

					birthDto.setUploadDocument(documentList);

					birthCertificateDtoList.add(birthDto);
				}
			}

		} else if (viewRequestDto.getBirthSearchDto().getBrDob() != null) {
			List<BirthCertificateEntity> birthCertificateEntities = birthCerRepro.findByBrDobAndOrgIdAndBrStatus(
					viewRequestDto.getBirthSearchDto().getBrDob(), viewRequestDto.getBirthSearchDto().getOrgId(), MainetConstants.FlagA);
			if (birthCertificateEntities != null && birthCertificateEntities.size() > 0) {
				for (BirthCertificateEntity entity : birthCertificateEntities) {
					BirthCertificateDTO birthDto = new BirthCertificateDTO();
					BeanUtils.copyProperties(entity, birthDto);

					final List<CFCAttachment> attachDocs = iChecklistVerificationService
							.findAttachmentsForAppId(birthDto.getApmApplicationId(), null, birthDto.getOrgId());
					if (attachDocs != null)
						birthDto.setUploadDocument(new ArrayList<>());
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

					birthDto.setUploadDocument(documentList);
					birthCertificateDtoList.add(birthDto);
				}
			}
		} else {
			BirthCertificateEntity DetailEntity = birthCerRepro.findByApmApplicationIdAndOrgIdAndBrStatus(
					viewRequestDto.getBirthSearchDto().getApmApplicationId(),
					viewRequestDto.getBirthSearchDto().getOrgId(), MainetConstants.FlagA);
			BirthCertificateDTO birthDto = new BirthCertificateDTO();
			BeanUtils.copyProperties(DetailEntity, birthDto);
			final List<CFCAttachment> attachDocs = iChecklistVerificationService
					.findAttachmentsForAppId(birthDto.getApmApplicationId(), null, birthDto.getOrgId());
			if (attachDocs != null)
				birthDto.setUploadDocument(new ArrayList<>());
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

			birthDto.setUploadDocument(documentList);

			birthCertificateDtoList.add(birthDto);
		}

		return birthCertificateDtoList;

	}
	
	/*
	 * @Override public List<RequestDTO> searchData(Long applicationId, Long
	 * serviceId, Long orgId) {
	 * 
	 * List<RequestDTO> requestDTOList = new ArrayList<RequestDTO>(); RequestDTO
	 * dto;
	 * 
	 * ServiceMaster serviceMasterData = serviceMaster.getServiceMaster(serviceId,
	 * orgId); List<TbCfcApplicationMstEntity> mstData =
	 * drainageConnectionServiceDAO.searchData(applicationId, serviceId, orgId);
	 * String serviceName = serviceMasterData.getSmServiceName(); for
	 * (TbCfcApplicationMstEntity data : mstData) { dto = new RequestDTO();
	 * dto.setfName(data.getApmFname()); dto.setlName(data.getApmLname());
	 * dto.setApplicationId(data.getApmApplicationId());
	 * dto.setServiceShortCode(serviceName);
	 * 
	 * requestDTOList.add(dto); }
	 * 
	 * return requestDTOList; }
	 */
	
	@Override
	public List<Long> getApplicationNo(Long orgId, Long serviceId) {

		List<TbCfcApplicationMstEntity> applicationNo = birthCerRepro.loadSummaryData(orgId, serviceId);
		List<Long> appNo = new ArrayList<Long>();
		applicationNo.forEach(no -> {
			appNo.add(no.getApmApplicationId());

		});
		return appNo;

	}
	
	@Override
	public void smsAndEmailApprovalNacBirth(BirthCertificateDTO birthCertificateDto ,String decision) {
			SMSAndEmailDTO smdto = new SMSAndEmailDTO();
			smdto.setUserId(birthCertificateDto.getUserId());
			smdto.setAppNo(birthCertificateDto.getApplicationId());
			String fullName = String.join(" ", Arrays.asList(birthCertificateDto.getRequestDTO().getfName(),
					birthCertificateDto.getRequestDTO().getmName(), birthCertificateDto.getRequestDTO().getlName()));
			smdto.setAppName(fullName);
			smdto.setRegNo(birthCertificateDto.getBrCertNo());
			smdto.setMobnumber(birthCertificateDto.getApplicantMobilno());
			Organisation organisation =UserSession.getCurrent().getOrganisation();
			smdto.setCc(decision);
			smdto.setEmail(birthCertificateDto.getApplicantEmail());
			String alertType = MainetConstants.BLANK;
			if(decision.equals(BndConstants.APPROVED)) {
				alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
			}else {
				alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
			}
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, "NacForBirthRegApproval.html", alertType, smdto, organisation, birthCertificateDto.getLangId());

		
	}

}
