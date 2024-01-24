package com.abm.mainet.tradeLicense.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.PropertyDetailDto;
import com.abm.mainet.integration.ws.dto.PropertyInputDto;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TradeLicenseApplicationServiceImpl implements ITradeLicenseApplicationService {

	private static Logger log = Logger.getLogger(ITradeLicenseApplicationService.class);
	@Autowired
	private IOrganisationService organisatonService;
	public TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO tradeMasterDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				tradeMasterDto, ServiceEndpoints.TRADE_LICENSE_URL.TRADE_LICENSE_SERVICE_SAVE_URL);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	public TradeMasterDetailDTO getPropertyDetailsByPropertyNumber(TradeMasterDetailDTO tradeMasterDTO) {
		TradeMasterDetailDTO infoDTO = new TradeMasterDetailDTO();
		PropertyDetailDto detailDTO = null;
		PropertyInputDto propInputDTO = new PropertyInputDto();
		propInputDTO.setPropertyNo(tradeMasterDTO.getPmPropNo());
		propInputDTO.setOrgId(tradeMasterDTO.getOrgid());		
        //#124288	
		if (StringUtils.isNotEmpty(tradeMasterDTO.getTrdFlatNo()))
		propInputDTO.setFlatNo(tradeMasterDTO.getTrdFlatNo());
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(propInputDTO, ServiceEndpoints.ServiceCallURI.PROP_BY_PROP_NO_AND_FLATNO);

		final String d = new JSONObject(responseVo).toString();
		try {
			detailDTO = new ObjectMapper().readValue(d, PropertyDetailDto.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to Trade", e);
		}
		log.info("PropertyDetailDto formed is " + detailDTO.toString());

		if (detailDTO != null) {
			if (tradeMasterDTO.getLangId() != null && tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID &&  StringUtils.isNotEmpty(detailDTO.getJointOwnerNameReg()))
			infoDTO.setPrimaryOwnerName(detailDTO.getJointOwnerNameReg());
			else
			infoDTO.setPrimaryOwnerName(detailDTO.getJointOwnerName());
			if (tradeMasterDTO.getLangId() != null && tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID &&  StringUtils.isNotEmpty(detailDTO.getUsageTypeReg()))
			infoDTO.setUsage(detailDTO.getUsageTypeReg());
			else
			infoDTO.setUsage(detailDTO.getUasge());
			infoDTO.setTotalOutsatandingAmt(detailDTO.getTotalOutsatandingAmt());
			if (tradeMasterDTO.getLangId() != null && tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID &&  StringUtils.isNotEmpty(detailDTO.getPropAddressReg()))
			infoDTO.setPropertyAddress(detailDTO.getPropAddressReg());
			else
			infoDTO.setPropertyAddress(detailDTO.getAddress());
			infoDTO.setPmPropNo(detailDTO.getPropNo());
			infoDTO.setSurveyNumber(detailDTO.getTppSurveyNumber());
			infoDTO.setVillageName(detailDTO.getAreaName());
			infoDTO.setPartNo(detailDTO.getTppKhataNo());
			infoDTO.setPlotNo(detailDTO.getTppPlotNo());
			infoDTO.setRoadType(detailDTO.getProAssdRoadfactorDesc());
			infoDTO.setPropLvlRoadType(detailDTO.getRoadTypeId());
			infoDTO.setLandType(detailDTO.getLandTypeId());
			infoDTO.setAssPlotArea(detailDTO.getAssPlotArea());
			infoDTO.setAssessmentCheckFlag(detailDTO.getAssessmentCheckFlag());
			infoDTO.setTrdLicno(tradeMasterDTO.getTrdLicno());
			infoDTO.setpTaxCollEmpId(detailDTO.getpTaxCollEmpId());
			infoDTO.setpTaxCollEmpName(detailDTO.getpTaxCollEmpName());
			infoDTO.setpTaxCollEmpMobNo(detailDTO.getpTaxCollEmpMobNo());
			infoDTO.setpTaxCollEmpEmailId(detailDTO.getpTaxCollEmpEmailId());
			if (tradeMasterDTO.getLangId() != null && tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID &&  StringUtils.isNotEmpty(detailDTO.getPropertyTypeReg()))
			infoDTO.setPropertyType(detailDTO.getPropertyTypeReg());
			else
			infoDTO.setPropertyType(detailDTO.getPropertyType());
			if (tradeMasterDTO.getTrdFlatNo() != null)
			infoDTO.setTrdFlatNo(tradeMasterDTO.getTrdFlatNo().replace(",", ""));
			// infoDTO.setCsOrdcross(detailDTO.getLocation());
			/*
			 * if (detailDTO.getGender() != null && !detailDTO.getGender().isEmpty()) { Long
			 * lookupId =
			 * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(detailDTO.getGender(),
			 * MainetConstants.GENDER, requestDTO.getOrgId()); if (lookupId != null &&
			 * lookupId != 0L) { LookUp lookUp =
			 * CommonMasterUtility.lookUpByLookUpIdAndPrefix(lookupId,
			 * MainetConstants.GENDER, requestDTO.getOrgId());
			 * infoDTO.setCsOGender(lookUp.getLookUpId()); } }
			 */
		}
		//to get water due amount by property no
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA)) {	
			if (StringUtils.isNotEmpty(tradeMasterDTO.getPmPropNo())) {
			final Double response = (Double) JersyCall
	                .callRestTemplateClient(tradeMasterDTO.getPmPropNo(),ServiceEndpoints.ServiceCallURI.WATER_DUE_AMOUNT_BY_PROP_NO + MainetConstants.WINDOWS_SLASH + tradeMasterDTO.getPmPropNo());
			if ((response != null)) {
					infoDTO.setTotalWaterOutsatandingAmt(response);
			}else {
				log.info("Problem occured while getting Water outstanding by property Number " + tradeMasterDTO.getPmPropNo());
			}
		 }
		}
		return infoDTO;
	}

	@Override
	public TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(masDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_CHARGES_FROM_BRMS_RULE);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public TradeMasterDetailDTO getTradeLicenceApplicationChargesFromBrmsRule(TradeMasterDetailDTO masDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				masDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_APPLICATION_CHARGES_FROM_BRMS_RULE);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public TradeMasterDetailDTO getLicenseDetailsByLicenseNo(String trdLicno, Long orgId) {

		// String key = trdLicno;
		String encodedKey = URLEncoder.encode(trdLicno);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(encodedKey, ServiceEndpoints.TRADE_LICENSE_URL.LICENSE_DETAILS_BY_LICENSE_NO
						+ encodedKey + MainetConstants.WINDOWS_SLASH + orgId);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public TradeMasterDetailDTO getTradeLicenseWithAllDetailsByApplicationId(Long applicationId) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				applicationId, ServiceEndpoints.TRADE_LICENSE_URL.GET_TRADE_LICENSE_DETAILS + applicationId);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String serviceShortCode) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
		uriTemplateHandler.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		requestParam.put(MainetConstants.AdvertisingAndHoarding.SERVICE_SGORTCODE, serviceShortCode);
		URI uri = uriTemplateHandler.expand(ServiceEndpoints.TRADE_LICENSE_URL.GET_SERVICE_MASTER_DETAIL, requestParam);
		final LinkedHashMap<String, Object> responseVo = (LinkedHashMap<String, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		map = responseVo;
		return map;
	}

	@Override
	public Boolean resolveServiceWorkflowType(TradeMasterDetailDTO tradeDTO) {
		// TODO Auto-generated method stub
		final Boolean responseVo = (Boolean) JersyCall.callRestTemplateClient(tradeDTO,
				ServiceEndpoints.TRADE_LICENSE_URL.CHECK_WORKFLOW_DEFINED_OR_NOT);

		try {
			return responseVo;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	@Override
	public Boolean checkTaskRejectedOrNot(Long applicationId, Long orgId) {
		
//D#125111
		final Boolean responseVo = (Boolean) JersyCall.callRestTemplateClient(applicationId,
				ServiceEndpoints.TRADE_LICENSE_URL.CHEK_APP_REJ_OR_NOT + MainetConstants.WINDOWS_SLASH + applicationId + MainetConstants.WINDOWS_SLASH
						+ orgId);

		try {
			return responseVo;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public List<TradeLicenseOwnerDetailDTO> getOwnerList(String licNo, Long orgId) {

		String encodedKey = URLEncoder.encode(licNo);

		@SuppressWarnings("unchecked")
		List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(encodedKey, ServiceEndpoints.TRADE_LICENSE_URL.GET_OWNER_DETAILS + encodedKey
						+ MainetConstants.WINDOWS_SLASH + orgId);
		List<TradeLicenseOwnerDetailDTO> dtos = new ArrayList<>();
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				TradeLicenseOwnerDetailDTO dto = new ObjectMapper().readValue(d, TradeLicenseOwnerDetailDTO.class);
				dtos.add(dto);
			}
		} catch (Exception e) {
		}
		return dtos;
	}


	public CommonChallanDTO getFeesId(TradeMasterDetailDTO tradeMasterDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				tradeMasterDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_FEESID);

		final String d = new JSONObject(responseVo).toString();
		try {
			return  new ObjectMapper().readValue(d, CommonChallanDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}
	
	@Override
	public boolean isKDMCEnvPresent() {
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV",org);
		return  envLookUpList.stream().anyMatch(
                env -> env.getLookUpCode().equals("SKDCL") && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		//return true;
	}


    //#124288	 to get flat number list
	@Override
	public List<String> getPropertyFlatNo(String propNo, Long orgId) {		
		// call for flat no list
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		if (StringUtils.isNotBlank(propNo)) {
			requestParam.put("propNo", propNo.replaceAll("\\s", ""));
		} else {
			log.info("propNo can't be empty");
		}
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ApplicationSession.getInstance().getMessage("GET_FLAT_LIST_BY_PROP_NO"),
				requestParam);
		List<String> flatNoList = (List<String>) JersyCall.callRestTemplateClient(requestParam, uri.toString());
		if (flatNoList != null && !flatNoList.isEmpty()) {		
			return flatNoList;
		} else {
			return flatNoList;
		}
		// return null;

	}

    //#140263
	@Override
	public List<TradeMasterDetailDTO> getLicenseDetails(TradeMasterDetailDTO dto) {
		@SuppressWarnings("unchecked")
		List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(dto, ServiceEndpoints.TRADE_LICENSE_URL.GET_LICENSE_DETAILS);
		List<TradeMasterDetailDTO> dtos = new ArrayList<>();
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				TradeMasterDetailDTO mstDto = new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
				mstDto.setLicenseToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mstDto.getTrdLictoDate()));
				mstDto.setCreatedDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mstDto.getCreatedDate()));
				dtos.add(mstDto);
			}
		} catch (Exception e) {
			log.info("Problem occured while fetching license details getLicenseDetails() ");
		}
		return dtos;
	}
	@Override
	public void aapaleSarakarPortalEntry(TradeMasterDetailDTO masDto) {

		log.error("Aaple Sarkar: " + masDto.getTenant() + "ApplicationId:" + masDto.getApmApplicationId());
		String request= masDto.getTenant();
		
		log.error("Before Updating Status: " + request);
		
		String replace = request.replace("appId", masDto.getApmApplicationId().toString());
		
		String replace1= replace.replace("appStatus", MainetConstants.Common_Constant.NUMBER.THREE);
		
		String replace2= replace1.replace("remark", "Dept Scrutiny");
		
		log.error("Final Request: " + replace2);

		EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
		
		encryptDecrypt.getUpdateStatus(replace2);
		
	}
}
