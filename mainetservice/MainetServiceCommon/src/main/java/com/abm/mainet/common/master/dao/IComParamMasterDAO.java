package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.domain.ViewPrefixDetails;

public interface IComParamMasterDAO {

    List<String> getAllStartupPrefix();

    List<ViewPrefixDetails> getViewPrefixDetailsByType(String cpmType);

    List<String> getNonReplicatePrefix();

}