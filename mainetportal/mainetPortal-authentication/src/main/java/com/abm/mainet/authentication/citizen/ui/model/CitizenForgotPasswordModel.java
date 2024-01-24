package com.abm.mainet.authentication.citizen.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.MenuRoleEntitlement;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.PasswordManager;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "request")
public class CitizenForgotPasswordModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 9147514992549410053L;

    private String mobileNumber;
    private String oneTimePassword;
    private String confirmedPassword;
    private String newPassword;
    private boolean accountLock;
    private String captchaSessionLoginValue;
    @Autowired
    private IEmployeeService iEmployeeService;

    private String mobileNumberType;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    /**
     *
     * @param citizenMobileNo
     * @return
     */
    public boolean ifRegisteredCitizenThenSendOTP(final String citizenMobileNo) {
        final Organisation organisation = ApplicationSession.getInstance().getSuperUserOrganization();
        final LookUp citizenLookUp = getCitizenLooUp();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(citizenMobileNo, organisation,
                MainetConstants.IsDeleted.ZERO,((citizenLookUp!=null)?citizenLookUp.getLookUpId():0));
       
        if ((employeeList == null) || (employeeList.size() == 0)) {
            return false;
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() != null) {
                    if (employee.getEmplType() == citizenLookUp.getLookUpId()) {
                        if (employee.getLockUnlock() == null) {
                            oneTimePasswordStep2(employee);
                            return true;
                        } else {
                            setAccountLock(true);
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     *
     * @param employee
     * @return
     */
    public boolean oneTimePasswordStep2(final Employee employee) {
        /*
         * if (employee.getAutMob().equals(MainetConstants.AUTH)) { employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE); }
         */
        final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
        System.err.println(MainetConstants.FORGOT_PASSWORD_OTP + otp);
        final String encryptOtp = Utility.encryptPassword(employee.getEmploginname(), otp);
        if (getMobileNumberType().equals(MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER)) {
            employee.setMobNoOtp(encryptOtp);
        } else {
            employee.setMobNoOtp(encryptOtp);
        }

        iEmployeeService.updateEmployeeDetails(employee, getUserSession().getEmployee());
        this.sendSMSandEMail(employee, otp, getMobileNumberType());
        return true;
    }

    /**
     * Send OTP via SMS and Email.
     * @param employee
     * @param otp
     */
    private void sendSMSandEMail(final Employee employee, final String otp, final String mobileNumberType) {

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(employee.getEmpemail());
        if (mobileNumberType.equals(MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER)) {
            dto.setMobnumber(getMobileNumber());
        } else {
            dto.setMobnumber(employee.getEmpmobno());
        }
        dto.setAppNo(otp);
        dto.setAppName(employee.getFullName());
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }

        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());

    }

    /**
     * From citizen login only NEC type employee can able to login. Method will check login request is given by an NEC type
     * employee.
     * @param emplTypeId
     * @return true/false
     */
    boolean checkNECType(final long emplTypeId) {
        final List<LookUp> lookUpList = super.getCitizenLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpId() == emplTypeId) {
                return true;
            }
        }
        return false;
    }

    /**
     * OTP verification.
     */
    public String verficationOfOTP() {
        String result = getAppSession().getMessage("eip.citizen.forgotPassword.otpVerificationFailed");

        if ((getOneTimePassword() != null) && (getOneTimePassword() != MainetConstants.BLANK)) {
            if ((getMobileNumber() != null) && !getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
                Employee authEmployee = null;
                final Employee emp = UserSession.getCurrent().getEmployee();
                Organisation organisation;
                //Check condition for dept User
                if (null != emp.getEmploginname() && !emp.getEmploginname().equalsIgnoreCase("NOUSER") && emp.getEmplType() == null) {
                    organisation = UserSession.getCurrent().getOrganisation();
                } else {
                    organisation = ApplicationSession.getInstance().getSuperUserOrganization();
                }
                if (getMobileNumberType().equals(MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER)) {

                    authEmployee = iEmployeeService.getAuthenticatedEmployee(emp.getEmploginname(), getOneTimePassword(),
                            emp.getEmplType(), organisation, MainetConstants.IsDeleted.ZERO, getMobileNumberType());

                } else {
                	 final LookUp citizenLookUp = getCitizenLooUp();
                    final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(), organisation,
                            MainetConstants.IsDeleted.ZERO,((citizenLookUp!=null)?citizenLookUp.getLookUpId():0));
                   
                    if ((employeeList != null) && (employeeList.size() != 0)) {
                        for (final Employee employee : employeeList) {
                            if (employee.getEmplType() != null) {
                                if (employee.getEmplType() == citizenLookUp.getLookUpId()) {
                                    authEmployee = iEmployeeService.getAuthenticatedEmployee(employee.getEmploginname(),
                                            getOneTimePassword(), employee.getEmplType(), organisation,
                                            MainetConstants.IsDeleted.ZERO, getMobileNumberType());
                                }
                            }
                        }
                    }

                }

                if (authEmployee != null) {
                    Date valiDateTime = null;
                    if (authEmployee.getUpdatedDate() != null) {
                    	valiDateTime = authEmployee.getUpdatedDate();
                    } else {
                    	valiDateTime = authEmployee.getOndate();
                    }

                    final boolean isValidPeriod = UtilityService.checkOTPValidityPeriod(valiDateTime,
                                        MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());

                    if (isValidPeriod == true) {
                    	result = MainetConstants.SUCCESS;
                    }else {
                    	result = getAppSession().getMessage("app.regisration.form.otp.expired");
                    	this.addValidationError(getAppSession().getMessage("app.regisration.form.otp.expired"));
                    	return result;
                    }
                } else {
                    result = getAppSession().getMessage("eip.citizen.forgotPassword.invalidOTP");
                }

            }
        } else {
            result = getAppSession().getMessage("citizen.login.otp.must.error");
        }
        return result;
    }

    public Employee getCitizenByMobile() {
        final LookUp citizenLookUp = getCitizenLooUp();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(),
                ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.IsDeleted.ZERO,((citizenLookUp!=null)?citizenLookUp.getLookUpId():0));
    
        if ((employeeList != null) && (employeeList.size() > 0)) {
            for (final Employee citizen : employeeList) {
                if (citizen.getEmplType() != null) {
                    if (citizen.getEmplType() == citizenLookUp.getLookUpId()) {
                        return citizen;
                    }
                }
            }
        }

        return null;
    }

    public boolean setPassword(final Employee citizen) {
        boolean result = false;

        Employee loggedInUser = getUserSession().getEmployee();
        if (getUserSession().getEmployee().getEmpId() == 0l) {
            final String loginName = getAppSession().getMessage("citizen.noUser.loginName");
            loggedInUser = super.getEmployeeByLoginName(loginName, ApplicationSession.getInstance().getSuperUserOrganization());
        }
        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
        citizen.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.CITIZEN.getPrifixCode()));
        final Employee updateEmployee = iEmployeeService.setEmployeePassword(citizen, empPassword, loggedInUser);

        if (updateEmployee != null) {
            result = true;
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return result;
    }

    public boolean setMobileNumber() {

        boolean result = false;

        final Employee updateEmployee = iEmployeeService.changeMobileNumber(UserSession.getCurrent().getEmployee(),
                getMobileNumber());

        if (updateEmployee != null) {
            result = true;
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return result;
    }

    /**
     *
     * @return LookUp Object Of Citizen
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
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the oneTimePassword
     */
    public String getOneTimePassword() {
        return oneTimePassword;
    }

    /**
     * @param oneTimePassword the oneTimePassword to set
     */
    public void setOneTimePassword(final String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    /**
     * @return the confirmedPassword
     */
    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    /**
     * @param confirmedPassword the confirmedPassword to set
     */
    public void setConfirmedPassword(final String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMobileNumberType() {
        return mobileNumberType;
    }

    public void setMobileNumberType(final String mobileNumberType) {
        this.mobileNumberType = mobileNumberType;
    }

    /**
     *
     * @param empMobileNo
     * @return {@value true} if mobile no is not already registered
     */
    public boolean isUniqueMobileNumber(final String empMobileNo) {
        boolean isUnique = false;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(empMobileNo,
                UserSession.getCurrent().getEmployee().getEmplType(), ApplicationSession.getInstance().getSuperUserOrganization(),
                MainetConstants.IsDeleted.ZERO, false);
        final LookUp lookUp = getCitizenLooUp();
        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() != null) {
                    if (employee.getEmplType() == lookUp.getLookUpId()) {
                        return false;
                    } else {
                        isUnique = true;
                    }
                } else {
                    isUnique = true;
                }
            }

            return isUnique;
        }

    }

    public void accessListAndMenuForCitizen(final Employee employee) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(employee.getGmid(), orgId);// 3287L hard coded

    }

    public boolean isAccountLock() {
        return accountLock;
    }

    public void setAccountLock(final boolean accountLock) {
        this.accountLock = accountLock;
    }

	public String getCaptchaSessionLoginValue() {
		return captchaSessionLoginValue;
	}

	public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
		this.captchaSessionLoginValue = captchaSessionLoginValue;
	}
}
