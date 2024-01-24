package com.abm.mainet.adh.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.RenewalAdvertisementApplicationModel;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

/**
 * @author vishwajeet.kumar
 * @since 04 Dec 2019
 */
@Controller
@RequestMapping("/RenewalAdvLicenseAuth.html")
public class RenewalAdvertisementAuthController extends AbstractFormController<RenewalAdvertisementApplicationModel> {

    @Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;
    @Autowired
    private TbLoiMasService loiMasService;

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
    public ModelAndView showNewAdvertisementApplication(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
            @RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) Long serviceId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
            final HttpServletRequest request) {

        this.getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        NewAdvertisementReqDto advReqDto = newAdvApplicationService.getNewAdvertisementApplicationByAppId(applicationId,
                orgId);
        // Defect #124749
        if (getModel().getRemarkList() == null) {
            List<String> remarkList = new ArrayList<String>();
            getModel().setRemarkList(remarkList);
        }
        // START Defect#126605
        List<TbLoiMas> loiMasList = loiMasService.getloiByApplicationId(applicationId, serviceId, orgId);
        if (CollectionUtils.isNotEmpty(loiMasList)) {
            TbLoiMas loiMas = loiMasList.get(loiMasList.size() - 1);
            if (loiMas != null && loiMas.getLoiRemark() != null) {
                String loiRemark = loiMas.getLoiRemark();
                List<String> remarkList = Stream.of(loiRemark.split(",")).map(rem -> new String(rem))
                        .collect(Collectors.toList());
                getModel().setRemarkList(remarkList);
            }
        }// END Defect#126605
        this.getModel().setAdvertisementReqDto(advReqDto);
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        taskAction.setApplicationId(applicationId);
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setTaskId(actualTaskId);

        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.AdvertisingAndHoarding.PROCESS_SCRUITNY);
        workflowProcessParameter.setWorkflowTaskAction(taskAction);
        try {
            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }

        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADVERTISEMENT_LICENSE_GEN, MainetConstants.FORM_NAME,
                this.getModel());
    }
}
