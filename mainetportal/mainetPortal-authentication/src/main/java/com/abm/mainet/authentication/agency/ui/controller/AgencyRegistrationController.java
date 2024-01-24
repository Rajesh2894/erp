package com.abm.mainet.authentication.agency.ui.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.authentication.agency.ui.model.AgencyRegistrationModel;
import com.abm.mainet.authentication.agency.ui.validator.AgencyRegistrationFormValidation;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/AgencyRegistration.html")
public class AgencyRegistrationController extends AbstractFormController<AgencyRegistrationModel>
        implements Serializable {

    private static final long serialVersionUID = 2517124206196031320L;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("AgencyRegistration", MainetConstants.FORM_NAME, new AgencyRegistrationModel());
    }

    @RequestMapping(params = "registerAgency", method = RequestMethod.POST)
    public ModelAndView registerNewAgency(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        final AgencyRegistrationModel model = getModel();

        final AgencyEmployeeReqDTO newAgency = model.getEntity();

        final boolean result = validateFormData(model.getEntity());
        if ((getModel() != null) && (getModel().getEntity() != null)) {
            getModel().setTitle(getModel().getEntity().getTitle());
            getModel().setGender(getModel().getEntity().getEmpGender());
            if (getModel().getEntity().getEmplType() != null) {
                getModel().setEmpType(getModel().getEntity().getEmplType());
            }
        }

        model.validateBean(model.getEntity(), AgencyRegistrationFormValidation.class);
        if (!model.isUniqueEmailAddress(newAgency.getEmpemail())) {
            getModel().addValidationError(getApplicationSession().getMessage("eip.agency.UniqueEmailAddress"));
        }

        if (!model.isUniqueMobileNumber(newAgency.getEmpmobno())) {
            getModel().addValidationError(getApplicationSession().getMessage("eip.agency.UniqueMobileNumber"));
        }

        if (result || model.hasValidationErrors()) {
            return defaultResult();
        } else {

            final Employee registeredAgency = model.doAgencyRegistration(newAgency);
            UserSession.getCurrent().setEmployee(registeredAgency);
            if (registeredAgency != null) {
                return model.redirectToOTPVerification();
            } else {
                model.addValidationError("Some Internal error");
            }
        }
        return defaultResult();
    }

    private boolean validateFormData(final AgencyEmployeeReqDTO employeeReqDTO) {

        boolean status = false;

        if ((employeeReqDTO.getEmplType() == null) || (employeeReqDTO.getEmplType() == 0l)) {
            getModel().addValidationError("Please select Agency Type");
            status = true;
        }
        if ((employeeReqDTO.getAgencyName() == null) || employeeReqDTO.getAgencyName().isEmpty()) {
            getModel().addValidationError(getApplicationSession().getMessage("please.select.agency.name"));
            status = true;
        }

        if ((employeeReqDTO.getTitle() == null) || (employeeReqDTO.getTitle() == 0l)) {
            getModel().addValidationError(getApplicationSession().getMessage("select.title"));
            status = true;
        }

        if ((employeeReqDTO.getEmpname() == null) || employeeReqDTO.getEmpname().isEmpty()) {
            getModel().addValidationError(getApplicationSession().getMessage("please.select.owner.first.name"));
            status = true;
        }
        if ((employeeReqDTO.getEmpLName() == null) || employeeReqDTO.getEmpLName().isEmpty()) {
            getModel().addValidationError(getApplicationSession().getMessage("select.owner.last.name"));
            status = true;
        }

        if ((employeeReqDTO.getEmpGender() == null) || employeeReqDTO.getEmpGender().isEmpty()) {
            getModel().addValidationError(getApplicationSession().getMessage("select.gender"));
            status = true;
        }

        if ((employeeReqDTO.getEmpdob() == null) || employeeReqDTO.getEmpdob().equals(MainetConstants.BLANK)) {

            getModel().addValidationError(getApplicationSession().getMessage("select.date.of.birth"));
            status = true;
        } else {

            final Date empdob = employeeReqDTO.getEmpdob();

            final SimpleDateFormat formatDateJava = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);

            final String dobstring = formatDateJava.format(empdob);
            final String[] date1 = dobstring.split(MainetConstants.WINDOWS_SLASH);
            final String day = date1[0];
            final String month = date1[1];
            final String year = date1[2];
            final Integer day1 = Integer.valueOf(day);
            final Integer month1 = Integer.valueOf(month);
            final Integer year1 = Integer.valueOf(year);

            if ((month1 < 1) || (month1 > 12)) {
                getModel().addValidationError(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1);
                status = true;

            }

            if ((day1 < 1) || (day1 > 31)) {
                getModel().addValidationError(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1);
                status = true;
            }

            if (((month1 == 4) || (month1 == 6) || (month1 == 9) || (month1 == 11)) && (day1 == 31)) {
                getModel().addValidationError(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1);
                status = true;
            }

            if (month1 == 2) { // check for february 29th
                final boolean isleap = ((year1 % 4) == 0) && (((year1 % 100) != 0) || ((year1 % 400) == 0));
                if ((day1 > 29) || ((day1 == 29) && !isleap)) {
                    getModel().addValidationError(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1);
                    status = true;
                }
            }
            final Calendar now = Calendar.getInstance();
            int curr_year = now.get(Calendar.YEAR);
            int curr_month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            final int curr_date = now.get(Calendar.DAY_OF_MONTH);
            if (curr_date < day1) {
                curr_month = curr_month - 1;
            }
            if (curr_month < month1) {
                curr_year = curr_year - 1;
            }
            curr_year = curr_year - year1;
            if (curr_year <= 0) {
                getModel().addValidationError(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1);
                status = true;
            } else if (curr_year < 18) {
                getModel().addValidationError("citizen.login.reg.dob.error3");
                status = true;
            }

        }

        if ((employeeReqDTO.getEmpAddress() == null) || employeeReqDTO.getEmpAddress().isEmpty()) {
            getModel().addValidationError(getApplicationSession().getMessage("select.permanenet.address"));
            status = true;
        }

        return status;

    }

    }
