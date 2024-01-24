package com.abm.mainet.additionalservices.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.abm.mainet.additionalservices.dto.BndRateMaster;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.NOCforOtherGovtDeptDto;
import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.ui.model.NOCForOtherGovtDeptModel;
import com.abm.mainet.additionalservices.ui.model.NursingHomePermisssionModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.CFCApplicationAddressRepository;
import com.abm.mainet.common.service.ApplicationService;
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

@Service
public class NOCForOtherGovtDeptServiceImpl implements NOCForOtherGovtDeptService {

	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private static final Logger LOGGER = LoggerFactory.getLogger(NOCForOtherGovtDeptServiceImpl.class);
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";

	@Autowired
	private TbCfcApplicationMstJpaRepository cfcApplicationMstJpaRepository;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private GroupMasterService groupMasterService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Resource
	private TbTaxMasService taxMasService;
	
	private ApplicationService applicationService;

	@Autowired
	private CFCApplicationAddressRepository cfcApplicationAddressRepository;

	@Autowired
	IFileUploadService fileUploadService;
	
	@Autowired
	private TreeCuttingPermissionService treeCuttingTrimmingPer;

	/*
	 * @Override public List<NursingHomeSummaryDto> getAllByServiceIdAndAppId(Long
	 * serviceId, String refId, Long orgId) {
	 * 
	 * List<NursingHomeSummaryDto> dtos = new ArrayList<>();
	 * 
	 * TbCfcApplicationMstEntity dto =
	 * cfcApplicationMstJpaRepository.fetchCfcApplicationsByServiceId(serviceId,
	 * refId);
	 * 
	 * if (dto != null) { NursingHomeSummaryDto homeSummaryDto = new
	 * NursingHomeSummaryDto(); homeSummaryDto.setfName(dto.getApmFname());
	 * homeSummaryDto.setAppId(dto.getApmApplicationId());
	 * homeSummaryDto.setRefNo(dto.getRefNo());
	 * homeSummaryDto.setlName(dto.getApmLname());
	 * homeSummaryDto.setServiceName(serviceMasterService.getServiceNameByServiceId(
	 * serviceId)); dtos.add(homeSummaryDto); }
	 * 
	 * return dtos; }
	 */

	@Override
	public String saveApplicantData(NOCforOtherGovtDeptDto noCforOtherGovtDeptDto,
			CFCApplicationAddressEntity addressEntity, TbCfcApplicationMst cfcApplicationMst,
			NOCForOtherGovtDeptModel model) {
		String appicationId = null;

		TbCfcApplicationMstEntity cfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		BeanUtils.copyProperties(cfcApplicationMst, cfcApplicationMstEntity);

		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(cfcApplicationMst.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		RequestDTO requestDto = setApplicantRequestDto(addressEntity, cfcApplicationMst, serviceMaster);

		if (cfcApplicationMstEntity.getApmApplicationId() == null) {

			appicationId =treeCuttingTrimmingPer.createApplicationNumber(requestDto);

			ServiceMaster master = new ServiceMaster();
			master.setSmServiceId(cfcApplicationMst.getSmServiceId());
			cfcApplicationMstEntity.setTbServicesMst(master);
			cfcApplicationMst.setApmApplicationId(Long.parseLong(appicationId));
			cfcApplicationMst.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmPayStatFlag(MainetConstants.FlagF);
			cfcApplicationMstEntity.setApmApplicationId(Long.parseLong(appicationId));
			cfcApplicationMstEntity.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmApplicationDate(new Date());
			cfcApplicationMstEntity.setLmoddate(UtilityService.getSQLDate(new Date()));
			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDto.getOrgId());
			cfcApplicationMstEntity.setTbOrganisation(organisation);
			
			cfcApplicationMstJpaRepository.save(cfcApplicationMstEntity);
			

			addressEntity.setApmApplicationId(Long.parseLong(appicationId));
			addressEntity.setLmodDate(UtilityService.getSQLDate(new Date()));
			cfcApplicationAddressRepository.save(addressEntity);

			boolean checklist = false;
			if ((noCforOtherGovtDeptDto.getDocumentList() != null)
					&& !noCforOtherGovtDeptDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(Long.parseLong(appicationId));
				checklist = fileUploadService.doFileUpload(noCforOtherGovtDeptDto.getDocumentList(), requestDto);
				checklist = true;
			}

			/*
			 * int i = 0;
			 * 
			 * if ((noCforOtherGovtDeptDto.getAttachments() != null) &&
			 * !noCforOtherGovtDeptDto.getAttachments().isEmpty()) { List<DocumentDetailsVO>
			 * getImgList = null;
			 * 
			 * getImgList = new ArrayList<>();
			 * 
			 * requestDto.setReferenceId(appicationId); requestDto.setApplicationId(appiId);
			 * List<DocumentDetailsVO> getList = noCforOtherGovtDeptDto.getAttachments();
			 * for (int j = i; j < getList.size(); j++) { DocumentDetailsVO img =
			 * getList.get(i); getImgList.add(img); break;
			 * 
			 * }
			 * 
			 * i++; fileUploadService.doFileUpload(getImgList, requestDto);
			 * 
			 * }
			 */
		}

		if (serviceMaster.getSmFeesSchedule() == 0) {
			initializeWorkFlowForFreeService(cfcApplicationMst, serviceMaster, noCforOtherGovtDeptDto,
					addressEntity.getApaMobilno());
		} else {
			setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model, noCforOtherGovtDeptDto, cfcApplicationMst,
					addressEntity);
		}

		return appicationId;
	}

	@Transactional
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, NOCForOtherGovtDeptModel model,
			NOCforOtherGovtDeptDto noCforOtherGovtDeptDto, TbCfcApplicationMst applicationMst,
			CFCApplicationAddressEntity addressEntity) {
		/*
		 * ServiceMaster serviceMas =
		 * iServiceMasterDAO.getServiceMasterByShortCode("RBC",
		 * model.getCfcHospitalInfoDTO().getOrgId());
		 */

		ServiceMaster serviceMas = serviceMasterService.getServiceMaster(model.getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		offline.setApplNo(applicationMst.getApmApplicationId());
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
		if ((noCforOtherGovtDeptDto.getDocumentList() != null)
				&& !noCforOtherGovtDeptDto.getDocumentList().isEmpty()) {
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

			//offline.setAmountToPay("1500");
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

	public void initializeWorkFlowForFreeService(TbCfcApplicationMst requestDto, ServiceMaster serviceMaster,
			NOCforOtherGovtDeptDto noCforOtherGovtDeptDto, String mobNo) {
		boolean checkList = false;
		if ((noCforOtherGovtDeptDto.getDocumentList() != null)
				&& !noCforOtherGovtDeptDto.getDocumentList().isEmpty()) {
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
		applicantDto.setUserId(requestDto.getUserId());
		applicationMetaData.setApplicationId(requestDto.getApmApplicationId());
		applicationMetaData.setReferenceId(requestDto.getRefNo());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgid());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
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
	public List<NursingHomeSummaryDto> getAllByServiceIdAndAppId(Long serviceId, String refId, Long orgId) {
		List<NursingHomeSummaryDto> dtos = new ArrayList<>();

		TbCfcApplicationMstEntity dto = cfcApplicationMstJpaRepository.fetchCfcApplicationsByServiceId(serviceId,
				refId);

		if (dto != null) {
			NursingHomeSummaryDto homeSummaryDto = new NursingHomeSummaryDto();
			homeSummaryDto.setfName(dto.getApmFname());
			homeSummaryDto.setAppId(dto.getApmApplicationId());
			homeSummaryDto.setRefNo(dto.getRefNo());
			homeSummaryDto.setlName(dto.getApmLname());
			homeSummaryDto.setServiceName(serviceMasterService.getServiceNameByServiceId(serviceId));
			dtos.add(homeSummaryDto);
		}

		return dtos;
	}
	
	
	@Override
	@Transactional
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction) {
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
}
