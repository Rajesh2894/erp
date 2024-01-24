package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.workManagement.domain.TenderMasterEntity;
import com.abm.mainet.workManagement.domain.WorkOrder;
import com.abm.mainet.workManagement.domain.WorkOrderHistory;
import com.abm.mainet.workManagement.domain.WorkOrderTerms;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorkOrderTermsDto;
import com.abm.mainet.workManagement.repository.WorkOrderRepository;

/**
 * @author vishwajeet.kumar
 * @since 14 May 2018
 */
@Service
public class WorkOrderServiceImpl implements WorkOrderService {
	private static final Logger LOGGER = Logger.getLogger(WorkOrderServiceImpl.class);

	@Autowired
	private WorkOrderRepository workOrderRepository;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private AuditService auditService;
	
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;
    
    @Autowired
	private IOrganisationService organisationService;
    
    @Autowired
	ContractAgreementRepository contractRepo;
    
    @Autowired
    private TbAcVendormasterService vendormasterService;
   
	@Override
	@Transactional(readOnly = true)
	public WorkOrderDto getWorkOredrByOrderId(Long orderId) {
		WorkOrderDto workOrderDto = new WorkOrderDto();
		List<WorkOrderTermsDto> workOrderTermsDtos = new ArrayList<>();
		WorkOrderTermsDto termsDto = null;
		try {
			WorkOrder workOrderEntity = workOrderRepository.findOne(orderId);

			BeanUtils.copyProperties(workOrderEntity, workOrderDto);
			workOrderDto.setWorkOrderStartDate(
					(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(workOrderEntity.getStartDate())));
			if (workOrderEntity.getContractFromDate() != null) {
				workOrderDto.setContractFromDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(workOrderEntity.getContractFromDate()));
			}
			workOrderDto.setWorkOrderDate(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(workOrderEntity.getOrderDate()));
			workOrderDto.setContractDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
					.format(workOrderEntity.getContractMastEntity().getContDate()));
			workOrderDto.setContractAmount(
					workOrderEntity.getContractMastEntity().getContractDetailList().get(0).getContAmount());
			workOrderDto.setContractValue(
					BigDecimal.valueOf(workOrderDto.getContractAmount()).setScale(2, RoundingMode.HALF_UP));
			ContractMastDTO contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(workOrderEntity.getContractMastEntity().getContId());
			contractMastDTO.setContNo(workOrderEntity.getContractMastEntity().getContNo());
			//Defect #148411
			if(workOrderEntity.getContractMastEntity().getContractPart2List()!=null &&  workOrderEntity.getContractMastEntity().getContractPart2List().get(0).getVmVendorid() != null) {
				//#154873
				String vendorName = vendormasterService.getVendorNameById(workOrderEntity.getContractMastEntity().getContractPart2List().get(0).getVmVendorid(), workOrderEntity.getContractMastEntity().getContractPart2List().get(0).getOrgId());
				workOrderDto.setVendorName(vendorName);
			}
			
			workOrderDto.setContractMastDTO(contractMastDTO);

			// set tender details and its related works details like work name, work code,
			// work id

			for (WorkOrderTerms detailsEntity : workOrderEntity.getWorkOrderTermsList()) {
				termsDto = new WorkOrderTermsDto();
				BeanUtils.copyProperties(detailsEntity, termsDto);
				workOrderTermsDtos.add(termsDto);
			}
			workOrderDto.setWorkOrderTermsDtoList(workOrderTermsDtos);

			String ids = workOrderDto.getWorkAssignee();
			if (ids != null && !ids.isEmpty()) {
				String array[] = ids.split(MainetConstants.operator.COMMA);
				for (String id : array) {
					workOrderDto.getMultiSelect().add(id);
				}
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while calling method getWorkOredrByOrderId " + e);
		}
		return workOrderDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public WorkOrderDto getWorkOredrByWorkId(Long workId) {
		WorkOrderDto workOrderDto = new WorkOrderDto();
		
		try {
			WorkOrder workOrderEntity = workOrderRepository.findOne(workId);
			BeanUtils.copyProperties(workOrderEntity, workOrderDto);

		} catch (Exception e) {
			throw new FrameworkException("Exception acured while calling method getWorkOredrByOrderId " + e);
		}
		return workOrderDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderDto> getAllWorkOrder(Long orgId) {
		List<WorkOrderDto> workOrderList = new ArrayList<>();
		try {
			List<WorkOrder> workOrderEntity = workOrderRepository.findByOrgId(orgId);
			if (workOrderEntity != null && !workOrderEntity.isEmpty()) {
				workOrderEntity.forEach(entity -> {
					WorkOrderDto dto = new WorkOrderDto();
					BeanUtils.copyProperties(entity, dto);

					// set tender details and its related works details like work name, work code,
					// work id
					ContractMastDTO contractMastDTO = new ContractMastDTO();
					contractMastDTO.setContNo(entity.getContractMastEntity().getContNo());
					contractMastDTO.setContId(entity.getContractMastEntity().getContId());
					dto.setContractMastDTO(contractMastDTO);
					dto.setContractAmount(
							entity.getContractMastEntity().getContractDetailList().get(0).getContAmount());
					dto.setContractValue(BigDecimal.valueOf(dto.getContractAmount()).setScale(2, RoundingMode.HALF_UP));
					if (dto.getContractFromDate() != null) {
						dto.setContractFromDateDesc(
								new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(dto.getContractFromDate()));
					}

					List<WorkOrderTermsDto> termsList = new ArrayList<>();
					entity.getWorkOrderTermsList().forEach(terms -> {
						WorkOrderTermsDto termsDto = new WorkOrderTermsDto();
						BeanUtils.copyProperties(terms, termsDto);
						termsList.add(termsDto);
					});

					dto.setWorkOrderTermsDtoList(termsList);
					String ids = dto.getWorkAssignee();
					if (ids != null && !ids.isEmpty()) {
						String array[] = ids.split(MainetConstants.operator.COMMA);
						for (String id : array) {
							dto.getMultiSelect().add(id);
						}
					}
					workOrderList.add(dto);
				});
			}

		} catch (Exception ex) {
			throw new FrameworkException("Exception acured while calling method getAllWorkOrder : ", ex);
		}
		return workOrderList;
	}

	// set tender details and its related works details like work name, work code,
	// work id
	@SuppressWarnings("unused")
	private void setTenderAndWorkDetails(WorkOrder entity, WorkOrderDto dto) {
		// set tender details
		TenderMasterDto tenderDto = new TenderMasterDto();
		BeanUtils.copyProperties(entity.getTenderMasterEntity(), tenderDto);

		setDescriptionDetailsForTender(entity.getTenderMasterEntity(), tenderDto);

		// set tender work details
		List<TenderWorkDto> workDtoList = new ArrayList<>();
		entity.getTenderMasterEntity().getTenderWorkList().forEach(workEntity -> {
			TenderWorkDto workDto = new TenderWorkDto();
			BeanUtils.copyProperties(workEntity, workDto);
			workDto.setWorkName(workEntity.getWorkDefinationEntity().getWorkName());
			workDto.setWorkCode(workEntity.getWorkDefinationEntity().getWorkcode());
			if (workEntity.getEmployee() != null) {
				workDto.setWorkAssignee(workEntity.getEmployee().getEmpId());
				workDto.setWorkAssigneeName(workEntity.getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
						+ workEntity.getEmployee().getEmplname());
			}
			workDtoList.add(workDto);
		});
		tenderDto.setWorkDto(workDtoList);
		dto.setTenderMasterDto(tenderDto);
	}

	/**
	 * set description tender details like vendor class, vendor category from
	 * particular id for view purpose
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
				.getNonHierarchicalLookUpObject(entity.getTenderCategory(), organisation).getDescLangFirst());
		if (tenderDto.getInitiationDate() != null) {
			tenderDto.setInitiationDateDesc(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getInitiationDate()));

		}
	}

	@Override
	@Transactional(readOnly = true)
	public WorkOrderDto getNotAssigneedWorksByWorkOrder(Long orderId) {
		WorkOrderDto workOrderDto = new WorkOrderDto();
		List<WorkOrderTermsDto> workOrderTermsDtos = new ArrayList<>();
		WorkOrderTermsDto termsDto = null;
		try {
			WorkOrder workOrderEntity = workOrderRepository.findOne(orderId);
			BeanUtils.copyProperties(workOrderEntity, workOrderDto);
			// set tender details and its related works details like work name, work code,
			// work id
			setTenderAndWorkDetailsForNotAssigneWorks(workOrderEntity, workOrderDto);

			for (WorkOrderTerms detailsEntity : workOrderEntity.getWorkOrderTermsList()) {
				termsDto = new WorkOrderTermsDto();
				BeanUtils.copyProperties(detailsEntity, termsDto);
				workOrderTermsDtos.add(termsDto);
			}
			workOrderDto.setWorkOrderTermsDtoList(workOrderTermsDtos);
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while calling method getWorkOredrByOrderId " + e);
		}
		return workOrderDto;
	}

	// set tender details and its related works details like work name, work code,
	// work id
	private void setTenderAndWorkDetailsForNotAssigneWorks(WorkOrder entity, WorkOrderDto dto) {
		// set tender details
		TenderMasterDto tenderDto = new TenderMasterDto();
		BeanUtils.copyProperties(entity.getTenderMasterEntity(), tenderDto);

		setDescriptionDetailsForTender(entity.getTenderMasterEntity(), tenderDto);

		// set tender work details
		List<TenderWorkDto> workDtoList = new ArrayList<>();
		entity.getTenderMasterEntity().getTenderWorkList().forEach(workEntity -> {
			if (workEntity.getEmployee() == null) {
				TenderWorkDto workDto = new TenderWorkDto();
				BeanUtils.copyProperties(workEntity, workDto);
				workDto.setWorkName(workEntity.getWorkDefinationEntity().getWorkName());
				workDto.setWorkCode(workEntity.getWorkDefinationEntity().getWorkcode());
				workDto.setWorkId(workEntity.getWorkDefinationEntity().getWorkId());
				workDtoList.add(workDto);
			}

		});
		tenderDto.setWorkDto(workDtoList);
		dto.setTenderMasterDto(tenderDto);
	}

	public String generateAndUpdateWorkOrderNumber(Date workOrderDate, Long orgId, Long deptId) {

		// get financial by date
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).getFinanciaYearByDate(workOrderDate);

		// get financial year formate
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

		// generate sequence
		SequenceConfigMasterDTO configMasterDTO = null;

		configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptId,
				MainetConstants.WorksManagement.TB_WMS_WORKEORDER, MainetConstants.WorksManagement.WORKOR_NO);
		if (configMasterDTO.getSeqConfigId() == null) {

			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class)
					.generateSequenceNo(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
							MainetConstants.WorksManagement.TB_WMS_WORKEORDER,
							MainetConstants.WorksManagement.WORK_MB_NO, orgId, MainetConstants.FlagC,
							financiaYear.getFaYear());
			String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDeptCode(deptId);

			String workOrder = MainetConstants.WorksManagement.WO + MainetConstants.WINDOWS_SLASH + deptCode
					+ MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
					+ String.format(MainetConstants.CommonMasterUi.PADDING_SIX, sequence);

			return workOrder;

		} else {
			CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
			commonSequenceConfigDto.setFinancialYear(finacialYear);
			return seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
		}
	}

	@Override
	@Transactional
	public WorkOrderDto createWorkOrderGeneration(WorkOrderDto workOrderDto, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, Long deptId) {

		WorkOrderDto savedWorkOrderDto = null;
		
     
		if (workOrderDto != null) {
			WorkOrder workOrderEntity = new WorkOrder();
			// workOrderDto.setOrderDate(new Date());

			String workOrderNo = generateAndUpdateWorkOrderNumber(workOrderDto.getStartDate(), workOrderDto.getOrgId(),
					deptId);
			workOrderDto.setWorkOrderNo(workOrderNo);
			if (workOrderDto.getMultiSelect() != null && !workOrderDto.getMultiSelect().isEmpty()) {
				String assigneeName = String.join(MainetConstants.operator.COMMA, workOrderDto.getMultiSelect());
				workOrderDto.setWorkAssignee(assigneeName);
			}
			workOrderEntity.setCompletionDate(workOrderDto.getCompletionDate());
			BeanUtils.copyProperties(workOrderDto, workOrderEntity);

			ContractMastEntity contractMastEntity = new ContractMastEntity();
			contractMastEntity.setContId(workOrderDto.getContractMastDTO().getContId());
			workOrderEntity.setContractMastEntity(contractMastEntity);
			workOrderEntity.setWorkOrderStatus(MainetConstants.FlagD);
			workOrderEntity.setWorkAssigneeDate(new Date());
			
			

			// set data Terms And Condition details
			createTermsAndCondition(workOrderDto, workOrderEntity);

			WorkOrder savedWorkOrder = workOrderRepository.save(workOrderEntity);
			createHistoryForWorkOrderGeneration(savedWorkOrder);
			savedWorkOrderDto = new WorkOrderDto();
			BeanUtils.copyProperties(savedWorkOrder, savedWorkOrderDto);
			requestDTO.setIdfId(savedWorkOrder.getWorkOrderNo());
			fileUploadService.doMasterFileUpload(attachments, requestDTO);
			
			if(savedWorkOrder != null) {	
				Organisation org = organisationService.getOrganisationById(workOrderDto.getOrgId());
				List<ContractPart2DetailEntity> contractPart2Dto = contractRepo.getContractParty2Detail(contractMastEntity, workOrderDto.getOrgId());
				contractPart2Dto.forEach(part2Dto->{
					if(part2Dto.getVmVendorid() !=null) {
					TbAcVendormasterEntity vendorEntity = contractRepo
		    				.getVenderbyId(workOrderDto.getOrgId(),part2Dto.getVmVendorid());  
				
		        	 final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		        	
		        	if (vendorEntity != null) {
		        		smsDto.setMobnumber(vendorEntity.getMobileNo());
		        		smsDto.setUserId(org.getUserId());
						smsDto.setAppName(vendorEntity.getVmVendorname());
						smsDto.setAppNo(workOrderDto.getTenderNo());
						smsDto.setEmail(vendorEntity.getEmailId());
						smsDto.setOrgId(vendorEntity.getOrgid());
						smsDto.setTenantNo(workOrderDto.getTenderNo());
					    smsDto.setTenderDate(workOrderDto.getWorkOrderDate());				
						String menuUrl = "workordergeneration.html";
					      org.setOrgid(workOrderDto.getOrgId());
					      int langId = Utility.getDefaultLanguageId(org);
					      iSMSAndEmailService.sendEmailSMS("WMS", menuUrl,
					              PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
					}
					}
				});
				
			}

		}
		return savedWorkOrderDto;	
	}

	private void createHistoryForWorkOrderGeneration(WorkOrder savedWorkOrder) {

		WorkOrderHistory workOrderHistory = new WorkOrderHistory();

		workOrderHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
		workOrderHistory.setContId(savedWorkOrder.getContractMastEntity().getContId());
		try {
			auditService.createHistory(savedWorkOrder, workOrderHistory);
		} catch (Exception exception) {
			LOGGER.error("Could not make audit entry for" + savedWorkOrder, exception);
		}

	}

	// set data Terms And Condition details
	private void createTermsAndCondition(WorkOrderDto workOrderDto, WorkOrder workOrderEntity) {
		if (workOrderDto.getWorkOrderTermsDtoList() != null && !workOrderDto.getWorkOrderTermsDtoList().isEmpty()) {

			List<WorkOrderTerms> workOrderTermsList = new ArrayList<>();
			workOrderDto.getWorkOrderTermsDtoList().forEach(termsDetails -> {
				WorkOrderTerms workOrderTerms = new WorkOrderTerms();
				BeanUtils.copyProperties(termsDetails, workOrderTerms);
				setTermsDetails(workOrderDto, workOrderTerms);
				workOrderTerms.setWorkOrder(workOrderEntity);
				workOrderTermsList.add(workOrderTerms);
			});
			workOrderEntity.setWorkOrderTermsList(workOrderTermsList);
		}
	}

	private void setTermsDetails(WorkOrderDto workOrderDto, WorkOrderTerms workOrderTerms) {

		workOrderTerms.setCreatedBy(workOrderDto.getCreatedBy());
		workOrderTerms.setCreatedDate(workOrderDto.getCreatedDate());
		workOrderTerms.setLgIpMac(workOrderDto.getLgIpMac());
		workOrderTerms.setOrgId(workOrderDto.getOrgId());
		workOrderTerms.setActive(MainetConstants.Y_FLAG);

	}

	@Override
	@Transactional
	public void updateWorkOrderGeneration(WorkOrderDto workOrderDto, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, List<Long> removeFileId, List<Long> removeTermsById) {

		WorkOrder workOrderEntity = new WorkOrder();

		if (workOrderDto.getMultiSelect() != null && !workOrderDto.getMultiSelect().isEmpty()) {
			String assigneeName = String.join(MainetConstants.operator.COMMA, workOrderDto.getMultiSelect());
			workOrderDto.setWorkAssignee(assigneeName);
		}
		if (workOrderDto.getWorkOrderStartDate() != null) {
			workOrderDto.setStartDate(Utility.stringToDate(workOrderDto.getWorkOrderStartDate()));
		}
		if (workOrderDto.getWorkOrderDate() != null) {
			workOrderDto.setOrderDate(Utility.stringToDate(workOrderDto.getWorkOrderDate()));
		}
		BeanUtils.copyProperties(workOrderDto, workOrderEntity);

		ContractMastEntity contractMastEntity = new ContractMastEntity();
		contractMastEntity.setContId(workOrderDto.getContractMastDTO().getContId());
		workOrderEntity.setContractMastEntity(contractMastEntity);

		setWorkOrderTermsandCondition(workOrderDto, workOrderEntity);
		WorkOrder updateWorkOrder = workOrderRepository.save(workOrderEntity);

		// update work definition history
		updateHistoryForWorkDefinition(updateWorkOrder);

		// inactive removed work definition year details by set its flag as 'D'
		inactiveDeletedDocuments(workOrderDto, removeFileId);

		inactiveTermsAndCondition(removeTermsById);

		// document uploading details
		requestDTO.setIdfId(workOrderDto.getWorkOrderNo());
		fileUploadService.doMasterFileUpload(attachments, requestDTO);

	}

	private void updateHistoryForWorkDefinition(WorkOrder updateWorkOrder) {

		try {
			WorkOrderHistory orderHistory = new WorkOrderHistory();
			orderHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());

			orderHistory.setContId(updateWorkOrder.getContractMastEntity().getContId());
			auditService.createHistory(updateWorkOrder, orderHistory);
		} catch (Exception exception) {
			LOGGER.error("Exception occured while updatng work order history " + exception);
		}

	}

	private void setWorkOrderTermsandCondition(WorkOrderDto workOrderDto, WorkOrder workOrderEntity) {

		if (workOrderDto.getWorkOrderTermsDtoList() != null) {
			List<WorkOrderTerms> termsCondList = new ArrayList<>();
			workOrderDto.getWorkOrderTermsDtoList().forEach(termsCondDet -> {
				WorkOrderTerms orderTerms = new WorkOrderTerms();
				BeanUtils.copyProperties(termsCondDet, orderTerms);
				if (termsCondDet.getTermsId() != null) {
					orderTerms.setUpdatedBy(workOrderDto.getUpdatedBy());
					orderTerms.setUpdatedDate(new Date());
					orderTerms.setLgIpMacUpd(workOrderDto.getLgIpMacUpd());
				} else {
					setTermsDetails(workOrderDto, orderTerms);
				}
				orderTerms.setWorkOrder(workOrderEntity);
				termsCondList.add(orderTerms);
			});
			workOrderEntity.setWorkOrderTermsList(termsCondList);
		}
	}

	private void inactiveDeletedDocuments(WorkOrderDto workOrderDto, List<Long> removeFileId) {
		if (removeFileId != null && !removeFileId.isEmpty()) {

			ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(removeFileId,
					workOrderDto.getUpdatedBy(), MainetConstants.FlagD);
		}

	}

	private void inactiveTermsAndCondition(List<Long> removeTermsById) {
		if (removeTermsById != null && !removeTermsById.isEmpty()) {
			workOrderRepository.deleteMBDetailsById(removeTermsById);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderDto> getFilterWorkOrderGenerationList(String workOrderNo, Date workStipulatedDate,
			Date contractFromDate, Date contractToDate, String vendorName, Long orgId) {
		List<WorkOrderDto> ordersList = new ArrayList<>();
		WorkOrderDto orderDto = null;
		ContractMastDTO contractMastDTO = null;
		List<WorkOrder> list = workOrderRepository.getFilterWorkOrderGenerationList(workOrderNo, workStipulatedDate,
				contractFromDate, contractToDate, vendorName, orgId);
		for (WorkOrder workOrder : list) {
			orderDto = new WorkOrderDto();
			BeanUtils.copyProperties(workOrder, orderDto);

			contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(workOrder.getContractMastEntity().getContId());
			contractMastDTO.setContNo(workOrder.getContractMastEntity().getContNo());
			orderDto.setContractMastDTO(contractMastDTO);
			orderDto.setOrderDateDesc(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(orderDto.getOrderDate()));
			if (orderDto.getContractFromDate() != null) {
				orderDto.setContractFromDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(orderDto.getContractFromDate()));
			}
			if (orderDto.getContractToDate() != null) {
				orderDto.setContractToDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(orderDto.getContractToDate()));
			}
			orderDto.setActualStartDateDesc(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(orderDto.getStartDate()));
			ordersList.add(orderDto);
		}
		return ordersList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getAllWorkOrderIdAndNoList(Long orgid) {
		return workOrderRepository.findAllWorkOrderIdAndCNo(orgid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderDto> getAllWorkOrderGroupByAssignee(Long orgId) {
		LOGGER.info("getAllWorkOrderGroupByAssignee method started");
		List<WorkOrderDto> workOrderList = new ArrayList<>();
		try {
			List<WorkOrder> workOrderEntity = workOrderRepository.findByOrgId(orgId);
			if (workOrderEntity != null && !workOrderEntity.isEmpty()) {
				workOrderEntity.forEach(orderEntity -> {
					getWorkOrderListGroupByAssignee(workOrderList, orderEntity);
				});
			}
		} catch (Exception ex) {
			throw new FrameworkException("Exception acured while calling method getAllWorkOrderGroupByAssignee ", ex);
		}
		LOGGER.info("getAllWorkOrderGroupByAssignee method End");
		return workOrderList;
	}

	// work assignee grouping
	private void getWorkOrderListGroupByAssignee(List<WorkOrderDto> workOrderList, WorkOrder orderEntity) {
		boolean present = orderEntity.getTenderMasterEntity().getTenderWorkList().stream()
				.anyMatch(tw -> tw.getEmployee() != null);
		if (present) {
			List<String> tempCodeList = new ArrayList<>();
			orderEntity.getTenderMasterEntity().getTenderWorkList().forEach(work -> {
				if (work.getEmployee() != null) {
					String workCode = work.getWorkDefinationEntity().getWorkcode();
					if (!tempCodeList.contains(workCode)) {
						Long tenderWorkId = work.getTndWId();
						Long empId = work.getEmployee().getEmpId();
						List<String> codeList = new ArrayList<>();
						codeList.add(workCode);
						orderEntity.getTenderMasterEntity().getTenderWorkList().stream()
								.filter(filteredWorkList -> (filteredWorkList.getTndWId() != tenderWorkId
										&& filteredWorkList.getEmployee() != null
										&& filteredWorkList.getEmployee().getEmpId() == empId))
								.forEach(wd -> {
									codeList.add(wd.getWorkDefinationEntity().getWorkcode());
									tempCodeList.add(wd.getWorkDefinationEntity().getWorkcode());
								});
						String codes = String.join(MainetConstants.operator.COMMA, codeList);

						// set tender master details
						TenderMasterDto tMaster = new TenderMasterDto();
						BeanUtils.copyProperties(orderEntity.getTenderMasterEntity(), tMaster);

						tMaster.setTenderAllWorks(codes);
						tMaster.setWorkAssigneeName(work.getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
								+ work.getEmployee().getEmplname());
						tMaster.setWorkAssigneeId(empId);

						// set work order details
						WorkOrderDto workDTO = new WorkOrderDto();
						BeanUtils.copyProperties(orderEntity, workDTO);
						workDTO.setTenderMasterDto(tMaster);
						workOrderList.add(workDTO);
					}
				}
			});
		}
	}

	/**
	 * search work order assignee
	 */
	@Override
	@Transactional
	public List<WorkOrderDto> searchWorkOrderGroupByAssignee(Long orderId) {
		LOGGER.info("searchWorkOrderGroupByAssignee method started");
		List<WorkOrderDto> workOrderList = new ArrayList<>();
		try {
			WorkOrder orderEntity = workOrderRepository.findOne(orderId);
			if (orderEntity != null) {
				getWorkOrderListGroupByAssignee(workOrderList, orderEntity);
			}

		} catch (Exception ex) {
			throw new FrameworkException("Exception acured while calling method searchWorkOrderGroupByAssignee ", ex);
		}
		LOGGER.info("searchWorkOrderGroupByAssignee method End");
		return workOrderList;
	}

	@Override
	@Transactional(readOnly = true)
	public WorkOrderDto getTenderWorkForUpdateByWorkIdAndAssignee(Long orderId, Long empId) {
		LOGGER.info("getTenderWorkForUpdateByWorkCodeAndAssignee method started");
		WorkOrderDto workOrderDto = new WorkOrderDto();
		WorkOrder workOrderEntity = workOrderRepository.findOne(orderId);
		if (workOrderEntity != null) {
			BeanUtils.copyProperties(workOrderEntity, workOrderDto);
			// set tender details and its related works details like work name, work code,
			// work id
			TenderMasterDto tenderDto = new TenderMasterDto();
			BeanUtils.copyProperties(workOrderEntity.getTenderMasterEntity(), tenderDto);
			setDescriptionDetailsForTender(workOrderEntity.getTenderMasterEntity(), tenderDto);

			// set tender work details
			List<TenderWorkDto> workDtoList = new ArrayList<>();
			workOrderEntity.getTenderMasterEntity().getTenderWorkList().forEach(workEntity -> {
				if (workEntity.getEmployee() == null || workEntity.getEmployee().getEmpId().equals(empId)) {
					TenderWorkDto workDto = new TenderWorkDto();
					BeanUtils.copyProperties(workEntity, workDto);
					workDto.setWorkName(workEntity.getWorkDefinationEntity().getWorkName());
					workDto.setWorkCode(workEntity.getWorkDefinationEntity().getWorkcode());
					workDto.setWorkId(workEntity.getWorkDefinationEntity().getWorkId());
					workDtoList.add(workDto);
				}
			});
			tenderDto.setWorkDto(workDtoList);
			workOrderDto.setTenderMasterDto(tenderDto);
		}
		LOGGER.info("getTenderWorkForUpdateByWorkCodeAndAssignee method End");
		return workOrderDto;
	}

	@Override
	@Transactional(readOnly = true)
	public WorkOrderDto getAssigneeTenderWork(Long orderId, Long empId) {
		LOGGER.info("getAssigneeTenderWork method started");
		WorkOrderDto workOrderDto = new WorkOrderDto();
		WorkOrder workOrderEntity = workOrderRepository.findOne(orderId);
		if (workOrderEntity != null) {
			BeanUtils.copyProperties(workOrderEntity, workOrderDto);
			// set tender details and its related works details like work name, work code,
			// work id
			TenderMasterDto tenderDto = new TenderMasterDto();
			BeanUtils.copyProperties(workOrderEntity.getTenderMasterEntity(), tenderDto);
			setDescriptionDetailsForTender(workOrderEntity.getTenderMasterEntity(), tenderDto);

			// set tender work details
			List<TenderWorkDto> workDtoList = new ArrayList<>();
			workOrderEntity.getTenderMasterEntity().getTenderWorkList().forEach(workEntity -> {

				// only those work that assigned to particular employee
				if (workEntity.getEmployee() != null && workEntity.getEmployee().getEmpId().equals(empId)) {
					TenderWorkDto workDto = new TenderWorkDto();
					BeanUtils.copyProperties(workEntity, workDto);
					workDto.setWorkName(workEntity.getWorkDefinationEntity().getWorkName());
					workDto.setWorkCode(workEntity.getWorkDefinationEntity().getWorkcode());
					workDtoList.add(workDto);
				}
			});
			tenderDto.setWorkDto(workDtoList);
			workOrderDto.setTenderMasterDto(tenderDto);
		}
		LOGGER.info("getAssigneeTenderWork method End");
		return workOrderDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderDto> workOrderList(Long venderId, Long orgId) {
		List<WorkOrderDto> workOrderList = new ArrayList<>();

		WorkOrderDto workdto = null;
		try {
			List<TenderWorkDto> dto = ApplicationContextProvider.getApplicationContext()
					.getBean(TenderInitiationService.class).findTenderByVenderId(venderId);
			if (dto != null) {
				for (TenderWorkDto tenderWorkDto : dto) {
					List<WorkOrder> workOrderEntity = workOrderRepository
							.getWorkOrderByOrderNumber(tenderWorkDto.getContractId(), orgId);
					for (WorkOrder workOrder : workOrderEntity) {
						workdto = new WorkOrderDto();
						BeanUtils.copyProperties(workOrder, workdto);
						if (workOrder.getContractMastEntity() != null
								&& workOrder.getContractMastEntity().getContractDetailList() != null
								&& !workOrder.getContractMastEntity().getContractDetailList().isEmpty()) {
							workdto.setContractAmt(new BigDecimal(
									workOrder.getContractMastEntity().getContractDetailList().get(0).getContAmount()));
						}
						workOrderList.add(workdto);
					}
				}
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while calling method workOrderList", e);
		}
		return workOrderList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderContractDetailsDto> findAllContractDetailsByOrgId(Long orgId) {

		List<WorkOrderContractDetailsDto> workOrderList = new ArrayList<>();
		List<Object[]> contractDetails = workOrderRepository.findAllContractDetailsByOrgId(orgId);
		Set<Long> set = new HashSet<>();
		if (contractDetails != null && !contractDetails.isEmpty()) {
			WorkOrderContractDetailsDto detailsDto = null;
			for (final Object contractDetailsDto[] : contractDetails) {
				if(set.contains((Long) contractDetailsDto[0]))
					continue;
				set.add((Long) contractDetailsDto[0]);
				detailsDto = new WorkOrderContractDetailsDto();
				detailsDto.setContId((Long) contractDetailsDto[0]);
				detailsDto.setContNo((String) contractDetailsDto[1]);

				if (contractDetailsDto[2] != null) {
					detailsDto.setContDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[2]));
				}
				detailsDto.setContDeptId((Long) contractDetailsDto[3]);

				if (contractDetailsDto[4] != null) {
					detailsDto.setContFromDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[4]));
				}
				if (contractDetailsDto[5] != null) {
					detailsDto.setContToDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[5]));
				}
				detailsDto.setContp1Name((String) contractDetailsDto[6]);
				detailsDto.setContp2Name((String) contractDetailsDto[16]);//#154413-to show actual contractor name
				if (contractDetailsDto[8] != null) {
					detailsDto.setContAmount(
							BigDecimal.valueOf((Double) contractDetailsDto[8]).setScale(2, RoundingMode.HALF_UP));
				}
				if (contractDetailsDto[9] != null) {
					detailsDto.setContToPeriod((Long) contractDetailsDto[9]);
				}

				detailsDto.setVmVendorAdd((String) contractDetailsDto[10]);
				detailsDto.setVendorEmailId((String) contractDetailsDto[11]);
				detailsDto.setVendorMobileNo((String) contractDetailsDto[12]);
				detailsDto.setTenderNo((String) contractDetailsDto[13]);
				if (contractDetailsDto[14] != null) {
					detailsDto.setTenderDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[14]));
				}
				
				if (contractDetailsDto[15] != null) {
					detailsDto.setContToPeriodUnit((Long) contractDetailsDto[15]);
				}
				workOrderList.add(detailsDto);
			}
		}

		return workOrderList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderContractDetailsDto> getAllContractDetailsInWorkOrderByOrgId(Long orgId) {

		List<WorkOrderContractDetailsDto> workOrderList = new ArrayList<>();
		List<Object[]> contractDetails = workOrderRepository.getAllContractDetailsInWorkOrderByOrgId(orgId);

		if (contractDetails != null && !contractDetails.isEmpty()) {
			WorkOrderContractDetailsDto detailsDto = null;
			for (final Object contractDetailsDto[] : contractDetails) {

				detailsDto = new WorkOrderContractDetailsDto();
				detailsDto.setContId((Long) contractDetailsDto[0]);
				detailsDto.setContNo((String) contractDetailsDto[1]);

				if (contractDetailsDto[2] != null) {
					detailsDto.setContDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[2]));
				}
				detailsDto.setContDeptId((Long) contractDetailsDto[3]);

				if (contractDetailsDto[4] != null) {
					detailsDto.setContFromDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[4]));
				}
				if (contractDetailsDto[5] != null) {
					detailsDto.setContToDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[5]));
				}
				detailsDto.setContp1Name((String) contractDetailsDto[6]);
				detailsDto.setContp2Name((String) contractDetailsDto[16]);//#154413-to show actual contractor name
				if (contractDetailsDto[8] != null) {
					detailsDto.setContAmount(
							BigDecimal.valueOf((Double) contractDetailsDto[8]).setScale(2, RoundingMode.HALF_UP));
				}

				if (contractDetailsDto[9] != null) {
					detailsDto.setContToPeriod((Long) contractDetailsDto[9]);
				}

				detailsDto.setVmVendorAdd((String) contractDetailsDto[10]);
				detailsDto.setVendorEmailId((String) contractDetailsDto[11]);
				detailsDto.setVendorMobileNo((String) contractDetailsDto[12]);
				detailsDto.setTenderNo((String) contractDetailsDto[13]);
				if (contractDetailsDto[14] != null) {
					detailsDto.setTenderDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[14]));
				}
				
				if (contractDetailsDto[15] != null) {
					detailsDto.setContToPeriodUnit((Long) contractDetailsDto[15]);
				}
				workOrderList.add(detailsDto);
			}
		}

		return workOrderList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderContractDetailsDto> getAllSummaryContractDetails(Long orgId) {

		List<WorkOrderContractDetailsDto> workOrderList = new ArrayList<>();
		List<Object[]> contractDetails = workOrderRepository.getAllSummaryContractDetails(orgId);

		if (contractDetails != null && !contractDetails.isEmpty()) {
			WorkOrderContractDetailsDto detailsDto = null;
			for (final Object contractDetailsDto[] : contractDetails) {

				detailsDto = new WorkOrderContractDetailsDto();
				detailsDto.setContId((Long) contractDetailsDto[0]);
				detailsDto.setContNo((String) contractDetailsDto[1]);

				if (contractDetailsDto[2] != null) {
					detailsDto.setContDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[2]));
				}
				detailsDto.setContDeptId((Long) contractDetailsDto[3]);

				if (contractDetailsDto[4] != null) {
					detailsDto.setContFromDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[4]));
				}
				if (contractDetailsDto[5] != null) {
					detailsDto.setContToDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(contractDetailsDto[5]));
				}
				detailsDto.setContp1Name((String) contractDetailsDto[6]);

				detailsDto.setVenderName((String) contractDetailsDto[7]);
				if (contractDetailsDto[8] != null) {
					detailsDto.setContAmount(
							BigDecimal.valueOf((Double) contractDetailsDto[8]).setScale(2, RoundingMode.HALF_UP));
				}
				detailsDto.setVmVendorAdd((String) contractDetailsDto[9]);
				detailsDto.setVendorEmailId((String) contractDetailsDto[10]);
				detailsDto.setVendorMobileNo((String) contractDetailsDto[11]);
				detailsDto.setWorkOrderStatus((String) contractDetailsDto[12]);
				workOrderList.add(detailsDto);
			}
		}

		return workOrderList;
	}

	@Override
	@Transactional
	public void updateContractVariationStatus(Long contId, String flag) {
		workOrderRepository.updateContractVariationStatus(contId, flag);
	}

	@Override
	@Transactional(readOnly = true)
	public WorkOrderDto fetchWorkOrderByContId(Long contId, Long orgId) {
		WorkOrderDto workOrderDto = new WorkOrderDto();
		List<WorkOrderTermsDto> workOrderTermsDtos = new ArrayList<>();
		WorkOrderTermsDto termsDto = null;
		try {
			WorkOrder workOrderEntity = workOrderRepository.fetchWorkOrderByContId(contId, orgId);
			BeanUtils.copyProperties(workOrderEntity, workOrderDto);
			for (WorkOrderTerms detailsEntity : workOrderEntity.getWorkOrderTermsList()) {
				termsDto = new WorkOrderTermsDto();
				BeanUtils.copyProperties(detailsEntity, termsDto);
				workOrderTermsDtos.add(termsDto);
			}
			workOrderDto.setWorkOrderTermsDtoList(workOrderTermsDtos);
		}catch(Exception e) {
			
		}
		return workOrderDto;

	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderContractDetailsDto> findAllWorkOrderGeneratedVendorDetail(Long orgId) {
		List<WorkOrderContractDetailsDto> workOrderList = new ArrayList<>();
		List<Object[]> contractorDetails = workOrderRepository.findAllWorkOrderGeneratedVendorDetail(orgId);

		if (contractorDetails != null && !contractorDetails.isEmpty()) {
			WorkOrderContractDetailsDto detailsDto = null;
			for (final Object contractDetailsDto[] : contractorDetails) {

				detailsDto = new WorkOrderContractDetailsDto();

				if (contractDetailsDto[0] != null) {
					detailsDto.setVmVendorid((Long) contractDetailsDto[0]);
				}

				if (contractDetailsDto[1] != null) {
					detailsDto.setVenderName((String) contractDetailsDto[1]);
				}
				workOrderList.add(detailsDto);
			}
		}

		return workOrderList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkOrderDto> getAllLegacyWorkOrder(String fromLegacy, Long orgId) {
		List<WorkOrderDto> workOrderList = new ArrayList<>();
		List<WorkOrder> workOrderEntity = null;
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		Long legacyId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagL, "WRT", org).getLookUpId();
		try {
			if (fromLegacy != null && fromLegacy.equals(MainetConstants.FlagY)) {
				workOrderEntity = workOrderRepository.findAllLegacyWorkOrder(legacyId, orgId);
			} 
			else if (fromLegacy != null) {
				workOrderEntity = workOrderRepository.findAllWorkOrder(orgId);
			}
			
			else {
				workOrderEntity = workOrderRepository.findAllNonLegacyWorkOrder(legacyId, orgId);
			}
			if (!CollectionUtils.isEmpty(workOrderEntity)) {
				workOrderEntity.forEach(entity -> {
					WorkOrderDto dto = new WorkOrderDto();
					BeanUtils.copyProperties(entity, dto);

					// set tender details and its related works details like work name, work code,
					// work id
					ContractMastDTO contractMastDTO = new ContractMastDTO();
					contractMastDTO.setContNo(entity.getContractMastEntity().getContNo());
					contractMastDTO.setContId(entity.getContractMastEntity().getContId());
					dto.setContractMastDTO(contractMastDTO);
					dto.setContractAmount(
							entity.getContractMastEntity().getContractDetailList().get(0).getContAmount());
					dto.setContractValue(BigDecimal.valueOf(dto.getContractAmount()).setScale(2, RoundingMode.HALF_UP));
					if (dto.getContractFromDate() != null) {
						dto.setContractFromDateDesc(
								new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(dto.getContractFromDate()));
					}

					List<WorkOrderTermsDto> termsList = new ArrayList<>();
					entity.getWorkOrderTermsList().forEach(terms -> {
						WorkOrderTermsDto termsDto = new WorkOrderTermsDto();
						BeanUtils.copyProperties(terms, termsDto);
						termsList.add(termsDto);
					});

					dto.setWorkOrderTermsDtoList(termsList);
					String ids = dto.getWorkAssignee();
					if (ids != null && !ids.isEmpty()) {
						String array[] = ids.split(MainetConstants.operator.COMMA);
						for (String id : array) {
							dto.getMultiSelect().add(id);
						}
					}
					workOrderList.add(dto);
				});
			}

		} catch (Exception ex) {
			throw new FrameworkException("Exception acured while calling method getAllLegacyWorkOrder : ", ex);
		}
		return workOrderList;
	}
	//added by sadik.shaikh
	public WorkOrder getWorkOrderbyorderNo(String orderNo) {
		WorkOrder order=new WorkOrder();
		order=workOrderRepository.findWorkOrderbyOrderNo(orderNo);
		return order;
		
	}
	
	@Override
	public List<WorkOrderDto> getFilterWorkOrderGeneration(String workOrderNo, Date workStipulatedDate, Long codId1,
			Long codId2, Long codId3, Long orgId, Long dpDeptId) {
		
		List<Object[]> list =null;
		if(workOrderNo != null){
				list = workOrderRepository.getFilterWorkOrderGeneration(workOrderNo, workStipulatedDate, orgId,
					codId1, codId2, codId3, dpDeptId);
			
		}  else if(workStipulatedDate!=null) {
				list = workOrderRepository.getFilterWorkOrderGeneration3(workStipulatedDate, orgId,
						  codId1, codId2, codId3, dpDeptId); 
		} else if(workOrderNo != null) { 
				list = workOrderRepository.getFilterWorkOrderGeneration2(workOrderNo, orgId, codId1,
					  codId2, codId3, dpDeptId); 
			 
		} else {
				list = workOrderRepository.getFilterWorkOrderGeneration1(orgId, codId1, codId2,
						  codId3, dpDeptId);
		}
			 
		List<WorkOrderDto> listDto = new ArrayList<WorkOrderDto>();
		if (!list.isEmpty()) {
			for (Object[] obj : list) {

				WorkOrderDto dto = new WorkOrderDto();
				ContractMastDTO contractMastDTO = new ContractMastDTO();
				dto.setWorkOrderNo(obj[1].toString());
				dto.setWorkId(Long.valueOf(obj[0].toString()));
				dto.setWorkOrderDate(Utility.dateToString((Date) obj[2]));
				contractMastDTO.setContNo(obj[14].toString());
				dto.setActualStartDateDesc((Utility.dateToString((Date) obj[7])));
				contractMastDTO.setContId(Long.valueOf(obj[3].toString()));
				dto.setContractMastDTO(contractMastDTO);
				listDto.add(dto);

			}
		}
		return listDto;
	}
}
