package com.abm.mainet.buildingplan.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jamabandi.LRDataService;
import org.json.JSONObject;
import org.model.District;
import org.model.Khasra;
import org.model.Must;
import org.model.Owner;
import org.model.Tehsil;
import org.model.Village;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.abm.mainet.buildingplan.datamodel.BPMSRateMaster;
import com.abm.mainet.buildingplan.domain.LicenseApplicationFeesMasterEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationFeesMasterHistEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetHistEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandCategoryEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandDet;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandSchedule;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandSurroundingsEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;
import com.abm.mainet.buildingplan.domain.LicenseApplicationPurposeTypeDetEntity;
import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;
import com.abm.mainet.buildingplan.dto.DeveloperRegistrationDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationFeeMasDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandAcquisitionDetailDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandCategoryDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandDetDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandScheduleDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandSurroundingsDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationMasterDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationPurposeTypeDetDTO;
import com.abm.mainet.buildingplan.repository.DevRegMasRepository;
import com.abm.mainet.buildingplan.repository.LicenseApplicationFeesMasterHistRespository;
import com.abm.mainet.buildingplan.repository.LicenseApplicationLandAcquisitionDetHistRepository;
import com.abm.mainet.buildingplan.repository.LicenseApplicationLandAcquisitionDetRepository;
import com.abm.mainet.buildingplan.repository.LicenseApplicationLandSurroundingsRespository;
import com.abm.mainet.buildingplan.repository.LicenseApplicationMasRepository;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Service
@Transactional
public class NewLicenseFormServiceImpl implements INewLicenseFormService {
	
	private final Logger LOGGER = Logger.getLogger(INewLicenseFormService.class);
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";

	
	@Autowired
	LicenseApplicationMasRepository licenseApplicationMasRepository;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	LRDataService service;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Resource
	private IFileUploadService fileUpload;
	
	@Autowired
	private CommonService commonService;
	
	@Resource
	private IEmployeeService employeeService;
	
	@Resource
	private DevRegMasRepository devRegMasRepository;
	
	@Resource
	private IWorkflowActionService workflowActionService;
	
	@Autowired
	LicenseApplicationLandAcquisitionDetRepository licenseApplicationLandAcquisitionDetRep;
	
	@Resource
	private TbTaxMasService taxMasService;

	@Autowired
	AuditService auditService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private CFCAttechmentRepository attechDocumentRepository;
	
	@Autowired
	private LicenseApplicationLandAcquisitionDetHistRepository licenseLandDetHist;
	
	@Autowired
	LicenseApplicationFeesMasterHistRespository feeMastHistRep;
	
	@Autowired
	LicenseApplicationLandSurroundingsRespository surroundingResp;

	@Override
	public LicenseApplicationMasterDTO saveRegForm(LicenseApplicationMasterDTO licenseApplicationMasterDTO) {
		
		LicenseApplicationMasterDTO masterDto = new LicenseApplicationMasterDTO();
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE,
						licenseApplicationMasterDTO.getOrgId());
		if(licenseApplicationMasterDTO.getApplicationNo() == null) {
			RequestDTO requestDto = setApplicantRequestDto(licenseApplicationMasterDTO, sm);
			final Long appicationId = applicationService.createApplication(requestDto);
			licenseApplicationMasterDTO.setApplicationNo(appicationId);
			
		}
		
		LicenseApplicationMaster entityMast = mapDtoToEntity(licenseApplicationMasterDTO);
		entityMast = licenseApplicationMasRepository.save(entityMast);	
		masterDto = mapEntitytoDto(entityMast);

		//initializeWorkFlowForFreeService(licenseApplicationMasterDTO, sm);
		//}
	/*
	 * catch (Exception e) { LOGGER.error("Error while saving Application " + e); }
	 */
		return masterDto;
		 
		
		
	}
	
	
	 
	private LicenseApplicationMaster mapDtoToEntity(LicenseApplicationMasterDTO masterDTO) {
	    LicenseApplicationMaster entityMast = new LicenseApplicationMaster();
	    BeanUtils.copyProperties(masterDTO, entityMast);

	    if (masterDTO.getLicenseApplicationLandScheduleDTO() != null) {
	        LicenseApplicationLandSchedule landScheduleEntity = new LicenseApplicationLandSchedule();
	        landScheduleEntity.setLicenseApplicationMaster(entityMast);
	        BeanUtils.copyProperties(masterDTO.getLicenseApplicationLandScheduleDTO(), landScheduleEntity);
	        entityMast.setLicenseApplicationLandSchedule(landScheduleEntity);
	        if(masterDTO.getLicenseApplicationLandScheduleDTO().getLicenseApplicationLandSurroundingsDtoList() != null) {
	        	entityMast.getLicenseApplicationLandSchedule().setLicenseApplicationLandSurroundingsEntity(
	        			masterDTO.getLicenseApplicationLandScheduleDTO().getLicenseApplicationLandSurroundingsDtoList().stream()
	        			.map(surrondingDto -> {
	        				LicenseApplicationLandSurroundingsEntity surroundingEntity = new LicenseApplicationLandSurroundingsEntity();
	        				surroundingEntity.setLandSchId(entityMast.getLicenseApplicationLandSchedule());
	        				BeanUtils.copyProperties(surrondingDto, surroundingEntity);
		                    return surroundingEntity;
	        			}).collect(Collectors.toList())
	        			);
	        }
	    }
	    
	    if (masterDTO.getLandCategoryDTO() != null) {
	    	LicenseApplicationLandCategoryEntity landCategoryEntity = new LicenseApplicationLandCategoryEntity();
	    	landCategoryEntity.setLicenseApplicationMaster(entityMast);
	    	BeanUtils.copyProperties(masterDTO.getLandCategoryDTO(), landCategoryEntity);
	    	entityMast.setLicenseApplicationLandCategoryEntity(landCategoryEntity);
	    }
	    

	    if (masterDTO.getLicenseApplicationLandDetDTOList() != null) {
	        entityMast.setLicenseApplicationLandDetList(
	            masterDTO.getLicenseApplicationLandDetDTOList().stream()
	                .map(landDetDto -> {
	                    LicenseApplicationLandDet landDetEntity = new LicenseApplicationLandDet();
	                    landDetEntity.setLicenseApplicationMaster(entityMast);
	                    BeanUtils.copyProperties(landDetDto, landDetEntity);
	                    return landDetEntity;
	                })
	                .collect(Collectors.toList())
	        );
	    }

	    if (masterDTO.getLicenseApplicationLandAcquisitionDetDTOList() != null) {
	        entityMast.setLicenseApplicationLandAcquisitionDetEntityList(
	            masterDTO.getLicenseApplicationLandAcquisitionDetDTOList().stream()
	                .map(landAcqDto -> {
	                    LicenseApplicationLandAcquisitionDetEntity landAcqEntity = new LicenseApplicationLandAcquisitionDetEntity();
	                    landAcqEntity.setLicenseApplicationMaster(entityMast);
	                    BeanUtils.copyProperties(landAcqDto, landAcqEntity);
	                    return landAcqEntity;
	                })
	                .collect(Collectors.toList())
	        );
	    }

	    if (masterDTO.getLicenseApplicationPurposeTypeDetDTOList() != null) {
	        entityMast.setLicenseApplicationPurposeTypeDetEntityList(
	            masterDTO.getLicenseApplicationPurposeTypeDetDTOList().stream()
	                .map(purposeTypeDto -> {
	                	purposeTypeDto.setApplicationPurpose1(masterDTO.getAppPLicPurposeId());
	                    LicenseApplicationPurposeTypeDetEntity purposeTypeDetEntity = new LicenseApplicationPurposeTypeDetEntity();
	                    purposeTypeDetEntity.setLicenseApplicationMaster(entityMast);
	                    BeanUtils.copyProperties(purposeTypeDto, purposeTypeDetEntity);
	                    return purposeTypeDetEntity;
	                })
	                .collect(Collectors.toList())
	        );
	    }
	    
	    
	    if (masterDTO.getFeeMasterDto() != null) {
	        entityMast.setLicenseApplicationFeesMasterEntity(
	            masterDTO.getFeeMasterDto().stream()
	                .map(feeMasDto -> {
	                    LicenseApplicationFeesMasterEntity feeEntity = new LicenseApplicationFeesMasterEntity();
	                    feeEntity.setLicenseApplicationMaster(entityMast);
	                    BeanUtils.copyProperties(feeMasDto, feeEntity);
	                    return feeEntity;
	                })
	                .collect(Collectors.toList())
	        );
	    }

	    return entityMast;
	}
	
	
	private RequestDTO setApplicantRequestDto(LicenseApplicationMasterDTO dto, ServiceMaster sm) {
		
		RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(dto.getCreatedBy());
		requestDto.setOrgId(dto.getOrgId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setfName(dto.getAuthPName());
		requestDto.setLangId(dto.getLangId());
		requestDto.setEmail(dto.getEmail());
		if(dto.getAuthPMobile() != null){
			requestDto.setMobileNo(dto.getAuthPMobile().toString());
		}

		return requestDto;
	}
	
	
	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = LicenseApplicationMasterDTO.class)
	@Path("/getLicenceAppChargesFromBrmsRule")
	@Transactional(readOnly = true)
	public LicenseApplicationMasterDTO getLicenceAppChargesFromBrmsRule(LicenseApplicationMasterDTO masDto) {
		
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgId());
		List<BPMSRateMaster> licChargeDataModelList =  new ArrayList<>();
		Date todayDate = new Date();
        
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		
        ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE, masDto.getOrgId());
        
        final List<TbTaxMasEntity> taxesMaster = tbTaxMasService.fetchAllApplicableServiceCharge(sm.getSmServiceId(),
                organisation.getOrgid(), chargeApplicableAt.getLookUpId());

        for (TbTaxMasEntity taxes : taxesMaster) {
            TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgId(),
                    sm.getTbDepartment().getDpDeptid(), taxes.getTaxCode());
            String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
                    MainetConstants.FlagE, masDto.getOrgId());

            masDto.getLicenseApplicationPurposeTypeDetDTOList().forEach(purposeDto -> {
                BPMSRateMaster license = new BPMSRateMaster(); 
                license.setOrgId(masDto.getOrgId());
                license.setServiceCode(MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE);
                license.setTaxType(taxType);
                license.setTaxCode(taxes.getTaxCode());

                settingTaxCategories(license, taxes, organisation);

                license.setApplicableAt(chargeApplicableAt.getDescLangFirst());

                LookUp itemCategory1Lookup = CommonMasterUtility.getHierarchicalLookUp(masDto.getAppPLicPurposeId(),
                        organisation);
                LookUp itemCategory2Lookup = CommonMasterUtility.getHierarchicalLookUp(purposeDto.getApplicationPurpose2(),
                        organisation);
                if (purposeDto.getApplicationPurpose3() != null) {
                    LookUp itemCategory3Lookup = CommonMasterUtility.getHierarchicalLookUp(purposeDto.getApplicationPurpose3(),
                            organisation);
                    license.setItemCategory3(itemCategory3Lookup.getDescLangFirst());
                }
                LookUp zoneLookup = CommonMasterUtility.getHierarchicalLookUp(masDto.getDdz3(),
                        organisation);
                LookUp farLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(purposeDto.getFar(),
                        organisation);

                license.setItemCategory1(itemCategory1Lookup.getDescLangFirst());
                license.setItemCategory2(itemCategory2Lookup.getDescLangFirst());
                license.setZone(zoneLookup.getDescLangFirst());
                license.setRateStartDate(todayDate.getTime());
                license.setFar(Double.parseDouble(farLookup.getDescLangFirst()));
                license.setArea(purposeDto.getArea());

                licChargeDataModelList.add(license);
            });
			
        	
        }
        LOGGER.info("brms NL getLicenceChargesFromBrmsRule execution start..");
        
        WSResponseDTO responseDTO = new WSResponseDTO();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		
		wsRequestDTO.setDataModel(licChargeDataModelList);
		
		List<BPMSRateMaster> master =  new ArrayList<>();
		
		try {
			LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
			responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.NL_LICENSE_FEE);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				master = setLicenceChargesDTO(responseDTO);
				
			} else {
				throw new FrameworkException(responseDTO.getErrorMessage());
			}
			
		}catch(Exception ex) {
			
		}
		setChargeToDtoList(master, taxesMaster, masDto);
		setFeeIds(master, taxesMaster, masDto);
		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");
        
		return masDto;
		
		
	}
	
	
	private void setFeeIds(List<BPMSRateMaster> master, List<TbTaxMasEntity> taxesMaster,
			LicenseApplicationMasterDTO masDto) {
		Map<Long, Double> map = new HashMap();
		master.forEach(mast -> {
			taxesMaster.forEach(taxMast -> {

				map.put(taxMast.getTaxId(), mast.getFlatRate());

			});
		});
		masDto.setFeeIds(map);
	}



	private BPMSRateMaster settingTaxCategories(BPMSRateMaster mlNewLicense, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				mlNewLicense.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				mlNewLicense.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return mlNewLicense;

	}
	
	private List<BPMSRateMaster> setLicenceChargesDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setTradeLicenceChargesDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, BPMSRateMaster.class);
		List<BPMSRateMaster> finalRateMaster = new ArrayList<>();
		for (Object rate : charges) {
			BPMSRateMaster masterRate = (BPMSRateMaster) rate;
			finalRateMaster.add(masterRate);
		}
		LOGGER.info("setTradeLicenceChargesDTO execution end..");
		return finalRateMaster;
	}
	
	private LicenseApplicationMasterDTO setChargeToDtoList(List<BPMSRateMaster> master,  List<TbTaxMasEntity> taxesMaster,
			LicenseApplicationMasterDTO masDto) {
		
		if(masDto.getFeeMasterDto().isEmpty()) {
		master.forEach(model -> {
			LicenseApplicationFeeMasDTO feeMasterDto = new LicenseApplicationFeeMasDTO();

		    if (model != null && masDto != null) {
		        feeMasterDto.setTaxCategory(model.getTaxSubCategory() + " - " + model.getItemCategory2());
		        feeMasterDto.setFees(new BigDecimal (model.getFlatRate()).setScale(2, BigDecimal.ROUND_UP));
		        if ("Licence Fee".equalsIgnoreCase(model.getTaxSubCategory())) {
		            feeMasterDto.setCalculation(model.getArea() + "(total applied Area in acre)*12500(Rate)" + "*" + model.getFar() + "(FAR)");
		        }

		        if ("Scrutiny Fee".equalsIgnoreCase(model.getTaxSubCategory())) {
		            feeMasterDto.setCalculation("4047(acre to sq. mt)*" + model.getArea() + "(total applied Area in acre)*10(Rate)"  + "*" + model.getFar() + "(FAR)");
		        }

		        masDto.getFeeMasterDto().add(feeMasterDto);
		    }
		});
		
		BigDecimal totalFees = new BigDecimal(0);
		BigDecimal licFees = new BigDecimal(0);
		BigDecimal scrutinyFees = new BigDecimal(0);

		for (LicenseApplicationFeeMasDTO feesMaster : masDto.getFeeMasterDto()) {
		    totalFees = totalFees.add(feesMaster.getFees());
		    if (feesMaster.getTaxCategory().contains("Licence Fee")) {
		    	licFees = licFees.add(feesMaster.getFees());
		    }
		    if (feesMaster.getTaxCategory().contains("Scrutiny Fee")) {
		    	scrutinyFees = scrutinyFees.add(feesMaster.getFees());
		    }
		}
		
		
		masDto.setTotalFees(totalFees.setScale(2, BigDecimal.ROUND_UP));
		masDto.setLicenseFee(licFees.setScale(2, BigDecimal.ROUND_UP));
		masDto.setScrutinyFee(scrutinyFees.setScale(2, BigDecimal.ROUND_UP));
			}
	
		return masDto;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<District> getDistrict() throws Exception {

		List<District> districts = new ArrayList<District>();
		LOGGER.info("Service URL" + ApplicationSession.getInstance().getMessage("land.det.api.serviceURl"));
		service = new LRDataService(new URL(ApplicationSession.getInstance().getMessage("land.det.api.serviceURl")));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
		DocumentBuilder db;

		db = dbf.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(
				service.getLRDataServiceSoap().getDistrict(ApplicationSession.getInstance().getMessage("land.det.api.key"), 
						ApplicationSession.getInstance().getMessage("haryana.stateCode")).getBytes());
		Document doc = db.parse(is);

		LOGGER.info("Number of districts\t" + doc.getDocumentElement().getElementsByTagName("Districts").getLength());

		NodeList nodeList = doc.getElementsByTagName("Districts");
		NodeList nodeList1 = null;
		District district = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList1 = nodeList.item(i).getChildNodes();
			district = new District();

			district.setDistrictCode(nodeList1.item(1).getTextContent());
			district.setDistrictName(nodeList1.item(0).getTextContent());
			if (nodeList1.item(2) != null)
				district.setCensusCode(nodeList1.item(2).getTextContent());

			districts.add(district);

		}
		return districts;

	}

	/**
	 * 
	 * @param DistrictCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Tehsil> getTehsil(String DistrictCode) throws Exception {

		List<Tehsil> tehsils = new ArrayList<Tehsil>();
		LOGGER.info("Service URL" + ApplicationSession.getInstance().getMessage("land.det.api.serviceURl"));
		service = new LRDataService(new URL(ApplicationSession.getInstance().getMessage("land.det.api.serviceURl")));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
		DocumentBuilder db;

		db = dbf.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(
				service.getLRDataServiceSoap().getTehsil(ApplicationSession.getInstance().getMessage("land.det.api.key"), 
						ApplicationSession.getInstance().getMessage("haryana.stateCode"), DistrictCode).getBytes());
		Document doc = db.parse(is);

		LOGGER.info("Number of Tehsils\t" + doc.getDocumentElement().getElementsByTagName("Tehsils").getLength());

		NodeList nodeList = doc.getElementsByTagName("Tehsils");
		NodeList nodeList1 = null;
		Tehsil tehsil = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList1 = nodeList.item(i).getChildNodes();
			tehsil = new Tehsil();

			tehsil.setCode(nodeList1.item(1).getTextContent());
			tehsil.setName(nodeList1.item(0).getTextContent());
			if (nodeList1.item(2) != null)
				tehsil.setCensusCode(nodeList1.item(2).getTextContent());

			tehsils.add(tehsil);

		}
		return tehsils;

	}

	/**
	 * \
	 * 
	 * @param dCode
	 * @param tCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Village> getVillages(String dCode, String tCode) throws Exception {

		List<Village> villages = new ArrayList<Village>();
		LOGGER.info("Service URL" + ApplicationSession.getInstance().getMessage("land.det.api.serviceURl"));
		service = new LRDataService(new URL(ApplicationSession.getInstance().getMessage("land.det.api.serviceURl")));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
		DocumentBuilder db;

		db = dbf.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(
				service.getLRDataServiceSoap().getVillages(ApplicationSession.getInstance().getMessage("land.det.api.key"), 
						ApplicationSession.getInstance().getMessage("haryana.stateCode"), dCode, tCode).getBytes());
		Document doc = db.parse(is);

		LOGGER.info("Number of Villages \t" + doc.getDocumentElement().getElementsByTagName("Villages").getLength());

		NodeList nodeList = doc.getElementsByTagName("Villages");
		NodeList nodeList1 = null;
		Village village = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList1 = nodeList.item(i).getChildNodes();
			village = new Village();

			village.setCode(nodeList1.item(1).getTextContent());
			village.setName(nodeList1.item(0).getTextContent());

			village.setKhewats(nodeList1.item(2).getTextContent());
			village.setKhatonis(nodeList1.item(3).getTextContent());
			village.setKhasras(nodeList1.item(4).getTextContent());

			villages.add(village);

		}
		return villages;

	}

	/**
	 * 
	 * @param dCode
	 * @param tCode
	 * @param NVCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Must getMurabaByNVCODE(String dCode, String tCode, String NVCode) throws Exception {

		Must must = new Must();
		LOGGER.info("Service URL" + ApplicationSession.getInstance().getMessage("land.det.api.serviceURl"));
		service = new LRDataService(new URL(ApplicationSession.getInstance().getMessage("land.det.api.serviceURl")));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
		DocumentBuilder db;

		db = dbf.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(
				service.getLRDataServiceSoap().getMurabaByNVCODE(ApplicationSession.getInstance().getMessage("land.det.api.key"), dCode, tCode, NVCode).getBytes());
		Document doc = db.parse(is);

		LOGGER.info("Number of Must\t" + doc.getDocumentElement().getElementsByTagName("must").getLength());

		NodeList nodeList = doc.getElementsByTagName("must");
		NodeList nodeList1 = null;
		List<String> musts = new ArrayList<String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList1 = nodeList.item(i).getChildNodes();
			musts.add(nodeList1.item(0).getTextContent());

		}
		must.setMust(musts);
		return must;

	}

	/**
	 * 
	 * @param dCode
	 * @param tCode
	 * @param NVCode
	 * @param muraba
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Khasra> getKhasraListByNVCODE(String dCode, String tCode, String NVCode, String muraba)
			throws Exception {

		List<Khasra> Khasras = new ArrayList<Khasra>();
		LOGGER.info("Service URL" + ApplicationSession.getInstance().getMessage("land.det.api.serviceURl"));
		service = new LRDataService(new URL(ApplicationSession.getInstance().getMessage("land.det.api.serviceURl")));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
		DocumentBuilder db;

		db = dbf.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(
				service.getLRDataServiceSoap().getKhasraListByNVCODE(ApplicationSession.getInstance().getMessage("land.det.api.key"), dCode, tCode, NVCode, muraba).getBytes());
		Document doc = db.parse(is);

		LOGGER.info("Number of khasra \t" + doc.getDocumentElement().getElementsByTagName("khasra").getLength());

		NodeList nodeList = doc.getElementsByTagName("khasra");
		NodeList nodeList1 = null;
		Khasra khasra = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList1 = nodeList.item(i).getChildNodes();

			khasra = new Khasra();
			khasra.setKhewats(nodeList1.item(0).getTextContent());
			khasra.setKhatonis(nodeList1.item(1).getTextContent());
			khasra.setKilla(nodeList1.item(2).getTextContent());
			khasra.setKnl(nodeList1.item(3).getTextContent());
			khasra.setMrl(nodeList1.item(4).getTextContent());
			khasra.setPeriod(nodeList1.item(5).getTextContent());
			khasra.setGovt(nodeList1.item(6).getTextContent());

			Khasras.add(khasra);

		}
		return Khasras;

	}

	/**
	 * 
	 * @param dCode
	 * @param tCode
	 * @param NVCode
	 * @param _Khewat
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Owner> getOwnersbykhewatOnline(String dCode, String tCode, String NVCode, String _Khewat)
			throws Exception {

		List<Owner> owners = new ArrayList<Owner>();
		LOGGER.info("Service URL" + ApplicationSession.getInstance().getMessage("land.det.api.serviceURl"));
		service = new LRDataService(new URL(ApplicationSession.getInstance().getMessage("land.det.api.serviceURl")));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
		DocumentBuilder db;

		db = dbf.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(
				service.getLRDataServiceSoap().getOwnersbykhewatOnline(ApplicationSession.getInstance().getMessage("land.det.api.key"), dCode, tCode, NVCode, _Khewat).getBytes());
		Document doc = db.parse(is);

		LOGGER.info("Number of DistrictCode\t" + doc.getDocumentElement().getElementsByTagName("root").getLength());

		NodeList nodeList = doc.getElementsByTagName("root");
		NodeList nodeList1 = null;
		Owner owner = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList1 = nodeList.item(i).getChildNodes();

			owner = new Owner();
			owner.setCkhewat(nodeList1.item(2).getTextContent());
			owner.setName(nodeList1.item(0).getTextContent());
			LOGGER.info(nodeList1.item(0).getTextContent());			
			owner.setNvCode(nodeList1.item(1).getTextContent());

			owners.add(owner);

		}
		return owners;

	}
	
	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
				requestDTO);
	}
	@Override
	public List<LicenseApplicationMasterDTO> getNewLicenseSummaryData(Long orgId,  Long userId) {
		List<LicenseApplicationMasterDTO> applicationMasDTOList = new ArrayList<>();
		List<LicenseApplicationMaster> applicationMasterList = licenseApplicationMasRepository.getNewLicenseSummaryData(orgId, userId);
		applicationMasterList.forEach(applicationMaster -> {
			LicenseApplicationMasterDTO applicationMasterDTO = new LicenseApplicationMasterDTO();
			BeanUtils.copyProperties(applicationMaster, applicationMasterDTO);
			WorkflowRequest workflowRequest= workflowActionService.findWorkflowActionRequest(applicationMasterDTO.getApplicationNo(), orgId);
			if(null!=workflowRequest.getStatus() && workflowRequest.getStatus().equalsIgnoreCase(MainetConstants.DASHBOARD.CLOSED) && workflowRequest.getLastDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {
				applicationMasterDTO.setWorkflowStatus(workflowRequest.getLastDecision());
			}
			applicationMasDTOList.add(applicationMasterDTO);
		});
		return applicationMasDTOList;
		
	}
	
	private void initializeWorkFlowForFreeService(LicenseApplicationMasterDTO requestDto, ServiceMaster serviceMaster) {
		boolean checkList = false;
		if (requestDto.getPurposeAttachments() != null && !requestDto.getPurposeAttachments().isEmpty()) {
			checkList = true; //need to take clarity
		}
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
 
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getAuthPName());
		applicantDto.setDepartmentId(serviceMaster.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(requestDto.getCreatedBy());
		if (null != requestDto.getDdz1()) {
			applicantDto.setDwzid1(requestDto.getDdz1());
		}
		if (null != requestDto.getDdz2()) {
			applicantDto.setDwzid2(requestDto.getDdz2());
		}
		if (null != requestDto.getDdz3()) {
			applicantDto.setDwzid3(requestDto.getDdz3());
		}
		if (null != requestDto.getDdz4()) {
			applicantDto.setDwzid4(requestDto.getDdz4());
		}
		applicationMetaData.setApplicationId(requestDto.getApplicationNo());
		//applicationMetaData.setReferenceId(requestDto.getReferenceId());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		applicationMetaData.setIsApprReqInObjection("Y");
		applicantDto.setServiceId(serviceMaster.getSmServiceId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}



	@Override
	public LicenseApplicationMasterDTO findLicMasById(Long id) {
		LicenseApplicationMaster masEnttiy = licenseApplicationMasRepository.findByTcpLicMstrId(id);
		
		LicenseApplicationMasterDTO licMasDto = mapEntitytoDto(masEnttiy);
		
		return licMasDto;
		
	}



	private LicenseApplicationMasterDTO mapEntitytoDto(LicenseApplicationMaster masEnttiy) {
		
		LicenseApplicationMasterDTO dto = new LicenseApplicationMasterDTO();
		List<LicenseApplicationLandDetDTO> licenseApplicationLandDetDTOList = new ArrayList<LicenseApplicationLandDetDTO>();
		LicenseApplicationLandScheduleDTO licenseApplicationLandScheduleDTO = new LicenseApplicationLandScheduleDTO();
		List<LicenseApplicationLandAcquisitionDetailDTO> licenseApplicationLandAcquisitionDetDTOList = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
		List<LicenseApplicationPurposeTypeDetDTO> licenseApplicationPurposeTypeDetDTOList = new ArrayList<LicenseApplicationPurposeTypeDetDTO>();
		List<LicenseApplicationFeeMasDTO> feeMasterDto = new ArrayList<LicenseApplicationFeeMasDTO>();
		List<LicenseApplicationLandSurroundingsDTO> surroundingDtoList = new ArrayList<LicenseApplicationLandSurroundingsDTO>();
		LicenseApplicationLandCategoryDTO categoryDto = new LicenseApplicationLandCategoryDTO();
		
		BeanUtils.copyProperties(masEnttiy, dto);
		
		dto.setDdz1(masEnttiy.getKhrsDist());
		dto.setDdz2(masEnttiy.getKhrsDevPlan());
		dto.setDdz3(masEnttiy.getKhrsZone());
		dto.setDdz4(masEnttiy.getKhrsSec());

		if(masEnttiy.getLicenseApplicationLandAcquisitionDetEntityList() != null) {
			masEnttiy.getLicenseApplicationLandAcquisitionDetEntityList().forEach(landAcqDet ->{
				LicenseApplicationLandAcquisitionDetailDTO landAcqDto = new LicenseApplicationLandAcquisitionDetailDTO();
				BeanUtils.copyProperties(landAcqDet, landAcqDto);
				 LicenseApplicationMasterDTO licenseApplicationMaster=new LicenseApplicationMasterDTO();
					BeanUtils.copyProperties(landAcqDet.getLicenseApplicationMaster(), licenseApplicationMaster);
					landAcqDto.setLicenseApplicationMaster(licenseApplicationMaster);
				licenseApplicationLandAcquisitionDetDTOList.add(landAcqDto);
				
			});
		}
		
		if(masEnttiy.getLicenseApplicationLandSchedule() != null) {
			BeanUtils.copyProperties(masEnttiy.getLicenseApplicationLandSchedule(), licenseApplicationLandScheduleDTO);
			masEnttiy.getLicenseApplicationLandSchedule().getLicenseApplicationLandSurroundingsEntity().forEach(surrondEntity ->{
				LicenseApplicationLandSurroundingsDTO  surroundingDto = new LicenseApplicationLandSurroundingsDTO();
				BeanUtils.copyProperties(surrondEntity, surroundingDto);
				surroundingDtoList.add(surroundingDto);
			});
		}
		
		if (masEnttiy != null) {
		    List<LicenseApplicationLandDet> landDetList = masEnttiy.getLicenseApplicationLandDetList();
		    if (landDetList != null) {
			masEnttiy.getLicenseApplicationLandDetList().forEach(landDet ->{
				LicenseApplicationLandDetDTO landDto = new LicenseApplicationLandDetDTO();
				BeanUtils.copyProperties(landDet, landDto);
				licenseApplicationLandDetDTOList.add(landDto);
				
			});
		    }
		}
		
		if(masEnttiy.getLicenseApplicationPurposeTypeDetEntityList() != null) {
			masEnttiy.getLicenseApplicationPurposeTypeDetEntityList().forEach(purDetEntity ->{
				LicenseApplicationPurposeTypeDetDTO puTypDto = new LicenseApplicationPurposeTypeDetDTO();
				BeanUtils.copyProperties(purDetEntity, puTypDto);
				licenseApplicationPurposeTypeDetDTOList.add(puTypDto);
				
			});
		}
		
		if(masEnttiy.getLicenseApplicationFeesMasterEntity() != null) {
			masEnttiy.getLicenseApplicationFeesMasterEntity().forEach(feeDetEntity ->{
				LicenseApplicationFeeMasDTO feeDto = new LicenseApplicationFeeMasDTO();
				BeanUtils.copyProperties(feeDetEntity, feeDto);
				feeMasterDto.add(feeDto);
			});
		}
		
		if(masEnttiy.getLicenseApplicationLandCategoryEntity() != null) {
			BeanUtils.copyProperties(masEnttiy.getLicenseApplicationLandCategoryEntity(), categoryDto);
		}
		
		dto.setLicenseApplicationLandAcquisitionDetDTOList(licenseApplicationLandAcquisitionDetDTOList);
		if (licenseApplicationLandScheduleDTO != null) {
			dto.setLicenseApplicationLandScheduleDTO(licenseApplicationLandScheduleDTO);
			dto.getLicenseApplicationLandScheduleDTO().setLicenseApplicationLandSurroundingsDtoList(surroundingDtoList);
		}
		if (licenseApplicationPurposeTypeDetDTOList != null) {
			dto.setLicenseApplicationPurposeTypeDetDTOList(licenseApplicationPurposeTypeDetDTOList);
		}
		if(feeMasterDto != null) {
			dto.setFeeMasterDto(feeMasterDto);
		}
		if(categoryDto != null) {
			dto.setLandCategoryDTO(categoryDto);
		}
		
		
		return dto;
	}
	
	@Override
	public LicenseApplicationMasterDTO findByApplicationNoAndOrgId(Long applicationNo, Long orgId) {
		LicenseApplicationMasterDTO mastDto = new LicenseApplicationMasterDTO();
		LicenseApplicationMaster masEntity = licenseApplicationMasRepository.findByApplicationNoAndOrgId(applicationNo, orgId);
		mastDto = mapEntitytoDto(masEntity);
		return mastDto;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Map<String, String> getApplicationDetail(final Long applicationId,Long orgId) {

		LOGGER.info("Trade Licence Data fetched for application id " + applicationId);
		Map<String, String> map = new HashMap<String, String>();
		try {
			LicenseApplicationMaster entity = licenseApplicationMasRepository
					.findByApplicationNoAndOrgId(applicationId,orgId);
			DeveloperRegistrationDTO developerRegistrationDTO = new DeveloperRegistrationDTO();
			TbDeveloperRegistrationEntity tbDeveloperRegistrationEntity=new TbDeveloperRegistrationEntity();
			EmployeeBean employee = employeeService.findById(entity.getCreatedBy());
			Long empId = null;
			if (null == employee.getReportingManager()) {
				empId = employee.getEmpId();
			} else {
				empId = employee.getReportingManager();
			}
			 List<TbDeveloperRegistrationEntity> tbDeveloperRegistrationEntityList = devRegMasRepository.getDeveloperRegistrationByCreatedById(empId);
			    
			    if (!tbDeveloperRegistrationEntityList.isEmpty()) {
			         tbDeveloperRegistrationEntity = tbDeveloperRegistrationEntityList.get(0);
			        
			        BeanUtils.copyProperties(tbDeveloperRegistrationEntity, developerRegistrationDTO);
			    }
			if (entity != null) {
				if (entity.getAppPAppType() != null)
					map.put("APP_P_APP_TYPE", CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getAppPAppType(), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (entity.getAppPLicPurposeId() != null)
					map.put("APP_P_LIC_PURPOSE_ID",CommonMasterUtility.getHierarchicalLookUp(entity.getAppPLicPurposeId(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getKhrsDist() != null)
					map.put("KHRS_DIST",CommonMasterUtility.getHierarchicalLookUp(entity.getKhrsDist(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getKhrsDevPlan() != null)
					map.put("KHRS_DEV_PLAN",CommonMasterUtility.getHierarchicalLookUp(entity.getKhrsDevPlan(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getKhrsZone() != null)
					map.put("KHRS_ZONE",CommonMasterUtility.getHierarchicalLookUp(entity.getKhrsZone(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getKhrsSec() != null)
					map.put("KHRS_SEC",CommonMasterUtility.getHierarchicalLookUp(entity.getKhrsSec(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getKhrsThesil() != null)
					map.put("KHRS_THESIL", entity.getKhrsThesil().toString());
				if (entity.getKhrsRevEst() != null)
					map.put("KHRS_REV_EST", entity.getKhrsRevEst().toString());
				if (entity.getKhrsHadbast() != null)
					map.put("KHRS_HADBAST", entity.getKhrsHadbast().toString());
				if (entity.getKhrsMustil() != null)
					map.put("KHRS_MUSTIL", entity.getKhrsMustil().toString());
				if (entity.getKhrsKilla() != null)
					map.put("KHRS_KILLA", entity.getKhrsKilla().toString());
				if (entity.getMin() != null)
					map.put("MIN", entity.getMin().toString());
				if (entity.getTcpNameOfLO() != null)
					map.put("TCP_NAME_OF_LO", entity.getTcpNameOfLO().toString());
				if (entity.getKhrsDevCollab() != null)
					map.put("KHRS_DEV_COLLAB", entity.getKhrsDevCollab().toString());
				if (entity.getKhrsDevComName() != null)
					map.put("KHRS_DEV_COM_NAME", entity.getKhrsDevComName().toString());
				if (entity.getKhrsColabRegDate() != null)
					map.put("KHRS_COLAB_REG_DATE", entity.getKhrsColabRegDate().toString());
				if (entity.getKhrsCollabAggRevo() != null)
					map.put("KHRS_COLLAB_AGG_REVO", entity.getKhrsCollabAggRevo().toString());
				if (entity.getKhrsAuthSignLO() != null)
					map.put("KHRS_AUTH_SIGN_LO", entity.getKhrsAuthSignLO().toString());
				if (entity.getKhrsAurSignDev() != null)
					map.put("Khrs_aur_sign_dev", entity.getKhrsAurSignDev().toString());
				if (entity.getKhrsRegAuth() != null)
					map.put("Khrs_reg_auth", entity.getKhrsRegAuth().toString());
				if (entity.getKhrsLandTypeId() != null)
					map.put("Khrs_land_type_id",CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getKhrsLandTypeId(), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (entity.getCiConsType() != null)
					map.put("CI_Cons_type", entity.getCiConsType().toString());
				if (entity.getCiTotArea() != null)
					map.put("CI_tot_area", entity.getCiTotArea().toString());
				//tb_bpms_lic_fees
				if(!entity.getLicenseApplicationFeesMasterEntity().isEmpty()) {
				if (entity.getLicenseApplicationFeesMasterEntity().get(0).getTaxCategory() != null)
					map.put("CWC_Purpose", entity.getLicenseApplicationFeesMasterEntity().get(0).getTaxCategory().toString());}
				//tb_bpms_lic_Aqu_Det
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getChInfo() != null)
					map.put("Ch_info", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getChInfo().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getMustilCh() != null)
					map.put("Mustil_ch", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getMustilCh().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getKhasaraCh() != null)
					map.put("Khasara_ch", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getKhasaraCh().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getLandOwnerMUJAM() != null)
					map.put("Land_owner_MU_JAM", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getLandOwnerMUJAM().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getKanal() != null)
					map.put("kanal", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getKanal().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getMarla() != null)
					map.put("Marla", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getMarla().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getSarsai() != null)
					map.put("Sarsai", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getSarsai().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getConsolTotArea() != null)
					map.put("consol_tot_area", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getConsolTotArea().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getBigha() != null)
					map.put("Bigha", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getBigha().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getBiswa() != null)
					map.put("Biswa", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getBiswa().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getBiswansi() != null)
					map.put("Biswansi", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getBiswansi().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getConsolTotArea() != null)
					map.put("consol_tot_area", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getConsolTotArea().toString());
				if (entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getAcqStat() != null)
					map.put("acq_stat", entity.getLicenseApplicationLandAcquisitionDetEntityList().get(0).getAcqStat().toString());
				
				
				//tb_bpms_app_purpose
				if (entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getApplicationPurpose1()!= null)
					map.put("Purpose",CommonMasterUtility.getHierarchicalLookUp(entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getApplicationPurpose1(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getApplicationPurpose2()!= null)
					map.put("Subpurpose1",CommonMasterUtility.getHierarchicalLookUp(entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getApplicationPurpose2(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getApplicationPurpose3()!= null)
					map.put("Subpurpose2",CommonMasterUtility.getHierarchicalLookUp(entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getApplicationPurpose3(),UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getFar()!= null)
					map.put("FAR",CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getFar(), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getArea()!= null)
					map.put("Area", entity.getLicenseApplicationPurposeTypeDetEntityList().get(0).getArea().toString());
				
				//tb_bpms_land_Surroundings
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getLicenseApplicationLandSurroundingsEntity().get(0).getPocketName()))
					map.put("PocketName", entity.getLicenseApplicationLandSchedule().getLicenseApplicationLandSurroundingsEntity().get(0).getPocketName());

				//TB_BPMS_LAND_SCH
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getEncumb()))
					map.put("encumb", entity.getLicenseApplicationLandSchedule().getEncumb().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getEncumbRemarks()))
					map.put("encumb_remarks", entity.getLicenseApplicationLandSchedule().getEncumbRemarks().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getExiLitigation()))
					map.put("Exi_Litigation", entity.getLicenseApplicationLandSchedule().getExiLitigation().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getExiLitigationCOrd()))
					map.put("Exi_Litigation_C_ord", entity.getLicenseApplicationLandSchedule().getExiLitigationCOrd().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getExiLitigationCOrdRemAse()))
					map.put("Exi_Litigation_C_ord_rem_ase", entity.getLicenseApplicationLandSchedule().getExiLitigationCOrdRemAse().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getInsolv()))
					map.put("Insolv", entity.getLicenseApplicationLandSchedule().getInsolv().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getInsolvRemarks()))
					map.put("Insolv_remarks", entity.getLicenseApplicationLandSchedule().getInsolvRemarks().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAppliedLand()))
					map.put("SP_applied_land", entity.getLicenseApplicationLandSchedule().getSpAppliedLand().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpRevRastaTyp()))
					map.put("SP_rev_rasta_typ", entity.getLicenseApplicationLandSchedule().getSpRevRastaTyp().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpRevRastaTypConId()!= null)
					map.put("SP_rev_rasta_typ_con_id",CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getLicenseApplicationLandSchedule().getSpRevRastaTypConId(), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpRevRastaTypConWid()!= null)
					map.put("SP_rev_rasta_typ_con_wid", entity.getLicenseApplicationLandSchedule().getSpRevRastaTypConWid().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpWatercourse()))
					map.put("SP_Watercourse", entity.getLicenseApplicationLandSchedule().getSpWatercourse().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpWatercourseTypId()!= null)
					map.put("SP_Watercourse_typ_id",CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getLicenseApplicationLandSchedule().getSpWatercourseTypId(), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpWatercourseTypWid()!= null)
					map.put("SP_Watercourse_typ_wid", entity.getLicenseApplicationLandSchedule().getSpWatercourseTypWid().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpWatercourseTypRem()))
					map.put("SP_Watercourse_typ_rem", entity.getLicenseApplicationLandSchedule().getSpWatercourseTypRem().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatus()))
					map.put("SP_Acq_status", entity.getLicenseApplicationLandSchedule().getSpAcqStatus().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpAcqStatusD4Not()!= null)
					map.put("SP_Acq_status_D4_not", entity.getLicenseApplicationLandSchedule().getSpAcqStatusD4Not().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpAcqStatusD6Not()!= null)
					map.put("SP_Acq_status_D6_not", entity.getLicenseApplicationLandSchedule().getSpAcqStatusD6Not().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpAcqStatusDAward()!= null)
					map.put("SP_Acq_status_DAward", entity.getLicenseApplicationLandSchedule().getSpAcqStatusDAward().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatusExcluAquPr()))
					map.put("SP_Acq_status_Exclu_Aqu_pr", entity.getLicenseApplicationLandSchedule().getSpAcqStatusExcluAquPr().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatusLdComp()))
					map.put("SP_Acq_status_Ld_comp", entity.getLicenseApplicationLandSchedule().getSpAcqStatusLdComp().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelStat()))
					map.put("SP_Acq_status_rel_stat",CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelStat()), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelDate()!= null)
					map.put("SP_Acq_status_rel_date", entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelDate().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatusSiteDet()))
					map.put("SP_Acq_status_site_det", entity.getLicenseApplicationLandSchedule().getSpAcqStatusSiteDet().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelLitig()))
					map.put("SP_Acq_status_rel_litig", entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelLitig().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelCwpSl()))
					map.put("SP_Acq_status_rel_CWP/SLP", entity.getLicenseApplicationLandSchedule().getSpAcqStatusRelCwpSl().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpCompactBlock()))
					map.put("SP_compact_block", entity.getLicenseApplicationLandSchedule().getSpCompactBlock().toString());
				if (entity.getLicenseApplicationLandSchedule().getSpCompactBlockSep()!= null && entity.getLicenseApplicationLandSchedule().getSpCompactBlockSep()!=0)
					map.put("SP_compact_block_sep", entity.getLicenseApplicationLandSchedule().getSpCompactBlockSep().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getSpCompactBlockPkt())) 
					map.put("SP_compact_block_pkt", entity.getLicenseApplicationLandSchedule().getSpCompactBlockPkt().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getaPaSiteAprSprWid()))
					map.put("a_pa_site_apr_SR_wid", entity.getLicenseApplicationLandSchedule().getaPaSiteAprSprWid().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getbPaSiteAprSrAcq()))
					map.put("b_pa_site_apr_SR_acq", entity.getLicenseApplicationLandSchedule().getbPaSiteAprSrAcq().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getcPaSiteAprSrCons()))
					map.put("c_pa_site_apr_SR_cons", entity.getLicenseApplicationLandSchedule().getcPaSiteAprSrCons().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getdPaSiteAprSrSRA()))
					map.put("d_pa_site_apr_SR_SRA", entity.getLicenseApplicationLandSchedule().getdPaSiteAprSrSRA().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getePaSiteAprSrSRC()))
					map.put("e_pa_site_apr_SR_SRC", entity.getLicenseApplicationLandSchedule().getePaSiteAprSrSRC().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getPaSiteAprSPR()))
					map.put("pa_site_apr_SPR", entity.getLicenseApplicationLandSchedule().getPaSiteAprSPR().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getaPaSiteAprSprWid()))
					map.put("a_pa_site_apr_SPR_wid", entity.getLicenseApplicationLandSchedule().getaPaSiteAprSprWid().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getbPaSiteAprSprAcq()))
					map.put("b_pa_site_apr_SPR_acq", entity.getLicenseApplicationLandSchedule().getbPaSiteAprSprAcq().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getcPaSiteAprSprCons()))
					map.put("c_pa_site_apr_SPR_cons", entity.getLicenseApplicationLandSchedule().getcPaSiteAprSprCons().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getPaSiteAprOther()))
					map.put("pa_site_apr_other", entity.getLicenseApplicationLandSchedule().getPaSiteAprOther().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScVacRemmark()))
					map.put("SC_vac_remmark", entity.getLicenseApplicationLandSchedule().getScVacRemmark().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScHtLine()))
					map.put("SC_HT_line", entity.getLicenseApplicationLandSchedule().getScHtLine().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScHtLineRemark()))
					map.put("SC_HT_line_remark", entity.getLicenseApplicationLandSchedule().getScHtLineRemark().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScIocGasPline()))
					map.put("SC_IOC_Gas_Pline", entity.getLicenseApplicationLandSchedule().getScIocGasPline().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScIocGasPlineRemark()))
					map.put("SC_IOC_Gas_Pline_remark", entity.getLicenseApplicationLandSchedule().getScIocGasPlineRemark().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScNallah()))
					map.put("SC_Nallah", entity.getLicenseApplicationLandSchedule().getScNallah().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScNallahRemark()))
					map.put("SC_Nallah_remark", entity.getLicenseApplicationLandSchedule().getScNallahRemark().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScUtilityLine()))
					map.put("SC_Utility_Line", entity.getLicenseApplicationLandSchedule().getScUtilityLine().toString());
				if (entity.getLicenseApplicationLandSchedule().getScUtilityLineWidth()!= null)
					map.put("SC_Utility_Line_width", entity.getLicenseApplicationLandSchedule().getScUtilityLineWidth().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScUtilityLineRemark()))
					map.put("SC_Utility_Line_remark", entity.getLicenseApplicationLandSchedule().getScUtilityLineRemark().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScUtilityLineAL()))
					map.put("SC_Utility_Line_AL", entity.getLicenseApplicationLandSchedule().getScUtilityLineAL().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScUtilityLineALRemark()))
					map.put("SC_Utility_Line_AL_remark", entity.getLicenseApplicationLandSchedule().getScUtilityLineALRemark().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScOtherFeature()))
					map.put("SC_Other_feature", entity.getLicenseApplicationLandSchedule().getScOtherFeature().toString());
				if (StringUtils.isNotBlank(entity.getLicenseApplicationLandSchedule().getScOtherFeatureDet()))
					map.put("SC_Other_feature_det", entity.getLicenseApplicationLandSchedule().getScOtherFeatureDet().toString());
				
			}
			
			if (developerRegistrationDTO != null) {
				//tb_bpms_dev_reg
				if (StringUtils.isNotBlank(developerRegistrationDTO.getRegisteredAddress()))
					map.put("tcp_dev_reg_add", developerRegistrationDTO.getRegisteredAddress().toString());
				if (StringUtils.isNotBlank(developerRegistrationDTO.getEmail()))
					map.put("tcp_dev_email", developerRegistrationDTO.getEmail().toString());
				if (developerRegistrationDTO.getDevType()!= null)
					map.put("tcp_dev_typ_id", CommonMasterUtility.getNonHierarchicalLookUpObject(developerRegistrationDTO.getDevType(), UserSession.getCurrent().getOrganisation()).getLookUpDesc().toString());
				if (StringUtils.isNotBlank(developerRegistrationDTO.getCinNo()))
					map.put("tcp_dev_cin_no", developerRegistrationDTO.getCinNo().toString());
				if (StringUtils.isNotBlank(developerRegistrationDTO.getLlpNo()))
					map.put("LLP_Number", developerRegistrationDTO.getLlpNo().toString());
				if (StringUtils.isNotBlank(developerRegistrationDTO.getGstNo()))
					map.put("tcp_dev_gst_no", developerRegistrationDTO.getGstNo().toString());
				
				//tb_bpms_stkhldr_mst
				
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList().get(0).getStakeholderName()))
					map.put("name", tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList().get(0).getStakeholderName().toString());
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList().get(0).getStakeholderDesignation()))
					map.put("designation", tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList().get(0).getStakeholderDesignation().toString());
				if (tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList().get(0).getStakeholderPercentage() != null)
					map.put("percentage", tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList().get(0).getStakeholderPercentage().toString());

				// TB_BPMS_DIRECTORS_MSTR
				if(!tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().isEmpty()) {
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().get(0).getDinNumber()))
					map.put("din_no", tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().get(0).getDinNumber().toString());
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().get(0).getDirectorName()))
					map.put("name", tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().get(0).getDirectorName().toString());
				if (tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().get(0).getDirectorContactNumber() != null)
					map.put("contact_no", tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList().get(0).getDirectorContactNumber().toString());
				}
				// TB_BPMS_AUTH_USER_MSTR
				if(!tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().isEmpty()) {
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthUserName()))
					map.put("full_name", tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthUserName().toString());
				if (tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthMobileNo() != null)
					map.put("mobile_no", tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthMobileNo().toString());
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthEmail()))
					map.put("email", tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthEmail().toString());
				if (StringUtils.isNotBlank(tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthPanNumber()))
					map.put("pan_no", tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList().get(0).getAuthPanNumber().toString());
				}
			}	
			
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return map;
	}
	
	@Override
	public void saveApplicantCheckList(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId) {
		if ((licenseApplicationMasterDTO.getApplicantCheckListDoc() != null) && !licenseApplicationMasterDTO.getApplicantCheckListDoc().isEmpty()) {						
			List<DocumentDetailsVO> appCheckListDocSave = new ArrayList<>();
			licenseApplicationMasterDTO.getApplicantCheckListDoc().forEach(appCheckList->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), appCheckList.getDocumentId(), 
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.APPLICANT_INFORMATION + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					appCheckListDocSave.add(appCheckList);
				}else if(documentUploadedListByClmIdRefId!=null){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), appCheckList.getDocumentId(), 
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.APPLICANT_INFORMATION + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					appCheckListDocSave.add(appCheckList);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.APPLICANT_INFORMATION + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(appCheckListDocSave, reqDto);
		}
	}
	
	@Override
	public void saveApplicantPurposeCheckList(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId) {
		if ((licenseApplicationMasterDTO.getPurposeAttachments() != null) && !licenseApplicationMasterDTO.getPurposeAttachments().isEmpty()) {
			List<DocumentDetailsVO> purCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getPurposeAttachments().forEach(purCheckListDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), purCheckListDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.KHASRA_DEVELOPED_COLLABORATION + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					purCheckListDocSave.add(purCheckListDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), purCheckListDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.KHASRA_DEVELOPED_COLLABORATION + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					purCheckListDocSave.add(purCheckListDoc);
				}
			
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.KHASRA_DEVELOPED_COLLABORATION + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(purCheckListDocSave, reqDto);
		}
	}
	
	@Override
	public void saveLandScheduleCheckListDoc(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId) {
						
		if ((licenseApplicationMasterDTO.getLandScheduleCheckListDoc() != null) && !licenseApplicationMasterDTO.getLandScheduleCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> landScheduleCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getLandScheduleCheckListDoc().forEach(landScheduleDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), landScheduleDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.LAND_SCHEDULE + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					landScheduleCheckListDocSave.add(landScheduleDoc);
				}else if(null !=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), landScheduleDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.LAND_SCHEDULE + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					landScheduleCheckListDocSave.add(landScheduleDoc);
				}
			});
			
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.LAND_SCHEDULE + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(landScheduleCheckListDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getEncumbranceCheckListDoc() != null) && !licenseApplicationMasterDTO.getEncumbranceCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> enCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getEncumbranceCheckListDoc().forEach(enCheckListDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), enCheckListDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ENCUMBRANCE_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					enCheckListDocSave.add(enCheckListDoc);
				}else if(null !=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), enCheckListDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ENCUMBRANCE_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					enCheckListDocSave.add(enCheckListDoc);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ENCUMBRANCE_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(enCheckListDocSave, reqDto);
		}
		
		
		if ((licenseApplicationMasterDTO.getCourtOrdersLandCheckListDoc() != null) && !licenseApplicationMasterDTO.getCourtOrdersLandCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> courtCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getCourtOrdersLandCheckListDoc().forEach(courtOrderDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), courtOrderDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.COURT_ORDER_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					courtCheckListDocSave.add(courtOrderDoc);
				}else if(null !=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), courtOrderDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.COURT_ORDER_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					courtCheckListDocSave.add(courtOrderDoc);
				}			
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.COURT_ORDER_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(courtCheckListDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getInsolvencyLandCheckListDoc() != null) && !licenseApplicationMasterDTO.getInsolvencyLandCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> insCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getInsolvencyLandCheckListDoc().forEach(insDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), insDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.INSOLVENCY_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					insCheckListDocSave.add(insDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), insDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.INSOLVENCY_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					insCheckListDocSave.add(insDoc);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.INSOLVENCY_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(insCheckListDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getShajraAppLandCheckListDoc() != null) && !licenseApplicationMasterDTO.getShajraAppLandCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> shajraDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getShajraAppLandCheckListDoc().forEach(shajraCheckListDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), shajraCheckListDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.SHAJRA_APP_LAND_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					shajraDocSave.add(shajraCheckListDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), shajraCheckListDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.SHAJRA_APP_LAND_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					shajraDocSave.add(shajraCheckListDoc);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.SHAJRA_APP_LAND_DOCUMENTS + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(shajraDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getReleaseOrderCheckListDoc() != null) && !licenseApplicationMasterDTO.getReleaseOrderCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> releaseOrderDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getReleaseOrderCheckListDoc().forEach(releaseOrderDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), releaseOrderDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.RELEASE_ORDER_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					releaseOrderDocSave.add(releaseOrderDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), releaseOrderDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.RELEASE_ORDER_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					releaseOrderDocSave.add(releaseOrderDoc);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.RELEASE_ORDER_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(releaseOrderDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getUsageAllotteesCheckListDoc() != null) && !licenseApplicationMasterDTO.getUsageAllotteesCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> usageDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getUsageAllotteesCheckListDoc().forEach(usageDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), usageDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.USAGE_ALLOTEE_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					usageDocSave.add(usageDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), usageDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.USAGE_ALLOTEE_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					usageDocSave.add(usageDoc);
				}
				
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.USAGE_ALLOTEE_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(usageDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getAccessNHSRCheckListDoc() != null) && !licenseApplicationMasterDTO.getAccessNHSRCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> accessDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getAccessNHSRCheckListDoc().forEach(accessDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), accessDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ACCESS_NHSR_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					accessDocSave.add(accessDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), accessDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ACCESS_NHSR_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					accessDocSave.add(accessDoc);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ACCESS_NHSR_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(accessDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getExistingApproachCheckListDoc() != null) && !licenseApplicationMasterDTO.getExistingApproachCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> existingAppDocSave = new ArrayList<>();
			licenseApplicationMasterDTO.getExistingApproachCheckListDoc().forEach(existingAppDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), existingAppDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.EXISTING_APPROACH_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					existingAppDocSave.add(existingAppDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), existingAppDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.EXISTING_APPROACH_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					existingAppDocSave.add(existingAppDoc);
				}
			
			});
			
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.EXISTING_APPROACH_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(existingAppDocSave, reqDto);
		}
	}
	
	@Override
	public void saveAppLandDetailsCheckListDoc(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId) {
			
		if ((licenseApplicationMasterDTO.getLandDetailsCheckListDoc() != null) && !licenseApplicationMasterDTO.getLandDetailsCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> landDetailsCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getLandDetailsCheckListDoc().forEach(landDetailsDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), landDetailsDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DETAILS_OF_LAND + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					landDetailsCheckListDocSave.add(landDetailsDoc);
				}else if(null!= documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), landDetailsDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DETAILS_OF_LAND + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					landDetailsCheckListDocSave.add(landDetailsDoc);
				}			
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DETAILS_OF_LAND + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(landDetailsCheckListDocSave, reqDto);
		}
		
		if ((licenseApplicationMasterDTO.getDGPSCheckListDoc() != null) && !licenseApplicationMasterDTO.getDGPSCheckListDoc().isEmpty()) {
			List<DocumentDetailsVO> dgpsCheckListDocSave = new ArrayList<>();
			
			licenseApplicationMasterDTO.getDGPSCheckListDoc().forEach(dgpsDoc->{
				CFCAttachment documentUploadedListByClmIdRefId = iChecklistVerificationService.getDocumentUploadedListByClmIdRefId(licenseApplicationMasterDTO.getApplicationNo(), dgpsDoc.getDocumentId(),
						"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DGPS_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
				if(null==documentUploadedListByClmIdRefId){
					dgpsCheckListDocSave.add(dgpsDoc);
				}else if(null!=documentUploadedListByClmIdRefId){
					attechDocumentRepository.deleteDocumentByRefIdAndClmId(licenseApplicationMasterDTO.getApplicationNo(), serviceId, licenseApplicationMasterDTO.getOrgId(), dgpsDoc.getDocumentId(),
							"NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DGPS_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
					dgpsCheckListDocSave.add(dgpsDoc);
				}
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setOrgId(licenseApplicationMasterDTO.getOrgId());
			reqDto.setReferenceId("NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DGPS_DOC + MainetConstants.WINDOWS_SLASH + licenseApplicationMasterDTO.getApplicationNo());
			reqDto.setServiceId(serviceId);
			fileUpload.doFileUpload(dgpsCheckListDocSave, reqDto);
		}
	}
	
	@Override
	public List<LicenseApplicationLandAcquisitionDetEntity> saveLandDetailsData(List<LicenseApplicationLandAcquisitionDetEntity> listLandDetailsEntity,String scrnFlag) {
		licenseApplicationLandAcquisitionDetRep.save(listLandDetailsEntity);
		List<LicenseApplicationLandAcquisitionDetHistEntity> licenseHistData=licenseApplicationLandAcquisitionDetRep.getDataByTaskId(listLandDetailsEntity.get(0).getTaskId(),scrnFlag);
		if(!licenseHistData.isEmpty()) {
			for(LicenseApplicationLandAcquisitionDetEntity releaseOrderDocList :listLandDetailsEntity){
				LicenseApplicationLandAcquisitionDetHistEntity scrutinyDetailHistEntity = new LicenseApplicationLandAcquisitionDetHistEntity();
				BeanUtils.copyProperties(releaseOrderDocList, scrutinyDetailHistEntity);
				for(LicenseApplicationLandAcquisitionDetHistEntity listData :licenseHistData){
					if(releaseOrderDocList.getLicAcquDetId().equals(listData.getLicAcquDetId())){
						scrutinyDetailHistEntity.sethLicAcquDetId(listData.gethLicAcquDetId());
					}
				}
			   scrutinyDetailHistEntity.sethStatus(MainetConstants.FlagA);
			   scrutinyDetailHistEntity.setScrnFlag(scrnFlag);
			   licenseLandDetHist.save(scrutinyDetailHistEntity);
			}
			
		}else {
			for(LicenseApplicationLandAcquisitionDetEntity releaseOrderDocList :listLandDetailsEntity){
				LicenseApplicationLandAcquisitionDetHistEntity scrutinyDetailHistEntity = new LicenseApplicationLandAcquisitionDetHistEntity();
			   BeanUtils.copyProperties(releaseOrderDocList, scrutinyDetailHistEntity);
			   scrutinyDetailHistEntity.sethStatus(MainetConstants.FlagA);
			   scrutinyDetailHistEntity.setScrnFlag(scrnFlag);
			   licenseLandDetHist.save(scrutinyDetailHistEntity);
			}
		}
		
		return listLandDetailsEntity;
		
	}
	
	@Override
    @Transactional
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
            throws CloneNotSupportedException {
        Map<Long, Double> chargeMap = new HashMap<>();
        final WSRequestDTO requestDTO = new WSRequestDTO();
        chargeMap = bpmsLOICharges(requestDTO, applicationId, orgId, serviceId);
        return chargeMap;
    }


	
	private Map<Long, Double> bpmsLOICharges(final WSRequestDTO requestDTO, final long applicationId,
            final long orgId, final long serviceId) throws CloneNotSupportedException {

        final Map<Long, Double> chargeMap = new HashMap<>();
        final List<BPMSRateMaster> chargeModelList = new ArrayList<>();
        List<BPMSRateMaster> requiredCharges = new ArrayList<>();
        BPMSRateMaster tempRate = null;
        BPMSRateMaster rateMaster = null;
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        // [START] BRMS call initialize model
        LicenseApplicationMaster entity = licenseApplicationMasRepository.findByApplicationNoAndOrgId(applicationId, orgId);
       requiredCharges = getChargesForBPMSRateMaster(requestDTO,orgId, MainetConstants.TCP_NEW_LICENSE);

        String conversionCharge = MainetConstants.BLANK;
        String extDevWorkCharge = MainetConstants.BLANK;
        String licenseFeeCharge = MainetConstants.BLANK;
        String stateInfraDevCharge = MainetConstants.BLANK;
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC, 2, org);
        for (final LookUp lookup : subCategryLookup) {
            if (lookup.getLookUpCode().equals("COPY")) {
            	conversionCharge = lookup.getDescLangFirst();
                
            }
            if (lookup.getLookUpCode().equals("EDW")) {
            	extDevWorkCharge = lookup.getDescLangFirst();
                
            }
            if (lookup.getLookUpCode().equals("LF")) {
            	licenseFeeCharge = lookup.getDescLangFirst();
                
            }
            if (lookup.getLookUpCode().equals("SIDC")) {
            	stateInfraDevCharge = lookup.getDescLangFirst();
                break;
                
            }
        }
        for (final BPMSRateMaster actualRate : requiredCharges) {
            if (actualRate.getTaxSubCategory().equals(conversionCharge) || actualRate.getTaxSubCategory().equals(extDevWorkCharge)
            		||actualRate.getTaxSubCategory().equals(licenseFeeCharge) || actualRate.getTaxSubCategory().equals(stateInfraDevCharge)) {
                if (!entity.getLicenseApplicationPurposeTypeDetEntityList().isEmpty()) {
                    for (final LicenseApplicationPurposeTypeDetEntity puposeDetEntity : entity.getLicenseApplicationPurposeTypeDetEntityList()) {
                            rateMaster = null;
                            tempRate = (BPMSRateMaster) actualRate.clone();
                            rateMaster = populateChargeModel( puposeDetEntity, tempRate,entity);
                            chargeModelList.add(rateMaster);

                    }
                }
            }
        }
        
     
        requestDTO.setDataModel(chargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO, ServiceEndpoints.NL_LICENSE_FEE);
        final List<?> response = RestClient.castResponse(output, BPMSRateMaster.class);
        BPMSRateMaster loiCharges = null;
        Double baseRate = 0d;
        for (final Object rate : response) {
            loiCharges = (BPMSRateMaster) rate;
            baseRate = loiCharges.getFlatRate();
            TbTaxMas taxMas = taxMasService.findTaxDetails(loiCharges.getOrgId(),loiCharges.getTaxCode());
            chargeMap.put(taxMas.getTaxId(), baseRate);
        }
        return chargeMap;
        // [END]
    }

    
    
    private  List<BPMSRateMaster> getChargesForBPMSRateMaster(WSRequestDTO requestDTO, Long orgId,
            String serviceShortCode) {
        requestDTO.setModelName(MainetConstants.BPMS_RATE_MASTER_MODEL);
        List<BPMSRateMaster> requiredCharges = new ArrayList<BPMSRateMaster>();
        WSResponseDTO response = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<Object> BPMSRateMasterList = RestClient.castResponse(response, BPMSRateMaster.class);
            BPMSRateMaster rateMaster = (BPMSRateMaster) BPMSRateMasterList.get(0);
            rateMaster.setOrgId(orgId);
            rateMaster.setServiceCode(serviceShortCode);
            rateMaster.setApplicableAt(
                    Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
                            PrefixConstants.LookUp.CHARGE_MASTER_CAA,new Organisation(orgId)).getLookUpId()));
            requestDTO.setDataModel(rateMaster);
            WSResponseDTO res = getApplicableTaxes(requestDTO);

            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {

                List<?> rates = castResponse(res, BPMSRateMaster.class);
                for (Object rate : rates) {
                    requiredCharges.add((BPMSRateMaster) rate);
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
                	BPMSRateMaster responseMap = (BPMSRateMaster) object;
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
    
    
    private BPMSRateMaster populateChargeModel(LicenseApplicationPurposeTypeDetEntity purposeDetEnitity, BPMSRateMaster BPMSRateMaster, LicenseApplicationMaster entity) {
		
		Organisation organisation = new Organisation();
		organisation.setOrgid(entity.getOrgId());
		BPMSRateMaster.setOrgId(entity.getOrgId());
		BPMSRateMaster.setDeptCode(MainetConstants.ENV_TCP);
		BPMSRateMaster.setRateStartDate(new Date().getTime());
		BPMSRateMaster.setZone(CommonMasterUtility.getHierarchicalLookUp(entity.getKhrsZone(), organisation).getDescLangFirst());
		BPMSRateMaster.setItemCategory1(CommonMasterUtility.getHierarchicalLookUp(purposeDetEnitity.getApplicationPurpose1(),organisation).getDescLangFirst());
		BPMSRateMaster.setItemCategory2(CommonMasterUtility.getHierarchicalLookUp(purposeDetEnitity.getApplicationPurpose2(), organisation).getDescLangFirst());
		BPMSRateMaster.setItemCategory3(CommonMasterUtility.getHierarchicalLookUp(purposeDetEnitity.getApplicationPurpose3(), organisation).getDescLangFirst());
		BPMSRateMaster.setArea(purposeDetEnitity.getArea());
		if(null != purposeDetEnitity.getFar()){
			BPMSRateMaster.setFar(purposeDetEnitity.getFar());
		}
		return BPMSRateMaster;
	}
    
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
				BPMSRateMaster bpmsRateMaster = (BPMSRateMaster) CommonMasterUtility
						.castRequestToDataModel(requestDTO, BPMSRateMaster.class);
				validateDataModel(bpmsRateMaster, responseDTO);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					responseDTO = populateOtherFieldsForServiceCharge(bpmsRateMaster, responseDTO);
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
    
    private WSResponseDTO validateDataModel(BPMSRateMaster bpmsRateMaster, WSResponseDTO responseDTO) {
		LOGGER.info("validateDataModel execution start..");
		StringBuilder builder = new StringBuilder();
		if (bpmsRateMaster.getServiceCode() == null || bpmsRateMaster.getServiceCode().isEmpty()) {
			builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
		}
		if (bpmsRateMaster.getOrgId() == 0l) {
			builder.append(ORG_ID_CANT_BE_ZERO).append(",");
		}
		if (bpmsRateMaster.getApplicableAt() == null
				|| bpmsRateMaster.getApplicableAt().isEmpty()) {
			builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
		} else if (!StringUtils.isNumeric(bpmsRateMaster.getApplicableAt())) {
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
    
    private WSResponseDTO populateOtherFieldsForServiceCharge(BPMSRateMaster bpmsRateMaster,
			WSResponseDTO responseDTO) throws CloneNotSupportedException {
		LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
		List<BPMSRateMaster> listOfCharges;
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(bpmsRateMaster.getServiceCode(),
				bpmsRateMaster.getOrgId());
		if (serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES) || serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
			List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
					serviceMas.getSmServiceId(), bpmsRateMaster.getOrgId(),
					Long.parseLong(bpmsRateMaster.getApplicableAt()));
			Organisation organisation = new Organisation();
			organisation.setOrgid(bpmsRateMaster.getOrgId());
			listOfCharges = settingAllFields(applicableCharges, bpmsRateMaster, organisation);
			responseDTO.setResponseObj(listOfCharges);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		} else {
			responseDTO.setFree(true);
			responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
		}
		LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
		return responseDTO;
	}
    
    private List<BPMSRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges,
    		BPMSRateMaster bpmsRateMaster, Organisation organisation) throws CloneNotSupportedException {
		LOGGER.info("settingAllFields execution start..");
		List<BPMSRateMaster> list = new ArrayList<>();
		for (TbTaxMasEntity entity : applicableCharges) {
			BPMSRateMaster bpmsRateMasters = (BPMSRateMaster) bpmsRateMaster.clone();
			// SLD for dependsOnFactor
			String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
					bpmsRateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
			String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
					entity.getOrgid(), entity.getTaxApplicable());
			bpmsRateMasters.setTaxType(taxType);
			bpmsRateMasters.setTaxCode(entity.getTaxCode());
			bpmsRateMasters.setApplicableAt(chargeApplicableAt);
			settingTaxCategories(bpmsRateMasters, entity, organisation);
			//bpmsRateMasters.setTaxId(entity.getTaxId());
			list.add(bpmsRateMasters);
		}
		LOGGER.info("settingAllFields execution end..");
		return list;
	}
	
    @Transactional(readOnly = true)
	@Override
	public List<LicenseApplicationLandAcquisitionDetailDTO> getApplicationNotingDetail(final Long applicationId,Long orgId,Long taskId,String scrnFlag) {
    	List<LicenseApplicationLandAcquisitionDetailDTO> licenseApplicationLandAcquisitionDetDTOList = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
    	List<LicenseApplicationLandAcquisitionDetHistEntity> list=licenseApplicationMasRepository.getApplicationNotingDetail(applicationId,taskId,scrnFlag);
    	  BigDecimal stotalAreaa = BigDecimal.ZERO;
		  BigDecimal pTotalArea = BigDecimal.ZERO;
    	if (list != null && !list.isEmpty()) {
			list.forEach(landAcqDet -> {
				LicenseApplicationLandAcquisitionDetailDTO landAcqDto = new LicenseApplicationLandAcquisitionDetailDTO();
				 
				BeanUtils.copyProperties(landAcqDet, landAcqDto);
				if(landAcqDto.getKanal()!=null) {
					landAcqDto.setbArea(landAcqDto.getKanal().toString()+MainetConstants.HYPHEN+landAcqDto.getMarla().toString()+MainetConstants.HYPHEN+landAcqDto.getSarsai().toString());
					landAcqDto.setType("C");
				}else {
					landAcqDto.setbArea(landAcqDto.getBigha()+MainetConstants.HYPHEN+landAcqDto.getBiswa()+MainetConstants.HYPHEN+landAcqDto.getBiswansi());
					landAcqDto.setType("N");
				}
				licenseApplicationLandAcquisitionDetDTOList.add(landAcqDto);
			});
		}
    	for(LicenseApplicationLandAcquisitionDetailDTO ab : licenseApplicationLandAcquisitionDetDTOList) {
    		pTotalArea=pTotalArea.add(ab.getTotalArea());
    		if(ab.getNonConsolTotArea()!=null) {
    			stotalAreaa=stotalAreaa.add(ab.getNonConsolTotArea());
    		}else if(ab.getConsolTotArea()!=null){
    			stotalAreaa=stotalAreaa.add(ab.getConsolTotArea());
    		}
    	}
    	if(!licenseApplicationLandAcquisitionDetDTOList.isEmpty()) {
    	licenseApplicationLandAcquisitionDetDTOList.get(0).setStotalArea(stotalAreaa);
    	licenseApplicationLandAcquisitionDetDTOList.get(0).setpTotalArea(pTotalArea);
    	}
    	
		return licenseApplicationLandAcquisitionDetDTOList;
    	
    }
    
    @Override
	public List<DocumentDetailsVO> prepareFileUploadForNewLicenseDoc(final List<DocumentDetailsVO> docs, Long dCount) {
		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		if (docs != null && !docs.isEmpty() && !listOfString.isEmpty()) {
			long count = 0;
			for (final DocumentDetailsVO d : docs) {
				if (d.getDocumentSerialNo() != null) {
					count = d.getDocumentSerialNo() - 1;// writing this code because every time we did not get
														// documentSerialNo
				}

				if (listOfString.containsKey(dCount + count) && fileName.containsKey(dCount + count)) {
					d.setDocumentByteCode(listOfString.get(dCount + count));
					d.setDocumentName(fileName.get(dCount + count));
				}
				count++;
			}
		}
		return docs;
	}
    
    @Override
   	@Transactional
   	public void updateLoaRemark(Long applicationNo, String loaDcRemark) {			
   		licenseApplicationMasRepository.updateLoaRemark(applicationNo, loaDcRemark);
   	}
       
   	@Override
   	public String getLaoRemark(final Long applicationId,Long orgId) {
       	return licenseApplicationMasRepository.getLaoRemark(applicationId,orgId);
       }
   	
   	@Override
	public List<LicenseApplicationMaster> doesLicenseApplicationExist(Long khrsDist, Long khrsDevPlan, Long khrsZone, Long khrsSec,
			String khrsThesil, String khrsRevEst, String khrsHadbast, String khrsMustil, String khrsKilla) {
		List<LicenseApplicationMaster> licenseApplications = licenseApplicationMasRepository
				.findByKhrsDistAndKhrsDevPlanAndKhrsZoneAndKhrsSecAndKhrsThesilAndKhrsRevEstAndKhrsHadbastAndKhrsMustilAndKhrsKillaAndApplicationNoEServiceIsNotNull(
						khrsDist, khrsDevPlan, khrsZone, khrsSec, khrsThesil, khrsRevEst, khrsHadbast, khrsMustil,
						khrsKilla);
		

		return licenseApplications;
	}
   	@Override
   	public List<LicenseApplicationFeeMasDTO> getFeeAndCharges(final Long applicationId,Long orgId) {
   		List<LicenseApplicationFeeMasDTO> listDto=new ArrayList<>();
   	   List<LicenseApplicationFeesMasterEntity> entity=licenseApplicationMasRepository.getFeeAndCharges(applicationId);
   		for(LicenseApplicationFeesMasterEntity feeDto : entity) {
   			LicenseApplicationFeeMasDTO dto = new LicenseApplicationFeeMasDTO();
   			BeanUtils.copyProperties(feeDto, dto);
			dto.setTcpLicAppNo(feeDto.getLicenseApplicationMaster());
   			listDto.add(dto);
    	}
       	return listDto;
       }
   	
	@Override
	public List<LicenseApplicationFeeMasDTO> saveFeeCharges(List<LicenseApplicationFeeMasDTO> feeListDto, Long taskId) {
		for (LicenseApplicationFeeMasDTO feeList : feeListDto) {
			LicenseApplicationFeesMasterHistEntity scrutinyDetailHistEntity = new LicenseApplicationFeesMasterHistEntity();
			BeanUtils.copyProperties(feeList, scrutinyDetailHistEntity);
			scrutinyDetailHistEntity.sethStatus(MainetConstants.FlagA);
			scrutinyDetailHistEntity.setTaskId(taskId);
			scrutinyDetailHistEntity.setLicenseApplicationMaster(feeList.getTcpLicAppNo());
			feeMastHistRep.save(scrutinyDetailHistEntity);
		}
		return feeListDto;
	}
	
	@Override
   	public List<LicenseApplicationLandSurroundingsDTO> getSurroundingDetail(final Long applicationId,Long orgId) {
   		List<LicenseApplicationLandSurroundingsDTO> listDto=new ArrayList<>();
   	   List<LicenseApplicationLandSurroundingsEntity> entity=licenseApplicationMasRepository.getSurroundingDetail(applicationId);
   		for(LicenseApplicationLandSurroundingsEntity surroundDto : entity) {
   			LicenseApplicationLandSurroundingsDTO dto = new LicenseApplicationLandSurroundingsDTO();
   			BeanUtils.copyProperties(surroundDto, dto);
   			LicenseApplicationLandScheduleDTO landdto = new LicenseApplicationLandScheduleDTO();
   			BeanUtils.copyProperties(surroundDto.getLandSchId(), landdto);
   			dto.setLandSchId(landdto);
   			listDto.add(dto);
    	}
       	return listDto;
       }
   	
	@Override
	public List<LicenseApplicationLandSurroundingsDTO> saveSurroundingDetail(List<LicenseApplicationLandSurroundingsDTO> landSurroundListDto, Long taskId) {
		for (LicenseApplicationLandSurroundingsDTO surroudList : landSurroundListDto) {
			LicenseApplicationLandSurroundingsEntity surroudEntity = new LicenseApplicationLandSurroundingsEntity();
			BeanUtils.copyProperties(surroudList, surroudEntity);
			LicenseApplicationLandSchedule landEntity = new LicenseApplicationLandSchedule();
   			BeanUtils.copyProperties(surroudList.getLandSchId(), landEntity);
   			surroudEntity.setLandSchId(landEntity);
			surroundingResp.save(surroudEntity);
		}
		return landSurroundListDto;
	}
}
