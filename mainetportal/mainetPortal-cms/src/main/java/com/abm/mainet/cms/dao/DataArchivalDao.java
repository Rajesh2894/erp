package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Primary;
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
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Repository
@Primary
public class DataArchivalDao extends AbstractDAO<PublicNotices> implements IDataArchivalDao {
    private static final Logger LOG = Logger.getLogger(DataArchivalDao.class);

    @Override
    public List<PublicNotices> getArchivedDataForPublicNotice(Organisation organisation) {
        final Query query = createQuery(
                "select p from PublicNotices p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate < ?4 order by p.validityDate desc");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, MainetConstants.Common_Constant.NUMBER.ONE);
        
		/* final Calendar calender = Calendar.getInstance();
		 * calender.set(Calendar.MINUTE, 0); calender.set(Calendar.MILLISECOND, 0);
		 * calender.set(Calendar.HOUR_OF_DAY, 0); calender.set(Calendar.SECOND, 0);
		 * query.setParameter(4, calender.getTime());
		 */
        query.setParameter(4, new Date());
        @SuppressWarnings("unchecked")
        final List<PublicNotices> noticeIdList = query.getResultList();
       
        return noticeIdList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SubLinkMaster getArchivedSections(long sectionId) {
        String subLinkName = null;
        try {

        	 final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                     " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.id=:subLinkId order by subLinkMaster.id asc");
            query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
            query.setParameter("active", "N");
            query.setParameter("subLinkId", sectionId);
            final SubLinkMaster subLinkMaster = (SubLinkMaster) query.getSingleResult();

            subLinkName = String.valueOf(subLinkMaster.getId());

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
                        "select h from SubLinkFieldDetails h where h.chekkerflag='Y' and h.id in ?1 ");

                if (subLinkName != null && subLinkName.equalsIgnoreCase(String.valueOf(sectionId))) {
                    queryAppender
                            .append("and h.validityDate < ?2 ");
                }
                queryAppender
                .append(" and h.isDeleted = ?3 ");
                queryAppender
                .append(" order by h.validityDate desc ");
                final Query query2 = createQuery(queryAppender.toString());
 
                query2.setParameter(1, subLinkDetailId);
               
                if (subLinkName != null && subLinkName.equalsIgnoreCase(String.valueOf(sectionId))) {
                    query2.setParameter(2, new Date());
                }
                query2.setParameter(3, MainetConstants.IsDeleted.NOT_DELETE);
                List<SubLinkFieldDetails> resultList = query2.getResultList();

                subLinkMaster.setSubLinkFieldDetails(resultList);
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
                    "select h from EIPAnnouncementHistory  h where h.chekkerflag='Y' and h.announceId in ?1 and h.validityDate < ?2  and (h.announceId,h.updatedDate) in (select eh.announceId,max(eh.updatedDate) from EIPAnnouncementHistory eh where eh.chekkerflag='Y' and eh.announceId in ?1 and eh.validityDate < ?2 group by eh.announceId) and h.isDeleted='N' order by h.validityDate desc");
            query2.setParameter(1, eipAnnouncementList);
            /*final Calendar calender = Calendar.getInstance();
            calender.set(Calendar.MINUTE, 0);
            calender.set(Calendar.MILLISECOND, 0);
            calender.set(Calendar.HOUR_OF_DAY, 0);
            calender.set(Calendar.SECOND, 0);
            query2.setParameter(2, calender.getTime());*/
            query2.setParameter(2, new Date());

            announcementHistories = query2.getResultList();
        }
        return announcementHistories;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<LookUp> getArchiveSectionName(Organisation organisation,int langId) {
		List<LookUp> sectionName=new ArrayList<>();
		LookUp lookUp=null; 
		  final Query query = createQuery("select subLinkMaster.id,subLinkMaster.subLinkNameEn,subLinkMaster.subLinkNameRg from SubLinkMaster subLinkMaster" +
                  " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.isArchive= :archive order by subLinkMaster.id asc");
          query.setParameter("orgId", organisation);
          query.setParameter("active", "N");
          query.setParameter("archive", "Y");
          final List<Object[]> subLinkMaster =query.getResultList();
          for(Object[] obj:subLinkMaster) {
        	  lookUp = new LookUp();
          if(langId==MainetConstants.ENGLISH) {
        	  lookUp.setLookUpId(Long.parseLong(obj[0].toString()));
        	  lookUp.setLookUpCode(obj[1].toString());
        	  sectionName.add(lookUp);
          }else {
        	  lookUp.setLookUpId(Long.parseLong(obj[0].toString()));
        	  lookUp.setLookUpCode(obj[2].toString());
        	  sectionName.add(lookUp); 
          }
          }
		return sectionName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getArchiveFielsId(long id) {
		final Query query=createQuery("select s.id from SubLinkFieldDetails s where s.subLinkMaster.id= :id and s.isDeleted= :isDeleted and s.chekkerflag= :checkerFlag and s.validityDate <= CURRENT_DATE");
		query.setParameter("id", id);
		query.setParameter("isDeleted", "N");
		query.setParameter("checkerFlag", "Y");
		 final List<Long> listId  =query.getResultList();
		return listId;
	}

	@Override
	public SubLinkMaster getArchivedSections(String sections) {
		// TODO Auto-generated method stub
		return null;
	}


}
