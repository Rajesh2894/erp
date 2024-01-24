package com.abm.mainet.water.service;

import java.util.Map;

public interface WaterCommonService {

    Map<String, String> validateAndFetchProperty(final String propertyNo, final Long orgId);
}
