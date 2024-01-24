package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface IDataArchivalDao {

    List<PublicNotices> getArchivedDataForPublicNotice(Organisation organisation);

    List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation);

	SubLinkMaster getArchivedSections(long sectionId);

	List<LookUp> getArchiveSectionName(Organisation organisation, int langId);

	SubLinkMaster getArchivedSections(String sections);

	List<Long> getArchiveFielsId(long id);

}
