package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author vishwanath.s
 *
 */
@Service

public class InclusionOfChildNameServiceImpl implements InclusionOfChildNameService {

	
	@SuppressWarnings("unchecked")
	@Override
	public BirthRegistrationDTO getBirthByID(Long brId) {
		BirthRegistrationDTO birthDto = new BirthRegistrationDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(null, ServiceEndpoints.BIRTH_DEATH.SEARCH_BIRTH_INCLUSION_DATA_BY_ID+ brId);
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
	public BirthRegistrationDTO saveInclusionOfChild(BirthRegistrationDTO birthCertificateDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(birthCertificateDto,ServiceEndpoints.BIRTH_DEATH.SAVE_BND_BIRTH_INCLUSION_DATA);
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
	
	@Override
	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto) {
		long noOfDays = Utility.getDaysBetweenDates(birthRegDto.getBrDob(), new Date());
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

	
}
