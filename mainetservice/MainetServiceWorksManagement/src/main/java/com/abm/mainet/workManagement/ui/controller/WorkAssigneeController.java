package com.abm.mainet.workManagement.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.ui.model.WorkAssigneeModel;

/**
 * @author hiren.poriya
 * @Since 17-May-2018
 */
@Controller
@RequestMapping(MainetConstants.WorksManagement.WORK_ASSIGNEE_HTML)
public class WorkAssigneeController extends AbstractFormController<WorkAssigneeModel> {

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest httpServletRequest, Model model) {
        sessionCleanup(httpServletRequest);
        model.addAttribute(MainetConstants.WorksManagement.WORK_ORDER_LIST,
                workOrderService.getAllWorkOrderIdAndNoList(UserSession.getCurrent().getOrganisation().getOrgid()));
        model.addAttribute(MainetConstants.WorksManagement.WORK_ASSIGNEE_GROUP,
                workOrderService.getAllWorkOrderGroupByAssignee(UserSession.getCurrent().getOrganisation().getOrgid()));
        model.addAttribute(MainetConstants.WorksManagement.EMPLOYEE, employeeService.findAllEmpByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @RequestMapping(params = MainetConstants.WorksManagement.ASSIGNEE_FORM, method = RequestMethod.POST)
    public ModelAndView workAssigneeForm(HttpServletRequest httpServletRequest,
            @RequestParam(value = MainetConstants.WorksManagement.WORK_ORDER_ID, required = false) Long orderId,
            @RequestParam(value = MainetConstants.WorksManagement.ASSIGNEE_ID, required = false) Long assigneeId,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = false) String formMode) {
        WorkAssigneeModel assigneeModel = this.getModel();
        populateModel(assigneeModel, orderId, formMode, assigneeId);
        return new ModelAndView(MainetConstants.WorksManagement.WORK_ASSIGNEE_FORM, MainetConstants.FORM_NAME, assigneeModel);

    }

    private void populateModel(WorkAssigneeModel assigneeModel, Long orderId, String formMode, Long assigneeId) {
        assigneeModel.setWorkOderList(
                workOrderService.getAllWorkOrderIdAndNoList(UserSession.getCurrent().getOrganisation().getOrgid()));
        assigneeModel.setEmpList(employeeService.findAllEmpByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
        if (orderId == null) {
            assigneeModel.setMode(MainetConstants.MODE_CREATE);

        } else {
            assigneeModel.setWorkOrderId(orderId);
            assigneeModel.setEmpId(assigneeId);
            if (formMode.equals(MainetConstants.MODE_EDIT)) {
                assigneeModel.setWorkOderDto(workOrderService.getTenderWorkForUpdateByWorkIdAndAssignee(orderId, assigneeId));
                assigneeModel.setMode(MainetConstants.MODE_EDIT);
            } else {
                assigneeModel.setWorkOderDto(workOrderService.getAssigneeTenderWork(orderId, assigneeId));
                assigneeModel.setMode(MainetConstants.MODE_VIEW);
            }
        }
    }

    /**
     * search assigned work of work order
     * @param httpServletReuest
     * @param orderId
     * @return List<WorkOrderDto>
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.SEARCH_ASSIGNEE, method = RequestMethod.POST)
    public List<WorkOrderDto> searchAssinedWorkByWorkOrderNo(HttpServletRequest httpServletReuest,
            @RequestParam(value = MainetConstants.WorksManagement.WORK_ORDER_ID, required = false) Long orderId) {
        return workOrderService.searchWorkOrderGroupByAssignee(orderId);
    }

    /**
     * get not assigned work list for add assignee form
     * @param httpServletReuest
     * @param orderId WorkOrderDto
     * @return WorkOrderDto
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_WORK_LIST, method = RequestMethod.POST)
    public WorkOrderDto getWorkWithWorkOrderDetails(HttpServletRequest httpServletReuest,
            @RequestParam(MainetConstants.WorksManagement.WORK_ORDER_ID) Long orderId) {
        return workOrderService.getNotAssigneedWorksByWorkOrder(orderId);
    }

}
