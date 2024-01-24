package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface IDataArchivalService {

    List<PublicNotices> getArchivedDataForPublicNotice(Organisation organisation);

    SubLinkMaster getArchivedSections(String sections);
	SubLinkMaster getArchivedSections(long sectionId);

    List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation);

	List<LookUp> getArchiveSectionName(Organisation organisation, int langId);

	List<Long>  getArchiveFielsId(long id);


}
