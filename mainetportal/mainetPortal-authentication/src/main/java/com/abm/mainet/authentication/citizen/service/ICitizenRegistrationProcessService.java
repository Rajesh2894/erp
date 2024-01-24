/**
 *
 */
package com.abm.mainet.authentication.citizen.service;

import java.io.Serializable;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

public interface ICitizenRegistrationProcessService extends Serializable {
    /**
     * @Method To generate new password with length {newPasswordLength}
     * @param newPasswordLength.
     * @return password
     */
    public String generateNewPassword(int newPasswordLength);

    /**
     * @Method To get citizen department.
     * @param organisation {@link Organisation}
     * @param dpDeptcode
     * @return
     */
    public Department getCitizenDepartment(Organisation organisation);

    /**
     * To get citizen designation. If not exist, insert Designation for citizen in database and return it.
     * @param citizenLocationName
     * @param organisation {@link Organisation}
     * @param departmentLocation {@link DepartmentLocation}
     * @param userId
     * @return {@link Designation}
     */

    public Employee saveCitizenRegistrationForm(Employee newEmployee, Organisation organisation, String newOTPPassword,
            String citizen_LOCATION, Designation citizen_DESIGNATION, Long employee_TYPE, Employee userId);

    /**
     * @Method to update employee password.
     * @param mobile
     * @param newPassword
     * @param userId
     * @return {@link Employee}
     */
    public Employee setEmployeePassword(String mobile, String newPassword, Employee userId);

    /**
     *
     * @param mobile
     * @param newPassword
     * @param userId
     * @return
     */
    public Employee setAdminEmployeePassword(String mobile, String newPassword, Employee userId);

    /**
     *
     * @param employee
     * @param newPassword
     * @param employee2
     * @param userId
     * @return
     */
    public Employee setAgencyEmployeePassword(Employee employee, String newPassword, Employee userId);

}
