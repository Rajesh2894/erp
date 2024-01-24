package com.abm.mainet.cms.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.ui.model.SectionEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author Pranit.Mhatre
 * @since 17 February, 2014
 */
@Controller
@RequestMapping("/SectionEntry.html")
public class SectionEntryController extends AbstractFormController<SectionEntryModel> {
	@Autowired
    private IEntitlementService iEntitlementService;
	
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return super.index();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.URL_EVENT.SERACH)
    public ModelAndView searchSection(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        String flag = "Y";
        getModel().getSections(flag);
        return super.index();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "SEARCH_RESULT_PEN")
    public @ResponseBody JQGridResponse<SubLinkMaster> getSectionListPen(@RequestParam final String page,
            @RequestParam final String rows, final HttpServletRequest httpServletRequest) {
        String flag = null;
        return getModel().paginate(httpServletRequest, page, rows, getModel().getSections(flag));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "SEARCH_RESULT_APP")
    public @ResponseBody JQGridResponse<SubLinkMaster> getSectionListApp(@RequestParam final String page,
            @RequestParam final String rows, final HttpServletRequest httpServletRequest) {
        String flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows, getModel().getSections(flag));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "SEARCH_RESULT_REJ")
    public @ResponseBody JQGridResponse<SubLinkMaster> getSectionListRej(@RequestParam final String page,
            @RequestParam final String rows, final HttpServletRequest httpServletRequest) {
        String flag = "N";
        return getModel().paginate(httpServletRequest, page, rows, getModel().getSections(flag));
    }

    @RequestMapping(method = RequestMethod.POST, params = "function")
    public @ResponseBody List<LookUp> getFunction(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        return getModel().getFunctions();
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

    @RequestMapping(params = "AdminFaqCheker/search", method = { RequestMethod.POST })
    public ModelAndView chekkerSearch(final HttpServletRequest request) {
        bindModel(request);
        String flag = "Y";
        getModel().getSections(flag);        
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
