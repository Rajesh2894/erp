package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.ViewBirthCertiDetailRequestDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("ApplyBirthCertificateService")
public class BirthCertificateService implements IBirthCertificateService {

	@SuppressWarnings("unchecked")
	@Override
	public BirthCertificateDTO saveBirthCertificateP(BirthCertificateDTO birthCertificateDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				birthCertificateDto,ServiceEndpoints.BIRTH_DEATH.BND_SAVE_BIRTH_DATA);
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
				.callRestTemplateClient(requestDTO, ServiceEndpoints.BIRTH_DEATH.BND_GET_APPLICABLE_TAXE);

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
				.callRestTemplateClient(wSRequestDTO, ServiceEndpoints.BIRTH_DEATH.BND_GET_BND_CHARGE);

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
				.callRestTemplateClient(birthDto, ServiceEndpoints.BIRTH_DEATH.BND_FETCH_BIRTH_CONN_INFO);
		final String d = new JSONObject(responseVo).toString(); 
		try {
			return new ObjectMapper().readValue(d, BirthCertificateDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}
	
	@Override
	public List<BirthCertificateDTO> searchBirthCertiDetails(BirthCertificateDTO searchDto) {
		List<BirthCertificateDTO> dto = new ArrayList<>();
		ViewBirthCertiDetailRequestDto viewRequestDto = new ViewBirthCertiDetailRequestDto();
		
		/*
		 * if(searchDto.getApmApplicationId()== null) {
		 * searchDto.setApmApplicationId(null); }
		 * 
		 * if(searchDto.getBrDob()== null) { searchDto.setBrDob(null); }
		 */
		
		viewRequestDto.setBirthSearchDto(searchDto);
		final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(viewRequestDto,
						ApplicationSession.getInstance().getMessage("BIRTH_CERTIFICATE_DETAILS"));
		try {
			
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				BirthCertificateDTO birthSearchDto = new ObjectMapper().readValue(d, BirthCertificateDTO.class);
				dto.add(birthSearchDto);
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
