package com.abm.mainet.tradeLicense.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Department;
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
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created Date:09/08/2019
 * 
 * @author Gayatri.Kokane
 *
 */

@Service(value = "CancellationLicenseService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.ICancellationLicenseService")
@Api(value = "/cancellationLicenseApplicationService")
@Path("/cancellationLicenseApplicationService")
public class CancellationLicenseServiceImpl implements ICancellationLicenseService {

	private static final Logger LOGGER = Logger.getLogger(CancellationLicenseServiceImpl.class);
	@Autowired
	AuditService auditService;
	
	@Autowired
	private TradeLicenseApplicationRepository tradeLicenseApplicationRepository;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private TbCfcApplicationMstService cfcApplicationMstService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;

	@Autowired
	private TradeLicenseApplicationRepository tradeRepository;
	
	@Autowired
	IOrganisationService organisationService;
	
	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;
	
	@Autowired
	ServiceMasterService serviceMasterService;

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.SAVE_CANCELLATION_LICENSE_SERVICE, notes = MainetConstants.TradeLicense.SAVE_CANCELLATION_LICENSE_SERVICE, response = TradeMasterDetailDTO.class)
	@Path("/saveCancellationService")
	@Transactional
	public TradeMasterDetailDTO saveCancellationService(TradeMasterDetailDTO masDto) {

		try {
			Long dept = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDepartmentIdByDeptCode("ML");
			if (dept != null) {
				masDto.setDeptId(dept);
			}
			LOGGER.info("saveAndUpdateApplication started");
            //for changing status of license for Issued to Cancel  when application punched from Mobile
			Organisation organisation = organisationService.getOrganisationById(masDto.getOrgid());
			//124306 - trdstaus changed to transit till last approval
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",organisation);
			masDto.setTrdStatus(lookUp.getLookUpId());

			// end

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
							masDto.getOrgid());
			if (sm != null && sm.getSmServiceId() != null) {
				masDto.setServiceId(sm.getSmServiceId());
			}
			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);
			//#122717 
			StringBuilder ownName = new StringBuilder();
			String fName = null;
			masDto.getTradeLicenseOwnerdetailDTO().forEach(dto -> {
				if (dto.getTroName() != null) 
					ownName.append(dto.getTroName()  + MainetConstants.operator.COMMA);				
			});
			fName = ownName.deleteCharAt(ownName.length() - 1).toString();
			if (fName != null) {
				requestDto.setfName(fName);
			}
			
			if (masDto.isFree() || masDto.getApplicationchargeApplFlag().equals(MainetConstants.FlagY)) {
				final Long appicationId = ApplicationContextProvider.getApplicationContext()
						.getBean(ApplicationService.class).createApplication(requestDto);
				masDto.setApmApplicationId(appicationId);
	

			}
			TbMlTradeMast masEntity = mapDtoToEntity(masDto);
			//125706 to save application id in owner and item detail table
			masEntity.getItemDetails().forEach(itemEntity ->{
				itemEntity.setApmApplicationId(masDto.getApmApplicationId());
			});
			masEntity.getOwnerDetails().forEach(ownerEntity ->{
				ownerEntity.setApmApplicationId(masDto.getApmApplicationId());
			});
			tradeLicenseApplicationRepository.save(masEntity);
			saveHistoryData(masEntity);
			LOGGER.info("saveAndUpdateApplication started");
			tradeLicenseApplicationRepository.updateCancellationTrdFlag(masDto.getTrdLicno(), masDto.getOrgid(),
					masDto.getTrdStatus(), masDto.getLgIpMacUpd());

			boolean checklist = false;
			if ((masDto.getDocumentList() != null) && !masDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(masDto.getApmApplicationId());
				checklist = fileUploadService.doFileUpload(masDto.getDocumentList(), requestDto);
				checklist = true;
			}

			if (masDto.isFree()) {

				iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(masDto.getApmApplicationId(),
						MainetConstants.PAY_STATUS.PAID, masDto.getOrgId());
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(masDto.getApmApplicationId());
				applicationData.setOrgId(masDto.getOrgId());
				applicationData.setIsCheckListApplicable(checklist);
				masDto.getApplicantDetailDto().setUserId(masDto.getUserId());
				masDto.getApplicantDetailDto().setServiceId(masDto.getServiceId());
				masDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(masDto.getDeptId()));
				// adding code for calling workflow according to word-zone for SKDCL
				if (masDto.getTrdWard1() != null) {
					masDto.getApplicantDetailDto().setDwzid1(masDto.getTrdWard1());
				}
				if (masDto.getTrdWard2() != null) {
					masDto.getApplicantDetailDto().setDwzid2(masDto.getTrdWard2());
				}
				if (masDto.getTrdWard3() != null) {
					masDto.getApplicantDetailDto().setDwzid3(masDto.getTrdWard3());
				}
				if (masDto.getTrdWard4() != null) {
					masDto.getApplicantDetailDto().setDwzid4(masDto.getTrdWard4());
				}
				if (masDto.getTrdWard5() != null) {
					masDto.getApplicantDetailDto().setDwzid5(masDto.getTrdWard5());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, masDto.getApplicantDetailDto());
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Cancellation Service Application ", exception);
			throw new FrameworkException("Exception occur while saving Cancellation Service Application ", exception);
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
		//125445 code updated to show LOI Data  on portal Dashboard
		//requestDto.setReferenceId(tradeMasterDto.getTrdLicno());
		return requestDto;
	}

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

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CANCELLATION_SERVICE_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CANCELLATION_SERVICE_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getCancellationChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getApplicationChargesBrmsRule(TradeMasterDetailDTO tradeDto) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(tradeDto.getOrgid());
		Date todayDate = new Date();

		List<MLNewTradeLicense> masterList = new ArrayList<>();
		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = tradeDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
		try {
			lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
					tradeDto.getOrgid());
			lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 2,
					tradeDto.getOrgid());
			lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 3,
					tradeDto.getOrgid());
			lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 4,
					tradeDto.getOrgid());
			lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 5,
					tradeDto.getOrgid());
		} catch (Exception e) {
			LOGGER.info("prefix level not found");
		}

		for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {

			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.TradeLicense.APL, PrefixConstants.NewWaterServiceConstants.CAA, organisation);

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
							tradeDto.getOrgid());

			final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
					.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
							organisation.getOrgid(), chargeApplicableAt.getLookUpId());

			MLNewTradeLicense license = new MLNewTradeLicense();
			Organisation org = UserSession.getCurrent().getOrganisation();
			license.setOrgId(dto.getOrgid());
			license.setServiceCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE);
			Department dept = tbDepartmentService.findDepartmentByCode("ML");
			TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(organisation.getOrgid(), dept.getDpDeptid(),
					taxesMaster.get(0).getTaxCode());
			String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
					MainetConstants.FlagE, dto.getOrgid());
			license.setTaxType(taxType);
			license.setTaxCode(taxesMaster.get(0).getTaxCode()); // for getting scrutiny level tax code(category and
																	// subcategory)
			if (tax.getParentCode() != null) {
				TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), organisation.getOrgid());
				license.setParentTaxCode(tbTax.getTaxCode());
			}
			license.setTaxCategory(CommonMasterUtility
					.getHierarchicalLookUp(taxesMaster.get(0).getTaxCategory1(), organisation).getDescLangFirst());
			license.setTaxSubCategory(CommonMasterUtility
					.getHierarchicalLookUp(taxesMaster.get(0).getTaxCategory2(), organisation).getDescLangFirst());
			license.setRateStartDate(todayDate.getTime());
			license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
			license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
			LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(tradeDto.getTrdLictype(),
					organisation);
			license.setLicenseType(licenseType.getDescLangFirst());
			license.setArea(dto.getTrdUnit());

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
					dto.setItemCategory3(level3.get(0).getDescLangFirst());
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
			
			//#142577-set parentTaxValue for TSCL project
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && tax.getParentCode() != null) {
			try {
				tradeDto.setCheckApptimeCharge(MainetConstants.FlagY);
				license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(tradeDto));
			 } catch (Exception e) {
				LOGGER.error("Error ocurred while setting parent tax value in service CTL at application time"+ e);
			 }
			}
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

		setChargeToItemsDtoList(master, tradeDto);

		/*
		 * for (TbTaxMasEntity tbTaxMas : taxes) {
		 * tradeDto.getFeeIds().put(tbTaxMas.getTaxId(),
		 * tradeDto.getTotalApplicationFee().doubleValue()); }
		 */

		return tradeDto;
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

	private TradeMasterDetailDTO setChargeToItemsDtoList(List<MLNewTradeLicense> master, TradeMasterDetailDTO masDto) {

		if (masDto.getApmApplicationId() != null)
			masDto.getTradeLicenseItemDetailDTO().forEach(entity -> {
				master.forEach(model -> {
					if (entity.getItemCategory1().equals(model.getItemCategory1())
							&& entity.getItemCategory2().equals(model.getItemCategory2())
							&& entity.getItemCategory3().equals(model.getItemCategory3())
							&& entity.getItemCategory4().equals(model.getItemCategory4())
							&& entity.getItemCategory5().equals(model.getItemCategory5())) {

						entity.setTriRate(BigDecimal.valueOf(model.getSlabRate1()));
					}

				});
			});

		masDto.setTotalApplicationFee(BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getSlabRate1()).sum()));
		return masDto;
	}

	@Override
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId) {

		TbCfcApplicationMst entity = cfcApplicationMstService.findById(applicationId);
        //125445 code updated to show LOI Data  on portal Dashboard
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
                       //.getLicenseDetailsByLicenseNo(entity.getRefNo(), orgId);

		/*
		 * TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService
		 * .getTradeLicenseWithAllDetailsByApplicationId(applicationId);
		 */

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
	public TradeMasterDetailDTO getscrutinyChargesBrmsRule(TradeMasterDetailDTO masDto) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());
		Date todayDate = new Date();

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAtScrutiny = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster smm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE, masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(smm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAtScrutiny.getLookUpId());
		
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
				    if (taxLookUp.getLookUpCode().equals("TRC")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SRC")) {
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

		for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
			MLNewTradeLicense license = new MLNewTradeLicense();
			Organisation org = UserSession.getCurrent().getOrganisation();
			license.setOrgId(dto.getOrgid());
			license.setServiceCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE);
			Department dept = tbDepartmentService.findDepartmentByCode("ML");
			TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(org.getOrgid(), dept.getDpDeptid(),
					taxesMaster.get(0).getTaxCode());
			String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
					MainetConstants.FlagE, dto.getOrgid());
			license.setTaxType(taxType);
			license.setTaxCode(taxesMaster.get(0).getTaxCode());
			TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), org.getOrgid());
			license.setParentTaxCode(tbTax.getTaxCode());
			license.setTaxCategory(CommonMasterUtility
					.getHierarchicalLookUp(taxesMaster.get(0).getTaxCategory1(), organisation).getDescLangFirst());
			license.setTaxSubCategory(CommonMasterUtility
					.getHierarchicalLookUp(taxesMaster.get(0).getTaxCategory2(), organisation).getDescLangFirst());
			license.setRateStartDate(todayDate.getTime());
			license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
			license.setApplicableAt(chargeApplicableAtScrutiny.getDescLangFirst());
			LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
					organisation);
			license.setLicenseType(licenseType.getDescLangFirst());
			license.setArea(dto.getTrdUnit());
			//#142577-set parentTaxValue for TSCL project
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)
					&& tax.getParentCode() != null) {
				try {
					license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(masDto));
				} catch (Exception e) {
					LOGGER.error("Error ocurred while setting parent tax value in service CTL" + e);
				}
			}
			
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
					dto.setItemCategory3(level3.get(0).getDescLangFirst());
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

		return masDto;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {
		Map<Long, Double> chargeMap = new HashMap<>();
		TbCfcApplicationMst entity = cfcApplicationMstService.findById(applicationId);
		//125445 code updated to show LOI Data  on portal Dashboard
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
				//.getLicenseDetailsByLicenseNo(entity.getRefNo(), orgId);
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);

		final LookUp chargeApplicableAtScrutiny = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE, orgId);

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAtScrutiny.getLookUpId());
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(tradeDetail.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), tradeDetail.getOrgid());

			//LocalDate applicationDate = LocalDate.now();l
			if (lookUp.getLookUpCode().equals("STR")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				    		tradeDetail.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TRC")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				    		tradeDetail.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SRC")) {
				        iterator.remove();
				    }
				}
			}
		}

		double amount = tradeDetail.getTradeLicenseItemDetailDTO().stream().filter(c -> c.getTriRate() != null)
				.mapToDouble(c -> c.getTriRate().doubleValue()).sum();

		TradeMasterDetailDTO tradeLicenceCharges = getscrutinyChargesBrmsRule(tradeDetail);
		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			chargeMap.put(tbTaxMas.getTaxId(), amount);
		}
		return tradeLicenceCharges.getFeeIds();
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {
		// app
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE, masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2,
				UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpId();

		for (TbTaxMasEntity taxes : taxesMaster) {

			if ((taxes.getTaxCategory2() == appChargetaxId)) {

				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE);
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
				//#142577-set parentTaxValue for TSCL project
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && tax.getParentCode() != null) {
				try {
					masDto.setCheckApptimeCharge(MainetConstants.FlagY);
					TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxes.getParentCode(),
							masDto.getOrgid());
					license.setParentTaxCode(ParentTax.getTaxCode());
					masDto.setCheckApptimeCharge(MainetConstants.FlagY);
					license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(masDto));
				 } catch (Exception e) {
					LOGGER.error("Error ocurred while setting parent tax value in service CTL at application time"+ e);
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
	public Boolean saveCancellationLicenseByForceForm(TradeMasterDetailDTO dto) {
		TbMlTradeMast masEntity = mapDtoToEntity(dto);
		TbMlTradeMast trdMast = tradeLicenseApplicationRepository.save(masEntity);
		saveHistoryData(masEntity);
		if (trdMast != null) {
			return true;
		}
		return false;
	}

	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);
		//125706 to set history status in history table 
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
	
	   
		//126164
	    @Override
		public void sendSmsEmail(TradeMasterDetailDTO masDto) {
			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			Organisation org = organisationService.getOrganisationById(masDto.getOrgid());
			smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
			smsDto.setAppNo(masDto.getApmApplicationId().toString());
			smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
					org.getOrgid());
			smsDto.setServName(sm.getSmServiceName());
			smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
			String url = "CancellationLicenseForm.html";
			org.setOrgid(org.getOrgid());
			int langId = masDto.getLangId().intValue();
			smsDto.setUserId(masDto.getUserId());
			iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
		}
}
