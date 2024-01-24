package com.abm.mainet.common.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeSearchDTO;

public interface IEmployeeDAO {

    /**
     * @Method To get {@link Employee} details by Login Name
     * @param organisation {@link Organisation}
     * @param isDeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Employee}
     */

    int getCountOfGroup(Long gmId, Long orgId, String isDeleted);

    public abstract Employee getEmployeeByLoginName(String emploginname,
            Organisation organisation, String isDeleted);

    public abstract Employee getEmployeeById(Long empId,
            Organisation organisation, String isdeleted);

    /**
     * @Method To get {@link Employee} details by Login Name, Password
     * @param organisation {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Employee}
     */

    public abstract Employee getAuthenticatedEmployee(String emploginname,
            String emppassword, Long emplType, Organisation organisation,
            String isdeleted, String type);

    public abstract Employee getAuthenticatedEmployee(String emploginname,
            String emppassword, Long emplType, Organisation organisation,
            String isdeleted);

    /**
     * @Method To get {@link Employee} details by Login Name, Password
     * @param organisation {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Employee}
     */
    public abstract Employee getAdminAuthenticatedEmployee(String emploginname,
            String emppassword, Long emplType, Long empId,
            Organisation organisation, String isdeleted);

    public abstract List<Employee> getEmployeeByEmailId(String empemail,
            Organisation organisation, String isdeleted);

    public abstract List<Employee> getEmployeeByEmpMobileNo(String empmobno,
            String empPassword, Organisation organisation);

    /**
     * @Method To get {@link Employee} details for EmailId.
     * @param empemail is EmailId.
     * @param organisation {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return List of {@link Employee}
     */

    /*
     * Method to get Employee by Email Id, adding the check for employee_type
     */
    public abstract List<Employee> getEmployeeByEmailIdAndEmpType(
            String empEMail, Long empType, Organisation organisation,
            String isDeleted, boolean isAgency);

    /**
     * @Method To get {@link Employee} details by mobile number.
     * @param empmobno is employee mobile number.
     * @param organisation {@link Organisation}
     * @param isDeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return List of {@link Employee}
     */
    public abstract List<Employee> getEmployeeByEmpMobileNo(String empmobno,
            Organisation organisation, String isDeleted,Long empType);

    public abstract List<Employee> getEmployeeByEmpMobileNoAndEmpType(
            String empMobileNo, Long empType, Organisation organisation,
            String isDeleted, boolean isAgency);

    public abstract List<Employee> getEmployeeListByLoginName(
            String emploginname, Organisation organisation, String isDeleted);

    /**
     * @Method to save or update {@link Employee}
     * @param employee is {@link Employee}
     * @return saved or updated {@link Employee}
     */
    public abstract Employee saveEmployee(Employee employee);

    public abstract Employee saveEmployeeForAgency(Employee employee);

    /**
     * 
     * Added by Vikrant Thakur for IT Support Module 03 April 2014
     * 
     * @param design : required designation param
     * @return : Employee to that Designation
     */

    public abstract boolean updateEmpDetails(Employee employee);

    public abstract List<Employee> getAllAgencyList(Organisation organisation);

    /**
     * 
     * @param mobile
     * @param agencyType
     * @param organisation
     * @param isDeleted
     * @return {@code Employee} object based on Agency Type and mobile
     */
    public abstract Employee getAgencyByEmplTypeAndMobile(String mobile,
            Long agencyType, Organisation organisation, String isDeleted);

    /**
     * 
     * @param email
     * @param agencyType
     * @param organisation
     * @param isDeleted
     * @return {@code Employee} object based on Agency Type and email
     */
    public abstract Employee getAgencyByEmplTypeAndEmail(String email,
            Long agencyType, Organisation organisation, String isDeleted);

    /**
     * 
     * @param empLoginName
     * @param agencyType
     * @param organisation
     * @param isDeleted
     * @return {@code Employee} object based on Agency Type and emploginName
     */
    public abstract Employee getAgencyByEmplTypeAndEmploginName(
            String empLoginName, Long agencyType, Organisation organisation,
            String isDeleted);

    public abstract void persistModifiedCitizenInfo(Employee modifiedEmployee);

    public abstract void setEmployeeLoggedInFlag(
            Employee currentLoggedInEmployee);

    public void resetEmployeeLoggedInFlag(String flag, long empid);

    public abstract List<EmployeeSearchDTO> findEmployeeInfo(String empName,
            Organisation organisation);

    public abstract Employee changeMobileNumber(Employee emp, String newMobNo);

    public Map<Long, String> getEmployeeLookUp(Organisation organisation);

    public abstract void generateLicenseData(Employee employee);

    boolean isUniqueUserAlias(String userAlias, Organisation organisation);

    Employee getEmployeeByUserAlias(String userAlias,
            Organisation organisation, String isDeleted);

    Employee saveAgencyEmployeeDetails(Employee emp);

    Employee getAuthenticatedAgencyEmployee(String emploginname,
            String emppassword, Long emplType, Organisation organisation,
            String isdeleted, String type);

    List<Employee> getAgencyEmployeeByEmpMobileNo(String empMobileNo,
            Organisation organisation, String isDeleted);

    void setAgencyEmployeeLoggedInFlag(Employee agencyEmployee);

    Employee getAuthenticatedAgencyEmployee(String emploginname,
            String emppassword, Long emplType, Organisation organisation,
            String isdeleted);

    Employee saveUpdatedAgencyEmployeeDetails(Employee employee);

    Long findCountOfEmployee();

    Long findCountOfLoggedInUser();

    /**
     * @param orgId
     * @param deptId
     * @return
     */
    List<Employee> findAllEmployeeByDept(Long orgId, Long deptId);

    public Employee create(Employee employee);

    List<Object[]> getAllEmpByDesignation(Long desgId, Long orgId);

    String findEmployeeSessionId(Long empId, String string);

    void updateEmployeeLoggedInFlag(String loggedIn, String sessionId);

}