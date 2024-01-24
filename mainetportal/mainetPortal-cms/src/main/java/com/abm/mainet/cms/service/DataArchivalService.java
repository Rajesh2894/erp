package com.abm.mainet.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IDataArchivalDao;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

@Service
public class DataArchivalService implements IDataArchivalService {

    @Autowired
    IDataArchivalDao dataArchivalDao;

    @Override
    public List<PublicNotices> getArchivedDataForPublicNotice(Organisation organisation) {
        return dataArchivalDao.getArchivedDataForPublicNotice(organisation);
    }

    @Override
    @Transactional
    public SubLinkMaster getArchivedSections(String sections) {
        return dataArchivalDao.getArchivedSections(sections);
    }

	 @Override
    @Transactional
    public SubLinkMaster getArchivedSections(long sectionId) {
        return dataArchivalDao.getArchivedSections(sectionId);
    }

    @Override
    public List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation) {
        return dataArchivalDao.getGuestHomeEIPAnnouncement(organisation);

    }

	/*
	 * @Override public List<String> getArchiveSectionName(Organisation
	 * organisation,int langId) { return
	 * dataArchivalDao.getArchiveSectionName(organisation,langId); }
	 */
	
	
	  @Override 
	  public List<LookUp> getArchiveSectionName(Organisation organisation,int langId) { 
		  return dataArchivalDao.getArchiveSectionName(organisation,langId); 
	}
	 
	

	@Override
	public List<Long> getArchiveFielsId(long id) {
		return dataArchivalDao.getArchiveFielsId(id);
	}


}
