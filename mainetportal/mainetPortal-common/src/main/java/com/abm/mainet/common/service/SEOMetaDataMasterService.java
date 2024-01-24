package com.abm.mainet.common.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dao.SEOMetaDataMasterRepository;
import com.abm.mainet.common.domain.SEOKeyWordMaster;

@Service
public class SEOMetaDataMasterService implements ISEOMetaDataMasterService {

    @Autowired
    private SEOMetaDataMasterRepository repository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.cms.service.ISEOMetaDataMasterService#saveOrUpdateSEOMaster(com.abm.mainet.cms.domain.SEOKeyWordMaster)
     */
    @Override
    public SEOKeyWordMaster saveOrUpdateSEOMaster(SEOKeyWordMaster master) {
        return repository.save(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.cms.service.ISEOMetaDataMasterService#getSEOMaster(long)
     */
    @Override
    public Optional<SEOKeyWordMaster> getSEOMaster(long orgId) {
        return repository.findByOrgId(orgId);
    }

}
