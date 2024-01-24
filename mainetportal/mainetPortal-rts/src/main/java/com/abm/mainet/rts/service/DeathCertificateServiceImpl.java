package com.abm.mainet.rts.service;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.DeathCertificateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeathCertificateServiceImpl implements IDeathCertificateService{

	@SuppressWarnings("unchecked")
	@Override
	public DeathCertificateDTO savedeathCertificateDeatils(DeathCertificateDTO deathCertificateDTO) {
		deathCertificateDTO.setApplnId(0L);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				deathCertificateDTO, ServiceEndpoints.RTS.SAVE_DEATH_CERTIFICATE);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, DeathCertificateDTO.class);
			} 
			catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		
		return null;
    }

	@SuppressWarnings("unchecked")
	@Override
	public WSResponseDTO getApplicableTaxes(WSRequestDTO taxRequestDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				taxRequestDto, ServiceEndpoints.RTS.GET_APPLICABLE_TAXES);
		
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, WSResponseDTO.class);
			} 
			catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WSResponseDTO getDeathCertificateCharges(WSRequestDTO wSRequestDTO) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				wSRequestDTO, ServiceEndpoints.RTS.GET_DEATH_CERT_CHARGES);
		
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, WSResponseDTO.class);
			} 
			catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@Override
	public DeathCertificateDTO getDeathCertificateDetail(Long appId, Long orgId) {
		DeathCertificateDTO deathDto = new DeathCertificateDTO();
		deathDto.setApplicationNo(appId);
		deathDto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(deathDto, ServiceEndpoints.RTS.FETCH_DEATH_CONN_INFO);
		final String d = new JSONObject(responseVo).toString(); 
		try {
			return new ObjectMapper().readValue(d, DeathCertificateDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}

}
