package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.EipAnnouncementLandingFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

/**
 * /*@author rajdeep.sinha
 */

@Controller
@RequestMapping("/EipAnnouncementLandingForm.html")
public class EipAnnouncementLandingFormController extends AbstractEntryFormController<EipAnnouncementLandingFormModel> {
	@Autowired
    private IEntitlementService iEntitlementService;
	
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        sessionCleanup(httpServletRequest);
        return new ModelAndView("EipAnnouncementLanding", MainetConstants.FORM_NAME, new EipAnnouncementLandingFormModel());
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
