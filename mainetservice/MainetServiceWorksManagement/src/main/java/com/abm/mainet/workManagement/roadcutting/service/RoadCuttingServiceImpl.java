/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.domain.TbWorkOrderDetailEntity;
import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.TbWorkOrderDetailJpaRepository;
import com.abm.mainet.common.repository.TbWorkOrderJpaRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.workManagement.datamodel.RoadCuttingRateMaster;
import com.abm.mainet.workManagement.roadcutting.domain.RoadCuttingEntity;
import com.abm.mainet.workManagement.roadcutting.domain.RoadRouteDetailEntity;
import com.abm.mainet.workManagement.roadcutting.mapper.RoadCuttingMapper;
import com.abm.mainet.workManagement.roadcutting.repository.RoadCuttingRepository;
import com.abm.mainet.workManagement.roadcutting.repository.RoadRouteDetailRepository;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadRouteDetailsDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author satish.rathore
 *
 */
@WebService(endpointInterface = "com.abm.mainet.workManagement.roadcutting.service.IRoadCuttingService")
@Api(value = "/roadCuttingService")
@Path("/roadCuttingService")
@Service
public class RoadCuttingServiceImpl implements IRoadCuttingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoadCuttingServiceImpl.class);
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Resource
	private TbTaxMasService taxMasService;

	@Resource
	private RoadCuttingRepository roadCuttingRepository;
	
	@Resource
    private TbDepartmentService tbDepartmentService;
	
	@Resource
    private TbWorkOrderJpaRepository tbWorkOrderJpaRepository;
	
	@Resource
    private TbWorkOrderDetailJpaRepository tbWorkOrderDetailJpaRepository;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private IFileUploadService fileUploadService;
	
	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private IWorkflowRequestService requestService;
	
	@Autowired
    private BRMSCommonService brmsCommonService;
	
	@Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;


	@Autowired
	private RoadRouteDetailRepository roadRouteDetailRepository;

	@POST
	@Path("/dependentparams")
	@ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
	@Override
	public WSResponseDTO getApplicableTaxes(
			@ApiParam(value = "get dependent paramaters", required = true) WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms Road Cutting getApplicableTaxes execution start..");
		try {
			if (requestDTO.getDataModel() == null) {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
			} else {
				RoadCuttingRateMaster roadCuttingRateMaster = (RoadCuttingRateMaster) CommonMasterUtility
						.castRequestToDataModel(requestDTO, RoadCuttingRateMaster.class);
				validateDataModel(roadCuttingRateMaster, responseDTO);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					responseDTO = populateOtherFieldsForServiceCharge(roadCuttingRateMaster, responseDTO);
				}
			}
		} catch (CloneNotSupportedException | FrameworkException ex) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
		}
		LOGGER.info("brms Road cutting  getApplicableTaxes execution end..");
		return responseDTO;
	}

	@POST
	@Path("/servicecharge")
	@ApiOperation(value = "get service charge", notes = "get service charges", response = WSResponseDTO.class)
	@Override
	public WSResponseDTO getApplicableCharges(
			@ApiParam(value = "get service charges", required = true) WSRequestDTO requestDTO) {
		LOGGER.info("brms Road cutting getApplicableCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.WMS_ROAD_CUTTING_RATE_MASTER);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				responseDTO = setServiceChargeDTO(responseDTO);
			} else {
			    return responseDTO;
			}
		} catch (Exception ex) {
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
		}
		LOGGER.info("brms road cutting getApplicableCharges execution End..");
		return responseDTO;
	}

	/**
	 * validating RoadCuttingRateMaster model
	 * 
	 * @param RoadCuttingRateMaster
	 * @param responseDTO
	 * @return
	 */
	private WSResponseDTO validateDataModel(RoadCuttingRateMaster roadCuttingRateMaster, WSResponseDTO responseDTO) {
		LOGGER.info("validateDataModel execution start..");
		StringBuilder builder = new StringBuilder();
		if (roadCuttingRateMaster.getServiceCode() == null || roadCuttingRateMaster.getServiceCode().isEmpty()) {
			builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
		}
		if (roadCuttingRateMaster.getOrgId() == 0l) {
			builder.append(ORG_ID_CANT_BE_ZERO).append(",");
		}
		if (roadCuttingRateMaster.getChargeApplicableAt() == null
				|| roadCuttingRateMaster.getChargeApplicableAt().isEmpty()) {
			builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
		} else if (!StringUtils.isNumeric(roadCuttingRateMaster.getChargeApplicableAt())) {
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

	private WSResponseDTO populateOtherFieldsForServiceCharge(RoadCuttingRateMaster roadCuttingRateMaster,
			WSResponseDTO responseDTO) throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<RoadCuttingRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(roadCuttingRateMaster.getServiceCode(),
				roadCuttingRateMaster.getOrgId());
		if (serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES) || serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), roadCuttingRateMaster.getOrgId(),
					Long.parseLong(roadCuttingRateMaster.getChargeApplicableAt()));
			Organisation organisation = new Organisation();
			organisation.setOrgid(roadCuttingRateMaster.getOrgId());
			listOfCharges = settingAllFields(applicableCharges, roadCuttingRateMaster, organisation);
			responseDTO.setResponseObj(listOfCharges);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		} else {
			responseDTO.setFree(true);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		}
		LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
		return responseDTO;
	}

	/**
	 * 
	 * @param applicableCharges
	 * @param rateMaster
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private List<RoadCuttingRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges,
			RoadCuttingRateMaster roadCuttingRateMaster, Organisation organisation) throws CloneNotSupportedException {
		LOGGER.info("settingAllFields execution start..");
		List<RoadCuttingRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			RoadCuttingRateMaster roadCuttingRateMasters = (RoadCuttingRateMaster) roadCuttingRateMaster.clone();
			// SLD for dependsOnFactor
			String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
					roadCuttingRateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
			String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
					entity.getOrgid(), entity.getTaxApplicable());
			roadCuttingRateMasters.setTaxType(taxType);
			roadCuttingRateMasters.setTaxCode(entity.getTaxCode());
			roadCuttingRateMasters.setChargeApplicableAt(chargeApplicableAt);
			roadCuttingRateMasters.setChargeDescEng(entity.getTaxDesc());
			roadCuttingRateMasters.setChargeDescReg(entity.getTaxDesc());
			settingTaxCategories(roadCuttingRateMasters, entity, organisation);
			roadCuttingRateMasters.setTaxId(entity.getTaxId());
			list.add(roadCuttingRateMasters);
		}
		LOGGER.info("settingAllFields execution end..");
		return list;
	}

	private RoadCuttingRateMaster settingTaxCategories(RoadCuttingRateMaster roadCuttingRateMaster,
			TbTaxMasEntity enity, Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				roadCuttingRateMaster.setTaxCategory(lookUp.getDescLangFirst().trim());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				roadCuttingRateMaster.setTaxSubCategory(lookUp.getDescLangFirst().trim());
				break;
			}

		}
		return roadCuttingRateMaster;

	}

	// set Service Charge DTO details based on rule response
	private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setServiceChargeDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, RoadCuttingRateMaster.class);
		final List<RoadCuttingRateMaster> finalRateMaster = new ArrayList<>();
		for (final Object rate : charges) {
			final RoadCuttingRateMaster masterRate = (RoadCuttingRateMaster) rate;
			finalRateMaster.add(masterRate);
		}
		final ChargeDetailDTO chargedto = new ChargeDetailDTO();
		final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
		for (final RoadCuttingRateMaster rateCharge : finalRateMaster) {
			chargedto.setChargeCode(rateCharge.getTaxId());
			chargedto.setChargeAmount(rateCharge.getFlatRate());
			chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
			chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
			chargedto.setTaxCode(rateCharge.getTaxCode());
			detailDTOs.add(chargedto);
		}
		responseDTO.setResponseObj(detailDTOs);
		LOGGER.info("setServiceChargeDTO execution end..");
		return responseDTO;
	}

	@POST
	@Path("/save")
	@ApiOperation(value = "save Road Cutting Application", notes = "save Road Cutting Application", response = RoadCuttingDto.class)
	@Override
	@Transactional
	public RoadCuttingDto saveRoadCutting(RoadCuttingDto requestDTO) {
		
		//TODO: Generate Application Id
            try {
            	ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByServiceId(requestDTO.getServiceId(),
        				requestDTO.getOrgId());
            	requestDTO.setPayStatus(MainetConstants.PAYMENT.FREE);
            	Long applicationId = null;
        		String referenceId=null;
        		Organisation org = new Organisation();
        		org.setOrgid(requestDTO.getOrgId());
        		requestDTO.setUserId(requestDTO.getUserId());
            	if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {	
        			FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
        			String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
        					.getDeptCode(serviceMas.getTbDepartment().getDpDeptid());
        			
        			String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
        			final Long sequence = ApplicationContextProvider.getApplicationContext()
        					.getBean(SeqGenFunctionUtility.class).generateSequenceNo(deptCode, "TB_WMS_ROAD_CUTTING",
        							"RC_ID", requestDTO.getOrgId(), MainetConstants.FlagF, null);
        			
        			referenceId = "PDA"+MainetConstants.WINDOWS_SLASH+"RC"+MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
        					+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)+MainetConstants.WINDOWS_SLASH+"NOC";
        			requestDTO.setReferenceId(referenceId);
        			applicationId = applicationService.createApplication(requestDTO);
        			requestDTO.setApplicationId(applicationId);
        			
        		}
            	else{
                applicationId = applicationService.createApplication(requestDTO);
                requestDTO.setApplicationId(applicationId);
            	}
                RoadCuttingEntity entity = new RoadCuttingEntity();
                entity = RoadCuttingMapper.dtoToEntitymapper(requestDTO);
        		roadCuttingRepository.save(entity);
                if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                    fileUploadService.doFileUpload(requestDTO.getDocumentList(), requestDTO);
                }
                
             // Add Project Location Image Save Code
    			if (CollectionUtils.isNotEmpty(requestDTO.getProjectLocation())) {
    				uploadLocationImage(requestDTO);
    			}

                requestDTO.setStatus(PrefixConstants.NewWaterServiceConstants.SUCCESS);
            } catch (final Exception ex) {
            	LOGGER.error("In Road Cutting saving error", ex);
                requestDTO.setStatus(PrefixConstants.NewWaterServiceConstants.FAIL);
            }
			return requestDTO;
		
		
		
}		

	@Override
	@Transactional
	public RoadCuttingDto updateRoadCutting(RoadCuttingDto requestDTO) {
		try {
			RoadCuttingEntity entity = new RoadCuttingEntity();
			entity = RoadCuttingMapper.dtoToEntitymapper(requestDTO);
			roadCuttingRepository.save(entity);
			requestDTO.setStatus(PrefixConstants.NewWaterServiceConstants.SUCCESS);
		} catch (final Exception ex) {
			LOGGER.error("In Road Cutting saving error", ex);
			requestDTO.setStatus(PrefixConstants.NewWaterServiceConstants.FAIL);
		}
		return requestDTO;
	}

	
	
	@Override
	@Transactional(readOnly=true)
	public RoadCuttingDto getRoadCuttingApplicationData(long applicationId, long orgid) {
		RoadCuttingEntity entity = roadCuttingRepository.findByOrgIdAndApmApplicationId(orgid, applicationId);
		return RoadCuttingMapper.entityToDtoMapper(entity);
	}
	
	@WebMethod(exclude=true)
	@Override
	@Transactional(readOnly = true)
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId, final Long orgId) {

		RoadCuttingEntity entity = roadCuttingRepository.findByOrgIdAndApmApplicationId(orgId, applicationId);

        WardZoneBlockDTO wardZoneDTO = null;

        if (entity != null) {
            wardZoneDTO = new WardZoneBlockDTO();
            if (entity.getCodWard1() != null) {
                wardZoneDTO.setAreaDivision1(entity.getCodWard1());
            }
            if (entity.getCodWard2() != null) {
                wardZoneDTO.setAreaDivision2(entity.getCodWard2());
            }
            if (entity.getCodWard3() != null) {
                wardZoneDTO.setAreaDivision3(entity.getCodWard3());
            }
            if (entity.getCodWard4() != null) {
                wardZoneDTO.setAreaDivision4(entity.getCodWard4());
            }
            if (entity.getCodWard5() != null) {
                wardZoneDTO.setAreaDivision5(entity.getCodWard5());
            }
        }

        return wardZoneDTO;
    }
	
	 	@Override
	    @Transactional
	    @WebMethod(exclude = true)
	    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
	            throws CloneNotSupportedException {
	        Map<Long, Double> chargeMap = new HashMap<>();
	        final WSRequestDTO requestDTO = new WSRequestDTO();
	        chargeMap = roadCuttingLOICharges(requestDTO, applicationId, orgId, serviceId);
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

	    private Map<Long, Double> roadCuttingLOICharges(final WSRequestDTO requestDTO, final long applicationId,
	            final long orgId, final long serviceId) throws CloneNotSupportedException {

	        final Map<Long, Double> chargeMap = new HashMap<>();
	        final List<RoadCuttingRateMaster> chargeModelList = new ArrayList<>();
	        List<RoadCuttingRateMaster> requiredCharges = new ArrayList<>();
	        RoadCuttingRateMaster tempRate = null;
	        RoadCuttingRateMaster rateMaster = null;
	        final Organisation org = new Organisation();
	        org.setOrgid(orgId);
	        // [START] BRMS call initialize model
	        RoadCuttingEntity entity = roadCuttingRepository.findByOrgIdAndApmApplicationId(orgId, applicationId);
	       requiredCharges = getChargesForRoadCuttingRateMaster(requestDTO,orgId, MainetConstants.RoadCuttingConstant.RCP);

	        String roadDiggingCharge = MainetConstants.BLANK;
	        String collectionCharge = MainetConstants.BLANK;
	        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC, 2, org);
	        for (final LookUp lookup : subCategryLookup) {
	            if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.RDC)) {
	                roadDiggingCharge = lookup.getDescLangFirst();
	                
	            }
	            if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.CO)) {
	            	collectionCharge = lookup.getDescLangFirst();
	                break;
	            }
	        }
	        String key = MainetConstants.BLANK;
	        
	        Map<String,BigDecimal> roadMap = new HashMap<>();
	        for (final RoadCuttingRateMaster actualRate : requiredCharges) {
	            if (actualRate.getTaxSubCategory().equals(roadDiggingCharge) || actualRate.getTaxSubCategory().equals(collectionCharge)) {
	                if (!entity.getRoadRouteList().isEmpty()) {
	                    for (final RoadRouteDetailEntity road : entity.getRoadRouteList()) {
	                            rateMaster = null;
	                            tempRate = (RoadCuttingRateMaster) actualRate.clone();
	                            rateMaster = populateChargeModel( road, tempRate);
	                            chargeModelList.add(rateMaster);
	                            
	                            if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
		                            key = CommonMasterUtility.getCPDDescription(road.getTypeOfTechnology().longValue(),
		    								PrefixConstants.D2KFUNCTION.ENGLISH_DESC);
		                            if(roadMap.containsKey(key)) {
		                            	
		                            	BigDecimal totalRoadLength = roadMap.get(key).add(setDefaultIfNullToZero(road.getLength()));
		                            	roadMap.put(key, setDefaultIfNullToZero(totalRoadLength));
		                            	
		                            }else {
		                            	roadMap.put(key, setDefaultIfNullToZero(road.getLength()));
		                            }
	                            }
	                            else{
	                            	roadMap.put(key, setDefaultIfNullToZero(road.getLength()));
	                            }

	                    }
	                }
	            }
	        }
	        
	      //SECURITY DEPOSIT  
	        
	        for (final RoadCuttingRateMaster actualRate : requiredCharges) {
				if (!actualRate.getTaxSubCategory().equals(roadDiggingCharge) && !actualRate.getTaxSubCategory().equals(collectionCharge)) {
					roadMap.forEach((cuttingTechnique, value) -> {
						 try {
							RoadCuttingRateMaster securityDeposit = (RoadCuttingRateMaster) actualRate.clone();
							securityDeposit.setTotalRoadLength(value.doubleValue());
							securityDeposit.setRoadCuttingTechnique(cuttingTechnique);
							securityDeposit.setOrgId(orgId);
							securityDeposit.setDeptCode(MainetConstants.RoadCuttingConstant.RCP);
							
							securityDeposit.setOrganisationType(
									CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
											orgId,MainetConstants.CommonMasterUi.OTY
											).getDescLangFirst());
							
							securityDeposit.setDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
							securityDeposit.setStartDate(new Date().getTime());
                            chargeModelList.add(securityDeposit);

						} catch (CloneNotSupportedException e) {
							LOGGER.error("Error while setting securityDeposit model",e);
						}
					});
				}
			}  
	        
	        requestDTO.setDataModel(chargeModelList);
	        final WSResponseDTO output = RestClient.callBRMS(requestDTO, ServiceEndpoints.WMS_ROAD_CUTTING_RATE_MASTER);
	        final List<?> response = RestClient.castResponse(output, RoadCuttingRateMaster.class);
	        RoadCuttingRateMaster loiCharges = null;
	        Double baseRate = 0d;
	        double amount;
	        for (final Object rate : response) {

	            loiCharges = (RoadCuttingRateMaster) rate;
	            baseRate = loiCharges.getRoadCuttingCharge();
	            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
	            amount += baseRate;
	            chargeMap.put(loiCharges.getTaxId(), amount);
	        }
	        return chargeMap;
	        // [END]
	    }

	    
	    
	    private  List<RoadCuttingRateMaster> getChargesForRoadCuttingRateMaster(WSRequestDTO requestDTO, Long orgId,
	            String serviceShortCode) {
	        requestDTO.setModelName(MainetConstants.RoadCuttingConstant.ROADCUTTING_RATE_MASTER);
	        List<RoadCuttingRateMaster> requiredCharges = new ArrayList<RoadCuttingRateMaster>();
	        WSResponseDTO response = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
	        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
	            List<Object> RoadCuttingRateMasterList = RestClient.castResponse(response, RoadCuttingRateMaster.class);
	            RoadCuttingRateMaster rateMaster = (RoadCuttingRateMaster) RoadCuttingRateMasterList.get(0);
	            rateMaster.setOrgId(orgId);
	            rateMaster.setServiceCode(serviceShortCode);
	            rateMaster.setChargeApplicableAt(
	                    Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
	                            PrefixConstants.LookUp.CHARGE_MASTER_CAA,new Organisation(orgId)).getLookUpId()));
	            requestDTO.setDataModel(rateMaster);
	            WSResponseDTO res = getApplicableTaxes(requestDTO);

	            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {

	                List<?> rates = castResponse(res, RoadCuttingRateMaster.class);
	                for (Object rate : rates) {
	                    requiredCharges.add((RoadCuttingRateMaster) rate);
	                }
	            } else {
	            	LOGGER.error("Error in Initializing other fields for taxes");
	            }
	        } else {
	        	LOGGER.error("Error in Initializing model");
	        }
	        return requiredCharges;
	    }
	    
	    private  List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {

	        Object dataModel = null;
	        final List<Object> dataModelList = new ArrayList<>();
	        try {
	            if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
	                final List<?> list = (List<?>) response.getResponseObj();
	                for (final Object object : list) {
	                	RoadCuttingRateMaster responseMap = (RoadCuttingRateMaster) object;
	                    final String jsonString = new JSONObject(responseMap).toString();
	                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
	                    dataModelList.add(dataModel);
	                }
	            }

	        } catch (final IOException e) {
	            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
	        }

	        return dataModelList;

	    }
	    
	    
	    private RoadCuttingRateMaster populateChargeModel(RoadRouteDetailEntity road, RoadCuttingRateMaster roadCuttingRateMaster) {
			roadCuttingRateMaster.setNumber(road.getNumbers() != null ? road.getNumbers().longValue():1L);
			roadCuttingRateMaster.setRoadLength(setDefaultIfNull(road.getLength()));
			roadCuttingRateMaster.setRoadBreadth(setDefaultIfNull(road.getBreadth()));
			roadCuttingRateMaster.setRoadHeight(setDefaultIfNull(road.getHeight()));
			roadCuttingRateMaster.setRoadDiemeter(setDefaultIfNull(road.getDaimeter()));
			Organisation org = new Organisation();
			org.setOrgid(road.getOrgId());
			if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
				roadCuttingRateMaster.setRoadCuttingTechnique(CommonMasterUtility.getCPDDescription(road.getTypeOfTechnology().longValue(), PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
			}
			roadCuttingRateMaster.setRoadType(CommonMasterUtility.getCPDDescription(road.getRoadType().longValue(), PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
			
			roadCuttingRateMaster.setOrgId(road.getOrgId());
			roadCuttingRateMaster.setDeptCode(MainetConstants.RoadCuttingConstant.RCP);
			
			roadCuttingRateMaster.setOrganisationType(
					CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
							road.getOrgId(),MainetConstants.CommonMasterUi.OTY
							).getDescLangFirst());
			
			roadCuttingRateMaster.setDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
			roadCuttingRateMaster.setStartDate(new Date().getTime());
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
			List<LookUp> lookup = CommonMasterUtility.getLookUps(MainetConstants.RoadCuttingConstant.OPR, org);
			for(LookUp lookup1 : lookup){
				if(lookup1.getLookUpId() == road.getRoadCuttingEntity().getPurpose()){
					roadCuttingRateMaster.setPurposeType(lookup1.getLookUpCode());
				}
			}
			}
			return roadCuttingRateMaster;
		}
	    
	    private double setDefaultIfNull(BigDecimal value) {
	    	if (value == null) {
	    		return 1D;
	    	}else {
	    		return value.doubleValue();
	    	}
	    }
	    
	    private BigDecimal setDefaultIfNullToZero(BigDecimal value) {
	    	if (value == null) {
	    		return BigDecimal.ZERO;
	    	}else {
	    		return value;
	    	}
	    }
	    
	 // Add Project Location Image Save Code
		private void uploadLocationImage(RoadCuttingDto roadCutting) {
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(roadCutting.getOrgId());
			requestDTO.setStatus(MainetConstants.FlagA);

			requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
			requestDTO.setUserId(roadCutting.getCreatedBy());

			requestDTO.setIdfId(MainetConstants.RoadCuttingConstant.PROJECT_LOCATION + MainetConstants.DOUBLE_BACK_SLACE
					+ roadCutting.getApplicationId());

			fileUploadService.doMasterFileUpload(roadCutting.getProjectLocation(), requestDTO);

		}
		
		@Override
	    @Transactional(readOnly = true)
		 public List<RoadCuttingDto> getNOCDepartmentTable(Long orgId) {
			Organisation org =new Organisation();
			org.setOrgid(orgId);
		Long activeFlag=	CommonMasterUtility.getValueFromPrefixLookUp("A", "ACN",
					org).getLookUpId();
	        
	        List<Object[]> detailList = roadCuttingRepository.getServiceAndDeptName(orgId,activeFlag);
            List<RoadCuttingDto> cuttingDtosList  = new ArrayList<>();
	        for (Object[] masterEntity : detailList) {
	        	RoadCuttingDto roadCuttingDto = new RoadCuttingDto();
	        	roadCuttingDto.setDeptId((Long) masterEntity[0]);
	        	roadCuttingDto.setDpDeptdesc((String) masterEntity[1]);
	        	roadCuttingDto.setSmShortdesc((String) masterEntity[3]);
	        	roadCuttingDto.setServiceId((Long) masterEntity[5]);
	        	
	        	cuttingDtosList.add(roadCuttingDto);
	        }
	        return cuttingDtosList;
	    }

		@Override
		public List<RoadCuttingDto> getNOCViewStatusDetail(List<RoadCuttingDto> dtos,Long applicationId) {
			
			for (RoadCuttingDto masterEntity : dtos) {
			ServiceMaster smm = serviceMasterService.getServiceMaster(masterEntity.getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			
			masterEntity.setApplicationId(applicationId);

			ApplicationMetadata applicationData = new ApplicationMetadata();
			final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
			applicationData.setApplicationId(applicationId);
			applicationData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			applicationData.setIsCheckListApplicable(false);
			applicantDetailDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			applicantDetailDto.setServiceId(masterEntity.getServiceId());
			applicantDetailDto.setDepartmentId(smm.getTbDepartment().getDpDeptid());

			WorkflowMas workFlowMas = workflowTyepResolverService
					.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
							smm.getTbDepartment().getDpDeptid(), smm.getSmServiceId(), null, null, null, null, null);



			WorkflowRequest workflowRequest = requestService.findByApplicationId(applicationId,
					workFlowMas.getWfId());

			if(workflowRequest!= null) {
				if ((masterEntity.getDeptId().equals(workFlowMas.getDepartment().getDpDeptid()))
						&& (masterEntity.getServiceId().equals(workFlowMas.getService().getSmServiceId()))) {

					masterEntity.setStatus(workflowRequest.getStatus());
				}
			}
			}
			return dtos;
		}

		@Override
	    @POST
	    @Path("/fetchCheckListForRoadCutting")
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<DocumentDetailsVO> fetchCheckList(RoadCuttingDto cuttingDto) {
	        final WSRequestDTO wsdto = new WSRequestDTO();
	        List<DocumentDetailsVO> docs = null;
	        wsdto.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
	        WSResponseDTO response = brmsCommonService.initializeModel(wsdto);;
	        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
	            List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
	            CheckListModel checkListModel = (CheckListModel) checklist.get(0);
	            docs = populateCheckListModel(cuttingDto, checkListModel, wsdto);
	        }
	        return docs;       
	    }
		
		 private List<DocumentDetailsVO> populateCheckListModel(RoadCuttingDto cuttingDto, CheckListModel checklistModel,
		            final WSRequestDTO wsdto) {
		        Map<Long, DocumentDetailsVO> docMap = new HashMap<>(0);
		        List<DocumentDetailsVO> checklist = new ArrayList<>(0);
		        checklistModel.setOrgId(cuttingDto.getOrgId());
		        checklistModel.setServiceCode(cuttingDto.getServiceCode());
		        wsdto.setDataModel(checklistModel);
		        WSResponseDTO response = brmsCommonService.getChecklist(wsdto);
		        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
		            @SuppressWarnings("unchecked")
		            List<DocumentDetailsVO> checklistData = (List<DocumentDetailsVO>) response.getResponseObj();
		            // Defect #91001
		            long count = 1;
		            for (DocumentDetailsVO doc : checklistData) {
		                doc.setDocumentSerialNo(count);
		                count++;
		                docMap.put(doc.getDocumentId(), doc);
		            }
		        }
		        if (!docMap.isEmpty()) {
		            docMap.forEach((key, value) -> {
		                checklist.add(value);
		            });
		            checklist.sort(Comparator.comparing(DocumentDetailsVO::getDocumentSerialNo));// Sorting List by collection
		                                                                                         // sequence
		        }
		        return checklist;
		    }

	@POST
	@Path("/saveZoneWise")
	@ApiOperation(value = "save Road Cutting Application", notes = "save Road Cutting Application", response = RoadCuttingDto.class)
	@Override
	@Transactional
	public void saveZoneWiseDetails(RoadCuttingDto dto) {
		/* applicant Information */
		RoadCuttingEntity entity = new RoadCuttingEntity();
		entity.setRcId(dto.getRcId());
		entity.setApplicantCompName1(dto.getApplicantCompName1());
		entity.setCompanyAddress1(dto.getCompanyAddress1());
		entity.setPersonName1(dto.getPersonName1());
		entity.setPersonAddress1(dto.getPersonAddress1());
		entity.setFaxNumber1(dto.getFaxNumber1());
		entity.setTelephoneNo1(dto.getTelephoneNo1());
		entity.setPersonMobileNo1(dto.getPersonMobileNo1());
		entity.setPersonEmailId1(dto.getPersonEmailId1());
		entity.setAlterContact1(dto.getAlterContact1());
		/* local office detail */
		entity.setCompanyName2(dto.getCompanyName2());
		entity.setCompanyAddress2(dto.getCompanyAddress2());
		entity.setPersonName2(dto.getPersonName2());
		entity.setPersonAddress2(dto.getPersonAddress2());
		entity.setFaxNumber2(dto.getFaxNumber2());
		entity.setTelephoneNo2(dto.getTelephoneNo2());
		entity.setPersonMobileNo2(dto.getPersonMobileNo2());
		entity.setPersonEmailId2(dto.getPersonEmailId2());
		entity.setAlterContact2(dto.getAlterContact2());
		/* contractor datails */
		entity.setContractorName(dto.getContractorName());
		entity.setContractorAddress(dto.getContractorAddress());
		entity.setContracterContactPerMobileNo(dto.getContracterContactPerMobileNo());
		entity.setContractorEmailId(dto.getContractorEmailId());
		entity.setContractorContactPerName(dto.getContractorContactPerName());
		entity.setTotalCostOfproject(dto.getTotalCostOfproject());
		entity.setEstimteForRoadDamgCharge(dto.getEstimteForRoadDamgCharge());
		entity.setApmApplicationId(dto.getApplicationId());
		entity.setCodWard1(dto.getCodWard1());
		entity.setCodWard2(dto.getCodWard2());
		entity.setCodWard3(dto.getCodWard3());
		entity.setCodWard4(dto.getCodWard4());
		entity.setCodWard5(dto.getCodWard5());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setUpdatedDate(dto.getUpdatedDate());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setOrgId(dto.getOrgId());
		entity.setLgIpMac(dto.getLgIpMac());
		entity.setLgIpMacUpd(dto.getLgIpMacUpd());
		/** Applicant Info*/
		/*entity.setApplicantAddress(dto.getApplicantDetailDto().getBuildingName());
		entity.setApplicantAreaName(dto.getApplicantDetailDto().getAreaName());
		entity.setApplicantHouseNo(dto.getApplicantDetailDto().getHouseNumber());
		entity.setApplicantMobileNo(dto.getApplicantDetailDto().getMobileNo() != null ? Long.parseLong(dto.getApplicantDetailDto().getMobileNo()) : null);
		entity.setApplicantName(dto.getApplicantDetailDto().getApplicantFirstName());
		entity.setApplicantMName(dto.getApplicantDetailDto().getApplicantMiddleName());
		entity.setApplicantLName(dto.getApplicantDetailDto().getApplicantLastName());*/
		
		entity.setPurpose(dto.getPurpose());
		entity.setPurposeValue(dto.getPurposeValue());
		
		// Asign engineer
		entity.setRcAssignEng(dto.getRcAssignEng());
		
		roadCuttingRepository.save(entity);
		
		RoadRouteDetailsDto dtos = dto.getScrutinyPerformList().get(0);
		RoadRouteDetailEntity roaddetailsEntity = new RoadRouteDetailEntity();
		roaddetailsEntity.setBreadth(dtos.getBreadth());
		roaddetailsEntity.setLength(dtos.getLength());
		roaddetailsEntity.setHeight(dtos.getHeight());
		roaddetailsEntity.setDaimeter(dtos.getDiameter());
		roaddetailsEntity.setNumbers(dtos.getNumbers());
		Organisation org = new Organisation();
		org.setOrgid(dto.getOrgId());
		if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
			roaddetailsEntity.setRcdQuantiy(dtos.getQuantity());
			roaddetailsEntity.setTypeOfTechnology(dtos.getTypeOfTechnology());
		}
		roaddetailsEntity.setRoadRouteDesc(dtos.getRoadRouteDesc());
		roaddetailsEntity.setRoadType(dtos.getRoadType());
		roaddetailsEntity.setRoadAreaName(dtos.getRoadAreaName());
		roaddetailsEntity.setCreatedBy(dtos.getCreatedBy());
		roaddetailsEntity.setCreatedDate(dtos.getCreatedDate());
		roaddetailsEntity.setUpdatedBy(dtos.getUpdatedBy());
		roaddetailsEntity.setUpdatedDate(dtos.getUpdatedDate());
		roaddetailsEntity.setLgIpMac(dtos.getLgIpMac());
		roaddetailsEntity.setLgIpMacUpd(dtos.getLgIpMacUpd());
		roaddetailsEntity.setOrgId(dtos.getOrgId());
		roaddetailsEntity.setCodZoneward1(dtos.getCodZoneward1());
		roaddetailsEntity.setCodZoneward2(dtos.getCodZoneward2());
		roaddetailsEntity.setCodZoneward3(dtos.getCodZoneward3());
		roaddetailsEntity.setCodZoneward4(dtos.getCodZoneward4());
		roaddetailsEntity.setCodZoneward5(dtos.getCodZoneward5());
		roaddetailsEntity.setRcdEndpoint(dtos.getRcdEndpoint());
		roaddetailsEntity.setRcdCompletionDate(dtos.getRcdCompletionDate());
		roaddetailsEntity.setRcdStartlogitude(dtos.getRcdStartlogitude());
		roaddetailsEntity.setRcdStartlatitude(dtos.getRcdStartlatitude());
		roaddetailsEntity.setRcdEndlogitude(dtos.getRcdEndlogitude());
		roaddetailsEntity.setRcdEndlatitude(dtos.getRcdEndlatitude());
		roaddetailsEntity.setRcdQuantiy(dtos.getQuantity());
		roaddetailsEntity.setRcdId(dtos.getRcdId());
		roaddetailsEntity.setApmAppStatus(dtos.getApmAppStatus());
		roaddetailsEntity.setRefId(dtos.getRefId());
        RoadCuttingEntity roadCuttingEntity =new RoadCuttingEntity();
        roadCuttingEntity.setRcId(dto.getRcId());
        roaddetailsEntity.setRoadCuttingEntity(roadCuttingEntity);
        roaddetailsEntity.setUpdatedBy(dto.getUpdatedBy());
        roaddetailsEntity.setUpdatedDate(dto.getUpdatedDate());
		roadRouteDetailRepository.save(roaddetailsEntity);
	}

	@POST
	@Path("/saveZoneWise")
	@ApiOperation(value = "save Road Cutting Application", notes = "save Road Cutting Application", response = RoadCuttingDto.class)
	@Override
	@Transactional
	public void saveZoneWiseStatus(RoadCuttingDto dto) {
		RoadRouteDetailsDto dtos = dto.getScrutinyPerformList().get(0);
		RoadRouteDetailEntity roaddetailsEntity = new RoadRouteDetailEntity();
		roaddetailsEntity.setRcdId(dtos.getRcdId());
		roaddetailsEntity.setApmAppStatus("I");
		roaddetailsEntity.setRefId(dto.getReferenceId());
        RoadCuttingEntity entity =new RoadCuttingEntity();
        entity.setRcId(dto.getRcId());
        roaddetailsEntity.setRoadCuttingEntity(entity);
		roadRouteDetailRepository.save(roaddetailsEntity);
	}
	
	@Override
	@Transactional
	public void updateRefId(RoadCuttingDto cuttingDto, long zone) {
		for(RoadRouteDetailsDto cuttingDto1 : cuttingDto.getScrutinyPerformList()){
			if(cuttingDto1.getApmAppStatus() == null && cuttingDto1.getCodZoneward1() == zone){
		/*RoadRouteDetailsDto details=cuttingDto.getScrutinyPerformList().get(0);*/
		roadRouteDetailRepository.updateRefId(cuttingDto.getReferenceId(),cuttingDto1.getRcdId(),"I");
		break;
			}
		}
	}
	
	@Override
    public RoadCuttingDto create(final RoadCuttingDto roadCuttingdto) {
        String WPCcode = "WO";
        String FinancialYear = null;
        String workorderNo;
        final Long EmpId = UserSession.getCurrent().getEmployee().getEmpId();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long LanguageId = Long.valueOf(UserSession.getCurrent().getLanguageId());
        final Long WoServiceId = roadCuttingdto.getWoServiceId();
        final Long WoApplicationId = roadCuttingdto.getWoApplicationId();
        final Long woId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER", "WO_ID", orgid, null, null);
        final Long WoDeptId = roadCuttingdto.getWoDeptId();
        final String MacAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
        final TbWorkOrderEntity tbWorkOrderEntity = new TbWorkOrderEntity();
        final TbDepartment dept = tbDepartmentService.findById(WoDeptId);
        
       // RoadCuttingMapper.mapTbWorkOrderToTbWorkOrderEntity(roadCuttingdto, tbWorkOrderEntity);
        try {
			BeanUtils.copyProperties(tbWorkOrderEntity, roadCuttingdto);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        final TbWorkOrderDetailEntity tbWorkOrderDetailEntity = new TbWorkOrderDetailEntity();
        tbWorkOrderEntity.setLmoddate(new Date());
        tbWorkOrderEntity.setUpdatedDate(new Date());
        tbWorkOrderEntity.setWoOrderDate(new Date());
        tbWorkOrderEntity.setUserId(EmpId);
        tbWorkOrderEntity.setOrgid(orgid);
        tbWorkOrderEntity.setLangId(LanguageId);
        tbWorkOrderEntity.setLgIpMac(MacAddress);
        tbWorkOrderEntity.setWoId(woId);
        tbWorkOrderEntity.setWoDeptId(WoDeptId);
       /* final List<LookUp> lookUpList = CommonMasterUtility.getListLookup("WPC", UserSession.getCurrent().getOrganisation());
        for (final LookUp Lookup1 : lookUpList) {

            if (Lookup1.getLookUpId() == tbWorkOrder.getWoAllocation().longValue()) {
                WPCcode = Lookup1.getLookUpCode();
            }
        }*/
        try {
            FinancialYear = Utility.getFinancialYearFromDate(new Date());
        } catch (final Exception e) {

            LOGGER.error("error in getFinyear" + e);
        }

        workorderNo = WPCcode + MainetConstants.operator.FORWARD_SLACE + dept.getDpDeptcode().toUpperCase() + MainetConstants.operator.FORWARD_SLACE + FinancialYear + MainetConstants.operator.FORWARD_SLACE
                +  String.format("%04d", woId);
        tbWorkOrderEntity.setWoOrderNo(workorderNo);

        tbWorkOrderJpaRepository.save(tbWorkOrderEntity);
        for (final TbApprejMas WorkOrderRemark : roadCuttingdto.getLoaRemarklist()) {
            tbWorkOrderDetailEntity.setWdApplicationId(WoApplicationId);
            tbWorkOrderDetailEntity.setWdServiceId(WoServiceId);
            tbWorkOrderDetailEntity.setTbWorkOrder(tbWorkOrderEntity);
            tbWorkOrderDetailEntity.setLmoddate(new Date());
            tbWorkOrderDetailEntity.setUpdatedDate(new Date());
            tbWorkOrderDetailEntity.setUserId(EmpId);
            tbWorkOrderDetailEntity.setOrgid(orgid);
            tbWorkOrderDetailEntity.setLangId(LanguageId);
            tbWorkOrderDetailEntity.setLgIpMac(MacAddress);
            tbWorkOrderDetailEntity.setWdRemarkId(WorkOrderRemark.getIsSelected());
            final Long woDId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER_DETAIL", "WD_ID", orgid, null,
                    null);
            tbWorkOrderDetailEntity.setWdId(woDId);
            tbWorkOrderDetailJpaRepository.save(tbWorkOrderDetailEntity);
        }
        
        roadCuttingdto.setlModDate(new Date());
        roadCuttingdto.setUpdatedDate(new Date());
        roadCuttingdto.setWoOrderDate(new Date());
        roadCuttingdto.setUserId(EmpId);
        roadCuttingdto.setOrgId(orgid);
        roadCuttingdto.setLangId(LanguageId);
        roadCuttingdto.setLgIpMac(MacAddress);
        roadCuttingdto.setWoId(woId);
        roadCuttingdto.setWoDeptId(WoDeptId);
        
        //return RoadCuttingMapper.mapTbWorkOrderEntityToTbWorkOrder(tbWorkOrderEntitySaved);
        return roadCuttingdto;
    }

}
