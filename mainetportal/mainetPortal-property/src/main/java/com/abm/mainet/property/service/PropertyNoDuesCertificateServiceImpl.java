package com.abm.mainet.property.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PropertyNoDuesCertificateServiceImpl implements PropertyNoDuesCertificateService {

	private static final Logger LOGGER = Logger.getLogger(PropertyNoDuesCertificateServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public NoDuesCertificateDto getPropertyDetailsByPropertyNumber(NoDuesCertificateDto dto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				dto, ApplicationSession.getInstance().getMessage("PORPERTY_DETAILS_BY_PROP_NO"));
		if (responseVo != null) {

			final String d = new JSONObject(responseVo).toString();
			try {
				return new ObjectMapper().readValue(d, NoDuesCertificateDto.class);
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
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentDetailsVO> fetchCheckList(NoDuesCertificateDto dto) {
		List<DocumentDetailsVO> dataModel = new ArrayList<>();
		final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(dto,
						ApplicationSession.getInstance().getMessage("PROPERTY_GET_NO_DUES_CHECKLIST"));

		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				DocumentDetailsVO l = new ObjectMapper().readValue(d, DocumentDetailsVO.class);
				dataModel.add(l);
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
		return dataModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NoDuesCertificateDto generateNoDuesCertificate(NoDuesCertificateDto noDuesCertificateDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				noDuesCertificateDto,
				ApplicationSession.getInstance().getMessage("PROPERTY_GENERATE_NO_DUES_CERTIFICATE"));
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, NoDuesCertificateDto.class);
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
		return noDuesCertificateDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NoDuesCertificateDto fetchChargesForNoDues(NoDuesCertificateDto noDuesDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				noDuesDto, ApplicationSession.getInstance().getMessage("PROPERTY_GET_NO_DUES_CHARGES"));
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, NoDuesCertificateDto.class);
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
		return noDuesDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NoDuesCertificateDto getPropertyServiceData(NoDuesCertificateDto dto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ApplicationSession.getInstance().getMessage("GET_PROPERTY_SERVICE_DATA"));
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();
			try {
				dto = new ObjectMapper().readValue(d, NoDuesCertificateDto.class);
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
		}
		return dto;
	}

	@Override
	public boolean getChequeClearanceStatus(NoDuesCertificateDto dto) {
		final boolean responseVo = (boolean) JersyCall.callRestTemplateClient(dto,
				ApplicationSession.getInstance().getMessage("CHECK_CHEQUE_CLEARANCE_STATUS"));
		return responseVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NoDuesCertificateDto getPropertyDetailsByPropertyNumberNFlatNo(NoDuesCertificateDto noDuesDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(noDuesDto, ApplicationSession.getInstance().getMessage("GET_PORPERTY_BY_PROPNO_N_FLATNO"));
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();
			try {
				return new ObjectMapper().readValue(d, NoDuesCertificateDto.class);
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
		}
		return noDuesDto;
	}

	@Override
	public boolean initiateWorkflowForPropertyfreeService(NoDuesCertificateDto dto) {
		final boolean responseVo = (boolean) JersyCall.callRestTemplateClient(dto,
				ApplicationSession.getInstance().getMessage("INITIATE_WORKFLOW_PROPERTY_SERVICE"));
		return responseVo;
		
	}
	
	@Override
	public boolean checkWorkflowDefined(NoDuesCertificateDto dto) {
		final boolean responseVo = (boolean) JersyCall.callRestTemplateClient(dto,
				ApplicationSession.getInstance().getMessage("CHECK_WORKFLOW_DEFINED"));
		return responseVo;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPropertyFlatList(String propNo, Long orgId){		
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("propNo", propNo);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ApplicationSession.getInstance().getMessage("GET_FLAT_LIST_BY_PROP_NO"), requestParam);
		List<String> requestList = (List<String>) JersyCall
				.callRestTemplateClient(requestParam, uri.toString());
		if (requestList != null) {
			return requestList;			
		}
		return null;
		
	}
	
	@Override
	public String getPropertyBillingMethod(String propNo, Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("propNo", propNo);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ApplicationSession.getInstance().getMessage("GET_BILLING_MEHOD_BY_PROP_NO"), requestParam);
		String billingMethod = "";
		final LinkedHashMap<String, String> responseVo  =  (LinkedHashMap<String, String>) JersyCall.callRestTemplateClient(null, uri.toString());
		if (responseVo != null) 
			billingMethod = (String) new JSONObject(responseVo).get("billingMethod");
		return billingMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NoDuesCertificateDto getNoDuesDetails(NoDuesCertificateDto noDuesCertificateDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(noDuesCertificateDto, ApplicationSession.getInstance().getMessage("GET_NO_DUES_APPLICANT_DETAILS"));
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();
			try {
				return new ObjectMapper().readValue(d, NoDuesCertificateDto.class);
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
		}
		return noDuesCertificateDto;
		
	}

	@Override
	public int getBillExistByPropNoFlatNoAndYearId(NoDuesPropertyDetailsDto detailsDto) {
		final int count = (int) JersyCall.callRestTemplateClient(detailsDto,
				ApplicationSession.getInstance().getMessage("GET_BILL_EXISTED_BY_PROP_FLAT_YEARID"));
		return count;
	}


}
