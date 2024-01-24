package com.abm.mainet.rnl.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.ILocationMasService;
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
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.rnl.dao.IReportDAO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.domain.EstateBookingCancel;
import com.abm.mainet.rnl.domain.EstateEntity;
import com.abm.mainet.rnl.domain.EstatePropertyEntity;
import com.abm.mainet.rnl.domain.EstatePropertyShift;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.domain.TankerBookingDetailsEntity;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;
import com.abm.mainet.rnl.dto.PropFreezeDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.dto.TankerBookingDetailsDTO;
import com.abm.mainet.rnl.repository.EstateBookingCancelRepository;
import com.abm.mainet.rnl.repository.EstateBookingRepository;
import com.abm.mainet.rnl.repository.EstateContractMappingRepository;
import com.abm.mainet.rnl.repository.EstatePropertyRepository;
import com.abm.mainet.rnl.repository.WaterTankerBookingRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author ritesh.patil
 *
 */

@WebService(endpointInterface = "com.abm.mainet.rnl.service.IEstateBookingService")
@Api(value = "/estateBooking")
@Path("/estateBooking")
@Service
public class EstateBookingServiceImpl implements IEstateBookingService {

	@Autowired
	private EstatePropertyRepository estatePropertyRepository;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private IChallanService iChallanService;

	@Resource
	private IFileUploadService fileUploadService;

	@Autowired
	private EstateBookingRepository estateBookingRepository;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private BRMSRNLService brmsRNLService;

	@Autowired
	private EstateBookingCancelRepository bookingCancelRepository;

	@Autowired
	private IDuplicateReceiptService iDuplicateReceiptService;

	@Autowired
	private TbServicesMstService servicesMstService;

	@Autowired
	private IEstatePropertyService iEstatePropertyService;

	@Autowired
	private IReceiptEntryService iReceiptEntryService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IContractAgreementService contractAgreementService;

	@Autowired
	private IRLBILLMasterService iRLBILLMasterService;

	@Autowired
	private IEstateContractMappingService iEstateContractMappingService;

	@Autowired
	private IContractAgreementService iContractAgreementService;

	@Autowired
	private ContractAgreementRepository contractAgreementRepository;

	@Autowired
	private IReportDAO reportDAO;
	
	@Autowired
	private WaterTankerBookingRepository waterTankerBookingRepository;
	
	@Autowired
	EstateContractMappingRepository estateContractMappingRepository;
	
	@Autowired
	private ICFCApplicationMasterService cfcApplicationService;

	private static final Logger LOGGER = Logger.getLogger(EstateBookingServiceImpl.class);

	@ApiOperation(value = "getAllRentedProperties", notes = "getAllRentedProperties", response = Object.class)
	@POST
	@Path("/getAllRentedProperties/{subCategoryName}/{prefixName}/{orgId}/{subCategoryType}")
	@Override
	@Transactional(readOnly = true)
	public PropertyResDTO getAllRentedProperties(@PathParam(value = "subCategoryName") final String subCategoryName,
			@PathParam(value = "prefixName") final String prefixName, @PathParam(value = "orgId") final Long orgId,
			@QueryParam(value = "subCategoryType") final Integer subCategoryType) {
		EstatePropResponseDTO estatePropResponseDTO = null;
		List<EstatePropResponseDTO> estatePropResponseDTOs = null;
		final PropertyResDTO propResponseDTO = new PropertyResDTO();
		List<EstatePropertyEntity> list = null;
		if (null == subCategoryType) {
			list = estatePropertyRepository.getAllRentedProperties(subCategoryName, prefixName, orgId);
		}
		if (list != null) {
			estatePropResponseDTOs = new ArrayList<>();
			for (final EstatePropertyEntity estatePropertyEntity : list) {
				estatePropResponseDTO = new EstatePropResponseDTO();
				estatePropResponseDTO.setPropId(estatePropertyEntity.getPropId());
				estatePropResponseDTO.setPropName(estatePropertyEntity.getName());
				estatePropResponseDTO.setPropertyNo(estatePropertyEntity.getCode());
				final EstateEntity estateEntity = estatePropertyEntity.getEstateEntity();
				estatePropResponseDTO.setCategory(findCategoryName(estateEntity.getType2(), orgId));
				final LocationMasEntity locationMasEntity = estateEntity.getLocationMas();
				estatePropResponseDTO
						.setLocation(locationMasEntity.getLocAddress() + " " + locationMasEntity.getPincode());
				estatePropResponseDTOs.add(estatePropResponseDTO);
			}

			propResponseDTO.setEstatePropResponseDTOs(estatePropResponseDTOs);
		}
		return propResponseDTO;
	}

	/*
	 * get property list by search filter with total rent(BRMS call)
	 */

	@Override
	@Transactional(readOnly = true)
	public PropertyResDTO getFilteredRentedProperties(final Integer categoryId, final Long eventId,
			final long capacityFrom, final long capacityTo, final double rentFrom, final double rentTo,
			final Organisation org) {
		EstatePropResponseDTO estatePropResponseDTO = null;
		List<EstatePropResponseDTO> estatePropResponseDTOs = new ArrayList<>();
		final PropertyResDTO propResponseDTO = new PropertyResDTO();
		propResponseDTO.setEstatePropResponseDTOs(estatePropResponseDTOs);
		List<EstatePropertyEntity> list = null;

		list = estatePropertyRepository.getFilteredRentedProperties(categoryId, eventId, Long.valueOf(capacityFrom),
				Long.valueOf(capacityTo), org.getOrgid());
		if (list != null && !list.isEmpty()) {
			for (final EstatePropertyEntity estatePropertyEntity : list) {
				// filter list based on RENT only
				// PREFIX - ROC - RENT(RE), LEASE(LE)
				// D-#30365
				LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(
						estatePropertyEntity.getOccupancy().longValue(), "ROC", org.getOrgid());
				if (lookUp.getLookUpCode().equalsIgnoreCase("RE")) {
					estatePropResponseDTO = new EstatePropResponseDTO();
					setDataIntoEstatePropResDto(estatePropertyEntity, estatePropResponseDTO, org);
					estatePropResponseDTOs.add(estatePropResponseDTO);
				}
			}
			String serviceCode=MainetConstants.RNL_ESTATE_BOOKING;
			fetchRentForAllRenetedProperties(estatePropResponseDTOs, eventId, org,serviceCode);
			if (!estatePropResponseDTOs.isEmpty()) {
				List<EstatePropResponseDTO> filterdList = estatePropResponseDTOs.stream()
						.filter(resDto -> rentFrom <= resDto.getTotalRent() && rentTo >= resDto.getTotalRent())
						.collect(Collectors.toList());
				propResponseDTO.setEstatePropResponseDTOs(filterdList);
			}
		}
		return propResponseDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PropertyResDTO getFilteredWaterTanker(final Integer categoryId, final Long eventId,final Organisation org) {
		final double rentFrom=0;
		final double rentTo=99999;
		EstatePropResponseDTO estatePropResponseDTO = null;
		List<EstatePropResponseDTO> estatePropResponseDTOs = new ArrayList<>();
		final PropertyResDTO propResponseDTO = new PropertyResDTO();
		propResponseDTO.setEstatePropResponseDTOs(estatePropResponseDTOs);
		List<EstatePropertyEntity> list = null;

		list = estatePropertyRepository.getFilteredWaterTanker(categoryId, eventId,org.getOrgid());
		if (list != null && !list.isEmpty()) {
			for (final EstatePropertyEntity estatePropertyEntity : list) {
				// filter list based on RENT only
				// PREFIX - ROC - RENT(RE), LEASE(LE)
				// D-#30365
				LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(
						estatePropertyEntity.getOccupancy().longValue(), "ROC", org.getOrgid());
				if (lookUp.getLookUpCode().equalsIgnoreCase("RE")) {
					estatePropResponseDTO = new EstatePropResponseDTO();
					setDataIntoEstatePropResDto(estatePropertyEntity, estatePropResponseDTO, org);
					estatePropResponseDTOs.add(estatePropResponseDTO);
				}
			}
			String serviceCode=MainetConstants.RNL_WATER_TANKER_BOOKING;
			fetchRentForAllRenetedProperties(estatePropResponseDTOs, eventId, org,serviceCode);
			if (!estatePropResponseDTOs.isEmpty()) {
				List<EstatePropResponseDTO> filterdList = estatePropResponseDTOs.stream()
						.filter(resDto -> rentFrom <= resDto.getTotalRent() && rentTo >= resDto.getTotalRent())
						.collect(Collectors.toList());
				propResponseDTO.setEstatePropResponseDTOs(filterdList);
			}
		}
		return propResponseDTO;
	}
	
	void setDataIntoEstatePropResDto(final EstatePropertyEntity estatePropertyEntity,
			EstatePropResponseDTO estatePropResponseDTO, final Organisation organisation) {
		estatePropResponseDTO.setPropId(estatePropertyEntity.getPropId());
		final EstateEntity estateEntity = estatePropertyEntity.getEstateEntity();
		estatePropResponseDTO.setCategory(findCategoryName(estateEntity.getType2(), organisation.getOrgid()));
		final LocationMasEntity locationMasEntity = estateEntity.getLocationMas();
		estatePropResponseDTO.setLocation(locationMasEntity.getLocAddress() + " ");
		if (locationMasEntity.getPincode() != null) {
			estatePropResponseDTO.setLocation(locationMasEntity.getLocAddress() + " " + locationMasEntity.getPincode());
		}
		estatePropResponseDTO.setEstateCode(estatePropertyEntity.getEstateEntity().getCode());
		estatePropResponseDTO.setEstateName(estatePropertyEntity.getEstateEntity().getNameEng());
		estatePropResponseDTO.setEstateNameReg(estatePropertyEntity.getEstateEntity().getNameReg());
		estatePropResponseDTO.setPropertyNo(estatePropertyEntity.getCode());
		estatePropResponseDTO.setPropName(estatePropertyEntity.getName());
		estatePropResponseDTO.setCapacity(estatePropertyEntity.getPropCapacity());
		estatePropResponseDTO.setUnit(
				estatePropertyEntity.getUnitNo() == null ? "" : String.valueOf(estatePropertyEntity.getUnitNo()));
		if (estatePropertyEntity.getUsage() != null) {
			estatePropResponseDTO.setUsage(CommonMasterUtility
					.getHierarchicalLookUp(estatePropertyEntity.getUsage(), organisation).getDescLangFirst());
			estatePropResponseDTO.setUsageForm(CommonMasterUtility
					.getHierarchicalLookUp(estatePropertyEntity.getUsage(), organisation).getDescLangFirst());
		}
		if (estatePropertyEntity.getOccupancy() != null) {
			estatePropResponseDTO.setOccupancy(CommonMasterUtility
					.getNonHierarchicalLookUpObject(estatePropertyEntity.getOccupancy(), organisation)
					.getDescLangFirst());
			estatePropResponseDTO.setOccupancyForm(CommonMasterUtility
					.getNonHierarchicalLookUpObject(estatePropertyEntity.getOccupancy(), organisation)
					.getDescLangSecond());
		}
		if (estatePropertyEntity.getFloor() != null) {
			estatePropResponseDTO.setFloor(CommonMasterUtility
					.getNonHierarchicalLookUpObject(estatePropertyEntity.getFloor(), organisation).getDescLangFirst());
			estatePropResponseDTO.setFloorForm(CommonMasterUtility
					.getNonHierarchicalLookUpObject(estatePropertyEntity.getFloor(), organisation).getDescLangSecond());
		}
		if (estatePropertyEntity.getEstateEntity().getType1() != null) {
			estatePropResponseDTO.setType(CommonMasterUtility
					.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType1(), organisation)
					.getDescLangFirst());
			estatePropResponseDTO.setTypeForm(CommonMasterUtility
					.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType1(), organisation)
					.getDescLangSecond());
		}
		if (estatePropertyEntity.getEstateEntity().getType2() != null) {
			estatePropResponseDTO.setSubType(CommonMasterUtility
					.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType2(), organisation)
					.getDescLangFirst());
			estatePropResponseDTO.setSubTypeForm(CommonMasterUtility
					.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType2(), organisation)
					.getDescLangSecond());
		}
		if (estatePropertyEntity.getRoadType() != null) {
			estatePropResponseDTO.setRoadType(
					CommonMasterUtility.getNonHierarchicalLookUpObject(estatePropertyEntity.getRoadType(), organisation)
							.getDescLangFirst());
			estatePropResponseDTO.setRoadTypeForm(
					CommonMasterUtility.getNonHierarchicalLookUpObject(estatePropertyEntity.getRoadType(), organisation)
							.getDescLangSecond());
		}
		estatePropResponseDTO.setTotalArea(String.valueOf(estatePropertyEntity.getTotalArea()));
		estatePropResponseDTO.setPropId(estatePropertyEntity.getPropId());

	}

	private String findCategoryName(final long subCategoryId, final Long orgId) {
		String categoryName = StringUtils.EMPTY;
		final List<LookUp> lookUps = CommonMasterUtility.getNextLevelData(PrefixConstants.CATEGORY_PREFIX_NAME,
				MainetConstants.EstateBooking.LEVEL, orgId);
		if ((lookUps != null) && !lookUps.isEmpty()) {
			for (final LookUp lookUp : lookUps) {
				if (lookUp.getLookUpId() == subCategoryId) {
					categoryName = lookUp.getDescLangFirst();
					break;
				}
			}
		}
		return categoryName == null ? "" : categoryName;
	}

	/*
	 * @ApiOperation(value = "saveEstateBooking",notes = "saveEstateBooking",
	 * response = BookingResDTO.class)
	 */
	@POST
	@Path("/saveEstateBooking")
	@Override
	@Transactional
	public BookingResDTO saveEstateBooking(@RequestBody final BookingReqDTO bookingReqDTO,
			final CommonChallanDTO offline, final String serviceName) {
		setRequestApplicantDetails(bookingReqDTO);
		final EstateBookingDTO estateBookingDTO = bookingReqDTO.getEstateBookingDTO();
		if (estateBookingDTO.getPayFlag() == null) {
			estateBookingDTO.setPayFlag(false);
		}
		/*
		 * if (estateBookingDTO.getBookingStatus() == null) {
		 * estateBookingDTO.setBookingStatus(MainetConstants.RnLCommon.N_FLAG); }
		 */
		bookingReqDTO.setOrgId(estateBookingDTO.getOrgId());
		bookingReqDTO.setLangId(estateBookingDTO.getLangId());
		bookingReqDTO.setUserId(estateBookingDTO.getCreatedBy());
		final Long applicationId = applicationService.createApplication(bookingReqDTO);
		ChallanReceiptPrintDTO printDto = null;
		EstateBooking estateBooking = new EstateBooking();

		estateBooking.setPayFlag(false);
		final Long javaFq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RnLCommon.RentLease,
				MainetConstants.EstateMaster.TB_RL_ESTATE_MAS, MainetConstants.EstateMaster.ES_CODE,
				estateBookingDTO.getOrgId(), MainetConstants.RnLCommon.Flag_C, null);
		BeanUtils.copyProperties(estateBookingDTO, estateBooking);
		estateBooking.setApplicationId(applicationId);
		estateBooking.setBookingNo(javaFq.toString());
		estateBooking.setBookingDate(new Date());
		final EstatePropertyEntity estatePropertyEntity = new EstatePropertyEntity();
		estatePropertyEntity.setPropId(bookingReqDTO.getEstatePropResponseDTO().getPropId());
		estateBooking.setEstatePropertyEntity(estatePropertyEntity);
		estateBooking.setAmount(bookingReqDTO.getPayAmount());
		estateBooking.setSecurityAmount(0.0); // HArd Code
		estateBooking.setTermAccepted(true);
		estateBooking.setBookingStatus(MainetConstants.RnLCommon.Y_FLAG);
		estateBooking = estateBookingRepository.save(estateBooking);
		estateBookingRepository.updateAppliactionMst(estateBooking.getApplicationId());
		final BookingResDTO bookingResDTO = new BookingResDTO();

		bookingResDTO.setBookingNo(estateBooking.getBookingNo());
		bookingResDTO.setApplicationNo(estateBooking.getApplicationId());
		bookingReqDTO.setApplicationId(applicationId);
		if ((bookingReqDTO.getDocumentList() != null) && !bookingReqDTO.getDocumentList().isEmpty()) {
			fileUploadService.doFileUpload(bookingReqDTO.getDocumentList(), bookingReqDTO);
		}
		return bookingResDTO;
	}

	@POST
	@Path("/saveWaterTanker")
	@Override
	@Transactional
	public BookingResDTO saveWaterTanker(@RequestBody final BookingReqDTO bookingReqDTO, final CommonChallanDTO offline,
			final String serviceName) {
		setRequestApplicantDetails(bookingReqDTO);
		final EstateBookingDTO estateBookingDTO = bookingReqDTO.getEstateBookingDTO();
		if (estateBookingDTO.getPayFlag() == null) {
			estateBookingDTO.setPayFlag(false);
		}
		bookingReqDTO.setOrgId(estateBookingDTO.getOrgId());
		bookingReqDTO.setLangId(estateBookingDTO.getLangId());
		bookingReqDTO.setUserId(estateBookingDTO.getCreatedBy());
		final Long applicationId = applicationService.createApplication(bookingReqDTO);
		ChallanReceiptPrintDTO printDto = null;
		EstateBooking estateBooking = new EstateBooking();
		estateBooking.setPayFlag(false);
		final Long javaFq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RnLCommon.RentLease,
				MainetConstants.EstateMaster.TB_RL_ESTATE_MAS, MainetConstants.EstateMaster.ES_CODE,
				estateBookingDTO.getOrgId(), MainetConstants.RnLCommon.Flag_C, null);
		BeanUtils.copyProperties(estateBookingDTO, estateBooking);
		estateBooking.setApplicationId(applicationId);
		estateBooking.setBookingNo(javaFq.toString());
		estateBooking.setBookingDate(new Date());
		final EstatePropertyEntity estatePropertyEntity = new EstatePropertyEntity();
		estatePropertyEntity.setPropId(bookingReqDTO.getEstatePropResponseDTO().getPropId());
		estateBooking.setEstatePropertyEntity(estatePropertyEntity);
		estateBooking.setAmount(bookingReqDTO.getPayAmount());
		estateBooking.setSecurityAmount(0.0); // HArd Code
		estateBooking.setTermAccepted(true);
		estateBooking.setBookingStatus(MainetConstants.RnLCommon.Y_FLAG);

		estateBooking = estateBookingRepository.save(estateBooking);
		estateBookingRepository.updateAppliactionMst(estateBooking.getApplicationId());
		Character flag = 'W';
		estatePropertyRepository.updateStatus(flag,bookingReqDTO.getEstatePropResponseDTO().getPropId());
		final BookingResDTO bookingResDTO = new BookingResDTO();

		bookingResDTO.setBookingNo(estateBooking.getBookingNo());
		bookingResDTO.setApplicationNo(estateBooking.getApplicationId());
		bookingReqDTO.setApplicationId(applicationId);
		if ((bookingReqDTO.getDocumentList() != null) && !bookingReqDTO.getDocumentList().isEmpty()) {
			fileUploadService.doFileUpload(bookingReqDTO.getDocumentList(), bookingReqDTO);
		}
		return bookingResDTO;
	}
	
	private void setRequestApplicantDetails(final BookingReqDTO reqDTO) {
		final ApplicantDetailDTO appDTO = reqDTO.getApplicantDetailDto();
		final Organisation organisation = new Organisation();
		final Long orgId = reqDTO.getEstateBookingDTO().getOrgId();
		organisation.setOrgid(orgId);
		reqDTO.setmName(appDTO.getApplicantMiddleName());
		reqDTO.setfName(appDTO.getApplicantFirstName());
		reqDTO.setlName(appDTO.getApplicantLastName());
		reqDTO.setEmail(appDTO.getEmailId());
		reqDTO.setMobileNo(appDTO.getMobileNo());
		reqDTO.setTitleId(appDTO.getApplicantTitle());
		reqDTO.setBldgName(appDTO.getBuildingName());
		reqDTO.setRoadName(appDTO.getRoadName());
		reqDTO.setAreaName(appDTO.getAreaName());
		reqDTO.setBplNo(appDTO.getBplNo());

		if ((appDTO.getPinCode() != null) && !appDTO.getPinCode().isEmpty()) {
			reqDTO.setPincodeNo(Long.valueOf(appDTO.getPinCode()));
		}
		reqDTO.setWing(appDTO.getWing());
		reqDTO.setDeptId(reqDTO.getDeptId());
		reqDTO.setFloor(appDTO.getFloorNo());
		reqDTO.setWardNo(appDTO.getDwzid2());
		reqDTO.setZoneNo(appDTO.getDwzid1());
		reqDTO.setCityName(appDTO.getVillageTownSub());
		reqDTO.setBlockName(appDTO.getBlockName());
		reqDTO.setHouseComplexName(appDTO.getHousingComplexName());
		reqDTO.setFlatBuildingNo(appDTO.getFlatBuildingNo());
		if ((appDTO.getAadharNo() != null) && !appDTO.getAadharNo().equals("")) {
			reqDTO.setUid(Long.valueOf(appDTO.getAadharNo().replaceAll("\\s", "")));
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
		for (final LookUp lookUp : lookUps) {
			if ((appDTO.getGender() != null) && !appDTO.getGender().isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(appDTO.getGender())) {
					reqDTO.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
		reqDTO.setGender(appDTO.getGender());
	}

	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {
		final Long id = estateBookingRepository.getApplicantInformationById(applicationId, orgId);

		WardZoneBlockDTO wardZoneDTO = new WardZoneBlockDTO();
		if (id != null) {
			wardZoneDTO.setAreaDivision1(id);
		}
		return wardZoneDTO;
	}

	@ApiOperation(value = "findByPropId", notes = "findByPropId", response = Object.class)
	@POST
	@Path("/findByPropId/propId{propId}/orgId{orgId}")
	@Override
	@Transactional
	public List<EstateBookingDTO> findByPropId(@PathParam(value = "propId") final Long propId,
			@PathParam(value = "orgId") final Long orgId) {
		// fetch booking list based on EBK_BOOK_STATUS='Y'
		// than here update the booking status using TB_ONL_TRAN_MAS_SERVICE
		// if RECV_STATUS is cancel than booking status update for available to everyone
		// D#74246
		final List<EstateBooking> bookedList = estateBookingRepository
				.findByEstatePropertyEntityPropIdAndOrgIdAndBookingStatus(propId, orgId, "Y");
		List<String> referenceIds = new ArrayList<String>();
		referenceIds = bookedList.stream().map(booking -> String.valueOf(booking.getApplicationId()))
				.collect(Collectors.toList());
		// D#79885
		if (!referenceIds.isEmpty()) {
			estateBookingRepository.updateTheBookingStatusIfOnlineNotPaid("N", orgId, referenceIds);
		}
		final List<EstateBooking> estateBooking = estateBookingRepository
				.findByEstatePropertyEntityPropIdAndOrgIdAndBookingStatusNotIn(propId, orgId, "N");
		final List<EstateBookingDTO> list = new ArrayList<>();
		EstateBookingDTO bookingDTO = null;
		if (null != estateBooking) {
			for (final EstateBooking booking : estateBooking) {
				// (cancelled booking should not be seen in calendar) (#33101)
				if (!booking.getBookingStatus().contentEquals(MainetConstants.FlagC)) {
					bookingDTO = new EstateBookingDTO();
					bookingDTO.setId(booking.getId());
					bookingDTO.setToDate(booking.getToDate());
					bookingDTO.setFromDate(booking.getFromDate());
					bookingDTO.setShiftName(getShiftNameById(booking));
					bookingDTO.setBookingStatus(booking.getBookingStatus());
					bookingDTO.setApplicationId(booking.getApplicationId());
					bookingDTO.setBookingNo(booking.getBookingNo());
					bookingDTO.setBookingDate(booking.getBookingDate());
					list.add(bookingDTO);
				}
			}
		}
		return list;
	}

	private String getShiftNameById(final EstateBooking estateBooking) {
		String name = null;
		final Organisation organisation = new Organisation();
		organisation.setOrgid(estateBooking.getOrgId());
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.SHIFT_PREFIX, organisation);
		for (final LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == estateBooking.getShiftId()) {
				name = lookUp.getLookUpCode();
				break;
			}
		}
		return name;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getEstateBookingFromAndToDates(final Long propId, final Long orgId, Boolean isAllShift) {
		final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
		final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.SHIFT_PREFIX_GENERAL,
				PrefixConstants.SHIFT_PREFIX, organisation);
		// D#74975 fetch shifts of this propId only
		List<Long> shiftIds = new ArrayList<Long>();
		if (!isAllShift) {
			shiftIds.add(lookup.getLookUpId());
		} else {
			EstatePropertyEntity propertyEntitiesList = estatePropertyRepository.findPropertyForBooking(propId);
			propertyEntitiesList.getEstatePropShift().forEach(propertyShifts -> {
				shiftIds.add(propertyShifts.getPropShift());
			});
		}
		List<String> dateList = null;
		// if shiftIds is empty than query create PBLM
		List<Object[]> objects = new ArrayList<Object[]>();
		if (!shiftIds.isEmpty()) {
			objects = estateBookingRepository.getBookedFromAndToDates(propId, shiftIds, orgId);
		}

		dateList = new ArrayList<>();
		for (final Object[] obj : objects) {
			getDaysBetweenDates(dateList, (Date) obj[0], (Date) obj[1]);
		}
		return dateList;
	}

	/*
	 * @Consumes("application/json")
	 * 
	 * @ApiOperation(value = "find All Booking Details", notes =
	 * "find All Booking Details")
	 */
	/* @POST */
	/*
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes({ "application/xml", "application/json" })
	 */
	/*
	 * @Override
	 * 
	 * @Consumes("application/json")
	 * 
	 * @GET
	 * 
	 * @ApiOperation(value = "getConnectionDetails", notes = "getConnectionDetails",
	 * response = Object.class)
	 * 
	 * @Path("/getConnectionDetails/csIdn/{csIdn}")
	 */

	@ApiOperation(value = "getEstateBookingFromAndToDatesForGeneral", notes = "getEstateBookingFromAndToDatesForGeneral", response = Object.class)
	@POST
	@Path("/getEstateBookingFromAndToDatesForGeneral/propId{propId}/orgId{orgId}")
	@Override
	@Transactional(readOnly = true)
	public List<String> getEstateBookingFromAndToDatesForGeneral(@PathParam(value = "propId") final Long propId,
			@PathParam(value = "orgId") Long orgId) {
		final List<Object[]> objects = estateBookingRepository.getBookedFromAndToDatesForGeneral(propId, orgId);
		final List<String> dateList = new ArrayList<>();
		for (final Object[] obj : objects) {
			getDaysBetweenDates(dateList, (Date) obj[0], (Date) obj[1]);
		}
		return dateList;
	}

	private List<String> getDaysBetweenDates(final List<String> dateList, final Date startdate, final Date enddate) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);
		while (calendar.getTime().before(enddate) || calendar.getTime().equals(enddate)) {
			final Date result = calendar.getTime();
			dateList.add(new SimpleDateFormat("d-M-yyyy").format(result));
			calendar.add(Calendar.DATE, 1);
		}
		return dateList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LookUp> getEstateBookingShifts(final Long propId, final String fromdate, final String todate,
			final Long orgId) {
		List<LookUp> lookupShift = null;
		final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
		if (fromdate.equals(todate)) {
			final List<LookUp> lookup = CommonMasterUtility.getListLookup(PrefixConstants.SHIFT_PREFIX, organisation);
			final Date dateD = UtilityService.convertStringDateToDateFormat(fromdate);
			lookupShift = new ArrayList<>();
			final List<Long> fromAndtoDate = estateBookingRepository.getShitIdFromDate(propId, dateD, orgId);
			if ((fromAndtoDate == null) || fromAndtoDate.isEmpty()) {
				lookup.get(0).setOtherField("");
				return lookup;
			} else {
				for (final LookUp look : lookup) {
					if (!fromAndtoDate.contains(look.getLookUpId())) {
						if (!look.getLookUpCode().equalsIgnoreCase(MainetConstants.SHIFT_PREFIX_GENERAL)) {
							lookupShift.add(look);
						}
					}
				}
			}
		} else {
			final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.SHIFT_PREFIX_GENERAL,
					PrefixConstants.SHIFT_PREFIX, organisation);
			lookupShift = new ArrayList<>();
			lookupShift.add(lookup);
			lookupShift.get(0).setOtherField("");
		}

		// D#104569 check property freeze on particular date or not with propId
		Date fromDate = Utility.stringToDate(fromdate, MainetConstants.DATE_FORMAT);
		Date toDate = Utility.stringToDate(todate, MainetConstants.DATE_FORMAT);
		List<EstateBooking> propertyFreeze = reportDAO.findFreezePropertyByDate(fromDate, toDate, propId, orgId);

		if (!propertyFreeze.isEmpty() && lookupShift != null && !lookupShift.isEmpty()) {
			// check shift type is GENERAL
			LookUp lookupSHF = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					propertyFreeze.get(0).getShiftId(), orgId, PrefixConstants.SHIFT_PREFIX);
			if (lookupSHF != null && lookupSHF.getLookUpCode().equals(MainetConstants.SHIFT_PREFIX_GENERAL)) {
				lookupShift.get(0).setOtherField(MainetConstants.SHIFT_PREFIX_GENERAL);
			}
		}

		return lookupShift;
	}

	/*
	 * @Consumes("application/json")
	 * 
	 * @ApiOperation(value = "find All Booking Details", notes =
	 * "find All Booking Details")
	 */

	/* @Produces(MediaType.APPLICATION_JSON) */

	@ApiOperation(value = "findBooking", notes = "findBooking", response = Object.class)
	@POST
	@Path("/findBooking/bookId{bookId}/orgId{orgId}")
	@Override
	@Transactional(readOnly = true)
	public PropInfoDTO findBooking(@PathParam("bookId") final Long bookId, @PathParam("orgId") final Long orgId) {
		final EstateBooking estateBooking = estateBookingRepository.findByIdAndOrgId(bookId, orgId);
		final PropInfoDTO propInfoDTO = new PropInfoDTO();
		// D#74975
		if (estateBooking.getApplicationId() != 0) {
			final TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
					.getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
			final TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository = ApplicationContextProvider
					.getApplicationContext().getBean(TbCfcApplicationAddressJpaRepository.class);
			final List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository
					.getApplicantInfo(estateBooking.getApplicationId(), orgId);
			final List<Object[]> addressInfoList = tbCfcApplicationAddressJpaRepository
					.findAddressInfo(estateBooking.getApplicationId(), orgId);

			propInfoDTO.setToDate(estateBooking.getToDate());
			propInfoDTO.setFromDate(estateBooking.getFromDate());
			propInfoDTO.setDayPeriod(getShiftNameById(estateBooking));

			Long bookingpurpose = estateBooking.getPurpose();

			Organisation organisation = new Organisation();

			organisation.setOrgid(orgId);

			LookUp bookingPurpose = CommonMasterUtility.getNonHierarchicalLookUpObject(bookingpurpose, organisation);
			propInfoDTO.setBookingPuprpose(String.valueOf(bookingPurpose.getDescLangFirst()));

			propInfoDTO.setBookingId(estateBooking.getBookingNo().toString());
			propInfoDTO.setPropName(estateBooking.getEstatePropertyEntity().getName());
			String mName = "";
			for (final Object[] strings : appliInfoList) {
				if (strings[1] != null) {
					mName = strings[1].toString();
				}
				propInfoDTO.setApplicantName(strings[0].toString() + " " + mName + " " + strings[2].toString());
			}
			for (final Object[] strings : addressInfoList) {
				propInfoDTO.setAreaName(strings[0].toString());
				if (!(strings[1] == null)) {
					propInfoDTO.setCity(strings[1].toString());
				}
				propInfoDTO.setPinCode(strings[2].toString());
				propInfoDTO.setContactNo(strings[3].toString());
			}

			final LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(
					estateBooking.getEstatePropertyEntity().getEstateEntity().getType2(), organisation);
			propInfoDTO.setCategory(lookup.getLookUpDesc());

			// propInfoDTO.setAmount(estateBooking.getAmount());
			propInfoDTO.setApplicationId(String.valueOf(estateBooking.getApplicationId()));
		}
		return propInfoDTO;
	}

	@Override
	@Transactional
	public boolean saveFreezeProperty(final EstateBookingDTO estateBookingDTO) {
		EstateBooking estateBooking = new EstateBooking();
		BeanUtils.copyProperties(estateBookingDTO, estateBooking);
		estateBooking.setApplicationId(0L);
		estateBooking.setBookingNo("0");
		estateBooking.setBookingDate(new Date());
		final EstatePropertyEntity estatePropertyEntity = new EstatePropertyEntity();
		estatePropertyEntity.setPropId(estateBookingDTO.getPropId());
		estateBooking.setEstatePropertyEntity(estatePropertyEntity);
		estateBooking.setAmount(0.0);
		estateBooking.setSecurityAmount(0.0); // HArd Code
		estateBooking.setTermAccepted(false);
		estateBooking.setPayFlag(false);
		estateBooking.setBookingStatus(MainetConstants.RnLCommon.F_FLAG);
		estateBooking = estateBookingRepository.save(estateBooking);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PropFreezeDTO> findAllFreezeBookingProp(final Long orgId, final String bookingStatus) {
		final List<EstateBooking> estateBookingList = estateBookingRepository.findByOrgIdAndBookingStatus(orgId,
				bookingStatus);
		final Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		PropFreezeDTO propFreezeDTO = null;
		final List<PropFreezeDTO> dtos = new ArrayList<>();
		for (final EstateBooking estateBooking : estateBookingList) {
			propFreezeDTO = new PropFreezeDTO();
			propFreezeDTO.setId(estateBooking.getId());
			propFreezeDTO.setEstate(estateBooking.getEstatePropertyEntity().getEstateEntity().getNameEng());
			propFreezeDTO.setProperty(estateBooking.getEstatePropertyEntity().getName());
			propFreezeDTO.setLocation(
					estateBooking.getEstatePropertyEntity().getEstateEntity().getLocationMas().getLocNameEng());
			propFreezeDTO.setFromDate(UtilityService.convertDateToDDMMYYYY(estateBooking.getFromDate()));
			propFreezeDTO.setToDate(UtilityService.convertDateToDDMMYYYY(estateBooking.getToDate()));
			propFreezeDTO.setShift(CommonMasterUtility
					.getNonHierarchicalLookUpObject(estateBooking.getShiftId(), organisation).getLookUpDesc());
			// propFreezeDTO.setPurpose(estateBooking.getPurpose());
			propFreezeDTO.setReasonOfFreezing(estateBooking.getReasonOfFreezing());
			dtos.add(propFreezeDTO);
		}
		return dtos;
	}

	@Override
	@Transactional
	public int updateFreezeProperty(final Long id, final Long empId) {
		estateBookingRepository.updateFreezeProperty(id, empId);
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean findCountForProperty(final Long orgId, final Long propId) {
		boolean count = true;
		if (estateBookingRepository.findCountForProperty(orgId, propId) > 0L) {
			count = false;
		}
		return count;
	}

	/*
	 * method fetch total rent for List of properties in one BRMS call with data
	 * structure(<propId,<Rate List>>)
	 */
	@SuppressWarnings("unchecked")
	void fetchRentForAllRenetedProperties(final List<EstatePropResponseDTO> estatePropResponseDTOs, Long eventId,
			final Organisation org, String serviceCode) {
		WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName(MainetConstants.RnLCommon.RNLRATEMASTER);
		WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

			final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 0);
			final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);

			WSRequestDTO taxReqDTO = new WSRequestDTO();
			rnlRateMaster.setOrgId(org.getOrgid());
			rnlRateMaster.setServiceCode(serviceCode);
			rnlRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL, PrefixConstants.LookUpPrefix.CAA, org)
					.getLookUpId()));
			taxReqDTO.setDataModel(rnlRateMaster);
			final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())
					&& !taxResponseDTO.isFree()) {
				final List<RNLRateMaster> rates = (List<RNLRateMaster>) taxResponseDTO.getResponseObj();
				Map<Long, List<RNLRateMaster>> propRateMap = new ConcurrentHashMap<>();// <propId,<Rate List>>
				WSRequestDTO request = new WSRequestDTO();
				estatePropResponseDTOs.forEach(estatePropResponseDTO -> {
					final List<RNLRateMaster> requiredCHarges = prepareModelForRateMaster(org, eventId, rnlRateMaster,
							rates, estatePropResponseDTO, serviceCode);
					propRateMap.put(estatePropResponseDTO.getPropId(), requiredCHarges);

				});
				request.setDataModel(propRateMap);
				WSResponseDTO rentCharges = RestClient.callBRMS(request,
						ServiceEndpoints.BRMSMappingURL.RNL_CHARGES_FOR_MULTI_PROP);// call BRMS
				if (rentCharges != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(rentCharges.getWsStatus())) {
					Map<Long, List<RNLRateMaster>> interestResponse = castRequestTopropRateMap(rentCharges);
					estatePropResponseDTOs.forEach(estatePropResponseDTO -> {
						List<RNLRateMaster> rateList = interestResponse.get(estatePropResponseDTO.getPropId());
						double totalRent = rateList.stream().mapToDouble(i -> i.getFlatRate()).sum();
						estatePropResponseDTO.setTotalRent(totalRent);
					});
				} else {
					throw new FrameworkException("Exception while calling Rent for properties status fail");
				}
			}
		}
	}

	private List<RNLRateMaster> prepareModelForRateMaster(final Organisation org, final Long eventId,
			final RNLRateMaster rnlRateMaster, final List<RNLRateMaster> rates,
			EstatePropResponseDTO estatePropResponseDTO,String serviceCode) {
		final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
		/*
		 * for (final RNLRateMaster rate : rates) { RNLRateMaster rateMaster = null; try
		 * { rateMaster = (RNLRateMaster) rate.clone(); } catch
		 * (CloneNotSupportedException e) { LOGGER.error(e.getMessage(), e); throw new
		 * FrameworkException(e); } rateMaster.setOrgId(org.getOrgid());
		 * rateMaster.setServiceCode(MainetConstants.RNL_ESTATE_BOOKING);
		 * rateMaster.setDeptCode(MainetConstants.RNL_DEPT_CODE);
		 * rateMaster.setUsageSubtype1(estatePropResponseDTO.getUsage());
		 * rateMaster.setUsageSubtype2(estatePropResponseDTO.getType());
		 * rateMaster.setUsageSubtype3(estatePropResponseDTO.getSubType());
		 * rateMaster.setRateStartDate(new Date().getTime());
		 * rateMaster.setFloorLevel(estatePropResponseDTO.getFloor());
		 * rateMaster.setOccupancyType(estatePropResponseDTO.getOccupancy());
		 * rateMaster.setRoadType(estatePropResponseDTO.getRoadType());
		 * rateMaster.setChargeApplicableAt(
		 * CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
		 * org.getOrgid(), Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
		 * rateMaster.setTaxSubCategory(getSubCategoryDesc(rateMaster.getTaxSubCategory(
		 * ), org)); requiredCHarges.add(rateMaster); }
		 */
		for (final RNLRateMaster rate : rates) {
			RNLRateMaster rateMaster = null;
			try {
				rateMaster = (RNLRateMaster) rate.clone();
			} catch (CloneNotSupportedException e) {
				LOGGER.error(e.getMessage(), e);
				throw new FrameworkException(e);
			}
			rateMaster.setOrgId(org.getOrgid());
			rateMaster.setServiceCode(serviceCode);
			rateMaster.setDeptCode(MainetConstants.RNL_DEPT_CODE);
			rateMaster.setChargeApplicableAt(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
					org.getOrgid(), Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
			rateMaster.setTaxSubCategory(getSubCategoryDesc(rateMaster.getTaxSubCategory(), org));
			rateMaster.setRateStartDate(new Date().getTime());
			rateMaster.setNoOfBookingDays(1l);
			// D#101787
			LookUp lookupPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue("SHF",
					PrefixConstants.EPLOYEE_ORGNA_ISBPLM, MainetConstants.NUMBER_ONE, org);
			if (lookupPrefix.getOtherField() != null && lookupPrefix.getOtherField().equalsIgnoreCase("Y")) {
				rateMaster.setShiftType(MainetConstants.SHIFT_PREFIX_GENERAL);
			}

			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(eventId, org.getOrgid(), "EVT");
			rateMaster.setOccupancyType(lookUp.getLookUpDesc());

			rateMaster.setFactor4(estatePropResponseDTO.getPropName());
			if (CollectionUtils.isNotEmpty(rateMaster.getDependsOnFactorList())) {
				for (String dependFactor : rateMaster.getDependsOnFactorList()) {
					if (StringUtils.equalsIgnoreCase(dependFactor, "USB")) {
						rateMaster.setUsageSubtype1(estatePropResponseDTO.getUsage());
					}
					if (StringUtils.equalsIgnoreCase(dependFactor, "EST")) {
						rateMaster.setUsageSubtype2(estatePropResponseDTO.getType());
					}

					if (StringUtils.equalsIgnoreCase(dependFactor, "ES")) {
						rateMaster.setUsageSubtype3(estatePropResponseDTO.getSubType());
					}
					/*
					 * if (StringUtils.equalsIgnoreCase(dependFactor, "OT")) {
					 * rateMaster.setOccupancyType(CommonMasterUtility.getCPDDescription(eventId,
					 * MainetConstants.FlagE)); }
					 */

					if (StringUtils.equalsIgnoreCase(dependFactor, "FL")) {
						rateMaster.setFloorLevel(estatePropResponseDTO.getFloor());
					}
				}
			}
			requiredCHarges.add(rateMaster);
		}
		return requiredCHarges;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = "";
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.TAC_PREFIX,
				MainetConstants.EstateBooking.LEVEL, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
				break;
			}
		}
		return subCategoryDesc;
	}

	@SuppressWarnings("unchecked")
	public Map<Long, List<RNLRateMaster>> castRequestTopropRateMap(WSResponseDTO response) {
		Map<Long, List<RNLRateMaster>> dataModel = null;
		if (response.getResponseObj() != null) {
			LinkedHashMap<String, Object> requestMap = (LinkedHashMap<String, Object>) response.getResponseObj();
			String jsonString = new JSONObject(requestMap).toString();
			try {
				TypeReference<Map<Long, List<RNLRateMaster>>> typeRef = new TypeReference<Map<Long, List<RNLRateMaster>>>() {
				};
				dataModel = new ObjectMapper().readValue(jsonString, typeRef);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				throw new FrameworkException(e);
			}
		}
		return dataModel;
	}

	/*
	 * @Consumes("application/json")
	 * 
	 * @ApiOperation(value = "find All Booking Details", notes =
	 * "find All Booking Details")
	 */

	/*
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes({ "application/xml", "application/json" })
	 */

	@ApiOperation(value = "getShiftDetailsFromDateAndTodate", notes = "getShiftDetailsFromDateAndTodate", response = Object.class)
	@POST
	@Path("/getShiftDetailsFromDateAndTodate/propId{propId}/fromdate{fromdate}/todate{todate}/orgId{orgId}")
	@Override
	@Transactional(readOnly = true)
	public List<EstatePropertyShiftDTO> getShiftDetailsFromDateAndTodate(@PathParam(value = "propId") Long propId,
			@PathParam(value = "fromdate") String fromdate, @PathParam(value = "todate") String todate,
			@PathParam(value = "orgId") Long orgId) {
		List<EstatePropertyShiftDTO> propertyShiftDTOsList = null;
		List<EstatePropertyShiftDTO> shiftDTOsList = null;
		final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
		if (fromdate.equals(todate)) {
			EstatePropertyEntity propertyEntitiesList = estatePropertyRepository.findPropertyForBooking(propId);
			final Date dateD = UtilityService.convertStringDateToDateFormat(fromdate);
			EstatePropertyShiftDTO shiftDTO = null;
			propertyShiftDTOsList = new ArrayList<EstatePropertyShiftDTO>();
			shiftDTOsList = new ArrayList<EstatePropertyShiftDTO>();
			for (EstatePropertyShift propShiftEntities : propertyEntitiesList.getEstatePropShift()) {
				shiftDTO = new EstatePropertyShiftDTO();
				BeanUtils.copyProperties(propShiftEntities, shiftDTO);
				if (propShiftEntities.getPropShift() != null) {
					organisation.setOrgid(orgId);
					shiftDTO.setPropShiftDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(propShiftEntities.getPropShift(), organisation)
							.getDescLangFirst());
				}
				propertyShiftDTOsList.add(shiftDTO);
			}
			final List<Long> fromAndtoDate = estateBookingRepository.getShitIdFromDate(propId, dateD, orgId);
			if ((fromAndtoDate == null) || fromAndtoDate.isEmpty()) {
				return propertyShiftDTOsList;
			} else {
				for (EstatePropertyShiftDTO dto : propertyShiftDTOsList) {
					if (!fromAndtoDate.contains(dto.getPropShift())) {
						if (!dto.getPropShiftDesc().equalsIgnoreCase(MainetConstants.SHIFT_PREFIX_GENERAL)) {
							shiftDTOsList.add(dto);
						}
					}
				}
			}
		} else {
			EstatePropertyEntity propertyEntitiesList = estatePropertyRepository.findPropertyForBooking(propId);
			EstatePropertyShiftDTO shiftDTO = null;
			shiftDTOsList = new ArrayList<EstatePropertyShiftDTO>();
			for (EstatePropertyShift propShiftEntities : propertyEntitiesList.getEstatePropShift()) {
				shiftDTO = new EstatePropertyShiftDTO();
				BeanUtils.copyProperties(propShiftEntities, shiftDTO);
				if (propShiftEntities.getPropShift() != null) {
					organisation.setOrgid(orgId);
					shiftDTO.setPropShiftDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(propShiftEntities.getPropShift(), organisation)
							.getDescLangFirst());
				}
				if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_TSCL)) {

					if (shiftDTO.getPropShiftDesc().equalsIgnoreCase(MainetConstants.SHIFT_PREFIX_GENERAL))
						shiftDTOsList.add(shiftDTO);
				} else {
					shiftDTOsList.add(shiftDTO);
				}
				  
			}
		}
		// D#103497 check property freeze on particular date or not with propId
		Date fromDate = Utility.stringToDate(fromdate, MainetConstants.DATE_FORMAT);
		Date toDate = Utility.stringToDate(todate, MainetConstants.DATE_FORMAT);
		// EstateBooking propertyFreeze =
		// estateBookingRepository.getPropertyFreezeOnDate(fromDate, toDate, propId,
		// orgId);
		List<EstateBooking> propertyFreeze = reportDAO.findFreezePropertyByDate(fromDate, toDate, propId, orgId);

		if (!propertyFreeze.isEmpty() && shiftDTOsList != null && !shiftDTOsList.isEmpty()) {
			// check shift type is GENERAL
			LookUp lookupSHF = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					propertyFreeze.get(0).getShiftId(), orgId, PrefixConstants.SHIFT_PREFIX);
			if (lookupSHF != null && lookupSHF.getLookUpCode().equals(MainetConstants.SHIFT_PREFIX_GENERAL)) {
				shiftDTOsList.get(0).setShiftStatus(MainetConstants.SHIFT_PREFIX_GENERAL);
			}
		}
		return shiftDTOsList;
	}

	@Override
	@Transactional
	public void updateEstateBookingStatus(Long applicationNo, Long empId) {
		estateBookingRepository.updateEstateBookingStatus(applicationNo, empId);
	}

	@Override
	@Path("/fetchAllBookingsByOrg")
	@Transactional
	public List<EstateBookingDTO> fetchAllBookingsByOrg(Long orgId, String bookingStatus) {
		final List<EstateBooking> estateBookingList = estateBookingRepository.findByOrgIdAndBookingStatus(orgId,
				bookingStatus);
		List<EstateBookingDTO> dtos = new ArrayList<>();
		for (EstateBooking estate : estateBookingList) {

			EstateBookingDTO dto = new EstateBookingDTO();
			if (dto.getApplicationId() != null) {
				estate.setApplicationId(dto.getApplicationId());
			}
			BeanUtils.copyProperties(estate, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<BookingCancelDTO> fetchChargesDetails(Long apmApplicationId, Long orgId) {
		List<BookingCancelDTO> bookingCancelDTOS = new ArrayList<>();
		List<TbSrcptFeesDetEntity> entities = estateBookingRepository.fetchChargesDetails(apmApplicationId, orgId);
		HashSet<Long> taxIdSet = new HashSet<>();
		List<TbSrcptFeesDetEntity> distinctEntities = entities.stream().filter(e -> taxIdSet.add(e.getTaxId()))
				.collect(Collectors.toList());
		/*
		 * this will update old list also HashSet<Object> seen = new HashSet<>();
		 * entities.removeIf(c -> !seen.add(c.getTaxId()));
		 */
		for (TbSrcptFeesDetEntity entity : distinctEntities) {
			BookingCancelDTO dto = new BookingCancelDTO();
			BeanUtils.copyProperties(entity, dto);
			// get tax name (fee description)
			TbTaxMas tax = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
					.findTaxByTaxIdAndOrgId(entity.getTaxId(), orgId);
			dto.setFeeDescription(tax.getTaxDesc());
			bookingCancelDTOS.add(dto);
		}

		return bookingCancelDTOS;
	}

	@Transactional
	public void updateDataForCancelBooking(EstateBookingDTO estateBookingDTO,
			List<EstateBookingCancel> estateBookingCancelEntities, List<BookingCancelDTO> bookingCancelList) {
		// update in TB_RL_ESTATE_BOOKING
		estateBookingRepository.updateCancelData(estateBookingDTO.getId(), bookingCancelList.get(0).getCreatedBy(),
				estateBookingDTO.getCancelReason());
		// insert record in TB_RL_ESTATE_BOOKING_CANCEL
		bookingCancelRepository.save(estateBookingCancelEntities);

		// insert in account
		// check account module live or not
		LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

		if (lookup != null) {
			String accountCode = lookup.getLookUpCode();
			if (lookup.getDefaultVal().equals(MainetConstants.FlagY) && accountCode.equals(MainetConstants.FlagL)) {
				Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
				Long empId = UserSession.getCurrent().getEmployee().getEmpId();
				VendorBillApprovalDTO billDTO = new VendorBillApprovalDTO();
				List<VendorBillExpDetailDTO> billExpDetListDto = new ArrayList<>();
				//VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();

				/*
				 * List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
				 * VendorBillDedDetailDTO billDedDetailDTO = new VendorBillDedDetailDTO();
				 */

				billDTO.setBillEntryDate(Utility.dateToString(new Date()));
				billDTO.setBillTypeId(
						CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("MI", MainetConstants.ABT, orgId));
				// get LOI no for set in NARRATION field
				billDTO.setOrgId(orgId);
				billDTO.setNarration(estateBookingDTO.getBookingNo() + "-" + estateBookingDTO.getCancelReason());
				billDTO.setCreatedBy(empId);
				billDTO.setCreatedDate(Utility.dateToString(new Date()));
				billDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
				billDTO.setVendorId(estateBookingDTO.getVendorId());
				// ask to Samadhan Sir
				Double refundAmtSum = estateBookingCancelEntities.stream()
						.mapToDouble(x -> x.getRefundAmt().doubleValue()).sum();
				billDTO.setInvoiceAmount(BigDecimal.valueOf(refundAmtSum));
				long fieldId = 0;
				if (UserSession.getCurrent().getLoggedLocId() != null) {
					TbLocationMas locMas = ApplicationContextProvider.getApplicationContext()
							.getBean(ILocationMasService.class).findById(UserSession.getCurrent().getLoggedLocId());
					if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
						fieldId = locMas.getLocId();
					}
				}
				if (fieldId == 0) {
					throw new NullPointerException("fieldId is not linked with Location Master for[locId="
							+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
							+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
				}
				billDTO.setFieldId(fieldId);

				billDTO.setDepartmentId(ApplicationContextProvider.getApplicationContext()
						.getBean(DepartmentService.class)
						.getDepartment(MainetConstants.RnLCommon.RentLease, MainetConstants.CommonConstants.ACTIVE)
						.getDpDeptid());

				for (BookingCancelDTO cancelDTO : bookingCancelList) {
					Long sacHeadId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
							.fetchSacHeadIdForReceiptDet(orgId, cancelDTO.getTaxId(), MainetConstants.FlagA); // here A
																												// means
					VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();	
					// Active
					billExpDetailDTO.setBudgetCodeId(cancelDTO.getRfFeeid());
					billExpDetailDTO.setAmount(cancelDTO.getRefundAmt());
					billExpDetailDTO.setSanctionedAmount(cancelDTO.getRefundAmt());
					billExpDetListDto.add(billExpDetailDTO);
					
				}
				
				billDTO.setExpDetListDto(billExpDetListDto);
				try {
					ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(billDTO,
							ServiceEndpoints.SALARY_POSTING);
					if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {

					}
				} catch (Exception exception) {
					throw new FrameworkException("error occured while bill posting to account module ", exception);
				}

			}

		}
	}

	@Override
	public EstateBooking findbookingIdbyBookingNo(String bookingNo, Long orgId) {

		return estateBookingRepository.getbookingIdbyBookingNo(bookingNo, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstatePropertyEventDTO> getEventOrPropertyId(Integer categoryId, Long orgId) {

		List<EstatePropertyEventDTO> dtosList = new ArrayList<>();
		List<Object[]> defEntityList = estatePropertyRepository.getEventOrPropertyId(categoryId, orgId);
		if (!defEntityList.isEmpty()) {
			for (Object[] eventCate : defEntityList) {

				EstatePropertyEventDTO eventDto = new EstatePropertyEventDTO();
				eventDto.setPropEvent((Long) eventCate[0]);
				eventDto.setOrgId((Long) eventCate[1]);
				eventDto.setCpdDesc((eventCate[2]).toString());
				eventDto.setCpdDescMar((eventCate[3]).toString());
				dtosList.add(eventDto);
			}
		} else {
			LOGGER.error("Event does not exist based on this Property");
		}
		return dtosList;

	}

	@Override
	@Transactional
	public void enableEstateBookingStatus(String bookingNo, Long orgId, Date fromDate, Date toDate) {
		estateBookingRepository.enableBookingStatus(bookingNo, orgId, fromDate, toDate);
	}

	@Override
	public CommonChallanDTO getForChallanVerification(Long orgId, Long applicationId) {

		EstateBooking estBookEntity = estateBookingRepository.getForChallanVerification(orgId, applicationId);

		CommonChallanDTO dto = new CommonChallanDTO();
		dto.setFromedate(estBookEntity.getFromDate());
		dto.setToDate(estBookEntity.getToDate());

		BeanUtils.copyProperties(dto, estBookEntity);

		return dto;

	}

	@Override
	public List<EstateBookingDTO> checkedReceiptValiadtion(long orgId, Long propId) {

		List<EstateBookingDTO> listObj = new ArrayList<>();
		List<Object[]> defEntityList = estateBookingRepository.checkedReceiptValiadtion(orgId, propId);
		// need to change in modelMapper
		if (!defEntityList.isEmpty()) {
			for (Object[] comObject : defEntityList) {

				EstateBookingDTO eventDto = new EstateBookingDTO();
				eventDto.setApplicationId((Long) comObject[1]);
				eventDto.setBookingDate((Date) comObject[2]);
				eventDto.setBookingNo((String) comObject[3]);
				eventDto.setFromDate((Date) comObject[4]);
				eventDto.setToDate((Date) comObject[5]);
				listObj.add(eventDto);
			}
		}

		return listObj;
	}

	@Override
	public CommonChallanDTO getForPropertyname(Long applicationId, Long orgId) {

		String estBookEntity = estateBookingRepository.getPropertyName(applicationId, orgId);
		CommonChallanDTO dto = new CommonChallanDTO();
		dto.setPropName(estBookEntity);
		BeanUtils.copyProperties(dto, estBookEntity);

		return dto;
	}

	@Override
	public Boolean checkPropertyBookedByEventId(Long propId, List<Long> eventIds, Long orgId) {

		return estateBookingRepository.checkPropertyBookedByEventId(propId, eventIds, orgId);
	}

	@Override
	public EstateBookingDTO getBookingDetailsByApplId(Long apmApplicationId, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	// #99721
	@POST
	@Consumes("application/json")
	@ApiOperation(value = "Fetch Booking details by date and Property Name", notes = "Fetch Booking details by date and Property Name")
	@Path("/getBookingDetails/fromDate/{fromDate}/toDate/{toDate}/propertyName/{propertyName}/orgId/{orgId}/")
	@Override
	@Transactional(readOnly = true)
	public List<EstatePropResponseDTO> getBookingDetails(@PathParam("fromDate") Date fromDate,
			@PathParam("toDate") Date toDate, @PathParam("propertyName") String propertyName,
			@PathParam("orgId") Long orgId) {
		Long propId = estatePropertyRepository.getPropertyIdByName(propertyName, orgId);

		List<EstateBooking> estateBooking = estateBookingRepository.findByDatePropIdAndOrgId(fromDate, toDate, propId,
				orgId);
		ServiceMaster sm = serviceMasterService.getServiceByShortName(MainetConstants.RNL_ESTATE_BOOKING, orgId);
		List<EstatePropResponseDTO> estatePropResponseDTOList = new ArrayList<>();
		for (EstateBooking entity : estateBooking) {
			EstatePropResponseDTO estatePropResponsdto = new EstatePropResponseDTO();
			estatePropResponsdto.setServiceId(sm.getSmServiceId());
			TbServiceReceiptMasEntity receiptMasterEntity = iReceiptEntryService
					.getReceiptDetByAppIdAndServiceId(entity.getApplicationId(), sm.getSmServiceId(), orgId);
			if (receiptMasterEntity != null) {
				if (entity.getApplicationId() != 0) {
					final TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
							.getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
					final List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository
							.getApplicantInfo(entity.getApplicationId(), orgId);

					estatePropResponsdto.setFromDate(entity.getFromDate());
					estatePropResponsdto.setToDate(entity.getToDate());
					estatePropResponsdto.setBookingId(entity.getId());
					estatePropResponsdto.setPropName(entity.getEstatePropertyEntity().getName());
					String mName = "";
					for (final Object[] strings : appliInfoList) {
						if (strings[1] != null) {
							mName = strings[1].toString();
						}
						estatePropResponsdto
								.setApplicantName(strings[0].toString() + " " + mName + " " + strings[2].toString());
					}
					estatePropResponsdto.setApplicationId(entity.getApplicationId());
					estatePropResponsdto.setPropId(entity.getEstatePropertyEntity().getPropId());

					estatePropResponsdto.setReceiptId(receiptMasterEntity.getRmRcptid());
					estatePropResponsdto.setReceiptNo(receiptMasterEntity.getRmRcptno());

				}
				estatePropResponseDTOList.add(estatePropResponsdto);
			}
		}

		return estatePropResponseDTOList;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.EstateBooking.GET_BOOKING_DETAILS, notes = MainetConstants.EstateBooking.GET_BOOKING_DETAILS, response = EstatePropResponseDTO.class)
	@Path("/getEstateBookingDetails")
	@Transactional(readOnly = true)
	public List<EstatePropResponseDTO> getEstateBookingDetails(@RequestBody EstatePropResponseDTO requestDto) {
		List<EstatePropResponseDTO> estatePropResponseDTOList = new ArrayList<>();
		EstatePropResponseDTO estatePropResponsdto = new EstatePropResponseDTO();
		Long propId = estatePropertyRepository.getPropertyIdByName(requestDto.getPropName(), requestDto.getOrgId());

		List<EstateBooking> estateBooking = estateBookingRepository.findByDatePropIdAndOrgId(requestDto.getFromDate(),
				requestDto.getToDate(), propId, requestDto.getOrgId());
		ServiceMaster sm = serviceMasterService.getServiceByShortName(MainetConstants.RNL_ESTATE_BOOKING,
				requestDto.getOrgId());

		for (EstateBooking entity : estateBooking) {
			if (entity.getApplicationId() != 0) {
				final TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
						.getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
				final List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository
						.getApplicantInfo(entity.getApplicationId(), requestDto.getOrgId());
				final TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository = ApplicationContextProvider
						.getApplicationContext().getBean(TbCfcApplicationAddressJpaRepository.class);

				final List<Object[]> addressInfoList = tbCfcApplicationAddressJpaRepository
						.findAddressInfo(entity.getApplicationId(), requestDto.getOrgId());
				for (final Object[] data : addressInfoList) {
					if (requestDto.getApaMobilno().equals(data[3])) {
						estatePropResponsdto.setServiceId(sm.getSmServiceId());
						TbServiceReceiptMasEntity receiptMasterEntity = iReceiptEntryService
								.getReceiptDetByAppIdAndServiceId(entity.getApplicationId(), sm.getSmServiceId(),
										requestDto.getOrgId());
						if (receiptMasterEntity != null) {
							/*
							 * SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); Date frmDate =
							 * df.parse(entity.getFromDate()); Date toDate = df.parse(entity.getToDate());
							 */
							estatePropResponsdto.setFromDate(entity.getFromDate());
							estatePropResponsdto.setToDate(entity.getToDate());
							estatePropResponsdto.setBookingId(entity.getId());
							estatePropResponsdto.setPropName(entity.getEstatePropertyEntity().getName());
							String mName = "";
							for (final Object[] strings : appliInfoList) {
								if (strings[1] != null) {
									mName = strings[1].toString();
								}
								estatePropResponsdto.setApplicantName(
										strings[0].toString() + " " + mName + " " + strings[2].toString());
							}
							estatePropResponsdto.setApplicationId(entity.getApplicationId());
							estatePropResponsdto.setPropId(entity.getEstatePropertyEntity().getPropId());
							estatePropResponsdto.setReceiptId(receiptMasterEntity.getRmRcptid());
							estatePropResponsdto.setReceiptNo(receiptMasterEntity.getRmRcptno());
							estatePropResponseDTOList.add(estatePropResponsdto);
						}
					}
				}
			}

		}

		return estatePropResponseDTOList;
	}

	@Override
	@POST
	@Path("/getPropetyDetailsByOrgId/{orgId}")
	@Transactional(readOnly = true)
	public List<EstatePropMaster> getPropetyDetailsByOrgId(@PathParam("orgId") Long orgId) {
		List<EstatePropMaster> estatePropMasterList = new ArrayList<>();
		List<EstatePropertyEntity> estatePropertyEntities = estatePropertyRepository.getAllPropertyDetByOrgId(orgId);
		estatePropertyEntities.forEach(entity -> {
			EstatePropMaster estatePropList = new EstatePropMaster();
			BeanUtils.copyProperties(entity, estatePropList);
			estatePropMasterList.add(estatePropList);
		});
		return estatePropMasterList;
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/getDuplicateReceiptDetail/{receiptId}/{orgId}")
	@Transactional(readOnly = true)
	public ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long receiptId, Long orgId) {

		TbServiceReceiptMasBean receiptMasBean = iReceiptEntryService.findReceiptById(receiptId, orgId);
		ChallanReceiptPrintDTO receiptDto = iDuplicateReceiptService.getReceiptDetails(receiptId,
				receiptMasBean.getRmRcptno(), receiptMasBean.getApmApplicationId());
		if (receiptDto != null) {
			receiptDto.setPaymentMode(CommonMasterUtility
					.getNonHierarchicalLookUpObject(receiptMasBean.getReceiptModeDetailList().getCpdFeemode(),
							UserSession.getCurrent().getOrganisation())
					.getLookUpDesc());

			EstateBookingDTO bookingdto = getBookingDetailsByApplId(receiptMasBean.getApmApplicationId(), orgId);

			EstatePropMaster master = iEstatePropertyService.findByPropDetailsById(bookingdto.getPropId());

			String serviceName = servicesMstService.getServiceNameByServiceId(receiptMasBean.getSmServiceId());
			String changeServiceName = master.getName();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			String fromDate = "", toDate = "";
			if (bookingdto.getFromDate() != null && bookingdto.getToDate() != null) {
				fromDate = formatter.format(bookingdto.getFromDate());
				toDate = formatter.format(bookingdto.getToDate());
			}

			String fromDateToDate = fromDate + " to " + toDate;
			String addMuncipaleProperty = serviceName + MainetConstants.HYPHEN + MainetConstants.WHITE_SPACE
					+ changeServiceName + MainetConstants.HYPHEN + "Booking from " + MainetConstants.HYPHEN
					+ fromDateToDate;
			receiptDto.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
					+ MainetConstants.WHITE_SPACE + addMuncipaleProperty);
		}
		return receiptDto;

	}

	@Override
	@POST
	@ApiOperation(value = "fetch Summary Data for portal", notes = "fetch Summary Data for portal", response = BookingReqDTO.class)
	@Path("/getSummaryDataFromPortal/{orgId}")
	public List<ContractAgreementSummaryDTO> getSummaryDataFromPortal(@PathParam("orgId") Long orgId) {
		List<ContractAgreementSummaryDTO> contractAgreementSummaryList = new ArrayList<ContractAgreementSummaryDTO>();
		List<ContractAgreementSummaryDTO> contractAgreementFinalList = new ArrayList<ContractAgreementSummaryDTO>();
		try {
			contractAgreementSummaryList = contractAgreementService.getContractAgreementSummaryData(orgId, null, null,
					null, null, null, null);
		} catch (Exception ex) {
			throw new FrameworkException("Exception while Contract List Data : " + ex.getMessage());
		}
		if (CollectionUtils.isNotEmpty(contractAgreementSummaryList)) {
			// re-filter with contract entry in tb_rl_bill_mast present or not
			contractAgreementFinalList = contractAgreementSummaryList.stream()
					.filter(agreement -> !(iRLBILLMasterService.finByContractId(agreement.getContId(), orgId,
							MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B)).isEmpty())
					.collect(Collectors.toList());
			List<Object[]> objectList = iEstateContractMappingService.fetchEstatePropertyForBillPay("Y"/* Map Active */,
					orgId);
			List<Object[]> objectFinalList = new ArrayList<Object[]>();
			objectFinalList = objectList.stream()
					.filter(obj -> !(iRLBILLMasterService.finByContractId((Long) obj[0], orgId,
							MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B)).isEmpty())
					.collect(Collectors.toList());
			contractAgreementFinalList.get(0).setPropertyDetails(objectFinalList);
		}
		return contractAgreementFinalList;
	}

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "Bill Payment Search Data", notes = "Bill Payment Search Data")
	@POST
	@Path("/fetchSearchData/{contNo}/{propertyContractNo}/{orgId}")
	@Transactional
	public ContractAgreementSummaryDTO fetchSearchData(@PathParam("contNo") String contNo,
			@PathParam("propertyContractNo") String propertyContractNo, @PathParam("orgId") Long orgId) {

		final ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CBP,
				orgId);
		// D#88671
		String contractNo = null;
		if(contNo.equalsIgnoreCase("0")) {
			contNo="";
		}
		contractNo = (StringUtils.isEmpty(contNo) ? propertyContractNo : contNo);
		ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService.findByContractNo(orgId,
				contractNo);

		if (contractAgreementSummaryDTO != null) {
			// check contract entry in tb_rl_bill_mast present or not
			final List<RLBillMaster> billMasters = iRLBILLMasterService.finByContractId(
					contractAgreementSummaryDTO.getContId(), orgId, MainetConstants.CommonConstants.N,
					MainetConstants.CommonConstants.B);
			if (billMasters.isEmpty()) {
				// no record found message
			} else {
				final Calendar currentDate = Calendar.getInstance();
				final Calendar billDate = Calendar.getInstance();
				currentDate.setTime(new Date());
				contractAgreementSummaryDTO = iRLBILLMasterService.getReceiptAmountDetailsForBillPayment(
						contractAgreementSummaryDTO.getContId(), contractAgreementSummaryDTO, orgId);
				contractAgreementSummaryDTO.setServiceId(service.getSmServiceId());
				contractAgreementSummaryDTO.setDeptId(service.getTbDepartment().getDpDeptid());
			}
		}
		return contractAgreementSummaryDTO;
	}
	
	@Override
	@Consumes("application/json")
	@ApiOperation(value = "Bill Payment Search Data Using ContNo and PropNo", notes = "Bill Payment Search Data Using ContNo and PropNo")
	@POST
	@Path("/fetchSearchDataUsingContAndProp/{contNo}/{propertyContractNo}/{orgId}")
	@Transactional
	public ContractAgreementSummaryDTO fetchSearchDataUsingContAndProp(@PathParam("contNo") String contNo,
			@PathParam("propertyContractNo") String propertyContractNo, @PathParam("orgId") Long orgId) {
		
		 LOGGER.info("Contract No:- "+ contNo + "  Property No:- " + propertyContractNo + "  OrgUd:- "+ orgId);

		final ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CBP,
				orgId);
		// D#88671
		String contractNo = null;
		//contractNo = (StringUtils.isEmpty(contNo) ? propertyContractNo : contNo);		
		if(contNo == null || contNo.equalsIgnoreCase("0") || StringUtils.isEmpty(contNo)) {
			Long propId = estatePropertyRepository.getPropertyIdByPropNo(propertyContractNo, orgId);
			final ContractMastEntity contracts = estateContractMappingRepository.findByEstatePropertyPropId(orgId, propId);
			contractNo = contracts.getContNo();
		}else{
			contractNo = contNo;
		}
		
		ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService.findByContractNo(orgId,
				contractNo);

		if (contractAgreementSummaryDTO != null) {
			// check contract entry in tb_rl_bill_mast present or not
			final List<RLBillMaster> billMasters = iRLBILLMasterService.finByContractId(
					contractAgreementSummaryDTO.getContId(), orgId, MainetConstants.CommonConstants.N,
					MainetConstants.CommonConstants.B);
			if (billMasters.isEmpty()) {
				// no record found message
			} else {
				final Calendar currentDate = Calendar.getInstance();
				final Calendar billDate = Calendar.getInstance();
				currentDate.setTime(new Date());
				contractAgreementSummaryDTO = iRLBILLMasterService.getReceiptAmountDetailsForBillPayment(
						contractAgreementSummaryDTO.getContId(), contractAgreementSummaryDTO, orgId);
				contractAgreementSummaryDTO.setServiceId(service.getSmServiceId());
				contractAgreementSummaryDTO.setDeptId(service.getTbDepartment().getDpDeptid());
			}
		}
		return contractAgreementSummaryDTO;
	}

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "Bill Payment Amount Check", notes = "Bill Payment Amount Check")
	@POST
	@Path("/checkData/{contNo}/{inputAmount}/{orgId}")
	@Transactional
	public List<String> checkData(@PathParam("contNo") String contNo, @PathParam("inputAmount") String inputAmount,
			@PathParam("orgId") Long orgId) {

		List<String> error = new ArrayList<>();
		final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = contractAgreementRepository
				.finAllRecords(Long.valueOf(contNo), MainetConstants.RnLCommon.Y_FLAG);
		int noOfInstallment = contractInstalmentDetailEntities.size();
		Double sumOfInstallmentAmt = contractInstalmentDetailEntities.stream().mapToDouble(ci -> ci.getConitAmount())
				.sum();
		Double installment = (sumOfInstallmentAmt / noOfInstallment);
		/*
		 * if (!(Double.valueOf(inputAmount) % installment == 0)) {
		 * error.add("Not Multiple of installment"); }
		 */
		Double alreadyPaidAmt = 0d;
		List<RLBillMaster> rlBillMasters = iRLBILLMasterService.finByContractId(Long.valueOf(contNo), orgId,
				MainetConstants.CommonConstants.Y, MainetConstants.FlagI);
		for (RLBillMaster obj : rlBillMasters) {
			alreadyPaidAmt += obj.getPaidAmount();
		}
		if (Double.valueOf(inputAmount) > (sumOfInstallmentAmt - alreadyPaidAmt)) {
			// error MSG
			error.add("amount is more than a contract amount");
		}
		return error;
	}

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "Update Bill Payment Data", notes = "Update Bill Payment Data")
	@POST
	@Path("/upadteDataFromPortal")
	@Transactional
	public ContractAgreementSummaryDTO upadteDataFromPortal(@RequestBody ContractAgreementSummaryDTO requestDto) {
		try {
			List<RLBillMaster> rlBillMasterList = iRLBILLMasterService.finByContractId(requestDto.getContId(),
					requestDto.getOrgId(), MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
			// update installments data in TB_RL_BILL_MAST
			/*
			 * if (requestDto.getInputAmount().equals(rlBillMasterList.get(0).getAmount()))
			 * { // getPayAmount equal receivable amount RLBillMaster rlBillMaster =
			 * rlBillMasterList.get(0);
			 * rlBillMaster.setPaidAmount(requestDto.getInputAmount());
			 * rlBillMaster.setBalanceAmount(0d); //
			 * rlBillMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			 * rlBillMaster.setUpdatedDate(new Date()); //
			 * rlBillMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().
			 * getEmppiservername()); iRLBILLMasterService.updateRLBillMas(rlBillMaster); }
			 * else { // if advance payment Double paidInstallmentAmt = 0d; for (int i = 0;
			 * i < rlBillMasterList.size(); i++) { RLBillMaster rlBillMaster =
			 * rlBillMasterList.get(i); paidInstallmentAmt += rlBillMaster.getAmount(); if
			 * (requestDto.getInputAmount() >= paidInstallmentAmt) {
			 * rlBillMaster.setPaidAmount(rlBillMaster.getAmount());
			 * rlBillMaster.setBalanceAmount(0d); //
			 * rlBillMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			 * rlBillMaster.setUpdatedDate(new Date()); //
			 * rlBillMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().
			 * getEmppiservername()); iRLBILLMasterService.updateRLBillMas(rlBillMaster); }
			 * } }
			 */
			
			
			
			
			
			
			
			if (requestDto.getInputAmount().equals(rlBillMasterList.get(0).getAmount())) {
	            // getPayAmount equal receivable amount
	            RLBillMaster rlBillMaster = rlBillMasterList.get(0);
	            rlBillMaster.setPaidAmount(requestDto.getInputAmount());
	            rlBillMaster.setBalanceAmount(0d);
	            rlBillMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	            rlBillMaster.setUpdatedDate(new Date());
	            rlBillMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
	            iRLBILLMasterService.updateRLBillMas(rlBillMaster);
	        } else {
	        	Double paidInstallmentAmt = 0d;
	            Double inputAmount = 0d;
	        	// BigDecimal inputAmount = new BigDecimal(0.00);
	        	//inputAmount=BigDecimal.valueOf(getInputAmount());
	            inputAmount=requestDto.getInputAmount();
	            for (int i = 0; i < rlBillMasterList.size(); i++) {
	            RLBillMaster rlBillMaster = rlBillMasterList.get(i);
	            paidInstallmentAmt = rlBillMaster.getBalanceAmount();
	          
	            if (inputAmount >0 && inputAmount >=paidInstallmentAmt) {
	                rlBillMaster.setPaidAmount(rlBillMaster.getAmount());
	                
	                BigDecimal b1 = new BigDecimal(Double.toString(rlBillMaster.getBalanceAmount()));
	                BigDecimal b2 = new BigDecimal(Double.toString(inputAmount));
	                inputAmount=  b2.subtract(b1).doubleValue();
	                rlBillMaster.setBalanceAmount(0d);
	               
	                rlBillMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	                rlBillMaster.setUpdatedDate(new Date());
	                rlBillMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
	                iRLBILLMasterService.updateRLBillMas(rlBillMaster);
	            }else if(inputAmount >0 && inputAmount < paidInstallmentAmt) {
	                	
	                	 BigDecimal b1 = new BigDecimal(Double.toString(rlBillMaster.getBalanceAmount()));
	                     BigDecimal b2 = new BigDecimal(Double.toString(inputAmount));
	                     Double  balanceAmount=  b1.subtract(b2).doubleValue();
	                     rlBillMaster.setBalanceAmount(balanceAmount);
	                     if(rlBillMaster.getPaidAmount() != null){
	                     rlBillMaster.setPaidAmount(inputAmount + rlBillMaster.getPaidAmount());
	                     }else {
	                    	 rlBillMaster.setPaidAmount(inputAmount);
	                     }
	                     
	                     
	                     inputAmount=0d;
	                     
	                     rlBillMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	                     rlBillMaster.setUpdatedDate(new Date());
	                     rlBillMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
	                     iRLBILLMasterService.updateRLPartialBillMas(rlBillMaster);
	                }
	            }
	        }
	            
			requestDto.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
		} catch (Exception e) {
			requestDto.setStatus(MainetConstants.WebServiceStatus.FAIL);
		}

		return requestDto;

	}
	
	@Override
	public List<EstateBookingDTO> getDetailByAppIdAndOrgId(long ampApplicationId, long orgId) {
		List<EstateBookingDTO> masterDtolist = new ArrayList<>();
		try {
			List<EstateBooking> entitylist = estateBookingRepository.getBookingDetByApplIdAndOrgId(ampApplicationId,
					orgId);
			if (CollectionUtils.isNotEmpty(entitylist)) {
				entitylist.forEach(entity -> {
					EstateBookingDTO masterDto = new EstateBookingDTO();
					BeanUtils.copyProperties(entity, masterDto);
					masterDtolist.add(masterDto);
				});
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occurred while fething the Booking Details", exception);
		}
		return masterDtolist;
	}
	
	@Override
	public Long findPropIdByAppId(Long applicationId, long orgId) {
		return estateBookingRepository.getPropIdByAppId(applicationId,orgId);
	}
	
	@Transactional
    @WebMethod(exclude = true)
    private WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId) {

        WorkflowTaskActionResponse workflowResponse = null;
        try {
            String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getProcessName(serviceId, taskAction.getOrgId());
            if (StringUtils.isNotBlank(processName)) {

                WorkflowProcessParameter workflowDto = new WorkflowProcessParameter();
                workflowDto.setProcessName(processName);
                workflowDto.setWorkflowTaskAction(taskAction);
                workflowResponse = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowExecutionService.class).updateWorkflow(workflowDto);

            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return workflowResponse;
    }

	@Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
            String serviceShortCode) {

        boolean updateFlag = false;
        try {
            if (StringUtils.equalsIgnoreCase(eventName, serviceShortCode)) {

                if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)) {
                	estateBookingRepository.WaterTankerApprovalWorkflow(MainetConstants.FlagA,
                            taskAction.getEmpId(), taskAction.getApplicationId());
                } else if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.REJECTED)) {
                	estateBookingRepository.WaterTankerApprovalWorkflow(MainetConstants.FlagR,
                            taskAction.getEmpId(), taskAction.getApplicationId());
                }
                updateWorkflowTaskAction(taskAction, serviceId);
                updateFlag = true;

            }
        } catch (Exception exception) {
            LOGGER.error("Exception Occured while Updating workflow action task", exception);
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return updateFlag;
    }
	
	@Override
	public BookingReqDTO saveDriverDetail(BookingReqDTO bookingReqDTO) {
		TankerBookingDetailsEntity entity  = new TankerBookingDetailsEntity();
		EstateBooking estatebooking = new EstateBooking();
		BeanUtils.copyProperties(bookingReqDTO.getEstateBookingDTO(), estatebooking);
		entity.setEstateBooking(estatebooking);
		entity.setDriverId(bookingReqDTO.getTankerBookingDetailsDTO().getDriverId());
		entity.setRemark(bookingReqDTO.getTankerBookingDetailsDTO().getRemark());
		entity.setCreatedBy(bookingReqDTO.getTankerBookingDetailsDTO().getCreatedBy());
		entity.setCreatedDate(bookingReqDTO.getTankerBookingDetailsDTO().getCreatedDate());
		entity.setOrgId(bookingReqDTO.getTankerBookingDetailsDTO().getOrgId());
		entity.setLangId(bookingReqDTO.getTankerBookingDetailsDTO().getLangId());
		entity.setLgIpMac(bookingReqDTO.getTankerBookingDetailsDTO().getLgIpMac());
		entity.setLgIpMacUp(bookingReqDTO.getTankerBookingDetailsDTO().getLgIpMacUp());
		entity = waterTankerBookingRepository.save(entity);
		bookingReqDTO.getTankerBookingDetailsDTO().setId(entity.getId());
		return bookingReqDTO;	
	}
	
	@Override
	public void saveReturnDetail(BookingReqDTO bookingReqDTO) {
		Date returndate= bookingReqDTO.getTankerBookingDetailsDTO().getTankerReturnDate();
		String returnRemark = bookingReqDTO.getTankerBookingDetailsDTO().getReturnRemark();
		Long updatedBy = bookingReqDTO.getTankerBookingDetailsDTO().getUpdatedBy();
		Date updartedDate= bookingReqDTO.getTankerBookingDetailsDTO().getUpdatedDate();
		Long id = bookingReqDTO.getEstateBookingDTO().getId();
		waterTankerBookingRepository.update(returndate,returnRemark,updatedBy,updartedDate,id);
		Character flag = 'O';
		estatePropertyRepository.updateStatus(flag,bookingReqDTO.getEstatePropResponseDTO().getPropId());
	}
	
	@Override
	public TankerBookingDetailsDTO getDriverData(Long id) {
		
		TankerBookingDetailsEntity entity = waterTankerBookingRepository.findByEbkId(id);
		TankerBookingDetailsDTO dto = new TankerBookingDetailsDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public BookingReqDTO getWaterTankerDetailByAppId(Long applicationId, Long orgId) {
		BookingReqDTO bookingReqDTO= new BookingReqDTO();
		ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
		applicantDetail.setOrgId(orgId);
		List<EstateBooking> bookingdetails= estateBookingRepository.getBookingDetByApplIdAndOrgId(applicationId,orgId);
		EstateBookingDTO dto = null;
		for(EstateBooking bookingdetail : bookingdetails) {
			dto = new EstateBookingDTO();
			BeanUtils.copyProperties(bookingdetail, dto);
		}
		bookingReqDTO.setEstateBookingDTO(dto);
		Long propId = findPropIdByAppId(applicationId,orgId);
		final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(propId,orgId);
		bookingReqDTO.setEstatePropResponseDTO(estatePropResponseDTO);
		bookingReqDTO.getEstatePropResponseDTO().setEventDTOList(estatePropResponseDTO.getEventDTOList());
		//setApmApplicationId(applicationId);
		bookingReqDTO.setApplicantDetailDto(populateApplicantionDetails(applicantDetail,applicationId));
		//setPayableFlag(MainetConstants.FlagN);
		//setPropId(propId);
		bookingReqDTO.getEstatePropResponseDTO().setShiftDTOsList(estatePropResponseDTO.getShiftDTOsList());
		return bookingReqDTO;
	}
	
	public ApplicantDetailDTO populateApplicantionDetails(ApplicantDetailDTO detailDto, Long applicationNo) {

		TbCfcApplicationMstEntity masterEntity = cfcApplicationService.getCFCApplicationByApplicationId(applicationNo,
				detailDto.getOrgId());

		if (masterEntity != null) {
			detailDto.setApplicantTitle(masterEntity.getApmTitle());
			detailDto.setApplicantFirstName(masterEntity.getApmFname());
			detailDto.setApplicantLastName(masterEntity.getApmLname());
			detailDto.setGender(masterEntity.getApmSex());
			if (StringUtils.isNotBlank(masterEntity.getApmMname())) {
				detailDto.setApplicantMiddleName(masterEntity.getApmMname());
			}
			if (StringUtils.isNotBlank(masterEntity.getApmBplNo())) {
				detailDto.setIsBPL(MainetConstants.YES);
				detailDto.setBplNo(masterEntity.getApmBplNo());
			} else {
				detailDto.setIsBPL(MainetConstants.NO);
			}

			if (masterEntity.getApmUID() != null && masterEntity.getApmUID() != 0) {
				detailDto.setAadharNo(String.valueOf(masterEntity.getApmUID()));
			}
			/*
			 * if (masterEntity.ge != null && masterEntity.getApmUID() != 0) {
			 * detailDto.setPanNo(panNo); }
			 */
		}

		CFCApplicationAddressEntity addressEntity = cfcApplicationService.getApplicantsDetails(applicationNo);
		if (addressEntity != null) {
			detailDto.setMobileNo(addressEntity.getApaMobilno());
			detailDto.setEmailId(addressEntity.getApaEmail());
			detailDto.setAreaName(addressEntity.getApaAreanm());
			detailDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
			if (addressEntity.getApaZoneNo() != null && addressEntity.getApaZoneNo() != 0) {
				detailDto.setDwzid1(addressEntity.getApaZoneNo());
			}
			if (addressEntity.getApaWardNo() != null && addressEntity.getApaWardNo() != 0) {
				detailDto.setDwzid2(addressEntity.getApaWardNo());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBlockno())) {
				detailDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
			}
			if (StringUtils.isNotBlank(addressEntity.getApaCityName())) {
				detailDto.setVillageTownSub(addressEntity.getApaCityName());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaRoadnm())) {
				detailDto.setRoadName(addressEntity.getApaRoadnm());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaFlatBuildingNo())) {
				detailDto.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBldgnm())) {
				detailDto.setBuildingName(addressEntity.getApaBldgnm());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBlockName())) {
				detailDto.setBlockName(addressEntity.getApaBlockName());
			}
		}
		return detailDto;
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<LookUp> getEstateBookingShiftsData(final Long propId, final Long orgId) {
		List<LookUp> lookupShift = new ArrayList<>();
		List<Long> propShiftList = estatePropertyRepository.getPropShiftInfo(propId, orgId);

		propShiftList.forEach(propShift -> {
			if(propShift!=null) {
			LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(propShift, PrefixConstants.SHIFT_PREFIX, orgId);
			lookupShift.add(lookUp);
	}
		});

		return lookupShift;
	}

	
	

}
