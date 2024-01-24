package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;
import com.abm.mainet.materialmgmt.domain.MaterialDispatchNote;
import com.abm.mainet.materialmgmt.domain.MaterialDispatchNoteItems;
import com.abm.mainet.materialmgmt.domain.MaterialDispatchNoteItemsEntry;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteDTO;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteItemsDTO;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteItemsEntryDTO;
import com.abm.mainet.materialmgmt.repository.ICommonTransactionRepository;
import com.abm.mainet.materialmgmt.repository.IMaterialDispatchNoteRepository;
import com.abm.mainet.materialmgmt.repository.IStoreIndentRepository;
import com.abm.mainet.materialmgmt.repository.ItemMasterRepository;
import com.abm.mainet.materialmgmt.repository.StoreMasterRepository;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;


@Service
@Transactional
public class MaterialDispatchNoteService implements IMaterialDispatchNoteService {
	
	@Resource
	private IStoreIndentRepository storeIndentRepository; 
	
	@Resource
	private StoreMasterRepository storeMasterRepository;
	
	@Resource
	private ItemMasterService itemMasterservice;
	
	@Resource
	private IMaterialDispatchNoteRepository dispatchNoteRepository;
	
	@Resource
	private CommonService commonService;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private ItemMasterRepository itemMasterRepository;

	@Resource
	private ICommonTransactionRepository transactionRepository;
	
	@Override
	public void saveMaterialDispatchNote(MaterialDispatchNoteDTO dispatchNoteDTO) {
		MaterialDispatchNote materialDispatchNote = new MaterialDispatchNote();
		BeanUtils.copyProperties(dispatchNoteDTO, materialDispatchNote);
		MaterialDispatchNoteItems dispatchNoteItems;
		for(MaterialDispatchNoteItemsDTO matDispatchItemDTO : dispatchNoteDTO.getMatDispatchItemList()) {
			if(null != matDispatchItemDTO.getIssuedQty()) {
				dispatchNoteItems = new MaterialDispatchNoteItems();
				BeanUtils.copyProperties(matDispatchItemDTO, dispatchNoteItems);
				dispatchNoteItems.setMaterialDispatchNote(materialDispatchNote);
				dispatchNoteItems.setIssueStoreId(materialDispatchNote.getIssueStoreId());
				
				MaterialDispatchNoteItemsEntry dispatchNoteItemsEntry;
				for(MaterialDispatchNoteItemsEntryDTO notesItemEntryDTO : matDispatchItemDTO.getMatDispatchItemsEntryList()) {
					dispatchNoteItemsEntry = new MaterialDispatchNoteItemsEntry();
					BeanUtils.copyProperties(notesItemEntryDTO, dispatchNoteItemsEntry);
					dispatchNoteItemsEntry.setMaterialDispatchNote(materialDispatchNote);
					dispatchNoteItemsEntry.setMaterialDispatchNoteItems(dispatchNoteItems);
					dispatchNoteItemsEntry.setIssueStoreId(materialDispatchNote.getIssueStoreId());
					dispatchNoteItemsEntry.setItemId(dispatchNoteItems.getItemId());
					dispatchNoteItems.getMatDispatchItemsEntryEntities().add(dispatchNoteItemsEntry);
				}
				materialDispatchNote.getMatDispatchItemEntities().add(dispatchNoteItems);
			}
		}
		dispatchNoteRepository.save(materialDispatchNote);
		
		/** save MDN Data To Transaction Table */
		transactionRepository.save(saveMDNDataToTransactionEntity(dispatchNoteDTO));
	}
	
	
	/** Transaction Type : Purchase-P, Indent-I, Store Indent Outward-M, Store Indent Inward-S,
	  		Disposal-D, Adjustment-A, Transfer-T, Expired-E, Opening Balance-O, Returned-R  */
	private List<CommonTransactionEntity> saveMDNDataToTransactionEntity(MaterialDispatchNoteDTO dispatchNoteDTO) {
		List<CommonTransactionEntity> transactionEntityList = new ArrayList<>();
		dispatchNoteDTO.getMatDispatchItemList().forEach(matDispatchItem -> {
			matDispatchItem.getMatDispatchItemsEntryList().forEach(dispatchItemsEntry -> {
				if (MainetConstants.FlagI.equalsIgnoreCase(dispatchNoteDTO.getStatus())
						|| (MainetConstants.FlagS.equalsIgnoreCase(dispatchNoteDTO.getStatus())
								&& dispatchItemsEntry.getAcceptQty().compareTo(BigDecimal.ZERO) > 0)) {

					CommonTransactionEntity transactionEntity = new CommonTransactionEntity();
					transactionEntity.setReferenceNo(dispatchNoteDTO.getMdnNumber());					
					transactionEntity.setItemId(matDispatchItem.getItemId());
					transactionEntity.setItemNo(dispatchItemsEntry.getItemNo());
					transactionEntity.setMfgDate(dispatchItemsEntry.getMfgDate());
					transactionEntity.setExpiryDate(dispatchItemsEntry.getExpiryDate());
					transactionEntity.setStatus(MainetConstants.FlagY);
					transactionEntity.setDisposalStatus(MainetConstants.FlagN);
					transactionEntity.setOrgId(dispatchItemsEntry.getOrgId());
					transactionEntity.setLangId(dispatchItemsEntry.getLangId());

					if (MainetConstants.FlagI.equalsIgnoreCase(dispatchNoteDTO.getStatus())) {
						transactionEntity.setTransactionDate(dispatchItemsEntry.getLmodDate());						
						transactionEntity.setStoreId(dispatchNoteDTO.getIssueStoreId());
						transactionEntity.setTransactionType(MainetConstants.FlagM);
						transactionEntity.setBinLocation(dispatchItemsEntry.getIssueBinLocation());
						transactionEntity.setDebitQuantity(dispatchItemsEntry.getQuantity());
						transactionEntity.setUserId(dispatchItemsEntry.getUserId());
						transactionEntity.setCreatedDate(dispatchItemsEntry.getLmodDate());
						transactionEntity.setLgIpMac(dispatchItemsEntry.getLgIpMac());
					} else if (MainetConstants.FlagS.equalsIgnoreCase(dispatchNoteDTO.getStatus())) {
						transactionEntity.setTransactionDate(dispatchItemsEntry.getUpdatedDate());						
						transactionEntity.setStoreId(dispatchNoteDTO.getRequestStoreId());
						transactionEntity.setTransactionType(MainetConstants.FlagS);
						transactionEntity.setBinLocation(dispatchItemsEntry.getReceivedBinLocation());
						transactionEntity.setCreditQuantity(dispatchItemsEntry.getAcceptQty());
						transactionEntity.setUserId(dispatchItemsEntry.getUpdatedBy());
						transactionEntity.setCreatedDate(dispatchItemsEntry.getUpdatedDate());
						transactionEntity.setLgIpMac(dispatchItemsEntry.getLgIpMacUpd());
					}
					transactionEntityList.add(transactionEntity);
				}
			});
		});
		return transactionEntityList;
	}
	
	


	@Override
	public void initializeWorkFlowForFreeService(MaterialDispatchNoteDTO dispatchNoteDTO, ServiceMaster serviceMas) {
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(dispatchNoteDTO.getUserName());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(dispatchNoteDTO.getUserId());
		applicationMetaData.setReferenceId(dispatchNoteDTO.getMdnNumber());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(dispatchNoteDTO.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}


	@Override
	public void updateWorkFlowService(WorkflowTaskAction workflowActionDto, ServiceMaster serviceMas) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		Organisation organisation = new Organisation();
		organisation.setOrgid(serviceMas.getOrgid());
		workflowProcessParameter.setProcessName(CommonMasterUtility.getNonHierarchicalLookUpObject(
					serviceMas.getSmProcessId(), organisation).getLookUpCode().toLowerCase());
		workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
	}


	@Override
	public List<Object[]> getgetStoreIndentListForMDN(Long orgId) {
		List<Object[]> storeIndentObjects = dispatchNoteRepository.getStoreIndentListForMDN(orgId, MainetConstants.FlagN);
		Map<Long, String> storeIndentMap = new HashMap<>();
		storeIndentObjects.forEach(object -> storeIndentMap.put(Long.valueOf(object[0].toString()), object[1].toString()));
		storeIndentObjects.clear();
		storeIndentObjects = storeIndentMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.map(entry -> new Object[] { entry.getKey(), entry.getValue() }).collect(Collectors.toList());
		return storeIndentObjects;
	}


	@Override
	public MaterialDispatchNoteDTO getStoreIndentDetailsById(MaterialDispatchNoteDTO dispatchNoteDTO, Long orgId) {
		dispatchNoteDTO.getMatDispatchItemList().clear(); // clear detail list, else appends past list data
		
		List<Object[]> storeIndentObjectList = dispatchNoteRepository.getDataByStoreIndentId(dispatchNoteDTO.getSiId(), orgId);
		dispatchNoteDTO.setRequestStoreId(Long.valueOf(storeIndentObjectList.get(0)[1].toString()));
		dispatchNoteDTO.setIssueStoreId(Long.valueOf(storeIndentObjectList.get(0)[2].toString()));		
		String[] indentDate = storeIndentObjectList.get(0)[3].toString().split(MainetConstants.WHITE_SPACE);
		dispatchNoteDTO.setStoreIndentdate(Utility.stringToDate(
				LocalDate.parse(indentDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
		
		List<Object[]> reqStoreDetList = storeMasterRepository.getStoreDetailListByStore(
				Arrays.asList(dispatchNoteDTO.getIssueStoreId(), dispatchNoteDTO.getRequestStoreId()), orgId);
		Map<Long, Object[]> storeDataMap = new HashMap<>();
		for(Object[] reqStoreDet : reqStoreDetList) {
			storeDataMap.put(Long.valueOf(reqStoreDet[0].toString()), reqStoreDet);
		}
		
		Object[] reqStoreDet = storeDataMap.get(dispatchNoteDTO.getRequestStoreId());
		dispatchNoteDTO.setRequestStore(reqStoreDet[1].toString());
		dispatchNoteDTO.setRequestStoreLocName(reqStoreDet[3].toString());
		dispatchNoteDTO.setRequestedByName(reqStoreDet[5].toString() + MainetConstants.WHITE_SPACE + reqStoreDet[6].toString());

		Object[] issueStoreDet = storeDataMap.get(dispatchNoteDTO.getIssueStoreId());
		dispatchNoteDTO.setIssueStore(issueStoreDet[1].toString());
		dispatchNoteDTO.setIssueStoreLocName(issueStoreDet[3].toString());
		dispatchNoteDTO.setIssueInchargeName(issueStoreDet[5].toString() + MainetConstants.WHITE_SPACE + issueStoreDet[6].toString());
			
		storeIndentObjectList.forEach(indentObject->{
			if(Double.valueOf(indentObject[9].toString()) > Double.valueOf(indentObject[10].toString())) {
				MaterialDispatchNoteItemsDTO dispatchNoteItemsDTO = new MaterialDispatchNoteItemsDTO(); 
				dispatchNoteItemsDTO.setSiItemId(Long.valueOf(indentObject[4].toString()));
				dispatchNoteItemsDTO.setItemId(Long.valueOf(indentObject[5].toString()));
				dispatchNoteItemsDTO.setItemName(indentObject[6].toString());
				dispatchNoteItemsDTO.setUom(Long.valueOf(indentObject[7].toString()));
				dispatchNoteItemsDTO.setUomDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
						dispatchNoteItemsDTO.getUom(), orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());
				LookUp managementLookup = (CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
						Long.valueOf(indentObject[8].toString()), orgId, MainetMMConstants.MmItemMaster.ITEM_MANAGEMENT_PREFIX));			
				dispatchNoteItemsDTO.setManagement(managementLookup.getLookUpCode());
				dispatchNoteItemsDTO.setManagementDesc(managementLookup.getLookUpDesc());
				dispatchNoteItemsDTO.setRequestedQty(new BigDecimal(indentObject[9].toString()).setScale(2, RoundingMode.HALF_UP));	
				dispatchNoteItemsDTO.setPrevRecQty(new BigDecimal(indentObject[10].toString()).setScale(2, RoundingMode.HALF_UP));
				dispatchNoteItemsDTO.setRemainingQty(dispatchNoteItemsDTO.getRequestedQty().subtract(dispatchNoteItemsDTO.getPrevRecQty()));
				dispatchNoteDTO.getMatDispatchItemList().add(dispatchNoteItemsDTO);
			}
		});		
		return dispatchNoteDTO;
	}
	
	
	@Override
	public MaterialDispatchNoteDTO getMDNApprovalDataByMDNNumber(String mdnNumber, Long orgId) {
		MaterialDispatchNote dispatchNoteEntity = dispatchNoteRepository.findByMdnNumberAndOrgId(mdnNumber, orgId);
		return mapMaterialDispatchEntityToMaterialDispatchDTO(dispatchNoteEntity, orgId);		
	}
	

	@Override
	public MaterialDispatchNoteDTO getMDNDataByMDNId(Long mdnId, Long orgId) {
		MaterialDispatchNote dispatchNoteEntity = dispatchNoteRepository.findByMdnIdAndOrgId(mdnId, orgId);
		return mapMaterialDispatchEntityToMaterialDispatchDTO(dispatchNoteEntity, orgId);
	}
	
	
	private MaterialDispatchNoteDTO mapMaterialDispatchEntityToMaterialDispatchDTO( MaterialDispatchNote dispatchNoteEntity, Long orgId) {
		MaterialDispatchNoteDTO dispatchNoteDTO = new MaterialDispatchNoteDTO();
		BeanUtils.copyProperties(dispatchNoteEntity, dispatchNoteDTO);
		
		Object[] indentObject = (Object[]) storeIndentRepository.getStoreIndentNumberAndDate(dispatchNoteDTO.getSiId(), orgId)[0];
		dispatchNoteDTO.setSiNumber(indentObject[0].toString());
		String[] indentDate = indentObject[1].toString().split(MainetConstants.WHITE_SPACE);
		dispatchNoteDTO.setStoreIndentdate(Utility.stringToDate(
				LocalDate.parse(indentDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
				
		List<Object[]> reqStoreDetList = storeMasterRepository.getStoreDetailListByStore(
				Arrays.asList(dispatchNoteDTO.getIssueStoreId(), dispatchNoteDTO.getRequestStoreId()), orgId);
		Map<Long, Object[]> storeDataMap = new HashMap<>();
		for(Object[] reqStoreDet : reqStoreDetList) {
			storeDataMap.put(Long.valueOf(reqStoreDet[0].toString()), reqStoreDet);
		}
		
		Object[] reqStoreDet = storeDataMap.get(dispatchNoteDTO.getRequestStoreId());
		dispatchNoteDTO.setRequestStore(reqStoreDet[1].toString());
		dispatchNoteDTO.setRequestStoreLocName(reqStoreDet[3].toString());
		dispatchNoteDTO.setRequestedByName(reqStoreDet[5].toString() + MainetConstants.WHITE_SPACE + reqStoreDet[6].toString());

		Object[] issueStoreDet = storeDataMap.get(dispatchNoteDTO.getIssueStoreId());
		dispatchNoteDTO.setIssueStore(issueStoreDet[1].toString());
		dispatchNoteDTO.setIssueStoreLocName(issueStoreDet[3].toString());
		dispatchNoteDTO.setIssueInchargeName(issueStoreDet[5].toString() + MainetConstants.WHITE_SPACE + issueStoreDet[6].toString());
		
		List<MaterialDispatchNoteItemsDTO>  dispatchNoteItemEntityList = new ArrayList<>() ;
		dispatchNoteEntity.getMatDispatchItemEntities().forEach(dispatchNoteItemEntity->{
			MaterialDispatchNoteItemsDTO dispatchNoteItemsDTO = new MaterialDispatchNoteItemsDTO();
			BeanUtils.copyProperties(dispatchNoteItemEntity, dispatchNoteItemsDTO);
			dispatchNoteItemsDTO.setRemainingQty(dispatchNoteItemsDTO.getRequestedQty()
					.subtract(dispatchNoteItemsDTO.getIssuedQty().add(dispatchNoteItemsDTO.getPrevRecQty())));
			
			Object[] itemObject = (Object[]) itemMasterRepository.getItemNameAndExpiryFlagByItemId(orgId, dispatchNoteItemsDTO.getItemId())[0];
			dispatchNoteItemsDTO.setItemName(itemObject[0].toString());
			if (null != itemObject[1])
				dispatchNoteItemsDTO.setIsExpiry(itemObject[1].toString());		
			dispatchNoteItemsDTO.setUomDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					dispatchNoteItemsDTO.getUom(), orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());	
		
			List<MaterialDispatchNoteItemsEntryDTO>  dispatchNoteItemEntryList = new ArrayList<>();
			dispatchNoteItemEntity.getMatDispatchItemsEntryEntities().forEach(dispatchNoteEntryEntity->{
				MaterialDispatchNoteItemsEntryDTO dispatchNoteEntryDTO = new MaterialDispatchNoteItemsEntryDTO();
				BeanUtils.copyProperties(dispatchNoteEntryEntity, dispatchNoteEntryDTO);
				dispatchNoteItemEntryList.add(dispatchNoteEntryDTO);
			});
			dispatchNoteItemsDTO.setMatDispatchItemsEntryList(dispatchNoteItemEntryList);
			dispatchNoteItemEntityList.add(dispatchNoteItemsDTO);
		});
		dispatchNoteDTO.setMatDispatchItemList(dispatchNoteItemEntityList);
		return dispatchNoteDTO;
	}


	@Override
	public List<MaterialDispatchNoteDTO> getmdnSummaryDataObjectList(Long orgId, Long mdnId, Long requestStoreId, 
									Long issueStoreId, String status,  Long siId) {
		List<MaterialDispatchNoteDTO> dispatchNoteDTOList = new ArrayList<>();
		List<Object[]> dispatchNoteObjectListNative = dispatchNoteRepository.mdnSummaryDataObjectList(orgId, mdnId,
				requestStoreId, issueStoreId, status, siId);		
		MaterialDispatchNoteDTO dispatchNoteDTO;
		String[] mdnDate;
		for(Object[] dispatchNoteObject : dispatchNoteObjectListNative) {
			dispatchNoteDTO = new MaterialDispatchNoteDTO();
			dispatchNoteDTO.setMdnId(Long.valueOf(dispatchNoteObject[0].toString()));
			dispatchNoteDTO.setMdnNumber(dispatchNoteObject[1].toString());	
			mdnDate = dispatchNoteObject[2].toString().split(MainetConstants.WHITE_SPACE);			
			dispatchNoteDTO.setMdnDate(Utility.stringToDate(
					LocalDate.parse(mdnDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			dispatchNoteDTO.setSiId(Long.valueOf(dispatchNoteObject[3].toString()));
			dispatchNoteDTO.setSiNumber(dispatchNoteObject[4].toString());
			dispatchNoteDTO.setRequestStoreId(Long.valueOf(dispatchNoteObject[5].toString()));
			dispatchNoteDTO.setRequestStore(dispatchNoteObject[6].toString());
			dispatchNoteDTO.setIssueStoreId(Long.valueOf(dispatchNoteObject[7].toString()));
			dispatchNoteDTO.setIssueStore(dispatchNoteObject[8].toString());
			dispatchNoteDTO.setStatus(dispatchNoteObject[9].toString());
			dispatchNoteDTOList.add(dispatchNoteDTO);
		}				
		return dispatchNoteDTOList;
	}


}
