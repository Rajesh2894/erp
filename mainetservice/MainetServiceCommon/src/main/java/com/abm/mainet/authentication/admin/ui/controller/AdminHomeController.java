
package com.abm.mainet.authentication.admin.ui.controller;


import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminHomeModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping(value = { "/AdminHome.html", "/AdminDashboard.html" })
public class AdminHomeController extends AbstractFormController<AdminHomeModel> {
    Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {

        try {
            if (isRestrictClickJacking()) {
                response.addHeader(MainetConstants.AdminHome.XFRAME_OPTIONS, MainetConstants.AdminHome.DENY);
            }
            this.getModel().setCommonHelpDocs("AdminHome.html");
            final AdminHomeModel model = getModel();

            final Employee employee = UserSession.getCurrent().getEmployee();
            if ((employee != null) && (employee.getEmpGender() != null)) {
                if (employee.getEmpGender().length() > 1) {
                    employee.setEmpGender(
                            model.getNonHierarchicalLookUpObject(new Long(employee.getEmpGender())).getLookUpCode());
                }
            }

            if ((employee != null) && (employee.getEmploginname() != null) && !employee.getEmploginname()
                    .equals(ApplicationSession.getInstance().getMessage(MainetConstants.AdminHome.CITIZEN_NOUSER_LOGINAME))) {
                if (((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                        && UserSession.getCurrent().getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.APPROVED))
                        && UserSession.getCurrent().getEmployee().getDesignation().getDsgname()
                                .equalsIgnoreCase(MainetConstants.AdminHome.ADMIN)) {

                }

                if (((UserSession.getCurrent().getEmployee().getIsuploaded() != null)
                        && UserSession.getCurrent().getEmployee().getIsuploaded().equals(MainetConstants.IsUploaded.UPLOADED)
                        && (UserSession.getCurrent().getEmployee().getAuthStatus() == null))

                        || ((UserSession.getCurrent().getEmployee().getIsuploaded() != null)
                                && UserSession.getCurrent().getEmployee().getIsuploaded()
                                        .equals(MainetConstants.IsUploaded.UPLOADED)
                                && UserSession.getCurrent().getEmployee().getAuthStatus()
                                        .equals(MainetConstants.Common_Constant.DELETE_FLAG))

                        || ((UserSession.getCurrent().getEmployee().getIsuploaded() != null)
                                && UserSession.getCurrent().getEmployee().getIsuploaded()
                                        .equals(MainetConstants.IsUploaded.UPLOADED)
                                && ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                                        && UserSession.getCurrent().getEmployee().getAuthStatus()
                                                .equals(MainetConstants.AuthStatus.APPROVED))))

                {
                    // D#127111 set applicableENV
                    Boolean envPresent = false;
                    Boolean sudaCheck = false;
                    try {
						 List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
						  UserSession.getCurrent().getOrganisation());
						  
						  envPresent = envLookUpList.stream().anyMatch( env ->
						  env.getLookUpCode().equals(MainetConstants.APP_NAME.SKDCL) &&
						  StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
						  
						  //#161047
						  envPresent = envLookUpList.stream().anyMatch( env ->
						  env.getLookUpCode().equals(MainetConstants.APP_NAME.DSCL) &&
						  StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
						  
						  logger.info("EnvLookupList" +envLookUpList.toString());
						  
						 //Added Suda env US-141152
						  sudaCheck = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA);
						  
						  if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA_UAT)){
							  UserSession.getCurrent().setCheckSudaUatEnv(MainetConstants.FlagY);
						  }
						  if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL_PROD)){
							  UserSession.getCurrent().setCheckThaneProdEnv(MainetConstants.FlagY);
						  }
                    } catch (Exception e) {
                        logger.error("Prefix Not Found :" + MainetConstants.ENV);
                    }
                    if (envPresent) {
                        getModel().setApplicableENV(true);
                    } else {
                        getModel().setApplicableENV(false);
                    }
                    if(sudaCheck) {//To check is it suda Environment
                    	getModel().setSudaCheck(true);
                    }
                    else {
                    	getModel().setSudaCheck(false);
                    }
                    if (request.getRequestURI().contains("AdminDashboard")) {
                        return new ModelAndView("DeptHomeEIPDashboard", MainetConstants.FORM_NAME, getModel());
                    } else {//Added Department dropdown for suda US-141152
                    	getModel().setDepartmentsList(
                                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),UserSession.getCurrent().getOrganisation().getOrgid()));
                        return new ModelAndView(MainetConstants.AdminHome.DEPT_HOME_EIP, MainetConstants.FORM_NAME, getModel());

                    }

                } else {
                    return new ModelAndView(MainetConstants.AdminHome.REDIRECT_DEPT_DOC_VERY);
                }

            } else {

                logger.error(ApplicationSession.getInstance().getMessage("admin.emp.dep.identify"));
                return new ModelAndView(MainetConstants.AdminHome.REDIRECT);
            }

        } catch (final Exception e) {

            logger.error(e.getMessage(), e);

            return new ModelAndView(MainetConstants.AdminHome.REDIRECT);
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public ModelAndView completeForm() {
        return new ModelAndView(MainetConstants.AdminHome.HOME_PAGE, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.GET, params = "EditUserProfile")
    public ModelAndView editUserProfile() {
        final Employee currentEmployee = UserSession.getCurrent().getEmployee();
        getModel().setCurrentEmpForEditProfile(currentEmployee);
        getModel().setEmpGender();
        if (currentEmployee.getEmpmname() == null) {
            getModel().setEmpNameForEditProfile(currentEmployee.getEmpname() + " " + currentEmployee.getEmplname());
        } else {
            getModel().setEmpNameForEditProfile(
                    currentEmployee.getEmpname() + " " + currentEmployee.getEmpmname() + " " + currentEmployee.getEmplname());
        }
        return new ModelAndView(MainetConstants.AdminHome.CITIZEN_EDIT_PROFILE, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.GET, params = "viewEmialId")
    public ModelAndView showEmailIdForm(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView(MainetConstants.AdminHome.CITIZEN_UPDATE_EMAIL, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "UpdateEmailID", method = RequestMethod.POST)
    public @ResponseBody String updateEmilId(final HttpServletRequest request,
            @RequestParam("updateEmailId") final String newMailId) {
        bindModel(request);
        getModel();
        String result = "Not";

        if ((newMailId != null) || (newMailId != "")) {
            if (!getModel().isUniqueEmailAddress(newMailId)) {
                result = ApplicationSession.getInstance().getMessage(MainetConstants.AdminHome.EMPLOYEE_UNIQUE);
            } else {

                result = getModel().setEmailId(newMailId);
            }
        }

        return result;
    }

    @RequestMapping(params = "CitizenEditSave", method = RequestMethod.POST)
    public ModelAndView editUserProfileSave(final HttpServletRequest request) {
        bindModel(request);
        final AdminHomeModel model = getModel();
        final Employee modifiedEmployee = model.getCurrentEmpForEditProfile();
        if (modifiedEmployee.getCpdTtlId() == 0L) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFFILE));
        }
        if (modifiedEmployee.getEmpGender().equals(MainetConstants.ZERO)) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USERPROFFILE_GENDER));
        } else {
            modifiedEmployee.setEmpGender(
                    model.getNonHierarchicalLookUpObject(new Long(modifiedEmployee.getEmpGender())).getLookUpCode());
        }
        if ((modifiedEmployee.getEmpemail() != null) && !(modifiedEmployee.getEmpemail().isEmpty())) {
            if (!(Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(modifiedEmployee.getEmpemail()).matches())) {
                model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USERPROFFILE_EMAIL));
            }
        }
        if (modifiedEmployee.getEmpname().isEmpty()) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_FNAME));
        }
        if (modifiedEmployee.getEmplname().isEmpty()) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_LNAME));
        }
        if (modifiedEmployee.getEmpdob() == null) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_DOB));
        }
        if (modifiedEmployee.getEmpAddress().isEmpty()) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_PERADD));
        }
        if (modifiedEmployee.getEmpCorAdd1().isEmpty()) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_CUR_ADDRESS));
        }
        if (modifiedEmployee.getEmppincode() == null) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_PINCODE));
        }
        if (modifiedEmployee.getEmpCorPincode() == null) {
            model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_CORRESS_PINCODE));
        }
        if (modifiedEmployee.getEmpCorPincode() != null) {
            final int pin = String.valueOf(modifiedEmployee.getEmpCorPincode()).length();
            if (pin < 6) {
                model.addValidationError(getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_LENGTH));
            }
        }
        if (modifiedEmployee.getEmppincode() != null) {
            final int pin = String.valueOf(modifiedEmployee.getEmppincode()).length();
            if (pin < 6) {
                model.addValidationError(
                        getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_PINCODE_LENGTH));
            }
        }

        if (!(modifiedEmployee.getEmpAddress().isEmpty()) && !(modifiedEmployee.getEmppincode() == null)
                && !(modifiedEmployee.getEmpCorAdd1().isEmpty()) && !(modifiedEmployee.getEmpCorPincode() == null)) {
            if (((!modifiedEmployee.getEmpAddress().equals(modifiedEmployee.getEmpCorAdd1()))
                    && (!modifiedEmployee.getEmppincode().equals(modifiedEmployee.getEmpCorPincode())))
                    || (!modifiedEmployee.getEmpAddress1().equals(modifiedEmployee.getEmpCorAdd2()))) {
                modifiedEmployee.setAddFlag(MainetConstants.Common_Constant.NO);
            } else {
                modifiedEmployee.setAddFlag(MainetConstants.Common_Constant.YES);
            }
        }
        if (!model.hasValidationErrors()) {
            try {
                model.saveEditedInformation(modifiedEmployee);

                return jsonResult(JsonViewObject.successResult(
                        getApplicationSession().getMessage(MainetConstants.AdminHome.USER_PROFILE_SUCESS_MSG)));
            } catch (final Throwable ex) {
                return jsonResult(JsonViewObject.failureResult(ex));
            }
        }
        return defaultMyResultEdit(model);
    }

    private ModelAndView defaultMyResultEdit(final AdminHomeModel model) {
        model.getAppSession().setLangId(UserSession.getCurrent().getLanguageId());

        final ModelAndView mv = new ModelAndView(MainetConstants.AdminHome.CITIZEN_PROFILE_VAL, MainetConstants.FORM_NAME, model);

        final BindingResult bindingResult = model.getBindingResult();

        if (bindingResult != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }

        return mv;
    }

    private boolean isRestrictClickJacking() {
        Boolean restrictClickJacking = false;
        final String restrictClickJackingCheck = getApplicationSession()
                .getMessage(MainetConstants.SECURITY_FLAG.CLICK_JACKING_CHECK);
        if ((restrictClickJackingCheck != null) && restrictClickJackingCheck.equals("Y")) {
            restrictClickJacking = true;
        }
        return restrictClickJacking;
    }

    @RequestMapping(method = RequestMethod.POST, params = "sessionHit")
    @ResponseBody
    public String SessionActivation() {
        return MainetConstants.HIT;
    }

    @RequestMapping(method = RequestMethod.GET, params = "editAgencyLoginUserProfile")
    public ModelAndView editAgencyLoginUserProfile() {
        final Employee currentEmployee = UserSession.getCurrent().getEmployee();
        getModel().setCurrentEmpForEditProfile(currentEmployee);
        getModel().setEmpGender();
        if (currentEmployee.getEmpmname() == null) {
            getModel().setEmpNameForEditProfile(currentEmployee.getEmpname() + " " + currentEmployee.getEmplname());
        } else {
            getModel().setEmpNameForEditProfile(
                    currentEmployee.getEmpname() + " " + currentEmployee.getEmpmname() + " " + currentEmployee.getEmplname());
        }
        return new ModelAndView(MainetConstants.AdminHome.Edit_login_profile, MainetConstants.FORM_NAME, getModel());
    }

}
