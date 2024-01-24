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

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.ui.model.AdminCommissionerModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminCommissioner.html")
public class AdminCommissionerController extends AbstractFormController<AdminCommissionerModel> implements Serializable {

    private static final long serialVersionUID = 210039220967679968L;
    @Autowired
    private IEntitlementService iEntitlementService;
    
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        /* getModel().gridSize(); */
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        return super.index();
    }

    @RequestMapping(params = "Commissioner_PEN", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<ProfileMaster> getCommissionerListPen(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = null;
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateProfileMasterList(Flag));
    }

    @RequestMapping(params = "Commissioner_APP", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<ProfileMaster> getCommissionerListApp(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateProfileMasterList(Flag));
    }

    @RequestMapping(params = "Commissioner_REJ", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<ProfileMaster> getCommissionerListRej(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        String Flag = "N";
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateProfileMasterList(Flag));
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
