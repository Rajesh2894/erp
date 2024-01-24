package com.abm.mainet.materialmgmt.service;

import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.StoreIndentDto;

public interface IStoreIndentService {

	void saveStoreIndentData(StoreIndentDto storeIndentDto);

	void initializeWorkFlowForFreeService(StoreIndentDto storeIndentDto, ServiceMaster serviceMas);

	void updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster serviceMas);

	void updateStoreIndentStatus(Long orgId, String storeIndentNo, String status, String wfFlag);

	StoreIndentDto getStoreIndentDataByIndentId(Long indentid, Long orgId);

	StoreIndentDto getStoreIndentDataByIndentNo(String appNo, Long orgId);

	List<StoreIndentDto> getStoreIndentSummaryList(Long requestStore, Long storeIndentId, Long issueStore,
			String status, Long orgId);

	List<Object[]> getStoreIndentIdNumberList(Long orgId);


	
	
	
	
}
