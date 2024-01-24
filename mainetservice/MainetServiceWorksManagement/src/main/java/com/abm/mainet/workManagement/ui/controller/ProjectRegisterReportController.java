package com.abm.mainet.workManagement.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.service.SchemeMasterService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorksProjectRegisterReportService;
import com.abm.mainet.workManagement.ui.model.ProjectRegisterReportModel;

@Controller
@RequestMapping("/ProjectRegisterReport.html")
public class ProjectRegisterReportController extends AbstractFormController<ProjectRegisterReportModel> {

    @Autowired
    private SchemeMasterService schemeMasterService;

    @Autowired
    private WmsProjectMasterService wmsProjectMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final ProjectRegisterReportModel model = getModel();
        this.getModel().setCommonHelpDocs("ProjectRegisterReport.html");
        ModelAndView mv = null;
        model.setSchemeMasterList(schemeMasterService.getSchemeMasterList(null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<WmsProjectMasterDto> obj1 = wmsProjectMasterService.getActiveProjectMasterListWithoutSchemeByOrgId(orgId);
        this.getModel().setProjectMasterList(obj1);
        mv = new ModelAndView(MainetConstants.WorksManagement.PROJECT_REGISETR_REPORT, MainetConstants.FORM_NAME,
                getModel());
        return mv;

    }

    @RequestMapping(params = MainetConstants.WorksManagement.PROJECT_NAME, method = RequestMethod.POST)
    public @ResponseBody List<WmsProjectMasterDto> getAllActiveWorksNameByProjectId(
            @RequestParam(MainetConstants.WorksManagement.SCHEME_ID) Long wmSchId) {

        List<WmsProjectMasterDto> obj;
        if (wmSchId == -1l) {
            obj = this.getModel().getProjectMasterList();
        } else {
            obj = schemeMasterService.getSchmMastToProject(wmSchId).getSchemeProjectlist();
        }
        return obj;
    }

    @RequestMapping(params = MainetConstants.WorksManagement.GET_WORK_PROJECT_REGISTER_REPORT, method = {
            RequestMethod.POST })
    public ModelAndView getWorkProjectRegisterReport(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.WorksManagement.schId) Long schId,
            @RequestParam(MainetConstants.WorksManagement.WORK_TYPE) Long workType, final HttpServletRequest request) {

        sessionCleanup(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setProjectStatusReportDetDto(
                ApplicationContextProvider.getApplicationContext().getBean(WorksProjectRegisterReportService.class)
                        .findProjectRegisterSheetReport(schId, projId, workType, orgId));
        return new ModelAndView(MainetConstants.WorksManagement.PROJECT_REGISTER_REPORT_SHEET,
                MainetConstants.FORM_NAME, getModel());

    }

}