package com.abm.mainet.water.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.dto.ChallanDetailsDTO;
import com.abm.mainet.cfc.challan.dto.ChallanRegenerateDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CustomerInfoDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.PlumberHoleManDTO;
import com.abm.mainet.common.integration.dto.PlumberMasterDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.mapper.TbCfcApplicationMstServiceMapper;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.AbstractService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CFCCommonService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
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
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.water.dao.ChangeOfOwnershipRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.dao.WaterDisconnectionRepository;
import com.abm.mainet.water.dao.WaterReconnectionRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.AdditionalOwnerInfo;
import com.abm.mainet.water.domain.ChangeOfOwnerMas;
import com.abm.mainet.water.domain.ChangeOfUsage;
import com.abm.mainet.water.domain.CsmrInfoHistEntity;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbKLinkCcn;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.domain.TbWtExcessAmt;
import com.abm.mainet.water.domain.TbWtExcessAmtHist;
import com.abm.mainet.water.domain.WaterPenaltyEntity;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.MeterDetailsEntryDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.RoadTypeDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.ViewWaterDetailRequestDto;
import com.abm.mainet.water.dto.WaterBillPrintingDTO;
import com.abm.mainet.water.dto.WaterDashboardDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.dto.WaterPenaltyDto;
import com.abm.mainet.water.repository.BillMasterJpaRepository;
import com.abm.mainet.water.repository.ChangeOfUsageRepository;
import com.abm.mainet.water.repository.PlumberMasterRepository;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.repository.TbWtExcessAmtJpaRepository;
import com.abm.mainet.water.repository.WaterPenaltyRepository;
import com.abm.mainet.water.rest.dto.KDMCWaterDetailsResponseDTO;
import com.abm.mainet.water.rest.dto.ViewBillDet;
import com.abm.mainet.water.rest.dto.ViewBillMas;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.rest.dto.WaterTaxDetailsDto;
import com.abm.mainet.water.utility.WaterCommonUtility;
import com.google.common.util.concurrent.AtomicDouble;
import com.ibm.icu.text.SimpleDateFormat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author deepika.pimpale
 *
 */
@Service(value = "NewWaterConnection")
@WebService(endpointInterface = "com.abm.mainet.water.service.NewWaterConnectionService")
@Api(value = "/waterconnectionservice")
@Path("/waterconnectionservice")
public class NewWaterConnectionServiceImpl extends AbstractService implements NewWaterConnectionService, CFCCommonService {
	
	private static final Logger LOGGER = Logger.getLogger(NewWaterConnectionServiceImpl.class);
  
	@Resource
    private NewWaterRepository waterRepository;

    @Resource
    private WaterServiceMapper waterServiceMapper;

    @Resource
    private TbCfcApplicationMstServiceMapper cfcServiceMapper;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private CommonService commonService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private NewWaterRepository newWaterRepository;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private TbWtBillScheduleService tbWtBillScheduleService;

    @Resource
    private TbWtBillMasService tbWtBillMasService;

    @Resource
    private BillMasterService billMasterService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Resource
    private AuditService auditService;

    @Autowired
    private IOrganisationService organizationService;

    @Resource
    private WaterDisconnectionRepository disconnectionRepository;

    @Resource
    private ChangeOfUsageRepository changeOfUsageRepository;

    @Resource
    private WaterCommonService waterCommonService;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;
    
    @Resource
    TbDepartmentService departmentService;

    @Autowired
    private DepartmentService departmentserveise;

    // added for BRMS changes, can be moved later.

    @Resource
    private ServiceMasterService serviceMasterService;

    @Resource
    private TbTaxMasService taxMasService;

    @Resource
    private MeterDetailEntryService meterDetailsService;

    @Resource
    private BillMasterCommonService billMasterCommonService;

    @Resource
    private BillMasterJpaRepository billMasterJpaRepository;

    private UtilityService utilityService;

    @Autowired
    private PlumberMasterRepository plumberMasterRepository;

    @Resource
    private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;

    @Autowired
    private TbFinancialyearService financialyearService;

    @Autowired
    private TbWtExcessAmtJpaRepository tbWtExcessAmtJpaRepository;

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private ChangeOfOwnershipRepository changeOfOwnershipRepository; 
    
    @Resource
    private WaterReconnectionRepository waterReconnectionRepository;
    
    @Autowired
	private IReceiptEntryService iReceiptEntryService;
    
    @Autowired
    private IFinancialYearService financialYearService;
    
	@Resource
	private WaterPenaltyRepository waterPenaltyRepository;
    
	@Autowired
	private IWaterPenaltyService waterPenaltyService;
	
    @Autowired
    private DepartmentService deptService;
    @Autowired
    private TbCfcApplicationMstService tbCfcApplicationMstService;
    
   
  
    
    private static Logger log = Logger.getLogger(NewWaterConnectionServiceImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.WaterConnectionService#
     * saveWaterApplication(com.abm.mainetservice.rest.water.bean. NewWaterConnectionReqDTO)
     */
    @Override
    @POST
    @Path("/savewaterconnection")
    @Transactional
    public NewWaterConnectionResponseDTO saveWaterApplication(@RequestBody final NewWaterConnectionReqDTO requestDTO) {
        NewWaterConnectionResponseDTO response = saveNewWaterApplication(requestDTO);
        return response;
    }

    @Transactional
    @Override
    @WebMethod(exclude = true)
    public void initiateWorkFlowForFreeService(final NewWaterConnectionReqDTO requestDTO) {
        if (requestDTO.isFree()) { // free
            boolean checklist = false;
            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                checklist = true;
            }
            ApplicationMetadata applicationData = new ApplicationMetadata();
            applicationData.setApplicationId(requestDTO.getApplicationId());
            applicationData.setIsCheckListApplicable(checklist);
            applicationData.setOrgId(requestDTO.getOrgId());
            requestDTO.getApplicantDTO().setServiceId(requestDTO.getServiceId());
            requestDTO.getApplicantDTO().setDepartmentId(requestDTO.getDeptId());
            if(requestDTO.getCsmrInfo()!=null) {
            	if(requestDTO.getCsmrInfo().getCodDwzid1()!=null) {
            		requestDTO.getApplicantDTO().setDwzid1(requestDTO.getCsmrInfo().getCodDwzid1());
            	}
            	if(requestDTO.getCsmrInfo().getCodDwzid2()!=null) {
            		requestDTO.getApplicantDTO().setDwzid2(requestDTO.getCsmrInfo().getCodDwzid2());
            	}
            	if(requestDTO.getCsmrInfo().getCodDwzid3()!=null) {
            		requestDTO.getApplicantDTO().setDwzid3(requestDTO.getCsmrInfo().getCodDwzid3());
            	}
            	requestDTO.getApplicantDTO().setConnectonSize(null);
            	if(requestDTO.getCsmrInfo().getCodDwzid4()!=null) {
            		requestDTO.getApplicantDTO().setDwzid4(requestDTO.getCsmrInfo().getCodDwzid4());
            	}
            	if(requestDTO.getCsmrInfo().getCodDwzid5()!=null) {
                	requestDTO.getApplicantDTO().setDwzid5(requestDTO.getCsmrInfo().getCodDwzid5());
            	}
            	
            	Organisation org = organizationService.getOrganisationById(requestDTO.getOrgId());
				
	        	try {
	        		if( Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
	        			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(requestDTO.getCsmrInfo().getCsCcnsize(), org);
						Double d=Double.valueOf(lookUp.getLookUpDesc());
					//	reqDTO.getApplicantDTO().setConnectonSize(new BigDecimal(d));
	        			requestDTO.getApplicantDTO().setConnectonSize(new BigDecimal(d));
	        			requestDTO.getCsmrInfo().setConnectionSize(d);
	        		
	        		}
			    } catch (Exception e) {
			        LOGGER.error("Error", e);
			    }
        	}
            commonService.initiateWorkflowfreeService(applicationData, requestDTO.getApplicantDTO());
        }
    }

    @Transactional
    @Override
    @WebMethod(exclude = true)
    public NewWaterConnectionResponseDTO saveNewWaterApplication(final NewWaterConnectionReqDTO requestDTO) {

        final Long orgId = requestDTO.getOrgId();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final NewWaterConnectionResponseDTO waterDTO = new NewWaterConnectionResponseDTO();
        try {
            final TbKCsmrInfoMH master = waterServiceMapper
                    .mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(requestDTO.getCsmrInfo());
            final ApplicantDetailDTO applicantDetailDto = requestDTO.getApplicantDTO();
            applicantDetailDto.setOrgId(orgId);
            master.setLmodDate(new Date());
            if ((requestDTO.getPlumberName() != null)
                    && !requestDTO.getPlumberName().equals(MainetConstants.CommonConstants.BLANK)) {
                final PlumberMaster plumber = waterRepository.getVerifiedPlumberId(requestDTO.getPlumberName());
                if (plumber == null) {
                	if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
                		 waterDTO.getErrorList().add(ApplicationSession.getInstance().getMessage("water.valid.plumber"));
                	}
                } else {
                    master.setPlumId(plumber.getPlumId());
                }
            }
            if( Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
            	master.setHouseNumber(applicantDetailDto.getHouseNumber());
            }
            master.setPropertyNo(requestDTO.getPropertyNo());
            master.setCsEntryFlag(MainetConstants.FlagS);
            if (waterDTO.getErrorList().isEmpty()) {
                try {
                    if (requestDTO.isFree()) {
                        requestDTO.setPayStatus("F");
                    }
                    final Long applicationId = applicationService.createApplication(requestDTO);
                    master.setApplicationNo(applicationId);
                    waterDTO.setApplicationNo(applicationId);
                    requestDTO.setApplicationId(applicationId);
                    if (master.getOwnerList() != null) {
                        for (final AdditionalOwnerInfo owner : master.getOwnerList()) {
                            owner.setCsIdn(master);
                        }
                    }
                    if (master.getLinkDetails() != null) {
                        for (final TbKLinkCcn link : master.getLinkDetails()) {
                            link.setCsIdn(master);
                        }
                    }
                    
                    master.setDistribution(null);
                    master.setRoadList(null);
                    waterRepository.saveCsmrInfo(master);
                    final CsmrInfoHistEntity hist = new CsmrInfoHistEntity();
                    auditService.createHistory(master, hist);
                    if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                        fileUploadService.doFileUpload(requestDTO.getDocumentList(), requestDTO);
                    }
                    waterDTO.setStatus(PrefixConstants.NewWaterServiceConstants.SUCCESS);
                } catch (final Exception ex) {
                    log.error("In saving new water Connection", ex);
                    waterDTO.setStatus(PrefixConstants.NewWaterServiceConstants.FAIL);
                }
            } else {
                waterDTO.setStatus(PrefixConstants.NewWaterServiceConstants.FAIL);
            }
        } catch (Exception e) {
            log.error("Exception ocours to saveNewWaterApplication() " + e);
            throw new FrameworkException("Exception occours in saveNewWaterApplication() method" + e);
        }

        return waterDTO;

    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
            final Long orgId) {
        WardZoneBlockDTO wardZoneDTO = null;
        try {
            final TbKCsmrInfoMH master = waterRepository.getApplicantInformationById(applicationId, orgId);

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
                if (master.getTrmGroup1() != null) {
                    wardZoneDTO.setTariffCategory(master.getTrmGroup1());
                }
            }
        } catch (Exception e) {
            log.error("Exception ocours to getWordZoneBlockByApplicationId() " + e);
            throw new FrameworkException("Exception occours in getWordZoneBlockByApplicationId() method" + e);
        }

        return wardZoneDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.NewWaterConnectionService# getPlumberByPlumberNo(java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public PlumberMaster getPlumberByPlumberNo(final String plumId) {
        final PlumberMaster plumberNumber = waterRepository.getVerifiedPlumberId(plumId);
        return plumberNumber;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.NewWaterConnectionService# getAvgConsumptionById(java.lang.Long, long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Long getAvgConsumptionById(final Long instId, final long orgid) {

        final Long avgConsumtion = waterRepository.getAvgConsumtionById(instId, orgid);
        return avgConsumtion;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.NewWaterConnectionService# getSlopeValueByRoadLength(java.lang.Long, long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Double getSlopeValueByRoadLength(final Long rcLength, final Organisation org) {
        final Double slopeValue = waterRepository.getSlopeValueByLength(rcLength, org);
        return slopeValue;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.NewWaterConnectionService# getDiameterSlab(java.lang.Double,
     * com.abm.mainetservice.web.common.entity.Organisation)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Double getDiameterSlab(final Double diameter, final Organisation organisation) {
        final Double ccnSize = waterRepository.getDiameteSlab(diameter, organisation);
        return ccnSize;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
            throws CloneNotSupportedException {
        Map<Long, Double> chargeMap = new HashMap<>();
        final WSRequestDTO requestDTO = new WSRequestDTO();
        chargeMap = newWaterConnectionLOICharges(requestDTO, applicationId, orgId, serviceId);
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
    private Map<Long, Double> newWaterConnectionLOICharges(final WSRequestDTO requestDTO, final long applicationId,
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
        final TbKCsmrInfoMH master = newWaterRepository.getApplicantInformationById(applicationId, orgId);
        
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	master.setDistribution(newWaterRepository.getDistributionByCsIdn(master.getCsIdn(), orgId));
        }
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			 if(master.getNoOffmls()!=null && master.getNoOfFamilies()==null) {
				master.setNoOfFamilies(master.getNoOffmls());
			 }
        }
        ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
        if (StringUtils.contains(serviceMaster.getSmShortdesc(), PrefixConstants.NewWaterServiceConstants.WNC)) {
            requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId,
                    PrefixConstants.NewWaterServiceConstants.WNC);
        } else {
            requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId, "WIL");
        }

        String subCategoryRDCDesc = MainetConstants.BLANK; 
        String subCategoryRRDesc = MainetConstants.BLANK;
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC, 2,
                org);
        for (final LookUp lookup : subCategryLookup) {
            if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.RDC)) {
                subCategoryRDCDesc = lookup.getDescLangFirst();
            }
            if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.RR)) {
                subCategoryRRDesc = lookup.getDescLangFirst();
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
        
        //
        
        double dischargeRate = 0.0d;
        
        if(master != null)
        {
        	if(null != master.getDistribution() && null != master.getDistribution().getRcDistccndif())
        	{
        		dischargeRate= master.getDistribution().getRcDistccndif();
        		log.info("dischargeRate:" + dischargeRate);
        	}
        	else
        	{
        		log.info("dischargeRate is null");
        	}
        		
        }
        
        for (final WaterRateMaster actualRate : requiredCharges) {
            TbTaxMas taxMaster = tbTaxMasService.findById(actualRate.getTaxId(), actualRate.getOrgId());
            final String taxCode = CommonMasterUtility.getHierarchicalLookUp(taxMaster.getTaxCategory1(), org)
                    .getLookUpCode();
            if (!actualRate.getDependsOnFactorList().contains("ELC")) {
                if (actualRate.getTaxSubCategory().equals(subCategoryRDCDesc)) {
                    if (!CollectionUtils.isEmpty(master.getRoadList())) {
                        for (final TbWtCsmrRoadTypes road : master.getRoadList()) {
                            if (road.getIsDeleted().equals(MainetConstants.IsDeleted.NOT_DELETE)) {
                                rateMaster = null;
                                tempRate = (WaterRateMaster) actualRate.clone();
                                dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
                                rateMaster = WaterCommonUtility.populateLOINewWaterConnectionModel(tempRate, master,
                                        org);

                                /* Changes For Construction Cost */
                                /*
                                 * if(master.getTypeOfApplication().equals("T")) {
                                 * rateMaster.setFactor2(ApplicationSession.getInstance().getMessage( "water.temp")); } else {
                                 * rateMaster.setFactor2(ApplicationSession.getInstance().getMessage( "water.perm")); }
                                 * rateMaster.setFactor3( CommonMasterUtility.getNonHierarchicalLookUpObject(master.getCsReason(),
                                 * org).getDescLangFirst()); if(master.getConstructionCost()!=null) {
                                 * rateMaster.setChargeAmount(master.getConstructionCost()); }
                                 */
                                setRoadDiggingCharges(rateMaster, dependsOnRateMaster, road, master, org);
                                WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
                                rateMaster.setDischargeRate(dischargeRate);
                                if(master.getNocAppl() != null) {
                                	rateMaster.setFactor3(master.getNocAppl());
                                }
                                if(master.getFloorNo() != null && master.getFloorNo() > 0) {
                                	rateMaster.setFactor2(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getFloorNo(), org).getLookUpDesc());
                                }
                              //This NoOfDays using for build up area
                                if(master.getBuiltUpArea() != null) {
                                	rateMaster.setNoOfDays(master.getBuiltUpArea());
                                }else {
                                	rateMaster.setNoOfDays(0.00);
                                }
                              //This NoOfCopies using for estimation charge
                                if(master.getEstimateCharge() != null) {
                                	rateMaster.setNoOfCopies(master.getEstimateCharge().intValue());
                                }else {
                                	rateMaster.setNoOfCopies(0);
                                }
                                chargeModelList.add(rateMaster);

                            }
                        }
                    } else {
                    	
                    		// non-suda
//                   		 	tempRate = (WaterRateMaster) actualRate.clone();
//                            dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
//                            rateMaster = WaterCommonUtility.populateLOINewWaterConnectionModel(tempRate, master, org);
//                            rateMaster.setDischargeRate(dischargeRate);
//                            setsecurityDeposit(tempRate, master);
//                            WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
//                            chargeModelList.add(rateMaster);
                    	
                       
                    }
                } else {
                	if (actualRate.getTaxSubCategory().equals(subCategoryRRDesc) && CollectionUtils.isEmpty(master.getRoadList()))  { 
                    	//dont add road repair charge if road digging is not applicable
                    }
                	else {
                		tempRate = (WaterRateMaster) actualRate.clone();
                        dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
                        rateMaster = WaterCommonUtility.populateLOINewWaterConnectionModel(tempRate, master, org);

                        /* Changes For Construction Cost */

                        /*
                         * if(master.getTypeOfApplication().equals("T")) {
                         * rateMaster.setFactor2(ApplicationSession.getInstance().getMessage( "water.temp")); } else {
                         * rateMaster.setFactor2(ApplicationSession.getInstance().getMessage( "water.perm")); } rateMaster.setFactor3(
                         * CommonMasterUtility.getNonHierarchicalLookUpObject(master.getCsReason(), org).getDescLangFirst());
                         * if(master.getConstructionCost()!=null) { rateMaster.setChargeAmount(master.getConstructionCost()); }
                         */
                        rateMaster.setDischargeRate(dischargeRate);
                        setsecurityDeposit(tempRate, master);
                        WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
                        if(master.getFloorNo() != null && master.getFloorNo() > 0) {
                        	rateMaster.setFactor2(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getFloorNo(), org).getLookUpDesc());
                        }
                        if(master.getNocAppl() != null) {
                        	rateMaster.setFactor3(master.getNocAppl());
                        }
                      //This NoOfDays using for build up area
                        if(master.getBuiltUpArea() != null) {
                        	rateMaster.setNoOfDays(master.getBuiltUpArea());
                        }else {
                        	rateMaster.setNoOfDays(0.00);
                        }
                      //This NoOfCopies using for estimation charge
                        if(master.getEstimateCharge() != null) {
                        	rateMaster.setNoOfCopies(master.getEstimateCharge().intValue());
                        }else {
                        	rateMaster.setNoOfCopies(0);
                        }
                        chargeModelList.add(rateMaster);
                	}
                    
                }
            } else {
                chargeMap.put(actualRate.getTaxId(), 0.0);
            }
        }
        
        log.info("chargeModelList:" + chargeModelList);
        
        requestDTO.setDataModel(chargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
        final List<?> waterRateList = RestClient.castResponse(output, WaterRateMaster.class);
        WaterRateMaster loiCharges = null;
        Double baseRate = 0d;
        double amount;
        
        double roadDigCharges = 0.0d;
        boolean isSupervisionCharges = false;
        long supTaxId = 0;
        double supPercRt = 0.0d;
        long rdTaxId = 0;
        
        for (final Object rate : waterRateList) {

            loiCharges = (WaterRateMaster) rate;
            LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
                    PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);
            baseRate = WaterCommonUtility.getAndSetBaseRate(loiCharges.getRoadLength(), loiCharges, null,
                    taxLookup.getLookUpCode());
            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(loiCharges.getTaxId(), amount);
            
            String roadDiggingChrg = "Road Digging Charge";
            String supVisnChrg="Supervision Charge";
            
            
            log.info("Taxes Desc Charges found:" + loiCharges.getTaxSubCategory());
            
            if(loiCharges.getTaxSubCategory().toLowerCase().equals(supVisnChrg.toLowerCase())) 
            {
            	log.info("Supervision Charges found:");
            	
            	isSupervisionCharges = true;
            	supTaxId = loiCharges.getTaxId();
            	supPercRt=loiCharges.getPercentageRate();
            	
            	log.info("Supervision Charges " + supPercRt);
            }
            
            if(loiCharges.getTaxSubCategory().toLowerCase().equals(roadDiggingChrg.toLowerCase()))
            {
            	log.info("Road Digging Charges found:");
            	
            	roadDigCharges = amount;
            	
            	log.info("Road Digging Charges " + roadDigCharges);
            	
            	rdTaxId = loiCharges.getTaxId();
            	
            	log.info("rdTaxId:" + rdTaxId);
            }
        }
        
        log.info("isSupervisionCharges:" + isSupervisionCharges + " , supTaxId:" + supTaxId + ", supPercRt:" + supPercRt + ", rdTaxId:" + rdTaxId);
        
        log.info("chargeMap:" + chargeMap );
        
        if(isSupervisionCharges)
        {
        	if(chargeMap.get(rdTaxId)!=null) {
        	double roadDigChargesTmp = chargeMap.get(rdTaxId);
        	
        	roadDigChargesTmp = roadDigChargesTmp * (supPercRt / 100d);
        	
        	log.info("SuperVision Charges:" + roadDigChargesTmp);
        	
        	chargeMap.put(supTaxId, roadDigChargesTmp);
        	}
        }
        
        log.info("Modified chargeMap:" + chargeMap );
        
        return chargeMap;
        // [END]
    }

    /**
     * @param actualRate
     * @param master
     */
    private void setsecurityDeposit(final WaterRateMaster actualRate, final TbKCsmrInfoMH master) {
        actualRate.setConnectionSize(Double
                .valueOf(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getCsCcnsize()).getDescLangFirst()));
    }

    /**
     * @param tempRate
     * @param road
     * @param master
     */
    private void setRoadDiggingCharges(final WaterRateMaster tempRate, final WaterRateMaster dependsOnFactorMaster,
            final TbWtCsmrRoadTypes road, final TbKCsmrInfoMH master, final Organisation org) {
        LookUp roadLookup = null;
        if (road != null) {
            tempRate.setRoadLength(road.getCrtRoadUnits());
            roadLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(road.getCrtRoadTypes()), org);
            dependsOnFactorMaster.setRoadType(roadLookup.getDescLangFirst());
            dependsOnFactorMaster.setRoadLength(road.getCrtRoadUnits());

        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.NewWaterConnectionService#getRoadListById (long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<TbWtCsmrRoadTypes> getRoadListById(final TbKCsmrInfoMH master) {
        final List<TbWtCsmrRoadTypes> list = waterRepository.getRoadListById(master);
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.NewWaterConnectionService# getReconnectionRoadDiggingListByAppId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<RoadTypeDTO> getReconnectionRoadDiggingListByAppId(final Long appId, final Long orgId) {

        final List<TbWtCsmrRoadTypes> list = waterRepository.getReconnectionRoadDiggingListByAppId(appId, orgId);
        final List<RoadTypeDTO> roadTypeDTOs = waterServiceMapper.mapTbWtCsmrRoadTypesEntityToRoadTypeDTO(list);

        return roadTypeDTOs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.NewWaterConnectionService# saveReconnectionRoadDiggingDetails(java.util.List,
     * com.abm.mainetservice.web.common.bean.ScrutinyLableValueDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void saveReconnectionRoadDiggingDetails(final List<TbWtCsmrRoadTypes> csmrRoadTypesEntity,
            final ScrutinyLableValueDTO lableValueDTO) {

        saveScrutinyValue(lableValueDTO);
        waterRepository.saveReconnectionRoadDiggingDetails(csmrRoadTypesEntity);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.NewWaterConnectionService#
     * getwaterRecordsForBill(com.abm.mainetservice.water.bean. TbCsmrInfoDTO)
     */
    @Override
    @WebMethod(exclude = true)
    public List<TbCsmrInfoDTO> getwaterRecordsForBill(final TbCsmrInfoDTO entity, final String contype,
            final Long billfrequency, final String finYearId) {
        final List<TbKCsmrInfoMH> entityData = waterRepository.getwaterRecordsForBill(entity, contype);
        final List<TbCsmrInfoDTO> beans = new ArrayList<>();
        TbCsmrInfoDTO dto = null;
        if ((entityData != null) && !entityData.isEmpty()) {
            for (final TbKCsmrInfoMH tbwater : entityData) {
                dto = new TbCsmrInfoDTO();
                BeanUtils.copyProperties(tbwater, dto);
                dto.setPcFlg(MainetConstants.MENU.N);
                beans.add(dto);
            }
        }
        return beans;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.NewWaterConnectionService# getViewDetailsData(com.abm.mainetservice.water.bean.
     * NewWaterConnectionReqDTO)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getViewDetailsData", notes = "getViewDetailsData", response = Object.class)
    @Path("/getViewDetailsData")
    public NewWaterConnectionReqDTO getViewDetailsData(final NewWaterConnectionReqDTO requestDTO) {
        final Long orgId = requestDTO.getOrgId();
        final Long applicationId = requestDTO.getApplicationId();
        final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
        try {
            final TbCsmrInfoDTO csmrInfo = waterCommonService.getApplicantInformationById(applicationId, orgId);
            final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
            for (final AdditionalOwnerInfoDTO owner : csmrInfo.getOwnerList()) {

                if (!MainetConstants.YES.equals(owner.getIsDeleted())) {
                    tempOwnerList.add(owner);
                }
            }
            for (final TbKLinkCcnDTO link : csmrInfo.getLinkDetails()) {

                if (!MainetConstants.YES.equals(link.getIsDeleted())) {
                    tempLinkList.add(link);
                    requestDTO.setExistingConsumerNumber("Y");
                }
            }
            if (org.apache.commons.collections.CollectionUtils.isEmpty(tempLinkList)) {
                requestDTO.setExistingConsumerNumber("N");
            }
            requestDTO.setOwnerList(tempOwnerList);
            requestDTO.setLinkDetails(tempLinkList);
            csmrInfo.setOwnerList(null);
            csmrInfo.setRoadList(null);
            requestDTO.setCsmrInfo(csmrInfo);
            requestDTO.setPropertyNo(csmrInfo.getPropertyNo());
            requestDTO.setPlumberName(waterCommonService.getPlumberLicenceName(csmrInfo.getPlumId()));

            final List<CFCAttachment> documentList = iChecklistVerificationService.getDocumentUploaded(applicationId,
                    orgId);
            final List<DocumentDetailsVO> tempDocList = new ArrayList<>();
            DocumentDetailsVO document = null;
            for (final CFCAttachment doc : documentList) {
                document = new DocumentDetailsVO();
                document.setAttachmentId(doc.getAttId());
                document.setCheckkMANDATORY(doc.getClmStatus());
                document.setDoc_DESC_ENGL(doc.getClmDescEngl());
                document.setDoc_DESC_Mar(doc.getClmDesc());
                document.setDocumentName(doc.getAttFname());
                document.setUploadedDocumentPath(doc.getAttPath());
                document.setDocumentSerialNo(doc.getClmSrNo());
                tempDocList.add(document);

            }
            requestDTO.setDocumentList(tempDocList);
            final ApplicantDetailDTO appDTO = requestDTO.getApplicantDTO();
            final CFCApplicationAddressEntity cfcAddressEntity = cfcService.getApplicantsDetails(applicationId);
            appDTO.setFlatBuildingNo(cfcAddressEntity.getApaFlatBuildingNo());
            appDTO.setRoadName(cfcAddressEntity.getApaRoadnm());
            appDTO.setBuildingName(cfcAddressEntity.getApaBldgnm());
            appDTO.setBlockName(cfcAddressEntity.getApaBlockName());
            appDTO.setAreaName(cfcAddressEntity.getApaAreanm());
            appDTO.setVillageTownSub(cfcAddressEntity.getApaCityName());
            appDTO.setPinCode(String.valueOf(cfcAddressEntity.getApaPincode()));
            appDTO.setEmailId(cfcAddressEntity.getApaEmail());
            final TbCfcApplicationMstEntity cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationId,
                    orgId);
            appDTO.setApplicantFirstName(cfcEntity.getApmFname());
            appDTO.setApplicantLastName(cfcEntity.getApmLname());
            appDTO.setApplicantMiddleName(cfcEntity.getApmMname());
            appDTO.setApplicantTitle(cfcEntity.getApmTitle());
            appDTO.setDwzid1(csmrInfo.getCodDwzid1());
            appDTO.setDwzid2(csmrInfo.getCodDwzid2());
            if (csmrInfo.getCsUid() != null) {
                appDTO.setAadharNo(String.valueOf(csmrInfo.getCsUid()));
            }
            appDTO.setBplNo(csmrInfo.getBplNo());
            final Organisation org = new Organisation();
            org.setOrgid(requestDTO.getOrgId());
            final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, org);
            for (final LookUp lookUp : lookUps) {
                if ((cfcEntity.getApmSex() != null) && !cfcEntity.getApmSex().isEmpty()) {
                    if (lookUp.getLookUpCode().equals(cfcEntity.getApmSex())) {
                        appDTO.setGender(String.valueOf(lookUp.getLookUpId()));
                        break;
                    }
                }
            }
            requestDTO.setPropertyNo(csmrInfo.getPropertyNo());
        } catch (Exception e) {
            log.error("Exception ocours to getViewDetailsData() " + e);
            throw new FrameworkException("Exception occours in getViewDetailsData() method" + e);
        }

        return requestDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.NewWaterConnectionService#
     * getBillPrintingSearchDetail(com.abm.mainetservice.water.bean. TbCsmrInfoDTO, java.lang.String)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<WaterBillPrintingDTO> getBillPrintingSearchDetail(final TbCsmrInfoDTO entity, final String conType) {
        final List<WaterBillPrintingDTO> billMas = new ArrayList<>(0);
        try {
            final List<Object[]> result = waterRepository.getBillPrintingSearchDetail(entity, conType);
            WaterBillPrintingDTO bill = null;
            if ((result != null) && !result.isEmpty()) {
                for (final Object[] obj : result) {
                    bill = new WaterBillPrintingDTO();
                    bill.setBmIdno(Long.valueOf(obj[0].toString()));
                    bill.setCsIdn(Long.valueOf(obj[1].toString()));
                    bill.getWaterMas().setCsCcn(obj[2].toString());
                    if (obj[3] != null) {
                        bill.setBmNo(obj[3].toString());
                    }
                    if (obj[4] != null) {
                        bill.getWaterMas().setCsMeteredccn(Long.valueOf(obj[4].toString()));
                    }
                    if (obj[5] != null) {
                        bill.getWaterMas().setCsName(obj[5].toString());
                    }
                    if (obj[6] != null) {
                        bill.getWaterMas().setCsMname(obj[6].toString());
                    }
                    if (obj[7] != null) {
                        bill.getWaterMas().setCsLname(obj[7].toString());
                    }

                    billMas.add(bill);
                }
            }
        } catch (Exception e) {
            log.error("Exception ocours to getBillPrintingSearchDetail() " + e);
            throw new FrameworkException("Exception occours in getBillPrintingSearchDetail() method" + e);
        }

        return billMas;
    }

    @Transactional
    @WebMethod(exclude = true)
    public ChallanRegenerateDTO recalculateChallanAmount(final ChallanDetailsDTO challanDetail) {
        final ChallanRegenerateDTO regenerateDto = new ChallanRegenerateDTO();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbBillMas> entities = null;
        TbTaxMasEntity taxMas = new TbTaxMasEntity();
        final List<LookUp> taxCategory = CommonMasterUtility.getLevelData(PrefixConstants.WATERMODULEPREFIX.TAC,
                MainetConstants.NUMBERS.ONE, UserSession.getCurrent().getOrganisation());
        Long penaltyInterest = null;
        if ((taxCategory != null) && !taxCategory.isEmpty()) {
            for (final LookUp lookupid : taxCategory) {
                if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                    penaltyInterest = lookupid.getLookUpId();
                    break;
                }
            }
        }
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA,
                UserSession.getCurrent().getOrganisation());
        final List<TbTaxMasEntity> taxMasEntity = tbTaxMasService.getTaxMasterByTaxCategoryId(challanDetail.getDeptId(),
                penaltyInterest, orgId, chargeApplicableAt.getLookUpId());
        if ((taxMasEntity != null) && !taxMasEntity.isEmpty()) {
            taxMas = taxMasEntity.get(0);
        }
        TbBillMas currentBill = null;
        for (final TbBillMas dto : entities) {
            if (dto.getBmToatlInt() > 0d) {
                boolean isTaxAvail = false;
                for (final TbBillDet det : dto.getTbWtBillDet()) {
                    if (det.getTaxId().equals(taxMas.getTaxId())) {
                        final double amount = dto.getBmToatlInt() - det.getBdCurTaxamt();
                        final double amountAdded = amount;
                        det.setBdCurTaxamt(dto.getBmToatlInt());
                        det.setBdCurBalTaxamt(amountAdded + det.getBdCurBalTaxamt());
                        isTaxAvail = true;
                    }
                }
                if (!isTaxAvail) {
                    final TbBillDet billDet = new TbBillDet();
                    billDet.setBdCurBalTaxamt(dto.getBmToatlInt());
                    billDet.setBdCurTaxamt(dto.getBmToatlInt());
                    billDet.setTaxId(taxMas.getTaxId());
                    billDet.setTaxCategory(taxMas.getTaxCategory1());
                    billDet.setTaxSubCategory(taxMas.getTaxCategory2());
                    billDet.setCollSeq(taxMas.getCollSeq());
                    billDet.setOrgid(orgId);
                    billDet.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                    billDet.setLmoddate(new Date());
                    billDet.setLangId(MainetConstants.ENGLISH);
                    billDet.setLgIpMac(Utility.getMacAddress());
                    dto.getTbWtBillDet().add(billDet);
                }
            }
            currentBill = tbWtBillMasService.update(dto);
        }
        regenerateDto.setAmountToPay(currentBill.getBmTotalOutstanding());
        for (final TbBillDet det : currentBill.getTbWtBillDet()) {
            final String taxCode = CommonMasterUtility
                    .getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
                    .getLookUpCode();
            if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)
                    && !taxCode.equals(PrefixConstants.TAX_CATEGORY.REBATE)) {
                regenerateDto.getFeeIds().put(det.getTaxId(), det.getBdCurTaxamt());
                regenerateDto.getBillDetIds().put(det.getTaxId(), det.getBdBilldetid());
            }
        }
        return regenerateDto;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String setConnectionNoDetails(final long applicationid, final long orgId, final long serviceId,
            final long empId) {
        final TbKCsmrInfoMH entity = waterRepository.getApplicantInformationById(applicationid, orgId);
        if ((entity.getCsCcn() == null) || MainetConstants.CommonConstants.BLANK.equals(entity.getCsCcn())) {
            final String connectionNo = generateWaterConnectionNumber(applicationid, serviceId, orgId, entity);

            if (entity.getCsMeteredccn() == null) {
                final Long nonMeterId = CommonMasterUtility.getValueFromPrefixLookUp(
                        PrefixConstants.NewWaterServiceConstants.NO, PrefixConstants.NewWaterServiceConstants.WMN,
                        UserSession.getCurrent().getOrganisation()).getLookUpId();
                entity.setCsMeteredccn(nonMeterId);
            }
            entity.setUpdatedBy(empId);
            entity.setUpdatedDate(new Date());
            entity.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getLgIpMac());
            entity.setCsCcn(connectionNo);
            final TbCfcApplicationMstEntity cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationid,
                    orgId);
            cfcEntity.setAppAppRejBy(empId);
            cfcEntity.setAppAppRejDate(new Date());
            cfcEntity.setApmAppRejFlag(MainetConstants.Common_Constant.ACTIVE_FLAG);
            cfcEntity.setUpdatedBy(empId);
            cfcEntity.setUpdatedDate(new Date());
            cfcEntity.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getLgIpMac());
            newWaterRepository.updateCsmrInfo(entity);
            applicationService.updateApplication(cfcEntity);
            final CsmrInfoHistEntity hist = new CsmrInfoHistEntity();
            auditService.createHistory(entity, hist);
        }
        return entity.getCsCcn() + MainetConstants.operator.COMMA + entity.getPlumId();

    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchBillsForDistributionEntry(final WardZoneBlockDTO entity, final String billCcnType,
            final Long distriutionType, final String connectionNo, final long orgId) {
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<Object[]> bill = null;
        final String lookupCode = CommonMasterUtility.getNonHierarchicalLookUpObject(distriutionType, org)
                .getLookUpCode();
        if (MainetConstants.BILL_DISTRIBUTION.equals(lookupCode)) {
            bill = newWaterRepository.fetchBillsForDistributionEntry(entity, billCcnType, distriutionType, connectionNo,
                    orgId);
        } else {
            Long noticeType = null;
            if (MainetConstants.DEMAND_DISTRIBUTION.equals(lookupCode)) {
                noticeType = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.DemandNotice.DEMAND_NOTICE,
                        MainetConstants.DemandNotice.DEMAND_TYPE).getLookUpId();
            } else if (MainetConstants.FINAL_DEMAND_DISTRIBUTION.equals(lookupCode)) {
                noticeType = CommonMasterUtility
                        .getValueFromPrefixLookUp(MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE,
                                MainetConstants.DemandNotice.DEMAND_TYPE)
                        .getLookUpId();
            }

            bill = newWaterRepository.fetchNoticesForDistributionEntry(entity, billCcnType, connectionNo, orgId,
                    noticeType);
        }
        final List<TbBillMas> billDto = new ArrayList<>();
        if ((bill != null) && !bill.isEmpty()) {
            TbBillMas bilMas = null;
            for (final Object[] obj : bill) {
                bilMas = new TbBillMas();
                bilMas.setBmIdno(Long.valueOf(obj[0].toString()));
                bilMas.setCsIdn(Long.valueOf(obj[1].toString()));
                bilMas.setWtV1(obj[2].toString());
                if (obj[3] != null) {
                    bilMas.setBmNo(obj[3].toString());
                }
                if (obj[4] != null) {
                    bilMas.setBmMeteredccn(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(Long.valueOf(obj[4].toString()), org).getLookUpCode());
                }
                bilMas.setBmCcnOwner(
                        (obj[5] != null ? obj[5].toString() + MainetConstants.WHITE_SPACE : MainetConstants.BLANK)
                                + (obj[6] != null ? obj[6].toString() + MainetConstants.WHITE_SPACE
                                        : MainetConstants.BLANK)
                                + (obj[7] != null ? obj[7].toString() : MainetConstants.BLANK));
                if (obj[8] != null) {
                    bilMas.setBmBilldt((Date) obj[8]);
                }
                billDto.add(bilMas);
            }
        }
        return billDto;
    }

    /* method modified by @Sadik.shaikh to generate the custom sequence number */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String generateWaterConnectionNumber(final Long applicationId, final Long serviceId, final Long orgId,
            TbKCsmrInfoMH master) {
        SequenceConfigMasterDTO configMasterDTO = null;

        Long deptId = departmentserveise.getDepartmentIdByDeptCode(MainetConstants.DeptCode.WATER,
                PrefixConstants.STATUS_ACTIVE_PREFIX);

        configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptId,
                MainetConstants.CommonMasterUi.TB_CSMR_INFO, MainetConstants.CommonMasterUi.CS_CCN);

        if (configMasterDTO.getSeqConfigId() == null) {
            final Long seq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DeptCode.WATER,
                    MainetConstants.CommonMasterUi.TB_CSMR_INFO, MainetConstants.CommonMasterUi.CS_CCN, orgId,
                    MainetConstants.CommonConstants.C, null);

            final String num = seq.toString();
            final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.CD, Integer.parseInt(num));
            final String orgid = orgId.toString();
            final String appNumber = orgid.concat(paddingAppNo);
            return appNumber;
        } else {
            CommonSequenceConfigDto levelDto = new CommonSequenceConfigDto();
            if (master.getTrmGroup1() != null) {
                levelDto.setUsageId1(master.getTrmGroup1());
                levelDto.setUsageCtrlId(1L);
            }
            if (master.getTrmGroup2() != null) {
                levelDto.setUsageId1(master.getTrmGroup1());
                levelDto.setUsageCtrlId(0L);
            }
            
            //D#148736 In KDMC - Sequence start from Ward
            Organisation org = new Organisation();
            org.setOrgid(orgId);
            if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
            	levelDto.setLevel1Id(master.getCodDwzid1());
            }
            
            levelDto.setLevel2Id(master.getCodDwzid2());
            if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
            	levelDto.setLevel3Id(master.getCodDwzid3());
            }
            levelDto.setLevel4Id(master.getCodDwzid4());
            levelDto.setLevel5Id(master.getCodDwzid5());
            levelDto.setServiceCode(MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION);
            return seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, levelDto);
        }
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void saveWaterDataEntry(NewWaterConnectionReqDTO newWaterConnectionReqDTO,
            MeterDetailsEntryDTO meterDetailsEntryDTO, List<TbBillMas> bills) {
        Organisation org = new Organisation();
        org.setOrgid(newWaterConnectionReqDTO.getOrgId());
        final TbKCsmrInfoMH master = waterServiceMapper
                .mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(newWaterConnectionReqDTO.getCsmrInfo());

        master.setPropertyNo(newWaterConnectionReqDTO.getPropertyNo());
        if (StringUtils.isBlank(newWaterConnectionReqDTO.getPropertyNo())) {
            master.setCsOname(master.getCsName());
        }
        master.setCsCcn(generateWaterConnectionNumber(null, null, newWaterConnectionReqDTO.getOrgId(), master));
        newWaterConnectionReqDTO.getCsmrInfo().setCsCcn(master.getCsCcn());
        newWaterConnectionReqDTO.setConsumerNo(master.getCsCcn());
        master.setOrgId(newWaterConnectionReqDTO.getOrgId());
        master.setLgIpMac(newWaterConnectionReqDTO.getLgIpMac());
        master.setUserId(newWaterConnectionReqDTO.getUserId());
        master.setLmodDate(new Date());
        master.setPcFlg("Y");
        master.setCsIsBillingActive("A");
        if (master.getLinkDetails() != null) {
            for (final TbKLinkCcn link : master.getLinkDetails()) {
                link.setCsIdn(master);
            }
        }
        if( Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
        	master.setHouseNumber(newWaterConnectionReqDTO.getCsmrInfo().getHouseNumber());
        }
   
        waterRepository.saveCsmrInfo(master);
     

        String metertype = CommonMasterUtility
                .getNonHierarchicalLookUpObject(newWaterConnectionReqDTO.getCsmrInfo().getCsMeteredccn(), org)
                .getLookUpCode();
        if ("MTR".equals(metertype)) {
            meterDetailsEntryDTO.setOrgId(newWaterConnectionReqDTO.getOrgId());
            meterDetailsEntryDTO.setUserId(newWaterConnectionReqDTO.getUserId());
            meterDetailsEntryDTO.setCsIdn(master.getCsIdn());
            meterDetailsEntryDTO.setLangId((int) MainetConstants.ENGLISH);
            meterDetailsService.saveMeterDataEntryFormData(meterDetailsEntryDTO, master);
        }
        if (bills != null && !bills.isEmpty()) {
            List<TbBillMas> waterbills = billMasterCommonService.generateBillForDataEntry(bills, org);
            waterbills.forEach(bill ->{
            	bill.setOrgid(newWaterConnectionReqDTO.getOrgId());
            });
            billMasterCommonService.taxCarryForward(waterbills, org.getOrgid());
            final List<TbWtBillMasEntity> billGenerated = new ArrayList<>(0);
            waterbills.forEach(bill -> {
                bill.setIntFrom(bill.getBmFromdt());
                bill.setIntTo(bill.getBmDuedate());
                bill.setCsIdn(master.getCsIdn());
                bill.setOrgid(newWaterConnectionReqDTO.getOrgId());
                bill.setLgIpMac(newWaterConnectionReqDTO.getLgIpMac());
                bill.setUserId(newWaterConnectionReqDTO.getUserId());
                bill.setLmoddate(new Date());
                bill.setBmPaidFlag(MainetConstants.FlagN);
                bill.setGenFlag(MainetConstants.FlagY);
                billGenerated.add(billMasterService.create(bill));
            });
            billMasterJpaRepository.save(billGenerated);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<TbCsmrInfoDTO> getAllConnectionMasterForBillScheduler(long orgid) {
        List<TbCsmrInfoDTO> result = new ArrayList<>(0);
        List<TbKCsmrInfoMH> allConnectionData = new ArrayList<>(0);
        List<TbKCsmrInfoMH> entityMeter = waterRepository.getAllConnectionMasterForBillSchedulerMeter(orgid);
        List<TbKCsmrInfoMH> entityNonMeter = waterRepository.getAllConnectionMasterForBillSchedulerNonMeter(orgid);
        if (entityMeter != null && !entityMeter.isEmpty()) {
            allConnectionData.addAll(entityMeter);
        }
        if (entityNonMeter != null && !entityNonMeter.isEmpty()) {
            allConnectionData.addAll(entityNonMeter);
        }

        if (allConnectionData != null && !allConnectionData.isEmpty()) {
            allConnectionData.forEach(data -> {
                result.add(waterServiceMapper.maptbKCsmrInfoMHToTbKCsmrInfoDTO(data));
            });
        }
        return result;
    }

    @Override
    @WebMethod(exclude = true)

    public void findNoOfDaysCalculation(TbCsmrInfoDTO csmrDto, final Organisation organisation) {

        String lookUpId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TCD, "NOD", organisation)
                .getOtherField();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = utilityService.convertDateToDDMMYYYY(csmrDto.getFromDate());
        String date2 = utilityService.convertDateToDDMMYYYY(csmrDto.getToDate());
        /* Long noday1=csmrDto.getNumberOfDays(); */
        if (date1 != null && !date1.isEmpty() && date2 != null && !date2.isEmpty()) {
            try {
                Date fromDate = myFormat.parse(date1);
                Date toDate = myFormat.parse(date2);
                Long diff = toDate.getTime() - fromDate.getTime();
                Long diffDays = diff / (60 * 60 * 1000 * 24);
                csmrDto.setNumberOfDays(diffDays);
                Long maxNumberOfDay = Long.parseLong(lookUpId);
                csmrDto.setMaxNumberOfDay(maxNumberOfDay);
            } catch (Exception e) {
                log.error("Exception In Calculation Of Number Of Days.");
                throw new FrameworkException("NOD prefix not defined", e);
            }
        }
    }

    @Override
    public TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO) {
        TbCsmrInfoDTO infoDTO = new TbCsmrInfoDTO();
        PropertyDetailDto detailDTO = null;
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(requestDTO.getPropertyNo());
        propInputDTO.setOrgId(requestDTO.getOrgId());
        Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgId());
        
        ResponseEntity<?> responseEntity = null;
        if(requestDTO.getPropertyNo()!=null && !requestDTO.getPropertyNo().isEmpty() && !Utility.isEnvPrefixAvailable(organisation, "PIN")) {
        	  responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                    ServiceEndpoints.PROP_BY_PROP_ID);
        }
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

            detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
            log.info("PropertyDetailDto formed is " + detailDTO.toString());
            // #109242
            if (!detailDTO.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.FAIL)) {
                infoDTO.setCsOname(detailDTO.getPrimaryOwnerName());
                infoDTO.setCsOcontactno(detailDTO.getPrimaryOwnerMobNo());
                infoDTO.setTotalOutsatandingAmt(detailDTO.getTotalOutsatandingAmt());
                infoDTO.setPropertyNo(detailDTO.getPropNo());
                if (detailDTO.getTotalArv() != 0) {
                    infoDTO.setArv(detailDTO.getTotalArv());
                }
                if (detailDTO.getOwnerEmail() != null) {
                    infoDTO.setCsOEmail(detailDTO.getOwnerEmail());
                }
                if (detailDTO.getPinCode() != null) {
                    infoDTO.setOpincode(detailDTO.getPinCode().toString());
                }
                infoDTO.setPropertyUsageType(detailDTO.getUasge());
                if (detailDTO.getOccupancyType() != null) {
                    infoDTO.setOccupancyType(detailDTO.getOccupancyType());
                }
                infoDTO.setCsOadd(detailDTO.getAddress());
                // infoDTO.setCsOrdcross(detailDTO.getLocation());
                if (detailDTO.getGender() != null && !detailDTO.getGender().isEmpty()) {
                    Long lookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(detailDTO.getGender(),
                            MainetConstants.GENDER, requestDTO.getOrgId());
                    if (lookupId != null && lookupId != 0L) {
                        LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(lookupId, MainetConstants.GENDER,
                                requestDTO.getOrgId());
                        infoDTO.setCsOGender(lookUp.getLookUpId());
                    }
                }
                if (StringUtils.isNotEmpty(detailDTO.getGardianOwnerName())) {
                    infoDTO.setCsPowner(detailDTO.getGardianOwnerName());
                }
            } else {
                infoDTO = null;
                log.info("Problem while getting property details by property Number " + responseEntity);
            }
        }

        else {
            infoDTO = null;
            log.info("Problem while getting property by property Number " + responseEntity);
        }

        return infoDTO;
    }

    @Override
    @Transactional
    public void updateWaterDataEntry(NewWaterConnectionReqDTO newWaterConnectionReqDTO,
            MeterDetailsEntryDTO meterDetailsEntryDTO, List<TbBillMas> list) {
        Organisation org = new Organisation();
    
        org.setOrgid(newWaterConnectionReqDTO.getOrgId());
        //final TbKCsmrInfoMH master = new TbKCsmrInfoMH();
        final TbKCsmrInfoMH master = waterServiceMapper
                .mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(newWaterConnectionReqDTO.getCsmrInfo());

        //BeanUtils.copyProperties(newWaterConnectionReqDTO.getCsmrInfo(), master);
        
        master.setPropertyNo(newWaterConnectionReqDTO.getCsmrInfo().getPropertyNo());
        master.setCsIdn(newWaterConnectionReqDTO.getCsmrInfo().getCsIdn());
        master.setCsCcn(newWaterConnectionReqDTO.getCsmrInfo().getCsCcn());
        newWaterConnectionReqDTO.getCsmrInfo().setCsCcn(master.getCsCcn());
        newWaterConnectionReqDTO.setConsumerNo(master.getCsCcn());
        master.setOrgId(newWaterConnectionReqDTO.getOrgId());
        master.setLgIpMac(newWaterConnectionReqDTO.getLgIpMac());
        master.setUserId(newWaterConnectionReqDTO.getUserId());
        master.setLmodDate(new Date());
        master.setPcFlg("Y");
        master.setCsIsBillingActive("A");
        if (master.getLinkDetails() != null) {
            for (final TbKLinkCcn link : master.getLinkDetails()) {
                link.setCsIdn(master);
            }
        }
        if( Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
        	master.setHouseNumber(newWaterConnectionReqDTO.getCsmrInfo().getHouseNumber());
        }
   
        waterRepository.updateCsmrInfo(master);

        String metertype = CommonMasterUtility
                .getNonHierarchicalLookUpObject(newWaterConnectionReqDTO.getCsmrInfo().getCsMeteredccn(), org)
                .getLookUpCode();
        if ("MTR".equals(metertype)) {
            meterDetailsEntryDTO.setOrgId(newWaterConnectionReqDTO.getOrgId());
            meterDetailsEntryDTO.setUserId(newWaterConnectionReqDTO.getUserId());
            meterDetailsEntryDTO.setCsIdn(master.getCsIdn());
            meterDetailsEntryDTO.setLangId((int) MainetConstants.ENGLISH);
            meterDetailsService.saveMeterDataEntryFormData(meterDetailsEntryDTO, master);
        }

        if (list != null && !list.isEmpty()) {
            List<TbBillMas> waterbills = billMasterCommonService.generateBillForDataEntry(list, org);
            final List<TbWtBillMasEntity> billGenerated = new ArrayList<>(0);
            waterbills.forEach(bill -> {
                bill.setIntFrom(bill.getBmFromdt());
                bill.setIntTo(bill.getBmTodt());
                bill.setCsIdn(master.getCsIdn());
                bill.setOrgid(newWaterConnectionReqDTO.getOrgId());
                bill.setLgIpMac(newWaterConnectionReqDTO.getLgIpMac());
                bill.setUserId(newWaterConnectionReqDTO.getUserId());
                bill.setLmoddate(new Date());
                bill.setBmPaidFlag("N");
                bill.setGenFlag(MainetConstants.FlagY);
                billGenerated.add(billMasterService.create(bill));
            });
            billMasterJpaRepository.save(billGenerated);
     }
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalSearchCount(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        return waterRepository.getTotalSearchCount(searchDTO, pagingDTO, gridSearchDTO);
    }

   
	@Override
    @Transactional
    public List<WaterDataEntrySearchDTO> searchConnectionDetails(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        List<WaterDataEntrySearchDTO> searchList = new ArrayList<>(0);
        try {
            List<TbKCsmrInfoMH> masterList = waterRepository.searchConnectionForView(searchDTO, pagingDTO,
                    gridSearchDTO);
            final Organisation org = new Organisation();
            for (TbKCsmrInfoMH master : masterList) {
                if (StringUtils.isNotBlank(master.getCsCcn())) {
                    WaterDataEntrySearchDTO search = new WaterDataEntrySearchDTO();
                    search.setRowId(String.valueOf(master.getCsIdn()));
                    search.setCodDwzid1(master.getCodDwzid1());
                    search.setCodDwzid2(master.getCodDwzid2());
                    search.setCodDwzid3(master.getCodDwzid3());
                    search.setCodDwzid4(master.getCodDwzid4());
                    search.setCodDwzid5(master.getCodDwzid5());
                    search.setCsAdd(master.getCsAdd());
                    search.setCsCcn(master.getCsCcn());
                    if (StringUtils.isNotBlank(master.getCsContactno())) {
                        search.setCsContactno(master.getCsContactno());
                    } else {
                        search.setCsContactno(master.getCsOcontactno());
                    }
                    search.setCsName(master.getCsName());
                    search.setPropertyNo(master.getPropertyNo());
                    search.setOrgId(master.getOrgId());
                    // Defect:-117766-Guardian Details Need To Show
                    
					NewWaterConnectionReqDTO reqDTO = new NewWaterConnectionReqDTO();
					reqDTO.setOrgId(search.getOrgId());
					if (search.getPropertyNo() != null)
						reqDTO.setPropertyNo(search.getPropertyNo());
					if (reqDTO != null && reqDTO.getPropertyNo() != null && reqDTO.getOrgId() != null) {
						String ownName = getPropertyGurdianName(reqDTO);
						
							if (StringUtils.isNotEmpty(ownName))
								search.setGardianOwnerName(ownName);
						}
					
                     
                    // US#149164
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
						if (master != null && master.getCodDwzid2() != null ) {
								search.setWard(CommonMasterUtility.getHierarchicalLookUp(master.getCodDwzid2(), master.getOrgId()).getDescLangFirst());
						}
						if (master != null && master.getCodDwzid3() != null) {
								search.setZone(CommonMasterUtility.getHierarchicalLookUp(master.getCodDwzid3(), master.getOrgId()).getDescLangFirst());
						}
						KDMCWaterDetailsResponseDTO totalWaterOutstandingAmount = getTotalWaterOutstandingAmount(master.getCsCcn(),
								UserSession.getCurrent().getOrganisation().getOrgid());
						if(totalWaterOutstandingAmount!=null) {
							search.setPayableAmount(String.valueOf("₹ "+totalWaterOutstandingAmount.getTotalActPayAmt()));
						}
					}
					
					searchList.add(search);
                }

            }
        } catch (Exception e) {
            log.error("Exception ocours to searchConnectionDetails() " + e);
            throw new FrameworkException("Exception occours in searchConnectionDetails() method" + e);
        }

        return searchList;
    }

    @Override
    @Consumes("application/json")
    @Transactional
    @GET
    @ApiOperation(value = "getConnectionDetails", notes = "getConnectionDetails", response = Object.class)
    @Path("/getConnectionDetails/csIdn/{csIdn}")
    public TbCsmrInfoDTO getConnectionDetailsById(@PathParam("csIdn") Long csIdn) {
        TbKCsmrInfoMH master = null;
        try {
            master = waterRepository.getConnectionDetailsById(csIdn);
        } catch (Exception e) {
            log.error("Exception ocours to getConnectionDetailsById() " + e);
            throw new FrameworkException("Exception occours in getConnectionDetailsById() method" + e);
        }

        return waterServiceMapper.maptbKCsmrInfoMHToTbKCsmrInfoDTO(master);
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getPlumberList", notes = "getPlumberList", response = Object.class)
    @Path("/getPlumberList/orgId/{orgId}")
    public List<PlumberDTO> listofplumber(@PathParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
        List<PlumberDTO> list = new ArrayList<>();
        try {
            List<PlumberMaster> masList = plumberMasterRepository.getAllPlumber(orgId);
            if (!CollectionUtils.isEmpty(masList)) {
                masList.forEach(obj -> {
                    PlumberDTO dto = new PlumberDTO();
                    dto.setPlumberId(obj.getPlumId());
                    dto.setPlumberFullName(obj.getPlumFname() + " " + obj.getPlumLname());
                    dto.setPlumberFName(obj.getPlumFname());
                    dto.setPlumberMName(obj.getPlumMname());
                    dto.setPlumberLName(obj.getPlumLname());
                    list.add(dto);
                });
            }
        } catch (Exception e) {
            log.error("Exception ocours to listofplumber() " + e);
            throw new FrameworkException("Exception occours in listofplumber() method" + e);
        }

        return list;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getAllActiveConnectionList", notes = "getAllActiveConnectionList", response = Object.class)
    @Path("/getAllActiveConnectionList/orgId/{orgId}/refNo/{refNo}")
    public List<WaterDashboardDTO> getAllConnectionByMobileNo(@PathParam("refNo") String refNo,
            @PathParam(MainetConstants.Common_Constant.ORGID) Long orgid) {
        List<WaterDashboardDTO> result = new ArrayList<>();
        try {
            List<TbKCsmrInfoMH> list = waterRepository.getAllConnectionMasterByMoblieNo(refNo, orgid);
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(data -> {
                    WaterDashboardDTO flowDto = new WaterDashboardDTO();
                    flowDto.setCsIdn(data.getCsIdn());
                    flowDto.setCsCcn(data.getCsCcn());
                    flowDto.setApplicationNo(data.getApplicationNo());
                    flowDto.setCsApldate(data.getCsApldate());
                    flowDto.setDeptShortName("WT");
                    TbDepartment dept = departmentService.findDeptByCode(orgid, "A", "WT");
                    if (dept != null)
                        flowDto.setDeptName(dept.getDpDeptdesc());
                    result.add(flowDto);
                });
            }
        } catch (Exception e) {
            log.error("Exception ocours to getAllConnectionByMobileNo() " + e);
            throw new FrameworkException("Exception occours in getAllConnectionByMobileNo() method" + e);
        }

        return result;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getConnectionDetailsByConnNo", notes = "getConnectionDetailsByConnNo", response = Object.class)
    @Path("/getConnectionDetailsByConnNo/csCcnNo/{csCcnNo}/orgId/{orgId}")
    public TbCsmrInfoDTO fetchConnectionDetailsByConnNo(@PathParam("csCcnNo") String csCcnNo,
            @PathParam("orgId") Long orgid) {
        TbCsmrInfoDTO flowDto = new TbCsmrInfoDTO();
        try {
            final TbKCsmrInfoMH data = waterRepository.fetchConnectionDetails(csCcnNo, orgid, MainetConstants.FlagA);
            if (data != null) {
                flowDto = new TbCsmrInfoDTO();
                flowDto.setCsIdn(data.getCsIdn());
                flowDto.setCsCcn(data.getCsCcn());
                flowDto.setCsCcnsize(data.getCsCcnsize());
                flowDto.setCsNooftaps(data.getCsNooftaps());
                flowDto.setCodDwzid1(data.getCodDwzid1());
                flowDto.setCodDwzid2(data.getCodDwzid2());
                flowDto.setCodDwzid3(data.getCodDwzid3());
                flowDto.setCodDwzid4(data.getCodDwzid4());
                flowDto.setCodDwzid5(data.getCodDwzid5());
                flowDto.setTrmGroup1(data.getTrmGroup1());
                flowDto.setCsAdd(data.getCsAdd());
                
                // new fields to be added
                flowDto.setCsName(data.getCsName());
                flowDto.setCsTitle(data.getCsTitle());
                if (null != data.getCsOGender() && data.getCsOGender() > 0) {
                flowDto.setGenderDesc((CommonMasterUtility.getHierarchicalLookUp(data.getCsOGender(), data.getOrgId()).getDescLangFirst()));
                }
                flowDto.setCsPinCode(data.getCsCpinCode());
                //new fields to be added
                
                if (StringUtils.isNotBlank(data.getCsOflatno())) {
                    flowDto.setCsOflatno(data.getCsOflatno());
                }
                if (StringUtils.isNotBlank(data.getCsOldccn())) {
                    flowDto.setCsOldccn(data.getCsOldccn());
                }
                if (data.getCsMeteredccn() != null) {
                    flowDto.setCsMeteredccn(data.getCsMeteredccn());
                }
                // Defect #127039 set mobile number
                if (StringUtils.isNotBlank(data.getCsContactno())) {
                    flowDto.setCsContactno(data.getCsContactno());
                }
                // #131368 set connection status 
                if (data.getCsCcnstatus() != null) {
                	flowDto.setCsCcnstatus(data.getCsCcnstatus());
                }
                List<TbBillMas> bill = billMasterService.getBillMasterListByUniqueIdentifier(data.getCsIdn(), orgid);
                if (!bill.isEmpty()) {
                    flowDto.setCcnOutStandingAmt(Double.toString(bill.get(bill.size() - 1).getBmTotalOutstanding()));
                } else {
                    flowDto.setCcnOutStandingAmt("0");
                }
            } else {
                flowDto.setCsIdn(0);
            }
        } catch (Exception e) {
            log.error("Exception ocours to fetchConnectionDetailsByConnNo() " + e);
            throw new FrameworkException("Exception occours in fetchConnectionDetailsByConnNo() method" + e);
        }

        return flowDto;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void saveIllegalConnectionNoticeGeneration(TbCsmrInfoDTO infoDto) {
        try {
            final TbKCsmrInfoMH master = waterServiceMapper.mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(infoDto);
            master.setLmodDate(new Date());
            master.setDistribution(null);
            master.setRoadList(null);
            if (master.getCsIdn() == 0)
                waterRepository.saveCsmrInfo(master);
            else
                waterRepository.updateCsmrInfo(master);
            final CsmrInfoHistEntity hist = new CsmrInfoHistEntity();
            auditService.createHistory(master, hist);
        } catch (Exception e) {
            log.error("Exception ocours to saveIllegalConnectionNoticeGeneration() " + e);
            throw new FrameworkException("Exception occours in saveIllegalConnectionNoticeGeneration() method" + e);
        }
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String generateIllegalConnectionNoticeNumber(Long orgId) {
        String initNo = null;
        try {
            // tender initiation date is current date so finding financial year for current
            // date.
            FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
            if (financiaYear != null) {

                // get financial year from date & end date and generate financial year as like:

                String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(),
                        financiaYear.getFaToDate());

                // generate sequence number.
                final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DeptCode.WATER,
                        MainetConstants.CommonMasterUi.TB_CSMR_INFO, "CS_ILLEGAL_NOTICE_NO", orgId,
                        MainetConstants.FlagC, financiaYear.getFaYear());

                // generate Illegal Connection Notice Number.
                initNo = "WT" + MainetConstants.WINDOWS_SLASH
                        + String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)
                        + MainetConstants.WINDOWS_SLASH + finacialYear;

            }
        } catch (Exception ex) {
            throw new FrameworkException("Exception occured while generateIllegalConnectionNoticeNumber() " + ex);
        }
        return initNo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbCsmrInfoDTO> getAllIllegalConnectionNotice(WaterDataEntrySearchDTO searchDto) {
        List<TbCsmrInfoDTO> result = new ArrayList<>();
        try {
            List<TbKCsmrInfoMH> list = waterRepository.getAllIllegalConnectionNotice(searchDto);
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(data -> {
                    TbCsmrInfoDTO flowDto = new TbCsmrInfoDTO();
                    flowDto.setCsIdn(data.getCsIdn());
                    flowDto.setCsIllegalNoticeNo(data.getCsIllegalNoticeNo());
                    flowDto.setCsOcontactno(data.getCsOcontactno());
                    flowDto.setCsOname(data.getCsOname());
                    flowDto.setCsOadd(data.getCsOadd());
                    result.add(flowDto);
                });
            }
        } catch (Exception e) {
            log.error("Exception ocours to getAllIllegalConnectionNotice() " + e);
            throw new FrameworkException("Exception occours in getAllIllegalConnectionNotice() method" + e);
        }

        return result;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getConnectionByNoticeNo", notes = "getConnectionByNoticeNo", response = Object.class)
    @Path("/getConnectionByNoticeNo")
    public TbCsmrInfoDTO fetchConnectionByIllegalNoticeNo(@RequestBody final TbCsmrInfoDTO infoDto) {
        TbCsmrInfoDTO flowDto = new TbCsmrInfoDTO();
        try {
            final TbKCsmrInfoMH data = waterRepository.fetchConnectionByIllegalNoticeNo(infoDto.getCsIllegalNoticeNo(),
                    infoDto.getOrgId());
            if (data != null) {
                flowDto = waterServiceMapper.maptbKCsmrInfoMHToTbKCsmrInfoDTO(data);
            } else {
                flowDto.setCsIdn(0);
            }
        } catch (Exception e) {
            log.error("Exception ocours to fetchConnectionByIllegalNoticeNo() " + e);
            throw new FrameworkException("Exception occours in fetchConnectionByIllegalNoticeNo() method" + e);
        }

        return flowDto;
    }

    @Override
    @POST
    @Path("/saveIllegalToLegalConnectionApplication")
    @Transactional
    public NewWaterConnectionResponseDTO saveIllegalToLegalConnectionApplication(NewWaterConnectionReqDTO requestDTO) {

        final Long orgId = requestDTO.getOrgId();
        final NewWaterConnectionResponseDTO waterDTO = new NewWaterConnectionResponseDTO();
        try {
            final TbKCsmrInfoMH master = waterServiceMapper
                    .mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(requestDTO.getCsmrInfo());
            final ApplicantDetailDTO applicantDetailDto = requestDTO.getApplicantDTO();
            applicantDetailDto.setOrgId(orgId);
            master.setUpdatedBy(requestDTO.getUserId());
            master.setUpdatedDate(new Date());

            master.setPropertyNo(requestDTO.getPropertyNo());
            if (waterDTO.getErrorList().isEmpty()) {
                try {
                    if (requestDTO.isFree()) {
                        requestDTO.setPayStatus("F");
                    }
                    final Long applicationId = applicationService.createApplication(requestDTO);
                    master.setApplicationNo(applicationId);
                    waterDTO.setApplicationNo(applicationId);
                    requestDTO.setApplicationId(applicationId);
                    if (master.getOwnerList() != null) {
                        for (final AdditionalOwnerInfo owner : master.getOwnerList()) {
                            owner.setCsIdn(master);
                        }
                    }
                    if (master.getLinkDetails() != null) {
                        for (final TbKLinkCcn link : master.getLinkDetails()) {
                            link.setCsIdn(master);
                        }
                    }

                    master.setDistribution(null);
                    master.setRoadList(null);
                    waterRepository.updateCsmrInfo(master);
                    final CsmrInfoHistEntity hist = new CsmrInfoHistEntity();
                    auditService.createHistory(master, hist);
                    if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                        fileUploadService.doFileUpload(requestDTO.getDocumentList(), requestDTO);
                    }
                    waterDTO.setStatus(PrefixConstants.NewWaterServiceConstants.SUCCESS);
                } catch (final Exception ex) {
                    log.error("In saving new water Connection", ex);
                    waterDTO.setStatus(PrefixConstants.NewWaterServiceConstants.FAIL);
                }
            } else {
                waterDTO.setStatus(PrefixConstants.NewWaterServiceConstants.FAIL);
            }
        } catch (Exception e) {
            log.error("Exception ocours to saveIllegalToLegalConnectionApplication() " + e);
            throw new FrameworkException("Exception occours in saveIllegalToLegalConnectionApplication() method" + e);
        }

        return waterDTO;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.NewWaterConnectionService# checkValidConnectionNumber(java.lang.String, java.lang.Long)
     */
    @Override
    public Long checkValidConnectionNumber(String csCcn, Long orgId) {
        return waterRepository.checkValidConnectionNumber(csCcn, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.NewWaterConnectionService#checkEntryFlag(java. lang.Long, java.lang.Long)
     */
    @Override
    public String checkEntryFlag(Long csIdn, Long orgId) {
        return waterRepository.checkEntryFlag(csIdn, orgId);
    }

    @Override
    @Transactional
    public ViewCsmrConnectionDTO viewConnectionDetailsByconnNo(String cnnoNo, Long orgId) {
        TbCsmrInfoDTO csrinfodto = null;
        ViewCsmrConnectionDTO viewdto = null;
        ViewBillMas mas = null;
        ViewBillDet Billdet = null;
        List<TbBillMas> billList = null;
        List<ViewBillDet> listViewBillDet = new ArrayList<>();
        Long csidn = waterRepository.getconnIdByConnNo(cnnoNo, orgId);
        if (csidn != null) {
            viewdto = new ViewCsmrConnectionDTO();
            csrinfodto = getConnectionDetailsById(csidn);
            BeanUtils.copyProperties(csrinfodto, viewdto);
            if (csrinfodto.getCodDwzid1() != null)
                viewdto.setCodDwzid1(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid1(), orgId).getDescLangFirst());
            if (csrinfodto.getCodDwzid2() != null)
                viewdto.setCodDwzid2(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid2(), orgId).getDescLangFirst());
            if (csrinfodto.getCodDwzid3() != null)
                viewdto.setCodDwzid3(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid3(), orgId).getDescLangFirst());
            if (csrinfodto.getCodDwzid4() != null)
                viewdto.setCodDwzid4(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid4(), orgId).getDescLangFirst());
            if (csrinfodto.getCodDwzid5() != null)
                viewdto.setCodDwzid5(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid5(), orgId).getDescLangFirst());
            if (csrinfodto.getTrmGroup1() != null)
                viewdto.setTrmGroup1(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup1(), orgId).getDescLangFirst());
            if (csrinfodto.getTrmGroup2() != null)
                viewdto.setTrmGroup2(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup2(), orgId).getDescLangFirst());
            if (csrinfodto.getTrmGroup3() != null)
                viewdto.setTrmGroup3(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup3(), orgId).getDescLangFirst());
            if (csrinfodto.getTrmGroup4() != null)
                viewdto.setTrmGroup4(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup4(), orgId).getDescLangFirst());
            if (csrinfodto.getTrmGroup5() != null)
                viewdto.setTrmGroup5(
                        CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup5(), orgId).getDescLangFirst());
            viewdto.setCsCcnstatus(CommonMasterUtility.findLookUpDesc("CNS", orgId, csrinfodto.getCsCcnstatus()));
            viewdto.setCsMeteredccn(CommonMasterUtility.findLookUpDesc("WMN", orgId, csrinfodto.getCsMeteredccn()));
            if (csrinfodto.getTypeOfApplication().equals("P"))
                viewdto.setTypeOfApplication("Permanent");
            else if (csrinfodto.getTypeOfApplication().equals("T"))
                viewdto.setTypeOfApplication("Temporary");
            viewdto.setCsCcnsize(CommonMasterUtility.findLookUpDesc("CSZ", orgId, csrinfodto.getCsCcnsize()));
            viewdto.setCsOGender(CommonMasterUtility.findLookUpDesc("GEN", orgId, csrinfodto.getCsOGender()));
            billList = billMasterService.getBillMasterListByUniqueIdentifier(csidn, orgId);

            if (billList != null && !billList.isEmpty()) {
                for (TbBillMas viewBillMas : billList) {
                    mas = new ViewBillMas();
                    BeanUtils.copyProperties(viewBillMas, mas);
                    String financialyear = financialyearService.findYearById(viewBillMas.getBmYear(), orgId)
                            .getFaFromDate().toString().substring(0, 4);
                    mas.setBmYear(financialyear + "-" + financialyearService
                            .findYearById(viewBillMas.getBmYear(), orgId).getFaToDate().toString().substring(2, 4));
                    for (TbBillDet det : viewBillMas.getTbWtBillDet()) {
                        Billdet = new ViewBillDet();
                        BeanUtils.copyProperties(det, Billdet);
                        if (det.getTaxCategory() != null)
                            Billdet.setTaxCategory(CommonMasterUtility
                                    .getHierarchicalLookUp(det.getTaxCategory(), orgId).getDescLangFirst());
                        Billdet.setTaxName(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId));
                        listViewBillDet.add(Billdet);
                    }
                }
                mas.setViewBillDet(listViewBillDet);
                viewdto.setViewBillMas(mas);
            }
        }

        return viewdto;
    }

    @Override
    public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
        return waterRepository.getPlumIdByApplicationId(applicationId, orgId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean saveServiceWiseAdvancePayment(Map<Long, Double> chargeDesc, Long applicationNo, Long receiptid,
            Long taxId, Long deptId, Long userId, Long orgId) {
        boolean saveAdvancePayment = false;
        Long connectionId = waterRepository.getCsIdnByApplicationId(applicationNo, orgId);
        for (final Entry<Long, Double> entry : chargeDesc.entrySet()) {
            saveAdvancePayment = saveAdvancePayment(orgId, entry.getValue(), String.valueOf(connectionId), userId, receiptid,
                    taxId);
        }
        return saveAdvancePayment;
    }

    private boolean saveAdvancePayment(final Long orgId, final Double amount, final String csIdn, final Long userId,
            final Long receiptId, Long taxId) {
        TbWtExcessAmt excessAmt = tbWtExcessAmtJpaRepository.findExcessAmountByCsIdnAndOrgId(Long.valueOf(csIdn),
                orgId);
        if (excessAmt == null) {
            excessAmt = new TbWtExcessAmt();
        }
        excessAmt.setExcAmt(amount + excessAmt.getExcAmt());
        excessAmt.setCsIdn(Long.valueOf(csIdn));
        excessAmt.setExcadjFlag(MainetConstants.RnLCommon.MODE_EDIT);
        excessAmt.setUserId(userId);
        excessAmt.setOrgId(orgId);
        excessAmt.setLangId(MainetConstants.NUMBERS.ONE);
        excessAmt.setLmodDate(new Date());
        excessAmt.setTaxId(taxId);
        excessAmt.setRmRcptid(receiptId);
        if (excessAmt.getExcessId() > 0) {
            final TbWtExcessAmtHist excessAmtHist = new TbWtExcessAmtHist();
            excessAmtHist.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
            excessAmtHist.setExcessId(excessAmt.getExcessId());
            final TbWtExcessAmt excessAmtOld = tbWtExcessAmtJpaRepository.findOne(excessAmt.getExcessId());
            auditService.createHistory(excessAmtOld, excessAmtHist);
        }
        tbWtExcessAmtJpaRepository.save(excessAmt);
        return true;
    }

    @Override
    @POST
    @Path("/searchwaterDeatils")
    @Transactional(readOnly = true)
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.APPLICATION_JSON)
    public List<WaterDataEntrySearchDTO> searchWaterDetailsByRequest(ViewWaterDetailRequestDto viewWaterDet) {
        return searchConnectionDetails(viewWaterDet.getSearchDto(), viewWaterDet.getPagingDTO(),
                viewWaterDet.getGridSearchDTO(), null);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public double getTotalOutstandingOfConnNosAssocWithPropNo(String propNo) {
        double outstandingAmount = 0;
        List<Long> csidnsByPropNo = waterRepository.getCsidnsByPropNo(propNo);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(csidnsByPropNo)) {
            outstandingAmount = billMasterService.getTotalOutstandingOfConnNosAssocWithPropNo(csidnsByPropNo);
        }
        return outstandingAmount;
    }

    // #102456 By Arun
    @Override
    @WebMethod(exclude = true)
    public CommonChallanDTO getDepartmentWiseLoiData(Long applicationNo, Long orgId) {
        CommonChallanDTO challanDto = new CommonChallanDTO();
        try {
            TbKCsmrInfoMH csmrInfoEntity = waterRepository.getApplicantInformationById(applicationNo, orgId);
            if (csmrInfoEntity.getPropertyNo() != null) {
                challanDto.setPropNoConnNoEstateNoV(csmrInfoEntity.getPropertyNo());
                challanDto.setOrgId(orgId);
                final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(challanDto,
                        ServiceEndpoints.PROP_DETAILS_BY_PROP_NO);
                if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
                    challanDto = (CommonChallanDTO) RestClient.castResponse(responseEntity, CommonChallanDTO.class);
                    log.info("CommonChallanDTO formed is " + challanDto.toString());
                }
            }
        } catch (Exception e) {
            throw new FrameworkException(e.getMessage());
        }
        return challanDto;
    }

    // User Story #97963-Customization for SKDCL-Manish Chaurasiya
    // Manual Receipt Entry Mobile No Add/Update in case of SKDCL
    @Transactional
    @WebMethod(exclude = true)
    public void updateMobileNumberOfConMaster(Long csIdn, String mobNo, Long orgId, String emailId) {
        waterRepository.updateMobileNumberOfConMaster(csIdn, orgId, mobNo, emailId);
    }

    public Map<Long, Double> getWaterConnectionSizeByAppId(Long appId, Long orgId) {
        Map<Long, Double> map = new HashMap<Long, Double>();
        Organisation organisation = organizationService.getOrganisationById(orgId);
        TbCsmrInfoDTO master = waterCommonService.getApplicantInformationById(appId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        // final TbKCsmrInfoMH master = newWaterRepository.getWaterConnectionDetailsConNumber(appId.toString(), orgId);
        Double doubl = master.getConnectionSize();
        LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(doubl.intValue(), organisation);

        Double d = Double.valueOf(lookUp.getLookUpDesc());
        map.put(appId, d);
        return map;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "searchConnectionDetails", notes = "searchConnectionDetails", response = Object.class)
    @Path("/searchConnectionDetails")
    @Transactional(readOnly = true)
    public List<WaterDataEntrySearchDTO> searchConnectionDetailsForMobileOrPortal(
            @RequestBody WaterDataEntrySearchDTO searchDTO) {
        List<WaterDataEntrySearchDTO> searchList = new ArrayList<>(0);
        try {
            List<TbKCsmrInfoMH> masterList = waterRepository.searchConnectionForView(searchDTO, null, null);
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(masterList)) {

                for (TbKCsmrInfoMH master : masterList) {
                    if (StringUtils.isNotBlank(master.getCsCcn())) {
                        WaterDataEntrySearchDTO search = new WaterDataEntrySearchDTO();
                        search.setRowId(String.valueOf(master.getCsIdn()));
                        search.setCodDwzid1(master.getCodDwzid1());
                        search.setCodDwzid2(master.getCodDwzid2());
                        search.setCodDwzid3(master.getCodDwzid3());
                        search.setCodDwzid4(master.getCodDwzid4());
                        search.setCodDwzid5(master.getCodDwzid5());
                        search.setCsAdd(master.getCsAdd());
                        search.setCsCcn(master.getCsCcn());
                        if (StringUtils.isNotBlank(master.getCsContactno())) {
                            search.setCsContactno(master.getCsContactno());
                        } else {
                            search.setCsContactno(master.getCsOcontactno());
                        }
                        search.setCsName(master.getCsName());
                        search.setPropertyNo(master.getPropertyNo());
                        search.setOrgId(master.getOrgId());
                        // Defect:-117766-Guardian Details Need To Show
                        NewWaterConnectionReqDTO reqDTO = new NewWaterConnectionReqDTO();
                        reqDTO.setOrgId(search.getOrgId());
                        if (search.getPropertyNo() != null)
                            reqDTO.setPropertyNo(search.getPropertyNo());
                        if (reqDTO != null && reqDTO.getPropertyNo() != null && reqDTO.getOrgId() != null) {
                            TbCsmrInfoDTO propInfoDTO = getPropertyDetailsByPropertyNumber(reqDTO);
                            if (propInfoDTO != null) {
                                if (StringUtils.isNotEmpty(propInfoDTO.getCsPowner()))
                                    search.setGardianOwnerName(propInfoDTO.getCsPowner());
                            }
                        }
                        searchList.add(search);
                    }

                }
            } else {
                WaterDataEntrySearchDTO search = new WaterDataEntrySearchDTO();
                search.setStatus(MainetConstants.WebServiceStatus.FAIL);
                search.setErrorMsg("No records found");
                searchList.add(search);
            }

        } catch (Exception e) {
            log.error("Exception ocours to searchConnectionDetails() " + e);
            throw new FrameworkException("Exception occours in searchConnectionDetails() method" + e);
        }

        return searchList;
    }

	@Override
	@Transactional(readOnly=true)
	public PropertyDetailDto getTotalOutStandingOfPropertyNumber(Long appNo, Long orgId) {
		TbCsmrInfoDTO dto = waterCommonService.getApplicantInformationById(appNo, orgId);
		if (dto != null && !org.apache.commons.lang3.StringUtils.isEmpty(dto.getPropertyNo())) {
			PropertyInputDto detailDto = new PropertyInputDto();
			detailDto.setPropertyNo(dto.getPropertyNo());
			detailDto.setOrgId(orgId);
			return getPropertyDueAmt(detailDto);
		}
		return null;
	}

	private PropertyDetailDto getPropertyDueAmt(PropertyInputDto propertyInputDto) {
		ResponseEntity<?> responseEntity = null;
		PropertyDetailDto app = null;
		Object obj = new Object();
		try {
			responseEntity = RestClient.callRestTemplateClient(propertyInputDto,ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
			String d = new JSONObject(responseEntity).toString();
			if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
				
				app = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error occured while fetching Property detail ", exception);
		}

		return app;
	}
	
	@Override
    public TbCsmrInfoDTO getPropertyDetailsByPropertyNumberNFlatNo(NewWaterConnectionReqDTO requestDTO) {
        TbCsmrInfoDTO infoDTO = new TbCsmrInfoDTO();
        PropertyDetailDto detailDTO = null;
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(requestDTO.getPropertyNo());
        propInputDTO.setOrgId(requestDTO.getOrgId());
        propInputDTO.setFlatNo(requestDTO.getFlatNo());
        Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgId());
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

            detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
            log.info("PropertyDetailDto formed is " + detailDTO.toString());            
            if (!detailDTO.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.FAIL)) {
                infoDTO.setCsOname(detailDTO.getPrimaryOwnerName());
                infoDTO.setCsOcontactno(detailDTO.getPrimaryOwnerMobNo());
                infoDTO.setTotalOutsatandingAmt(detailDTO.getTotalOutsatandingAmt());
                infoDTO.setPropertyNo(detailDTO.getPropNo());
                if (detailDTO.getOwnerEmail() != null) {
                    infoDTO.setCsOEmail(detailDTO.getOwnerEmail());
                }
                if (detailDTO.getPinCode() != null) {
                    infoDTO.setOpincode(detailDTO.getPinCode().toString());
                }
                infoDTO.setPropertyUsageType(detailDTO.getUasge());
                if (detailDTO.getOccupancyType() != null) {
                    infoDTO.setOccupancyType(detailDTO.getOccupancyType());
                }
                infoDTO.setCsOadd(detailDTO.getAddress());
                // infoDTO.setCsOrdcross(detailDTO.getLocation());
                if (detailDTO.getGender() != null && !detailDTO.getGender().isEmpty()) {
                    Long lookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(detailDTO.getGender(),
                            MainetConstants.GENDER, requestDTO.getOrgId());
                    if (lookupId != null && lookupId != 0L) {
                        LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(lookupId, MainetConstants.GENDER,
                                requestDTO.getOrgId());
                        infoDTO.setCsOGender(lookUp.getLookUpId());
                    }
                }
                if (StringUtils.isNotEmpty(detailDTO.getGardianOwnerName())) {
                    infoDTO.setCsPowner(detailDTO.getGardianOwnerName());
                }
            } else {
                infoDTO = null;
                log.info("Problem while getting property details by property Number " + responseEntity);
            }
        }

        else {
            infoDTO = null;
            log.info("Problem while getting property by property Number " + responseEntity);
        }

        return infoDTO;
    }

	// #140250  - To fetch total water outstanding against property
	@Override
	@POST
	@Transactional(readOnly = true)
	@Consumes("application/json")
	@ApiOperation(value = "getTotalOutstanding", notes = "getTotalOutstanding", response = Double.class)
	@Path("/getTotalOutstanding/{propNo}")
	public double getTotalOutstanding(@PathParam("propNo") String propNo) {
		LOGGER.info("Start of getTotalOutstanding Method.....");
		double outstandingAmount = 0;
		List<Long> csidnsByPropNo = waterRepository.getCsidnsByPropNo(propNo);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(csidnsByPropNo)) {
			outstandingAmount = billMasterService.getTotalOutstandingOfConnNosAssocWithPropNo(csidnsByPropNo);
		}
		return outstandingAmount;
	}
	
	@Override
	public HashMap<String, Double> getDischargePrefixMap(String prefixStr, Long orgId) {
		HashMap<String, Double> map = new HashMap<>();
		Organisation org = new Organisation();
		org.setOrgid(orgId); //need to change 
		String[] prefixArr = prefixStr.split(",");
		for(String prefix : prefixArr) {
			List<LookUp> lookUpList = CommonMasterUtility.getLookUps(prefix, org);
			if(lookUpList != null && lookUpList.size() > 0) {
				map.put(prefix, Double.valueOf(lookUpList.get(0).getLookUpCode()));
			}
		}
		
		return map;
	}

	@Override
	public Double getDischargeRateForDomestic(TbCsmrInfoDTO master, HashMap<String, Double> dischargePrefixMap) {
		
		Long noOfFamily = master.getNoOfFamilies(); // no of family
		Double n_APF = dischargePrefixMap.get("APF");
		Double n_ACP = dischargePrefixMap.get("ACP");
		Double n_ANH = dischargePrefixMap.get("ANH");
		
		Double dischargeRate = (noOfFamily * n_APF * n_ACP)/(1000 * n_ANH);
		
		return dischargeRate;
	}

	@Override
	public Double getDischargeRateForCommercial(TbCsmrInfoDTO master, HashMap<String, Double> dischargePrefixMap) {
		Long noOfFamily = master.getNoOfFamilies(); // no of family
		Double n_APF = dischargePrefixMap.get("APF");
		Double n_ACP = dischargePrefixMap.get("ACP");
		
		Double quantityRequired = getQuantityRequired(noOfFamily, n_APF, n_ACP);
		
		Double dischargeRate = quantityRequired / 4.0;
		
		return dischargeRate;
	}
	
	private Double getQuantityRequired(Long noOfFamily, Double n_APF, Double n_ACP) {
		return (noOfFamily * n_APF * n_ACP) / 1000;
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public Double getSlopeValueByRoadLengthAndOrg(Long rcLength, Organisation org) {
		final Double slopeValue = waterRepository.getSlopeValueByRoadLengthAndOrg(rcLength, org);
        return slopeValue;
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public Double getConnectionSizeByDFactor(Long d_Factor, Organisation org) {
		final Double ccnSize = waterRepository.getConnectionSizeByDFactor(d_Factor, org);
        return ccnSize;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CsmrInfoHistEntity getCsmrHistByCsIdAndOrgId(Long csIdn, Long orgId) {
		return waterRepository.getCsmrHistByCsIdAndOrgId(csIdn, orgId);
	}

	@Override
	public String getConnectionDetailsByAppIdAndOrgId(Long applicationId, long orgId, String serviceCode) {
		String connectioNo = null;
		if(MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER.equals(serviceCode)) {
			try{
				ChangeOfOwnerMas fetchWaterConnectionOwnerDetail = changeOfOwnershipRepository.fetchWaterConnectionOwnerDetail(applicationId);
				if(fetchWaterConnectionOwnerDetail != null) {
					TbKCsmrInfoMH fetchConnectionByCsIdn = newWaterRepository.fetchConnectionByCsIdn(fetchWaterConnectionOwnerDetail.getCsIdn(), orgId);
					if(fetchConnectionByCsIdn != null) {
						connectioNo = fetchConnectionByCsIdn.getCsCcn();
					}
				}
			}catch(Exception e) {
				log.error("Error while fetching connection number" + e.getMessage());
			}
		}else {
			try {
				TbKCsmrInfoMH applicantInformationById = newWaterRepository.getApplicantInformationById(applicationId, orgId);
				connectioNo = applicantInformationById.getCsCcn();
			}catch(Exception e) {
				log.error("Error while calling getApplicantInformationById" + e.getMessage());
			}	
		}
		
		return connectioNo; 
	}

	@Override
	public List<PlumberMasterDTO> getPlumberList(Long applicationId, Long orgId) {
		List<PlumberMasterDTO> plumberListBeans = new ArrayList<PlumberMasterDTO>();
		try {
			List<PlumberMaster> allPlumber = plumberMasterRepository.getAllPlumber(orgId);
			allPlumber.stream().forEach(plumber->{
				PlumberMasterDTO bean = new PlumberMasterDTO();
                BeanUtils.copyProperties(plumber, bean);
                plumberListBeans.add(bean);
			});
		}catch(Exception e) {
			LOGGER.error("Error occured while calling getAllPlumber " + e.getMessage());
		}
		return plumberListBeans;
	}
	
	@Override
	public void updatePlumberHoleManInfoInCsmr(PlumberHoleManDTO plumberHoleManDTO, Long applicationId, Long orgId) {
		
		try{
			TbKCsmrInfoMH applicantInformationById = newWaterRepository.getApplicantInformationById(applicationId, orgId);
			applicantInformationById.setPlumId(plumberHoleManDTO.getPlumId());
			applicantInformationById.setHoleMan(plumberHoleManDTO.getHoleManName());
			newWaterRepository.updateCsmrInfo(applicantInformationById);
		}catch(Exception e) {
			LOGGER.error("Error occured updating plumber and hole man details " + e.getMessage());
		}
		
	}
	
	@Override
	public String getConnectionDetailsByAppIdAndService(Long applicationId, long orgId, String serviceCode) {
		
		try {
			String connectioNo = null;
			Long csIdn = null;
			
			if(MainetConstants.WaterServiceShortCode.WATER_DISCONNECTION.equals(serviceCode)) {
				TbKCsmrInfoMH applicantInformationById  = disconnectionRepository.getApplicantDetails(applicationId, orgId);
				connectioNo = applicantInformationById.getCsCcn();
				return connectioNo;
			}else if(MainetConstants.WaterServiceShortCode.CHANGE_OF_USAGE.equals(serviceCode)) {
				ChangeOfUsage changeOfUsagebj = changeOfUsageRepository.getChangeOfUsaes(orgId, applicationId);
				csIdn = changeOfUsagebj.getCsIdn();
			}else if(MainetConstants.WaterServiceShortCode.WATER_RECONN.equals(serviceCode)) {
				TbWaterReconnection waterReconnectionObj = waterReconnectionRepository.getReconnectionDetails(applicationId, orgId);
				csIdn = waterReconnectionObj.getCsIdn();
			}
		
			if(csIdn != null) {
				TbKCsmrInfoMH applicantInformationById = newWaterRepository.getConnectionDetailsById(csIdn);
				connectioNo = applicantInformationById.getCsCcn();
				return connectioNo;
			}
			
			
		}catch(Exception e) {
			log.error("Error while calling getApplicantInformationById" + e.getMessage());
		}
		return null;
		 
	}

	@Override
	public TbKCsmrInfoMH getActiveConnectionByCsCcnAndOrgId(String csCcn, Long orgId, String activeFlag) {
		 TbKCsmrInfoMH master = null;
	        try {
	            master = waterRepository.getActiveConnectionByCsCcnAndOrgId(csCcn, orgId, activeFlag);
	        } catch (Exception e) {
	            log.error("Exception ocours to getConnectionDetailsById() " + e);
	            throw new FrameworkException("Exception occours in getConnectionDetailsById() method" + e);
	        }

		return master;
	}
	
	/**
	 * Get outstanding water payment including changing dynamic penalty
	 */
	@Override
	public KDMCWaterDetailsResponseDTO getTotalWaterOutstandingAmount(String connectionNo, Long orgId) {
		
		Assert.notNull(connectionNo, "Connection No. Can not be null");
        final KDMCWaterDetailsResponseDTO kdmcWaterDetailsResponseDTO = new KDMCWaterDetailsResponseDTO();
        try {
        	 TbKCsmrInfoMH csmrInfo = newWaterRepository.getWaterConnectionDetailsConNumber(connectionNo, orgId);
        	 if(csmrInfo!=null) {
        		 
            	 AtomicDouble totPayableAmt = new AtomicDouble(0);
            	 List<TbWtBillMasEntity> billMasList = tbWtBillMasJpaRepository
     		            .getBillMasByConnectionId(csmrInfo.getCsIdn());
 				 Double sumOfPenalty = 0.0;
 				List<WaterPenaltyEntity> penaltyList = null;
                 if(CollectionUtils.isNotEmpty(billMasList)) {
                     if (billMasList != null && !billMasList.isEmpty() &&
                    		 MainetConstants.N_FLAG.equals(billMasList.get(billMasList.size() - 1).getBmPaidFlag())) {
                         billMasList.get(billMasList.size() - 1).getBillDetEntity().forEach(det -> {
                        	 totPayableAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                         });
                     
	                     penaltyList = waterPenaltyRepository
	 							.getWaterPenaltyByCCnNo(String.valueOf(csmrInfo.getCsIdn()), csmrInfo.getOrgId());
		 				
	                     sumOfPenalty = penaltyList.stream().mapToDouble(penalty -> penalty.getPendingAmount()).sum();
                     }
                     kdmcWaterDetailsResponseDTO.setTotalPayAmt(totPayableAmt.get());
	 				 kdmcWaterDetailsResponseDTO.setBillNo(billMasList.get(billMasList.size() - 1).getBmNo());
	 				 
	 				 Organisation org = new Organisation();
	 				 org.setOrgid(csmrInfo.getOrgId());
	 				 final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
	                         PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA, org);

	 				 final LookUp chargeApplicableAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
	                         PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);

	 				 final Long deptId = deptService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
	                        MainetConstants.STATUS.ACTIVE);
	                 // Here fetching all taxes applicable at the time of payment
	                 List<TbTaxMas> taxListPenalty = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
	                         chargeApplicableAtBillReceipt.getLookUpId());
	                 
	                 List<TbTaxMas> taxListBillDetails = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
	                		 chargeApplicableAtBill.getLookUpId());
	                 

	                 Map<String, List<TbWtBillDetEntity>> taxNameWiseBillDet = new HashMap<>();
	                 List<WaterTaxDetailsDto> waterTaxes = new ArrayList<>();
	                 List<TbWtBillMasEntity> unPaidBills = billMasList.stream().filter(bill->bill.getBmPaidFlag()!=null && bill.getBmPaidFlag().equals(MainetConstants.FlagN)).
	                 	collect(Collectors.toList()); 
	                 for(TbWtBillMasEntity bill: unPaidBills){
	                	 Map<Long, List<TbWtBillDetEntity>> taxIdWiseBillDet = bill.getBillDetEntity().stream().collect
	                			 (Collectors.groupingBy(billDet-> billDet.getTaxId()));
	                	 for(Entry<Long, List<TbWtBillDetEntity>> tax : taxIdWiseBillDet.entrySet()){
		                	 TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getKey(), org.getOrgid());
		                	 if(taxMas!=null) {
		                		 if(taxNameWiseBillDet.containsKey(taxMas.getTaxDesc())) {
		                			 List<TbWtBillDetEntity> existingDet = taxNameWiseBillDet.get(taxMas.getTaxDesc());
		                			 existingDet.get(0).setBdCurBalTaxamt(existingDet.get(0).getBdCurBalTaxamt() +
		                					 tax.getValue().get(0).getBdCurBalTaxamt());
		                			 
		                			 existingDet.get(0).setBdPrvBalArramt(tax.getValue().get(0).getBdPrvBalArramt());
		                			 existingDet.get(0).setBdCurTaxamt(tax.getValue().get(0).getBdCurBalTaxamt());
			                		 taxNameWiseBillDet.put(taxMas.getTaxDesc(), existingDet);

		                		 }else {
			                		 taxNameWiseBillDet.put(taxMas.getTaxDesc(), tax.getValue());
		                		 }
		                	 }
	                	 }
	                 }
	                 for(Entry<String, List<TbWtBillDetEntity>> taxWiseDet : taxNameWiseBillDet.entrySet()) {
	                	 WaterTaxDetailsDto taxDetail = new WaterTaxDetailsDto();
	             		 taxDetail.setTaxName(taxWiseDet.getKey());
	             		 taxDetail.setBalArrearsAmt(taxWiseDet.getValue().get(0).getBdPrvBalArramt());
	             		 taxDetail.setBalTaxAmt(taxWiseDet.getValue().get(0).getBdCurTaxamt());
	             		 taxDetail.setTaxAmt(taxWiseDet.getValue().get(0).getBdCurBalTaxamt());
	             		 waterTaxes.add(taxDetail); 
	                 }
	                 // adding penalty
                	 TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(penaltyList.get(0).getTaxId(), org.getOrgid());
                	 if(taxMas!=null) {
                		 WaterTaxDetailsDto taxDetail = new WaterTaxDetailsDto();
	             		 taxDetail.setTaxName(taxMas.getTaxDesc());
	             		 taxDetail.setBalArrearsAmt(sumOfPenalty);
	             		 taxDetail.setBalTaxAmt(0d);
	             		 taxDetail.setTaxAmt(sumOfPenalty);
	             		 waterTaxes.add(taxDetail); 	                		 
                	 }
	 				 
	                 kdmcWaterDetailsResponseDTO.setWaterTaxDetailList(waterTaxes);
	                 List<WaterPenaltyDto> dynamicPenalty = getDynamicPenalty(taxListPenalty, billMasList, csmrInfo, deptId);
	                 Double dynamicPenaltyAmt = 0d;
	                 if(dynamicPenalty!=null && !dynamicPenalty.isEmpty()) {
	                	 dynamicPenaltyAmt  = dynamicPenalty.stream().mapToDouble(penalty->penalty.getPendingAmount()).sum();
	                	 sumOfPenalty = dynamicPenaltyAmt > sumOfPenalty ? dynamicPenaltyAmt : sumOfPenalty;
	                 }
	                 kdmcWaterDetailsResponseDTO.setTotalInterestAmt(sumOfPenalty);
	 				 kdmcWaterDetailsResponseDTO.setTotalActPayAmt(sumOfPenalty + totPayableAmt.get());

                 }
        	
        	 }        	 
        }catch(Exception ex) {
            kdmcWaterDetailsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
			LOGGER.error("Error While Fetching connection detail: " + ex.getMessage(), ex);
	}

		return kdmcWaterDetailsResponseDTO;
	}

	private List<WaterPenaltyDto> getDynamicPenalty(List<TbTaxMas> taxList, List<TbWtBillMasEntity> billMasList, TbKCsmrInfoMH csmrInfo, Long deptId) {
		
		Organisation org = new Organisation();
		org.setOrgid(csmrInfo.getOrgId());
        List<WaterPenaltyDto> surChargeList=new ArrayList<>();
        Long finYearId = financialYearService.getFinanceYearId(new Date());
        TbCsmrInfoDTO csmrInfoDto = new TbCsmrInfoDTO();
        BeanUtils.copyProperties(csmrInfo, csmrInfoDto);
        
        List<TbBillMas> billMasListDto = tbWtBillMasService.getUnpaidBillEntityToDto(billMasList);
        
        for (TbTaxMas tbTaxMas : taxList) {
            if (StringUtils.equalsIgnoreCase(tbTaxMas.getTaxActive(), MainetConstants.FlagY)) {

                LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory2(),
                		org.getOrgid());
                if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.ReceiptForm.SC)) {
                    if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                    	LOGGER.info("fetch bill data  calculate surcharge for skdcl");
                    	TbServiceReceiptMasEntity receiptOfLatestPaidBill = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(org.getOrgid(),
                    			String.valueOf(csmrInfo.getCsIdn()));
                        Date manualReceiptDate = receiptOfLatestPaidBill != null ? receiptOfLatestPaidBill.getRmDate(): null;
						 surChargeList = tbWtBillMasService.calculateSurcharge(org, deptId, billMasListDto, tbTaxMas,finYearId,
								 csmrInfoDto, UserSession.getCurrent().getUserIp(), null, 
										 "Y", manualReceiptDate, surChargeList);
                    }
                }
            }
        }
        return surChargeList;
	}

	@Override
	public TbKCsmrInfoMH getConnectionByCsCcnAndOrgId(String csCcn, Long orgId) {
		 TbKCsmrInfoMH master = null;
	        try {
	            master = waterRepository.getConnectionByCsCcnAndOrgId(csCcn, orgId);
	        } catch (Exception e) {
	            log.error("Exception occurs in getConnectionByCsCcnAndOrgId() " + e.getMessage());
	            throw new FrameworkException("Exception occours in getConnectionByCsCcnAndOrgId() method" + e);
	        }

		return master;
	}
	
	public CustomerInfoDTO getCustomerInfoDTOByCsCcnAndOrgId(String csCcn, Long orgId) {
		
		CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
		try {
             TbKCsmrInfoMH csmrInfo = waterRepository.getConnectionByCsCcnAndOrgId(csCcn, orgId);
             if(csmrInfo!=null) {
            	 
                 customerInfoDTO.setName(Stream.of(csmrInfo.getCsName(), csmrInfo.getCsMname(), csmrInfo.getCsLname()).filter(
                		 nameString -> nameString!=null && !nameString.isEmpty()).collect(Collectors.joining(" ")));

                 customerInfoDTO.setAddress(Stream.of(csmrInfo.getCsAdd(), csmrInfo.getFlatNo(), csmrInfo.getCsBldplt(), 
            		 csmrInfo.getCsLanear(), csmrInfo.getCsRdcross()).filter(
                		 addString -> addString!=null && !addString.isEmpty()).collect(Collectors.joining(", ")));
             }
		}catch(Exception ex) {
			log.error("Could not find customer information for connection : " + csCcn + " and orgId: " + orgId + " -> " + ex.getMessage());;
		}
		return customerInfoDTO;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "getProvisnalCertDetails", notes = "getProvisnalCertDetails", response = Object.class)
	@Path("/getProvisnalCertDetails/orgId/{orgId}/applicationNo/{applicationNo}")
	public ProvisionalCertificateDTO getProvisionalCertificateData(@PathParam("applicationNo") Long applicationNo,
			@PathParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
		LOGGER.info("Begin getProvisionalCertificateData--> " + this.getClass().getSimpleName());

		ProvisionalCertificateDTO provisionCertificateDTO = new ProvisionalCertificateDTO();
		try {

			TbKCsmrInfoMH csmrInfo = waterRepository.getApplicantInformationById(applicationNo, orgId);
			if (csmrInfo != null) {
				provisionCertificateDTO.setApplicationNo(csmrInfo.getApplicationNo());
				try {
					TbCfcApplicationMst applicationMst = tbCfcApplicationMstService
							.findById(csmrInfo.getApplicationNo());
					provisionCertificateDTO.setApplicationDate(applicationMst.getApmApplicationDate());
					provisionCertificateDTO.setProvApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(applicationMst.getApmApplicationDate()));

				} catch (Exception ex) {
					LOGGER.error(ex.getMessage());
				}

				String firstName = csmrInfo.getCsName() != null && !csmrInfo.getCsName().isEmpty()
						? csmrInfo.getCsName()
						: "";
				String middleName = csmrInfo.getCsMname() != null && !csmrInfo.getCsMname().isEmpty()
						? csmrInfo.getCsMname()
						: "";
				String lastName = csmrInfo.getCsLname() != null && !csmrInfo.getCsLname().isEmpty()
						? csmrInfo.getCsLname()
						: "";
				provisionCertificateDTO.setFullName(String.join(" ", Arrays.asList(firstName, middleName, lastName)));

				StringBuilder address1 = new StringBuilder(
						csmrInfo.getCsAdd() != null && !csmrInfo.getCsAdd().isEmpty() ? csmrInfo.getCsAdd()
								: StringUtils.EMPTY);
				address1.append(csmrInfo.getFlatNo() != null && !csmrInfo.getFlatNo().isEmpty()
						? csmrInfo.getFlatNo() + StringUtils.EMPTY
						: StringUtils.EMPTY);
				address1.append(csmrInfo.getCsBldplt() != null && !csmrInfo.getCsBldplt().isEmpty()
						? csmrInfo.getCsBldplt() + StringUtils.EMPTY
						: StringUtils.EMPTY);

				StringBuilder address2 = new StringBuilder(
						csmrInfo.getCsLanear() != null && !csmrInfo.getCsLanear().isEmpty()
								? csmrInfo.getCsLanear() + StringUtils.EMPTY
								: StringUtils.EMPTY);
				address2.append(csmrInfo.getCsRdcross() != null && !csmrInfo.getCsRdcross().isEmpty()
						? csmrInfo.getCsRdcross() + StringUtils.EMPTY
						: StringUtils.EMPTY);
				provisionCertificateDTO.setAddress1(address1.toString());
				provisionCertificateDTO.setAddress2(address2.toString());
				
				provisionCertificateDTO.setFatherHusbandName(csmrInfo.getFatherName());
				
				provisionCertificateDTO.setPincode(csmrInfo.getCsCpinCode());

				provisionCertificateDTO.setConsumerHouseNumber(
						csmrInfo.getHouseNumber() != null && !csmrInfo.getHouseNumber().isEmpty()
								? csmrInfo.getHouseNumber()
								: StringUtils.EMPTY);
				provisionCertificateDTO.setConsumerLandmark(
						csmrInfo.getLandmark() != null && !csmrInfo.getLandmark().isEmpty() ? csmrInfo.getLandmark()
								: StringUtils.EMPTY);
				provisionCertificateDTO.setWard(CommonMasterUtility.getHierarchicalLookUp(
						Long.valueOf(
								csmrInfo.getCodDwzid2() != null ? csmrInfo.getCodDwzid2() : csmrInfo.getCodDwzid1()),
						orgId).getDescLangFirst());
				provisionCertificateDTO
						.setConsumerMobile(csmrInfo.getCsContactno() != null && !csmrInfo.getCsContactno().isEmpty()
								? csmrInfo.getCsContactno()
								: StringUtils.EMPTY);
				provisionCertificateDTO.setConsumerEmail(
						csmrInfo.getCsEmail() != null && !csmrInfo.getCsEmail().isEmpty() ? csmrInfo.getCsEmail()
								: StringUtils.EMPTY);

//		        provisionCertificateDTO.setTapTypeConnection(CommonMasterUtility
//	                    .getHierarchicalLookUp(csmrInfo.getCsCcncategory1(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
//		        provisionCertificateDTO.setTapUsage(CommonMasterUtility.getHierarchicalLookUp(
//                        Long.valueOf(csmrInfo.getTrmGroup1()), orgId).getDescLangFirst());

//				final List<LookUp> wardLookUps = CommonMasterUtility.getNextLevelData("WWZ", 2, UserSession.getCurrent().getOrganisation().getOrgid());
//				for (final LookUp lookUp : wardLookUps) {
//					if ((getModel().getCsmrInfo().getCodDwzid1() != null) && getModel().getCsmrInfo().getCodDwzid1() != 0l) {
//						if (lookUp.getLookUpId() ==getModel().getCsmrInfo().getCodDwzid1()) {
//							provisionCertificateDTO.setWard(lookUp.getDescLangFirst());
//							break;
//						}
//					}
//
//				}

				final List<LookUp> categoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 1, orgId);
				for (final LookUp lookUp : categoryLookUps) {
					if ((csmrInfo.getCodDwzid1() != null) && csmrInfo.getCodDwzid1() != 0l) {
						if (lookUp.getLookUpId() == csmrInfo.getCsCcncategory1()) {
							provisionCertificateDTO.setTapTypeConnection(lookUp.getDescLangFirst());
							break;
						}
					}

				}

				final List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getNextLevelData("TRF", 1, orgId);
				for (final LookUp lookUp : tarrifCategoryLookUps) {
					if ((csmrInfo.getTrmGroup1() != null) && csmrInfo.getTrmGroup1() != 0l) {
						if (lookUp.getLookUpId() == csmrInfo.getTrmGroup1()) {
							provisionCertificateDTO.setTapUsage(lookUp.getDescLangFirst());
							break;
						}
					}

				}

				try {
					TbServiceReceiptMasEntity receiptDetailsByAppId = iReceiptEntryService
							.getReceiptDetailsByAppId(applicationNo, orgId);
					if (receiptDetailsByAppId != null) {
						provisionCertificateDTO.setPaymentDate(receiptDetailsByAppId.getRmDate());
						provisionCertificateDTO.setProvPaymentDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(receiptDetailsByAppId.getRmDate()));
						if (receiptDetailsByAppId.getReceiptModeDetail() != null
								&& !receiptDetailsByAppId.getReceiptModeDetail().isEmpty()) {
							Long cpdFeemode = receiptDetailsByAppId.getReceiptModeDetail().get(0).getCpdFeemode();
							provisionCertificateDTO.setPaymentMode(
									cpdFeemode != null
											? CommonMasterUtility.getCPDDescription(cpdFeemode,
													PrefixConstants.D2KFUNCTION.ENGLISH_DESC)
											: StringUtils.EMPTY);
						}
					} else {
						LOGGER.error("receipt obj : " + receiptDetailsByAppId + " for application no " + applicationNo);
					}
				} catch (Exception ex) {
					LOGGER.error("Payment receipt not found for app no " + applicationNo + " " + ex.getMessage());
				}
			} else {
				LOGGER.error("csmr info " + csmrInfo);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured fetching csmr info object for application no. " + applicationNo);
		}

		return provisionCertificateDTO;
	}
	
	private String getPropertyGurdianName(NewWaterConnectionReqDTO requestDTO) {
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		serviceClassName = "com.abm.mainet.property.service.ViewPropertyDetailsServiceImpl";
		try {
			clazz = ClassUtils.forName(serviceClassName,
					ApplicationContextProvider.getApplicationContext().getClassLoader());

			dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
					.autowire(clazz, 2, false);
			final Method method = ReflectionUtils.findMethod(clazz, "getGurdianName",
					new Class[] { Long.class, String.class });
			Object obj = (Object) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
					new Object[] { requestDTO.getOrgId(), requestDTO.getPropertyNo() });

			if (obj != null) {
				return (String) obj;
			}
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	@Override
	public Map<String, Long> getMeterData(Long applicationId) {
		LOGGER.info("Trade Licence Data fetched for application id " + applicationId);
		Map<String, Long> map = new HashMap<String, Long>();
		try {

			TbKCsmrInfoMH entity = tbWtBillMasJpaRepository
					.getDataWithApplicationNo(applicationId);
			if (entity != null) {
				map.put("CsMeteredccn", entity.getCsMeteredccn());
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return map;
	}
	@Override
	public Long getCsIdnByConnectionNo(String connectionNo, Long orgId){
		TbKCsmrInfoMH connectionByCsCcnAndOrgId = getConnectionByCsCcnAndOrgId(connectionNo, orgId);
		Long csIdn = null;
		if(connectionByCsCcnAndOrgId!=null){
			csIdn = connectionByCsCcnAndOrgId.getCsIdn();
		}
		return csIdn;
	}
}