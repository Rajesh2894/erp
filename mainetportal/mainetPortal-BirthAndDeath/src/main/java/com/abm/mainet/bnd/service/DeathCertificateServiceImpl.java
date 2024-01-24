package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.dto.ViewBirthCertiDetailRequestDto;
import com.abm.mainet.bnd.dto.ViewDeathCertiDetailRequestDto;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("ApplyDeathCertificateServiceImpl")
public class DeathCertificateServiceImpl implements IDeathCertificateService{

	@SuppressWarnings("unchecked")
	@Override
	public DeathCertificateDTO savedeathCertificateDeatils(DeathCertificateDTO deathCertificateDTO) {
		deathCertificateDTO.setApplnId(0L);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				deathCertificateDTO, ServiceEndpoints.BIRTH_DEATH.BND_SAVE_DEATH_CERTIFICATE);
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
				taxRequestDto, ServiceEndpoints.BIRTH_DEATH.BND_GET_APPLICABLE_TAXES);
		
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
				wSRequestDTO, ServiceEndpoints.BIRTH_DEATH.BND_GET_DEATH_CERT_CHARGES);
		
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
				.callRestTemplateClient(deathDto, ServiceEndpoints.BIRTH_DEATH.BND_FETCH_DEATH_CONN_INFO);
		final String d = new JSONObject(responseVo).toString(); 
		try {
			return new ObjectMapper().readValue(d, DeathCertificateDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}
	
	
	@Override
	public List<DeathCertificateDTO> searchDeathCertiDetails(DeathCertificateDTO searchDto) {
		List<DeathCertificateDTO> dto = new ArrayList<>();
		ViewDeathCertiDetailRequestDto viewRequestDto = new ViewDeathCertiDetailRequestDto();
		
		/*
		 * if(searchDto.getApmApplicationId()== null) {
		 * searchDto.setApmApplicationId(null); }
		 * 
		 * if(searchDto.getBrDob()== null) { searchDto.setBrDob(null); }
		 */
		
		viewRequestDto.setDeathSearchDto(searchDto);
		final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(viewRequestDto,
						ApplicationSession.getInstance().getMessage("DEATH_CERTIFICATE_DETAILS"));
		try {
			
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				DeathCertificateDTO deathSearchDto = new ObjectMapper().readValue(d, DeathCertificateDTO.class);
				dto.add(deathSearchDto);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		}

		return dto;
	}
	

}
