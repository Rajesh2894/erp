package com.abm.mainet.materialmgmt.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.dao.IStoreIndentDao;
import com.abm.mainet.materialmgmt.domain.StoreIndentEntity;
import com.abm.mainet.materialmgmt.domain.StoreIndentItemEntity;
import com.abm.mainet.materialmgmt.dto.StoreIndentDto;
import com.abm.mainet.materialmgmt.dto.StoreIndentItemDto;
import com.abm.mainet.materialmgmt.repository.IStoreIndentRepository;
import com.abm.mainet.materialmgmt.repository.StoreMasterRepository;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants.MmItemMaster;

@Service
public class StoreIndentServiceImpl implements IStoreIndentService {

	@Resource
	private IStoreIndentRepository storeIndentRepository;
	
	@Resource
	private CommonService commonService;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private StoreMasterRepository storeMasterRepository;
	
	@Resource
	private ItemMasterService itemMasterservice;

	@Resource
	private IStoreIndentDao storeIndentDao;
	
	@Resource
	private IStoreMasterService storeMasterservice;

	
	@Override
	@Transactional
	public void saveStoreIndentData(StoreIndentDto storeIndentDto) {
		StoreIndentEntity storeIndentEntity = new StoreIndentEntity();
		BeanUtils.copyProperties(storeIndentDto, storeIndentEntity);
		storeIndentDto.getStoreIndentItemDtoList().forEach(indentItemDto -> {
			StoreIndentItemEntity indentItemEntity = new StoreIndentItemEntity();
			BeanUtils.copyProperties(indentItemDto, indentItemEntity);
			indentItemEntity.setStoreIndentEntity(storeIndentEntity);
			storeIndentEntity.getStoreIndentItemEntityList().add(indentItemEntity);
		});
		storeIndentRepository.save(storeIndentEntity);
	}
	
	
	@Override
	@Transactional
	public void initializeWorkFlowForFreeService(StoreIndentDto storeIndentDto, ServiceMaster serviceMas) {	
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(storeIndentDto.getUserName());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo(MainetConstants.CommonConstants.NA);
		applicantDto.setUserId(storeIndentDto.getUserId());
		applicationMetaData.setReferenceId(storeIndentDto.getStoreIndentNo());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(storeIndentDto.getOrgId());
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
		workflowProcessParameter.setProcessName(
				serviceMasterService.getProcessName(serviceMas.getSmServiceId(), serviceMas.getOrgid()));
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
	}


	@Override
	@Transactional
	public void updateStoreIndentStatus(Long orgId, String storeIndentNo, String status, String wfFlag) {
		storeIndentRepository.updateStoreIndentStatus(orgId, storeIndentNo, status, wfFlag);
	
	}


	@Override
	public StoreIndentDto getStoreIndentDataByIndentId(Long storeIndentId, Long orgId) {
		StoreIndentEntity storeIndentEntity = storeIndentRepository.findByStoreIndentIdAndOrgId(storeIndentId, orgId);
		return mapStoreIndentEntityToStoreIndentDto(storeIndentEntity, orgId);
	}


	@Override
	public StoreIndentDto getStoreIndentDataByIndentNo(String appNo, Long orgId) {
		StoreIndentEntity storeIndentEntity = storeIndentRepository.findByStoreIndentNoAndOrgId(appNo, orgId);
		return mapStoreIndentEntityToStoreIndentDto(storeIndentEntity, orgId);
	}
	
	
	private StoreIndentDto mapStoreIndentEntityToStoreIndentDto(StoreIndentEntity storeIndentEntity, Long orgId) {
		StoreIndentDto storeIndentDto = new StoreIndentDto();
		BeanUtils.copyProperties(storeIndentEntity, storeIndentDto);

		List<Object[]> reqStoreDetList = storeMasterRepository.getStoreDetailListByStore(
				Arrays.asList(storeIndentDto.getRequestStore(), storeIndentDto.getIssueStore()), orgId);		
		Map<Long, Object[]> storeDataMap = new HashMap<>();
		for(Object[] reqStoreDet : reqStoreDetList) {
			storeDataMap.put(Long.valueOf(reqStoreDet[0].toString()), reqStoreDet);
		}		
		
		Object[] reqStoreDet = storeDataMap.get(storeIndentDto.getRequestStore());
		storeIndentDto.setRequestStoreName(reqStoreDet[1].toString());
		storeIndentDto.setRequestStoreLocId(Long.valueOf(reqStoreDet[2].toString()));
		storeIndentDto.setRequestStoreLocName(reqStoreDet[3].toString());
		storeIndentDto.setRequestedByName(reqStoreDet[5].toString() + MainetConstants.WHITE_SPACE + reqStoreDet[6].toString());

		Object[] issueStoreDet = storeDataMap.get(storeIndentDto.getIssueStore());
		storeIndentDto.setIssueStoreName(issueStoreDet[1].toString());
		storeIndentDto.setIssueStoreLocId(Long.valueOf(issueStoreDet[2].toString()));
		storeIndentDto.setIssueStoreLocName(issueStoreDet[3].toString());
		storeIndentDto.setIssueInchargeName(issueStoreDet[5].toString() + MainetConstants.WHITE_SPACE + issueStoreDet[6].toString());
		
		storeIndentEntity.getStoreIndentItemEntityList().forEach(indentItemEntity -> {
			StoreIndentItemDto storeIndentItemDto = new StoreIndentItemDto();
			BeanUtils.copyProperties(indentItemEntity, storeIndentItemDto);
			
			Object[] itemObj = (Object[]) itemMasterservice.getItemDetailObjectByItemId(storeIndentItemDto.getItemId())[0];
			storeIndentItemDto.setItemName(itemObj[1].toString());
			storeIndentItemDto.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					Long.valueOf(itemObj[2].toString()), orgId, MmItemMaster.UOM_PREFIX).getLookUpDesc());
			
			storeIndentDto.getStoreIndentItemDtoList().add(storeIndentItemDto);
		});
		
		return storeIndentDto;
		
	}


	@Override
	public List<StoreIndentDto> getStoreIndentSummaryList(Long requestStore, Long storeIndentId, Long issueStore,
			String status, Long orgId) {
		List<Object[]> StoreIndentobjects = storeIndentDao.getStoreIndentSummaryList(requestStore, storeIndentId, issueStore, status,
				orgId);
		List<StoreIndentDto> indentProcessDTOList = new ArrayList<>();
		StoreIndentDto indentProcessDTO;
		String[] indentDate;
		for(Object[] indentData : StoreIndentobjects) {
			indentProcessDTO = new StoreIndentDto();
			indentProcessDTO.setStoreIndentId(Long.valueOf(indentData[0].toString()));
			indentProcessDTO.setStoreIndentNo(indentData[1].toString());			
			indentDate = indentData[2].toString().split(MainetConstants.WHITE_SPACE);
			indentProcessDTO.setStoreIndentdate(Utility.stringToDate(
					LocalDate.parse(indentDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			indentProcessDTO.setRequestStore(Long.valueOf(indentData[3].toString()));
			indentProcessDTO.setIssueStore(Long.valueOf(indentData[4].toString()));
			indentProcessDTO.setStatus(indentData[5].toString());
			indentProcessDTOList.add(indentProcessDTO);
		}
		return indentProcessDTOList;
	}
	
	@Override
	public List<Object[]> getStoreIndentIdNumberList(Long orgId) {   /** P == Pending For Issuance, Prefix: IDS */
		return storeIndentRepository.getStoreIndentIdNumberList(orgId, MainetConstants.FlagP);
	}

}
