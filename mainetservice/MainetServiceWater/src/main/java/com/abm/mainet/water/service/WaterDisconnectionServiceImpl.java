package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.AbstractService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.water.dao.WaterDisconnectionRepository;
import com.abm.mainet.water.dao.WaterNoDuesCertificateDao;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.TBWaterDisconnection;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.repository.PlumberMasterRepository;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.abm.mainet.water.utility.WaterCommonUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.WaterDisconnectionService")
@Api(value = "/waterdisconnectionservice")
@Path("/waterdisconnectionservice")
public class WaterDisconnectionServiceImpl extends AbstractService implements WaterDisconnectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterDisconnectionServiceImpl.class);

    @Resource
    private WaterDisconnectionRepository waterDisconnectionRepository;

    @Resource
    private PlumberMasterRepository plumberMasterRepository;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private CommonService commonService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ICFCApplicationAddressService addressService;

    @Resource
    private ICFCApplicationMasterService applicationMasterService;

    @Resource
    private WaterServiceMapper mapper;

    @Resource
    private TbCsmrInfoRepository connectionInfoRepository;

    @Resource
    private WaterCommonService waterCommonService;

    @Autowired
    ServiceMasterService iServiceMasterService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.WaterDisconnectionService# getConnectionDetails
     * (com.abm.mainetservice.rest.water.bean.WaterDeconnectionResponseDTO)
     */
    @Override
    @Transactional(readOnly = true)
    @POST
    @Path("/getconnectionfordisconnection")
    public WaterDisconnectionResponseDTO getConnectionsAvailableForDisConnection(
            @RequestBody final WaterDeconnectionRequestDTO requestDTO) {
        return waterDisconnectionRepository.getConnAccessibleForDisConnectionDetails(requestDTO);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.WaterDisconnectionService# saveDisconnectionDetails
     * (com.abm.mainetservice.rest.water.bean.WaterDeconnectionResponseDTO)
     */
    @Override
    @Transactional
    @POST
    @Path("/savedisconnection")
    public WaterDisconnectionResponseDTO saveDisconnectionDetails(final WaterDeconnectionRequestDTO requestDTO) {

        final RequestDTO commonRequest = new RequestDTO();

        final ApplicantDetailDTO applicantDetailsDto = requestDTO.getApplicantDetailsDto();
        commonRequest.setEmail(applicantDetailsDto.getEmailId());
        commonRequest.setTitleId(applicantDetailsDto.getApplicantTitle());
        commonRequest.setfName(applicantDetailsDto.getApplicantFirstName());
        commonRequest.setmName(applicantDetailsDto.getApplicantMiddleName());
        commonRequest.setlName(applicantDetailsDto.getApplicantLastName());
        commonRequest.setMobileNo(applicantDetailsDto.getMobileNo());
        if ((null != applicantDetailsDto.getAadharNo()) && (applicantDetailsDto.getAadharNo().length() > 0)) {
            commonRequest.setUid(
                    Long.valueOf(applicantDetailsDto.getAadharNo().replaceAll("[^0-9]", MainetConstants.BLANK)));
        }
        final Organisation org = new Organisation();
        org.setOrgid(requestDTO.getOrgId());

        requestDTO.getDisconnectionDto()
                .setDiscMethod(CommonMasterUtility.getValueFromPrefixLookUp("APP", "CAN", org).getLookUpId());

        /*
         * commonRequest.setGender(CommonMasterUtility
         * .getNonHierarchicalLookUpObject(Long.valueOf(applicantDetailsDto.getGender()) , org).getLookUpCode());
         */
        commonRequest.setBplNo(applicantDetailsDto.getBplNo());
        commonRequest.setFlatBuildingNo(applicantDetailsDto.getFlatBuildingNo());
        commonRequest.setCityName(applicantDetailsDto.getVillageTownSub());
        commonRequest.setPincodeNo(Long.valueOf(applicantDetailsDto.getPinCode()));

        // commonRequest.setBlockNo(applicantDetailsDto.getBlockName());
        commonRequest.setFloor(applicantDetailsDto.getFloorNo());
        commonRequest.setWing(applicantDetailsDto.getWing());
        commonRequest.setBldgName(applicantDetailsDto.getBuildingName());
        commonRequest.setHouseComplexName(applicantDetailsDto.getHousingComplexName());
        commonRequest.setRoadName(applicantDetailsDto.getRoadName());
        commonRequest.setAreaName(applicantDetailsDto.getAreaName());
        commonRequest.setLangId(Long.valueOf(applicantDetailsDto.getLangId()));
        commonRequest.setServiceId(requestDTO.getServiceId());
        commonRequest.setUserId(requestDTO.getUserId());
        commonRequest.setOrgId(requestDTO.getOrgId());
        if (requestDTO.isFreeService()) {
            commonRequest.setPayStatus("F");
        }
        final Long applicationId = applicationService.createApplication(commonRequest);
        if (null == applicationId) {
            throw new RuntimeException("Application Not Generated");
        }
        commonRequest.setApplicationId(applicationId);
        commonRequest.setDeptId(requestDTO.getDeptId());
        requestDTO.setApplicationId(applicationId);
        if ((applicationId != null) && (applicationId != 0)) {
            requestDTO.getDisconnectionDto().setApmApplicationId(applicationId);
        }
        if ((requestDTO.getUploadDocument() != null) && !requestDTO.getUploadDocument().isEmpty()) {
            fileUploadService.doFileUpload(requestDTO.getUploadDocument(), commonRequest);
        }

        return waterDisconnectionRepository.saveDisconnectionDetails(requestDTO);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.WaterDisconnectionService# getWordZoneBlockByApplicationId(java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
            final Long orgId) {

        final TbKCsmrInfoMH master = getAppDetailsAppliedForDisConnection(applicationId, orgId);

        WardZoneBlockDTO wardZoneDTO = null;

        if (master != null) {
            wardZoneDTO = new WardZoneBlockDTO();
            if (master.getCodDwzid1() != null) {
                wardZoneDTO.setAreaDivision1(master.getCodDwzid1());
            }
            if (master.getCodDwzid2() != null) {
                wardZoneDTO.setAreaDivision2(master.getCodDwzid2());
            }
            if (master.getCodDwzid3() != null) {
                wardZoneDTO.setAreaDivision3(master.getCodDwzid3());
            }
            if (master.getCodDwzid4() != null) {
                wardZoneDTO.setAreaDivision4(master.getCodDwzid4());
            }
            if (master.getCodDwzid5() != null) {
                wardZoneDTO.setAreaDivision5(master.getCodDwzid5());
            }
        }

        return wardZoneDTO;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public TbKCsmrInfoMH getAppDetailsAppliedForDisConnection(final Long applicationId, final Long orgId) {
        TbKCsmrInfoMH master = null;
        TBWaterDisconnection disconnection = waterDisconnectionRepository.getDisconnectionAppDetails(applicationId,
                orgId);
        if (disconnection != null) {
            master = waterCommonService.getWaterConnectionDetailsById(disconnection.getCsIdn(), orgId);
        }
        return master;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId) {
        Map<Long, Double> chargeMap = new HashMap<>();
        final WSRequestDTO requestDTO = new WSRequestDTO();
        try {
            chargeMap = disConnectionLOICharges(requestDTO, applicationId, orgId, serviceId);
        } catch (final CloneNotSupportedException e) {
            LOGGER.error("error in cloning", e);
        }
        return chargeMap;
    }

    /**
     * @param requestDTO
     * @param orgId
     * @param applicationId
     * @param serviceId
     * @param
     * @return
     * @throws CloneNotSupportedException
     *
     */
    private Map<Long, Double> disConnectionLOICharges(final WSRequestDTO requestDTO, final long applicationId,
            final long orgId, final long serviceId) throws CloneNotSupportedException {

        final Map<Long, Double> chargeMap = new HashMap<>();
        final List<WaterRateMaster> chargeModelList = new ArrayList<>();
        List<WaterRateMaster> requiredCharges = new ArrayList<>();
        WaterRateMaster tempRate = null;
        WaterRateMaster rateMaster = null;
        WaterRateMaster dependsOnRateMaster = null;
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        // [START] BRMS call initialize model
        final TbKCsmrInfoMH master = waterDisconnectionRepository.getApplicantDetails(applicationId, orgId);
        requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId,
                WaterServiceShortCode.WATER_DISCONNECTION);
        String subCategoryRDCDesc = MainetConstants.BLANK;
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC, 2,
                org);
        for (final LookUp lookup : subCategryLookup) {
            if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.RDC)) {
                subCategoryRDCDesc = lookup.getDescLangFirst();
            }
        }
        
        if(MainetConstants.BLANK.equals(subCategoryRDCDesc) && Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL))
        {
        	for (final LookUp lookup : subCategryLookup) {
                if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.RD)) {
                    subCategoryRDCDesc = lookup.getDescLangFirst();
                }
            }
        }
        
        for (final WaterRateMaster actualRate : requiredCharges) {

            if (actualRate.getTaxSubCategory().equals(subCategoryRDCDesc)) {
                if (master.getRoadList() != null) {
                    for (final TbWtCsmrRoadTypes road : master.getRoadList()) {
                        if (road.getIsDeleted().equals(MainetConstants.IsDeleted.NOT_DELETE)) {
                            rateMaster = null;
                            tempRate = (WaterRateMaster) actualRate.clone();
                            dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
                            rateMaster = WaterCommonUtility.populateLOINewWaterConnectionModel(tempRate, master, org);
                            setRoadDiggingCharges(rateMaster, dependsOnRateMaster, road, master, org);
                            WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
                            chargeModelList.add(rateMaster);

                        }
                    }
                }
            } else {
                tempRate = (WaterRateMaster) actualRate.clone();
                dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
                rateMaster = WaterCommonUtility.populateLOINewWaterConnectionModel(tempRate, master, org);
                setsecurityDeposit(tempRate, master);
                WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
                chargeModelList.add(rateMaster);
            }
        }
        requestDTO.setDataModel(chargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
        final List<?> waterRateList = RestClient.castResponse(output, WaterRateMaster.class);
        WaterRateMaster loiCharges = null;
        Double baseRate = 0d;
        double amount;
        for (final Object rate : waterRateList) {

            loiCharges = (WaterRateMaster) rate;
            LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
                    PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);
            baseRate = WaterCommonUtility.getAndSetBaseRate(loiCharges.getRoadLength(), loiCharges, null,
                    taxLookup.getLookUpCode());
            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(loiCharges.getTaxId(), amount);
        }
        return chargeMap;
        // [END]
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.WaterDisconnectionService# getApplicationDetails(long, long)
     */

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public WaterDeconnectionRequestDTO getAppDetailsForDisconnection(final long orgId, final long applicationId) {
        final WaterDeconnectionRequestDTO deconnectionRequestDTO = new WaterDeconnectionRequestDTO();

        final TBWaterDisconnection disconnection = waterDisconnectionRepository
                .getDisconnectionAppDetails(applicationId, orgId);
        final TbKCsmrInfoMH customerInfo = waterDisconnectionRepository.getApplicantDetails(applicationId, orgId);
        deconnectionRequestDTO
                .setDisconnectionDto(mapper.mapTBWaterDisconnectionToTBWaterDisconnectionDTO(disconnection));
        deconnectionRequestDTO.setConnectionInfo(mapper.mapTbKCsmrInfoToCustomerInfoDTO(customerInfo));

        final ApplicantDetailDTO applicantDetailsDTO = new ApplicantDetailDTO();
        final TbCfcApplicationMstEntity applicantMasterDetails = applicationMasterService
                .getCFCApplicationByApplicationId(applicationId, orgId);
        applicantDetailsDTO.setApplicantTitle(applicantMasterDetails.getApmTitle());
        applicantDetailsDTO.setApplicantFirstName(applicantMasterDetails.getApmFname());
        applicantDetailsDTO.setApplicantMiddleName(applicantMasterDetails.getApmMname());
        applicantDetailsDTO.setApplicantLastName(applicantMasterDetails.getApmLname());
        if (null != applicantMasterDetails.getApmUID()) {
            applicantDetailsDTO.setAadharNo(applicantMasterDetails.getApmUID().toString());
        }
        if ((null != applicantMasterDetails.getApmSex()) && !applicantMasterDetails.getApmSex().isEmpty()) {
            applicantDetailsDTO.setGender(CommonMasterUtility
                    .getValueFromPrefixLookUp(applicantMasterDetails.getApmSex(), MainetConstants.GENDER).getLookUpId()
                    + MainetConstants.BLANK);
        }
        if ((null != applicantMasterDetails.getApmBplNo()) && !applicantMasterDetails.getApmBplNo().isEmpty()) {
            applicantDetailsDTO.setBplNo(applicantMasterDetails.getApmBplNo());
            applicantDetailsDTO.setIsBPL(MainetConstants.Common_Constant.YES);

        } else {
            applicantDetailsDTO.setIsBPL(MainetConstants.Common_Constant.NO);
        }

        final CFCApplicationAddressEntity applicantDetails = addressService.getApplicationAddressByAppId(applicationId,
                orgId);
        applicantDetailsDTO.setBlockName(applicantDetails.getApaBlockno());
        applicantDetailsDTO.setFloorNo(applicantDetails.getApaFloor());
        applicantDetailsDTO.setWing(applicantDetails.getApaWing());
        applicantDetailsDTO.setBuildingName(applicantDetails.getApaBldgnm());
        applicantDetailsDTO.setHousingComplexName(applicantDetails.getApaHsgCmplxnm());
        applicantDetailsDTO.setRoadName(applicantDetails.getApaRoadnm());
        applicantDetailsDTO.setAreaName(applicantDetails.getApaAreanm());
        applicantDetailsDTO.setEmailId(applicantDetails.getApaEmail());
        applicantDetailsDTO.setMobileNo(applicantDetails.getApaMobilno());
        applicantDetailsDTO.setFlatBuildingNo(applicantDetails.getApaFlatBuildingNo());
        applicantDetailsDTO.setVillageTownSub(applicantDetails.getApaCityName());
        applicantDetailsDTO.setPinCode(applicantDetails.getApaPincode().toString());
        applicantDetailsDTO.setDwzid1(customerInfo.getCodDwzid1());
        applicantDetailsDTO.setDwzid2(customerInfo.getCodDwzid2());
        applicantDetailsDTO.setDwzid3(customerInfo.getCodDwzid3());
        applicantDetailsDTO.setDwzid4(customerInfo.getCodDwzid4());
        applicantDetailsDTO.setDwzid5(customerInfo.getCodDwzid5());
        deconnectionRequestDTO.setApplicantDetailsDto(applicantDetailsDTO);

        return deconnectionRequestDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.WaterDisconnectionService# validateOtherUlbLicence(java.lang.String)
     */
    @Override
    @POST
    @Path("/valiateLicense")
    public Long validateOtherUlbLicence(final String licenceNo) {

        return plumberMasterRepository.validatePlumberLicenceOutSideULB(licenceNo, new Date());
    }

    @Transactional
    @Override
    @WebMethod(exclude = true)
    public boolean updateDisconnectionDetails(final WaterDeconnectionRequestDTO requestDTO,
            final ScrutinyLableValueDTO scrutinyLableValueDTO,  final Organisation organisation) {

        final TBWaterDisconnection entity = mapper.mapWaterDisconnectionDtoTOEntity(requestDTO.getDisconnectionDto());
        saveScrutinyValue(scrutinyLableValueDTO);
        waterDisconnectionRepository.updateDisconnectionDetails(entity);
        TbKCsmrInfoMH csmrInfo = connectionInfoRepository.findOne(entity.getCsIdn());
        if (csmrInfo != null) {
            csmrInfo.setCsIsBillingActive(MainetConstants.Common_Constant.NO);
            connectionInfoRepository.save(csmrInfo);
           
            AtomicLong connstausId = new AtomicLong(0);
            List<LookUp> payLookUpList = CommonMasterUtility.getListLookup("CNS", organisation);
            payLookUpList.forEach(lookUp -> {
            	if(lookUp.getLookUpCode().equals("D")) {
            		connstausId.set(lookUp.getLookUpId());
            	}
            });
            connectionInfoRepository.updateConnectionStatusByCsIdn(csmrInfo.getCsIdn(), connstausId.longValue());
        }
        return true;
    }

    @Transactional
    @Override
    @WebMethod(exclude = true)
    public boolean updateDisconnectionDetails(final WaterDeconnectionRequestDTO requestDTO) {

        final TBWaterDisconnection entity = mapper.mapWaterDisconnectionDtoTOEntity(requestDTO.getDisconnectionDto());
        return waterDisconnectionRepository.updateDisconnectionDetails(entity);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public TBWaterDisconnectionDTO getDisconnectionAppDetails(final long orgId, final long applicationId) {
        final TBWaterDisconnection disconnection = waterDisconnectionRepository
                .getDisconnectionAppDetails(applicationId, orgId);
        return mapper.mapTBWaterDisconnectionToTBWaterDisconnectionDTO(disconnection);
    }

    private void setRoadDiggingCharges(final WaterRateMaster tempRate, final WaterRateMaster dependsOnFactorMaster,
            final TbWtCsmrRoadTypes road, final TbKCsmrInfoMH master, final Organisation org) {
        LookUp roadLookup = null;
        if (road != null) {
            tempRate.setRoadLength(road.getCrtRoadUnits());
            roadLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(road.getCrtRoadTypes()), org);
            dependsOnFactorMaster.setRoadType(String.valueOf(roadLookup.getDescLangFirst()));
            dependsOnFactorMaster.setRoadLength(road.getCrtRoadUnits());

        }

    }

    private void setsecurityDeposit(final WaterRateMaster actualRate, final TbKCsmrInfoMH master) {
        actualRate.setConnectionSize(Double
                .valueOf(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getCsCcnsize()).getDescLangFirst()));
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void initiateWorkflowForFreeService(WaterDeconnectionRequestDTO requestDTO) {
        if (requestDTO.isFreeService()) {// free service
            boolean checklist = false;
            if ((requestDTO.getUploadDocument() != null) && !requestDTO.getUploadDocument().isEmpty()) {
                checklist = true;
            }
            final ApplicantDetailDTO applicantDetailsDto = requestDTO.getApplicantDetailsDto();
            ApplicationMetadata applicationData = new ApplicationMetadata();
            final ServiceMaster serviceMaster = iServiceMasterService.getServiceMasterByShortCode(
                    MainetConstants.WaterServiceShortCode.WATER_DISCONNECTION, requestDTO.getOrgId());
            if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
                applicationData.setIsLoiApplicable(false);
            } else {
                applicationData.setIsLoiApplicable(true);
            }
            applicationData.setApplicationId(requestDTO.getApplicationId());
            applicationData.setIsCheckListApplicable(checklist);
            applicationData.setOrgId(requestDTO.getOrgId());
            applicantDetailsDto.setServiceId(requestDTO.getServiceId());
            applicantDetailsDto.setDepartmentId(requestDTO.getDeptId());
            commonService.initiateWorkflowfreeService(applicationData, applicantDetailsDto);
        }

    }

    @Override
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getDisconnectionDetailsForDashboardViewByAppId", notes = "getDisconnectionDetailsForDashboardViewByAppId", response = Object.class)
    @Path("/getDisconnectionDetailsForDashboardViewByAppId/applicationId/{applicationId}/orgId/{orgId}")
    public WaterDeconnectionRequestDTO getDisconnectionDetailsForDashboardViewByAppId(
            @PathParam("applicationId") Long applicationId, @PathParam("orgId") Long orgId) {

        WaterDeconnectionRequestDTO requestDto = new WaterDeconnectionRequestDTO();
        WaterDeconnectionRequestDTO waterDeconnRequestDto = getAppDetailsForDisconnection(orgId, applicationId);
        requestDto.setApplicantDetailsDto(waterDeconnRequestDto.getApplicantDetailsDto());
        requestDto.setDisconnectionDto(waterDeconnRequestDto.getDisconnectionDto());
        requestDto.setConnectionNo(waterDeconnRequestDto.getConnectionInfo().getCsCcn());
        requestDto.setConsumerName(waterDeconnRequestDto.getConnectionInfo().getConsumerName());
        return requestDto;
    }

    @Override
    public TbWtBillMasEntity getWaterBillDues(Long csIdn, Long orgId) {
        TbWtBillMasEntity waterDues = ApplicationContextProvider.getApplicationContext().getBean(WaterNoDuesCertificateDao.class)
                .getWaterDues(csIdn, orgId);
        if (waterDues != null) {
            double outStandingAmount = 0;
            if (CollectionUtils.isNotEmpty(waterDues.getBillDetEntity())) {
                for (TbWtBillDetEntity det : waterDues.getBillDetEntity()) {
                    outStandingAmount = outStandingAmount + (det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                }
            }
            waterDues.setBmTotalOutstanding(outStandingAmount);
        }
        return waterDues;
    }

    @Override
    public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
        return waterDisconnectionRepository.getPlumIdByApplicationId(applicationId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public TBWaterDisconnectionDTO getDisconnectionAppDetailsByCsIdn(final long orgId, final Long csIdn) {
        final TBWaterDisconnection disconnection = waterDisconnectionRepository
                .getDisconnectionAppDetailsByCsIdn(csIdn, orgId);
        return mapper.mapTBWaterDisconnectionToTBWaterDisconnectionDTO(disconnection);
    }

    @Override
    @Transactional(readOnly = true)
    public WaterDisconnectionResponseDTO getConnectionsForDisConnection(
            final WaterDeconnectionRequestDTO requestDTO) {
        return waterDisconnectionRepository.getConnForDisConnectionDetails(requestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public String getWorkflowRequestByAppId(Long apmApplicationId, Long orgId) {
        return connectionInfoRepository.getWorkflowRequestByAppId(apmApplicationId, orgId);
    }
}
