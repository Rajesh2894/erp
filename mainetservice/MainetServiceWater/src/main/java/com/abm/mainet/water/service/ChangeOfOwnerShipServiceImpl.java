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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.ResponseDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.repository.TbServicesMstJpaRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.water.dao.ChangeOfOwnershipRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.AdditionalOwnerInfo;
import com.abm.mainet.water.domain.ChangeOfOwnerMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.ChangeOfOwnershipDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.ui.model.ChangeOfOwnerShipModel;
import com.abm.mainet.water.utility.WaterCommonUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Rahul.Yadav
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.ChangeOfOwnerShipService")
@Api(value = "/changeownerservice")
@Path("/changeownerservice")
public class ChangeOfOwnerShipServiceImpl implements ChangeOfOwnerShipService {

	@Resource
	private ChangeOfOwnershipRepository iChangeOfOwnershipRepository;

	@Resource
	private ICFCApplicationAddressService addressService;

	@Resource
	private ICFCApplicationMasterService applicationMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	ICFCApplicationMasterService cfcService;

	@Autowired
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Autowired
	private TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository;

	@Autowired
	private TbServicesMstJpaRepository tbServicesMstJpaRepository;

	@Autowired
	private ICFCApplicationMasterDAO icfcApplicationMasterDAO;

	@Autowired
	private NewWaterRepository newWaterRepository;

	@Resource
	private CommonService commonService;

	@Autowired
	private ServiceMasterService iServiceMasterService;

	@Autowired
	private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@Autowired
	private TbWtBillMasService billMasService;
	
	@Autowired
	private BillMasterService billMasterService;
	
	 @Autowired
	    private ServiceMasterService serviceMaster;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfOwnerShipServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.rest.water.service.ChangeOfOwnerShipService#
	 * saveChangeData(com.abm.mainetservice.rest.water.bean.
	 * ChangeOfOwnerRequestDTO)
	 */
	@Override
	@POST
	@Path("/saveChangeOwner")
	@Transactional(rollbackFor = Exception.class)
	public ResponseDTO saveChangeData(@RequestBody final ChangeOfOwnerRequestDTO requestDTO) {
		final ChangeOfOwnerResponseDTO responseDTO = new ChangeOfOwnerResponseDTO();
		final ChangeOfOwnerMas master = new ChangeOfOwnerMas();
		BeanUtils.copyProperties(requestDTO, master);

		requestDTO.getApplicant().setOrgId(requestDTO.getOrgnId());
		requestDTO.getApplicant().setLangId(requestDTO.getLangId());

		final RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(requestDTO.getServiceId());
		requestDto.setUserId(requestDTO.getUserEmpId());
		requestDto.setOrgId(requestDTO.getOrgnId());
		requestDto.setLangId((long) requestDTO.getApplicant().getLangId());

		requestDto.setDeptId(requestDTO.getDepartmenttId());
		// setting applicant info
		requestDto.setTitleId(requestDTO.getApplicant().getApplicantTitle());
		requestDto.setfName(requestDTO.getApplicant().getApplicantFirstName());
		requestDto.setmName(requestDTO.getApplicant().getApplicantMiddleName());
		requestDto.setlName(requestDTO.getApplicant().getApplicantLastName());
		requestDto.setEmail(requestDTO.getApplicant().getEmailId());
		requestDto.setMobileNo(requestDTO.getApplicant().getMobileNo());
		requestDto.setBlockName(requestDTO.getApplicant().getBlockName());
		requestDto.setFloor(requestDTO.getApplicant().getFloorNo());
		requestDto.setWing(requestDTO.getApplicant().getWing());
		requestDto.setBldgName(requestDTO.getApplicant().getBuildingName());
		requestDto.setHouseComplexName(requestDTO.getApplicant().getHousingComplexName());
		requestDto.setRoadName(requestDTO.getApplicant().getRoadName());
		requestDto.setAreaName(requestDTO.getApplicant().getAreaName());
		if ((requestDTO.getApplicant().getPinCode() != null) && !requestDTO.getApplicant().getPinCode().isEmpty()) {
			requestDto.setPincodeNo(Long.parseLong(requestDTO.getApplicant().getPinCode()));
		}
		requestDto.setPhone(requestDTO.getApplicant().getPhone1());
		requestDto.setBplNo(requestDTO.getApplicant().getBplNo());
		requestDto.setGender(requestDTO.getApplicant().getGender());
		requestDto.setCityName(requestDTO.getApplicant().getVillageTownSub());
		requestDto.setZoneNo(requestDTO.getApplicant().getDwzid1());
		requestDto.setWardNo(requestDTO.getApplicant().getDwzid2());
		requestDto.setFlatBuildingNo(requestDTO.getApplicant().getFlatBuildingNo());
		if (requestDTO.getAmount() == 0d) {
			requestDto.setPayStatus("F");
		}
		if ((requestDTO.getApplicant().getAadharNo() != null) && !requestDTO.getApplicant().getAadharNo().isEmpty()) {
			final String aadhar = requestDTO.getApplicant().getAadharNo().replaceAll("\\s+", MainetConstants.BLANK);
			requestDto.setUid(Long.parseLong(aadhar));
		}

		final Long applicationId = applicationService.createApplication(requestDto);
		responseDTO.setApplicationNo(applicationId);
		master.setApmApplicationId(applicationId);
		master.setLmodDate(new Date());
		master.setChAPLDate(new Date());
		master.setLgIpMac(requestDTO.getLgIpMac());

		// saving New Owner detail
		iChangeOfOwnershipRepository.saveNewData(settingFieldsOfChangeOfOwnerMas(requestDTO, master));
		// saving Additional owner details
		iChangeOfOwnershipRepository.saveAdditionalOwners(prepareOwnerMasters(requestDTO, master));
		requestDTO.setApmApplicationId(applicationId);
		requestDto.setApplicationId(applicationId);
		responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
		// save uploaded docs
		if ((requestDTO.getUploadedDocList() != null) && !requestDTO.getUploadedDocList().isEmpty()) {
			final boolean isUploaded = fileUploadService.doFileUpload(requestDTO.getUploadedDocList(), requestDto);
			if (!isUploaded) {
				responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
				throw new FrameworkException("Problem while saving checklist");
			}
		}
		/*
		 * if (requestDTO.getAmount() == 0d) { ApplicationMetadata applicationData = new
		 * ApplicationMetadata(); applicationData.setApplicationId(applicationId);
		 * applicationData.setIsCheckListApplicable(checklist);
		 * applicationData.setOrgId(requestDTO.getOrgnId());
		 * requestDTO.getApplicant().setServiceId(requestDTO.getServiceId());
		 * requestDTO.getApplicant().setDepartmentId(requestDTO.getDepartmenttId());
		 * commonService.initiateWorkflowfreeService(applicationData,
		 * requestDTO.getApplicant()); }
		 */

		return responseDTO;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void initiateWorkFlowForFreeService(final ChangeOfOwnerRequestDTO requestDTO) {

		if (requestDTO.getAmount() == 0d) {
			final Long applicationId = requestDTO.getApmApplicationId();

			boolean checklist = false;
			if ((requestDTO.getUploadedDocList() != null) && !requestDTO.getUploadedDocList().isEmpty()) {
				checklist = true;
			}
			ApplicationMetadata applicationData = new ApplicationMetadata();
			final ServiceMaster serviceMaster = iServiceMasterService.getServiceMasterByShortCode(
					MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER, requestDTO.getOrgnId());
			if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
				applicationData.setIsLoiApplicable(false);
			} else {
				applicationData.setIsLoiApplicable(true);
			}
			applicationData.setApplicationId(applicationId);
			applicationData.setIsCheckListApplicable(checklist);
			applicationData.setOrgId(requestDTO.getOrgnId());
			requestDTO.getApplicant().setServiceId(requestDTO.getServiceId());
			requestDTO.getApplicant().setDepartmentId(requestDTO.getDepartmenttId());
			Organisation org = new Organisation();
			org.setOrgid(requestDTO.getOrgnId());
			if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
			requestDTO.getApplicant().setDwzid1(requestDTO.getOldOwnerInfo().getCodDwzid1());
			requestDTO.getApplicant().setDwzid2(requestDTO.getOldOwnerInfo().getCodDwzid2());
			requestDTO.getApplicant().setDwzid3(requestDTO.getOldOwnerInfo().getCodDwzid3());
			}
			commonService.initiateWorkflowfreeService(applicationData, requestDTO.getApplicant());
		}
	}

	/**
	 * 
	 * @param requestDTO
	 * @param master
	 * @return
	 */
	private ChangeOfOwnerMas settingFieldsOfChangeOfOwnerMas(final ChangeOfOwnerRequestDTO requestDTO,
			final ChangeOfOwnerMas master) {

		master.setChNewGender(requestDTO.getGender());
		master.setChNewLname(requestDTO.getCooNolname());
		master.setChNewMName(requestDTO.getCooNomname());
		master.setChNewName(requestDTO.getCooNoname());
		master.setChNewTitle(requestDTO.getCooNotitle());
		master.setChNewUIDNo(requestDTO.getConUidNo());
		master.setChRemark(requestDTO.getCooRemark());

		master.setChOldGender(requestDTO.getOldOwnerInfo().getCsOGender());
		master.setChOldLName(requestDTO.getOldOwnerInfo().getCooOolname());
		master.setChOldMnNme(requestDTO.getOldOwnerInfo().getCooOomname());
		master.setChOldName(requestDTO.getOldOwnerInfo().getOldOwnerFullName());
		master.setChOldTitle(requestDTO.getOldOwnerInfo().getCooOtitle());
		master.setChOldUIDNo(requestDTO.getOldOwnerInfo().getCooUidNo());
		master.setCreatedBy(requestDTO.getUserEmpId());
		master.setLangId((long) requestDTO.getLangId());
		master.setOrgId(requestDTO.getOrgnId());

		master.setOwnerTransferMode(requestDTO.getOwnerTransferMode());

		// set transferMode
		// master.set
		return master;
	}

	/**
	 * 
	 * @param requestDTO
	 * @param master
	 * @return
	 */
	private List<AdditionalOwnerInfo> prepareOwnerMasters(final ChangeOfOwnerRequestDTO requestDTO,
			final ChangeOfOwnerMas master) {

		final List<AdditionalOwnerInfo> additionalOwners = new ArrayList<>();
		AdditionalOwnerInfo ownerInfo = null;
		final TbKCsmrInfoMH csIdn = new TbKCsmrInfoMH();
		csIdn.setCsIdn(master.getCsIdn());

		for (final AdditionalOwnerInfoDTO additionalOwnerInfo : requestDTO.getAdditionalOwners()) {
			if ((additionalOwnerInfo.getCaoNewTitle() != null) && (additionalOwnerInfo.getCaoNewTitle() != 0)) {
				ownerInfo = new AdditionalOwnerInfo();
				BeanUtils.copyProperties(additionalOwnerInfo, ownerInfo);
				// setting other required fields
				ownerInfo.setCsIdn(csIdn);
				ownerInfo.setOrgid(master.getOrgId());
				ownerInfo.setUserId(master.getCreatedBy());
				ownerInfo.setLangId(master.getLangId());
				ownerInfo.setLmoddate(new Date());
				ownerInfo.setLgIpMac(Utility.getMacAddress());
				ownerInfo.setApmApplicationId(master.getApmApplicationId());
				ownerInfo.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
				additionalOwners.add(ownerInfo);
			}

		}

		return additionalOwners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.rest.water.service.ChangeOfOwnerShipService#
	 * getWordZoneBlockByApplicationId(long, long, long)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationid, final Long serviceId,
			final Long orgId) {
		final long csIdn = iChangeOfOwnershipRepository.findCsidnFromChangeOfOwner(applicationid);
		final TbKCsmrInfoMH infoMH = iChangeOfOwnershipRepository.findOldOwnerConnectionInfoByCsidn(csIdn, orgId);

		final WardZoneBlockDTO dto = new WardZoneBlockDTO();
		if (infoMH.getCodDwzid1() != null) {
			dto.setAreaDivision1(infoMH.getCodDwzid1());
		}
		if (infoMH.getCodDwzid2() != null) {
			dto.setAreaDivision2(infoMH.getCodDwzid2());
		}
		if (infoMH.getCodDwzid3() != null) {
			dto.setAreaDivision3(infoMH.getCodDwzid3());
		}
		if (infoMH.getCodDwzid4() != null) {
			dto.setAreaDivision4(infoMH.getCodDwzid4());
		}

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.rest.water.service.ChangeOfOwnerShipService#
	 * getLoiCharges(long, long, long)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {

		Map<Long, Double> chargeMap = new HashMap<>();
		final WSRequestDTO requestDTO = new WSRequestDTO();
		chargeMap = changeOfOwnerLOICharges(requestDTO, applicationId, orgId, serviceId);
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
	private Map<Long, Double> changeOfOwnerLOICharges(final WSRequestDTO requestDTO, final long applicationId,
			final long orgId, final long serviceId) throws CloneNotSupportedException {

		final Map<Long, Double> chargeMap = new HashMap<>();
		final List<WaterRateMaster> chargeModelList = new ArrayList<>();
		List<WaterRateMaster> requiredCharges = new ArrayList<>();
		WaterRateMaster tempRate = null;
		WaterRateMaster rateMaster = null;
		WaterRateMaster dependsOnRateMaster = null;
		final Organisation org = new Organisation();
		org.setOrgid(orgId);
		ChangeOfOwnerMas master1 = iChangeOfOwnershipRepository.fetchWaterConnectionOwnerDetail(applicationId);
		final TbKCsmrInfoMH master = newWaterRepository.getWaterConnectionDetailsById(master1.getCsIdn(), orgId);

		// [START] BRMS call for Initialize Parameters & set other Depends on Parameters

		requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId,
				MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER);
		String subCategoryRDCDesc = MainetConstants.BLANK;
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC, 2,
				org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(PrefixConstants.NewWaterServiceConstants.RDC)) {
				subCategoryRDCDesc = lookup.getDescLangFirst();
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
							rateMaster = WaterCommonUtility.populateLOIForChangeOfOwner(tempRate, master, org);
							setRoadDiggingCharges(rateMaster, dependsOnRateMaster, road, master, org);
							WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
							chargeModelList.add(rateMaster);

						}
					}
				}
			} else {
				tempRate = (WaterRateMaster) actualRate.clone();
				dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
				rateMaster = WaterCommonUtility.populateLOIForChangeOfOwner(tempRate, master, org);
				setsecurityDeposit(tempRate, master);
				WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
				chargeModelList.add(rateMaster);
			}
		}
		requestDTO.setDataModel(chargeModelList);

		// BRMS Call for get Service charges
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
			dependsOnFactorMaster.setRoadType(String.valueOf(roadLookup.getDescLangFirst()));
			dependsOnFactorMaster.setRoadLength(road.getCrtRoadUnits());

		}

	}

	/**
	 * @param actualRate
	 * @param master
	 */
	private void setsecurityDeposit(final WaterRateMaster actualRate, final TbKCsmrInfoMH master) {
		actualRate.setConnectionSize(Double
				.valueOf(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getCsCcnsize()).getDescLangFirst()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.ChangeOfOwnerShipService#
	 * fetchWaterConnectionOwnerDetail(long)
	 */

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public ChangeOfOwnershipDTO findWaterConnectionChangeOfOwnerDetail(final long applicationId) {

		final ChangeOfOwnershipDTO ownershipDTO = new ChangeOfOwnershipDTO();
		final ChangeOfOwnerMas ownershipMaster = iChangeOfOwnershipRepository
				.fetchWaterConnectionOwnerDetail(applicationId);
		BeanUtils.copyProperties(ownershipMaster, ownershipDTO);
		final List<AdditionalOwnerInfo> additionalOwnerInfos = iChangeOfOwnershipRepository
				.fetchAdditionalOwners(applicationId);
		final TbCsmrInfoDTO csmrInfoDTO = new TbCsmrInfoDTO();
		if (ownershipMaster != null) {
			final TbKCsmrInfoMH infoMH = iChangeOfOwnershipRepository
					.findOldOwnerConnectionInfoByCsidn(ownershipMaster.getCsIdn(), ownershipMaster.getOrgId());
			BeanUtils.copyProperties(infoMH, csmrInfoDTO);
			ownershipDTO.setCsmrInfoDTO(csmrInfoDTO);

		}

		return initializeAdditionalOwners(additionalOwnerInfos, ownershipDTO);
	}

	private ChangeOfOwnershipDTO initializeAdditionalOwners(final List<AdditionalOwnerInfo> additionalOwners,
			final ChangeOfOwnershipDTO ownershipDTO) {

		final List<AdditionalOwnerInfoDTO> additionalOwnerList = new ArrayList<>();
		AdditionalOwnerInfoDTO additionalOwner = null;
		for (final AdditionalOwnerInfo additionalOwnerInfo : additionalOwners) {

			additionalOwner = new AdditionalOwnerInfoDTO();
			BeanUtils.copyProperties(additionalOwnerInfo, additionalOwner);

			additionalOwnerList.add(additionalOwner);
		}
		ownershipDTO.setAdditionalOwners(additionalOwnerList);

		return ownershipDTO;
	}

	@Override
	@WebMethod(exclude = true)
	public ApplicantDetailDTO populateChangeOfOwnerShipApplicantInfo(ApplicantDetailDTO dto,
			final TbCfcApplicationMstEntity cfcEntity, final ChangeOfOwnershipDTO ownerDTO,
			final CFCApplicationAddressEntity addressEntity) {
		if (dto == null) {
			dto = new ApplicantDetailDTO();
		}
		populateWardZoneBlock(dto, ownerDTO);
		return initializeApplicantAddressDetail(initializeApplicationDetail(dto, cfcEntity), addressEntity);
	}

	/**
	 * 
	 * @param dto
	 * @param entity
	 * @return
	 */
	private ApplicantDetailDTO initializeApplicationDetail(final ApplicantDetailDTO dto,
			final TbCfcApplicationMstEntity entity) {

		dto.setApplicantTitle(entity.getApmTitle());
		dto.setApplicantFirstName(entity.getApmFname());
		dto.setApplicantMiddleName(entity.getApmMname());
		dto.setApplicantLastName(entity.getApmLname());
		dto.setBplNo(entity.getApmBplNo());
		if (StringUtils.isNotBlank(entity.getApmBplNo())) {
			dto.setIsBPL(MainetConstants.Common_Constant.YES);
			dto.setBplNo(entity.getApmBplNo());
		} else {
			dto.setIsBPL(MainetConstants.Common_Constant.NO);
		}
		if (entity.getApmUID() != null) {
			dto.setAadharNo(entity.getApmUID().toString());
		}

		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param addressEntity
	 * @return
	 */
	private ApplicantDetailDTO initializeApplicantAddressDetail(final ApplicantDetailDTO dto,
			final CFCApplicationAddressEntity addressEntity) {

		dto.setMobileNo(addressEntity.getApaMobilno());
		dto.setEmailId(addressEntity.getApaEmail());
		dto.setFloorNo(addressEntity.getApaFloor());
		dto.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo());
		dto.setBuildingName(addressEntity.getApaBldgnm());
		dto.setRoadName(addressEntity.getApaRoadnm());
		dto.setAreaName(addressEntity.getApaAreanm());
		if (addressEntity.getApaPincode() != null) {
			dto.setPinCode(Long.toString(addressEntity.getApaPincode()));
		}
		dto.setBlockName(addressEntity.getApaBlockName());
		dto.setVillageTownSub(addressEntity.getApaCityName());
		dto.setDwzid1(addressEntity.getApaZoneNo());
		dto.setDwzid2(addressEntity.getApaWardNo());

		return dto;

	}

	private void populateWardZoneBlock(final ApplicantDetailDTO dto, final ChangeOfOwnershipDTO ownerDTO) {

		dto.setDwzid1(ownerDTO.getCodDwzid1());
		dto.setDwzid2(ownerDTO.getCodDwzid2());
		dto.setDwzid3(ownerDTO.getCodDwzid3());
		dto.setDwzid4(ownerDTO.getCodDwzid4());

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public void saveFormDataModifiedByDepartment(final ChangeOfOwnerShipModel model) {

		updateApplicationMaster(model.getCfcEntity(), model);
		updateApplicationAddress(model.getCfcAddressEntity(), model);
		final ChangeOfOwnerMas entity = new ChangeOfOwnerMas();
		BeanUtils.copyProperties(model.getOwnerDTO(), entity);
		updateChangeOfOwnerMas(entity, model);
		updateAdditionalOwners(model);

	}

	private void updateApplicationMaster(final TbCfcApplicationMstEntity entity, final ChangeOfOwnerShipModel model) {

		final ApplicantDetailDTO applicantDetailDTO = model.getApplicantDetailDto();
		entity.setApmTitle(applicantDetailDTO.getApplicantTitle());
		entity.setApmFname(applicantDetailDTO.getApplicantFirstName());
		entity.setApmMname(applicantDetailDTO.getApplicantMiddleName());
		entity.setApmLname(applicantDetailDTO.getApplicantLastName());

		entity.setApmSex(getGender(model));
		entity.setApmBplNo(applicantDetailDTO.getBplNo());
		if ((applicantDetailDTO.getAadharNo() != null) && !applicantDetailDTO.getAadharNo().isEmpty()) {
			entity.setApmUID(Long.parseLong(applicantDetailDTO.getAadharNo()));
		}

		entity.setUpdatedBy(model.getUserSession().getEmployee().getEmpId());
		entity.setUpdatedDate(new Date());
		entity.setLgIpMacUpd(Utility.getMacAddress());

		tbCfcApplicationMstJpaRepository.save(entity);
	}

	private String getGender(final ChangeOfOwnerShipModel model) {
		final String gender = model.getApplicantDetailDto().getGender();
		String genderCode = StringUtils.EMPTY;
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				model.getUserSession().getOrganisation());
		for (final LookUp lookUp : lookUps) {

			if ((gender != null) && !gender.isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(model.getApplicantDetailDto().getGender())) {
					genderCode = lookUp.getLookUpCode();
					break;
				}
			}
		}
		return genderCode;
	}

	private void updateApplicationAddress(final CFCApplicationAddressEntity entity,
			final ChangeOfOwnerShipModel model) {
		final ApplicantDetailDTO applicantDetailDTO = model.getApplicantDetailDto();
		entity.setApaMobilno(applicantDetailDTO.getMobileNo());
		entity.setApaEmail(applicantDetailDTO.getEmailId());
		entity.setApaBldgnm(applicantDetailDTO.getBuildingName());
		entity.setApaFlatBuildingNo(applicantDetailDTO.getFlatBuildingNo());
		entity.setApaRoadnm(applicantDetailDTO.getRoadName());
		entity.setApaBlockName(applicantDetailDTO.getBlockName());
		entity.setApaAreanm(applicantDetailDTO.getAreaName());
		entity.setApaCityName(applicantDetailDTO.getVillageTownSub());
		if ((applicantDetailDTO.getPinCode() != null) && !applicantDetailDTO.getPinCode().isEmpty()) {
			entity.setApaPincode(Long.parseLong(applicantDetailDTO.getPinCode()));
		}
		entity.setApaZoneNo(applicantDetailDTO.getDwzid1());
		entity.setApaWardNo(applicantDetailDTO.getDwzid2());

		entity.setUpdatedBy(model.getUserSession().getEmployee().getEmpId());
		entity.setUpdatedDate(new Date());
		entity.setLgIpMacUpd(Utility.getMacAddress());

		tbCfcApplicationAddressJpaRepository.save(entity);

	}

	private void updateChangeOfOwnerMas(final ChangeOfOwnerMas entity, final ChangeOfOwnerShipModel model) {

		final ChangeOfOwnershipDTO dto = model.getOwnerDTO();
		entity.setChNewTitle(dto.getChNewTitle());
		entity.setChNewName(dto.getChNewName());
		entity.setChNewMName(dto.getChNewMName());
		entity.setChNewLname(dto.getChNewLname());
		entity.setChNewGender(dto.getChNewGender());
		entity.setChNewUIDNo(dto.getChNewUIDNo());

		entity.setUpdatedBy(model.getUserSession().getEmployee().getEmpId());
		entity.setUpdatedDate(new Date());
		entity.setLgIpMacUpd(Utility.getMacAddress());
		iChangeOfOwnershipRepository.updateChangeOfOwnershipMas(entity);
	}

	private void updateAdditionalOwners(final ChangeOfOwnerShipModel model) {

		final List<AdditionalOwnerInfoDTO> ownerInfoDTOs = model.getOwnerDTO().getAdditionalOwners();
		final List<AdditionalOwnerInfo> entityList = new ArrayList<>();
		AdditionalOwnerInfo entity = null;
		final TbKCsmrInfoMH csIdn = new TbKCsmrInfoMH();
		csIdn.setCsIdn(model.getOwnerDTO().getCsIdn());
		int count = 0;
		for (final AdditionalOwnerInfoDTO ownerInfo : ownerInfoDTOs) {
			entity = new AdditionalOwnerInfo();
			BeanUtils.copyProperties(ownerInfo, entity);
			final AdditionalOwnerInfoDTO dto = model.getAdditionalOwners().get(count);
			if (dto != null) {
				entity.setCsIdn(csIdn);
				entity.setCaoNewTitle(dto.getCaoNewTitle());
				entity.setCaoNewFName(dto.getCaoNewFName());
				entity.setCaoNewMName(dto.getCaoNewMName());
				entity.setCaoNewLName(dto.getCaoNewLName());
				entity.setCaoNewGender(dto.getCaoNewGender());
				entity.setCaoUID(dto.getCaoUID());
				entity.setApmApplicationId(model.getOwnerDTO().getApmApplicationId());
				entity.setUpdatedBy(model.getUserSession().getEmployee().getEmpId());
				entity.setUpdatedDate(new Date());
				entity.setLgIpMacUpd(Utility.getMacAddress());

				entityList.add(entity);
				count++;
			}

		}

		iChangeOfOwnershipRepository.saveAdditionalOwners(entityList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.service.ChangeOfOwnerShipService#getConnectionData(java.
	 * lang.String, long)
	 */
	@Override

	@POST
	@Path("/fetchConnectionData")
	@Transactional(readOnly = true)
	public ChangeOfOwnerResponseDTO fetchAndVelidatetConnectionData(
			@PathParam("connectionNo") final String connectionNo, @PathParam("orgnId") final long orgnId) {
		final ApplicationSession appSession = ApplicationSession.getInstance();
		Organisation org = new Organisation();
		org.setOrgid(orgnId);
		ChangeOfOwnerResponseDTO dto = null;
		final String canApplyOrNot = iChangeOfOwnershipRepository.enquiryConnectionNo(connectionNo);
		if (MainetConstants.FlagY.equals(canApplyOrNot)) {

			final TbKCsmrInfoMH conMas = iChangeOfOwnershipRepository.getConnectionData(connectionNo, orgnId);
		
			String trfLookupCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(conMas.getCsCcnstatus(), UserSession.getCurrent().getOrganisation().getOrgid(), "CNS").getLookUpCode();
	
			if(conMas != null) {
				dto = populateOldOwnerInfo(conMas);
				dto.setApplicationNo(conMas.getApplicationNo());
				if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) && trfLookupCode.equalsIgnoreCase("D") ) {
					
					dto.setCanApplyOrNot(appSession.getMessage("water.DeptCOO.ConNo") + MainetConstants.WHITE_SPACE + connectionNo
							+" "+ appSession.getMessage("changeofowner.searchCriteria.disconnected"));
				}
				else {
					dto.setCanApplyOrNot(canApplyOrNot);
				}
			}else {
				dto = new ChangeOfOwnerResponseDTO();
				dto.setCanApplyOrNot(appSession.getMessage("water.DeptCOO.ConNo") + MainetConstants.WHITE_SPACE + connectionNo
						+" "+ appSession.getMessage("changeofowner.searchCriteria.noValid"));
			}
			//D#146950
			List<TbWtBillMasEntity> billList = tbWtBillMasJpaRepository
 		            .getBillMasByConnectionId(conMas.getCsIdn());
		    if(CollectionUtils.isNotEmpty(billList)) {
		    	Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
                String respMsg=null;
               
                	 List<TbBillMas> billPendingList = billMasterService.getBillMasterListByUniqueIdentifier(conMas.getCsIdn(), orgnId);
                	 if(CollectionUtils.isNotEmpty(billPendingList)) {
                		 final ServiceMaster service = serviceMaster.getServiceByShortName("WCO", orgnId);
                		 dto.setErrMsg("Dues are pending against the connection number " + connectionNo  + ", unable to proceed with the service  " + service.getSmServiceName()); 
                	 }else {
                		 List<TbWtBillMasEntity> currentBill = tbWtBillMasJpaRepository.getArrearsDeletionBills(conMas.getCsIdn(), finYearId);
                		 if(CollectionUtils.isEmpty(currentBill)) {
                			 dto.setErrMsg("Please generate bill upto current year");
                		 }
                	 }
                
		    }
		} else {
			dto = new ChangeOfOwnerResponseDTO();
			dto.setCanApplyOrNot(canApplyOrNot);
		}

		return dto;
	}

	private ChangeOfOwnerResponseDTO populateOldOwnerInfo(final TbKCsmrInfoMH conMas) {

		ChangeOfOwnerResponseDTO dto = null;
		if (conMas != null) {
			dto = new ChangeOfOwnerResponseDTO();
			dto.setConId(conMas.getCsIdn());
			dto.setConnectionNumber(conMas.getCsCcn());
			dto.setCooOname(conMas.getCsOname());
			dto.setCooOomname(conMas.getCsOmname());
			dto.setCooOolname(conMas.getCsOlname());
			dto.setOldOwnerFullName(concateFullName(conMas));
			if ((conMas.getCsTitle() != null) && !conMas.getCsTitle().isEmpty()) {
				LookUp titleLookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(conMas.getCsTitle(),
						PrefixConstants.LookUp.TITLE, UserSession.getCurrent().getLanguageId());
				dto.setCooOtitle(titleLookUp.getLookUpId());
			}
			dto.setCsOGender(conMas.getCsOGender());
			dto.setCooUidNo(conMas.getCsUid());
			if (conMas.getCsCcnsize() != null) {
				dto.setConSize(conMas.getCsCcnsize());
			}

			dto.setConType(conMas.getTrdPremise());
			dto.setCodDwzid1(conMas.getCodDwzid1());
			dto.setCodDwzid2(conMas.getCodDwzid2());
			dto.setCodDwzid3(conMas.getCodDwzid3());
			dto.setCodDwzid4(conMas.getCodDwzid4());
			// Trariff category6
			dto.setTrmGroup1(conMas.getTrmGroup1());
			dto.setTrmGroup2(conMas.getTrmGroup2());
			dto.setTrmGroup3(conMas.getTrmGroup3());
			dto.setTrmGroup4(conMas.getTrmGroup4());
			dto.setTrmGroup5(conMas.getTrmGroup5());

			dto.setMeterType(conMas.getCsMeteredccn());
			dto.setApplicantType(conMas.getApplicantType());
			dto.setCsAddress(conMas.getCsAdd());

		}
		return dto;
	}

	/**
	 * 
	 * @param conMas
	 * @return
	 */
	private String concateFullName(final TbKCsmrInfoMH conMas) {
		final StringBuilder builder = new StringBuilder();
		if (conMas.getCsName() != null) {
			builder.append(conMas.getCsName()).append(" ");
		}
		if (conMas.getCsMname() != null) {
			builder.append(conMas.getCsMname()).append(" ");
		}
		if (conMas.getCsLname() != null) {
			builder.append(conMas.getCsLname());
		}

		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.ChangeOfOwnerShipService#
	 * initializeChangeOfOwnerExecutionData(long)
	 */
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public ChangeOfOwnershipDTO initializeChangeOfOwnerExecutionData(final long applicationId,
			final UserSession userSession) {

		final List<Object> objects = tbServicesMstJpaRepository
				.findServiceAndApplicantNameByApplicationId(applicationId);
		final ChangeOfOwnershipDTO dto = new ChangeOfOwnershipDTO();
		if ((objects != null) && !objects.isEmpty()) {
			final Object[] objArray = (Object[]) objects.get(0);
			if ((objArray != null) && (objArray.length == 6)) {
				dto.setServiceName((String) objArray[0]);
				dto.setApplicationDate(Utility.dateToString((Date) objArray[1]));
				dto.setApplicantFullName(ConcateFullName(objArray, userSession.getOrganisation()));
				dto.setApprovedDate(Utility.dateToString(new Date()));
				dto.setApprovedBy(approvedBy(userSession));
			}
		} else {

		}

		return dto;
	}

	private String ConcateFullName(final Object[] objArray, final Organisation org) {

		final StringBuilder builder = new StringBuilder();
		// title
		builder.append(objArray[2] != null ? commonService.findTitleDesc((Long) objArray[2], org) : "");
		builder.append(" ");
		// First Name
		builder.append(objArray[3] != null ? (String) objArray[3] : "");
		builder.append(" ");
		// middle name
		builder.append(objArray[4] != null ? (String) objArray[4] : "");
		builder.append(" ");
		// last name
		builder.append(objArray[5] != null ? (String) objArray[5] : "");

		return builder.toString();
	}

	private String approvedBy(final UserSession userSession) {

		final StringBuilder builder = new StringBuilder();
		final Employee employee = userSession.getEmployee();
		builder.append(employee.getCpdTtlId() != null
				? commonService.findTitleDesc(employee.getCpdTtlId(), userSession.getOrganisation())
				: "");
		builder.append(" ");
		builder.append(employee.getEmpname() != null ? employee.getEmpname() : "");
		builder.append(" ");
		builder.append(employee.getEmpmname() != null ? employee.getEmpmname() : "");
		builder.append(" ");
		builder.append(employee.getEmplname() != null ? employee.getEmplname() : "");

		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.ChangeOfOwnerShipService#
	 * executeChangeOfOwnership(com.abm.mainet.water.dto. ChangeOfOwnershipDTO)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public boolean executeChangeOfOwnership(final ChangeOfOwnershipDTO dto) {

		ChangeOfOwnerMas master = iChangeOfOwnershipRepository
				.fetchWaterConnectionOwnerDetail(dto.getApmApplicationId());
		TbKCsmrInfoMH mas = newWaterRepository.getWaterConnectionDetailsById(master.getCsIdn(), master.getOrgId());

		String userName = (master.getChNewName() == null ? MainetConstants.BLANK
				: master.getChNewName() + MainetConstants.WHITE_SPACE);
		userName += master.getChNewMName() == null ? MainetConstants.BLANK
				: master.getChNewMName() + MainetConstants.WHITE_SPACE;
		userName += master.getChNewLname() == null ? MainetConstants.BLANK : master.getChNewLname();
		mas.setCsName(userName);
		mas.setCsOname(userName);
		newWaterRepository.updateCsmrInfo(mas);
		final TbCfcApplicationMstEntity entity = new TbCfcApplicationMstEntity();
		entity.setApmApplicationId(dto.getApmApplicationId());
		entity.setApmApprovedBy(dto.getUpdatedBy());
		entity.setApmApprovedDate(new Date());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setUpdatedDate(new Date());
		entity.setLgIpMacUpd(Utility.getMacAddress());
		return icfcApplicationMasterDAO.updateApprovedBy(entity);
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "getOwnerDetailsByAppId", notes = "getOwnerDetailsByAppId", response = Object.class)
	@Path("/getOwnerDetailsByAppId/applicationId/{applicationId}/orgId/{orgId}")
	public ChangeOfOwnerRequestDTO getOwnerDetailsByAppId(@PathParam("applicationId") Long applicationId,
			@PathParam("orgId") Long orgId) {

		final ChangeOfOwnerRequestDTO responseDto = new ChangeOfOwnerRequestDTO();
		ChangeOfOwnerResponseDTO oldOwnerInfo = new ChangeOfOwnerResponseDTO();

		final ChangeOfOwnershipDTO changeOfOwnershipDTO = findWaterConnectionChangeOfOwnerDetail(applicationId);

		/*
		 * final TbKCsmrInfoMH conectionInfo =
		 * iChangeOfOwnershipRepository.findOldOwnerConnectionInfoByCsidn(orgId,
		 * changeOfOwnershipDTO.getCsIdn());
		 */

		oldOwnerInfo.setConnectionNumber(changeOfOwnershipDTO.getCsmrInfoDTO().getCsCcn());
		oldOwnerInfo.setOldOwnerFullName(changeOfOwnershipDTO.getChOldName());
		oldOwnerInfo.setConSize(changeOfOwnershipDTO.getCsmrInfoDTO().getCsCcnsize());

		responseDto.setOldOwnerInfo(oldOwnerInfo);

		responseDto.setAdditionalOwners(changeOfOwnershipDTO.getAdditionalOwners());
		responseDto.setCooNotitle(changeOfOwnershipDTO.getChNewTitle());
		responseDto.setCooNoname(changeOfOwnershipDTO.getChNewName());
		responseDto.setCooNomname(changeOfOwnershipDTO.getChNewMName());
		responseDto.setCooNolname(changeOfOwnershipDTO.getChNewLname());
		responseDto.setGender(changeOfOwnershipDTO.getChNewGender());
		responseDto.setCooRemark(changeOfOwnershipDTO.getChRemark());
		responseDto.setConUidNo(changeOfOwnershipDTO.getChNewUIDNo());

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
		if ((null != applicantMasterDetails.getApmBplNo()) && !applicantMasterDetails.getApmBplNo().isEmpty()) {
			applicantDetailsDTO.setBplNo(applicantMasterDetails.getApmBplNo());
			applicantDetailsDTO.setIsBPL(MainetConstants.Common_Constant.YES);

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

		responseDto.setApplicant(applicantDetailsDTO);
		return responseDto;
	}

}
