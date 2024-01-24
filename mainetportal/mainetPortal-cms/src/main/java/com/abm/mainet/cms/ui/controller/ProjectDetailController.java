package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.ProjectDetailModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.ApplicationSession;

/**
 * @author Manish.Gawali EIPHomeImagesSearch.html
 */
@Controller
@RequestMapping("/ProjectDetail.html")
public class ProjectDetailController extends AbstractEntryFormController<ProjectDetailModel> implements Serializable {
    private static final long serialVersionUID = 8707816701663698512L;

    @InitBinder
    public void init(final WebDataBinder webDataBinder) {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return super.index();
    }

    // ----Check Project Info-----
    @Override
    public ModelAndView addForm(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        if (!getModel().checkMaxRows()) {
            sessionCleanup(httpServletRequest);
            getModel().addForm();
        } else {
            getModel().addValidationError(getFieldLabel("MaxLimitError"));
            return new ModelAndView("ProjectValidator");
        }

        return super.defaultResult();
    }

    private String getFieldLabel(final String field) {
        return ApplicationSession.getInstance().getMessage("AboutProject" + MainetConstants.operator.DOT + field,
                new Object[] { getFieldLabel2("limit") });
    }

    private String getFieldLabel2(final String field) {
        return ApplicationSession.getInstance().getMessage("AboutProject" + MainetConstants.operator.DOT + field);
    }

}
