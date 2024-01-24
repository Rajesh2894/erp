package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.authentication.ldap.IAuthenticationManager;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.MenuRoleEntitlement;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class AdminLoginModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -5566262411904099203L;

    private Employee adminEmployee;
    private String captchaSessionLoginValue;
    private String showDashboard;
    @Autowired
    @Qualifier("authManager")
    private IAuthenticationManager authManager;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private IEntitlementService iEntitlementService;

    /**
     *
     * @param empLoginString
     * @param empPassword
     * @return Employee Object if user provide valid credential
     */
    public Employee validateEmployee(final String empLoginString, String empPassword) {
        Employee validateEmployee = null;
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)){
        	empPassword = EncryptionAndDecryption.decrypt(empPassword, UserSession.getCurrent().getUniqueKeyId());
        }else {
    		empPassword = new String(Base64.getDecoder().decode(empPassword));
        }
        String empEncryptPassword = Utility.encryptPassword(empLoginString, empPassword);
        if (!authManager.authenticateUser(empLoginString, empPassword)) {
            return validateEmployee;
        }
        if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(empLoginString).matches()) {
            final List<Employee> employeeListByEmpEMail = iEmployeeService.getEmployeeByEmpEMailAndType(empLoginString,
                    getUserSession().getEmployee().getEmplType(), getUserSession().getOrganisation(),
                    MainetConstants.IsDeleted.ZERO, false);
            if (employeeListByEmpEMail.size() == 1) {
                validateEmployee = employeeListByEmpEMail.get(0);
            } else {
                for (final Employee employee : employeeListByEmpEMail) {
                    if (employee.getEmplType() == null) {
                        validateEmployee = employee;
                    }
                }
            }

        } else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(empLoginString).matches()) {

            final List<Employee> employeeListByEmpMob = iEmployeeService.getEmployeeByEmpMobileNoAndType(empLoginString,
                    getUserSession().getEmployee().getEmplType(), getUserSession().getOrganisation(),
                    MainetConstants.IsDeleted.ZERO, false);
            if (employeeListByEmpMob.size() == 1) {
                validateEmployee = employeeListByEmpMob.get(0);
            } else {
                for (final Employee employee : employeeListByEmpMob) {
                    if (employee.getEmplType() == null) {
                        validateEmployee = employee;
                    }
                }
            }

        }

        else {

            final List<Employee> empListByLoginName = iEmployeeService.getEmployeeListByLoginName(empLoginString,
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
            if (empListByLoginName.size() == 1) {
                validateEmployee = empListByLoginName.get(0);
            } else {
                for (final Employee employee : empListByLoginName) {
                    if (employee.getEmplType() == null) {
                        validateEmployee = employee;
                    }
                }
            }

        }

        if (validateEmployee != null) {
            final Long emplType = validateEmployee.getEmplType();
            final String empLoginName = validateEmployee.getEmploginname();
            empEncryptPassword = Utility.encryptPassword(empLoginName, empPassword);
            boolean loginStatus = false;
            if (validateEmployee.getAutMob().equals(MainetConstants.AUTH)) {
                if (empLoginName != null) {
                    // Query to get user details
                    validateEmployee = iEmployeeService.getAdminEncryptAuthenticatedEmployee(empLoginName, null, emplType,
                            validateEmployee.getEmpId(), UserSession.getCurrent().getOrganisation(),
                            MainetConstants.IsDeleted.ZERO);
                    // if user password got matches with entered encrypted password
                    if (validateEmployee.getEmppassword().equals(empEncryptPassword)) {
                        validateEmployee.setLoggedInAttempt(null);
                        loginStatus = true;

                    } else {
                        // if password not match increment login attempt
                        Integer maxLoginAttempts = null;
                        try {
                            maxLoginAttempts = Integer.valueOf(getAppSession().getMessage("citizen.max.attempts").trim());
                        } catch (NumberFormatException e) {
                            maxLoginAttempts = 3;
                        }
                        if (null == validateEmployee.getLoggedInAttempt()
                                || validateEmployee.getLoggedInAttempt() < maxLoginAttempts) {
                            validateEmployee.setLoggedInAttempt(
                                    validateEmployee.getLoggedInAttempt() != null ? validateEmployee.getLoggedInAttempt() + 1
                                            : 1);
                            loginStatus = false;
                        } else {
                            // lock account if max attempted reached.
                            if (null == validateEmployee.getLockUnlock()) {
                                validateEmployee.setLockUnlock("L");
                                validateEmployee.setLockDate(new Date());
                            }
                            loginStatus = true;
                        }

                    }

                    // UPdate Employee attempt count and lock unlock status
                    iEmployeeService.updateEmpDetails(validateEmployee);

                    // Login status is false return null as expected in case of failed attempted
                    if (loginStatus == false) {
                        validateEmployee = null;
                    }
                }
            }
        }

        return validateEmployee;

    }

    /**
     * To check valid citizen.
     * @param citizen
     * @return true/false
     */
    public boolean isCitizenEmployee(final Employee citizen) {
        if ((citizen.getEmplType() != null) && (citizen.getEmplType() != 0l) && checkNECType(citizen.getEmplType())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAgencyEmployee(final Employee agency) {
        if ((agency.getEmplType() != null) && (agency.getEmplType() != 0l) && checkNECType(agency.getEmplType())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * From citizen login only NEC type employee can able to login. Method will check login request is given by an NEC type
     * employee.
     * @param emplTypeId
     * @return true/false
     * @since Add on 14 Feb 2014 as per instruction given.
     */
    private boolean checkNECType(final long emplTypeId) {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpId() == emplTypeId) {
                return true;
            }
        }
        return false;
    }

    public boolean isOTPVerificationDone(final Employee validEmployee) {
        if ((validEmployee.getAutMob() != null) && !validEmployee.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)) {
            return true;
        } else {
            getBindingResult()
                    .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("otp.verification.not.done")));
            return false;
        }
    }

    /**
     *
     * @param loggedEmployee
     */
    public void sendOTPAgain(final Employee loggedEmployee) {
        if (loggedEmployee.getAutMob().equals("Y")) {
            loggedEmployee.setAutMob("N");
        }
        final String newOTPPassword = iCitizenRegistrationProcessService
                .generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
        loggedEmployee.setMobNoOtp(Utility.encryptPassword(loggedEmployee.getEmploginname(), newOTPPassword));
        final Employee savedEmployee = iEmployeeService.updateEmployeeDetails(loggedEmployee,
                UserSession.getCurrent().getEmployee());
        if ((savedEmployee != null) && savedEmployee.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)) {
            this.sendSMSandEMail(loggedEmployee, newOTPPassword);
            UserSession.getCurrent().setMobileNoToValidate(loggedEmployee.getEmpmobno());

        }

    }

    /**
     *
     * @param registeredEmployee
     * @param newAutoGeneratePwd
     */
    private void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd) {
        String empName = replaceNull(registeredEmployee.getEmpname()) + MainetConstants.WHITE_SPACE
                + replaceNull(registeredEmployee.getEmpMName())
                + MainetConstants.WHITE_SPACE + replaceNull(registeredEmployee.getEmpLName());

        empName = empName.trim();

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppNo(newAutoGeneratePwd);
        dto.setAppName(empName);
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }

        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());

        getBindingResult()
                .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("Employee.registrationDone")));
    }

    public String redirectToOTP() {
        final String redirectTo = getAppSession().getMessage("citizen.OTPVerification.URL");

        return redirectTo.trim();
    }

    public void setUserSession(final Employee loggedEmployee) {
        final UserSession userSession = getUserSession();
        userSession.setEmployee(loggedEmployee);
    }

    public String redirectTo(final Employee loggedEmployee) {
        final String redirectTo = getAppSession().getMessage("admin.login.success.redirectURL");
        return redirectTo.trim();
    }

    /**
     * @return the adminEmployee
     */
    public Employee getAdminEmployee() {
        return adminEmployee;
    }

    /**
     * @param adminEmployee the adminEmployee to set
     */
    public void setAdminEmployee(final Employee adminEmployee) {
        this.adminEmployee = adminEmployee;
    }

    public String getEncryptData() {

        final String userId = getAdminEmployee().getEmploginname();
        final String empPassword = getAdminEmployee().getEmppassword();

        Employee validateEmployee = null;

        if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(userId).matches()) {
            final List<Employee> employeeListByEmpEMail = iEmployeeService.getEmployeeByEmpEMailAndType(userId,
                    getUserSession().getEmployee().getEmplType(), getUserSession().getOrganisation(),
                    MainetConstants.IsDeleted.ZERO, false);
            if (employeeListByEmpEMail.size() == 1) {
                validateEmployee = employeeListByEmpEMail.get(0);
            } else {
                for (final Employee employee : employeeListByEmpEMail) {
                    if (employee.getEmplType() == null) {
                        validateEmployee = employee;
                    }
                }
            }

        } else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(userId).matches()) {

            final List<Employee> employeeListByEmpMob = iEmployeeService.getEmployeeByEmpMobileNoAndType(userId,
                    getUserSession().getEmployee().getEmplType(), getUserSession().getOrganisation(),
                    MainetConstants.IsDeleted.ZERO, false);
            if (employeeListByEmpMob.size() == 1) {
                validateEmployee = employeeListByEmpMob.get(0);
            } else {
                for (final Employee employee : employeeListByEmpMob) {
                    if (employee.getEmplType() == null) {
                        validateEmployee = employee;
                    }
                }
            }

        }

        else {
            final List<Employee> empListByLoginName = iEmployeeService.getEmployeeListByLoginName(userId,
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
            if (empListByLoginName.size() == 1) {
                validateEmployee = empListByLoginName.get(0);
            } else {
                for (final Employee employee : empListByLoginName) {
                    if (employee.getEmplType() == null) {
                        validateEmployee = employee;
                    }
                }
            }

        }
        if (validateEmployee != null) {
            return Utility.encryptPassword(validateEmployee.getEmploginname(), empPassword);
        } else {
            return Utility.encryptPassword(userId, empPassword);
        }

    }

    public void accessListAndMenuForAdmin(final Employee loggedEmployee) {

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        if (loggedEmployee.getGmid() != null) {
            MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(loggedEmployee.getGmid(), organisation.getOrgid()); // 3286L
        } else {
            final Long groupId = iEntitlementService.getGroupIdByName(MainetConstants.MENU.PORTAL_LOGIN, organisation.getOrgid());
            MenuRoleEntitlement.getCurrentMenuRoleManager().getSpecificMenuList(groupId, organisation.getOrgid());
        }
    }

    private String replaceNull(String name) {
        if (name == null) {
            name = MainetConstants.BLANK;
        }
        return name;
    }

    public String getCaptchaSessionLoginValue() {
        return captchaSessionLoginValue;
    }

    public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
        this.captchaSessionLoginValue = captchaSessionLoginValue;
    }

	public String getShowDashboard() {
		return showDashboard;
	}

	public void setShowDashboard(String showDashboard) {
		this.showDashboard = showDashboard;
	}
    

}
