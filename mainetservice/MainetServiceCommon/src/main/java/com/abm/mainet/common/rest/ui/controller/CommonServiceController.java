package com.abm.mainet.common.rest.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cfc.checklist.domain.TbDocumentGroupEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbComparentMasEntity;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.dto.PrefixAppRequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.PrefixDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.mapper.TbComparentMasServiceMapper;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.TbComparentMasJpaRepository;
import com.abm.mainet.common.master.repository.TbDocumentGroupRepository;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;

import io.swagger.annotations.ApiOperation;

/**
 * @author Vivek.Kumar
 * @since 15-Dec-2015
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/commonService")
public class CommonServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceController.class);
	private static final String INITIALIZE_MODEL_EXCEPTION = "Exception while initilize model";
	private static final String GET_CHECKLIST_EXCEPTION = "Exception while fatching checklist";
	private static final String SERVICE_CODE_CANT_BE_NULL = "service code can not be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId can not be zero(0)";
	private static final String DATA_MODEL_CANT_BE_NULL = "WsRequestDTO field named dataModel cannot be null.";
	private static final String LOOKUP_NOT_FOUND_FOR_APL = "LookUps not found for Prefix APL for orgId=";

	@Resource
	private CommonService commonService;

	@Resource
	private TbComparamMasService tbComparamMasService;

	@Resource
	private IFileUploadService fileUploadService;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private TbDocumentGroupRepository documentGroupRepository;

	@Resource
	private ServiceMasterRepository serviceMasterRespository;

	@Autowired
	private TbOrganisationService organisationService;

	@Autowired
	private LocationMasJpaRepository locationMasJpaRepository;
	
    @Resource
    private TbComparentMasJpaRepository tbComparentMasJpaRepository;
    

    @Resource
    private TbComparentMasServiceMapper tbComparentMasServiceMapper;

	


	/**
	 * use for load prefix to portal
	 * 
	 * @param type
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/viewPrefixDetailsByType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object getViewPrefixDetailsByType(@RequestBody final String type, final HttpServletRequest request,
			final BindingResult bindingResult) {

		List<ViewPrefixDetails> prefixDetails = Collections.emptyList();
		if (!StringUtils.isEmpty(type)) {
			prefixDetails = tbComparamMasService.getViewPrefixDetailsByType(type);

		} else {

			LOGGER.error("Prefix Type not pass ");
		}
		final PrefixDTO prefixDTO = new PrefixDTO();
		prefixDTO.setViewPrefixDetailsByType(prefixDetails);
		return prefixDTO;

	}

	/**
	 * use for load prefix to portal
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/allStartupPrefix", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object getAllStartupPrefix(final HttpServletRequest request) {
		List<String> startupPrefix = Collections.emptyList();
		startupPrefix = tbComparamMasService.getAllStartupPrefix();

		final PrefixDTO prefixDTO = new PrefixDTO();
		prefixDTO.setAllStartupPrefix(startupPrefix);
		return prefixDTO;

	}

	/**
	 * use for load prefix to portal
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/nonReplicatePrefix", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object getNonReplicatePrefix(final HttpServletRequest request) {
		List<String> nonReplicatePrefix = Collections.emptyList();
		nonReplicatePrefix = tbComparamMasService.getNonReplicatePrefix();
		final PrefixDTO prefixDTO = new PrefixDTO();
		prefixDTO.setNonReplicatePrefix(nonReplicatePrefix);
		return prefixDTO;

	}

	@RequestMapping(value = "/retriveNonHData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object getPrefixNHData(@RequestBody final PrefixAppRequestDTO requestDTO, final HttpServletRequest request) {
		List<LookUp> lookupList = null;

		if (requestDTO != null) {
			final String lookUpCode = requestDTO.getLookUpCode();

			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDTO.getOrgId());

			lookupList = CommonMasterUtility.getLookUps(lookUpCode, organisation);

			if ((lookupList == null) || lookupList.isEmpty()) {
				LOGGER.error("prefix not found for:[prefix -->" + lookUpCode + " and Organization-->"
						+ organisation.getONlsOrgname() + "]");
			}

		}
		return lookupList;
	}

	@RequestMapping(value = "/retriveHData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object getPrefixHData(@RequestBody final PrefixAppRequestDTO requestDTO, final HttpServletRequest request) {
		List<LookUp> categorylist = null;

		if (requestDTO != null) {
			final String lookUpCode = requestDTO.getLookUpCode();

			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDTO.getOrgId());

			categorylist = CommonMasterUtility.getLevelData(lookUpCode, requestDTO.getLevel(), organisation);
		}
		return categorylist;

	}

	@RequestMapping(value = "/getPrefixLabels", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object getPrefixLabels(@RequestBody final PrefixAppRequestDTO requestDTO, final HttpServletRequest request) {
		List<LookUp> lookupLabels = null;

		if (requestDTO != null) {
			final String lookUpCode = requestDTO.getLookUpCode();

			final Organisation organisation = new Organisation(requestDTO.getOrgId());
			lookupLabels = ApplicationSession.getInstance().getHierarchicalLookUp(organisation, lookUpCode)
					.get(lookUpCode);
		}
		return lookupLabels;

	}

	@RequestMapping(value = "/getServerDate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object getShiftsBasedOnDate() {
		return new Date();
	}

	@RequestMapping(value = "/getLocationWardZone/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object getDepartmentListByOrgId(@PathVariable("orgId") final Long orgId) {
		final List<WardZoneDTO> locationList = iLocationMasService.findlocationByOrgId(orgId);
		return locationList;
	}

	@RequestMapping(value = "/initializeModel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public WSResponseDTO initializeModel(@RequestBody WSRequestDTO requestDTO) {
		LOGGER.info("brms common service initialize model execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		} catch (Exception ex) {
			throw new FrameworkException(INITIALIZE_MODEL_EXCEPTION, ex);
		}
		LOGGER.info("brms common service initialize model execution End..");
		return responseDTO;
	}

	@RequestMapping(value = "/checkList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public WSResponseDTO getChecklist(@RequestBody WSRequestDTO requestDTO) {
		LOGGER.info("brms common service get checklist execution start..");
		WSResponseDTO responseDTO = new WSResponseDTO();
		try {
			if (isChecklistApplicableForRequestOrg(requestDTO, responseDTO)) {

				// if checklist define at UDHD then set super organization OrgId as told by
				// rajesh sir
				CheckListModel checkList = (CheckListModel) CommonMasterUtility.castRequestToDataModel(requestDTO,
						CheckListModel.class);
				Organisation org = new Organisation();
				org.setOrgid(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
				LookUp lookup = CommonMasterUtility.getDefaultValue(PrefixConstants.LookUpPrefix.CHK, org);
				if (lookup.getLookUpCode() != null && lookup.getLookUpCode().equals(MainetConstants.FlagY)) {
					LOGGER.info("Checklist is define at UDHD level");
					checkList.setOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
					requestDTO.setDataModel(checkList);
				} else {
					LOGGER.info("Checklist is define at organization level");
				}
				responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.CHECKLIST_URL);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
					if (StringUtils.isNotBlank(checkList.getFactor3())
							&& StringUtils.equals(checkList.getFactor3(), "Y")) {
						StringBuilder documentGroupFactor = new StringBuilder();
						documentGroupFactor.append("D");
						documentGroupFactor.append(responseDTO.getDocumentGroup());
						responseDTO.setDocumentGroup(documentGroupFactor.toString());
					}
					CheckListModel checkListModel = (CheckListModel) CommonMasterUtility
							.castRequestToDataModel(requestDTO, CheckListModel.class);
					preparedDocuments(responseDTO, checkListModel);
				} else if (MainetConstants.WebServiceStatus.FAIL.equalsIgnoreCase(responseDTO.getWsStatus())) {
					throw new FrameworkException(
							"Exception while fetch checklist from BRMS : " + responseDTO.getErrorMessage());
				}
			} else {
				responseDTO.setWsStatus(MainetConstants.CommonConstants.NA);
				LOGGER.info("Checklist is not applicable..!");
			}
		} catch (Exception ex) {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(GET_CHECKLIST_EXCEPTION);
			throw new FrameworkException(GET_CHECKLIST_EXCEPTION, ex);
		}
		LOGGER.info("brms common service get checklist execution End..");
		return responseDTO;
	}

	// check is checklist is applicable or not for particular service in service
	// master
	private boolean isChecklistApplicableForRequestOrg(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
		boolean isApplicable = false;
		if (requestDTO.getDataModel() != null) {
			CheckListModel checkListModel = (CheckListModel) CommonMasterUtility.castRequestToDataModel(requestDTO,
					CheckListModel.class);
			String errorMsg = validateChecklistModel(checkListModel);
			if (errorMsg.isEmpty()) {
				isApplicable = isChecklistApplicable(checkListModel.getServiceCode(), checkListModel.getOrgId());
			} else {
				responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
				responseDTO.setErrorMessage(errorMsg);
				throw new FrameworkException("Invalid isChecklistApplicable " + errorMsg);
			}
		} else {
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
			responseDTO.setErrorMessage(DATA_MODEL_CANT_BE_NULL);
			throw new NullPointerException(DATA_MODEL_CANT_BE_NULL);
		}
		return isApplicable;
	}

	// validate checklist model data
	private String validateChecklistModel(CheckListModel checkListModel) {
		String errorMsg = StringUtils.EMPTY;
		if (checkListModel.getServiceCode() == null || checkListModel.getServiceCode().isEmpty()) {
			errorMsg = SERVICE_CODE_CANT_BE_NULL + ",";
		}
		if (checkListModel.getOrgId() == 0l) {
			errorMsg = ORG_ID_CANT_BE_ZERO;
		}
		return errorMsg;
	}

	// get documents details by document group
	private WSResponseDTO preparedDocuments(WSResponseDTO response, CheckListModel checkListModel) {
		LOGGER.info("preparing document from document group master execution start..");
		if (checkListModel != null) {
			List<String> docGroupList = Pattern.compile(MainetConstants.operator.COMMA)
					.splitAsStream(response.getDocumentGroup()).collect(Collectors.toList());
			List<TbDocumentGroupEntity> docList = documentGroupRepository.fetchCheckListByDocumentGroup(docGroupList,
					checkListModel.getOrgId());
			if (docList == null || docList.isEmpty()) {
				throw new FrameworkException("No Document found for serviceCode= " + checkListModel.getServiceCode()
						+ " and orgId= " + checkListModel.getOrgId() + " and documntGroup=" + docGroupList);
			} else {
				response.setResponseObj(prepareCheckList(docList, checkListModel.getOrgId()));
			}
		} else {
			throw new FrameworkException("Exception while casting checklist model after checklist response");
		}

		return response;
	}

	// prepare document details
	private List<DocumentDetailsVO> prepareCheckList(final List<TbDocumentGroupEntity> checkList, long orgId) {
		LOGGER.info("preparing checklist details execution start..");
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		List<DocumentDetailsVO> finalCheckList = null;
		DocumentDetailsVO doc = null;
		if (checkList != null && !checkList.isEmpty()) {
			finalCheckList = new ArrayList<>();
			for (final TbDocumentGroupEntity checklistData : checkList) {
				doc = new DocumentDetailsVO();
				doc.setDocumentId(checklistData.getDgId());
				doc.setDocumentSerialNo(checklistData.getDocSrNo());
				doc.setDoc_DESC_ENGL(checklistData.getDocName());
				doc.setDoc_DESC_Mar(checklistData.getDocName());
				if (checklistData.getCcmValueset() != null) {
					doc.setCheckkMANDATORY(CommonMasterUtility
							.getNonHierarchicalLookUpObject(checklistData.getCcmValueset(), org).getLookUpCode());
				}
				finalCheckList.add(doc);
			}
		}
		return finalCheckList;
	}

	public boolean isChecklistApplicable(String serviceShortCode, long orgId) {
		Long smCheckListVerifyId = serviceMasterRespository.isCheckListApplicable(serviceShortCode, orgId);
		if (smCheckListVerifyId == null || smCheckListVerifyId == 0) {
			throw new NullPointerException("No record found from TB_SERVICES_MST for serviceShortCode="
					+ serviceShortCode + "and orgId=" + orgId + "OR sm_chklst_verify column found null");
		}
		String flag = StringUtils.EMPTY;

		// get APL prefix from default organization
		Organisation defaultOrg = organisationService.findDefaultOrganisation();
		List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", defaultOrg);
		if (lookUps == null || lookUps.isEmpty()) {
			throw new NullPointerException(
					LOOKUP_NOT_FOUND_FOR_APL + defaultOrg.getONlsOrgname() + ", orgId:" + defaultOrg.getOrgid());
		}
		for (LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == smCheckListVerifyId) {
				flag = lookUp.getLookUpCode();
				break;
			}
		}
		if (flag.isEmpty()) {
			throw new IllegalArgumentException(
					"conflicts! Prefix APL ids does'nt match to id found from TB_SERVICES_MST");
		}
		return MainetConstants.APPLICABLE.equalsIgnoreCase(flag) ? true : false;
	}

	@RequestMapping(value = "/getDeptIdByServiceShortName/{orgId}/{serviceShortCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Long getDeptIdByServiceShortName(@PathVariable("orgId") Long orgId,
			@PathVariable("serviceShortCode") String serviceShortCode) {
		LOGGER.info("getDeptIdByServiceShortName() method execution Starts");
		if (orgId != null && orgId != 0 && !StringUtils.isEmpty(serviceShortCode)) {
			final ServiceMaster master = serviceMasterRespository.getServiceMasterByShrotName(serviceShortCode, orgId);
			if (master != null) {
				return master.getTbDepartment().getDpDeptid();
			} else {
				LOGGER.error("exception occur while fetching service Department Id By ServiceShortName");
				throw new FrameworkException(
						"Service action Url is not configured in Service Master for service Code = "
								+ serviceShortCode);
			}
		} else {
			return orgId;
		}
	}

	@RequestMapping(value = "/getDeptLocation/{orgId}/{deptId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Set<LookUp> getDeptLocation(@PathVariable("orgId") Long orgId, @PathVariable("deptId") Long deptId) {
		Set<LookUp> locations = new HashSet<>();
		List<LocationMasEntity> locationList = locationMasJpaRepository.findAllLocationWithOperationWZMapping(orgId,
				deptId);
		locationList.forEach(location -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(location.getLocNameEng());
			detData.setDescLangSecond(location.getLocNameReg());
			detData.setLookUpId(location.getLocId());
			locations.add(detData);
		});
		return locations;

	}

//for getting Bank payment details from prefix Defect #83529
	@RequestMapping(value = "/getPrefixDataForPayAtCounter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object payAtCounterPrefix(@RequestBody final PrefixAppRequestDTO requestDTO,
			final HttpServletRequest httprequest) {
		final Map<String, List<LookUp>> lookupMap = new HashMap<>(0);
		final Organisation organisation = new Organisation();
		organisation.setOrgid(requestDTO.getOrgId());
		final List<LookUp> bankLookUp = new ArrayList<>(0);
		LookUp bank = null;
		final List<LookUp> payPrefix = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
				organisation);
		lookupMap.put(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, payPrefix);
		final Map<Long, String> banks = ApplicationSession.getInstance().getCustomerBanks();
		if ((banks != null) && !banks.isEmpty()) {
			for (final Entry<Long, String> entry : banks.entrySet()) {
				bank = new LookUp();
				bank.setLookUpId(entry.getKey());
				bank.setLookUpDesc(entry.getValue());
				bank.setDescLangFirst(entry.getValue());
				bankLookUp.add(bank);
			}
		}
		lookupMap.put("BANK", bankLookUp);
		return lookupMap;
	}



	@RequestMapping(value = "/getPrefixLevelData/{prefix}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TbComparentMas> getPrefixLevelData(@PathVariable("prefix") String prefix, @PathVariable("orgId") Long orgId) {

		TbComparamMas tbComparamMas = tbComparamMasService.findComparamDetDataByCpmId(prefix);

		final List<TbComparentMasEntity> entities = tbComparentMasJpaRepository
				.findComparentMasDataById(tbComparamMas.getCpmId(), orgId);
		final List<TbComparentMas> beans = new ArrayList<>();

		for (final TbComparentMasEntity tbComparentMasEntity : entities) {
			beans.add(tbComparentMasServiceMapper.mapTbComparentMasEntityToTbComparentMas(tbComparentMasEntity));

		}
		return beans;

	}

}
