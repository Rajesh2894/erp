/**
 * 
 */
package com.abm.mainet.additional.service;
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

import com.abm.mainet.additional.dto.EChallanMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cherupelli.srikanth
 *
 */
@Service
public class EChallanEntryServiceImpl implements EChallanEntryService{



	@SuppressWarnings("unchecked")
	@Override
	public List<EChallanMasterDto> searchRaidDetailsList(String raidNo, String offenderName, Date challanFromDate,
			Date challanToDate, String offenderMobNo, Long orgid) {

		List<EChallanMasterDto> responseDto = new ArrayList<>();
		EChallanMasterDto inputRequest = new EChallanMasterDto();
		inputRequest.setRaidNo(raidNo);
		inputRequest.setOffenderName(offenderName);
		inputRequest.setOffenderMobNo(offenderMobNo);
		inputRequest.setOrgid(orgid);
		inputRequest.setChallanToDate(challanToDate);
		inputRequest.setChallanFromDate(challanFromDate);

		
		final List<LinkedHashMap<Long, Object>>  responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(inputRequest, ServiceEndpoints.GET_RAID_DETAILS_BY_RAIDNO);
		
		if (responseVo != null && !responseVo.isEmpty()) {
			responseVo.forEach(response -> {
				String jsonObject = new JSONObject(response).toString();
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					EChallanMasterDto lookUp = objectMapper.readValue(jsonObject, EChallanMasterDto.class);
					responseDto.add(lookUp);
				} catch (Exception exception) {
					throw new FrameworkException("Error Occured while fetching RaidDetailsList ", exception);
				}
			});
		}

		return responseDto;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public EChallanMasterDto getEChallanMasterByOrgidAndChallanId(Long orgId, Long challanId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		EChallanMasterDto requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("challanId", String.valueOf(challanId));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = dd.expand(ServiceEndpoints.GET_CHALLAN_MASTER_BY_CHALLAN_ID, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, EChallanMasterDto.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to EChallanMasterDto", e);
		}
		
		
		return requestDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long challanId, Long orgId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		ChallanReceiptPrintDTO requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("challanId", String.valueOf(challanId));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = dd.expand(ServiceEndpoints.GET_DUPLICATE_RECEIPT_DETAIL, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		if(responseVo != null) {
			final String dto = new JSONObject(responseVo).toString();
			try {
				requestDto = new ObjectMapper().readValue(dto, ChallanReceiptPrintDTO.class);
			} catch (final IOException e) {
				throw new FrameworkException("Error while casting response to ChallanReceiptPrintDTO", e);
			}
		}else {
			return null;
		}
		
		
		
		return requestDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean saveEChallanEntry(EChallanMasterDto challanMasterDto) {
		boolean flag = true;
		EChallanMasterDto requestDto = null;
		final LinkedHashMap<Long, Object>  responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(challanMasterDto, ServiceEndpoints.SAVE_ECHALL_DETAILS);
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, EChallanMasterDto.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to EChallanMasterDto", e);
		}
		
		if(requestDto != null && requestDto.getStatus().equals("F")) {
			return false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentDetailsVO> getDocumentUploadedByRefNoAndDeptId(String raidNo, Long orgId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		List<DocumentDetailsVO> responseDto = new ArrayList<>();
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("raidNo", raidNo);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = dd.expand(ServiceEndpoints.GET_DOCUMENT_BY_REFNO, requestParam);
		
		
		final List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		
		if (responseVo != null && !responseVo.isEmpty()) {
			responseVo.forEach(response -> {
				String jsonObject = new JSONObject(response).toString();
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					DocumentDetailsVO lookUp = objectMapper.readValue(jsonObject, DocumentDetailsVO.class);
					responseDto.add(lookUp);
				} catch (Exception exception) {
					throw new FrameworkException("Error Occured while fetching documents ", exception);
				}
			});
		}
		return responseDto;
	}


}
