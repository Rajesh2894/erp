package com.abm.mainet.dashboard.citizen.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dashboard.citizen.service.IServiceApplicationStatus;
import com.abm.mainet.dashboard.citizen.ui.model.ServiceApplicationStatusModel;

@Controller
@RequestMapping("/ServiceApplicationStatus.html")

public class ServiceApplicationStatusController extends AbstractEntryFormController<ServiceApplicationStatusModel> {

    @Autowired
    IServiceApplicationStatus iServiceApplicationStatus;

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IPortalServiceMasterService portalService;

    @RequestMapping(params = "getServiceStatus", method = RequestMethod.POST)
    public ModelAndView getServiceStatus(@RequestParam("serviceId") final Long appId, final HttpServletRequest request) {

        bindModel(request);
        final ApplicationPortalMaster details = iServiceApplicationStatus.getServiceApplicationStatus(appId);
        if (details != null) {
            final Employee emp = employeeService.getEmployeeById(details.getUserId().getEmpId(),
                    UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.ZERO);
            final PortalService service = portalService.getService(details.getSmServiceId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (emp.getEmpname() == null) {
                emp.setEmpname(MainetConstants.BLANK);
            }
            if (emp.getEmpMName() == null) {
                emp.setEmpMName(MainetConstants.BLANK);
            }
            if (emp.getEmpLName() == null) {
                emp.setEmpLName(MainetConstants.BLANK);
            }
            getModel().getDto().setApplicantName(emp.getEmpname() + MainetConstants.WHITE_SPACE + emp.getEmpMName()
                    + MainetConstants.WHITE_SPACE + emp.getEmpLName());
            final UserSession session = UserSession.getCurrent();
            if (session.getLanguageId() == 1) {
                getModel().getDto().setServiceName(service.getServiceName());
            } else {
                getModel().getDto().setServiceName(service.getServiceName());
            }

            getModel().setDetails(details);
        } else {

            getModel().setDetails(details);
        }
        return new ModelAndView("ServiceApplicationStatus", MainetConstants.FORM_NAME, getModel());
    }

}
