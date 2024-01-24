package com.abm.mainet.adh.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.domain.NewAdvertisementAppDetailsHistory;
import com.abm.mainet.adh.domain.NewAdvertisementApplication;
import com.abm.mainet.adh.domain.NewAdvertisementApplicationDet;
import com.abm.mainet.adh.domain.NewAdvertisementApplicationHistory;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.repository.NewAdvertisementApplicationRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author vishwajeet.kumar
 * @since 25 Sept 2019
 */

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.adh.service.INewAdvertisementApplicationService")
@Api(value = "/newAdvApplicationService")
@Path("/newAdvApplicationService")
@Service
public class NewAdvertisementApplicationServiceImpl implements INewAdvertisementApplicationService {

    private static final Logger LOGGER = Logger.getLogger(NewAdvertisementApplicationServiceImpl.class);

    @Autowired
    private NewAdvertisementApplicationRepository newAdvApplicationRepository;

    @Autowired
    private ILocationMasService locationMasService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private ServiceMasterService masterService;

    @Autowired
    private AuditService auditService;

    @Override
    @POST
    @Consumes("application/json")
    @ApiOperation(value = MainetConstants.AdvertisingAndHoarding.SAVE_NEW_ADVERTISEMENT_APPLICATION, notes = MainetConstants.AdvertisingAndHoarding.SAVE_NEW_ADVERTISEMENT_APPLICATION)
    @Path("/saveNewAdvertiseMentApplication")
    @Transactional
    public NewAdvertisementReqDto saveNewAdvertisementApplication(
            @RequestBody NewAdvertisementReqDto advertisementReqDto) {

        NewAdvertisementReqDto newAdvertisementReqDto = null;
        try {
            Long applicationId = null;
            LOGGER.info("saveNewAdvertisementApplication started");
            NewAdvertisementApplication applicationEntity = mapDtoToEntity(advertisementReqDto);
            ServiceMaster serviceMaster = null;
            if (advertisementReqDto.getNewOrRenewalApp().equals(MainetConstants.AdvertisingAndHoarding.NEW)) {
                serviceMaster = masterService.getServiceMasterByShortCode(
                        MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, advertisementReqDto.getOrgId());
            } else if (advertisementReqDto.getNewOrRenewalApp().equals(MainetConstants.AdvertisingAndHoarding.RENEWAL)) {
                serviceMaster = masterService.getServiceMasterByShortCode(
                        MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE, advertisementReqDto.getOrgId());
            }

            LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
                    advertisementReqDto.getAdvertisementDto().getLocId(),
                    serviceMaster.getTbDepartment().getDpDeptid());

            RequestDTO requestDto = setApplicantRequestData(advertisementReqDto, serviceMaster);

            if (applicationEntity.getApmApplicationId() == null) {
                applicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
                        .createApplication(requestDto);
                LOGGER.info("application number for new trade licence : " + applicationId);
                applicationEntity.setApmApplicationId(applicationId);
                if(wzMapping != null) {
	                if (wzMapping.getCodIdOperLevel1() != null) {
	                    applicationEntity.setAdhZone1(wzMapping.getCodIdOperLevel1());
	                }
	                if (wzMapping.getCodIdOperLevel2() != null) {
	                    applicationEntity.setAdhZone2(wzMapping.getCodIdOperLevel2());
	                }
	                if (wzMapping.getCodIdOperLevel3() != null) {
	                    applicationEntity.setAdhZone3(wzMapping.getCodIdOperLevel3());
	                }
	                if (wzMapping.getCodIdOperLevel4() != null) {
	                    applicationEntity.setAdhZone4(wzMapping.getCodIdOperLevel4());
	                }
	                if (wzMapping.getCodIdOperLevel4() != null) {
	                    applicationEntity.setAdhZone4(wzMapping.getCodIdOperLevel4());
	                }
	                if (wzMapping.getCodIdOperLevel5() != null) {
	                    applicationEntity.setAdhZone5(wzMapping.getCodIdOperLevel5());
	                }
                }
                applicationEntity.setAdhStatus(MainetConstants.FlagA);
                advertisementReqDto.setApplicationId(applicationId);
            }

            NewAdvertisementApplication advertisementApplication = newAdvApplicationRepository.save(applicationEntity);

            if ((advertisementReqDto.getDocumentList() != null) && !advertisementReqDto.getDocumentList().isEmpty()) {
                requestDto.setApplicationId(applicationId);
                ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doFileUpload(
                        advertisementReqDto.getDocumentList(),
                        requestDto);

            }
            createHistoryDetails(advertisementApplication, advertisementReqDto);

            newAdvertisementReqDto = mapEntityToDto(advertisementApplication);
            if (advertisementApplication != null) {
                newAdvertisementReqDto.getAdvertisementDto().setApmApplicationId(applicationId);
                newAdvertisementReqDto.setApplicationId(applicationId);
                newAdvertisementReqDto.setServiceId(serviceMaster.getSmServiceId());
                newAdvertisementReqDto.setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
                newAdvertisementReqDto.setServiceName(serviceMaster.getSmServiceName());
            }
            if (advertisementReqDto.isFree()) {
                initiateWorkFlowIsFreeService(advertisementReqDto, serviceMaster);
                newAdvertisementReqDto.setFree(true);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving New Advertisement Application ", exception);
            throw new FrameworkException("Exception occur while saving New Advertisement Application ", exception);
        }
        return newAdvertisementReqDto;
    }

    /*
     * Create New Advertisement Application History Details
     */
    private void createHistoryDetails(NewAdvertisementApplication advertisementApplication,
            NewAdvertisementReqDto advertisementReqDto) {
        NewAdvertisementApplicationHistory history = new NewAdvertisementApplicationHistory();
        List<Object> advHistoryList = new ArrayList<>();
        if (advertisementReqDto.getNewOrRenewalApp().equals(MainetConstants.AdvertisingAndHoarding.NEW)) {
            history.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            advertisementApplication.getNewAdvertisetDetails().forEach(masDet -> {
                NewAdvertisementAppDetailsHistory detailsHistory = new NewAdvertisementAppDetailsHistory();
                BeanUtils.copyProperties(masDet, detailsHistory);
                detailsHistory.setAdhId(masDet.getNewAdvertisement().getAdhId());
                detailsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                advHistoryList.add(detailsHistory);
            });

        } else if (advertisementReqDto.getNewOrRenewalApp().equals(MainetConstants.AdvertisingAndHoarding.RENEWAL)) {
            history.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            advertisementApplication.getNewAdvertisetDetails().forEach(masDet -> {
                NewAdvertisementAppDetailsHistory detailsHistory = new NewAdvertisementAppDetailsHistory();
                BeanUtils.copyProperties(masDet, detailsHistory);
                detailsHistory.setAdhId(masDet.getNewAdvertisement().getAdhId());
                detailsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                advHistoryList.add(detailsHistory);
            });
        }
        try {
            auditService.createHistory(advertisementApplication, history);
            auditService.createHistoryForListObj(advHistoryList);
        } catch (Exception exception) {
            LOGGER.error("Exception occured when calling audit service  ", exception);
        }
    }

    private void initiateWorkFlowIsFreeService(NewAdvertisementReqDto advertisementReqDto,
            ServiceMaster serviceMaster) {

        boolean checklist = false;

        if ((advertisementReqDto.getDocumentList() != null) && !advertisementReqDto.getDocumentList().isEmpty()) {
            checklist = true;
        }
        final ApplicantDetailDTO applicantDetailsDto = advertisementReqDto.getApplicantDetailDTO();
        ApplicationMetadata applicationData = new ApplicationMetadata();
        applicationData.setApplicationId(advertisementReqDto.getApplicationId());
        applicationData.setIsCheckListApplicable(checklist);
        applicationData.setOrgId(advertisementReqDto.getOrgId());
        if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
            applicationData.setIsLoiApplicable(false);
        } else {
            applicationData.setIsLoiApplicable(true);
        }
        applicantDetailsDto.setServiceId(serviceMaster.getSmServiceId());
        applicantDetailsDto.setDepartmentId(serviceMaster.getTbDepartment().getDpDeptid());
        applicantDetailsDto.setUserId(advertisementReqDto.getAdvertisementDto().getCreatedBy());
        ApplicationContextProvider.getApplicationContext().getBean(CommonService.class)
                .initiateWorkflowfreeService(applicationData, applicantDetailsDto);
        // commonService.initiateWorkflowfreeService(applicationData, applicantDetailsDto);
    }

    /**
     * This method is used for Convert DTO to ENTITY
     * 
     * @param advertisementReqDto
     * @return
     */
    private NewAdvertisementApplication mapDtoToEntity(NewAdvertisementReqDto advertisementReqDto) {
        NewAdvertisementApplication advertisementApplication = new NewAdvertisementApplication();
        List<NewAdvertisementApplicationDet> applicationDets = new ArrayList<NewAdvertisementApplicationDet>();
        BeanUtils.copyProperties(advertisementReqDto.getAdvertisementDto(), advertisementApplication);
        if (advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos() != null) {
            advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(advAppdetDto -> {
                NewAdvertisementApplicationDet advAppdetEntity = new NewAdvertisementApplicationDet();
                BeanUtils.copyProperties(advAppdetDto, advAppdetEntity);
                advAppdetEntity.setNewAdvertisement(advertisementApplication);
                applicationDets.add(advAppdetEntity);
            });
        }
        advertisementApplication.setNewAdvertisetDetails(applicationDets);
        return advertisementApplication;
    }

    /**
     * This method is used for Convert entity to Dto
     * 
     * @param advertisementApplication
     * @return
     */
    private NewAdvertisementReqDto mapEntityToDto(NewAdvertisementApplication advertisementApplication) {
        NewAdvertisementReqDto advertisementReqDto = new NewAdvertisementReqDto();
        BeanUtils.copyProperties(advertisementApplication, advertisementReqDto.getAdvertisementDto());
        advertisementReqDto.getAdvertisementDto().setLicenseFromDateDesc(
                new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(advertisementApplication.getLicenseFromDate()));
        advertisementReqDto.getAdvertisementDto().setLicenseToDateDesc(
                new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(advertisementApplication.getLicenseToDate()));
        List<NewAdvertisementApplicationDetDto> detDtosList = new ArrayList<NewAdvertisementApplicationDetDto>();
        if (advertisementApplication.getNewAdvertisetDetails() != null) {
            advertisementApplication.getNewAdvertisetDetails().forEach(advAppdetEntity -> {
                NewAdvertisementApplicationDetDto applicationDetDto = new NewAdvertisementApplicationDetDto();
                BeanUtils.copyProperties(advAppdetEntity, applicationDetDto);
                detDtosList.add(applicationDetDto);
            });
            advertisementReqDto.getAdvertisementDto().setNewAdvertDetDtos(detDtosList);
        }
        return advertisementReqDto;
    }

    /**
     * This method is used for set Application related Data( Generate Application Number)
     * 
     * @param advertisementReqDto
     * @param serviceMaster
     * @return
     */
    private RequestDTO setApplicantRequestData(NewAdvertisementReqDto advertisementReqDto,
            ServiceMaster serviceMaster) {

        RequestDTO requestDto = new RequestDTO();
        requestDto.setTitleId(advertisementReqDto.getApplicantDetailDTO().getApplicantTitle());
        requestDto.setfName(advertisementReqDto.getApplicantDetailDTO().getApplicantFirstName());
        requestDto.setmName(advertisementReqDto.getApplicantDetailDTO().getApplicantMiddleName());
        requestDto.setlName(advertisementReqDto.getApplicantDetailDTO().getApplicantLastName());
        requestDto.setGender(advertisementReqDto.getApplicantDetailDTO().getGender());
        requestDto.setMobileNo(advertisementReqDto.getApplicantDetailDTO().getMobileNo());
        requestDto.setEmail(advertisementReqDto.getApplicantDetailDTO().getEmailId());
        requestDto.setFlatBuildingNo(advertisementReqDto.getApplicantDetailDTO().getFlatBuildingNo());
        requestDto.setBldgName(advertisementReqDto.getApplicantDetailDTO().getBuildingName());
        requestDto.setRoadName(advertisementReqDto.getApplicantDetailDTO().getRoadName());
        requestDto.setBlockName(advertisementReqDto.getApplicantDetailDTO().getBlockName());
        requestDto.setAreaName(advertisementReqDto.getApplicantDetailDTO().getAreaName());
        requestDto.setCityName(advertisementReqDto.getApplicantDetailDTO().getVillageTownSub());
        requestDto.setPincodeNo(Long.valueOf(advertisementReqDto.getApplicantDetailDTO().getPinCode()));
        if ((StringUtils.isNotEmpty(advertisementReqDto.getApplicantDetailDTO().getAadharNo()))
                && (advertisementReqDto.getApplicantDetailDTO().getAadharNo().length() > 0)) {
            requestDto.setUid(Long.valueOf(advertisementReqDto.getApplicantDetailDTO().getAadharNo()
                    .replaceAll(MainetConstants.AdvertisingAndHoarding.REPLACE_NO, MainetConstants.BLANK)));
        }

        requestDto.setServiceId(serviceMaster.getSmServiceId());
        requestDto.setUserId(advertisementReqDto.getAdvertisementDto().getCreatedBy());
        requestDto.setOrgId(advertisementReqDto.getOrgId());
        requestDto.setLangId((long) advertisementReqDto.getLangId());
        requestDto.setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
        requestDto.setFloor(advertisementReqDto.getApplicantDetailDTO().getFloorNo());
        requestDto.setWing(advertisementReqDto.getApplicantDetailDTO().getWing());
        requestDto.setHouseComplexName(advertisementReqDto.getApplicantDetailDTO().getHousingComplexName());
        requestDto.setBplNo(advertisementReqDto.getApplicantDetailDTO().getBplNo());
        if (advertisementReqDto.isFree()) {
            requestDto.setPayStatus(MainetConstants.FlagF);
        }
        // requestDto.setPayAmount(1D);
        return requestDto;
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = MainetConstants.AdvertisingAndHoarding.GET_ALL_ADVERTISEMENT_APPLICATION_DATA_BY_APPLCATIONID, notes = MainetConstants.AdvertisingAndHoarding.GET_ALL_ADVERTISEMENT_APPLICATION_DATA_BY_APPLCATIONID)
    @Path("/getAdvApplicationById/applicationId/{applicationId}/orgId/{orgId}")
    @Override
    @Transactional(readOnly = true)
    public NewAdvertisementReqDto getNewAdvertisementApplicationByAppId(
            @PathParam(MainetConstants.AdvertisingAndHoarding.APPLICATION_ID) long applicationId,
            @PathParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
        NewAdvertisementReqDto advertisementReqDto = new NewAdvertisementReqDto();
        NewAdvertisementApplication applicationEntity = newAdvApplicationRepository
                .findAdvertisementDetailsByAppId(applicationId, orgId);
        if (applicationEntity != null) {
            advertisementReqDto = mapEntityToDto(applicationEntity);
        }
        ApplicantDetailDTO applicant = setApplicantRelatedInformation(applicationId, orgId);
        advertisementReqDto.setApplicantDetailDTO(applicant);
        advertisementReqDto.setApplicantName(applicant.getApplicantFirstName() + "  " + applicant.getApplicantLastName());
        return advertisementReqDto;
    }

    private ApplicantDetailDTO setApplicantRelatedInformation(Long apmApplicationId, Long orgId) {
        TbCfcApplicationMstEntity cfcApplicationMstEntity = new TbCfcApplicationMstEntity();
        CFCApplicationAddressEntity addressEntity = new CFCApplicationAddressEntity();
        cfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(apmApplicationId, orgId);
        addressEntity = cfcService.getApplicantsDetails(apmApplicationId);

        ApplicantDetailDTO applicant = new ApplicantDetailDTO();
        applicant.setApplicantFirstName(cfcApplicationMstEntity.getApmFname());
        applicant.setApplicantMiddleName(cfcApplicationMstEntity.getApmMname());
        applicant.setApplicantLastName(cfcApplicationMstEntity.getApmLname());
        applicant.setApplicantTitle(cfcApplicationMstEntity.getApmTitle());
        applicant.setGender(cfcApplicationMstEntity.getApmSex());
        setGender(applicant, orgId);
        applicant.setMobileNo(addressEntity.getApaMobilno());
        applicant.setEmailId(addressEntity.getApaEmail());
        applicant.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo());
        applicant.setBuildingName(addressEntity.getApaBldgnm());
        applicant.setAreaName(addressEntity.getApaAreanm());
        applicant.setRoadName(addressEntity.getApaRoadnm());
        applicant.setVillageTownSub(addressEntity.getApaCityName());
        applicant.setPinCode(addressEntity.getApaPincode().toString());
        applicant.setBlockName(addressEntity.getApaBlockName());
        if (cfcApplicationMstEntity.getApmUID() != null)
            applicant.setAadharNo(cfcApplicationMstEntity.getApmUID().toString());
        applicant.setBplNo(cfcApplicationMstEntity.getApmBplNo());
        if (applicant.getBplNo() != null && !applicant.getBplNo().isEmpty()) {
            applicant.setIsBPL(MainetConstants.FlagY);
        } else {
            applicant.setIsBPL(MainetConstants.FlagN);
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
    public String generateNewAdvertisementLicenseNumber(Organisation org, Long deptId) {

        // get financial by date
        FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());

        // get financial year formate
        String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

        // gerenerate sequence
        final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
                        MainetConstants.AdvertisingAndHoarding.TB_ADH_MAS,
                        MainetConstants.AdvertisingAndHoarding.ADH_LICNO, org.getOrgid(), MainetConstants.FlagC,
                        financiaYear.getFaYear());
        String licenseNo = org.getOrgShortNm() + MainetConstants.WINDOWS_SLASH
                + MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE + MainetConstants.WINDOWS_SLASH + finacialYear
                + MainetConstants.WINDOWS_SLASH
                + String.format(MainetConstants.AdvertisingAndHoarding.NO_FORMAT, sequence);

        return licenseNo;
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Fetch Location By Department Id And Orgid", notes = "Fetch Location By Department Id And Orgid")
    @Path("/getLocationLookupByOrgId/orgId/{orgId}")
    @Override
    @Transactional(readOnly = true)
    public List<LookUp> getLocationByDeptIdAndOrgId(@PathParam("orgId") Long orgId) {
        List<LookUp> listLookUp = new ArrayList<LookUp>();
        try {
            ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
                    .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);
            // servicesMstService.findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);
            List<TbLocationMas> locationMasList = locationMasService
                    .getlocationByDeptId(master.getTbDepartment().getDpDeptid(), orgId);
            if (locationMasList != null) {
                locationMasList.forEach(loc -> {
                    LookUp lookUp = new LookUp();
                    lookUp.setLookUpId(loc.getLocId());
                    lookUp.setDescLangFirst(loc.getLocNameEng());
                    lookUp.setDescLangSecond(loc.getLocNameReg());
                    listLookUp.add(lookUp);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while fetching location from Location Master", exception);
        }
        return listLookUp;
    }

    @Override
    public Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId)
            throws CloneNotSupportedException {
        Map<Long, Double> chargeMap = new HashMap<>();

        NewAdvertisementReqDto reqDto = getNewAdvertisementApplicationByAppId(applicationId, orgId);
        WSRequestDTO requestDTO = new WSRequestDTO();
        List<ADHRateMaster> requiredCharges = new ArrayList<ADHRateMaster>();
        List<ADHRateMaster> listOFLoiTaxes = ApplicationContextProvider.getApplicationContext().getBean(IBRMSADHService.class)
                .getLoiChargesForADH(requestDTO, orgId,
                        MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        if (reqDto.getAdvertisementDto().getNewAdvertDetDtos() != null) {
            for (NewAdvertisementApplicationDetDto applicationDetDto : reqDto.getAdvertisementDto()
                    .getNewAdvertDetDtos()) {
                for (ADHRateMaster Loitaxes : listOFLoiTaxes) {
                    ADHRateMaster master = new ADHRateMaster();
                    if (!Loitaxes.getDependsOnFactorList().isEmpty()) {
                        for (String dependFactor : Loitaxes.getDependsOnFactorList()) {
                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.AREA)) {
                                master.setArea(applicationDetDto.getAdvDetailsArea().doubleValue());
                            }
                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.UNIT)) {
                                master.setUnit(applicationDetDto.getUnit());
                            }

                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.MODE_OF_ADV)) {
                                master.setAdvertiserCategory(CommonMasterUtility.getCPDDescription(
                                        reqDto.getAdvertisementDto().getAppCategoryId(), MainetConstants.FlagE));
                            }

                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.LICENSE_TYPE)) {
                                if (reqDto.getAdvertisementDto().getLicenseType() != null
                                        && reqDto.getAdvertisementDto().getLicenseType() > 0) {
                                    master.setLicenseType(CommonMasterUtility
                                            .getNonHierarchicalLookUpObject(
                                                    reqDto.getAdvertisementDto().getLicenseType(), org)
                                            .getDescLangFirst());
                                }
                            }
                            master.setLicenseType(CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(
                                            reqDto.getAdvertisementDto().getLicenseType(), org)
                                    .getDescLangFirst());
                            if (StringUtils.equalsIgnoreCase(dependFactor,
                                    MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE) || (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))) {

                                List<LookUp> listLookup = CommonMasterUtility
                                        .getListLookup(MainetConstants.AdvertisingAndHoarding.ADVERTISEMENT, org);

                                if (applicationDetDto.getAdhTypeId1() != null) {
                                    master.setUsageSubtype1(CommonMasterUtility
                                            .getHierarchicalLookUp(applicationDetDto.getAdhTypeId1(), org)
                                            .getDescLangFirst());
                                }

                                if (applicationDetDto.getAdhTypeId2() != null) {
                                    master.setUsageSubtype2(CommonMasterUtility
                                            .getHierarchicalLookUp(applicationDetDto.getAdhTypeId2(), org)
                                            .getDescLangFirst());
                                }
                                if (applicationDetDto.getAdhTypeId3() != null) {
                                    master.setUsageSubtype3(CommonMasterUtility
                                            .getHierarchicalLookUp(applicationDetDto.getAdhTypeId3(), org)
                                            .getDescLangFirst());
                                }
                                if (applicationDetDto.getAdhTypeId4() != null) {
                                    master.setUsageSubtype4(CommonMasterUtility
                                            .getHierarchicalLookUp(applicationDetDto.getAdhTypeId4(), org)
                                            .getDescLangFirst());
                                }
                                if (applicationDetDto.getAdhTypeId5() != null) {
                                    master.setUsageSubtype5(CommonMasterUtility
                                            .getHierarchicalLookUp(applicationDetDto.getAdhTypeId5(), org)
                                            .getDescLangFirst());
                                }
                            }
                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.LOCATION)) {
                                List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
                                List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
                                List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();

                                try {
                                    lookupListLevel1 = CommonMasterUtility.getNextLevelData(
                                            MainetConstants.AdvertisingAndHoarding.ADV_ZONE_WARD, 1,
                                            applicationDetDto.getOrgId());
                                    lookupListLevel2 = CommonMasterUtility.getNextLevelData(
                                            MainetConstants.AdvertisingAndHoarding.ADV_ZONE_WARD, 2,
                                            applicationDetDto.getOrgId());
                                    lookupListLevel3 = CommonMasterUtility.getNextLevelData(
                                            MainetConstants.AdvertisingAndHoarding.ADV_ZONE_WARD, 3,
                                            applicationDetDto.getOrgId());
                                } catch (Exception e) {
                                    LOGGER.info("prefix level not found");
                                }

                                if (reqDto.getAdvertisementDto().getAdhZone1() != null
                                        && reqDto.getAdvertisementDto().getAdhZone1() != 0) {
                                    List<LookUp> level1 = lookupListLevel1.parallelStream()
                                            .filter(clList -> clList != null && clList.getLookUpId() == reqDto
                                                    .getAdvertisementDto().getAdhZone1().longValue())
                                            .collect(Collectors.toList());
                                    if (level1 != null && !level1.isEmpty()) {
                                        master.setZone(level1.get(0).getDescLangFirst());
                                    }
                                } else {
                                    master.setWard(MainetConstants.AdvertisingAndHoarding.NOT_APPLICABLE);
                                }
                                if (reqDto.getAdvertisementDto().getAdhZone2() != null
                                        && reqDto.getAdvertisementDto().getAdhZone2() != 0) {
                                    List<LookUp> level2 = lookupListLevel2.parallelStream()
                                            .filter(clList -> clList != null && clList.getLookUpId() == reqDto
                                                    .getAdvertisementDto().getAdhZone2().longValue())
                                            .collect(Collectors.toList());
                                    if (level2 != null && !level2.isEmpty()) {
                                        master.setWard(level2.get(0).getDescLangFirst());

                                    }
                                } else {
                                    master.setZone(MainetConstants.AdvertisingAndHoarding.NOT_APPLICABLE);
                                }
                            }
                        }
                    }
                    if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                    	if (reqDto.getAdvertisementDto().getUlbOwned().contentEquals(MainetConstants.FlagY)) {
                            master.setLocationCategory(MainetConstants.YESL);
                        } else {
                            master.setLocationCategory(MainetConstants.NOL);
                        }
                    } else {
                        if (reqDto.getAdvertisementDto().getLocCatType().contentEquals(MainetConstants.FlagE)) {
                            master.setLocationCategory(MainetConstants.AdvertisingAndHoarding.EXISTING_LOCATION);
                        } else {
                            master.setLocationCategory(MainetConstants.AdvertisingAndHoarding.NEW_LOCATION);
                        }
                    }
                    master.setChargeApplicableAt(Loitaxes.getChargeApplicableAt());
                    master.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                    master.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                    master.setTaxCategory(Loitaxes.getTaxCategory());
                    master.setTaxSubCategory(Loitaxes.getTaxSubCategory());
                    master.setTaxType(Loitaxes.getTaxType());
                    master.setTaxCode(Loitaxes.getTaxCode());
                    master.setTaxId(Loitaxes.getTaxId());
                    master.setOrgId(reqDto.getAdvertisementDto().getOrgId());
                    master.setRateStartDate(new Date().getTime());
                    requiredCharges.add(master);
                }
            }
        }

        LOGGER.info("brms ADH Rate Master execution start..");
        WSResponseDTO responseDTO = new WSResponseDTO();
        WSRequestDTO wsRequestDTO = new WSRequestDTO();
        List<ADHRateMaster> master = new ArrayList<>();
        wsRequestDTO.setDataModel(requiredCharges);

        try {
            LOGGER.info("brms New Adh Rate Master request DTO  is :" + wsRequestDTO.toString());
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.ADH_SERVICE_CHARGE_URI);
            if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                master = setADHChargesDTO(responseDTO);
                Double baseRate = 0d;
                double amount;
                for (ADHRateMaster loiCharges : master) {
                    LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
                            PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);

                    amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
                    amount += baseRate;
                    chargeMap.put(loiCharges.getTaxId(), loiCharges.getFlatRate());
                }
            } else {
                throw new FrameworkException(responseDTO.getErrorMessage());
            }
        } catch (Exception ex) {
            throw new FrameworkException("unable to process request for ADH License Fee", ex);
        }
        LOGGER.info("brms ADH Rate Master execution End.");

        return chargeMap;
    }

    private List<ADHRateMaster> setADHChargesDTO(WSResponseDTO responseDTO) {
        LOGGER.info("set Adh Rate Master Charges DTO execution start..");
        final List<?> charges = RestClient.castResponse(responseDTO, ADHRateMaster.class);
        List<ADHRateMaster> finalRateMaster = new ArrayList<>();
        for (Object rate : charges) {
            ADHRateMaster masterRate = (ADHRateMaster) rate;
            finalRateMaster.add(masterRate);
        }
        LOGGER.info("set Adh Rate Master Charges DTO execution end..");
        return finalRateMaster;
    }

    @Override
    @Transactional
    public void updateAdvertisementLicenseNo(Long applicationId, String advertisementLicenseNo, Long orgId) {

        newAdvApplicationRepository.updateAdvertisementLiceNo(applicationId, advertisementLicenseNo, orgId);

    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Fetch License No By OrgId", notes = "Fetch License No By OrgId")
    @Path("/getLicenseNoByOrgId/orgId/{orgId}")
    @Override
    @Transactional(readOnly = true)
    public List<NewAdvertisementApplicationDto> getLicenseNoByOrgId(@PathParam("orgId") Long orgId) {
        List<Object[]> advertisementApplications = newAdvApplicationRepository.getLicenseNoByOrgId(orgId);
        List<NewAdvertisementApplicationDto> applicationDtosList = new ArrayList<NewAdvertisementApplicationDto>();
        for (Object[] AdvLicenseDate : advertisementApplications) {
            NewAdvertisementApplicationDto applicationDto = new NewAdvertisementApplicationDto();
            applicationDto.setLicenseNo(AdvLicenseDate[0].toString());
            applicationDto.setApplicantName(AdvLicenseDate[1].toString() + " " + AdvLicenseDate[2].toString());
            applicationDto.setMobileNo(AdvLicenseDate[3].toString());
            applicationDtosList.add(applicationDto);
        }
        return applicationDtosList;
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Get All Advertisement Application Data By License No", notes = "Get All Advertisement Application Data By License No")
    @Path("/getAdvApplicationBylicenseNo/licenseNo/{licenseNo}/orgId/{orgId}")
    @Override
    @Transactional(readOnly = true)
    public NewAdvertisementReqDto getAdvertisementApplicationByLicenseNo(@PathParam("licenseNo") String licenseNo,
            @PathParam("orgId") Long orgId) {

        NewAdvertisementReqDto advertisementReqDto = null;
        NewAdvertisementApplication applicationEntity = newAdvApplicationRepository
                .findAdvertisementDetailsByLicNoAndOrgId(licenseNo, orgId);
        if (applicationEntity != null) {
            advertisementReqDto = mapEntityToDto(applicationEntity);
        }
        ApplicantDetailDTO applicant = setApplicantRelatedInformation(
                advertisementReqDto.getAdvertisementDto().getApmApplicationId(), orgId);
        advertisementReqDto.setApplicantDetailDTO(applicant);
        advertisementReqDto.setApplicantName(applicant.getApplicantFirstName() + "  " + applicant.getApplicantLastName());
        return advertisementReqDto;
    }

}
