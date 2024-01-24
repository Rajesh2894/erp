package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.service.INewsLetterSubscriptionService;
import com.abm.mainet.cms.service.ISectionService;

@Controller
@RequestMapping("/NewsLetter.html")
public class NewsLetterController {

    @Autowired
    private INewsLetterSubscriptionService subscriptionService;

    @Autowired
    private ISectionService sectionService;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("NewsLetter");
    }

    @RequestMapping(params = "Newsletter", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView getNewsLetter(HttpServletRequest request, @RequestParam("newsId") Long newsLetterId,
            @RequestParam("subscriberId") Long subscriberId, @RequestParam("langId") Long languageId) {
        subscriptionService.updateViewDate(subscriberId);
        ModelAndView modelAndView = new ModelAndView("NewsLetter");
        modelAndView.addObject("newsLetter", sectionService.getCurrentNewsLetter(newsLetterId, languageId));
        return modelAndView;

    }

    @RequestMapping(params = "Unsubscribe", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView unsubscribeNewsLetter(HttpServletRequest request, @RequestParam("subscriberId") Long subscriberId) {
        subscriptionService.unSubscribeNewsLetter(subscriberId);
        return new ModelAndView("Unsubscribe");

    }

}
