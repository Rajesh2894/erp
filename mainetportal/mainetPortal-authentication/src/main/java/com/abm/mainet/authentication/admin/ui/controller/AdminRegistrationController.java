package com.abm.mainet.authentication.admin.ui.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminRegistrationModel;
import com.abm.mainet.authentication.admin.ui.validator.AdminRegistrationValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IDesignationService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author vinay.jangir
 *
 */
@Controller
@RequestMapping("/AdminRegistration.html")
public class AdminRegistrationController extends AbstractEntryFormController<AdminRegistrationModel> {

    @Autowired
    private IDesignationService iDesignationService;

    @Autowired
    private IEntitlementService entitlementService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request) {

        sessionCleanup(request);

        getModel().getDesignation();
        return new ModelAndView("AdminRegistration", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "registerEmployee", method = RequestMethod.POST)
    public ModelAndView registerNewEmployee(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        final AdminRegistrationModel model = getModel();

        final Employee newEmployee = model.getEntity();
        getModel().setTitle(getModel().getEntity().getTitle());
        getModel().setGender(getModel().getEntity().getEmpGender());

        model.validateBean(model.getEntity(), AdminRegistrationValidator.class);

        if ((model.getEntity().getDesignation().getDsgid() != 0) || (model.getHideMode() != 0)) {
            if (model.getEntity().getDesignation().getDsgid() != 0) {
                model.setHideMode(model.getEntity().getDesignation().getDsgid());
            }
            model.getEntity().getDesignation().setDsgid(model.getHideMode());
        }

        if (!model.isUniqueEmailAddress(newEmployee.getEmpemail(), newEmployee.getEmplType())) {
            getModel().addValidationError(getApplicationSession().getMessage("eip.agency.UniqueEmailAddress"));
        }

        if (!model.isUniqueMobileNumber(newEmployee.getEmpmobno(), newEmployee.getEmplType())) {
            getModel().addValidationError(getApplicationSession().getMessage("eip.agency.UniqueMobileNumber"));
        }

        if (model.hasValidationErrors()) {
            return defaultResult();
        } else {
            final Employee registeredEmployee = model.doEmployeeRegistration(newEmployee);
            if (registeredEmployee != null) {
                return model.redirectToOTPVerification();
            }
        }
        return defaultResult();
    }

    @RequestMapping(params = "searchDesg", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getDesignationByDeptId(@RequestParam("deptId") final String deptId) {
        getModel().setDesignationlist(iDesignationService.getAllListDesignationByDeptId(Long.valueOf(deptId).longValue()));

        return getModel().getAllDesignationByDeptId();
    }

    @ModelAttribute("deptList")
    public Map<Long, String> getGroupList() {
        return entitlementService.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
    }

   
    
}