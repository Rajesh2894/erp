package com.abm.mainet.rts.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.IntegratedServiceDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.RtsExternalServicesDTO;

@WebService
public interface IRtsService {

	public Map<String, String> fetchRtsService(Long orgId);

	public Map<Long, String> fetchWardZone(Long orgId);

	public Map<String, String> serviceInfo(Long orgId);

	public DrainageConnectionDto portalSave(DrainageConnectionDto dto);

	public List<DocumentDetailsVO> getRtsUploadedCheckListDocuments(Long applicationnId, Long orgId);

	public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode);
	
	public RtsExternalServicesDTO saveCertificateData(RtsExternalServicesDTO rtsExternalServicesDTO);
	
	public Map<String, String> serviceInfomation(Long orgId,String serviceShortCode);
	
	public RtsExternalServicesDTO updateStatus(RtsExternalServicesDTO rtsExternalServicesDTO);
	
	public List<IntegratedServiceDTO> getIntegratedServiceApplications(CitizenDashBoardReqDTO request);
	
	public RtsExternalServicesDTO getExternalServiceInfo(RtsExternalServicesDTO externalDto);
}
