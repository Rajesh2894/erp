package com.abm.mainet.authentication.agency.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;
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

/**
 *
 * @author vivek.kumar
 *
 */

@Component
@Scope(value = "request")
public class AgencyResetPasswordModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -1154829795830312838L;
    private static final Logger LOG = Logger.getLogger(AgencyResetPasswordModel.class);

    private String mobileNumber;
    private String oneTimePassword;
    private String confirmedPassword;
    private String newPassword;
    private String oldPassword;
    private Employee agencyEmployee;
    private long empType;
    private boolean accountLock;
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

        final Employee agency = iEmployeeService.getAgencyByEmplTypeAndMobile(agencyMobileNo, getAgencyEmployee().getEmplType(),
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (agency != null) {
            if (agency.getLockUnlock() == null) {
                oneTimePasswordStep2(agency);
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
        LOG.debug("reset password otp : " + otp);
        employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), otp));
        iEmployeeService.updatedAgencyEmployeeDetails(employee);
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
        dto.setAppName(employee.getEmpname());
        dto.setAppNo(otp);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
    }

    /**
     * To check valid agency.
     * @param agency
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
     * From agency login only NEC type employee can able to login. Method will check login request is given by an NEC type
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
                final Organisation organisation = getUserSession().getOrganisation();
                final Employee agency = iEmployeeService.getAgencyByEmplTypeAndMobile(getMobileNumber(),
                        getUserSession().getEmplType(), organisation, MainetConstants.IsDeleted.ZERO);
                if (agency != null) {
                    final Employee authAgency = iEmployeeService.getAuthenticatedEmployee(agency.getEmploginname(),
                            getOneTimePassword(), agency.getEmplType(), organisation, MainetConstants.IsDeleted.ZERO);
                    if (authAgency != null) {
                        result = MainetConstants.SUCCESS;
                        UserSession.getCurrent().setEmployee(authAgency);
                        UserSession.getCurrent().setEmplType(authAgency.getEmplType());

                    } else {
                        result = getAppSession().getMessage("eip.citizen.forgotPassword.invalidOTP");
                    }
                }
            }
        } else {
            result = getAppSession().getMessage("citizen.login.otp.must.error");
        }
        return result;
    }

    public Employee getAgencyByMobile() {

        final Employee agency = iEmployeeService.getAgencyByEmplTypeAndMobile(getMobileNumber(),
                UserSession.getCurrent().getEmplType(), getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (agency != null) {
            return agency;
        }
        return null;
    }

    public boolean setPassword(final Employee agency) {
        boolean result = false;

        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
		agency.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.AGENCY.getPrifixCode()));
        final Employee updateEmployee = iEmployeeService.setEmployeePassword(agency, empPassword, getUserSession().getEmployee());

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
    private void sendSuccessSMSandEMail(final Employee agency) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(agency.getEmpemail());
        dto.setMobnumber(agency.getEmpmobno());
        dto.setAppName(agency.getFullName());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.RESET_PWD_SUCC,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
    }

    /**
     *
     * @return {@code LookUp} of Citizen
     */
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

    public Employee getAgencyEmployee() {
        return agencyEmployee;
    }

    public void setAgencyEmployee(Employee agencyEmployee) {
        this.agencyEmployee = agencyEmployee;
    }

    public Long getEmpType() {
        return empType;
    }

    public void setEmpType(final Long empType) {
        this.empType = empType;
    }

    public boolean isAccountLock() {
        return accountLock;
    }

    public void setAccountLock(final boolean accountLock) {
        this.accountLock = accountLock;
    }

}
