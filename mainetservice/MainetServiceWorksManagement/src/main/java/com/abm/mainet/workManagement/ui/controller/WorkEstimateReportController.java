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
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateReportService;
import com.abm.mainet.workManagement.ui.model.WorkEstimateReportModel;

/**
 * @author vishwajeet.kumar
 * @since 1 March 2018
 */

@Controller
@RequestMapping(MainetConstants.WorksManagement.WORK_ESTIMATE_REPORT_HTML)
public class WorkEstimateReportController extends AbstractFormController<WorkEstimateReportModel> {

    @Autowired
    private WmsProjectMasterService projectMasterService;

    @Autowired
    private WorkDefinitionService workDefinationService;

    @Autowired
    private WorkEstimateReportService service;
    
    @Autowired
    private TbDepartmentService tbdepartmentservice;

    /**
     * Used for showing default home page for Work Estimate Report
     * 
     * @param httpServletRequest
     * @return defaultView
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
       /*  Defect #85135*/
        //this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
       this.getModel().setReportTypeLookUp(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.WET,
                UserSession.getCurrent().getOrganisation()));

         this.getModel().setDepartmentsList(tbdepartmentservice.findMappedDepartments(currentOrgId));
        
        this.getModel().setOrgId(currentOrgId);
        this.getModel().setCommonHelpDocs("WorkEstimateReport.html");
        return index();

    }
    
    

    /**
     * Used to get All Active Works Name By ProjectId
     * 
     * @param orgId
     * @param projId
     * @return
     */
    @RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
    public @ResponseBody List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,@RequestParam(MainetConstants.WorksManagement.WORKTYPE) Long workType){
    	
        List<WorkDefinitionDto> obj = workDefinationService.findAllWorkDefByProjIdAndWorkType(orgId, projId, workType);
        return obj;
    }
    
  /*  Defect #85135*/
    @RequestMapping(params = MainetConstants.WorksManagement.PROJ_LIST, method = RequestMethod.POST)
    public @ResponseBody List<WmsProjectMasterDto> getAllActiveProjectsByDeptId(
    		 @RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
    	
        List<WmsProjectMasterDto> obj = projectMasterService.getAllActiveProjectsByDeptIdAndOrgId(deptId, orgId);
        return obj;
    }

    /**
     * Used to view Abstract report Sheet and Measurement Sheet
     * 
     * @param projId
     * @param workId
     * @param reportType
     * @param request
     * @return reportModel
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_ESTIMATE_ABSTRACT_SHEET, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewAbstractreportSheet(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId,
            @RequestParam(MainetConstants.WorksManagement.REPORT_TYPE) String reportType,
           @RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
            @RequestParam(MainetConstants.WorksManagement.WORKTYPE) Long workType,
            final HttpServletRequest request) {

        sessionCleanup(request);
        if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
                || !request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                        .equals(MainetConstants.WorksManagement.APPROVAL)) {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.REPORT);
        } else {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.APPROVAL);
            request.getSession().removeAttribute(MainetConstants.WorksManagement.SAVEMODE);
        }
        ModelAndView reportModel = null;
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

        this.getModel().setProjectMasterDto(projectMasterService.getProjectMasterByProjId(projId));
        this.getModel().setDefinitionDto(workDefinationService.findAllWorkDefinitionById(workId));
        Department department= tbdepartmentservice.findDepartmentById(deptId);
        this.getModel().setDepartName(department.getDpDeptdesc());
        this.getModel().setDepartNameReg(department.getDpNameMar());
             
        if (reportType.equals(MainetConstants.WorksManagement.ABSTRACT_SHEET)) {

            this.getModel().setWorkMasterDtosList(service.findAbstractSheetReport(workId,deptId,workType,currentOrgId));
            if (!this.getModel().getWorkMasterDtosList().isEmpty()) {
                reportModel = new ModelAndView(MainetConstants.WorksManagement.WORK_REPORT_ABSTRACT_SHEET,
                        MainetConstants.FORM_NAME, this.getModel());
            }

        } else if (reportType.equals(MainetConstants.WorksManagement.MEASUREMENT_SHEET)) {
            this.getModel().setWorkMasterDtosList(service.findMeasurementReport(workId, currentOrgId));
            if (!this.getModel().getWorkMasterDtosList().isEmpty()) {
                reportModel = new ModelAndView(MainetConstants.WorksManagement.WORK_REPORT_MEASUREMENT_SHEET,
                        MainetConstants.FORM_NAME, this.getModel());
            }

        } else if (reportType.equals(MainetConstants.FlagR)) {
            /*
             * reportModel = new ModelAndView(MainetConstants.WorksManagement.WORK_REPORT_RATE_ANALYSIS,
             * MainetConstants.FORM_NAME, this.getModel());
             */
        }
        return reportModel;

    }

}
