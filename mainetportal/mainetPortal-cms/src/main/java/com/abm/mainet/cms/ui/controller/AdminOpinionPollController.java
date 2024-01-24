package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.ui.model.AdminOpinionPollModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.util.UserSession;

/**
 * @author ranjit.seth
 */
@Controller
@RequestMapping("/Opinionpoll.html")
public class AdminOpinionPollController extends AbstractFormController<AdminOpinionPollModel> implements Serializable {

    private static final long serialVersionUID = -99231006537502961L;
    @Autowired
    private IEntitlementService iEntitlementService;
    
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        getModel().setCommonHelpDocs("Opinionpoll.html");
        return super.index();
    }

    @RequestMapping(params = "SEARCH_RESULTS_PEN", produces = MainetConstants.URL_EVENT.JSON_APP)
    public @ResponseBody JQGridResponse<OpinionPoll> getOpinionPollPen(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = null;
        return getModel().paginate(httpServletRequest, page, rows, getModel().querySearchResults(Flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_APP", produces = MainetConstants.URL_EVENT.JSON_APP)
    public @ResponseBody JQGridResponse<OpinionPoll> getOpinionPollApp(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows, getModel().querySearchResults(Flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_REJ", produces = MainetConstants.URL_EVENT.JSON_APP)
    public @ResponseBody JQGridResponse<OpinionPoll> getOpinionPollRej(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "N";
        return getModel().paginate(httpServletRequest, page, rows, getModel().querySearchResults(Flag));
    }

    @RequestMapping(params = "AdminFaqCheker", method = { RequestMethod.POST, RequestMethod.GET })

    public ModelAndView chekker(final HttpServletRequest request) {
    	sessionCleanup(request);        
    	final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
    	if(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH)){ 
    		getModel().setMakkerchekkerflag(MainetConstants.NEC.CITIZEN);
    		return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    	}else {
    	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER, UserSession.getCurrent().getOrganisation().getOrgid());
    	if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
    		getModel().setMakkerchekkerflag(MainetConstants.NEC.CITIZEN);
    		return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    	} else {
    		return new ModelAndView("redirect:/LogOut.html");
    	}}
    }

}
