package com.abm.mainet.dashboard.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dao.IPortalServiceMasterDao;
import com.abm.mainet.common.domain.ApplicationPortalMaster;

@Service
public class ServiceApplicationStatusService implements IServiceApplicationStatus {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IPortalServiceMasterDao iServiceMasterDao;

    @Override
    public ApplicationPortalMaster getServiceApplicationStatus(final Long appId) {
        return iServiceMasterDao.getServiceApplicationStatus(appId);
    }

}
