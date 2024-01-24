/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.dao.AllocationOfBlocksDao;
import com.abm.mainet.sfac.domain.BlockAllocationDetailEntity;
import com.abm.mainet.sfac.domain.BlockAllocationDetailHist;
import com.abm.mainet.sfac.domain.BlockAllocationEntity;
import com.abm.mainet.sfac.domain.BlockAllocationHist;
import com.abm.mainet.sfac.domain.BlockTargetDetEntity;
import com.abm.mainet.sfac.domain.BlockTargetDetHist;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.dto.BlockTargetDetDto;
import com.abm.mainet.sfac.repository.AllocationOfBlocksHistRepository;
import com.abm.mainet.sfac.repository.AllocationOfBlocksRepository;
import com.abm.mainet.sfac.repository.BlockAllocationDetailRepository;
import com.abm.mainet.sfac.repository.BlockTargetDetRepository;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.ui.model.AllocationOfBlocksModel;
import com.abm.mainet.sfac.ui.model.ChangeofBlockApprovalModel;
import com.abm.mainet.sfac.ui.model.ChangeofBlockModel;

/**
 * @author pooja.maske
 *
 */
@Service

public class AllocationOfBlocksServiceImpl implements AllocationOfBlocksService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#saveBlockDetails(java.
	 * util.List, com.abm.mainet.sfac.ui.model.AllocationOfBlocksModel)
	 */
	private static final Logger logger = Logger.getLogger(AllocationOfBlocksServiceImpl.class);

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	IAttachDocsDao iAttachDocsDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private AllocationOfBlocksRepository allocationOfBlocksRepository;

	@Autowired
	private DepartmentService deptService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private AllocationOfBlocksDao allocationOfBlocksDao;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	AllocationOfBlocksHistRepository allocationOfBlocksHistRepo;

	@Autowired
	AuditService auditService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private CBBOMasterRepository cbboMasterRepository;

	@Autowired
	private BlockAllocationDetailRepository blockDetRepo;
	@Autowired
	BlockTargetDetRepository targetDetRepo;

	/*
	 * @Override
	 * 
	 * @Transactional public BlockAllocationDto
	 * saveBlockDetails(List<BlockAllocationDto> blockAllocationDtoList,
	 * AllocationOfBlocksModel allocationOfBlocksModel) {
	 * List<BlockAllocationEntity> entityList = new
	 * ArrayList<BlockAllocationEntity>(); try { for (BlockAllocationDto dto :
	 * blockAllocationDtoList) { BlockAllocationEntity entity = new
	 * BlockAllocationEntity(); BeanUtils.copyProperties(dto, entity);
	 * entity.setOrganizationNameId(allocationOfBlocksModel.getBlockAllocationDto().
	 * getOrganizationNameId()); //
	 * entity.setAllocationYearId(allocationOfBlocksModel.getBlockAllocationDto().
	 * getAllocationYearId());
	 * entity.setOrgTypeId(allocationOfBlocksModel.getBlockAllocationDto().
	 * getOrgTypeId()); //
	 * entity.setAllocationTarget(allocationOfBlocksModel.getBlockAllocationDto().
	 * getAllocationTarget()); entityList.add(entity);
	 * allocationOfBlocksRepository.save(entityList); saveHistoryData(entity); } }
	 * catch (Exception e) { logger.error("Error Occured while saving block details"
	 * + e); throw new
	 * FrameworkException("Exception occur while saving block details", e); } return
	 * allocationOfBlocksModel.getBlockAllocationDto();
	 * 
	 * }
	 */

	/**
	 * @param entityList
	 */
	@Transactional
	private void saveHistoryData(BlockAllocationEntity entity) {
		// save history
		BlockAllocationHist histEn = new BlockAllocationHist();
		BeanUtils.copyProperties(entity, histEn);
		histEn.setHistoryStatus(MainetConstants.FlagA);

		List<BlockAllocationDetailHist> detEntityList = new ArrayList<>();
		List<BlockTargetDetHist> targetDetHist = new ArrayList<>();
		entity.getDetailEntity().forEach(detEntiy -> {
			BlockAllocationDetailHist hist = new BlockAllocationDetailHist();
			BeanUtils.copyProperties(detEntiy, hist);
			hist.setStatus(MainetConstants.FlagA);
			hist.setBlockAllocationHist(histEn);
			if (detEntiy.getBdId() != null)
				detEntityList.add(hist);
		});
		entity.getTargetDetEntity().forEach(target -> {
			BlockTargetDetHist hist = new BlockTargetDetHist();
			BeanUtils.copyProperties(target, hist);
			hist.setStatus(MainetConstants.FlagA);
			hist.setBlockAllocationHist(histEn);
			targetDetHist.add(hist);
		});
		histEn.setBlockDetHist(detEntityList);
		histEn.setTargetDetHist(targetDetHist);
		allocationOfBlocksHistRepo.save(histEn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#getBlockDetailsByIds(
	 * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<BlockAllocationDto> getBlockDetailsByIds(Long orgTypeId, Long organizationNameId, Long allocationYearId,
			Long orgId,Long sdb1,Long sdb2, Long sdb3) {
		List<BlockAllocationEntity> blockEntityList = allocationOfBlocksDao.getBlockDetailsByIds(orgTypeId,
				organizationNameId, allocationYearId,sdb1,sdb2,sdb3);
		List<BlockAllocationDto> dtoList = new ArrayList<>();
		for (BlockAllocationEntity entity : blockEntityList) {
			if (entity != null) {
				BlockAllocationDto dto = new BlockAllocationDto();
				BeanUtils.copyProperties(entity, dto);
				Long sum = entity.getTargetDetEntity().stream().mapToLong(BlockTargetDetEntity::getAllocationTarget)
						.sum();
				dto.setNoofBlockAssigned(sum);
				String orgName = iaMasterService.fetchNameById(entity.getOrgTypeId(), entity.getOrganizationNameId());
				if (orgName != null)
					dto.setOrgName(orgName);
				if (entity.getAllocationYearId() != null) {
					TbFinancialyear financialYear = financialyearService.findYearById(entity.getAllocationYearId(),
							entity.getOrgId());
					try {
						dto.setAlcYear(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
					} catch (Exception e) {
						logger.error("Error occured while fetching financial year " + e);
					}
				}
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public List<BlockAllocationDto> getAllBlockDetailsByStateId(Long orgTypeId, Long orgNameId, Long allYearId,
			Long orgId) {
		List<BlockAllocationEntity> blockEntityList = allocationOfBlocksRepository
				.getAllBlockDetailsByStateId(orgTypeId, orgNameId, allYearId, orgId);
		List<BlockAllocationDto> dtoList = new ArrayList<>();
		for (BlockAllocationEntity entity : blockEntityList) {
			BlockAllocationDto dto = new BlockAllocationDto();
			BeanUtils.copyProperties(entity, dto);
			/*
			 * LookUp state = CommonMasterUtility.getHierarchicalLookUp(entity.getSdb1());
			 * LookUp district =
			 * CommonMasterUtility.getHierarchicalLookUp(entity.getSdb2()); LookUp block =
			 * CommonMasterUtility.getHierarchicalLookUp(entity.getSdb3()); if
			 * (UserSession.getCurrent().getLanguageId() ==
			 * MainetConstants.DEFAULT_LANGUAGE_ID) {
			 * dto.setState(state.getDescLangFirst());
			 * dto.setDistrict(district.getDescLangFirst());
			 * dto.setBlock(block.getDescLangFirst()); } else {
			 * dto.setState(state.getDescLangSecond());
			 * dto.setDistrict(district.getDescLangSecond());
			 * dto.setBlock(block.getDescLangSecond()); }
			 */
			String orgName = deptService.fetchDepartmentDescEngById(entity.getOrganizationNameId(),
					UserSession.getCurrent().getLanguageId());
			dto.setOrgName(orgName);
			/*
			 * TbFinancialyear financialYear =
			 * financialyearService.findYearById(entity.getAllocationYearId(), orgId); try {
			 * dto.setAlcYear(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()
			 * )); } catch (Exception e) {
			 * logger.error("Error occured while fetching financial year " + e); }
			 */
			dtoList.add(dto);
		}
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#getBlockByOrgId(java.
	 * lang.Long)
	 */
	@Override
	public List<BlockAllocationDto> getCBBOBlockListByOrgId(Long orgId, Long orgTypeId) {
		List<BlockAllocationDto> dtoList = new ArrayList<>();
		try {
			logger.info("getCBBOBlockListByOrgId started");
			List<BlockAllocationEntity> blockEntityList = allocationOfBlocksRepository.fetchAllBlocks(orgId, orgTypeId);
			for (BlockAllocationEntity entity : blockEntityList) {
				BlockAllocationDto dto = new BlockAllocationDto();
				BeanUtils.copyProperties(entity, dto);
				String orgName = deptService.fetchDepartmentDescEngById(entity.getOrganizationNameId(),
						UserSession.getCurrent().getLanguageId());
				dto.setOrgName(orgName);
				/*
				 * TbFinancialyear financialYear =
				 * financialyearService.findYearById(entity.getAllocationYearId(), orgId); try {
				 * dto.setAlcYear(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()
				 * )); } catch (Exception e) {
				 * logger.error("Error occured while fetching financial year " + e); }
				 */
				dtoList.add(dto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching CBBO Block List By OrgId  " + e);
		}
		logger.info("getCBBOBlockListByOrgId ended");
		return dtoList;
	}

	@Override
	public List<LookUp> getNotAllocatedBlockList(Long distId, Long orgId) {
		List<LookUp> lookUpList = new ArrayList<>();
		List<LookUp> finalLookUpList = new ArrayList<>();
		List<Long> blockList = allocationOfBlocksRepository.getAllocatedBlockList(orgId);
		List<LookUp> lookup = CommonMasterUtility.getNextLevelData("SDB", 3, orgId);
		lookUpList = lookup.stream().filter(mo -> !(blockList.contains(mo.getLookUpId()))).collect(Collectors.toList());
		for (LookUp lookUp2 : lookUpList) {
			if (lookUp2.getLookUpParentId() == distId)
				finalLookUpList.add(lookUp2);
		}
		return finalLookUpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#updateChangedBlock(com.
	 * abm.mainet.sfac.dto.BlockAllocationDto, java.util.List)
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public BlockAllocationDto saveChangedBlockDetails(BlockAllocationDto masDto, BlockAllocationDto newlyAllocatedBlock,
			ChangeofBlockModel model) {
		BlockAllocationEntity entity = new BlockAllocationEntity();
		Long applicationId = null;
		try {
			logger.info("saveAndUpdateApplication End");
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.CBR,
					masDto.getOrgId());
			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				masDto.setApplicationId(applicationId);

			if ((masDto.getDocumentList() != null) && !masDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(applicationId);
				fileUploadService.doFileUpload(masDto.getDocumentList(), requestDto);
			}

			/*
			 * for (BlockAllocationDetailDto dto : masDto.getBlockDetailDto()) {
			 * dto.setApplicationId(applicationId); dto.setOrgId(dto.getOrgId());
			 * allocationOfBlocksRepository.updateStatusAndApplId(dto.getBlckId(),
			 * entity.getApplicationId(), dto.getStatus(), dto.getOrgId());
			 * allocationOfBlocksHistRepo.updateStatusAndApplId(dto.getBlckId(),
			 * entity.getApplicationId(), dto.getStatus(),dto.getOrgId()); }
			 */
			if (newlyAllocatedBlock != null && CollectionUtils.isNotEmpty(newlyAllocatedBlock.getBlockDetailDto())) {
				newlyAllocatedBlock.getBlockDetailDto().forEach(d -> {
					if (d.getStateId() != null && d.getStateId() != 0)
						masDto.getBlockDetailDto().addAll(newlyAllocatedBlock.getBlockDetailDto());
				});
			}
			masDto.getBlockDetailDto().forEach(detDto -> {
				detDto.setApplicationId(masDto.getApplicationId());
			});
			masDto.getTargetDetDto().forEach(targetDto -> {
				targetDto.setApplicationId(masDto.getApplicationId());
			});
			entity = mapDtoToEntity(masDto);
			allocationOfBlocksRepository.save(entity);
			saveHistoryData(entity);

			logger.info("saveAndUpdateApplication End");

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(masDto.getApplicationId());
				applicationData.setOrgId(masDto.getOrgId());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				masDto.getApplicantDetailDto().setUserId(masDto.getCreatedBy());
				masDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				masDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				if (requestDto != null && requestDto.getMobileNo() != null) {
					masDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, masDto.getApplicantDetailDto());
			}

		} catch (Exception e) {
			logger.error("Error Occured while saving block details" + e);
			throw new FrameworkException("Exception occur while saving block details", e);
		}
		return masDto;

	}

	/**
	 * @param masDto
	 * @param sm
	 * @return
	 */
	private RequestDTO setApplicantRequestDto(BlockAllocationDto masDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());
		requestDto.setOrgId(masDto.getOrgId());
		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(masDto.getOrgId());
		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		requestDto.setEmail(masDto.getEmail());
		requestDto.setMobileNo(masDto.getMobileNo());
		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.AllocationOfBlocksService#
	 * fetchBlockDetailsbyAppId(java.lang.Long)
	 */
	@Override
	public BlockAllocationDto fetchBlockDetailsbyAppId(Long applId) {
		BlockAllocationDto dto = new BlockAllocationDto();
		List<BlockAllocationDetailDto> detList = new ArrayList<>();
		List<BlockTargetDetDto> targetList = new ArrayList<>();
		List<BlockAllocationDetailEntity> detEntityList = blockDetRepo.findByApplicationId(applId);
		if (CollectionUtils.isNotEmpty(detEntityList)) {
			detEntityList.forEach(t -> {
				BlockAllocationDetailDto detDto = new BlockAllocationDetailDto();
				BeanUtils.copyProperties(t, detDto);
				detDto.setStateId(t.getSdb1());
				detDto.setDistId(t.getSdb2());
				detDto.setBlckId(t.getSdb3());
				detDto.setCbboName(cbboMasterRepository.fetchNameById(detDto.getCbboId()));
				detDto.setCbboId(detDto.getCbboId());
				detList.add(detDto);
			});
			
			BeanUtils.copyProperties(detEntityList.get(0).getBlockMasterEntity(), dto);
			dto.setApplicationId(applId);
			List<BlockTargetDetEntity> target = targetDetRepo.findByBlockMasterEntity(detEntityList.get(0).getBlockMasterEntity());
			target.forEach(t -> {
				BlockTargetDetDto detDto = new BlockTargetDetDto();
				BeanUtils.copyProperties(t, detDto);
				targetList.add(detDto);
			});
		}
		
		
		dto.setTargetDetDto(targetList);
		dto.setBlockDetailDto(detList);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#updateApprovalStatus(
	 * com.abm.mainet.sfac.dto.BlockAllocationDto, java.util.List,
	 * com.abm.mainet.sfac.ui.model.ChangeofBlockApprovalModel)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateApprovalStatusAndRemark(BlockAllocationDto newMasDto, BlockAllocationDto oldMasDto,
			ChangeofBlockApprovalModel model) {
		if (oldMasDto.getStatus().equals("APPROVED")) {
			allocationOfBlocksRepository.updateApprovalStatusAndRemark(oldMasDto.getBlockId(),
					oldMasDto.getApplicationId(), oldMasDto.getStatus(), oldMasDto.getAuthRemark());
			allocationOfBlocksHistRepo.updateApprovalStatusAndRemHist(oldMasDto.getBlockId(),
					oldMasDto.getApplicationId(), oldMasDto.getStatus(), oldMasDto.getAuthRemark());
			for (BlockAllocationDetailDto dto : newMasDto.getBlockDetailDto()) {
				allocationOfBlocksRepository.updateStatusInDetailEntity(dto.getBdId(), dto.getApplicationId(),
						dto.getStatus());
				allocationOfBlocksHistRepo.updateStatusInDetailHistEntity(dto.getBdId(), dto.getApplicationId(),
						dto.getStatus());
			}
		} else {
			allocationOfBlocksRepository.updateApprovalStatusAndRemark(oldMasDto.getBlockId(),
					oldMasDto.getApplicationId(), oldMasDto.getStatus(), oldMasDto.getAuthRemark());
			allocationOfBlocksHistRepo.updateApprovalStatusAndRemHist(oldMasDto.getBlockId(),
					oldMasDto.getApplicationId(), oldMasDto.getStatus(), oldMasDto.getAuthRemark());
			for (BlockAllocationDetailDto dto : oldMasDto.getBlockDetailDto()) {
				allocationOfBlocksRepository.updateStatusInDetailEntity(dto.getBdId(), dto.getApplicationId(),
						dto.getStatus());
				allocationOfBlocksHistRepo.updateStatusInDetailHistEntity(dto.getBdId(), dto.getApplicationId(),
						dto.getStatus());
			}
			for (BlockAllocationDetailDto dto : newMasDto.getBlockDetailDto()) {
				allocationOfBlocksRepository.updateStatusInDetailEntity(dto.getBdId(), dto.getApplicationId(),
						dto.getStatus());
				allocationOfBlocksHistRepo.updateStatusInDetailHistEntity(dto.getBdId(), dto.getApplicationId(),
						dto.getStatus());
			}
		}
	}

	@Override
	@Transactional
	public void deleteContractDocFileById(List<Long> enclosureRemoveById, Long empId) {
		iAttachDocsDao.updateRecord(enclosureRemoveById, empId, MainetConstants.Common_Constant.DELETE_FLAG);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#saveBlockDetails(com.
	 * abm.mainet.sfac.dto.BlockAllocationDto,
	 * com.abm.mainet.sfac.ui.model.AllocationOfBlocksModel)
	 */
	@Override
	@Transactional
	public BlockAllocationDto saveBlockDetails(BlockAllocationDto mastDto,
			AllocationOfBlocksModel allocationOfBlocksModel) {

		try {
			logger.info("saveBlockDetails started");
			BlockAllocationEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = allocationOfBlocksRepository.save(masEntity);
			saveHistoryData(masEntity);
		} catch (Exception e) {
			logger.error("error occured while saving block allocation details" + e);
			throw new FrameworkException("error occured while saving block allocation details" + e);
		}
		logger.info("saveBlockDetails End");
		return mastDto;

	}

	private BlockAllocationEntity mapDtoToEntity(BlockAllocationDto mastDto) {
		BlockAllocationEntity masEntity = new BlockAllocationEntity();
		List<BlockTargetDetEntity> targetDetailList = new ArrayList<>();
		List<BlockAllocationDetailEntity> blockDetailList = new ArrayList<>();

		BeanUtils.copyProperties(mastDto, masEntity);
		mastDto.getTargetDetDto().forEach(dto -> {
			BlockTargetDetEntity entity = new BlockTargetDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setBlockMasterEntity(masEntity);
			targetDetailList.add(entity);
		});

		mastDto.getBlockDetailDto().forEach(bankDto -> {
			BlockAllocationDetailEntity entity = new BlockAllocationDetailEntity();
			BeanUtils.copyProperties(bankDto, entity);
			entity.setSdb1(bankDto.getStateId());
			entity.setSdb2(bankDto.getDistId());
			entity.setSdb3(bankDto.getBlckId());
			entity.setAllcyrToCbbo(financialyearService.getFinanciaYearIdByFromDate(bankDto.getCreatedDate()));
			entity.setBlockMasterEntity(masEntity);
			blockDetailList.add(entity);
		});

		if (mastDto.getTargetDetDto().get(0).getAllocationTarget() != null)
			masEntity.setTargetDetEntity(targetDetailList);
		if (mastDto.getBlockDetailDto().get(0).getStateId() != null)
			masEntity.setDetailEntity(blockDetailList);

		return masEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#getDetailById(java.lang
	 * .Long)
	 */
	@Override
	public BlockAllocationDto getDetailById(Long blockId, Long cbboId) {
		BlockAllocationDto dto = new BlockAllocationDto();
		List<BlockTargetDetDto> targetDtoList = new ArrayList<>();
		List<BlockAllocationDetailDto> detDtoList = new ArrayList<>();
		BlockAllocationEntity entity = allocationOfBlocksRepository.findOne(blockId);
		BeanUtils.copyProperties(entity, dto);
		List<BlockTargetDetEntity> target = targetDetRepo.findByBlockMasterEntity(entity);
		target.forEach(t -> {
			BlockTargetDetDto detDto = new BlockTargetDetDto();
			BeanUtils.copyProperties(t, detDto);
			targetDtoList.add(detDto);
		});
		List<BlockAllocationDetailEntity> detList = new ArrayList<>();
		if (cbboId != null)
			detList = blockDetRepo.findByBlockMasterEntityAndCbboId(entity, cbboId);
		else
			detList = blockDetRepo.findByBlockMasterEntity(entity);
		if (CollectionUtils.isEmpty(detList)) {
			detList = entity.getDetailEntity();
		}
		detList.forEach(t -> {
			BlockAllocationDetailDto detDto = new BlockAllocationDetailDto();
			BeanUtils.copyProperties(t, detDto);
			detDto.setStateId(t.getSdb1());
			detDto.setDistId(t.getSdb2());
			detDto.setBlckId(t.getSdb3());
			detDto.setCbboName(cbboMasterRepository.fetchNameById(detDto.getCbboId()));
			detDto.setCbboId(detDto.getCbboId());
			// if (t.getStatus() != null &&(t.getStatus().equals(MainetConstants.FlagA) ||
			// t.getStatus().equals(MainetConstants.FlagC)))
			detDtoList.add(detDto);
		});

		dto.setTargetDetDto(targetDtoList);
		dto.setBlockDetailDto(detDtoList);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.AllocationOfBlocksService#
	 * getAllBlockDetailSummary(long)
	 */
	@Override
	@Transactional
	public List<BlockAllocationDto> getAllBlockDetailSummary(Long orgId, Long masId) {
		List<BlockAllocationDto> dtoList = new ArrayList<>();
		List<BlockAllocationEntity> entityList = new ArrayList<>();
		Organisation org = orgService.getOrganisationById(orgId);
		if (org.getOrgShortNm().equals(MainetConstants.Sfac.CBBO)) {
			entityList = allocationOfBlocksDao.findBlockDetailsForCbbo(null, masId);
			logger.info("findBlockDetailsForCbbo" +entityList);
		}else if (org.getOrgShortNm().equals(MainetConstants.Sfac.NPMA)) {
			entityList = allocationOfBlocksDao.findBlockDetails(orgId, masId);
			logger.info("findBlockDetails" +entityList);
		}else {
			entityList = allocationOfBlocksDao.findBlockDetails(null, masId);
			logger.info("findBlockDetails" +entityList);
		}
		if (CollectionUtils.isNotEmpty(entityList)) {
			for (BlockAllocationEntity entity : entityList) {
				if (entity != null) {
					BlockAllocationDto dto = new BlockAllocationDto();
					BeanUtils.copyProperties(entity, dto);
					Long sum = entity.getTargetDetEntity().stream().mapToLong(BlockTargetDetEntity::getAllocationTarget)
							.sum();
					dto.setNoofBlockAssigned(sum);
					Long blockCount = allocationOfBlocksRepository.getAllocatedBlockCount(entity.getBlockId());
					logger.info("getAllocatedBlockCount"+ blockCount);
					if (blockCount != 0 && blockCount != null && sum !=0) {
						Long pendingCount = (sum - blockCount);
						dto.setPendingBlockCount(pendingCount);
					} else if (blockCount == 0) {
						dto.setPendingBlockCount(sum);
					}

					if (entity.getDetailEntity() != null) {
						logger.info("detail Entity"+ entity.getDetailEntity());
						entity.getDetailEntity().forEach(det -> {
							logger.info("cbboId"+ det.getCbboId() +"masid" +masId);
							if (det != null && det.getCbboId() != null && masId != null && det.getCbboId().equals(masId)
									&& (det.getSdb3() != null && det.getStatus() != null
											&& det.getStatus().equals(MainetConstants.FlagC))) {
								@SuppressWarnings("deprecation")
								LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(det.getSdb3());
								if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
									dto.setChangedBlock(lookup.getDescLangFirst());
								else
									dto.setChangedBlock(lookup.getDescLangSecond());
							}
						});
					}

					String orgName = iaMasterService.fetchNameById(entity.getOrgTypeId(),
							entity.getOrganizationNameId());
					if (orgName != null)
						dto.setOrgName(orgName);
					if (entity.getAllocationYearId() != null) {
						TbFinancialyear financialYear = financialyearService.findYearById(entity.getAllocationYearId(),
								entity.getOrgId());
						try {
							dto.setAlcYear(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
						} catch (Exception e) {
							logger.error("Error occured while fetching financial year " + e);
						}
					}

					dtoList.add(dto);

				}
			}
		}
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#checkMasIdPresent(java.
	 * lang.Long)
	 */
	@Override
	public boolean checkMasIdPresent(Long masId) {
		Boolean result = false;
		result = allocationOfBlocksRepository.checkMasIdPresent(masId);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.AllocationOfBlocksService#checKDataExist(java.
	 * lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean checKDataExist(Long orgTypeId, Long orgNameId, Long allocationYearId) {
		Boolean result = allocationOfBlocksRepository.checKDataExist(orgTypeId, orgNameId, allocationYearId);
		if (result) {
			return true;
		}
		return false;
	}

}
