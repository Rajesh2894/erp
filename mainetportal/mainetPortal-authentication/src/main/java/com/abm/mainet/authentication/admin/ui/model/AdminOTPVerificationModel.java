package com.abm.mainet.authentication.admin.ui.model;

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
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 *
 * @author Vivek.Kumar
 *
 */

@Component
@Scope(value = "session")
public class AdminOTPVerificationModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -1505022376994033832L;

    private String oneTimePassword;
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
     * @return {@code Employee} object by mobile no.
     */
    public Employee getAdminByMobile() {
        Employee validateEmployee = null;
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(getMobileNumber(), getEmplType(),
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO, false);

        if ((employeeList != null) && (employeeList.size() == 1)) {
            if (employeeList.get(0).getEmplType() == null) {
                validateEmployee = employeeList.get(0);
            }

        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    validateEmployee = employee;
                }
            }
        }

        return validateEmployee;
    }

    /**
     *
     * @param emploginname
     * @param emplType
     * @return {@value true} if login id and otp matched
     */
    public boolean validateMobileByOTP(final String emploginname, final Long emplType) {
        final Employee validAdmin = iEmployeeService.getAuthenticatedEmployee(emploginname, getOneTimePassword(), emplType,
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (validAdmin != null) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param adminEmployee
     * @return {@value true} after sending otp as a mail and sms.
     */
    public boolean resendOTP(final Employee adminEmployee) {
        boolean result = false;
        final String newAutoGeneratePwd = iCitizenRegistrationProcessService
                .generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
        final Employee updateEmployee = iEmployeeService.updateEmployeePassword(adminEmployee, newAutoGeneratePwd,
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
        final String empName = replaceNull(registeredEmployee.getEmpname()) + MainetConstants.WHITE_SPACE
                + replaceNull(registeredEmployee.getEmpMName())
                + MainetConstants.WHITE_SPACE + replaceNull(registeredEmployee.getEmpLName());
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppName(empName);
        dto.setAppNo(newAutoGeneratePwd);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
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
     * @return the otpPassword
     */

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
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

    private String replaceNull(String name) {
        if (name == null) {
            name = MainetConstants.BLANK;
        }
        return name;
    }

}
