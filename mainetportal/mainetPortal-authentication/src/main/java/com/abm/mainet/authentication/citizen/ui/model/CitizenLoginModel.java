/**
 *
 */
package com.abm.mainet.authentication.citizen.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.authentication.ldap.IAuthenticationManager;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.MenuRoleEntitlement;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class CitizenLoginModel extends AbstractFormModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2062751157228090923L;

    private Employee citizenEmployee;
    private String mode;
    private String isEmailIDASUserId;
    private String empShowPassword;
    private String captchaSessionLoginValue;
    private String serviceFlag;
    private Long OrgId;

    private static final Logger LOG = Logger.getLogger(CitizenLoginModel.class);
    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    @Autowired
    ISMSAndEmailService ismsAndEmailService;
    @Autowired
    IOrganisationService iOrganisationService;

    @Autowired
    @Qualifier("authManager")
    private IAuthenticationManager authManager;

    @Autowired
    private IEntitlementService iEntitlementService;

    private List<Organisation> orgnisationList;

    /**
     * @see com.abm.mainet.ui.model.AbstractModel#initializeModel()
     */

    /**
     * This method will validate employee(CITIZEN) by email is Is-EmailID-AS-UserId flag is true and
     * @return Logged Employee
     * @throws Exception 
     */
    public Employee validateCitizenLogin() throws Exception {

        final String userId = getCitizenEmployee().getEmploginname();
        String empPassword;
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)){
        	empPassword = EncryptionAndDecryption.decrypt(getCitizenEmployee().getEmppassword(), UserSession.getCurrent().getUniqueKeyId());
        }else {
        	empPassword = new String(Base64.getDecoder().decode(getCitizenEmployee().getEmppassword()));
        }
        final String empEncryptPassword = Utility.encryptPassword(userId, empPassword);
        Employee validateEmployee = null;
        if (!authManager.authenticateUser(userId, empEncryptPassword)) {
            return validateEmployee;
        }

        final Organisation organisation = ApplicationSession.getInstance().getSuperUserOrganization();

        final LookUp citizeLookUp = getCitizenLooUp();
        if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(userId).matches()) {
            final List<Employee> employeeListByEmpEMail = iEmployeeService.getEmployeeByEmpEMailAndType(userId,
                    getUserSession().getEmployee().getEmplType(), organisation, MainetConstants.IsDeleted.ZERO, false);

            if (employeeListByEmpEMail.size() == 1) {
                if (employeeListByEmpEMail.get(0).getEmplType() != null) {
                    if (employeeListByEmpEMail.get(0).getEmplType() == citizeLookUp.getLookUpId()) {
                        validateEmployee = employeeListByEmpEMail.get(0);
                    }
                }
            } else {
                for (final Employee employee : employeeListByEmpEMail) {
                    if (employee.getEmplType() != null) {
                        if (employee.getEmplType() == citizeLookUp.getLookUpId()) {
                            validateEmployee = employee;
                            break;
                        }
                    }
                }
            }

        } else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(userId).matches()) {

            final List<Employee> employeeListByEmpMob = iEmployeeService.getEmployeeByEmpMobileNoAndType(userId,
                    getUserSession().getEmployee().getEmplType(), organisation, MainetConstants.IsDeleted.ZERO, false);
            if (employeeListByEmpMob.size() == 1) {
                if (employeeListByEmpMob.get(0).getEmplType() != null) {
                    if (employeeListByEmpMob.get(0).getEmplType() == citizeLookUp.getLookUpId()) {
                        validateEmployee = employeeListByEmpMob.get(0);
                    }                   
                }

            } else {
                for (final Employee employee : employeeListByEmpMob) {
                    if (employee.getEmplType() != null) {
                        if (employee.getEmplType() == citizeLookUp.getLookUpId()) {
                            validateEmployee = employee;
                            break;
                        }
                    }
                }
            }

        }

        else {

            final List<Employee> empListByLoginName = iEmployeeService.getEmployeeListByLoginName(userId, organisation,
                    MainetConstants.IsDeleted.ZERO);
            if (!empListByLoginName.isEmpty()) {
                if (empListByLoginName.size() == 1) {
                    if (empListByLoginName.get(0).getEmplType() != null) {
                        if (empListByLoginName.get(0).getEmplType() == citizeLookUp.getLookUpId()) {
                            validateEmployee = empListByLoginName.get(0);
                        }
                    }
                } else {
                    for (final Employee employee : empListByLoginName) {
                        if (employee.getEmplType() != null) {
                            if (employee.getEmplType() == citizeLookUp.getLookUpId()) {
                                validateEmployee = employee;
                                break;
                            }
                        }
                    }
                }
            } else {
                final Employee employeeByAlias = iEmployeeService.getEmployeeByUserAlias(userId, organisation,
                        MainetConstants.IsDeleted.ZERO);
                validateEmployee = employeeByAlias;
            }

        }

        if (validateEmployee != null) {
            final Long emplType = validateEmployee.getEmplType();
            final String empLoginName = validateEmployee.getEmploginname();
            final String empEncryptPassword1 = Utility.encryptPassword(validateEmployee.getEmploginname(), empPassword);
            boolean loginStatus = false;
            if (validateEmployee.getAutMob().equals("Y")) {
                if (empLoginName != null) {
                    // Query to get user details
                    validateEmployee = iEmployeeService.getAdminEncryptAuthenticatedEmployee(empLoginName, null,
                            emplType, validateEmployee.getEmpId(), ApplicationSession.getInstance().getSuperUserOrganization(),
                            MainetConstants.IsDeleted.ZERO);
                    // if user password got matches with entered encrypted password
                    if (validateEmployee.getEmppassword().equals(empEncryptPassword1)) {
                        validateEmployee.setLoggedInAttempt(null);
                        loginStatus = true;

                    } else {
                        // if password not match increment login attempt
                        Integer maxLoginAttempts = null;
                        try {
                            maxLoginAttempts = Integer.valueOf(getAppSession().getMessage("citizen.max.attempts").trim());
                        } catch (final NumberFormatException e) {
                            LOG.error("Parsing error citizen.max.attempts", e);
                            maxLoginAttempts = 3;
                        }
                        if ((null == validateEmployee.getLoggedInAttempt())
                                || (validateEmployee.getLoggedInAttempt() < maxLoginAttempts)) {
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
                    	throw new Exception("Password Wrong");
                        
                    }
                }
            }
        }

        return validateEmployee;
    }

    /**
     *
     * @return
     */
    public LookUp getCitizenLooUp() {
        final List<LookUp> lookUpList = super.getCitizenLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
                return lookUp;
            }
        }
        return null;
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

    /**
     * From citizen login only NEC type employee can able to login. Method will check login request is given by an NEC type
     * employee.
     * @param emplTypeId
     * @return true/false
     * @since Add on 14 Feb 2014 as per instruction given.
     */
    private boolean checkNECType(final long emplTypeId) {
        final List<LookUp> lookUpList = super.getCitizenLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpId() == emplTypeId) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param loggedEmployee: send OTP again if user has'nt set own password
     */
    public void sendOTPAgain(final Employee loggedEmployee) {
        final String newOTPPassword = iCitizenRegistrationProcessService
                .generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
        loggedEmployee.setMobNoOtp(Utility.encryptPassword(loggedEmployee.getEmploginname(), newOTPPassword));
        final Employee savedEmployee = iEmployeeService.updateEmployeeDetails(loggedEmployee,
                UserSession.getCurrent().getEmployee());
        // Employee savedEmployee=iEmployeeService.saveEmployee(loggedEmployee);
        if ((savedEmployee != null) && savedEmployee.getAutMob().equalsIgnoreCase("N")) {
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
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();

        final String empName = replaceNull(registeredEmployee.getEmpname()) + MainetConstants.WHITE_SPACE
                + replaceNull(registeredEmployee.getEmpMName())
                + MainetConstants.WHITE_SPACE + replaceNull(registeredEmployee.getEmpLName());

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

        getBindingResult().addError(new ObjectError("", getAppSession().getMessage("Employee.registrationDone")));
    }

    public boolean isOTPVerificationDone(final Employee validEmployee) {
        if ((validEmployee.getAutMob() != null) && !validEmployee.getAutMob().equalsIgnoreCase("N")) {
            return true;
        } else {
            getBindingResult().addError(new ObjectError("", "OTP Verification Not Done."));
            return false;
        }
    }

    public String redirectToOTP() {
        final String redirectTo = getAppSession().getMessage("citizen.OTPVerification.URL");

        return redirectTo.trim();
    }

    public String redirectSuccess() {
        final String redirectTo = getAppSession().getMessage("citizen.login.success.redirectURL");
        return redirectTo.trim();
    }

    /**
     * @return the citizenEmployee
     */
    public Employee getCitizenEmployee() {
        return citizenEmployee;
    }

    /**
     * @param citizenEmployee the citizenEmployee to set
     */
    public void setCitizenEmployee(final Employee citizenEmployee) {
        this.citizenEmployee = citizenEmployee;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }

    /**
     * @return the isEmailIDASUserId
     */
    public String getIsEmailIDASUserId() {
        return isEmailIDASUserId;
    }

    /**
     * @param isEmailIDASUserId the isEmailIDASUserId to set
     */
    public void setIsEmailIDASUserId(final String isEmailIDASUserId) {
        this.isEmailIDASUserId = isEmailIDASUserId;
    }

    public String getCaptchaSessionLoginValue() {
        return captchaSessionLoginValue;
    }

    public void setCaptchaSessionLoginValue(final String captchaSessionLoginValue) {
        this.captchaSessionLoginValue = captchaSessionLoginValue;
    }

    public String getEmpShowPassword() {
        return empShowPassword;
    }

    public void setEmpShowPassword(final String empShowPassword) {
        this.empShowPassword = empShowPassword;
    }

    public String getEncryptData() {

        final String userId = getCitizenEmployee().getEmploginname();
        final String empPassword = getCitizenEmployee().getEmppassword();
        final Organisation organisation = ApplicationSession.getInstance().getSuperUserOrganization();
        Employee validateEmployee = null;
        final LookUp citizeLookUp = getCitizenLooUp();
        if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(userId).matches()) {
            final List<Employee> employeeListByEmpEMail = iEmployeeService.getEmployeeByEmpEMailAndType(userId,
                    citizeLookUp.getLookUpId(), organisation, MainetConstants.IsDeleted.ZERO, false);

            if (employeeListByEmpEMail.size() == 1) {
                if (employeeListByEmpEMail.get(0).getEmplType() != null) {
                    if (employeeListByEmpEMail.get(0).getEmplType() == citizeLookUp.getLookUpId()) {
                        validateEmployee = employeeListByEmpEMail.get(0);
                    }else {
                    	LOG.error("Lookup ID employee: " + employeeListByEmpEMail.get(0).getEmplType());
                    	LOG.error("Lookup ID lookup: " + citizeLookUp.getLookUpId());
                    }
                }
            } else {
                for (final Employee employee : employeeListByEmpEMail) {
                    if (employee.getEmplType() != null) {
                        if (employee.getEmplType() == citizeLookUp.getLookUpId()) {
                            validateEmployee = employee;
                            break;
                        }
                    }
                }
            }

        } else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(userId).matches()) {

            final List<Employee> employeeListByEmpMob = iEmployeeService.getEmployeeByEmpMobileNoAndType(userId,
                    citizeLookUp.getLookUpId(), organisation, MainetConstants.IsDeleted.ZERO, false);
            if (employeeListByEmpMob.size() == 1) {
                if (employeeListByEmpMob.get(0).getEmplType() != null) {
                    if (employeeListByEmpMob.get(0).getEmplType() == citizeLookUp.getLookUpId()) {
                        validateEmployee = employeeListByEmpMob.get(0);
                    }else {
                    	LOG.error("Lookup ID employee: " + employeeListByEmpMob.get(0).getEmplType());
                    	LOG.error("Lookup ID lookup: " + citizeLookUp.getLookUpId());
                    }
                }

            } else {
                for (final Employee employee : employeeListByEmpMob) {
                    if (employee.getEmplType() != null) {
                        if (employee.getEmplType() == citizeLookUp.getLookUpId()) {
                            validateEmployee = employee;
                            break;
                        }
                    }
                }
            }

        }

        else {

            final List<Employee> empListByLoginName = iEmployeeService.getEmployeeListByLoginName(userId, organisation,
                    MainetConstants.IsDeleted.ZERO);
            if (!empListByLoginName.isEmpty()) {
                if (empListByLoginName.size() == 1) {
                    if (empListByLoginName.get(0).getEmplType() != null) {
                        if (empListByLoginName.get(0).getEmplType() == citizeLookUp.getLookUpId()) {
                            validateEmployee = empListByLoginName.get(0);
                        }
                    }
                } else {
                    for (final Employee employee : empListByLoginName) {
                        if (employee.getEmplType() != null) {
                            if (employee.getEmplType() == citizeLookUp.getLookUpId()) {
                                validateEmployee = employee;
                                break;
                            }
                        }
                    }
                }
            } else {
                final Employee employeeByAlias = iEmployeeService.getEmployeeByUserAlias(userId, organisation,
                        MainetConstants.IsDeleted.ZERO);
                validateEmployee = employeeByAlias;
            }
        }

        if (validateEmployee != null) {
            /*
             * if("1".equalsIgnoreCase(validateEmployee.getHasAapleUSer())){ return Utility.getMD5(empPassword); }else{
             */
            return Utility.encryptPassword(validateEmployee.getEmploginname(), empPassword);
            // }
        } else {
            return "";
        }
    }

    public void accessListAndMenuForCitizen(final Employee employee) {
        //
        /*
         * String orderByClause = MainetConstants.MENU.NAMEENG; if (UserSession.getCurrent().getLanguageId() == 2) orderByClause =
         * MainetConstants.MENU.NAMEREG;
         */
    	
    	  if (UserSession.getCurrent().getOrganisation() != null) {
	    	final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        final Long groupid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.DEFAULT_CITIZEN,
	                orgId);
	        MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(groupid != null ? groupid : employee.getGmid(), orgId);// 3287L
                                                                                                                   // hard
    	  }                                                                                                                 // coded
        /*
         * Set<RoleEntitlement> existList=new LinkedHashSet<RoleEntitlement>();
         * existList.addAll(MenuRoleEntitlement.getCurrentMenuRoleManager().getChildList());
         * existList.addAll(iEntitlementService.getExistTemplateChild(3287L,Arrays.asList(MainetConstants.MENU.SUB_FUNCTION_LIST),
         * orgId,orderByClause)); List<String> accessList=new ArrayList<String>(); for (RoleEntitlement roleEntitlement :
         * existList) { String str=roleEntitlement.getEntitle().getAction(); if(str != null && !(str.equals(""))){
         * if(str.contains("?")) { str=str.substring(0,str.indexOf("?")); } accessList.add(str); } }
         * UserSession.getCurrent().setUserAccessList(accessList);
         */
    }

    private String replaceNull(String name) {
        if (name == null) {
            name = MainetConstants.BLANK;
        }
        return name;
    }
    /*
    *//**
        * @return the organisationsList
        */
    /*
     * public List<Organisation> getOrganisationsList() { return iOrganisationService.findAllActiveOrganization("A"); }
     *//**
        * @param organisationsList the organisationsList to set
        *//*
           * public void setOrganisationsList(final List<Organisation> organisationsList) {
           * iOrganisationService.findAllActiveOrganization("A"); }
           */

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return OrgId;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long OrgId) {
        this.OrgId = OrgId;
    }

    public String getServiceFlag() {
        return serviceFlag;
    }

    public void setServiceFlag(String serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    public List<Organisation> getOrgnisationList() {
        return orgnisationList;
    }

    public void setOrgnisationList(List<Organisation> orgnisationList) {
        this.orgnisationList = orgnisationList;
    }
    public boolean saveEmployeeSession() {
		EmployeeSession empSession = new EmployeeSession();
		empSession.setDateOfAction(new Date());
		empSession.setLoginDate(new Date());
		//empSession.setLogOutDate(new Date());
		if (UserSession.getCurrent().getOrganisation() != null)
			empSession.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		empSession.setTransMode("P");
		if (UserSession.getCurrent().getEmployee() != null)
			empSession.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		return iEmployeeService.saveEmployeeSession(empSession);

	}
}
