package com.abm.mainet.bnd.service;
import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
import com.abm.mainet.bnd.dao.BirthRegDao;
import com.abm.mainet.bnd.dao.IssuenceOfBirthCertificateDao;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.domain.BirthCertificateEntity;
import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.TbBdCertCopy;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.ParentDetailDTO;
import com.abm.mainet.bnd.repository.BirthCertificateRepository;
import com.abm.mainet.bnd.repository.BirthDeathCertificateCopyRepository;
import com.abm.mainet.bnd.repository.BirthDeathCfcInterfaceRepository;
import com.abm.mainet.bnd.repository.BirthRegRepository;
import com.abm.mainet.bnd.repository.CertCopyRepository;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.ParentDetHistRepository;
import com.abm.mainet.bnd.ui.model.BirthRegistrationCertificateModel;
import com.abm.mainet.bnd.ui.model.NacForBirthRegModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
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
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author vishwanath.s
 *
 */
@Service
@WebService(endpointInterface="com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService")
@Produces(value= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE})
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Api(value = "/issuanceBirthCertService")
@Path(value="/issuanceBirthCertService")
public class IssuenceOfBirthCertificateServiceImpl implements IssuenceOfBirthCertificateService {

	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private static final Logger LOGGER = LoggerFactory.getLogger(IssuenceOfBirthCertificateServiceImpl.class);
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private TbTaxMasService taxMasService;
	
	@Autowired
	private ReceiptRepository receiptRepository;

	@Resource
	private IssuenceOfBirthCertificateDao issuenceOfBirthCertificateDao;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Resource
	private BirthRegRepository birthRegRepository;
	
	@Resource
	private BirthDeathCertificateCopyRepository birthDeathCertificateCopyRepository;
	
	
	 @Resource
	 private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Resource
	private CfcInterfaceJpaRepository tbBdCfcInterfaceJpaRepository;
	
	@Autowired
	private CertCopyRepository certCopyRepository;
	
	@Autowired
	private IChallanService challanService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Resource
    private TbDepartmentJpaRepository tbDepartmentJpaRepository;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private BirthCertificateRepository birthCerRepro;
	
	@Autowired
	private IOrganisationDAO organisationDAO;
	
	@Autowired
	private BirthRegDao birthRegDao;
	
	@Autowired
	private BirthDeathCfcInterfaceRepository  birthDeathCfcInterfaceRepository;
	
	@Autowired
	private IRtsService irtsService;
	
	@Autowired
	private ParentDetHistRepository parentDetHistRepo;
	
    @Resource
    private IReceiptEntryService receiptEntryService;
	
	@Override
	@Transactional(readOnly = true)
	@Path(value="/SearchBirth")
	@ApiOperation(value = "get application detail", response = BirthRegistrationDTO.class)
	@GET
	public BirthRegistrationDTO getBirthRegisteredAppliDetail(@QueryParam("certNo")String certNo, @QueryParam("regNo")String regNo, @QueryParam("regDate") String regDate,
			@QueryParam("applicnId")String applicnId, @QueryParam("orgId")Long orgId) {
		//String birthWFStatus ="APPROVED";
		BirthRegistrationEntity DetailEntity = issuenceOfBirthCertificateDao.getBirthRegisteredApplicantList(certNo,
				regNo, regDate, applicnId, orgId);
		//Long noOfCopies = issuenceOfBirthCertificateDao.getNoOfRequestCopies(applicnId, orgId);
		
		
		BirthRegistrationDTO dto = new BirthRegistrationDTO();
		ParentDetailDTO pDto = new ParentDetailDTO();
		if (DetailEntity != null) {
		LookUp lokkup = null;
		if (DetailEntity.getBrSex() != null) {
			lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(DetailEntity.getBrSex()),
					orgId, GENDER);
		}
		
			//dto = new BirthRegistrationDTO();
			//pDto = new ParentDetailDTO();
			BeanUtils.copyProperties(DetailEntity, dto);
			dto.setBirthWfStatus(DetailEntity.getBirthWFStatus());
			if(DetailEntity.getNoOfCopies()!=null) {
				dto.setAlreayIssuedCopy(DetailEntity.getNoOfCopies());	
			}else {
				dto.setAlreayIssuedCopy(0L);
			}
			dto.setBrCertNo(String.valueOf(DetailEntity.getNoOfCopies()));
			BeanUtils.copyProperties(DetailEntity.getParentDetail(), pDto);
			dto.setParentDetailDTO(pDto);
			dto.setBrSex(lokkup.getDescLangFirst());
			dto.setBrSexMar(lokkup.getDescLangSecond());
			dto.setNoOfCopies(null);
		}
		if (DetailEntity.getWardid() != null && DetailEntity.getWardid() != 0) {
			dto.setWardDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(DetailEntity.getWardid()),
					orgId, "BWD").getDescLangFirst());
			dto.setWardDescReg(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(DetailEntity.getWardid()),
					orgId, "BWD").getDescLangSecond());
		}
		/*
		 * if(noOfCopies!=null) { dto.setNoOfCopies(noOfCopies); } else {
		 * dto.setNoOfCopies(0L); }
		 */
	
		return dto;
	}
	
	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public BirthRegistrationDTO getBirthIssueRegisteredAppliDetail(@QueryParam("certNo")String certNo, @QueryParam("regNo")String regNo, @QueryParam("regDate") String regDate,
			@QueryParam("applicnId")String applicnId, @QueryParam("orgId")Long orgId) {
		//String birthWFStatus ="APPROVED";
		BirthRegistrationEntity DetailEntity = issuenceOfBirthCertificateDao.getBirthRegisteredApplicantList(certNo,
				regNo, regDate, applicnId, orgId);
		Long noOfCopies = issuenceOfBirthCertificateDao.getNoOfRequestCopies(applicnId, orgId);
		
		
		BirthRegistrationDTO dto = new BirthRegistrationDTO();
		ParentDetailDTO pDto = new ParentDetailDTO();
		if (DetailEntity != null) {
		LookUp lokkup = null;
		if (DetailEntity.getBrSex() != null) {
			lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(DetailEntity.getBrSex()),
					orgId, GENDER);
		}
			//dto = new BirthRegistrationDTO();
			//pDto = new ParentDetailDTO();
			BeanUtils.copyProperties(DetailEntity, dto);
			dto.setBirthWfStatus(DetailEntity.getBirthWFStatus());
				Long sum = 0L;
				
				sum = DetailEntity.getNoOfCopies() != null && DetailEntity.getBrManualCertNo() != null
						? DetailEntity.getNoOfCopies() + DetailEntity.getBrManualCertNo()
						: dto.getNoOfCopies() != null ? sum + dto.getNoOfCopies()
								: DetailEntity.getBrManualCertNo() != null ? sum + DetailEntity.getBrManualCertNo()
										: sum;

				dto.setAlreayIssuedCopy(sum);
			
			dto.setBrCertNo(String.valueOf(DetailEntity.getNoOfCopies()));
			BeanUtils.copyProperties(DetailEntity.getParentDetail(), pDto);
			dto.setParentDetailDTO(pDto);
			dto.setBrSex(lokkup.getLookUpDesc());
			dto.setBrSexMar(lokkup.getDescLangSecond());
		}
		if(noOfCopies!=null) {
			dto.setNoOfCopies(noOfCopies);
		}
		else {
			dto.setNoOfCopies(0L);
		}
	
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude=true)
	public Long saveIssuanceOfBirtCert(@RequestBody BirthRegistrationDTO requestDTO,BirthRegistrationCertificateModel model) {
		final RequestDTO commonRequest = requestDTO.getRequestDTO();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy=UserSession.getCurrent().getEmployee().getEmpId();
		requestDTO.setOrgId(orgId);
		requestDTO.setBirthWfStatus(BndConstants.OPEN);
		String birthWfStatus= requestDTO.getBirthWfStatus();
		try {
  			if( model.getOfflineDTO().getPayModeIn()!=null) {
  			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getOfflineDTO().getPayModeIn());
  			if (lookup != null && lookup.getLookUpCode().equals("POS")) {
  				CommonChallanDTO dto=irtsService.createPushToPayApiRequest(model.getOfflineDTO(), createdBy, orgId,"IBC" , model.getOfflineDTO().getAmountToPay());
  				if(dto!=null && dto.getPushToPayErrMsg()!=null) {
  					model.addValidationError(dto.getPushToPayErrMsg());
  					return null;
  				}
  			}}
  		} catch (Exception e) {
  			// TODO: handle exception
  		}
		// Get the serviceId from service.
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("IBC", requestDTO.getOrgId());
		if (serviceMas != null) {
			commonRequest.setOrgId(requestDTO.getOrgId());
			commonRequest.setServiceId(serviceMas.getSmServiceId());
			commonRequest.setApmMode("F");
			commonRequest.setUserId(requestDTO.getUserId());
			commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
			
			
		}
		 //update the certificate no copies 
		 birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(),birthWfStatus);//change
		// Generate the Application Number #111859 By Arun
		commonRequest.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,PrefixConstants.STATUS_ACTIVE_PREFIX));
		commonRequest.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
		commonRequest.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);		
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String monthStr = localDate.getMonthValue() < 10 ? "0"+localDate.getMonthValue() : String.valueOf(localDate.getMonthValue());
		String dayStr = localDate.getDayOfMonth() < 10 ? "0"+localDate.getDayOfMonth() : String.valueOf(localDate.getDayOfMonth());
		commonRequest.setCustomField(String.valueOf(monthStr+""+dayStr));
		///
		final Long applicationId = applicationService.createApplication(commonRequest);
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApmApplicationId(applicationId);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setLmodDate(new Date());
		birthDeathCFCInterface.setUpdatedBy(requestDTO.getUpdatedBy());
		birthDeathCFCInterface.setUserId(requestDTO.getUpdatedBy());
		birthDeathCFCInterface.setUpdatedDate(new Date());
		birthDeathCFCInterface.setBdRequestId(requestDTO.getBrId());
		birthDeathCFCInterface.setLgIpMac(requestDTO.getLgIpMac());
		birthDeathCFCInterface.setLgIpMacUpd(requestDTO.getLgIpMacUpd());
		birthDeathCFCInterface.setCopies(requestDTO.getNoOfCopies());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		//birthReg.getParentDetail().setPdBrId(birthReg.getBrId());
		birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		//model.getBirthRegDto().setApplicationId(String.valueOf(applicationId));
		//when BPM is not applicable
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),requestDTO.getOrgId());
		if(processName==null) {
			requestDTO.setApplicationId(String.valueOf(applicationId));
			updateBirthApproveStatus(requestDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
			updateBirthWorkFlowStatus(requestDTO.getBrId(), MainetConstants.WorkFlow.Decision.APPROVED, orgId, "A");
			updatNoOfcopyStatus(requestDTO.getBrId(), orgId, requestDTO.getBrId(), requestDTO.getNoOfCopies());
		}
		 if(serviceMas.getSmFeesSchedule()==0 || requestDTO.getAmount()==0.0)
			{
			  initializeWorkFlowForFreeService(requestDTO);
			}else {
				setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model);
			}
		 
		    Organisation organisation = organisationDAO.getOrganisationById(requestDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		    if(requestDTO.getLangId()==1) {
				requestDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceName());
			}
			else {
				requestDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceNameMar());
			}
			smsAndEmail(requestDTO, organisation);
		return applicationId;
	}

	@Override
	@Path(value="/getBndCharge")
	@POST
	@Transactional
	public WSResponseDTO getBndCharge(@RequestBody WSRequestDTO wSRequestDTO) {
		LOGGER.info("brms BND date getcertificateCharges execution start..");
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
		LOGGER.info("brms BND date getcertificateCharges execution End..");
		
	return null;
	}

	@Override
	@Path(value="/getBndApplicableTax")
	@ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
	@POST
	@Transactional
	public WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms Bnd getApplicableTaxes execution start..");
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
		LOGGER.info("brms Bnd getApplicableTaxes execution end..");
		
		return responseDTO;
	}
	
	private WSResponseDTO populateOtherFieldsForServiceCharge(BndRateMaster bndRateMaster,
			WSResponseDTO responseDTO) throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<BndRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(bndRateMaster.getServiceCode(),
				bndRateMaster.getOrgId());
		if ((serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))||(serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES))) {
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
			//roadCuttingRateMasters.setChargeDescEng(entity.getTaxDesc());
			//roadCuttingRateMasters.setChargeDescReg(entity.getTaxDesc());
			settingTaxCategories(BNDRateMasters, entity, organisation);
			//roadCuttingRateMasters.setTaxId(entity.getTaxId());
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
	
	public String getAmount(Long noOfDays,LinkedHashMap<String,String> charges) {
		 String slab1=String.valueOf(charges.get("slab1"));
		 String slab2=String.valueOf(charges.get("slab2"));
		 String slab3=String.valueOf(charges.get("slab3"));
		if(noOfDays <= Long.valueOf(slab1)) {
           return String.valueOf(charges.get("slabRate1")) ;
           }
           else if(noOfDays>Long.valueOf(slab1) & noOfDays<=Long.valueOf(slab2)) {
        	   return String.valueOf(charges.get("slabRate2")) ;
           }
           else if(noOfDays>Long.valueOf(slab2) & noOfDays<=Long.valueOf(slab3)) {
        	   return String.valueOf(charges.get("slabRate3")) ;
           }
           else {
        	   return String.valueOf(charges.get("slabRate4")) ;
           }
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url, 
			String workFlowFlag) {
		try {
			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			ApplicationMetadata applicationMetadata = new ApplicationMetadata();
			applicationMetadata.setApplicationId(workflowActionDto.getApplicationId());
			//applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
			applicationMetadata.setOrgId(workflowActionDto.getOrgId());
			applicationMetadata.setWorkflowId(workFlowMas.getWfId());
			applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);
			//applicationMetadata.setIsScrutinyApplicable(true);
			//applicationMetadata.setIsFreeService(true);
			ApplicationSession appSession = ApplicationSession.getInstance();
			TaskAssignment assignment = new TaskAssignment();
			assignment.setActorId(workflowActionDto.getEmpId().toString());
			assignment.addActorId(workflowActionDto.getEmpId().toString());
			assignment.setOrgId(workflowActionDto.getOrgId());
			assignment.setServiceEventId(-1L);
			String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
			assignment.setServiceEventName(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));
			assignment.setServiceEventNameReg(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));
			assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
			assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
			assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
			assignment.setServiceId(workFlowMas.getService().getSmServiceId());
			assignment.setServiceName(workFlowMas.getService().getSmServiceNameMar());
			assignment.setServiceEventNameReg(workFlowMas.getService().getSmServiceNameMar());
			assignment.setUrl(url);
			workflowProcessParameter.setRequesterTaskAssignment(assignment);
			workflowProcessParameter.setApplicationMetadata(applicationMetadata);
			workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).initiateWorkflow(workflowProcessParameter);

		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
		}
		return null;

	}

	@Override
    @Transactional
    public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction) {
        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
        try {
        	ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
        return null;
    }
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String updateBirthApproveStatus(BirthRegistrationDTO birthRegDTO, String status, String lastDecision){
    List<BirthDeathCFCInterface> birthDeathCFCInterface = birthDeathCfcInterfaceRepository.findData(Long.valueOf(birthRegDTO.getApplicationId()));
		if ((birthDeathCFCInterface.get(0).getCopies() != 0)&& (birthDeathCFCInterface.get(0).getCopies() != null)) {
		String finalcertificateNo = null;
		TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
		List<BirthRegistrationEntity> tbBirthregentity = birthRegDao.getBirthRegApplnData(birthRegDTO.getBrId(), birthRegDTO.getOrgId());

		String certnum = null;
		if ((!tbBirthregentity.isEmpty())) {
			certnum = tbBirthregentity.get(0).getBrCertNo();
		}
		if ((certnum == null)||(certnum.equals("0"))) {
		 Long alreadyIssuCopy=birthRegDTO.getAlreayIssuedCopy();
		 if(alreadyIssuCopy==null) {
	    	 alreadyIssuCopy=0L;
	     }
		 
			SequenceConfigMasterDTO configMasterDTO = null;
			Long deptId = departmentService.getDepartmentIdByDeptCode(BndConstants.BND, PrefixConstants.STATUS_ACTIVE_PREFIX);
	        configMasterDTO = seqGenFunctionUtility.loadSequenceData(birthRegDTO.getOrgId(), deptId,
	        		BndConstants.TB_BIRTHREG, BndConstants.BR_CERT_NO);
	        if (configMasterDTO.getSeqConfigId() == null) {
	        	Long certificateNo = seqGenFunctionUtility.generateSequenceNo(BndConstants.BND, BndConstants.TB_BD_CERT_COPY, BndConstants.CERT_NO,
	        			birthRegDTO.getOrgId(), "Y", null);

				String financialYear = UserSession.getCurrent().getCurrentDate();
				finalcertificateNo = "HQ/" + financialYear.substring(6, 10) + "/"
						+ new DecimalFormat("000000").format(certificateNo);
			} else {
				CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
				finalcertificateNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
			}
			    tbBdCertCopy.setOrgid(birthRegDTO.getOrgId());
			    tbBdCertCopy.setBrId(birthRegDTO.getBrId());
			    tbBdCertCopy.setCopyNo(birthRegDTO.getNoOfCopies());
			    tbBdCertCopy.setUpdatedBy(birthRegDTO.getUserId());
			    tbBdCertCopy.setUpdatedDate(birthRegDTO.getUpdatedDate());
			    tbBdCertCopy.setUserId(birthRegDTO.getUserId());
			    tbBdCertCopy.setLgIpMac(birthRegDTO.getLgIpMac());
			    tbBdCertCopy.setLgIpMacUpd(birthRegDTO.getLgIpMacUpd());
			    tbBdCertCopy.setCertNo(finalcertificateNo);
			    tbBdCertCopy.setLmoddate(new Date()); 
			    tbBdCertCopy.setStatus("N");
			    tbBdCertCopy.setAPMApplicationId(Long.valueOf(birthRegDTO.getApplicationId()));
			   
				birthDeathCertificateCopyRepository.save(tbBdCertCopy);
				birthRegRepository.updateBrCertNo(birthRegDTO.getBrId(), birthRegDTO.getOrgId(), finalcertificateNo);
				return finalcertificateNo;
		}else {
			if(certnum!=null) {
				finalcertificateNo = certnum;
				tbBdCertCopy.setOrgid(birthRegDTO.getOrgId());
			    tbBdCertCopy.setBrId(birthRegDTO.getBrId());
			    tbBdCertCopy.setCopyNo(birthRegDTO.getNoOfCopies());
			    tbBdCertCopy.setUpdatedBy(birthRegDTO.getUserId());
			    tbBdCertCopy.setUpdatedDate(birthRegDTO.getUpdatedDate());
			    tbBdCertCopy.setUserId(birthRegDTO.getUserId());
			    tbBdCertCopy.setLgIpMac(birthRegDTO.getLgIpMac());
			    tbBdCertCopy.setLgIpMacUpd(birthRegDTO.getLgIpMacUpd());
			    tbBdCertCopy.setCertNo(finalcertificateNo);
			    tbBdCertCopy.setLmoddate(new Date()); 
			    tbBdCertCopy.setStatus("N");
			    tbBdCertCopy.setAPMApplicationId(Long.valueOf(birthRegDTO.getApplicationId()));
			    birthDeathCertificateCopyRepository.save(tbBdCertCopy);
			   birthRegRepository.updateBrCertNo(birthRegDTO.getBrId(), birthRegDTO.getOrgId(), finalcertificateNo);
			    return finalcertificateNo;
			}
		}
	    //Generate the Certificate Number end
		}
		return null;
 }
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateBirthWorkFlowStatus(Long drId, String taskNamePrevious, Long orgId,String brStatus) {
		birthRegRepository.updateWorkFlowStatus(drId, orgId, taskNamePrevious,brStatus);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updatNoOfcopyStatus(Long brId, Long orgId,Long bdId, Long noOfCopies) {
		/*String status ="P";
		
		TbBdCertCopy tbBdCertCopy=certCopyRepository.findOne(bdId);
		if(tbBdCertCopy.getStatus().equals("N")) {
		birthRegRepository.updatNoOfcopyStatus(brId, orgId, noOfCopies);
		certCopyRepository.updateStatus(bdId, orgId, status);
		}*/
		birthRegRepository.updatNoOfcopyStatus(brId, orgId, noOfCopies);
		}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline,
			BirthRegistrationCertificateModel birthRegModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.IBC,
				birthRegModel.getBirthRegDto().getOrgId());
		offline.setApplNo(Long.valueOf(birthRegModel.getBirthRegDto().getApmApplicationId()));
		offline.setAmountToPay(birthRegModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ", Arrays.asList(birthRegModel.getRequestDTO().getfName(),
				birthRegModel.getRequestDTO().getmName(), birthRegModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		String wardName = "";
		 if(birthRegModel.getRequestDTO().getWardNo()!=null  &&  birthRegModel.getRequestDTO().getWardNo()!=0L) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(birthRegModel.getRequestDTO().getWardNo()).getLookUpDesc();
		 }
		 String pinCode = "";
		 if(birthRegModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(birthRegModel.getRequestDTO().getPincodeNo());
		 }
		String applicantAddress = String.join(" ",
				Arrays.asList(birthRegModel.getRequestDTO().getBldgName(),
						birthRegModel.getRequestDTO().getBlockName(), birthRegModel.getRequestDTO().getRoadName(),wardName,
						birthRegModel.getRequestDTO().getCityName(),pinCode));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(birthRegModel.getRequestDTO().getMobileNo());
		offline.setEmailId(birthRegModel.getRequestDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setPosPayApplicable(true);

		if (birthRegModel.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : birthRegModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

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
			printDto.setSubject(printDto.getSubject()+" - "+birthRegModel.getBirthRegDto().getBrRegNo());
			printDto.setDwz1(birthRegModel.getBirthRegDto().getBudgetCode());
			printDto.setDwz1L(CommonMasterUtility
					.getNonHierarchicalLookUpObject(birthRegModel.getBirthRegDto().getWardid(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode());
			birthRegModel.setReceiptDTO(printDto);
			birthRegModel.setSuccessMessage(birthRegModel.getAppSession().getMessage("adh.receipt"));
		}
		
	}

	@Override
	public Long saveIssuanceOfBirtCert(BirthRegistrationDTO birthRegDto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TbServiceReceiptMasBean getReceiptNo(Long applicationNo, Long orgId) {
		 TbServiceReceiptMasBean dto=new TbServiceReceiptMasBean();
		// Long app=Long.parseLong(applicationNo);
		 TbServiceReceiptMasEntity entity= receiptRepository.getReceiptDetailsByAppId(applicationNo,orgId);
		 if(entity!=null) {
		 BeanUtils.copyProperties(entity, dto);
		 if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
		 dto.setRmReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(entity.getFieldId(), entity.getSmServiceId(), entity.getSmServiceId(), entity.getRmDate(), entity.getOrgId()));
		  }
		 }
		return dto;
	}
	
	private void initializeWorkFlowForFreeService(BirthRegistrationDTO requestDto) {
		boolean checkList = false;
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.IBC,
				requestDto.getOrgId());
		if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
			checkList = true;
		}
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();

		applicantDto.setApplicantFirstName(requestDto.getParentDetailDTO().getPdFathername());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(BndConstants.BIRTH_DEATH));
		applicantDto.setMobileNo("NA");
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
	@Transactional(rollbackFor = Exception.class)
	@POST
	@Path("/saveIssuanceOfBirtCertFromPortal")
	public BirthRegistrationDTO saveIssuanceOfBirtCertFromPortal(@RequestBody BirthRegistrationDTO requestDTO) {
		final RequestDTO commonRequest = requestDTO.getRequestDTO();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		Long orgId = requestDTO.getOrgId();
		requestDTO.setOrgId(orgId);
		requestDTO.setBirthWfStatus(BndConstants.OPEN);
		String birthWfStatus= requestDTO.getBirthWfStatus();
		// Get the serviceId from service.
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("IBC", requestDTO.getOrgId());
		if (serviceMas != null) {
			commonRequest.setOrgId(requestDTO.getOrgId());
			commonRequest.setServiceId(serviceMas.getSmServiceId());
			commonRequest.setApmMode("F");
			//commonRequest.setUserId(requestDTO.getUserId());
			commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
			requestDTO.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
			commonRequest.setMobileNo(requestDTO.getMobileNo());
			
		}
		 //update the wfstatus 
		 birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(),birthWfStatus);//change
		// Generate the Application Number #111859 By Arun
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
		///
		final Long applicationId = applicationService.createApplication(commonRequest);
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApmApplicationId(applicationId);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setLmodDate(new Date());
		birthDeathCFCInterface.setUpdatedBy(requestDTO.getUpdatedBy());
		birthDeathCFCInterface.setUserId(requestDTO.getUpdatedBy());
		birthDeathCFCInterface.setUpdatedDate(new Date());
		birthDeathCFCInterface.setBdRequestId(requestDTO.getBrId());
		birthDeathCFCInterface.setLgIpMac(requestDTO.getLgIpMac());
		birthDeathCFCInterface.setLgIpMacUpd(requestDTO.getLgIpMacUpd());
		birthDeathCFCInterface.setCopies(requestDTO.getNoOfCopies());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		//birthReg.getParentDetail().setPdBrId(birthReg.getBrId());
		birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		//model.getBirthRegDto().setApplicationId(String.valueOf(applicationId));
		//when BPM is not applicable
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),requestDTO.getOrgId());
		if(processName==null) {
			requestDTO.setApplicationId(String.valueOf(applicationId));
			updateBirthApproveStatus(requestDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
			updateBirthWorkFlowStatus(requestDTO.getBrId(), MainetConstants.WorkFlow.Decision.APPROVED, orgId, "A");
		}
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		 if(serviceMas.getSmFeesSchedule()==0 || requestDTO.getAmount()==0.0)
			{
			  initializeWorkFlowForFreeService(requestDTO);
			}
		Organisation organisation = organisationDAO.getOrganisationById(requestDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		requestDTO.setUserId(requestDTO.getRequestDTO().getUserId());
		if (requestDTO.getLangId() == 1) {
			requestDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceName());
		} else {
			requestDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceNameMar());
		}
		smsAndEmail(requestDTO, organisation);
		return requestDTO;
	}
	
	@Override
	@POST
	@Path("/saveNacBirthCertificate")
	@WebMethod(exclude = true)
	@Transactional
	public BirthCertificateDTO saveBirthCertificate(BirthCertificateDTO birthCertificateDto,
			NacForBirthRegModel model) {
		BirthCertificateEntity birthcer = new BirthCertificateEntity();
		final RequestDTO commonRequest = model.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.NBR, birthCertificateDto.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setOrgId(birthCertificateDto.getOrgId());
		commonRequest.setApmMode(MainetConstants.FlagF);
		if (serviceMas.getSmFeesSchedule() == 0) {
			commonRequest.setPayStatus(BndConstants.PAYSTSTUSFREE);
		} else {
			commonRequest.setPayStatus(BndConstants.PAYSTATUSCHARGE);
		}
		commonRequest.setUserId(birthCertificateDto.getUserId());

		// Generate the Application Number #111859 By Arun
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
		///
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
			birthCertificateDto.setBirthWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
		}
		else {
			birthCertificateDto.setBrStatus(BndConstants.STATUSOPEN);
			birthCertificateDto.setBirthWfStatus(MainetConstants.WorkFlow.Status.PENDING);
		}
		BeanUtils.copyProperties(birthCertificateDto, birthcer);
		
		birthcer.setSmServiceId(serviceMas.getSmServiceId());
		BirthCertificateEntity saveBirth = birthCerRepro.save(birthcer);
      if (processName == null) {
    	  birthCertificateDto.setBrRId(saveBirth.getBrRId());
			String certificateno = generateCertificate(birthCertificateDto, MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
		}
		
		if (serviceMas.getSmFeesSchedule() == 0) {
			initializeWorkFlowForFreeService(birthCertificateDto);
		} else {
			setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smsAndEmailNac(birthCertificateDto,organisation);
		return birthCertificateDto;
	}	
	
	public void initializeWorkFlowForFreeService(BirthCertificateDTO requestDto) {
		boolean checkList = false;
		if (CollectionUtils.isNotEmpty(requestDto.getUploadDocument())) {
			checkList = true;
		}
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.NBR, requestDto.getOrgId());
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getPdFathername());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(BndConstants.BIRTH_DEATH));
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
	
	@Transactional
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, NacForBirthRegModel birthRegModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.NBR,
				birthRegModel.getNacForBirthRegDTO().getOrgId());
		offline.setApplNo(birthRegModel.getNacForBirthRegDTO().getApmApplicationId());
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
		 if(birthRegModel.getRequestDTO().getWardNo()!=0L && birthRegModel.getRequestDTO().getWardNo()!=null) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(birthRegModel.getRequestDTO().getWardNo()).getLookUpDesc();
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
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

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

	@Override
	public String generateCertificate(BirthCertificateDTO birthCertificateDTO, String lastDecision, String status) {
		Long brId = birthCertificateDTO.getBrRId();
		if ((birthCertificateDTO.getNoOfCopies() != 0) && (birthCertificateDTO.getNoOfCopies() != null)) {

			List<TbBdCertCopy> certCopy = certCopyRepository.findDeathCertData(brId);
			String finYear = UserSession.getCurrent().getCurrentDate();

			String certnum = null;
			String certnumcopy = null;
			if ((!certCopy.isEmpty())) {
				certnum = certCopy.get(0).getCertNo();
				certnumcopy = certnum.substring(3, 7);
			}

			if ((certnum == null) || (!certnumcopy.equals(finYear.substring(6, 10)))) {
				String finalcertificateNo = null;
				Long certificateNo = seqGenFunctionUtility.generateSequenceNo("BND", "TB_BD_CERT_COPY", "CERT_NO",
						birthCertificateDTO.getOrgId(), "Y", null);
				TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
				String financialYear = UserSession.getCurrent().getCurrentDate();
				finalcertificateNo = "HQ/" + financialYear.substring(6, 10) + "/"
						+ new DecimalFormat("000000").format(certificateNo);
				tbBdCertCopy.setOrgid(birthCertificateDTO.getOrgId());
				tbBdCertCopy.setNacBrId(brId);
				tbBdCertCopy.setCopyNo(birthCertificateDTO.getNoOfCopies());
				tbBdCertCopy.setUpdatedBy(birthCertificateDTO.getUserId());
				tbBdCertCopy.setUpdatedDate(birthCertificateDTO.getUpdatedDate());
				tbBdCertCopy.setUserId(birthCertificateDTO.getUserId());
				tbBdCertCopy.setLgIpMac(birthCertificateDTO.getLgIpMac());
				tbBdCertCopy.setLgIpMacUpd(birthCertificateDTO.getLgIpMacUpd());
				tbBdCertCopy.setCertNo(finalcertificateNo);
				tbBdCertCopy.setLmoddate(new Date());
				tbBdCertCopy.setStatus("N");
				tbBdCertCopy.setAPMApplicationId(birthCertificateDTO.getApmApplicationId());
				// saveCertcopy
				birthDeathCertificateCopyRepository.save(tbBdCertCopy);
				return finalcertificateNo;
			} else {
				if ((certnum != null) || (certnumcopy.equals(finYear.substring(6, 10)))) {
					String finalcertificateNo = null;
					TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
					finalcertificateNo = certnum;
					tbBdCertCopy.setOrgid(birthCertificateDTO.getOrgId());
					tbBdCertCopy.setNacBrId(brId);
					tbBdCertCopy.setCopyNo(birthCertificateDTO.getNoOfCopies());
					tbBdCertCopy.setUpdatedBy(birthCertificateDTO.getUserId());
					tbBdCertCopy.setUpdatedDate(birthCertificateDTO.getUpdatedDate());
					tbBdCertCopy.setUserId(birthCertificateDTO.getUserId());
					tbBdCertCopy.setLgIpMac(birthCertificateDTO.getLgIpMac());
					tbBdCertCopy.setLgIpMacUpd(birthCertificateDTO.getLgIpMacUpd());
					tbBdCertCopy.setCertNo(finalcertificateNo);
					tbBdCertCopy.setLmoddate(new Date());
					tbBdCertCopy.setStatus("N");
					tbBdCertCopy.setAPMApplicationId(birthCertificateDTO.getApmApplicationId());
					// saveCertcopy
					birthDeathCertificateCopyRepository.save(tbBdCertCopy);
					return finalcertificateNo;
				}
			}
		}
		return null;
	}

	@Override
	public BirthCertificateDTO getNacBirthCertdetail(Long apmApplicationId, Long orgId) {
		Organisation organisation = organisationDAO.getOrganisationById(orgId,MainetConstants.STATUS.ACTIVE);
		BirthCertificateDTO birthDto = new BirthCertificateDTO();
		BirthCertificateEntity birthEntity = birthCerRepro.findData(apmApplicationId, orgId);
		if(birthEntity!=null) {
		BeanUtils.copyProperties(birthEntity, birthDto);
		String dateToString = Utility.dateToString(birthEntity.getBrDob());
		birthDto.setAdOrderNo(dateToString.substring(6));
		birthDto.setApplicantAddress(organisation.getOrgAddress());
		String state = CommonMasterUtility.getNonHierarchicalLookUpObject(organisation.getOrgCpdIdState(), organisation).getLookUpDesc();
		String district = CommonMasterUtility.getNonHierarchicalLookUpObject(organisation.getOrgCpdIdDis(), organisation).getLookUpDesc();
		birthDto.setBrInformantAddr(district);
		birthDto.setBrInformantAddrMar(state);
		}
		return birthDto;
	}

	@Override
	@Transactional
	public void updatPrintStatus(Long brId, Long orgId, Long bdId, Long noOfCopies) {
        String status ="P";
		TbBdCertCopy tbBdCertCopy=certCopyRepository.findOne(bdId);
		if(tbBdCertCopy.getStatus().equals("N")) {
		certCopyRepository.updateStatus(bdId, orgId, status);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(BirthRegistrationDTO dto,Organisation organisation)
	{ //issuence of birth certificate sms and email
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApmApplicationId()));
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		if(dto.getAmount()==null) {
			dto.setAmount(0.0d);
		}
		smdto.setAmount(dto.getAmount());
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		smdto.setServName(dto.getRequestDTO().getServiceShortCode());
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BIRTH_DEATH, BndConstants.ISSUANCE_BIRTH_URL_FOR_WORLFLOW_INIT, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}

	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmailNac(BirthCertificateDTO dto,Organisation organisation)
	{
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApmApplicationId()));
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		if(dto.getAmount()== null) {
			dto.setAmount(0.0d);
		}
		smdto.setAmount(dto.getAmount());
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BIRTH_DEATH, BndConstants.NAC_BIRTHREG, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}
	@Override
	@Transactional
	public void smsAndEmailApproval(BirthRegistrationDTO dto, String decision) {
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApplicationId()));
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smdto.setCc(decision);
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		smdto.setRegNo(dto.getBrCertNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		String alertType = MainetConstants.BLANK;
		if(decision.equals(BndConstants.APPROVED)) {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
		}else {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
		}
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BND, BndConstants.ISSUE_BIRTH_APPR_URL, alertType, smdto, organisation, dto.getLangId());
	}
	
	@Override
	@Transactional
	@Path(value = "/getBirthIssuenceApplIdPortal/{applicationId}/{orgId}")
	@POST
	public BirthRegistrationDTO getBirthIssuenceApplId(@PathParam("applicationId") Long applicationId,
			@PathParam("orgId") Long orgId) {
		BirthRegistrationDTO dto = new BirthRegistrationDTO();
		dto = getBirthIssueRegisteredAppliDetail(null, null, null, applicationId.toString(), orgId);
		return dto;

	}
	
	public Long getUpdatedRegUnitByBrId(Long brId) {
		Long regUnitId;
		regUnitId = parentDetHistRepo.getRegUnitIdByBrId(brId);
		return regUnitId;
	}
	
}
