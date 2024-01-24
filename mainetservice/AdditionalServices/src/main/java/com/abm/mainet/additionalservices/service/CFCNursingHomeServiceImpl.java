package com.abm.mainet.additionalservices.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.domain.CFCNursingHomeDetailEntity;
import com.abm.mainet.additionalservices.domain.CFCNursingHomeInfoEntity;
import com.abm.mainet.additionalservices.domain.CFCSonographyDetailEntity;
import com.abm.mainet.additionalservices.domain.CFCSonographyMasterEntity;
import com.abm.mainet.additionalservices.dto.BndRateMaster;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDetailDTO;
import com.abm.mainet.additionalservices.dto.CFCSonographyDetailDto;
import com.abm.mainet.additionalservices.dto.CFCSonographyMastDto;
import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.repository.CFCNursingHomeRepository;
import com.abm.mainet.additionalservices.repository.CFCSonographyRepository;
import com.abm.mainet.additionalservices.ui.model.NursingHomePermisssionModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.CFCApplicationAddressRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.fasterxml.jackson.databind.util.BeanUtil;

@Service
public class CFCNursingHomeServiceImpl implements CFCNursingHomeService {

	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private static final Logger LOGGER = LoggerFactory.getLogger(CFCNursingHomeServiceImpl.class);
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	private Long taxId = null;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private DepartmentService departmentService;

	@Resource
	private TbTaxMasService taxMasService;

	@Autowired
	private TbCfcApplicationMstJpaRepository cfcApplicationMstJpaRepository;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private CFCNursingHomeRepository cfcNursingHomeRepo;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private GroupMasterService groupMasterService;

	@Autowired
	private CFCApplicationAddressRepository cfcApplicationAddressRepository;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private CommonService commonService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Autowired
	private TreeCuttingPermissionService treeCuttingTrimmingPer;

	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private CFCSonographyRepository cfcSonographyRepo;
	
	@Resource
	private TbServicesMstService tbServicesMstService;
	
	@Override
	@Transactional
	public String saveCFCNursingHomeReg(CFCNursingHomeInfoDTO cfcNursingHomeInfoDTO,
			CFCApplicationAddressEntity addressEntity, TbCfcApplicationMst applicationMst,
			NursingHomePermisssionModel model) {
		String appicationId = null;

		TbCfcApplicationMstEntity cfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		BeanUtils.copyProperties(applicationMst, cfcApplicationMstEntity);

		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(applicationMst.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		RequestDTO requestDto = setApplicantRequestDto(addressEntity, applicationMst, serviceMaster);

		if (cfcApplicationMstEntity.getApmApplicationId() == null) {

			appicationId = treeCuttingTrimmingPer.createApplicationNumber(requestDto);

			ServiceMaster master = new ServiceMaster();
			master.setSmServiceId(applicationMst.getSmServiceId());
			cfcApplicationMstEntity.setTbServicesMst(master);
			cfcApplicationMstEntity.setApmPayStatFlag(MainetConstants.FlagY);
			cfcApplicationMstEntity.setApmApplicationId(Long.parseLong(appicationId));
			cfcApplicationMstEntity.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmCfcWard(addressEntity.getApaZoneNo());
			applicationMst.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmApplicationDate(new Date());
			cfcApplicationMstEntity.setLmoddate(UtilityService.getSQLDate(new Date()));
			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDto.getOrgId());
			cfcApplicationMstEntity.setTbOrganisation(organisation);
			cfcApplicationMstJpaRepository.save(cfcApplicationMstEntity);

			addressEntity.setApmApplicationId(Long.parseLong(appicationId));
			addressEntity.setLmodDate(UtilityService.getSQLDate(new Date()));
			cfcApplicationAddressRepository.save(addressEntity);

			CFCNursingHomeInfoEntity cfcNursingHomeInfoEntity = new CFCNursingHomeInfoEntity();
			BeanUtils.copyProperties(cfcNursingHomeInfoDTO, cfcNursingHomeInfoEntity);
			cfcNursingHomeInfoEntity.setApmApplicationId(Long.parseLong(appicationId));
			List<CFCNursingHomeDetailEntity> detailEntities = new ArrayList<CFCNursingHomeDetailEntity>();
			cfcNursingHomeInfoDTO.getCfcHospitalInfoDetailDTOs().forEach(dto -> {
				CFCNursingHomeDetailEntity cfcNursingHomeDetailEntity = new CFCNursingHomeDetailEntity();
				BeanUtils.copyProperties(dto, cfcNursingHomeDetailEntity);
				cfcNursingHomeDetailEntity.setCfcHospitalInfoEntity(cfcNursingHomeInfoEntity);
				detailEntities.add(cfcNursingHomeDetailEntity);
			});

			cfcNursingHomeInfoEntity.setCfcHospitalDetInfoEntities(detailEntities);

			CFCNursingHomeInfoEntity masEntity = cfcNursingHomeRepo.save(cfcNursingHomeInfoEntity);
			cfcNursingHomeInfoDTO.setApmApplicationId(Long.parseLong(appicationId));
			boolean checklist = false;
			if ((cfcNursingHomeInfoDTO.getDocumentList() != null)
					&& !cfcNursingHomeInfoDTO.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(Long.parseLong(appicationId));
				checklist = fileUploadService.doFileUpload(cfcNursingHomeInfoDTO.getDocumentList(), requestDto);
				checklist = true;
			}

		}
		if (serviceMaster.getSmFeesSchedule() == 0
				|| serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			initializeWorkFlowForFreeService(applicationMst, serviceMaster, cfcNursingHomeInfoDTO,
					addressEntity.getApaMobilno());
		} else {
			setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model, cfcNursingHomeInfoDTO, applicationMst,
					addressEntity);
		}

		return appicationId;
	}

	public void initializeWorkFlowForFreeService(TbCfcApplicationMst requestDto, ServiceMaster serviceMaster,
			CFCNursingHomeInfoDTO cfcNursingHomeInfoDTO, String mobNo) {
		boolean checkList = false;
		if (CollectionUtils.isNotEmpty(cfcNursingHomeInfoDTO.getDocumentList())) {
			checkList = true;
		}

		// ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(,
		// requestDto.getOrgId());

		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getApmFname());
		applicantDto.setServiceId(serviceMaster.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode("CFC"));
		applicantDto.setMobileNo(mobNo);
		applicationMetaData.setApplicationId(cfcNursingHomeInfoDTO.getApmApplicationId());
		applicantDto.setUserId(requestDto.getUserId());
		applicationMetaData.setIsLoiApplicable(false);

		applicationMetaData.setIsCheckListApplicable(checkList);

		applicationMetaData.setOrgId(requestDto.getOrgid());

		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Transactional
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, NursingHomePermisssionModel model,
			CFCNursingHomeInfoDTO cfcNursingHomeInfoDTO, TbCfcApplicationMst applicationMst,
			CFCApplicationAddressEntity addressEntity) {
		/*
		 * ServiceMaster serviceMas =
		 * iServiceMasterDAO.getServiceMasterByShortCode("RBC",
		 * model.getCfcHospitalInfoDTO().getOrgId());
		 */

		ServiceMaster serviceMas = serviceMasterService.getServiceMaster(model.getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		offline.setApplNo(cfcNursingHomeInfoDTO.getApmApplicationId());
		offline.setReferenceNo(applicationMst.getRefNo());
		offline.setObjectionNumber(applicationMst.getRefNo());
		offline.setAmountToPay(model.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = null;
		String applicantAddress = null;
		if (StringUtils.isNotEmpty(applicationMst.getApmFname()) &&  StringUtils.isNotEmpty(applicationMst.getApmMname()) && StringUtils.isNotEmpty(applicationMst.getApmLname()))
			fullName = String.join(" ", Arrays.asList(applicationMst.getApmFname(), applicationMst.getApmMname(),
				applicationMst.getApmLname()));
		else if(StringUtils.isNotEmpty(applicationMst.getApmFname())  && StringUtils.isNotEmpty(applicationMst.getApmLname()))
			fullName = String.join(" ", Arrays.asList(applicationMst.getApmFname(),applicationMst.getApmLname()));
		 if (StringUtils.isNotEmpty(fullName))
		 offline.setApplicantName(fullName);
        if (StringUtils.isNotEmpty(addressEntity.getApaBlockName()) && StringUtils.isNotEmpty(addressEntity.getApaBldgnm()) &&  StringUtils.isNotEmpty(addressEntity.getApaRoadnm())) {
		 applicantAddress = String.join(" ", Arrays.asList(addressEntity.getApaBldgnm(),
				addressEntity.getApaBlockName(), addressEntity.getApaRoadnm(), addressEntity.getApaCityName()));
        }else
        	applicantAddress = addressEntity.getApaCityName();
        
		offline.setApplicantAddress(applicantAddress);
		// offline.setApplicantAddress(birthRegModel.getBirthCertificateDto().getPdParaddress());
		offline.setMobileNumber(addressEntity.getApaMobilno());
		if (addressEntity.getApaEmail() != null )
		offline.setEmailId(addressEntity.getApaEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		
		if (model.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : model.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if ((cfcNursingHomeInfoDTO.getDocumentList() != null) && !cfcNursingHomeInfoDTO.getDocumentList().isEmpty()) {
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

			model.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());

			model.setReceiptDTO(printDto);
			model.setSuccessMessage(model.getAppSession().getMessage("adh.receipt"));
		}
	}

	@Override
	@Transactional
	public Boolean checkEmployeeRole(UserSession ses) {
		@SuppressWarnings("deprecation")
		List<LookUp> lookup = CommonMasterUtility.getListLookup(NOCForBuildPermissionConstant.DESIGN_PRIFIX,
				UserSession.getCurrent().getOrganisation());

		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		for (int i = 0; i < lookup.size(); i++) {
			if (lookup.get(i).getOtherField() != null
					&& lookup.get(i).getOtherField().equals(NOCForBuildPermissionConstant.NBP)) {
				if (lookup.get(i).getDescLangFirst().equalsIgnoreCase(groupMaster.getGrCode())) {
					checkFinalAproval = true;
				}
			}
		}

		return checkFinalAproval;
	}

	@Override
	@Transactional
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		String processName = serviceMasterService.getProcessName(workflowTaskAction.getServiceId(), workflowTaskAction.getOrgId());		
		workflowProcessParameter.setProcessName(processName);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	private RequestDTO setApplicantRequestDto(CFCApplicationAddressEntity addressEntity,
			TbCfcApplicationMst applicationMst, ServiceMaster sm) {

		RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(addressEntity.getUserId());

		requestDto.setOrgId(addressEntity.getOrgId().getOrgid());
		requestDto.setLangId((long) addressEntity.getLangId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(applicationMst.getApmFname());
		requestDto.setEmail(addressEntity.getApaEmail());
		requestDto.setMobileNo(addressEntity.getApaMobilno());
		requestDto.setAreaName(addressEntity.getApaAreanm());

		/*
		 * if (tradeMasterDto.getTotalApplicationFee() != null) {
		 * requestDto.setPayAmount(tradeMasterDto.getTotalApplicationFee().doubleValue()
		 * ); }
		 */
		return requestDto;

	}

	@Override
	public List<String> findAllApplicationNo() {
		 // #129863
		List<String> list = new ArrayList<>(); 
		List<CFCNursingHomeInfoEntity> cfcNursingHomeInfoEntities = cfcNursingHomeRepo.findAll();
        List <CFCSonographyMasterEntity> sonographyMasterEntities = cfcSonographyRepo.findAll();
       if (CollectionUtils.isNotEmpty(sonographyMasterEntities))
          sonographyMasterEntities.forEach(dto ->{
        	String refNo  = cfcApplicationMstJpaRepository.getrefNosByAppId(dto.getApmApplicationId());
        	list.add(refNo);
        });
       if (CollectionUtils.isNotEmpty(cfcNursingHomeInfoEntities))
	  	  cfcNursingHomeInfoEntities.forEach(ent -> {
			String refNo = cfcApplicationMstJpaRepository.getrefNosByAppId(ent.getApmApplicationId());
			list.add(refNo);
		});
		return list;
	}

	@Override
	public List<NursingHomeSummaryDto> getAllByServiceIdAndAppId(Long serviceId, String refId, Long orgId) {

		List<NursingHomeSummaryDto> dtos = new ArrayList<>();
		String shortCode = tbServicesMstService.getServiceShortDescByServiceId(serviceId);
		TbCfcApplicationMstEntity dto = cfcApplicationMstJpaRepository.fetchCfcApplicationsByServiceId(serviceId,
				refId);
        // #129863
		if (dto != null) {
			if((MainetConstants.CFCServiceCode.Hospital_Sonography_center).equals(shortCode)) {
				CFCSonographyMasterEntity sonographyMasterEntity = cfcSonographyRepo.findbyApmApplId(dto.getApmApplicationId());
				if (sonographyMasterEntity != null && dto != null && dto.getApmApplicationId().equals(sonographyMasterEntity.getApmApplicationId())) {
					NursingHomeSummaryDto homeSummaryDto = new NursingHomeSummaryDto();
					homeSummaryDto.setfName(dto.getApmFname());
					homeSummaryDto.setAppId(dto.getApmApplicationId());
					homeSummaryDto.setRefNo(refId);
					homeSummaryDto.setlName(dto.getApmLname());
					homeSummaryDto.setServiceName(serviceMasterService.getServiceNameByServiceId(serviceId));
					dtos.add(homeSummaryDto);
				 }
			}else {
		    	CFCNursingHomeInfoEntity cfcNursingHomeInfoEntity = cfcNursingHomeRepo
					.findbyApmApplicationId(dto.getApmApplicationId());
			if (dto != null && dto.getApmApplicationId().equals(cfcNursingHomeInfoEntity.getApmApplicationId())) {
				NursingHomeSummaryDto homeSummaryDto = new NursingHomeSummaryDto();
				homeSummaryDto.setfName(dto.getApmFname());
				homeSummaryDto.setAppId(dto.getApmApplicationId());
				homeSummaryDto.setRefNo(refId);
				homeSummaryDto.setlName(dto.getApmLname());
				homeSummaryDto.setServiceName(serviceMasterService.getServiceNameByServiceId(serviceId));
				dtos.add(homeSummaryDto);
			  }
			}
		}

		return dtos;
	}

	@Override
	@Transactional
	public CFCNursingHomeInfoDTO findByApplicationId(Long appId) {

		CFCNursingHomeInfoEntity cfcNursingHomeInfoEntity = cfcNursingHomeRepo.findbyApmApplicationId(appId);

		CFCNursingHomeInfoDTO cfcNursingHomeInfoDTO = new CFCNursingHomeInfoDTO();
		if (cfcNursingHomeInfoEntity != null) {
		BeanUtils.copyProperties(cfcNursingHomeInfoEntity, cfcNursingHomeInfoDTO);

		List<CFCNursingHomeInfoDetailDTO> cfcNursingHomeInfoDetailDTOs = new ArrayList<CFCNursingHomeInfoDetailDTO>();
		cfcNursingHomeInfoEntity.getCfcHospitalDetInfoEntities().forEach(det -> {
			CFCNursingHomeInfoDetailDTO cfcNursingHomeInfoDetailDTO = new CFCNursingHomeInfoDetailDTO();
			BeanUtils.copyProperties(det, cfcNursingHomeInfoDetailDTO);
			cfcNursingHomeInfoDetailDTOs.add(cfcNursingHomeInfoDetailDTO);

		});

		cfcNursingHomeInfoDTO.setCfcHospitalInfoDetailDTOs(cfcNursingHomeInfoDetailDTOs);
		}
		return cfcNursingHomeInfoDTO;
	}
	
    // #129863
	@Override
	@Transactional
	public CFCSonographyMastDto findDetByApplicationId(Long appId) {
		CFCSonographyMasterEntity cfcMastEntity = cfcSonographyRepo.findbyApmApplId(appId);
		CFCSonographyMastDto cfcMastDto = new CFCSonographyMastDto();
		BeanUtils.copyProperties(cfcMastEntity, cfcMastDto);
		List<CFCSonographyDetailDto> cfcDetailDto = new ArrayList<CFCSonographyDetailDto>();
		cfcMastEntity.getCfcSonographyDetEntity().forEach(det -> {
			CFCSonographyDetailDto sonographyDetailDto = new CFCSonographyDetailDto();
			BeanUtils.copyProperties(det, sonographyDetailDto);
			cfcDetailDto.add(sonographyDetailDto);

		});
		cfcMastDto.setCfcSonoDetDtoList(cfcDetailDto);

		return cfcMastDto;
	}
	
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

	private WSResponseDTO populateOtherFieldsForServiceCharge(BndRateMaster bndRateMaster, WSResponseDTO responseDTO)
			throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<BndRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(bndRateMaster.getServiceCode(),
				bndRateMaster.getOrgId());
		if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)
				|| serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), bndRateMaster.getOrgId(),
					Long.parseLong(bndRateMaster.getChargeApplicableAt()));
			if (!applicableCharges.isEmpty() && applicableCharges.get(0).getTaxId() != null)
				taxId = applicableCharges.get(0).getTaxId();
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
	
	/* D121289  Method to get the LOI charges for Scrutiny workflow*/

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {
		Map<Long, Double> chargeMap = new HashMap<>();
		CFCNursingHomeInfoDTO cfcNuringHomeInfoDto = findByApplicationId(applicationId);

		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);

		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;

		WSResponseDTO certificateCharges = null;
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName("BNDRateMaster");
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgIds);
			rateMasterModel.setServiceCode(serviceMaster.getSmShortdesc());
			rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			rateMasterModel.setOrganisationType(CommonMasterUtility
					.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
							UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonMasterUi.OTY)
					.getDescLangFirst());
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);
			// WSResponseDTO responsefortax =
			// birthCertificateService.getApplicableTaxes(taxRequestDto);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = getApplicableTaxes(taxRequestDto);
				/* birthCertificateService.getApplicableTaxes(taxRequestDto); */
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				return chargeMap;
			}

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;
				if (true/* !responsefortax.isFree() */) {
					final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
					final List<BndRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						BndRateMaster masterrate = (BndRateMaster) rate;
						masterrate = populateChargeModel(masterrate, serviceMaster.getSmShortdesc());
						if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg))
							masterrate.setSlab4(cfcNuringHomeInfoDto.getTotalBedCount());
						else
							masterrate.setSlab4(0);
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName("BNDRateMaster");
					certificateCharges = getBndCharge(bndChagesRequestDto);
					if (certificateCharges != null) {
						detailDTOs = (List<Object>) certificateCharges.getResponseObj();
						final List<?> mrmRateList = RestClient.castResponse(certificateCharges, BndRateMaster.class);
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							break;
						}
						if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg))
							chargesAmount = String.valueOf(charges.get("slab4"));
						else
							chargesAmount = String.valueOf(charges.get("flatRate"));

					} else {
						chargesAmount = MainetConstants.FlagN;

					}
				}

			}
		}
		chargeMap.put(taxId, Double.parseDouble(chargesAmount));
		return chargeMap;
	}

	private BndRateMaster populateChargeModel(BndRateMaster bndRateMaster, String smShortCode) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(smShortCode);
		bndRateMaster.setDeptCode("CFC");
		return bndRateMaster;
	}

	@Override
	@Transactional
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long org) {

		// TbCfcApplicationMst cfcApplicationMst =
		// tbCFCApplicationMst.findById(Long.parseLong(complainNo));
		CFCApplicationAddressEntity findOne = cfcApplicationAddressRepository.findOne(applicationId);

		WardZoneBlockDTO wardZoneDTO = new WardZoneBlockDTO();

		// ProvisionalAssesmentMstEntity assMst = assMstList.get(0);
		if (findOne.getApaZoneNo() != null) {
			wardZoneDTO.setAreaDivision1(findOne.getApaZoneNo());
		}
		if (findOne.getApaWardNo() != null) {
			wardZoneDTO.setAreaDivision2(findOne.getApaWardNo());
		}

		return wardZoneDTO;
	}

    // #129863
	@Override
	public String saveCFCSonographyReg(CFCSonographyMastDto cfcSonographyMastDto,
			CFCApplicationAddressEntity addressEntity, TbCfcApplicationMst applicationMst,
			NursingHomePermisssionModel model) {
		String appicationId = null;

		TbCfcApplicationMstEntity cfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		BeanUtils.copyProperties(applicationMst, cfcApplicationMstEntity);

		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(applicationMst.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		RequestDTO requestDto = setApplicantRequestDto(addressEntity, applicationMst, serviceMaster);

		if (cfcApplicationMstEntity.getApmApplicationId() == null) {

			appicationId = treeCuttingTrimmingPer.createApplicationNumber(requestDto);

			ServiceMaster master = new ServiceMaster();
			master.setSmServiceId(applicationMst.getSmServiceId());
			cfcApplicationMstEntity.setTbServicesMst(master);
			cfcApplicationMstEntity.setApmPayStatFlag(MainetConstants.FlagY);
			cfcApplicationMstEntity.setApmApplicationId(Long.parseLong(appicationId));
			cfcApplicationMstEntity.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmCfcWard(addressEntity.getApaZoneNo());
			applicationMst.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmApplicationDate(new Date());
			cfcApplicationMstEntity.setLmoddate(UtilityService.getSQLDate(new Date()));
			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDto.getOrgId());
			cfcApplicationMstEntity.setTbOrganisation(organisation);
			cfcApplicationMstJpaRepository.save(cfcApplicationMstEntity);

			addressEntity.setApmApplicationId(Long.parseLong(appicationId));
			addressEntity.setLmodDate(UtilityService.getSQLDate(new Date()));
			cfcApplicationAddressRepository.save(addressEntity);
			
			CFCSonographyMasterEntity mastEntity = new CFCSonographyMasterEntity();
			BeanUtils.copyProperties(cfcSonographyMastDto, mastEntity);
			mastEntity.setApmApplicationId(Long.parseLong(appicationId));
			
			List<CFCSonographyDetailEntity> detEntityList = new ArrayList<>();
			cfcSonographyMastDto.getCfcSonoDetDtoList().forEach(dto -> {
				CFCSonographyDetailEntity detEntity = new CFCSonographyDetailEntity();
				BeanUtils.copyProperties(dto, detEntity);
				detEntity.setCfcSonographyMasEntity(mastEntity);
				detEntityList.add(detEntity);
			});
			mastEntity.setCfcSonographyDetEntity(detEntityList);
		
		CFCSonographyMasterEntity entity = cfcSonographyRepo.save(mastEntity);
		cfcSonographyMastDto.setApmApplicationId(Long.parseLong(appicationId));
		boolean checklist = false;
		if ((cfcSonographyMastDto.getDocumentList() != null)
				&& !cfcSonographyMastDto.getDocumentList().isEmpty()) {
			requestDto.setApplicationId(Long.parseLong(appicationId));
			checklist = fileUploadService.doFileUpload(cfcSonographyMastDto.getDocumentList(), requestDto);
			checklist = true;
		 }
		}
		if (serviceMaster.getSmFeesSchedule() == 0
				|| serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			initializeWorkFlowForFreeServiceForSonography(applicationMst, serviceMaster, cfcSonographyMastDto,
					addressEntity.getApaMobilno());
		} else {
			setAndSaveChallanDtoOffLineForSonography(model.getOfflineDTO(), model, cfcSonographyMastDto, applicationMst,
					addressEntity);
		}

		return appicationId;
		
	}

	private void initializeWorkFlowForFreeServiceForSonography(TbCfcApplicationMst requestDto, ServiceMaster serviceMaster,
			CFCSonographyMastDto cfcSonographyMastDto, String mobNo) {

		boolean checkList = false;
		if (CollectionUtils.isNotEmpty(cfcSonographyMastDto.getDocumentList())) {
			checkList = true;
		}

		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getApmFname());
		applicantDto.setServiceId(serviceMaster.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode("CFC"));
		applicantDto.setMobileNo(mobNo);
		applicationMetaData.setApplicationId(cfcSonographyMastDto.getApmApplicationId());
		applicantDto.setUserId(requestDto.getUserId());
		applicationMetaData.setIsLoiApplicable(false);

		applicationMetaData.setIsCheckListApplicable(checkList);

		applicationMetaData.setOrgId(requestDto.getOrgid());

		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}

	}

	private void setAndSaveChallanDtoOffLineForSonography(CommonChallanDTO offline,
			NursingHomePermisssionModel model, CFCSonographyMastDto cfcSonographyMastDto,
			TbCfcApplicationMst applicationMst, CFCApplicationAddressEntity addressEntity) {
		/*
		 * ServiceMaster serviceMas =
		 * iServiceMasterDAO.getServiceMasterByShortCode("RBC",
		 * model.getCfcHospitalInfoDTO().getOrgId());
		 */

		ServiceMaster serviceMas = serviceMasterService.getServiceMaster(model.getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		offline.setApplNo(cfcSonographyMastDto.getApmApplicationId());
		offline.setReferenceNo(applicationMst.getRefNo());
		offline.setObjectionNumber(applicationMst.getRefNo());
		offline.setAmountToPay(model.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());

		String fullName = String.join(" ", Arrays.asList(applicationMst.getApmFname(), applicationMst.getApmMname(),
				applicationMst.getApmLname()));
		offline.setApplicantName(fullName);

		String applicantAddress = String.join(" ", Arrays.asList(addressEntity.getApaBldgnm(),
				addressEntity.getApaBlockName(), addressEntity.getApaRoadnm(), addressEntity.getApaCityName()));
		offline.setApplicantAddress(applicantAddress);
		// offline.setApplicantAddress(birthRegModel.getBirthCertificateDto().getPdParaddress());
		offline.setMobileNumber(addressEntity.getApaMobilno());
		offline.setEmailId(addressEntity.getApaEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		
		if (model.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : model.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if ((cfcSonographyMastDto.getDocumentList() != null) && !cfcSonographyMastDto.getDocumentList().isEmpty()) {
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

			model.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());

			model.setReceiptDTO(printDto);
			model.setSuccessMessage(model.getAppSession().getMessage("adh.receipt"));
		}
	}

}
