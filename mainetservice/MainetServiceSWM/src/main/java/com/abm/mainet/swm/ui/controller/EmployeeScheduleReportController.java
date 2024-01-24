package com.abm.mainet.swm.ui.controller;

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
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;
import com.abm.mainet.swm.service.IEmployeeScheduleService;
import com.abm.mainet.swm.service.ISLRMEmployeeMasterService;
import com.abm.mainet.swm.ui.model.EmployeeScheduleReportModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/EmployeeScheduleReport.html")
public class EmployeeScheduleReportController extends AbstractFormController<EmployeeScheduleReportModel> {
    /**
     * The ISLRMEmployeeMasterService Service
     */
    @Autowired
    private ISLRMEmployeeMasterService employeeService;

    /**
     * The IEmployeeSchedule Service
     */
    @Autowired
    private IEmployeeScheduleService employeeScheduleService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null,UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCommonHelpDocs("EmployeeScheduleReport.html");
        return index();
    }

    /**
     * EmployeeSchedule Report Summary
     * @param request
     * @param empId
     * @param scheduleType
     * @param fromDate
     * @param toDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "report", method = RequestMethod.POST)
    public ModelAndView EmployeeScheduleReportSummary(final HttpServletRequest request, @RequestParam("empId") Long empId,
            @RequestParam("scheduleType") String scheduleType, @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        EmployeeScheduleReportModel empScheRModel = this.getModel();
        Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        empScheRModel.getEmployeeDTO().setFromDate(fromDate);
        empScheRModel.getEmployeeDTO().setToDate(toDate);
        EmployeeScheduleDTO employeeScheduleDetails = employeeScheduleService.getEmployeeSheduleDetailsForReports(OrgId, empId,
                scheduleType,
                Utility.stringToDate(fromDate),
                Utility.stringToDate(toDate));
        if (employeeScheduleDetails != null) {
            empScheRModel.getEmployeeScheduleDTO().setFlagMsg("Y");
            if (employeeScheduleDetails.getEmployeeScheduleList() != null
                    && !employeeScheduleDetails.getEmployeeScheduleList().isEmpty()) {
                empScheRModel.setEmployeeScheduleDTO(employeeScheduleDetails);
                for (EmployeeScheduleDTO det : this.getModel().getEmployeeScheduleDTO().getEmployeeScheduleList()) {
                    if (empId != 0)
                        empScheRModel.getEmployeeDTO().setEmpName(det.getEmpName());
                    empScheRModel.getEmployeeDTO().setEmsType(det.getEmsType());
                    break;
                }
            }
            redirectType = "EmployeeScheduleReportSummary";
        } else {
            empScheRModel.getEmployeeScheduleDTO().setFlagMsg("N");
            redirectType = "EmployeeScheduleReportList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, empScheRModel);
    }
}
