package com.abm.mainet.rts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.repository.TbLoiDetJpaRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.rts.dto.MediaChargeAmountDTO;
import com.abm.mainet.rts.ui.model.WaterRateMaster;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.rts.service.RtsBRMSService")
@Api(value = "/rts-brmsservice")
@Path("/rts-brmsservice")
@Service
public class RtsBRMSServiceImpl implements RtsBRMSService {

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private AuditService auditService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private IChallanService iChallanService;

	@Resource
	private IDepartmentDAO iDepartmentDAO;

	@Resource
	private LocationMasJpaRepository locationMasJpaRepository;

	@Resource
	private ILocationMasService iLocationMasService;
	@Resource
	private BRMSCommonService brmsCommonService;

	@Resource
	private TbTaxMasService taxMasService;

	@Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Resource
	private TbDepartmentService departmentService;

	@Autowired
	private TbDepartmentJpaRepository departmentJpaRepository;

	@Resource
	IFileUploadService fileUploadService;

	@Resource
	private CommonService commonService;

	@Resource
	private ISMSAndEmailService ismsAndEmailService;

	@Resource
	IFinancialYearService iFinancialYearService;

	@Resource
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Resource
	TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Resource
	private TbLoiDetJpaRepository tbLoiDetJpaRepository;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	private static Map<Long, String> dependsOnFactorMap = null;

	private static final Logger LOGGER = Logger.getLogger(RtsBRMSServiceImpl.class);
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
	private final ModelMapper modelMapper;

	public RtsBRMSServiceImpl() {
		this.modelMapper = new ModelMapper();
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@POST
	@Path("/dependentparams")
	@ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
	@Override
	public WSResponseDTO getApplicableTaxes(
			@ApiParam(value = "get dependent paramaters", required = true) WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms RTS getApplicableTaxes execution start..");
		try {
			if (requestDTO.getDataModel() == null) {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
			} else {
				WaterRateMaster waterRateMaster = (WaterRateMaster) CommonMasterUtility
						.castRequestToDataModel(requestDTO, WaterRateMaster.class);
				validateDataModel(waterRateMaster, responseDTO);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					responseDTO = populateOtherFieldsForServiceCharge(waterRateMaster, responseDTO);
				}
			}
		} catch (CloneNotSupportedException | FrameworkException ex) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
		}
		LOGGER.info("brms RTS getApplicableTaxes execution end..");
		return responseDTO;
	}

	@POST
	@Path("/servicecharge")
	@ApiOperation(value = "get service charge", notes = "get service charges", response = WSResponseDTO.class)
	@Override
	public WSResponseDTO getApplicableCharges(
			@ApiParam(value = "get service charges", required = true) WSRequestDTO requestDTO) {
		LOGGER.info("brms RTS getApplicableCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				responseDTO = setServiceChargeDTO(responseDTO);
			} else {
				// throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE +
				// responseDTO.getErrorMessage());
				// added by rajesh.das Defect #78975
				return responseDTO;
			}
		} catch (Exception ex) {
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
		}
		LOGGER.info("brms RTS getApplicableCharges execution End..");
		return responseDTO;
	}

	private WSResponseDTO validateDataModel(WaterRateMaster WaterRateMaster, WSResponseDTO responseDTO) {
		LOGGER.info("validateDataModel execution start..");
		StringBuilder builder = new StringBuilder();
		if (WaterRateMaster.getServiceCode() == null || WaterRateMaster.getServiceCode().isEmpty()) {
			builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
		}
		if (WaterRateMaster.getOrgId() == 0l) {
			builder.append(ORG_ID_CANT_BE_ZERO).append(",");
		}
		if (WaterRateMaster.getChargeApplicableAt() == null || WaterRateMaster.getChargeApplicableAt().isEmpty()) {
			builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
		} else if (!StringUtils.isNumeric(WaterRateMaster.getChargeApplicableAt())) {
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

	public WSResponseDTO populateOtherFieldsForServiceCharge(WaterRateMaster WaterRateMaster, WSResponseDTO responseDTO)
			throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<WaterRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(WaterRateMaster.getServiceCode(),
				WaterRateMaster.getOrgId());
	//	if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), WaterRateMaster.getOrgId(),
					Long.parseLong(WaterRateMaster.getChargeApplicableAt()));
			Organisation organisation = new Organisation();
			organisation.setOrgid(WaterRateMaster.getOrgId());
			listOfCharges = settingAllFields(applicableCharges, WaterRateMaster, organisation);
			responseDTO.setResponseObj(listOfCharges);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		//}
			/*
			 * else { responseDTO.setFree(true);
			 * responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS); }
			 */
		LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
		return responseDTO;
	}

	private List<WaterRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, WaterRateMaster rateMaster,
			Organisation organisation) throws CloneNotSupportedException {
		LOGGER.info("settingAllFields execution start..");
		List<WaterRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			WaterRateMaster WaterRateMaster = (WaterRateMaster) rateMaster.clone();
			// SLD for dependsOnFactor
			String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
					rateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
			String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
					entity.getOrgid(), entity.getTaxApplicable());
			WaterRateMaster.setTaxType(taxType);
			WaterRateMaster.setTaxCode(entity.getTaxCode());
			WaterRateMaster.setChargeApplicableAt(chargeApplicableAt);
			settingTaxCategories(WaterRateMaster, entity, organisation);
			WaterRateMaster.setTaxId(entity.getTaxId());
			WaterRateMaster.setDependsOnFactorList(settingDependsOnFactor(entity.getListOfTbTaxDetMas(), organisation));
			list.add(WaterRateMaster);
		}
		LOGGER.info("settingAllFields execution end..");
		return list;
	}

	private WaterRateMaster settingTaxCategories(WaterRateMaster WaterRateMaster, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				WaterRateMaster.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				WaterRateMaster.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return WaterRateMaster;

	}

	private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setServiceChargeDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, WaterRateMaster.class);
		final List<WaterRateMaster> finalRateMaster = new ArrayList<>();
		for (final Object rate : charges) {
			final WaterRateMaster masterRate = (WaterRateMaster) rate;
			finalRateMaster.add(masterRate);
		}
		final List<MediaChargeAmountDTO> detailDTOs = new ArrayList<>();
		for (final WaterRateMaster rateCharge : finalRateMaster) {
			final MediaChargeAmountDTO chargedto = new MediaChargeAmountDTO();
			chargedto.setChargeAmount(rateCharge.getFlatRate());
			if(rateCharge.getRoadType()!=null && !rateCharge.getRoadType().isEmpty() && !rateCharge.getRoadType().equals("NA")) {
				 chargedto.setMediaType(rateCharge.getTaxSubCategory()+" "+MainetConstants.HYPHEN +" "+ rateCharge.getRoadType());
			}else {
				 chargedto.setMediaType(rateCharge.getTaxSubCategory());
			}
			
			chargedto.setTaxId(rateCharge.getTaxId());
			
			if (CollectionUtils.isNotEmpty(rateCharge.getDependsOnFactorList())) {
				for (String dependFact : rateCharge.getDependsOnFactorList()) {
					if (dependFact != null && StringUtils.equals(dependFact,
							PrefixConstants.LookUpPrefix.ELC)) {
						chargedto.setEditableLoiFlag(MainetConstants.FlagY);
						break;
					}
				}
			}
			 
			detailDTOs.add(chargedto);
		}
		responseDTO.setResponseObj(detailDTOs);
		LOGGER.info("setServiceChargeDTO execution end..");
		return responseDTO;
	}
	
	private List<String> settingDependsOnFactor(List<TbTaxDetMasEntity> taxDetList, Organisation orgId) {

		if (dependsOnFactorMap == null) {
			cacheDependsOnFactors(orgId);
		}
		List<String> dependsOnFactorList = new ArrayList<>();

		if (taxDetList != null) {
			for (TbTaxDetMasEntity entity : taxDetList) {
				if (entity.getStatus().equals(MainetConstants.FlagA)) {
					dependsOnFactorList.add(dependsOnFactorMap.get(entity.getTdDependFact()));
				}
			}
		}

		return dependsOnFactorList;
	}

	private void cacheDependsOnFactors(Organisation organisation) {
		List<LookUp> lookUps = CommonMasterUtility.getListLookup("RDF", organisation);
		dependsOnFactorMap = new HashMap<>();
		for (LookUp lookUp : lookUps) {
			dependsOnFactorMap.put(lookUp.getLookUpId(), lookUp.getLookUpCode());
		}
	}

}
