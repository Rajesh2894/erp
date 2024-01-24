package com.abm.mainet.rts.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.IntegratedServiceDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.rts.dao.DrainageConnectionServiceDAO;
import com.abm.mainet.rts.dao.IrtsExternalDAO;
import com.abm.mainet.rts.domain.DrainageConnectionEntity;
import com.abm.mainet.rts.domain.DrainageConnectionHistoryEntity;
import com.abm.mainet.rts.domain.RtsExternalServicesEntity;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.RtsExternalServicesDTO;
import com.abm.mainet.rts.repository.DrainageConnectionRepository;
import com.abm.mainet.rts.repository.RtsExternalServicesRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.rts.service.IRtsService")
@Api(value = "/rts-service")
@Path("/rts-service")
@Service
public class RtsServiceImpl implements IRtsService {

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private ILocationMasService locationMasterService;

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private IrtsExternalDAO rtsExternalDAO;
	

	@Autowired
	@Resource
	private DrainageConnectionRepository drainageConnectionRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private DrainageConnectionServiceDAO drainageConnectionServiceDAO;

	@Autowired
	private IChecklistVerificationService ichckService;
	@Autowired
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private IRtsExternalServices rts;
	
	@Autowired
	RtsExternalServicesRepository rtsRepro;

	@Override
	@POST
	@ApiOperation(value = "fetch wardZones", notes = "fetch wardZones")
	@Path("/fetch-wardZone/{orgId}")
	@Transactional
	public Map<Long, String> fetchWardZone(Long orgId) {

		List<WardZoneDTO> wardList = new ArrayList<WardZoneDTO>();

		wardList = locationMasterService.findlocationByOrgId(orgId);

		Map<Long, String> wardMapList = new LinkedHashMap<Long, String>();
		for (WardZoneDTO ward : wardList) {
			wardMapList.put(ward.getLocationId(), ward.getLocationName());
		}
		return wardMapList;
	}

	@Override
	@POST
	@ApiOperation(value = "fetch rts service", notes = "fetch rts service")
	@Path("/fetch-rts-services/{orgId}")
	@Transactional
	public Map<String, String> fetchRtsService(Long orgId) {
		List<Object[]> service = null;
		List<Object[]> service2 = null;
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode("RTS");
		final Map<String, String> serviceMap = new LinkedHashMap<String, String>();
		 Long activeStatusId= CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.Common_Constant.ACTIVE_FLAG, MainetConstants.Common_Constant.ACN,orgId);
		service2 = serviceMasterService.findAllActiveServicesByDepartment(orgId, deptId,activeStatusId);
		List<String[]> serviceString = new ArrayList<String[]>();
		String[] tempService;

		for (final Object[] obj : service2) {
			if (obj[0] != null) {
				tempService = new String[3];
				if (obj[1].toString().compareTo("First Appeal") != 0
						&& obj[1].toString().compareTo("Second Appeal") != 0) {
					serviceMap.put(obj[2].toString(), obj[1].toString());
				}

			}
		}
		return serviceMap;
	}

	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/fetch-rts-service-info/{orgId}")
	public Map<String, String> serviceInfo(@PathParam("orgId") Long orgId) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceByShortName(MainetConstants.RightToService.DCS, Long.valueOf(orgId));
		Map<String, String> serviceDataMap = new LinkedHashMap<String, String>();

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagY);

			} else {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}

			serviceDataMap.put("serviceId", serviceMasterData.getSmServiceId().toString());
			serviceDataMap.put("serviceName", serviceMasterData.getSmServiceName());
			serviceDataMap.put("deptId", serviceMasterData.getTbDepartment().getDpDeptid().toString());
		}

		return serviceDataMap;
	}

	@Override
	@POST
	@ApiOperation(value = "Save RTS Data", notes = "Save RTS Data")
	@Path("/save-data")
	@Transactional
	public DrainageConnectionDto portalSave(DrainageConnectionDto dto) {

		ServiceMaster serviceMas = serviceMasterService.getServiceMasterByShortCode(MainetConstants.RightToService.DCS,
				dto.getOrgId());

		CommonChallanDTO offline = dto.getOfflineDTO();
		// add for defect#100746
		dto.getReqDTO().setApplicationType(dto.getApplicantType());
		DrainageConnectionEntity entity = new DrainageConnectionEntity();
		DrainageConnectionHistoryEntity historyEntity = new DrainageConnectionHistoryEntity();
		dto.getReqDTO().setServiceId(serviceMas.getSmServiceId());
		final Long applicationNo = applicationService.createApplication(dto.getReqDTO());
		dto.getReqDTO().setApplicationId(applicationNo);
		dto.getReqDTO().setPayStatus(MainetConstants.FlagF);
		dto.setApmApplicationId(applicationNo);

		boolean checklist = false;
		boolean flag = true;
		try {
			BeanUtils.copyProperties(dto, entity);
			drainageConnectionRepository.save(entity);
			historyEntity.sethStatus(MainetConstants.FlagA);

			auditService.createHistory(entity, historyEntity);

			if ((dto.getDocumentList() != null) && !dto.getDocumentList().isEmpty()) {
				if (!dto.getCheckListApplFlag().equalsIgnoreCase("N")) {
					fileUploadService.doFileUpload(dto.getDocumentList(), dto.getReqDTO());
					checklist = true;
				}
			}
			// fileUploadService.doFileUpload(drainageConnectionDto.getDocumentList(),
			// model.getRequestDTO());
			if (dto.getApplicationchargeApplFlag().equalsIgnoreCase("N")) {
				dto.getOfflineDTO().setDocumentUploaded(checklist);
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(dto.getApmApplicationId());
				applicationData.setOrgId(dto.getOrgId());
				applicationData.setIsCheckListApplicable(checklist);
				dto.getApplicantDTO().setUserId(dto.getReqDTO().getUserId());
				dto.getApplicantDTO().setServiceId(serviceMas.getSmServiceId());
				dto.getApplicantDTO().setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
				
				if(dto.getWard() != null) {
				       TbDepartment deptObj = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
				                .findDeptByCode(dto.getOrgId(), MainetConstants.FlagA, "CFC");
					
		            final LocOperationWZMappingDto wzMapping = locationMasterService.findOperLocationAndDeptId(dto.getWard(),deptObj.getDpDeptid());
					
		            if (wzMapping != null) {
		                if (wzMapping.getCodIdOperLevel1() != null) {
		                	dto.getApplicantDTO().setDwzid1(wzMapping.getCodIdOperLevel1());
		                }
		                if (wzMapping.getCodIdOperLevel2() != null) {
		                	dto.getApplicantDTO().setDwzid2(wzMapping.getCodIdOperLevel2());
		                }
		                if (wzMapping.getCodIdOperLevel3() != null) {
		                	dto.getApplicantDTO().setDwzid3(wzMapping.getCodIdOperLevel3());
		                }
		                if (wzMapping.getCodIdOperLevel4() != null) {
		                	dto.getApplicantDTO().setDwzid4(wzMapping.getCodIdOperLevel4());
		                }
		                if (wzMapping.getCodIdOperLevel5() != null) {
		                	dto.getApplicantDTO().setDwzid5(wzMapping.getCodIdOperLevel5());
		                }
		            }

				 }
				try {
					commonService.initiateWorkflowfreeService(applicationData, dto.getApplicantDTO());
				} catch (Exception e) {
					// TODO: handle exception
				}

				// call smsandemail

			}

			String paymentUrl = "drainageConnection.html";

			SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			Organisation org = UserSession.getCurrent().getOrganisation();

			smsDto.setOrgId(dto.getOrgId());
			smsDto.setLangId(UserSession.getCurrent().getLanguageId());
			smsDto.setAppNo(dto.getApmApplicationId().toString());
			smsDto.setServName(MainetConstants.RightToService.DRAINAGECONNECTION);
			smsDto.setMobnumber(dto.getReqDTO().getMobileNo());
			smsDto.setAppName(dto.getOfflineDTO().getApplicantName());
			smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			if (StringUtils.isNotBlank(dto.getReqDTO().getEmail())) {
				smsDto.setEmail(dto.getReqDTO().getEmail());
			}

			// ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class)
			// .sendEmailSMS(RtsConstants.RTS, paymentUrl,
			// PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org,
			// UserSession.getCurrent().getLanguageId());

		} catch (Exception e) {
			flag = true;
			throw new FrameworkException("Unable to Save the Drainage Connection Details", e);
		}
		return dto;
	}

	// This method is used for geting uploaded document for view
	@Override
	public List<DocumentDetailsVO> getRtsUploadedCheckListDocuments(Long applicationnId, Long orgId) {
		// TODO Auto-generated method stub
		List<DocumentDetailsVO> attachsList = new ArrayList<>();

		Optional<TbCfcApplicationMstEntity> tbmstEnt = tbCfcApplicationMstJpaRepository
				.findByApmApplicationId(applicationnId);
		List<CFCAttachment> cfcAtt = new ArrayList();
		cfcAtt = ichckService.findAttachmentsForAppId(applicationnId, null, orgId);
		if (tbmstEnt.isPresent()) {
			TbCfcApplicationMstEntity entity = tbmstEnt.get();
			if (entity != null) {
				Boolean isNewTask = false;
				if (entity.getApmChklstVrfyFlag() == null) {
					isNewTask = true;
				}
				if (isNewTask || (entity.getApmChklstVrfyFlag().equalsIgnoreCase(MainetConstants.FlagH)
						|| entity.getApmChklstVrfyFlag().equalsIgnoreCase(MainetConstants.FlagN))) {
					cfcAtt.forEach(att -> {
						DocumentDetailsVO dvo = new DocumentDetailsVO();
						dvo.setAttachmentId(att.getAttId());
						dvo.setCheckkMANDATORY(att.getMandatory());
						dvo.setDocumentSerialNo(att.getClmSrNo());
						dvo.setDocumentName(att.getAttFname());
						dvo.setUploadedDocumentPath(att.getAttPath());
						dvo.setDoc_DESC_ENGL(att.getClmDescEngl());
						dvo.setDoc_DESC_Mar(att.getClmDesc());
						attachsList.add(dvo);

					});
				} else if (entity.getApmChklstVrfyFlag().equalsIgnoreCase(MainetConstants.FlagY)) {
					cfcAtt.forEach(att -> {
						if (att.getClmAprStatus() != null
								&& att.getClmAprStatus().equalsIgnoreCase(MainetConstants.FlagY)) {
							DocumentDetailsVO dvo = new DocumentDetailsVO();
							dvo.setAttachmentId(att.getAttId());
							dvo.setCheckkMANDATORY(att.getMandatory());
							dvo.setDocumentSerialNo(att.getClmSrNo());
							dvo.setDocumentName(att.getAttFname());
							dvo.setUploadedDocumentPath(att.getAttPath());
							dvo.setDoc_DESC_ENGL(att.getClmDescEngl());
							dvo.setDoc_DESC_Mar(att.getClmDesc());
							attachsList.add(dvo);
						}

					});

				} else {
					cfcAtt.forEach(att -> {
						if ((att.getClmRemark() == null || att.getClmRemark().isEmpty())
								|| (att.getClmAprStatus() != null
										&& att.getClmAprStatus().equalsIgnoreCase(MainetConstants.FlagY))) {
							DocumentDetailsVO dvo = new DocumentDetailsVO();
							dvo.setAttachmentId(att.getAttId());
							dvo.setCheckkMANDATORY(att.getMandatory());
							dvo.setDocumentSerialNo(att.getClmSrNo());
							dvo.setDocumentName(att.getAttFname());
							dvo.setUploadedDocumentPath(att.getAttPath());
							dvo.setDoc_DESC_ENGL(att.getClmDescEngl());
							dvo.setDoc_DESC_Mar(att.getClmDesc());
							attachsList.add(dvo);
						}

					});

				}
			}
		}

		return attachsList;
	}

	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId and ServiceCode", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/fetch-rts-service-information/{orgId}/{serviceShortCode}")
	public LinkedHashMap<String, Object> serviceInformation(@PathParam("orgId") Long orgId,
			@PathParam("serviceShortCode") String serviceShortCode) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceByShortName(serviceShortCode, Long.valueOf(orgId));
		LinkedHashMap<String, Object> serviceDataMap = new LinkedHashMap<String, Object>();

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagY);

			} else {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}
		}

		return serviceDataMap;
	}
	
	@Override
	@POST
	@Path("/saveCertificateData")
	@Transactional
	public RtsExternalServicesDTO saveCertificateData(RtsExternalServicesDTO rtsExternalServicesDTO) {
		rtsExternalServicesDTO=rts.saveCertificateData(rtsExternalServicesDTO);
		return rtsExternalServicesDTO;
	}
	
	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/fetch-rts-service/{orgId}/{serviceShortCode}")
	public Map<String, String> serviceInfomation(@PathParam("orgId") Long orgId,@PathParam("serviceShortCode") String serviceShortCode) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceByShortName(serviceShortCode, Long.valueOf(orgId));
		Map<String, String> serviceDataMap = new LinkedHashMap<String, String>();

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagY);

			} else {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}

			serviceDataMap.put("serviceId", serviceMasterData.getSmServiceId().toString());
			serviceDataMap.put("serviceName", serviceMasterData.getSmServiceName());
			serviceDataMap.put("deptId", serviceMasterData.getTbDepartment().getDpDeptid().toString());
			serviceDataMap.put("serviceNameMar", serviceMasterData.getSmServiceNameMar());
		}

		return serviceDataMap;
	}
	
	@Override
	@POST
	@Path("/updateStatus")
	@Transactional
	public RtsExternalServicesDTO updateStatus(RtsExternalServicesDTO rtsExternalServicesDTO) {
	rts.updateStatus(rtsExternalServicesDTO.getApplicationId(), rtsExternalServicesDTO.getOrgId(), rtsExternalServicesDTO.getStatus());
		return rtsExternalServicesDTO;
	}

	@Override
	@POST
	@ApiOperation(value = "get All IntegratedService Applications", notes = "get All IntegratedService Applications")
	@Path("/getAllIntegratedServiceApplications")
	@Transactional
	public List<IntegratedServiceDTO> getIntegratedServiceApplications(CitizenDashBoardReqDTO request) {
		List<IntegratedServiceDTO> listdto= new ArrayList<IntegratedServiceDTO>();
		List<Object[]> listObject = rtsExternalDAO.getIntegratedServiceApplications(request);
		 for(Object[] obj : listObject) {
			 IntegratedServiceDTO dto = new IntegratedServiceDTO();
			 String format = MainetConstants.DATE_FORMAT_WITH_HOURS_TIME;
			 dto.setAppDate(Utility.dateToString((Date)obj[0], format));
			 dto.setApmApplicationId(String.valueOf(obj[1]));
			 dto.setApmMobileNo(String.valueOf(obj[2]));
			 dto.setStatus(String.valueOf(obj[4]));
			if (obj[3] != null) {
				ServiceMaster service = (ServiceMaster) (obj[3]);
				dto.setSmServiceName(service.getSmServiceName());
				dto.setSmServiceNameMar(service.getSmServiceNameMar());
				dto.setSmShortdesc(service.getSmShortdesc());
			}
			listdto.add(dto);
		 }
		return listdto;
		
	}
	
	@Override
	@POST
	@Path("/getExternalServiceInfo")
	@Transactional
	public RtsExternalServicesDTO getExternalServiceInfo(RtsExternalServicesDTO externalDto) {
		RtsExternalServicesEntity DetailEntity = rtsRepro.findData(externalDto.getApplicationId(), externalDto.getOrgId());
		RtsExternalServicesDTO dto = new RtsExternalServicesDTO();
		BeanUtils.copyProperties(DetailEntity, dto);
		List<DocumentDetailsVO> dvo = getRtsUploadedCheckListDocuments(externalDto.getApplicationId(),
				externalDto.getOrgId());
		if (!dvo.isEmpty()) {
			for (int i = 0; i < dvo.size(); i++) {
				dvo.get(i).setDocumentByteCode(
						convertInBase64(dvo.get(i).getDocumentName(), dvo.get(i).getUploadedDocumentPath()));
			}
		}
		dto.setUploadDocument(dvo);
		return dto;
	}
	
	 // method for convert in base64
    public String convertInBase64(String docName, String docPath) {
        String base64String = null;
        String existingPath = null;
        if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
            existingPath = docPath.replace('/', '\\');
        } else {
            existingPath = docPath.replace('\\', '/');
        }
        String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        try {
            final byte[] image = FileNetApplicationClient.getInstance().getFileByte(docName, directoryPath);
            Base64 base64 = new Base64();
            base64String = base64.encodeToString(image);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return base64String;
    }
}