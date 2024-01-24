package com.abm.mainet.common.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.dto.PrefixDTO;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Lalit.Prusti
 *
 */
@SuppressWarnings("unchecked")
@Service
public class ComparamMasterWebService implements IComparamMasterWebService {

    private static final Logger LOG = Logger.getLogger(ComparamMasterWebService.class);

    public PrefixDTO getPrefix(final String type) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CommonPrefixUri.LOAD_PREFIX);
        final PrefixDTO prefixDTO = convertToDTO(responseVo);
        return prefixDTO;
    }

    @Override
    public List<ViewPrefixDetails> getViewPrefixDetailsByType(final String cpmType) {

        List<ViewPrefixDetails> ViewPrefixDetailsByType = null;
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(cpmType,
                ServiceEndpoints.CommonPrefixUri.VIEW_DETAILS_BYTYPE);
        if (responseVo != null) {
            final PrefixDTO prefixDTO = convertToDTO(responseVo);
            ViewPrefixDetailsByType = prefixDTO.getViewPrefixDetailsByType();
        }
        return ViewPrefixDetailsByType;
    }

    @Override
    public List<String> getAllStartupPrefix() {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CommonPrefixUri.ALL_STARTUP_PREFIX);

        final PrefixDTO prefixDTO = convertToDTO(responseVo);

        return prefixDTO.getAllStartupPrefix();
    }

    @Override
    public List<String> getNonReplicatePrefix() {

        List<String> nonReplicatePrefix = null;
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CommonPrefixUri.NON_REPLICATE_PREFIX);
        if (responseVo != null) {
            final PrefixDTO prefixDTO = convertToDTO(responseVo);
            nonReplicatePrefix = prefixDTO.getNonReplicatePrefix();
        }

        return nonReplicatePrefix;
    }

    private PrefixDTO convertToDTO(final LinkedHashMap<Long, Object> responseVo) {
        final String d = new JSONObject(responseVo).toString();
        PrefixDTO prefixDTO = null;
        try {

            prefixDTO = new ObjectMapper().readValue(d, PrefixDTO.class);

        } catch (final IOException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return prefixDTO;
    }

}
