package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
import com.abm.mainet.common.exception.FrameworkException;
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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.ChangeOfUsage;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.repository.ChangeOfUsageRepository;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.abm.mainet.water.utility.WaterCommonUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Lalit.Prusti
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.ChangeOfUsageService")
@Api(value = "/changeusageservice")
@Path("/changeusageservice")
public class ChangeOfUsageServiceImpl extends AbstractService implements ChangeOfUsageService {

    @Resource
    private ChangeOfUsageRepository changeOfUsesRepository;

    @Resource
    private WaterServiceMapper waterServiceMapper;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ICFCApplicationAddressService addressService;

    @Resource
    private ICFCApplicationMasterService applicationMasterService;

    @Resource
    private CommonService commonService;

    @Autowired
    private ServiceMasterService iServiceMasterService;
    
    @Resource
    private NewWaterRepository waterRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ChangeOfUsageServiceImpl.class);

	@Autowired
	private NewWaterConnectionService waterConnectionService;
	
	@Autowired
	private TbCsmrInfoRepository csmrInfoRepository;
	
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationid, final Long serviceId,
            final Long orgId) {
        final ChangeOfUsage changeOfUsage = changeOfUsesRepository.getChangeOfUsaes(orgId, applicationid);
        final TbKCsmrInfoMH master = changeOfUsesRepository.getConnectionDetails(orgId, changeOfUsage.getCsIdn());
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
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId) {
        Map<Long, Double> chargeMap = new HashMap<>();
        final WSRequestDTO requestDTO = new WSRequestDTO();
        try {
            chargeMap = changeOfUsageLOICharges(requestDTO, applicationId, orgId, serviceId);
        } catch (final CloneNotSupportedException e) {
            LOG.error("Error in cloning ", e);
            throw new FrameworkException("Error while cloning in getLoiCharges() ", e);
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
    private Map<Long, Double> changeOfUsageLOICharges(final WSRequestDTO requestDTO, final long applicationId,
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
        final TbKCsmrInfoMH master = getConnDetailsByChangeOfUsageConnId(applicationId, orgId);

        requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId,
                WaterServiceShortCode.CHANGE_OF_USAGE);
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
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)){
                    final ChangeOfUsage changeOfUsage = changeOfUsesRepository.getChangeOfUsaes(orgId, applicationId);

            		if(MainetConstants.TAX_TYPE.LONG_TERM_DEPOSITS.equals(actualRate.getTaxSubCategory())) {
            			
				        String newUsageType1 = CommonMasterUtility.getHierarchicalLookUp(changeOfUsage.getNewTrmGroup1(), org).getDescLangFirst();
				        String newUsageType2 = CommonMasterUtility.getHierarchicalLookUp(changeOfUsage.getNewTrmGroup2(), org).getDescLangFirst();
				        String oldUsageType2 = CommonMasterUtility.getHierarchicalLookUp(master.getTrmGroup2(), org).getDescLangFirst();
	
	                	rateMaster.setUsageSubtype1(newUsageType1);
	                	rateMaster.setUsageSubtype2(newUsageType2);
	                	rateMaster.setUsageSubtype4(oldUsageType2);
	                	
            		}else if(MainetConstants.TAX_TYPE.METER_CHARGE.equals(actualRate.getTaxSubCategory())) {
	            		String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(changeOfUsage.getNewCsMeteredccn(), org).getDescLangFirst();
	            		rateMaster.setMeterType(meterType);
            		}
                }
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
    @WebMethod(exclude = true)
    public ChangeOfUsageDTO saveChangeOfUses(final ChangeOfUsageDTO changeOfUsesDTO) {
        final ChangeOfUsage uses = changeOfUsesRepository
                .save(waterServiceMapper.mapChangeOfUsageDtoToChangeOfUsage(changeOfUsesDTO));
        return waterServiceMapper.mapChangeOfUsageToChangeOfUsageDto(uses);
    }

    @Override
    @Transactional
    @POST
    @Path("/savechangeusage")
    public ChangeOfUsageDTO saveOrUpdateChangeOfUses(@RequestBody final ChangeOfUsageRequestDTO requestDTO) {
        final RequestDTO commonRequest = new RequestDTO();

        final ApplicantDetailDTO applicantDetailsDto = requestDTO.getApplicant();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			TbKCsmrInfoMH csmrInfo = waterRepository.fetchConnectionDetailsByConnNo(requestDTO.getConnectionNo(),
					requestDTO.getOrgId());
			commonRequest.setEmail(csmrInfo.getCsEmail());
        	commonRequest.setfName(csmrInfo.getCsName());
        	commonRequest.setlName(csmrInfo.getCsLname());
        	commonRequest.setMobileNo(csmrInfo.getMobileNoOTP());
        	commonRequest.setUid(csmrInfo.getCsUid());
        	
        	 commonRequest.setGender(String.valueOf(csmrInfo.getCsOGender()));
             commonRequest.setBplNo(csmrInfo.getBplNo());
             commonRequest.setFlatBuildingNo(csmrInfo.getFlatNo());
             commonRequest.setCityName(csmrInfo.getCsCcityName());
             commonRequest.setPincodeNo(csmrInfo.getCsCpinCode()!=null ? Long.valueOf(csmrInfo.getCsCpinCode()):0l);

             commonRequest.setBldgName(csmrInfo.getCsBldplt());
             commonRequest.setRoadName(csmrInfo.getCsBrdcross());
             commonRequest.setAreaName(csmrInfo.getCsBlanear());
            	
        }else{
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
            commonRequest.setGender(applicantDetailsDto.getGender());
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
           
        }
        commonRequest.setLangId(Long.valueOf(applicantDetailsDto.getLangId()));
        commonRequest.setServiceId(requestDTO.getServiceId());
        commonRequest.setUserId(requestDTO.getUserId());
        commonRequest.setOrgId(requestDTO.getOrgId());
        commonRequest.setDeptId(requestDTO.getDeptId());	
        if (requestDTO.isFree()) {
            commonRequest.setPayStatus("F");
        }
        final Long applicationId = applicationService.createApplication(commonRequest);
        if (null == applicationId) {
            throw new RuntimeException("Application Not Generated");
        }

        commonRequest.setApplicationId(applicationId);
        requestDTO.setApplicationId(applicationId);
        if ((applicationId != null) && (applicationId != 0)) {
            requestDTO.getChangeOfUsage().setApmApplicationId(applicationId);
        }

        if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
            fileUploadService.doFileUpload(requestDTO.getDocumentList(), commonRequest);
        }

        requestDTO.getChangeOfUsage().setApmApplicationId(applicationId);
        requestDTO.getChangeOfUsage().setApmApplicationDate(new Date());
        return saveChangeOfUses(requestDTO.getChangeOfUsage());

    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public ChangeOfUsageDTO getChangeOfUses(final long orgId, final long applicationId) {
        final ChangeOfUsage uses = changeOfUsesRepository.getChangeOfUsaes(orgId, applicationId);
        return waterServiceMapper.mapChangeOfUsageToChangeOfUsageDto(uses);
    }

    @Override
    @WebMethod(exclude = true)
    public boolean approveChangeOfUses(final ChangeOfUsageDTO changeOfUsesDTO) {
        changeOfUsesRepository.updateChangeOfUsaes(changeOfUsesDTO.getOrgId(), changeOfUsesDTO.getCsIdn(),
                changeOfUsesDTO.getNewTrmGroup1(), changeOfUsesDTO.getNewTrmGroup2(), changeOfUsesDTO.getNewTrmGroup3(),
                changeOfUsesDTO.getNewTrmGroup4(), changeOfUsesDTO.getNewTrmGroup5());
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.ChangeOfUsageService#getApplicantData (long, long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getApplicantData", notes = "getApplicantData", response = Object.class)
    @Path("/getApplicantData/applicationId/{applicationId}/orgId/{orgId}")
    public ChangeOfUsageRequestDTO getApplicantData(@PathParam("applicationId") Long applicationId,
            @PathParam("orgId") Long orgId) {

        final ChangeOfUsageRequestDTO responseDto = new ChangeOfUsageRequestDTO();
        final ChangeOfUsage changeOfUsage = changeOfUsesRepository.getChangeOfUsaes(orgId, applicationId);
        final TbKCsmrInfoMH conectionInfo = changeOfUsesRepository.getConnectionDetails(orgId,
                changeOfUsage.getCsIdn());
        responseDto.setConnectionNo(conectionInfo.getCsCcn());
        responseDto.setConnectionSize(conectionInfo.getCsCcnsize());
        responseDto.setConsumerName(conectionInfo.getCsOname());
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
        /*
         * if ((null != applicantMasterDetails.getApmSex()) && !applicantMasterDetails.getApmSex().isEmpty()) {
         * applicantDetailsDTO.setGender(CommonMasterUtility .getValueFromPrefixLookUp(applicantMasterDetails.getApmSex(),
         * MainetConstants.GENDER).getLookUpId() + MainetConstants.BLANK); }
         */
        if ((null != applicantMasterDetails.getApmBplNo()) && !applicantMasterDetails.getApmBplNo().isEmpty()) {
            applicantDetailsDTO.setBplNo(applicantMasterDetails.getApmBplNo());
            applicantDetailsDTO.setIsBPL(MainetConstants.Common_Constant.YES);

        } else {
            applicantDetailsDTO.setIsBPL(MainetConstants.Common_Constant.NO);
        }

        final CFCApplicationAddressEntity applicantDetails = addressService.getApplicationAddressByAppId(applicationId,
                orgId);
        TbKCsmrInfoMH csmrInfo = getConnDetailsByChangeOfUsageConnId(applicationId, orgId);
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

        applicantDetailsDTO.setDwzid1(csmrInfo.getCodDwzid1());
        applicantDetailsDTO.setDwzid2(csmrInfo.getCodDwzid2());
        applicantDetailsDTO.setDwzid3(csmrInfo.getCodDwzid3());
        applicantDetailsDTO.setDwzid4(csmrInfo.getCodDwzid4());
        applicantDetailsDTO.setDwzid5(csmrInfo.getCodDwzid5());

        responseDto.setApplicant(applicantDetailsDTO);
        responseDto.setChangeOfUsage(waterServiceMapper.mapChangeOfUsageToChangeOfUsageDto(changeOfUsage));
        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public TbKCsmrInfoMH getConnDetailsByChangeOfUsageConnId(final Long applicationId, final Long orgId) {
    	TbKCsmrInfoMH master = null;
    	try {
    		final ChangeOfUsage changeOfUsage = changeOfUsesRepository.getChangeOfUsaes(orgId, applicationId);
    		if(changeOfUsage!=null) {
    			try {
    				master = changeOfUsesRepository.getConnectionDetails(orgId, changeOfUsage.getCsIdn());
    			}catch(Exception ex) {
    				LOG.error("Exception while getting csmr details for applicationId " + applicationId + " " + ex.getMessage());
    			}
    		}
    	}catch(Exception ex) {
			LOG.error("Exception while getting applicant details for applicationId " + applicationId + " " + ex.getMessage());
    	}
        return master;

    }

    @Transactional
    @Override
    @WebMethod(exclude = true)
    public boolean updateChangeOfUsageDetails(final ChangeOfUsageRequestDTO requestDTO,
            final ScrutinyLableValueDTO scrutinyLableValueDTO) {

        final ChangeOfUsage entity = waterServiceMapper
                .mapChangeOfUsageDtoToChangeOfUsage(requestDTO.getChangeOfUsage());
        saveScrutinyValue(scrutinyLableValueDTO);
        approveChangeOfUses(requestDTO.getChangeOfUsage());
        Organisation organisation = new Organisation();
        organisation.setOrgid(entity.getOrgId());
        if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)){
            changeOfUsesRepository.updateChangeOfUsageEntity(entity.getApmApplicationId(), entity.getChanGrantFlag(), entity.getOrgId());
        }else {
            changeOfUsesRepository.save(entity);
        }
        updateNewUsageDetailsInCsmrEntity(entity.getCsIdn(), requestDTO);
        return true;
    }

    private void updateNewUsageDetailsInCsmrEntity(Long csIdn, ChangeOfUsageRequestDTO requestDTO) {
    	try {
    		csmrInfoRepository.updateCsmrEntry(csIdn,requestDTO.getChangeOfUsage().getNewCsMeteredccn(),
    				requestDTO.getChangeOfUsage().getNewTrmGroup1(), requestDTO.getChangeOfUsage().getNewTrmGroup2(),
    				requestDTO.getChangeOfUsage().getNewTrmGroup3());
    		LOG.info("Updated csmr info to meter type = "+requestDTO.getChangeOfUsage().getNewCsMeteredccn() +
    				" group1 = "+requestDTO.getChangeOfUsage().getNewTrmGroup1() + " group 2 "+
    				requestDTO.getChangeOfUsage().getNewTrmGroup2() + " group 3 "+requestDTO.getChangeOfUsage().getNewTrmGroup3());
    	}catch(Exception exception) {
			LOG.error("Exception occured when calling audit service  ", exception);
		}		
	}

	@Transactional
    @Override
    @WebMethod(exclude = true)
    public void initiateWorkflowForFreeService(ChangeOfUsageRequestDTO requestDTO) {

        if (requestDTO.isFree()) {
            boolean checklist = false;
            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                checklist = true;
            }
            final ApplicantDetailDTO applicantDetailsDto = requestDTO.getApplicant();
            ApplicationMetadata applicationData = new ApplicationMetadata();
            applicationData.setApplicationId(requestDTO.getApplicationId());
            applicationData.setIsCheckListApplicable(checklist);
            applicationData.setOrgId(requestDTO.getOrgId());
            final ServiceMaster serviceMaster = iServiceMasterService.getServiceMasterByShortCode(
                    MainetConstants.WaterServiceShortCode.CHANGE_OF_USAGE, requestDTO.getOrgId());
            if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
                applicationData.setIsLoiApplicable(false);
            } else {
                applicationData.setIsLoiApplicable(true);
            }
            applicantDetailsDto.setServiceId(requestDTO.getServiceId());
            applicantDetailsDto.setDepartmentId(requestDTO.getDeptId());
            commonService.initiateWorkflowfreeService(applicationData, applicantDetailsDto);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Long> getChangeOfUsageApplicationId(Long orgId, Long csIdn) {
        return changeOfUsesRepository.getChangeOfUsageApplicationId(orgId, csIdn);
    }
    
    @Override
    public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
        return waterRepository.getPlumIdByApplicationId(applicationId, orgId);
    }

	@Override
	public List<ChangeOfUsage> getChangedUsageDetails(Long orgid, String changedFlag) {
		return changeOfUsesRepository.getChangedUsageDetails(orgid, changedFlag);
	}

}
