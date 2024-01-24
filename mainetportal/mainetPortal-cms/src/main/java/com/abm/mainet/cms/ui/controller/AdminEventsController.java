package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.cms.ui.model.AdminEventsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;

/**
 * @author swapnil.shirke
 * @see This Controller for listing all events records in grid {@link : News}
 */
@Controller
@RequestMapping("/AdminEvents.html")
public class AdminEventsController extends AbstractFormController<AdminEventsModel> implements Serializable {

    private static final long serialVersionUID = 1783105307588892004L;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        return super.index();
    }

    @RequestMapping(params = "Event_LIST", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<News> getEventList(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        return getModel().paginate(httpServletRequest, page, rows, getModel().generateEventList());
    }

}
