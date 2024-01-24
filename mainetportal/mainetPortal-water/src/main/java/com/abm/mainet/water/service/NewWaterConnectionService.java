package com.abm.mainet.water.service;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.PropertyDetailDto;
import com.abm.mainet.integration.ws.dto.PropertyInputDto;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NewWaterConnectionService implements INewWaterConnectionService {

	private static final long serialVersionUID = 1828977797442600240L;

	private UtilityService utilityService;

	private static Logger log = Logger.getLogger(NewWaterConnectionService.class);

	@SuppressWarnings("unchecked")
	@Override
	public NewWaterConnectionResponseDTO saveOrUpdateNewConnection(final NewWaterConnectionReqDTO reqDTO) {
		NewWaterConnectionResponseDTO outPutObject = new NewWaterConnectionResponseDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(reqDTO, ServiceEndpoints.ServiceCallURI.NEW_WATER_CONNECTION_SAVE_URI);
		final String d = new JSONObject(responseVo).toString();
		try {
			outPutObject = new ObjectMapper().readValue(d, NewWaterConnectionResponseDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to NewWaterConnectionResponseDTO", e);
		}
		return outPutObject;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProvisionalCertificateDTO getProvisionalCertificateData(final ProvisionalCertificateDTO reqDTO) {
		ProvisionalCertificateDTO outPutObject = new ProvisionalCertificateDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(reqDTO, ServiceEndpoints.ServiceCallURI.NEW_WATER_CONNECTION_PROVISIONAL_CERT);
		final String d = new JSONObject(responseVo).toString();
		try {
			outPutObject = new ObjectMapper().readValue(d, ProvisionalCertificateDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to ProvisionalCertificateDTO", e);
		}
		return outPutObject;
	}

	@Override
	public void findNoOfDaysCalculation(TbCsmrInfoDTO csmrDto, Organisation organisation) {

		// String lookUpId =
		// CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.LookUp.TCD,"NOD",
		// organisation).getOtherField();
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.LookUp.TCD, "NOD");
		String lookUpId = lookUp.getOtherField();
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		String date1 = utilityService.convertDateToDDMMYYYY(csmrDto.getFromDate());
		String date2 = utilityService.convertDateToDDMMYYYY(csmrDto.getToDate());
		if (date1 != null && !date1.isEmpty() && date2 != null && !date2.isEmpty()) {
			try {
				Date fromDate = myFormat.parse(date1);
				Date toDate = myFormat.parse(date2);
				Long diff = toDate.getTime() - fromDate.getTime();
				Long diffDays = diff / (60 * 60 * 1000 * 24);
				csmrDto.setNumberOfDays(diffDays);
				Long maxNumberOfDay = Long.parseLong(lookUpId);
				csmrDto.setMaxNumberOfDay(maxNumberOfDay);
			} catch (Exception e) {
				log.error("Exception In Calculation Of Number Of Days.");
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO) {
		TbCsmrInfoDTO infoDTO = null;
		PropertyDetailDto detailDTO = null;
		PropertyInputDto propInputDTO = new PropertyInputDto();
		propInputDTO.setPropertyNo(requestDTO.getPropertyNo());
		propInputDTO.setOrgId(requestDTO.getOrgId());
		Organisation organisation = new Organisation();
		organisation.setOrgid(requestDTO.getOrgId());
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(propInputDTO, ServiceEndpoints.ServiceCallURI.PROP_BY_PROP_ID);

		final String d = new JSONObject(responseVo).toString();
		try {
			detailDTO = new ObjectMapper().readValue(d, PropertyDetailDto.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to NewWaterConnectionResponseDTO", e);
		}
		log.info("PropertyDetailDto formed is " + detailDTO.toString());
		if (detailDTO != null) {
			infoDTO = new TbCsmrInfoDTO();
			infoDTO.setCsOname(detailDTO.getPrimaryOwnerName());
			infoDTO.setCsOcontactno(detailDTO.getPrimaryOwnerMobNo());
			 if (detailDTO.getTotalArv() != 0) {
                 infoDTO.setArv(detailDTO.getTotalArv());
             }
			if (detailDTO.getOwnerEmail() != null) {
				infoDTO.setCsOEmail(detailDTO.getOwnerEmail());
			}
			DecimalFormat decim = new DecimalFormat(MainetConstants.COMMON_DECIMAL_FORMAT);
			infoDTO.setTotalOutsatandingAmt(Double.valueOf(decim.format(detailDTO.getTotalOutsatandingAmt())));
			if (detailDTO.getPinCode() != null) {
				infoDTO.setOpincode(detailDTO.getPinCode().toString());
			}
			infoDTO.setPropertyUsageType(detailDTO.getUasge());
			if(detailDTO.getGender()!=null && !detailDTO.getGender().isEmpty() )
			infoDTO.setCsOGender(CommonMasterUtility.getValueFromPrefixLookUp(detailDTO.getGender(), "GEN").getLookUpId());
			infoDTO.setCsOadd(detailDTO.getAddress());
		}
		return infoDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlumberDTO> getListofplumber(Long orgid) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgid));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_PLUMBER_LIST, requestParam);

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgid, uri.toString());

		List<PlumberDTO> plumbers = new ArrayList<>();
		requestList.forEach(obj -> {
			String d = new JSONObject(obj).toString();
			try {
				PlumberDTO plumberDTO = new ObjectMapper().readValue(d, PlumberDTO.class);

				plumbers.add(plumberDTO);
			} catch (Exception e) {
			}
		});
		return plumbers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbCsmrInfoDTO> getAllConnectionByMobileNo(String mobileNo, Long orgid) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgid));
		requestParam.put("refNo", String.valueOf(mobileNo));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_ALL_CONNECTIONS, requestParam);

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgid, uri.toString());

		List<TbCsmrInfoDTO> dtos = new ArrayList<>();
		requestList.forEach(obj -> {
			String d = new JSONObject(obj).toString();
			try {
				TbCsmrInfoDTO dto = new ObjectMapper().readValue(d, TbCsmrInfoDTO.class);

				dtos.add(dto);
			} catch (Exception e) {
			}
		});
		return dtos;
	}

	@SuppressWarnings("unchecked")
	public TbCsmrInfoDTO fetchConnectionDetailsByConnNo(String csCcnNo, Long orgid) {
		TbCsmrInfoDTO flowDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("csCcnNo", String.valueOf(csCcnNo));
		requestParam.put("orgId", String.valueOf(orgid));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_CONNECTION_DETAIL_BY_CONNO, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgid, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			flowDto = new ObjectMapper().readValue(dto, TbCsmrInfoDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to TbCsmrInfoDTO", e);
		}
		log.info("TbCsmrInfoDTO formed is " + flowDto.toString());
		return flowDto;
	}

	@Override
	public NewWaterConnectionReqDTO getApplicationData(NewWaterConnectionReqDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDTO, ServiceEndpoints.WebServiceUrl.GET_NEWWATER_DETAILS_BY_APPID);
		final String response = new JSONObject(responseVo).toString();
		return new ObjectMapper().readValue(response, NewWaterConnectionReqDTO.class);

	}

	@SuppressWarnings("unchecked")
	@Override
	public TbCsmrInfoDTO fetchConnectionByIllegalNoticeNo(TbCsmrInfoDTO infoDto) {

		TbCsmrInfoDTO detailDTO = null;
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				infoDto, ServiceEndpoints.WebServiceUrl.GET_CONNECTION_DETAIL_BY_ILLEGALNOTICENO);

		final String d = new JSONObject(responseVo).toString();
		try {
			detailDTO = new ObjectMapper().readValue(d, TbCsmrInfoDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to TbCsmrInfoDTO", e);
		}
		log.info("TbCsmrInfoDTO formed is " + detailDTO.toString());
		return detailDTO;

	}

	@SuppressWarnings("unchecked")
	@Override
	public NewWaterConnectionResponseDTO saveIllegalToLegalConnectionApplication(NewWaterConnectionReqDTO reqDTO) {
		NewWaterConnectionResponseDTO outPutObject = new NewWaterConnectionResponseDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				reqDTO, ServiceEndpoints.WebServiceUrl.ILLEGALTOLEGAL_CONNECTION_SAVE);
		final String d = new JSONObject(responseVo).toString();
		try {
			outPutObject = new ObjectMapper().readValue(d, NewWaterConnectionResponseDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to NewWaterConnectionResponseDTO", e);
		}
		return outPutObject;
	}
}
