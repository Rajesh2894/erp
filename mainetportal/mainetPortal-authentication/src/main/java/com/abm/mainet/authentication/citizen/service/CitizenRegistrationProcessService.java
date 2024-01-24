package com.abm.mainet.authentication.citizen.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.authentication.ldap.ILDAPManager;
import com.abm.mainet.common.authentication.ldap.UserProfile;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.dao.IEmployeeDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IDepartmentService;
import com.abm.mainet.common.service.IDesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.PasswordManager;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;

@Service
public class CitizenRegistrationProcessService implements ICitizenRegistrationProcessService {

    private static final long serialVersionUID = -3979265235197763334L;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IDesignationService iDesignationService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IEmployeeDAO employeeDAO;

    @Autowired
    @Qualifier("ldapManager")
    private ILDAPManager ldapManager;

    @Override
    public String generateNewPassword(final int newPasswordLength) {
        return UtilityService.generateRandomNumericCode(newPasswordLength);
    }

    @Override
    @Transactional
    public Department getCitizenDepartment(final Organisation organisation) {
        return iDepartmentService.getDepartment(MainetConstants.DEPARTMENT.ONLINE_SERVICES_CODE,
                MainetConstants.STATUS.ACTIVE);
    }

    @Override
    @Transactional
    public Employee saveCitizenRegistrationForm(final Employee newEmployee, final Organisation organisation,
            final String newOTPPassword, final String citizen_LOCATION, Designation designation, final Long employee_TYPE,
            final Employee userId) {
        final Department department = getCitizenDepartment(organisation);
        if (designation == null) {

            final List<Designation> designationList = iDesignationService
                    .getAllDesignationByDesgName(MainetConstants.AGENCY.NAME.CITIZEN);
            designation = designationList.get(0);
        }

        if ((department != null) && (designation != null)) {
            newEmployee.setEmplType(employee_TYPE);
            newEmployee.setEmploginname(newEmployee.getEmpmobno());// Mobile_Number_AS_LoginName
            newEmployee.setMobNoOtp(newOTPPassword);
            if (employee_TYPE != null) {
                ApplicationSession.getInstance().getNonHierarchicalLookUp(
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), employee_TYPE);
            }
            final Employee registeredCitizen = iEmployeeService.saveEmployeeDetails(newEmployee, organisation,
                    designation, department, userId);
            return registeredCitizen;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Employee setEmployeePassword(final String mobile, final String newPassword, final Employee userId) {

        Employee updateEmployee = null;
        final LookUp citizenLookUp = getCitizenLooUp();
        final List<Employee> employeeList = employeeDAO.getEmployeeByEmpMobileNo(mobile,
                ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.IsDeleted.ZERO,((citizenLookUp!=null)?citizenLookUp.getLookUpId():0));
       
        if (employeeList != null) {
            final Date date = new Date();
            final Employee employee = employeeList.get(0);
            if (employee.getEmplType() != null) {
                if (employee.getEmplType() == citizenLookUp.getLookUpId()) {
                    final String encPassword = Utility.encryptPassword(employee.getEmploginname(), newPassword);
                    employee.setAutMob(MainetConstants.AUTH);
                    employee.setUpdatedDate(date);
                    employee.setUpdatedBy(userId);
                    employee.setEmppassword(encPassword);
					employee.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.CITIZEN.getPrifixCode()));
                    updateEmployee = employeeDAO.saveEmployee(employee);

                    final UserProfile uProfile = new UserProfile();
                    uProfile.setFirstName(employee.getEmpname());
                    uProfile.setLastName(employee.getEmpLName());
                    uProfile.setPwd(encPassword);
                    uProfile.setRole(employee.getGmid().toString());
                    uProfile.setuID(employee.getEmploginname());
                    uProfile.setUserType(MainetConstants.NEC.CITIZEN);
                    ldapManager.createUser(uProfile);
                }
            }
        } else {
            return updateEmployee;
        }

        return updateEmployee;
    }

    @Override
    @Transactional
    public Employee setAgencyEmployeePassword(final Employee employee, final String newPassword, final Employee userId) {

        Employee updateEmployee = null;

        employee.setAutMob(MainetConstants.AUTH);
        final Date date = new Date();
        employee.setUpdatedDate(date);
        employee.setUpdatedBy(userId);
        employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), newPassword));
		employee.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.AGENCY.getPrifixCode()));
        updateEmployee = employeeDAO.saveUpdatedAgencyEmployeeDetails(employee);
        return updateEmployee;
    }

    @Override
    @Transactional
    public Employee setAdminEmployeePassword(final String mobile, final String newPassword, final Employee userId) {

        Employee updateEmployee = null;
       
        final List<Employee> employeeList = employeeDAO.getEmployeeByEmpMobileNo(mobile,
                UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.ZERO,MainetConstants.ADMIN_EMP_TYPE);

        if (employeeList != null) {
            final Date date = new Date();
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    employee.setAutMob(MainetConstants.AUTH);
                    employee.setUpdatedDate(date);
                    employee.setUpdatedBy(userId);
                    employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), newPassword));
					employee.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));
                    updateEmployee = employeeDAO.saveEmployee(employee);

                }

            }
        } else {
            return updateEmployee;
        }

        return updateEmployee;
    }

    public LookUp getCitizenLooUp() {

        final List<LookUp> lookUpList = ApplicationSession.getInstance()
                .getHierarchicalLookUp(ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.NEC.PARENT)
                .get(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
                return lookUp;
            }
        }
        return null;
    }

}
