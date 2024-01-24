package com.abm.mainet.workManagement.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.workManagement.constants.WorkManagementConstant;
import com.abm.mainet.workManagement.dao.BIDMasterDao;
import com.abm.mainet.workManagement.datamodel.WMSRateMaster;
import com.abm.mainet.workManagement.domain.BIDMasterEntity;
import com.abm.mainet.workManagement.domain.BIDMasterHistEntity;
import com.abm.mainet.workManagement.domain.CommercialBIDDetailEntity;
import com.abm.mainet.workManagement.domain.CommercialBIDDetailHistEntity;
import com.abm.mainet.workManagement.domain.ItemRateBidDetailEntity;
import com.abm.mainet.workManagement.domain.ScheduleOfRateDetEntity;
import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;
import com.abm.mainet.workManagement.domain.TechnicalBIDDetailEntity;
import com.abm.mainet.workManagement.domain.TechnicalBIdHistEntity;
import com.abm.mainet.workManagement.domain.TenderMasterEntity;
import com.abm.mainet.workManagement.domain.TenderWorkEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;
import com.abm.mainet.workManagement.dto.BidMasterDto;
import com.abm.mainet.workManagement.dto.CommercialBIDDetailDto;
import com.abm.mainet.workManagement.dto.ItemRateBidDetailDto;
import com.abm.mainet.workManagement.dto.TechnicalBIDDetailDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.repository.BIDDetailRepository;
import com.abm.mainet.workManagement.repository.ScheduleOfRateRepository;
import com.abm.mainet.workManagement.repository.TenderInitiationRepository;
import com.abm.mainet.workManagement.repository.TenderWorkEntityRepository;
import com.abm.mainet.workManagement.repository.WorkDefinitionRepository;
import com.abm.mainet.workManagement.rest.dto.ExpiryItemsDto;
import com.abm.mainet.workManagement.rest.dto.PurchaseRequistionDetDto;
import com.abm.mainet.workManagement.rest.dto.PurchaseRequistionDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
@Service
public class TenderInitiationServiceImpl implements TenderInitiationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenderInitiationServiceImpl.class);

	@Autowired
	private TenderInitiationRepository tenderRepository;
	
	@Autowired
	TenderWorkEntityRepository tenderWorkEntityRepo;

	@Resource
	@Autowired
	private BIDDetailRepository bidDetailRepository;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private WmsProjectMasterService projectMasterService;

	@Autowired
	private TbOrganisationService organisationService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private WorkDefinitionService workDefinitionService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	ContractAgreementRepository contractRepo;

	@Autowired
	private AuditService auditService;

	@Autowired
	private BIDMasterDao bidMasterDao;
	
	@Autowired
	private WorkDefinitionRepository workDefinitionRepository;

	@Resource
	TbDepartmentService tbDepartmentService;
	
	@Autowired
    private ScheduleOfRateRepository scheduleOfRateRepository;
	
	/**
	 * it is used to create tender initiation details
	 * 
	 * @param initiationDto
	 * @param attachments
	 */
	@Override
	@Transactional
	public TenderMasterDto createTenderInitiation(TenderMasterDto initiationDto, List<DocumentDetailsVO> attachmentList,
			RequestDTO requestDTO) {
		TenderMasterEntity initiationEntity = new TenderMasterEntity();

		if (!initiationDto.getPreBidMeetDateDesc().isEmpty()) {
			initiationDto.setPreBidMeetDate(stringToDateConvert(initiationDto.getPreBidMeetDateDesc()));
		}
		if (!initiationDto.getPublishDateDesc().isEmpty()) {
			initiationDto.setPublishDate(stringToDateConvert(initiationDto.getPublishDateDesc()));
		}
		if (!initiationDto.getTechnicalOpenDateDesc().isEmpty()) {
			initiationDto.setTechnicalOpenDate(stringToDateConvert(initiationDto.getTechnicalOpenDateDesc()));
		}

		if (!initiationDto.getFinancialeOpenDateDesc().isEmpty()) {
			initiationDto.setFinancialeOpenDate(stringToDateConvert(initiationDto.getFinancialeOpenDateDesc()));
		}

		BeanUtils.copyProperties(initiationDto, initiationEntity);
		// set project master details
		TbWmsProjectMaster projectMas = new TbWmsProjectMaster();
		projectMas.setProjId(initiationDto.getProjId());
		projectMas.setDpDeptId(initiationDto.getDeptId());
		initiationEntity.setProjMasEntity(projectMas);

		// initiationEntity.setStatus(MainetConstants.Y_FLAG);
		initiationEntity.setCreatedDate(new Date());
		// initiationEntity.setLgIpMac(Utility.getMacAddress());

		List<TenderWorkEntity> workEntityList = new ArrayList<>();
		AtomicLong alWorkId=new AtomicLong();
		if(MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(initiationDto.getDeptCode())) {
			Long workId=workDefinitionRepository.findWorkIdByWorkCode(MainetConstants.DEPT_SHORT_NAME.STORE,initiationDto.getOrgId());
			alWorkId.set(workId);
		 }
		
		initiationDto.getWorkDto().parallelStream().forEach(work -> {
			if (work.isInitiated()) {
				TenderWorkEntity workEntity = new TenderWorkEntity();
				BeanUtils.copyProperties(work, workEntity);

				// set work definition entity
				WorkDefinationEntity workDefEntity = new WorkDefinationEntity();
				if(MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(initiationDto.getDeptCode())) {
					workDefEntity.setWorkId(alWorkId.get());
					if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(initiationDto.getProjectCode()))
					  workEntity.setPrId(work.getWorkId());//set Purchase Requisition Id 
					else
					  workEntity.setExpiryId(work.getWorkId()); //set Disposal Id 
				}else {
					workDefEntity.setWorkId(work.getWorkId());
				}
				
				workEntity.setWorkDefinationEntity(workDefEntity);

				// set master entity
				workEntity.setTenderMasEntity(initiationEntity);

				setNewWorkCommonDetails(initiationDto, workEntity);

				// by default selected work definition status should be active
				workEntityList.add(workEntity);
			}
		});
		initiationEntity.setTenderWorkList(workEntityList);
		if (initiationDto.getServiceFlag() != null && initiationDto.getServiceFlag().contentEquals("A")) {
			String initiationNo = generateInitiationNumber(initiationEntity);
			initiationEntity.setInitiationNo(initiationNo);
			initiationEntity.setInitiationDate(new Date());
			initiationDto.setInitiationNo(initiationNo);

		}
		TenderMasterEntity savedEntity = tenderRepository.save(initiationEntity);
		
		if(MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(initiationDto.getDeptCode())) {
			List<Long> workIdList =new ArrayList<>();
			initiationDto.getWorkDto().parallelStream().forEach(work -> {
				if (work.isInitiated()) {
					workIdList.add(work.getWorkId());
				}
			});
			
			if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(initiationDto.getProjectCode())) {
				PurchaseRequistionDto purRequistionDto =new PurchaseRequistionDto();
				purRequistionDto.setOrgId(initiationDto.getOrgId());
				purRequistionDto.setUpdatedBy(initiationDto.getCreatedBy());
				purRequistionDto.setStatus(MainetConstants.FlagI);  // Tender Initiation
				purRequistionDto.setPrIds(workIdList);
				RestClient.callRestTemplateClient(purRequistionDto, ServiceEndpoints.UPDATE_PURCHASE_REQ_STATUS);
			}else {
				ExpiryItemsDto expiryItemsDto =new ExpiryItemsDto();
				expiryItemsDto.setOrgId(initiationDto.getOrgId());
				expiryItemsDto.setUpdatedBy(initiationDto.getCreatedBy());
				expiryItemsDto.setStatus(MainetConstants.FlagI);  // Tender Initiation
				expiryItemsDto.setExpiryIds(workIdList);
				RestClient.callRestTemplateClient(expiryItemsDto, ServiceEndpoints.UPDATE_EXPIRY_DISPOSAL_STATUS);
			}
		}

		if (attachmentList != null && !attachmentList.isEmpty()) {
			requestDTO.setIdfId(projectMasterService.getProjectMasterByProjId(initiationDto.getProjId()).getProjCode()
					+ MainetConstants.WINDOWS_SLASH + savedEntity.getTndId());
			fileUpload.doMasterFileUpload(attachmentList, requestDTO);
		}
		
		if (!"NA".equalsIgnoreCase(initiationDto.getProcessName()) && initiationDto.getServiceFlag() != null && initiationDto.getServiceFlag().equalsIgnoreCase("A")) {
			initateWorkFlowForTenderInitation(savedEntity, requestDTO);
		}
		return initiationDto;
	}

	private void initateWorkFlowForTenderInitation(TenderMasterEntity savedEntity, RequestDTO requestDTO) {
		boolean checklist = false;
		ApplicantDetailDTO applicantDetailsDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationData = new ApplicationMetadata();
		applicationData.setIsCheckListApplicable(checklist);
		applicationData.setOrgId(savedEntity.getOrgId());
		applicationData.setReferenceId(savedEntity.getInitiationNo());
		applicantDetailsDto.setServiceId(requestDTO.getServiceId());
		applicantDetailsDto.setDepartmentId(requestDTO.getDeptId());		
		applicantDetailsDto.setUserId(savedEntity.getCreatedBy());
		ApplicationContextProvider.getApplicationContext().getBean(CommonService.class)
				.initiateWorkflowfreeService(applicationData, applicantDetailsDto);
	}

	private String generateInitiationNumber(TenderMasterEntity initiationEntity) {
		String initiationNo = null;
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		if (financiaYear != null) {
			String ulbCode = organisationService.findById(initiationEntity.getOrgId()).getOrgShortNm();
			String deptCode = departmentService.getDeptCode(initiationEntity.getProjMasEntity().getDpDeptId());
			String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
			// generate sequence number.
			final Long sequence = seqGenFunctionUtility.generateSequenceNo(
					MainetConstants.WorksManagement.WORKS_MANAGEMENT,
					MainetConstants.WorksManagement.TB_WMS_TENDER_MAST,
					MainetConstants.WorksManagement.TND_INITIATIONNO, initiationEntity.getOrgId(),
					MainetConstants.FlagC, financiaYear.getFaYear());

			// generate initiation code.
			initiationNo = ulbCode + MainetConstants.WINDOWS_SLASH + MainetConstants.WorksManagement.IN
					+ MainetConstants.WINDOWS_SLASH + deptCode + MainetConstants.WINDOWS_SLASH
					+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)
					+ MainetConstants.WINDOWS_SLASH + finacialYear;

		} else {
			throw new FrameworkException("Exception Occured when create Initiation No Initation");
		}
		return initiationNo;
	}

	/**
	 * it is used to update tender initiation details.
	 * 
	 * @param initiationDto
	 * @param attachments   documents
	 */
	@Override
	@Transactional
	public void updateTenderInitiation(TenderMasterDto initiationDto, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, List<Long> removedWorkIds, Long deletedFileId) {
		TenderMasterEntity initiationEntity = new TenderMasterEntity();
		if (!initiationDto.getPreBidMeetDateDesc().isEmpty()) {
			initiationDto.setPreBidMeetDate(stringToDateConvert(initiationDto.getPreBidMeetDateDesc()));
		}
		if (!initiationDto.getPublishDateDesc().isEmpty()) {
			initiationDto.setPublishDate(stringToDateConvert(initiationDto.getPublishDateDesc()));
		}

		if (!initiationDto.getTechnicalOpenDateDesc().isEmpty()) {
			initiationDto.setTechnicalOpenDate(stringToDateConvert(initiationDto.getTechnicalOpenDateDesc()));
		}
		if (!initiationDto.getFinancialeOpenDateDesc().isEmpty()) {
			initiationDto.setFinancialeOpenDate(stringToDateConvert(initiationDto.getFinancialeOpenDateDesc()));
		}

		BeanUtils.copyProperties(initiationDto, initiationEntity);

		TbWmsProjectMaster projectMas = new TbWmsProjectMaster();
		projectMas.setProjId(initiationDto.getProjId());
		initiationEntity.setProjMasEntity(projectMas);

		// set initiation common details
		initiationEntity.setUpdatedDate(new Date());
		// initiationEntity.setLgIpMacUpd(initiationDto.getLgIpMacUpd());

		List<TenderWorkEntity> workEntityList = new ArrayList<>();
		initiationDto.getWorkDto().forEach(work -> {
			TenderWorkEntity workEntity = null;
			if (work.isInitiated()) {
				workEntity = new TenderWorkEntity();
				BeanUtils.copyProperties(work, workEntity);
				// set work definition entity
				WorkDefinationEntity workDefEntity = new WorkDefinationEntity();
				workDefEntity.setWorkId(work.getWorkId());
				workEntity.setWorkDefinationEntity(workDefEntity);
				// set master entity
				workEntity.setTenderMasEntity(initiationEntity);

				if (work.getTndWId() != null) {
					// set common details
					setUpdateWorkCommonDetails(initiationDto, workEntity);
				} else if (work.getTndWId() == null) {
					// by default selected work definition status should be active
					setNewWorkCommonDetails(initiationDto, workEntity);
					workEntity.setCreatedBy(initiationDto.getUpdatedBy());
				}
				workEntityList.add(workEntity);
			}
		});
		initiationEntity.setTenderWorkList(workEntityList);
		tenderRepository.save(initiationEntity);

		if (removedWorkIds != null && !removedWorkIds.isEmpty()) {
			tenderRepository.deleteWorksbyIds(removedWorkIds);
		}

		// update documents
		if (attachments != null && !attachments.isEmpty()) {
			requestDTO.setIdfId(projectMasterService.getProjectMasterByProjId(initiationDto.getProjId()).getProjCode()
					+ MainetConstants.WINDOWS_SLASH + initiationDto.getTndId());
			fileUpload.doMasterFileUpload(attachments, requestDTO);
			if (deletedFileId != null) {
				List<Long> deletedDocFiles = new ArrayList<>();
				deletedDocFiles.add(deletedFileId);
				ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class)
						.updateRecord(deletedDocFiles, initiationDto.getUpdatedBy(), MainetConstants.FlagD);
			}
		}
	}

	/**
	 * common method to set TenderWorkEntity update time common details like updated
	 * date, update IP address, and updated by.
	 * 
	 * @param initiationDto
	 * @param workEntity
	 */
	private void setUpdateWorkCommonDetails(TenderMasterDto initiationDto, TenderWorkEntity workEntity) {
		workEntity.setUpdatedDate(new Date());
		workEntity.setUpdatedBy(initiationDto.getUpdatedBy());
		workEntity.setLgIpMacUpd(initiationDto.getLgIpMacUpd());
	}

	/**
	 * common method to set TenderWorkEntity create time common details like created
	 * date,created by and created IP address.
	 * 
	 * @param initiationDto
	 * @param workEntity
	 */
	private void setNewWorkCommonDetails(TenderMasterDto initiationDto, TenderWorkEntity workEntity) {
		workEntity.setOrgId(initiationDto.getOrgId());
		workEntity.setCreatedDate(new Date());
		workEntity.setCreatedBy(initiationDto.getCreatedBy());
		workEntity.setLgIpMac(initiationDto.getLgIpMac());
	}

	/**
	 * get prepared tender details by tndId
	 * 
	 * @param tndId
	 * @return TenderMasterDto
	 */
	@Override
	@Transactional(readOnly = true)
	public TenderMasterDto getPreparedTenderDetails(Long tndId) {
		TenderMasterDto tenderDto = null;
		TenderMasterEntity tndEntity = tenderRepository.findOne(tndId);
		tenderDto = mapTenderEntityToTenderDto(tenderDto, tndEntity);
		return tenderDto;
	}

	private TenderMasterDto mapTenderEntityToTenderDto(TenderMasterDto tenderDto, TenderMasterEntity tndEntity) {
		if (tndEntity != null) {
			tenderDto = new TenderMasterDto();
			List<TenderWorkDto> workDtoList = new ArrayList<>();
			tenderDto.setPreBidMeetDateDesc(TimeToStringConvert(tndEntity.getPreBidMeetDate()));
			tenderDto.setPublishDateDesc(TimeToStringConvert(tndEntity.getPublishDate()));
			tenderDto.setTechnicalOpenDateDesc(TimeToStringConvert(tndEntity.getTechnicalOpenDate()));
			tenderDto.setFinancialeOpenDateDesc(TimeToStringConvert(tndEntity.getFinancialeOpenDate()));
			// Defect #78118
			tenderDto.setIssueFromDateDesc(Utility.dateToString(tndEntity.getIssueFromDate()));
			tenderDto.setIssueToDateDesc(Utility.dateToString(tndEntity.getIssueToDate()));
			tenderDto.setResolutionDateDesc(Utility.dateToString(tndEntity.getResolutionDate()));
			tenderDto.setDeptId(tndEntity.getProjMasEntity().getDpDeptId());
			BeanUtils.copyProperties(tndEntity, tenderDto);
			setDescriptionDetailsForTender(tndEntity, tenderDto);
			for (TenderWorkEntity workEntity : tndEntity.getTenderWorkList()) {
				TenderWorkDto workDto = new TenderWorkDto();
				BeanUtils.copyProperties(workEntity, workDto);
				if (workDto.getTndLOANo() != null) {
					workDto.setTenderInitiated(true);
					tenderDto.setLoaGenerated(true);
				}
				workDtoList.add(workDto);
				setWorkCommonDetails(workEntity, workDto);
			}
			tenderDto.setWorkDto(workDtoList);
		}
		return tenderDto;
	}

	// set work common details like work id, work name, work code, work assignee
	private void setWorkCommonDetails(TenderWorkEntity workEntity, TenderWorkDto workDto) {
		workDto.setTndId(workEntity.getTenderMasEntity().getTndId());
		if(null != workEntity.getPrId()) {
			getPurchaseRequistionData(workDto);
		}/*else if() {
			//Disposal work is pending
			getPurchaseRequistionData(workDto);
		}*/else {
			workDto.setWorkCode(workEntity.getWorkDefinationEntity().getWorkcode());
			workDto.setWorkName(workEntity.getWorkDefinationEntity().getWorkName());
		}
		workDto.setWorkId(workEntity.getWorkDefinationEntity().getWorkId());
		workDto.setWorkTypeCode(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
				workEntity.getWorkDefinationEntity().getWorkType(), workEntity.getOrgId(), "WRT").getLookUpCode());
		if (workEntity.getEmployee() != null) {
			workDto.setWorkAssignee(workEntity.getEmployee().getEmpId());
			workDto.setWorkAssigneeName(workEntity.getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
					+ workEntity.getEmployee().getEmplname());
		}
		if (workEntity.getVendorMaster() != null) {
			workDto.setVenderId(workEntity.getVendorMaster().getVmVendorid());
			workDto.setVendorName(workEntity.getVendorMaster().getVmVendorname());
			workDto.setVendorAddress(workEntity.getVendorMaster().getVmVendoradd());
			workDto.setVenderTypeId(workEntity.getVendorMaster().getCpdVendortype());
		}
	}
	
	private void getPurchaseRequistionData(TenderWorkDto workDto) {
		List<Long> ids =new ArrayList<>();
		 ids.add(workDto.getPrId());
		  
		  PurchaseRequistionDto dto =new PurchaseRequistionDto();
		  dto.setOrgId(workDto.getOrgId());
		  dto.setPrIds(ids);
		  final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(dto,
		            ServiceEndpoints.FETCH_PURCHASE_REQ);
		   if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
			   final LinkedHashMap<String, String> responseMap = (LinkedHashMap<String, String>) responseEntity.getBody();
			   
			   workDto.setWorkCode(responseMap.get(workDto.getPrId().toString()));
			   workDto.setWorkName("");
				 
		   }else {
			  
			   workDto.setWorkCode("");
			   workDto.setWorkName("");
				  
		   }
	}

	/**
	 * used to get all created tender.
	 * 
	 * @param orgId
	 * @return List<TenderMasterDto>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TenderMasterDto> getAllTenders(Long orgId) {
		List<TenderMasterDto> tenderList = new ArrayList<>();
		List<TenderMasterEntity> entityList = tenderRepository.findByOrgId(orgId);
		if (entityList != null && !entityList.isEmpty()) {
			entityList.forEach(entity -> {
				TenderMasterDto tenderDto = new TenderMasterDto();
				BeanUtils.copyProperties(entity, tenderDto);
				setDescriptionDetailsForTender(entity, tenderDto);
				tenderList.add(tenderDto);
			});
		}
		return tenderList;
	}

	/**
	 * set description details like vendor class, vendor category from particular id
	 * for view purpose
	 * 
	 * @param entity
	 * @param tenderDto with description details
	 */
	private void setDescriptionDetailsForTender(TenderMasterEntity entity, TenderMasterDto tenderDto) {
		tenderDto.setProjId(entity.getProjMasEntity().getProjId());
		tenderDto.setDeptId(entity.getProjMasEntity().getDpDeptId());
		tenderDto.setProjectName(entity.getProjMasEntity().getProjNameEng());
		tenderDto.setProjectCode(entity.getProjMasEntity().getProjCode());
		Organisation organisation = new Organisation();
		organisation.setOrgid(entity.getOrgId());
		tenderDto.setTenderCategoryDesc(CommonMasterUtility
				.getNonHierarchicalLookUpObject(entity.getTenderCategory(), organisation).getLookUpDesc());
		if (tenderDto.getInitiationDate() != null) {
			tenderDto.setInitiationDateDesc(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getInitiationDate()));

		}
		if (StringUtils.isNotEmpty(tenderDto.getStatus()) && tenderDto.getStatus().equals(MainetConstants.FlagP)) {
			tenderDto.setStatusDesc(MainetConstants.TASK_STATUS_PENDING);
		}
		if (StringUtils.isNotEmpty(tenderDto.getStatus()) && tenderDto.getStatus().equals(MainetConstants.FlagA)) {
			tenderDto.setStatusDesc(MainetConstants.TASK_STATUS_APPROVED);
		}
		if (StringUtils.isNotEmpty(tenderDto.getStatus()) && tenderDto.getStatus().equals(MainetConstants.FlagR)) {
			tenderDto.setStatusDesc(MainetConstants.TASK_STATUS_REJECTED);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getAllActiveTenderProjects(Long orgId) {
		return tenderRepository.getAllActiveTenderProjects(orgId);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<TenderMasterDto> searchTenderDetails(Long orgid, Long projId, String initiationNo, Date initiationDate,
			Long tenderCategory, String flag) {
		List<TenderMasterDto> tenderList = new ArrayList<>();
		
		List<TenderMasterEntity> entityList = tenderRepository.searchTenderDetails(orgid, projId, initiationNo,
				initiationDate, tenderCategory, flag);
		if (entityList != null && !entityList.isEmpty()) {
			entityList.forEach(entity -> {
				TenderMasterDto tenderDto = new TenderMasterDto();
				BeanUtils.copyProperties(entity, tenderDto);
				setDescriptionDetailsForTender(entity, tenderDto);
				List<TenderWorkDto> tenderWorkList = new ArrayList<TenderWorkDto>();
				tenderWorkEntityRepo.findByTenderMasEntity(entity).forEach(technicalEntity -> {
					TenderWorkDto bidDetailDto = new TenderWorkDto();
					BeanUtils.copyProperties(technicalEntity, bidDetailDto);
					tenderWorkList.add(bidDetailDto);
				});
				if(!tenderWorkList.isEmpty()) {
				tenderDto.setTndLOANo(tenderWorkList.get(0).getTndLOANo());
				tenderList.add(tenderDto);
				}
			});
		}
		return tenderList;
	}

	/**
	 * to delete prepared tender details
	 * 
	 * @param tndId : tender Id
	 */
	@Override
	@Transactional
	public void deleteTender(Long tndId) {
		tenderRepository.delete(tndId);
	}

	/**
	 * this service is used to initiate tender details and generate initiation
	 * number of tender
	 * 
	 * @param tenderId
	 */
	@Override
	@Transactional
	public String initiateTender(Long tenderId, Long orgId) {
		String result = null;
		try {
			// tender initiation date is current date so finding financial year for current
			// date.
			FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
			if (financiaYear != null) {

				// find organization code.
				String ulbCode = organisationService.findById(orgId).getOrgShortNm();
				TenderMasterEntity tenderEntity = tenderRepository.findOne(tenderId);

				// find department code.
				String deptCode = departmentService.getDeptCode(tenderEntity.getProjMasEntity().getDpDeptId());

				// get financial year from date & end date and generate financial year as like:
				// 2018-19 format for initiation code
				String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(),
						financiaYear.getFaToDate());

				// generate sequence number.
				final Long sequence = seqGenFunctionUtility.generateSequenceNo(
						MainetConstants.WorksManagement.WORKS_MANAGEMENT,
						MainetConstants.WorksManagement.TB_WMS_TENDER_MAST,
						MainetConstants.WorksManagement.TND_INITIATIONNO, orgId, MainetConstants.FlagC,
						financiaYear.getFaYear());

				// generate initiation code.
				String initNo = ulbCode + MainetConstants.WINDOWS_SLASH + MainetConstants.WorksManagement.IN
						+ MainetConstants.WINDOWS_SLASH + deptCode + MainetConstants.WINDOWS_SLASH
						+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)
						+ MainetConstants.WINDOWS_SLASH + finacialYear;
				tenderEntity.setInitiationNo(initNo);

				// set initiation date as current date
				tenderEntity.setInitiationDate(new Date());
				tenderRepository.save(tenderEntity);
				List<Long> worksId = new ArrayList<>();

				// get tender's all work Id
				tenderEntity.getTenderWorkList().forEach(work -> {
					worksId.add(work.getWorkDefinationEntity().getWorkId());
				});

				// update all tender work status from approved work to initiated work by
				// updating flag 'A' to 'I'.
				workDefinitionService.updateWorksStatusToInitiated(worksId);
				result = MainetConstants.Y_FLAG;
			} else {
				result = "Tender can not initiated. No Financial year defined for Date : "
						+ Utility.dateToString(new Date());
			}
		} catch (Exception ex) {
			throw new FrameworkException("Exception occured while Initiate tender : " + ex);
		}
		return result;
	}

	@Override
	@Transactional
	public void saveTenderUpdateDetails(TenderMasterDto initiationDto, Organisation org) {
		TenderMasterEntity initiationEntity = new TenderMasterEntity();
		initiationDto.setPreBidMeetDate(stringToDateConvert(initiationDto.getPreBidMeetDateDesc()));
		initiationDto.setPublishDate(stringToDateConvert(initiationDto.getPublishDateDesc()));
		initiationDto.setTechnicalOpenDate(stringToDateConvert(initiationDto.getTechnicalOpenDateDesc()));
		initiationDto.setFinancialeOpenDate(stringToDateConvert(initiationDto.getFinancialeOpenDateDesc()));
		BeanUtils.copyProperties(initiationDto, initiationEntity);

		TbWmsProjectMaster projectMas = new TbWmsProjectMaster();
		projectMas.setProjId(initiationDto.getProjId());
		initiationEntity.setProjMasEntity(projectMas);

		// set initiation common details
		initiationEntity.setUpdatedDate(new Date());
		initiationEntity.setLgIpMacUpd(initiationDto.getLgIpMacUpd());
		List<Long> workIds = new ArrayList<>();
		List<TenderWorkEntity> workEntityList = new ArrayList<>();
		initiationDto.getWorkDto().forEach(work -> {
			if (work.isTenderInitiated()) {
				TenderWorkEntity workEntity = new TenderWorkEntity();
				BeanUtils.copyProperties(work, workEntity);

				// set work definition entity
				WorkDefinationEntity workDefEntity = new WorkDefinationEntity();
				workDefEntity.setWorkId(work.getWorkId());
				workEntity.setWorkDefinationEntity(workDefEntity);

				if (workEntity.getTndLOANo() == null) {
					workEntity.setTndLOANo(generateLOA(initiationEntity.getTndId(), initiationEntity.getOrgId()));
					workEntity.setTndLOADate(new Date());
				}

				// set master entity
				workEntity.setTenderMasEntity(initiationEntity);
				// set vendor master details
				TbAcVendormasterEntity venderMas = new TbAcVendormasterEntity();
				venderMas.setVmVendorid(work.getVenderId());
				workEntity.setVendorMaster(venderMas);

				// by default selected work definition status should be active
				workEntityList.add(workEntity);

				workIds.add(work.getWorkId());
			}
		});
		initiationEntity.setTenderWorkList(workEntityList);
		ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
				.findShortCodeByOrgId("TDA", org.getOrgid());
		LookUp lookup = null;
		if (master != null)
			lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getSmProcessId(),UserSession.getCurrent().getOrganisation());
		if (lookup != null && !lookup.getLookUpCode().equals(MainetConstants.CommonConstants.NA))
			initiationEntity.setStatus("P");
		TenderMasterEntity savedEntity = tenderRepository.save(initiationEntity);
		if (lookup != null && !lookup.getLookUpCode().equals(MainetConstants.CommonConstants.NA))
			initateWorkFlowForTenderAward(savedEntity);
		
		initiationDto.getWorkDto().forEach(workDto -> {
			if (workDto.isTenderInitiated() == true && initiationDto.isLoaGenerated() == false) {
				TbAcVendormasterEntity vendorEntity = contractRepo.getVenderbyId(workDto.getOrgId(),
						workDto.getVenderId());
				final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();

				if (vendorEntity != null) {
					smsDto.setMobnumber(vendorEntity.getMobileNo());
					smsDto.setUserId(org.getUserId());
					smsDto.setAppName(vendorEntity.getVmVendorname());
					smsDto.setAppNo(initiationDto.getTenderNo());
					smsDto.setEmail(vendorEntity.getEmailId());
					smsDto.setOrgId(vendorEntity.getOrgid());
					smsDto.setTenantNo(initiationDto.getTenderNo());
					smsDto.setTenderDate(initiationDto.getTenderDate().toString());
					String menuUrl = "TenderUpdate.html";
					org.setOrgid(vendorEntity.getOrgid());
					int langId = Utility.getDefaultLanguageId(org);
					iSMSAndEmailService.sendEmailSMS("WMS", menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED,
							smsDto, org, langId);
				}
			}
		});

		// update all tender work status as 'T' that means these works are used for
		// tender.
		if (!workIds.isEmpty()) {
			workDefinitionService.updateWorkStatusAsTendered(workIds);
		}
		
		if (MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(initiationDto.getDeptCode())) {
			List<Long> workIdList = new ArrayList<>();
			if (WorkManagementConstant.Tender.PURCHASE_REQ.equals(initiationDto.getProjectCode())) {
				initiationDto.getWorkDto().parallelStream().forEach(work -> workIdList.add(work.getPrId()));
				PurchaseRequistionDto purRequistionDto = new PurchaseRequistionDto();
				purRequistionDto.setOrgId(initiationDto.getOrgId());
				purRequistionDto.setUpdatedBy(initiationDto.getCreatedBy());
				purRequistionDto.setStatus(MainetConstants.FlagT);  // Tender Execution
				purRequistionDto.setPrIds(workIdList);
				RestClient.callRestTemplateClient(purRequistionDto, ServiceEndpoints.UPDATE_PURCHASE_REQ_STATUS);
			} else {
				initiationDto.getWorkDto().parallelStream().forEach(work -> workIdList.add(work.getExpiryId()));
				ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();
				expiryItemsDto.setOrgId(initiationDto.getOrgId());
				expiryItemsDto.setUpdatedBy(initiationDto.getCreatedBy());
				expiryItemsDto.setStatus(MainetConstants.FlagT);   // Tender Execution
				expiryItemsDto.setExpiryIds(workIdList);
				RestClient.callRestTemplateClient(expiryItemsDto, ServiceEndpoints.UPDATE_EXPIRY_DISPOSAL_STATUS);
			}
		}
		
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation organisation = new Organisation();
		organisation.setOrgid(savedEntity.getOrgId());
		smsDto.setTenantNo(savedEntity.getTenderNo());
		smsDto.setTenderDate(savedEntity.getTenderDate().toString());
		String works = savedEntity.getTenderWorkList().stream().map(tw -> tw.getWorkDefinationEntity().getWorkName())
				.collect(Collectors.joining(MainetConstants.operator.COMMA));
		smsDto.setTenderWorkName(works);

	}

	/**
	 * find all initiated tender based on orgId which has tender number not null
	 * 
	 * @param orgId
	 * @return List<TenderMasterDto>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TenderMasterDto> getAllInitiatedTenders(Long orgId) {
		List<TenderMasterDto> tenderList = new ArrayList<>();
		List<TenderMasterEntity> entityList = tenderRepository.getAllInitiatedTenders(orgId);
		if (entityList != null && !entityList.isEmpty()) {
			entityList.forEach(entity -> {
				TenderMasterDto tenderDto = new TenderMasterDto();
				BeanUtils.copyProperties(entity, tenderDto);
				setDescriptionDetailsForTender(entity, tenderDto);
				tenderList.add(tenderDto);
			});
		}
		return tenderList;
	}

	@Override
	@Transactional
	public void updateTenderWorkAssignee(List<Long> workId, Long assigneeId, Date assignedDate,
			List<Long> removedAssignedWorkId) {
		try {
			if (workId != null && !workId.isEmpty()) {
				tenderRepository.updateTenderWorkAssignee(workId, assigneeId, assignedDate);
				if (removedAssignedWorkId != null && !removedAssignedWorkId.isEmpty()) {
					tenderRepository.deleteTenderWorkAssignee(removedAssignedWorkId);
				}
			}
		} catch (Exception ex) {
			throw new FrameworkException("Exception while update tender work assignee", ex);

		}
	}

	public String generateLOA(Long tndId, Long orgId) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		// find organization code.
		String ulbCode = organisationService.findById(orgId).getOrgShortNm();
		TenderMasterEntity tenderEntity = tenderRepository.findOne(tndId);

		// find department code.
		String deptCode = departmentService.getDeptCode(tenderEntity.getProjMasEntity().getDpDeptId());

		// get financial year from date & end date and generate financial year as like:
		// 2018-19 format for initiation code
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

		// generate sequence number.
		final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
				MainetConstants.WorksManagement.TB_WMS_TENDER_WORK, MainetConstants.WorksManagement.TND_LOA_NO, orgId,
				MainetConstants.FlagC, financiaYear.getFaYear());

		// generate initiation code.
		return (ulbCode + MainetConstants.WINDOWS_SLASH + MainetConstants.WorksManagement.LOA
				+ MainetConstants.WINDOWS_SLASH + deptCode + MainetConstants.WINDOWS_SLASH
				+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)
				+ MainetConstants.WINDOWS_SLASH + finacialYear);
	}

	/**
	 * get tender master details by tender Id
	 * 
	 * @param tenderId
	 * @return TenderMasterDto
	 */
	@Override
	@Transactional(readOnly = true)
	public TenderMasterDto getTenderDetailsByTenderId(Long tenderId) {

		TenderMasterDto tenderDto = null;
		TenderMasterEntity tndEntity = tenderRepository.getTenderById(tenderId);
		tenderDto = mapTenderEntityToTenderDto(tenderDto, tndEntity);
		return tenderDto;
	}

	/**
	 * used to get LOA details
	 */
	@Override
	@Transactional(readOnly = true)
	public TenderWorkDto generateLOA(Long tndId, long orgid) {
		TenderWorkDto workDto = new TenderWorkDto();
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
		TenderWorkEntity tenderWorkEntity = tenderRepository.generateLOA(tndId, orgid);
		
		BeanUtils.copyProperties(tenderWorkEntity, workDto);
		BeanUtils.copyProperties(tenderWorkEntity.getTenderMasEntity(), workDto.getMasterDto());
		setWorkCommonDetails(tenderWorkEntity, workDto);
		workDto.setProjectName(tenderWorkEntity.getTenderMasEntity().getProjMasEntity().getProjNameEng());
		workDto.setTenderNo(tenderWorkEntity.getTenderMasEntity().getTenderNo());
		workDto.setTenderDate(tenderWorkEntity.getTenderMasEntity().getTenderDate());
		}
		else {
			TenderWorkEntity tenderWorkEntity = tenderRepository.generateLOANo(tndId, orgid);
			
			BeanUtils.copyProperties(tenderWorkEntity, workDto);
			BeanUtils.copyProperties(tenderWorkEntity.getTenderMasEntity(), workDto.getMasterDto());
			setWorkCommonDetails(tenderWorkEntity, workDto);
			workDto.setProjectName(tenderWorkEntity.getTenderMasEntity().getProjMasEntity().getProjNameEng());
			workDto.setTenderNo(tenderWorkEntity.getTenderMasEntity().getTenderNo());
			workDto.setTenderDate(tenderWorkEntity.getTenderMasEntity().getTenderDate());
		}
		
		return workDto;
	}

	@Override
	@Transactional(readOnly = true)
	public TenderWorkDto getTenderDetailsByLoaNumber(String loaNumber, long orgId) {
		TenderWorkDto workDto = null;
		TenderWorkEntity tenderWorkEntity = tenderRepository.getTenderDetailsByLoaNumber(loaNumber.toString(), orgId);
		if (tenderWorkEntity != null) {
			try {
				workDto = new TenderWorkDto();
				BeanUtils.copyProperties(tenderWorkEntity, workDto);
				setWorkCommonDetails(tenderWorkEntity, workDto);
				workDto.setTndResolutionDate(tenderWorkEntity.getTndAwdResDate());
				workDto.setTndResolutionNo(tenderWorkEntity.getTndAwdResNo());
				workDto.setProjectName(tenderWorkEntity.getTenderMasEntity().getProjMasEntity().getProjNameEng());
				workDto.setTenderDeptId(tenderWorkEntity.getTenderMasEntity().getProjMasEntity().getDpDeptId());
				workDto.setTenderNo(tenderWorkEntity.getTenderMasEntity().getTenderNo());
				workDto.setTndLOANo(tenderWorkEntity.getTndLOANo());
				workDto.setTenderDate(tenderWorkEntity.getTenderMasEntity().getTenderDate());
				workDto.setVenderId(tenderWorkEntity.getVendorMaster().getVmVendorid());
				workDto.setVenderTypeId(tenderWorkEntity.getVendorMaster().getCpdVendortype());
				/* Remove As Per SUDA UAT */
				/*
				 * workDto.setWorkEndDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
				 * .format(tenderWorkEntity.getWorkDefinationEntity().getWorkEndDate()));
				 */
			} catch (Exception ex) {
				throw new FrameworkException("Exception while getting tender details", ex);
			}
		}
		return workDto;
	}

	@Override
	@Transactional(readOnly = true)
	public TenderWorkDto findWorkByWorkId(Long contId) {
		TenderWorkDto workDto = null;
		try {
		TenderWorkEntity tenderWorkEntity = tenderRepository.getWorkNameByConId(contId);
		if (tenderWorkEntity != null) {
			workDto = new TenderWorkDto();
			BeanUtils.copyProperties(tenderWorkEntity, workDto);
			workDto.setWorkId(tenderWorkEntity.getWorkDefinationEntity().getWorkId());
			workDto.setTndId(tenderWorkEntity.getTenderMasEntity().getTndId());
			workDto.setTenderNo(tenderWorkEntity.getTenderMasEntity().getTenderNo());
			workDto.setTenderDate(tenderWorkEntity.getTenderMasEntity().getTenderDate());
			workDto.setWorkName(tenderWorkEntity.getWorkDefinationEntity().getWorkName());
			workDto.setTenderType(tenderWorkEntity.getTenderType());
			if(UserSession.getCurrent().getLanguageId()!=1) {
				workDto.setProjectName(tenderWorkEntity.getWorkDefinationEntity().getProjMasEntity().getProjNameReg());
			}else {
				workDto.setProjectName(tenderWorkEntity.getWorkDefinationEntity().getProjMasEntity().getProjNameEng());	
			}
			if (tenderWorkEntity.getVendorMaster() != null) {
				workDto.setVendorName(tenderWorkEntity.getVendorMaster().getVmVendorname());
			}

			if (tenderWorkEntity.getTenderType() != null)
				workDto.setTenderTypeDesc(CommonMasterUtility
						.getCPDDescription(tenderWorkEntity.getTenderType().longValue(), MainetConstants.MENU.E));

			if (tenderWorkEntity.getVendorMaster() != null) {
				workDto.setVenderId(tenderWorkEntity.getVendorMaster().getVmVendorid());
			}

			if (tenderWorkEntity.getVendorWorkPeriodUnit() != null) {
				workDto.setUnitDesc(CommonMasterUtility.getCPDDescription(
						tenderWorkEntity.getVendorWorkPeriodUnit().longValue(), MainetConstants.MODE_EDIT));
			}

		}
		}
			catch (Exception e) {
				LOGGER.info("Exception------------------------------------------"+e);
			}
		return workDto;
	}

	@Override
	@Transactional
	public void updateContractId(Long contId, Long workId, Long empId) {
		tenderRepository.updateContractId(contId, empId, workId);
	}

	@Override
	@Transactional(readOnly = true)
	public TenderWorkDto findContractByWorkId(Long workId) {
		TenderWorkEntity tenderWorkEntity = tenderRepository.getContractDetailsByWorkId(workId);
		TenderWorkDto tenderWorkDto = null;
		if (tenderWorkEntity != null) {
			tenderWorkDto = new TenderWorkDto();
			BeanUtils.copyProperties(tenderWorkEntity, tenderWorkDto);
			tenderWorkDto.setWorkId(tenderWorkEntity.getWorkDefinationEntity().getWorkId());
			tenderWorkDto.setWorkName(tenderWorkEntity.getWorkDefinationEntity().getWorkName());
			tenderWorkDto.setProjDeptId(tenderWorkEntity.getWorkDefinationEntity().getProjMasEntity().getDpDeptId());
			tenderWorkDto.setWorkCategory(tenderWorkEntity.getWorkDefinationEntity().getWorkCategory());
			tenderWorkDto.setWorkCode(tenderWorkEntity.getWorkDefinationEntity().getWorkcode());
			tenderWorkDto.setLocId(tenderWorkEntity.getWorkDefinationEntity().getLocIdSt());
			/* Remove As per SUDA UAT */
			/*
			 * tenderWorkDto.setWorkPlannedDate(UtilityService
			 * .convertDateToDDMMYYYY(tenderWorkEntity.getWorkDefinationEntity().
			 * getWorkStartDate())); tenderWorkDto.setWorkEndDate(
			 * UtilityService.convertDateToDDMMYYYY(tenderWorkEntity.getWorkDefinationEntity
			 * ().getWorkEndDate()));
			 */
			tenderWorkDto.setProjectName(tenderWorkEntity.getTenderMasEntity().getProjMasEntity().getProjNameEng());
			tenderWorkDto.setWorkType(tenderWorkEntity.getWorkDefinationEntity().getWorkType());
			tenderWorkDto.setTenderType(tenderWorkEntity.getTenderType());
			if (tenderWorkEntity.getTenderType() != null)
				tenderWorkDto.setTenderTypeDesc(CommonMasterUtility
						.getCPDDescription(tenderWorkEntity.getTenderType().longValue(), MainetConstants.MENU.E));
			if (tenderWorkEntity.getVendorMaster() != null) {
				tenderWorkDto.setVenderId(tenderWorkEntity.getVendorMaster().getVmVendorid());
				tenderWorkDto.setVendorName(tenderWorkEntity.getVendorMaster().getVmVendorname());
				tenderWorkDto.setVendorAddress(tenderWorkEntity.getVendorMaster().getVmVendoradd());
				tenderWorkDto.setVenderTypeId(tenderWorkEntity.getVendorMaster().getCpdVendortype());
				tenderWorkDto.setVendorPanNo(tenderWorkEntity.getVendorMaster().getVmPanNumber());
				tenderWorkDto.setVendorSubType(tenderWorkEntity.getVendorMaster().getCpdVendorSubType());
			}
			tenderWorkDto.setContractId(tenderWorkEntity.getContractId());
		}
		return tenderWorkDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TenderWorkDto> findTenderByVenderId(Long venderId) {
		List<TenderWorkEntity> tenderWorkEntity = tenderRepository.findTenderByVenderId(venderId);
		TenderWorkDto tenderWorkDto = null;
		List<TenderWorkDto> dtos = new ArrayList<>();
		if (tenderWorkEntity != null) {
			for (TenderWorkEntity tenderWorkDto2 : tenderWorkEntity) {
				tenderWorkDto = new TenderWorkDto();
				BeanUtils.copyProperties(tenderWorkEntity, tenderWorkDto);
				tenderWorkDto.setContractId(tenderWorkDto2.getContractId());
				dtos.add(tenderWorkDto);
			}
		}
		return dtos;
	}

	@Override
	@Transactional(readOnly = true)
	public TenderWorkDto findNITAndPqDocFormDetailsByWorkId(Long workId, Long orgId) {

		TenderWorkEntity tenderWorkEntity = tenderRepository.findNITAndPqDocFormDetailsByWorkId(workId, orgId);
		TenderWorkDto tenderWorkDto = null;
		if (tenderWorkEntity != null) {
			tenderWorkDto = new TenderWorkDto();
			BeanUtils.copyProperties(tenderWorkEntity, tenderWorkDto);
			tenderWorkDto.setWorkId(tenderWorkEntity.getWorkDefinationEntity().getWorkId());
			tenderWorkDto.setWorkName(tenderWorkEntity.getWorkDefinationEntity().getWorkName());

			/* Remove As Per SUDA UAT */

			/*
			 * tenderWorkDto.setWorkPlannedDate(UtilityService
			 * .convertDateToDDMMYYYY(tenderWorkEntity.getWorkDefinationEntity().
			 * getWorkStartDate())); tenderWorkDto.setWorkEndDate(
			 * UtilityService.convertDateToDDMMYYYY(tenderWorkEntity.getWorkDefinationEntity
			 * ().getWorkEndDate()));
			 */
			tenderWorkDto.setProjectName(tenderWorkEntity.getTenderMasEntity().getProjMasEntity().getProjNameEng());
			tenderWorkDto.setWorkType(tenderWorkEntity.getWorkDefinationEntity().getWorkType());

			tenderWorkDto.setContractId(tenderWorkEntity.getContractId());
			if (tenderWorkEntity.getTenderMasEntity().getTechnicalOpenDate() != null) {
				tenderWorkDto.setTechnicalOpenDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(tenderWorkEntity.getTenderMasEntity().getTechnicalOpenDate()));
			}
			if (tenderWorkEntity.getTenderMasEntity().getIssueFromDate() != null) {
				tenderWorkDto.setTenderIssueFromDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(tenderWorkEntity.getTenderMasEntity().getIssueFromDate()));
			}
			if (tenderWorkEntity.getTenderMasEntity().getIssueToDate() != null) {
				tenderWorkDto.setTenderIssueToDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(tenderWorkEntity.getTenderMasEntity().getIssueToDate()));
			}
			if (tenderWorkEntity.getVenderClassId() != null) {
				tenderWorkDto.setVenderClassDesc(CommonMasterUtility
						.getCPDDescription(tenderWorkEntity.getVenderClassId().longValue(), MainetConstants.MODE_EDIT));
			}
			if (tenderWorkEntity.getTenderAmount() != null) {
				tenderWorkDto
						.setAmountInStringFormat(Utility.convertBigNumberToWord(tenderWorkEntity.getTenderAmount()));
			}
			if (tenderWorkEntity.getTenderMasEntity().getTndValidityDay() != null) {
				tenderWorkDto.setTndValidityDay(tenderWorkEntity.getTenderMasEntity().getTndValidityDay());
			}
			if (tenderWorkEntity.getTenderSecAmt() != null) {
				tenderWorkDto.setTndEarAmntString(Utility.convertBigNumberToWord(tenderWorkEntity.getTenderSecAmt()));
			}

		}
		return tenderWorkDto;
	}

	public String TimeToStringConvert(Date date) {
		String dateString = null;
		if (date != null) {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			dateString = sdf.format(date);
		}

		return dateString;
	}

	public Date stringToDateConvert(String time) {
		DateFormat formatter = new SimpleDateFormat(MainetConstants.WorksManagement.DATE_FORMAT);
		Date timeValue = null;
		if (time != null) {
			try {
				timeValue = formatter.parse(time);
				// timeValue = new Date(formatter.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return timeValue;

	}

	@Override
	public WMSRateMaster getEmdAmountFromBrmsRule(Long orgId, BigDecimal tenderAmount) {

		LOGGER.info("brms WMS getEmdAmountFromBrmsRule execution start..");
		WSResponseDTO responseDTO = new WSResponseDTO();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		WMSRateMaster master = null;

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("BILL",
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.WorksManagement.WOA, orgId);

		LookUp taxSubCat = CommonMasterUtility.getHieLookupByLookupCode("SR", PrefixConstants.LookUpPrefix.TAC, 2,
				orgId);

		List<TbTaxMasEntity> taxList = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
				.findAllTaxesByChargeAppAtAndTaxSubCat(organisation.getOrgid(), sm.getTbDepartment().getDpDeptid(),
						chargeApplicableAt.getLookUpId(), taxSubCat.getLookUpId());
		List<WMSRateMaster> masterList = new ArrayList<>();
		if (!taxList.isEmpty()) {
			taxList.forEach(taxMas -> {
				WMSRateMaster wmsMasterModel = new WMSRateMaster();
				wmsMasterModel.setChargeApplicableAt(MainetConstants.WorksManagement.CHARGE_APPLICABLE_AT);
				wmsMasterModel.setTaxCode(taxMas.getTaxCode());
				wmsMasterModel.setParentTaxCode(MainetConstants.WorksManagement.PARENT_TAX_CODE);
				List<LookUp> categoryList = CommonMasterUtility.getLevelData("TAC", 1, organisation).stream()
						.filter(c -> taxMas.getTaxCategory1().equals(c.getLookUpId())).collect(Collectors.toList());
				List<LookUp> subCategoryList = CommonMasterUtility.getLevelData("TAC", 2, organisation).stream()
						.filter(c -> taxMas.getTaxCategory2().equals(c.getLookUpId())).collect(Collectors.toList());
				wmsMasterModel.setTaxCategory(categoryList.get(0).getDescLangFirst());
				wmsMasterModel.setTaxSubCategory(subCategoryList.get(0).getDescLangFirst());
				wmsMasterModel.setTenderAmount(tenderAmount.doubleValue());
				wmsMasterModel.setRateStartDate(new Date().getTime());
				wmsMasterModel.setOrgId(orgId);
				masterList.add(wmsMasterModel);
			});
		}
		wsRequestDTO.setDataModel(masterList);
		try {
			LOGGER.info("brms WMS request DTO is :" + wsRequestDTO.toString());
			responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.WMS_RATE_MASTER);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				master = setEmdAmountDTO(responseDTO);
			} else {
				throw new FrameworkException(responseDTO.getErrorMessage());
			}
		} catch (Exception ex) {
			throw new FrameworkException("unable to process request for EMD Amount", ex);
		}
		LOGGER.info("brms WMS getEmdAmountFromBrmsRule execution End.");
		return master;
	}

	private WMSRateMaster setEmdAmountDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setEmdAmountDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, WMSRateMaster.class);
		WMSRateMaster finalRateMaster = new WMSRateMaster();
		for (final Object rate : charges) {
			final WMSRateMaster masterRate = (WMSRateMaster) rate;
			finalRateMaster = masterRate;
		}
		LOGGER.info("setEmdAmountDTO execution end..");
		return finalRateMaster;
	}

	@Override
	@Transactional
	public void updateTenderStatus(Long orgId, String initiationNo, String updateFlag) {
		tenderRepository.updateTenderStatus(orgId, initiationNo, updateFlag);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getTenderIdByInitiationNumber(String tenderInitiationNo, Long orgId) {
		return tenderRepository.getTenderIdByInitiationNumber(tenderInitiationNo, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TenderMasterDto> findAllTenderList(Long orgId) {
		List<TenderMasterEntity> masterEntity = tenderRepository.findAllTenderList(orgId);
		List<TenderMasterDto> tenderList = new ArrayList<TenderMasterDto>();
		
		if (masterEntity != null && !masterEntity.isEmpty()) {
			masterEntity.forEach(entity -> {
				TenderMasterDto tenderDto = new TenderMasterDto();
				BeanUtils.copyProperties(entity, tenderDto);
				setDescriptionDetailsForTender(entity, tenderDto);
				List<TenderWorkDto> tenderWorkList = new ArrayList<TenderWorkDto>();
				tenderWorkEntityRepo.findByTenderMasEntity(entity).forEach(technicalEntity -> {
						TenderWorkDto bidDetailDto = new TenderWorkDto();
						BeanUtils.copyProperties(technicalEntity, bidDetailDto);
						tenderWorkList.add(bidDetailDto);
					});
				//tenderDto.setWorkDtos(tenderWorkList.get(0));
				if(!tenderWorkList.isEmpty()) {
				tenderDto.setTndLOANo(tenderWorkList.get(0).getTndLOANo());
				
				tenderList.add(tenderDto);
				
				}
			});
		}
		return tenderList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getAllActiveProjectsForTndServices(Long orgId) {
		return tenderRepository.getActiveProjectTenderServices(orgId);
	}

	@Override
	@Transactional
	public WMSRateMaster getTndFessFromBrmsRule(Long orgId, BigDecimal estimatedAmount) {

		WSResponseDTO responseDTO = new WSResponseDTO();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		WMSRateMaster master = null;

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("RCPT",
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.WorksManagement.TND_SHORT_CODE, orgId);
		List<TbTaxMas> taxList = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
				.findAllActiveTaxList(orgId, sm.getTbDepartment().getDpDeptid(), chargeApplicableAt.getLookUpId());
		List<WMSRateMaster> masterList = new ArrayList<>();
		if (!taxList.isEmpty()) {
			taxList.forEach(taxMas -> {
				WMSRateMaster wmsMasterModel = new WMSRateMaster();
				wmsMasterModel.setChargeApplicableAt(chargeApplicableAt.getLookUpDesc());
				wmsMasterModel.setTaxCode(taxMas.getTaxCode());
				wmsMasterModel.setTenderAmount(estimatedAmount.doubleValue());
				wmsMasterModel.setRateStartDate(new Date().getTime());
				wmsMasterModel.setOrgId(orgId);
				masterList.add(wmsMasterModel);
			});
		}
		wsRequestDTO.setDataModel(masterList);
		try {
			LOGGER.info("brms WMS request DTO is :" + wsRequestDTO.toString());
			responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.WMS_RATE_MASTER);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				master = setEmdAmountDTO(responseDTO);
			} else {
				throw new FrameworkException(responseDTO.getErrorMessage());
			}
		} catch (Exception ex) {
			throw new FrameworkException("unable to process request for Tender Fees Amount", ex);
		}
		LOGGER.info("brms WMS getTndFeesFromBrmsRule execution End.");
		// defect #78115
		if (sm.getSmFeesSchedule() == 0) {
			master.setFlatRate(0);
		}
		return master;
	}

	@Override
	@Transactional
	public boolean saveBIDDetail(BidMasterDto bidMasterDto) {
		BIDMasterEntity bidMasterEntity = new BIDMasterEntity();
		BeanUtils.copyProperties(bidMasterDto, bidMasterEntity);
		TenderMasterEntity tenderMasterEntity = new TenderMasterEntity();
		tenderMasterEntity.setTndId(bidMasterDto.getTndId());
		bidMasterEntity.setTenderMasterEntity(tenderMasterEntity);

		List<TechnicalBIDDetailEntity> technicalBIDDetailEntities = new ArrayList<TechnicalBIDDetailEntity>();
		List<CommercialBIDDetailEntity> commercialBIDDetailEntities = new ArrayList<CommercialBIDDetailEntity>();
		//D#138367
        if(CollectionUtils.isNotEmpty(bidMasterDto.getTernitDoc())) {
			RequestDTO requestdtos=new RequestDTO();
			requestdtos.setReferenceId(bidMasterDto.getTndId().toString());
			requestdtos.setApplicationId(Long.valueOf(bidMasterDto.getTndId()));
			requestdtos.setOrgId(bidMasterDto.getOrgId());
			requestdtos.setUserId(bidMasterDto.getUpdatedBy());
			fileUpload.doFileUpload(bidMasterDto.getTernitDoc(), requestdtos);	
		}

		for (TechnicalBIDDetailDto technicalDTO : bidMasterDto.getTechnicalBIDDetailDtos()) {
			TechnicalBIDDetailEntity bidDetailEntity = new TechnicalBIDDetailEntity();
			BeanUtils.copyProperties(technicalDTO, bidDetailEntity);
			bidDetailEntity.setBidMasterEntity(bidMasterEntity);
			technicalBIDDetailEntities.add(bidDetailEntity);
		}

		for (CommercialBIDDetailDto commercialBIDDetailDto : bidMasterDto.getCommercialBIDDetailDtos()) {
			CommercialBIDDetailEntity commercialBIDDetailEntity = new CommercialBIDDetailEntity();
			BeanUtils.copyProperties(commercialBIDDetailDto, commercialBIDDetailEntity);
			commercialBIDDetailEntity.setBidMasterEntity(bidMasterEntity);
			commercialBIDDetailEntities.add(commercialBIDDetailEntity);
		}
		bidMasterEntity.setCommercialBIDDetailEntities(commercialBIDDetailEntities);
		bidMasterEntity.setTechnicalBidDetailEntities(technicalBIDDetailEntities);
		Long bidId = bidMasterEntity.getBidId();
		try {
			bidMasterEntity = bidDetailRepository.save(bidMasterEntity);

		} catch (Exception e) {
			LOGGER.error("Exception occur while saving the BID Details ", e);
			throw new FrameworkException("Exception occur while saving the BID Details", e);
		}

		try {
			BIDMasterHistEntity bidMasterHistEntity = new BIDMasterHistEntity();
			bidMasterHistEntity.setHistStatus(bidMasterDto.getHistFlag());
			bidMasterHistEntity.setTndId(bidMasterEntity.getTenderMasterEntity().getTndId());
			auditService.createHistory(bidMasterEntity, bidMasterHistEntity);

			bidMasterEntity.getTechnicalBidDetailEntities().forEach(entity -> {
				TechnicalBIdHistEntity technicalBIdHistEntity = new TechnicalBIdHistEntity();
				technicalBIdHistEntity.setHistStatus(bidMasterDto.getHistFlag());
				technicalBIdHistEntity.setBidId(bidId);
				auditService.createHistory(entity, technicalBIdHistEntity);
			});

			bidMasterEntity.getCommercialBIDDetailEntities().forEach(entity -> {
				CommercialBIDDetailHistEntity commercialBIDDetailHistEntity = new CommercialBIDDetailHistEntity();
				commercialBIDDetailHistEntity.setHistStatus(bidMasterDto.getHistFlag());
				commercialBIDDetailHistEntity.setBidId(bidId);
				auditService.createHistory(entity, commercialBIDDetailHistEntity);
			});

		} catch (Exception e) {
			LOGGER.error("Exception occur while saving the BID History Details ", e);
			throw new FrameworkException("Exception occur while saving the BID History Details", e);
		}
		return true;
	}

	@Override
	public List<TenderMasterDto> searchTenderByTendernoAndDate(Long orgid, String tenderNo, Date tenderDate) {
		List<TenderMasterDto> tenderMasterDtos = new ArrayList<TenderMasterDto>();
		List<TenderWorkEntity> tenderWorkEntities = bidMasterDao.searchTenderByTendernoAndDate(orgid, tenderNo,
				tenderDate);
		if (!tenderWorkEntities.isEmpty()) {
			tenderWorkEntities.forEach(entity -> {
				TenderMasterDto tenderMasterDto = new TenderMasterDto();
				BeanUtils.copyProperties(entity.getTenderMasEntity(), tenderMasterDto);
				tenderMasterDto.setProjId(entity.getWorkDefinationEntity().getProjMasEntity().getProjId());
				tenderMasterDto.setTenderCategoryDesc(CommonMasterUtility
						.getNonHierarchicalLookUpObject(entity.getTenderMasEntity().getTenderCategory(),
								UserSession.getCurrent().getOrganisation())
						.getLookUpDesc());
				tenderMasterDto.setProjectName(entity.getTenderMasEntity().getProjMasEntity().getProjNameEng());

				if (StringUtils.isNotEmpty(tenderMasterDto.getStatus())
						&& tenderMasterDto.getStatus().equals(MainetConstants.FlagP)) {
					tenderMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_PENDING);
				}
				if (StringUtils.isNotEmpty(tenderMasterDto.getStatus())
						&& tenderMasterDto.getStatus().equals(MainetConstants.FlagA)) {
					tenderMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_APPROVED);
				}
				if (StringUtils.isNotEmpty(tenderMasterDto.getStatus())
						&& tenderMasterDto.getStatus().equals(MainetConstants.FlagR)) {
					tenderMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_REJECTED);
				}
				

				tenderMasterDtos.add(tenderMasterDto);
			});
		}
		return tenderMasterDtos;
	}

	@Override
	public List<BidMasterDto> getBidMAsterByTndId(Long orgId, Long tndId) {
		List<BidMasterDto> bidMasterDtos = new ArrayList<BidMasterDto>();
		List<BIDMasterEntity> bidMasterEntities = tenderRepository.getBidMAsterByTndId(orgId, tndId);

		if (!bidMasterEntities.isEmpty()) {
			bidMasterEntities.forEach(entity -> {
				BidMasterDto bidMasterDto = new BidMasterDto();
				BeanUtils.copyProperties(entity, bidMasterDto);
				List<CommercialBIDDetailDto> commercialBIDDetailDtos = new ArrayList<CommercialBIDDetailDto>();
				List<TechnicalBIDDetailDto> technicalBIDDetailDtos = new ArrayList<TechnicalBIDDetailDto>();
				if (!entity.getCommercialBIDDetailEntities().isEmpty()) {
					entity.getCommercialBIDDetailEntities().forEach(commercialEntity -> {
						CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
						BeanUtils.copyProperties(commercialEntity, bidDetailDto);
						commercialBIDDetailDtos.add(bidDetailDto);
					});
				}
				if (!entity.getTechnicalBidDetailEntities().isEmpty()) {
					entity.getTechnicalBidDetailEntities().forEach(technicalEntity -> {
						TechnicalBIDDetailDto bidDetailDto = new TechnicalBIDDetailDto();
						BeanUtils.copyProperties(technicalEntity, bidDetailDto);
						technicalBIDDetailDtos.add(bidDetailDto);
					});
				}
				bidMasterDto.setCommercialBIDDetailDtos(commercialBIDDetailDtos);
				bidMasterDto.setTechnicalBIDDetailDtos(technicalBIDDetailDtos);
				bidMasterDtos.add(bidMasterDto);
			});
		}
		return bidMasterDtos;
	}

	@Override
	@Transactional
	public BidMasterDto getBidMAsterByBIDId(Long orgId, Long bidId) {
		BidMasterDto bidMasterDto = new BidMasterDto();
		BIDMasterEntity bidMasterEntity = tenderRepository.getBidMAsterByBIDId(orgId, bidId);

		BeanUtils.copyProperties(bidMasterEntity, bidMasterDto);
		List<CommercialBIDDetailDto> commercialBIDDetailDtos = new ArrayList<CommercialBIDDetailDto>();
		List<TechnicalBIDDetailDto> technicalBIDDetailDtos = new ArrayList<TechnicalBIDDetailDto>();
		List<ItemRateBidDetailDto> itemRateBidDetailDtos = new ArrayList<ItemRateBidDetailDto>();
		
		TenderMasterEntity tenderMasterEntity = tenderRepository.getTenderByInitiationNumber(bidMasterEntity.getTndNo(), orgId);
		
		List<Long> tenderTypes = tenderRepository.getTenderTypeByTenderId(tenderMasterEntity.getTndId(), orgId);
		
		if (!bidMasterEntity.getCommercialBIDDetailEntities().isEmpty()) {
			bidMasterEntity.getCommercialBIDDetailEntities().forEach(commercialEntity -> {
				CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
				BeanUtils.copyProperties(commercialEntity, bidDetailDto);
				commercialBIDDetailDtos.add(bidDetailDto);
			});
		} else {
			CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
			bidDetailDto.setTenderType(tenderTypes.get(0));
			commercialBIDDetailDtos.add(bidDetailDto);
		}	
			
		if (!bidMasterEntity.getTechnicalBidDetailEntities().isEmpty()) {
			bidMasterEntity.getTechnicalBidDetailEntities().forEach(technicalEntity -> {
				TechnicalBIDDetailDto bidDetailDto = new TechnicalBIDDetailDto();
				BeanUtils.copyProperties(technicalEntity, bidDetailDto);
				technicalBIDDetailDtos.add(bidDetailDto);
			});
		}
		bidMasterEntity.getItemRateBidDetailEntities().forEach(itemRateEntity -> {
			ItemRateBidDetailDto itemRateBidDetailDto = new ItemRateBidDetailDto();
			BeanUtils.copyProperties(itemRateEntity, itemRateBidDetailDto);
			itemRateBidDetailDtos.add(itemRateBidDetailDto);
		});
		
		bidMasterDto.setCommercialBIDDetailDtos(commercialBIDDetailDtos);
		bidMasterDto.setTechnicalBIDDetailDtos(technicalBIDDetailDtos);
		bidMasterDto.setItemRateBidDetailDtos(itemRateBidDetailDtos);
		
		if (WorkManagementConstant.Tender.PURCHASE_REQ.equalsIgnoreCase(tenderMasterEntity.getProjMasEntity().getProjCode())) {
			List<TenderWorkEntity> tenderWorkEntities = tenderWorkEntityRepo.findByTenderMasEntity(tenderMasterEntity);
			if(bidMasterEntity.getItemRateBidDetailEntities().isEmpty()) {
				List<Long> prIds = tenderWorkEntities.stream().map(list -> list.getPrId()).collect(Collectors.toList());
				if(!prIds.isEmpty() && !prIds.contains(null))
					bidMasterDto.setItemRateBidDetailDtos(fetchPurchaseDetailsForCommercialBid(prIds, orgId));
			} else
				getItemDataForCommercialBid(bidMasterDto);
		} else if (!MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(tbDepartmentService
				.findDepartmentShortCodeByDeptId(tenderMasterEntity.getProjMasEntity().getDpDeptId(), orgId))) {
			
			Long workId=tenderWorkEntityRepo.getTenderworkid(tenderMasterEntity.getTndId(), orgId);
			List<WorkEstimateMaster> entity= tenderWorkEntityRepo.getWorkEstimateDate(workId, orgId);
			entity.forEach(workentity -> {
				ItemRateBidDetailDto itemRateBidDetailDto = new ItemRateBidDetailDto();
				itemRateBidDetailDto.setItemName(workentity.getSorDDescription());
				itemRateBidDetailDto.setItemId(workentity.getSordCategory());
				itemRateBidDetailDto.setQuantity(workentity.getWorkEstimQuantity().doubleValue());
				itemRateBidDetailDtos.add(itemRateBidDetailDto);
			});
				
				
			
			
			bidMasterDto.setItemRateBidDetailDtos(itemRateBidDetailDtos);
			// use this for Works Mgnt
		}
		return bidMasterDto;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<ItemRateBidDetailDto> fetchPurchaseDetailsForCommercialBid(List<Long> prIds,Long orgId) {
		List<ItemRateBidDetailDto> rateBidDetailDtoList =new ArrayList<>();
		List<PurchaseRequistionDetDto> requistionDetDtoList =new ArrayList<>();

		try {			
			PurchaseRequistionDto purRequistionDto = new PurchaseRequistionDto();
			purRequistionDto.setOrgId(orgId);
			purRequistionDto.setPrIds(prIds);
			ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(purRequistionDto, ServiceEndpoints.FETCH_PURCHASE_REQUISATION_DETAILS);
			HttpStatus statusCode = responseEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) responseEntity.getBody();
				try {
					for (LinkedHashMap<Long, Object> obj : responseVo) {
						final String d = new JSONObject(obj).toString();
						PurchaseRequistionDetDto requistionDetDto = new ObjectMapper().readValue(d, PurchaseRequistionDetDto.class);
						requistionDetDtoList.add(requistionDetDto);
					}
				} catch (JsonParseException e) {
					throw new FrameworkException(e);
				} catch (JsonMappingException e) {
					throw new FrameworkException(e);
				} catch (IOException e) {
					throw new FrameworkException(e);
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured while fetching PR details : ", ex);
			throw new FrameworkException(ex);
		}		
		rateBidDetailDtoList = requistionDetDtoList.stream().map(detDto -> new ItemRateBidDetailDto(detDto.getItemId(),
				detDto.getItemName(), detDto.getQuantity().doubleValue())).collect(Collectors.toList());
		return rateBidDetailDtoList;
	}

	@SuppressWarnings("unchecked")
	private BidMasterDto getItemDataForCommercialBid(BidMasterDto bidMasterDto) {
		List<Long> itemIds = bidMasterDto.getItemRateBidDetailDtos().stream().map(list -> list.getItemId()).collect(Collectors.toList());
		PurchaseRequistionDto purRequistionDto =new PurchaseRequistionDto();
		purRequistionDto.setOrgId(bidMasterDto.getOrgId());
		purRequistionDto.setItemIds(itemIds);
		final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(purRequistionDto, ServiceEndpoints.FETCH_ITEM_DATA);
	
		if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
			final LinkedHashMap<String, String> responseMap = (LinkedHashMap<String, String>) responseEntity.getBody();
			bidMasterDto.getItemRateBidDetailDtos().forEach(itemRateDto -> {
				itemRateDto.setItemName(responseMap.get(itemRateDto.getItemId().toString()));
			});
		} else {
			LOGGER.warn("Problem During Setting Item Name");
		}
		return bidMasterDto;
	}

	

	@Override
	public BidMasterDto searchBIdDetByVendorIdandBidId(Long orgId, String bidId, Long vendorId, Long tndId) {

		BIDMasterEntity bidMasterEntity = bidMasterDao.searchBIdDetByVendorIdandBidId(orgId, bidId, vendorId, tndId);

		BidMasterDto bidMasterDto = new BidMasterDto();
		// BIDMasterEntity bidMasterEntity = tenderRepository.getBidMAsterByBIDId(orgId,
		// bidId);

		BeanUtils.copyProperties(bidMasterEntity, bidMasterDto);
		List<CommercialBIDDetailDto> commercialBIDDetailDtos = new ArrayList<CommercialBIDDetailDto>();
		List<TechnicalBIDDetailDto> technicalBIDDetailDtos = new ArrayList<TechnicalBIDDetailDto>();
		if (!bidMasterEntity.getCommercialBIDDetailEntities().isEmpty()) {
			bidMasterEntity.getCommercialBIDDetailEntities().forEach(commercialEntity -> {
				CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
				BeanUtils.copyProperties(commercialEntity, bidDetailDto);
				commercialBIDDetailDtos.add(bidDetailDto);
			});
		}
		if (!bidMasterEntity.getTechnicalBidDetailEntities().isEmpty()) {
			bidMasterEntity.getTechnicalBidDetailEntities().forEach(technicalEntity -> {
				TechnicalBIDDetailDto bidDetailDto = new TechnicalBIDDetailDto();
				BeanUtils.copyProperties(technicalEntity, bidDetailDto);
				technicalBIDDetailDtos.add(bidDetailDto);
			});
		}
		bidMasterDto.setCommercialBIDDetailDtos(commercialBIDDetailDtos);
		bidMasterDto.setTechnicalBIDDetailDtos(technicalBIDDetailDtos);
		return bidMasterDto;

	}
	
	 void initateWorkFlowForTenderAward(TenderMasterEntity savedEntity) {
		boolean checklist = false;

		ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
				.findShortCodeByOrgId("TDA", savedEntity.getOrgId());
		ApplicantDetailDTO applicantDetailsDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationData = new ApplicationMetadata();
		applicationData.setIsCheckListApplicable(checklist);
		applicationData.setOrgId(savedEntity.getOrgId());
		applicationData.setReferenceId(savedEntity.getInitiationNo());
		applicantDetailsDto.setServiceId(master.getSmServiceId());
		applicantDetailsDto.setDepartmentId(master.getTbDepartment().getDpDeptid());
		applicantDetailsDto.setUserId(savedEntity.getCreatedBy());

		ApplicationContextProvider.getApplicationContext().getBean(CommonService.class)
				.initiateWorkflowfreeService(applicationData, applicantDetailsDto);

	}
	 
	 
		@Override
		@Transactional
		public List<TenderMasterDto> getTenderOnProjId(Long projId, Long orgId) {
			List<TenderMasterDto> tenderMasterDtoList = new ArrayList<>();
			List<TenderMasterEntity> tenderEntityList = tenderRepository.getAllInitiatedTenders(projId, orgId);
			if (!tenderEntityList.isEmpty()) {
				tenderEntityList.forEach(tenderEntity -> {
					TenderMasterDto tenderMasterDto = new TenderMasterDto();
					BeanUtils.copyProperties(tenderEntity, tenderMasterDto);
					tenderMasterDtoList.add(tenderMasterDto);
				});
			}

			return tenderMasterDtoList;
		}
		
		@Override
		@Transactional
		public List<BidMasterDto> createBid( List<BidMasterDto> bidDto) {
			List<BIDMasterEntity> bidEntityList = new ArrayList<>();
			bidDto.forEach(bidMasDto -> {
				BIDMasterEntity bidMasterEntity = new BIDMasterEntity();
				BeanUtils.copyProperties(bidMasDto, bidMasterEntity);
				bidEntityList.add(bidMasterEntity);
			});
			bidDetailRepository.save(bidEntityList);
			return bidDto;
		}
		
		
		@Override
		public List<BidMasterDto> getBidMAster(Long orgId, Long projId,String tndNo) {
			List<BidMasterDto> bidMasterDtos = new ArrayList<BidMasterDto>();
			List<BIDMasterEntity> bidMasterEntities = tenderRepository.getBidMAster(orgId, projId,tndNo);
			if (!bidMasterEntities.isEmpty()) {
				bidMasterEntities.forEach(entity -> {
					BidMasterDto bidMasterDto = new BidMasterDto();
					BeanUtils.copyProperties(entity, bidMasterDto);
					bidMasterDtos.add(bidMasterDto);
				});
			}
			return bidMasterDtos;
		}
		
		@Override
		public boolean saveData(BidMasterDto bidMasterDto) {
			BIDMasterEntity bidMasterEntity = new BIDMasterEntity();
			BeanUtils.copyProperties(bidMasterDto, bidMasterEntity);
		
			List<TechnicalBIDDetailEntity> technicalBIDDetailEntities = new ArrayList<TechnicalBIDDetailEntity>();
			List<CommercialBIDDetailEntity> commercialBIDDetailEntities = new ArrayList<CommercialBIDDetailEntity>();

			for (TechnicalBIDDetailDto technicalDTO : bidMasterDto.getTechnicalBIDDetailDtos()) {
				TechnicalBIDDetailEntity bidDetailEntity = new TechnicalBIDDetailEntity();
				technicalDTO.setTechnicalflag('Y');
				BeanUtils.copyProperties(technicalDTO, bidDetailEntity);
				bidDetailEntity.setBidMasterEntity(bidMasterEntity);
				technicalBIDDetailEntities.add(bidDetailEntity);
			}
			for (CommercialBIDDetailDto commercialBIDDetailDto : bidMasterDto.getCommercialBIDDetailDtos()) {
				CommercialBIDDetailEntity commercialBIDDetailEntity = new CommercialBIDDetailEntity();
				commercialBIDDetailDto.setFinancialflag('Y');
				BeanUtils.copyProperties(commercialBIDDetailDto, commercialBIDDetailEntity);
				commercialBIDDetailEntity.setBidMasterEntity(bidMasterEntity);
				commercialBIDDetailEntities.add(commercialBIDDetailEntity);
			}
			
			for(ItemRateBidDetailDto itemRateBidDetailDto : bidMasterDto.getItemRateBidDetailDtos()) {
				ItemRateBidDetailEntity rateBidDetailEntity = new ItemRateBidDetailEntity();
				BeanUtils.copyProperties(itemRateBidDetailDto, rateBidDetailEntity);
				rateBidDetailEntity.setBidMasterEntity(bidMasterEntity);
				bidMasterEntity.getItemRateBidDetailEntities().add(rateBidDetailEntity);
			}
			
			bidMasterEntity.setCommercialBIDDetailEntities(commercialBIDDetailEntities);
			bidMasterEntity.setTechnicalBidDetailEntities(technicalBIDDetailEntities);
			Long bidId = bidMasterEntity.getBidId();
			try {
				bidMasterEntity = bidDetailRepository.save(bidMasterEntity);

			} catch (Exception e) {
				LOGGER.error("Exception occur while saving the BID Details ", e);
				throw new FrameworkException("Exception occur while saving the BID Details", e);
			}

			return true;
		}
		
		@Override
		@Transactional(readOnly = true)
		public TenderMasterDto getTenderDetailsByTenderNo(String tenderInitiationNo,Long orgId) {

			TenderMasterDto tenderDto = null;
			Long tndId=tenderRepository.getTenderIdByInitiationNumber(tenderInitiationNo,orgId);
			tenderDto = getPreparedTenderDetails(tndId);
			return tenderDto;
		}
		
		@Override
		@Transactional(readOnly = true)
		public TenderWorkDto getTenderByWorkId(Long workId) {
			TenderWorkDto tenderWorkDto = new TenderWorkDto();
			
			try {
				TenderWorkEntity tenderEntity = tenderWorkEntityRepo.getTenderData(workId,UserSession.getCurrent().getOrganisation().getOrgid());
				if(tenderEntity != null) 
				BeanUtils.copyProperties(tenderEntity, tenderWorkDto);

			} catch (Exception e) {
				 LOGGER.info("Data not found"  + e);
				//throw new FrameworkException("Exception occcured while calling method getTenderByWorkId " + e);
			}
			return tenderWorkDto;
		}
		
		@Override
		@Transactional
		public List<BidMasterDto> getBidMAsterByOrgId(Long orgId) {
			List<BidMasterDto> bidMasterDtoList = new ArrayList<BidMasterDto>();
			List<BIDMasterEntity> bidMasterEntity = tenderRepository.getBidMAsterByOrgId(orgId);
			
			for(BIDMasterEntity entity : bidMasterEntity) {
				BidMasterDto bidMasterDto = new BidMasterDto();
				BeanUtils.copyProperties(entity, bidMasterDto);
				
				
				
				
				List<CommercialBIDDetailDto> commercialBIDDetailDtos = new ArrayList<CommercialBIDDetailDto>();
				
				List<TechnicalBIDDetailDto> technicalBIDDetailDtos = new ArrayList<TechnicalBIDDetailDto>();
				if (!entity.getCommercialBIDDetailEntities().isEmpty()) {
					entity.getCommercialBIDDetailEntities().forEach(commercialEntity -> {
						CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
						BeanUtils.copyProperties(commercialEntity, bidDetailDto);
						commercialBIDDetailDtos.add(bidDetailDto);
					});
				}
				if (!entity.getTechnicalBidDetailEntities().isEmpty()) {
					entity.getTechnicalBidDetailEntities().forEach(technicalEntity -> {
						TechnicalBIDDetailDto bidDetailDto = new TechnicalBIDDetailDto();
						BeanUtils.copyProperties(technicalEntity, bidDetailDto);
						technicalBIDDetailDtos.add(bidDetailDto);
					});
				}
				bidMasterDto.setCommercialBIDDetailDtos(commercialBIDDetailDtos);
				bidMasterDto.setTechnicalBIDDetailDtos(technicalBIDDetailDtos);
				//bidMasterDtos.add(bidMasterDto);
				
				bidMasterDtoList.add(bidMasterDto);
	
			}
			return bidMasterDtoList;
		}
		
		@Transactional
		public Long BidCount(String bidNo,Long orgId) {
			long id = bidDetailRepository.BidCount(bidNo,orgId);
			return id;
		}
		
		@Override
		@Transactional(readOnly = true)
		public List<TenderMasterDto> findAllApprovedTender(Long orgId) {
			List<TenderMasterEntity> masterEntity = tenderRepository.findAllApprovedTender(orgId);
			List<TenderMasterDto> tenderList = new ArrayList<TenderMasterDto>();
			
			if (masterEntity != null && !masterEntity.isEmpty()) {
				masterEntity.forEach(entity -> {
					TenderMasterDto tenderDto = new TenderMasterDto();
					BeanUtils.copyProperties(entity, tenderDto);
					setDescriptionDetailsForTender(entity, tenderDto);
					List<TenderWorkDto> tenderWorkList = new ArrayList<TenderWorkDto>();
					tenderWorkEntityRepo.findByTenderMasEntity(entity).forEach(technicalEntity -> {
							TenderWorkDto bidDetailDto = new TenderWorkDto();
							BeanUtils.copyProperties(technicalEntity, bidDetailDto);
							tenderWorkList.add(bidDetailDto);
						});
					if(!tenderWorkList.isEmpty()) {
					tenderDto.setTndLOANo(tenderWorkList.get(0).getTndLOANo());
					
					tenderList.add(tenderDto);
					
					}
				});
			}
			return tenderList;
		}
		
		
		
		@Override
		@Transactional
		public List<BidMasterDto> getBidListByOrgId(Long orgId) {
			List<BidMasterDto> bidMasterDtoList = new ArrayList<BidMasterDto>();
			List<BIDMasterEntity> bidMasterEntity = bidDetailRepository.getAllBidListByOrgId(orgId);
			for(BIDMasterEntity entity : bidMasterEntity) {
				BidMasterDto bidMasterDto = new BidMasterDto();
				BeanUtils.copyProperties(entity, bidMasterDto);
				bidMasterDtoList.add(bidMasterDto);
			
			}
			
			return bidMasterDtoList;
		}
		
		@Override
			@Transactional
			public BidMasterDto viewandeditFinancial(Long orgId, Long bidId,String saveMode) {
				BidMasterDto bidMasterDto = new BidMasterDto();
				BIDMasterEntity bidMasterEntity = tenderRepository.getBidMAsterByBIDId(orgId, bidId);
	 
				BeanUtils.copyProperties(bidMasterEntity, bidMasterDto);
				List<CommercialBIDDetailDto> commercialBIDDetailDtos = new ArrayList<CommercialBIDDetailDto>();
				List<TechnicalBIDDetailDto> technicalBIDDetailDtos = new ArrayList<TechnicalBIDDetailDto>();
				List<ItemRateBidDetailDto> itemRateBidDetailDtos = new ArrayList<ItemRateBidDetailDto>();
				TenderMasterEntity tenderMasterEntity = tenderRepository.getTenderByInitiationNumber(bidMasterEntity.getTndNo(), orgId);
				List<Long> tenderTypes = tenderRepository.getTenderTypeByTenderId(tenderMasterEntity.getTndId(), orgId);
				if (!bidMasterEntity.getCommercialBIDDetailEntities().isEmpty()) {
					bidMasterEntity.getCommercialBIDDetailEntities().forEach(commercialEntity -> {
						CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
						BeanUtils.copyProperties(commercialEntity, bidDetailDto);
						commercialBIDDetailDtos.add(bidDetailDto);
					});
				} else {
					CommercialBIDDetailDto bidDetailDto = new CommercialBIDDetailDto();
					bidDetailDto.setTenderType(tenderTypes.get(0));
					commercialBIDDetailDtos.add(bidDetailDto);
				}	
				if (!bidMasterEntity.getTechnicalBidDetailEntities().isEmpty()) {
					bidMasterEntity.getTechnicalBidDetailEntities().forEach(technicalEntity -> {
						TechnicalBIDDetailDto bidDetailDto = new TechnicalBIDDetailDto();
						BeanUtils.copyProperties(technicalEntity, bidDetailDto);
						technicalBIDDetailDtos.add(bidDetailDto);
					});
				}
				bidMasterEntity.getItemRateBidDetailEntities().forEach(itemRateEntity -> {
					ItemRateBidDetailDto itemRateBidDetailDto = new ItemRateBidDetailDto();
					BeanUtils.copyProperties(itemRateEntity, itemRateBidDetailDto);
					itemRateBidDetailDtos.add(itemRateBidDetailDto);
				});
				bidMasterDto.setCommercialBIDDetailDtos(commercialBIDDetailDtos);
				bidMasterDto.setTechnicalBIDDetailDtos(technicalBIDDetailDtos);
				bidMasterDto.setItemRateBidDetailDtos(itemRateBidDetailDtos);
				if (WorkManagementConstant.Tender.PURCHASE_REQ.equalsIgnoreCase(tenderMasterEntity.getProjMasEntity().getProjCode())) {
					List<TenderWorkEntity> tenderWorkEntities = tenderWorkEntityRepo.findByTenderMasEntity(tenderMasterEntity);
					if(bidMasterEntity.getItemRateBidDetailEntities().isEmpty()) {
						List<Long> prIds = tenderWorkEntities.stream().map(list -> list.getPrId()).collect(Collectors.toList());
						if(!prIds.isEmpty() && !prIds.contains(null))
							bidMasterDto.setItemRateBidDetailDtos(fetchPurchaseDetailsForCommercialBid(prIds, orgId));
					} else
						getItemDataForCommercialBid(bidMasterDto);
				} else if (!MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(tbDepartmentService
						.findDepartmentShortCodeByDeptId(tenderMasterEntity.getProjMasEntity().getDpDeptId(), orgId))) {
					Long workId=tenderWorkEntityRepo.getTenderworkid(tenderMasterEntity.getTndId(), orgId);
				//List<WorkEstimateMaster> entity= tenderWorkEntityRepo.getWorkEstimateDate(workId, orgId);
					List<ItemRateBidDetailDto> itemRateBidDetailDtoList = new ArrayList<ItemRateBidDetailDto>();
					bidMasterEntity.getItemRateBidDetailEntities().forEach(itemRateEntity -> {
							ItemRateBidDetailDto itemRateBidDetailDto = new ItemRateBidDetailDto();
							itemRateBidDetailDto.setQuantity(itemRateEntity.getQuantity());
							itemRateBidDetailDto.setItemId(itemRateEntity.getItemId());
							//itemRateBidDetailDto.setItemName(entity.getSorDDescription());
							itemRateBidDetailDto.setPerUnitRate(itemRateEntity.getPerUnitRate());
							itemRateBidDetailDto.setAmount(itemRateEntity.getAmount());
							itemRateBidDetailDto.setItemRateBidId(itemRateEntity.getItemRateBidId());
							itemRateBidDetailDto.setCreatedBy(itemRateEntity.getCreatedBy());
							itemRateBidDetailDto.setUpdatedDate(itemRateEntity.getUpdatedDate());
							itemRateBidDetailDto.setCreationDate(itemRateEntity.getCreationDate());
							itemRateBidDetailDto.setUpdatedBy(itemRateEntity.getUpdatedBy());
							itemRateBidDetailDto.setLgIpMac(itemRateEntity.getLgIpMac());
							itemRateBidDetailDto.setLgIpMacUpd(itemRateEntity.getLgIpMacUpd());
							itemRateBidDetailDto.setOrgId(itemRateEntity.getOrgId());
							
							

							itemRateBidDetailDtoList.add(itemRateBidDetailDto);
						});
						bidMasterDto.setItemRateBidDetailDtos(itemRateBidDetailDtoList);
					}

				return bidMasterDto;
			}
}