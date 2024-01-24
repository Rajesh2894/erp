package com.abm.mainet.cms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.ui.model.CitizenFAQModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/CitizenFAQ.html")
public class CitizenFAQController extends AbstractFormController<CitizenFAQModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {

        sessionCleanup(request);
        final CitizenFAQModel model = getModel();
        model.prepareFAQList();
        final Employee employee = UserSession.getCurrent().getEmployee();

        if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else if (null != employee && null != employee.getLoggedIn()
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                       // USER
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else {
            return new ModelAndView("CitizenFAQLogin", MainetConstants.FORM_NAME, getModel()); // SITE MAP FOR LOGGED-IN
                                                                                               // USER
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "getFAQ")
    public ModelAndView showQuestionAndAnswer(@RequestParam final long faqId, final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        final CitizenFAQModel citizenFAQModel = getModel();

        citizenFAQModel.emptyGrid();

        final List<FrequentlyAskedQuestions> list = new ArrayList<>();
        list.add(citizenFAQModel.getiFrequentlyAskedQuestionsService().getFAQById(faqId,
                UserSession.getCurrent().getOrganisation()));

        citizenFAQModel.setFaqList(list);

        return defaultResult();
    }
}
