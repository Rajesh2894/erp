package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdvocateMasterServiceImpl implements AdvocateMasterService {
	
	private static Logger log = Logger.getLogger(AdvocateMasterServiceImpl.class);
	

	@SuppressWarnings("unchecked")
	@Override
	public AdvocateMasterDTO saveAdvocateMaster(AdvocateMasterDTO advocateMasterDTO) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(advocateMasterDTO, ServiceEndpoints.LEGAL_URL.SAVE_ADVOCATE_DATA);
		final String response = new JSONObject(responseVo).toString();
		try {
			advocateMasterDTO = new ObjectMapper().readValue(response, AdvocateMasterDTO.class);
		} catch (final Exception e) {
			log.info("Error Occured in saveAdvocateMaster()" + e);
			throw new FrameworkException("Error while saving advocate m", e);
		}
		return advocateMasterDTO;

	}



	@Override
	public List<AdvocateMasterDTO> validateAdvocateMaster(AdvocateMasterDTO advocateMasterDTO) {
		@SuppressWarnings("unchecked")
		List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(advocateMasterDTO,ServiceEndpoints.LEGAL_URL.VALIDATE_ADVOCATE_MASTER);
		List<AdvocateMasterDTO> dtos = new ArrayList<>();
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				AdvocateMasterDTO dto = new ObjectMapper().readValue(d, AdvocateMasterDTO.class);
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.info("Error Occured in validateAdvocateMaster()" + e);
		}
		return dtos;

	}


	@Override
	public AdvocateMasterDTO getAdvocateDetailsById(Long applicationId, Long orgid) {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.LEGAL_URL.GET_ADVOCATE_DETAILS_BY_ID + MainetConstants.WINDOWS_SLASH + orgid + MainetConstants.WINDOWS_SLASH + applicationId);
		final String response = new JSONObject(responseVo).toString();
		AdvocateMasterDTO advocateMasterDTO = new AdvocateMasterDTO();
		try {
			advocateMasterDTO = new ObjectMapper().readValue(response, AdvocateMasterDTO.class);
		} catch (final Exception e) {
			log.info("Error Occured in getAdvocateDetailsById()" + e);
			throw new FrameworkException("Error while saving marriage tabs", e);
		}
		return advocateMasterDTO;
	
	}

}
