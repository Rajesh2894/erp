package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.utility.LookUp;

/**
 * @author Pranit.Mhatre
 * @since 26 November, 2013
 */
public interface ILookUpService {
    // public Map<String, Object> getAllPrefixDetails();

    public Map<String, Object> getNonHirachicalPrefixDetails();

    public Map<String, Object> getHirachicalPrefixDetails();

    public List<String> getNonReplicatePrefix();

    // Rajesh
    Map<Long, LookUp> getNonHirachicalPrefixDetailsByLookupID();

    // Rajesh
    Map<Long, LookUp> getHirachicalPrefixDetailsByLookupID();
}
