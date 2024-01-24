package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.AdminStandingCommitteeModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminStandingCommittee.html")
public class AdminStandingCommitteeController extends AbstractFormController<AdminStandingCommitteeModel>
        implements Serializable {
    private static final long serialVersionUID = 210039220967679968L;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        return super.index();
    }

    /*
     * @RequestMapping(params = "StandingCommitteeList", produces = MainetConstants.URL_EVENT.JSON_APP, method =
     * RequestMethod.POST) public @ResponseBody JQGridResponse<ProfileMaster> getCommitteeList(final HttpServletRequest
     * httpServletRequest,
     * @RequestParam final String page, @RequestParam final String rows) { return getModel().paginate(httpServletRequest, page,
     * rows, getModel().generateProfileMasterList()); }
     */
}
