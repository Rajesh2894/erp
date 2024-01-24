package com.abm.mainet.common.master.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.dto.EmployeeSearchDTO;

public interface IEmployeeDAO {

    int getCountOfGroup(Long gmId, Long orgId, String isDeleted);

    public abstract Employee getEmployeeByLoginName(String emploginname, Organisation organisation,
            String isDeleted);

    public abstract Employee getEmployeeById(Long empId, Organisation organisation, String isdeleted);

    public abstract Employee getAuthenticatedEmployee(String emploginname, String emppassword,
            Long emplType, Organisation organisation, String isdeleted);

    public abstract Employee getAdminAuthenticatedEmployee(String emploginname, String emppassword,
            Long emplType, Long empId, Organisation organisation, String isdeleted);

    // prasant.sahu---start
    public abstract List<Employee> getEmployeeByEmail(String empEmail, String empPassword,
            Organisation organisation);

    public abstract List<Employee> getEmployeeByEmailIdAndEmpType(String empEMail, Long empType,
            Organisation organisation, String isDeleted, boolean isAgency);

    public abstract List<Employee> getEmployeeByEmpMobileNo(String empmobno,
            Organisation organisation, String isDeleted);

    public abstract List<Employee> getEmployeeByEmpMobileNoAndEmpType(String empMobileNo,
            Long empType, Organisation organisation, String isDeleted, boolean isAgency);

    public abstract List<Employee> getEmployeeListByLoginName(String emploginname,
            Organisation organisation, String isDeleted);

    public abstract Employee saveEmployee(Employee employee);

    public abstract Employee saveEmployeeForAgency(Employee employee);

    public abstract List<Employee> getAllEmployeeByDeptId(Organisation orgId, long deptId);

    public abstract List<EmployeeDTO> getEmployeeByAgencyTypeAndBySortOption(Long agencyType,
            String agencyName, String sortValue, Long orgid);

    public abstract List<Employee> getAllAgencyList(Organisation organisation);

    public abstract Employee getAgencyByEmplTypeAndEmail(String email, Long agencyType,
            Organisation organisation, String isDeleted);

    public abstract void persistModifiedCitizenInfo(Employee modifiedEmployee);

    public abstract void setEmployeeLoggedInFlag(Employee currentLoggedInEmployee);

    public void resetEmployeeLoggedInFlag(String flag, long empid);

    public abstract List<EmployeeSearchDTO> findEmployeeInfo(String empName, Organisation organisation);

    public Map<Long, String> getEmployeeLookUp(Organisation organisation);

    List<Employee> findMappedEmployeeLevel1(Long empId, Long orgId, Long dpDeptid);

    int getTotalEmployeeCountByRoles(List<Long> roleIds, Long orgId);

    public abstract boolean updateEmpDetails(Employee employee);

    String findEmployeeSessionId(Long empId, String flag);

    void updateEmployeeLoggedInFlag(String no, String sessionId);

    public List<Long> getEmpId(Long orgId, String isdeleted);
    
    public  List<Employee> getAllEmployeeByReportingManager(Long reportingManager,Long orgId,String isdeleted);

	/**
	 * @param deptId
	 * @param locId
	 * @param designId
	 * @param orgid
	 * @param gmid
	 * @param masId
	 * @return
	 */
	List<Employee> findEmployeeDataByIdsSFAC(Long deptId, Long locId, Long designId, Long orgid, Long gmid, Long masId);
	
	 public  List<Employee> getAllMPOSEmployee(String firstName,String lastName,String loginName);

	List<Employee> getAllEmployeeByGmId(long orgId, long roleId);
}