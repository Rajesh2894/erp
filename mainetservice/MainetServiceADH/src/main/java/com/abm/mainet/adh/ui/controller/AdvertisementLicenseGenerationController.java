package com.abm.mainet.adh.ui.controller;

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

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.NewAdvertisementApplicationModel;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Controller
@RequestMapping("/AdvertisementLicenseGeneration.html")
public class AdvertisementLicenseGenerationController extends AbstractFormController<NewAdvertisementApplicationModel> {

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

        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);

        String advertisementLicenseNo = newAdvApplicationService.generateNewAdvertisementLicenseNumber(
                UserSession.getCurrent().getOrganisation(),
                master.getTbDepartment().getDpDeptid());
        
        try {
            newAdvApplicationService.updateAdvertisementLicenseNo(applicationId, advertisementLicenseNo, orgId);
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured while updating License Number", exception);
        }
        NewAdvertisementReqDto advReqDto = newAdvApplicationService.getNewAdvertisementApplicationByAppId(applicationId,
                orgId);
        AdvertiserMasterDto dto=ApplicationContextProvider.getApplicationContext()
                .getBean(IAdvertiserMasterService.class).getAgencyRegistrationByAppIdByOrgId(applicationId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        // US#106604
        List<TbLoiMas> loiMasList = loiMasService.getloiByApplicationId(applicationId, serviceId, orgId);
        if (CollectionUtils.isNotEmpty(loiMasList)) {
            TbLoiMas loiMas = loiMasList.get(loiMasList.size() - 1);
            if (loiMas != null && loiMas.getLoiRemark() != null) {
                String loiRemark = loiMas.getLoiRemark();
                List<String> remarkList = Stream.of(loiRemark.split(",")).map(rem -> new String(rem))
                        .collect(Collectors.toList());
                getModel().setRemarkList(remarkList);
            }
        }// END
        advReqDto.getAdvertisementDto().setLicenseFromDate(dto.getAgencyLicFromDate());
        advReqDto.getAdvertisementDto().setLicenseToDate(dto.getAgencyLicToDate());
        advReqDto.getAdvertisementDto().setLicenseNo(advertisementLicenseNo);
       
        this.getModel().setAdvertisementReqDto(advReqDto);

        if (advertisementLicenseNo != null) {
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
            workflowProcessParameter.setProcessName("scrutiny");
            workflowProcessParameter.setWorkflowTaskAction(taskAction);
            try {
                ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                        .updateWorkflow(workflowProcessParameter);
            } catch (Exception exception) {
                throw new FrameworkException("Exception Occured when Update Workflow methods", exception);
            }
        }
        return new ModelAndView("advertisementLicenseGeneration", MainetConstants.FORM_NAME, this.getModel());
    }
}
