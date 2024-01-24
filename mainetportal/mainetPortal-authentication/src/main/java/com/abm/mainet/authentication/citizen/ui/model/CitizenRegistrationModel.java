package com.abm.mainet.authentication.citizen.ui.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.MenuRoleEntitlement;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class CitizenRegistrationModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 7287100840299065238L;

    private String address;
    private Employee newCitizen;

    private String uploadedFile = MainetConstants.BLANK;
    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iRegistrationService;

    private String isCorrespondence;
    private String captchaSessionValue;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private IEntitlementService iEntitlementService;
    

    public CitizenRegistrationModel() {

    }

    /**
     *
     * @return {@code LookUp} of CItizen
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
     *
     * @param newEmployee
     * @return {@code Employee} after successful citizen registration
     */
    public Employee doCitizenRegistration(final Employee newEmployee) {
        final List<LookUp> genderLookUp = getLevelData(MainetConstants.GENDER);

        for (final LookUp lookUp : genderLookUp) {
            if ((newEmployee.getEmpGender() != null)
                    && newEmployee.getEmpGender().equalsIgnoreCase(String.valueOf(lookUp.getLookUpId()))) {
                newEmployee.setEmpGender(lookUp.getLookUpCode());
            }
        }

        final LookUp citizenLooUp = getCitizenLooUp();
        final String citizen_LOCATION = MainetConstants.BLANK;
        final long employee_TYPE = citizenLooUp.getLookUpId();

        if (getIsCorrespondence() == null) {
            addValidationError(getAppSession().getMessage("Employee.isCorres.option"));
        } else {
            newEmployee.setAddFlag(getIsCorrespondence());
        }

        final String newOTPPassword = getNewOTPPassword();
        newEmployee.setTitle(newCitizen.getTitle());
        
        long orgId = Utility.getOrgId();
        Long groupId = getGroupIdByOrg(MainetConstants.MENU.ORG_CITIZEN + orgId, orgId);
        if (groupId != null) {
            newEmployee.setGmid(groupId);
        } else {
            groupId = getGroupIdByOrg(MainetConstants.MENU.DEFAULT_CITIZEN, orgId);
            newEmployee.setGmid(groupId);
        }
        try {
        	 String citizenImagePath = Utility.getProfileImagePath();
     	   uploadedFile =	super.uploadAndGetFilePath(getFileNetClient(), citizenImagePath);
     	  if (!uploadedFile.isEmpty() && !uploadedFile.equalsIgnoreCase("")) {
              
              uploadedFile = citizenImagePath + File.separator + uploadedFile;
              getNewCitizen().setEmpphotopath(uploadedFile);
          }
 	} catch (Exception e) {
 		// TODO Auto-generated catch block
 		
 	}
       
        final Employee registeredCitizen = iRegistrationService.saveCitizenRegistrationForm(newEmployee,
                ApplicationSession.getInstance().getSuperUserOrganization(), newOTPPassword, citizen_LOCATION, null,
                employee_TYPE, getUserSession().getEmployee());

        if ((registeredCitizen != null) && (registeredCitizen.getEmpId() != 0l)) {

            this.sendSMSandEMail(registeredCitizen, newOTPPassword);
            setSessionAttribute(registeredCitizen);
            System.err.println("OTP password is :" + newOTPPassword);
            
            MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(registeredCitizen.getGmid(), orgId);
        }

        return registeredCitizen;
    }

    /**
     *
     * @param registeredCitizen : used to set citizen mobile no to session
     */
    public void setSessionAttribute(final Employee registeredCitizen) {
        getUserSession().setMobileNoToValidate(registeredCitizen.getEmpmobno());
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

        getBindingResult()
                .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("Employee.registrationDone")));
    }

    /**
     *
     * @return {@code String} of new generated otp
     */
    public String getNewOTPPassword() {
        return iRegistrationService.generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
    }

    /**
     *
     * @param empMobileNo
     * @return {@value true} if mobile no is not already registered
     */
    public boolean isUniqueMobileNumber(final String empMobileNo) {
        boolean isUnique = false;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(empMobileNo,
                getNewCitizen().getEmplType(), ApplicationSession.getInstance().getSuperUserOrganization(),
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

    /**
     *
     * @param empEMail
     * @return {@value true} if email id is not already registered
     */
    public boolean isUniqueEmailAddress(final String empEMail) {
        boolean isUnique = false;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpEMailAndType(empEMail, getNewCitizen().getEmplType(),
                ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.IsDeleted.ZERO, false);

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

    /**
     *
     * @return
     */
    public String redirectToOTPVerification() {
        final String redirectTo = getAppSession().getMessage("citizen.OTPVerification.URL");
        return redirectTo.trim();
    }

    /**
     * @return the newCitizen
     */
    public Employee getNewCitizen() {
        return newCitizen;
    }

    /**
     * @param newCitizen the newCitizen to set
     */
    public void setNewCitizen(final Employee newCitizen) {
        this.newCitizen = newCitizen;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    public String getIsCorrespondence() {
        return isCorrespondence;
    }

    public void setIsCorrespondence(final String isCorrespondence) {
        this.isCorrespondence = isCorrespondence;
    }

    public String getCaptchaSessionValue() {
        return captchaSessionValue;
    }

    public void setCaptchaSessionValue(final String captchaSessionValue) {
        this.captchaSessionValue = captchaSessionValue;
    }

    public boolean isUniqueUserAlias(final String userName) {
        return iEmployeeService.isUniqueUserAlias(userName, ApplicationSession.getInstance().getSuperUserOrganization());
    }

    private Long getGroupIdByOrg(final String groupCode, final Long org) {
        return iEntitlementService.getGroupIdByName(groupCode, org);
    }

}
