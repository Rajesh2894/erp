package com.abm.mainet.water.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;

/**
 * @author Arun.Chavda
 *
 */

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface WaterReconnectionService {

	List<WaterReconnectionResponseDTO> getDisconnectionDetailsForReConnection(WaterReconnectionRequestDTO requestDTO);

	WaterReconnectionResponseDTO checkIsRegisteredPlumberLicNo(WaterReconnectionRequestDTO requestDTO);

	WaterReconnectionResponseDTO saveWaterReconnectionDetails(WaterReconnectionRequestDTO requestDTO);

	TbWaterReconnection getReconnectionDetails(Long applicationId, Long orgId);

	TbKCsmrInfoMH getWaterConnectionDetailsById(Long consumerIdNo, Long orgId);

	void updatedWaterReconnectionDetailsByDept(TbWaterReconnection reconnection);

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId);

	void updateUserTaskAndReconnectionExecutionDetails(TbWaterReconnection reconnection,
			ScrutinyLableValueDTO scrutinyLableValueDTO);

	boolean isAlreadyAppliedForReConn(Long csIdn);

	void initiateWorkflowForFreeService(WaterReconnectionRequestDTO requestDTO);
	
	WaterReconnectionRequestDTO getWaterReconnApplicationDashBoardDetails(Long applicationId, Long orgId);
	
	Long getPlumIdByApplicationId(Long applicationId, Long orgId);
}
