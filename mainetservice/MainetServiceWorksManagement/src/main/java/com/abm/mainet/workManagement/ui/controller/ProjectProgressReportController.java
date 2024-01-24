/**
 * 
 */
package com.abm.mainet.workManagement.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.service.ProjectProgressService;
import com.abm.mainet.workManagement.ui.model.ProjectProgressReportModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/ProjectProgressReport.html")
public class ProjectProgressReportController extends AbstractFormController<ProjectProgressReportModel> {

    /**
     * Used to default Project Progress Report Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_PROJPROGRESS, method = RequestMethod.POST)
    public ModelAndView viewAbstractreportSheet(
            @RequestParam(MainetConstants.WorksManagement.FROM_DATE) String fromDate,
            @RequestParam(MainetConstants.WorksManagement.TO_DATE) String toDate) {
        ProjectProgressReportModel model = this.getModel();
        this.getModel().setCommonHelpDocs("ProjectProgressReport.html");
        model.setFromDate(fromDate);
        model.setToDate(toDate);
        Date frmDate = Utility.stringToDate(fromDate);
        Date tDate = Utility.stringToDate(toDate);
        this.getModel()
                .setProjectProgressDto(ApplicationContextProvider.getApplicationContext().getBean(ProjectProgressService.class)
                        .getAllProjectProgressByDate(frmDate, tDate, UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.WorksManagement.PROJ_PROGRESS, MainetConstants.FORM_NAME,
                this.getModel());

    }

}
