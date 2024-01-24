package com.abm.mainet.rts.service;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dto.BirthCertificateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BirthCertificateService implements IBirthCertificateService {

	@SuppressWarnings("unchecked")
	@Override
	public BirthCertificateDTO saveBirthCertificateP(BirthCertificateDTO birthCertificateDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				birthCertificateDto,ServiceEndpoints.RTS.SAVE_BIRTH_DATA);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, BirthCertificateDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDTO, ServiceEndpoints.RTS.GET_APPLICABLE_TAXE);

		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, WSResponseDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(wSRequestDTO, ServiceEndpoints.RTS.GET_BND_CHARGE);

		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, WSResponseDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@Override
	public BirthCertificateDTO getBirthCertificateInfo(Long appId, Long orgId) {
		BirthCertificateDTO birthDto = new BirthCertificateDTO();
		birthDto.setApmApplicationId(appId);
		birthDto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(birthDto, ServiceEndpoints.RTS.FETCH_BIRTH_CONN_INFO);
		final String d = new JSONObject(responseVo).toString(); 
		try {
			return new ObjectMapper().readValue(d, BirthCertificateDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}

}
