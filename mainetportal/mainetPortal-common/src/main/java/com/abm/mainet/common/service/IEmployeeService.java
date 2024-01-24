/**
 *
 */
package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.dto.EmployeeSearchDTO;
import com.abm.mainet.dms.client.FileNetApplicationClient;

@Transactional(readOnly = true)
public interface IEmployeeService {

    /**
     * @Method To get employee by empId Id.
     * @param empId
     * @param organisation {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return
     */
    public int getCountOfGroup(Long gmId, Long orgId, String isDeleted);

    /**
     * @Method To get {@link Employee} details by Login Name, Password
     * @param emploginname = LOGIN NAME
     * @param emppassword = PASSWORD
     * @param organisation is object of {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return An object of {@link Employee}
     */
    public Employee getAuthenticatedEmployee(String emploginname, String emppassword, Long emplType, Organisation organisation,
            String isdeleted, String type);

    public Employee getAuthenticatedEmployee(String emploginname, String emppassword, Long emplType, Organisation organisation,
            String isdeleted);

    /**
     * @Method To get employee by E-Mail Id.
     * @param empEMail is E-Mail Id of {@link Employee}
     * @param organisation is {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return List of {@link Employee} if E-Mail Id is equal to @param empEMail
     */
    public List<Employee> getEmployeeByEmpEMail(String empEMail, Organisation organisation, String isDeleted);

    /**
     * Method to get Employee by Email Id, along with new requirement for adding filter on the basis of Employee_Type
     */
    public List<Employee> getEmployeeByEmpEMailAndType(String empEMail, Long empType, Organisation organisation, String isDeleted,
            boolean isAgency);

    /**
     * @Method To get employee by mobile number.
     * @param empMobileNo is mobile number of {@link Employee}
     * @param organisation is {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return List of {@link Employee} if mobile is equal to @param empMobileNo
     */
    public List<Employee> getEmployeeByEmpMobileNo(String empMobileNo, Organisation organisation, String isDeleted,Long empType);

    /*
     * Method to get Employee by registered Emp Mobile Number, along with new requirement for adding filter on the basis of
     * Employee_Type
     */
    public List<Employee> getEmployeeByEmpMobileNoAndType(String empMobileNo, Long empType, Organisation organisation,
            String isDeleted, boolean isAgency);

    /**
     * @Method To save employee details.
     * @param newEmployee {@link Employee}
     * @param departmentLocation {@link DepartmentLocation}
     * @param designation {@link Designation}
     * @param userId is {@link Employee} logged
     * @param organisation is {@link Organisation}
     * @return saved {@link Employee} or null if failed to save.
     */
    public Employee saveEmployeeDetails(Employee newEmployee, Organisation organisation,
            Designation designation, Department department, Employee userId);

    /**
     * @Method To update employee details.
     * @param updateEmployee {@link Employee}
     * @param userId is {@link Employee} logged
     * @return updated {@link Employee} or null if failed to updated.
     */
    public Employee updateEmployeeDetails(Employee updateEmployee, Employee userId);

    /**
     * @Method To update employee password.
     * @param updateEmployee {@link Employee}
     * @param newPassword is non encrypted password.
     * @param userId is {@link Employee} logged
     * @return updated {@link Employee} or null if failed to updated.
     */
    public Employee updateEmployeePassword(Employee updateEmployee, String newPassword, Employee userId);

    /**
     * @Method To update employee password.
     * @param updateEmployee {@link Employee}
     * @param newPassword is non encrypted password.
     * @param userId is {@link Employee} logged
     * @return updated {@link Employee} or null if failed to updated.
     */
    public Employee setEmployeePassword(Employee updateEmployee, String newPassword, Employee userId);

    /**
     * @Method To update employee mobile number.
     */
    public Employee changeMobileNumber(Employee emp, String newMobNo);

    /**
     * @Method To get {@link Employee} details by Login Name
     * @param organisation {@link Organisation}
     * @param isDeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Employee}
     */
    public Employee getEmployeeByLoginName(String emploginname, Organisation organisation, String isDeleted);

    public boolean updateEmpDetails(Employee emp);

    /**
     * 
     * @param employee
     * @return saved employee:
     */
    public Employee saveEmployee(Employee employee);

    public Employee saveEmployeeForAgency(Employee employee);

    public List<Employee> getAllAgencyList();

    public List<Employee> getEmployeeListByLoginName(String emploginname,
            Organisation organisation, String isDeleted);

    /**
     * 
     * @param mobile
     * @param agencyType
     * @param organisation
     * @param isDeleted
     * @return {@code Employee} object based on Agency type and mobile
     */
    public Employee getAgencyByEmplTypeAndMobile(String mobile, Long agencyType, Organisation organisation, String isDeleted);

    /**
     * 
     * @param email
     * @param agencyType
     * @param organisation
     * @param isDeleted
     * @return {@code Employee} object based on Agency type and email
     */
    public Employee getAgencyByEmplTypeAndEmail(String email, Long agencyType, Organisation organisation, String isDeleted);

    /**
     * 
     * @param empLoginName
     * @param agencyType
     * @param organisation
     * @param isDeleted
     * @return {@code Employee} object based on Agency type and emploginname
     */
    public Employee getAgencyByEmplTypeAndEmpLoginName(String empLoginName, Long agencyType, Organisation organisation,
            String isDeleted);

    public void saveEditProfileInfo(Employee modifiedEmployee);

    void setEmployeeLoggedInFlag(Employee currentLoggedInEmployee);

    public void resetEmployeeLoggedInFlag(Employee employee);

    public List<EmployeeSearchDTO> getAllEmployeeInfoByOrgID(String empName);

    public Map<Long, String> getEmployeeLookUp();

    public Employee getAdminEncryptAuthenticatedEmployee(String emploginname, String emppassword, Long emplType, Long empId,
            Organisation organisation, String isdeleted);

    public void setTpLicenseDataFromAuthorizer(Employee entity);

    Employee getEmployeeById(Long empId, Organisation organisation,
            String isdeleted);

    boolean isUniqueUserAlias(String userAlias, Organisation organisation);

    Employee getEmployeeByUserAlias(String userAlias,
            Organisation organisation, String isDeleted);

    Employee saveAgencyEmployeeDetails(Employee newEmployee,
            Organisation organisation,
            Designation designation, Department department, Long userId);

    public Employee updatedAgencyEmployeeDetails(Employee employee);

    public Employee getAuthenticatedAgencyEmployee(String emploginname, String emppassword, Long emplType,
            Organisation organisation, String isdeleted, String type);

    public Employee setAgencyEmployeePassword(Employee updateEmployee, String newPassword);

    public List<Employee> getAgencyEmployeeByEmpMobileNo(String empMobileNo, Organisation organisation, String isDeleted);

    public Employee getAuthenticatedEncryptAgencyEmployee(String emploginname, String emppassword, Long emplType,
            Organisation organisation, String isdeleted);

    void setAgencyEmployeeLoggedInFlag(Employee agencyEmployee);

    Long findCountOfRegisteredEmployee();

    Long findCountOfLoggedInUser();

    public Employee create(Employee employee);

    public List<Object[]> getAllEmpByDesignation(Long dsgid, Long orgId);

    public Map<Long, String> getGroupList(Long orgid);

    public List<EmployeeBean> getAllEmployee(Long orgId);

    public List<EmployeeBean> getEmployeeData(Long deptId, Long locId, Long designId, Long valueOf);

    public EmployeeBean findById(Long empid);

    public int validateEmployee(String emploginname, Long orgid);

    public EmployeeBean create(EmployeeBean employee, String directry, FileNetApplicationClient instance);

    public EmployeeBean updateEmployee(EmployeeBean employee, String directry, FileNetApplicationClient instance);

    public void deleteEmployee(Long empid, Long orgId);

    public List<Employee> getEmployeeByMobileNo(String mobileNo, Long orgId);

    public List<Employee> getEmployeeByPancardNo(String pancardNo, Long orgId);

    public List<Employee> getEmployeeByUid(String uid, Long orgId);

    public void updateEmployeeLoggedInFlag(String no, String sessionId);

    public List<Employee> getEmployeeByGroupId(Long gmId, Long orgId);

	public String findEmployeeSessionId(Long empId, String string);

	public List<Employee> getEmployeeByListOfGroupId(List<Long> helpDeskUserGroupId, Long orgId);

	public List<EmployeeBean> getEmployeeByGroup(List<Long> helpDeskUserGroupId);
	
	public Long findAllCitizenCount(Long orgId);//As we are returning key and value pair of data
	
	  public List<EmployeeBean> getAllEmployeeBasedOnRoleCode(final Long orgId);
	  
	  boolean isUniqueMobileNumber(final String empMobileNo ,final Long empType,
				final Organisation organisation);
	  
	  boolean isUniqueEmailAddress(final String empEMail ,final Long empType,
				final Organisation organisation);

	public boolean saveEmployeeSession(EmployeeSession empSession);

	public EmployeeSession getEmployeeSessionDataByEmpId(Long empId);


	List<EmployeeBean> getAllEmployeeDetails();

	long getHitCounterUser(Long orgId);

	public List<EmployeeBean> getAllDeptEmployee(final Long orgId);

	/*
	 * public void updatePassword(String Password,Long empid);
	 * 
	 * public List<EmployeeBean> getAllAdmin(Long orgId);
	 */

	 

}
