package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.ui.model.EIPLogoImagesModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

@Controller
@RequestMapping("/EIPLogoImages.html")
public class EIPLogoImagesController extends AbstractEntryFormController<EIPLogoImagesModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        sessionCleanup(httpServletRequest);
        return super.index();
    }

    @Override
    public ModelAndView addForm(final HttpServletRequest httpServletRequest) {

        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        bindModel(httpServletRequest);
        final EIPLogoImagesModel eipLogoImagesModel = getModel();
        eipLogoImagesModel.setFlag(MainetConstants.FlagL);
        if (eipLogoImagesModel.checkMaxRows(MainetConstants.FlagL)) {
            sessionCleanup(httpServletRequest);
            eipLogoImagesModel.setEntity(new EIPHomeImages());
            eipLogoImagesModel.addForm();
        } else {
            eipLogoImagesModel.addValidationError(getFieldLabel(MainetConstants.HOMEMAX_IMAGE_ERROR));
            eipLogoImagesModel.addValidationError(ApplicationSession.getInstance().getMessage("eip.logo.max"));
            return new ModelAndView(MainetConstants.EIP_IMAGEVALIDATE_VIEW);
        }

        return new ModelAndView(MainetConstants.EIP_HOMEIMAGES_VIEW, MainetConstants.FORM_NAME, eipLogoImagesModel);
    }

    private String getFieldLabel(final String field) {
        return ApplicationSession.getInstance().getMessage(
                MainetConstants.EIP_HOMEIMAGES_VIEW + MainetConstants.operator.DOT + field,
                new Object[] { getFieldLabel2("maxlogoImages") });
    }

    private String getFieldLabel2(final String field) {
        return ApplicationSession.getInstance().getMessage("landingPage" + MainetConstants.operator.DOT + field);
    }

}
