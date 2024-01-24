package com.abm.mainet.common.service;

import java.util.Optional;

import com.abm.mainet.common.domain.SEOKeyWordMaster;

public interface ISEOMetaDataMasterService {

    SEOKeyWordMaster saveOrUpdateSEOMaster(SEOKeyWordMaster master);

    Optional<SEOKeyWordMaster> getSEOMaster(long orgId);

}