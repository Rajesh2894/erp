package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.cms.ui.model.AdminHomeModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author pranit.mhatre
 */
@Controller
@RequestMapping("/AdminHome.html")
public class AdminHomeController extends AbstractEntryFormController<AdminHomeModel> {

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        setDefaultObject();

        return super.index();
    }

    @Override
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final AdminHomeModel model = getModel();

        if (model.saveOrUpdateForm()) {
            sessionCleanup(httpServletRequest);
            httpServletRequest.setAttribute("MSG_TEXT",
                    model.getAppSession().getMessage("Save.SUCCESS", new Object[] { "Information" }));

            setDefaultObject();

            return new ModelAndView(getViewName());
        }

        return super.defaultResult();

    }

    private void setDefaultObject() {
        EIPHome entity = getModel().getService().getObject();

        if (entity == null) {
            entity = new EIPHome();
        }

        getModel().setEntity(entity);
    }

}
