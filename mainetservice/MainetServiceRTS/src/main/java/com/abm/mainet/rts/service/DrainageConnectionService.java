package com.abm.mainet.rts.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.DrainageConnectionRoadDetDTO;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;
import com.abm.mainet.rts.ui.model.RtsServiceFormModel;

/**
 * @author rahul.chaubey
 * 
 */
@WebService
public interface DrainageConnectionService {
	public DrainageConnectionDto saveDrainageConnection(DrainageConnectionModel model);

	public void updateDrainageConnection(DrainageConnectionDto drainageConnectionDto);

	public List<Long> getApplicationNo(Long orgId, Long serviceId);

	Long saveApplicationData(RequestDTO requestDTO);

	public List<RequestDTO> loadSummaryData(Long orgId, String deptCode);

	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DrainageConnectionDto model);

	public RequestDTO getApplicationFormData(Long apmApplicationNo, Long orgId);

	public DrainageConnectionDto getDrainageConnectionData(Long apmApplicationNo, Long orgId);

	public boolean saveDecision(DrainageConnectionModel model, WorkflowTaskAction workFlowActionDto, Employee emp);

	public Map<Long, String> getDept(Long orgId);

	public Map<Long, String> getService(Long orgId, Long deptId, String activeStatus);

	public List<RequestDTO> searchData(Long applicationId, Long serviceId, Long orgId);

	public Boolean getEmployeeRole(UserSession ses);

	//public List<DocumentDetailsVO> getCheckListData(Long appId, FileNetApplicationClient fClient, UserSession session);

	public List<DocumentDetailsVO> getDrnPortalDocs(DrainageConnectionDto dto);

	public void updateRoadLength(Long apmApplicationId, Long orgId, Long roadType, Long lenRoad);

	public void saveRoadDetailList(List<DrainageConnectionRoadDetDTO> roadDetDTOList);

}
