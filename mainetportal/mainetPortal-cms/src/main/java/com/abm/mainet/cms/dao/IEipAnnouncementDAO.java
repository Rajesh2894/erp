package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IEipAnnouncementDAO {

    public abstract List<EIPAnnouncement> getEIPAnnouncement(Organisation organisation, String flag , String env);

    public abstract List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation);

    public abstract EIPAnnouncement getEIPAnnouncementById(long announceId);

    public abstract boolean saveOrUpdate(EIPAnnouncement EIPAnnouncement);

}