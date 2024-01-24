package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author sanket.joshi
 *
 */
@Service
public class LocationService {

    /**
     * REST call to MaintService to get all location by organization id.
     * 
     * @param orgId Organization ID
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<LocationDTO> getLocationByOrgId(final Long orgId, final int lang) {
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null, ServiceEndpoints.URLS.GET_LOCATION_BY_ORGID + orgId);
        final List<LocationDTO> locations = new ArrayList<>();
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final LocationDTO l = new ObjectMapper().readValue(d, LocationDTO.class);
                if (l != null) {
                    final String lname = (lang == 1) ? l.getLocNameEng()
                            : (lang == 2) ? l.getLocNameReg() : MainetConstants.BLANK;
                    l.setLocName(lname);
                    locations.add(l);
                }
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
        return locations;
    }

    /**
     * REST call to MaintService to get all location by area pin code.
     * 
     * @param pincode area pin code
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<LocationDTO> getLocationByPinCode(final Integer pincode, final int lang) throws Exception {
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null, ServiceEndpoints.URLS.GET_LOCATION_BY_PINCODE + pincode);
        final List<LocationDTO> locations = new ArrayList<>();
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final LocationDTO l = new ObjectMapper().readValue(d, LocationDTO.class);
                if (l != null) {
                    final String lname = (lang == 1) ? l.getLocNameEng()
                            : (lang == 2) ? l.getLocNameReg() : MainetConstants.BLANK;
                    l.setLocName(lname);
                    locations.add(l);
                }
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
        return locations;
    }

    /**
     * REST call to MaintService to get all location by id.
     * 
     * @param orgId Organization ID
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public LocationDTO getLocationById(Long id, int lang) throws Exception {
        LinkedHashMap<Long, Object> requestList = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.URLS.GET_LOCATION_BY_ID + id);
        LocationDTO l = null;
        String d = new JSONObject(requestList).toString();
        try {
            l = new ObjectMapper().readValue(d, LocationDTO.class);
            if (l != null) {
                String lname = (lang == 1) ? l.getLocNameEng() : (lang == 2) ? l.getLocNameReg() : "";
                l.setLocName(lname);
            }
        } catch (Exception e) {
        }

        return l;
    }

}
