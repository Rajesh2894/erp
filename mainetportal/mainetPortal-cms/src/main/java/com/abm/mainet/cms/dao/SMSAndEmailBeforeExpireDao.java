package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.UserSession;

@Repository
public class SMSAndEmailBeforeExpireDao extends AbstractDAO<PublicNotices> implements ISMSAndEmailBeforeExpireDao{

	 private static final Logger LOG = Logger.getLogger(DataArchivalDao.class);

	    @Override
	    public List<PublicNotices> getArchivedDataForPublicNotice(Organisation organisation) {
	        final Query query = createQuery(
	                "select p from PublicNotices p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate > ?4");
	        query.setParameter(1, organisation);
	        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
	        query.setParameter(3, MainetConstants.Common_Constant.NUMBER.ONE);
	        final Calendar calender = Calendar.getInstance();
	        calender.set(Calendar.MINUTE, 0);
	        calender.set(Calendar.MILLISECOND, 0);
	        calender.set(Calendar.HOUR_OF_DAY, 0);
	        calender.set(Calendar.SECOND, 0);
	        query.setParameter(4, calender.getTime());
	        @SuppressWarnings("unchecked")
	        final List<PublicNotices> noticeIdList = query.getResultList();
	       
	        return noticeIdList;
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public SubLinkMaster getArchivedSections(String sections) {
	        String subLinkName = null;
	        try {

	            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
	                    " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.subLinkNameEn=:subLinkNm order by subLinkMaster.id asc");
	            query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
	            query.setParameter("active", "N");
	            query.setParameter("subLinkNm", sections);
	            final SubLinkMaster subLinkMaster = (SubLinkMaster) query.getSingleResult();

	            subLinkName = subLinkMaster.getSubLinkNameEn();

	            final ListIterator<SubLinkFieldMapping> listIterator = subLinkMaster.getSubLinkFieldMappings().listIterator();
	            while (listIterator.hasNext()) {
	                listIterator.next();
	            }

	            List<Long> subLinkDetailId = new ArrayList<Long>();
	            for (SubLinkFieldDetails subLinkFieldDetails : subLinkMaster.getSubLinkFieldDetails()) {
	                subLinkDetailId.add(subLinkFieldDetails.getId());
	            }
	            if (subLinkDetailId != null && !subLinkDetailId.isEmpty()) {

	                final StringBuilder queryAppender = new StringBuilder(
	                        "select h from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' and h.id in ?1 ");

	                if (subLinkName != null && subLinkName.equalsIgnoreCase(sections)) {
	                    queryAppender
	                            .append("and h.validityDate > ?2 ");
	                }

	                queryAppender
	                        .append("and (h.hId) in (select max(hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 ");

	                if (subLinkName != null && subLinkName.equalsIgnoreCase(sections)) {
	                    queryAppender
	                            .append("and eh.validityDate > ?2 ");
	                }

	                queryAppender
	                        .append("group by eh.id) order by h.updatedDate desc ");

	                final Query query2 = createQuery(queryAppender.toString());

	                query2.setParameter(1, subLinkDetailId);

	                if (subLinkName != null && subLinkName.equalsIgnoreCase(sections)) {
	                    query2.setParameter(2, new Date());
	                }

	                List<SubLinkFieldDetailsHistory> resultList = query2.getResultList();

	                subLinkMaster.setDetailsHistories(resultList);
	            }
	            return subLinkMaster;
	        } catch (final Exception e) {
	            LOG.error(MainetConstants.FINDBYID_ERROR, e);

	            return null;
	        }

	    }

	    @SuppressWarnings("unchecked")
		@Override
	    public List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(Organisation organisation) {
	        List<EIPAnnouncementHistory> announcementHistories = new ArrayList<EIPAnnouncementHistory>();
	        final Query query = createQuery(
	                "select e.announceId from EIPAnnouncement e where e.isDeleted = ?1 and e.orgId = ?2");
	        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
	        query.setParameter(2, organisation);
	        final List<Long> eipAnnouncementList = query.getResultList();

	        if (eipAnnouncementList != null && !eipAnnouncementList.isEmpty()) {
	            final Query query2 = createQuery(
	                    "select h from EIPAnnouncementHistory  h where h.chekkerflag='Y' and h.announceId in ?1 and h.validityDate > ?2  and (h.announceId,h.updatedDate) in (select eh.announceId,max(eh.updatedDate) from EIPAnnouncementHistory eh where eh.chekkerflag='Y' and eh.announceId in ?1 and eh.validityDate > ?2 group by eh.announceId) order by h.newsDate desc");
	            query2.setParameter(1, eipAnnouncementList);
	            final Calendar calender = Calendar.getInstance();
	            calender.set(Calendar.MINUTE, 0);
	            calender.set(Calendar.MILLISECOND, 0);
	            calender.set(Calendar.HOUR_OF_DAY, 0);
	            calender.set(Calendar.SECOND, 0);
	            query2.setParameter(2, calender.getTime());

	            announcementHistories = query2.getResultList();
	        }
	        return announcementHistories;
	    }

		@SuppressWarnings("unchecked")
		@Override
		public List<String> getArchiveSectionName(Organisation organisation,int langId) {
			List<String> sectionName=new ArrayList<>();
			  final Query query = createQuery("select subLinkMaster.subLinkNameEn,subLinkMaster.subLinkNameRg from SubLinkMaster subLinkMaster" +
	                  " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.isArchive= :archive order by subLinkMaster.id asc");
	          query.setParameter("orgId", organisation);
	          query.setParameter("active", "N");
	          query.setParameter("archive", "Y");
	          final List<Object[]> subLinkMaster =query.getResultList();
	          for(Object[] obj:subLinkMaster) {
	          if(langId==MainetConstants.ENGLISH) {
	        	  sectionName.add(obj[0].toString());
	          }else {
	        	  sectionName.add(obj[1].toString()); 
	          }
	          }
			return sectionName;
		}
}
