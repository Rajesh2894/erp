/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestionsHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IFrequentlyAskedQuestionsService extends Serializable {
    public List<FrequentlyAskedQuestions> getFAQs(Organisation organisation, String isDeleted);

    public List<FrequentlyAskedQuestionsHistory> getGuestFAQs(Organisation organisation, String isDeleted);

    public FrequentlyAskedQuestions getFAQById(long faqId, Organisation organisation);

    public boolean saveOrUpdateAdminFAQ(FrequentlyAskedQuestions frequentlyAskedQuestions, String chekkerflag);

    public List<FrequentlyAskedQuestions> getAllAdminFAQList(Organisation organisation, String flag);

    public void deleteAdminFAQ(FrequentlyAskedQuestions frequentlyAskedQuestions, String checkerFlag);

}
