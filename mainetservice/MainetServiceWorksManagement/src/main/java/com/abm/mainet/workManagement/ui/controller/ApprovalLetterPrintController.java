package com.abm.mainet.workManagement.ui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionSancDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.service.ApprovalTermsConditionService;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.ui.model.ApprovalLetterPrintModel;

@Controller
@RequestMapping("/ApprovalLetterPrint.html")
public class ApprovalLetterPrintController extends AbstractFormController<ApprovalLetterPrintModel> {

    @Autowired
    private WorkDefinitionService workDefinationService;

    @Autowired
    private WmsProjectMasterService projectMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        this.getModel().setCommonHelpDocs("ApprovalLetterPrint.html");
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
        this.getModel().setReportTypeLookUp(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.WET,
                UserSession.getCurrent().getOrganisation()));
        this.getModel().setOrgId(currentOrgId);
        return new ModelAndView(MainetConstants.WorksManagement.APPROVAL_LETTER_PRINT, MainetConstants.FORM_NAME,
                getModel());

    }

    /**
     * Used to get All Active Works Name By ProjectId
     * 
     * @param orgId
     * @param projId
     * @return
     */
    @RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
    public @ResponseBody List<Object> getAllActiveWorksNameByProjectId(
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {

        List<Object> obj = workDefinationService.findAllWorksByProjIdAndStatus(orgId, projId);
        return obj;
    }

    /**
     * Used to get All Active Sanction Number By WorkId
     * 
     * @param orgId
     * @param workId
     * @return
     */
    @RequestMapping(params = MainetConstants.WorksManagement.SANCTION_NUMBER, method = RequestMethod.POST)
    public @ResponseBody List<WorkDefinitionSancDetDto> getSanctionNoByWorkId(
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<WorkDefinitionSancDetDto> obj = workDefinationService.findSanctionNoByWorkId(orgId, workId);
        return obj;
    }

    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_WORK_REPORT, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewWorkReport(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId,
            @RequestParam(MainetConstants.WorksManagement.WORK_SANC_NO) String workSancNo,
            @RequestParam(value = "parentOrgId", required = false) Long parentOrgId, final HttpServletRequest request) {

        bindModel(request);
        if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
                || !request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                        .equals(MainetConstants.WorksManagement.APPROVAL)) {
        } else {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.APPROVAL);
            request.getSession().removeAttribute(MainetConstants.WorksManagement.SAVEMODE);
        }
        if (parentOrgId == null) {
            parentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }

        this.getModel().setProjectMasterDto(projectMasterService.getProjectMasterByProjId(projId));
        this.getModel().setDefinitionDto(workDefinationService.findAllWorkDefinitionById(workId));
        Long dpDeptid = this.getModel().getDefinitionDto().getDeptId();
        TbDepartment deptName = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findById(dpDeptid);

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, this.getModel().getDefinitionDto().getWorkcode(), parentOrgId);
        if(workflowRequest != null) {
        	 List<WorkflowTaskActionWithDocs> actionHistory = ApplicationContextProvider.getApplicationContext()
                     .getBean(IWorkflowActionService.class)
                     .getActionLogByUuidAndWorkflowId(this.getModel().getDefinitionDto().getWorkcode(), workflowRequest.getId(),
                             (short) UserSession.getCurrent().getLanguageId());
             Long workFlowOrgId = null;
             for (WorkflowTaskActionWithDocs docs : actionHistory) {
                 if ((docs.getTaskName() != null && !docs.getTaskName().isEmpty())
                         && docs.getTaskName().contains(MainetConstants.WorksManagement.TECHNICAL_SANCTION)) {
                     if (workSancNo.startsWith(MainetConstants.WorksManagement.FLAG_T))
                         workFlowOrgId = docs.getOrgId();
                 } else if ((docs.getTaskName() != null && !docs.getTaskName().isEmpty())
                         && docs.getTaskName().contains(MainetConstants.WorksManagement.ADM_SANCTION)) {
                     if (workSancNo.startsWith(MainetConstants.FlagA)) {
                         workFlowOrgId = docs.getOrgId();
                     }
                 }
             }
             //#148872
            if (workFlowOrgId != null) {
             TbOrganisation tbOrganisation = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
                     .findById(workFlowOrgId);
             this.getModel().setOrgRegionalName(tbOrganisation.getONlsOrgnameMar());
            }
        }
       
       
        if(UserSession.getCurrent().getLanguageId()==MainetConstants.ENGLISH) {
        	this.getModel().setDeptName(deptName.getDpDeptdesc());
        }else {
        	this.getModel().setDeptName(deptName.getDpNameMar());
        }
        
        BigDecimal workEstimAmt = this.getModel().getDefinitionDto().getWorkEstAmt();
        Utility.convertBiggerNumberToWord(workEstimAmt);

        WmsProjectMasterDto projectMasterDto = projectMasterService.getProjectMasterByProjId(projId);
        WorkDefinitionDto definitionDto = workDefinationService.findAllWorkDefinationSanctionByProjId(parentOrgId, projId,
                workSancNo);
        if (!definitionDto.getSanctionDetails().isEmpty()) {
            definitionDto.setWorksancDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                    .format(definitionDto.getSanctionDetails().get(0).getWorkSancDate()));
        }
        definitionDto.setSanctionNumber(workSancNo);
        this.getModel().setDefinitionDto(definitionDto);
        if(projectMasterDto.getRsoDate() != null) {
        projectMasterDto.setOrderDateDesc(
                new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(projectMasterDto.getRsoDate()));
        }
        this.getModel().setProjectMasterDto(projectMasterDto);
        List<WorkEstimateMasterDto> workEstimateMasterDto = ApplicationContextProvider.getApplicationContext()
                .getBean(WorkEstimateService.class)
                .getWorkEstimateByWorkId(workId, parentOrgId);

        Long sorId = workEstimateMasterDto.get(0).getSorId();
        if (sorId != null) {
            ScheduleOfRateMstDto scheduleOfRateMstDto = ApplicationContextProvider.getApplicationContext()
                    .getBean(ScheduleOfRateService.class).findSORMasterWithDetailsBySorId(sorId);
            this.getModel().setScheduleOfRateMstDto(scheduleOfRateMstDto);
        }
        this.getModel().setWorkEstimAmt(Utility.convertBiggerNumberToWord(workEstimAmt));
        this.getModel().setApprovalTermsConditionDto(
                ApplicationContextProvider.getApplicationContext().getBean(ApprovalTermsConditionService.class)
                        .findAllTermsAndCodition(workId, workSancNo));
        return new ModelAndView(MainetConstants.WorksManagement.TECHNICAL_APPROVAL_REPORT, MainetConstants.FORM_NAME,
                this.getModel());

    }

}