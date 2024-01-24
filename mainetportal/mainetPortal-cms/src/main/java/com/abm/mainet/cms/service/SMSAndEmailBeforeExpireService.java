package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cms.dao.ISMSAndEmailBeforeExpireDao;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

@Service
public class SMSAndEmailBeforeExpireService implements ISMSAndEmailBeforeExpireService {

    @Autowired
    private ISMSAndEmailBeforeExpireDao emailBeforeExpireDao;

    public void invokeSMSAndEmail(QuartzSchedulerMaster master, List<Object> parameterList) {
    	
    	List<PublicNotices> notices=emailBeforeExpireDao.getArchivedDataForPublicNotice(master.getOrgId());
    	if(notices!=null && notices.size()>0) {
    	for(PublicNotices noticeObj:notices) {
    		Date d1=noticeObj.getValidityDate();
    		Date d2=new Date();
    		 long diff = d1.getTime() -  d2.getTime();
    		  long  diffDate =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    		  if((diffDate+1)==Long.valueOf(ApplicationSession.getInstance().getMessage("noOfDaysForExpire"))) {
    		 Utility.sendSmsAndEmailBeforeExpire(ApplicationSession.getInstance().getMessage("PublicNotice")+" "+(master.getLangId()==1?noticeObj.getNoticeSubEn():noticeObj.getNoticeSubReg()),master.getOrgId(),master.getLangId());
    		  }
    	}
    	}
    	List<String> sectionList=emailBeforeExpireDao.getArchiveSectionName(master.getOrgId(),master.getLangId());
    	if(sectionList!=null && sectionList.size()>0) {
    		for(String section:sectionList) {
    			SubLinkMaster linkMaster=emailBeforeExpireDao.getArchivedSections(section);
    			if(linkMaster!=null) {
    			List<SubLinkFieldDetailsHistory> detailsHistories=linkMaster.getDetailsHistories();
    			if(detailsHistories!=null && detailsHistories.size()>0) {
    			for(SubLinkFieldDetailsHistory fieldDetailsHistory:detailsHistories) {
    	    		Date d1=fieldDetailsHistory.getValidityDate();
    	    		Date d2=new Date();
    	    		 long diff = d1.getTime() -  d2.getTime();
    	    		  long  diffDate =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    	    		  if((diffDate+1)==Long.valueOf(ApplicationSession.getInstance().getMessage("noOfDaysForExpire"))) {
    	    		 Utility.sendSmsAndEmailBeforeExpire(ApplicationSession.getInstance().getMessage("News")+" "+(master.getLangId()==1?linkMaster.getSubLinkNameEn():linkMaster.getSubLinkNameRg()),master.getOrgId(),master.getLangId());
    	    		  }
    	    	}
    			}
    			}
    		}
    	}
    	
    	List<EIPAnnouncementHistory> announcement=emailBeforeExpireDao.getGuestHomeEIPAnnouncement(master.getOrgId());
    	if(announcement!=null && announcement.size()>0) {
    	for(EIPAnnouncementHistory announcementObj:announcement) {
    		Date d1=announcementObj.getValidityDate();
    		Date d2=new Date();
    		 long diff = d1.getTime() -  d2.getTime();
    		  long  diffDate =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    		  if((diffDate+1)==Long.valueOf(ApplicationSession.getInstance().getMessage("noOfDaysForExpire"))) {
    		 Utility.sendSmsAndEmailBeforeExpire(ApplicationSession.getInstance().getMessage("dashboard.sections")+" "+(master.getLangId()==1?announcementObj.getAnnounceDescEng():announcementObj.getAnnounceDescReg()),master.getOrgId(),master.getLangId());
    		  }
    	}
    	}
    	
    }
}
