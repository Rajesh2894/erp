package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.SiteMapModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;

/**
 *
 * @author Jugnu.pandey
 *
 */
@Controller
@RequestMapping("/sitemap.html")
public class SiteMapController extends AbstractFormController<SiteMapModel> {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {

        sessionCleanup(request);
        final SiteMapModel model = getModel();
        
            return new ModelAndView("SiteMapLogin", MainetConstants.FORM_NAME, model); 
       
    }

}
