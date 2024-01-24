package com.abm.mainet.cms.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.dto.ThemeMasterDTO;
import com.abm.mainet.cms.service.IThemeMasterService;
import com.abm.mainet.cms.ui.model.ThemeMasterFormModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/ThemeMaster.html")
public class ThemeMasterController extends AbstractFormController<ThemeMasterFormModel> {

    @Autowired
    private IThemeMasterService themeMasterService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("ThemeMaster.html");
        this.getModel().setThemeMasterDTOList(
                themeMasterService.getThemeMasterByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        final ThemeMasterFormModel model = getModel();

        validateForm(model.getThemeMasterDTOList());
        boolean result = themeMasterService.saveThemeMaster(model.getThemeMasterDTOList(),
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getEmployee().getEmpId());
        if (result) {
            return jsonResult(JsonViewObject
                    .successResult(getApplicationSession().getMessage("admin.update.successmsg")));
        }

        return defaultMyResult();
    }

    enum ThemeItems {
        MENU, LOGO, FOOTER, GIS_MAP, PUBLIC_NOTICE, COMMITTEE_MEMBERS, KEY_INITIATIVES, TOURISM, NEWS, PHOTO_GALLERY, VIDEO_GALLERY, RTI
    }

    enum ThemePosition {
        TOP, LEFT, RIGHT, ACTIVE, INACTIVE
    }

    private void validateForm(List<ThemeMasterDTO> themeMasterList) {
        // TODO : WRITE IMPLEMENTATIOMN IF REQUIRED
    }

}
