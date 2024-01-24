/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IFrequentlyAskedQuestionsDAO;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestionsHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;

@Service
public class FrequentlyAskedQuestionsService implements IFrequentlyAskedQuestionsService {
    /**
     *
     */
    private static final long serialVersionUID = -6345245588678307720L;

    @Autowired
    private IFrequentlyAskedQuestionsDAO faqDAO;

    @Override
    @Transactional
    public List<FrequentlyAskedQuestions> getFAQs(final Organisation organisation, final String isDeleted) {
        return faqDAO.getFAQs(organisation, isDeleted);
    }

    @Override
    @Transactional
    public FrequentlyAskedQuestions getFAQById(final long faqId, final Organisation organisation) {

        return faqDAO.getgetFAQById(faqId, organisation);
    }

    @Override
    @Transactional
    public boolean saveOrUpdateAdminFAQ(final FrequentlyAskedQuestions frequentlyAskedQuestions, String chekkerflag) {

        frequentlyAskedQuestions.updateAuditFields();
        frequentlyAskedQuestions.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);

        return faqDAO.saveOrUpdateAdminFAQ(frequentlyAskedQuestions, chekkerflag);
    }

    @Transactional
    public List<FrequentlyAskedQuestions> getAdminFAQList(
            final FrequentlyAskedQuestions frequentlyAskedQuestions,
            final Organisation organisation) {

        return faqDAO.getAdminFAQList(frequentlyAskedQuestions, organisation);
    }

    @Override
    @Transactional
    public List<FrequentlyAskedQuestions> getAllAdminFAQList(final Organisation organisation, String flag) {

        return faqDAO.getAllAdminFAQList(organisation, flag);
    }

    @Override
    @Transactional
    public void deleteAdminFAQ(final FrequentlyAskedQuestions frequentlyAskedQuestions, String checkerFlag) {
        faqDAO.saveOrUpdateAdminFAQ(frequentlyAskedQuestions, checkerFlag);
    }

    @Override
    public List<FrequentlyAskedQuestionsHistory> getGuestFAQs(
            final Organisation organisation, final String isDeleted) {

        return faqDAO.getGuestFAQs(organisation, isDeleted);
    }
}
