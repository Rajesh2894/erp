/**
 * 
 */
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.domain.HoardingBookingEntity;
import com.abm.mainet.adh.domain.HoardingMasterEntity;
import com.abm.mainet.adh.domain.NewAdvertisementApplication;
import com.abm.mainet.adh.domain.NewAdvertisementApplicationDet;
import com.abm.mainet.adh.domain.NewAdvertisementApplicationHistory;
import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.repository.HoardingBookingRepository;
import com.abm.mainet.adh.repository.HoardingMasterRepository;
import com.abm.mainet.adh.repository.NewAdvertisementApplicationRepository;
import com.abm.mainet.adh.repository.NewHoardingApplicationRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
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
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author anwarul.hassan
 * @since 17-Oct-2019
 */

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.adh.service.INewHoardingApplicationService")
@Api(value = "/newHoardingAppService")
@Path("/newHoardingAppService")
@Service
public class NewHoardingApplicationServiceImpl implements INewHoardingApplicationService {

    private static final Logger LOGGER = Logger.getLogger(NewHoardingApplicationServiceImpl.class);

    @Autowired
    private NewHoardingApplicationRepository newHoardingApplicationRepository;

    @Autowired
    private ServiceMasterService masterService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ILocationMasService locationMasService;

    @Autowired
    private HoardingBookingRepository hoardingBookingRepository;

    @Autowired
    private HoardingMasterRepository hoardingMasterRepository;

    /*
     * @Autowired private HoardingMasterEntity hoardingMasterRepository;
     */

    @Autowired
    private NewAdvertisementApplicationRepository newAdvApplicationRepository;

    @Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;

    @Autowired
    private IHoardingMasterService hoardingMasterService;

    @Autowired
    private TbServicesMstService servicesMstService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.INewHoardingApplicationService#getHoardingNumberListByOrgId(java.lang.Long)
     */
    @Override
    @Transactional
    public List<String> getHoardingNumberListByOrgId(@PathParam("orgId") Long orgId) {
        return newHoardingApplicationRepository.findHoardingNumberByOrgId(orgId);
    }

    @Override
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "get hoarding number list", notes = MainetConstants.AdvertisingAndHoarding.SAVE_HOARDING_APPLICATION)
    @Path("/getHoardingNumberAndIdListByOrgId/orgId/{orgId}")
    @Transactional
    public List<Object[]> getHoardingNumberAndIdListByOrgId(Long orgId) {
        List<Object[]> object = new ArrayList<>();
        List<String[]> test = hoardingMasterRepository.findHoardingNumberAndIdByOrgId(orgId);
        // list of string[] is not pass in json
        for (Object[] obj : test) {
            object.add(obj);
        }
        return object;
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Get All hoarding details   Data By hoarding id and orgId", notes = "Get All hoarding details   Data By hoarding id and orgId")
    @Path("/getByOrgIdAndHoardingId/orgId/{orgId}/hoardingId/{hoardingId}")
    @Override
    @Transactional(readOnly = true)
    public HoardingMasterDto getByOrgIdAndHoardingId(@PathParam("orgId") Long orgId, @PathParam("hoardingId") Long hoardingId) {
        HoardingMasterDto masterDto = null;
        try {
            HoardingMasterEntity entity = hoardingMasterRepository.findByOrgIdAndHoardingId(orgId, hoardingId);
            if (entity != null) {
                masterDto = new HoardingMasterDto();
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Master by hoardingId", exception);
        }
        return masterDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.INewHoardingApplicationService#getHoardingDetailsByHoardingNumberAndOrgId(java.lang.String,
     * java.lang.Long)
     */
    @Override
    @Transactional
    public HoardingMasterDto getHoardingDetailsByHoardingNumberAndOrgId(String hoardingNumber, Long orgId) {
        HoardingMasterEntity hoardingMasterEntity = newHoardingApplicationRepository
                .findHoardingDetailsByHoardingNumberAndOrgId(hoardingNumber, orgId);
        HoardingMasterDto masterDto = null;
        if (hoardingMasterEntity != null) {
            masterDto = new HoardingMasterDto();
            BeanUtils.copyProperties(hoardingMasterEntity, masterDto);
        }
        return masterDto;
    }

    @Override
    @POST
    @Consumes("application/json")
    @ApiOperation(value = MainetConstants.AdvertisingAndHoarding.SAVE_HOARDING_APPLICATION, notes = MainetConstants.AdvertisingAndHoarding.SAVE_HOARDING_APPLICATION)
    @Path("/saveNewHoardingApplication")
    @Transactional
    public NewAdvertisementReqDto saveNewHoardingApplication(
            @RequestBody NewAdvertisementReqDto advertisementReqDto) {

        NewAdvertisementReqDto newAdvertisementReqDto = null;
        try {
            Long applicationId = null;
            LOGGER.info("saveNewAdvertisementApplication started");
            NewAdvertisementApplication applicationEntity = mapDtoToEntity(advertisementReqDto);
            ServiceMaster serviceMaster = null;

            serviceMaster = masterService.getServiceMasterByShortCode(
                    MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE, advertisementReqDto.getOrgId());

            LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
                    advertisementReqDto.getAdvertisementDto().getLocId(),
                    serviceMaster.getTbDepartment().getDpDeptid());

            RequestDTO requestDto = setApplicantRequestData(advertisementReqDto, serviceMaster);

            if (applicationEntity.getApmApplicationId() == null) {
                applicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
                        .createApplication(requestDto);
                LOGGER.info("application number for new trade licence : " + applicationId);
                applicationEntity.setApmApplicationId(applicationId);
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
                applicationEntity.setAdhStatus(MainetConstants.FlagA);
                advertisementReqDto.setApplicationId(applicationId);
            }

            NewAdvertisementApplication advertisementApplication = newAdvApplicationRepository.save(applicationEntity);

            advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(det -> {
                HoardingBookingEntity hoardingEntity = new HoardingBookingEntity();
                // hoardingEntity.getHoardingMasterEntity().setHoardingId(det.getHoardingId());
                HoardingMasterEntity hoardingMasterEntity = new HoardingMasterEntity();
                hoardingMasterEntity.setHoardingId(det.getHoardingId());
                hoardingEntity.setHoardingMasterEntity(hoardingMasterEntity);
                /*
                 * NewAdvertisementApplication newAdvertApplication = new NewAdvertisementApplication();
                 * newAdvertApplication.setAdhId(det.getAdhId());
                 */
                hoardingEntity.setNewAdvertisement(advertisementApplication);
                hoardingEntity.setCreatedBy(det.getCreatedBy());
                hoardingEntity.setCreatedDate(new Date());
                hoardingEntity.setOrgId(det.getOrgId());
                hoardingEntity.setLgIpMac(det.getLgIpMac());
                hoardingBookingRepository.save(hoardingEntity);
            });

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
            LOGGER.error("Exception occur while saving  hoarding Application ", exception);
            throw new FrameworkException("Exception occur while saving New hoarding Application ", exception);
        }
        return newAdvertisementReqDto;

    }

    private NewAdvertisementApplication mapDtoToEntity(NewAdvertisementReqDto advertisementReqDto) {
        NewAdvertisementApplication advertisementApplication = new NewAdvertisementApplication();
        List<NewAdvertisementApplicationDet> applicationDets = new ArrayList<NewAdvertisementApplicationDet>();
        BeanUtils.copyProperties(advertisementReqDto.getAdvertisementDto(), advertisementApplication);
        /*
         * if (advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos() != null) {
         * advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(advAppdetDto -> {
         * NewAdvertisementApplicationDet advAppdetEntity = new NewAdvertisementApplicationDet();
         * BeanUtils.copyProperties(advAppdetDto, advAppdetEntity); advAppdetEntity.setNewAdvertisement(advertisementApplication);
         * applicationDets.add(advAppdetEntity); }); }
         */
        advertisementApplication.setNewAdvertisetDetails(applicationDets);
        return advertisementApplication;
    }

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

    private void createHistoryDetails(NewAdvertisementApplication advertisementApplication,
            NewAdvertisementReqDto advertisementReqDto) {
        NewAdvertisementApplicationHistory history = new NewAdvertisementApplicationHistory();
        history.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        history.setAdhStatus(MainetConstants.FlagA);
        try {
            auditService.createHistory(advertisementApplication, history);
            // auditService.createHistoryForListObj(advHistoryList);
        } catch (Exception exception) {
            LOGGER.error("Exception occured when calling audit service  ", exception);
        }
    }

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

    @Override
    public Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId)
            throws CloneNotSupportedException {
        Map<Long, Double> chargeMap = new HashMap<>();

        NewAdvertisementReqDto reqDto = newAdvApplicationService.getNewAdvertisementApplicationByAppId(applicationId, orgId);
        List<HoardingBookingEntity> hoardingBookingEntityList = ApplicationContextProvider.getApplicationContext()
                .getBean(HoardingBookingRepository.class)
                .findHoardingDetailsByAdhIdAndOrgId(reqDto.getAdvertisementDto().getAdhId(), orgId);
        WSRequestDTO requestDTO = new WSRequestDTO();
        List<ADHRateMaster> requiredCharges = new ArrayList<ADHRateMaster>();
        List<ADHRateMaster> listOFLoiTaxes = ApplicationContextProvider.getApplicationContext().getBean(IBRMSADHService.class)
                .getLoiChargesForADH(requestDTO, orgId,
                        MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE);
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        if (CollectionUtils.isNotEmpty(hoardingBookingEntityList)) {
            for (HoardingBookingEntity hoardingEntity : hoardingBookingEntityList) {

                ADHRateMaster master = new ADHRateMaster();
                for (ADHRateMaster Loitaxes : listOFLoiTaxes) {
                    if (!Loitaxes.getDependsOnFactorList().isEmpty()) {
                        for (String dependFactor : Loitaxes.getDependsOnFactorList()) {
                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.AREA)) {
                                master.setArea(hoardingEntity.getHoardingMasterEntity().getHoardingArea().doubleValue());
                            }
                            if (StringUtils.equalsIgnoreCase(dependFactor, MainetConstants.AdvertisingAndHoarding.UNIT)) {
                                master.setUnit(Long.valueOf(hoardingBookingEntityList.size()));
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
                                    MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE)) {

                                List<LookUp> listLookup = CommonMasterUtility
                                        .getListLookup(MainetConstants.AdvertisingAndHoarding.ADVERTISEMENT, org);

                                if (hoardingEntity.getHoardingMasterEntity().getHoardingTypeId1() != null) {
                                    master.setUsageSubtype1(CommonMasterUtility
                                            .getHierarchicalLookUp(
                                                    hoardingEntity.getHoardingMasterEntity().getHoardingTypeId1(), org)
                                            .getDescLangFirst());
                                }

                                if (hoardingEntity.getHoardingMasterEntity().getHoardingTypeId2() != null) {
                                    master.setUsageSubtype2(CommonMasterUtility
                                            .getHierarchicalLookUp(
                                                    hoardingEntity.getHoardingMasterEntity().getHoardingTypeId2(), org)
                                            .getDescLangFirst());
                                }
                                if (hoardingEntity.getHoardingMasterEntity().getHoardingTypeId3() != null) {
                                    master.setUsageSubtype3(CommonMasterUtility
                                            .getHierarchicalLookUp(
                                                    hoardingEntity.getHoardingMasterEntity().getHoardingTypeId3(), org)
                                            .getDescLangFirst());
                                }
                                if (hoardingEntity.getHoardingMasterEntity().getHoardingTypeId4() != null) {
                                    master.setUsageSubtype4(CommonMasterUtility
                                            .getHierarchicalLookUp(
                                                    hoardingEntity.getHoardingMasterEntity().getHoardingTypeId4(), org)
                                            .getDescLangFirst());
                                }
                                if (hoardingEntity.getHoardingMasterEntity().getHoardingTypeId5() != null) {
                                    master.setUsageSubtype5(CommonMasterUtility
                                            .getHierarchicalLookUp(
                                                    hoardingEntity.getHoardingMasterEntity().getHoardingTypeId5(), org)
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
                                            orgId);
                                    lookupListLevel2 = CommonMasterUtility.getNextLevelData(
                                            MainetConstants.AdvertisingAndHoarding.ADV_ZONE_WARD, 2,
                                            orgId);
                                    lookupListLevel3 = CommonMasterUtility.getNextLevelData(
                                            MainetConstants.AdvertisingAndHoarding.ADV_ZONE_WARD, 3,
                                            orgId);
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

                    if (reqDto.getAdvertisementDto().getLocCatType().contentEquals(MainetConstants.FlagE)) {
                        master.setLocationCategory(MainetConstants.AdvertisingAndHoarding.EXISTING_LOCATION);
                    } else {
                        master.setLocationCategory(MainetConstants.AdvertisingAndHoarding.NEW_LOCATION);
                    }
                    master.setChargeApplicableAt(Loitaxes.getChargeApplicableAt());
                    master.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                    master.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE);
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

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch get All Hoarding Master By OrgId", notes = "Fetch get All Hoarding Master By OrgId", response = Object.class)
    @Path("/getAllHoardingMasterByOrgId/orgId/{orgId}")
    public List<HoardingMasterDto> getHoardingMasterListByOrgId(Long orgId) {

        List<HoardingMasterDto> hoardingMasterDtoList = new ArrayList<>();
        List<HoardingMasterEntity> hoardingMasterEntityList = newHoardingApplicationRepository.findHoardingListByOrgId(orgId);
        if (CollectionUtils.isNotEmpty(hoardingMasterEntityList)) {
            hoardingMasterEntityList.forEach(entity -> {
                HoardingMasterDto hoardinDto = new HoardingMasterDto();
                BeanUtils.copyProperties(entity, hoardinDto);
                hoardingMasterDtoList.add(hoardinDto);
            });
        }

        return hoardingMasterDtoList;
    }

    @Override
    @Transactional
    public NewAdvertisementReqDto getDataForHoardingApplication(Long applicationId, Long orgId) {
        TbCfcApplicationMstEntity cfcApplicationMstEntity = new TbCfcApplicationMstEntity();
        CFCApplicationAddressEntity addressEntity = new CFCApplicationAddressEntity();
        NewAdvertisementReqDto advReqDto = newAdvApplicationService
                .getNewAdvertisementApplicationByAppId(applicationId, orgId);
        // make data for hoarding details
        List<NewAdvertisementApplicationDetDto> newAdvertDetDtos = new ArrayList<>();
        List<HoardingBookingDto> hoardingBookingDto = hoardingMasterService
                .getHoardingBookingDetails(advReqDto.getAdvertisementDto().getAdhId(), orgId);
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        hoardingBookingDto.forEach(hoardingBooking -> {
            Long hoardingId = hoardingBooking.getHoardingId();
            HoardingMasterDto hoardingDto = hoardingMasterService.getByOrgIdAndHoardingId(orgId, hoardingId);
            NewAdvertisementApplicationDetDto applicationDetDto = new NewAdvertisementApplicationDetDto();
            // set data into applicationDetDto
            applicationDetDto.setHoardingId(hoardingId);
            applicationDetDto.setAdvDetailsDesc(hoardingDto.getHoardingDescription());
            applicationDetDto.setAdvDetailsHeight(hoardingDto.getHoardingHeight());
            applicationDetDto.setAdvDetailsLength(hoardingDto.getHoardingLength());
            applicationDetDto.setAdvDetailsArea(hoardingDto.getHoardingArea());
            applicationDetDto.setDisplayIdDesc(hoardingDto.getHoardingTypeIdDesc());

            applicationDetDto.setDisplayIdDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(hoardingDto.getDisplayTypeId(),
                    org).getDescLangFirst());
            newAdvertDetDtos.add(applicationDetDto);
        });
        advReqDto.getAdvertisementDto().setNewAdvertDetDtos(newAdvertDetDtos);

        cfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
        addressEntity = cfcService.getApplicantsDetails(applicationId);

        ApplicantDetailDTO applicant = new ApplicantDetailDTO();
        applicant.setApplicantFirstName(cfcApplicationMstEntity.getApmFname());
        applicant.setApplicantMiddleName(cfcApplicationMstEntity.getApmMname());
        applicant.setApplicantLastName(cfcApplicationMstEntity.getApmLname());
        applicant.setApplicantTitle(cfcApplicationMstEntity.getApmTitle());
        applicant.setGender(cfcApplicationMstEntity.getApmSex());
        applicant.setMobileNo(addressEntity.getApaMobilno());
        applicant.setEmailId(addressEntity.getApaEmail());
        applicant.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo());
        applicant.setBuildingName(addressEntity.getApaBldgnm());
        applicant.setAreaName(addressEntity.getApaAreanm());
        applicant.setRoadName(addressEntity.getApaRoadnm());
        applicant.setVillageTownSub(addressEntity.getApaCityName());
        applicant.setPinCode(addressEntity.getApaPincode().toString());
        setGender(applicant, orgId);
        if (cfcApplicationMstEntity.getApmUID() != null)
            applicant.setAadharNo(cfcApplicationMstEntity.getApmUID().toString());
        applicant.setBplNo(cfcApplicationMstEntity.getApmBplNo());
        if (applicant.getBplNo() != null && !applicant.getBplNo().isEmpty()) {
            applicant.setIsBPL(MainetConstants.FlagY);
        } else {
            applicant.setIsBPL(MainetConstants.FlagN);
        }
        advReqDto.setApplicantDetailDTO(applicant);
        return advReqDto;
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

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Get All Hoarding Booking details   Data By  orgId", notes = "Get All Hoarding Booking details   Data By  orgId")
    @Path("/getHoardingBookingByOrgId/orgId/{orgId}")
    @Override
    @Transactional(readOnly = true)
    public List<HoardingBookingDto> getHoardingDetailsByOrgId(Long orgId) {
    	List<HoardingBookingDto> bookingDtoList = new ArrayList<>();
        try {
            List<HoardingBookingEntity> entityList = hoardingBookingRepository.fetchHoardingDetailsByOrgId(orgId);
            if (entityList != null) {
            	entityList.forEach(entity-> {
            		HoardingBookingDto bookingDto = new HoardingBookingDto();
            		Long hoardingId=entity.getHoardingMasterEntity().getHoardingId();
                    BeanUtils.copyProperties(entityList, bookingDto);
                    bookingDto.setHoardingId(hoardingId);
                    bookingDtoList.add(bookingDto);
            	});
            	
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Master by hoardingId", exception);
        }
        return bookingDtoList;
    }
}
