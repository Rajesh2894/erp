package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;
import com.abm.mainet.materialmgmt.domain.StoresReturnDetailEntity;
import com.abm.mainet.materialmgmt.domain.StoresReturnEntity;
import com.abm.mainet.materialmgmt.dto.StoresReturnDTO;
import com.abm.mainet.materialmgmt.dto.StoresReturnDetailDTO;
import com.abm.mainet.materialmgmt.repository.ICommonTransactionRepository;
import com.abm.mainet.materialmgmt.repository.IStoresReturnRepository;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Service
@Transactional
public class StoresReturnServiceImpl implements IStoressReturnService {

	@Resource
	private IStoresReturnRepository storesReturnRepository;
	
	@Resource
	private ItemMasterService itemMasterService;
	
	@Resource
	private CommonService commonService;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private ICommonTransactionRepository transactionRepository;

  
	@Override
	public void saveStoresReturnData(StoresReturnDTO storesReturnDTO) {
		StoresReturnEntity storesReturnEntity = new StoresReturnEntity();
		BeanUtils.copyProperties(storesReturnDTO, storesReturnEntity);
		storesReturnDTO.getStoresReturnDetailList().forEach(returnDetailDTO -> {
			StoresReturnDetailEntity returnDetailEntity = new StoresReturnDetailEntity();
			BeanUtils.copyProperties(returnDetailDTO, returnDetailEntity);
			returnDetailEntity.setStoresReturnEntity(storesReturnEntity);
			returnDetailEntity.setRequestStoreId(storesReturnEntity.getRequestStoreId());
			returnDetailEntity.setIssueStoreId(storesReturnEntity.getIssueStoreId());
			storesReturnEntity.getReturnDetailEntityList().add(returnDetailEntity);
		});
		storesReturnRepository.save(storesReturnEntity);
		
		/** save Store Return Data To Transaction Table */
		if(MainetConstants.FlagY.equalsIgnoreCase(storesReturnDTO.getStatus()))
			transactionRepository.save(saveStoreReturnDataToTransactionEntity(storesReturnDTO));
	}
	
	/** Transaction Type : Purchase-P, Indent-I, Store Indent Outward-M, Store Indent Inward-S,
	  	Disposal-D, Adjustment-A, Transfer-T, Expired-E, Opening Balance-O, Returned-R  */
	private List<CommonTransactionEntity> saveStoreReturnDataToTransactionEntity(StoresReturnDTO storesReturnDTO) {
		List<CommonTransactionEntity> transactionEntityList = new ArrayList<>();
		storesReturnDTO.getStoresReturnDetailList().forEach(returnDetail -> {
			CommonTransactionEntity transactionEntity = new CommonTransactionEntity();
			transactionEntity.setTransactionDate(returnDetail.getUpdatedDate());
			transactionEntity.setTransactionType(MainetConstants.FlagR);
			transactionEntity.setReferenceNo(storesReturnDTO.getStoreReturnNo());			
			transactionEntity.setStoreId(storesReturnDTO.getIssueStoreId());
			transactionEntity.setItemId(returnDetail.getItemId());
			transactionEntity.setItemNo(returnDetail.getItemNo());
			transactionEntity.setStatus(MainetConstants.FlagY);

			if(MainetConstants.FlagY.equalsIgnoreCase(returnDetail.getDisposalFlag())) {
				transactionEntity.setDebitQuantity(returnDetail.getQuantity());
				transactionEntity.setDisposalStatus(MainetConstants.FlagP); /** For Marked for Disposal Item */
			} else {
				transactionEntity.setBinLocation(returnDetail.getBinLocation());
				transactionEntity.setCreditQuantity(returnDetail.getQuantity());
				transactionEntity.setDisposalStatus(MainetConstants.FlagN);
			}			
			transactionEntity.setOrgId(returnDetail.getOrgId());
			transactionEntity.setUserId(returnDetail.getUpdatedBy());
			transactionEntity.setLangId(returnDetail.getLangId());
			transactionEntity.setCreatedDate(returnDetail.getUpdatedDate());
			transactionEntity.setLgIpMacUpd(returnDetail.getLgIpMacUpd());
			transactionEntityList.add(transactionEntity);
		});
		return transactionEntityList;
	}
	
	
	@Override
	public void initializeWorkFlowForFreeService(StoresReturnDTO storesReturnDTO, ServiceMaster serviceMas) {
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(storesReturnDTO.getUserName());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(storesReturnDTO.getUserId());
		applicationMetaData.setReferenceId(storesReturnDTO.getStoreReturnNo());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(storesReturnDTO.getOrgId());
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
			throw new FrameworkException("Exception Occured when Update Workflow methods", exception);
		}
	}
	

	@Override
	public List<Object[]> getMDNNumbersForReturn(Long orgId) {
		return storesReturnRepository.fetchMDNIDAndNumberListForStoreReturn(BigDecimal.ZERO, MainetConstants.FlagY, orgId);
	}

	
	@Override
	public List<Object[]> getMDNNumbersReturned(Long orgId) {
		return storesReturnRepository.getMDNNumbersReturned(orgId);
	}
	
	
	@Override
	public StoresReturnDTO fetchRejectedItemDataByMDNId(Long mdnId, Long orgId) {
		StoresReturnDTO storesReturnDTO = new StoresReturnDTO();		
		Object[] mdnDataObject = (Object[]) storesReturnRepository.getMDNDetailsByMDNId(mdnId, orgId)[0];
		storesReturnDTO.setMdnId(Long.valueOf(mdnDataObject[0].toString()));
		String[] indentDate = mdnDataObject[2].toString().split(MainetConstants.WHITE_SPACE);
		storesReturnDTO.setMdnDate(Utility.stringToDate(
				LocalDate.parse(indentDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));		
		storesReturnDTO.setStoreIndentId(Long.valueOf(mdnDataObject[3].toString()));
		storesReturnDTO.setStoreIndentNo(mdnDataObject[4].toString());
		storesReturnDTO.setRequestStoreId(Long.valueOf(mdnDataObject[5].toString()));
		storesReturnDTO.setRequestStoreName(mdnDataObject[6].toString());
		storesReturnDTO.setIssueStoreId(Long.valueOf(mdnDataObject[7].toString()));
		storesReturnDTO.setIssueStoreName(mdnDataObject[8].toString());
		
		List<Object[]> mdnReturnObjectList = storesReturnRepository.getMDNRejectedItemsByMDNId(mdnId, BigDecimal.ZERO, orgId);
		StoresReturnDetailDTO returnDetailDTO;
		for(Object[] mdnReturnObject : mdnReturnObjectList) {
			returnDetailDTO = new StoresReturnDetailDTO();
			returnDetailDTO.setMdnId(Long.valueOf(mdnReturnObject[0].toString()));
			returnDetailDTO.setMdnItemEntryId(Long.valueOf(mdnReturnObject[1].toString()));
			returnDetailDTO.setItemId(Long.valueOf(mdnReturnObject[2].toString()));
			returnDetailDTO.setItemName(mdnReturnObject[3].toString());
			returnDetailDTO.setUom(Long.valueOf(mdnReturnObject[4].toString()));
			returnDetailDTO.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					returnDetailDTO.getUom(), orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());
			if (null != mdnReturnObject[5])
				returnDetailDTO.setItemNo(mdnReturnObject[5].toString());
			returnDetailDTO.setQuantity(new BigDecimal(mdnReturnObject[6].toString()));
			returnDetailDTO.setReturnReason(Long.valueOf(mdnReturnObject[7].toString()));
			returnDetailDTO.setReturnReasonDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(returnDetailDTO.getReturnReason()),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());			
			storesReturnDTO.getStoresReturnDetailList().add(returnDetailDTO);
		}		
		return storesReturnDTO;
	}
	

	@Override
	public StoresReturnDTO fetchStoreRetunDataByReturnId(Long storeReturnId, Long orgId) {
		return mapStoresReturnEntirtyToStoresReturnDTO(
				storesReturnRepository.findByStoreReturnIdAndOrgId(storeReturnId, orgId), orgId);
	}	

	@Override
	public StoresReturnDTO fetchStoreRetunDataByReturnNo(String storeReturnNo, Long orgId) {
		return mapStoresReturnEntirtyToStoresReturnDTO(
				storesReturnRepository.findByStoreReturnNoAndOrgId(storeReturnNo, orgId), orgId);
	}


	private StoresReturnDTO mapStoresReturnEntirtyToStoresReturnDTO(StoresReturnEntity storesReturnEntity, Long orgId) {
		StoresReturnDTO storesReturnDTO = new StoresReturnDTO();
		BeanUtils.copyProperties(storesReturnEntity, storesReturnDTO);

		Object[] mdnDataObject = (Object[]) storesReturnRepository.getMDNDetailsByMDNId(storesReturnDTO.getMdnId(),
				orgId)[0];
		storesReturnDTO.setMdnId(Long.valueOf(mdnDataObject[0].toString()));
		storesReturnDTO.setMdnNumber(mdnDataObject[1].toString());
		String[] indentDate = mdnDataObject[2].toString().split(MainetConstants.WHITE_SPACE);
		storesReturnDTO.setMdnDate(Utility.stringToDate(
				LocalDate.parse(indentDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
		storesReturnDTO.setStoreIndentId(Long.valueOf(mdnDataObject[3].toString()));
		storesReturnDTO.setStoreIndentNo(mdnDataObject[4].toString());
		storesReturnDTO.setRequestStoreId(Long.valueOf(mdnDataObject[5].toString()));
		storesReturnDTO.setRequestStoreName(mdnDataObject[6].toString());
		storesReturnDTO.setIssueStoreId(Long.valueOf(mdnDataObject[7].toString()));
		storesReturnDTO.setIssueStoreName(mdnDataObject[8].toString());

		storesReturnEntity.getReturnDetailEntityList().forEach(returnDetailEntity -> {
			StoresReturnDetailDTO returnDetailDTO = new StoresReturnDetailDTO();
			BeanUtils.copyProperties(returnDetailEntity, returnDetailDTO);
			Object[] itemObj = (Object[]) itemMasterService.getItemDetailObjectByItemId(returnDetailDTO.getItemId())[0];
			returnDetailDTO.setItemName(itemObj[1].toString());
			returnDetailDTO.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(itemObj[2].toString()), 
					orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());
			returnDetailDTO.setReturnReasonDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(returnDetailDTO.getReturnReason()),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());
			storesReturnDTO.getStoresReturnDetailList().add(returnDetailDTO);
		});
		return storesReturnDTO;
	}
	
	

	@Override
	public List<StoresReturnDTO> fetchStoreReturnSummaryData(Long storeReturnId, Long mdnId, Date fromDate, Date toDate,
			Long issueStoreId, Long requestStoreId, Long orgId) {
		List<StoresReturnDTO> storesReturnDTOList = new ArrayList<>();		
		List<Object[]> returnObjectList = storesReturnRepository.storeReturnSummaryList(orgId, storeReturnId, mdnId,
				fromDate, toDate, issueStoreId, requestStoreId);
		StoresReturnDTO storesReturnDTO;
		String[] returnDateArr;
		for(Object[] returnObject  : returnObjectList) {
			storesReturnDTO = new StoresReturnDTO();
			storesReturnDTO.setStoreReturnId(Long.valueOf(returnObject[0].toString()));
			storesReturnDTO.setStoreReturnNo(returnObject[1].toString());
			returnDateArr = returnObject[2].toString().split(MainetConstants.WHITE_SPACE);			
			storesReturnDTO.setStoreReturnDate(Utility.stringToDate(
					LocalDate.parse(returnDateArr[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			storesReturnDTO.setMdnId(Long.valueOf(returnObject[3].toString()));
			storesReturnDTO.setStoreIndentNo(returnObject[4].toString());
			storesReturnDTO.setIssueStoreId(Long.valueOf(returnObject[5].toString()));
			storesReturnDTO.setRequestStoreId(Long.valueOf(returnObject[6].toString()));
			storesReturnDTOList.add(storesReturnDTO);
		}
		return storesReturnDTOList;
	}

}
