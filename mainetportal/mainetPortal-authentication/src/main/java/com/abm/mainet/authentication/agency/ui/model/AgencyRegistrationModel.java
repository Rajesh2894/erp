package com.abm.mainet.authentication.agency.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.authentication.agency.service.IAgencyRegistrationProcessService;
import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class AgencyRegistrationModel extends AbstractFormModel {

    private static final long serialVersionUID = -6981693733748207530L;

    private String address;

    private Employee newAgency;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IAgencyRegistrationProcessService agencyRegProcessService;

    @Autowired
    private ICitizenRegistrationProcessService iRegistrationService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    private List<LookUp> agencylist = new ArrayList<>();
    private Long title;
    private String gender;
    private long empType;

    private AgencyEmployeeReqDTO entity = new AgencyEmployeeReqDTO();

    public AgencyEmployeeReqDTO getEntity() {
        return entity;
    }

    public void setEntity(final AgencyEmployeeReqDTO entity) {
        this.entity = entity;
    }

    /**
     *
     * @param newAgency
     * @return
     */
    public Employee doAgencyRegistration(final AgencyEmployeeReqDTO newAgency) {
        final List<LookUp> genderLookUp = getLevelData(MainetConstants.GENDER);

        for (final LookUp lookUp : genderLookUp) {
            if ((newAgency.getEmpGender() != null)
                    && newAgency.getEmpGender().equalsIgnoreCase(String.valueOf(lookUp.getLookUpId()))) {
                newAgency.setEmpGender(lookUp.getLookUpCode());
            }
        }

        final LookUp agencyLookUp = getAgencyLookUp();
        final String agency_LOCATION = MainetConstants.BLANK;
        long employee_TYPE = getEmpType();
        if (agencyLookUp != null) {
            employee_TYPE = agencyLookUp.getLookUpId();
        }

        final UserSession userSession = UserSession.getCurrent();
        final Organisation organisation = userSession.getOrganisation();
        final String newOTPPassword = getNewOTPPassword();
        System.err.println("OTP password is :" + newOTPPassword);
        newAgency.setAddFlag("N");
        newAgency.setTitle(title);
        final Employee registeredAgency = agencyRegProcessService.saveCitizenRegistrationForm(newAgency, organisation,
                newOTPPassword, agency_LOCATION, null, employee_TYPE, null);

        if ((registeredAgency != null) && (registeredAgency.getEmpId() != 0l)) {
            this.sendSMSandEMail(registeredAgency, newOTPPassword);
            setSessionAttribute(registeredAgency);
        }

        return registeredAgency;
    }

    /**
     *
     * @param registeredAgency
     */
    public void setSessionAttribute(final Employee registeredAgency) {
        getUserSession().setMobileNoToValidate(registeredAgency.getEmpmobno());
    }

    /**
     *
     * @return
     */
    public String getNewOTPPassword() {
        return iRegistrationService.generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
    }

    /**
     *
     * @return
     */
    public LookUp getAgencyLookUp() {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);
        final AgencyEmployeeReqDTO employee = getEntity();
        for (final LookUp lookUp : lookUpList) {
            if ((lookUp != null) && (employee.getEmplType() != null) && (employee.getEmplType() != null)) {
                if (lookUp.getLookUpId() == employee.getEmplType()) {
                    if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.BUILDER)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.ARCHITECT)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CENTER)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CYBER)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.HOSPITAL)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CREMATORIA)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.ENGINEER)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.ADVOCATE)
                            || lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.ADVERTISE)
                            || lookUp.getLookUpCode().equals(MainetConstants.NEC.TOWN_PLANNER)
                            || lookUp.getLookUpCode().equals(MainetConstants.NEC.STRUCTURAL_ENGINEER)
                            || lookUp.getLookUpCode().equals(MainetConstants.NEC.SUPERVISOR)) {
                        return lookUp;
                    }
                }
            }
        }
        return null;
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
        dto.setAppName(registeredEmployee.getEmpname());
        dto.setAppNo(newAutoGeneratePwd);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.NORMAL, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());

        getBindingResult()
                .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("Employee.registrationDone")));

        getBindingResult()
                .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("Employee.registrationDone")));
    }

    /**
     *
     * @return
     */
    public ModelAndView redirectToOTPVerification() {
        final AgencyOTPVerificationModel model = new AgencyOTPVerificationModel();
        model.setMobileNumber(getEntity().getEmpmobno());
        model.setEmplType(getEntity().getEmplType());
        getUserSession().setEmplType(getEntity().getEmplType());
        return new ModelAndView("AgencyOTPVerification", MainetConstants.FORM_NAME, model);
    }

    /* Validation to enter unique email id */
    /**
     *
     * @param empEMail
     * @return
     */
    public boolean isUniqueEmailAddress(final String empEMail) {
        boolean isUnique = false;
        final UserSession userSession = UserSession.getCurrent();
        final Organisation organisation = userSession.getOrganisation();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpEMail(empEMail, organisation,
                MainetConstants.IsDeleted.ZERO);
        final LookUp lookUp = getAgencyLookUp();
        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        } else {
            for (final Employee employee : employeeList) {
                if ((employee.getEmplType() != null) && (lookUp != null)) {
                    if (employee.getEmplType() == lookUp.getLookUpId()) {
                        return false;
                    } else {
                        isUnique = true;
                    }
                } else {
                    isUnique = false;
                }
            }

            return isUnique;
        }

    }

    /* Validation to enter unique phone number */
    /**
     *
     * @param empMobileNo
     * @return
     */
    public boolean isUniqueMobileNumber(final String empMobileNo) {
        boolean isUnique = false;
        final UserSession userSession = UserSession.getCurrent();
        final Organisation organisation = userSession.getOrganisation();
        final List<Employee> employeeList = iEmployeeService.getAgencyEmployeeByEmpMobileNo(empMobileNo, organisation,
                MainetConstants.IsDeleted.ZERO);

        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        } else {
            for (final Employee employee : employeeList) {
                if ((employee.getEmplType() != null) && (getAgencyLookUp() != null)) {
                    if (employee.getEmplType() == getAgencyLookUp().getLookUpId()) {
                        return false;
                    } else {
                        isUnique = true;
                    }
                } else {
                    isUnique = false;
                }
            }

            return isUnique;
        }

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

    public Employee getNewAgency() {
        return newAgency;
    }

    public void setNewAgency(final Employee newAgency) {
        this.newAgency = newAgency;
    }

    public long getEmpType() {
        return empType;
    }

    public void setEmpType(final long empType) {
        this.empType = empType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(final Long title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
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

}
