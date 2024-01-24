package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.BeansException;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

@WebService
public interface IRtsService {

	public Map<String, String> fetchRtsService(Long orgId);

	public Map<Long, String> fetchWardZone(Long orgId);

	public Map<String, String> serviceInfo(Long orgId);

	public List<DocumentDetailsVO> getRtsUploadedCheckListDocuments(Long applicationnId, Long orgId);

	public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode);
	
	
	public Map<String, String> serviceInfomation(Long orgId,String serviceShortCode);

	CommonChallanDTO createPushToPayApiRequest(CommonChallanDTO offlineDTO2, Long empId, Long orgId, String serviceName,
			String amount) throws BeansException, IOException, InterruptedException;
}
