package com.abm.mainet.agency.authentication.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.agency.authentication.ui.model.AgencyAuthorizationModel;
import com.abm.mainet.agency.authentication.ui.validation.AgencyAuthorizationValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/AgencyAuthorization.html")
public class AgencyAuthorizationController extends AbstractFormController<AgencyAuthorizationModel> implements Serializable {
    private static final long serialVersionUID = 6657847222325801035L;
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {

        sessionCleanup(request);
        return new ModelAndView(MainetConstants.AGENCY.AGENCY_AUTH, MainetConstants.CommonConstants.COMMAND, getModel());
    }
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView getAgencyByAgencyType(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final AgencyAuthorizationModel model = getModel();
        final EmployeeDTO employee = getModel().getEntity();
        employee.setStatusIS(model.getStatusIS());
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        model.validateBean(employee, AgencyAuthorizationValidator.class);

        if (!model.hasValidationErrors()) {

            model.setAgency(model.getiEmployeeService().getEmployeeByAgencyTypeAndBySortOption(employee.getEmplType(),
                    model.getAgencyName(), model.getStatusIS(), orgid));

            if (model.getAgency().size() == 0) {
                model.addValidationError(getApplicationSession().getMessage("eip.agency.noRecords"));
                return defaultResult();
            }
        }
        return defaultResult();
    }
    @RequestMapping(params = "AGENCY_LIST", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<EmployeeDTO> getAppFormList(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        final List<EmployeeDTO> employees = getModel().getAgency();
        return getModel().paginate(httpServletRequest, page, rows, employees);
    }
}
