package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IDesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author vinay.jangir
 *
 */
@Component
@Scope(value = "session")
public class AdminRegistrationModel extends AbstractEntryFormModel<Employee> implements Serializable {

    private static final long serialVersionUID = -1652795911326729221L;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IDesignationService iDesignationService;

    @Autowired
    private ICitizenRegistrationProcessService iRegistrationService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    private List<LookUp> lookUps = new ArrayList<>(0);

    private long deptId;

    private List<Designation> designationlist = new ArrayList<>(0);

    private long desgid;
    private Long title;
    private String gender;

    private Employee employee;

    private long hideMode;

    /**
     *
     * @param newEmployee
     * @return
     */
    public Employee doEmployeeRegistration(final Employee newEmployee) {

        final List<LookUp> genderLookUp = getLevelData(MainetConstants.GENDER);

        for (final LookUp lookUp : genderLookUp) {
            if ((newEmployee.getEmpGender() != null)
                    && newEmployee.getEmpGender().equalsIgnoreCase(String.valueOf(lookUp.getLookUpId()))) {
                newEmployee.setEmpGender(lookUp.getLookUpCode());
            }
        }

        final String citizen_LOCATION = MainetConstants.BLANK;

        final Designation designation = iDesignationService.getDesignation(newEmployee.getDesignation().getDsgid());

        final Long employee_TYPE = null;

        final UserSession userSession = UserSession.getCurrent();
        newEmployee.setEmployeeNo(getEntity().getEmployeeNo());
        final Organisation organisation = userSession.getOrganisation();
        final String newOTPPassword = getNewOTPPassword();
        System.err.println("OTP password is :" + newOTPPassword);
        newEmployee.setAddFlag(MainetConstants.NO);
        final Employee registeredCitizen = iRegistrationService.saveCitizenRegistrationForm(newEmployee, organisation,
                newOTPPassword, citizen_LOCATION, designation, employee_TYPE, getUserSession().getEmployee());

        if ((registeredCitizen != null) && (registeredCitizen.getEmpId() != 0l)) {
            this.sendSMSandEMail(registeredCitizen, newOTPPassword);
            setSessionAttribute(registeredCitizen);
        }
        return registeredCitizen;

    }

    public void setSessionAttribute(final Employee registeredCitizen) {
        getUserSession().setMobileNoToValidate(registeredCitizen.getEmpmobno());
    }

    public String getNewOTPPassword() {
        return iRegistrationService.generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
    }

    public LookUp getEmployeeLookUp() {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.EMPLOYEE)) {
                return lookUp;
            }
        }
        return null;
    }

    private void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        final String empName = replaceNull(registeredEmployee.getEmpname()) + MainetConstants.WHITE_SPACE
                + replaceNull(registeredEmployee.getEmpMName())
                + MainetConstants.WHITE_SPACE + replaceNull(registeredEmployee.getEmpLName());
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppNo(newAutoGeneratePwd);
        dto.setAppName(empName);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());

        getBindingResult()
                .addError(new ObjectError(MainetConstants.BLANK, getAppSession().getMessage("Employee.registrationDone")));
    }

    public ModelAndView redirectToOTPVerification() {

        final AdminOTPVerificationModel model = new AdminOTPVerificationModel();

        model.setMobileNumber(getEntity().getEmpmobno());
        model.setEmplType(getEntity().getEmplType());
        return new ModelAndView("AdminOTPVerification", MainetConstants.FORM_NAME, model);
    }

    /* Validation to enter unique email id */

    public boolean isUniqueEmailAddress(final String empEMail, final Long emplType) {
        final UserSession userSession = UserSession.getCurrent();
        final Organisation organisation = userSession.getOrganisation();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpEMailAndType(empEMail, emplType, organisation,
                MainetConstants.IsDeleted.ZERO, false);

        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    return false;
                }
            }
        }
        return true;

    }

    /* Validation to enter unique phone number */

    public boolean isUniqueMobileNumber(final String empMobileNo, final Long emplType) {
        final UserSession userSession = UserSession.getCurrent();
        final Organisation organisation = userSession.getOrganisation();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(empMobileNo, emplType, organisation,
                MainetConstants.IsDeleted.ZERO, false);

        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    return false;
                }
            }
        }
        return true;

    }

    boolean checkNECType(final long emplTypeId) {
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
     * @return the lookUps
     */
    public List<LookUp> getLookUps() {

        return lookUps;
    }

    public List<LookUp> getAllDesignationByDeptId() {
        final List<LookUp> list = new ArrayList<>(0);

        if (designationlist.size() > 0) {
            for (final Designation desg : designationlist) {
                final LookUp lookup = new LookUp();
                lookup.setLookUpId(desg.getDsgid());
                lookup.setLookUpDesc(desg.getDsgname());
                lookup.setLookUpCode(desg.getDsgshortname());
                lookup.setDescLangFirst(desg.getDsgname());
                lookup.setDescLangSecond(desg.getDsgname());
                list.add(lookup);
            }
        }
        return list;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
    }

    public long getHideMode() {
        return hideMode;
    }

    public void setHideMode(final long hideMode) {
        this.hideMode = hideMode;
    }

    public void setDesignationlist(final List<Designation> designationlist) {
        this.designationlist = designationlist;
    }

    public long getDesgid() {
        return desgid;
    }

    public void setDesgid(final long desgid) {
        this.desgid = desgid;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(final long deptId) {
        this.deptId = deptId;
    }

    public void getDesignation() {
        lookUps = iDesignationService.getDesignationByOrg(UserSession.getCurrent().getOrganisation());
    }

    private String replaceNull(String name) {
        if (name == null) {
            name = MainetConstants.BLANK;
        }
        return name;
    }

}
