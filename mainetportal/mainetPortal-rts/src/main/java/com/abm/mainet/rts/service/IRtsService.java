package com.abm.mainet.rts.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

public interface IRtsService {
	// List<Object[]> fetchRtsService(Long orgId);
	public Map<String, String> fetchRtsService(Long orgId);

	public Map<Long, String> fetchWardZone(Long orgId);

	public Map<String, String> serviceInfo(Long orgId);

	public RequestDTO fetchRtsApplicationInformationById(Long appId, Long orgId);
	
	public List<DocumentDetailsVO> fetchDocumentDetailsByAppNo(Long appId, Long orgId);
	
	public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode);
}
