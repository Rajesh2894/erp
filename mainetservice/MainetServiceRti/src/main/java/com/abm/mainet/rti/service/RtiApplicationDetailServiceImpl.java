/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Service Implementation file for RTI Application.
 * @method  : saveRtiApplication - method for creating RTI Application number and saving rti application.            
 *            getCheckListRti - REST call for getting checklist.
 *            fetchCharges - REST call for fetch application charges.
 *            getActiveDepartment - Implementation to get the active department. 
 *            getDeptLocation - Implementation to get the RTI Department location.
 */
package com.abm.mainet.rti.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.repository.TbLoiDetJpaRepository;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.repository.ObjectionMastRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WorkFlow;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.SysmodfunctionService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.model.mapping.AbstractModelMapper;
import com.abm.mainet.common.repository.CFCApplicationAddressRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.ServicesEventEntity;
import com.abm.mainet.common.workflow.domain.WorkflowDet;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.repository.IServicesEventEntityRepository;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;
import com.abm.mainet.common.workflow.repository.WorkflowMappingRepository;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.dao.IRtiApplicationServiceDAO;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.domain.TbRtiApplicationDetails;
import com.abm.mainet.rti.domain.TbRtiApplicationDetailsHistory;
import com.abm.mainet.rti.domain.TbRtiFwdEmployeeEntity;
import com.abm.mainet.rti.domain.TbRtiMediaDetails;
import com.abm.mainet.rti.dto.FormEReportDTO;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiFrdEmployeeDetails;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.dto.RtiRemarksHistDto;
import com.abm.mainet.rti.repository.RtiForwardRepository;
import com.abm.mainet.rti.repository.RtiHistoryRepository;
import com.abm.mainet.rti.repository.RtiRepository;
import com.abm.mainet.rti.ui.model.RtiApplicationFormModel;
import com.abm.mainet.rti.utility.RtiUtility;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@WebService(endpointInterface = "com.abm.mainet.rti.service.IRtiApplicationDetailService")
@Path("/brmsrtiservice")
@Api("/brmsrtiservice")
@Service
public class RtiApplicationDetailServiceImpl extends AbstractModelMapper implements IRtiApplicationDetailService {

	private static final long serialVersionUID = 3839478044093637088L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RtiApplicationDetailServiceImpl.class);
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private final ModelMapper modelMapper;

	@Autowired
	private WorkFlowTypeRepository workflowRepo;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ObjectionMastRepository objectionRepository;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IRtiApplicationServiceDAO rtiApplicationServiceDAO;

	@Resource
	private AuditService auditService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private IChallanService iChallanService;

	@Resource
	private IDepartmentDAO iDepartmentDAO;

	@Resource
	private LocationMasJpaRepository locationMasJpaRepository;

	@Resource
	private ILocationMasService iLocationMasService;
	@Resource
	private BRMSCommonService brmsCommonService;

	@Resource
	private TbTaxMasService taxMasService;

	@Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;
	@Autowired
	private RtiUtility rtiUtility;
	@Resource
	private TbDepartmentService departmentService;

	@Autowired
	private TbDepartmentJpaRepository departmentJpaRepository;

	@Resource
	IFileUploadService fileUploadService;

	@Resource
	private CommonService commonService;

	@Resource
	private ISMSAndEmailService ismsAndEmailService;

	@Resource
	IFinancialYearService iFinancialYearService;

	@Resource
	RtiRepository rtiRepository;

	@Resource
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Resource
	TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Resource
	private TbLoiDetJpaRepository tbLoiDetJpaRepository;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	@Autowired
	private CFCApplicationAddressRepository cfcApplicationAddressRepository;

	@Autowired
	private DepartmentService deptService;
	@Resource
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Autowired
	private IServicesEventEntityRepository iServiceEventRepository;

	@Autowired
	private SysmodfunctionService sysModeFuncService;
	@Autowired
	private WorkflowMappingRepository workFlowMapRepo;
	@Autowired
	private IWorkflowTypeDAO workflowTypeDAO;

	@Autowired
	private RtiHistoryRepository histRepo;

	@Autowired
	private IWorkflowActionService actionService;

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private TbLoiMasService tbLoiMasService;
	
	@Autowired
	private RtiForwardRepository rtiForwardRepository;

	public RtiApplicationDetailServiceImpl() {
		this.modelMapper = new ModelMapper();
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@Override
	@Transactional
	public RtiApplicationFormDetailsReqDTO saveApplication(@RequestBody RtiApplicationFormDetailsReqDTO rtiDto) {
		if (rtiDto != null) {
         Organisation org=new Organisation();
         org.setOrgid(rtiDto.getOrgId());
			RequestDTO requestDto = setApplicantRequestDto(rtiDto);
			// creating Rti Application

			// for checking workflow exist or not Defect#117838
			if (!checkWoflowDefinedOrNot(rtiDto) && UserSession.getCurrent().getOrganisation() == null) {
				rtiDto.setWorkflowExist(true);
				return rtiDto;
			}

			rtiDto.setUid(rtiDto.getUid());

			final Long applicationNo = applicationService.createApplication(rtiDto);
			rtiDto.setApmApplicationId(applicationNo);

			// Generate Rti Number

			String rtiNumber = generateRtiApplicationNumber(rtiDto.getApmApplicationId(), rtiDto.getServiceId(),
					rtiDto.getOrgId());
			rtiDto.setRtiNo(rtiNumber);
			rtiDto.setSmServiceId(rtiDto.getSmServiceId());
			TbRtiApplicationDetails tbRtiApplicationDetails = mapDTOToEntity(rtiDto);
			rtiApplicationServiceDAO.saveRtiApplicationForm(tbRtiApplicationDetails);
			// add code for saving history data
			TbRtiApplicationDetailsHistory rtiHistory = mapDTOToHistryEntity(rtiDto);
			histRepo.save(rtiHistory);

			String deptName = rtiApplicationServiceDAO.getdepartmentNameById(rtiDto.getDeptId());
			rtiDto.setDepartmentName(deptName);

			boolean checklist = false;
			if ((rtiDto.getDocumentList() != null) && !rtiDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(applicationNo);
				checklist = fileUploadService.doFileUpload(rtiDto.getDocumentList(), requestDto);
				checklist = true;
			}

			if ((rtiDto.getFetchDocs() != null) && !rtiDto.getFetchDocs().isEmpty()) {
				requestDto.setApplicationId(Long.valueOf(tbRtiApplicationDetails.getRtiNo()));
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo());
				fileUploadService.doFileUpload(rtiDto.getFetchDocs(), requestDto);
			}
//added regarding US#111612
			if ((rtiDto.getStampDoc() != null)
					&& (!rtiDto.getStampDoc().isEmpty() && rtiDto.getStampDoc().get(0).getDocumentByteCode() != null)) {
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo() + MainetConstants.FlagS);
				fileUploadService.doFileUpload(rtiDto.getStampDoc(), requestDto);
			}
//added regarding US#111612
			if ((rtiDto.getPostalDoc() != null) && (!rtiDto.getPostalDoc().isEmpty()
					&& rtiDto.getPostalDoc().get(0).getDocumentByteCode() != null)) {
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo() + MainetConstants.FlagS);
				fileUploadService.doFileUpload(rtiDto.getPostalDoc(), requestDto);
			}
			if (!CollectionUtils.isEmpty(rtiDto.getChlNonJudDoc())&&rtiDto.getChlNonJudDoc().get(0).getDocumentByteCode()!=null) {
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo() + MainetConstants.FlagN);
				fileUploadService.doFileUpload(rtiDto.getChlNonJudDoc(), requestDto);
			}
			if (rtiDto.isFree()) {
				rtiDto.getOfflineDTO().setDocumentUploaded(checklist);
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(rtiDto.getApmApplicationId());
				applicationData.setOrgId(rtiDto.getOrgId());
				if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_DSCL))
				applicationData.setIsCheckListApplicable(false);
				else
					applicationData.setIsCheckListApplicable(checklist);	
				rtiDto.getApplicantDTO().setUserId(rtiDto.getUserId());
				rtiDto.getApplicantDTO().setServiceId(rtiDto.getSmServiceId());
				rtiDto.getApplicantDTO().setDepartmentId(Long.valueOf(rtiDto.getRtiDeptId()));
				// US#139003
				if (rtiDto.getTrdWard1() != null) {
					rtiDto.getApplicantDTO().setDwzid1(rtiDto.getTrdWard1());
				}
				if (rtiDto.getTrdWard2() != null) {
					rtiDto.getApplicantDTO().setDwzid2(rtiDto.getTrdWard2());
				}
				if (rtiDto.getTrdWard3() != null) {
					rtiDto.getApplicantDTO().setDwzid3(rtiDto.getTrdWard3());
				}
				if (rtiDto.getTrdWard4() != null) {
					rtiDto.getApplicantDTO().setDwzid4(rtiDto.getTrdWard4());
				}
				if (rtiDto.getTrdWard5() != null) {
					rtiDto.getApplicantDTO().setDwzid5(rtiDto.getTrdWard5());
				}
				// D#77334 code for mobile please don't remove
				commonService.initiateWorkflowfreeService(applicationData, rtiDto.getApplicantDTO());
			}
		}
		return rtiDto;

	}

	private RequestDTO setApplicantRequestDto(RtiApplicationFormDetailsReqDTO rtiDto) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(rtiDto.getSmServiceId());
		requestDto.setUserId(rtiDto.getUserId());
		requestDto.setOrgId(rtiDto.getOrgId());
		requestDto.setLangId(rtiDto.getLangId());
		// requestDto.setDeptId(rtiDto.getRtiDeptId());
		requestDto.setfName(rtiDto.getfName());
		requestDto.setfName(rtiDto.getlName());
		requestDto.setEmail(rtiDto.getEmail());
		requestDto.setMobileNo(rtiDto.getMobileNo());
		requestDto.setAreaName(rtiDto.getAddress());
		return requestDto;
	}

	private TbRtiApplicationDetails mapDTOToEntity(RtiApplicationFormDetailsReqDTO requestDTO) {
		if (requestDTO == null) {
			return null;
		}

		final TbRtiApplicationDetails tbRtiApplicationDetails = map(requestDTO, TbRtiApplicationDetails.class);

		return tbRtiApplicationDetails;

	}

	@Override
	@WebMethod(exclude = true)
	public String generateRtiApplicationNumber(Long applicationId, Long serviceId, Long orgId) {
		final Long seq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.RTI,
				MainetConstants.RTISERVICE.TB_RTI_APPLICATION, MainetConstants.RTISERVICE.RTI_NO, orgId,
				MainetConstants.CommonConstants.C, null);
		final String num = seq.toString();
		final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.CD, Integer.parseInt(num));
		final String orgid = orgId.toString();
		return orgid.concat(paddingAppNo);

	}

	@Override
	@WebMethod(exclude = true)
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	@Override
	@Transactional
	@POST
	@ApiOperation(value = "get Department By Organisation", notes = "get Department By Organisation", response = Department.class)
	@Path("/getDepartmentByOrg")
	public List<LookUp> getActiveDepartment(long orgId) {
		List<Department> dept = departmentJpaRepository.findAllMappedDepartments(orgId);
		List<LookUp> lookupList = new ArrayList<>();
		for (Department d : dept) {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(d.getDpDeptdesc());
			detData.setDescLangSecond(d.getDpNameMar());
			detData.setLookUpId(d.getDpDeptid());
			detData.setLookUpCode(d.getDpDeptcode());
			lookupList.add(detData);
		}
		return lookupList;
	}

	@Override
	@Transactional
	public Set<LookUp> getDeptLocation(Long orgId, Long deptId) {
		Set<LookUp> locations = new HashSet<>();
		List<LocationMasEntity> locationList = locationMasJpaRepository.findAllLocationWithOperationWZMapping(orgId,
				deptId);
		locationList.forEach(location -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(location.getLocNameEng());
			detData.setDescLangSecond(location.getLocNameReg());
			detData.setLookUpId(location.getLocId());
			locations.add(detData);
		});
		return locations;

	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Boolean saveRtiApplication(RtiApplicationFormDetailsReqDTO requestDTO) {

		TbRtiApplicationDetails tbRtiApplicationDetails = mapDTOToEntity(requestDTO);
		rtiApplicationServiceDAO.saveRtiApplicationForm(tbRtiApplicationDetails);
		return true;
	}

	@Override
	@Transactional

	@POST
	@ApiOperation(value = "fetch information by application no", notes = "fetch information by application no", response = RtiApplicationFormDetailsReqDTO.class)
	@Path("/fetchInformationByApplicationid/{applicationId}/{orgId}")
	public RtiApplicationFormDetailsReqDTO fetchRtiApplicationInformationById(
			@ApiParam(value = "applicationId", required = true) @PathParam("applicationId") Long applicationId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") Long orgId) {

		Organisation org = new Organisation();

		// for fettching View data in DSCL
		RtiApplicationFormDetailsReqDTO rtiDto = new RtiApplicationFormDetailsReqDTO();
		
		TbRtiApplicationDetails entity = new TbRtiApplicationDetails();
		// US#111591
		entity = rtiApplicationServiceDAO.getRtiApplicationDetailsForDSCL(applicationId, orgId);
		orgId = entity.getOrgId();
		if (UserSession.getCurrent().getOrganisation() != null) {
			org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		} else {
			org.setOrgid(orgId);
		}

		RtiApplicationFormModel rtiApplicationFormModel = new RtiApplicationFormModel();

		TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(applicationId,
				orgId);

		TbCfcApplicationMstEntity Details = cfcApplicationMasterService.getCFCApplicationByApplicationId(applicationId,
				orgId);
		rtiDto = mapEntityToDTO(entity);

		/* #29855 by Priti */
		if (entity.getRtiRelatedDeptId() != null) {
			/* fetching Department Name based on Department Id */
			rtiDto.setRelatedDeptName(getdepartmentNameById(Long.valueOf(entity.getRtiRelatedDeptId())));
		}

		rtiDto.setfName(cfcApplicationMstEntity.getApmFname());
		rtiDto.setlName(cfcApplicationMstEntity.getApmLname());
		rtiDto.setmName(cfcApplicationMstEntity.getApmMname());

		if (Details.getApmBplNo() != null && Details.getApmBplIssueAuthority() != null
				&& Details.getApmBplYearIssue() != null) {
			rtiDto.setBplNo(Details.getApmBplNo());
			rtiDto.setBplIssuingAuthority(Details.getApmBplIssueAuthority());
			rtiDto.setYearOfIssue(Details.getApmBplYearIssue());
		}

		rtiDto.setGender(cfcApplicationMstEntity.getApmSex());
		rtiDto.setTitle(String.valueOf(cfcApplicationMstEntity.getApmTitle()));
		String input = cfcApplicationMstEntity.getApmApplicationDate().toString();
		String output = input.substring(0, 19);
		rtiDto.setApmApplicationDateDesc(output);//
		rtiDto.setAadhaarNo(rtiDto.getAadhaarNo());//
		if (cfcApplicationMstEntity.getApmBplNo() != null && !cfcApplicationMstEntity.getApmBplNo().isEmpty()) {
			rtiDto.setIsBPL(MainetConstants.YES);
			rtiDto.setBplNo(cfcApplicationMstEntity.getApmBplNo());
		} else {
			rtiDto.setIsBPL(MainetConstants.NO);
		}

		rtiApplicationFormModel.setCfcAddressEntity(cfcService.getApplicantsDetails(applicationId));
		rtiDto.setAddress(rtiApplicationFormModel.getCfcAddressEntity().getApaAreanm());
		rtiDto.setMobileNo(rtiApplicationFormModel.getCfcAddressEntity().getApaMobilno());
		rtiDto.setPincodeNo(rtiApplicationFormModel.getCfcAddressEntity().getApaPincode());

		/* Defect #74661 by rajesh.das */

		if (rtiDto.getCorrAddrsAreaName() == null || rtiDto.getCorrAddrsAreaName().isEmpty()) {
			rtiDto.setCorrAddrsAreaName(rtiApplicationFormModel.getCfcAddressEntity().getApaAreanm());
		}
		if (rtiDto.getCorrAddrsPincodeNo() == null) {
			rtiDto.setCorrAddrsPincodeNo(rtiApplicationFormModel.getCfcAddressEntity().getApaPincode());
		}

		if (rtiApplicationFormModel.getCfcAddressEntity().getApaEmail() != null) {
			rtiDto.setEmail(rtiApplicationFormModel.getCfcAddressEntity().getApaEmail());
		}
		// Defect#119295
		if (rtiDto.getRtiLocationId() != 0) {
			rtiDto.setLocationName(
					getlocationNameById(Long.valueOf(String.valueOf(rtiDto.getRtiLocationId())), rtiDto.getOrgId()));

			rtiDto.setLocRegName((locationMasJpaRepository.findbyLocationId(Long.valueOf(rtiDto.getRtiLocationId()))
					.getLocNameReg()));
		}

		LookUp ccdApmTypeDesc = CommonMasterUtility
				.getNonHierarchicalLookUpObject(cfcApplicationMstEntity.getCcdApmType(), org);

		rtiDto.setApplicant(ccdApmTypeDesc.getDescLangFirst());
		// for setting applicant id for view in Portal
		rtiDto.setApplicationType(cfcApplicationMstEntity.getCcdApmType());

		/* new test */

		rtiApplicationFormModel
				.setFetchDocumentList((iChecklistVerificationService.getDocumentUploaded(applicationId, orgId)));

		rtiApplicationFormModel.setFetchApplnUpload(iChecklistVerificationService
				.getDocumentUploadedByRefNo(rtiApplicationFormModel.getReqDTO().getRtiNo(), orgId));

		// getDocumentUploadedByApplicationId for showinguploaded document

		List<DocumentDetailsVO> attachsList = new ArrayList<>();

		try {
			List<CFCAttachment> cfcAtt = iChecklistVerificationService.getDocumentUploadedForAppId(applicationId,
					orgId);
			List<CFCAttachment> cfcAtt1 = iChecklistVerificationService
					.getDocumentUploadedForAppId(Long.valueOf(rtiDto.getRtiNo()), orgId);
			if (cfcAtt1 != null && !cfcAtt1.isEmpty()) {
				cfcAtt1.stream().forEach(att -> {
					cfcAtt.add(att);
				});
			}
			if (cfcAtt != null) {
				for (CFCAttachment att : cfcAtt) {
					DocumentDetailsVO dvo = new DocumentDetailsVO();
					dvo.setAttachmentId(att.getAttId());
					dvo.setCheckkMANDATORY(att.getMandatory());
					dvo.setDocumentSerialNo(att.getClmSrNo());
					dvo.setDocumentName(att.getAttFname());
					dvo.setUploadedDocumentPath(att.getAttPath());
					dvo.setDoc_DESC_ENGL(att.getClmDescEngl());
					attachsList.add(dvo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		rtiDto.setDocumentList(attachsList);
		// D#127764
		if(rtiDto.getPostalAmt()!=null) {
			BigDecimal bd = new BigDecimal(rtiDto.getPostalAmt().toString());
			rtiDto.setPostalCardAmt(bd.toString());
		}

		return rtiDto;

	}

	private RtiApplicationFormDetailsReqDTO mapEntityToDTO(TbRtiApplicationDetails tbRtiApplicationDetails) {
		if (tbRtiApplicationDetails == null) {
			return null;
		}
		return map(tbRtiApplicationDetails, RtiApplicationFormDetailsReqDTO.class);
	}

	@Override
	@WebMethod(exclude = true)
	public String getlocationNameById(Long locId, Long orgId) {

		return locationMasJpaRepository.getLocationNameById(locId, orgId);
	}

	@Override
	@WebMethod(exclude = true)
	public String getdepartmentNameById(Long deptId) {

		return rtiApplicationServiceDAO.getdepartmentNameById(deptId);
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Boolean saveRtiMediaList(TbRtiMediaDetails tbRtiMediaDetails) {

		rtiApplicationServiceDAO.saveRtiMediaDetails(tbRtiMediaDetails);
		return true;
	}

	@Override
	@WebMethod(exclude = true)
	public List<RtiMediaListDTO> getMediaList(Long rtiId, Long orgId) {
		List<TbRtiMediaDetails> tbRtiMediaDetails = rtiApplicationServiceDAO.getMediaList(rtiId, orgId);
		List<RtiMediaListDTO> rtiMediaList = new ArrayList<>();
		RtiMediaListDTO rtiMediaListDTO = null;
		for (int i = 0; i < tbRtiMediaDetails.size(); i++) {
			rtiMediaListDTO = new RtiMediaListDTO();
			rtiMediaListDTO.setMediaType(tbRtiMediaDetails.get(i).getMediaType().intValue());
			rtiMediaListDTO.setQuantity(tbRtiMediaDetails.get(i).getMediaQuantity());
			rtiMediaListDTO.setMediaDesc(tbRtiMediaDetails.get(i).getMediaDesc());
			rtiMediaListDTO.setMediaAmount(tbRtiMediaDetails.get(i).getMediaAmount());
			rtiMediaList.add(rtiMediaListDTO);
		}

		return rtiMediaList;
	}

	@POST
	@Path("/dependentparams")
	@ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
	@Override
	public WSResponseDTO getApplicableTaxes(
			@ApiParam(value = "get dependent paramaters", required = true) WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms RTI getApplicableTaxes execution start..");
		try {
			if (requestDTO.getDataModel() == null) {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
			} else {
				RtiRateMaster rtiRateMaster = (RtiRateMaster) CommonMasterUtility.castRequestToDataModel(requestDTO,
						RtiRateMaster.class);
				validateDataModel(rtiRateMaster, responseDTO);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					responseDTO = populateOtherFieldsForServiceCharge(rtiRateMaster, responseDTO);
				}
			}
		} catch (CloneNotSupportedException | FrameworkException ex) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
		}
		LOGGER.info("brms RTI getApplicableTaxes execution end..");
		return responseDTO;
	}

	@POST
	@Path("/servicecharge")
	@ApiOperation(value = "get service charge", notes = "get service charges", response = WSResponseDTO.class)
	@Override
	public WSResponseDTO getApplicableCharges(
			@ApiParam(value = "get service charges", required = true) WSRequestDTO requestDTO) {
		LOGGER.info("brms RTI getApplicableCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.RTI_SERVICE_CHARGE_URI);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				responseDTO = setServiceChargeDTO(responseDTO);
			} else {

				// code added for production issue Defect #92885 start
				return responseDTO;
			}
		} catch (Exception ex) {
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
		}
		LOGGER.info("brms RTI getApplicableCharges execution End..");
		return responseDTO;
	}

	private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setServiceChargeDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, RtiRateMaster.class);
		final List<RtiRateMaster> finalRateMaster = new ArrayList<>();
		for (final Object rate : charges) {
			final RtiRateMaster masterRate = (RtiRateMaster) rate;
			finalRateMaster.add(masterRate);
		}
		final List<MediaChargeAmountDTO> detailDTOs = new ArrayList<>();
		for (final RtiRateMaster rateCharge : finalRateMaster) {
			final MediaChargeAmountDTO chargedto = new MediaChargeAmountDTO();
			chargedto.setChargeAmount(rateCharge.getFlatRate());
			chargedto.setFreeCopy(rateCharge.getFreeCopy());
			chargedto.setQuantity(rateCharge.getQuantity());
			chargedto.setMediaType(rateCharge.getMediaType());
			chargedto.setTaxId(rateCharge.getTaxId());
			detailDTOs.add(chargedto);
		}
		responseDTO.setResponseObj(detailDTOs);
		LOGGER.info("setServiceChargeDTO execution end..");
		return responseDTO;
	}

	/**
	 * validating rtiRateMaster model
	 * 
	 * @param rtiRateMaster
	 * @param responseDTO
	 * @return
	 */
	public WSResponseDTO validateDataModel(RtiRateMaster rtiRateMaster, WSResponseDTO responseDTO) {
		LOGGER.info("validateDataModel execution start..");
		StringBuilder builder = new StringBuilder();
		if (rtiRateMaster.getServiceCode() == null || rtiRateMaster.getServiceCode().isEmpty()) {
			builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
		}
		if (rtiRateMaster.getOrgId() == 0l) {
			builder.append(ORG_ID_CANT_BE_ZERO).append(",");
		}
		if (rtiRateMaster.getChargeApplicableAt() == null || rtiRateMaster.getChargeApplicableAt().isEmpty()) {
			builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
		} else if (!StringUtils.isNumeric(rtiRateMaster.getChargeApplicableAt())) {
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

	public WSResponseDTO populateOtherFieldsForServiceCharge(RtiRateMaster rtiRateMaster, WSResponseDTO responseDTO)
			throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<RtiRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rtiRateMaster.getServiceCode(),
				rtiRateMaster.getOrgId());
		if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), rtiRateMaster.getOrgId(),
					Long.parseLong(rtiRateMaster.getChargeApplicableAt()));
			Organisation organisation = new Organisation();
			organisation.setOrgid(rtiRateMaster.getOrgId());
			listOfCharges = settingAllFields(applicableCharges, rtiRateMaster, organisation);
			responseDTO.setResponseObj(listOfCharges);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		} else {
			responseDTO.setFree(true);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		}
		LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
		return responseDTO;
	}

	/**
	 * 
	 * @param applicableCharges
	 * @param rateMaster
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private List<RtiRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, RtiRateMaster rateMaster,
			Organisation organisation) throws CloneNotSupportedException {
		LOGGER.info("settingAllFields execution start..");
		List<RtiRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			RtiRateMaster rtiRateMaster = (RtiRateMaster) rateMaster.clone();
			// SLD for dependsOnFactor
			String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
					rateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
			String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
					entity.getOrgid(), entity.getTaxApplicable());
			rtiRateMaster.setTaxType(taxType);
			rtiRateMaster.setTaxCode(entity.getTaxCode());
			rtiRateMaster.setChargeApplicableAt(chargeApplicableAt);
			settingTaxCategories(rtiRateMaster, entity, organisation);
			rtiRateMaster.setTaxId(entity.getTaxId());
			list.add(rtiRateMaster);
		}
		LOGGER.info("settingAllFields execution end..");
		return list;
	}

	/**
	 * 
	 * @param rtiRateMaster
	 * @param enity
	 * @param organisation
	 * @return
	 */
	private RtiRateMaster settingTaxCategories(RtiRateMaster rtiRateMaster, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				rtiRateMaster.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				rtiRateMaster.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return rtiRateMaster;

	}

	@Override
	@Transactional
	public List<FormEReportDTO> getRTIApplicationDetail(long apmApplicationId, Organisation organisation) {
		List<FormEReportDTO> dtoList = new ArrayList<>();
		FormEReportDTO dto = new FormEReportDTO();
		TbRtiApplicationDetails rtiApplicationDetails = rtiApplicationServiceDAO
				.getRTIApplicationDetail(apmApplicationId, organisation.getOrgid());
		// code added because when application forwarded to other organisation but in DB
		// source organisation id will be there
		organisation = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
				.getOrganisationById(rtiApplicationDetails.getOrgId());
		TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
				.getCFCApplicationByApplicationId(apmApplicationId, organisation.getOrgid());
		CFCApplicationAddressEntity cfcApplicationAddress = cfcApplicationMasterDAO
				.getApplicantsDetailsDao(apmApplicationId);
		LookUp ccdApmTypeDesc = CommonMasterUtility
				.getNonHierarchicalLookUpObject(cfcApplicationDetails.getCcdApmType(), organisation);
		LookUp refModeDesc = CommonMasterUtility
				.getNonHierarchicalLookUpObject(rtiApplicationDetails.getApplReferenceMode(), organisation);

		if ((ccdApmTypeDesc.getLookUpDesc().contains("Individual")
				&& rtiApplicationDetails.getRtiBplFlag().equals(MainetConstants.FlagN)
				&& refModeDesc.getLookUpDesc().equals("Direct"))
				|| ccdApmTypeDesc.getLookUpDesc().equals("Organisation")
						&& refModeDesc.getLookUpDesc().equals("Direct")) {
			TbServiceReceiptMasEntity tbServiceReceiptMasEntity = rtiApplicationServiceDAO
					.getReceiptDetails(apmApplicationId, organisation.getOrgid());
			dto.setApplicationFee(tbServiceReceiptMasEntity.getRmAmount().toString());
			dto.setApplicationFeeInWord(Utility.convertBiggerNumberToWord(tbServiceReceiptMasEntity.getRmAmount()));
		} else if ((ccdApmTypeDesc.getLookUpDesc().contains("Individual")
				&& rtiApplicationDetails.getRtiBplFlag().equals(MainetConstants.FlagY)
				&& refModeDesc.getLookUpDesc().equals("Stamp"))
				|| ccdApmTypeDesc.getLookUpDesc().equals("Organisation")
						&& refModeDesc.getLookUpDesc().equals("Stamp")) {
			dto.setApplicationFee(rtiApplicationDetails.getStampAmt().toString());
			if (rtiApplicationDetails.getStampAmt() != null) {
				dto.setApplicationFeeInWord(Utility.convertNumberToWord(rtiApplicationDetails.getStampAmt()));
			}
		} else if ((ccdApmTypeDesc.getLookUpDesc().contains("Individual")
				&& rtiApplicationDetails.getRtiBplFlag().equals(MainetConstants.FlagN)
				&& refModeDesc.getLookUpDesc().equals("Stamp"))
				|| ccdApmTypeDesc.getLookUpDesc().equals("Organisation")
						&& refModeDesc.getLookUpDesc().equals("Stamp")) {
			dto.setApplicationFee(rtiApplicationDetails.getStampAmt().toString());
			if (rtiApplicationDetails.getStampAmt() != null) {
				dto.setApplicationFeeInWord(Utility.convertNumberToWord(rtiApplicationDetails.getStampAmt()));
			}
		} else {
			dto.setApplicationFee("0.00");
			dto.setApplicationFeeInWord("Zero Rs Only");
		}
		dto.setApplicationId(String.valueOf(apmApplicationId));
		dto.setApplicantName(cfcApplicationDetails.getApmFname() + " " + cfcApplicationDetails.getApmLname());
		dto.setApplicationAdd(cfcApplicationAddress.getApaAreanm());
		dto.setApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
				.format(cfcApplicationDetails.getApmApplicationDate()));

		dto.setPioActionDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date()));
		dto.setLetterNo(generateTokenNo(apmApplicationId, organisation.getOrgid()));
		dto.setAuthorityAddress(rtiApplicationDetails.getInwAuthorityAddress());
		dto.setAuthorityDepartment(rtiApplicationDetails.getInwAuthorityDept());
		dto.setAuthorityName(rtiApplicationDetails.getInwAuthorityName());
		dto.setEmail(cfcApplicationAddress.getApaEmail());
		dto.setMobileno(cfcApplicationAddress.getApaMobilno());
		dto.setOrgAddress(organisation.getOrgAddress());
		dto.setOrgName(organisation.getONlsOrgname());
		dto.setRtino(rtiApplicationDetails.getRtiNo());
		dto.setPioName(UserSession.getCurrent().getEmployee().getEmpname());
		//124874 
		dto.setPioEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		//changes due to binding of wrong value
		dto.setPioMobNo(UserSession.getCurrent().getEmployee().getEmpmobno());

		// dto.setSignPath(applicationDetails);
		dtoList.add(dto);
		return dtoList;
	}

	@Override
	@Transactional
	public Map<String, Long> getApplicationNumberByRefNo(String rtiNumber, Long serviceId, Long orgId, Long empId) {
		Map<String, Long> outputMap = new HashMap<>();
		outputMap.put(MainetConstants.Objection.APPLICTION_NO,
				rtiApplicationServiceDAO.getApplicationNumberByRefNo(rtiNumber, serviceId, orgId, empId));
		outputMap.put(MainetConstants.Objection.PERIOD, null);
		return outputMap;
	}

	@Override
	@Transactional
	public String getBplType(String rtiNo, Long orgId) {

		return rtiApplicationServiceDAO.getBplType(rtiNo, orgId);
	}

	private RtiRateMaster populateChargeModel(RtiRateMaster rtiRateMaster, String bplFlag) {
		rtiRateMaster.setOrgId(rtiRateMaster.getOrgId());
		rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL);
		rtiRateMaster.setDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		rtiRateMaster.setRateStartDate(new Date().getTime());
		rtiRateMaster.setIsBPL(bplFlag);
		return rtiRateMaster;
	}

	private void setChargeMap(RtiApplicationFormDetailsReqDTO rtiChargeDto,
			List<MediaChargeAmountDTO> chargeDetailDTO) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final MediaChargeAmountDTO dto : chargeDetailDTO) {
			chargesMap.put(dto.getTaxId(), dto.getChargeAmount());
		}
		rtiChargeDto.setChargesMap(chargesMap);
	}

	private double chargesToPay(List<MediaChargeAmountDTO> chargeDetailDTO) {
		double amountSum = 0.0;
		for (final MediaChargeAmountDTO charge : chargeDetailDTO) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	public CommonChallanDTO getCharges(Long serviceId, Long orgId, String refNo) {
		RtiApplicationFormDetailsReqDTO rtiDto = new RtiApplicationFormDetailsReqDTO();
		rtiDto.setServiceId(serviceId);
		rtiDto.setOrgId(orgId);
		rtiDto.setRtiNo(refNo);
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		rtiDto.setIsBPL(getBplType(rtiDto.getRtiNo(), orgId));
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		initRequestDto.setModelName(MainetConstants.RTISERVICE.CHECKLIST_RTIRATE_MASTER);
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> rtiratemasterlist = RestClient.castResponse(response, RtiRateMaster.class, 1);
			final RtiRateMaster rtiRateMaster = (RtiRateMaster) rtiratemasterlist.get(0);
			rtiRateMaster.setOrgId(orgId);
			rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL);
			rtiRateMaster.setChargeApplicableAt(
					Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
							MainetConstants.NewWaterServiceConstants.CAA, organisation).getLookUpId()));
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rtiRateMaster);
			final WSResponseDTO responseDTO = getApplicableTaxes(taxRequestDto);
			if (responseDTO.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
				if (!responseDTO.isFree()) {
					@SuppressWarnings("unchecked")
					final List<Object> rates = (List<Object>) responseDTO.getResponseObj();
					final List<RtiRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						RtiRateMaster rtiMaster = (RtiRateMaster) rate;
						rtiMaster = populateChargeModel(rtiMaster, rtiDto.getIsBPL());
						requiredCharges.add(rtiMaster);
					}
					WSRequestDTO chargeReqDto = new WSRequestDTO();
					chargeReqDto.setDataModel(requiredCharges);
					WSResponseDTO chargesResDTO = getApplicableCharges(chargeReqDto);
					@SuppressWarnings("unchecked")
					final List<MediaChargeAmountDTO> chargeDetailDTO = (List<MediaChargeAmountDTO>) chargesResDTO
							.getResponseObj();
					rtiDto.setIsFree(MainetConstants.N_FLAG);
					rtiDto.setFree(false);

					rtiDto.setCharges(chargesToPay(chargeDetailDTO));
					setChargeMap(rtiDto, chargeDetailDTO);
					rtiDto.getOfflineDTO().setAmountToShow(rtiDto.getCharges());
					for (final Map.Entry<Long, Double> entry : rtiDto.getChargesMap().entrySet()) {
						rtiDto.getOfflineDTO().getFeeIds().put(entry.getKey(), entry.getValue());
					}
				}
			}
		}

		return rtiDto.getOfflineDTO();

	}

	@Override
	@WebMethod(exclude = true)
	public List<RtiApplicationFormDetailsReqDTO> getRtiDetails(Long applicationId, Long orgId) {
		List<TbRtiApplicationDetails> tbRtiMediaDetails = rtiApplicationServiceDAO.getDetails(applicationId, orgId);
		List<RtiApplicationFormDetailsReqDTO> rtiList = new ArrayList<>();
		RtiApplicationFormDetailsReqDTO rtiDTO = null;
		for (int i = 0; i < tbRtiMediaDetails.size(); i++) {
			rtiDTO = new RtiApplicationFormDetailsReqDTO();
			rtiDTO.setRtiRemarks(tbRtiMediaDetails.get(i).getRtiRemarks());
			rtiList.add(rtiDTO);
		}
		return rtiList;
	}

	@Override
	@Transactional
	public List<RtiApplicationFormDetailsReqDTO> getRTIApplicationDetailBy(Long orgId) {

		List<TbRtiApplicationDetails> ApplicationList = rtiRepository.findByOrgid(orgId, orgId.toString());
		List<RtiApplicationFormDetailsReqDTO> RtiApplicationDtoList = new ArrayList<>();

		for (TbRtiApplicationDetails RtiApplicationDetails : ApplicationList) {
			RtiApplicationFormDetailsReqDTO rtiApplicationFormDetailsReqDTO = new RtiApplicationFormDetailsReqDTO();

			BeanUtils.copyProperties(RtiApplicationDetails, rtiApplicationFormDetailsReqDTO);

			String departmentName = getdepartmentNameById(Long.valueOf(RtiApplicationDetails.getRtiDeptId()));
			rtiApplicationFormDetailsReqDTO.setDepartmentName(departmentName);
			rtiApplicationFormDetailsReqDTO.setInwAuthorityDept(
					serviceMasterService.getServiceNameByServiceId(RtiApplicationDetails.getSmServiceId()));

			rtiApplicationFormDetailsReqDTO.setApmApplicationDate(RtiApplicationDetails.getApmApplicationDate());
			Optional<TbCfcApplicationMstEntity> ApplicationInfo = tbCfcApplicationMstJpaRepository
					.findByApmApplicationId(RtiApplicationDetails.getApmApplicationId());

			List<Object[]> AddressInfo = tbCfcApplicationAddressJpaRepository
					.findAddressInfo(RtiApplicationDetails.getApmApplicationId(), RtiApplicationDetails.getOrgId());

			Object[] objects = AddressInfo.get(0);
			rtiApplicationFormDetailsReqDTO.setfName(ApplicationInfo.get().getApmFname());
			rtiApplicationFormDetailsReqDTO.setmName(ApplicationInfo.get().getApmMname());
			rtiApplicationFormDetailsReqDTO.setlName(ApplicationInfo.get().getApmLname());
			rtiApplicationFormDetailsReqDTO.setApmOrgnName(ApplicationInfo.get().getApmOrgnName());

			rtiApplicationFormDetailsReqDTO.setGender(ApplicationInfo.get().getApmSex());
			rtiApplicationFormDetailsReqDTO.setTitleId(ApplicationInfo.get().getApmTitle());

			rtiApplicationFormDetailsReqDTO.setApplicationType(ApplicationInfo.get().getCcdApmType());

			rtiApplicationFormDetailsReqDTO.setMobileNo(objects[3] == null ? null : objects[3].toString());

			rtiApplicationFormDetailsReqDTO
					.setPincodeNo(objects[2] == null ? null : Long.valueOf(String.valueOf(objects[2])));

			rtiApplicationFormDetailsReqDTO.setUid(ApplicationInfo.get().getApmUID());

			rtiApplicationFormDetailsReqDTO.setAreaName(objects[0] == null ? null : objects[0].toString());

			rtiApplicationFormDetailsReqDTO.setEmail(objects[4] == null ? null : String.valueOf(objects[4]));

			rtiApplicationFormDetailsReqDTO.setIsBPL(RtiApplicationDetails.getRtiBplFlag());

			rtiApplicationFormDetailsReqDTO.setBplNo(ApplicationInfo.get().getApmBplNo());
			rtiApplicationFormDetailsReqDTO.setYearOfIssue(ApplicationInfo.get().getApmBplYearIssue());
			rtiApplicationFormDetailsReqDTO.setBplIssuingAuthority(ApplicationInfo.get().getApmBplIssueAuthority());

			rtiApplicationFormDetailsReqDTO.setStampAmt(RtiApplicationDetails.getStampAmt());
			if(RtiApplicationDetails.getFrdOrgId()!=null)
			rtiApplicationFormDetailsReqDTO.setFrdOrgId(Long.valueOf(RtiApplicationDetails.getFrdOrgId()));

			RtiApplicationDtoList.add(rtiApplicationFormDetailsReqDTO);
		}

		return RtiApplicationDtoList;
	}

	@Override
	@Transactional
	public RtiApplicationFormDetailsReqDTO saveSecondApplication(
			@RequestBody RtiApplicationFormDetailsReqDTO requestDTO) {
		if (requestDTO != null) {
			final Long appNo = requestDTO.getApmApplicationId();
			// creating Rti Application
			final Long applicationNo = applicationService.createApplication(requestDTO);
			requestDTO.setApmApplicationId(applicationNo);
			requestDTO.setApplicationId(applicationNo);
			requestDTO.setSmServiceId(requestDTO.getServiceId());

			boolean checklist = false;
			if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
				checklist = fileUploadService.doFileUpload(requestDTO.getDocumentList(), requestDTO);
				checklist = true;
			}
			if ((requestDTO.getFetchDocs() != null) && !requestDTO.getFetchDocs().isEmpty()) {
				requestDTO.setApplicationId(applicationNo);
				requestDTO.setReferenceId(requestDTO.getRtiNo());
				fileUploadService.doFileUpload(requestDTO.getFetchDocs(), requestDTO);
			}
        //D#140951
			Organisation org = new Organisation();
			String serviceCode = MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE;
			long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			boolean status = checkWorkflowIsNotDefine(org, orgId, serviceCode);
			if(status) {
				requestDTO.setRtiSecndAplStatus(MainetConstants.TASK_STATUS_PENDING);
			}
			else {
				requestDTO.setRtiSecndAplStatus(MainetConstants.TASK_STATUS_COMPLETED);
			}
			TbRtiApplicationDetails tbRtiApplicationDetails = mapDTOToEntity(requestDTO);
			rtiApplicationServiceDAO.saveRtiApplicationForm(tbRtiApplicationDetails);
			TbRtiApplicationDetailsHistory rtiHistory = mapDTOToHistryEntity(requestDTO);
			histRepo.save(rtiHistory);
			

			// ForCalling Workflow in DOON ENV DEFECT#102772
		
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)) {
				
				if (status == false) {

					if (requestDTO.isFree()) {

						requestDTO.getOfflineDTO().setDocumentUploaded(checklist);
						ApplicationMetadata applicationData = new ApplicationMetadata();
						applicationData.setApplicationId(requestDTO.getApplicationId());
						applicationData.setOrgId(requestDTO.getOrgId());
						applicationData.setIsCheckListApplicable(checklist);
						requestDTO.getApplicantDTO().setUserId(requestDTO.getUserId());
						requestDTO.getApplicantDTO().setServiceId(requestDTO.getServiceId());
						requestDTO.getApplicantDTO().setDepartmentId(Long.valueOf(requestDTO.getRtiDeptId()));
						commonService.initiateWorkflowfreeService(applicationData, requestDTO.getApplicantDTO());
					}
				}
				// Defect#102772
				ServiceMaster servMaster = serviceMasterService
						.getServiceByShortName(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL, orgId);
				//getObjectionDetailByApplicationId(orgId, appNo, serviceCode);

			}
		}
		return requestDTO;

	}

	@Override
	public List<RtiApplicationFormDetailsReqDTO> getForwardDeptList(Long ForwardDeptId, Long orgId) {

		List<RtiApplicationFormDetailsReqDTO> rtiList = new ArrayList<>();
		RtiApplicationFormDetailsReqDTO rtiDTO = null;
		List<TbRtiApplicationDetails> tbRtiDetails = rtiApplicationServiceDAO.getRtiAction(ForwardDeptId, orgId);
		for (TbRtiApplicationDetails rtiDetails : tbRtiDetails) {
			rtiDTO = new RtiApplicationFormDetailsReqDTO();
			BeanUtils.copyProperties(rtiDetails, rtiDTO);

			String departmentName = getdepartmentNameById(Long.valueOf(rtiDetails.getRtiDeptId()));
			rtiDTO.setDepartmentName(departmentName);
			rtiList.add(rtiDTO);

			Optional<TbCfcApplicationMstEntity> ApplicationInfo = tbCfcApplicationMstJpaRepository
					.findByApmApplicationId(rtiDetails.getApmApplicationId());

			List<Object[]> AddressInfo = tbCfcApplicationAddressJpaRepository
					.findAddressInfo(rtiDetails.getApmApplicationId(), orgId);

			Object[] objects = AddressInfo.get(0);
			rtiDTO.setfName(ApplicationInfo.get().getApmFname());
			rtiDTO.setmName(ApplicationInfo.get().getApmMname());
			rtiDTO.setlName(ApplicationInfo.get().getApmLname());
			rtiDTO.setApmOrgnName(ApplicationInfo.get().getApmOrgnName());
			rtiDTO.setGender(ApplicationInfo.get().getApmSex());
			rtiDTO.setTitleId(ApplicationInfo.get().getApmTitle());
			rtiDTO.setApplicationType(ApplicationInfo.get().getCcdApmType());
			rtiDTO.setMobileNo(objects[3] == null ? null : objects[3].toString());
			rtiDTO.setPincodeNo(objects[2] == null ? null : Long.valueOf(String.valueOf(objects[2])));
			rtiDTO.setUid(ApplicationInfo.get().getApmUID());
			rtiDTO.setAreaName(objects[0] == null ? null : objects[0].toString());
			rtiDTO.setEmail(objects[4] == null ? null : String.valueOf(objects[4]));
			rtiDTO.setIsBPL(rtiDetails.getRtiBplFlag());
			rtiDTO.setBplNo(ApplicationInfo.get().getApmBplNo());
			rtiDTO.setYearOfIssue(ApplicationInfo.get().getApmBplYearIssue());
			rtiDTO.setBplIssuingAuthority(ApplicationInfo.get().getApmBplIssueAuthority());

			rtiDTO.setStampAmt(rtiDetails.getStampAmt());
		}
		return rtiList;

	}

	@Override
	@Transactional
	public RtiApplicationFormDetailsReqDTO saveAppealHistory(RtiApplicationFormDetailsReqDTO dto) {
		rtiRepository.saveInforamtionReceiveDate(dto.getRtiDeciRecDate(), dto.getRtiNo(), dto.getOrgId());
		dto.setRtiDeciRecDateDesc(
				new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(dto.getRtiDeciRecDate()));
		return dto;
	}

	@Override
	public TbRtiApplicationDetails getLoiIdByLoiNumber(Long Loinumber, Long orgId) {

		return rtiRepository.getLoiIdByLoiNumber(Loinumber, orgId);
	}

	@Override
	@Transactional
	public int inactiveMedia(Long rtiId, Long orgId) {

		return rtiRepository.inactiveMedia(rtiId, orgId);
	}

	@POST
	@Path("/getCurentTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@Transactional
	public RtiApplicationFormDetailsReqDTO getCurentTime(@RequestBody RtiApplicationFormDetailsReqDTO saveDto) {

		RtiApplicationFormDetailsReqDTO rtiApplicationFormDetailsReqDTO = new RtiApplicationFormDetailsReqDTO();
		rtiApplicationFormDetailsReqDTO.setTime(rtiRepository.getCurrentTime());
		return rtiApplicationFormDetailsReqDTO;

	}

	@Override
	@Transactional
	public int updateLoiMasterAmount(BigDecimal loiAmount, Long loiApplicationId) {

		return rtiRepository.updateLoiMasAmount(loiAmount, loiApplicationId);
	}

	@Override
	@Transactional
	public int updateLoiDetailsAmount(BigDecimal loiDetailsAmt, BigDecimal loiPrevAmt, String loiRemarks, Long loiId) {

		return rtiRepository.updateLoiDetAmount(loiDetailsAmt, loiPrevAmt, loiRemarks, loiId);
	}

	@Override

	public TbLoiMasEntity getLoiIdbyAppno(Long applicationId) {

		return rtiRepository.getLoiIdByApplicationNo(applicationId);
	}

	// Defect #34042-->first appeal issue
	@Override
	@Transactional
	public ObjectionDetailsDto fetchRtiAppDetailByRefNo(String objectionReferenceNumber, Long orgId) {
		TbRtiApplicationDetails tbRtiApplicationDetails = rtiRepository
				.getRtiApplicationDetailsByRtiNo(objectionReferenceNumber);
		ObjectionDetailsDto objectionDetailsDto = new ObjectionDetailsDto();
		objectionDetailsDto.setApplicationDate(tbRtiApplicationDetails.getApmApplicationDate());
		objectionDetailsDto.setApmApplicationId(tbRtiApplicationDetails.getApmApplicationId());
		objectionDetailsDto.setTitle(Long.valueOf(tbRtiApplicationDetails.getFinalDispatchMode()));
		objectionDetailsDto.setDispatchNo(tbRtiApplicationDetails.getDispatchNo());
		objectionDetailsDto.setDispachDate(tbRtiApplicationDetails.getDispatchDt());
		objectionDetailsDto.setDeliveryDate(tbRtiApplicationDetails.getDispatchDate());

		return objectionDetailsDto;
	}

	@Override
	public Boolean checkWorkflowIsNotDefine(Organisation org, Long orgId, String serviceCode) {
		boolean status = false;
		try {
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(serviceCode, orgId);
			// final List<LookUp> bptPrefixList =
			// CommonMasterUtility.getListLookup(PrefixConstants.LookUp.BPT, org);
			/*
			 * List<LookUp> lookUp = bptPrefixList.stream() .filter(s ->
			 * Long.valueOf(s.getLookUpId()).equals(sm.getSmProcessId()))
			 * .collect(Collectors.toList()); if
			 * (lookUp.get(0).getLookUpCode().trim().equals("NA")) { status = true; }
			 */
			// Defect #81535
			WorkflowMas workflowMas = workflowRepo.getWorkFlowTypeByOrgDepartmentAndServiceIdForAllWardZone(orgId,
					sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId());
			if (workflowMas == null) {
				status = true;
			}
		} catch (Exception ex) {
			throw new FrameworkException("exception occurs while saving  form data", ex);
		}
		return status;
	}

	// add for getting for updating objection status after dispatch of first appeal
	@Override
	public TbObjectionEntity getObjectionDetailByApplicationId(Long orgId, Long appId, String serviceCod) {
		// Defect#102772
		ServiceMaster serMaster = serviceMasterService
				.getServiceByShortName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, orgId);
		TbObjectionEntity objEntity = objectionRepository.getObjectionDetailByAppId(orgId, appId,
				serMaster.getSmServiceId());
		if (objEntity != null) {
			// objEntity.setApmApplicationId(model.getReqDTO().getApmApplicationId());
			objEntity.setObjectionId(objEntity.getObjectionId());
			if (serviceCod != null && serviceCod.equals(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL)) {
				objEntity.setObjectionStatus(MainetConstants.TASK_STATUS_COMPLETED);
			}/* else if (serviceCod != null && serviceCod.equals(MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE)) {
				objEntity.setObjectionStatus(MainetConstants.RTISERVICE.TASK_STATUS_SECONDAPPEAL);
			}*/
			objectionRepository.save(objEntity);
		}

		return objEntity;
	}

	@Override
	@Transactional
	public RtiApplicationFormDetailsReqDTO saveServiceApplication(RtiApplicationFormDetailsReqDTO rtiDto) {
		if (rtiDto != null) {

			RequestDTO requestDto = setApplicantRequestDto(rtiDto);
			// creating Rti Application
			rtiDto.setUid(rtiDto.getUid());
			final Long applicationNo = applicationService.createApplication(rtiDto);
			rtiDto.setApmApplicationId(applicationNo);

			// Generate Rti Number

			String rtiNumber = generateRtiApplicationNumber(rtiDto.getApmApplicationId(), rtiDto.getServiceId(),
					rtiDto.getOrgId());
			rtiDto.setRtiNo(rtiNumber);

			rtiDto.setSmServiceId(rtiDto.getSmServiceId());
			rtiDto.setAadhaarNo(rtiDto.getAadhaarNo());
			TbRtiApplicationDetails tbRtiApplicationDetails = mapDTOToEntity(rtiDto);
			rtiApplicationServiceDAO.saveRtiApplicationForm(tbRtiApplicationDetails);
			// add code for saving history data
			TbRtiApplicationDetailsHistory rtiHistory = mapDTOToHistryEntity(rtiDto);
			histRepo.save(rtiHistory);
			String deptName = rtiApplicationServiceDAO.getdepartmentNameById(rtiDto.getDeptId());
			rtiDto.setDepartmentName(deptName);

			boolean checklist = false;
			if ((rtiDto.getDocumentList() != null) && !rtiDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(applicationNo);
				checklist = fileUploadService.doFileUpload(rtiDto.getDocumentList(), requestDto);
				checklist = true;
			}

			if ((rtiDto.getFetchDocs() != null) && !rtiDto.getFetchDocs().isEmpty()) {
				requestDto.setApplicationId(Long.valueOf(tbRtiApplicationDetails.getRtiNo()));
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo());
				fileUploadService.doFileUpload(rtiDto.getFetchDocs(), requestDto);
			}

			if ((rtiDto.getStampDoc() != null) && !rtiDto.getStampDoc().isEmpty()
					&& rtiDto.getStampDoc().get(0).getDocumentByteCode() != null) {
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo() + MainetConstants.FlagS);
				fileUploadService.doFileUpload(rtiDto.getStampDoc(), requestDto);
			}
//added regarding US#111612
			if ((rtiDto.getPostalDoc() != null) && !rtiDto.getPostalDoc().isEmpty()
					&& rtiDto.getPostalDoc().get(0).getDocumentByteCode() != null) {
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo() + MainetConstants.FlagP);
				fileUploadService.doFileUpload(rtiDto.getPostalDoc(), requestDto);
			}
			if (!CollectionUtils.isEmpty(rtiDto.getChlNonJudDoc())&&rtiDto.getChlNonJudDoc().get(0).getDocumentByteCode()!=null) {
				requestDto.setReferenceId(tbRtiApplicationDetails.getRtiNo() + MainetConstants.FlagN);
				fileUploadService.doFileUpload(rtiDto.getChlNonJudDoc(), requestDto);
			}
			
			if (rtiDto.isFree()) {

				rtiDto.getOfflineDTO().setDocumentUploaded(checklist);
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(rtiDto.getApmApplicationId());
				applicationData.setOrgId(rtiDto.getOrgId());
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL))
				applicationData.setIsCheckListApplicable(false);
				else
					applicationData.setIsCheckListApplicable(checklist);
				rtiDto.getApplicantDTO().setUserId(rtiDto.getUserId());
				rtiDto.getApplicantDTO().setServiceId(rtiDto.getSmServiceId());
				rtiDto.getApplicantDTO().setDepartmentId(Long.valueOf(rtiDto.getRtiDeptId()));
				if (rtiDto.getTrdWard1() != null) {
					rtiDto.getApplicantDTO().setDwzid1(rtiDto.getTrdWard1());
				}
				if (rtiDto.getTrdWard2() != null) {
					rtiDto.getApplicantDTO().setDwzid2(rtiDto.getTrdWard2());
				}
				if (rtiDto.getTrdWard3() != null) {
					rtiDto.getApplicantDTO().setDwzid3(rtiDto.getTrdWard3());
				}
				if (rtiDto.getTrdWard4() != null) {
					rtiDto.getApplicantDTO().setDwzid4(rtiDto.getTrdWard4());
				}
				if (rtiDto.getTrdWard5() != null) {
					rtiDto.getApplicantDTO().setDwzid5(rtiDto.getTrdWard5());
				}

				commonService.initiateWorkflowfreeService(applicationData, rtiDto.getApplicantDTO());
			}

		}
		return rtiDto;
	}

	@Override
	public void setSecondAppealDataByRtiNo(RtiApplicationFormDetailsReqDTO reqDto) {
		TbRtiApplicationDetails tbRtiAppl = rtiRepository.getLatestDataOfSecondAppeal(reqDto.getRtiNo());
		if (tbRtiAppl != null) {
			reqDto.setRtiDeciRecDate(tbRtiAppl.getRtiDeciRecDate());
			reqDto.setRtiPioActionDate(tbRtiAppl.getRtiPioActionDate());
			reqDto.setRtiPioAction(tbRtiAppl.getRtiPioAction());
			reqDto.setRtiLocationId(tbRtiAppl.getRtiLocationId());
			reqDto.setRtiDeciDet(tbRtiAppl.getRtiDeciDet());
			reqDto.setApmApplicationId(tbRtiAppl.getApmApplicationId());
			reqDto.setAppealType(tbRtiAppl.getAppealType());
			reqDto.setRtiSubject(tbRtiAppl.getRtiSubject());
			reqDto.setDeptId((long) tbRtiAppl.getRtiDeptId());
		}
	}

	@Override
	public List<DocumentDetailsVO> getCheckListData(Long appId, FileNetApplicationClient fClient, UserSession session) {
		List<DocumentDetailsVO> attachsList = new ArrayList<>();
		List<CFCAttachment> cfcAtt = new ArrayList();
		cfcAtt = iChecklistVerificationService.findAttachmentsForAppId(appId, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!cfcAtt.isEmpty()) {
			int count = 0;
			int count1 = 0;
			for (int j = count; j <= cfcAtt.size() - 1 + count; j++) {

				DocumentDetailsVO dvo = new DocumentDetailsVO();
				dvo.setAttachmentId(cfcAtt.get(count1).getAttId());
				dvo.setCheckkMANDATORY(cfcAtt.get(count1).getMandatory());
				dvo.setDocumentSerialNo(cfcAtt.get(count1).getClmSrNo());
				dvo.setDocumentName(cfcAtt.get(count1).getAttFname());
				dvo.setUploadedDocumentPath(cfcAtt.get(count1).getAttPath());
				dvo.setDoc_DESC_Mar(cfcAtt.get(count1).getClmDescEngl());
				dvo.setDoc_DESC_ENGL(cfcAtt.get(count1).getClmDescEngl());
				attachsList.add(dvo);
				count1++;

			}
		}
		return attachsList;
	}

	@Override
	@Transactional(readOnly = true)
	public RequestDTO getApplicationDetailsByMobile(String mobileNumber) {

		RequestDTO applicationDetail = null;

		Optional<CFCApplicationAddressEntity> add = cfcApplicationAddressRepository
				.findTopByApaMobilnoOrderByApmApplicationIdDesc(mobileNumber);
		if (add.isPresent()) {
			CFCApplicationAddressEntity address = add.get();
			TbCfcApplicationMstEntity app = cfcApplicationMasterService
					.getCFCApplicationByApplicationId(address.getApmApplicationId(), address.getOrgId().getOrgid());
			applicationDetail = RtiUtility.getApplicationDetails(app, address);
		}
		return applicationDetail;
	}

	@Override
	public String generateDispatchNo(Date estimateDate, Long orgId, Long deptId) {
		// get ULBCODE
		String ulbCode = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
				.findById(orgId).getOrgShortNm();
		// get financial by date
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).getFinanciaYearByDate(estimateDate);

		// get financial year formate
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

		// gerenerate sequence
		final Long seq = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.RTI, MainetConstants.RTISERVICE.TB_RTI_APPLICATION,
						MainetConstants.RTISERVICE.DISPATCH_NO, orgId, MainetConstants.CommonConstants.C,
						financiaYear.getFaYear());
		String num = seq.toString();
		final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.PADDING_THREE, Integer.parseInt(num));
		String deptCode = deptService.getDeptCode(deptId);

		String mbNumber = ulbCode + MainetConstants.WINDOWS_SLASH + deptCode + MainetConstants.WINDOWS_SLASH
				+ finacialYear + MainetConstants.WINDOWS_SLASH + paddingAppNo;
		return mbNumber;
	}

	@Override
	@WebMethod(exclude = true)
	public String generateTokenNo(Long applicationId, Long orgId) {

		final Long seq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.RTI,
				MainetConstants.RTISERVICE.TB_RTI_APPLICATION, MainetConstants.RTISERVICE.RTI_TOKEN_NO, orgId,
				MainetConstants.CommonConstants.C, null);
		final String num = seq.toString();
		final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.CD, Integer.parseInt(num));
		final String orgid = orgId.toString();
		return orgid.concat(paddingAppNo);

	}

//Defect#117838
	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.CHECK_WORKFLOW_EXIST_OR_NOT, notes = MainetConstants.TradeLicense.CHECK_WORKFLOW_EXIST_OR_NOT, response = Boolean.class)
	@Path("/checkWorkFlowExistOrNot")
	@Transactional(readOnly = true)
	@ResponseBody
	public Boolean checkWoflowDefinedOrNot(RtiApplicationFormDetailsReqDTO mastDto) {
		WorkflowMas mas = null;
		Boolean flag = false;
		Long deptId = deptService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, mastDto.getOrgId());

		try {
			mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(mastDto.getOrgId(), deptId,
					sm.getSmServiceId(), mastDto.getTrdWard1(), mastDto.getTrdWard2(), mastDto.getTrdWard3(),
					mastDto.getTrdWard4(), mastDto.getTrdWard5());
			flag = true;
		} catch (Exception e) {

		}
		if (mas == null || mas.equals(MainetConstants.BLANK)) {
			return flag;
		}
		return flag;
	}

	@Override
	public Boolean setWorkflowData(WorkflowTaskAction workflowAction, WorkflowProcessParameter workflowdto,
			RtiApplicationFormDetailsReqDTO reqDto) {
		// TODO Auto-generated method stub
		WorkflowMas mas = null;
		Boolean flag = false;
		Organisation org = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
				.getOrganisationById(workflowAction.getOrgId());
		Long deptId = deptService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI,
				PrefixConstants.STATUS_ACTIVE_PREFIX);
		try {
			// For fetching servicedetails
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE,
							workflowAction.getOrgId());
			if (sm != null && deptId != null) {
				// for checking work flow exist or not
				mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(workflowAction.getOrgId(), deptId,
						sm.getSmServiceId(), reqDto.getTrdWard1(), reqDto.getTrdWard2(), reqDto.getTrdWard3(),
						reqDto.getTrdWard4(), reqDto.getTrdWard5());
			}

			ServicesEventEntity eventMas = null;
			List<WorkflowDet> workFlowDet = new ArrayList<WorkflowDet>();
			if (mas != null) {
				// finding SMF_Id from Sysmodfunction table
				Long smfId = sysModeFuncService.findSmfIdBySmfaction(MainetConstants.RTISERVICE.RTI_PIO_SMS_EMAIL);
				if (smfId != null) {
					// fetching event details data by smfid, deptid,organisation ,serviceid to find
					// out the assigned employee for PIO response
					eventMas = iServiceEventRepository.findEventByEventId(smfId, deptId, org, sm.getSmServiceId());
				}
				String empLists = null;
				if (eventMas != null && eventMas.getServiceEventId() != 0) {
					// find the Details
					List<Object[]> workFlowDetList = workFlowMapRepo.getAllWorkFlowMappingByWfId(mas.getWfId(),
							workflowAction.getOrgId().longValue(), MainetConstants.FlagY);
					for (Object[] ob : workFlowDetList) {
						if (Long.valueOf(ob[1].toString()) == eventMas.getServiceEventId()) {
							empLists = ob[0].toString();
							break;
						}
					}

				}

				workflowAction.setForwardToEmployee(empLists);
				workflowAction.setForwardToEmployeeType(WorkFlow.EMPLOYEE);
				workflowAction.setOrgId(workflowAction.getOrgId());
				workflowAction.setWorkflowId(mas.getWfId());
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.info(" RTI Work flow  not found ..");
			flag = false;
		}
		return flag;

	}

	// US#139003
	@Override
	@Transactional(readOnly = true)
	public String resolveWorkflowTypeDefinition(Long orgId, Long deptId) {
		Long serviceId = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceIdByShortName(orgId, MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
		String workflowTypeDefinition = "";
		List<WorkflowMas> workflowTypeList = workflowTypeDAO.getAllWorkFlows(orgId, deptId, serviceId);

		Optional<WorkflowMas> mas = workflowTypeList.stream()
				.filter(wf -> wf != null && wf.getStatus().equals(MainetConstants.FlagY)).findFirst();
		if (mas.isPresent()) {
			workflowTypeDefinition = mas.get().getType();
		}
		return workflowTypeDefinition;
	}

	// US#139003
	@WebMethod(exclude = true)
	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {

		TbRtiApplicationDetails entity = rtiApplicationServiceDAO.getRtiApplicationDetailsForDSCL(applicationId, orgId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (entity != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (entity.getTrdWard1() != null) {
				wardZoneDTO.setAreaDivision1(entity.getTrdWard1());
			}
			if (entity.getTrdWard2() != null) {
				wardZoneDTO.setAreaDivision2(entity.getTrdWard2());
			}
			if (entity.getTrdWard3() != null) {
				wardZoneDTO.setAreaDivision3(entity.getTrdWard3());
			}
			if (entity.getTrdWard4() != null) {
				wardZoneDTO.setAreaDivision4(entity.getTrdWard4());
			}
			if (entity.getTrdWard5() != null) {
				wardZoneDTO.setAreaDivision5(entity.getTrdWard5());
			}
		}

		return wardZoneDTO;
	}

	private TbRtiApplicationDetailsHistory mapDTOToHistryEntity(RtiApplicationFormDetailsReqDTO requestDTO) {
		if (requestDTO == null) {
			return null;
		}

		final TbRtiApplicationDetailsHistory tbRtiApplicationDetails = map(requestDTO,
				TbRtiApplicationDetailsHistory.class);

		return tbRtiApplicationDetails;

	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Boolean saveRtiApplicationHistory(RtiApplicationFormDetailsReqDTO requestDTO) {

		TbRtiApplicationDetailsHistory tbRtiApplicationDetails = mapDTOToHistryEntity(requestDTO);
		histRepo.save(tbRtiApplicationDetails);

		return true;
	}

// Code added for showing history data on Pio response form as per 's demo points
	@Override
	@Transactional(readOnly = true)
	public List<RtiRemarksHistDto> getRtiActionLogByApplicationId(final Long applicationId, Long orgId, long langId) {
		List<TbRtiApplicationDetailsHistory> actions = histRepo.findByAppId(applicationId);
		List<RtiRemarksHistDto> histdtoList = new ArrayList<RtiRemarksHistDto>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		actions.stream().forEach(action -> {
			/* D#115566 setting full employee name */
			RtiRemarksHistDto dto = new RtiRemarksHistDto();

			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(action.getRtiAction());
			LOGGER.info("Getting Emp Details for empId :" + action.getUserId());
			Employee employee = employeeService.findEmployeeById(action.getUserId());
			if (employee != null) {
				dto.setActionBy(employee.getEmpname() + " " + employee.getEmplname());
				dto.setActionByDesg(employee.getDesignation().getDsgname());
				dto.setEmail(employee.getEmpemail());
			}
			dto.setActionname(lookUp.getLookUpDesc());
			if (lookUp != null) {
				dto.setActionname(lookUp.getLookUpDesc());
			}
			if (lookUp != null && (lookUp.getLookUpCode() != null
					&& lookUp.getLookUpCode().equals(MainetConstants.AuthStatus.FORWARDTODEPARTMENT))) {
				dto.setDocList(iChecklistVerificationService.getDocumentUploadedByRefNoAndUserId(
						action.getRtiNo() + MainetConstants.DEPT_SHORT_NAME.RTI, action.getOrgId(),
						action.getUserId()));
			}
			dto.setRemarks(action.getRtiRemarks());
			dto.setApplicationDate(action.getUpdateDate());

			histdtoList.add(dto);
		});
		return histdtoList;
	}

	@Override
	public void initiateWorkFlowForFrdEmploye(RtiApplicationFormDetailsReqDTO reqDTO, Long serviceId, String remList) {

	
		ApplicationMetadata applicationData = new ApplicationMetadata();
		applicationData.setApplicationId(reqDTO.getApmApplicationId());
		applicationData.setOrgId(reqDTO.getOrgId());
		
		
		applicationData.setIsCheckListApplicable(false);
		//applicationData.setReferenceId(reqDTO.getApmApplicationId()+"_"+serviceId);
		reqDTO.getApplicantDTO().setServiceId(serviceId);
		reqDTO.getApplicantDTO().setDepartmentId(departmentService.getDepartmentIdByDeptCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICENAME));
		//reqDTO.getApplicantDTO().setComment(remList);
	
		// D#77334 code for mobile please don't remove
		commonService.initiateWorkflowfreeService(applicationData, reqDTO.getApplicantDTO());
	
		
	}
	@Override
	public List<RtiMediaListDTO> setLoiDetails(Long rtiNo, long orgid) {
		List<RtiMediaListDTO> loiDet=new ArrayList<>();
	
				List<TbRtiMediaDetails> loidetList=rtiRepository.getMediaListByLoiId(rtiNo, orgid);
				for(TbRtiMediaDetails loiDets:loidetList) {
					RtiMediaListDTO det=new RtiMediaListDTO();
					det.setMediaDesc(rtiUtility.getPrefixDesc(PrefixConstants.MEDIA_TYPE,
							Long.valueOf(loiDets.getMediaType())));
					det.setQuantity(loiDets.getMediaQuantity());
					det.setMediaAmount(loiDets.getMediaAmount());
					det.setMediaTypeDesc(employeeService.getEmpNameByEmpId(loiDets.getUserId()));
					loiDet.add(det);
					
					
				
			}
		
		return loiDet;
	}

	@Override
	public List<RtiFrdEmployeeDetails> setRtiFwdEmployeeDetails(Long applicationId) {
		List<TbRtiFwdEmployeeEntity> frdEMpList=new ArrayList<>();
		List<RtiFrdEmployeeDetails> frdempListDet=new ArrayList<>();
		frdEMpList=rtiForwardRepository.getRtiFOrwardedEmployeeDetails(applicationId);
		List<TbRtiApplicationDetailsHistory> rtiHistList=histRepo.findByAppId(applicationId);
		if(!CollectionUtils.isEmpty(frdEMpList)) {
			for(TbRtiFwdEmployeeEntity empEnt:frdEMpList) {
				RtiFrdEmployeeDetails det=new RtiFrdEmployeeDetails();
				StringBuilder builder=new StringBuilder();
				if(empEnt.getFwdEmpId()!=null)
				det.setEmpDesg(serviceMasterService.getServiceNameByServiceId(empEnt.getFwdEmpId()));
				det.setEmpRematk(empEnt.getEmpFwdRemark());
				det.setDeptName(empEnt.getSlADays());
				if(!CollectionUtils.isEmpty(rtiHistList)) {
					for (TbRtiApplicationDetailsHistory entity : rtiHistList) {
						if(entity.getUserId()!=null) {
						EmployeeBean bean = employeeService.findById(entity.getUserId());
						if (bean != null && det.getEmpDesg().contains(bean.getEmpname())) {

								if (entity.getRtiAction() != 0)
									builder.append(rtiUtility.getPrefixDesc(PrefixConstants.ACTION,
											(long) entity.getRtiAction()));
								if (entity.getLoiApplicable() != null
										&& StringUtils.isNumeric(entity.getLoiApplicable()))
									builder.append(" / " + rtiUtility.getPrefixDesc(PrefixConstants.LOI_APPLICABLE,
											Long.valueOf(entity.getLoiApplicable())));
								if (entity.getReasonForLoiNa() != null)
									builder.append(" / " + entity.getReasonForLoiNa());

							
						}
						if (builder != null && !builder.toString().isEmpty())
							det.setEmpName(builder.toString());
						else
							det.setEmpName("No action performed yet !");
					}}}
				frdempListDet.add(det);
			}
		}
		return frdempListDet;
	}

}
