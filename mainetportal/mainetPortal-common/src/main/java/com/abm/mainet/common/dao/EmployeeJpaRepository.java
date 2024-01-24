package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.dto.EmployeeBean;

/**
 * Repository : Employee.
 */
public interface EmployeeJpaRepository extends PagingAndSortingRepository<Employee, Long> {

    @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
            + " and e.designation.dsgid =:designId  and e.isdeleted='0' ")
    List<Employee> findEmployeeData(@Param("deptId") Long deptId,
            @Param("designId") Long designId,
            @Param("orgid") Long orgid);

    @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId and e.isdeleted='0' ")
    List<Employee> findEmployeeData(@Param("deptId") Long deptId,
            @Param("orgid") Long orgid);

    @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
            + " and e.designation.dsgid =:designId and e.isdeleted='0'")
    List<Employee> findEmployeeDatabyDesign(@Param("deptId") Long deptId,
            @Param("designId") Long designId, @Param("orgid") Long orgid);

    @Modifying
    @Transactional
    @Query("UPDATE Employee e set e.isdeleted='1' where e.organisation.orgid=:orgid and e.empId=:empid")
    void deleteEmployee(@Param("empid") Long empid, @Param("orgid") Long orgid);
    
	/*
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query("UPDATE Employee e set e.emppassword=:emppassword where e.empId=:empId"
	 * ) void updateEmployee(@Param("emppassword") String
	 * emppassword, @Param("empId") Long empId);
	 */

    @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.empmobno=:empmobno "
            + "  and e.isdeleted='0' and e.emplType is null ")
    List<Employee> getRegisterdMobile(@Param("empmobno") String empmobno,
            @Param("orgid") Long orgid);

    @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.empemail=:empemail "
            + " and e.isdeleted='0' and e.emplType is null ")
    List<Employee> getRegisterdEmail(@Param("empemail") String empemail,
            @Param("orgid") Long orgid);

    @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.emploginname=:emploginname "
            + " and e.isdeleted='0' and e.emplType is null ")
    List<Employee> getRegisterdLoginName(
            @Param("emploginname") String emploginname,
            @Param("orgid") Long orgId);

    @Query("select rl from GroupMaster rl where rl.orgId.orgid=:orgid and rl.grStatus='A' and rl.grCode not like 'GR_CITIZEN_%'")
    List<GroupMaster> getGroupList(@Param("orgid") long orgid);

    // ********************************************
    @Query("select bd from Employee bd where bd.organisation.orgid=:orgId order by bd.empId desc ") // and bd.isdeleted ='0'
    List<Employee> getAllEmployee(@Param("orgId") Long orgId);

    @Query("select count(bd.empId) from Employee bd where bd.emploginname = :emploginname and bd.organisation.orgid=:orgId and bd.isdeleted ='0'")
    Long validateEmployee(@Param("emploginname") String emploginname,
            @Param("orgId") Long orgid);

    @Query("select e.empId,e.empname from Employee e  where e.organisation.orgid=:orgId")
    List<Object[]> getAllEmployeeNames(@Param("orgId") Long orgId);

    @Query("SELECT e.empname,e.empMName,e.empLName,e.empId "
            + "FROM Employee e  WHERE e.organisation.orgid=?1 and e.isdeleted = '0' order by e.empname asc")
    List<Object[]> findAllEmpByOrg(Long orgId);

    @Query("SELECT employee"
            + " FROM Employee employee WHERE employee.emploginname=:empName and employee.organisation.orgid=:orgid")
    Employee findEmployeeByName(@Param("empName") String empName, @Param("orgid") Long orgId);

    @Query("select e.empId,e.empname,e.empMName,e.empLName from Employee e  where e.organisation.orgid=:orgId and e.designation.dsgid=:desgId")
    List<Object[]> getAllEmpByDesignation(@Param("desgId") Long desgId, @Param("orgId") Long orgId);

    @Query("SELECT e FROM Employee e  WHERE e.organisation.orgid=?1 and  e.tbDepartment.dpDeptid =?2 and e.isdeleted = '0' order by e.empId asc")
    List<Employee> findAllEmployeeByDept(Long orgId, Long deptId);

    @Query("select bd from Employee bd where bd.organisation.orgid=:orgId and bd.isdeleted ='0' and bd.empId not in (:empId)")
    List<Employee> getAllEmployeeWithLoggedInEmployeeNotPresent(@Param("orgId") Long orgId, @Param("empId") Long empId);

    @Query("SELECT employee"
            + " FROM Employee employee WHERE employee.gmid=:gmid")
    List<Employee> getByGmId(@Param("gmid") Long gmid);

    @Query("SELECT employee FROM Employee employee "
            + "WHERE employee.empmobno=:mobileNo and employee.organisation.orgid=:orgid")
    List<Employee> validateEmpMobileNo(@Param("mobileNo") String mobileNo, @Param("orgid") Long orgid);

    @Query("SELECT employee FROM Employee employee "
            + "WHERE employee.empuid=:empuid and employee.organisation.orgid=:orgid")
    List<Employee> validateEmpUid(@Param("empuid") String empuid, @Param("orgid") Long orgid);

    @Query("SELECT employee FROM Employee employee "
            + "WHERE employee.panCardNo=:panCardNo and employee.organisation.orgid=:orgid")
    List<Employee> validateEmpPancardNo(@Param("panCardNo") String panCardNo, @Param("orgid") Long orgid);

    @Query("SELECT e.empId, e.empname,e.empMName, e.empLName FROM Employee e WHERE e.tbDepartment.dpDeptid=:dpDeptid AND e.organisation.orgid=:orgId AND e.authStatus='A' ORDER BY e.empname ASC")
    List<Object[]> findActiveEmployeeByDeptId(@Param("dpDeptid") Long dpDeptid, @Param("orgId") Long orgId);

    @Query("select emp from Employee emp where emp.organisation.orgid = :orgId ")
    List<Employee> getEmpList(@Param("orgId") Long orgId);
    
    @Query("select emp from Employee emp where emp.gmid in (:groupId) and orgid=:orgId")
	List<Employee> getEmployeeByListOfGroupId(@Param("groupId") List<Long> helpDeskUserGroupId,@Param("orgId") Long orgId);

    @Query("select emp from Employee emp where emp.gmid in (:groupId)")
	List<EmployeeBean> getEmployeeByGroup(@Param("groupId") List<Long> helpDeskUserGroupId);
    
    @Query("select count(e.empId) from Employee e  where  e.organisation.orgid=:orgId and e.gmid in (select g.gmId from GroupMaster g where g.grCode ='GR_CITIZEN_DEFAULT' ) and e.isdeleted='0' ")            
    Long findAllCitizenCount(@Param("orgId") Long orgId);

    @Query("select bd from Employee bd where bd.organisation.orgid=:orgId and bd.gmid  not in (select g.gmId from GroupMaster g where g.grCode like 'GR_CITIZEN_%' ) and bd.isdeleted='0' order by bd.empId desc ") // and bd.isdeleted ='0'
    List<Employee> getAllEmployeeBasedOnRoleCode(@Param("orgId") Long orgId);
    
    @Query("select bd from Employee bd where bd.organisation.orgid=:orgId and bd.gmid not in (select g.gmId from GroupMaster g where g.grCode like 'GR_CITIZEN_%' ) order by bd.empId desc ")
    List<Employee> getAllDeptEmployee(@Param("orgId") Long orgId);
    
	/*
	 * @Query("select bd from Employee bd where bd.organisation.orgid=:orgId and bd.gmid not in (select g.gmId from GroupMaster g where g.grCode ='SUPER_ADMIN' )  order by bd.empId desc "
	 * ) // and bd.isdeleted ='0' List<Employee> getAllAdmin(@Param("orgId") Long
	 * orgId);
	 */
    
 
    @Query("select bd from Employee bd   order by bd.empId desc ") 
    List<Employee> getAllEmployeeDetails();
    
	@Query(value="select TOTAL_COUNT from TB_PAGE_MASTER  where ORGID=:orgId",nativeQuery = true)
	Long getTotalUser(@Param("orgId") Long orgId);
}
