package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dao.WorkDefinitionDao;
import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;
import com.abm.mainet.workManagement.domain.WorkDefinationAssetInfoEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationAssetInfoHistoryEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationHistoryEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationWardZoneDetails;
import com.abm.mainet.workManagement.domain.WorkDefinationYearDetEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationYearDetHistoryEntity;
import com.abm.mainet.workManagement.domain.WorkDefinitionSancDet;
import com.abm.mainet.workManagement.domain.WorkDefinitionSancDetHistory;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkCompletionRegisterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationAssetInfoDto;
import com.abm.mainet.workManagement.dto.WorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionSancDetDto;
import com.abm.mainet.workManagement.repository.WorkDefinationWardZoneRepo;
import com.abm.mainet.workManagement.repository.WorkDefinationYearDetRepo;
import com.abm.mainet.workManagement.repository.WorkDefinitionRepository;
import com.abm.mainet.workManagement.rest.dto.ViewWorkCompletionRegisterDto;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinationAssetInfoDto;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinationYearDetDto;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinitionDto;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinitionSancDetDto;
import com.abm.mainet.workManagement.ui.controller.SchemeMasterController;

/**
 * @author hiren.poriya
 * @Since 08-Feb-2018
 */

@Service
public class WorkDefinitionServiceImpl implements WorkDefinitionService {

	
	  @Autowired
	    private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Autowired
	private WorkDefinitionDao workDefinitionDao;
	@Resource
	@Autowired
	private WorkDefinitionRepository workDefinitionRepository;

	@Resource
	@Autowired
	private WorkDefinationYearDetRepo workDefYearRepo;

	@Resource
	@Autowired
	private WorkDefinationWardZoneRepo wardZoneRepo;
	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private AuditService auditService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	DepartmentService departmentService;

	private static final Logger LOGGER = Logger.getLogger(SchemeMasterController.class);

	/**
	 * this service is used to create new work definitions
	 * 
	 * @param workDefDto
	 */
	@Override
	@Transactional
	public WorkDefinitionDto createWorkDefinition(WorkDefinitionDto workDefDto, List<DocumentDetailsVO> attachmentList,
			RequestDTO requestDTO) {
		WorkDefinitionDto savedDto = null;
		if (workDefDto != null) {
			Organisation org = new Organisation();
			org.setOrgid(workDefDto.getOrgId());
			// set work definitions master details
			WorkDefinationEntity workDefEntity = new WorkDefinationEntity();
			BeanUtils.copyProperties(workDefDto, workDefEntity);
			workDefEntity.setCreatedDate(new Date());
			TbWmsProjectMaster projMasEntity = new TbWmsProjectMaster();
			projMasEntity.setProjId(workDefDto.getProjId());
			workDefEntity.setProjMasEntity(projMasEntity);

			// set data for work definition financial year related details
			createNewFinancialYearsDetails(workDefDto, workDefEntity);

			// set data for Asset information related detailsT
			createNewAssetDetails(workDefDto, workDefEntity);

			// set data for sanctionDetails related information
			if (workDefDto.getWorkType() != null) {
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(workDefDto.getWorkType(), org);
				if (lookUp.getLookUpCode().contains("L")) {
					createNewSanctionDetails(workDefDto, workDefEntity);
				}
			}

			// set data for sanctionDetails related information
			createNewWardZoneDetails(workDefDto, workDefEntity);

			// find project department of project
			WmsProjectMasterDto projectMasterDto = ApplicationContextProvider.getApplicationContext()
					.getBean(WmsProjectMasterService.class).getProjectMasterByProjId(workDefDto.getProjId());

			// work code generation using project code and work definition primary key
			String wardNumber = checkForWardZoneCode(workDefDto);
			//start
			    SequenceConfigMasterDTO configMasterDTO = null;
		        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
		                PrefixConstants.STATUS_ACTIVE_PREFIX);
		        configMasterDTO = seqGenFunctionUtility.loadSequenceData(workDefDto.getOrgId(), deptId,
		                MainetConstants.WorksManagement.TB_WMS_WORKDEFINATION, MainetConstants.WorksManagement.WORK_CODE);//
		        String code =null;
		        
		        if (configMasterDTO.getSeqConfigId() == null) {	
		        	final Long workSequence = ApplicationContextProvider.getApplicationContext()
							.getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
									MainetConstants.WorksManagement.TB_WMS_WORKDEFINATION,
									MainetConstants.WorksManagement.WORK_CODE, workDefDto.getOrgId(), MainetConstants.FlagC,
									workDefDto.getOrgId());

					TbOrganisation orgDto = ApplicationContextProvider.getApplicationContext()
							.getBean(TbOrganisationService.class).findById(workDefDto.getOrgId());
					//Defect #180104
					 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {	
					 code = orgDto.getOrgShortNm() + MainetConstants.operator.FORWARD_SLACE
							+ CommonMasterUtility.getNonHierarchicalLookUpObject(orgDto.getOrgCpdId(), org).getDescLangFirst()
									.substring(0, 2).toUpperCase()
							+ MainetConstants.operator.FORWARD_SLACE + wardNumber + MainetConstants.operator.FORWARD_SLACE
							+ String.format("%03d", workSequence);
					 }
					 else { 
						 code = orgDto.getOrgShortNm() + MainetConstants.FILE_PATH_SEPARATOR
									+ CommonMasterUtility.getNonHierarchicalLookUpObject(orgDto.getOrgCpdId(), org).getDescLangFirst()
											.substring(0, 2).toUpperCase()
									+ MainetConstants.FILE_PATH_SEPARATOR + wardNumber + MainetConstants.FILE_PATH_SEPARATOR
									+ String.format("%03d", workSequence); 
					 }
					 
					 
		        }else {
		            CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
		            code=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
		        }
			//end
			workDefEntity.setWorkcode(code);
			WorkDefinationEntity savedDefEntity = workDefinitionDao.createWorkDefination(workDefEntity);
			createHistoryForWorkDefinition(savedDefEntity);
			savedDto = new WorkDefinitionDto();
			BeanUtils.copyProperties(savedDefEntity, savedDto); // document uploading details
			requestDTO.setIdfId(savedDefEntity.getWorkcode());
			fileUpload.doMasterFileUpload(attachmentList, requestDTO);
		}
		return savedDto;
	}

	private String checkForWardZoneCode(WorkDefinitionDto workDefDto) {

		String wardNumber = null;
		List<WorkDefinationWardZoneDetailsDto> uniqueWardZoneList = workDefDto.getWardZoneDto().stream()
				.collect(Collectors.collectingAndThen(Collectors.toCollection(
						() -> new TreeSet<>(Comparator.comparingLong(WorkDefinationWardZoneDetailsDto::getCodId1))),
						ArrayList::new));

		if (workDefDto.getWardZoneDto().size() > uniqueWardZoneList.size())
			wardNumber = MainetConstants.XX;
		else {
			wardNumber = CommonMasterUtility
					.getHierarchicalLookUp(workDefDto.getWardZoneDto().get(0).getCodId1(), workDefDto.getOrgId())
					.getLookUpCode().toUpperCase();
		}
		return wardNumber;
	}

	private void createHistoryForWorkDefinition(WorkDefinationEntity savedEntity) {

		// work definition master details history

		try {
			WorkDefinationHistoryEntity defHistoryEntity = new WorkDefinationHistoryEntity();
			defHistoryEntity.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
			defHistoryEntity.setProjId(savedEntity.getProjMasEntity().getProjId());
			auditService.createHistory(savedEntity, defHistoryEntity);
		} catch (Exception exception) {
			LOGGER.error("Exception ocours to create History of Work definition Master (Asset Details History)  "
					+ exception);
		}

		// asset details history
		if (savedEntity.getWdAssetInfoEntity() != null && !savedEntity.getWdAssetInfoEntity().isEmpty()) {
			try {
				List<Object> assetHistoryList = new ArrayList<>();
				savedEntity.getWdAssetInfoEntity().forEach(asset -> {
					WorkDefinationAssetInfoHistoryEntity assetHistory = new WorkDefinationAssetInfoHistoryEntity();
					BeanUtils.copyProperties(asset, assetHistory);
					assetHistory.setWorkId(savedEntity.getWorkId());
					assetHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
					assetHistoryList.add(assetHistory);
				});
				auditService.createHistoryForListObj(assetHistoryList);
			} catch (Exception exception) {
				LOGGER.error("Exception ocours to create History of Work definition Master (Asset Details History)  "
						+ exception);
			}
		}

		// financial details history
		if (savedEntity.getWdYearDetEntity() != null && !savedEntity.getWdYearDetEntity().isEmpty()) {
			try {
				List<Object> financeHistoryList = new ArrayList<>();
				savedEntity.getWdYearDetEntity().forEach(finance -> {
					WorkDefinationYearDetHistoryEntity financeHistory = new WorkDefinationYearDetHistoryEntity();
					BeanUtils.copyProperties(finance, financeHistory);
					financeHistory.setWorkId(savedEntity.getWorkId());
					financeHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
					financeHistoryList.add(financeHistory);
				});
				auditService.createHistoryForListObj(financeHistoryList);
			} catch (Exception exception) {
				LOGGER.error("Exception ocours to create History of Work definition Master (financial year history)  "
						+ exception);
			}

		}

		Organisation org = new Organisation();
		org.setOrgid(savedEntity.getOrgId());
		// Sanction Details History
		if (CommonMasterUtility.getNonHierarchicalLookUpObject(savedEntity.getWorkType(), org).getLookUpCode()
				.contains("L")) {
			if (savedEntity.getWorkSancDetails() != null && !savedEntity.getWorkSancDetails().isEmpty()) {
				try {
					List<Object> sancDetHistoryList = new ArrayList<>();
					savedEntity.getWorkSancDetails().forEach(sancDet -> {
						WorkDefinitionSancDetHistory sanctionHistory = new WorkDefinitionSancDetHistory();
						BeanUtils.copyProperties(sancDet, sanctionHistory);
						sanctionHistory.setWorkId(savedEntity.getWorkId());
						sanctionHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
						sancDetHistoryList.add(sanctionHistory);
					});
					auditService.createHistoryForListObj(sancDetHistoryList);
				} catch (Exception exception) {
					LOGGER.error("Exception ocours to create History of Work definition Master Sanction detils  "
							+ exception);
				}
			}
		}

	}

	// set data for Asset information related details if entered
	private void createNewAssetDetails(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {
		if (workDefDto.getAssetInfoDtos() != null && !workDefDto.getAssetInfoDtos().isEmpty()) {
			List<WorkDefinationAssetInfoEntity> assetInfoEntityList = new ArrayList<>();
			workDefDto.getAssetInfoDtos().forEach(assetInfo -> {
				if ((assetInfo.getAssetCategory() != null && !assetInfo.getAssetCategory().equals(StringUtils.EMPTY))
						|| (assetInfo.getAssetCode() != null && !assetInfo.getAssetCode().equals(StringUtils.EMPTY))
						|| (assetInfo.getAssetName() != null && !assetInfo.getAssetName().equals(StringUtils.EMPTY))
						|| (assetInfo.getAssetDepartment() != null
								&& !assetInfo.getAssetDepartment().equals(StringUtils.EMPTY))
						|| (assetInfo.getAssetLocation() != null
								&& !assetInfo.getAssetLocation().equals(StringUtils.EMPTY))
						|| (assetInfo.getAssetStatus() != null
								&& !assetInfo.getAssetStatus().equals(StringUtils.EMPTY))) {
					WorkDefinationAssetInfoEntity assetInfoEntity = new WorkDefinationAssetInfoEntity();
					BeanUtils.copyProperties(assetInfo, assetInfoEntity);
					setCreateAssetCommonDetails(workDefDto, assetInfoEntity);
					assetInfoEntity.setWorkDefEntity(workDefEntity);
					assetInfoEntityList.add(assetInfoEntity);
				}

			});
			workDefEntity.setWdAssetInfoEntity(assetInfoEntityList);
		}
	}

	// set data for work definition financial year related details if entered
	private void createNewFinancialYearsDetails(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {
		if (workDefDto.getYearDtos() != null && !workDefDto.getYearDtos().isEmpty()) {
			List<WorkDefinationYearDetEntity> defYearEntityList = new ArrayList<>();
			workDefDto.getYearDtos().forEach(defYear -> {
				if (defYear.getFaYearId() != null
						|| (defYear.getFinanceCodeDesc() != null
								&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
						|| defYear.getYearPercntWork() != null
						|| (defYear.getYeDocRefNo() != null && !defYear.getYeDocRefNo().equals(StringUtils.EMPTY))
						|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
					WorkDefinationYearDetEntity defYearEntity = new WorkDefinationYearDetEntity();
					BeanUtils.copyProperties(defYear, defYearEntity);
					setCreateYearCommonDetails(workDefDto, defYearEntity);
					defYearEntity.setWorkDefEntity(workDefEntity);
					defYearEntityList.add(defYearEntity);
				}
			});

			workDefEntity.setWdYearDetEntity(defYearEntityList);
		}
	}

	// set data for work sanction details related information
	private void createNewSanctionDetails(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {

		if (workDefDto.getSanctionDetails() != null && !workDefDto.getSanctionDetails().isEmpty()) {
			List<WorkDefinitionSancDet> sanctionDetailsEntity = new ArrayList<>();
			workDefDto.getSanctionDetails().forEach(sancDet -> {
				WorkDefinitionSancDet sanctionEntity = new WorkDefinitionSancDet();
				BeanUtils.copyProperties(sancDet, sanctionEntity);
				setSanctionDetails(workDefDto, sanctionEntity);
				sanctionEntity.setWorkDefEntity(workDefEntity);
				sanctionDetailsEntity.add(sanctionEntity);
			});
			workDefEntity.setWorkSancDetails(sanctionDetailsEntity);
		}
	}

	// set data for work ward zone details related information
	private void createNewWardZoneDetails(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {

		if (workDefDto.getWardZoneDto() != null && !workDefDto.getWardZoneDto().isEmpty()) {
			List<WorkDefinationWardZoneDetails> wardZoneDetailsEntity = new ArrayList<>();
			workDefDto.getWardZoneDto().forEach(wardZoneDet -> {
				WorkDefinationWardZoneDetails wardZoneEntity = new WorkDefinationWardZoneDetails();
				BeanUtils.copyProperties(wardZoneDet, wardZoneEntity);
				setWardZoneDetails(workDefDto, wardZoneEntity);
				wardZoneEntity.setWorkDefEntity(workDefEntity);
				wardZoneDetailsEntity.add(wardZoneEntity);
			});
			workDefEntity.setWardZoneDetails(wardZoneDetailsEntity);
		}
	}

	/**
	 * this service is used to find all work definitions by organization id
	 * 
	 * @param orgId
	 * @return List of work definition DTOs
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkDefinitionsByOrgId(Long orgId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.findAllWorkDefinitionsByOrgId(orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			Organisation organisation = new Organisation();
			organisation.setOrgid(orgId);
			defEntityList.forEach(entity -> {
				WorkDefinitionDto defDto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, defDto);
				// set common description details Ids for view purpose
				setCommonDescDetails(entity, defDto, organisation);
				dtoList.add(defDto);
			});
		}
		return dtoList;
	}

	// set common description details by Ids for view purpose
	private void setCommonDescDetails(WorkDefinationEntity entity, WorkDefinitionDto defDto, Organisation org) {
		defDto.setProjId(entity.getProjMasEntity().getProjId());
		defDto.setProjName(entity.getProjMasEntity().getProjNameEng());
		defDto.setProjCode(entity.getProjMasEntity().getProjCode());
		defDto.setProjDeptId(entity.getProjMasEntity().getDpDeptId());

		defDto.setWorkProjPhaseDesc(
				CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getWorkProjPhase(), org).getDescLangFirst());
		defDto.setWorkTypeDesc(
				CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getWorkType(), org).getLookUpDesc());
		if (entity.getWorkStartDate() != null)
			defDto.setStartDateDesc(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getWorkStartDate()));
		if (entity.getWorkEndDate() != null)
			defDto.setEndDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getWorkEndDate()));

		/* REMOVE AS PER SUDA UAT */
		/*
		 * defDto.setStartDateDesc(new
		 * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getWorkStartDate(
		 * ))); defDto.setEndDateDesc(new
		 * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getWorkEndDate())
		 * );
		 */
		defDto.setCreatedDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getCreatedDate()));
	}

	/**
	 * this service is used to filter search based on work code or work name or work
	 * start date or work end date or project id or work type id or project phase id
	 * 
	 * @param orgId
	 * @param workDefDto
	 * @return List<WorkDefinitionDto>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> filterWorkDefRecords(Long orgId, WorkDefinitionDto workDefDto) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionDao.filterWorkDefRecords(orgId, workDefDto);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			Organisation organisation = new Organisation();
			organisation.setOrgid(orgId);
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				// set common description details by Ids for view purpose
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	/**
	 * this service is used to find work definition details by its primary key work
	 * id.
	 * 
	 * @param workId
	 * @return WorkDefinitionDto
	 */
	@Override
	@Transactional(readOnly = true)
	public WorkDefinitionDto findAllWorkDefinitionById(Long workId) {
		WorkDefinitionDto detDto = null;

		WorkDefinationEntity detEntity = workDefinitionRepository.findOne(workId);
		if (detEntity != null) {
			Organisation organisation = new Organisation();
			organisation.setOrgid(detEntity.getOrgId());
			detDto = new WorkDefinitionDto();

			setCommonDescDetails(detEntity, detDto, organisation);
			BeanUtils.copyProperties(detEntity, detDto);
			detDto.setProjId(detEntity.getProjMasEntity().getProjId());
			detDto.setWorkType((detEntity.getWorkType()));
			detDto.setDeptId(detEntity.getDeptId());
			detDto.setProjDeptId(detEntity.getProjMasEntity().getDpDeptId());
			
			if(UserSession.getCurrent().getLanguageId()!=1) {
				detDto.setProjName(detEntity.getProjMasEntity().getProjNameReg());
			}else {
				detDto.setProjName(detEntity.getProjMasEntity().getProjNameEng());	
			}
			detDto.setProjCode(detEntity.getProjMasEntity().getProjCode());

			if (detEntity.getProjMasEntity().getSchId() != null)
				detDto.setSouceOffund(detEntity.getProjMasEntity().getSchId().getWmSchCodeId1());

			/* REMOVE AS PER SUDA UAT */
			/*
			 * detDto.setStartDateDesc( new
			 * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(detEntity.
			 * getWorkStartDate())); detDto.setEndDateDesc(new
			 * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(detEntity.getWorkEndDate
			 * ()));
			 */
			// set Assets details if record found for work definition vi commented
			if (detEntity.getWdAssetInfoEntity() != null && !detEntity.getWdAssetInfoEntity().isEmpty()) {
				List<WorkDefinationAssetInfoDto> assetDtoList = new ArrayList<>();
				detEntity.getWdAssetInfoEntity().forEach(assetEntity -> {
					WorkDefinationAssetInfoDto assetDto = new WorkDefinationAssetInfoDto();
					BeanUtils.copyProperties(assetEntity, assetDto);
					assetDtoList.add(assetDto);
				});
				detDto.setAssetInfoDtos(assetDtoList);
			}

			// set financial year details if record found for work definition
			if (detEntity.getWdYearDetEntity() != null && !detEntity.getWdYearDetEntity().isEmpty()) {
				List<WorkDefinationYearDetDto> yearDtoList = new ArrayList<>();
				detEntity.getWdYearDetEntity().forEach(yearEntity -> {
					WorkDefinationYearDetDto yearDto = new WorkDefinationYearDetDto();
					BeanUtils.copyProperties(yearEntity, yearDto);
					yearDtoList.add(yearDto);
				});
				detDto.setYearDtos(yearDtoList);
			}
			if (detEntity.getWdYearDetEntity() == null || detEntity.getWdYearDetEntity().isEmpty()) {
				List<WorkDefinationYearDetDto> definationYearDetDto = getYearDetailByWorkId(detDto, detDto.getOrgId());
				detDto.setYearDtos(definationYearDetDto);
			}

			if (detEntity.getWorkSancDetails() != null && !detEntity.getWorkSancDetails().isEmpty()) {
				List<WorkDefinitionSancDetDto> sancDetDtosList = new ArrayList<>();
				detEntity.getWorkSancDetails().forEach(scanDetEntity -> {
					WorkDefinitionSancDetDto sancDetDto = new WorkDefinitionSancDetDto();
					BeanUtils.copyProperties(scanDetEntity, sancDetDto);
					sancDetDtosList.add(sancDetDto);
				});
				detDto.setSanctionDetails(sancDetDtosList);
			}

			if (detEntity.getWardZoneDetails() != null && !detEntity.getWardZoneDetails().isEmpty()) {
				List<WorkDefinationWardZoneDetailsDto> wardZoneDetDtosList = new ArrayList<>();
				detEntity.getWardZoneDetails().forEach(wardZoneDetEntity -> {
					WorkDefinationWardZoneDetailsDto wardZoneDetDto = new WorkDefinationWardZoneDetailsDto();
					BeanUtils.copyProperties(wardZoneDetEntity, wardZoneDetDto);
					wardZoneDetDtosList.add(wardZoneDetDto);
				});
				detDto.setWardZoneDto(wardZoneDetDtosList);
			}
		}
		return detDto;
	}

	/**
	 * this service is used to update work definitions
	 * 
	 * @param workDefDto
	 * @param list
	 * @param requestDTO
	 * @param removeAssetIdList
	 * @param removeYearIdList
	 * @param removedDocIds
	 */
	@Override
	@Transactional
	public void updateWorkDefinition(WorkDefinitionDto workDefDto, List<DocumentDetailsVO> attachmentList,
			RequestDTO requestDTO, List<Long> removeAssetIdList, List<Long> removeYearIdList, List<Long> removedDocIds,
			List<Long> removeScanIdList, List<Long> removeWarZoneIdList) {

		// set work definitions master details
		WorkDefinationEntity workDefEntity = new WorkDefinationEntity();
		BeanUtils.copyProperties(workDefDto, workDefEntity);
		workDefEntity.setUpdatedDate(new Date());
		workDefEntity.setUpdatedBy(workDefDto.getCreatedBy());
		workDefEntity.setLgIpMacUpd(workDefDto.getLgIpMacUpd());
		TbWmsProjectMaster projMasEntity = new TbWmsProjectMaster();
		projMasEntity.setProjId(workDefDto.getProjId());
		workDefEntity.setProjMasEntity(projMasEntity);

		// set data for work definition financial year related details for update
		setFinanacialYearDetailsForUpdate(workDefDto, workDefEntity);

		// set data for Asset information related details for update
		setAssetDetailsForUpdate(workDefDto, workDefEntity);

		// set data for Sanction details related information
		setSanctionDetailsForUpdate(workDefDto, workDefEntity);

		// set data for ward zone details related information
		setWardZoneDetailsForUpdate(workDefDto, workDefEntity);

		WorkDefinationEntity savedDefinition = workDefinitionRepository.save(workDefEntity);

		// update work definition history
		updateHistoryForWorkDefinition(savedDefinition);

		// inactive removed asset details by set its flag as 'N'
		inactiveRemovedAssetDetails(workDefDto, removeAssetIdList);

		// inactive removed work definition year details by set its flag as 'N'
		inactiveRemovedYearDetails(workDefDto, removeYearIdList);

		// inactive removed work definition year details by set its flag as 'D'
		inactiveDeletedDocuments(workDefDto, removedDocIds);

		// Deleted Sanction details hard remove
		deleteSanctionDetails(workDefDto, removeScanIdList);

		// Deleted ward Zone details hard remove
		deleteWardZoneDetails(workDefDto, removeWarZoneIdList);

		// document uploading details
		if (requestDTO != null) {
			requestDTO.setIdfId(workDefDto.getWorkcode());
			fileUpload.doMasterFileUpload(attachmentList, requestDTO);
		}
	}

	private void updateHistoryForWorkDefinition(WorkDefinationEntity savedEntity) {

		// update work definition master details history
		try {
			WorkDefinationHistoryEntity defHistoryEntity = new WorkDefinationHistoryEntity();
			defHistoryEntity.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			defHistoryEntity.setProjId(savedEntity.getProjMasEntity().getProjId());
			auditService.createHistory(savedEntity, defHistoryEntity);
		} catch (Exception exception) {
			LOGGER.error("Exception ocours to create work Definition Master " + exception);
		}

		// update asset details history
		if (savedEntity.getWdAssetInfoEntity() != null && !savedEntity.getWdAssetInfoEntity().isEmpty()) {
			try {
				List<Object> assetHistoryList = new ArrayList<>();
				savedEntity.getWdAssetInfoEntity().forEach(asset -> {
					WorkDefinationAssetInfoHistoryEntity assetHistory = new WorkDefinationAssetInfoHistoryEntity();
					BeanUtils.copyProperties(asset, assetHistory);
					assetHistory.setWorkId(savedEntity.getWorkId());
					assetHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
					assetHistoryList.add(assetHistory);
				});
				auditService.createHistoryForListObj(assetHistoryList);
			} catch (Exception exception) {
				LOGGER.error("Exception ocours to create History of Work definition Master AssetHistory  " + exception);
			}

		}

		// update financial details history
		if (savedEntity.getWdYearDetEntity() != null && !savedEntity.getWdYearDetEntity().isEmpty()) {
			try {
				List<Object> financeHistoryList = new ArrayList<>();
				savedEntity.getWdYearDetEntity().forEach(finance -> {
					WorkDefinationYearDetHistoryEntity financeHistory = new WorkDefinationYearDetHistoryEntity();
					BeanUtils.copyProperties(finance, financeHistory);
					financeHistory.setWorkId(savedEntity.getWorkId());
					financeHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
					financeHistoryList.add(financeHistory);
				});
				auditService.createHistoryForListObj(financeHistoryList);
			} catch (Exception exception) {
				LOGGER.error(
						"Exception ocours to create History of Work definition Master financial year " + exception);
			}

		}

		if (savedEntity.getWorkSancDetails() != null && !savedEntity.getWorkSancDetails().isEmpty()) {
			try {
				List<Object> sancDetailsHistoryList = new ArrayList<>();
				savedEntity.getWorkSancDetails().forEach(sancDetails -> {
					WorkDefinitionSancDetHistory sancDetHistory = new WorkDefinitionSancDetHistory();
					BeanUtils.copyProperties(sancDetails, sancDetHistory);
					sancDetHistory.setWorkId(savedEntity.getWorkId());
					sancDetHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
					sancDetailsHistoryList.add(sancDetHistory);
				});
				auditService.createHistoryForListObj(sancDetailsHistoryList);
			} catch (Exception exception) {
				LOGGER.error(
						"Exception ocours to create History of Work definition Master Sanction details " + exception);
			}

		}
	}

	// set data for work definition financial year related details for update
	private void setFinanacialYearDetailsForUpdate(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {
		if (workDefDto.getYearDtos() != null) {
			List<WorkDefinationYearDetEntity> defYearEntityList = new ArrayList<>();
			workDefDto.getYearDtos().forEach(defYear -> {
				if (defYear.getFinActiveFlag() != null) {
					if (defYear.getFaYearId() != null
							|| (defYear.getFinanceCodeDesc() != null
									&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
							|| defYear.getYearPercntWork() != null
							|| (defYear.getYeDocRefNo() != null && !defYear.getYeDocRefNo().equals(StringUtils.EMPTY))
							|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
						WorkDefinationYearDetEntity defYearEntity = new WorkDefinationYearDetEntity();
						BeanUtils.copyProperties(defYear, defYearEntity);
						if (defYear.getYearId() != null) {
							defYearEntity.setUpdatedBy(workDefDto.getCreatedBy());
							defYearEntity.setUpdatedDate(new Date());
							defYearEntity.setLgIpMacUpd(workDefDto.getLgIpMac());
						} else {
							setCreateYearCommonDetails(workDefDto, defYearEntity);
						}
						defYearEntity.setWorkDefEntity(workDefEntity);
						defYearEntityList.add(defYearEntity);
					}
				}
			});
			workDefEntity.setWdYearDetEntity(defYearEntityList);
		}
	}

	// set data for Asset information related details for update
	private void setAssetDetailsForUpdate(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {
		if (workDefDto.getAssetInfoDtos() != null) {
			List<WorkDefinationAssetInfoEntity> assetInfoEntityList = new ArrayList<>();
			workDefDto.getAssetInfoDtos().forEach(assetInfo -> {
				if (assetInfo.getAssetActiveFlag() != null) {
					if ((assetInfo.getAssetCategory() != null
							&& !assetInfo.getAssetCategory().equals(StringUtils.EMPTY))
							|| (assetInfo.getAssetCode() != null && !assetInfo.getAssetCode().equals(StringUtils.EMPTY))
							|| (assetInfo.getAssetName() != null && !assetInfo.getAssetName().equals(StringUtils.EMPTY))
							|| (assetInfo.getAssetDepartment() != null
									&& !assetInfo.getAssetDepartment().equals(StringUtils.EMPTY))
							|| (assetInfo.getAssetLocation() != null
									&& !assetInfo.getAssetLocation().equals(StringUtils.EMPTY))
							|| (assetInfo.getAssetStatus() != null
									&& !assetInfo.getAssetStatus().equals(StringUtils.EMPTY))) {
						WorkDefinationAssetInfoEntity assetInfoEntity = new WorkDefinationAssetInfoEntity();
						BeanUtils.copyProperties(assetInfo, assetInfoEntity);

						if (assetInfo.getWorkAssetId() != null) {
							assetInfoEntity.setUpdatedBy(workDefDto.getCreatedBy());
							assetInfoEntity.setUpdatedDate(new Date());
							assetInfoEntity.setLgIpMacUpd(workDefDto.getLgIpMac());
						} else {
							setCreateAssetCommonDetails(workDefDto, assetInfoEntity);
						}
						assetInfoEntity.setWorkDefEntity(workDefEntity);
						assetInfoEntityList.add(assetInfoEntity);
					}
				}
			});
			workDefEntity.setWdAssetInfoEntity(assetInfoEntityList);
		}
	}

	// set Sanction Details for update
	private void setSanctionDetailsForUpdate(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {
		if (workDefDto.getSanctionDetails() != null) {
			List<WorkDefinitionSancDet> sancDetailsEntityList = new ArrayList<>();
			workDefDto.getSanctionDetails().forEach(sancDet -> {
				WorkDefinitionSancDet definitionSancDet = new WorkDefinitionSancDet();
				BeanUtils.copyProperties(sancDet, definitionSancDet);
				if (sancDet.getWorkSancId() != null) {
					definitionSancDet.setUpdatedBy(workDefDto.getUpdatedBy());
					definitionSancDet.setUpdatedDate(new Date());
					definitionSancDet.setLgIpMacUpd(workDefDto.getLgIpMacUpd());
				} else {
					setSanctionDetails(workDefDto, definitionSancDet);
				}
				definitionSancDet.setWorkDefEntity(workDefEntity);
				sancDetailsEntityList.add(definitionSancDet);
			});
			workDefEntity.setWorkSancDetails(sancDetailsEntityList);
		}

	}

	// set ward zone Details for update
	private void setWardZoneDetailsForUpdate(WorkDefinitionDto workDefDto, WorkDefinationEntity workDefEntity) {
		if (workDefDto.getWardZoneDto() != null) {
			List<WorkDefinationWardZoneDetails> wardZoneDetailsEntityList = new ArrayList<>();
			workDefDto.getWardZoneDto().forEach(sancDet -> {
				WorkDefinationWardZoneDetails definitionwardZoneDet = new WorkDefinationWardZoneDetails();
				BeanUtils.copyProperties(sancDet, definitionwardZoneDet);
				if (sancDet.getWardZoneId() != null) {
					definitionwardZoneDet.setUpdatedBy(workDefDto.getUpdatedBy());
					definitionwardZoneDet.setUpdatedDate(new Date());
					definitionwardZoneDet.setLgIpMacUpd(workDefDto.getLgIpMacUpd());
				} else {
					setWardZoneDetails(workDefDto, definitionwardZoneDet);
				}

				definitionwardZoneDet.setWorkDefEntity(workDefEntity);
				wardZoneDetailsEntityList.add(definitionwardZoneDet);
			});
			workDefEntity.setWardZoneDetails(wardZoneDetailsEntityList);
		}

	}

	// inactive removed asset details by set its flag as 'N'
	private void inactiveRemovedAssetDetails(WorkDefinitionDto workDefDto, List<Long> removeAssetIdList) {
		if (removeAssetIdList != null && !removeAssetIdList.isEmpty()) {
			workDefinitionRepository.iactiveAssetsByIds(removeAssetIdList, workDefDto.getUpdatedBy());
		}
	}

	// inactive removed work definition year details by set its flag as 'N'
	private void inactiveRemovedYearDetails(WorkDefinitionDto workDefDto, List<Long> removeYearIdList) {
		if (removeYearIdList != null && !removeYearIdList.isEmpty()) {
			workDefinitionRepository.iactiveYearsByIds(removeYearIdList, workDefDto.getUpdatedBy());
		}
	}

	// inactive removed work definition year details by set its flag as 'D'
	private void inactiveDeletedDocuments(WorkDefinitionDto workDefDto, List<Long> removedDocIds) {

		if (removedDocIds != null && !removedDocIds.isEmpty()) {
			ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(removedDocIds,
					workDefDto.getUpdatedBy(), MainetConstants.RnLCommon.Flag_D);
		}
	}

	private void deleteSanctionDetails(WorkDefinitionDto workDefDto, List<Long> removeScanIdList) {

		if (removeScanIdList != null && !removeScanIdList.isEmpty()) {
			workDefinitionRepository.deleteSanctionDetails(removeScanIdList);
		}
	}

	private void deleteWardZoneDetails(WorkDefinitionDto workDefDto, List<Long> removeWardZoneIdList) {

		if (removeWardZoneIdList != null && !removeWardZoneIdList.isEmpty()) {
			workDefinitionRepository.deleteWardZoneDetails(removeWardZoneIdList);
		}
	}

	private void setCreateYearCommonDetails(WorkDefinitionDto workDefDto, WorkDefinationYearDetEntity defYearEntity) {
		defYearEntity.setCreatedBy(workDefDto.getCreatedBy());
		defYearEntity.setCreatedDate(new Date());
		defYearEntity.setLgIpMac(workDefDto.getLgIpMac());
		defYearEntity.setYeActive(MainetConstants.Common_Constant.YES);
		defYearEntity.setOrgId(workDefDto.getOrgId());
	}

	private void setCreateAssetCommonDetails(WorkDefinitionDto workDefDto,
			WorkDefinationAssetInfoEntity assetInfoEntity) {
		assetInfoEntity.setOrgId(workDefDto.getOrgId());
		assetInfoEntity.setCreatedBy(workDefDto.getCreatedBy());
		assetInfoEntity.setCreatedDate(new Date());
		assetInfoEntity.setLgIpMac(workDefDto.getLgIpMac());
		assetInfoEntity.setAssetRecStatus(MainetConstants.Common_Constant.YES);
	}

	private void setSanctionDetails(WorkDefinitionDto workDefDto, WorkDefinitionSancDet sanctionEntity) {
		sanctionEntity.setCreatedBy(workDefDto.getCreatedBy());
		sanctionEntity.setCreatedDate(workDefDto.getCreatedDate());
		sanctionEntity.setLgIpMac(workDefDto.getLgIpMac());
		sanctionEntity.setOrgid(workDefDto.getOrgId());
	}

	private void setWardZoneDetails(WorkDefinitionDto workDefDto, WorkDefinationWardZoneDetails wardZoneEntity) {
		wardZoneEntity.setCreatedBy(workDefDto.getCreatedBy());
		wardZoneEntity.setCreatedDate(new Date());
		wardZoneEntity.setLgIpMac(workDefDto.getLgIpMac());
		wardZoneEntity.setOrgId(workDefDto.getOrgId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkDefinitionsExcludedWork(long orgid, List<Long> workIdList, Long projId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		List<WorkDefinationEntity> defEntityList = workDefinitionDao.findAllWorkDefinitionsExcludedWork(orgid,
				workIdList, projId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto defDto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, defDto);
				setCommonDescDetails(entity, defDto, organisation);
				dtoList.add(defDto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional
	public void updateWorkDefinationMode(Long workDefination, String flag) {
		workDefinitionRepository.updateWorkDefinationMode(workDefination, flag);

	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> getWorkDefinationBySearch(Long orgId, String estimateNo, Long projectId,
			Long workName, String status, Date fromDate, Date toDate) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinationEntity> defEntityList = workDefinitionDao.getWorkDefinationBySearch(orgId, estimateNo,
				projectId, workName, status, fromDate, toDate);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto defDto = new WorkDefinitionDto();
				defDto.setProjNameReg(entity.getProjMasEntity().getProjNameReg());
				BeanUtils.copyProperties(entity, defDto);
				setCommonDescDetails(entity, defDto, organisation);
				dtoList.add(defDto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkDefinationByProjId(Long orgId, Long projId) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.filterWorkDefRecordsByProjId(orgId, projId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	//Defect #85135
	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkDefByProjIdAndWorkType(Long orgId, Long projId, Long workType) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.filterWorkDefDetByProjIdAndWorkType(orgId, projId, workType);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkByWorkList(long orgId, List<Long> workId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.findAllWorkByWorkList(orgId, workId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public WorkDefinitionDto findWorkDefinitionByWorkCode(String workCode, Long orgId) {
		WorkDefinitionDto defDto = null;
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		WorkDefinationEntity defEntity = workDefinitionRepository.findWorkDefinitionByWorkCode(workCode, orgId);
		if (defEntity != null) {
			defDto = new WorkDefinitionDto();
			defDto.setProjNameReg(defEntity.getProjMasEntity().getProjNameReg());
			BeanUtils.copyProperties(defEntity, defDto);
			setCommonDescDetails(defEntity, defDto, organisation);
		}
		return defDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(Long projId, Long orgId,
			Long tenderId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository
				.findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(projId, orgId, tenderId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllApprovedNotInitiatedWorkByProjIdAndOrgId(Long projId, Long orgId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository
				.findAllApprovedNotInitiatedWorkByProjIdAndOrgId(projId, orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional
	public void updateWorksStatusToInitiated(List<Long> worksId) {
		workDefinitionRepository.updateWorksStatusToInitiated(worksId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllApprovedNotInitiatedWork(Long tndId, Long projId, Long orgId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.findAllApprovedNotInitiatedWork(tndId,
				projId, orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional
	public void updateWorkStatusAsTendered(List<Long> workIds) {
		workDefinitionRepository.updateWorkStatusAsTendered(workIds);

	}

	@Override
	@Transactional
	public void updateSanctionNumber(String sanctionNumber, Long workId, Long orgid, Long deptId, String workSancBy,
			String workDesignBy) {

		workDefinitionRepository.updateWorkSanctionNumber(sanctionNumber, workId, orgid, deptId, workSancBy,
				workDesignBy);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionSancDetDto> getAllSanctionDetailsByWorkId(Long workId) {

		final List<WorkDefinitionSancDetDto> workDtoList = new ArrayList<>();
		List<WorkDefinitionSancDet> workSancDetList = workDefinitionRepository.finadAllSanctionDetailsByWorkId(workId);
		if (workSancDetList != null && !workSancDetList.isEmpty()) {
			workSancDetList.forEach(sancDet -> {
				WorkDefinitionSancDetDto definitionSancDetDto = new WorkDefinitionSancDetDto();
				BeanUtils.copyProperties(sancDet, definitionSancDetDto);
				workDtoList.add(definitionSancDetDto);
			});
		}
		return workDtoList;
	}

	@Override
	@Transactional
	public void updateTotalEstimatedAmount(Long workId, BigDecimal totalAmount) {
		workDefinitionRepository.updateTotalEstimatedAmount(workId, totalAmount);
	}

	@Override
	@Transactional
	public void updateWorkCompletionDate(Long workId, Date workCompletionDate, String completionNo) {
		workDefinitionRepository.updateWorkCompletionDate(workId, workCompletionDate, completionNo);
	}

	@Override
	@Transactional(readOnly = true)
	public WorkDefinitionDto findAllWorkDefinationSanctionByProjId(Long orgId, Long projId, String workSancNo) {

		final List<WorkDefinitionSancDetDto> dtoList = new ArrayList<>();
		WorkDefinitionDto dto = new WorkDefinitionDto();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinitionSancDet> defEntityList = workDefinitionRepository.filterWorkDefSanctionRecordsByProjId(orgId,
				projId, workSancNo);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			BeanUtils.copyProperties(defEntityList.get(0).getWorkDefEntity(), dto);
			setCommonDescDetails(defEntityList.get(0).getWorkDefEntity(), dto, organisation);
			defEntityList.forEach(entity -> {
				WorkDefinitionSancDetDto sanctionDto = new WorkDefinitionSancDetDto();
				BeanUtils.copyProperties(entity, sanctionDto);
				dtoList.add(sanctionDto);
			});
		}
		dto.setSanctionDetails(dtoList);
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> findAllWorksByProjIdAndStatus(Long orgId, Long projId) {
		final List<Object> dtoList = new ArrayList<>();
		List<Object[]> defEntityList = workDefinitionRepository.filterWorkDefRecordsByProjIdAndStatus(orgId, projId);

		for (Object[] masterEntity : defEntityList) {
			WorkDefinitionDto masterDto = new WorkDefinitionDto();
			masterDto.setWorkId((Long) masterEntity[0]);
			masterDto.setWorkName((String) masterEntity[1]);
			dtoList.add(masterDto);
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionSancDetDto> findSanctionNoByWorkId(Long orgId, Long workId) {
		final List<WorkDefinitionSancDetDto> workDtoList = new ArrayList<>();
		List<WorkDefinitionSancDet> workSancDetList = workDefinitionRepository.filterSanctionNoRecordsByWrojId(orgId,
				workId);
		if (workSancDetList != null && !workSancDetList.isEmpty()) {
			workSancDetList.forEach(sancDet -> {
				WorkDefinitionSancDetDto definitionSancDetDto = new WorkDefinitionSancDetDto();
				BeanUtils.copyProperties(sancDet, definitionSancDetDto);
				workDtoList.add(definitionSancDetDto);
			});
		}
		return workDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllCompletedWorks(Long orgId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.findAllCompletedWorks(orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, organisation);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllCompletedWorksByProjId(Long projId, String flag, Long orgId) {
		List<Object[]> workList = null;
		List<Object[]> workList1 = null;
		List<WorkDefinitionDto> definitionDtos = new ArrayList<>();
	 	
		//Defect #88775
    	LookUp envSpeceficWorkforComlCertificate = CommonMasterUtility.getValueFromPrefixLookUp("B",
                 "CCV", UserSession.getCurrent().getOrganisation());
    	if(envSpeceficWorkforComlCertificate.getOtherField().equalsIgnoreCase(MainetConstants.FlagY)) {
    		workList = workDefinitionDao.findAllCompletedWorkBasedOnEnv(projId, flag, orgId);
    		workList1 = workDefinitionDao.findAllCompletedWorksByProjId(projId, flag, orgId);
    		workList.addAll(workList1);

    	}else {
    		workList = workDefinitionDao.findAllCompletedWorksByProjId(projId, flag, orgId);
    	}
   	
		if (workList != null && !workList.isEmpty()) {
			WorkDefinitionDto workDefinitionDto = null;

			for (final Object venObj[] : workList) {
				workDefinitionDto = new WorkDefinitionDto();
				workDefinitionDto.setWorkId(Long.valueOf(venObj[0].toString()));
				workDefinitionDto.setWorkName(venObj[1].toString());

				definitionDtos.add(workDefinitionDto);
			}
		}
		return definitionDtos;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> filterCompletionRecords(Long projId, Long workId, String completionNo, Long orgId) {
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionDao.filterCompletionRecords(projId, workId,
				completionNo, orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				setCommonDescDetails(entity, dto, org);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinationYearDetDto> getAllBudgetHeadByWorkId(Long workId) {
		List<WorkDefinationYearDetDto> dtosList = new ArrayList<>();
		List<Object[]> listsOfBudget = workDefinitionRepository.getAllBudgetHeadByWorkId(workId);
		WorkDefinationYearDetDto yearDetDto = null;
		if (!listsOfBudget.isEmpty() && listsOfBudget != null) {
			for (Object[] objects : listsOfBudget) {
				yearDetDto = new WorkDefinationYearDetDto();
				if (objects[0] != null) {
					yearDetDto.setFinanceCodeDesc((String) objects[0]);
				}
				if (objects[1] != null) {
					yearDetDto.setFinanceCodeDesc((String) objects[1]);
				}
				if (objects[2] != null) {
					Date date = (Date) objects[2];
					try {
						yearDetDto.setFaYearFromTo(Utility.getFinancialYearFromDate(date));
					} catch (Exception exception) {
						throw new FrameworkException(
								MainetConstants.WorksManagement.EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + exception);
					}
				}
				dtosList.add(yearDetDto);
			}
		}
		return dtosList;
	}

	@Override
	@Transactional(readOnly = true)
	public WorkDefinitionDto getWorkRegisterByWorkId(Long workId, String raCode, Long orgId) {
		WorkDefinitionDto defDto = null;

		List<Object[]> listsOfDef = workDefinitionRepository.getWorkCompletionRegisterByWorkId(workId, raCode);
		if (!listsOfDef.isEmpty() && listsOfDef != null) {
			for (Object[] objects : listsOfDef) {
				defDto = new WorkDefinitionDto();
				defDto.setWorkId(Long.valueOf(objects[0].toString()));
				defDto.setWorkName(objects[1].toString());
				defDto.setLocationDesc(
						ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
								.getLocationNameById(Long.valueOf(objects[2].toString()), orgId));
				defDto.setAgreementFromDate(CommonUtility.dateToString((Date) objects[3]));
				defDto.setAgreementToDate(CommonUtility.dateToString((Date) objects[4]));
				defDto.setWorkOrderStartDate(CommonUtility.dateToString((Date) objects[5]));
				defDto.setContractorname(objects[7].toString());

			}
		}
		return defDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkCompletionRegisterDto> getWorkRegisterDetailsByWorkId(Long workId, String raCode, Long orgId) {
		List<WorkCompletionRegisterDto> registerList = new ArrayList<>();
		List<Object[]> listsOfDef = workDefinitionRepository.getRegisterDetailsByWorkId(workId, raCode);
		WorkCompletionRegisterDto regDto = null;
		if (!listsOfDef.isEmpty() && listsOfDef != null) {
			for (Object[] objects : listsOfDef) {
				regDto = new WorkCompletionRegisterDto();
				regDto.setWorkId(Long.valueOf(objects[0].toString()));
				regDto.setSorDIteamNo(objects[1].toString());
				regDto.setSorDDescription(objects[2].toString());
				regDto.setWorkEstimQuantity(new BigDecimal(objects[4].toString()));
				regDto.setSorBasicRate(new BigDecimal(objects[5].toString()));
				regDto.setWorkEstAmt(new BigDecimal(objects[6].toString()));
				if (objects[7] != null)
					regDto.setWorkActualQty(new BigDecimal(objects[7].toString()));
				if (objects[8] != null)
					regDto.setWorkActualAmt(new BigDecimal(objects[8].toString()));

				registerList.add(regDto);
			}
		}
		return registerList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkOrderGeneratedWorks(Long orgId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.findAllWorkOrderGeneratedWorks(orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			defEntityList.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional
	public void updateOverHeadAmount(Long workId, BigDecimal overheadAmount) {

		workDefinitionRepository.updateOverHeadAmount(workId, overheadAmount);
	}

	@Override
	@Transactional
	public VendorBillApprovalDTO getBudgetExpenditureDetails(VendorBillApprovalDTO billApprovalDTO) {
		VendorBillApprovalDTO vendorBillApprovalDTO = null;
		ResponseEntity<?> response = null;

		try {
			response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.SALARY_BILL_BUDGET_DETAILS);
			if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
				LOGGER.info("get Budget Expenditure Details done successfully ::");
				vendorBillApprovalDTO = (VendorBillApprovalDTO) RestClient.castResponse(response,
						VendorBillApprovalDTO.class);
			} else {
				LOGGER.error("get Budget Expenditure Details failed due to :"
						+ (response != null ? response.getBody() : MainetConstants.BLANK));

				throw new FrameworkException("get Budget Expenditure Details failed due to :"
						+ (response != null ? response.getBody() : MainetConstants.BLANK));
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
			throw new FrameworkException(ex);
		}
		return vendorBillApprovalDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkByOrgIdWithWorkStatus(Long orgId) {

		List<WorkDefinationEntity> entities = workDefinitionRepository.findAllWorkByWorkStatus(orgId);
		List<WorkDefinitionDto> list = new ArrayList<>();
		if (entities != null && !entities.isEmpty()) {
			entities.forEach(entity -> {
				WorkDefinitionDto dto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, dto);
				list.add(dto);
			});
		}
		return list;
	}

	@Override
	public WorkDefinitionDto getProjCodeAndWorkCode(String projCode, String workCode) {
		WorkDefinitionDto defDto = null;

		WorkDefinationEntity defEntity = workDefinitionRepository.getProjCodeWorkCode(projCode, workCode);

		if (defEntity != null) {
			defDto = new WorkDefinitionDto();
			defDto.setProjId(defEntity.getProjMasEntity().getProjId());
			defDto.setWorkId(defEntity.getWorkId());
			BeanUtils.copyProperties(defEntity, defDto);

		}
		return defDto;
	}

	@Override
	@Transactional
	public void saveSanctionDetailsApproval(WorkDefinitionSancDetDto definitionSancDetDto) {
		if (definitionSancDetDto != null) {
			WorkDefinitionSancDet definitionSancDet = new WorkDefinitionSancDet();
			BeanUtils.copyProperties(definitionSancDetDto, definitionSancDet);
			WorkDefinationEntity definationEntity = new WorkDefinationEntity();
			definationEntity.setWorkId(definitionSancDetDto.getWorkId());
			definitionSancDet.setWorkDefEntity(definationEntity);
			definitionSancDet.setSancNature("A");
			workDefinitionDao.saveSanctionDetails(definitionSancDet);
		}
	}

	@Override
	@Transactional
	public WorkDefinitionDto findWorkDefinitionByWorkId(Long workId, Long orgId) {
		WorkDefinitionDto defDto = null;
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		WorkDefinationEntity defEntity = workDefinitionRepository.findWorkDefinitionByWorkId(workId, orgId);
		if (defEntity != null) {
			defDto = new WorkDefinitionDto();
			BeanUtils.copyProperties(defEntity, defDto);
			if(!defEntity.getWdAssetInfoEntity().isEmpty()){
				List<WorkDefinationAssetInfoDto> assetInfoDtos=new ArrayList<WorkDefinationAssetInfoDto>();
				defEntity.getWdAssetInfoEntity().forEach(entity->{
					WorkDefinationAssetInfoDto assetInfoDto=new WorkDefinationAssetInfoDto();
					BeanUtils.copyProperties(entity, assetInfoDto);
					assetInfoDtos.add(assetInfoDto);
				});
				defDto.setAssetInfoDtos(assetInfoDtos);
			}
			setCommonDescDetails(defEntity, defDto, organisation);
		}
		return defDto;

	}

	@Override
	public void saveWorkDefinationYesrDet(WorkDefinitionDto workDefinitionDto, WorkDefinationYearDetDto definitionDto) {
		WorkDefinationYearDetEntity definationYearDetEntity = new WorkDefinationYearDetEntity();
		WorkDefinationEntity workDefinationEntity = new WorkDefinationEntity();
		definitionDto.setOrgId(workDefinitionDto.getOrgId());
		definitionDto.setCreatedBy(workDefinitionDto.getCreatedBy());
		definitionDto.setCreatedDate(workDefinitionDto.getCreatedDate());
		definitionDto.setLgIpMac(workDefinitionDto.getLgIpMac());
		if (workDefinitionDto.getUpdatedBy() != null) {
			definitionDto.setUpdatedBy(workDefinitionDto.getUpdatedBy());
		}
		if (workDefinitionDto.getLgIpMacUpd() != null) {
			definitionDto.setLgIpMacUpd(workDefinitionDto.getLgIpMacUpd());
		}
		if (workDefinitionDto.getUpdatedDate() != null) {
			definitionDto.setUpdatedDate(workDefinitionDto.getUpdatedDate());
		}
		BeanUtils.copyProperties(definitionDto, definationYearDetEntity);
		BeanUtils.copyProperties(workDefinitionDto, workDefinationEntity);
		definationYearDetEntity.setWorkDefEntity(workDefinationEntity);
		//D99427
		definationYearDetEntity.setYeActive("Y");
		WorkDefinationYearDetEntity detEntity = workDefYearRepo.save(definationYearDetEntity);
		try {
			WorkDefinationYearDetHistoryEntity defHistoryEntity = new WorkDefinationYearDetHistoryEntity();
			defHistoryEntity.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
			auditService.createHistory(detEntity, defHistoryEntity);
		} catch (Exception exception) {
			LOGGER.error("Exception ocours to create History of Work Defination Year Details  " + exception);
		}

	}

	@Override
	public void saveWardZoneDto(WorkDefinitionDto workDefinitionDto,
			List<WorkDefinationWardZoneDetailsDto> definationWardZoneDetailsDto) {

		WorkDefinationEntity definationEntity = new WorkDefinationEntity();
		BeanUtils.copyProperties(workDefinitionDto, definationEntity);

		definationWardZoneDetailsDto.forEach(dto -> {
			dto.setOrgId(workDefinitionDto.getOrgId());
			dto.setCreatedBy(workDefinitionDto.getCreatedBy());
			dto.setCreatedDate(workDefinitionDto.getCreatedDate());
			dto.setLgIpMac(workDefinitionDto.getLgIpMac());
			if (workDefinitionDto.getUpdatedBy() != null) {
				dto.setUpdatedBy(workDefinitionDto.getUpdatedBy());
			}
			if (workDefinitionDto.getLgIpMacUpd() != null) {
				dto.setLgIpMacUpd(workDefinitionDto.getLgIpMacUpd());
			}
			if (workDefinitionDto.getUpdatedDate() != null) {
				dto.setUpdatedDate(workDefinitionDto.getUpdatedDate());
			}
			WorkDefinationWardZoneDetails definationWardZoneDetails = new WorkDefinationWardZoneDetails();
			BeanUtils.copyProperties(dto, definationWardZoneDetails);
			definationWardZoneDetails.setWorkDefEntity(definationEntity);
			WorkDefinationWardZoneDetails entity = wardZoneRepo.save(definationWardZoneDetails);

		});

	}

	@Override
	public List<WorkDefinationYearDetDto> getYearDetailByWorkId(WorkDefinitionDto workDefinitionDto, Long orgId) {
		WorkDefinationEntity definationEntity = new WorkDefinationEntity();
		List<WorkDefinationYearDetDto> definationYearDetDto = new ArrayList<WorkDefinationYearDetDto>();
		BeanUtils.copyProperties(workDefinitionDto, definationEntity);

		List<WorkDefinationYearDetEntity> definationYearDetEntity = workDefYearRepo
				.getYearDetByWorkId(workDefinitionDto.getWorkId());

		definationYearDetEntity.forEach(entity -> {

			WorkDefinationYearDetDto yearDto = new WorkDefinationYearDetDto();
			BeanUtils.copyProperties(entity, yearDto);
			definationYearDetDto.add(yearDto);

		});

		return definationYearDetDto;
	}

	@Override
	@Transactional(readOnly = true)
	public ViewWorkDefinitionDto findAllWorkDefinitionByWorkCodeNo(String workCode, Long orgId) {
		// TODO Auto-generated method stubj
		ViewWorkDefinitionDto viewWorkDto = null;
		List<ViewWorkDefinationYearDetDto> yearDtos = new ArrayList<>();
		List<ViewWorkDefinationAssetInfoDto> assetInfoDtos = new ArrayList<>();
		List<ViewWorkDefinitionSancDetDto> sanctionDetails = new ArrayList<>();
		List<ViewWorkCompletionRegisterDto> regDtos = new ArrayList<>();
		List<ViewWorkDefinationWardZoneDetailsDto> wardZoneDto = new ArrayList<>();

		Long workid = workDefinitionRepository.findWorkIdByWorkCode(workCode, orgId);

		if (workid != null) {
			viewWorkDto = new ViewWorkDefinitionDto();
			WorkDefinitionDto workDefDto = findAllWorkDefinitionById(workid);
			BeanUtils.copyProperties(workDefDto, viewWorkDto);
			viewWorkDto.setWorkCategory(CommonMasterUtility.findLookUpDesc("ACL", orgId, workDefDto.getWorkCategory()));
			viewWorkDto.setLocStart(CommonMasterUtility.findLookUpDesc("UOA", orgId, workDefDto.getLocIdSt()));
			viewWorkDto.setLocEnd(CommonMasterUtility.findLookUpDesc("UOA", orgId, workDefDto.getLocIdEn()));
			if (viewWorkDto.getSouceOffund() != null)
				viewWorkDto.setSouceOffund(CommonMasterUtility.getHierarchicalLookUp(workDefDto.getSouceOffund(), orgId)
						.getDescLangFirst());
			viewWorkDto.setProjDept(departmentService.fetchDepartmentDescById(workDefDto.getProjDeptId()));
			if (workDefDto.getYearDtos() != null) {
				workDefDto.getYearDtos().forEach(dto -> {
					ViewWorkDefinationYearDetDto viewWorkDefinationYearDetDto = new ViewWorkDefinationYearDetDto();
					BeanUtils.copyProperties(dto, viewWorkDefinationYearDetDto);
					String financialyear = financialyearService.findYearById(dto.getFaYearId(), orgId).getFaFromDate()
							.toString().substring(0, 4);
					viewWorkDefinationYearDetDto.setFaYear(financialyear + "-" + financialyearService
							.findYearById(dto.getFaYearId(), orgId).getFaToDate().toString().substring(2, 4));

					yearDtos.add(viewWorkDefinationYearDetDto);
				});
				viewWorkDto.setYearDtos(yearDtos);
			}
			if (workDefDto.getAssetInfoDtos() != null) {
				workDefDto.getAssetInfoDtos().forEach(dto -> {
					ViewWorkDefinationAssetInfoDto viewWorkDefinationAssetInfoDto = new ViewWorkDefinationAssetInfoDto();
					BeanUtils.copyProperties(dto, viewWorkDefinationAssetInfoDto);
					assetInfoDtos.add(viewWorkDefinationAssetInfoDto);
				});

				viewWorkDto.setAssetInfoDtos(assetInfoDtos);
			}

			if (workDefDto.getSanctionDetails() != null) {
				workDefDto.getSanctionDetails().forEach(dto -> {
					ViewWorkDefinitionSancDetDto viewWorkDefinitionSancDetDto = new ViewWorkDefinitionSancDetDto();
					BeanUtils.copyProperties(dto, viewWorkDefinitionSancDetDto);
					sanctionDetails.add(viewWorkDefinitionSancDetDto);
				});
				viewWorkDto.setSanctionDetails(sanctionDetails);

			}

			if (workDefDto.getRegDtos() != null) {
				workDefDto.getRegDtos().forEach(dto -> {
					ViewWorkCompletionRegisterDto viewWorkCompletionRegisterDto = new ViewWorkCompletionRegisterDto();
					BeanUtils.copyProperties(dto, viewWorkCompletionRegisterDto);
					regDtos.add(viewWorkCompletionRegisterDto);
				});
				viewWorkDto.setRegDtos(regDtos);
			}

			if (workDefDto.getWardZoneDto() != null) {
				workDefDto.getWardZoneDto().forEach(dto -> {
					ViewWorkDefinationWardZoneDetailsDto ViewWorkDefinationWardZoneDetailsDto = new ViewWorkDefinationWardZoneDetailsDto();
					BeanUtils.copyProperties(dto, ViewWorkDefinationWardZoneDetailsDto);
					if (dto.getCodId1() != null)
						ViewWorkDefinationWardZoneDetailsDto.setCodId1(
								CommonMasterUtility.getHierarchicalLookUp(dto.getCodId1(), orgId).getDescLangFirst());
					if (dto.getCodId2() != null)
						ViewWorkDefinationWardZoneDetailsDto.setCodId2(
								CommonMasterUtility.getHierarchicalLookUp(dto.getCodId2(), orgId).getDescLangFirst());
					if (dto.getCodId3() != null) {
						ViewWorkDefinationWardZoneDetailsDto.setCodId3(
								CommonMasterUtility.getHierarchicalLookUp(dto.getCodId3(), orgId).getDescLangFirst());
					}
					if (dto.getCodId4() != null)
						ViewWorkDefinationWardZoneDetailsDto.setCodId4(
								CommonMasterUtility.getHierarchicalLookUp(dto.getCodId4(), orgId).getDescLangFirst());
					if (dto.getCodId5() != null)
						ViewWorkDefinationWardZoneDetailsDto.setCodId5(
								CommonMasterUtility.getHierarchicalLookUp(dto.getCodId5(), orgId).getDescLangFirst());
					wardZoneDto.add(ViewWorkDefinationWardZoneDetailsDto);

				});

				viewWorkDto.setWardZoneDto(wardZoneDto);
			}

		}
		return viewWorkDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<WorkDefinitionDto> findAllWorkdefinationforScehedularByOrgId(Long orgId) {
		final List<WorkDefinitionDto> dtoList = new ArrayList<>();
		List<WorkDefinationEntity> defEntityList = workDefinitionRepository.findAllWorkDefinitionsforSchedularByOrgId(orgId);
		if (defEntityList != null && !defEntityList.isEmpty()) {
			Organisation organisation = new Organisation();
			organisation.setOrgid(orgId);
			defEntityList.forEach(entity -> {
				WorkDefinitionDto defDto = new WorkDefinitionDto();
				BeanUtils.copyProperties(entity, defDto);
				dtoList.add(defDto);
			});
		}
		return dtoList;
	}
	
	@Override
	@Transactional
	public void updateWorkDefinitionLatLong(WorkDefinitionDto workDefDto, List<DocumentDetailsVO> attachmentList,
			RequestDTO requestDTO) {
		workDefDto.setWorkId(Long.valueOf(workDefDto.getWorkName()));
		WorkDefinitionDto dto=findWorkDefinitionByWorkId(workDefDto.getWorkId(),UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setIdfId(dto.getWorkcode());
		if(!attachmentList.isEmpty())
		fileUpload.doMasterFileUpload(attachmentList, requestDTO);
		workDefinitionRepository.updateWorkDefLatLong(workDefDto.getLatitude(),workDefDto.getLongitude(),workDefDto.getWorkId());
	}
	
	 @Override
	    @WebMethod(exclude = true)
	    @Transactional
	    public VendorBillApprovalDTO getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO) {
	        VendorBillApprovalDTO vendorBillApprovalDTO = null;
	        ResponseEntity<?> response = null;

	        try {
	            response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.COUNCIL_BILL_BUDGET_DETAILS);
	            if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
	                LOGGER.info("get Budget Expenditure Details done successfully ::");
	                vendorBillApprovalDTO = (VendorBillApprovalDTO) RestClient.castResponse(response,
	                        VendorBillApprovalDTO.class);
	            } else {
	                LOGGER.error("get Budget Expenditure Details failed due to :"
	                        + (response != null ? response.getBody() : MainetConstants.BLANK));

	                throw new FrameworkException("get Budget Expenditure Details failed due to :"
	                        + (response != null ? response.getBody() : MainetConstants.BLANK));
	            }
	        } catch (Exception ex) {
	            LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
	            throw new FrameworkException(ex);
	        }
	        return vendorBillApprovalDTO;
	    }
	
	@Override
	@Transactional
	public void updateBudgetHead(Long yearId, Long sacHeadId,Long orgId) {

		workDefYearRepo.updateBudgetHead(yearId, sacHeadId,orgId);
	}
}
