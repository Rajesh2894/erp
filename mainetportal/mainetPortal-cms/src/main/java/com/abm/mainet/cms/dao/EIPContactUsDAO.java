package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.cms.domain.EipUserContactUsHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Bhavesh.Gadhe
 */
@Repository
public class EIPContactUsDAO extends AbstractDAO<EIPContactUs> implements IEIPContactUsDAO {

    @Autowired
    private AuditService auditService;

    @Override
    public List<EIPContactUsHistory> getContactUsorg(final Organisation organisation) {
        List<EIPContactUsHistory> eipContactUsHistroys = new ArrayList<>();
        final Query query = createQuery(
                "select e.contactUsId from EIPContactUs e where e.orgId = ?1 and e.isDeleted = ?2 and e.flag = ?3 and e.chekkerflag='Y' order by e.sequenceNo asc");
        query.setParameter(1, organisation);
        query.setParameter(2, "N");
        query.setParameter(3, "S");
        @SuppressWarnings("unchecked")
        final List<Long> contactUsList = query.getResultList();
        if (contactUsList != null && !contactUsList.isEmpty()) {
            final Query query2 = createQuery(
                    "select h from EIPContactUsHistory h where h.chekkerflag='Y' and h.contactUsId in ?1 and (h.contactUsId,h.updatedDate) in (select eh.contactUsId,max(eh.updatedDate) from EIPContactUsHistory eh where eh.chekkerflag='Y' and eh.contactUsId in ?1 group by eh.contactUsId) order by h.sequenceNo asc, h.updatedDate desc");
            query2.setParameter(1, contactUsList);
            eipContactUsHistroys = query2.getResultList();
        }
        return eipContactUsHistroys;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPContactUsDAO#saveOrUpdateContactUs(com.abm.mainet.eip.domain.core.EIPContactUs)
     */
    @Override
    public void saveOrUpdateContactUs(EIPContactUs contactUs) {

        EIPContactUsHistory contactUsHistory = new EIPContactUsHistory();

        if (contactUs.getContactUsId() == 0L) {
            contactUsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            entityManager.persist(contactUs);
        } else {
            if (contactUs.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                contactUsHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
            } else {
                contactUsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }
            if (contactUs.getChekkerflag1() != null && contactUs.getChekkerflag1().equalsIgnoreCase("Y")) {
                contactUs = entityManager.merge(contactUs);
            } else {
                contactUs.setChekkerflag(null);
                contactUs = entityManager.merge(contactUs);
            }
        }
        auditService.createHistory(contactUs, contactUsHistory);

    }

    /*
     * Citizen EIPUserContact US
     */
    @Override
    public void saveEIPUserContactus(final EipUserContactUs eipUserContactUs) {
        entityManager.persist(eipUserContactUs);

        EipUserContactUsHistory contactUsHistory = new EipUserContactUsHistory();
        contactUsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(eipUserContactUs, contactUsHistory);

    }

    @Override
    public List<EIPContactUs> getContactList(final Organisation organisation, String flag) {

        final StringBuilder queryAppender = new StringBuilder(
                "select e from EIPContactUs e where e.orgId = ?1 and e.isDeleted = ?2 ");
        if (flag != null) {
            queryAppender
                    .append("and e.chekkerflag =?3");
        } else {
            queryAppender
                    .append("and e.chekkerflag is null");
        }
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        if (flag != null) {
            query.setParameter(3, flag);
        }
        final List<EIPContactUs> list = query.getResultList();

        return list;
    }

    @Override
    public List<EIPContactUs> getContactListchekker(final Organisation organisation) {
        final Query query = createQuery(
                "select e from EIPContactUs e where e.orgId = ?1 and e.isDeleted = ?2 and e.chekkerflag='Y' ");
        query.setParameter(1, organisation);
        query.setParameter(2, "N");
        @SuppressWarnings("unchecked")
        final List<EIPContactUs> list = query.getResultList();
        return list;

    }

    @Override
    public EIPContactUs editContactInfoDetails(final long rowId, final Organisation organisation) {

        final Query query = createQuery("select e from EIPContactUs e where e.orgId = ?1 and e.contactUsId = ?2");
        query.setParameter(1, organisation);
        query.setParameter(2, rowId);
        @SuppressWarnings("unchecked")
        final List<EIPContactUs> eipContactUs = query.getResultList();
        if ((eipContactUs == null) || eipContactUs.isEmpty()) {
            return null;
        } else {
            return eipContactUs.get(0);
        }
    }

    @Override
    public boolean hasSequenceExist(final String flag, final Long sequenceNo, final Organisation organisation) {

        boolean saveFlag = false;

        final Query query = createQuery(
                "select e from EIPContactUs e where e.flag = ?1 and e.sequenceNo = ?2 and e.isDeleted = ?3 and e.orgId = ?4 ");
        query.setParameter(1, flag);
        query.setParameter(2, sequenceNo);
        query.setParameter(3, "N");
        query.setParameter(4, organisation);

        @SuppressWarnings("unchecked")
        final List<EIPContactUs> eipContactUsList = query.getResultList();

        if ((eipContactUsList == null) || eipContactUsList.isEmpty()) {
            return saveFlag;
        } else {
            saveFlag = true;
            return saveFlag;
        }
    }

	@Override
    public boolean hasSequenceExist(final String flag, final Double sequenceNo, final Organisation organisation) {

        boolean saveFlag = false;

        final Query query = createQuery(
                "select e from EIPContactUs e where e.flag = ?1 and e.sequenceNo = ?2 and e.isDeleted = ?3 and e.orgId = ?4 ");
        query.setParameter(1, flag);
        query.setParameter(2, sequenceNo);
        query.setParameter(3, "N");
        query.setParameter(4, organisation);

        @SuppressWarnings("unchecked")
        final List<EIPContactUs> eipContactUsList = query.getResultList();

        if ((eipContactUsList == null) || eipContactUsList.isEmpty()) {
            return saveFlag;
        } else {
            saveFlag = true;
            return saveFlag;
        }
    }

    @Override
    public List<EIPContactUsHistory> getContactUsListBy(final Organisation organisation) {
        List<EIPContactUsHistory> eipContactUsHistories = new ArrayList<>();
        final Query query = createQuery(
                "select e.contactUsId from EIPContactUs e where e.flag = ?1 and e.isDeleted = ?2 and e.orgId = ?3 and e.chekkerflag='Y' order by e.sequenceNo asc");
        query.setParameter(1, "P");
        query.setParameter(2, "N");
        query.setParameter(3, organisation);

        final List<Long> contactUsList = query.getResultList();
        if (contactUsList != null && !contactUsList.isEmpty()) {
            final Query query2 = createQuery(
                    "select h from EIPContactUsHistory h where h.chekkerflag='Y' and h.contactUsId in ?1 and (h.contactUsId,h.updatedDate) in (select eh.contactUsId,max(eh.updatedDate) from EIPContactUsHistory eh where eh.chekkerflag='Y' and eh.contactUsId in ?1 group by eh.contactUsId) order by h.sequenceNo asc, h.updatedDate desc");
            query2.setParameter(1, contactUsList);
            eipContactUsHistories = query2.getResultList();
        }
        return eipContactUsHistories;
    }

    @Override
    public List<EIPContactUsHistory> getAllContactUsListByOrganisation(final Organisation organisation) {
        List<EIPContactUsHistory> eipContactUsHistories = new ArrayList<>();
        final Query query = createQuery(
                "select e.contactUsId from EIPContactUs e where e.flag in ?1 and e.isDeleted = ?2 and e.orgId = ?3 order by e.flag asc");
        query.setParameter(1, Arrays.asList("P", "S"));
        query.setParameter(2, "N");
        query.setParameter(3, organisation);

        final List<Long> contactUsList = query.getResultList();
        if (contactUsList != null && !contactUsList.isEmpty()) {
            final Query query2 = createQuery(
            		"select h from EIPContactUsHistory h where h.chekkerflag='Y' and h.contactUsId in ?1 and (h.contactUsId,h.updatedDate) in (select eh.contactUsId,max(eh.updatedDate) from EIPContactUsHistory eh where eh.chekkerflag='Y' and eh.contactUsId in ?1 group by eh.contactUsId) order by h.flag asc , h.sequenceNo asc, h.updatedDate desc ");
                query2.setParameter(1, contactUsList);
            eipContactUsHistories = query2.getResultList();
        }
        return eipContactUsHistories;
    }
    
    @Override
    public List<EIPContactUs> getContactListByOrganisation(final Organisation organisation) {

        final StringBuilder queryAppender = new StringBuilder(
                "select e from EIPContactUs e where e.orgId = ?1 order by contactUsId desc");
       
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
       
        final List<EIPContactUs> list = query.getResultList();

        return list;
    }

}
