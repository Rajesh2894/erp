package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.common.domain.Organisation;

public interface IEipAnnouncementLandingDAO {

    public abstract List<EIPAnnouncementLanding> getEIPAnnouncementLanding(Organisation organisation);

    public abstract List<EIPAnnouncementLanding> getLandingpageEIPAnnouncement(Organisation organisation);

    public abstract boolean saveOrUpdate(EIPAnnouncementLanding eipAnnouncementLanding);

    public abstract EIPAnnouncementLanding getEipAnnouncementLandingById(long announceId);

}