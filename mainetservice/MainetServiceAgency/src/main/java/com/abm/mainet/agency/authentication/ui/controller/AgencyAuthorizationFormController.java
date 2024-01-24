package com.abm.mainet.agency.authentication.ui.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.agency.authentication.ui.model.AgencyAuthorizationFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/AgencyAuthorizationForm.html")
public class AgencyAuthorizationFormController extends AbstractFormController<AgencyAuthorizationFormModel>
        implements Serializable {

    private static final long serialVersionUID = 3110560111013867584L;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IEmployeeService employeeService;

    @ModelAttribute("agencyList")
    public Map<Long, String> getGroupList() {

        final Map<Long, String> groupLookup = employeeService.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
        return groupLookup;
    }
    @ModelAttribute("locationList")
    public List<Department> getLocationList() {
        final List<Department> list = departmentService.getDepartments(PrefixConstants.IsLookUp.ACTIVE);
        return list;
    }
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam final long rowId, final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        try {
            if (getModel().doAuthorization(rowId)) {
                getModel().editForm(rowId);
            } else {
                getModel().addValidationError("Not authorized user!");
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return new ModelAndView(MainetConstants.AGENCY.AGENCY_AUTH_FORM,MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final AgencyAuthorizationFormModel model = getModel();

        try {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            }
        } catch (final Exception ex) {

            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return defaultResult();
    }

}
