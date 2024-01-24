package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IEipAnnouncementService extends Serializable {

    public List<EIPAnnouncement> getAllEIPAnnouncement(Organisation organisation, String flag, String env);

    public List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation);

    public EIPAnnouncement getEIPAnnouncement(long announceId);

    public boolean delete(long announceId);

    public boolean saveOrUpdate(EIPAnnouncement EIPAnnouncement);

}
