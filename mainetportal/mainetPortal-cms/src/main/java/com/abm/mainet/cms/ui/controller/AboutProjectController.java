package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.cms.ui.model.AboutProjectModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.util.UserSession;

/**
 * @author manish.gawali
 */
@Controller
@RequestMapping("/AboutProject.html")
public class AboutProjectController extends AbstractFormController<AboutProjectModel> {
	@Autowired
    private IEntitlementService iEntitlementService;
	
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().setCommonHelpDocs("AboutProject.html");
        getModel().emptyGrid();
        return super.index();
    }

    @RequestMapping(params = "ABOUT_PROJECT_LIST_PEN", produces = MainetConstants.URL_EVENT.JSON_APP)
    public @ResponseBody JQGridResponse<AboutProject> getAboutProjectPen(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "";
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateAboutProjectList(Flag));
    }

    @RequestMapping(params = "ABOUT_PROJECT_LIST_APP", produces = MainetConstants.URL_EVENT.JSON_APP)
    public @ResponseBody JQGridResponse<AboutProject> getAboutProjectApp(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateAboutProjectList(Flag));
    }

    @RequestMapping(params = "ABOUT_PROJECT_LIST_REJ", produces = MainetConstants.URL_EVENT.JSON_APP)
    public @ResponseBody JQGridResponse<AboutProject> getAboutProjectRej(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "N";
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateAboutProjectList(Flag));
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

    @RequestMapping(params = "AdminFaqReviewer", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView reviewer(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().setMakkerchekkerflag(MainetConstants.NEC.REVIEWER);
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

}
