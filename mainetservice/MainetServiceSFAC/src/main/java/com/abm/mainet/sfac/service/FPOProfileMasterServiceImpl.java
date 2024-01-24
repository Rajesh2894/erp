package com.abm.mainet.sfac.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.domain.AuditedBalanceSheetInfoDetailEntity;
import com.abm.mainet.sfac.domain.BusinessPlanInfoDetailEntity;
import com.abm.mainet.sfac.domain.CreditGrantDetEntity;
import com.abm.mainet.sfac.domain.CreditInformationDetEntity;
import com.abm.mainet.sfac.domain.CustomHiringCenterInfoDetailEntity;
import com.abm.mainet.sfac.domain.CustomHiringServiceInfoDetailEntity;
import com.abm.mainet.sfac.domain.DPRInfoDetEntity;
import com.abm.mainet.sfac.domain.EquipmentInfoDetailEntity;
import com.abm.mainet.sfac.domain.EquityInformationDetEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.FPOProfileFarmerSummaryDetEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.abm.mainet.sfac.domain.FPOProfileTrainingDetEntity;
import com.abm.mainet.sfac.domain.FinancialInformationDetEntity;
import com.abm.mainet.sfac.domain.LicenseInformationDetEntity;
import com.abm.mainet.sfac.domain.ManagementCostDetEntity;
import com.abm.mainet.sfac.domain.MarketLinkageInfoDetailEntity;
import com.abm.mainet.sfac.domain.PostHarvestInfraInfoDetailEntity;
import com.abm.mainet.sfac.domain.PreHarveshInfraInfoDetailEntity;
import com.abm.mainet.sfac.domain.ProductionInfoDetailEntity;
import com.abm.mainet.sfac.domain.SalesInfoDetailEntity;
import com.abm.mainet.sfac.domain.StrorageInfomartionDetailEntity;
import com.abm.mainet.sfac.domain.SubsidiesInfoDetailEntity;
import com.abm.mainet.sfac.domain.TransportVehicleInfoDetailEntity;
import com.abm.mainet.sfac.dto.AuditedBalanceSheetInfoDTO;
import com.abm.mainet.sfac.dto.BusinessPlanInfoDTO;
import com.abm.mainet.sfac.dto.CreditGrantDetailDto;
import com.abm.mainet.sfac.dto.CreditLinkageInformationDTO;
import com.abm.mainet.sfac.dto.CustomHiringCenterInfoDTO;
import com.abm.mainet.sfac.dto.CustomHiringServiceInfoDTO;
import com.abm.mainet.sfac.dto.DPRInfoDTO;
import com.abm.mainet.sfac.dto.EquipmentInfoDto;
import com.abm.mainet.sfac.dto.EquityInformationDetDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.FPOProfileFarmerSummaryDto;
import com.abm.mainet.sfac.dto.FPOProfileMasterDto;
import com.abm.mainet.sfac.dto.FPOProfileTrainingDetailDto;
import com.abm.mainet.sfac.dto.FinancialInformationDto;
import com.abm.mainet.sfac.dto.LicenseInformationDTO;
import com.abm.mainet.sfac.dto.ManagementCostDetailDto;
import com.abm.mainet.sfac.dto.MarketLinkageInfoDTO;
import com.abm.mainet.sfac.dto.PostHarvestInfraInfoDTO;
import com.abm.mainet.sfac.dto.PreHarveshInfraInfoDTO;
import com.abm.mainet.sfac.dto.ProductionInfoDTO;
import com.abm.mainet.sfac.dto.SalesInfoDTO;
import com.abm.mainet.sfac.dto.StrorageInfomartionDTO;
import com.abm.mainet.sfac.dto.SubsidiesInfoDTO;
import com.abm.mainet.sfac.dto.TransportVehicleInfoDTO;
import com.abm.mainet.sfac.repository.AuditedBalanceSheetInfoDetRepository;
import com.abm.mainet.sfac.repository.BusinessPlanInfoDetRepository;
import com.abm.mainet.sfac.repository.CreditGrandDetRepository;
import com.abm.mainet.sfac.repository.CreditInformationDetRepository;
import com.abm.mainet.sfac.repository.CustomHiringCenterInfoDetRepository;
import com.abm.mainet.sfac.repository.CustomHiringServiceInfoDetRepository;
import com.abm.mainet.sfac.repository.DPRInfoDetRepository;
import com.abm.mainet.sfac.repository.EquipmentInfoDetRepository;
import com.abm.mainet.sfac.repository.EquityInformationDetRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.repository.FPOProfileFarmerSummaryDetRepository;
import com.abm.mainet.sfac.repository.FPOProfileMasterRepository;
import com.abm.mainet.sfac.repository.FPOProfileTrainingDetEntityRepository;
import com.abm.mainet.sfac.repository.FinancialInformationDetEntityRepository;
import com.abm.mainet.sfac.repository.LicenseInformationDetRepository;
import com.abm.mainet.sfac.repository.ManagementCostDetRepository;
import com.abm.mainet.sfac.repository.MarketLinkageInfoDetRepository;
import com.abm.mainet.sfac.repository.PostHarvestInfraInfoDetRepository;
import com.abm.mainet.sfac.repository.PreHarveshInfraInfoDetRepository;
import com.abm.mainet.sfac.repository.ProductionInfoDetRepository;
import com.abm.mainet.sfac.repository.SalesInfoDetRepository;
import com.abm.mainet.sfac.repository.StrorageInfomartionDetRepository;
import com.abm.mainet.sfac.repository.SubsidiesInfoDetRepository;
import com.abm.mainet.sfac.repository.TransportVehicleInfoDetRepository;

@Service
public class FPOProfileMasterServiceImpl implements FPOProfileMasterService {

	private static final Logger logger = Logger.getLogger(FPOProfileMasterServiceImpl.class);

	@Autowired FPOProfileMasterRepository  fpoProfileMasterRepository;

	@Autowired LicenseInformationDetRepository licenseInformationDetRepository;

	@Autowired FPOMasterRepository fpoMasterRepository;

	@Autowired FinancialInformationDetEntityRepository financialInformationDetEntityRepository;

	@Autowired CreditInformationDetRepository creditInformationDetRepository;

	@Autowired EquityInformationDetRepository equityInformationDetRepository;

	@Autowired FPOProfileFarmerSummaryDetRepository fpoProfileFarmerSummaryDetRepository;

	@Autowired FPOProfileTrainingDetEntityRepository fpoProfileTrainingDetEntityRepository;

	@Autowired ManagementCostDetRepository managementCostDetRepository;

	@Autowired CreditGrandDetRepository creditGrandDetRepository;

	@Autowired StrorageInfomartionDetRepository strorageInfomartionDetRepository;

	@Autowired CustomHiringCenterInfoDetRepository customHiringCenterInfoDetRepository;

	@Autowired EquipmentInfoDetRepository equipmentInfoDetRepository;

	@Autowired CustomHiringServiceInfoDetRepository customHiringServiceInfoDetRepository;

	@Autowired ProductionInfoDetRepository productionInfoDetRepository;

	@Autowired SalesInfoDetRepository salesInfoDetRepository;

	@Autowired SubsidiesInfoDetRepository subsidiesInfoDetRepository;

	@Autowired PreHarveshInfraInfoDetRepository preHarveshInfraInfoDetRepository;

	@Autowired PostHarvestInfraInfoDetRepository postHarvestInfraInfoDetRepository;

	@Autowired TransportVehicleInfoDetRepository transportVehicleInfoDetRepository;

	@Autowired MarketLinkageInfoDetRepository marketLinkageInfoDetRepository;

	@Autowired BusinessPlanInfoDetRepository businessPlanInfoDetRepository;

	@Autowired AuditedBalanceSheetInfoDetRepository auditedBalanceSheetInfoDetRepository;

	@Autowired DPRInfoDetRepository dprInfoDetRepository;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Override
	public FPOProfileMasterDto saveAndUpdateApplication(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			if(mastDto !=null && mastDto.getFpmId()!=null &&  mastDto.getFpmId()!=0) {
				//FPOProfileManagemntMaster masEntity = mapDtoToMasterEntity(mastDto);
				List<FinancialInformationDetEntity> masEntity1 = mapDtoToChildEntity(mastDto);

				financialInformationDetEntityRepository.save(masEntity1);


			}else {
				FPOProfileManagementMaster masEntity = mapDtoToEntity(mastDto);
				masEntity = fpoProfileMasterRepository.save(masEntity);
				mastDto.setFpmId(masEntity.getFpmId());
			}


		} catch (Exception e) {
			logger.error("error occured while saving fpo master  details" + e);
			throw new FrameworkException("error occured while saving fpo master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<FinancialInformationDetEntity> mapDtoToChildEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());

		masterEntity.setAdditionalSharesIssued(mastDto.getAdditionalSharesIssued());
		masterEntity.setDateIssued(mastDto.getDateIssued());
		masterEntity.setOverallTurnOver(mastDto.getOverallTurnOver());
		masterEntity.setCommodityName(mastDto.getCommodityName());
		masterEntity.setCommodityQuanity(mastDto.getCommodityQuanity());
		masterEntity.setLiveStockName(mastDto.getLiveStockName());
		masterEntity.setLiveStockQuanity(mastDto.getLiveStockQuanity());
		masterEntity.setLgIpMac(mastDto.getLgIpMac());
		masterEntity.setUpdatedBy(mastDto.getUpdatedBy());
		masterEntity.setUpdatedDate(mastDto.getUpdatedDate());
		List<FinancialInformationDetEntity> financialInformationDetEntities = new ArrayList<>();

		fpoProfileMasterRepository.save(masterEntity);



		mastDto.getFinancialInformationDto().forEach(dto -> {
			FinancialInformationDetEntity entity = new FinancialInformationDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			financialInformationDetEntities.add(entity);
		});



		return financialInformationDetEntities;
	}

	private FPOProfileManagementMaster mapDtoToEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = new FPOProfileManagementMaster();

		List<FinancialInformationDetEntity> financialInformationDetEntities = new ArrayList<>();

		masterEntity.setFpoMasterEntity(fpoMasterRepository.findOne(mastDto.getFpoId()));
		masterEntity.setAdditionalSharesIssued(mastDto.getAdditionalSharesIssued());
		masterEntity.setDateIssued(mastDto.getDateIssued());
		masterEntity.setOverallTurnOver(mastDto.getOverallTurnOver());
		masterEntity.setCommodityName(mastDto.getCommodityName());
		masterEntity.setCommodityQuanity(mastDto.getCommodityQuanity());
		masterEntity.setLiveStockName(mastDto.getLiveStockName());
		masterEntity.setLiveStockQuanity(mastDto.getLiveStockQuanity());
		masterEntity.setCreatedBy(mastDto.getCreatedBy());
		masterEntity.setCreatedDate(mastDto.getCreatedDate());
		masterEntity.setOrgId(mastDto.getOrgId());
		masterEntity.setLgIpMac(mastDto.getLgIpMac());
		masterEntity.setUpdatedBy(mastDto.getUpdatedBy());
		masterEntity.setUpdatedDate(mastDto.getUpdatedDate());



		mastDto.getFinancialInformationDto().forEach(dto -> {
			FinancialInformationDetEntity entity = new FinancialInformationDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			financialInformationDetEntities.add(entity);
		});

		masterEntity.setFinancialInformationDetEntities(financialInformationDetEntities);

		return masterEntity;
	}

	@Override
	public FPOProfileMasterDto getDetailById(Long fpoId) {
		FPOProfileMasterDto fpoProfileMasterDto =   new FPOProfileMasterDto();
		List<FinancialInformationDto> financialInformationDtos = new ArrayList<>();
		List<LicenseInformationDTO> licenseInformationDTOs = new ArrayList<>();
		List<CreditLinkageInformationDTO> creditLinkageInformationDTOs = new ArrayList<>();
		List<FPOProfileFarmerSummaryDto> farmerSummaryDtos = new ArrayList<>();
		List<EquityInformationDetDto> equityInformationDetDtos = new ArrayList<>();
		List<ManagementCostDetailDto> managementCostDetailDtos = new ArrayList<>();
		List<CreditGrantDetailDto> creditGrantDetailDtos = new ArrayList<>();
		List<FPOProfileTrainingDetailDto> fpoProfileTrainingDetailDtos = new ArrayList<>();

		///////////////Phase 2 DTOs//////////////////////////////////////
		List<StrorageInfomartionDTO> strorageInfomartionDTOs = new ArrayList<>();
		List<ProductionInfoDTO> productionInfoDTOs = new ArrayList<>();
		List<SalesInfoDTO> salesInfoDTOs = new ArrayList<>();
		//List<CustomHiringCenterInfoDTO> customHiringCenterInfoDTOs = new ArrayList<>();
		List<EquipmentInfoDto> equipmentInfoDtos = new ArrayList<>();
		List<CustomHiringServiceInfoDTO> customHiringServiceInfoDTOs = new ArrayList<>();
		List<SubsidiesInfoDTO> subsidiesInfoDTOs = new ArrayList<>();
		List<PreHarveshInfraInfoDTO> preHarveshInfraInfoDTOs = new ArrayList<>();
		List<PostHarvestInfraInfoDTO> postHarvestInfraInfoDTOs = new ArrayList<>();
		List<TransportVehicleInfoDTO> transportVehicleInfoDTOs = new ArrayList<>();
		List<MarketLinkageInfoDTO> marketLinkageInfoDTOs = new ArrayList<>();
		List<BusinessPlanInfoDTO> businessPlanInfoDTOs = new ArrayList<>();
		List<AuditedBalanceSheetInfoDTO> auditedBalanceSheetInfoDTOs = new ArrayList<>();
		List<DPRInfoDTO> dprInfoDTOs = new ArrayList<>();

		/////////////////////////END ////////////////////////////////////////////////


		if(fpoMasterRepository.exists(fpoId)) {
			FPOMasterEntity fpoMasterEntity = fpoMasterRepository.findOne(fpoId);
			fpoProfileMasterDto.setFpoName(fpoMasterEntity.getFpoName());
			FPOProfileManagementMaster fpoProfileManagemntMaster = fpoProfileMasterRepository.findByFpoMasterEntity(fpoMasterEntity);
			Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();

			if(fpoProfileManagemntMaster!=null) {
				BeanUtils.copyProperties(fpoProfileManagemntMaster, fpoProfileMasterDto);
				List<FinancialInformationDetEntity> financialInformationDetEntities = financialInformationDetEntityRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(financialInformationDetEntities.size()>0) {
					for(FinancialInformationDetEntity financialInformationDetEntity : financialInformationDetEntities) {
						FinancialInformationDto financialInformationDto = new FinancialInformationDto();
						BeanUtils.copyProperties(financialInformationDetEntity,financialInformationDto);
						financialInformationDtos.add(financialInformationDto);
					}

				}
				List<LicenseInformationDetEntity> licenseInformationDetEntities = licenseInformationDetRepository.findByFpoProfileMgmtMasterAndLicenseName(fpoProfileManagemntMaster,"A");
				if(licenseInformationDetEntities.size()>0) {
					Long count = 0L;
					for(LicenseInformationDetEntity licenseInformationDetEntity : licenseInformationDetEntities) {
						LicenseInformationDTO licenseInformationDTO = new LicenseInformationDTO();
						BeanUtils.copyProperties(licenseInformationDetEntity,licenseInformationDTO);

						List<String> identifer = new ArrayList<>();
						identifer.add("FPM_LD" + MainetConstants.WINDOWS_SLASH + licenseInformationDetEntity.getLicId());

						// get attached document
						final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
								.getBean(IAttachDocsService.class)
								.findByIdfInQuery(licenseInformationDetEntity.getOrgId(), identifer);
						if (!attachDocs.isEmpty()) {
							newMap.put(count, attachDocs);
						}
						licenseInformationDTO.setAttachDocsListLi(attachDocs);



						licenseInformationDTOs.add(licenseInformationDTO);
						count++;
					}

				}
				List<CreditInformationDetEntity> creditInformationDetEntities = creditInformationDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(creditInformationDetEntities.size()>0) {
					for(CreditInformationDetEntity creditInformationDetEntity : creditInformationDetEntities) {
						CreditLinkageInformationDTO creditLinkageInformationDTO = new CreditLinkageInformationDTO();
						BeanUtils.copyProperties(creditInformationDetEntity,creditLinkageInformationDTO);
						creditLinkageInformationDTOs.add(creditLinkageInformationDTO);
					}

				}
				List<FPOProfileFarmerSummaryDetEntity> fpoProfileFarmerSummaryDetEntities = fpoProfileFarmerSummaryDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(fpoProfileFarmerSummaryDetEntities.size()>0) {
					for(FPOProfileFarmerSummaryDetEntity fpoProfileFarmerSummaryDetEntity : fpoProfileFarmerSummaryDetEntities) {
						FPOProfileFarmerSummaryDto farmerSummaryDto = new FPOProfileFarmerSummaryDto();
						BeanUtils.copyProperties(fpoProfileFarmerSummaryDetEntity,farmerSummaryDto);
						farmerSummaryDtos.add(farmerSummaryDto);
					}

				}

				List<EquityInformationDetEntity> equityInformationDetEntities = equityInformationDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(equityInformationDetEntities.size()>0) {
					for(EquityInformationDetEntity equityInformationDetEntity : equityInformationDetEntities) {
						EquityInformationDetDto equityInformationDetDto = new EquityInformationDetDto();
						BeanUtils.copyProperties(equityInformationDetEntity,equityInformationDetDto);
						equityInformationDetDtos.add(equityInformationDetDto);
					}

				}

				List<ManagementCostDetEntity> managementCostDetEntities = managementCostDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(managementCostDetEntities.size()>0) {
					for(ManagementCostDetEntity managementCostDetEntity : managementCostDetEntities) {
						ManagementCostDetailDto managementCostDetailDto = new ManagementCostDetailDto();
						BeanUtils.copyProperties(managementCostDetEntity,managementCostDetailDto);
						managementCostDetailDtos.add(managementCostDetailDto);
					}

				}

				List<CreditGrantDetEntity> creditGrantDetEntities = creditGrandDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(creditGrantDetEntities.size()>0) {
					for(CreditGrantDetEntity creditGrantDetEntity : creditGrantDetEntities) {
						CreditGrantDetailDto creditGrantDetailDto = new CreditGrantDetailDto();
						BeanUtils.copyProperties(creditGrantDetEntity,creditGrantDetailDto);
						creditGrantDetailDtos.add(creditGrantDetailDto);
					}

				}

				List<FPOProfileTrainingDetEntity> fpoProfileTrainingDetEntities = fpoProfileTrainingDetEntityRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(fpoProfileTrainingDetEntities.size()>0) {
					for(FPOProfileTrainingDetEntity fpoProfileTrainingDetEntity : fpoProfileTrainingDetEntities) {
						FPOProfileTrainingDetailDto fpoProfileTrainingDetailDto = new FPOProfileTrainingDetailDto();
						BeanUtils.copyProperties(fpoProfileTrainingDetEntity,fpoProfileTrainingDetailDto);
						fpoProfileTrainingDetailDtos.add(fpoProfileTrainingDetailDto);
					}

				}

				////////////////Phase 2 tables....////////////////////////////////////////////////

				List<StrorageInfomartionDetailEntity> strorageInfomartionDetailEntities = strorageInfomartionDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(strorageInfomartionDetailEntities.size()>0) {
					for(StrorageInfomartionDetailEntity strorageInfomartionDetailEntity : strorageInfomartionDetailEntities) {
						StrorageInfomartionDTO strorageInfomartionDTO = new StrorageInfomartionDTO();
						BeanUtils.copyProperties(strorageInfomartionDetailEntity,strorageInfomartionDTO);
						strorageInfomartionDTOs.add(strorageInfomartionDTO);
					}

				}

				List<EquipmentInfoDetailEntity> equipmentInfoDetailEntities = equipmentInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(equipmentInfoDetailEntities.size()>0) {
					for(EquipmentInfoDetailEntity equipmentInfoDetailEntity : equipmentInfoDetailEntities) {
						EquipmentInfoDto equipmentInfoDto = new EquipmentInfoDto();
						BeanUtils.copyProperties(equipmentInfoDetailEntity,equipmentInfoDto);
						equipmentInfoDtos.add(equipmentInfoDto);
					}

				}

				List<CustomHiringServiceInfoDetailEntity> customHiringServiceInfoDetailEntities = customHiringServiceInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(customHiringServiceInfoDetailEntities.size()>0) {
					for(CustomHiringServiceInfoDetailEntity customHiringServiceInfoDetailEntity : customHiringServiceInfoDetailEntities) {
						CustomHiringServiceInfoDTO customHiringServiceInfoDTO = new CustomHiringServiceInfoDTO();
						BeanUtils.copyProperties(customHiringServiceInfoDetailEntity,customHiringServiceInfoDTO);
						customHiringServiceInfoDTOs.add(customHiringServiceInfoDTO);
					}

				}

				List<ProductionInfoDetailEntity> productionInfoDetailEntities = productionInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(productionInfoDetailEntities.size()>0) {
					for(ProductionInfoDetailEntity productionInfoDetailEntity : productionInfoDetailEntities) {
						ProductionInfoDTO productionInfoDTO = new ProductionInfoDTO();
						BeanUtils.copyProperties(productionInfoDetailEntity,productionInfoDTO);
						productionInfoDTOs.add(productionInfoDTO);
					}

				}

				List<SalesInfoDetailEntity> salesInfoDetailEntities = salesInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(salesInfoDetailEntities.size()>0) {
					for(SalesInfoDetailEntity salesInfoDetailEntity : salesInfoDetailEntities) {
						SalesInfoDTO salesInfoDTO = new SalesInfoDTO();
						BeanUtils.copyProperties(salesInfoDetailEntity,salesInfoDTO);
						salesInfoDTOs.add(salesInfoDTO);
					}

				}


				List<SubsidiesInfoDetailEntity> subsidiesInfoDetailEntities = subsidiesInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(subsidiesInfoDetailEntities.size()>0) {
					for(SubsidiesInfoDetailEntity subsidiesInfoDetailEntity : subsidiesInfoDetailEntities) {
						SubsidiesInfoDTO subsidiesInfoDTO = new SubsidiesInfoDTO();
						BeanUtils.copyProperties(subsidiesInfoDetailEntity,subsidiesInfoDTO);
						subsidiesInfoDTOs.add(subsidiesInfoDTO);
					}

				}

				List<PreHarveshInfraInfoDetailEntity> preHarveshInfraInfoDetailEntities = preHarveshInfraInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(preHarveshInfraInfoDetailEntities.size()>0) {
					for(PreHarveshInfraInfoDetailEntity preHarveshInfraInfoDetailEntity : preHarveshInfraInfoDetailEntities) {
						PreHarveshInfraInfoDTO preHarveshInfraInfoDTO = new PreHarveshInfraInfoDTO();
						BeanUtils.copyProperties(preHarveshInfraInfoDetailEntity,preHarveshInfraInfoDTO);
						preHarveshInfraInfoDTOs.add(preHarveshInfraInfoDTO);
					}

				}

				List<PostHarvestInfraInfoDetailEntity> postHarvestInfraInfoDetailEntities = postHarvestInfraInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(postHarvestInfraInfoDetailEntities.size()>0) {
					for(PostHarvestInfraInfoDetailEntity postHarvestInfraInfoDetailEntity : postHarvestInfraInfoDetailEntities) {
						PostHarvestInfraInfoDTO postHarvestInfraInfoDTO = new PostHarvestInfraInfoDTO();
						BeanUtils.copyProperties(postHarvestInfraInfoDetailEntity,postHarvestInfraInfoDTO);
						postHarvestInfraInfoDTOs.add(postHarvestInfraInfoDTO);
					}

				}

				List<TransportVehicleInfoDetailEntity> transportVehicleInfoDetailEntities = transportVehicleInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(transportVehicleInfoDetailEntities.size()>0) {
					for(TransportVehicleInfoDetailEntity transportVehicleInfoDetailEntity : transportVehicleInfoDetailEntities) {
						TransportVehicleInfoDTO transportVehicleInfoDTO = new TransportVehicleInfoDTO();
						BeanUtils.copyProperties(transportVehicleInfoDetailEntity,transportVehicleInfoDTO);
						transportVehicleInfoDTOs.add(transportVehicleInfoDTO);
					}

				}

				List<MarketLinkageInfoDetailEntity> marketLinkageInfoDetailEntities = marketLinkageInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(marketLinkageInfoDetailEntities.size()>0) {
					for(MarketLinkageInfoDetailEntity marketLinkageInfoDetailEntity : marketLinkageInfoDetailEntities) {
						MarketLinkageInfoDTO marketLinkageInfoDTO = new MarketLinkageInfoDTO();
						BeanUtils.copyProperties(marketLinkageInfoDetailEntity,marketLinkageInfoDTO);
						marketLinkageInfoDTOs.add(marketLinkageInfoDTO);
					}

				}

				List<BusinessPlanInfoDetailEntity> businessPlanInfoDetailEntities = businessPlanInfoDetRepository.findByFpoProfileMgmtMasterAndDocumentName(fpoProfileManagemntMaster,"A");
				if(businessPlanInfoDetailEntities.size()>0) {
					Long count = 100L;
					for(BusinessPlanInfoDetailEntity businessPlanInfoDetailEntity : businessPlanInfoDetailEntities) {
						BusinessPlanInfoDTO businessPlanInfoDTO = new BusinessPlanInfoDTO();
						BeanUtils.copyProperties(businessPlanInfoDetailEntity,businessPlanInfoDTO);
						List<String> identifer = new ArrayList<>();
						identifer.add("BP_ID" + MainetConstants.WINDOWS_SLASH + businessPlanInfoDetailEntity.getBpID());

						// get attached document
						final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
								.getBean(IAttachDocsService.class)
								.findByIdfInQuery(businessPlanInfoDetailEntity.getOrgId(), identifer);
						if (!attachDocs.isEmpty()) {
							newMap.put(count, attachDocs);

						}
						businessPlanInfoDTO.setAttachDocsListBP(attachDocs);

						businessPlanInfoDTOs.add(businessPlanInfoDTO);
						count++;
					}

				}else {
					BusinessPlanInfoDTO businessPlanInfoDTO = new BusinessPlanInfoDTO();
					businessPlanInfoDTO.setDocumentDescription("Business Plan 1");
					businessPlanInfoDTOs.add(businessPlanInfoDTO);
					businessPlanInfoDTO = new BusinessPlanInfoDTO();
					businessPlanInfoDTO.setDocumentDescription("Business Plan 2");
					businessPlanInfoDTOs.add(businessPlanInfoDTO);
				}

				List<AuditedBalanceSheetInfoDetailEntity> auditedBalanceSheetInfoDetailEntities = auditedBalanceSheetInfoDetRepository.findByFpoProfileMgmtMasterAndDocumentName(fpoProfileManagemntMaster, "A");
				if(auditedBalanceSheetInfoDetailEntities.size()>0) {
					Long count = 200L;
					for(AuditedBalanceSheetInfoDetailEntity auditedBalanceSheetInfoDetailEntity : auditedBalanceSheetInfoDetailEntities) {
						AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO = new AuditedBalanceSheetInfoDTO();
						BeanUtils.copyProperties(auditedBalanceSheetInfoDetailEntity,auditedBalanceSheetInfoDTO);
						List<String> identifer = new ArrayList<>();

						identifer.add("ABS_ID" + MainetConstants.WINDOWS_SLASH + auditedBalanceSheetInfoDetailEntity.getAuditedBalanceSheetId());

						// get attached document
						final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
								.getBean(IAttachDocsService.class)
								.findByIdfInQuery(auditedBalanceSheetInfoDetailEntity.getOrgId(), identifer);
						if (!attachDocs.isEmpty()) {
							newMap.put(count, attachDocs);
						}
						auditedBalanceSheetInfoDTO.setAttachDocsListABS(attachDocs);

						auditedBalanceSheetInfoDTOs.add(auditedBalanceSheetInfoDTO);
						count++;
					}

				}

				List<DPRInfoDetEntity> dprInfoDetEntities = dprInfoDetRepository.findByFpoProfileMgmtMaster(fpoProfileManagemntMaster);
				if(dprInfoDetEntities.size()>0) {
					for(DPRInfoDetEntity dprInfoDetEntity : dprInfoDetEntities) {
						DPRInfoDTO dprInfoDTO = new DPRInfoDTO();
						BeanUtils.copyProperties(dprInfoDetEntity,dprInfoDTO);


						dprInfoDTOs.add(dprInfoDTO);
					}

				}

				//////////////////////////END //////////////////////////////////////////////////


				fpoProfileMasterDto.setFinancialInformationDto(financialInformationDtos);
				fpoProfileMasterDto.setLicenseInformationDetEntities(licenseInformationDTOs);
				fpoProfileMasterDto.setCreditInformationDetEntities(creditLinkageInformationDTOs);
				fpoProfileMasterDto.setFarmerSummaryDto(farmerSummaryDtos);
				fpoProfileMasterDto.setEquityInformationDetDto(equityInformationDetDtos);
				fpoProfileMasterDto.setManagementCostDetailDto(managementCostDetailDtos);
				fpoProfileMasterDto.setCreditGrantDetailDto(creditGrantDetailDtos);
				fpoProfileMasterDto.setFarmerSummaryDto(farmerSummaryDtos);
				fpoProfileMasterDto.setFpoProfileTrainingDetailDto(fpoProfileTrainingDetailDtos);

				/////////////Phase 2 ////////////////////////////////////////

				fpoProfileMasterDto.setStrorageInfomartionDTOs(strorageInfomartionDTOs);
				//fpoProfileMasterDto.setCustomHiringCenterInfoDTOs(customHiringCenterInfoDTOs);
				fpoProfileMasterDto.setEquipmentInfoDtos(equipmentInfoDtos);
				fpoProfileMasterDto.setCustomHiringServiceInfoDTOs(customHiringServiceInfoDTOs);
				fpoProfileMasterDto.setProductionInfoDTOs(productionInfoDTOs);
				fpoProfileMasterDto.setSalesInfoDTOs(salesInfoDTOs);
				fpoProfileMasterDto.setSubsidiesInfoDTOs(subsidiesInfoDTOs);
				fpoProfileMasterDto.setPreHarveshInfraInfoDTOs(preHarveshInfraInfoDTOs);
				fpoProfileMasterDto.setPostHarvestInfraInfoDTOs(postHarvestInfraInfoDTOs);
				fpoProfileMasterDto.setTranspostVehicleInfoDTOs(transportVehicleInfoDTOs);
				fpoProfileMasterDto.setMarketLinkageInfoDTOs(marketLinkageInfoDTOs);
				fpoProfileMasterDto.setBusinessPlanInfoDTOs(businessPlanInfoDTOs);
				fpoProfileMasterDto.setAuditedBalanceSheetInfoDetailEntities(auditedBalanceSheetInfoDTOs);
				fpoProfileMasterDto.setDprInfoDTOs(dprInfoDTOs);

				FileUploadUtility.getCurrent().setFileMap(
						getUploadedFileList(newMap, FileNetApplicationClient.getInstance()));


				/////////////End ///////////////////////////////////////

			}
		}
		return fpoProfileMasterDto;

	}

	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
				requestDTO);
	}

	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}
	}

	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateLicenseInfo(FPOProfileMasterDto mastDto, List<Long> removeIds) {
		try {
			logger.info("saveAndUpdateApplication started");
			List<LicenseInformationDetEntity> licenseInformationDetEntities =  licenseInformationDetRepository.save(mapDtoToLicenseEntity(mastDto));

			if (removeIds != null && !removeIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				licenseInformationDetRepository.deActiveLicenseInfo(removeIds,
						UserSession.getCurrent().getEmployee().getEmpId());
			}


			int count = 0;
			int attCount = 0;
			int newRecord = mastDto.getLicenseInformationDetEntities().size();//(int) mastDto.getLicenseInformationDetEntities().stream().filter(e -> Objects.isNull(e.getLicId())).count();
			int newRecordN = (int) mastDto.getLicenseInformationDetEntities().stream().filter(e -> Objects.isNull(e.getLicId())).count();
			logger.info("*********************************************************  "+newRecord + " ***************** "+ newRecordN);
			for (LicenseInformationDetEntity licenseInformationDetEntity : licenseInformationDetEntities) {
				List<DocumentDetailsVO> attach = new ArrayList<>();

				if(newRecord == mastDto.getLicenseInformationDetEntities().get(count).getAttachmentsLi().size()) {

					attach.add(mastDto.getLicenseInformationDetEntities().get(count).getAttachmentsLi().get(attCount));

					attCount++;
				}else {

					attach.add(mastDto.getLicenseInformationDetEntities().get(count).getAttachmentsLi().get(count));
				}
				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(licenseInformationDetEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("FPM_LD" + MainetConstants.WINDOWS_SLASH + licenseInformationDetEntity.getLicId());
				requestDTO.setUserId(licenseInformationDetEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);
				attCount++;

				count++;
				// history table update

			}
			for(LicenseInformationDTO licenseInformationDTO :  mastDto.getLicenseInformationDetEntities())
				updateLegacyDocuments(licenseInformationDTO.getAttachDocsListLi(), UserSession.getCurrent().getEmployee().getEmpId());


		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");

		return mastDto;
	}

	private List<LicenseInformationDetEntity> mapDtoToLicenseEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<LicenseInformationDetEntity> licenseInformationDetEntities = new ArrayList<>();

		mastDto.getLicenseInformationDetEntities().forEach(dto -> {

			LicenseInformationDetEntity entity = new LicenseInformationDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			licenseInformationDetEntities.add(entity);

		});


		return licenseInformationDetEntities;
	}


	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateABSInfo(FPOProfileMasterDto mastDto, List<Long> removeIds) {
		try {
			logger.info("saveAndUpdateApplication started");
			List<AuditedBalanceSheetInfoDetailEntity> auditedBalanceSheetInfoDetailEntities =  auditedBalanceSheetInfoDetRepository.save(mapDtoToABSEntity(mastDto));

			if (removeIds != null && !removeIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				auditedBalanceSheetInfoDetRepository.deActiveABSInfo(removeIds,
						UserSession.getCurrent().getEmployee().getEmpId());
			}

			int count = 0;
			int attCount = 0;
			int countL = mastDto.getLicenseInformationDetEntities().size() + mastDto.getBusinessPlanInfoDTOs().size();

			int newRecord = mastDto.getAuditedBalanceSheetInfoDetailEntities().size();//(int) mastDto.getAuditedBalanceSheetInfoDetailEntities().stream().filter(e -> Objects.isNull(e.getAuditedBalanceSheetId())).count();

			int newRecordN = (int) mastDto.getAuditedBalanceSheetInfoDetailEntities().stream().filter(e -> Objects.isNull(e.getAuditedBalanceSheetId())).count();
			logger.info("*********************************************************  "+newRecord + " ***************** "+ newRecordN);
			for (AuditedBalanceSheetInfoDetailEntity auditedBalanceSheetInfoDetailEntity : auditedBalanceSheetInfoDetailEntities) {

				List<DocumentDetailsVO> attach = new ArrayList<>();
				if(newRecord == mastDto.getAuditedBalanceSheetInfoDetailEntities().get(count).getAttachmentsABS().size()) {

					attach.add(mastDto.getAuditedBalanceSheetInfoDetailEntities().get(count).getAttachmentsABS().get(attCount));
					attCount++;

				}else {
					attach.add(mastDto.getAuditedBalanceSheetInfoDetailEntities().get(count).getAttachmentsABS().get(countL));
					countL++;
				}
				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(auditedBalanceSheetInfoDetailEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("ABS_ID" + MainetConstants.WINDOWS_SLASH + auditedBalanceSheetInfoDetailEntity.getAuditedBalanceSheetId());
				requestDTO.setUserId(auditedBalanceSheetInfoDetailEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);

				count++;

				// history table update

			}
			for(AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO :  mastDto.getAuditedBalanceSheetInfoDetailEntities())
				updateLegacyDocuments(auditedBalanceSheetInfoDTO.getAttachDocsListABS(), UserSession.getCurrent().getEmployee().getEmpId());


		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");

		return mastDto;
	}

	private List<AuditedBalanceSheetInfoDetailEntity> mapDtoToABSEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<AuditedBalanceSheetInfoDetailEntity> auditedBalanceSheetInfoDetailEntities = new ArrayList<>();

		mastDto.getAuditedBalanceSheetInfoDetailEntities().forEach(dto -> {

			AuditedBalanceSheetInfoDetailEntity entity = new AuditedBalanceSheetInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			auditedBalanceSheetInfoDetailEntities.add(entity);

		});


		return auditedBalanceSheetInfoDetailEntities;
	}

	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateCreditInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			creditInformationDetRepository.save(mapDtoToCreditEntity(mastDto));


		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<CreditInformationDetEntity> mapDtoToCreditEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<CreditInformationDetEntity> creditInformationDetEntities = new ArrayList<>();

		mastDto.getCreditInformationDetEntities().forEach(dto -> {

			CreditInformationDetEntity entity = new CreditInformationDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			creditInformationDetEntities.add(entity);

		});


		return creditInformationDetEntities;
	}

	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateEquityInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			equityInformationDetRepository.save(mapDtoToEquityEntity(mastDto));


		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<EquityInformationDetEntity> mapDtoToEquityEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<EquityInformationDetEntity> equityInformationDetEntities = new ArrayList<>();

		mastDto.getEquityInformationDetDto().forEach(dto -> {

			EquityInformationDetEntity entity = new EquityInformationDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			equityInformationDetEntities.add(entity);

		});
		return equityInformationDetEntities;
	}

	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateFarmerSummary(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			fpoProfileFarmerSummaryDetRepository.save(mapDtoToFarmerSummaryEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo master  details" + e);
			throw new FrameworkException("error occured while saving fpo master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<FPOProfileFarmerSummaryDetEntity> mapDtoToFarmerSummaryEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<FPOProfileFarmerSummaryDetEntity> farmerSummaryDetEntities = new ArrayList<>();


		mastDto.getFarmerSummaryDto().forEach(dto -> {

			FPOProfileFarmerSummaryDetEntity entity = new FPOProfileFarmerSummaryDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			farmerSummaryDetEntities.add(entity);

		});

		return farmerSummaryDetEntities;
	}

	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateCreditGrand(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			creditGrandDetRepository.save(mapDtoToCreditGrandEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<CreditGrantDetEntity> mapDtoToCreditGrandEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<CreditGrantDetEntity> creditGrantDetEntities = new ArrayList<>();
		mastDto.getCreditGrantDetailDto().forEach(dto -> {

			CreditGrantDetEntity entity = new CreditGrantDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			creditGrantDetEntities.add(entity);

		});

		return creditGrantDetEntities;
	}

	@Transactional
	@Override
	public FPOProfileMasterDto saveAndUpdateTraningInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			fpoProfileTrainingDetEntityRepository.save(mapDtoToTrainingEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<FPOProfileTrainingDetEntity> mapDtoToTrainingEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<FPOProfileTrainingDetEntity> trainingDetEntities = new ArrayList<>();

		mastDto.getFpoProfileTrainingDetailDto().forEach(dto -> {

			FPOProfileTrainingDetEntity entity = new FPOProfileTrainingDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			trainingDetEntities.add(entity);

		});

		return trainingDetEntities;

	}

	@Override
	public FPOProfileMasterDto saveAndUpdateManagementInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			managementCostDetRepository.save(mapDtoToManagementCostEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<ManagementCostDetEntity> mapDtoToManagementCostEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<ManagementCostDetEntity> managementCostDetEntities = new ArrayList<>();

		mastDto.getManagementCostDetailDto().forEach(dto -> {

			ManagementCostDetEntity entity = new ManagementCostDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFPOProfileMgmtMaster(masterEntity);
			managementCostDetEntities.add(entity);

		});

		return managementCostDetEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateStorageInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			strorageInfomartionDetRepository.save(mapDtoToStorageInfoEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<StrorageInfomartionDetailEntity> mapDtoToStorageInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<StrorageInfomartionDetailEntity> strorageInfomartionDetailEntities = new ArrayList<>();
		mastDto.getStrorageInfomartionDTOs().forEach(dto -> {

			StrorageInfomartionDetailEntity entity = new StrorageInfomartionDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			strorageInfomartionDetailEntities.add(entity);

		});

		return strorageInfomartionDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateCustomInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			equipmentInfoDetRepository.save(mapDtoToCustomInfoEquipmentEntity(mastDto));
			customHiringServiceInfoDetRepository.save(mapDtoToCustomInfoServiceEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<EquipmentInfoDetailEntity> mapDtoToCustomInfoEquipmentEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<EquipmentInfoDetailEntity> equipmentInfoDetailEntities = new ArrayList<>();
		mastDto.getEquipmentInfoDtos().forEach(dto -> {

			EquipmentInfoDetailEntity entity = new EquipmentInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			equipmentInfoDetailEntities.add(entity);

		});

		return equipmentInfoDetailEntities;
	}

	private List<CustomHiringCenterInfoDetailEntity> mapDtoToCustomInfoCenterEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<CustomHiringCenterInfoDetailEntity> customHiringCenterInfoDetailEntities = new ArrayList<>();
		mastDto.getCustomHiringCenterInfoDTOs().forEach(dto -> {

			CustomHiringCenterInfoDetailEntity entity = new CustomHiringCenterInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			customHiringCenterInfoDetailEntities.add(entity);

		});

		return customHiringCenterInfoDetailEntities;
	}

	private List<CustomHiringServiceInfoDetailEntity> mapDtoToCustomInfoServiceEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<CustomHiringServiceInfoDetailEntity> customHiringServiceInfoDetailEntities = new ArrayList<>();
		mastDto.getCustomHiringServiceInfoDTOs().forEach(dto -> {

			CustomHiringServiceInfoDetailEntity entity = new CustomHiringServiceInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			customHiringServiceInfoDetailEntities.add(entity);

		});

		return customHiringServiceInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdatePNSInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			productionInfoDetRepository.save(mapDtoToProductionInfoEntity(mastDto));
			salesInfoDetRepository.save(mapDtoToSaleInfoEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<ProductionInfoDetailEntity> mapDtoToProductionInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<ProductionInfoDetailEntity> productionInfoDetailEntities = new ArrayList<>();
		mastDto.getProductionInfoDTOs().forEach(dto -> {

			ProductionInfoDetailEntity entity = new ProductionInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			productionInfoDetailEntities.add(entity);

		});

		return productionInfoDetailEntities;
	}

	private List<SalesInfoDetailEntity> mapDtoToSaleInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<SalesInfoDetailEntity> salesInfoDetailEntities = new ArrayList<>();
		mastDto.getSalesInfoDTOs().forEach(dto -> {

			SalesInfoDetailEntity entity = new SalesInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			salesInfoDetailEntities.add(entity);

		});

		return salesInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateSubsidiesInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			subsidiesInfoDetRepository.save(mapDtoToSubsidiesInfoEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<SubsidiesInfoDetailEntity> mapDtoToSubsidiesInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<SubsidiesInfoDetailEntity> subsidiesInfoDetailEntities = new ArrayList<>();
		mastDto.getSubsidiesInfoDTOs().forEach(dto -> {

			SubsidiesInfoDetailEntity entity = new SubsidiesInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			subsidiesInfoDetailEntities.add(entity);

		});

		return subsidiesInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdatePreharveshInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			preHarveshInfraInfoDetRepository.save(mapDtoToPreharveshInfoEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<PreHarveshInfraInfoDetailEntity> mapDtoToPreharveshInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<PreHarveshInfraInfoDetailEntity> preHarveshInfraInfoDetailEntities = new ArrayList<>();
		mastDto.getPreHarveshInfraInfoDTOs().forEach(dto -> {

			PreHarveshInfraInfoDetailEntity entity = new PreHarveshInfraInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			preHarveshInfraInfoDetailEntities.add(entity);

		});

		return preHarveshInfraInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdatePostharvestInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			postHarvestInfraInfoDetRepository.save(mapDtoToPostharvestInfoEntity(mastDto));

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<PostHarvestInfraInfoDetailEntity> mapDtoToPostharvestInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<PostHarvestInfraInfoDetailEntity> postHarvestInfraInfoDetailEntities = new ArrayList<>();
		mastDto.getPostHarvestInfraInfoDTOs().forEach(dto -> {

			PostHarvestInfraInfoDetailEntity entity = new PostHarvestInfraInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			postHarvestInfraInfoDetailEntities.add(entity);

		});

		return postHarvestInfraInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateTransportVehicleInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			List<TransportVehicleInfoDetailEntity> transportVehicleInfoDetailEntities = mapDtoToTVInfoEntity(mastDto);
			transportVehicleInfoDetRepository.save(transportVehicleInfoDetailEntities);

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<TransportVehicleInfoDetailEntity> mapDtoToTVInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<TransportVehicleInfoDetailEntity> transportVehicleInfoDetailEntities = new ArrayList<>();
		mastDto.getTranspostVehicleInfoDTOs().forEach(dto -> {

			TransportVehicleInfoDetailEntity entity = new TransportVehicleInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			transportVehicleInfoDetailEntities.add(entity);

		});

		return transportVehicleInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateMLInfo(FPOProfileMasterDto mastDto, FPOMasterDto fpoMasterDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			marketLinkageInfoDetRepository.save(mapDtoToMLInfoEntity(mastDto));

			FPOMasterEntity fpoMasterEntity = fpoMasterRepository.findOne(fpoMasterDto.getFpoId());
			if(fpoMasterDto.getRegisteredOnEnam()!=null && fpoMasterDto.getRegisteredOnEnam()!=0) {
				fpoMasterEntity.setRegisteredOnEnam(fpoMasterDto.getRegisteredOnEnam());
				if(fpoMasterDto.getUserIdEnam()!=null && !fpoMasterDto.getUserIdEnam().isEmpty())
					fpoMasterEntity.setUserIdEnam(fpoMasterDto.getUserIdEnam());
				fpoMasterRepository.save(fpoMasterEntity);
			}

		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<MarketLinkageInfoDetailEntity> mapDtoToMLInfoEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<MarketLinkageInfoDetailEntity> marketLinkageInfoDetailEntities = new ArrayList<>();
		mastDto.getMarketLinkageInfoDTOs().forEach(dto -> {

			MarketLinkageInfoDetailEntity entity = new MarketLinkageInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			marketLinkageInfoDetailEntities.add(entity);

		});

		return marketLinkageInfoDetailEntities;
	}

	public Map<Long, Set<File>> getUploadedFileList(Map<Long, List<AttachDocs>> newMap,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;


		Map<Long, Set<File>> fileMap = new HashMap<>();
		for (Map.Entry<Long,List<AttachDocs>> entry : newMap.entrySet()) {
			for (AttachDocs doc : entry.getValue()) {
				fileList = new HashSet<>();
				final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
						+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
				String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
				final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);

				String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);

				directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
				FileOutputStream fos = null;
				File file = null;
				try {
					final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

					Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

					file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

					fos = new FileOutputStream(file);

					fos.write(image);

					fos.close();

				} catch (final Exception e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				} finally {
					try {

						if (fos != null) {
							fos.close();
						}

					} catch (final IOException e) {
						throw new FrameworkException("Exception in getting getUploadedFileList", e);
					}
				}
				fileList.add(file);
				fileMap.put(entry.getKey(), fileList);
			}
		}

		return fileMap;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateBPInfo(FPOProfileMasterDto mastDto, List<Long> removeIds) {
		try {
			logger.info("saveAndUpdateApplication started");
			List<BusinessPlanInfoDetailEntity> businessPlanInfoDetailEntities =  businessPlanInfoDetRepository.save(mapDtoToBPEntity(mastDto));

			if (removeIds != null && !removeIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				businessPlanInfoDetRepository.deActiveBPInfo(removeIds,
						UserSession.getCurrent().getEmployee().getEmpId());
			}

			int count = 0;
			int attCount = 0;
			int countL = mastDto.getLicenseInformationDetEntities().size();
			int newRecord = mastDto.getBusinessPlanInfoDTOs().size();//(int) mastDto.getBusinessPlanInfoDTOs().stream().filter(e -> Objects.isNull(e.getBpID())).count();
			int newRecordN = (int) mastDto.getBusinessPlanInfoDTOs().stream().filter(e -> Objects.isNull(e.getBpID())).count();
			logger.info("*********************************************************  "+newRecord + " ***************** "+ newRecordN);
			for (BusinessPlanInfoDetailEntity businessPlanInfoDetailEntity : businessPlanInfoDetailEntities) {

				List<DocumentDetailsVO> attach = new ArrayList<>();
				if(newRecord == mastDto.getBusinessPlanInfoDTOs().get(count).getAttachmentsBP().size()) {

					attach.add(mastDto.getBusinessPlanInfoDTOs().get(count).getAttachmentsBP().get(attCount));
					attCount++;

				}else {

					attach.add(mastDto.getBusinessPlanInfoDTOs().get(count).getAttachmentsBP().get(countL));
				}
				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(businessPlanInfoDetailEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("BP_ID" + MainetConstants.WINDOWS_SLASH + businessPlanInfoDetailEntity.getBpID());
				requestDTO.setUserId(businessPlanInfoDetailEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);

				count++;
				countL++;

				// history table update

			}
			for(BusinessPlanInfoDTO businessPlanInfoDTO :  mastDto.getBusinessPlanInfoDTOs())
				updateLegacyDocuments(businessPlanInfoDTO.getAttachDocsListBP(), UserSession.getCurrent().getEmployee().getEmpId());


		} catch (Exception e) {
			logger.error("error occured while saving fpo profile master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");

		return mastDto;
	}

	private List<BusinessPlanInfoDetailEntity> mapDtoToBPEntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<BusinessPlanInfoDetailEntity> businessPlanInfoDetailEntities = new ArrayList<>();

		mastDto.getBusinessPlanInfoDTOs().forEach(dto -> {

			BusinessPlanInfoDetailEntity entity = new BusinessPlanInfoDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			businessPlanInfoDetailEntities.add(entity);

		});


		return businessPlanInfoDetailEntities;
	}

	@Override
	public FPOProfileMasterDto saveAndUpdateDPRInfo(FPOProfileMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			dprInfoDetRepository.save(mapDtoToDPREntity(mastDto));


		} catch (Exception e) {
			logger.error("error occured while saving fpo master  details" + e);
			throw new FrameworkException("error occured while saving fpo profile master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<DPRInfoDetEntity> mapDtoToDPREntity(FPOProfileMasterDto mastDto) {
		// TODO Auto-generated method stub
		FPOProfileManagementMaster masterEntity = fpoProfileMasterRepository.findOne(mastDto.getFpmId());
		List<DPRInfoDetEntity> dprInfoDetEntities = new ArrayList<>();

		mastDto.getDprInfoDTOs().forEach(dto -> {

			DPRInfoDetEntity entity = new DPRInfoDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoProfileMgmtMaster(masterEntity);
			dprInfoDetEntities.add(entity);

		});


		return dprInfoDetEntities;
	}

	@Override
	public FPOMasterDto getFPODetails(Long masId) {
		FPOMasterEntity fpoMasterEntity = fpoMasterRepository.findOne(masId);
		FPOMasterDto fpoMasterDto = new FPOMasterDto();
		BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);
		return fpoMasterDto;
	}
}
