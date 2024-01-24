package com.abm.mainet.cfc.checklist.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.ui.model.ChecklistResubmissionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.FORM_URL.DOCUMENT_RESUBMISSION)
public class ChecklistResubmissionController extends AbstractEntryFormController<ChecklistResubmissionModel> {

    private static final String CHECKLIST_FORM = "ChecklistResubmissionForm";

    @Autowired
    private IFileUploadService fileUploadService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        fileUploadService.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("DocumentResubmission.html");
        sessionCleanUp(httpServletRequest);
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.URL_EVENT.SERACH)
    public ModelAndView search(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().querySearchResults();
        return index();
    }

    @RequestMapping(params = "applId", method = RequestMethod.GET)
    public ModelAndView resubmitApplication(@ModelAttribute("appId") final Long appid,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        if (null != appid) {
            getModel().setApplicationId(appid);
            getModel().setResubmitedApplication(true);
        } else {
            logger.error("Null or Invalid Application id Received.");
        }
        getModel().querySearchResults();
        return index();

    }

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView showDetails(final HttpServletRequest httpServletRequest,
            @RequestParam("appNo") final Long applicationId, @RequestParam("actualTaskId") final long actualTaskId) {
    	setData( httpServletRequest,  applicationId,  actualTaskId);
        return new ModelAndView(CHECKLIST_FORM, MainetConstants.CommonConstants.COMMAND, getModel());
    }
    
    //#106212
   	@Override
   	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
   	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
   			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
   			final HttpServletRequest httpServletRequest)  {	
   		setData( httpServletRequest,  Long.valueOf(applicationId),  taskId);
   		return new ModelAndView("ChecklistResubmissionViewDetails", MainetConstants.CommonConstants.COMMAND, getModel());
   	}

   	public void setData( HttpServletRequest httpServletRequest,Long applicationId,  long actualTaskId) {
   		bindModel(httpServletRequest);
        if (null != applicationId) {
            getModel().setApplicationId(applicationId);
            getModel().setResubmitedApplication(true);
            getModel().setTaskId(actualTaskId);
        } else {
            logger.error("Null or Invalid Application id Received.");
        }
        getModel().querySearchResults();
   	}
}
