package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.property.domain.NoDuesPropertyDetailsEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.NoDuesPropertyDetailsRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

@Produces(MediaType.APPLICATION_JSON)
@WebService(endpointInterface = "com.abm.mainet.property.service.PropertyNoDuesCertificateService")
@Api(value = "/NoDuesCertificateService")
@Path("/NoDuesCertificateService")
@Service
public class PropertyNoDuesCertificateServiceImpl implements PropertyNoDuesCertificateService{

    private static final Logger LOGGER = Logger.getLogger(PropertyNoDuesCertificateServiceImpl.class);

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private ApplicationService applicationService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private PropertyBRMSService propertyBRMSService;
    
    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

	@Autowired
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private NoDuesPropertyDetailsRepository noDuesDetailsRepository;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private PrimaryPropertyService primaryPropertyService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private IFinancialYearService financialYearService;

    @Override
    @POST
    @Path("/getPropertyDetailsByPropertyNumber")
    @Produces(MediaType.APPLICATION_JSON)
	public NoDuesCertificateDto getPropertyDetailsByPropertyNumber(@RequestBody NoDuesCertificateDto dto) {
		NoDuesCertificateDto noDuesDto= null;
        PropertyDetailDto detailDTO = null;
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(dto.getPropNo());
	    if(dto.getPropNo()== null) {
            propInputDTO.setOldPropertyNo(dto.getOldpropno());
        }
        propInputDTO.setOrgId(dto.getOrgId());
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                ServiceEndpoints.PROP_BY_PROP_ID);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
        	noDuesDto=new NoDuesCertificateDto();
            detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
            LOGGER.info("PropertyDetailDto formed is " + detailDTO.toString());
			if (detailDTO != null && detailDTO.getStatus().equalsIgnoreCase("Success")) {
				noDuesDto.setOwnerName(detailDTO.getPrimaryOwnerName());
				noDuesDto.setOwnerAddress(detailDTO.getAddress());
				noDuesDto.setOwnerCorrAdd(detailDTO.getCorrAddress());
				noDuesDto.setTotalOutsatandingAmt(detailDTO.getTotalOutsatandingAmt());
				noDuesDto.setPropNo(dto.getPropNo());
				noDuesDto.setAppliEmail(detailDTO.getOwnerEmail());
				noDuesDto.setAppliMobileno(detailDTO.getPrimaryOwnerMobNo());
				noDuesDto.setSuccessFlag(MainetConstants.FlagY);
				if (dto.getPropNo() == null) {
					noDuesDto.setOldpropno(dto.getOldpropno());
				}
			}else {
				 noDuesDto.setSuccessFlag(MainetConstants.FlagN);
			}
            }
        else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
        	noDuesDto=new NoDuesCertificateDto();
        	 noDuesDto.setSuccessFlag(MainetConstants.FlagN);
            LOGGER.info("No Data found for given property No ");
        } else {
        	noDuesDto=new NoDuesCertificateDto();
        	noDuesDto.setSuccessFlag(MainetConstants.FlagN);
            LOGGER.error("Problem while getting property by property Number " + responseEntity);
        }

        return noDuesDto;
    }

    @Override
    @POST
    @Path("/fetchCheckListForNoDues")
    @Transactional(readOnly = true)
    @Produces(MediaType.APPLICATION_JSON)
	public List<DocumentDetailsVO> fetchCheckList(@RequestBody NoDuesCertificateDto dto) {

        final WSRequestDTO wsdto = new WSRequestDTO();
        List<DocumentDetailsVO> docs = null;
        wsdto.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
        WSResponseDTO response = brmsCommonService.initializeModel(wsdto);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
            CheckListModel checkListModel = (CheckListModel) checklist.get(0);
            docs = populateCheckListModel(dto, checkListModel, wsdto);
        }
        return docs;
    }

    @SuppressWarnings("unchecked")
    private List<DocumentDetailsVO> populateCheckListModel(NoDuesCertificateDto dto, CheckListModel checkListModel,
            WSRequestDTO wsdto) {

        List<DocumentDetailsVO> checklist = new ArrayList<>(0);
        checkListModel.setOrgId(dto.getOrgId());
        checkListModel.setServiceCode(serviceMasterRepository.getServiceShortCode(dto.getSmServiceId(), dto.getOrgId()));
        wsdto.setDataModel(checkListModel);
        WSResponseDTO response = brmsCommonService.getChecklist(wsdto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            checklist = (List<DocumentDetailsVO>) response.getResponseObj();
            if (checklist != null && !checklist.isEmpty()) {
                long cnt = 1;
                for (final DocumentDetailsVO doc : checklist) {
                    doc.setDocumentSerialNo(cnt);
                    cnt++;
                }
            }
        }
        return checklist;
    }

    @Override
    @POST
    @Path("/generateNoDuesCertificate")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
	public NoDuesCertificateDto generateNoDuesCertificate(@RequestBody NoDuesCertificateDto noDuesCertificateDto) {
        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(noDuesCertificateDto.getOrgId());
        setRequestApplicantDetails(reqDto, noDuesCertificateDto);

		/*
		 * final String appNumber = propertyService
		 * .createApplicationNumberForSKDCL(noDuesCertificateDto.getApplicantDto());
		 * 
		 * reqDto.setReferenceId(appNumber);
		 */

		final Long applicationId = applicationService.createApplication(reqDto);
		noDuesCertificateDto.setApmApplicationId(applicationId);

		noDuesCertificateDto.setApplicationNo(applicationId.toString());
        
		noDuesCertificateDto.getPropertyDetails().forEach(tr -> {
			if (tr.getPropDetId() == null) {
				tr.setOrgId(noDuesCertificateDto.getOrgId());
				tr.setCreatedBy(noDuesCertificateDto.getEmpId());
				tr.setCreatedDate(new Date());
				tr.setLgIpMac(noDuesCertificateDto.getMacAddress());
				tr.setApplicationId(applicationId);
				tr.setIsDeleted(MainetConstants.FlagN);
				tr.setNoOfCopies(noDuesCertificateDto.getNoOfCopies());
			} else {
				tr.setUpdatedBy(noDuesCertificateDto.getEmpId());
				tr.setUpdatedDate(new Date());
				tr.setLgIpMacUpd(noDuesCertificateDto.getMacAddress());
				tr.setIsDeleted(MainetConstants.FlagN);
				tr.setNoOfCopies(noDuesCertificateDto.getNoOfCopies());
			}
		});

		List<NoDuesPropertyDetailsEntity> entityList = new ArrayList<>();
		noDuesCertificateDto.getPropertyDetails().forEach(tr -> {
			NoDuesPropertyDetailsEntity entity = new NoDuesPropertyDetailsEntity();
			BeanUtils.copyProperties(tr, entity);
			entity.setOutwardNo(
					noDuesCertificateDto.getOutwardNo() != null ? noDuesCertificateDto.getOutwardNo() : null);
			entityList.add(entity);
		});
		noDuesDetailsRepository.save(entityList);
        if ((noDuesCertificateDto.getDocs() != null) && !noDuesCertificateDto.getDocs().isEmpty()) {
			reqDto.setApplicationId(applicationId);
			// 9reqDto.setReferenceId(appNumber);
            fileUploadService.doFileUpload(noDuesCertificateDto.getDocs(), reqDto);
        }

		sendSMSEmail(noDuesCertificateDto, organisation, applicationId);
        return noDuesCertificateDto;

    }



	private void sendSMSEmail(NoDuesCertificateDto noDuesCertificateDto, final Organisation organisation,
			final Long applicationNo) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setAppNo(applicationNo.toString());
		dto.setEmail(noDuesCertificateDto.getApplicantDto().getEmailId());
		dto.setMobnumber(noDuesCertificateDto.getApplicantDto().getMobileNo());
		dto.setAppName(getApplicantFullName(noDuesCertificateDto.getApplicantDto()));
		dto.setReferenceNo(noDuesCertificateDto.getApplicationNo());
		if (noDuesCertificateDto.getSmServiceId() != null && noDuesCertificateDto.getOrgId() != null) {// Defect #135126
			ServiceMaster service = serviceMaster.getServiceMaster(noDuesCertificateDto.getSmServiceId(),
					noDuesCertificateDto.getOrgId());
			if (service != null) {
				dto.setServName(service.getSmServiceName());
				dto.setServNameMar(service.getSmServiceNameMar());
			}
		}
		/*
		 * if (payAmount != null) dto.setAppAmount(payAmount.toString());
		 */
		dto.setDeptShortCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
		dto.setLangId(noDuesCertificateDto.getLangId());
		if (noDuesCertificateDto.getEmpId() != 0) {
			dto.setUserId(noDuesCertificateDto.getEmpId());
		}
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.PROPERTY, noDuesCertificateDto.getServiceUrl(),
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, noDuesCertificateDto.getLangId());
	}

	private static String getApplicantFullName(final ApplicantDetailDTO applicantDto) {
		final StringBuilder builder = new StringBuilder();
		builder.append(applicantDto.getApplicantFirstName() == null ? "" : applicantDto.getApplicantFirstName());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(applicantDto.getApplicantMiddleName() == null ? "" : applicantDto.getApplicantMiddleName());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(applicantDto.getApplicantLastName() == null ? "" : applicantDto.getApplicantLastName());
		return builder.toString();
	}

    private void setRequestApplicantDetails(RequestDTO reqDto, NoDuesCertificateDto noDuesCertificateDto) {
		BeanUtils.copyProperties(noDuesCertificateDto.getApplicantDto(), reqDto);
		reqDto.setfName(noDuesCertificateDto.getApplicantDto().getApplicantFirstName());
		reqDto.setmName(noDuesCertificateDto.getApplicantDto().getApplicantMiddleName());
		reqDto.setlName(noDuesCertificateDto.getApplicantDto().getApplicantLastName());
		reqDto.setTitleId(noDuesCertificateDto.getApplicantDto().getApplicantTitle());
		reqDto.setEmail(noDuesCertificateDto.getApplicantDto().getEmailId());
		reqDto.setMobileNo(noDuesCertificateDto.getApplicantDto().getMobileNo());
        reqDto.setDeptId(noDuesCertificateDto.getDeptId());
        reqDto.setOrgId(noDuesCertificateDto.getOrgId());
        reqDto.setServiceId(noDuesCertificateDto.getSmServiceId());
        reqDto.setLangId(Long.valueOf(noDuesCertificateDto.getLangId()));
        reqDto.setUserId(noDuesCertificateDto.getEmpId());
        reqDto.setReferenceId(noDuesCertificateDto.getPropNo());

		reqDto.setBldgName(noDuesCertificateDto.getApplicantDto().getBuildingName());
		reqDto.setRoadName(noDuesCertificateDto.getApplicantDto().getRoadName());
		reqDto.setAreaName(noDuesCertificateDto.getApplicantDto().getAreaName());
		if (!StringUtils.isEmpty(noDuesCertificateDto.getApplicantDto().getPinCode()))
		reqDto.setPincodeNo(Long.valueOf(noDuesCertificateDto.getApplicantDto().getPinCode()));
		if (noDuesCertificateDto.getApplicantDto().getDwzid3() != null)
			reqDto.setBlockNo(noDuesCertificateDto.getApplicantDto().getDwzid3().toString());
		if (noDuesCertificateDto.getApplicantDto().getDwzid2() != null)
			reqDto.setWardNo(Long.valueOf(noDuesCertificateDto.getApplicantDto().getDwzid2()));
		if (noDuesCertificateDto.getApplicantDto().getDwzid1() != null)
			reqDto.setZoneNo(Long.valueOf(noDuesCertificateDto.getApplicantDto().getDwzid1()));
		reqDto.setFlatBuildingNo(noDuesCertificateDto.getApplicantDto().getFlatBuildingNo());
		noDuesCertificateDto.getApplicantDto().setOrgId(noDuesCertificateDto.getOrgId());

    }

    @Override
    @POST
    @Path("/fetchChargesForNoDues")
    @Transactional(readOnly = true)
    @Produces(MediaType.APPLICATION_JSON)
	public NoDuesCertificateDto fetchChargesForNoDues(@RequestBody NoDuesCertificateDto noDuesDto) {
        Organisation org = new Organisation();
        org.setOrgid(noDuesDto.getOrgId());
        ServiceMaster service = serviceMaster.getServiceMaster(noDuesDto.getSmServiceId(), noDuesDto.getOrgId());
        if (service.getSmFeesSchedule().equals(1l)) {
            noDuesDto.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
            noDuesDto.setAppliChargeFlag(MainetConstants.N_FLAG);
        }
        if (noDuesDto.getAppliChargeFlag().equals(MainetConstants.Y_FLAG)) {
            List<BillDisplayDto> charges = propertyBRMSService.fetchApplicationForNoDues(
                    org,
                    noDuesDto.getDeptId(), service.getSmShortdesc(), MainetConstants.ChargeApplicableAt.APPLICATION,noDuesDto.getNoOfCopies(),service.getSmServiceId());
            noDuesDto.setCharges(charges);
            noDuesDto.setTotalPaybleAmt(charges.stream().mapToDouble(c -> c.getTotalTaxAmt().doubleValue()).sum());
        }
        return noDuesDto;
    }
    
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
            String serviceShortCode) {

        boolean updateFlag = false;
        try {
            if (StringUtils.equalsIgnoreCase(eventName, serviceShortCode)) {

                if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)) {
                //	assesmentMstRepository.updateAgencyApprovalWorkflow(MainetConstants.FlagA,
                          //  taskAction.getEmpId(), taskAction.getApplicationId());
                } else if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.REJECTED)) {
                	//assesmentMstRepository.updateAgencyApprovalWorkflow(MainetConstants.FlagR,
                      //      taskAction.getEmpId(), taskAction.getApplicationId());
                }
                updateWorkflowTaskAction(taskAction, serviceId);
                updateFlag = true;

            }
        } catch (Exception exception) {
            LOGGER.error("Exception Occured while Updating workflow action task", exception);
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return updateFlag;
    }
    
    @Transactional
    @WebMethod(exclude = true)
    private WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId) {

        WorkflowTaskActionResponse workflowResponse = null;
        try {
            String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getProcessName(serviceId, taskAction.getOrgId());
            if (StringUtils.isNotBlank(processName)) {

                WorkflowProcessParameter workflowDto = new WorkflowProcessParameter();
                workflowDto.setProcessName(processName);
                workflowDto.setWorkflowTaskAction(taskAction);
                workflowResponse = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowExecutionService.class).updateWorkflow(workflowDto);

            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return workflowResponse;
    }
    
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiCharges(Organisation org,Long deptId, String serviceCode,String chargeAppAt) {
    	List<BillDisplayDto> billDtoList = propertyBRMSService.fetchApplicationOrScurtinyCharges(org, deptId, serviceCode, chargeAppAt, null, null);
        Double baseRate = 0d;
        double amount;
        final Map<Long, Double> chargeMap = new HashMap<>();
        for (final BillDisplayDto billDto : billDtoList) {
            amount = chargeMap.containsKey(billDto.getTaxId()) ? chargeMap.get(billDto.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(billDto.getTaxId(), billDto.getCurrentYearTaxAmt().doubleValue());
        }
        return chargeMap;

    }

	@Override
	@POST
	@Path("/getPropertyDetailsByPropertyNumberNFlatNo")
	@Produces(MediaType.APPLICATION_JSON)
	public NoDuesCertificateDto getPropertyDetailsByPropertyNumberNFlatNo(@RequestBody NoDuesCertificateDto dto) {
		LOGGER.info("Start of getPropertyDetailsByPropertyNumberNFlatNo Method  .....");
		NoDuesCertificateDto noDuesDto = null;
		PropertyDetailDto detailDTO = null;
		PropertyInputDto propInputDTO = new PropertyInputDto();
		propInputDTO.setPropertyNo(dto.getPropNo());
		if (dto.getPropNo() == null) {
			propInputDTO.setOldPropertyNo(dto.getOldpropno());
		}
		if (dto.getFlatNo() != null)
			propInputDTO.setFlatNo(dto.getFlatNo());
		propInputDTO.setOrgId(dto.getOrgId());
		final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
				ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
		if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
			noDuesDto = new NoDuesCertificateDto();
			detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
			LOGGER.info("PropertyDetailDto formed is " + detailDTO.toString());
			if (detailDTO != null && detailDTO.getStatus().equalsIgnoreCase("Success")) {
				noDuesDto.setOwnerName(detailDTO.getPrimaryOwnerName());
				if (org.apache.commons.lang3.StringUtils.isEmpty(noDuesDto.getOwnerName())) {
					noDuesDto.setOwnerName(detailDTO.getJointOwnerName());
				}
				noDuesDto.setOwnerAddress(detailDTO.getAddress());
				noDuesDto.setOwnerCorrAdd(detailDTO.getCorrAddress());
				noDuesDto.setTotalOutsatandingAmt(detailDTO.getTotalOutsatandingAmt());
				noDuesDto.setPropNo(dto.getPropNo());
				noDuesDto.setAppliEmail(detailDTO.getOwnerEmail());
				noDuesDto.setAppliMobileno(detailDTO.getPrimaryOwnerMobNo());
				noDuesDto.setFlatNo(detailDTO.getFlatNo());
				noDuesDto.setSuccessFlag(MainetConstants.FlagY);
				if (dto.getPropNo() == null) {
					noDuesDto.setOldpropno(dto.getOldpropno());
				}
			} else {
				noDuesDto.setSuccessFlag(MainetConstants.FlagN);
			}
		} else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
			noDuesDto = new NoDuesCertificateDto();
			noDuesDto.setSuccessFlag(MainetConstants.FlagN);
			LOGGER.info("No Data found for given property No ");
		} else {
			noDuesDto = new NoDuesCertificateDto();
			noDuesDto.setSuccessFlag(MainetConstants.FlagN);
			LOGGER.error("Problem while getting property by property Number " + responseEntity);
		}
		LOGGER.info("End of getPropertyDetailsByPropertyNumberNFlatNo Method  .....");
		return noDuesDto;
	}

	@Override
	@POST
	@Path("/getNoDuesApplicationDetails")
	@Transactional(readOnly = true)
	public NoDuesCertificateDto getNoDuesDetails(@RequestBody NoDuesCertificateDto dto) {
		LOGGER.info("Start of getNoDuesDetails Method  .....");
		TbCfcApplicationMstEntity masterEntity = cfcApplicationService
				.getCFCApplicationByRefNoOrAppNo(dto.getApplicationNo(), dto.getApmApplicationId(), dto.getOrgId());
		if (masterEntity != null) {
			CFCApplicationAddressEntity addressEntity = cfcApplicationService
					.getApplicantsDetails(masterEntity.getApmApplicationId());
			ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
			BeanUtils.copyProperties(addressEntity, applicantDto);
			BeanUtils.copyProperties(masterEntity, applicantDto);
			applicantDto.setApplicantFirstName(masterEntity.getApmFname());
			applicantDto.setApplicantMiddleName(masterEntity.getApmMname());
			applicantDto.setApplicantLastName(masterEntity.getApmLname());
			if (masterEntity.getApmTitle() != null)
				applicantDto.setApplicantTitle(masterEntity.getApmTitle());

			dto.setApplicationNo(masterEntity.getRefNo());
			applicantDto.setBuildingName(addressEntity.getApaBldgnm());
			applicantDto.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo());
			applicantDto.setRoadName(addressEntity.getApaRoadnm());
			if (addressEntity.getApaWardNo() != null)
				applicantDto.setDwzid2(addressEntity.getApaWardNo());
			if (addressEntity.getApaZoneNo() != null)
				applicantDto.setDwzid1(addressEntity.getApaZoneNo());
			applicantDto.setAreaName(addressEntity.getApaAreanm());
			if (addressEntity.getApaPincode() != null)
			applicantDto.setPinCode(addressEntity.getApaPincode().toString());
			// applicantDto.setAreaName(addressEntity.getApaCityName());
			applicantDto.setMobileNo(addressEntity.getApaMobilno());
			applicantDto.setEmailId(addressEntity.getApaEmail());
			dto.setApmApplicationId(masterEntity.getApmApplicationId());
			List<NoDuesPropertyDetailsEntity> entityList = noDuesDetailsRepository
					.getByApplicationId(masterEntity.getApmApplicationId(), dto.getOrgId());
			if (entityList != null && !entityList.isEmpty()) {
				entityList.forEach(tr -> {
					NoDuesPropertyDetailsDto noDues = new NoDuesPropertyDetailsDto();
					BeanUtils.copyProperties(tr, noDues);
					dto.getPropertyDetails().add(noDues);
					if (noDues.getNoOfCopies() != null) {
					dto.setNoOfCopies(noDues.getNoOfCopies());
					}
					if(noDues.getOutwardNo() != null) {
						dto.setOutwardNo(noDues.getOutwardNo());
					}
				});
			}

			dto.setApplicantDto(applicantDto);

			List<CFCAttachment> att = iChecklistVerificationService
					.findAttachmentsForAppId(masterEntity.getApmApplicationId(), null, dto.getOrgId());
			List<DocumentDetailsVO> docList = new ArrayList<DocumentDetailsVO>();
			att.forEach(at -> {
				DocumentDetailsVO dvo = new DocumentDetailsVO();
				dvo.setAttachmentId(at.getAttId());
				dvo.setCheckkMANDATORY(at.getMandatory());
				dvo.setDocumentSerialNo(at.getClmSrNo());
				dvo.setDocumentName(at.getAttFname());
				dvo.setUploadedDocumentPath(at.getAttPath());
				docList.add(dvo);
			});
			dto.setDocs(docList);
		}
		LOGGER.info("End of getNoDuesDetails Method  .....");
		return dto;
	}

	@Override
	@Transactional
	public void updateNoDuesProprtyDetails(NoDuesCertificateDto noDuesCertificateDto) {
		List<NoDuesPropertyDetailsEntity> entityList = new ArrayList<>();
		noDuesCertificateDto.getPropertyDetails().forEach(tr -> {
			NoDuesPropertyDetailsEntity entity = new NoDuesPropertyDetailsEntity();
			BeanUtils.copyProperties(tr, entity);
			entityList.add(entity);
		});
		noDuesDetailsRepository.save(entityList);

	}

	@Override
	@POST
	@Path("/getChequeClearanceStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = true)
	public boolean getChequeClearanceStatus(@RequestBody NoDuesCertificateDto noDuesCertificateDto) {
		LOGGER.info("Start of getChequeClearanceStatus Method  .....");
		boolean status = false;
		Organisation org = new Organisation();
		org.setOrgid(noDuesCertificateDto.getOrgId());
		final List<Long> modeIds = new ArrayList<>(0);
		final List<LookUp> payLookup = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
				org);
		if ((payLookup != null) && !payLookup.isEmpty()) {
			for (final LookUp payPrefix : payLookup) {
				if (PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_Q.equals(payPrefix.getLookUpCode())
						|| PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_P.equals(payPrefix.getLookUpCode())
						|| PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_D.equals(payPrefix.getLookUpCode())
						|| PrefixConstants.PaymentMode.BANK.equals(payPrefix.getLookUpCode())) {
					modeIds.add(payPrefix.getLookUpId());
				}
			}
		}
		String billingMethod = null;
		LookUp billingMethodLookUp = null;
		Long financeYearId = financialYearService.getFinanceYearId(new Date());
		FinancialYear finYear = financialYearService.getFinincialYearsById(financeYearId, org.getOrgid());
		if (noDuesCertificateDto.getPropertyDetails() != null && !noDuesCertificateDto.getPropertyDetails().isEmpty()) {
			for (NoDuesPropertyDetailsDto dto : noDuesCertificateDto.getPropertyDetails()) {
				Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(dto.getPropNo(), org.getOrgid());
				billingMethodLookUp = null;
				try {
					billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, org);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (billingMethodLookUp != null) {
					billingMethod = billingMethodLookUp.getLookUpCode();
				}

				if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
					if (noDuesDetailsRepository.getCountPendingChequeClearanceWithFlatNo(modeIds, dto.getPropNo(),
							dto.getFlatNo(), org.getOrgid(),finYear.getFaFromDate(),finYear.getFaToDate()) > 0) {
						return false;
					} else {
						status = true;
					}
				} else {
					if (noDuesDetailsRepository.getCountPendingChequeClearance(modeIds, dto.getPropNo(),
							org.getOrgid(),finYear.getFaFromDate(),finYear.getFaToDate()) > 0) {
						return false;
					} else {
						status = true;
					}
				}

			}
		}
		LOGGER.info("End of getChequeClearanceStatus Method  .....");
		return status;
	}

	@Override
	@POST
	@Path("/getPropertyNoDuesServiceData")
	@Consumes(MediaType.APPLICATION_JSON)
	public NoDuesCertificateDto getPropertyServiceData(@RequestBody NoDuesCertificateDto dto) {
		LOGGER.info("Start of getPropertyServiceData Method  .....");
		Organisation org = new Organisation();
		org.setOrgid(dto.getOrgId());
		ServiceMaster serviceDto = serviceMaster.getServiceByShortName(dto.getSmShortCode(), dto.getOrgId());
		dto.setDeptId(serviceDto.getTbDepartment().getDpDeptid());
		dto.setSmServiceId(serviceDto.getSmServiceId());
		dto.setAppliChargeFlag(serviceDto.getSmAppliChargeFlag());
		dto.setSmChklstVerify(serviceDto.getSmChklstVerify());
		dto.setSmScrutinyApplicableFlag(serviceDto.getSmScrutinyApplicableFlag());
		dto.setSmFeesSchedule(serviceDto.getSmFeesSchedule());
		final LookUp processLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceDto.getSmProcessId(),
				org);
		if (processLookUp != null)
			dto.setSmProcessLookUpCode(processLookUp.getLookUpCode());

		if (MainetConstants.Property.DUBLICATE_BILL.equals(dto.getSmShortCode())
				|| MainetConstants.Property.EXT.equals(dto.getSmShortCode())) {
			List<FinancialYear> financialYearList = ApplicationContextProvider.getApplicationContext()
					.getBean(IFinancialYearService.class).getAllFinincialYear();
			String financialYear = null;
			Map<Long, String> yearMap = new LinkedHashMap<>(0);
			for (final FinancialYear finYearTemp : financialYearList) {
				try {
					financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				} catch (Exception e) {
					e.printStackTrace();
				}
				yearMap.put(finYearTemp.getFaYear(), financialYear);

			}
			dto.setFinancialYearMap(yearMap);
		}
		LOGGER.info("End of getPropertyServiceData Method  .....");
		return dto;
	}

	@Override
	@POST
	@Path("/initiateWorkflowForPropertyFreeService")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean initiateWorkflowForFreeService(@RequestBody NoDuesCertificateDto dto) {
		LOGGER.info("Start of initiateWorkflowForFreeService Method for Property Services .....");
		try {
			ApplicationMetadata applicationData = new ApplicationMetadata();
			final ApplicantDetailDTO applicantDetailDto = dto.getApplicantDto();
			applicantDetailDto.setOrgId(dto.getOrgId());
			applicationData.setApplicationId(dto.getApmApplicationId());
			applicationData.setReferenceId(dto.getApplicationNo());
			applicationData.setIsCheckListApplicable(
					(dto.getSmChklstVerify() != null && MainetConstants.FlagY.equals(dto.getSmChklstVerify())) ? true
							: false);
			applicationData.setOrgId(dto.getOrgId());
			applicantDetailDto.setServiceId(dto.getSmServiceId());
			applicantDetailDto.setDepartmentId(dto.getDeptId());
			applicantDetailDto.setUserId(dto.getEmpId());
			applicantDetailDto.setOrgId(dto.getOrgId());
			applicantDetailDto.setDwzid1(applicantDetailDto.getDwzid1());
			applicantDetailDto.setDwzid2(applicantDetailDto.getDwzid2());
			applicantDetailDto.setDwzid3(applicantDetailDto.getDwzid3());
			applicantDetailDto.setDwzid4(applicantDetailDto.getDwzid4());
			applicantDetailDto.setDwzid5(applicantDetailDto.getDwzid5());
			commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
		} catch (Exception e) {
			LOGGER.error("Exception Occured While initiating workflow " + e);
			return false;
		}
		LOGGER.info("End of initiateWorkflowForFreeService Method for Property Services.....");
		return true;
	}

	@Override
	@POST
	@Path("/CheckWorkflowDefined")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean isWorkflowDefined(@RequestBody NoDuesCertificateDto dto) {
		WorkflowMas mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(dto.getOrgId(), dto.getDeptId(),
				dto.getSmServiceId(), dto.getApplicantDto().getDwzid1(), dto.getApplicantDto().getDwzid2(),
				dto.getApplicantDto().getDwzid3(), dto.getApplicantDto().getDwzid4(),
				dto.getApplicantDto().getDwzid5());
		if (mas == null) {
			return false;
		}
		return true;
	}

	@Override
	@POST
	@Path("/getPropertyBillingMethod/{propNo}/orgId/{orgId}")
	public Object getPropertyBillingMethod(@PathParam("propNo") String propNo, @PathParam("orgId") Long orgId) {
		LOGGER.info("Start of getPropertyBillingMethod Method.....");
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		String billingMethod = "";
		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo, org.getOrgid());
		LookUp billingMethodLookUp = null;
		try {
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, org);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (billingMethodLookUp != null) {
			billingMethod = billingMethodLookUp.getLookUpCode();
		}
		JSONObject obj = new JSONObject();
		obj.put("billingMethod", billingMethod);
		LOGGER.info("End of getPropertyBillingMethod Method.....");
		return obj.toString();
	}

	@Override
	@POST
	@Path("/getPropertyFlatList/{propNo}/orgId/{orgId}")
	public List<String> getPropertyFlatList(@PathParam("propNo") String propNo, @PathParam("orgId") String orgId) {
		List<String> flatNoList = new ArrayList<String>();
		flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo, Long.valueOf(orgId));
		return flatNoList;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {
		LOGGER.info("Start of getWordZoneBlockByApplicationId Method.....");
		final CFCApplicationAddressEntity master = iCFCApplicationAddressService
				.getApplicationAddressByAppId(applicationId, orgId);
		WardZoneBlockDTO wardZoneDTO = null;
		if (master != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (master.getApaZoneNo() != null) {
				wardZoneDTO.setAreaDivision1(master.getApaZoneNo());
			}
			if (master.getApaWardNo() != null) {
				wardZoneDTO.setAreaDivision2(master.getApaWardNo());
			}

			if (StringUtils.isNotEmpty(master.getApaBlockno())) {
				final Long blockNo = Long.parseLong(master.getApaBlockno());
				wardZoneDTO.setAreaDivision3(blockNo);
			}
		}
		LOGGER.info("End of getWordZoneBlockByApplicationId Method.....");
		return wardZoneDTO;
	}

}
