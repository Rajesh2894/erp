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

import com.abm.mainet.cms.ui.model.EIPHomeImagesSerachModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.util.UserSession;

/**
 * @author vinay.jangir
 *
 */
@Controller
@RequestMapping("/EIPHomeImagesSearch.html")
public class EIPHomeImagesSearchController extends AbstractEntryFormController<EIPHomeImagesSerachModel> {
	@Autowired
    private IEntitlementService iEntitlementService;
	
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView initial(final HttpServletRequest request) {
        sessionCleanup(request);
        return defaultResult();
    }

    @RequestMapping(params = "SEARCH_RESULTS_PEN", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geImageResultsPen(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        String flag = null;
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().querySearchResults(flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_APP", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geImageResultsApp(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        String flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().querySearchResults(flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_REJ", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geImageResultsRej(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        String flag = "N";
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().querySearchResults(flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_LOGO_PEN", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geLOGOResultsPen(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        String flag = null;
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().getLogoResults(flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_LOGO_APP", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geLOGOResultsApp(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        String flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().getLogoResults(flag));
    }

    @RequestMapping(params = "SEARCH_RESULTS_LOGO_REJ", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geLOGOResultsRej(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        String flag = "N";
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().getLogoResults(flag));
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
