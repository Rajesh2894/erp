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

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
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
import com.abm.mainet.water.dao.WaterDisconnectionRepository;
import com.abm.mainet.water.dao.WaterReconnectionRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.utility.WaterCommonUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Arun.Chavda
 *
 */

@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.WaterReconnectionService")
@Api(value = "/waterreconnectionservice")
@Path("/waterreconnectionservice")
public class WaterReconnectionServiceImpl extends AbstractService implements WaterReconnectionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterReconnectionServiceImpl.class);

	@Resource
	private WaterDisconnectionRepository waterDisconnectionRepository;

	@Resource
	private WaterReconnectionRepository waterReconnectionRepository;

	@Resource
	private NewWaterRepository newWaterRepository;

	@Resource
	CommonService commonService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	@Autowired
	private ServiceMasterService iServiceMasterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.service.WaterReconnectionService#getReconnectionDetails(
	 * com.abm.mainet.water.dto. WaterReconnectionRequestDTO)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public List<WaterReconnectionResponseDTO> getDisconnectionDetailsForReConnection(
			final WaterReconnectionRequestDTO requestDTO) {

		final List<WaterReconnectionResponseDTO> reConnection = waterDisconnectionRepository
				.getDisconnectionDetails(requestDTO.getUserId(), requestDTO.getOrgId(), requestDTO.getConnectionNo());

		return reConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.WaterReconnectionService#
	 * checkIsRegisteredPlumberLicNo(com.abm.mainet.water.dto.
	 * WaterReconnectionRequestDTO)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public WaterReconnectionResponseDTO checkIsRegisteredPlumberLicNo(final WaterReconnectionRequestDTO requestDTO) {

		final WaterReconnectionResponseDTO responseDTO = waterReconnectionRepository
				.checkIsRegisteredPlumberLicNo(requestDTO);

		return responseDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.WaterReconnectionService#
	 * saveWaterReconnectionDetails(com.abm.mainet.water.dto.
	 * WaterReconnectionRequestDTO)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public WaterReconnectionResponseDTO saveWaterReconnectionDetails(final WaterReconnectionRequestDTO requestDTO) {

		final RequestDTO applicantDetailDTO = new RequestDTO();
		final WaterReconnectionResponseDTO responseDTO = new WaterReconnectionResponseDTO();
		try {
			applicantDetailDTO.setTitleId(requestDTO.getApplicant().getApplicantTitle());
			applicantDetailDTO.setfName(requestDTO.getApplicant().getApplicantFirstName());
			applicantDetailDTO.setlName(requestDTO.getApplicant().getApplicantLastName());
			applicantDetailDTO.setMobileNo(requestDTO.getApplicant().getMobileNo());
			applicantDetailDTO.setEmail(requestDTO.getApplicant().getEmailId());
			applicantDetailDTO.setAreaName(requestDTO.getApplicant().getAreaName());
			applicantDetailDTO.setPincodeNo(Long.parseLong(requestDTO.getApplicant().getPinCode()));
			applicantDetailDTO.setServiceId(requestDTO.getServiceId());
			applicantDetailDTO.setDeptId(requestDTO.getDeptId());
			applicantDetailDTO.setUserId(requestDTO.getUserId());
			applicantDetailDTO.setOrgId(requestDTO.getOrgId());
			applicantDetailDTO.setLangId((long) requestDTO.getApplicant().getLangId());
			if (requestDTO.getApplicant().getIsBPL().equals(MainetConstants.FlagY)) {
				applicantDetailDTO.setBplNo(requestDTO.getApplicant().getBplNo());
			}

			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDTO.getOrgId());
			applicantDetailDTO.setCityName(requestDTO.getApplicant().getVillageTownSub());
			applicantDetailDTO.setBlockName(requestDTO.getApplicant().getBlockName());
			if ((null != requestDTO.getApplicant().getAadharNo())
					&& !MainetConstants.BLANK.equals(requestDTO.getApplicant().getAadharNo())) {
				final String aadhar = requestDTO.getApplicant().getAadharNo().replaceAll("\\s+", MainetConstants.BLANK);
				final Long aadhaarNo = Long.parseLong(aadhar);
				applicantDetailDTO.setUid(aadhaarNo);
			}
			if (null != requestDTO.getApplicant().getDwzid1()) {
				applicantDetailDTO.setZoneNo(requestDTO.getApplicant().getDwzid1());
			}
			if (null != requestDTO.getApplicant().getDwzid2()) {
				applicantDetailDTO.setWardNo(requestDTO.getApplicant().getDwzid2());
			}
			if (null != requestDTO.getApplicant().getDwzid3()) {
				applicantDetailDTO.setBlockNo(String.valueOf(requestDTO.getApplicant().getDwzid3()));
			}
			if (requestDTO.getAmount() == 0d) {
				applicantDetailDTO.setPayStatus("F");
			}
			final Long applicationId = applicationService.createApplication(applicantDetailDTO);
			final TbWaterReconnection entity = new TbWaterReconnection();
			entity.setApmApplicationId(applicationId);
			entity.setCsIdn(requestDTO.getConsumerIdNo());
			entity.setRcnDate(requestDTO.getReconnectionDate());
			entity.setRcnRemark(requestDTO.getDiscRemarks());
			entity.setDiscMethod(requestDTO.getDiscMethodId());
			entity.setDiscType(requestDTO.getDiscType());
			entity.setDiscReason(requestDTO.getDiscRemarks());
			entity.setPlumId(requestDTO.getPlumberId());
			entity.setDiscAppdate(requestDTO.getDiscAppDate());
			entity.setLangId(requestDTO.getLangId());
			entity.setLgIpMac(requestDTO.getLgIpMac());
			entity.setLmodDate(new Date());
			entity.setOrgId(requestDTO.getOrgId());
			entity.setUserId(requestDTO.getUserId());
			waterReconnectionRepository.saveWaterReconnectionDetails(entity);
			applicantDetailDTO.setApplicationId(applicationId);

			responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
			requestDTO.setApplicationId(applicationId);
			if ((requestDTO.getUploadedDocList() != null) && !requestDTO.getUploadedDocList().isEmpty()) {
				final boolean isUploaded = fileUploadService.doFileUpload(requestDTO.getUploadedDocList(),
						applicantDetailDTO);
				if (!isUploaded) {
					responseDTO.setUploadedDocSize(requestDTO.getDocumentList().size());
					responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
					throw new FrameworkException("Problem while saving checklist");
				}
			}
			responseDTO.setApplicationId(applicationId);
		} catch (Exception e) {
			LOGGER.error("Exception ocours to saveWaterReconnectionDetails() " + e);
			throw new FrameworkException("Exception occours in saveWaterReconnectionDetails() method" + e);
		}

		return responseDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.service.WaterReconnectionService#getReconnectionDetails(
	 * java.lang.Long)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public TbWaterReconnection getReconnectionDetails(final Long applicationId, final Long orgId) {
		return waterReconnectionRepository.getReconnectionDetails(applicationId, orgId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.WaterReconnectionService#
	 * getWaterConnectionDetailsById(java.lang.Long)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public TbKCsmrInfoMH getWaterConnectionDetailsById(final Long consumerIdNo, final Long orgId) {

		return newWaterRepository.getWaterConnectionDetailsById(consumerIdNo, orgId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.WaterReconnectionService#
	 * updatedWaterReconnectionDetailsByDept(com.abm.mainet.water.domain.
	 * TbWaterReconnection)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public void updatedWaterReconnectionDetailsByDept(final TbWaterReconnection reconnection) {

		reconnection.setLgIpMacUpd(Utility.getMacAddress());
		reconnection.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		reconnection.setUpdatedDate(new Date());
		waterReconnectionRepository.updatedWaterReconnectionDetailsByDept(reconnection);
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {
		final CFCApplicationAddressEntity master = iCFCApplicationAddressService
				.getApplicationAddressByAppId(applicationId, orgId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (master != null) {

			wardZoneDTO = new WardZoneBlockDTO();
			if (master.getApaWardNo() != null) {
				wardZoneDTO.setAreaDivision1(master.getApaWardNo());
			}
			if (master.getApaZoneNo() != null) {
				wardZoneDTO.setAreaDivision2(master.getApaZoneNo());
			}

			if ((null != master.getApaBlockno()) && !master.getApaBlockno().isEmpty()) {
				final Long blockNo = Long.parseLong(master.getApaBlockno());
				wardZoneDTO.setAreaDivision3(blockNo);
			}

		}
		return wardZoneDTO;
	}

	/*
	 * Below method used for get Scrutiny level LOI charges
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId) {
		// [START] BRMS call initialize model
		WaterRateMaster tempRate = null;
		WaterRateMaster rateMaster = null;
		WaterRateMaster dependsOnRateMaster = null;
		final Map<Long, Double> chargeMap = new HashMap<>();
		List<WaterRateMaster> requiredCharges = new ArrayList<>();
		final WSRequestDTO requestDTO = new WSRequestDTO();
		final List<WaterRateMaster> chargeModelList = new ArrayList<>();
		final Organisation org = new Organisation();
		org.setOrgid(orgId);
		TbCfcApplicationMstEntity cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);

		requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId,
				MainetConstants.WaterServiceShortCode.WATER_RECONN);
		TbWaterReconnection reCon = getReconnectionDetails(applicationId, orgId);
		final TbKCsmrInfoMH master = newWaterRepository.getWaterConnectionDetailsById(reCon.getCsIdn(), orgId);
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
							try {
								tempRate = (WaterRateMaster) actualRate.clone();
								dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
							} catch (CloneNotSupportedException e) {
								throw new FrameworkException("Problem while Cloning RateMaster in getLoiCharges()", e);
							}

							rateMaster = WaterCommonUtility.populateLOIForWaterReconnection(tempRate, master, org);
							setRoadDiggingCharges(rateMaster, dependsOnRateMaster, road, master, org);
							WaterCommonUtility.setFactorSpecificData(rateMaster, dependsOnRateMaster, org);
							chargeModelList.add(rateMaster);

						}
					}
				}
			} else {
				try {
					tempRate = (WaterRateMaster) actualRate.clone();
					dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
				} catch (CloneNotSupportedException e) {
					throw new FrameworkException("Problem while Cloning RateMaster in getLoiCharges()", e);
				}

				rateMaster = WaterCommonUtility.populateLOIForWaterReconnection(tempRate, master, org);
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
	 * 
	 * @see com.abm.mainet.water.service.WaterReconnectionService#
	 * updateUserTaskAndReconnectionExecutionDetails(com.abm.mainet.water.
	 * domain.TbWaterReconnection,
	 * com.abm.mainetservice.web.workflow.bean.TaskDefDto)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public void updateUserTaskAndReconnectionExecutionDetails(final TbWaterReconnection reconnection,
			ScrutinyLableValueDTO scrutinyLableValueDTO) {

		waterReconnectionRepository.updatedWaterReconnectionDetailsByDept(reconnection);
		waterReconnectionRepository.updatedBillingStatusOfCSMRInfo(reconnection.getCsIdn(), reconnection.getOrgId(),
				MainetConstants.MeterCutOffRestoration.BILLING_STATUS_ACTIVE);
		waterReconnectionRepository.updatedMeterStatusOfMeterMaster(reconnection.getCsIdn(), reconnection.getOrgId(),
				MainetConstants.MeterCutOffRestoration.METER_STATUS_ACTIVE);
		saveScrutinyValue(scrutinyLableValueDTO);

	}

	/**
	 * @param actualRate
	 * @param master
	 */
	private void setsecurityDeposit(final WaterRateMaster actualRate, final TbKCsmrInfoMH master) {
		actualRate.setConnectionSize(Double
				.valueOf(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getCsCcnsize()).getDescLangFirst()));
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public boolean isAlreadyAppliedForReConn(final Long csIdn) {
		boolean isAlreadyApplied = false;
		final long count = waterReconnectionRepository.isAlreadyAppliedForReConn(csIdn);
		if (count > 0) {
			isAlreadyApplied = true;
		}
		return isAlreadyApplied;
	}

	@Override
	@Transactional
	public void initiateWorkflowForFreeService(WaterReconnectionRequestDTO requestDTO) {
		if (requestDTO.getAmount() == 0d) {
			boolean checklist = false;
			Organisation org = new Organisation();
			org.setOrgid(Long.valueOf(requestDTO.getOrgId()));
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
				if ((requestDTO.getUploadedDocList() != null) && !requestDTO.getUploadedDocList().isEmpty()) {
					checklist = true;
				}
			}
			else {
				if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
					checklist = true;
				}
			}
			
			
			ApplicationMetadata applicationData = new ApplicationMetadata();
			final ServiceMaster serviceMaster = iServiceMasterService.getServiceMasterByShortCode(
					MainetConstants.WaterServiceShortCode.WATER_RECONN, requestDTO.getOrgId());
			if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
				applicationData.setIsLoiApplicable(false);
			} else {
				applicationData.setIsLoiApplicable(true);
			}
			applicationData.setApplicationId(requestDTO.getApplicationId());
			applicationData.setIsCheckListApplicable(checklist);
			applicationData.setOrgId(requestDTO.getOrgId());
			requestDTO.getApplicant().setServiceId(requestDTO.getServiceId());
			requestDTO.getApplicant().setDepartmentId(requestDTO.getDeptId());
			commonService.initiateWorkflowfreeService(applicationData, requestDTO.getApplicant());
		}

	}

	@Override
	@Consumes("application/json")
	@Transactional(readOnly = true)
	@POST
	@ApiOperation(value = "getWaterReconnApplicationDashBoardDetails", notes = "getWaterReconnApplicationDashBoardDetails", response = Object.class)
	@Path("/getWaterReconnApplicationDashBoardDetails/applicationId/{applicationId}/orgId/{orgId}")

	public WaterReconnectionRequestDTO getWaterReconnApplicationDashBoardDetails(
			@PathParam("applicationId") Long applicationId, @PathParam("orgId") Long orgId) {

		WaterReconnectionRequestDTO requestDTO = new WaterReconnectionRequestDTO();

		TbWaterReconnection tbWaterReconnection = getReconnectionDetails(applicationId, orgId);
		if (tbWaterReconnection != null) {

			final ApplicantDetailDTO applicantDetailsDTO = new ApplicantDetailDTO();
			final TbCfcApplicationMstEntity applicantMasterDetails = cfcService
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

			final CFCApplicationAddressEntity applicantDetails = iCFCApplicationAddressService
					.getApplicationAddressByAppId(applicationId, orgId);
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
			TbKCsmrInfoMH tbKCsmrInfoMH = getWaterConnectionDetailsById(tbWaterReconnection.getCsIdn(), orgId);
			if (tbKCsmrInfoMH != null) {
				requestDTO.setConnectionNo(tbKCsmrInfoMH.getCsCcn());
				requestDTO.setConsumerName(tbKCsmrInfoMH.getCsName());
				requestDTO.setConnectionSize(tbKCsmrInfoMH.getCsCcnsize());
				applicantDetailsDTO.setDwzid1(tbKCsmrInfoMH.getCodDwzid1());
				applicantDetailsDTO.setDwzid2(tbKCsmrInfoMH.getCodDwzid2());
				requestDTO.setDiscType(tbWaterReconnection.getDiscType());
				requestDTO.setDiscMethodId(tbWaterReconnection.getDiscMethod());
				requestDTO.setTarrifCategoryId(tbKCsmrInfoMH.getTrmGroup1());
				requestDTO.setApplicant(applicantDetailsDTO);
			}

		} else {
			return null;
		}

		return requestDTO;
	}

	@Override
	public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
		return waterReconnectionRepository.getPlumIdByApplicationId(applicationId, orgId);
	}

}
