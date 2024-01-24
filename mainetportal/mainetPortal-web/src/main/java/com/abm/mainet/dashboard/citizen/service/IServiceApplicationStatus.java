package com.abm.mainet.dashboard.citizen.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.ApplicationPortalMaster;

@Transactional(readOnly = true)
public interface IServiceApplicationStatus extends Serializable {
    public ApplicationPortalMaster getServiceApplicationStatus(Long appId);
}
