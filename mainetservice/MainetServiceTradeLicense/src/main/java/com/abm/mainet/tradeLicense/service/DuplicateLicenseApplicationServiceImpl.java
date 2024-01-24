package com.abm.mainet.tradeLicense.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
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

import com.abm.mainet.cfc.loi.domain.DishonourChargeEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.repository.DishonurChargeEntryRepository;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.datamodel.MLNewTradeLicense;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.repository.TradeMasterHistoryDetailsRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created Date:19/03/2019
 * 
 * @author Gayatri.Kokane
 *
 */

@Service(value = "DuplicateLicenseService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.IDuplicateLicenseApplicationService")
@Api(value = "/duplicateLicenseApplicationService")
@Path("/duplicateLicenseApplicationService")
public class DuplicateLicenseApplicationServiceImpl implements IDuplicateLicenseApplicationService {

	@Autowired
	private IFileUploadService fileUploadService;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private TbCfcApplicationMstService cfcApplicationMstService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private TradeLicenseApplicationRepository tradeApplicationRepository;
	@Autowired
	private AuditService auditService;
	@Autowired

	private TbLoiMasService tbLoiMasService;

	@Autowired
	TbLoiDetService tbLoiDetService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	IOrganisationService organisationService;

	@Autowired
	private DishonurChargeEntryRepository tbDisHnrRepo;
	@Autowired
	private TradeMasterHistoryDetailsRepository tradeDetailsHistRepo;

	private static final Logger LOGGER = Logger.getLogger(DuplicateLicenseApplicationServiceImpl.class);

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_DUPLICATE_SERVICE_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_DUPLICATE_SERVICE_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getDuplicateChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getDuplicateChargesFromBrmsRule(TradeMasterDetailDTO masDto) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());
		Date todayDate = new Date();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
						masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());
		
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

			if (lookUp.getLookUpCode().equals("STR")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TDLF")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SDLF")) {
				        iterator.remove();
				    }
				}
			}
		}
		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
		
		if (masDto.getTrdId() != null) {

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
		}

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		if (masDto.getTrdId() != null) {

			for (TbTaxMasEntity taxes : taxesMaster) {
				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);
				TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(),
						sm.getTbDepartment().getDpDeptid(), taxes.getTaxCode());
				String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
						MainetConstants.FlagE, masDto.getOrgid());
				license.setTaxType(taxType);
				license.setTaxCode(taxes.getTaxCode());
				settingTaxCategories(license, taxes, organisation);
				// license.setTaxCategory(taxes.getTaxCategory1().toString());
				// license.setTaxSubCategory(taxes.getTaxCategory2().toString());
				license.setRateStartDate(todayDate.getTime());
				license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
				license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
				// #142577-set parentTaxValue for TSCL project
				if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)
						&& taxes.getParentCode() != null) {
					LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
							organisation);
					license.setLicenseType(licenseType.getDescLangFirst());
					for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
						if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
							List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
									.collect(Collectors.toList());
							if (level1 != null && !level1.isEmpty()) {
								license.setItemCategory1(level1.get(0).getDescLangFirst());
								dto.setItemCategory1(level1.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
							List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
									.collect(Collectors.toList());
							if (level2 != null && !level2.isEmpty()) {
								license.setItemCategory2(level2.get(0).getDescLangFirst());
								dto.setItemCategory2(level2.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
							List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
									.collect(Collectors.toList());
							if (level3 != null && !level3.isEmpty()) {
								license.setItemCategory3(level3.get(0).getDescLangFirst());
								dto.setItemCategory3(level3.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
							List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
									.collect(Collectors.toList());
							if (level4 != null && !level4.isEmpty()) {
								license.setItemCategory4(level4.get(0).getDescLangFirst());
								dto.setItemCategory4(level4.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
							List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
									.collect(Collectors.toList());
							if (level5 != null && !level5.isEmpty()) {
								license.setItemCategory5(level5.get(0).getDescLangFirst());
								dto.setItemCategory5(level5.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						license.setArea(dto.getTrdUnit());
					}
					try {
						masDto.setCheckApptimeCharge(MainetConstants.FlagY);
						TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxes.getParentCode(),
								masDto.getOrgid());
						license.setParentTaxCode(ParentTax.getTaxCode());
						license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(masDto));

					} catch (Exception e) {
						LOGGER.error("Error ocurred while setting parent tax value in service DTL" + e);
					}
				}
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
		
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			setApplicationChargeToDtoList(master, masDto);
			
		} else {

			for (TbTaxMasEntity tbTaxMas : taxesMaster) {
	
				masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
				masDto.getFeeIds().put(tbTaxMas.getTaxId(), masDto.getTotalApplicationFee().doubleValue());
			}
			masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
		}	

	
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

	
	/*
	 * private TradeMasterDetailDTO setChargeToItemsDtoList(List<MLNewTradeLicense>
	 * master, TradeMasterDetailDTO masDto) { if (masDto.getApmApplicationId() !=
	 * null) masDto.getTradeLicenseItemDetailDTO().forEach(entity -> {
	 * master.forEach(model -> { if
	 * (entity.getItemCategory1().equals(model.getItemCategory1()) &&
	 * entity.getItemCategory2().equals(model.getItemCategory2()) &&
	 * entity.getItemCategory3().equals(model.getItemCategory3()) &&
	 * entity.getItemCategory4().equals(model.getItemCategory4()) &&
	 * entity.getItemCategory5().equals(model.getItemCategory5())) {
	 * entity.setTriRate(new BigDecimal(model.getFlatRate())); } }); });
	 * masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(
	 * c -> c.getFlatRate()).sum()));
	 * masDto.setApplicationCharge(master.get(0).getTaxSubCategory()); return
	 * masDto; }
	 */

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

	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_DUPLICATE_SERVICE_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_DUPLICATE_SERVICE_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getAppDuplicateChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getAppDuplicateChargesFromBrmsRule(TradeMasterDetailDTO masDto) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE, masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());
		
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

			//LocalDate applicationDate = LocalDate.now();l
			if (lookUp.getLookUpCode().equals("STR")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TDLF")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SDLF")) {
				        iterator.remove();
				    }
				}
			}
		}

		long appChargetaxId = CommonMasterUtility.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2,
				UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpId();
		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
		
		if (masDto.getTrdId() != null) {

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
		}

		for (TbTaxMasEntity taxes : taxesMaster) {

			if ((taxes.getTaxCategory2() == appChargetaxId)) {

				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);

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
				
				// #142577-set parentTaxValue for TSCL project
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)
						&& taxes.getParentCode() != null) {
					LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
							organisation);
					license.setLicenseType(licenseType.getDescLangFirst());
					for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
						if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
							List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
									.collect(Collectors.toList());
							if (level1 != null && !level1.isEmpty()) {
								license.setItemCategory1(level1.get(0).getDescLangFirst());
								dto.setItemCategory1(level1.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
							List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
									.collect(Collectors.toList());
							if (level2 != null && !level2.isEmpty()) {
								license.setItemCategory2(level2.get(0).getDescLangFirst());
								dto.setItemCategory2(level2.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
							List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
									.collect(Collectors.toList());
							if (level3 != null && !level3.isEmpty()) {
								license.setItemCategory3(level3.get(0).getDescLangFirst());
								dto.setItemCategory3(level3.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
							List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
									.collect(Collectors.toList());
							if (level4 != null && !level4.isEmpty()) {
								license.setItemCategory4(level4.get(0).getDescLangFirst());
								dto.setItemCategory4(level4.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
							List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
									.collect(Collectors.toList());
							if (level5 != null && !level5.isEmpty()) {
								license.setItemCategory5(level5.get(0).getDescLangFirst());
								dto.setItemCategory5(level5.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						license.setArea(dto.getTrdUnit());
					}
					try {
						masDto.setCheckApptimeCharge(MainetConstants.FlagY);
						TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxes.getParentCode(),
								masDto.getOrgid());
						license.setParentTaxCode(ParentTax.getTaxCode());
						license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(masDto));

					} catch (Exception e) {
						LOGGER.error("Error ocurred while setting parent tax value in service DTL" + e);
					}
				}
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
     //Defect #133557
		setDishonurChargeAmt(masDto);
		return masDto;
	}
    //Defect #133557
	private void setDishonurChargeAmt(TradeMasterDetailDTO masDto) {
		tradeLicenseApplicationService.setApplTimeDishonurChargeAmt(masDto,
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);

	}
	private TradeMasterDetailDTO setApplicationChargeToDtoList(List<MLNewTradeLicense> master,
			TradeMasterDetailDTO masDto) {
		
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());

		master.forEach(model -> {

			if (model.getTaxSubCategory().equalsIgnoreCase("Application Charge")) {
				if (Utility.isEnvPrefixAvailable(organisation,MainetConstants.ENV_TSCL)) {
					masDto.setApplicationCharge(
							BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getSlabRate1()).sum()).toString());
					masDto.setTotalApplicationFee(
							BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getSlabRate1()).sum()));
				} else {
					masDto.setApplicationCharge(
							BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()).toString());
					masDto.setTotalApplicationFee(
							BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
				}
			}

		});

		return masDto;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.SAVE_DUPLICATE_SERVICE_APPLICATION, notes = MainetConstants.TradeLicense.SAVE_DUPLICATE_SERVICE_APPLICATION, response = TradeMasterDetailDTO.class)
	@Path("/saveDuplicateService")
	@Transactional
	public TradeMasterDetailDTO saveAndGenerateApplnNo(TradeMasterDetailDTO masDto) {

		try {

			LOGGER.info("saveAndUpdateApplication started");

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
							masDto.getOrgid());
          //Defect #133557
			DishonourChargeEntity disHnrEnt = null;
			List<Long> appList = tradeDetailsHistRepo.getTradeRenewalAppHist(masDto.getTrdLicno(), sm.getSmShortdesc());
			if (CollectionUtils.isNotEmpty(appList)) {
				disHnrEnt = tbDisHnrRepo.getDishonourChargeData(appList.get(0));
				if (disHnrEnt != null) {
					disHnrEnt.setIsDishnrChgPaid(MainetConstants.FlagY);
					tbDisHnrRepo.save(disHnrEnt);
				}
			}

			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);
			final Long appicationId = ApplicationContextProvider.getApplicationContext()
					.getBean(ApplicationService.class).createApplication(requestDto);
			LOGGER.info("application number for duplicate trade licence : " + appicationId);
			masDto.setApmApplicationId(appicationId);
			if ((masDto.getDocumentList() != null) && !masDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(appicationId);
				fileUploadService.doFileUpload(masDto.getDocumentList(), requestDto);
			}
			// for saving duplicate license data in TbMlTradeMast table
			TbMlTradeMast entity = mapDtoToEntity(masDto);
			// 125706 to save application id in owner and item detail table
			entity.getItemDetails().forEach(itemEntity -> {
				itemEntity.setApmApplicationId(masDto.getApmApplicationId());
			});
			entity.getOwnerDetails().forEach(ownerEntity -> {
				ownerEntity.setApmApplicationId(masDto.getApmApplicationId());
			});

			tradeApplicationRepository.save(entity);
			// for saving history data D#125619
			saveHistoryData(masDto);
			LOGGER.info("saveAndUpdateApplication End");
		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Duplicate License Application ", exception);
			throw new FrameworkException("Exception occur while saving Duplicate License Application ", exception);
		}
		sendSmsEmail(masDto);
		return masDto;
	}

	private RequestDTO setApplicantRequestDto(TradeMasterDetailDTO tradeMasterDto, ServiceMaster sm) {
		TradeLicenseOwnerDetailDTO ownerDetails = tradeMasterDto.getTradeLicenseOwnerdetailDTO().get(0);
		RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(ownerDetails.getCreatedBy());
		requestDto.setOrgId(tradeMasterDto.getOrgid());
		requestDto.setLangId(tradeMasterDto.getLangId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(tradeLicenseApplicationService.getOwnersName(tradeMasterDto, MainetConstants.FlagA));
		requestDto.setEmail(ownerDetails.getTroEmailid());
		requestDto.setMobileNo(ownerDetails.getTroMobileno());
		requestDto.setAreaName(ownerDetails.getTroAddress());
		if (tradeMasterDto.getTotalApplicationFee() != null) {
			requestDto.setPayAmount(tradeMasterDto.getTotalApplicationFee().doubleValue());
		}
		// 125445 code updated to show LOI Data on portal Dashboard
		// requestDto.setReferenceId(tradeMasterDto.getTrdLicno());

		return requestDto;
	}

	@WebMethod(exclude = true)
	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {

		TbCfcApplicationMst entity = cfcApplicationMstService.findById(applicationId);
		// 125445 code updated to show LOI Data on portal Dashboard
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
		// .getLicenseDetailsByLicenseNo(entity.getRefNo(), orgId);

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

		BeanUtils.copyProperties(tradeMasterDto, masEntity);
		tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemdDetailsList.add(itemEntity);
		});
		tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownerDetails -> {
			TbMlOwnerDetail ownerEntity = new TbMlOwnerDetail();
			BeanUtils.copyProperties(ownerDetails, ownerEntity);
			ownerEntity.setMasterTradeId(masEntity);
			ownerDetailsList.add(ownerEntity);
		});

		masEntity.setOwnerDetails(ownerDetailsList);
		masEntity.setItemDetails(itemdDetailsList);

		return masEntity;
	}

	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);
		// 125706 to set history status in history table
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
			tbMlItemDetailHistList.add(TbMlItemDetailHistEntity);

		});

		TbMlTradeMastHist.setItemDetails(tbMlItemDetailHistList);
		TbMlTradeMastHist.setOwnerDetails(tbMlOwnerDetailHistList);
		auditService.createHistoryForObject(TbMlTradeMastHist);
	}

	@Override
	public void saveHistoryData(TradeMasterDetailDTO masDto) {
		// TODO Auto-generated method stub
		TbMlTradeMast entity = mapDtoToEntity(masDto);
		saveHistoryData(entity);
	}

	@Override
	@WebMethod(exclude = true)
	public List<TbLoiMas> getTotalAmount(String licNo) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TbLoiMas> tbLoiMas = new ArrayList<TbLoiMas>();

		try {
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
					UserSession.getCurrent().getOrganisation());
			List<TbMlTradeMastHist> tradeMastList = ApplicationContextProvider.getApplicationContext()
					.getBean(TradeMasterHistoryDetailsRepository.class).getTradeDetailsHistByLicNo(licNo).stream()
					.filter(t -> t.getTrdStatus() != null && t.getTrdStatus().equals(lookUp.getLookUpId()))
					.collect(Collectors.toList());

			for (int i = tradeMastList.size() - 1; i >= 0; i--) {
				long serviceIdByApplicationId = 0l;
				try {
					serviceIdByApplicationId = ApplicationContextProvider.getApplicationContext()
							.getBean(ICFCApplicationMasterService.class).getServiceIdByApplicationId(
									tradeMastList.get(i).getApmApplicationId(), Long.valueOf(orgId));

				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error("No data found for appId::" + tradeMastList.get(i).getApmApplicationId());
				}
				String servShortCode = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class).fetchServiceShortCode(serviceIdByApplicationId, orgId);
				// Defect #132404 for setting License fee details
				if (serviceIdByApplicationId != 0L) {
              ////Defect #133557
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
							MainetConstants.ENV_SKDCL)||Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
									MainetConstants.ENV_TSCL)) {

						if (servShortCode.equals(MainetConstants.TradeLicense.SERVICE_SHORT_CODE)) {
							tbLoiMas = tbLoiMasService.getloiByApplicationId(tradeMastList.get(i).getApmApplicationId(),
									serviceIdByApplicationId, orgId);
							if (tbLoiMas != null && !tbLoiMas.isEmpty()) {

								return tbLoiMas;
							}
						}
					} else if (!servShortCode.equals(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE)) {
						tbLoiMas = tbLoiMasService.getloiByApplicationId(tradeMastList.get(i).getApmApplicationId(),
								serviceIdByApplicationId, orgId);
						if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
							List<TbLoiDetEntity> loiDetails = tbLoiDetService
									.findLoiDetailsByLoiIdAndOrgId(tbLoiMas.get(0).getLoiId(), orgId);

							double loiAmount = 0d;

							for (TbLoiDetEntity loiDetailDto : loiDetails) {

								loiAmount = loiAmount + loiDetailDto.getLoiAmount().doubleValue();

							}

							return tbLoiMas;
						}
					}

				}
			}

		} catch (Exception e) {

			LOGGER.error("No charges found for License No: " + licNo);
		}
		return tbLoiMas;
	}

	// for saving history data D#125619
	@Override
	public String getLicenseNoByAppId(Long applicationId, Long orgId) {
		// TODO Auto-generated method stub

		String licNo = ApplicationContextProvider.getApplicationContext()
				.getBean(TradeMasterHistoryDetailsRepository.class).getTradeLicNo(applicationId);
		return licNo;
	}

	// 126164
	@Override
	public void sendSmsEmail(TradeMasterDetailDTO masDto) {
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = organisationService.getOrganisationById(masDto.getOrgid());
		smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
		smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
		ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE, org.getOrgid());

		smsDto.setServName(sm.getSmServiceName());
		smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		String url = "DuplicateLicenseForm.html";
		org.setOrgid(org.getOrgid());
		int langId = masDto.getLangId().intValue();
		smsDto.setUserId(masDto.getUserId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
	}
   //Defect #133557
	@Override
	@Transactional
	public Boolean updateStatusAfterDishonurEntry(Long appId, Long orgId) {
		Organisation org = organisationService.getOrganisationById(orgId);
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS", org);
		TbMlTradeMast entity = tradeApplicationRepository.getLicenseDetailsByAppIdAndOrgId(appId, orgId);
		entity.setTrdStatus(lookUp.getLookUpId());
		tradeApplicationRepository.save(entity);
		return true;
	}
}