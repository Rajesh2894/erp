package com.abm.mainet.authentication.citizen.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class CitizenOTPVerificationModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 2418543443693970622L;

    private String otpPassword;
    private String mobileNumber;
    private Long emplType;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    /**
     *
     * @return {@code Employee} existing citizen for this mobile no.
     */
    public Employee getCitizenByMobile() {
        Employee validateEmployee = null;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(getMobileNumber(), getEmplType(),
                ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.IsDeleted.ZERO, false);

        final LookUp lookUp = getCitizenLooUp();
        if ((employeeList != null) && (employeeList.size() == 1)) {
            if (employeeList.get(0).getEmplType() != null) {
                if (employeeList.get(0).getEmplType() == lookUp.getLookUpId()) {
                    validateEmployee = employeeList.get(0);
                }
            }
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() != null) {
                    if (employee.getEmplType() == lookUp.getLookUpId()) {
                        validateEmployee = employee;
                    }
                }
            }
        }

        return validateEmployee;
    }

    /**
     *
     * @param emploginname
     * @param emplType
     * @return
     */
    public boolean validateMobileByOTP(final String emploginname, final Long emplType) {
        final Employee validCitizen = iEmployeeService.getAuthenticatedEmployee(emploginname, getOtpPassword(), emplType,
                ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.IsDeleted.ZERO);
        if ((validCitizen != null) && (validCitizen.getEmpId() != 0l)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param citizenEmployee
     * @return
     */
    public boolean resendOTP(final Employee citizenEmployee) {
        boolean result = false;
        final String newAutoGeneratePwd = iCitizenRegistrationProcessService
                .generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
        final Employee updateEmployee = iEmployeeService.updateEmployeePassword(citizenEmployee, newAutoGeneratePwd,
                getUserSession().getEmployee());
        if (updateEmployee != null) {
            result = true;
            this.sendSMSandEMail(updateEmployee, newAutoGeneratePwd);
        }
        return result;
    }

    /**
     *
     * @param registeredEmployee
     * @param newAutoGeneratePwd
     */
    private void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd) {

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppNo(newAutoGeneratePwd);
        dto.setAppName(registeredEmployee.getFullName());
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
     * @return the otpPassword
     */
    public String getOtpPassword() {
        return otpPassword;
    }

    /**
     * @param otpPassword the otpPassword to set
     */
    public void setOtpPassword(final String otpPassword) {
        this.otpPassword = otpPassword;
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
     * @return the emplType
     */
    public Long getEmplType() {
        return emplType;
    }

    /**
     * @param emplType the emplType to set
     */
    public void setEmplType(final Long emplType) {
        this.emplType = emplType;
    }

}
