package com.abm.mainet.water.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.water.rest.dto.WaterDashboardConnectionsCreatedDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardPendingConnectionsDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardRequestDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardResponseDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardTodaysCollectionDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardWaterConnectionsDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardWaterSewerageConnectionsDTO;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

@WebService
public interface WaterDashboardService {

	List<WaterDashboardResponseDTO> fetchWaterData(WaterDashboardRequestDTO requestDTO);

	List<WaterDashboardConnectionsCreatedDTO> getWaterDashboardConnCreated(Long orgId, String dateN);
	List<WaterDashboardWaterConnectionsDTO> getWaterDashboardWaterConnList(Long orgId, String dateN);
	List<WaterDashboardPendingConnectionsDTO> getDashboardPendingConnectionsDTOs(Long orgId, String dateN);
	List<WaterDashboardTodaysCollectionDTO> getWaterDashboardtodyCollecDtoList(Long orgId, String dateN);
}
