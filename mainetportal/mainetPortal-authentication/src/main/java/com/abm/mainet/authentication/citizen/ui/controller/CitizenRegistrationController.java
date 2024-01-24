/**
 *
 */
package com.abm.mainet.authentication.citizen.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.citizen.ui.model.CitizenRegistrationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

@Controller
@RequestMapping("/CitizenRegistration.html")
public class CitizenRegistrationController extends AbstractFormController<CitizenRegistrationModel> {
    private final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs(MainetConstants.EIP_CHKLST.CITIZEN);
        return new ModelAndView("CitizenRegistration", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "registerCitizen",method = RequestMethod.POST)
    public @ResponseBody String registrationProcess(final HttpServletRequest request) {

        final BindingResult bindingResult = bindModel(request);
        final CitizenRegistrationModel model = getModel();
        if (request.getSession().getAttribute("captcha").equals(model.getCaptchaSessionValue())) {

            if (!bindingResult.hasErrors()) {
                final Employee newCitizen = model.getNewCitizen();
                final Employee registeredCitizen = model.doCitizenRegistration(newCitizen);

                if ((registeredCitizen != null) && (registeredCitizen.getEmpId() != 0l)) {
                    return model.redirectToOTPVerification();// Means_employee_registered_successfully.
                } else {
                    return MainetConstants.BLANK;
                }
            } else {

                for (final Object error : bindingResult.getAllErrors()) {

                    if (error instanceof FieldError) {

                    }
                    if (error instanceof ObjectError) {

                    }
                }
            }
            return MainetConstants.BLANK;
        } else {

            return MainetConstants.CAPTCHA_NOT_MATCHED;

        }

    }

    @RequestMapping(params = "IsUniqueMobile", method = RequestMethod.POST)
    public @ResponseBody String isUniqueMobileNumber(final HttpServletRequest request) {

        final BindingResult bindingResult = bindModel(request);
        final CitizenRegistrationModel model = getModel();
        String result = MainetConstants.BLANK;

        if (!bindingResult.hasErrors()) {
            if ((model.getNewCitizen() != null) && (model.getNewCitizen().getEmpmobno() != null)
                    && (model.getNewCitizen().getEmpmobno() != MainetConstants.BLANK)) {
                if (!model.isUniqueMobileNumber(model.getNewCitizen().getEmpmobno())) {
                    result = getApplicationSession().getMessage("Employee.uniqueMobileNum");
                }

            }
        }

        return result;
    }

    @RequestMapping(params = "IsUniqueEmailID", method = RequestMethod.POST)
    public @ResponseBody String isUniqueEmailAddress(final HttpServletRequest request) {

        final BindingResult bindingResult = bindModel(request);
        final CitizenRegistrationModel model = getModel();
        String result = MainetConstants.BLANK;

        if (!bindingResult.hasErrors()) {
            if ((model.getNewCitizen() != null) && (model.getNewCitizen().getEmpemail() != null)
                    && (model.getNewCitizen().getEmpemail() != MainetConstants.BLANK)) {
                if (!model.isUniqueEmailAddress(model.getNewCitizen().getEmpemail())) {
                    result = getApplicationSession().getMessage("Employee.uniqueEMailId");
                }
            }
        }

        return result;
    }


    @RequestMapping(params = "isUniqueUserAlias", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody boolean isUniqueUserAlias(final HttpServletRequest httpServletRequest,
            @RequestParam("userAlias") final String userAlias) {

        return getModel().isUniqueUserAlias(userAlias);
    }
    
   

}
