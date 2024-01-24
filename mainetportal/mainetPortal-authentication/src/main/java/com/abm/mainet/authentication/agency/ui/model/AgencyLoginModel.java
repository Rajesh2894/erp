package com.abm.mainet.authentication.agency.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class AgencyLoginModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -4447470408278374519L;

    private final Logger LOG = Logger.getLogger(AgencyLoginModel.class);

    private Employee agencyEmployee;

    private String isEmailIDASUserId;

    private long empType;
    private String captchaSessionLoginValue;
    @Autowired
    private IEmployeeService iEmployeeService;

    private List<LookUp> agencylist = new ArrayList<>();

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    /**
     *
     * @return {@code Employee} object if user enter valid credential otherwise returns null
     */
    public Employee validateAgency() {

        Employee existingAgency = null;
        final String useID = getAgencyEmployee().getEmploginname();
        final String empPassword = new String(Base64.getDecoder().decode(getAgencyEmployee().getEmppassword()));
        if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(useID).matches()) {
            existingAgency = iEmployeeService.getAgencyByEmplTypeAndEmail(useID, getAgencyEmployee().getEmplType(),
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        } else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(useID).matches()) {
            existingAgency = iEmployeeService.getAgencyByEmplTypeAndMobile(useID, getAgencyEmployee().getEmplType(),
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        } else {
            existingAgency = iEmployeeService.getAgencyByEmplTypeAndEmpLoginName(useID, getAgencyEmployee().getEmplType(),
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        }

        if (existingAgency != null) {
            final Long emplType = existingAgency.getEmplType();
            final String empLoginName = existingAgency.getEmploginname();
            final String empEncryptPassword = Utility.encryptPassword(existingAgency.getEmploginname(), empPassword);
            boolean loginStatus = false;
            if (existingAgency.getAutMob().equals(MainetConstants.AUTH)) {
                if (empLoginName != null) {
                    existingAgency = iEmployeeService.getAuthenticatedEncryptAgencyEmployee(empLoginName, null, emplType,
                            getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
                    // if user password got matches with entered encrypted password
                    if (existingAgency.getEmppassword().equals(empEncryptPassword)) {
                        existingAgency.setLoggedInAttempt(null);
                        loginStatus = true;

                    } else {

                        Integer maxLoginAttempts = null;
                        try {
                            maxLoginAttempts = Integer.valueOf(getAppSession().getMessage("agency.max.attempts").trim());
                        } catch (final NumberFormatException e) {
                            LOG.error("Parsing error agency.max.attempts", e);
                            maxLoginAttempts = 3;
                        }
                        // if password not match increment login attempt
                        if ((null == existingAgency.getLoggedInAttempt())
                                || (existingAgency.getLoggedInAttempt() < maxLoginAttempts)) {
                            existingAgency.setLoggedInAttempt(
                                    existingAgency.getLoggedInAttempt() != null ? existingAgency.getLoggedInAttempt() + 1 : 1);
                            loginStatus = false;
                        } else {
                            // lock account if max attempted reached.
                            if (null == existingAgency.getLockUnlock()) {
                                existingAgency.setLockUnlock(MainetConstants.NEC.ADVOCATE);
                                existingAgency.setLockDate(new Date());
                            }
                            loginStatus = true;
                        }

                    }

                    // UPdate Employee attempt count and lock unlock status
                    iEmployeeService.updateEmpDetails(existingAgency);

                    // Login status is false return null as expected in case of failed attempted
                    if (loginStatus == false) {
                        existingAgency = null;
                    }
                }
            }
        }

        return existingAgency;

    }

    /**
     *
     * @param loggedEmployee
     */
    public void sendOTPAgain(final Employee loggedEmployee) {
        final String newOTPPassword = iCitizenRegistrationProcessService
                .generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
        loggedEmployee.setEmppassword(Utility.encryptPassword(loggedEmployee.getEmploginname(), newOTPPassword));
        final Employee savedEmployee = iEmployeeService.updatedAgencyEmployeeDetails(loggedEmployee);
        if ((savedEmployee != null) && savedEmployee.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)) {
            this.sendSMSandEMail(loggedEmployee, newOTPPassword);
            UserSession.getCurrent().setMobileNoToValidate(loggedEmployee.getEmpmobno());

        }

    }

    private void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd) {

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppNo(newAutoGeneratePwd);
        dto.setAppName(registeredEmployee.getFullName());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());

        getBindingResult()
                .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("Employee.registrationDone")));
    }

    public boolean isAgencyEmployee(final Employee agency) {
        if (agency.getEmplType().equals(0l)) {
            return false;
        } else {
            return true;
        }
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

    public boolean isOTPVerificationDone(final Employee validEmployee) {
        if ((validEmployee.getAutMob() != null) && !validEmployee.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)) {
            return true;
        } else {
            getBindingResult()
                    .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("otp.verification.not.done")));
            return false;
        }
    }

    public String redirectToOTP() {
        final String redirectTo = getAppSession().getMessage("agency.OTPVerification.URL");
        return redirectTo.trim();
    }

    public String redirectSuccess() {
        final String redirectTo = getAppSession().getMessage("agency.login.success.redirectURL");

        return redirectTo.trim();
    }

    public Employee getAgencyEmployee() {
        return agencyEmployee;
    }

    public void setAgencyEmployee(Employee agencyEmployee) {
        this.agencyEmployee = agencyEmployee;
    }

    public String getIsEmailIDASUserId() {
        return isEmailIDASUserId;
    }

    public void setIsEmailIDASUserId(final String isEmailIDASUserId) {
        this.isEmailIDASUserId = isEmailIDASUserId;
    }

    public void setUserSession(final Employee loggedEmployee) {
        final UserSession session = getUserSession();
        session.setEmployee(loggedEmployee);
    }

    public long getEmpType() {
        return empType;
    }

    public void setEmpType(final long empType) {
        this.empType = empType;
    }

    @Override
    protected void initializeModel() {

        super.initializeModel();

        final Map<String, List<LookUp>> lookups = getAppSession()
                .getHierarchicalLookUp(UserSession.getCurrent().getOrganisation(), MainetConstants.NEC.PARENT);
        final List<LookUp> childs = lookups.get(MainetConstants.NEC.PARENT);
        for (final LookUp lookup : childs) {
            if (!lookup.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
                agencylist.add(lookup);
            }
        }

    }

    public List<LookUp> getAgencylist() {
        return agencylist;
    }

    public void setAgencylist(final List<LookUp> agencylist) {
        this.agencylist = agencylist;
    }

    public String getEncryptData() {

        Employee existingAgency = null;
        final String useID = getAgencyEmployee().getEmploginname();
        final String empPassword = getAgencyEmployee().getEmppassword();
        if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(useID).matches()) {
            existingAgency = iEmployeeService.getAgencyByEmplTypeAndEmail(useID, getAgencyEmployee().getEmplType(),
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        } else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(useID).matches()) {
            existingAgency = iEmployeeService.getAgencyByEmplTypeAndMobile(useID, getAgencyEmployee().getEmplType(),
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        } else {
            existingAgency = iEmployeeService.getAgencyByEmplTypeAndEmpLoginName(useID, getAgencyEmployee().getEmplType(),
                    getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        }

        if (existingAgency != null) {
            return Utility.encryptPassword(existingAgency.getEmploginname(), empPassword);
        } else {
            return MainetConstants.BLANK;
        }
    }

    public String getCaptchaSessionLoginValue() {
        return captchaSessionLoginValue;
    }

    public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
        this.captchaSessionLoginValue = captchaSessionLoginValue;
    }

}
