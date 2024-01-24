package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestionsHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IFrequentlyAskedQuestionsDAO {

    public abstract List<FrequentlyAskedQuestions> getFAQs(Organisation organisation,
            String isDeleted);

    public abstract List<FrequentlyAskedQuestionsHistory> getGuestFAQs(Organisation organisation,
            String isDeleted);

    public abstract FrequentlyAskedQuestions getgetFAQById(long faqId, Organisation organisation);

    public abstract boolean saveOrUpdateAdminFAQ(FrequentlyAskedQuestions frequentlyAskedQuestions, String chekkerflag);


    public abstract List<FrequentlyAskedQuestions> getAdminFAQList(
            FrequentlyAskedQuestions frequentlyAskedQuestions, Organisation organisation);

    public abstract List<FrequentlyAskedQuestions> getAllAdminFAQList(Organisation organisation, String flag);

}