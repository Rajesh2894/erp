package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.Base64;
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
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.PasswordManager;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "request")
public class AdminResetPasswordModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -301987374919892976L;

    private String mobileNumber;
    private String oneTimePassword;
    private String confirmedPassword;
    private String newPassword;
    private String oldPassword;
    private String result;
    private String captchaSessionLoginValue;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    public boolean ifRegisteredEmployeeThenSendOTP(final String mobileNo) {
        final Organisation organisation = getUserSession().getOrganisation();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(mobileNo, organisation,
                MainetConstants.IsDeleted.ZERO,MainetConstants.ADMIN_EMP_TYPE);

        if ((employeeList == null) || (employeeList.size() == 0)) {
            return false;
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    oneTimePasswordStep2(employee);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean oneTimePasswordStep2(final Employee employee) {

        /*
         * if (employee.getAutMob().equals(MainetConstants.AUTH)) { employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE); }
         */
        final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
        System.err.println(MainetConstants.RESET_PASSWORD_OTP + otp);
        employee.setMobNoOtp(Utility.encryptPassword(employee.getEmploginname(), otp));
        iEmployeeService.updateEmployeeDetails(employee, getUserSession().getEmployee());
        this.sendSMSandEMail(employee, otp);

        return true;
    }

    /**
     * Send OTP via SMS and Email.
     * @param employee
     * @param otp
     */
    private void sendSMSandEMail(final Employee employee, final String otp) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(employee.getEmpemail());
        dto.setMobnumber(employee.getEmpmobno());
        dto.setServDur(employee.getEmpname());
        dto.setAppNo(otp);
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
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

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
                final Organisation organisation = getUserSession().getOrganisation();
                final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(), organisation,
                        MainetConstants.IsDeleted.ZERO,MainetConstants.ADMIN_EMP_TYPE);

                if ((employeeList != null) && (employeeList.size() != 0)) {
                    for (final Employee employee : employeeList) {
                        if (employee.getEmplType() == null) {
                            final Employee authEmployee = iEmployeeService.getAuthenticatedEmployee(employee.getEmploginname(),
                                    getOneTimePassword(), employee.getEmplType(), organisation, MainetConstants.IsDeleted.ZERO);

                            if (authEmployee != null) {
                                result = MainetConstants.SUCCESS;
                            } else {
                                result = getAppSession().getMessage("eip.citizen.forgotPassword.invalidOTP");
                                this.addValidationError(getAppSession().getMessage("eip.citizen.forgotPassword.invalidOTP"));
                            }
                        }
                    }
                }
            }
        } else {
            result = getAppSession().getMessage("citizen.login.otp.must.error");
            this.addValidationError(getAppSession().getMessage("citizen.login.otp.must.error"));
        }
        return result;
    }

    public Employee getEmployeeByMobile() {
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(),
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO,MainetConstants.ADMIN_EMP_TYPE);

        if ((employeeList != null) && (employeeList.size() > 0)) {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    return employee;
                }
            }
        }

        return null;
    }

    public boolean setPassword(final Employee employee) {
        boolean result = false;

        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
		employee.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));
		employee.setLoggedIn(MainetConstants.Common_Constant.NO);
        final Employee updateEmployee = iEmployeeService.setEmployeePassword(employee, empPassword,
                getUserSession().getEmployee());

        if (updateEmployee != null) {
            result = true;
            sendSuccessSMSandEMail(updateEmployee);
        }
        return result;
    }

    /**
     * Send Success message via SMS and Email.
     * @param employee
     * @param otp
     */
    private void sendSuccessSMSandEMail(final Employee employee) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(employee.getEmpemail());
        dto.setMobnumber(employee.getEmpmobno());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.RESET_PWD_SUCC,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
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

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCaptchaSessionLoginValue() {
		return captchaSessionLoginValue;
	}

	public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
		this.captchaSessionLoginValue = captchaSessionLoginValue;
	}
}
