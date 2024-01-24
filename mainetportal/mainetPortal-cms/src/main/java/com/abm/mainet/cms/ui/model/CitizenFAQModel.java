package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestionsHistory;
import com.abm.mainet.cms.service.IFrequentlyAskedQuestionsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = "session")
public class CitizenFAQModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -7107223763449572243L;

    private List<FrequentlyAskedQuestions> faqList;

    private List<FrequentlyAskedQuestionsHistory> faqHistList;
    
    @Autowired
    private IFrequentlyAskedQuestionsService iFrequentlyAskedQuestionsService;

 

    public List<FrequentlyAskedQuestions> getFaqList() {
		return faqList;
	}

	public void setFaqList(List<FrequentlyAskedQuestions> faqList1) {
		this.faqList = faqList1;
	}



	public List<FrequentlyAskedQuestionsHistory> getFaqHistList() {
		return faqHistList;
	}

	public void setFaqHistList(List<FrequentlyAskedQuestionsHistory> faqHistList) {
		this.faqHistList = faqHistList;
	}

	public IFrequentlyAskedQuestionsService getiFrequentlyAskedQuestionsService() {
        return iFrequentlyAskedQuestionsService;
    }

    public void setiFrequentlyAskedQuestionsService(
            final IFrequentlyAskedQuestionsService iFrequentlyAskedQuestionsService) {
        this.iFrequentlyAskedQuestionsService = iFrequentlyAskedQuestionsService;
    }

    public void prepareFAQList() {
        faqHistList = iFrequentlyAskedQuestionsService.getGuestFAQs(getUserSession().getOrganisation(),
                MainetConstants.IsDeleted.NOT_DELETE);
    }

    public void emptyGrid() {
        if (getFaqList() != null) {
            getFaqList().clear();
        }
    }

    @Override
    public String getActiveClass() {
        return "faq";
    }

}
