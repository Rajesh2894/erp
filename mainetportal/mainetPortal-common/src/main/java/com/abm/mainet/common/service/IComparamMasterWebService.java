package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.ViewPrefixDetails;

public interface IComparamMasterWebService {

    List<String> getAllStartupPrefix();

    List<ViewPrefixDetails> getViewPrefixDetailsByType(String cpmType);

    List<String> getNonReplicatePrefix();

}
