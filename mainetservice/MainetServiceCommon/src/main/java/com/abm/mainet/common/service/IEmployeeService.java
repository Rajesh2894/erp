package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.EmployeeHistory;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.dto.EmployeeSearchDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.master.dto.EmployeeBean;

@WebService
public interface IEmployeeService {

    /**
     * @Method To get employee by empId Id.
     * @param empId
     * @param organisation {@link Organisation}
     * @param isdeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return
     */
    public int getCountOfGroup(Long gmId, Long orgId, String isDeleted);

    public Employee getAuthenticatedEmployee(String emploginname, String emppassword, Long emplType, Organisation organisation,
            String isdeleted);

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
    public List<Employee> getEmployeeByEmpMobileNo(String empMobileNo, Organisation organisation, String isDeleted);

    /*
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
    public Employee saveEmployeeDetails(Employee newEmployee, Organisation organisation, LocationMasEntity departmentLocation,
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
     * @Method To get {@link Employee} details by Login Name
     * @param organisation {@link Organisation}
     * @param isDeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Employee}
     */
    public Employee getEmployeeByLoginName(String emploginname, Organisation organisation, String isDeleted);

    /**
     *
     * @param org
     * @param DeptId
     * @return
     */
    public List<Employee> getAllListEmployeeByDeptId(Organisation org, long DeptId);

    /**
     *
     * @param employee
     * @return saved employee:
     */
    public Employee saveEmployee(Employee employee);

    public Employee saveEmployeeForAgency(Employee employee);

    public List<Employee> getAllAgencyList();

    public List<EmployeeDTO> getEmployeeByAgencyTypeAndBySortOption(Long agencyType, String agencyName, String sortValue,
            Long orgId);

    public List<Employee> getEmployeeListByLoginName(String emploginname,
            Organisation organisation, String isDeleted);

    public Employee getAgencyByEmplTypeAndEmail(String email, Long agencyType, Organisation organisation, String isDeleted);

    public void saveEditProfileInfo(Employee modifiedEmployee);

    void setEmployeeLoggedInFlag(Employee currentLoggedInEmployee);

    public void resetEmployeeLoggedInFlag(Employee employee);

    public List<EmployeeSearchDTO> getAllEmployeeInfoByOrgID(String empName);

    public Map<Long, String> getEmployeeLookUp();

    public Employee getAdminEncryptAuthenticatedEmployee(String emploginname, String emppassword, Long emplType, Long empId,
            Organisation organisation, String isdeleted);

    public Employee getAuthenticatedEncryptEmployee(String emploginname, String emppassword, Long emplType,
            Organisation organisation, String isdeleted);

    EmployeeDTO getEmployeeById(Long empId, Organisation organisation,
            String isdeleted);

    EmployeeBean findById(Long empid);

    List<EmployeeBean> findAll();

    EmployeeBean update(EmployeeBean entity);

    EmployeeBean create(EmployeeBean entity, String directory, FileNetApplicationClient fileNetApplicationClient, Long orgId,
            Long userId);

    List<EmployeeBean> getEmployeeData(Long deptId, Long locId,
            Long designId, Long orgid,Long gmid);

    EmployeeBean updateEmployee(EmployeeBean employee, String directry,
            FileNetApplicationClient filenetClient);

    void deleteEmployee(Long empid, Long orgId);

    List<EmployeeBean> getRegisterdMobile(String empmobno, Long valueOf);

    List<EmployeeBean> getRegisterdEmail(String empemail, Long valueOf);

    List<EmployeeBean> getRegisterdLoginName(String emploginname, Long valueOf);

    Map<Long, String> getGroupList(long orgid);

    List<GroupMaster> getGroupDataList(long orgid);

    List<EmployeeBean> getAllEmployee(Long orgId);

    // as per discussion with rajesh sir, employee mobile no and user name should be unique for
    // all organization
    int validateEmployee(String emploginname, Long orgid);

    List<Object[]> getAllEmployeeNames(Long orgid);

    List<Object[]> findAllEmpByOrg(Long orgId);

    List<Employee> findAllEmployeeByDept(Long orgId, Long deptId);

    List<Employee> findAllEmployeeByLocation(Long locId);

    /**
     * @param desgId
     * @param orgId
     * @return
     */
    List<Object[]> getAllEmpByDesignation(Long desgId, Long orgId);

    LocationMasEntity findEmployeeLocation(String empName, Long orgId);

    List<EmployeeBean> getAllEmployeeWithLoggedInEmployeeNotPresent(Long orgId, Long empId);

    List<Employee> getByGmId(Long gmId);

    Employee findEmployeeById(Long empId);

    List<Object[]> findAllEmpByLocation(Long orgId, Long deptId, List<Long> locationId);

    List<Object[]> findAllRoleByLocation(Long orgId, Long deptId, List<Long> locationId);

    List<Object[]> findActiveEmployeeByDeptId(long deptId, long orgId);

    /**
     * @Method To generate new password with length {newPasswordLength}
     * @param newPasswordLength.
     * @return password
     */
    public String generateNewPassword(int newPasswordLength);

    /**
     *
     * @param mobile
     * @param newPassword
     * @param userId
     * @return
     */
    public Employee setAdminEmployeePassword(String mobile, String newPassword, Employee userId);

    public List<Employee> getEmployeeByMobileNo(String mobileNo, Long orgId);

    public List<Employee> getEmployeeByUid(String uid, Long orgId);

    public List<Employee> getEmployeeByPancardNo(String pancardNo, Long orgId);

    public int getEmployeeByLocation(Long orgId, Long locId);

    List<Employee> findEmpList(long orgId);

    public List<Employee> findMappedEmployeeLevel1(final Long empId, final Long orgId, final Long dpDeptid);

    int getTotalEmployeeCountByRoles(List<Long> roleIds, Long orgId);

    public boolean updateEmpDetails(Employee validateEmployee);

    /**
     * This method will return all employees belonging in given list of locations
     * 
     * @param locIds Set of location ID
     * @return
     */
    List<Long> findAllEmployeeByLocation(Set<Long> locIds);

    List<Employee> getByGmId(List<Long> gmIds);

    List<Employee> getEmpDetailListByEmpIdList(List<Long> roleIds, Long orgId);

    List<EmployeeBean> getAllActiveEmployee();

    List<EmployeeBean> getActiveEmployeeByEmpMobileNo(String empMobileNo);

    Employee findEmployeeByIdAndOrgId(Long empId, Long orgId);

	public String findEmployeeSessionId(Long empId, String flag);

    List<GroupMaster> getGroupDataList(long orgid, long deptId);

	public void updateEmployeeLoggedInFlag(String no, String sessionId);

	public int getEmpCountByGmIdAndOrgId(Long orgId, Long gmid);

	public Employee validateLoggedInEmployee(String empLoginString, String empPassword, String orgId);

	List<Employee> findAllEmployeeByDesgId(Long orgId, Long desgId);

	boolean saveEmployeeSession(EmployeeSession empSession);

	EmployeeSession getEmployeeSessionDataByEmpId(Long empId);

	Long getEmpIdByEMpSession(String empSession);
	
	String getEmpNameByEmpId(Long empId);

	boolean isEmployeeUpdated(Long empId, long orgid, Date date);


	Map<String, String> latestUpdatedEmployeeDet(Long empId, Long orgid, String applicationId, String taskId, Date date,
			String empName);
	
	List<EmployeeBean> getAllActiveEmployee(final Long orgId);

	List<Employee> getActiveEmployeeList(Long orgId);

	Date getEmployeeUpdatedDateByEmpId(Long empId, Long orgId);
	
	List<Object[]> findAllEmpIntialInfoByOrg(Long orgId);

	List<Object[]> findActiveEmployeeAndDsgByDeptId(long deptId, long orgId);

	/**
	 * @param orgid
	 * @return
	 */
	public List<CommonMasterDto> getMasterDetail(Long orgid);

	/**
	 * @param orgid
	 * @param mastId
	 * @return
	 */
	String getMasterName(Long orgid, Long mastId);


	/**
	 * @param deptId
	 * @param locId
	 * @param designId
	 * @param orgid
	 * @param gmid
	 * @param masId
	 * @return
	 */
	List<EmployeeBean> getEmployeeDataForSFAC(Long deptId, Long locId, Long designId, Long orgid, Long gmid,
			Long masId);

	/**
	 * @return
	 */
	public Map<Long, String> fetchAllgroupList();

	/**
	 * @return
	 */
	public List<Object[]> findAllEmpForReporting();

	/**
	 * @param one
	 * @param cbboId
	 * @param empId
	 */
	public void updateIsDeletedFlagByMasId(String one, Long masId, Long empId,String uniqueId,Long orgId);

	List<Object[]> findAllActiveEmployeeByLocation(Long locId);

	String getEmpFullNameById(Long empId);

	List<Employee> findAllEmployeeByDesgIdDept(Long orgid, Long designId, long deptId);
	Map<String, String> findEmployeeLocLatLongAndPhoto(Long orgId, Long empId);

	List<Object[]> getEmployeesForVehicleDriverMas(Long orgId, String desgDriver);

	List<EmployeeBean> getAllEmployeeWithRole(Long orgId, Map<Long, String> groupLookup, String roleCode);
	
	public boolean saveEmpGrid(Long empid, String name, String mob, String oldNo);
	
	public boolean saveEmpGridWithWardZone(Long empid, String name, String mob, String oldNo, String zone,
			String ward, String sectorTehsil, String sector, Organisation org, String oldZone, String oldWard, String olsectorTehsil, String oldSector);
	
	public List<EmployeeBean> getEmployeeDataWithRole(Long deptId, Long locId, Long designId, Long valueOf, Long gmid,
			Map<Long, String> groupLookup, Long zone, Long empId, String roleCode);
	
    List<Employee> getActiveEmployeeByMobileNo(String empMobileNo);
	
	List<Employee> getEmployeeByEmpName(String empName);

	List<Employee> getAllListEmployeeByGmId(long org, long roleId);

	void updateEmployeeName(String empName, Long empId);

	Long getGroupIdByGroupCode(Long orgId, String grCode);

	List<Object[]> validateIsAuthUsersMobilesAndEmails(List<String> empmobnoList, List<String> emailList);
 
    List<Employee> getByGmIdAndWardZone(List<Long> gmIds, String loc1, String loc2, String loc3, String loc4,String loc5);

	List<Object[]> findAllRolesForDept(Long orgId, String status, Long deptId);
	
	List<Employee> getActiveEmployeeByEmpMobileNoAndLoginName(String empMobileNo,String empLogName);
}
