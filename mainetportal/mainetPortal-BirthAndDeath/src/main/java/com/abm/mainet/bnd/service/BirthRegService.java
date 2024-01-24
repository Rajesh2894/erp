package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

public class BirthRegService implements IBirthRegService {

	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetail(String brCertNo, String brRegNo, String year,
			Date brDob, String brChildName, String applicationId, Long orgId) {

		BirthRegistrationDTO birthDto = new BirthRegistrationDTO();
		birthDto.setBrCertNo(brCertNo);
		birthDto.setBrRegNo(brRegNo);
		birthDto.setYear(year);
		birthDto.setBrDob(brDob);
		birthDto.setBrChildName(brChildName);
		birthDto.setApplicationId(applicationId);
		birthDto.setOrgId(orgId);

		List<BirthRegistrationDTO> listDTO = new ArrayList<BirthRegistrationDTO>();

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(birthDto, ServiceEndpoints.BIRTH_DEATH.SEARCH_BIRTH_DATA);

		requestList.forEach(birthDTO -> {
			String jsonObject = new JSONObject(birthDTO).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				BirthRegistrationDTO estateBookingDTO = objectMapper.readValue(jsonObject, BirthRegistrationDTO.class);
				listDTO.add(estateBookingDTO);
			} catch (Exception exception) {
				throw new FrameworkException("Exception Occured when fetching details ", exception);
			}
		});

		return listDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BirthRegistrationDTO getBirthByID(Long brId) {
		BirthRegistrationDTO birthDto = new BirthRegistrationDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.SEARCH_BIRTH_DATA_BY_ID + brId);
		final String dto = new JSONObject(responseVo).toString();
		try {
			birthDto = new ObjectMapper().readValue(dto, BirthRegistrationDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Exception Occured when fetching details", e);
		}

		return birthDto;

	}

	@SuppressWarnings("unchecked")
	@Override
	public BirthRegistrationDTO saveBirthCorrectionDet(BirthRegistrationDTO birthCertificateDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(birthCertificateDto, ServiceEndpoints.BIRTH_DEATH.SAVE_BND_BIRTH_CORR_DATA);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, BirthRegistrationDTO.class);
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
				.callRestTemplateClient(requestDTO, ServiceEndpoints.BIRTH_DEATH.GET_BIRTH_DEATH_APPLICABLE_TAXES);

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
				.callRestTemplateClient(wSRequestDTO, ServiceEndpoints.BIRTH_DEATH.GET_BIRTH_DEATH_CHARGES);

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
	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto) {
		long noOfDays = Utility.getDaysBetweenDates(birthRegDto.getBrDob(), birthRegDto.getBrRegDate());
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
	
	@Override
	public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("serviceShortCode", serviceShortCode);
		URI uri = dd.expand(ServiceEndpoints.BIRTH_DEATH.BND_SERVICE_INFORMAION, requestParam);
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> serviceInfo = (LinkedHashMap<String, Object>) JersyCall.callRestTemplateClient(orgId, uri.toString());
		return serviceInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BirthRegistrationDTO saveIssuanceOfBirtCert(BirthRegistrationDTO birthRegDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(birthRegDto, ServiceEndpoints.BIRTH_DEATH.SAVE_ISSUENCE_BIRTH_DETAIL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, BirthRegistrationDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegistrationDTO> getBirthRegiDetailForCorr(BirthRegistrationDTO birthRegDto) {
		List<BirthRegistrationDTO> listDTO = new ArrayList<BirthRegistrationDTO>();

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(birthRegDto, ServiceEndpoints.BIRTH_DEATH.SEARCH_BIRTH_DATA);

		requestList.forEach(birthDTO -> {
			String jsonObject = new JSONObject(birthDTO).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				BirthRegistrationDTO estateBookingDTO = objectMapper.readValue(jsonObject, BirthRegistrationDTO.class);
				listDTO.add(estateBookingDTO);
			} catch (Exception exception) {
				throw new FrameworkException("Exception Occured when fetching details ", exception);
			}
		});

		return listDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HospitalMasterDTO getHospitalById(Long hiId) {
		HospitalMasterDTO hospDto = new HospitalMasterDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.BND_FETCH_HOSPITALS + hiId);
		final String dto = new JSONObject(responseVo).toString();
		try {
			hospDto = new ObjectMapper().readValue(dto, HospitalMasterDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Exception Occured when fetching hospital details", e);
		}
		return hospDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BirthRegistrationDTO getBirthByApplId(Long applicationId,Long orgId) {
		BirthRegistrationDTO birthDto = new BirthRegistrationDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.SEARCH_BIRTH_DATA_BY_APPLID + applicationId +"/"+ orgId);
		final String dto = new JSONObject(responseVo).toString();
		try {
			birthDto = new ObjectMapper().readValue(dto, BirthRegistrationDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Exception Occured when fetching details", e);
		}

		return birthDto;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BirthRegistrationDTO getBirthIssuanceApplId(Long applicationId,Long orgId) {
		BirthRegistrationDTO birthDto = new BirthRegistrationDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.SEARCH_BIRTH_ISSUENCE_DATA_BY_APPLID + applicationId +"/"+ orgId);
		final String dto = new JSONObject(responseVo).toString();
		try {
			birthDto = new ObjectMapper().readValue(dto, BirthRegistrationDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Exception Occured when fetching details", e);
		}

		return birthDto;

	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, Object> getTaxDescByTaxCode(Long orgId, String taxCode) {
		
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("taxCode", taxCode);
		URI uri = dd.expand(ServiceEndpoints.BIRTH_DEATH.GET_TAX_DESC, requestParam);
		
		LinkedHashMap<String, Object> serviceInfo = (LinkedHashMap<String, Object>) JersyCall.callRestTemplateClient(orgId, uri.toString());
		return serviceInfo;
	}
}
