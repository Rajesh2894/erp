package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.service.IFrequentlyAskedQuestionsService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;

/**
 * @author rajendra.bhujbal
 *
 */
@Component
@Scope("session")
public class AdminFAQSearchModel extends AbstractFormModel {

    String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    private static final long serialVersionUID = 4373230899535220754L;

    @Autowired
    private IFrequentlyAskedQuestionsService iFrequentlyAskedQuestionsService;

    public List<FrequentlyAskedQuestions> getSearchResults(String flag) {
        return iFrequentlyAskedQuestionsService.getAllAdminFAQList(UserSession.getCurrent().getOrganisation(), flag);
    }

    /*
     * @Override public List<FrequentlyAskedQuestions> getResults() { return
     * iFrequentlyAskedQuestionsService.getAllAdminFAQList(UserSession.getCurrent().getOrganisation()); }
     */
}
