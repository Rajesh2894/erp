package com.abm.mainet.authentication.agency.ui.model;

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
 * @author vivek.kumar
 *
 */

@Component
@Scope(value = "session")
public class AgencyOTPVerificationModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -248693976852396517L;

    private String otpPassword;
    private String mobileNumber;
    private Long emplType;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    public Employee getAgencyByMobile() {
        Employee existingAgency = null;
        final Long agencyType = UserSession.getCurrent().getEmplType();
        final Employee agency = iEmployeeService.getAgencyByEmplTypeAndMobile(getMobileNumber(), agencyType,
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (agency != null) {
            existingAgency = agency;
        }

        return existingAgency;
    }

    /**
     *
     * @param emploginname
     * @param emplType
     * @return
     */
    public boolean validateMobileByOTP(final String emploginname, final Long emplType) {
        final Employee validAgency = iEmployeeService.getAuthenticatedEmployee(emploginname, getOtpPassword(), emplType,
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if ((validAgency != null) && (validAgency.getEmpId() != 0l)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param agencyEmployee
     * @return
     */
    public boolean resendOTP(final Employee agencyEmployee) {
        boolean result = false;
        final String newAutoGeneratePwd = iCitizenRegistrationProcessService
                .generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
        final Employee updateEmployee = iEmployeeService.updateEmployeePassword(agencyEmployee, newAutoGeneratePwd,
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
    public void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppName(registeredEmployee.getEmpname());
        dto.setAppNo(newAutoGeneratePwd);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
    }

    /**
     *
     * @param employee
     * @return
     */
    LookUp getAgencyLookUp(final Employee employee) {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);
        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpId() == employee.getEmplType()) {
                if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.BUILDER)
                        || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.ARCHITECT)
                        || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CENTER)
                        || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CYBER)
                        || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.HOSPITAL)
                        || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CREMATORIA)
                        || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.ENGINEER)) {
                    return lookUp;
                }
            }
        }
        return null;
    }

    /**
     *
     * @return
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
