package com.abm.mainet.common.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.ui.model.AbstractSearchFormModel;
import com.abm.mainet.common.ui.model.JQGridResponse;

public abstract class AbstractSearchFormController<TModel extends AbstractSearchFormModel<? extends Serializable>>
        extends AbstractFormController<TModel> {

    public AbstractSearchFormController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);

        return super.index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "search")
    public String search(final HttpServletRequest httpServletRequest) {
        getModel().search(httpServletRequest);
        return getViewName();
    }

    @RequestMapping(params = "SEARCH_RESULTS", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        return getModel().paginate(httpServletRequest, page, rows,
                getModel().getResults());
    }
}