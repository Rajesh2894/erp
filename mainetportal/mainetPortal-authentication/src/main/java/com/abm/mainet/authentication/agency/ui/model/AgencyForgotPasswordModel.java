package com.abm.mainet.authentication.agency.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
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

/**
 *
 * @author vivek.kumar
 *
 */

@Component
@Scope(value = "session")
public class AgencyForgotPasswordModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -6545773849026007663L;

    private String mobileNumber;
    private String oneTimePassword;
    private String confirmedPassword;
    private String newPassword;
    private long empType;
    private Employee agencyEmployee;
    private boolean accountLock;
    private String mobileNumberType;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    /**
     *
     * @param agencyMobileNo
     * @return {@value true} if Agency is already registered.
     */
    public boolean ifRegisteredAgencyThenSendOTP(final String agencyMobileNo) {

        final Employee registeredAgency = iEmployeeService.getAgencyByEmplTypeAndMobile(agencyMobileNo,
                getAgencyEmployee().getEmplType(), getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (registeredAgency != null) {
            if (registeredAgency.getLockUnlock() == null) {
                oneTimePasswordStep2(registeredAgency);
                return true;

            } else {
                setAccountLock(true);
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
        if (employee.getAutMob().equals(MainetConstants.AUTH)) {
            employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
        }
        final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
        System.err.println("otp forgot is :" + otp);
        final String encryptOtp = Utility.encryptPassword(employee.getEmploginname(), otp);
        if (getMobileNumberType().equals(MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER)) {
            employee.setMobNoOtp(encryptOtp);
        } else {
            employee.setEmppassword(encryptOtp);
        }

        iEmployeeService.updatedAgencyEmployeeDetails(employee);
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

        if (MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER.equals(mobileNumberType)) {
            dto.setMobnumber(getMobileNumber());
        } else {
            dto.setMobnumber(employee.getEmpmobno());
        }

        dto.setAppNo(otp);
        dto.setAppName(employee.getFullName());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
    }

    /**
     * To check valid citizen.
     * @param citizen
     * @return true/false
     */
    boolean isAgencyEmployee(final Employee agency) {
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
     */
    private boolean checkNECType(final long emplTypeId) {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);
        final LookUp citizenLookUp = getCitizenLooUp();
        for (final LookUp lookUp : lookUpList) {
            if ((emplTypeId != citizenLookUp.getLookUpId()) && (lookUp.getLookUpId() == emplTypeId)) {
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
                final Organisation organisation = UserSession.getCurrent().getOrganisation();
                if (MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER.equals(getMobileNumberType())) {

                    final Employee emp = UserSession.getCurrent().getEmployee();
                    authEmployee = iEmployeeService.getAuthenticatedAgencyEmployee(emp.getEmploginname(), getOneTimePassword(),
                            emp.getEmplType(), organisation, MainetConstants.IsDeleted.ZERO, getMobileNumberType());
                } else {
                    final Employee agency = iEmployeeService.getAgencyByEmplTypeAndMobile(getMobileNumber(),
                            getUserSession().getEmplType(), getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
                    if (agency != null) {
                        authEmployee = iEmployeeService.getAuthenticatedAgencyEmployee(agency.getEmploginname(),
                                getOneTimePassword(), agency.getEmplType(), getUserSession().getOrganisation(),
                                MainetConstants.IsDeleted.ZERO, getMobileNumberType());
                        if (authEmployee != null) {
                            result = MainetConstants.SUCCESS;
                        } else {
                            result = getAppSession().getMessage("eip.citizen.forgotPassword.invalidOTP");
                        }
                    }
                }

                if (authEmployee != null) {
                    result = MainetConstants.SUCCESS;
                } else {
                    result = getAppSession().getMessage("eip.citizen.forgotPassword.invalidOTP");
                }
            }
        } else {
            result = getAppSession().getMessage("citizen.login.otp.must.error");
        }

        return result;

    }

    public Employee getAgencyByMobile() {
        final Long emplType = agencyEmployee.getEmplType();
        final Employee agency = iEmployeeService.getAgencyByEmplTypeAndMobile(getMobileNumber(), emplType,
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (agency != null) {
            return agency;
        }
        return null;
    }

    public boolean setPassword(final Employee agency) {
        boolean result = false;

        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
		agency.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(MainetConstants.PasswordValidType.AGENCY.getPrifixCode()));
        final Employee updateEmployee = iEmployeeService.setEmployeePassword(agency, empPassword, getUserSession().getEmployee());

        if (updateEmployee != null) {
            result = true;
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return result;
    }

    public LookUp getCitizenLooUp() {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
                return lookUp;
            }
        }
        return null;
    }

    /**
     *
     * @param empMobileNo
     * @return {@value true} if mobile no is not already registered
     */
    public boolean isUniqueMobileNumber(final String empMobileNo) {

        boolean isUnique = false;
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final long empltype = UserSession.getCurrent().getEmployee().getEmplType();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(empMobileNo, empltype, organisation,
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

    public long getEmpType() {
        return empType;
    }

    public void setEmpType(final long empType) {
        this.empType = empType;
    }

    public Employee getAgencyEmployee() {
        return agencyEmployee;
    }

    public void setAgencyEmployee(final Employee agencyEmployee) {
        this.agencyEmployee = agencyEmployee;
    }

    public String getMobileNumberType() {
        return mobileNumberType;
    }

    public void setMobileNumberType(final String mobileNumberType) {
        this.mobileNumberType = mobileNumberType;
    }

    public boolean isAccountLock() {
        return accountLock;
    }

    public void setAccountLock(final boolean accountLock) {
        this.accountLock = accountLock;
    }
}
