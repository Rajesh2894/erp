package com.abm.mainet.tradeLicense.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RenewalLicenseApplicationServiceImpl implements IRenewalLicenseApplicationService {
	
	private static Logger log = Logger.getLogger(IRenewalLicenseApplicationService.class);
	
	@Autowired
	private IOrganisationService organisatonService;

	@Override
	public TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO tardeDto) {
		
		final LinkedHashMap<Long, Object> responseVo =(LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(tardeDto,
        		ServiceEndpoints.TRADE_LICENSE_URL.SAVE_UPDATE_RENEWAL_LICENSE_APPLICATION);

        final String d = new JSONObject(responseVo).toString();
        try {   	
        	return new ObjectMapper().readValue(d,
        			TradeMasterDetailDTO.class);	
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

	
	//127361 to get application time charges from BRMS Rule 
	@Override
	public TradeMasterDetailDTO getTradeLicenceApplicationChargesFromBrmsRule(TradeMasterDetailDTO masDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				masDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_RENEWAL_SERVICE_CHARGES_FROM_BRMS_RULE);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public Boolean checkLicenseNoExist(String trdLicno, Long orgId) {
		// String key = trdLicno;
		String encodedKey = URLEncoder.encode(trdLicno);
		final Boolean responseVo = (Boolean) JersyCall.callRestTemplateClient(encodedKey,
				ServiceEndpoints.TRADE_LICENSE_URL.CHECK_LICENSE_IN_OBJECTION + encodedKey + MainetConstants.WINDOWS_SLASH
						+ orgId);
		try {
			return responseVo;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

}