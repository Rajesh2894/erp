/**
 *
 */
package com.abm.mainet.cfc.checklist.ui.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.dto.CheckListVerificationReportParentDTO;
import com.abm.mainet.cfc.checklist.service.ICheckListVerificationReportService;
import com.abm.mainet.cfc.checklist.ui.model.ChecklistVerificationSearchModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Vinay.Jangir
 *
 */

@Controller
@RequestMapping({ "/ChecklistVerification.html", "/checkListVerification.html" })
public class ChecklistVerificationSearchController extends AbstractEntryFormController<ChecklistVerificationSearchModel> {

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    ICheckListVerificationReportService iCheckListVerificationReportService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUploadService.sessionCleanUpForFileUpload();

        return super.index();

    }

    @RequestMapping(params = "PrintChecklistReport")
    public ModelAndView printRejAppReport(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse)
            throws IOException {
        bindModel(httpServletRequest);
        final ChecklistVerificationSearchModel model = getModel();
        return printCheckListRejectReport(model, httpServletRequest, httpServletResponse);
    }

    private ModelAndView printCheckListRejectReport(final ChecklistVerificationSearchModel model,
            final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        final List<CheckListVerificationReportParentDTO> dtolist = iCheckListVerificationReportService.getRejectedAppList(model,
                model.getCheckListReportEntity(), UserSession.getCurrent().getOrganisation());
        
        
        httpServletRequest.setAttribute("dto", dtolist.get(0));
        httpServletRequest.setAttribute("logo",  UserSession.getCurrent().getOrgLogoPath());
        httpServletRequest.setAttribute("sign",  UserSession.getCurrent().getEmployee().getScansignature());
		String reportName = "";
		if (model.getStatusVariable().equalsIgnoreCase(MainetConstants.FlagR)) {

			reportName = "RejectionLetter";
		} else if (model.getStatusVariable().equalsIgnoreCase(MainetConstants.FlagH)) {
			reportName = "QueryLetter";
		}
        if (MainetConstants.Common_Constant.YES.equals(getModel().getRedirectFlag())) {
            getModel().setRedirectURL("AdminHome.html");
        } else {
            getModel().setRedirectURL("ChecklistVerificationSearch.html");
        }
        
        return new ModelAndView(reportName, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView showDetails(final HttpServletRequest httpServletRequest,
            @RequestParam("appNo") final Long applicationId, @RequestParam("actualTaskId") final long actualTaskId) {
        setData( httpServletRequest,  applicationId,  actualTaskId);
        getModel().setStatusVariable(null);
        return new ModelAndView("ChecklistVerificationSearch", MainetConstants.CommonConstants.COMMAND, getModel());
    }
    
    //#106212
	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest)  {	
		setData( httpServletRequest,  Long.valueOf(applicationId),  taskId);
		return new ModelAndView("ChecklistVerificationViewDetails", MainetConstants.CommonConstants.COMMAND, getModel());
	}
	
	//#106212
	public void setData(HttpServletRequest httpServletRequest, Long applicationId, long actualTaskId) {
		bindModel(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		getModel().setRedirectFlag(MainetConstants.Common_Constant.YES);
		getModel().setTaskId(actualTaskId);
		getModel().editForm(applicationId);
		setDMSPath();
	}

}
