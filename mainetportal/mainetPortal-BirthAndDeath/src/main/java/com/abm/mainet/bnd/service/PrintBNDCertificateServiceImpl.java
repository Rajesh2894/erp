package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PrintBNDCertificateServiceImpl implements PrintBNDCertificateService {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbBdCertCopyDTO> getPrintCertificateDetails(Long applicationId, Long orgId) {
		List<LinkedHashMap<Long, Object>> responseVo = (List) JersyCall.callRestTemplateClient(null,
				ServiceEndpoints.BIRTH_DEATH.BND_GET_CERTIFICATE_DETAILS_BY_APPID + applicationId
						+ MainetConstants.WINDOWS_SLASH + orgId);
		List<TbBdCertCopyDTO> dtos = new ArrayList<TbBdCertCopyDTO>();
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				TbBdCertCopyDTO certDto = new ObjectMapper().readValue(d, TbBdCertCopyDTO.class);
				dtos.add(certDto);
			}
		} catch (final IOException e) {
			throw new FrameworkException(
					"Exception Occured when fetching certificate details in getPrintCertificateDetails()", e);
		}
		return dtos;
	}

	@Override
	public BirthRegistrationDTO getBirthRegisteredAppliDetail(String certNo, String regNo, Date regDate, String applNo,
			Long orgId) {
		BirthRegistrationDTO birthDto = new BirthRegistrationDTO();
		birthDto.setBrCertNo(certNo);
		birthDto.setBrRegNo(regNo);
		birthDto.setBrRegDate(regDate);
		birthDto.setApplicationId(applNo);
		birthDto.setApmApplicationId(Long.valueOf(applNo));
		birthDto.setOrgId(orgId);
		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(birthDto, ServiceEndpoints.BIRTH_DEATH.GET_BIRTH_CERTIFICATE_BY_IDS);

		final String dto = new JSONObject(responseVo).toString();
		try {
			birthDto = new ObjectMapper().readValue(dto, BirthRegistrationDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException(
					"Exception Occured when fetching details in method getBirthRegisteredAppliDetail", e);
		}

		return birthDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TbDeathregDTO getDeathRegisteredAppliDetail(String certNo, String regNo, Date regDate, String applNo,
			Long orgId) {
		TbDeathregDTO deathDto = new TbDeathregDTO();
		deathDto.setApplicationNo(applNo);
		deathDto.setOrgId(orgId);
		deathDto.setApmApplicationId(Long.valueOf(applNo));
		deathDto.setDrRegno(regNo);
		deathDto.setDrRegdate(regDate);
		deathDto.setDrCertNo(certNo);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(deathDto, ServiceEndpoints.BIRTH_DEATH.GET_DEATH_CERTIFICATE_BY_IDS);
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TbDeathregDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public Boolean updatPrintStatus(Long brId, Long orgId, Long bdId) {
		Object responseVo = (Object) JersyCall.callRestTemplateClient(null,
				ServiceEndpoints.BIRTH_DEATH.UPDATE_PRINT_STATUS + brId + MainetConstants.WINDOWS_SLASH + orgId
						+ MainetConstants.WINDOWS_SLASH + bdId);
		try {
			Boolean status = (Boolean) responseVo;
			return status;
		} catch (final Exception e) {
			throw new FrameworkException("Exception while updating certificate staus in updatPrintStatus()", e);
		}

	}
}
