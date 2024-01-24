package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestionsHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

@Repository
public class FrequentlyAskedQuestionsDAO extends AbstractDAO<FrequentlyAskedQuestions> implements IFrequentlyAskedQuestionsDAO {

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#saveOrUpdateFAQ(com.abm.mainet.eip.domain.core.
     * FrequentlyAskedQuestions)
     */

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#getFAQs(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public List<FrequentlyAskedQuestions> getFAQs(final Organisation organisation, final String isDeleted) {
        final Query query = createQuery(
                "select f from FrequentlyAskedQuestions f where f.orgId = ?1 and f.isDeleted = ?2 order by f.faqId asc");
        query.setParameter(1, organisation);
        query.setParameter(2, isDeleted);
        @SuppressWarnings("unchecked")
        final List<FrequentlyAskedQuestions> faqs = query.getResultList();
        return faqs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#getFAQ(long, java.lang.String)
     */

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#getgetFAQById(long)
     */
    @Override
    public FrequentlyAskedQuestions getgetFAQById(final long faqId, final Organisation organisation) {
        final Query query = createQuery(
                "select f from FrequentlyAskedQuestions f where f.orgId = ?1 and f.isDeleted = ?2  and f.faqId =?3");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, faqId);
        @SuppressWarnings("unchecked")
        final List<FrequentlyAskedQuestions> faqs = query.getResultList();
        if ((faqs == null) || faqs.isEmpty()) {
            return null;
        } else {
            return faqs.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#saveOrUpdateAdminFAQ(com.abm.mainet.eip.domain.core.
     * FrequentlyAskedQuestions)
     */
    @Override
    public boolean saveOrUpdateAdminFAQ(FrequentlyAskedQuestions frequentlyAskedQuestions, String chekkerflag) {

        FrequentlyAskedQuestionsHistory questionsHistory = new FrequentlyAskedQuestionsHistory();

        if (frequentlyAskedQuestions.getFaqId() == 0L) {
            questionsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            entityManager.persist(frequentlyAskedQuestions);
        } else {
            if (frequentlyAskedQuestions.getIsDeleted().equalsIgnoreCase("Y")) {
                questionsHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
            } else {
                questionsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }
            if (chekkerflag.equalsIgnoreCase("Y")) {
                frequentlyAskedQuestions = entityManager.merge(frequentlyAskedQuestions);
            } else {
                frequentlyAskedQuestions.setChekkerflag(null);
                frequentlyAskedQuestions = entityManager.merge(frequentlyAskedQuestions);
            }

        }
        auditService.createHistory(frequentlyAskedQuestions, questionsHistory);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#getAdminFAQList(com.abm.mainet.eip.domain.core.
     * FrequentlyAskedQuestions, com.abm.mainet.domain.core.Organisation)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FrequentlyAskedQuestions> getAdminFAQList(final FrequentlyAskedQuestions frequentlyAskedQuestions,
            final Organisation organisation) {

        final Query query = createQuery("select f from FrequentlyAskedQuestions f where f.orgId = ?1 and f.isDeleted = ?2 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);

        final List<FrequentlyAskedQuestions> frequentlyAskedQuestionsList = query.getResultList();

        return frequentlyAskedQuestionsList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFrequentlyAskedQuestionsDAO#getAllAdminFAQList(com.abm.mainet.domain.core.Organisation)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FrequentlyAskedQuestions> getAllAdminFAQList(final Organisation organisation, String flag) {
        final StringBuilder queryAppender = new StringBuilder(
                "select f from FrequentlyAskedQuestions f where f.orgId = ?1 and f.isDeleted = ?2 ");
        if (flag != null) {
            queryAppender
                    .append("and f.chekkerflag =?3");
        } else {
            queryAppender
                    .append("and f.chekkerflag is null");
        }
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        if (flag != null) {
            query.setParameter(3, flag);
        }
        final List<FrequentlyAskedQuestions> frequentlyAskedQuestionsList = query.getResultList();

        return frequentlyAskedQuestionsList;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FrequentlyAskedQuestionsHistory> getGuestFAQs(
            final Organisation organisation, final String isDeleted) {
        List<FrequentlyAskedQuestionsHistory> faqs = new ArrayList<>();
        final Query query1 = createQuery(
                "select h.faqId from FrequentlyAskedQuestions h where h.orgId = ?1 and h.isDeleted =?2 ");
        query1.setParameter(1, organisation);
        query1.setParameter(2, isDeleted);
        List<Long> faqIdList = query1.getResultList();
        if (faqIdList != null && !faqIdList.isEmpty()) {
            final Query query2 = createQuery(
                    "select f from FrequentlyAskedQuestionsHistory f where f.chekkerflag='Y' and orgId=?1 and f.faqId in ?2 and (f.faqId,f.updatedDate) in (select h.faqId,max(h.updatedDate ) from FrequentlyAskedQuestionsHistory h where h.chekkerflag='Y' and orgid=?1 and h.faqId in ?2 group by h.faqId ) order by f.faqId asc");
            query2.setParameter(1, organisation);
            query2.setParameter(2, faqIdList);

            faqs = query2.getResultList();
        }
        return faqs;

    }

}
