/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppEntityToDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/*  @author priti.singh
 *
 */
@Service
public class RenewalFormServiceImpl implements RenewalFormService {

	private static final Logger logger = Logger.getLogger(RenewalFormServiceImpl.class);
	

	@SuppressWarnings("unchecked")
	@Override
	public RenewalFormDto saveRenewalDetails(RenewalFormDto dto) {
		boolean status = false;
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				dto, ServiceEndpoints.SOCIAL_SECURITY_URL.SOCIAL_SECURITY_RENEWAL_SAVE_URL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, RenewalFormDto.class);
			} 
			catch (IOException e) {
				 throw new FrameworkException(e);
			}

		}
		return null;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public SchemeAppEntityToDto fetchDataOnBenef(RenewalFormDto renewalFormDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				renewalFormDto, ServiceEndpoints.SOCIAL_SECURITY_URL.FETCH_DATA_ON_BENEF_SAVE_URL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, SchemeAppEntityToDto.class);
			} 
			 catch (IOException e) {
				 throw new FrameworkException(e);
			}

		}
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public RenewalFormDto findRenewalDetails(Long applicationId, Long orgId) {
		RenewalFormDto dto=new RenewalFormDto();
		dto.setApplicationId(applicationId);
		 dto.setOrgId(orgId);
		
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				dto, ServiceEndpoints.SOCIAL_SECURITY_URL.FIND_RENEWAL_SAVE_URL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, RenewalFormDto.class);
			} catch (IOException e) {				
	            throw new FrameworkException(e);
			}

		}
		return null;
	}
	
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public RenewalFormDto workflowFormView(RenewalFormDto dto) { final
	 * LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>)
	 * JersyCall.callRestTemplateClient( dto,
	 * ServiceEndpoints.SOCIAL_SECURITY_URL.WORKFLOW_FORM_VIEW_SAVE_URL); if
	 * (responseVo != null) { final String d = new
	 * JSONObject(responseVo).toString();
	 * 
	 * try { return new ObjectMapper().readValue(d, RenewalFormDto.class); } catch
	 * (JsonParseException e) {
	 * 
	 * e.printStackTrace(); } catch (JsonMappingException e) {
	 * 
	 * e.printStackTrace(); } catch (IOException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * } return null; }
	 */
	
}