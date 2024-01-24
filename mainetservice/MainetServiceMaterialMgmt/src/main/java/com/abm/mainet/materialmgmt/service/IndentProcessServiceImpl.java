package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.dao.IndentProcessDao;
import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;
import com.abm.mainet.materialmgmt.domain.IndentIssueEntity;
import com.abm.mainet.materialmgmt.domain.IndentItemEntity;
import com.abm.mainet.materialmgmt.domain.IndentProcessEntity;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.IndentIssueDto;
import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;
import com.abm.mainet.materialmgmt.dto.IndentProcessItemDto;
import com.abm.mainet.materialmgmt.repository.ICommonTransactionRepository;
import com.abm.mainet.materialmgmt.repository.IndentProcessRepository;
import com.abm.mainet.materialmgmt.repository.StoreMasterRepository;

@Service
public class IndentProcessServiceImpl implements IndentProcessService {

	@Autowired
	private IndentProcessRepository indentProcessRepository;

	@Autowired
	private IndentProcessDao indentProcessDao;

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ItemMasterService itemMasterservice;

	@Autowired
	private IEmployeeService employeeService;
	
	@Resource
	private EmployeeJpaRepository employeeJpaRepository;

	@Resource
	private StoreMasterRepository storeMasterRepository;
  
	@Resource
	private ICommonTransactionRepository transactionRepository;

   @Override
   @Transactional
   public IndentProcessDTO saveIndentProcess(IndentProcessDTO indentProcessDTO, long levelCheck, WorkflowTaskAction workflowTaskAction) {

		IndentProcessEntity indentProcessEntity = new IndentProcessEntity();
		BeanUtils.copyProperties(indentProcessDTO, indentProcessEntity);

		List<IndentItemEntity> indentItemEntitieList = new ArrayList<>();
		List<IndentIssueEntity> indentIssueEntityList = new ArrayList<>();

		indentProcessDTO.getItem().forEach(indentItemDto -> {
			IndentItemEntity indentItemEntity = new IndentItemEntity();
			BeanUtils.copyProperties(indentItemDto, indentItemEntity);
			indentItemEntity.setIndentid(indentProcessEntity);
			indentItemEntity.setStoreid(indentProcessEntity.getStoreid());
			
			indentItemDto.getIndentIssueDtoList().forEach(indentIssueDto -> {
				if (indentIssueDto.getInditemid().equals(indentItemDto.getInditemid())) {
					IndentIssueEntity indentIssueEntity = new IndentIssueEntity();
					BeanUtils.copyProperties(indentIssueDto, indentIssueEntity);
					indentIssueEntity.setIndentid(indentProcessEntity);
					indentIssueEntity.setIndentItemEntity(indentItemEntity);
					indentIssueEntity.setItemid(indentItemDto.getItemid());
					indentIssueEntity.setIsreturned(MainetConstants.FlagN);
					indentIssueEntityList.add(indentIssueEntity);
				}
			});
			indentItemEntity.setIndentIssueEntities(indentIssueEntityList);
			indentItemEntitieList.add(indentItemEntity);
		});
		indentProcessEntity.setItem(indentItemEntitieList);
		indentProcessRepository.save(indentProcessEntity);

		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(
				MainetConstants.MaterialManagement.DeptIndentShortCODE, indentProcessDTO.getOrgid());
		if (0L == levelCheck) {
			initializeWorkFlowForFreeService(indentProcessDTO, serviceMas);
		} else {
			updateWorkFlowService(workflowTaskAction, serviceMas);
		}

		/** Save Department Indent Details to Transaction Table */
		if(2l == levelCheck)
			transactionRepository.save(saveDeptIndentDataToTransactionEntity(indentProcessDTO));
		
		return indentProcessDTO;
	}

	/** Transaction Type : Purchase-P, Indent-I, Store Indent Outward-M, Store Indent
	 	Inward-S Disposal-D, Adjustment-A, Transfer-T, Expired-E, Opening Balance-O,  Returned-R  */
	private List<CommonTransactionEntity> saveDeptIndentDataToTransactionEntity(IndentProcessDTO indentProcessDTO) {
		List<CommonTransactionEntity> transactionEntityList = new ArrayList<>();
		indentProcessDTO.getItem().forEach(itemDetail -> {
			itemDetail.getIndentIssueDtoList().forEach(issueDetail -> {
				CommonTransactionEntity transactionEntity = new CommonTransactionEntity();
				transactionEntity.setTransactionDate(issueDetail.getLmoddate());
				transactionEntity.setTransactionType(MainetConstants.FlagI);
				transactionEntity.setReferenceNo(indentProcessDTO.getIndentno());				
				transactionEntity.setStoreId(indentProcessDTO.getStoreid());
				transactionEntity.setItemId(itemDetail.getItemid());				
				transactionEntity.setBinLocation(issueDetail.getBinlocation());
				transactionEntity.setItemNo(issueDetail.getItemno());
				transactionEntity.setDebitQuantity(new BigDecimal(issueDetail.getIssueqty()));
				transactionEntity.setStatus(MainetConstants.FlagY);
				transactionEntity.setDisposalStatus(MainetConstants.FlagN);
				transactionEntity.setOrgId(issueDetail.getORGID());
				transactionEntity.setUserId(issueDetail.getUSER_ID());
				transactionEntity.setLangId(issueDetail.getLANGID());
				transactionEntity.setCreatedDate(issueDetail.getLmoddate());
				transactionEntity.setLgIpMac(issueDetail.getLG_IP_MAC());
				transactionEntityList.add(transactionEntity);
			});
		});
		return transactionEntityList;
	}

	
	@Transactional
	public void initializeWorkFlowForFreeService(IndentProcessDTO indentProcessDTO, ServiceMaster serviceMas) {		
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(employeeService.getEmpFullNameById(indentProcessDTO.getUser_id()));
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(indentProcessDTO.getUser_id());
		applicationMetaData.setReferenceId(indentProcessDTO.getIndentno());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(indentProcessDTO.getOrgid());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}
	

	@Override
	@Transactional
	public void updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster serviceMas) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(serviceMasterService.getProcessName(serviceMas.getSmServiceId(), serviceMas.getOrgid()));
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
	}
	

	@Override
	public List<IndentProcessDTO> searchIndentByStoreName(Long storeid, String indentno, Long deptId, Long indenter,
			String status, Long orgid) {
		List<Object[]> indentDataList = indentProcessDao.getIndentSummaryList(storeid, indentno, deptId, indenter,
				status, orgid);
		List<IndentProcessDTO> indentProcessDTOList = new ArrayList<IndentProcessDTO>();
		IndentProcessDTO indentProcessDTO;
		String[] indentDate;
		for(Object[] indentData : indentDataList) {
			indentProcessDTO = new IndentProcessDTO();
			indentProcessDTO.setIndentid(Long.valueOf(indentData[0].toString()));
			indentProcessDTO.setIndentno(indentData[1].toString());			
			indentDate = indentData[2].toString().split(MainetConstants.WHITE_SPACE);
			indentProcessDTO.setIndentdate(Utility.stringToDate(
					LocalDate.parse(indentDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			indentProcessDTO.setIndenterName(employeeService.getEmpFullNameById(Long.valueOf(indentData[3].toString())));
			indentProcessDTO.setStoreDesc(storeMasterservice.getStorenameByStoreId(Long.valueOf(indentData[4].toString())));
			indentProcessDTO.setStatus(indentData[5].toString());
			indentProcessDTOList.add(indentProcessDTO);
		}
		return indentProcessDTOList;
	}


	@Override
	@Transactional
	public IndentProcessDTO getIndentDataByIndentId(Long indentId, Long orgId) {
		IndentProcessEntity indentProcessEntity = indentProcessRepository.findByIndentidAndOrgid(indentId, orgId);		
		return getIndentEntityToIndentDto(indentProcessEntity, orgId);
	}
	

	@Override
	@Transactional
	public IndentProcessDTO getIndentDataByIndentNo(String indentNo, Long orgId) {
		IndentProcessEntity indentProcessEntity = indentProcessRepository.findByIndentNo(indentNo, orgId);	
		return getIndentEntityToIndentDto(indentProcessEntity, orgId);
	}
	
	
	/**
	 * To map IndentProcessEntity to IndentProcessDTO
	 * @param IndentProcessEntity
	 * @return IndentProcessDTO
	 */
	public IndentProcessDTO getIndentEntityToIndentDto(IndentProcessEntity indentProcessEntity, Long orgId) {
		IndentProcessDTO indentProcessDTO = new IndentProcessDTO();
		BeanUtils.copyProperties(indentProcessEntity, indentProcessDTO);
		
		final Object[] empObject = employeeJpaRepository.getEmployeDetailsById(indentProcessDTO.getIndenter(), orgId);
		Object[] employee = (Object[]) empObject[0];
		indentProcessDTO.setDeptName(employee[1].toString());
		indentProcessDTO.setDesgName(employee[3].toString());		
		if(null !=  employee[5] && null != employee[6])
			indentProcessDTO.setReportingMgrName(employee[5].toString()+ MainetConstants.WHITE_SPACE + employee[6].toString());
				
		Object[] storeDet = (Object[]) storeMasterRepository.getStoreDetailsByStore(indentProcessDTO.getStoreid(), orgId)[0];
		indentProcessDTO.setLocationName(storeDet[1].toString());
		indentProcessDTO.setStoreDesc(storeDet[2].toString());
		
		List<IndentProcessItemDto> indentProcessItemDtoList = new ArrayList<>();
		indentProcessEntity.getItem().forEach(itemsEntiry -> {
			IndentProcessItemDto indentItemDto = new IndentProcessItemDto();
			BeanUtils.copyProperties(itemsEntiry, indentItemDto);
						
			Object[] itemObj = (Object[]) itemMasterservice.getItemDetailObjectByItemId(indentItemDto.getItemid())[0];
			indentItemDto.setItemName(itemObj[1].toString());
			indentItemDto.setUom(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					Long.valueOf(itemObj[2].toString()), orgId, "UOM").getLookUpDesc());	
			LookUp managementLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(itemObj[3].toString()), orgId, "IMM");			
			indentItemDto.setManagementCode(managementLookup.getLookUpCode());
			indentItemDto.setManagementDesc(managementLookup.getLookUpDesc());
			
			List<IndentIssueDto> indentIssueDtoList = new ArrayList<>();
			itemsEntiry.getIndentIssueEntities().forEach(issueEntity -> {
				if (issueEntity.getIndentItemEntity().getInditemid() == indentItemDto.getInditemid()) {
					IndentIssueDto indentIssueDto = new IndentIssueDto();
					BeanUtils.copyProperties(issueEntity, indentIssueDto);
					indentIssueDtoList.add(indentIssueDto);
				}
			});
			indentItemDto.setIndentIssueDtoList(indentIssueDtoList);
			indentProcessItemDtoList.add(indentItemDto);
		});
		indentProcessDTO.setItem(indentProcessItemDtoList);
		return indentProcessDTO;
	}
	

	
	@Override
	@Transactional
	public void updateIndentStatus(Long orgId, String indentNo, String status, String wfFlag) {
		indentProcessRepository.updateIndentStatus(orgId, indentNo, status, wfFlag);
	}
	
	
	@Override
	@Transactional
	public List<IndentProcessDTO> findIndentByorgId(Long orgId) {
		List<IndentProcessEntity> entityList = indentProcessRepository.findIndentByorgId(orgId);
		List<IndentProcessDTO> indentProcessDTOList = new ArrayList<>();
		entityList.forEach(entity->{
			IndentProcessDTO indentProcessDTO = new IndentProcessDTO();
			if (entity != null) {
				BeanUtils.copyProperties(entity, indentProcessDTO);
				if(indentProcessDTO.getStoreid() != null && indentProcessDTO.getIndenter() != null) {
					indentProcessDTO.setStoreDesc(storeMasterservice.getStoreMasterByStoreId(entity.getStoreid()).getStoreName());
					indentProcessDTO.setIndenterName(employeeService.findEmployeeById(entity.getIndenter()).getEmpname());
				}
			}			
			indentProcessDTOList.add(indentProcessDTO);
		});
		return indentProcessDTOList;
	}
	
	
	/**To Get Bin Location List based on StoreId and ItemID*/
	@Override
	@Transactional(readOnly = true)
	public List<BinLocMasDto> findAllBinLocationByItemID(Long orgId, Long storeId, Long itemId) {
		//List<Object[]> entitiesList = indentProcessRepository.findByOrgIdAndItemId(orgId,storeId, itemId);
		List<Object[]> entitiesList = transactionRepository.fetchBinLocationListForIndent(orgId, storeId, itemId);		
		List<BinLocMasDto> binLocMasDtoList = new ArrayList<>();
		BinLocMasDto binLocMasDto;
		for (Object[] obj : entitiesList) {
			binLocMasDto = new BinLocMasDto();
				if (null != obj[0])
					binLocMasDto.setBinLocId(Long.valueOf(obj[0].toString()));
				if (null != obj[1]) 
					binLocMasDto.setBinLocation(obj[1].toString());
				binLocMasDtoList.add(binLocMasDto);
			}
		return binLocMasDtoList;
	}
	
	
	/**To Get Item No. list based on Bin Location, ItemID and StoreId */	
	@Override
	public List<String> getItemNumberListByBinLoc(Long binLocation, Long itemid, Long storeId, Long orgId) {
		return transactionRepository.fetchItemNoListForIndent(orgId, storeId, itemid, binLocation);
	}
	
	//Not in use : this is old method before MDN and Return services
	@Override
	public Double getSumOfAvailbleQuantities(Long binLocation, Long itemid, String itemNo, Long storeId, Long orgId) {
		Double availbleQty = indentProcessRepository.getSumOfQuantityByItemNoAndBinLocation(binLocation, itemid, itemNo, storeId,orgId);	
		return availbleQty= availbleQty == null ?0.0 : availbleQty;	
	}
	
	//Not in use : this is old method before MDN and Return services
	@Override
	public Double getSumOfNotInBatchAvailbleQuantities(Long binLocation, Long itemid, Long storeId, Long orgId) {
		Double availbleQty = indentProcessRepository.getSumOfNotInBatchQtyByBin(binLocation, itemid, storeId, orgId);
		return availbleQty= availbleQty == null ?0.0 : availbleQty;
	}
	
	/** To get available quantity based on orgId, storeId, itemid, binLocation, itemNo */
	@Override
	public Double fetchBalanceQuantityForIndent(Long orgId, Long storeId, Long itemid, Long binLocation, String itemNo) {
		return transactionRepository.fetchBalanceQuantityForIndent(orgId, storeId, itemid, binLocation, itemNo);		
	}
	

}
