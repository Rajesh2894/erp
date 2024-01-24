package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.domain.Organisation;

public interface ISMSAndEmailBeforeExpireDao {

	   List<PublicNotices> getArchivedDataForPublicNotice(Organisation organisation);

	    List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation);

		SubLinkMaster getArchivedSections(String sections);

		List<String> getArchiveSectionName(Organisation organisation, int langId);
}
