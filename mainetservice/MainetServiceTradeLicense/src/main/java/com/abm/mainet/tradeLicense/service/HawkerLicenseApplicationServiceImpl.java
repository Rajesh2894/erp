package com.abm.mainet.tradeLicense.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.tradeLicense.datamodel.MLNewTradeLicense;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerFamilyDetail;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Service(value = "IHawkerLicenseApplicationService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.IHawkerLicenseApplicationService")
@Api(value = "/hawkerLicenseApplicationService")
@Path("/hawkerLicenseApplicationService")
public class HawkerLicenseApplicationServiceImpl implements IHawkerLicenseApplicationService {

	private static final Logger LOGGER = Logger.getLogger(HawkerLicenseApplicationServiceImpl.class);

	@Autowired
	private TradeLicenseApplicationRepository tradeLicenseApplicationRepository;
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	AuditService auditService;
	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private ILicenseValidityMasterService licenseValidityMasterService;

	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.SAVE_UPDATE_TRADE_LICENSE_APPLICATION, notes = MainetConstants.TradeLicense.SAVE_UPDATE_TRADE_LICENSE_APPLICATION, response = TradeMasterDetailDTO.class)
	@Path("/tradeLicenseApplication")
	@Transactional
	public TradeMasterDetailDTO saveAndUpdateApplication(@RequestBody TradeMasterDetailDTO tradeMasterDto) {

		try {
			Long appicationId = null;
			LOGGER.info("saveAndUpdateApplication started");
			tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				if (!itemDto.isSelectedItems()) {
					itemDto.setTriRate(null);
					itemDto.setTriStatus(MainetConstants.FlagY);
				} else {
					itemDto.setTriStatus(MainetConstants.FlagA);
				}
			});

			tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
				ownDto.setTroPr(MainetConstants.FlagA);
			});

			TbMlTradeMast masEntity = mapDtoToEntity(tradeMasterDto);

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("NHL", tradeMasterDto.getOrgid());
			RequestDTO requestDto = setApplicantRequestDto(tradeMasterDto, sm);
			if (masEntity.getApmApplicationId() == null) {

				appicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
						.createApplication(requestDto);

				LOGGER.info("application number for new trade licence : " + appicationId);
				masEntity.setApmApplicationId(appicationId);
				tradeMasterDto.setApmApplicationId(appicationId);
			}
			masEntity = tradeLicenseApplicationRepository.save(masEntity);

			boolean checklist = false;
			if ((tradeMasterDto.getDocumentList() != null) && !tradeMasterDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(appicationId);
				checklist = fileUploadService.doFileUpload(tradeMasterDto.getDocumentList(), requestDto);
				checklist = true;
			}

			int i = 0;
			if ((tradeMasterDto.getAttachments() != null) && !tradeMasterDto.getAttachments().isEmpty()) {
				List<DocumentDetailsVO> getImgList = null;
				for (final TbMlOwnerDetail d : masEntity.getOwnerDetails()) {
					getImgList = new ArrayList<>();

					requestDto.setReferenceId(d.getTroId().toString());
					requestDto.setApplicationId(d.getTroId());
					List<DocumentDetailsVO> getList = tradeMasterDto.getAttachments();
					for (int j = i; j < getList.size(); j++) {
						DocumentDetailsVO img = getList.get(i);
						getImgList.add(img);
						break;

					}

					i++;
					fileUploadService.doFileUpload(getImgList, requestDto);

				}

			}

			LOGGER.info("saveAndUpdateApplication End");

			if (tradeMasterDto.isFree()) {
				iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(tradeMasterDto.getApmApplicationId(),
						MainetConstants.PAY_STATUS.PAID, tradeMasterDto.getOrgId());

				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(tradeMasterDto.getApmApplicationId());
				applicationData.setOrgId(tradeMasterDto.getOrgId());
				applicationData.setIsCheckListApplicable(checklist);
				tradeMasterDto.getApplicantDetailDto().setUserId(tradeMasterDto.getUserId());
				tradeMasterDto.getApplicantDetailDto().setServiceId(tradeMasterDto.getServiceId());
				tradeMasterDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(tradeMasterDto.getDeptId()));

				if (tradeMasterDto.getTrdWard1() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid1(tradeMasterDto.getTrdWard1());
				}
				if (tradeMasterDto.getTrdWard2() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid2(tradeMasterDto.getTrdWard2());
				}
				if (tradeMasterDto.getTrdWard3() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid3(tradeMasterDto.getTrdWard3());
				}
				if (tradeMasterDto.getTrdWard4() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid4(tradeMasterDto.getTrdWard4());
				}
				if (tradeMasterDto.getTrdWard5() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid5(tradeMasterDto.getTrdWard5());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, tradeMasterDto.getApplicantDetailDto());
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Trade License Application ", exception);
			throw new FrameworkException("Exception occur while saving Trade License Application ", exception);
		}

		return tradeMasterDto;
	}

	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);

		List<TbMlOwnerDetailHist> tbMlOwnerDetailHistList = new ArrayList<>();
		List<TbMlItemDetailHist> tbMlItemDetailHistList = new ArrayList<>();
		masEntity.getOwnerDetails().forEach(ownerEntity -> {

			TbMlOwnerDetailHist tbMlOwnerDetailHistEntity = new TbMlOwnerDetailHist();

			BeanUtils.copyProperties(ownerEntity, tbMlOwnerDetailHistEntity);

			tbMlOwnerDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlOwnerDetailHistList.add(tbMlOwnerDetailHistEntity);

		});
		masEntity.getItemDetails().forEach(itemEntity -> {

			TbMlItemDetailHist TbMlItemDetailHistEntity = new TbMlItemDetailHist();

			BeanUtils.copyProperties(itemEntity, TbMlItemDetailHistEntity);

			TbMlItemDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlItemDetailHistList.add(TbMlItemDetailHistEntity);

		});

		TbMlTradeMastHist.setItemDetails(tbMlItemDetailHistList);
		TbMlTradeMastHist.setOwnerDetails(tbMlOwnerDetailHistList);
		auditService.createHistoryForObject(TbMlTradeMastHist);
	}

	/**
	 * used to map DTO Object to Entity
	 * 
	 * @param tradeMasterDto
	 * @return
	 */
	private TbMlTradeMast mapDtoToEntity(TradeMasterDetailDTO tradeMasterDto) {
		TbMlTradeMast masEntity = new TbMlTradeMast();
		List<TbMlItemDetail> itemdDetailsList = new ArrayList<>();
		List<TbMlOwnerDetail> ownerDetailsList = new ArrayList<>();
		List<TbMlOwnerFamilyDetail> ownerFamilyDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(tradeMasterDto, masEntity);
		tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemdDetailsList.add(itemEntity);
		});

		tradeMasterDto.getOwnerFamilydetailDTO().forEach(ownerFamilyDetails -> {
			TbMlOwnerFamilyDetail ownerFamilyEntity = new TbMlOwnerFamilyDetail();
			BeanUtils.copyProperties(ownerFamilyDetails, ownerFamilyEntity);
			ownerFamilyEntity.setMasterTradeId(masEntity);
			ownerFamilyEntity.setCreatedBy(tradeMasterDto.getCreatedBy());
			ownerFamilyEntity.setCreatedDate(tradeMasterDto.getCreatedDate());
			ownerFamilyEntity.setLgIpMac(tradeMasterDto.getLgIpMac());
			ownerFamilyEntity.setOrgid(tradeMasterDto.getOrgid());
			ownerFamilyDetailsList.add(ownerFamilyEntity);

		});

		tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownerDetails -> {
			TbMlOwnerDetail ownerEntity = new TbMlOwnerDetail();
			BeanUtils.copyProperties(ownerDetails, ownerEntity);
			ownerEntity.setMasterTradeId(masEntity);
			ownerDetailsList.add(ownerEntity);
		});

		masEntity.setOwnerDetails(ownerDetailsList);
		masEntity.setOwnerFamilyDetails(ownerFamilyDetailsList);
		masEntity.setItemDetails(itemdDetailsList);

		return masEntity;
	}

	/**
	 * used to map Entity To DTO
	 * 
	 * @param masEntity
	 * @return
	 */
	private TradeMasterDetailDTO mapEntityToDto(TbMlTradeMast masEntity) {

		TradeMasterDetailDTO masDto = new TradeMasterDetailDTO();
		List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
		List<TradeLicenseOwnerDetailDTO> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(masEntity, masDto);
		masEntity.getItemDetails().forEach(itemdDetails -> {
			TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
			BeanUtils.copyProperties(itemdDetails, itemDto);
			if (itemDto.getTriRate() != null) {
				itemDto.setSelectedItems(true);
			}
			itemDto.setMasterTradeId(masDto);
			itemdDetailsList.add(itemDto);
		});
		masEntity.getOwnerDetails().forEach(ownerDetails -> {
			TradeLicenseOwnerDetailDTO ownerDto = new TradeLicenseOwnerDetailDTO();
			BeanUtils.copyProperties(ownerDetails, ownerDto);
			ownerDto.setMasterTradeId(masDto);
			ownerDetailsList.add(ownerDto);
		});

		masDto.setTradeLicenseOwnerdetailDTO(ownerDetailsList);
		masDto.setTradeLicenseItemDetailDTO(itemdDetailsList);

		return masDto;

	}

	private RequestDTO setApplicantRequestDto(TradeMasterDetailDTO tradeMasterDto, ServiceMaster sm) {
		TradeLicenseOwnerDetailDTO ownerDetails = tradeMasterDto.getTradeLicenseOwnerdetailDTO().get(0);
		RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(ownerDetails.getCreatedBy());
		requestDto.setOrgId(tradeMasterDto.getOrgid());
		requestDto.setLangId((long) tradeMasterDto.getLangId());

		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(ownerDetails.getTroName());
		requestDto.setEmail(ownerDetails.getTroEmailid());
		requestDto.setMobileNo(ownerDetails.getTroMobileno());
		requestDto.setAreaName(ownerDetails.getTroAddress());
		requestDto.setIsBPL(tradeMasterDto.getIsBPL());

		if (tradeMasterDto.getTotalApplicationFee() != null) {
			requestDto.setPayAmount(tradeMasterDto.getTotalApplicationFee().doubleValue());
		}
		return requestDto;

	}

	private MLNewTradeLicense settingTaxCategories(MLNewTradeLicense mlNewTradeLicense, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				mlNewTradeLicense.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				mlNewTradeLicense.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return mlNewTradeLicense;

	}

	private TradeMasterDetailDTO setApplicationChargeToDtoList(List<MLNewTradeLicense> master,
			TradeMasterDetailDTO masDto) {

		master.forEach(model -> {

			if (model.getTaxSubCategory().equalsIgnoreCase("Application Charge")) {

				masDto.setApplicationCharge(
						BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()).toString());
				masDto.setTotalApplicationFee(
						BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
			}

		});

		return masDto;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getApplicationChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {
		// app
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("NHL", masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility
				.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2, masDto.getOrgid()).getLookUpId();

		for (TbTaxMasEntity taxes : taxesMaster) {

			if ((taxes.getTaxCategory2() == appChargetaxId)) {

				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode("NHL");

				TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(),
						sm.getTbDepartment().getDpDeptid(), taxes.getTaxCode());
				String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
						MainetConstants.FlagE, masDto.getOrgid());

				license.setTaxType(taxType);
				license.setTaxCode(taxes.getTaxCode());
				settingTaxCategories(license, taxes, organisation);
				// license.setRateStartDate(todayDate.getTime());
				license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
				license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
				masterList.add(license);
			}

		}

		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
		WSResponseDTO responseDTO = new WSResponseDTO();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		List<MLNewTradeLicense> master = new ArrayList<>();

		wsRequestDTO.setDataModel(masterList);
		try {
			LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
			responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				master = setTradeLicenceChargesDTO(responseDTO);
			} else {
				throw new FrameworkException(responseDTO.getErrorMessage());
			}
		} catch (Exception ex) {
			throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
		}

		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");

		setApplicationChargeToDtoList(master, masDto);

		return masDto;
	}

	private List<MLNewTradeLicense> setTradeLicenceChargesDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setTradeLicenceChargesDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, MLNewTradeLicense.class);
		List<MLNewTradeLicense> finalRateMaster = new ArrayList<>();
		for (Object rate : charges) {
			MLNewTradeLicense masterRate = (MLNewTradeLicense) rate;
			finalRateMaster.add(masterRate);
		}
		LOGGER.info("setTradeLicenceChargesDTO execution end..");
		return finalRateMaster;
	}

	public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId,
			TradeMasterDetailDTO tradeMasterDto) {

		Long licenseMaxTenureDays = 0l;
		Date currentDate = new Date();
		if (licToDate != null && Utility.compareDate(new Date(), licToDate)) {
			currentDate = licToDate;
		}
		List<LicenseValidityMasterDto> licValMasterDtoList = new ArrayList<>();
//The system should have provision to define Item Category and Sub-category in the license validity master User Story #113614
		List<LicenseValidityMasterDto> licValidityMster = new ArrayList<LicenseValidityMasterDto>();
		String skdclEnv = ApplicationContextProvider.getApplicationContext()
				.getBean(ITradeLicenseApplicationService.class).checkEnviorement();
		if (skdclEnv != null && skdclEnv.equals(MainetConstants.ENV_SKDCL)) {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId,
					tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(),MainetConstants.ZERO_LONG);
		} else {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId,
					MainetConstants.ZERO_LONG,MainetConstants.ZERO_LONG);
		}
		if (CollectionUtils.isNotEmpty(licValidityMster)) {

			licValMasterDtoList = licValidityMster.stream()
					.filter(k -> (k.getLicType() == tradeMasterDto.getTrdLictype().longValue()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(licValMasterDtoList) || licValMasterDtoList.size() <= 0) {

				return null;
			}

			LicenseValidityMasterDto licValMasterDto = licValMasterDtoList.get(0);

			Organisation organisationById = ApplicationContextProvider.getApplicationContext()
					.getBean(IOrganisationService.class).getOrganisationById(orgId);
			LookUp dependsOnLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(licValMasterDto.getLicDependsOn(), organisationById);

			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
					organisationById);

			Long tenure = Long.valueOf(licValMasterDto.getLicTenure());
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagD)) {
				licenseMaxTenureDays = tenure - 1;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagH)) {
				licenseMaxTenureDays = 1l;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagM)) {
				int currentYear = Integer.valueOf(Year.now().toString());
				Month monthObject = Month.from(LocalDate.now());
				int month = monthObject.getValue();
				licenseMaxTenureDays = Long.valueOf(YearMonth.of(currentYear, month).lengthOfMonth());
				if (tenure > 1) {
					for (int i = 2; i <= tenure; i++) {
						licenseMaxTenureDays = licenseMaxTenureDays
								+ Long.valueOf(YearMonth.of(currentYear, ++month).lengthOfMonth());
						if (month == 12) {
							month = 0;
							currentYear++;
						}
					}
				}
			}

			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagY)) {
				if (StringUtils.equalsIgnoreCase(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagF)) {
					int month = 0;
					int currentYear = Integer.valueOf(Year.now().toString());
					TbFinancialyear financialYear;
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					int monthValue = currLocalDate.getMonthValue();
					int currentMonthValue = currLocalDate.getMonthValue();

					if (monthValue > 3 && monthValue <= 12) {

						for (int i = monthValue; i <= 15; i++) {

							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;

								monthValue++;

							} else {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}

							}
						}

					} else {
						for (int i = monthValue; i <= 3; i++) {
							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;
								monthValue++;
								// Long currMonthDays = Long.valueOf(YearMonth.of(currentYear,
								// monthValue).lengthOfMonth());
							} else {
								licenseMaxTenureDays = licenseMaxTenureDays
										+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
								monthValue++;
							}

						}
					}
					if (tenure > 1) {
						for (int i = 2; i <= tenure; i++) {
							monthValue = 4;
							currentYear++;
							month = 0;
							for (int j = monthValue; j <= 15; j++) {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}
							}
						}
					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagC)) {
					int currentYear = Integer.valueOf(Year.now().toString());
					LocalDate currLocalDate = LocalDate.now();
					LocalDate with = currLocalDate.with(lastDayOfYear());

					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
							Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));
					if (tenure > 1) {

						for (int i = 2; i <= tenure; i++) {
							Year year = Year.of(++currentYear);
							licenseMaxTenureDays = licenseMaxTenureDays + Long.valueOf(year.length());
						}

					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagA)) {
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					Instant instant1 = LocalDate
							.of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
									currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth())
							.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Date from1 = Date.from(instant1);
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1));

				}
			}
		}

		return licenseMaxTenureDays;

	}

	@Override
	public Long validateAdharNumber(Long adharNumber, Long orgId) {
		long count = 0;
		count = tradeLicenseApplicationRepository.getCountbyAdharNumber(adharNumber, orgId);
		return count;
	}

	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {

		TradeMasterDetailDTO entity = iTradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (entity != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (entity.getTrdWard1() != null) {
				wardZoneDTO.setAreaDivision1(entity.getTrdWard1());
			}
			if (entity.getTrdWard2() != null) {
				wardZoneDTO.setAreaDivision2(entity.getTrdWard2());
			}
			if (entity.getTrdWard3() != null) {
				wardZoneDTO.setAreaDivision3(entity.getTrdWard3());
			}
			if (entity.getTrdWard4() != null) {
				wardZoneDTO.setAreaDivision4(entity.getTrdWard4());
			}
			if (entity.getTrdWard5() != null) {
				wardZoneDTO.setAreaDivision5(entity.getTrdWard5());
			}
		}

		return wardZoneDTO;
	}

}
