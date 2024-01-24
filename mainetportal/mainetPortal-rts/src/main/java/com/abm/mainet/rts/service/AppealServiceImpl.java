package com.abm.mainet.rts.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.rts.dto.FirstAppealDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AppealServiceImpl implements AppealService {

	@SuppressWarnings("unchecked")
	@Override
	public FirstAppealDto getFirstAppealData(Long applicationId, Long orgId) {

		RequestDTO dto = new RequestDTO();

		dto.setApplicationId(applicationId);
		dto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ServiceEndpoints.RTS.GET_APPEAL_DATA);
		final String d = new JSONObject(responseVo).toString();
		try {
			FirstAppealDto reqDto = new ObjectMapper().readValue(d, FirstAppealDto.class);
			reqDto.setName(String.join(" ",
					Arrays.asList(reqDto.getApplicantDetailDTO().getApplicantFirstName(),
							reqDto.getApplicantDetailDTO().getApplicantMiddleName(),
							reqDto.getApplicantDetailDTO().getApplicantLastName())));
			reqDto.setCorrespondingAddress(String.join(" ", Arrays.asList(reqDto.getApplicantDetailDTO().getBlockName(),
					reqDto.getApplicantDetailDTO().getRoadName(), reqDto.getApplicantDetailDTO().getVillageTownSub())));
			reqDto.getApplicantDetailDTO().setPinCode(reqDto.getApplicantDetailDTO().getPinCode().toString());
			return reqDto;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	@Override
	@Transactional
	public ObjectionDetailsDto saveFirstAppealInObjection(ObjectionDetailsDto objectionDetailsDto) {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				objectionDetailsDto, ApplicationSession.getInstance().getMessage("SAVE_FIRST_APPEAL"));
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, ObjectionDetailsDto.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public ObjectionDetailsDto saveSecondAppealInObjection(ObjectionDetailsDto objectionDetailsDto) {
		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				objectionDetailsDto, ApplicationSession.getInstance().getMessage("SAVE_SECOND_APPEAL"));
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, ObjectionDetailsDto.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
