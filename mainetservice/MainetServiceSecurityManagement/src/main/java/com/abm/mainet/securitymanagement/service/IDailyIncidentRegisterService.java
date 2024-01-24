package com.abm.mainet.securitymanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.securitymanagement.dto.DailyIncidentRegisterDTO;

public interface IDailyIncidentRegisterService {

	public DailyIncidentRegisterDTO save(DailyIncidentRegisterDTO dailyIncidentRegisterDTO);
	
	//DailyIncidentRegisterDTO save1(DailyIncidentRegisterDTO dailyIncidentRegisterDTO);
	
	public List<DailyIncidentRegisterDTO> getAllRecords(Long orgId);
	
	List<DailyIncidentRegisterDTO> searchIncidentRegister(Date fromDate, Date toDate, Long orgid);

	public DailyIncidentRegisterDTO getIncidentById(Long incidentId);

}
