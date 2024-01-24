package com.abm.mainet.bnd.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author rahul.chaubey
 * 
 */
@WebService
public interface birthDeathCertService {

	/*public List<Long> getApplicationNo(Long orgId, Long serviceId);*/

	Long saveApplicationData(RequestDTO requestDTO);

	public List<RequestDTO> loadSummaryData(Long orgId, String deptCode);

	/*public RequestDTO getApplicationFormData(Long apmApplicationNo, Long orgId);*/

	public Map<Long, String> getDept(Long orgId);

	public Map<Long, String> getService(Long orgId, Long deptId, String activeStatus);

	/*public List<RequestDTO> searchData(Long applicationId, Long serviceId, Long orgId);*/

	public Boolean getEmployeeRole(UserSession ses);
	
	public List<Long> getApplicationNo(Long orgId, Long serviceId);
	
	public RequestDTO getApplicationFormData(Long apmApplicationNo, Long orgId);

	


}
