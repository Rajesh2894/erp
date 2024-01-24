/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dao.BankMasterDao;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.dao.ConfigurationMasterDao;
import com.abm.mainet.socialsecurity.datamodel.WaterRateMaster;
import com.abm.mainet.socialsecurity.domain.BeneficiaryPaymentDetailEntity;
import com.abm.mainet.socialsecurity.domain.SchemeApplicantFamilyDetEntity;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;
import com.abm.mainet.socialsecurity.mapper.ApplicationFormDetailsMapper;
import com.abm.mainet.socialsecurity.repository.IBeneficiaryPaymentOrderRepository;
import com.abm.mainet.socialsecurity.repository.PensionDetailMasterRepository;
import com.abm.mainet.socialsecurity.repository.SchemeApplicationFormRepository;
import com.abm.mainet.socialsecurity.repository.SchemeEligibilityDetailsRepository;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppFamilyDetailsDto;
import com.abm.mainet.socialsecurity.ui.model.SchemeApplicationFormModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author priti.singh
 *
 */
@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService")
@Api(value = "/schemeApplicationFormService")
@Path("/schemeApplicationFormService")
@Service(value = "SchemeApplicationFormService")
public class SchemeApplicationFormServiceImpl implements ISchemeApplicationFormService {

	private static final Logger logger = Logger.getLogger(SchemeApplicationFormServiceImpl.class);
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	
	@Autowired
	private IOrganisationService organisationService;
	@Autowired
	private SchemeApplicationFormRepository schemeApplicationFormRepository;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ServiceMasterService iServiceMasterService;
	@Autowired
	private TbDepartmentService iTbDepartmentService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private IFileUploadService fileUploadService;
	@Autowired
	private IWorkflowActionService iWorkflowActionService;
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;
	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;
	@Autowired
	private ISMSAndEmailService ismsAndEmailService;
	@Autowired
	private ServiceMasterService serviceMasterService;
	@Resource
	private BankMasterDao bankMasterDao;

	@Autowired
	private PensionDetailMasterRepository pensionDetailMasterRepository;

	@Autowired
	private SchemeEligibilityDetailsRepository scmEligibleRepo;

	@Autowired
	private IBeneficiaryPaymentOrderRepository iBeneficiaryPaymentOrderRepository;
	
	@Autowired
	ConfigurationMasterDao configurationMasterDao;
	
	@Resource
	private TbTaxMasService taxMasService;

	@Override
	@POST
	@WebMethod
	@Consumes("application/json")
	@Path("/FindSecondLevelPrefixByFirstLevelPxCode/{orgId}/{parentPx}/{parentpxId}/{level}")
	@Transactional(readOnly = true)
	public List<LookUp> FindSecondLevelPrefixByFirstLevelPxCode(@PathParam(value = "orgId") Long orgId,
			@PathParam(value = "parentPx") String parentPx, @PathParam(value = "parentpxId") Long parentpxId,
			@PathParam(value = "level") Long level) {
		List<LookUp> subType;
		try {
			subType = ApplicationSession.getInstance().getChildsByOrgPrefixTopParentLevel(orgId.intValue(), parentPx,
					parentpxId, level);
		} catch (Exception ex) {
			logger.warn("Prefix Not Defined child ULB level:" + ex);
			subType = ApplicationSession.getInstance().getChildsByOrgPrefixTopParentLevel(
					(int) organisationService.getSuperUserOrganisation().getOrgid(), parentPx, parentpxId, level);
		}
		return subType;
	}

	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = MainetConstants.SocialSecurity.SAVE_UPDATE_SOCIAL_SECURITY_APPLICATION, notes = MainetConstants.SocialSecurity.SAVE_UPDATE_SOCIAL_SECURITY_APPLICATION)
	@Path("/saveSocialSecurityApplication")
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	public ApplicationFormDto saveApplicationDetails(ApplicationFormDto dto) {
		ApplicationFormDto newDto;
		try {
			// D#122580 for checking Mobile Request and validating criteria
			if (dto.getSourceFlag() != null && dto.getSourceFlag().equals(MainetConstants.FlagM)) {
				validateSchemeCriteria(dto);
				if (dto != null && dto.getErrorList() != null && !dto.getErrorList().isEmpty()) {
					return dto;
				}
			}
			String wardCode = null;
			if (dto.getBeneficiaryno() == null) {
				if (dto.getSwdward1() != null) {
					Organisation orgid = organisationService.getSuperUserOrganisation();
				    wardCode = pensionDetailMasterRepository.getPrefixOtherValue(Long.valueOf(dto.getSwdward1()),
						orgid.getOrgid());
				}
				dto.setBeneficiaryno(getbeneficiarynumber(dto.getUlbName(), dto.getServiceCode(), dto.getOrgId(),wardCode));
			}
			newDto = prepareAndSaveApplicationMaster(dto);
			SocialSecurityApplicationForm entity = ApplicationFormDetailsMapper.dtoToEntity(newDto);
			entity.setDatalegacyflag("S");
			entity.setSapiStatus("P");
			entity.setBranchname(dto.getGridId());
			if ((newDto.getElectionIdNo() != null) && (!newDto.getElectionIdNo().isEmpty())) {
				entity.setElectionIdNo(newDto.getElectionIdNo());
			}
			// D#13819
			if (CollectionUtils.isNotEmpty(dto.getOwnerFamilydetailDTO())) {
				List<SchemeApplicantFamilyDetEntity> familyEnt = ApplicationFormDetailsMapper
						.familyDetailsDtotoEntity(dto, entity);
				entity.setOwnerFamilyDetails(familyEnt);

			}
			schemeApplicationFormRepository.save(entity);
			if ((dto.getDocumentList() != null) && !dto.getDocumentList().isEmpty()) {
				fileUploadService.doFileUpload(dto.getDocumentList(), prepareRequestDto(newDto));
			}
			if ((dto.getSwdward1() != null)) 
			newDto.getApplicant().setDwzid1(Long.valueOf(dto.getSwdward1()));
			if(StringUtils.isEmpty(dto.getStatus())||(dto.getStatus()!=null && !dto.getStatus().equals("D")))
				initiateWorkFlowForFreeService(newDto);
				newDto.setStatusFlag(true);
		} catch (Exception ex) {
			throw new FrameworkException(
					"Exception occured while saving the scheme application Details so please check all mandatory fields",
					ex);
		}

		return newDto;
	}

	@Override
	@Transactional
	public ApplicationFormDto prepareAndSaveApplicationMaster(ApplicationFormDto dto) {

		TbDepartment deptObj = iTbDepartmentService.findDeptByCode(dto.getOrgId(), MainetConstants.FlagA,
				MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		ServiceMaster sm = iServiceMasterService
				.getServiceMasterByShortCode(MainetConstants.SocialSecurity.SERVICE_CODE, dto.getOrgId());

		final RequestDTO applicantDetailDTO = new RequestDTO();
		applicantDetailDTO.setfName(dto.getNameofApplicant());
		if (dto.getMobNum() != null) {
			applicantDetailDTO.setMobileNo(dto.getMobNum().toString());
		}
		applicantDetailDTO.setEmail(dto.getEmail());
		applicantDetailDTO.setPincodeNo(dto.getPinCode());
		applicantDetailDTO.setServiceId(sm.getSmServiceId());
		applicantDetailDTO.setDeptId(deptObj.getDpDeptid());
		applicantDetailDTO.setUserId(dto.getCreatedBy());
		applicantDetailDTO.setOrgId(dto.getOrgId());
		applicantDetailDTO.setLangId(dto.getLangId());
		applicantDetailDTO.setPayStatus(MainetConstants.FlagF);
		if (dto.getBplid() != null && dto.getBplid().equals(MainetConstants.FlagY)) {
			applicantDetailDTO.setBplNo(dto.getBplfamily());
		}
		if(dto.getMasterAppId()==null) {
		final Long applicationId = applicationService.createApplication(applicantDetailDTO);
		dto.setMasterAppId(applicationId);}
		dto.setDeptId(deptObj.getDpDeptid());
		dto.setApplicableServiceId(sm.getSmServiceId());
      if(dto.getStatus()!=null)
		dto.setStatus(dto.getStatus());
		return dto;
	}

	@Override
	@Transactional
	public void initiateWorkFlowForFreeService(ApplicationFormDto dto) {
		boolean checklist = false;
		if ((dto.getDocumentList() != null) && !dto.getDocumentList().isEmpty()) {
			checklist = true;
		}
		ApplicationMetadata applicationData = new ApplicationMetadata();
		applicationData.setApplicationId(dto.getMasterAppId());
		applicationData.setIsCheckListApplicable(checklist);
		applicationData.setOrgId(dto.getOrgId());

		dto.getApplicant().setServiceId(dto.getApplicableServiceId());
		dto.getApplicant().setDepartmentId(dto.getDeptId());
		dto.getApplicant().setUserId(dto.getCreatedBy());
		dto.getApplicant().setMobileNo(dto.getMobNum().toString());
		dto.getApplicant().setOrgId(dto.getOrgId());
		commonService.initiateWorkflowfreeService(applicationData, dto.getApplicant());
	}

	// this code is for the document upload
	private RequestDTO prepareRequestDto(ApplicationFormDto dto) {

		final RequestDTO reqDto = new RequestDTO();
		reqDto.setfName(dto.getNameofApplicant());
		if (dto.getContactNumber() != null) {
			reqDto.setMobileNo(dto.getContactNumber().toString());
		} else if (dto.getMobNum() != null) {
			reqDto.setMobileNo(dto.getMobNum().toString());
		}
		reqDto.setPincodeNo(dto.getPinCode());
		reqDto.setServiceId(dto.getApplicableServiceId());
		reqDto.setDeptId(dto.getDeptId());
		reqDto.setUserId(dto.getCreatedBy());
		reqDto.setOrgId(dto.getOrgId());
		reqDto.setLangId(dto.getLangId());
		reqDto.setPayStatus(MainetConstants.FlagF);
		reqDto.setApplicationId(dto.getMasterAppId());
		reqDto.setIsBPL(dto.getIsBplApplicable());
		if (dto.getIsBplApplicable().equals(MainetConstants.FlagY)) {
			reqDto.setBplNo(dto.getBplfamily());
		}
		return reqDto;

	}

	@Override
	@Transactional(readOnly = true)
	public ApplicationFormDto findApplicationdetails(Long applicationId, Long parentOrgId) {
		ApplicationFormDto dto = null;
		try {
			SocialSecurityApplicationForm entity = schemeApplicationFormRepository
					.findApplicationdetails(applicationId.toString(), parentOrgId);
			dto = ApplicationFormDetailsMapper.entityToDto(entity);
		} catch (Exception ex) {
			throw new FrameworkException(
					"Exception occurs while finding data from application form please check inputs", ex);
		}
		return dto;

	}

	@Override
	@Transactional
	public boolean saveDecision(ApplicationFormDto applicationformdto, Long orgId, Employee emp,
			WorkflowTaskAction workFlowActionDto, RequestDTO req) {
		boolean status = false;
		try {
			iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, orgId, req.getServiceId());
			// for saving the documents
			if ((applicationformdto.getDocumentList() != null) && !applicationformdto.getDocumentList().isEmpty()) {
				fileUploadService.doFileUpload(applicationformdto.getDocumentList(), req);
			}

			WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(applicationformdto.getMasterAppId(), null, orgId);

			// sms n email when application gets rejected

			if (workflowRequest != null
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
				// sms and email (when application form rejects (workflow))
				final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
				Organisation org = UserSession.getCurrent().getOrganisation();
				smsDto.setMobnumber(applicationformdto.getMobNum().toString());
				smsDto.setAppNo(applicationformdto.getMasterAppId().toString());
				ServiceMaster sm = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class)
						.getServiceMasterByShortCode(MainetConstants.SocialSecurity.SERVICE_CODE, orgId);
				// setServiceMaster(sm);
				smsDto.setServName(sm.getSmServiceName());
				String url = "SchemeApplicationForm.html";
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				int langId = UserSession.getCurrent().getLanguageId();
				ismsAndEmailService.sendEmailSMS("SWD", url, MainetConstants.SocialSecurity.REJECTED, smsDto, org,
						langId);

				schemeApplicationFormRepository.rejectPension(applicationformdto.getMasterAppId().toString(), orgId);

			}
			// if user is last who reject or approve according to that we can update our own
			// table flag
			if (iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {
				schemeApplicationFormRepository.updateApprovalFlag(workFlowActionDto.getApplicationId().toString(),
						applicationformdto.getParentOrgId(), workFlowActionDto.getDecision().substring(0, 1));

				// sms and email (when application form approves (workflow))
				final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
				Organisation org = UserSession.getCurrent().getOrganisation();
				smsDto.setMobnumber(applicationformdto.getMobNum().toString());
				smsDto.setAppNo(applicationformdto.getMasterAppId().toString());
				ServiceMaster sm = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class)
						.getServiceMasterByShortCode(MainetConstants.SocialSecurity.SERVICE_CODE, orgId);
				// setServiceMaster(sm);
				smsDto.setServName(sm.getSmServiceName());
				String url = "SchemeApplicationForm.html";
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				int langId = UserSession.getCurrent().getLanguageId();
				ismsAndEmailService.sendEmailSMS("SWD", url, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, smsDto, org,
						langId);

			}
			status = true;
		} catch (Exception ex) {
			throw new FrameworkException("Exception occurs while updating workflow,upload docs,updating table", ex);
		}
		return status;
	}

	@Override
	@Transactional
	public ApplicationFormDto saveDataLegacyFormDetails(ApplicationFormDto dto) {
		try {
			dto.setBeneficiaryno(getbeneficiarynumber(dto.getUlbName(), dto.getServiceCode(), dto.getOrgId(),null));
			SocialSecurityApplicationForm entity = ApplicationFormDetailsMapper.dtoToEntity(dto);
			entity.setBranchname(dto.getGridId());
			entity.setDatalegacyflag(MainetConstants.FlagD);
			entity.setSapiStatus("A");
			schemeApplicationFormRepository.save(entity);
			dto.setStatusFlag(true);
		} catch (Exception ex) {
			throw new FrameworkException(
					"Exception occured while saving the Data Legacy Form Details so please check all mandatory fields",
					ex);
		}
		return dto;

	}

	@Override
	public String getbeneficiarynumber(String ulbName, String serviceSortCode, Long orgId, String wardCode) {
		try {
			Long squenceNo = seqGenFunctionUtility.generateSequenceNo(
					MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE, "TB_SWD_SCHEME_APPLICATION",
					"BENEFICIARY_NUMBER", orgId, MainetConstants.FlagC, null);
			String benefiNo=null;
			 Organisation org = organisationService.getOrganisationById(orgId);
			if (org!=null && Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL) && wardCode!=null && !wardCode.isEmpty() ) {
				benefiNo= ulbName + "/" + serviceSortCode + "/" + wardCode + "/" + squenceNo;
			}
			else {
				benefiNo= ulbName + "/" + serviceSortCode + "/" + squenceNo;
			}
			return benefiNo;
		} catch (Exception e) {
			throw new FrameworkException("Proper input not getting to generate beneficiary number", e);
		}

	}

	@POST
	@Path("/getSchemeName")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Object[]> getSchemeName(ApplicationFormDto dto) {
		Organisation org = new Organisation();
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, dto.getLangId().intValue(), org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		List<Object[]> schemeMastServiceList = serviceMasterService
				.findAllActiveServicesWhichIsNotActual(dto.getOrgId(), dto.getDeptId(), activeStatusId, "N");
		// D#77333
		List<SocialSecuritySchemeMaster> schemeMaster = getActiveScheme(dto.getOrgId());
		List<Object[]> schemeservicelist1 = new ArrayList();
		schemeMaster.stream().forEach(k -> {
			schemeMastServiceList.stream().forEach(l -> {
				if (k.getSchemeNameId().equals(l[0])) {
					schemeservicelist1.add(l);
				}
			});
		});
		return schemeservicelist1;
	}

	@POST
	@Path("/getDeptIdByServiceShortName/{orgId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Long getDeptIdByServiceShortName(@PathParam(value = "orgId") Long orgId) {
		ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.SocialSecurity.SERVICE_CODE,
				orgId);
		return service.getTbDepartment().getDpDeptid();
	}

	@POST
	@Path("/getGridId")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Long getCriteriaGridId(CriteriaDto dto) {
		Long gridId = 0l;
		try {
			SocialSecuritySchemeMaster schemeEntity = schemeApplicationFormRepository
					.getSchemeEntity(dto.getServiceId(), dto.getOrgId());
			Long sdsch_id = schemeEntity.getSchemeMstId();
			List<SocialSecuritySchemeEligibilty> elegibility = schemeApplicationFormRepository
					.getschemeEligibilityEntity(sdsch_id, dto.getOrgId(), dto.getFactrors(), dto.getCriterias());
			if (!elegibility.isEmpty()) {
             //check and fetch id whose amount payId is not null (for isue not showing in benisiciary Pa order)
				for (SocialSecuritySchemeEligibilty grid : elegibility) {
					if (grid.getAmount() != null && grid.getPaySchedule() != null) {
						gridId = grid.getGroupID();
						break;
					}
				}
			} else {
				gridId = 0l;
			}
		} catch (NullPointerException e) {
			gridId = 0l;
		}
		return gridId;
	}

	@Override
	public String isSchemeActive(Long serviceId, Long orgId) {
		String activeStatus = "";
		SocialSecuritySchemeMaster entity = schemeApplicationFormRepository.getSchemeEntity(serviceId, orgId);
		if (entity != null) {
			activeStatus = entity.getIsSchmeActive();
		} else {
			activeStatus = MainetConstants.FlagN;
		}
		return activeStatus;
	}

	// rajesh.das added for getting Active Service in portal side
	@POST
	@WebMethod
	@Consumes("application/json")
	@Path("/getServiceDetailWhichIsActual/{orgId}/{depId}/{activeStatusId}/{notActualFlag}")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> findAllActiveServicesWhichIsNotActual(@PathParam("orgId") Long orgId,
			@PathParam("depId") Long depId, @PathParam("activeStatusId") Long activeStatusId,
			@PathParam("notActualFlag") String notActualFlag) {

		List<SocialSecuritySchemeMaster> schemeMaster = getActiveScheme(orgId);
		List<Object[]> schemeMastServiceList = serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId, depId,
				activeStatusId, "N");
		List<Object[]> schemeservicelist1 = new ArrayList();
		schemeMaster.stream().forEach(k -> {
			schemeMastServiceList.stream().forEach(l -> {
				if (k.getSchemeNameId().equals(l[0])) {
					schemeservicelist1.add(l);
				}
			});
		});
		return schemeservicelist1;
	}

	@Override
	public List<SocialSecuritySchemeMaster> getActiveScheme(Long orgid) {
		List<SocialSecuritySchemeMaster> sschemeMaster = schemeApplicationFormRepository.getActiveScheme(orgid);
		return sschemeMaster;
	}

	@Override
	public Long getAllSchemeData(Long serviceId, Long orgId) {

		SocialSecuritySchemeMaster schemeMaster = schemeApplicationFormRepository.getSchemeEntity(serviceId, orgId);

		return schemeMaster.getSchemeMstId();
	}

	/*
	 * @Override public List<Object[]> getScemeEligibilityData(Long scmMstId, Long
	 * orgId) { List<Object[]> socialSecEligible =
	 * scmEligibleRepo.findAllEligiblityById(scmMstId, orgId);
	 * 
	 * return socialSecEligible; }
	 */
//Defect #79855 adding for getting latest eligibility data
	@Override
	public List<SocialSecuritySchemeEligibilty> getLatestScemeEligbleData(Long scmMstId) {
		List<SocialSecuritySchemeEligibilty> socialSecEligible = scmEligibleRepo.findLatestEligibiltyRecord(scmMstId);

		return socialSecEligible;

	}

	// D#122580
	@POST
	@Path("/getBankList")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<BankMasterEntity> getBankList() {
		return bankMasterDao.getBankList();

	}

//D#122580
	private void validateSchemeCriteria(ApplicationFormDto dto) {
		// TODO Auto-generated method stub
		Long schemeMstId = getAllSchemeData(dto.getSelectSchemeName(), dto.getOrgId());
		List<String> errorList = new ArrayList<String>();
		SchemeApplicationFormModel scmModel = new SchemeApplicationFormModel();
		Organisation org = organisationService.getOrganisationById(dto.getOrgId());
		getAndSetPrefix(dto.getOrgId(), dto.getLangId(), org, scmModel);

		Long age = dto.getAgeason();
		List<LookUp> lookupsCriteria = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2,
				dto.getOrgId());

		List<Object[]> objArr = new ArrayList<Object[]>();
		for (LookUp l : lookupsCriteria) {
			Object[] objarr = new Object[2];
			if (l.getLookUpCode().equalsIgnoreCase("MAE")) {
				objarr[0] = l.getLookUpId();
				objarr[1] = l.getLookUpParentId();
				objArr.add(objarr);
			} else if (l.getLookUpCode().equalsIgnoreCase("ADT")) {
				objarr[0] = l.getLookUpId();
				objarr[1] = l.getLookUpParentId();
				objArr.add(objarr);
			} else if (l.getLookUpCode().equalsIgnoreCase("ODA")) {
				objarr[0] = l.getLookUpId();
				objarr[1] = l.getLookUpParentId();
				objArr.add(objarr);
			}

		}

		if (schemeMstId != null) {
			List<SocialSecuritySchemeEligibilty> schemeEligibility = getLatestScemeEligbleData(schemeMstId);
			if ((schemeEligibility != null && !schemeEligibility.isEmpty()) && dto != null) {

				for (SocialSecuritySchemeEligibilty socSecScmEligble : schemeEligibility) {
					if (socSecScmEligble.getFactorApplicableId() != null && socSecScmEligble.getCriteriaId() != null) {
						Long scmCriteriaId = socSecScmEligble.getCriteriaId();
						Long scmFactApplId = socSecScmEligble.getFactorApplicableId();
						if (socSecScmEligble.getRangeFrom() != null && socSecScmEligble.getRangeTo() != null) {
							Long rangeFrom = Long.valueOf(socSecScmEligble.getRangeFrom());
							Long rangeTo = Long.valueOf(socSecScmEligble.getRangeTo());
							for (Object[] k : objArr) {
								if (k[0] != null && k[1] != null) {
									Long sdschCriteria1 = Long.valueOf(k[0].toString());
									Long aplFactId1 = Long.valueOf(k[1].toString());

									if ((scmCriteriaId != null && scmFactApplId != null)
											&& (aplFactId1 != null && sdschCriteria1 != null)) {
										if ((scmCriteriaId.equals(sdschCriteria1))
												&& (scmFactApplId.equals(aplFactId1))) {
											if (rangeFrom >= age || rangeTo <= age) {
												errorList.add(ApplicationSession.getInstance()
														.getMessage("eligibility.criteria.agerange") + rangeFrom
														+ "  to " + rangeTo);
											}
										}
									}
								}
							}
							if (scmModel.getTypeofdisabilityList().get(0).getLookUpParentId() == scmFactApplId) {
								if (dto.getTypeofDisId().equals(scmCriteriaId)) {

								} else if (dto.getTypeofDisId() != null) {
									if (rangeFrom >= dto.getPercenrofDis() || rangeTo <= dto.getPercenrofDis()) {

										errorList.add((ApplicationSession.getInstance()
												.getMessage("eligibility.criteria.disability.percentage") + rangeFrom
												+ "  to " + rangeTo));

									}
								}

							}
						} else {
							if (scmModel.getGenderList().get(0).getLookUpParentId() == scmFactApplId) {
								if (!dto.getGenderId().equals(scmCriteriaId)) {
									errorList.add((ApplicationSession.getInstance()
											.getMessage("eligibility.criteria.genderFact")));

								}

							}
							if (scmModel.getCategoryList().get(0).getLookUpParentId() == scmFactApplId) {
								if (!dto.getCategoryId().equals(scmCriteriaId)) {
									errorList.add((ApplicationSession.getInstance()
											.getMessage("eligibility.criteria.catagoryFact")));

								}

							}
							if (scmModel.getBplList().get(0).getLookUpParentId() == scmFactApplId) {
								if (!dto.getBplid().equals(scmCriteriaId)) {
									errorList.add((ApplicationSession.getInstance()
											.getMessage("eligibility.criteria.bplFact")));

								}

							}
							if (scmModel.getMaritalstatusList().get(0).getLookUpParentId() == scmFactApplId) {
								if (!dto.getMaritalStatusId().equals(scmCriteriaId)) {
									errorList.add((ApplicationSession.getInstance()
											.getMessage("eligibility.criteria.maritalFact")));

								}
							}
						}
					}
					dto.setErrorList(errorList);
				}
			}

		}
	}

	private void getAndSetPrefix(Long orgId, Long langId, Organisation org, SchemeApplicationFormModel dto) {
		List<LookUp> parentPxList = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId);
		dto.setGenderList(FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("GNR"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		dto.setEducationList(FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("EDU"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		dto.setMaritalstatusList(FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("MLS"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		dto.setCategoryList(FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("CTY"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		dto.setTypeofdisabilityList(FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("DSY"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		dto.setBplList(FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("BPL"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));

	}

	@Override
	public String checkFamilyDetailsReqOrNot(Long schemeId, Long orgId) {
		SocialSecuritySchemeMaster sss = pensionDetailMasterRepository.checkFamilyDetReqorNot(schemeId, orgId);
		if (sss != null && StringUtils.isNotBlank(sss.getFamilyDetReq())) {
			return sss.getFamilyDetReq();
		}
		return null;
	}
	  
	@Override
	public List<ApplicationFormDto> getExistingHolderByUID(String aadharCard, long orgid) {
		// TODO Auto-generated method stub
		List<ApplicationFormDto> dtoList = new ArrayList<ApplicationFormDto>();
		List<SocialSecurityApplicationForm> detList = schemeApplicationFormRepository.fetchAlldataByAadhar(orgid,
				aadharCard);
		if (CollectionUtils.isNotEmpty(detList)) {
			for (SocialSecurityApplicationForm entity : detList) {

				ApplicationFormDto dtos = ApplicationFormDetailsMapper.entityToDto(entity);
				ServiceMaster serMast = iServiceMasterService.getServiceMaster(dtos.getSelectSchemeName(), orgid);
				if (serMast != null) {
					dtos.setServDesc(serMast.getSmServiceName());
				}
				 Organisation org = organisationService.getOrganisationById(orgid);
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL) && dtos.getBeneficiaryno() != null) {
						BeneficiaryPaymentDetailEntity benList = iBeneficiaryPaymentOrderRepository
								.getRtgsData(dtos.getBeneficiaryno(),orgid);
						if (benList!=null) {
							dtos.setBenfitedAmt(benList.getAmount().toString());
						}
						try {
							if(benList!=null && benList.getUpdatedDate()!=null) {
							String financialYear = Utility.getFinancialYearFromDate(benList.getUpdatedDate());
							dtos.setYear(financialYear);
							}
						} catch (Exception e) {
							logger.info("Error------------>"+ e);
						}
					} else if (dtos.getApmApplicationId() != null) {
					List<BeneficiaryPaymentDetailEntity> benList = iBeneficiaryPaymentOrderRepository
							.getViewDataFromRtgsPayment(orgid, dtos.getApmApplicationId().toString());
					if (CollectionUtils.isNotEmpty(benList)) {
						dtos.setBenfitedAmt(benList.get(0).getAmount().toString());
					}
				}
				dtoList.add(dtos);
			}
		}
		return dtoList;
	}

	@Override
	public boolean checkAppPresentAgainstScheme(Long serviceId, Long orgId) {
		Boolean result = schemeApplicationFormRepository.checkAppPresentAgainstScheme(serviceId,orgId);
		if (result) {
			return true;
		}
		return false;
	}
    @Override
    public List<SchemeAppFamilyDetailsDto> getFamilyDetById(Long applicationId, Long orgId) {
        List<SchemeApplicantFamilyDetEntity> entityList = schemeApplicationFormRepository.getFamilyDetById(applicationId,orgId);
        List<SchemeAppFamilyDetailsDto>  dtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
        for (SchemeApplicantFamilyDetEntity entity : entityList) {
            SchemeAppFamilyDetailsDto dto  = new SchemeAppFamilyDetailsDto();
            String dateOfBirth = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(entity.getDob());
            if (StringUtils.isNotEmpty(dateOfBirth))
            dto.setDateOfBirth(dateOfBirth);
            BeanUtils.copyProperties(entity, dto);
            dtoList.add(dto);
             }
        }
        return dtoList;
    }
    // #142983
	@Override
	public String checkLifeCertificateDateReqOrNot(Long schemeId, Long orgId) {
		SocialSecuritySchemeMaster schemeMaster = pensionDetailMasterRepository.checkLifeCertificateDateReqOrNot(schemeId, orgId);
		if (schemeMaster != null && StringUtils.isNotBlank(schemeMaster.getLifeCertificateReq())) {
			return schemeMaster.getLifeCertificateReq();
		}
		return null;
	}
	
	
	@Override
	public String annualIncome(Long schemeId, Long orgId ,Long factorApplicableId) {
		String schemeMaster = pensionDetailMasterRepository.annualIncome(schemeId, orgId,factorApplicableId);
		if (schemeMaster != null && StringUtils.isNotBlank(schemeMaster)) {
			return schemeMaster;
		}
		return null;
	}

	@Override
	public List<ApplicationFormDto> getAppliDetail(Long selectSchemeName, Long subSchemeName, String swdward1,
			String swdward2, String swdward3, String swdward4, String swdward5, String aadharCard, String status, Long orgId) {
		List<ApplicationFormDto> dtoList = new ArrayList<ApplicationFormDto>();
		List<SocialSecurityApplicationForm> detList = configurationMasterDao.getAppData(selectSchemeName,subSchemeName,swdward1,swdward2,swdward3,swdward4,swdward5,aadharCard,status,orgId);
		if (CollectionUtils.isNotEmpty(detList)) {
			for (SocialSecurityApplicationForm entity : detList) {
				ApplicationFormDto dtos = ApplicationFormDetailsMapper.entityToDto(entity);
				ServiceMaster serMast = iServiceMasterService.getServiceMaster(dtos.getSelectSchemeName(), orgId);
				if (serMast != null) {
					dtos.setServDesc(serMast.getSmServiceName());
				}
				
				if(entity.getSwdward1()!=null)
					dtos.setSwdward1(CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(entity.getSwdward1()), orgId).getDescLangFirst());
				if (entity.getStatus() != null && entity.getStatus().equals("D")) {
					dtos.setStatusDesc(MainetConstants.TASK_STATUS_DRAFT);
				} else {
					dtos.setStatusDesc("Submitted");
				}
				if(entity.getSubSchemeName()!=null)
				dtos.setObjOfschme((CommonMasterUtility.getHierarchicalLookUp(entity.getSubSchemeName(),orgId)).getLookUpDesc());
				dtoList.add(dtos);
			}
		}
		return dtoList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ApplicationFormDto findApplicationData(Long applicationId) {
		ApplicationFormDto dto = null;
		try {
			SocialSecurityApplicationForm entity = schemeApplicationFormRepository.findOne(applicationId);
			dto = ApplicationFormDetailsMapper.entityToDto(entity);
		} catch (Exception ex) {
			throw new FrameworkException(
					"Exception occurs while finding data from application form please check inputs", ex);
		}
		return dto;

	}
	
	@Override
	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		logger.info("brms RTS getApplicableTaxes execution start..");
		try {
			if (requestDTO.getDataModel() == null) {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				//responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
			} else {
				WaterRateMaster waterRateMaster = (WaterRateMaster) CommonMasterUtility
						.castRequestToDataModel(requestDTO, WaterRateMaster.class);
				validateDataModel(waterRateMaster, responseDTO);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					responseDTO = populateOtherFieldsForServiceCharge(waterRateMaster, responseDTO);
				}
			}
		} catch (CloneNotSupportedException | FrameworkException ex) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
		}
		logger.info("brms RTS getApplicableTaxes execution end..");
		return responseDTO;
	}
	
	private WSResponseDTO validateDataModel(WaterRateMaster WaterRateMaster, WSResponseDTO responseDTO) {
		logger.info("validateDataModel execution start..");
		StringBuilder builder = new StringBuilder();
		if (WaterRateMaster.getServiceCode() == null || WaterRateMaster.getServiceCode().isEmpty()) {
			builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
		}
		if (WaterRateMaster.getOrgId() == 0l) {
			builder.append(ORG_ID_CANT_BE_ZERO).append(",");
		}
		if (WaterRateMaster.getChargeApplicableAt() == null || WaterRateMaster.getChargeApplicableAt().isEmpty()) {
			builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
		} else if (!StringUtils.isNumeric(WaterRateMaster.getChargeApplicableAt())) {
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

	public WSResponseDTO populateOtherFieldsForServiceCharge(WaterRateMaster WaterRateMaster, WSResponseDTO responseDTO)
			throws CloneNotSupportedException {
		logger.info("populateOtherFieldsForServiceCharge execution start..");
		List<WaterRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(WaterRateMaster.getServiceCode(),
				WaterRateMaster.getOrgId());
	//	if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), WaterRateMaster.getOrgId(),
					Long.parseLong(WaterRateMaster.getChargeApplicableAt()));
			Organisation organisation = new Organisation();
			organisation.setOrgid(WaterRateMaster.getOrgId());
			listOfCharges = settingAllFields(applicableCharges, WaterRateMaster, organisation);
			responseDTO.setResponseObj(listOfCharges);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		//}
			/*
			 * else { responseDTO.setFree(true);
			 * responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS); }
			 */
		logger.info("populateOtherFieldsForServiceCharge execution end..");
		return responseDTO;
	}
	
	private List<WaterRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, WaterRateMaster rateMaster,
			Organisation organisation) throws CloneNotSupportedException {
		logger.info("settingAllFields execution start..");
		List<WaterRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			WaterRateMaster WaterRateMaster = (WaterRateMaster) rateMaster.clone();
			// SLD for dependsOnFactor
			String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
					rateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
			String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
					entity.getOrgid(), entity.getTaxApplicable());
			WaterRateMaster.setTaxType(taxType);
			WaterRateMaster.setTaxCode(entity.getTaxCode());
			WaterRateMaster.setChargeApplicableAt(chargeApplicableAt);
			settingTaxCategories(WaterRateMaster, entity, organisation);
			WaterRateMaster.setTaxId(entity.getTaxId());
			list.add(WaterRateMaster);
		}
		logger.info("settingAllFields execution end..");
		return list;
	}
	
	
	private WaterRateMaster settingTaxCategories(WaterRateMaster WaterRateMaster, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				WaterRateMaster.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				WaterRateMaster.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return WaterRateMaster;

	}
	
	@Override
	public WSResponseDTO getApplicableCharges(WSRequestDTO requestDTO) {
		logger.info("brms getApplicableCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				responseDTO = setServiceChargeDTO(responseDTO);
			} else {
				// throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE +
				// responseDTO.getErrorMessage());
				// added by rajesh.das Defect #78975
				return responseDTO;
			}
		} catch (Exception ex) {
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
		}
		logger.info("brms getApplicableCharges execution End..");
		return responseDTO;
	}
	
	 private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
		 logger.info("setServiceChargeDTO execution start..");
			final List<?> charges = RestClient.castResponse(responseDTO, WaterRateMaster.class);
			final List<WaterRateMaster> finalRateMaster = new ArrayList<>();
			for (final Object rate : charges) {
			    final WaterRateMaster masterRate = (WaterRateMaster) rate;
			    finalRateMaster.add(masterRate);
			}
			final ChargeDetailDTO chargedto = new ChargeDetailDTO();
			final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
			for (final WaterRateMaster rateCharge : finalRateMaster) {
			    chargedto.setChargeCode(rateCharge.getTaxId());
			    chargedto.setChargeAmount(rateCharge.getFlatRate());
			    chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
			    chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
			    detailDTOs.add(chargedto);
			}
			responseDTO.setResponseObj(detailDTOs);
			logger.info("setServiceChargeDTO execution end..");
			return responseDTO;
		    }
}
