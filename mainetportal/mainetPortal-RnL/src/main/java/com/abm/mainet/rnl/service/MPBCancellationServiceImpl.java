/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author priti.singh
 *
 */
@Service
public class MPBCancellationServiceImpl implements MPBCancellationService {

	private static final Logger logger = Logger.getLogger(MPBCancellationServiceImpl.class);

	@SuppressWarnings("unchecked")

	@Override
	public List<EstateBookingDTO> fetchAllBookingsByOrg(Long userId, Long orgId) {

		Map<String, String> requestParam = new HashMap<>();

		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("userId", String.valueOf(userId));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ServiceEndpoints.BRMS_RNL_URL.RNL_BOOKED_PROPERTY_DETAILS_BY_USERID, requestParam);
		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(requestParam, uri.toString());

		List<EstateBookingDTO> masterDtosList = new ArrayList<>();
		requestList.forEach(estateBookingCancelDto -> {
			String jsonObject = new JSONObject(estateBookingCancelDto).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				EstateBookingDTO estateBookingDTO = objectMapper.readValue(jsonObject, EstateBookingDTO.class);
				masterDtosList.add(estateBookingDTO);
			} catch (Exception exception) {
				throw new FrameworkException("Exception Occured when fetching Booked Property details ", exception);
			}
		});

		return masterDtosList;

	}

	@Override
	public List<PropInfoDTO> fetchAllBookedPropertyDetails(String bookingNo, Long orgId) {

		Map<String, String> requestParam = new HashMap<>();

		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("bookingNo", bookingNo);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = uriHandler.expand(ServiceEndpoints.BRMS_RNL_URL.RNL_ALL_DETAILS_OF_BOOKED_PROPERTY, requestParam);
		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(requestParam, uri.toString());

		List<PropInfoDTO> propInfoDTOList = new ArrayList<>();
		requestList.forEach(estateBookingCancelDto -> {
			String jsonObject = new JSONObject(estateBookingCancelDto).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				PropInfoDTO propInfoDTO = objectMapper.readValue(jsonObject, PropInfoDTO.class);
				propInfoDTOList.add(propInfoDTO);
			} catch (Exception exception) {
				throw new FrameworkException("Exception Occured when fetching Booked Property details ", exception);
			}
		});

		return propInfoDTOList;
	}

	@Override
	public EstateBookingDTO saveBookingCancellation(EstateBookingDTO dto) {
		dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ServiceEndpoints.BRMS_RNL_URL.RNL_SAVE_BOOKING_CANCELLATION);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, EstateBookingDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@Override
	public BookingReqDTO getBookingDetailsByBookingId(String bookingId, Long orgId) {
		BookingReqDTO bookDto = new BookingReqDTO();
		bookDto.getEstateBookingDTO().setBookingNo(bookingId);
		bookDto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				bookDto, ServiceEndpoints.BRMS_RNL_URL.FETCH_EST_BOOKING_DETAILS + MainetConstants.WINDOWS_SLASH
						+ bookingId + MainetConstants.WINDOWS_SLASH + bookDto.getOrgId());
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, BookingReqDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}
}
