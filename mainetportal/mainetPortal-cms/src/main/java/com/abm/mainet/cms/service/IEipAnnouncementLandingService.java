package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.common.domain.Organisation;

/**
 * /*@author rajdeep.sinha
 */

public interface IEipAnnouncementLandingService {

    public List<EIPAnnouncementLanding> getAllEIPAnnouncementLanding(Organisation organisation);

    public List<EIPAnnouncementLanding> getLandingEIPAnnouncementLanding(Organisation organisation);

    public boolean saveOrUpdate(EIPAnnouncementLanding eipAnnouncementLanding);

    public EIPAnnouncementLanding getEipAnnouncementLanding(long announceId);

    public boolean delete(long announceId);

}
