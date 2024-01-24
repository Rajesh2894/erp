package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.property.rest.dto.PropertyDashboardAssessedPropertiesDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardCessDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardInterestDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardPenaltyDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardPropertiesRegisteredDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardPropertyTaxDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardRebateDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardRequestDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardResponseDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardTodaysCollectionDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardTodaysMovedApplDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardTransactionsDTO;

@WebService
public interface PropertyDashboardService {
	List<PropertyDashboardResponseDTO> fetchPropertyData(PropertyDashboardRequestDTO requestDTO);
	List<PropertyDashboardTodaysMovedApplDTO> getPropertyDashboardTodaysMovedApplDTOs(String orgId, Long deptId, String dateSet);
	List<PropertyDashboardPropertiesRegisteredDTO> getPropertyDashboardPropertiesRegisteredDTOs(String orgId, Long deptId, String dateSet);
	List<PropertyDashboardAssessedPropertiesDTO> getPropertyDashboardAssessedPropertiesDTO(String orgId, Long deptId, String dateSet);
	List<PropertyDashboardTransactionsDTO> getPropertyDashboardTransactionsDTO(String orgId, Long deptId, String dateSet);
	List<PropertyDashboardTodaysCollectionDTO> getPropertyDashboardTodyCollecDtoList(String orgId, Long deptId, String dateSet);
	List<PropertyDashboardPropertyTaxDTO> getPropertyDashboardPropertyTaxDTO(String orgId, Long deptId, String dateSet);
	List<PropertyDashboardPenaltyDTO> getPropertyDashboardPenaltyDTO(String orgId, Long deptId, String taxDescP, String dateSet);
	List<PropertyDashboardInterestDTO> getPropertyDashboardInterestDTO(String orgId, Long deptId, String taxDescI, String dateSet);
	List<PropertyDashboardRebateDTO> getPropertyDashboardRebateDTO(String orgId, Long deptId, String taxDescR, String dateSet);
	List<PropertyDashboardCessDTO> getPropertyDashboardCessDTO(String orgId, Long deptId, String taxDescC, String dateSet);
	
}
