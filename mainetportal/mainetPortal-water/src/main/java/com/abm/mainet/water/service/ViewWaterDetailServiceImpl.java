package com.abm.mainet.water.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.dto.ViewWaterDetailRequestDto;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Bhagyashri.dongardive
 *
 */
@Service
public class ViewWaterDetailServiceImpl implements ViewWaterDetailService {

	@Override
	public List<WaterDataEntrySearchDTO> searchPropertyDetails(WaterDataEntrySearchDTO searchDto) {
		List<WaterDataEntrySearchDTO> dto = new ArrayList<>();
		ViewWaterDetailRequestDto viewRequestDto = new ViewWaterDetailRequestDto();
		if(StringUtils.isBlank(searchDto.getCsName())) {
			searchDto.setCsName(null);
		}
		if(StringUtils.isBlank(searchDto.getCsContactno())) {
			searchDto.setCsContactno(null);
		}
		if(StringUtils.isNotBlank(searchDto.getCsOldccn())) {
			if(searchDto.getCsOldccn().contains(",")) {
				searchDto.setCsOldccn(null);
			}
		}
		viewRequestDto.setSearchDto(searchDto);
		final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(viewRequestDto,
						ApplicationSession.getInstance().getMessage("SEARCH_WATER_DETAILS"));
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				WaterDataEntrySearchDTO propSearchDto = new ObjectMapper().readValue(d, WaterDataEntrySearchDTO.class);
				dto.add(propSearchDto);
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

	@Override
	public ViewCsmrConnectionDTO getWaterViewByConnectionNumber(TbCsmrInfoDTO infoDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(infoDto, ApplicationSession.getInstance().getMessage("GET_WATER_VIEW_DET"));
		final String d = new JSONObject(responseVo).toString();

		try {
			return new ObjectMapper().readValue(d, ViewCsmrConnectionDTO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LookUp> getCollectionDetails(TbCsmrInfoDTO infoDto) {

		List<LookUp> lookup = new ArrayList<>();
		final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(infoDto,
						ApplicationSession.getInstance().getMessage("GET_WATER_COLLECTION_DET"));

		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				LookUp l = new ObjectMapper().readValue(d, LookUp.class);
				lookup.add(l);
			}
		} catch (JsonParseException e) {
			throw new FrameworkException(e);
		} catch (JsonMappingException e) {
			throw new FrameworkException(e);
		} catch (IOException e) {
			throw new FrameworkException(e);
		}

		return lookup;
	}

	@Override
	public ChallanReceiptPrintDTO getRevenueReceiptDetails(TbCsmrInfoDTO infoDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				infoDto, ApplicationSession.getInstance().getMessage("DOWNLOAD_WATER_REVENUE_RECEIPT"));

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, ChallanReceiptPrintDTO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			throw new FrameworkException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
