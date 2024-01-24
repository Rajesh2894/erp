package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.EipAnnouncementLandingModel;
import com.abm.mainet.common.ui.controller.AbstractSearchFormController;

@Controller
@RequestMapping("/EipAnnouncementLanding.html")
public class EipAnnouncementLandingController extends AbstractSearchFormController<EipAnnouncementLandingModel>
        implements Serializable {

    /**
     * @author rajdeep.sinha
     */
    private static final long serialVersionUID = 937132216359469945L;

    @Override
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        return super.index();
    }

}
