package com.abm.mainet.cms.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.dto.NewsLetterSubscriptionDTO;
import com.abm.mainet.cms.service.INewsLetterSubscriptionService;
import com.abm.mainet.cms.ui.model.NewsLetterSubscriptionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/NewsLetterSubscription.html")
public class NewsLetterSubscriptionController extends AbstractFormController<NewsLetterSubscriptionModel> {

    @Autowired
    private INewsLetterSubscriptionService subscriptionService;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        return index();

    }

    @RequestMapping(params = "subscribe", method = RequestMethod.POST)
    public ModelAndView subscribe(HttpServletRequest request) {
        this.bindModel(request);
        NewsLetterSubscriptionDTO subscription = this.getModel().getSubscription();
        subscription.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        subscription.setSubscriptionStartDate(new Date());
        subscription.setStatus(MainetConstants.IsLookUp.STATUS.YES);
        boolean result = subscriptionService.validateSubscriber(subscription);
        if (result) {
            subscriptionService.subscribeNewsLetter(subscription);
        } else {
            return jsonResult(JsonViewObject
                    .successResult(getApplicationSession()
                            .getMessage("portal.newssub.validate")));
        }

        return index();

    }

}
