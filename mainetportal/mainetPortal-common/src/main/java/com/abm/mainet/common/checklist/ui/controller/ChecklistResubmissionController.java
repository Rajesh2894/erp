package com.abm.mainet.common.checklist.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.checklist.ui.model.ChecklistResubmissionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.DOCUMENT_RESUBMISSION)
public class ChecklistResubmissionController extends AbstractFormController<ChecklistResubmissionModel> {

    private static final String NULL_OR_INVALID_APPLICATION_ID_RECEIVED = "Null or Invalid Application id Received.";

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        sessionCleanup(httpServletRequest);
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.URL_EVENT.SERACH)
    public ModelAndView search(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().querySearchResults();
        return index();
    }

    @RequestMapping(params = "applId", method = RequestMethod.POST)
    public ModelAndView resubmitApplication(@ModelAttribute("appId") final Long appid,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        if (null != appid) {
            getModel().setApplicationId(appid);
            getModel().setResubmitedApplication(true);
        } else {
            logger.error(NULL_OR_INVALID_APPLICATION_ID_RECEIVED);
        }
        getModel().querySearchResults();
        return index();

    }

}
