/**
 * 
 */
package com.abm.mainet.adh.service;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.NewWaterServiceConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

/**
 * @author cherupelli.srikanth
 * @since 31 October 2019
 */
@Service
public class BRMSADHServiceImpl implements IBRMSADHService {

    @Override
    public WSResponseDTO getApplicableTaxes(final ADHRateMaster rate) {
        final WSRequestDTO dto = new WSRequestDTO();
        // rate.setOrgId(orgid);
        // rate.setServiceCode(serviceShortCode);
        rate.setChargeApplicableAt(Long.toString(CommonMasterUtility
                .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL, NewWaterServiceConstants.CAA)
                .getLookUpId()));
        dto.setDataModel(rate);
        return JersyCall.callServiceBRMSRestClient(dto, ServiceEndpoints.BRMS_ADH_URL.ADH_DEPENDENT_PARAM_URL);
    }

    @Override
    public List<ADHRateMaster> getLoiChargesForADH(WSRequestDTO requestDto, Long orgId, String serviceShortCode) {
        return null;
    }

    @Override
    public List<ChargeDetailDTO> getApplicableCharges(List<ADHRateMaster> requiredCHarges) {
        final WSRequestDTO request = new WSRequestDTO();
        request.setDataModel(requiredCHarges);
        WSResponseDTO response = null;
        try {
            response = JersyCall.callServiceBRMSRestClient(request,
                    ServiceEndpoints.BRMS_ADH_URL.ADH_SERVICE_CHARGE_URL);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> charges = JersyCall.castResponse(response, ChargeDetailDTO.class);

                return (List<ChargeDetailDTO>) charges;
            } else {
                throw new FrameworkException("Exception while fatching service charges" + response.getErrorMessage());
            }
        } catch (Exception exception) {
            throw new FrameworkException("Exception while fatching service charges" + response.getErrorMessage());
        }
    }

    @Override
    public WSResponseDTO getApplicableTaxes(WSRequestDTO taxReqDTO) {
        WSResponseDTO applicableTaxes = null;
        try {
            applicableTaxes = JersyCall.callServiceBRMSRestClient(taxReqDTO,
                    ServiceEndpoints.BRMS_ADH_URL.ADH_DEPENDENT_PARAM_URL);
        } catch (Exception exception) {
            throw new FrameworkException("Exception while fetching getApplicableTaxes from service call:", exception);
        }

        return applicableTaxes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String serviceShortCode) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Map<String, String> requestParam = new HashMap<>();
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        dd.setParsePath(true);
        requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
        requestParam.put(MainetConstants.AdvertisingAndHoarding.SERVICE_SGORTCODE, serviceShortCode);
        URI uri = dd.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_CHECKLIST_CHARGES_FLAG_AND_LICMAXDAYS,
                requestParam);
        final LinkedHashMap<String, Object> responseVo = (LinkedHashMap<String, Object>) JersyCall
                .callRestTemplateClient(orgId, uri.toString());

        map = responseVo;

        return map;
    }

    @Override
    public Map<String, String> getWardAndZone(Long orgId, Long Location) {
        Map<String, String> wardZone = null;
        try {
            Map<String, String> requestParam = new HashMap<>();
            DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
            dd.setParsePath(true);
            requestParam.put("orgId", String.valueOf(orgId));
            requestParam.put("loc", String.valueOf(Location));
            URI uri = dd.expand(
                    ServiceEndpoints.ADVERTISER_HOARDING.GET_ADVERTISEMENT_WARD_ZONE_BY_LICENSENO_ORGID,
                    requestParam);
            wardZone = (Map<String, String>) JersyCall.callRestTemplateClient(orgId, uri.toString());
        } catch (Exception exception) {
            throw new FrameworkException("Exception while fetching getApplicableTaxes from service call:", exception);
        }

        return wardZone;
    }
}
