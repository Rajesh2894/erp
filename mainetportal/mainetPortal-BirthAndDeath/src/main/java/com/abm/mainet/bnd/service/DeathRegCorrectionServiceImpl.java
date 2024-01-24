package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeathRegCorrectionServiceImpl implements IdeathregCorrectionService {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbDeathregDTO> getDeathRegDataByStatus(TbDeathregDTO tbDeathregDTO) {
		List<LinkedHashMap<Long, Object>> requestList = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(tbDeathregDTO,ServiceEndpoints.BIRTH_DEATH.GET_DEATH_REGISTRATION_DETAILS);

		List<TbDeathregDTO> tbDeathregList = new ArrayList<>();
		requestList.forEach(obj -> {
			String d = new JSONObject(obj).toString();
			try {
				TbDeathregDTO DeathregDTO = new ObjectMapper().readValue(d, TbDeathregDTO.class);

				tbDeathregList.add(DeathregDTO);
			} catch (Exception e) {
				throw new FrameworkException(e);
			}
		});
		return tbDeathregList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TbDeathregDTO getDeathById(Long drID) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				drID, ServiceEndpoints.BIRTH_DEATH.GET_DEATH_REG_DETAIL_BY_ID + drID);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, TbDeathregDTO.class);
			} catch (IOException e) {

				throw new FrameworkException(e);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TbDeathregDTO savedeathCorrectionDeatils(TbDeathregDTO tbDeathregDTO) {
		tbDeathregDTO.setApplnId(0L);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				tbDeathregDTO, ServiceEndpoints.BIRTH_DEATH.SAVE_DEATH_CORRECTION_DETAIL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, TbDeathregDTO.class);
			} 
			catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@Override
	public long CalculateNoOfDays(TbDeathregDTO tbDeathregDTO) {
		long noOfDays = Utility.getDaysBetweenDates(tbDeathregDTO.getDrDod(), tbDeathregDTO.getDrRegdate());
		if (noOfDays <= 21) {
			return 21;
		} else if (noOfDays <= 30 & noOfDays > 21) {
			return 30;
		} else if (noOfDays <= 365 & noOfDays > 30) {
			return 365;

		} else {
			return 366;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public WSResponseDTO getApplicableTaxes(WSRequestDTO taxRequestDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				taxRequestDto, ServiceEndpoints.BIRTH_DEATH.GET_BIRTH_DEATH_APPLICABLE_TAXES);
		
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
	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				wSRequestDTO, ServiceEndpoints.BIRTH_DEATH.GET_BIRTH_DEATH_CHARGES);
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
	public TbDeathregDTO saveIssuanceDeathCertificateDetail(TbDeathregDTO tbDeathregDTO) {
		tbDeathregDTO.setApplnId(0L);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				tbDeathregDTO, ServiceEndpoints.BIRTH_DEATH.SAVE_ISSUENCE_DEATH_DETAIL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, TbDeathregDTO.class);
			} 
			catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TbDeathregDTO getDeathByApplId(Long applicationId,Long orgId) {
		TbDeathregDTO deathDto = new TbDeathregDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.SEARCH_DEATH_DATA_BY_APPLID + applicationId +"/"+ orgId);
		final String dto = new JSONObject(responseVo).toString();
		try {
			deathDto = new ObjectMapper().readValue(dto, TbDeathregDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Exception Occured when fetching details", e);
		}

		return deathDto;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TbDeathregDTO getDeathIssuanceApplId(Long applicationId,Long orgId) {
		TbDeathregDTO deathDto = new TbDeathregDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.SEARCH_DEATH_ISSUENCE_DATA_BY_APPLID + applicationId +"/"+ orgId);
		final String dto = new JSONObject(responseVo).toString();
		try {
			deathDto = new ObjectMapper().readValue(dto, TbDeathregDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Exception Occured when fetching details", e);
		}

		return deathDto;

	}
}
