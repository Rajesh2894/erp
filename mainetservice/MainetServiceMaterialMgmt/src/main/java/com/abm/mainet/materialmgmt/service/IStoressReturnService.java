package com.abm.mainet.materialmgmt.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.StoresReturnDTO;

public interface IStoressReturnService {

	void saveStoresReturnData(StoresReturnDTO storesReturnDTO);

	List<Object[]> getMDNNumbersForReturn(Long orgId);

	StoresReturnDTO fetchRejectedItemDataByMDNId(Long mdnId, Long orgId);

	StoresReturnDTO fetchStoreRetunDataByReturnId(Long storeReturnId, Long orgId);

	StoresReturnDTO fetchStoreRetunDataByReturnNo(String storeReturnNo, Long orgId);

	List<StoresReturnDTO> fetchStoreReturnSummaryData(Long storeReturnId, Long mdnId, Date fromDate, Date toDate,
			Long issueStoreId, Long requestStoreId, Long orgId);

	List<Object[]> getMDNNumbersReturned(Long orgId);

	void initializeWorkFlowForFreeService(StoresReturnDTO storesReturnDTO, ServiceMaster serviceMas);

	void updateWorkFlowService(WorkflowTaskAction workflowActionDto, ServiceMaster serviceMas);

}
