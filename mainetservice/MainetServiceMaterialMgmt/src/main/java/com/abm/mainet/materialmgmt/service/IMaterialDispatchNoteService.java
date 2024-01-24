package com.abm.mainet.materialmgmt.service;

import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteDTO;

public interface IMaterialDispatchNoteService {

	void saveMaterialDispatchNote(MaterialDispatchNoteDTO dispatchNoteDTO);

	void initializeWorkFlowForFreeService(MaterialDispatchNoteDTO dispatchNoteDTO, ServiceMaster serviceMas);

	void updateWorkFlowService(WorkflowTaskAction workflowActionDto, ServiceMaster serviceMas);

	List<Object[]> getgetStoreIndentListForMDN(Long orgId);

	MaterialDispatchNoteDTO getStoreIndentDetailsById(MaterialDispatchNoteDTO dispatchNoteDTO, Long orgId);

	MaterialDispatchNoteDTO getMDNApprovalDataByMDNNumber(String mdnNumber, Long orgId);

	List<MaterialDispatchNoteDTO> getmdnSummaryDataObjectList(Long orgId, Long mdnId, Long requestStoreId,
			Long issueStoreId, String status, Long siId);

	MaterialDispatchNoteDTO getMDNDataByMDNId(Long mdnId, Long orgId);

}
