package com.abm.mainet.adh.service;

import java.text.Format;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.dao.IAdvertiserMasterDao;
import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.domain.AdvertiserMasterHistoryEntity;
import com.abm.mainet.adh.dto.ADHRequestDto;
import com.abm.mainet.adh.dto.ADHResponseDTO;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.dto.LicenseLetterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.repository.AdvertiserMasterRepository;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
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
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
import com.ibm.icu.text.SimpleDateFormat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cherupelli.srikanth
 * @since 03 august 2019
 */
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.adh.service.IAdvertiserMasterService")
@Api(value = "/newAgencyService")
@Path("/newAgencyService")
@Service
public class AdvertiserMasterServiceImpl implements IAdvertiserMasterService {

    private static Logger LOGGER = Logger.getLogger(AdvertiserMasterServiceImpl.class);
    @Autowired
    private AdvertiserMasterRepository advertiserMasterRepository;

    @Resource
    private AuditService auditService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IFileUploadService uploadService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ICFCApplicationMasterService cfcApplicationService;

    @Autowired
    private INewHoardingApplicationService newHoardingApplicationService;

    @Autowired
    private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;

    @Autowired
    ILicenseValidityMasterService licenseValidityMasterService;
    
    @Autowired
    private IContractAgreementService iContractAgreementService;

    @Autowired
    private IADHBillMasService ADHBillMasService;

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void saveAdevertiserMasterData(AdvertiserMasterDto masterDto) {

        try {
            AdvertiserMasterEntity advertiserMaster = new AdvertiserMasterEntity();
            BeanUtils.copyProperties(masterDto, advertiserMaster);
            advertiserMasterRepository.save(advertiserMaster);
            AdvertiserMasterHistoryEntity history = new AdvertiserMasterHistoryEntity();
            history.sethStatus(MainetConstants.FlagA);
            auditService.createHistory(advertiserMaster, history);
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While Saving the Advertiser Master Data", exception);
        }

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void updateAdvertiserMasterData(AdvertiserMasterDto masterDto) {

        try {
            AdvertiserMasterEntity advertiserMaster = new AdvertiserMasterEntity();
            BeanUtils.copyProperties(masterDto, advertiserMaster);
            advertiserMasterRepository.save(advertiserMaster);
            AdvertiserMasterHistoryEntity history = new AdvertiserMasterHistoryEntity();
            history.sethStatus(MainetConstants.FlagU);
            auditService.createHistory(advertiserMaster, history);
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While Updating the Advertiser Master Data", exception);
        }

    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch get All Advertiser Master By OrgId", notes = "Fetch get All Advertiser Master By OrgId", response = Object.class)
    @Path("/getAllAdvertiserMasterByOrgId/orgId/{orgId}")
    public List<AdvertiserMasterDto> getAllAdvertiserMasterByOrgId(@PathParam("orgId") Long orgId) {

        List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<AdvertiserMasterEntity> entityList = advertiserMasterRepository.findByOrgId(orgId);
            if (CollectionUtils.isNotEmpty(entityList)) {
                entityList.forEach(entity -> {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Advertiser Master List", exception);
        }
        return masterDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public AdvertiserMasterDto getAdvertiserMasterByOrgidAndAgencyId(Long orgId, Long agencyId) {
        AdvertiserMasterDto masterDto = null;
        try {
            AdvertiserMasterEntity entity = advertiserMasterRepository.findByOrgIdAndAgencyId(orgId, agencyId);
            if (entity != null) {
                masterDto = new AdvertiserMasterDto();
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Advertiser Master agencyId", exception);
        }
        return masterDto;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<AdvertiserMasterDto> searchAdvertiserMasterData(Long orgId, String advertiserNumber,
            String advertiserOldNumber, String advertiserName, String advertiserStatus) {
        List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<AdvertiserMasterEntity> entityList = ApplicationContextProvider.getApplicationContext()
                    .getBean(IAdvertiserMasterDao.class).searchAdvertiserData(orgId, advertiserNumber,
                            advertiserOldNumber, advertiserName, advertiserStatus);
            if (CollectionUtils.isNotEmpty(entityList)) {
                entityList.forEach(entity -> {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    if (StringUtils.isNotBlank(masterDto.getAgencyStatus())) {
                        if (StringUtils.equalsIgnoreCase(masterDto.getAgencyStatus(), MainetConstants.FlagA)) {
                            masterDto.setAgencyStatus(MainetConstants.Common_Constant.ACTIVE);
                        }
                        if (StringUtils.equalsIgnoreCase(masterDto.getAgencyStatus(), MainetConstants.FlagI)) {
                            masterDto.setAgencyStatus(MainetConstants.Common_Constant.INACTIVE);
                        }
                        if (StringUtils.equalsIgnoreCase(masterDto.getAgencyStatus(), MainetConstants.FlagC)) {
                            masterDto.setAgencyStatus(MainetConstants.Common_Constant.CANCELLED);
                        }
                        if (StringUtils.equalsIgnoreCase(masterDto.getAgencyStatus(), MainetConstants.FlagT)) {
                            masterDto.setAgencyStatus(MainetConstants.Common_Constant.TERMINATED);
                        }
                    }

                    if (masterDto.getAgencyLicIssueDate() != null) {
                        masterDto.setAgencyRegisDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                                .format(masterDto.getAgencyLicIssueDate()));
                    }
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
        }
        return masterDtoList;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "saveAgencyRegistration", notes = "saveAgencyRegistration", response = Object.class)
    @Path("/saveAgencyRegistration")
    public AgencyRegistrationResponseDto saveAgencyRegistrationData(
            @RequestBody AgencyRegistrationRequestDto requestDto) {

        AgencyRegistrationResponseDto responseDto = new AgencyRegistrationResponseDto();

        Long applicationId = setAndSaveApplicationDetails(requestDto);

        if (applicationId != null && applicationId != 0) {
            // This method saves the uploded files
            requestDto.setApplicationId(applicationId);
            requestDto.getMasterDto().setApplicationId(applicationId);
            requestDto.getMasterDto().setApmApplicationId(applicationId);
            responseDto.setApplicationId(applicationId);
            responseDto.setStatus(MainetConstants.WebServiceStatus.SUCCESS);

            uploadService.doFileUpload(requestDto.getDocumentList(), requestDto);
        }

        saveAndInitializeWorkFlow(requestDto);
        return responseDto;
    }

    public ApplicantDetailDTO getApplicationDetails(Long applicationNo, Long orgId) {
        ApplicantDetailDTO applicant = new ApplicantDetailDTO();
        TbCfcApplicationMstEntity masterEntity = cfcApplicationService.getCFCApplicationByApplicationId(applicationNo,
                orgId);
        if (masterEntity != null) {
            applicant.setApplicantTitle(masterEntity.getApmTitle());
            applicant.setApplicantFirstName(masterEntity.getApmFname());
            applicant.setApplicantLastName(masterEntity.getApmLname());
            applicant.setGender(masterEntity.getApmSex());
            setGender(applicant, applicant.getOrgId());
            if (StringUtils.isNotBlank(masterEntity.getApmMname())) {
                applicant.setApplicantMiddleName(masterEntity.getApmMname());
            }
            if (StringUtils.isNotBlank(masterEntity.getApmBplNo())) {
                applicant.setIsBPL(MainetConstants.YES);
                applicant.setBplNo(masterEntity.getApmBplNo());
            } else {
                applicant.setIsBPL(MainetConstants.NO);
            }

            if (masterEntity.getApmUID() != null && masterEntity.getApmUID() != 0) {
                applicant.setAadharNo(String.valueOf(masterEntity.getApmUID()));
            }
        }

        CFCApplicationAddressEntity addressEntity = cfcApplicationService.getApplicantsDetails(applicationNo);
        if (addressEntity != null) {
            applicant.setMobileNo(addressEntity.getApaMobilno());
            applicant.setEmailId(addressEntity.getApaEmail());
            applicant.setAreaName(addressEntity.getApaAreanm());
            applicant.setPinCode(String.valueOf(addressEntity.getApaPincode()));

            if (addressEntity.getApaZoneNo() != null && addressEntity.getApaZoneNo() != 0) {
                applicant.setDwzid1(addressEntity.getApaZoneNo());
            }
            if (addressEntity.getApaWardNo() != null && addressEntity.getApaWardNo() != 0) {
                applicant.setDwzid2(addressEntity.getApaWardNo());
            }
            if (StringUtils.isNotBlank(addressEntity.getApaBlockno())) {
                applicant.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
            }
            if (StringUtils.isNotBlank(addressEntity.getApaCityName())) {
                applicant.setVillageTownSub(addressEntity.getApaCityName());
            }
            if (StringUtils.isNotBlank(addressEntity.getApaRoadnm())) {
                applicant.setRoadName(addressEntity.getApaRoadnm());
            }
        }
        return applicant;
    }

    private void setGender(ApplicantDetailDTO applicant, Long orgId) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
        for (final LookUp lookUp : lookUps) {
            if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()) {
                if (lookUp.getLookUpCode().equals(applicant.getGender())) {
                    applicant.setGender(String.valueOf(lookUp.getLookUpId()));
                    break;
                }
            }
        }
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void initializeWorkFlowForFreeService(AgencyRegistrationRequestDto requestDto, boolean loiChargeApplFlag) {

        if (requestDto.isFree()) {
            boolean checkList = false;
            if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
                checkList = true;

                ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

                ApplicationMetadata applicationMetaData = new ApplicationMetadata();

                applicantDto.setApplicantFirstName(requestDto.getMasterDto().getAgencyName());
                applicantDto.setServiceId(requestDto.getServiceId());
                applicantDto.setDepartmentId(requestDto.getDeptId());
                applicantDto.setMobileNo(requestDto.getMasterDto().getAgencyContactNo());
                applicantDto.setUserId(requestDto.getUserId());

                applicationMetaData.setApplicationId(requestDto.getApplicationId());
                applicationMetaData.setIsCheckListApplicable(checkList);
                applicationMetaData.setOrgId(requestDto.getMasterDto().getOrgId());

                applicationMetaData.setIsLoiApplicable(loiChargeApplFlag);
                try {
                    commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
                } catch (Exception e) {
                    throw new FrameworkException("Exception occured while calling workflow");
                }

            }

        }
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<AdvertiserMasterDto> getAgencyRegistrationByAppIdAndOrgId(Long applicationId, Long orgId) {

        // AdvertiserMasterDto masterDto = null;

        List<AdvertiserMasterDto> masterDtolist = new ArrayList<>();
        try {
            List<AdvertiserMasterEntity> entitylist = advertiserMasterRepository.findByApplicationIdAndOrgId(applicationId,
                    orgId);
            if (CollectionUtils.isNotEmpty(entitylist)) {
                entitylist.forEach(entity -> {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtolist.add(masterDto);
                });
            }
        }
        /*
         * try { AdvertiserMasterEntity entity = advertiserMasterRepository.findByApplicationIdAndOrgId(applicationId, orgId); if
         * (entity != null) { masterDto = new AdvertiserMasterDto(); BeanUtils.copyProperties(entity, masterDto); } }
         */ catch (Exception exception) {
            throw new FrameworkException("Error Occurred while fething the Agency Details", exception);
        }
        return masterDtolist;
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
                    advertiserMasterRepository.updateAgencyApprovalWorkflow(MainetConstants.FlagA,
                            taskAction.getEmpId(), taskAction.getApplicationId());
                } else if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.REJECTED)) {
                    advertiserMasterRepository.updateAgencyApprovalWorkflow(MainetConstants.FlagR,
                            taskAction.getEmpId(), taskAction.getApplicationId());
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
    public Map<Long, Double> getLoiCharges(AdvertiserMasterDto masterDto, Long serviceId, String serviceName) {
        final List<ADHRateMaster> adhChargeModelList = new ArrayList<>();
        WSRequestDTO requestDTO = new WSRequestDTO();
        List<ADHRateMaster> adhRequiredCharges;
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        adhRequiredCharges = ApplicationContextProvider.getApplicationContext()
                .getBean(IBRMSADHService.class).getLoiChargesForADHData(requestDTO, masterDto, serviceName);
        }
        else {
        	 adhRequiredCharges = ApplicationContextProvider.getApplicationContext()
                    .getBean(IBRMSADHService.class).getLoiChargesForADH(requestDTO, masterDto.getOrgId(), serviceName);
        }
        

        for (final ADHRateMaster actualRate : adhRequiredCharges) {

            actualRate.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
            actualRate.setOrgId(masterDto.getOrgId());
            actualRate.setServiceCode(serviceName);
            
            actualRate.setRateStartDate(new Date().getTime());
            adhChargeModelList.add(actualRate);
        }

        requestDTO.setDataModel(adhChargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.ADH_SERVICE_CHARGE_URI);
        final List<?> adhRateList = RestClient.castResponse(output, ADHRateMaster.class);
        ADHRateMaster loiCharges = null;
        Double baseRate = 0d;
        double amount;
        final Map<Long, Double> chargeMap = new HashMap<>();
        for (final Object rate : adhRateList) {

            loiCharges = (ADHRateMaster) rate;
            /*
             * LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
             * PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);
             */
            // baseRate = WaterCommonUtility.getAndSetBaseRate(0.0, loiCharges, null,
            // taxLookup.getLookUpCode());
            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(loiCharges.getTaxId(), loiCharges.getFlatRate());
        }
        return chargeMap;

    }

    @Override
    @WebMethod(exclude = true)
    public String generateAgencyLicenceNumber(Organisation org, Long categoryId) {
        String agencyLicNumber = null;
        Long generateSequenceNo = ApplicationContextProvider.getApplicationContext()
                .getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, "TB_ADH_AGENCYMASTER",
                        "AGN_LIC_NO", org.getOrgid(), null, null);

        FinancialYear financialYear = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
        String licFinYear = null;
        if (financialYear != null) {
            licFinYear = Utility.getFinancialYear(financialYear.getFaFromDate(), financialYear.getFaToDate());
        }
        LookUp categoryLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(categoryId, org);

        StringBuilder licenceNumber = new StringBuilder();
        String seperator = "/";
        licenceNumber.append(org.getOrgShortNm()).append(seperator);
        if (categoryLookUp != null) {
            licenceNumber.append(categoryLookUp.getLookUpCode()).append(seperator);
        }
        licenceNumber.append(licFinYear).append(seperator);
        licenceNumber.append(String.format("%06d", generateSequenceNo));
        agencyLicNumber = licenceNumber.toString();
        return agencyLicNumber;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<AdvertiserMasterDto> getAllLicPeriodDetails(Long agencyId, Long orgId) {

        List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<AdvertiserMasterHistoryEntity> masterHistoryEntityList = advertiserMasterRepository
                    .findAllLicPeriodDetails(agencyId, orgId);
            if (CollectionUtils.isNotEmpty(masterHistoryEntityList)) {

                masterHistoryEntityList.forEach(entity -> {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while fetching list of license details", exception);
        }

        return masterDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch Agency Details By LicNo", notes = "Fetch Agency Details By LicNo", response = Object.class)
    @Path("/getAgencyDetailsByLicNo/agencyLicNo/{agencyLicNo}/orgId/{orgId}")
    public AgencyRegistrationResponseDto getAgencyDetailByLicnoAndOrgId(@PathParam("agencyLicNo") String agencyLicNo,
            @PathParam("orgId") Long orgId) {
        // D#79136 code changes here
        List<AdvertiserMasterDto> masterDtolist = new ArrayList<AdvertiserMasterDto>();
        AgencyRegistrationResponseDto agencyRegDto = new AgencyRegistrationResponseDto();
        AdvertiserMasterEntity entity = advertiserMasterRepository.findAgencyByAgencyLicNoAndOrgId(agencyLicNo, orgId);
        if (entity != null) {
            List<AdvertiserMasterDto> listentity = getAgencyRegistrationByAppIdAndOrgId(entity.getApmApplicationId(), orgId);
            agencyRegDto.setMasterDtolist(listentity);
        }

        AdvertiserMasterDto masterDto = null;
        if (entity != null) {
            masterDto = new AdvertiserMasterDto();
            BeanUtils.copyProperties(entity, masterDto);
            ApplicantDetailDTO applicant = getApplicationDetails(entity.getApmApplicationId(), orgId);
            agencyRegDto.setMasterDto(masterDto);
            agencyRegDto.setApplicantDetailDTO(applicant);
            // Defect #125541
            masterDtolist.add(masterDto);
            if (agencyRegDto.getMasterDtolist() != null && agencyRegDto.getMasterDtolist().size() < 0)
                agencyRegDto.setMasterDtolist(masterDtolist);
        }
        return agencyRegDto;
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Fetch Agency Data by Agency Category Id", notes = "Fetch Agency Data by Agency Category Id")
    @Path("/getAgencyCategory/advertiserCategoryId/{advertiserCategoryId}/orgId/{orgId}")
    @Override
    @Transactional(readOnly = true)
    public List<AdvertiserMasterDto> getAgencyDetailsByAgencyCategoryAndOrgId(
            @PathParam("advertiserCategoryId") Long agencyCategoryId, @PathParam("orgId") Long orgId) {

        List<AdvertiserMasterEntity> advertiserEntityList = advertiserMasterRepository
                .findByAgencyCategoryAndOrgId(agencyCategoryId, orgId);
        List<AdvertiserMasterDto> advertiserDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(advertiserEntityList)) {
            advertiserEntityList.forEach(advertserEntity -> {
                AdvertiserMasterDto advertiserDto = new AdvertiserMasterDto();
                BeanUtils.copyProperties(advertserEntity, advertiserDto);
                advertiserDtoList.add(advertiserDto);
            });

        }
        return advertiserDtoList;
    }

    public Long setAndSaveApplicationDetails(AgencyRegistrationRequestDto requestDto) {

        Long applicationId = 0l;
        // Setting all fields in applicantDto to create application Number and saving
        // the data
        RequestDTO applicantDto = new RequestDTO();
        applicantDto.setOrgId(requestDto.getMasterDto().getOrgId());
        applicantDto.setUserId(requestDto.getUserId());
        applicantDto.setLangId(requestDto.getMasterDto().getLangId());
        applicantDto.setServiceId(requestDto.getServiceId());
        applicantDto.setDeptId(requestDto.getDeptId());
        applicantDto.setPayStatus(MainetConstants.FlagF);
        applicantDto.setTitleId(requestDto.getApplicantDetailDto().getApplicantTitle());
        applicantDto.setfName(requestDto.getApplicantDetailDto().getApplicantFirstName());
        applicantDto.setlName(requestDto.getApplicantDetailDto().getApplicantLastName());
        applicantDto.setMobileNo(requestDto.getApplicantDetailDto().getMobileNo());
        applicantDto.setAreaName(requestDto.getApplicantDetailDto().getAreaName());
        applicantDto.setPincodeNo(Long.valueOf(requestDto.getApplicantDetailDto().getPinCode()));
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getRoadName())) {
            applicantDto.setRoadName(requestDto.getApplicantDetailDto().getRoadName());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getVillageTownSub())) {
            applicantDto.setCityName(requestDto.getApplicantDetailDto().getVillageTownSub());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getApplicantMiddleName())) {
            applicantDto.setmName(requestDto.getApplicantDetailDto().getApplicantMiddleName());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getEmailId())) {
            applicantDto.setEmail(requestDto.getApplicantDetailDto().getEmailId());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getAadharNo())) {
            applicantDto.setAadhaarNo(requestDto.getApplicantDetailDto().getAadharNo());
            applicantDto.setUid(Long.valueOf(requestDto.getApplicantDetailDto().getAadharNo()));
        }
        if (requestDto.getApplicantDetailDto().getDwzid1() != null
                && requestDto.getApplicantDetailDto().getDwzid1() != 0) {
            applicantDto.setZoneNo(requestDto.getApplicantDetailDto().getDwzid1());
        }
        if (requestDto.getApplicantDetailDto().getDwzid2() != null
                && requestDto.getApplicantDetailDto().getDwzid2() != 0) {
            applicantDto.setWardNo(requestDto.getApplicantDetailDto().getDwzid2());
        }
        if (requestDto.getApplicantDetailDto().getDwzid3() != null
                && requestDto.getApplicantDetailDto().getDwzid3() != 0) {
            applicantDto.setBlockNo(String.valueOf(requestDto.getApplicantDetailDto().getDwzid3()));
        }

        return applicationId = applicationService.createApplication(applicantDto);

    }

    public void saveAndInitializeWorkFlow(AgencyRegistrationRequestDto requestDto) {

        ServiceMaster serviceMaster = new ServiceMaster();

        Organisation organisationById = ApplicationContextProvider.getApplicationContext()
                .getBean(IOrganisationService.class).getOrganisationById(requestDto.getMasterDto().getOrgId());
        if (StringUtils.isBlank(requestDto.getMasterDto().getAgencyLicNo())) {
            serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE,
                            requestDto.getOrgId());
        } else {
            serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE,
                            requestDto.getOrgId());
        }
        // requestDto.setFree(true);
        requestDto.setServiceId(serviceMaster.getSmServiceId());
        LookUp workflowLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMaster.getSmProcessId(),
                organisationById);
        if (!StringUtils.equals(workflowLookUp.getLookUpCode(), MainetConstants.CommonConstants.NA)) {
            boolean loiChargeApplflag = false;
            if (StringUtils.equalsIgnoreCase(serviceMaster.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
                loiChargeApplflag = true;
            }
            if (requestDto.isFree()) {
                initializeWorkFlowForFreeService(requestDto, loiChargeApplflag);
            }

            if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
                    MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE)) {
                requestDto.getMasterDtolist().forEach(ownDto -> {
                    ownDto.setCreatedDate(new Date());
                    ownDto.setCreatedBy(requestDto.getMasterDto().getUserId());
                    ownDto.setOrgId(requestDto.getMasterDto().getOrgId());
                    ownDto.setLangId(requestDto.getMasterDto().getLangId());
                    ownDto.setUserId(requestDto.getMasterDto().getUserId());
                    ownDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                    ownDto.setLgIpMac(requestDto.getMasterDto().getLgIpMac());
                    ownDto.setAgencyAdd(requestDto.getMasterDto().getAgencyAdd());
                    ownDto.setAgencyName(requestDto.getMasterDto().getAgencyName());
                    ownDto.setAgencyCategory(requestDto.getMasterDto().getAgencyCategory());
                    ownDto.setAgencyLicFromDate(new Date());
                    ownDto.setAgencyLicToDate(requestDto.getMasterDto().getAgencyLicToDate());
                    ownDto.setTrdFtype(requestDto.getMasterDto().getTrdFtype());
                    ownDto.setGstNo(requestDto.getMasterDto().getGstNo());
                    ownDto.setAgencyStatus(requestDto.getMasterDto().getAgencyStatus());
                    ownDto.setServiceId(requestDto.getMasterDto().getServiceId());
                    ownDto.setDeptId(requestDto.getMasterDto().getDeptId());
                    ownDto.setApmApplicationId(requestDto.getMasterDto().getApmApplicationId());
                    ownDto.setAgencyLicNo(requestDto.getMasterDto().getAgencyLicNo());
                    ownDto.setAgencyLicIssueDate(new Date());

                    saveAdevertiserMasterData(ownDto);
                });

            } else if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
                    MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE)) {
                // Start : ADH demo bellow point come
                requestDto.getMasterDtolist().forEach(ownDto -> {
                    ownDto.setAgencyLicFromDate(requestDto.getMasterDto().getAgencyLicFromDate());
                    ownDto.setAgencyLicToDate(requestDto.getMasterDto().getAgencyLicToDate());
                    ownDto.setAgencyStatus(requestDto.getMasterDto().getAgencyStatus());
                    ownDto.setApmApplicationId(requestDto.getMasterDto().getApmApplicationId());
                    /*
                     * if(requestDto.getMasterDto() != null && requestDto.getMasterDto().getAgencyLicNo() != null)
                     * ownDto.setAgencyLicNo(requestDto.getMasterDto().getAgencyLicNo());
                     */
                    updateAdvertiserMasterData(ownDto);
                });
                // END : according to ADH demo this code
                // updateAdvertiserMasterData(requestDto.getMasterDto());
            }

        } else if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
                MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE)) {
            String agencyLicNum = generateAgencyLicenceNumber(organisationById,
                    requestDto.getMasterDto().getAgencyCategory());
            requestDto.getMasterDto().setAgencyLicNo(agencyLicNum);
            requestDto.getMasterDto().setAgencyLicIssueDate(new Date());
            requestDto.getMasterDto().setAgencyStatus(MainetConstants.FlagA);
            requestDto.getMasterDtolist().forEach(ownDto -> {

                saveAdevertiserMasterData(ownDto);
            });
            // saveAdevertiserMasterData(requestDto.getMasterDto());
        } else if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
                MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE)) {
            requestDto.getMasterDto().setAgencyLicIssueDate(new Date());
            requestDto.getMasterDto().setAgencyStatus(MainetConstants.FlagA);
            updateAdvertiserMasterData(requestDto.getMasterDto());
        }
    }

    @Override
    public List<String[]> getAgenLicNoListByOrgId(Long orgId, String agencystatus) {
        return advertiserMasterRepository.findLicNoByOrgId(orgId, agencystatus);
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch License No Agency Name Status By OrgId", notes = "Fetch License No Agency Name Status By OrgId", response = Object.class)
    @Path("/getLicNoAgenNameStatusByOrgId/orgId/{orgId}")
    public List<AdvertiserMasterDto> getLicNoAndAgenNameAndStatusByorgId(@PathParam("orgId") Long orgId) {
        List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();
        List<Object[]> objectList = advertiserMasterRepository.findLicNoAndAgenNameStatusByorgId(orgId);
        if (CollectionUtils.isNotEmpty(objectList)) {
            for (Object[] masterEntity : objectList) {
                AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                if (masterEntity[0] != null) {
                    masterDto.setAgencyLicNo(masterEntity[0].toString());
                }

                masterDto.setAgencyStatus(masterEntity[1].toString());
                masterDto.setAgencyName(masterEntity[2].toString());
                masterDto.setApplicantMobileNo(masterEntity[3].toString());
                masterDtoList.add(masterDto);
            }

        }
        return masterDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
    @Path("/getLicMaxTenureByServiceCode/orgId/{orgId}/serviceShortName/{serviceShortName}")
    public Map<String, String> getLicMaxTenureByServiceCode(@PathParam("orgId") Long orgId,
            @PathParam("serviceShortName") String serviceShortName) {

        Map<String, String> licMaxMap = new LinkedHashMap<String, String>();
        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class).getServiceByShortName(serviceShortName, orgId);
        if (serviceMaster != null) {
            licMaxMap.put("licMaxTenureDays",
                    String.valueOf(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
                            .calculateLicMaxTenureDays(serviceMaster.getTbDepartment().getDpDeptid(),
                                    serviceMaster.getSmServiceId(), null, orgId, MainetConstants.ZERO_LONG)));

            LookUp checkListApplLookUp = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(serviceMaster.getSmChklstVerify(), ApplicationContextProvider
                            .getApplicationContext().getBean(IOrganisationService.class).getOrganisationById(orgId));

            if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
                licMaxMap.put("checkListApplFlag", MainetConstants.FlagY);
            } else {
                licMaxMap.put("checkListApplFlag", MainetConstants.FlagN);
            }
            if (StringUtils.equalsIgnoreCase(serviceMaster.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
                licMaxMap.put("applicationchargeApplFlag", MainetConstants.FlagY);
            } else {
                licMaxMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
            }
        }

        return licMaxMap;
    }

    @Override
    public String getLicNoByApplIdAndOrgId(Long applicationId, Long orgId) {
        return advertiserMasterRepository.findLicNoByOrgIdAndAppId(applicationId, orgId);

    }

    @Override
    public List<LicenseLetterDto> setLicValidPeriodsToPrint(List<AdvertiserMasterDto> masterDtoList) {

        List<LicenseLetterDto> licLetterDtoList = new ArrayList<>();

        long count = 1;
        for (int i = 0; i < masterDtoList.size(); i++) {
            AdvertiserMasterDto secondLicPeriod = masterDtoList.get(i);

            LicenseLetterDto licDto = new LicenseLetterDto();
            licDto.setsNo(count);
            if (secondLicPeriod.getAgencyLicFromDate() != null) {
                licDto.setLicFromDate(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(secondLicPeriod.getAgencyLicFromDate()));
            }
            if (secondLicPeriod.getAgencyLicToDate() != null) {
                licDto.setLicToDate(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(secondLicPeriod.getAgencyLicToDate()));
            }

            if (StringUtils.equals(secondLicPeriod.getAgencyStatus(), MainetConstants.FlagA)
                    && StringUtils.isNotBlank(secondLicPeriod.getAgencyLicNo())) {
                count = count + 1;
                licLetterDtoList.add(licDto);
            }

        }
        return licLetterDtoList;
    }

    @Override
    public String getAgencyNameByAgnIdAndOrgId(Long agencyId, Long orgId) {
        return advertiserMasterRepository.findAgencyNameByAgnIdAndOrgId(agencyId, orgId);
    }

    @Override
    public List<String> getAgencyNameByOrgId(Long orgId) {
        return advertiserMasterRepository.findAgencyNameByOrgId(orgId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void processTheAgencyLicenseGenerationTask(Organisation organisation, Long applicationId, Long actualTaskId,
            AgencyRegistrationModel model) {
        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class)
                .getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE,
                        organisation.getOrgid());
        // get agency master data by IDS
        List<AdvertiserMasterDto> advertiserMasterDto = getAgencyRegistrationByAppIdAndOrgId(applicationId,
                organisation.getOrgid());
        // String agencyLicNo = getLicNoByApplIdAndOrgId(applicationId, organisation.getOrgid());
        if (StringUtils.isEmpty(advertiserMasterDto.get(0).getAgencyLicNo())) {
            String agencyLicNum = generateAgencyLicenceNumber(organisation,
                    advertiserMasterDto.get(0).getAgencyCategory());
            advertiserMasterDto.get(0).setAgencyLicNo(agencyLicNum);
            advertiserMasterDto.get(0).setAgencyStatus(MainetConstants.FlagA);
            advertiserMasterDto.get(0).setAgencyLicIssueDate(new Date());
            /*
             * masterDto.setAgencyStatus("A"); masterDto.setAgencyLicNo(agencyLicNum); masterDto.setAgencyLicIssueDate(new
             * Date());
             */
            model.getAgencyRequestDto().setMasterDtolist(advertiserMasterDto);
            updateTheAgencyMaster(model.getAgencyRequestDto().getMasterDtolist());
            // agencyLicNo = agencyLicNum;
            printAgencyLicenseLetter(model, model.getAgencyRequestDto().getMasterDtolist(), organisation.getOrgid());
        } else {
            AgencyRegistrationResponseDto agencyRegDto = getAgencyDetailByLicnoAndOrgId(
                    advertiserMasterDto.get(0).getAgencyLicNo(),
                    organisation.getOrgid());
            agencyRegDto.getMasterDto().setAgencyStatus("A");
            agencyRegDto.getMasterDto().setAgencyLicIssueDate(new Date());
            updateTheAgencyMaster(agencyRegDto.getMasterDtolist());
            // updateAdvertiserMasterData(masterDto);
            printAgencyLicenseLetter(model, agencyRegDto.getMasterDtolist(), organisation.getOrgid());
        }

        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setIsFinalApproval(true);
        taskAction.setIsObjectionAppealApplicable(false);
        if (StringUtils.isNotBlank(UserSession.getCurrent().getEmployee().getEmpemail())) {
            taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        }
        taskAction.setApplicationId(applicationId);
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setTaskId(actualTaskId);

        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        LookUp workProcessLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMaster.getSmProcessId(),
                UserSession.getCurrent().getOrganisation());
        workflowProcessParameter.setProcessName(workProcessLookUp.getDescLangFirst());
        workflowProcessParameter.setWorkflowTaskAction(taskAction);

        try {

            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);

        } catch (Exception exception) {
            throw new FrameworkException("Exception Occured while updating workflow" + exception);
        }

    }

    private void printAgencyLicenseLetter(AgencyRegistrationModel model, List<AdvertiserMasterDto> masterDto, Long orgId) {
        List<AdvertiserMasterDto> masterDtoList = getAllLicPeriodDetails(masterDto.get(0).getAgencyId(), orgId);
        if (CollectionUtils.isNotEmpty(masterDtoList)) {
            LOGGER.info("ADH list is not empty size masterDtoList " + masterDtoList.size());
            AdvertiserMasterDto advertiserMasterDto = masterDtoList.get(masterDtoList.size() - 1);
            advertiserMasterDto.setAgencyRegisDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                    .format(advertiserMasterDto.getAgencyLicIssueDate()));
            model.setAdvertiserMasterDto(advertiserMasterDto);
            masterDtoList.get(masterDtoList.size() - 1).setAgencyStatus("A");
            model.setLicenseDto(setLicValidPeriodsToPrint(masterDtoList));
        } else {
            LOGGER.info("ADH list is empty masterDtoList " + masterDtoList.size());
        }

    }

    private void updateTheAgencyMaster(List<AdvertiserMasterDto> masterDto) {
        try {
            for (AdvertiserMasterDto dto : masterDto) {

                AdvertiserMasterEntity advertiserMaster = new AdvertiserMasterEntity();
                BeanUtils.copyProperties(dto, advertiserMaster);
                advertiserMasterRepository.save(advertiserMaster);
                AdvertiserMasterHistoryEntity history = new AdvertiserMasterHistoryEntity();
                history.sethStatus(MainetConstants.FlagU);
                // auditService.createHistory(advertiserMaster,history);
                // doing this because create history saving data in ASYNC mode and immediately need saved data therefore some time
                // getting and some not so saving like below way
                BeanUtils.copyProperties(advertiserMaster, history);
                ApplicationContextProvider.getApplicationContext()
                        .getBean(IAdvertiserMasterDao.class).saveInAdvertiserMasterHistoryEntity(history);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While Updating the Advertiser Master Data", exception);
        }

    }

    @Override
    @Transactional
    public List<AdvertiserMasterDto> getAgencyDetailsByOrgIdAndStatus(Long orgId, String agencyStatus) {
        List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<AdvertiserMasterEntity> entityList = advertiserMasterRepository.findByOrgIdAndStatus(orgId, agencyStatus);
            if (CollectionUtils.isNotEmpty(entityList)) {
                entityList.forEach(entity -> {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Advertiser Master List", exception);
        }
        return masterDtoList;

    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "get data using application id and service code", notes = "get data using application id and service code", response = Object.class)
    @Path("/getDataBasedOnApplicationId")
    public ADHResponseDTO getDataByApplicationId(@RequestBody ADHRequestDto adhRequestDto) {
        ADHResponseDTO adhResponseDto = new ADHResponseDTO();
        AgencyRegistrationRequestDto agencyRegDto = new AgencyRegistrationRequestDto();
        List<AdvertiserMasterDto> masterDtoList = null;
        // AdvertiserMasterDto masterDto = null;
        List<AdvertiserMasterEntity> entity = null;
        switch (adhRequestDto.getServiceCode()) {
        case "AGL":
            // data set in AgencyRegistrationResponseDto
            // get data
            entity = advertiserMasterRepository
                    .findByApplicationIdAndOrgId(adhRequestDto.getApplicationId(), adhRequestDto.getOrgId());
            if (entity != null) {
                masterDtoList = new ArrayList<>();
                for (AdvertiserMasterEntity ent : entity) {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(ent, masterDto);
                    masterDtoList.add(masterDto);
                }
                // BeanUtils.copyProperties(entity, masterDtoList);
                ApplicantDetailDTO applicant = getApplicationDetails(entity.get(0).getApmApplicationId(),
                        adhRequestDto.getOrgId());
                agencyRegDto.setMasterDtolist(masterDtoList);
                agencyRegDto.setApplicantDetailDto(applicant);
            }
            adhResponseDto.setAgencyRegistrationRequestDto(agencyRegDto);
            break;
        case "AGR":
            entity = advertiserMasterRepository
                    .findByApplicationIdAndOrgId(adhRequestDto.getApplicationId(), adhRequestDto.getOrgId());
            if (entity != null) {
                masterDtoList = new ArrayList<>();
                for (AdvertiserMasterEntity ent : entity) {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(ent, masterDto);
                    masterDtoList.add(masterDto);
                }
                // BeanUtils.copyProperties(entity, masterDtoList);
                ApplicantDetailDTO applicant = getApplicationDetails(entity.get(0).getApmApplicationId(),
                        adhRequestDto.getOrgId());
                agencyRegDto.setMasterDtolist(masterDtoList);
                agencyRegDto.setApplicantDetailDto(applicant);
            }
            adhResponseDto.setAgencyRegistrationRequestDto(agencyRegDto);
            break;

        // User Story 112154 code changes here
        case "CAL":
            entity = advertiserMasterRepository.findByApplicationIdAndOrgId(adhRequestDto.getApplicationId(),
                    adhRequestDto.getOrgId());
            if (entity != null) {
                masterDtoList = new ArrayList<>();
                for (AdvertiserMasterEntity ent : entity) {
                    AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(ent, masterDto);
                    masterDtoList.add(masterDto);
                }
                // BeanUtils.copyProperties(entity, masterDtoList);
                ApplicantDetailDTO applicant = getApplicationDetails(entity.get(0).getApmApplicationId(),
                        adhRequestDto.getOrgId());
                agencyRegDto.setMasterDtolist(masterDtoList);
                agencyRegDto.setApplicantDetailDto(applicant);
            }
            adhResponseDto.setAgencyRegistrationRequestDto(agencyRegDto);
            break;

        case "HBP":
            adhResponseDto.setAdvertisementReqDto(newHoardingApplicationService
                    .getDataForHoardingApplication(adhRequestDto.getApplicationId(), adhRequestDto.getOrgId()));
            break;
        }
        return adhResponseDto;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = MainetConstants.AdvertisingAndHoarding.PROPERTY_DATA_FETCH_SERVICE, notes = MainetConstants.AdvertisingAndHoarding.PROPERTY_DATA_FETCH_SERVICE, response = Object.class)
    @Path("/getPropertyDetailsByPropertyNumber")
    @Transactional
    public NewAdvertisementApplicationDto getPropertyDetailsByPropertyNumber(NewAdvertisementApplicationDto reqDto) {
        NewAdvertisementApplicationDto applicationDto = new NewAdvertisementApplicationDto();
        PropertyDetailDto detailDTO = null;
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(reqDto.getPropNumber());
        propInputDTO.setOrgId(reqDto.getOrgId());
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                ServiceEndpoints.PROP_BY_PROP_ID);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

            detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
            LOGGER.info("PropertyDetailDto formed is " + detailDTO.toString());
            if (!detailDTO.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.FAIL)) {
                // Defect #124747
                // applicationDto.setPropOwnerName(detailDTO.getPrimaryOwnerName());
                applicationDto.setPropOwnerName(detailDTO.getJointOwnerName());
                applicationDto.setPropAddress(detailDTO.getAddress());
                applicationDto.setPropOutstandingAmt(detailDTO.getTotalOutsatandingAmt());
                applicationDto.setAssessmentCheckFlag(detailDTO.getAssessmentCheckFlag());
            } else {
                applicationDto = null;
                LOGGER.info("Problem while getting property details by property Number " + responseEntity);
            }
        } else {
            applicationDto = null;
            LOGGER.info("Problem while getting property details by property Number " + responseEntity);
        }
        return applicationDto;
    }

    public Map<String, String> getLicMaxTenureByServiceCode(@PathParam("orgId") Long orgId,
            @PathParam("serviceShortName") String serviceShortName, Long licType) {

        Map<String, String> licMaxMap = new LinkedHashMap<String, String>();
        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class).getServiceByShortName(serviceShortName, orgId);
        if (serviceMaster != null) {
            licMaxMap.put("licMaxTenureDays",
                    String.valueOf(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
                            .calculateLicMaxTenureDays(serviceMaster.getTbDepartment().getDpDeptid(),
                                    serviceMaster.getSmServiceId(), null, orgId, licType)));

            LookUp checkListApplLookUp = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(serviceMaster.getSmChklstVerify(), ApplicationContextProvider
                            .getApplicationContext().getBean(IOrganisationService.class).getOrganisationById(orgId));

            if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
                licMaxMap.put("checkListApplFlag", MainetConstants.FlagY);
            } else {
                licMaxMap.put("checkListApplFlag", MainetConstants.FlagN);
            }
            if (StringUtils.equalsIgnoreCase(serviceMaster.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
                licMaxMap.put("applicationchargeApplFlag", MainetConstants.FlagY);
            } else {
                licMaxMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
            }
        }

        return licMaxMap;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = MainetConstants.AdvertisingAndHoarding.SAVE_CANCELLATION_LICENSE_SERVICE, notes = MainetConstants.AdvertisingAndHoarding.SAVE_CANCELLATION_LICENSE_SERVICE, response = Object.class)
    @Path("/saveCancellationService")
    @Transactional
    public AgencyRegistrationResponseDto saveCancellationService(@RequestBody AgencyRegistrationRequestDto requestDto) {
        AgencyRegistrationResponseDto responseDto = new AgencyRegistrationResponseDto();
        // Added new service for User Story 112154
        Long applicationId = setAndSaveApplicationDetails(requestDto);
        // boolean checklist = false;
        if (applicationId != null && applicationId != 0) {
            // This method saves the uploded files
            requestDto.setApplicationId(applicationId);
            requestDto.getMasterDto().setApplicationId(applicationId);
            requestDto.getMasterDto().setApmApplicationId(applicationId);
            responseDto.setApplicationId(applicationId);
            responseDto.setStatus(MainetConstants.WebServiceStatus.SUCCESS);

            uploadService.doFileUpload(requestDto.getDocumentList(), requestDto);

        }

        Long dept = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .getDepartmentIdByDeptCode("ADH");
        if (dept != null) {
            requestDto.setDeptId(dept);
        }

        ServiceMaster serviceMaster = new ServiceMaster();

        serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE,
                        requestDto.getOrgId());

        if (serviceMaster != null && serviceMaster.getSmServiceId() != null) {
            requestDto.setServiceId(serviceMaster.getSmServiceId());
        }

        if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
                MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE)) {
            requestDto.getMasterDto().setCancellationDate(new Date());
            requestDto.getMasterDto().setCancellationReason(requestDto.getMasterDto().getCancellationReason());
            // requestDto.getMasterDto().setAgencyStatus(MainetConstants.FlagA);
            updateAdvertiserMasterData(requestDto.getMasterDto());
        }

        if (requestDto.isFree()) {
            boolean checkList = false;
            if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
                checkList = true;

                iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(
                        requestDto.getMasterDto().getApmApplicationId(), MainetConstants.PAYMENT.FREE,
                        requestDto.getMasterDto().getOrgId());
                ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
                ApplicationMetadata applicationData = new ApplicationMetadata();
                applicationData.setApplicationId(requestDto.getMasterDto().getApmApplicationId());
                applicationData.setOrgId(requestDto.getMasterDto().getOrgId());
                applicationData.setIsCheckListApplicable(checkList);
                requestDto.getApplicantDetailDto().setUserId(requestDto.getUserId());
                requestDto.getApplicantDetailDto().setServiceId(requestDto.getServiceId());
                requestDto.getApplicantDetailDto().setDepartmentId(requestDto.getMasterDto().getDeptId());
                applicantDto.setApplicantFirstName(requestDto.getMasterDto().getAgencyName());
                applicantDto.setServiceId(requestDto.getServiceId());
                applicantDto.setDepartmentId(requestDto.getDeptId());
                applicantDto.setMobileNo(requestDto.getMasterDto().getAgencyContactNo());
                // applicantDto.setUserId(requestDto.getUserId());

                if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
                    applicationData.setIsLoiApplicable(false);
                } else {
                    applicationData.setIsLoiApplicable(true);
                }
                commonService.initiateWorkflowfreeService(applicationData, applicantDto);
            }
        }

        return responseDto;

    }
    // Defect #129856

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
    @Path("/getCalculateYearTypeBylicType/orgId/{orgId}/serviceShortName/{serviceShortName}/licType/{licType}")
    public Map<String, String> getCalculateYearTpe(@PathParam("orgId") Long orgid,
            @PathParam("serviceShortName") String serviceShortName, @PathParam("licType") Long licType) {
        Organisation org = new Organisation();
        org.setOrgid(orgid);
        /*
         * ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext() .getBean(ServiceMasterService.class)
         * .getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgid);
         */
        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class).getServiceByShortName(serviceShortName, orgid);
        List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService.searchLicenseValidityData(
                orgid, serviceMaster.getTbDepartment().getDpDeptid(), serviceMaster.getSmServiceId(),
                MainetConstants.ZERO_LONG, licType);
        LookUp dependsOnLookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(licValMasterDtoList.get(0).getLicDependsOn(), org);
        String YearType = dependsOnLookUp.getLookUpCode();
        Map<String, String> map = new HashMap<>();
        map.put("YearType", YearType);
        return map;
    }

    // Defect #129856

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
    @Path("/getLicMaxTenureDays/orgId/{orgId}/serviceShortName/{serviceShortName}/licType/{licType}")
    public Map<String, String> getLicMaxTenureDays(@PathParam("orgId") Long orgId,
            @PathParam("serviceShortName") String serviceShortName, @PathParam("licType") Long licType) {

        Map<String, String> licMaxMap = new HashMap<>();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        /*
         * ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext() .getBean(ServiceMasterService.class)
         * .getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);
         */
        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class).getServiceByShortName(serviceShortName, orgId);

        licMaxMap.put("licMaxTenureDays",
                String.valueOf(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
                        .calculateLicMaxTenureDays(serviceMaster.getTbDepartment().getDpDeptid(),
                                serviceMaster.getSmServiceId(), null, orgId, licType)));

        return licMaxMap;
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch Advertiser Details By LicNo", notes = "Fetch Advertiser Details By LicNo", response = Object.class)
    @Path("/viewAdvertiserDetails")
    public AgencyRegistrationResponseDto viewAdvertiserDetails(@RequestBody AdvertiserMasterDto masterDto) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " viewAdvertiserDetails method");
        Organisation organisation = new Organisation();
        organisation.setOrgid(masterDto.getOrgId());
        AgencyRegistrationResponseDto agencyRegDto = new AgencyRegistrationResponseDto();
        if ((masterDto.getAgencyLicNo() != null && !masterDto.getAgencyLicNo().isEmpty())
                && (masterDto.getOrgId() != null && masterDto.getOrgId() > 0)) {

            try {
                AdvertiserMasterEntity entity = advertiserMasterRepository.findAgencyByAgencyLicNoAndOrgId(
                        masterDto.getAgencyLicNo(),
                        masterDto.getOrgId());
                AdvertiserMasterDto advertiserMasterDto = null;
                if (entity != null) {
                    advertiserMasterDto = new AdvertiserMasterDto();
                    BeanUtils.copyProperties(entity, advertiserMasterDto);
                    advertiserMasterDto.setAgencyCategoryDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(advertiserMasterDto.getAgencyCategory(), organisation)
                            .getLookUpDesc());
                    Format format = new SimpleDateFormat("dd-MM-yyyy");
                    advertiserMasterDto.setAgencyLicenseFromDate(format.format(advertiserMasterDto.getAgencyLicFromDate()));
                    advertiserMasterDto.setAgencyLicenseToDate(format.format(advertiserMasterDto.getAgencyLicToDate()));
                    advertiserMasterDto.setAgencyRegisDate(format.format(advertiserMasterDto.getAgencyLicIssueDate()));
                    agencyRegDto.setMasterDto(advertiserMasterDto);
                    agencyRegDto.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
                } else {
                    agencyRegDto.setStatus(MainetConstants.WebServiceStatus.FAIL);
                    agencyRegDto.setErrorMsg("No record found");
                }
            } catch (Exception exception) {
                throw new FrameworkException("Error while fetching advertisement details", exception);
            }
        } else {
            agencyRegDto.setErrorMsg("Request DTO must not be null or empty");
            agencyRegDto.setStatus(MainetConstants.WebServiceStatus.FAIL);
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " viewAdvertiserDetails method");
        return agencyRegDto;
    }
    
    @Override
	@Consumes("application/json")
	@ApiOperation(value = "Bill Payment Search Data", notes = "Bill Payment Search Data")
	@POST
	@Path("/searchBillPaymentData/{contractNo}/{orgId}")
	@Transactional
	public ContractAgreementSummaryDTO searchBillPaymentData(@PathParam("contractNo") String contractNo,@PathParam("orgId") Long orgId) {

		ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService
				.findByContractNo(orgId, contractNo);
		if (contractAgreementSummaryDTO != null) {
			Long contractExist = ApplicationContextProvider.getApplicationContext()
					.getBean(IAdvertisementContractMappingService.class).findContractByContIdAndOrgId(contractAgreementSummaryDTO.getContId(),orgId);
			if (contractExist != null && contractExist > 0) {
				List<ADHBillMasEntity> adhBillMasList = ADHBillMasService.finByContractId(contractAgreementSummaryDTO.getContId(),orgId, MainetConstants.FlagN,MainetConstants.FlagB);
				if (CollectionUtils.isNotEmpty(adhBillMasList)) {
					contractAgreementSummaryDTO = ADHBillMasService.getReceiptAmountDetailsForBillPayment(contractAgreementSummaryDTO.getContId(), contractAgreementSummaryDTO,orgId, adhBillMasList);
					contractAgreementSummaryDTO.setBillBalanceAmount(adhBillMasList.get(0).getBalanceAmount());
					 ServiceMaster service = ApplicationContextProvider.getApplicationContext()
				                .getBean(ServiceMasterService.class).getServiceByShortName(MainetConstants.AdvertisingAndHoarding.ACP, orgId);
					 contractAgreementSummaryDTO.setServiceId(service.getSmServiceId());
						contractAgreementSummaryDTO.setDeptId(service.getTbDepartment().getDpDeptid());
					
				} else {
					contractAgreementSummaryDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("adh.validate.no.dues.exist")+ " " + contractNo);
				}
			} else {
				contractAgreementSummaryDTO.setErrorMsg("Contract No: " + contractNo + " is not mapped with any of the hoarding");
			}
		}
		return contractAgreementSummaryDTO;
	}
    
    @Override
	@Consumes("application/json")
	@ApiOperation(value = "Update Bill Payment Data", notes = "Update Bill Payment Data")
	@POST
	@Path("/upadteBillDataFromPortal")
	@Transactional
	public ContractAgreementSummaryDTO upadteBillDataFromPortal(@RequestBody ContractAgreementSummaryDTO requestDto) {
        
		List<ADHBillMasEntity> adhBillMasEnityList = ADHBillMasService.finByContractId(requestDto.getContId(),
				requestDto.getOrgId(), MainetConstants.FlagN, MainetConstants.FlagB);
		if (CollectionUtils.isNotEmpty(adhBillMasEnityList)) {
			if (requestDto.getInputAmount().equals(adhBillMasEnityList.get(0).getBalanceAmount())) {
				ADHBillMasEntity adhBillMas = adhBillMasEnityList.get(0);
				adhBillMas.setPaidAmount(requestDto.getInputAmount());
				adhBillMas.setBalanceAmount(0d);
				// adhBillMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				adhBillMas.setUpdatedDate(new Date());
				// adhBillMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				adhBillMas.setPaidFlag(MainetConstants.Common_Constant.YES);
				adhBillMas.setBillType(MainetConstants.STATUS.INACTIVE);
				ADHBillMasService.updateRLBillMas(adhBillMas);
			} else {

				Double actualPayAmount = requestDto.getInputAmount();
				for (int i = 0; i < adhBillMasEnityList.size(); i++) {
					if (actualPayAmount > 0) {
						ADHBillMasEntity adhBillMas = adhBillMasEnityList.get(i);
						if (actualPayAmount > adhBillMas.getBalanceAmount()
								|| actualPayAmount.equals(adhBillMas.getBalanceAmount())) {
							actualPayAmount = actualPayAmount - adhBillMas.getBalanceAmount();
							adhBillMas.setPaidAmount(adhBillMas.getBalanceAmount());
							adhBillMas.setBalanceAmount(0d);
							// adhBillMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
							adhBillMas.setUpdatedDate(new Date());
							// adhBillMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
							adhBillMas.setPaidFlag(MainetConstants.Common_Constant.YES);
							adhBillMas.setBillType(MainetConstants.STATUS.INACTIVE);
							ADHBillMasService.updateRLBillMas(adhBillMas);
						} else {
							adhBillMas.setPaidAmount(actualPayAmount);
							adhBillMas.setBalanceAmount(adhBillMas.getBalanceAmount() - actualPayAmount);
							actualPayAmount = 0.0;
							// adhBillMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
							adhBillMas.setUpdatedDate(new Date());
							// adhBillMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
							adhBillMas.setPaidFlag(MainetConstants.FlagN);
							ADHBillMasService.updateRLBillMas(adhBillMas);
						}
					} else {
						break;
					}
				}
			}
		}
		return requestDto;
	}
    
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public AdvertiserMasterDto getAgencyRegistrationByAppIdByOrgId(Long applicationId, Long orgId) {

        // AdvertiserMasterDto masterDto = null;

        AdvertiserMasterDto masterDto = new AdvertiserMasterDto();
        AdvertiserMasterEntity advertiserMasterEntity = advertiserMasterRepository.findByApplicationIdOrgId(applicationId, orgId);
           BeanUtils.copyProperties(advertiserMasterEntity, masterDto);  
        return masterDto;
    }


}
