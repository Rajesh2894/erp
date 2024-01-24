package com.abm.mainet.tradeLicense.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.tradeLicense.datamodel.MLNewTradeLicense;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterCategoryDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created Date:24/05/2019
 * 
 * @author Gayatri.Kokane
 *
 */

@Service(value = "ChangeCategorySubCategoryService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.IChangeCategorySubCategoryService")
@Api(value = "/changeCategorySubCategoryService")
@Path("/changeCategorySubCategoryService")
public class ChangeCategorySubCategoryServiceImpl implements IChangeCategorySubCategoryService {

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private TradeLicenseApplicationRepository tradeLicenseApplicationRepository;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private TbCfcApplicationMstService cfcApplicationMstService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TbTaxMasService tbTaxMasService;
	

	@Autowired
	private TbDepartmentService tbDepartmentService;
	
	@Autowired
	AuditService auditService;
	private static final Logger LOGGER = Logger.getLogger(ChangeCategorySubCategoryServiceImpl.class);

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CATEGORY_SUBCATEGORY_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CATEGORY_SUBCATEGORY_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getCatgorySubCategoryChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getCategoryChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException {

		// application time

		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());
		Date todayDate = new Date();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(
						MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
						masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		if (masDto.getTrdId() != null) {

			for (TbTaxMasEntity taxes : taxesMaster) {
				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE);
				license.setTaxType(MainetConstants.TradeLicense.TAX_TYPE);
				license.setTaxCode(taxes.getTaxCode());
				settingTaxCategories(license, taxes, organisation);
				// license.setTaxCategory(MainetConstants.TradeLicense.TAX_CATEGORY);
				// license.setTaxSubCategory("Application Charge");
				license.setRateStartDate(todayDate.getTime());
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

		// setChargeToItemsDtoList(master, masDto);

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {

			masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
			masDto.getFeeIds().put(tbTaxMas.getTaxId(), masDto.getTotalApplicationFee().doubleValue());
		}

		masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
		return masDto;

	}

	private TradeMasterDetailDTO setChargeToItemsDtoList(List<MLNewTradeLicense> master, TradeMasterDetailDTO masDto) {

		if (masDto.getApmApplicationId() != null)
			masDto.getTradeLicenseItemDetailDTO().forEach(entity -> {
				master.forEach(model -> {
					if (entity.getItemCategory1().equals(model.getItemCategory1())
							&& entity.getItemCategory2().equals(model.getItemCategory2())
							&& entity.getItemCategory3().equals(model.getItemCategory3())
							&& entity.getItemCategory4().equals(model.getItemCategory4())
							&& entity.getItemCategory5().equals(model.getItemCategory5())) {
						entity.setTriRate(new BigDecimal(model.getFlatRate()));
					}
				});
			});
		masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
		masDto.setApplicationCharge(master.get(0).getTaxSubCategory());

		return masDto;
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

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.SAVE_CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_APPLICATION, notes = MainetConstants.TradeLicense.SAVE_CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_APPLICATION, response = TradeMasterDetailDTO.class)
	@Path("/saveChangeInCategorySubCategoryService")
	@Transactional
	public TradeMasterDetailDTO saveCategoryServiceFromPortal(TradeMasterCategoryDto dto) {
		return saveChangeCategorySubcategoryService(dto.getTradeMasterDetailDTO(), dto.getTradeDTO());

	}

	@Transactional
	public TradeMasterDetailDTO saveChangeCategorySubcategoryService(TradeMasterDetailDTO masDto,
			TradeMasterDetailDTO tradeDto) {
		try {

			LOGGER.info("saveAndUpdateApplication started");
			masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				if (!itemDto.isSelectedItems()) {
					// itemDto.setTriStatus("Y");
				} else {
					itemDto.setTriStatus("A");
				}
			});
			TbMlTradeMast masEntity = mapDtoToEntity(masDto);
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(
							MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
							masDto.getOrgid());
			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);

			/* if (masEntity.getApmApplicationId() == null) */
			{

				final Long appicationId = ApplicationContextProvider.getApplicationContext()
						.getBean(ApplicationService.class).createApplication(requestDto);
				LOGGER.info("application number for change in category and sub category : " + appicationId);
			

				// to save application id in master , owner and item detail table
				masEntity.setApmApplicationId(appicationId);
				masDto.setApmApplicationId(appicationId);
				requestDto.setApplicationId(appicationId);
				masEntity.getItemDetails().forEach(itemEntity -> {
					itemEntity.setApmApplicationId(masDto.getApmApplicationId());
				});
				masEntity.getOwnerDetails().forEach(ownerEntity -> {
					ownerEntity.setApmApplicationId(masDto.getApmApplicationId());
				});
			}
			/*
			 * if ((masDto.getDocumentList() != null) &&
			 * !masDto.getDocumentList().isEmpty()) {
			 * 
			 * fileUploadService.doFileUpload(masDto.getDocumentList(), requestDto); }
			 */

			tradeLicenseApplicationRepository.save(masEntity);
           
			for (TradeLicenseItemDetailDTO dto : tradeDto.getTradeLicenseItemDetailDTO()) {
				TbMlItemDetail itemEntity = new TbMlItemDetail();
				dto.setTriStatus(MainetConstants.FlagN);
				BeanUtils.copyProperties(dto, itemEntity);
				masEntity.getItemDetails().add(itemEntity);
			}
			saveHistoryData(masEntity);

			for (TradeLicenseItemDetailDTO itemDetailDto : tradeDto.getTradeLicenseItemDetailDTO()) {

				if (itemDetailDto.getTriId() != null) {

					boolean detExit = masDto.getTradeLicenseItemDetailDTO().stream().filter(k -> k.getTriId() != null)
							.filter(s -> s.getTriId().equals(itemDetailDto.getTriId())).findFirst().isPresent();
					if (!detExit) {

						tradeLicenseApplicationRepository.updateDeletedFlag(itemDetailDto.getTriId(),
								itemDetailDto.getOrgid());
					}
				}

				LOGGER.info("saveAndUpdateApplication End");
			}

			boolean checklist = false;
			if ((masDto.getDocumentList() != null) && !masDto.getDocumentList().isEmpty()) {
				checklist = fileUploadService.doFileUpload(masDto.getDocumentList(), requestDto);
				checklist = true;
			}

			// initiate workflow

			if (masDto.isFree()) {

				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(masDto.getApmApplicationId());
				applicationData.setOrgId(masDto.getOrgid());
				applicationData.setIsCheckListApplicable(checklist);
				applicationData.setIsFreeService(true);

				ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();

				applicantDetailDTO.setUserId(masDto.getUserId());
				applicantDetailDTO.setServiceId(sm.getSmServiceId());
				applicantDetailDTO.setDepartmentId(sm.getTbDepartment().getDpDeptid());
				if (null != masDto.getTrdWard1())
				applicantDetailDTO.setDwzid1(masDto.getTrdWard1());
				if (null != masDto.getTrdWard2())
				applicantDetailDTO.setDwzid2(masDto.getTrdWard2());
				if (null != masDto.getTrdWard3())
					applicantDetailDTO.setDwzid3(masDto.getTrdWard3());
				if (null != masDto.getTrdWard4())
					applicantDetailDTO.setDwzid4(masDto.getTrdWard4());
				if (null != tradeDto.getTrdWard5())
					applicantDetailDTO.setDwzid5(tradeDto.getTrdWard5());

				masDto.getApplicantDetailDto().setUserId(masDto.getUserId());
				masDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				masDto.getApplicantDetailDto().setDepartmentId(sm.getTbDepartment().getDpDeptid());

				commonService.initiateWorkflowfreeService(applicationData, applicantDetailDTO);
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Change in Category and Sub Category Service Application ",
					exception);
			throw new FrameworkException(
					"Exception occur while saving Change in Category and Sub Category Service Application  ",
					exception);
		}

		return masDto;
	}

	private TbMlTradeMast mapDtoToEntity(TradeMasterDetailDTO masDto) {
		TbMlTradeMast masEntity = new TbMlTradeMast();
		List<TbMlItemDetail> itemdDetailsList = new ArrayList<>();

		String ipAddress = masDto.getLgIpMac();
		Long empId = masDto.getCreatedBy();

		BeanUtils.copyProperties(masDto, masEntity);
		masDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemEntity.setOrgid(masDto.getOrgid());
			itemEntity.setCreatedBy(empId);

			itemEntity.setLgIpMac(ipAddress);
			itemEntity.setCreatedDate(new Date());
			itemdDetailsList.add(itemEntity);

			if (itemEntity.getTriId() == null) {
				itemEntity.setTriStatus("Y");
			}
		});

		masEntity.setItemDetails(itemdDetailsList);
		return masEntity;
	}

	private RequestDTO setApplicantRequestDto(TradeMasterDetailDTO tradeMasterDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		if (CollectionUtils.isNotEmpty(tradeMasterDto.getTradeLicenseOwnerdetailDTO())) {
		TradeLicenseOwnerDetailDTO ownerDetails = tradeMasterDto.getTradeLicenseOwnerdetailDTO().get(0);
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(ownerDetails.getCreatedBy());
		requestDto.setOrgId(tradeMasterDto.getOrgid());
		requestDto.setLangId(tradeMasterDto.getLangId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(ownerDetails.getTroName());
		requestDto.setEmail(ownerDetails.getTroEmailid());
		requestDto.setMobileNo(ownerDetails.getTroMobileno());
		requestDto.setAreaName(ownerDetails.getTroAddress());
		//requestDto.setReferenceId(tradeMasterDto.getTrdLicno());
		requestDto.setPayStatus(MainetConstants.PAYMENT.FREE);
		requestDto.setApplicationId(tradeMasterDto.getApmApplicationId());
		}
		return requestDto;
	}

	@Override
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId) {

		TbCfcApplicationMst entity = cfcApplicationMstService.findById(applicationId);
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService
				.getLicenseDetailsByLicenseNo(entity.getRefNo(), orgId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (tradeDetail != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (tradeDetail.getTrdWard1() != null) {
				wardZoneDTO.setAreaDivision1(tradeDetail.getTrdWard1());
			}
			if (tradeDetail.getTrdWard2() != null) {
				wardZoneDTO.setAreaDivision2(tradeDetail.getTrdWard2());
			}
			if (tradeDetail.getTrdWard3() != null) {
				wardZoneDTO.setAreaDivision3(tradeDetail.getTrdWard3());
			}
			if (tradeDetail.getTrdWard4() != null) {
				wardZoneDTO.setAreaDivision4(tradeDetail.getTrdWard4());
			}
			if (tradeDetail.getTrdWard5() != null) {
				wardZoneDTO.setAreaDivision5(tradeDetail.getTrdWard5());
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
		//TbCfcApplicationMst entity = cfcApplicationMstService.findById(applicationId);
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);

		final LookUp chargeApplicableAtScrutiny = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(
						MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE, orgId);

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAtScrutiny.getLookUpId());

		double amount = tradeDetail.getTradeLicenseItemDetailDTO().stream()
				.filter(c -> c.getTriRate() != null && c.getTriStatus().equals("A"))
				.mapToDouble(c -> c.getTriRate().doubleValue()).sum();
		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			chargeMap.put(tbTaxMas.getTaxId(), amount);
		}

		TradeMasterDetailDTO tradeLicenceCharges = getCategoryWiseLoiChargesFromBrmsRule(tradeDetail);
		return tradeLicenceCharges.getFeeIds();
	}

	@Override
	public TradeMasterDetailDTO getCategoryWiseLoiChargesFromBrmsRule(TradeMasterDetailDTO masDto) {

		// scrutiny time

		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());
		Date todayDate = new Date();

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAtScrutiny = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster smm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(
						MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
						masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(smm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAtScrutiny.getLookUpId());

		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
		try {
			lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
					masDto.getOrgid());
			lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 2,
					masDto.getOrgid());
			lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 3,
					masDto.getOrgid());
			lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 4,
					masDto.getOrgid());
			lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 5,
					masDto.getOrgid());
		} catch (Exception e) {
			LOGGER.info("prefix level not found");
		}

		// tradeLicenseItemDetailDTO.parallelStream().forEach(dto -> {

		for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
			MLNewTradeLicense license = new MLNewTradeLicense();
			license.setOrgId(dto.getOrgid());
			license.setServiceCode(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE);
			//to set tax type
			Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
			Department dept = tbDepartmentService.findDepartmentByCode("ML");
			TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(orgId, dept.getDpDeptid(),
					taxesMaster.get(0).getTaxCode());
			String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
					MainetConstants.FlagE, orgId);
			license.setTaxCode(taxesMaster.get(0).getTaxCode()); // for getting scrutiny level tax code(category and // subcategory)
			license.setTaxType(taxType);		
			if (tax != null && tax.getParentCode() != null) {
				TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), orgId);
				license.setParentTaxCode(tbTax.getTaxCode());
			}
			license.setTaxCategory(CommonMasterUtility
					.getHierarchicalLookUp(taxesMaster.get(0).getTaxCategory1(), organisation).getDescLangFirst());
			license.setTaxSubCategory(CommonMasterUtility
					.getHierarchicalLookUp(taxesMaster.get(0).getTaxCategory2(), organisation).getDescLangFirst());
			license.setRateStartDate(todayDate.getTime());
			license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
			license.setApplicableAt(chargeApplicableAtScrutiny.getDescLangFirst());

			if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
				List<LookUp> level1 = lookupListLevel1.parallelStream()
						.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
						.collect(Collectors.toList());
				if (level1 != null && !level1.isEmpty()) {
					license.setItemCategory1(level1.get(0).getDescLangFirst());
					dto.setItemCategory1(level1.get(0).getDescLangFirst());
				}
			} else {
				dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
			}
			if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
				List<LookUp> level2 = lookupListLevel2.parallelStream()
						.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
						.collect(Collectors.toList());
				if (level2 != null && !level2.isEmpty()) {
					license.setItemCategory2(level2.get(0).getDescLangFirst());
					dto.setItemCategory2(level2.get(0).getDescLangFirst());
				}
			} else {
				dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
			}
			if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
				List<LookUp> level3 = lookupListLevel3.parallelStream()
						.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
						.collect(Collectors.toList());
				if (level3 != null && !level3.isEmpty()) {
					license.setItemCategory3(level3.get(0).getDescLangFirst());
					dto.setItemCategory2(level3.get(0).getDescLangFirst());
				}
			} else {
				dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
			}
			if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
				List<LookUp> level4 = lookupListLevel4.parallelStream()
						.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
						.collect(Collectors.toList());
				if (level4 != null && !level4.isEmpty()) {
					license.setItemCategory4(level4.get(0).getDescLangFirst());
					dto.setItemCategory4(level4.get(0).getDescLangFirst());
				}
			} else {
				dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
			}
			if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
				List<LookUp> level5 = lookupListLevel5.parallelStream()
						.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
						.collect(Collectors.toList());
				if (level5 != null && !level5.isEmpty()) {
					license.setItemCategory5(level5.get(0).getDescLangFirst());
					dto.setItemCategory5(level5.get(0).getDescLangFirst());
				}
			} else {
				dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
			}
			license.setArea(dto.getTrdUnit());
			masterList.add(license);

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

		setChargeToItemsDtoList(master, masDto);

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {

			masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getSlabRate1()).sum()));
			masDto.getFeeIds().put(tbTaxMas.getTaxId(), masDto.getTotalApplicationFee().doubleValue());
		}

		masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getSlabRate1()).sum()));
		
		return masDto;
	}
	
	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);
		
		TbMlTradeMastHist.setHistoryStatus(MainetConstants.FlagU);
		List<TbMlOwnerDetailHist> tbMlOwnerDetailHistList = new ArrayList<>();
		List<TbMlItemDetailHist> tbMlItemDetailHistList = new ArrayList<>();
		masEntity.getOwnerDetails().forEach(ownerEntity -> {

			TbMlOwnerDetailHist tbMlOwnerDetailHistEntity = new TbMlOwnerDetailHist();

			BeanUtils.copyProperties(ownerEntity, tbMlOwnerDetailHistEntity);
			tbMlOwnerDetailHistEntity.setHistoryStatus(MainetConstants.FlagU);
			tbMlOwnerDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlOwnerDetailHistList.add(tbMlOwnerDetailHistEntity);

		});
		masEntity.getItemDetails().forEach(itemEntity -> {

			TbMlItemDetailHist TbMlItemDetailHistEntity = new TbMlItemDetailHist();
           
			BeanUtils.copyProperties(itemEntity, TbMlItemDetailHistEntity);
			TbMlItemDetailHistEntity.setHistoryStatus(MainetConstants.FlagU);
			TbMlItemDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			 if (TbMlItemDetailHistEntity.getTriId() != null) {
			tbMlItemDetailHistList.add(TbMlItemDetailHistEntity);
			 }

		});

		TbMlTradeMastHist.setItemDetails(tbMlItemDetailHistList);
		TbMlTradeMastHist.setOwnerDetails(tbMlOwnerDetailHistList);
		TbMlTradeMastHist.setHistoryStatus(MainetConstants.FlagU);
		auditService.createHistoryForObject(TbMlTradeMastHist);
		// end history
	}
}
