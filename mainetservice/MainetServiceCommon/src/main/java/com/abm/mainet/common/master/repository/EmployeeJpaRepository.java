package com.abm.mainet.common.master.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.EmployeeWardZoneMapping;

/**
 * Repository : Employee.
 */
public interface EmployeeJpaRepository extends PagingAndSortingRepository<Employee, Long> {

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ " and e.designation.dsgid =:designId  and e.tbLocationMas.locId =:locId and e.isDeleted='0' ")
	List<Employee> findEmployeeData(@Param("deptId") Long deptId, @Param("locId") Long locId,
			@Param("designId") Long designId, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId and e.isDeleted='0' ")
	List<Employee> findEmployeeData(@Param("deptId") Long deptId, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ " and e.designation.dsgid =:designId and e.isDeleted='0'")
	List<Employee> findEmployeeDatabyDesign(@Param("deptId") Long deptId, @Param("designId") Long designId,
			@Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ "  and e.tbLocationMas.locId =:locId and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByLocID(@Param("deptId") Long deptId, @Param("locId") Long locId,
			@Param("orgid") Long orgid);

	@Modifying
	@Transactional
	@Query("UPDATE Employee e set e.isDeleted='1' where e.organisation.orgid=:orgid and e.empId=:empid")
	void deleteEmployee(@Param("empid") Long empid, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.empmobno=:empmobno "
			+ "  and e.isDeleted='0' and e.emplType is null ")
	List<Employee> getRegisterdMobile(@Param("empmobno") String empmobno, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.empemail=:empemail "
			+ " and e.isDeleted='0' and e.emplType is null ")
	List<Employee> getRegisterdEmail(@Param("empemail") String empemail, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.emploginname=:emploginname "
			+ " and e.isDeleted='0' and e.emplType is null ")
	List<Employee> getRegisterdLoginName(@Param("emploginname") String emploginname, @Param("orgid") Long orgId);

	@Query("select rl from GroupMaster rl where rl.orgId.orgid=:orgid and rl.grStatus='A'")
	List<GroupMaster> getGroupList(@Param("orgid") long orgid);

	// ********************************************
	@Query("select bd from Employee bd where bd.organisation.orgid=:orgId") // and bd.isDeleted ='0'
	List<Employee> getAllEmployee(@Param("orgId") Long orgId);

	@Query("select count(bd.empId) from Employee bd where bd.emploginname = :emploginname and bd.organisation.orgid=:orgId and bd.isDeleted ='0'")
	Long validateEmployee(@Param("emploginname") String emploginname, @Param("orgId") Long orgid);

	@Query("select e.empId,e.empname from Employee e  where e.organisation.orgid=:orgId")
	List<Object[]> getAllEmployeeNames(@Param("orgId") Long orgId);

	@Query("SELECT e.empname,e.empmname,e.emplname,e.empId,e.designation.dsgname "
			+ "FROM Employee e  WHERE e.organisation.orgid=?1 and e.isDeleted = '0' order by e.empname asc")
	List<Object[]> findAllEmpByOrg(Long orgId);

	@Query("SELECT employee"
			+ " FROM Employee employee WHERE employee.emploginname=:empName and employee.organisation.orgid=:orgid")
	Employee findEmployeeByName(@Param("empName") String empName, @Param("orgid") Long orgId);

	@Query("select e.empId,e.empname,e.empmname,e.emplname from Employee e  where e.organisation.orgid=:orgId and e.designation.dsgid=:desgId")
	List<Object[]> getAllEmpByDesignation(@Param("desgId") Long desgId, @Param("orgId") Long orgId);

	@Query("SELECT e FROM Employee e  WHERE e.organisation.orgid=?1 and  e.tbDepartment.dpDeptid =?2 and e.isDeleted = '0' order by e.empId asc")
	List<Employee> findAllEmployeeByDept(Long orgId, Long deptId);

	@Query("SELECT e FROM Employee e  WHERE e.tbLocationMas.locId =:locId")
	List<Employee> findAllEmployeeByLocation(@Param("locId") Long locId);

	
	@Query("SELECT e.empname,e.empmname,e.emplname,e.empId,e.designation.dsgname FROM Employee e  WHERE  "
			+ "e.tbLocationMas.locId =:locId and e.isDeleted = '0'")
	List<Object[]> findAllActiveEmployeeByLocation(@Param("locId") Long locId);
	
	
	/**
	 * This method will return all employees belonging in given list of locations
	 * 
	 * @param locIds Set of location ID
	 * @return
	 */
	@Query("SELECT distinct e.empId FROM Employee e  WHERE e.tbLocationMas.locId in (:locIds) order by e.empId asc")
	List<Long> findAllEmployeeByLocation(@Param("locIds") Set<Long> locIds);

	@Query("select bd from Employee bd where bd.organisation.orgid=:orgId and bd.isDeleted ='0' and bd.empId not in (:empId)")
	List<Employee> getAllEmployeeWithLoggedInEmployeeNotPresent(@Param("orgId") Long orgId, @Param("empId") Long empId);

	@Query("SELECT employee" + " FROM Employee employee WHERE employee.gmid=:gmid")
	List<Employee> getByGmId(@Param("gmid") Long gmid);

	@Query("SELECT employee" + " FROM Employee employee WHERE employee.gmid in (:gmids)")
	List<Employee> getByGmId(@Param("gmids") List<Long> gmids);
	
	@Query("SELECT employee FROM Employee employee, EmployeeWardZoneMapping wz WHERE employee.empId = wz.empId and employee.gmid in (:gmids) and (wz.loc1 is null or wz.loc1 like concat('%',:loc1,'%')) and (wz.loc2 is null or wz.loc2 like concat('%',:loc2,'%')) and (wz.loc3 is null or wz.loc3 like concat('%',:loc3,'%')) and (wz.loc4 is null or wz.loc4 like concat('%',:loc4,'%')) and (wz.loc5 is null or wz.loc5 like concat('%',:loc5,'%'))")
	List<Employee> getByGmIdAndWardZone(@Param("gmids") List<Long> gmids, @Param("loc1") String loc1, @Param("loc2") String loc2, @Param("loc3") String loc3, @Param("loc4") String loc4, @Param("loc5") String loc5);

	@Query("SELECT e.empname,e.empmname,e.emplname,e.empId "
			+ "FROM Employee e  WHERE e.organisation.orgid=?1 and e.tbDepartment.dpDeptid =?2 and e.tbLocationMas.locId in (?3) and e.isDeleted = '0' order by e.empname asc")
	List<Object[]> findAllEmpByLocation(Long orgId, Long deptId, List<Long> locationId);

	@Query("SELECT distinct g.gmId,g.grDescEng,g.grDescReg "
			+ "FROM Employee e , GroupMaster g  WHERE e.gmid = g.gmId and e.organisation.orgid=?1 and e.tbDepartment.dpDeptid =?2 and e.tbLocationMas.locId in (?3) and e.isDeleted = '0' order by e.empname asc")
	List<Object[]> findAllRoleByLocation(Long orgId, Long deptId, List<Long> locationId);

	@Query("SELECT employee FROM Employee employee "
			+ "WHERE employee.empmobno=:mobileNo and employee.organisation.orgid=:orgid")
	List<Employee> validateEmpMobileNo(@Param("mobileNo") String mobileNo, @Param("orgid") Long orgid);

	@Query("SELECT employee FROM Employee employee "
			+ "WHERE employee.empuid=:empuid and employee.organisation.orgid=:orgid")
	List<Employee> validateEmpUid(@Param("empuid") String empuid, @Param("orgid") Long orgid);

	@Query("SELECT employee FROM Employee employee "
			+ "WHERE employee.panNo=:panNo and employee.organisation.orgid=:orgid")
	List<Employee> validateEmpPancardNo(@Param("panNo") String panNo, @Param("orgid") Long orgid);

	@Query("SELECT e.empId, e.empname,e.empmname, e.emplname FROM Employee e WHERE e.tbDepartment.dpDeptid=:dpDeptid AND e.organisation.orgid=:orgId AND e.authStatus='A' ORDER BY e.empname ASC")
	List<Object[]> findActiveEmployeeByDeptId(@Param("dpDeptid") Long dpDeptid, @Param("orgId") Long orgId);

	@Query("SELECT e.empId, e.empname,e.empmname, e.emplname, desg.dsgname FROM Employee e, Designation desg  WHERE e.tbDepartment.dpDeptid=:dpDeptid AND e.organisation.orgid=:orgId AND e.designation.dsgid = desg.dsgid AND e.authStatus='A' ORDER BY e.empname ASC")
	List<Object[]> findActiveEmployeeAndDsgByDeptId(@Param("dpDeptid") Long dpDeptid, @Param("orgId") Long orgId);

	@Query("select COUNT(e) from Employee e  where e.organisation.orgid=:orgId and e.tbLocationMas.locId=:locId")
	int getEmpByLocation(@Param("orgId") Long orgId, @Param("locId") Long locId);

	@Query("select emp from Employee emp where emp.organisation.orgid = :orgId ")
	List<Employee> getEmpList(@Param("orgId") Long orgId);

	@Query("select emp from Employee emp where emp.organisation.orgid = :orgId and emp.empId in :empIds ")
	List<Employee> getEmpDetailListByEmpIdList(@Param("orgId") Long orgId, @Param("empIds") List<Long> empId);

	@Query("select emp from Employee emp where emp.isDeleted='0' ")
	List<Employee> getAllActiveEmployee();

	@Query("select emp from Employee emp where emp.isDeleted='0' and emp.empmobno=:mobileNo")
	List<Employee> getActiveEmployeeByEmpMobileNo(@Param("mobileNo") String empMobileNo);

	@Query("select dept.dpDeptdesc, desg.dsgname, loc.locNameEng, org.ONlsOrgname, gp.grCode from Department dept, Designation desg, LocationMasEntity loc, "
			+ " Organisation org, GroupMaster gp where dept.dpDeptid=:deptId and desg.dsgid=:desigId and loc.locId=:locId and org.orgid=:orgId and gp.gmId=:gmId")
	List<Object[]> getDescriptionDetailsByIds(@Param("deptId") Long deptId, @Param("desigId") Long desigId,
			@Param("locId") Long locId, @Param("orgId") Long orgId, @Param("gmId") Long gmId);

	@Query("select emp from Employee emp where emp.organisation.orgid = :orgId and emp.empId = :empId ")
	Employee findEmployeeByIdAndOrgId(@Param("empId") Long empId, @Param("orgId") Long orgId);

	@Query("select rl from GroupMaster rl where rl.orgId.orgid=:orgid and rl.dpDeptId=:dpDeptId and rl.grStatus='A'")
	List<GroupMaster> getGroupList(@Param("orgid") long orgid, @Param("dpDeptId") long dpDeptId);

	@Query("select COUNT(e) from Employee e  where e.organisation.orgid=:orgId and e.gmid=:gmid")
	int getEmpCountByGmIdAndOrgId(@Param("orgId") Long orgId, @Param("gmid") Long gmid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ " and e.designation.dsgid =:designId  and e.tbLocationMas.locId =:locId and e.gmid=:gmid and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByIds(@Param("deptId") Long deptId, @Param("locId") Long locId,
			@Param("designId") Long designId, @Param("orgid") Long orgid, @Param("gmid") Long gmid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ "  and e.gmid=:gmid and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByGmId(@Param("deptId") Long deptId, @Param("gmid") Long gmid,
			@Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ " and e.designation.dsgid =:designId and e.gmid=:gmid and e.isDeleted='0'")
	List<Employee> findEmployeeDatabyDesignAndGmId(@Param("deptId") Long deptId, @Param("designId") Long designId,
			@Param("orgid") Long orgid, @Param("gmid") Long gmid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
			+ "  and e.tbLocationMas.locId =:locId and e.gmid=:gmid and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByLocIDAndGmId(@Param("deptId") Long deptId, @Param("locId") Long locId,
			@Param("orgid") Long orgid, @Param("gmid") Long gmid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbLocationMas.locId =:locId and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByLocId(@Param("locId") Long locId, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.designation.dsgid =:designId and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByDesgId(@Param("designId") Long designId, @Param("orgid") Long orgid);
	
	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.designation.dsgid =:designId and e.tbDepartment.dpDeptid=:deptId and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByDesgIdDept(@Param("designId") Long designId, @Param("orgid") Long orgid,@Param("deptId") long deptId);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.gmid=:gmid and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByGmId(@Param("gmid") Long gmid, @Param("orgid") Long orgid);

	@Query("select e from Employee e  where e.organisation.orgid=:orgid and e.designation.dsgid =:designId and e.tbLocationMas.locId =:locId  and e.isDeleted='0' ")
	List<Employee> findEmployeeDataByLocIdAndDesgId(@Param("designId") Long designId, @Param("locId") Long locId,
			@Param("orgid") Long orgid);

	@Query("SELECT m.empId FROM Employee m WHERE m.empisecuritykey =:sessionId ")
	Long getEmpIdByEMpSession(@Param("sessionId") String sessionId);

	@Query("select e.empname from Employee e  where e.empId=:empId and e.isDeleted='0' ")
	String getEmpNameByEmpId(@Param("empId") Long empId);

	@Query("select e.empname, e.emplname from Employee e  where e.empId=:empId and e.isDeleted='0' ")
	Object[] getEmpFullNameByEmpId(@Param("empId") Long empId);
	
	
	@Query("select count(e)  from Employee e  where e.empId=:empId and e.updatedDate<:date and e.organisation.orgid=:orgid")
	int isEmployeeDataUpdated(@Param("empId") Long empId, @Param("orgid") Long orgid,@Param("date") Date date);
	
	@Query("select bd from Employee bd where bd.organisation.orgid=:orgId and bd.isDeleted='0'") // and bd.isDeleted ='0'
	List<Employee> getAllActiveEmployee(@Param("orgId") Long orgId);

	@Query("select e.updatedDate  from Employee e  where e.empId=:empId and e.organisation.orgid=:orgid")
	Date getEmployeeUpdatedDate(@Param("empId") Long empId, @Param("orgid") Long orgid);
	
	@Query("SELECT e.empname,e.empmname,e.emplname,e.empId"
			+ " FROM Employee e  WHERE e.organisation.orgid=?1 and e.isDeleted = '0' order by e.empname asc")
	List<Object[]> findAllEmpIntialInfoByOrg(Long orgId);

	/**
	 * @return
	 */
	@Query("select rl from GroupMaster rl where rl.grStatus='A'")
	List<GroupMaster> fetchAllgroupList();
	

	@Query("SELECT e.empname,e.empmname,e.emplname,e.empId,e.designation.dsgname "
			+ "FROM Employee e  WHERE e.isDeleted = '0' order by e.empname asc")
	List<Object[]> findAllEmpForReporting();



	Employee findByMasIdAndOrganisationAndEmpemailNotNull(Long cbboId, Organisation org);

	/**
	 * @param status
	 * @param cbboId
	 * @param userId
	 */
	@Modifying
	@Query("Update Employee e set  e.isDeleted=:status , e.updatedBy=:userId where (e.masId=:masId or e.emploginname=:uniqueId) and e.organisation.orgid=:orgId")
	void updateIsDeletedFlagByMasId(@Param("status") String status,@Param("masId") Long masId, @Param("userId") Long userId,@Param("uniqueId") String uniqueId,@Param("orgId") Long orgId);

	
	@Query(value = "SELECT e1.DP_DEPTID, dept.DP_DEPTDESC, e1.dsgid, des.dsgname, e1.REPORTING_EMPID, e2.empname, e2.emplname FROM "
			+ "Employee e1 LEFT JOIN Employee e2 ON e1.REPORTING_EMPID = e2.empId  JOIN TB_DEPARTMENT dept ON e1.DP_DEPTID = dept.DP_DEPTID " + 
			"JOIN DESIGNATION des ON e1.DSGID = des.DSGID WHERE e1.empId=:empId AND e1.orgid=:orgid", nativeQuery = true)
	Object[] getEmployeDetailsById(@Param("empId") Long empId, @Param("orgid") Long orgid);

	
	@Query("SELECT e.empId, e.empname, e.empmname, e.emplname, e.tbDepartment.dpDeptid,  e.tbDepartment.dpDeptdesc, "
			+ "e.designation.dsgid, e.designation.dsgname, e.cpdTtlId, e.empGender, e.empmobno, e.empemail, e.empAddress, "
			+ "e.empAddress1, e.emppincode, e.tbDepartment.dpNameMar, e.designation.dsgnameReg FROM Employee e  WHERE e.empId=:empId ")
	Object[] getEmployeeDetailsByEmpId(@Param("empId") Long empId);

	@Query("SELECT e.empId, e.empname, e.emplname FROM Employee e WHERE e.organisation.orgid=:orgid and "
			+ " e.designation.dsgname like :desgDriver and e.isDeleted = '0' order by e.empname asc")
	List<Object[]> getEmployeesForVehicleDriverMas(@Param("orgid") Long orgid, @Param("desgDriver") String desgDriver);
	
	@Query("SELECT COALESCE(e.empname, ''), COALESCE(e.empmname, ''), COALESCE(e.emplname, ''), COALESCE(e.empId, '')"
	        + " FROM Employee e WHERE e.organisation.orgid = :orgId and e.tbDepartment.dpDeptcode = :Deptcode"
	        + " ORDER BY e.empname ASC")
	List<Object[]> findAllEmpInfoByOrg(@Param("orgId") Long orgId, @Param("Deptcode") String Deptcode);
	
	List<Employee> findDistinctByEmpmobno(String mob);
	
	@Query("select e from Employee e where " +
	        "(?6 IS NULL OR (e.empId = ?6)) and " +
	        "(?1 IS NULL OR (e.tbDepartment.dpDeptid = ?1)) and " +
	        "(?2 IS NULL OR (e.tbLocationMas.locId = ?2)) and " +
	        "(?3 IS NULL OR (e.designation.dsgid = ?3)) and " +
	        "(?4 IS NULL OR (e.organisation.orgid = ?4)) and " +
	        "(?5 IS NULL OR (e.gmid = ?5)) and " +
	        "e.isDeleted = '0'")
	List<Employee> findAllEmpWithAllCondNoWard(
	        Long deptId, Long locId, Long designId, Long orgid, Long gmid,
	        Long empId);
	
	@Query("select e from Employee e, EmployeeWardZoneMapping ew where " +
	        "e.empId = ew.empId and " +
	        "(?6 IS NULL OR (e.empId = ?6)) and " +
	        "(?1 IS NULL OR (e.tbDepartment.dpDeptid = ?1)) and " +
	        "(?2 IS NULL OR (e.tbLocationMas.locId = ?2)) and " +
	        "(?3 IS NULL OR (e.designation.dsgid = ?3)) and " +
	        "(?4 IS NULL OR (e.organisation.orgid = ?4)) and " +
	        "(?5 IS NULL OR (e.gmid = ?5)) and " +
	        "(?7 IS NULL OR (ew.zones = ?7 OR ew.zones LIKE ?8%)) and " +
	        "e.isDeleted = '0'")
	List<Employee> findAllEmpWithAllCond(
	        Long deptId, Long locId, Long designId, Long orgid, Long gmid, Long empId,
	        String string, String string2);

	
	@Query("select bd from Employee bd where bd.empname=:empname and bd.isDeleted='0'")
	List<Employee> getEmployeeByEmpName(@Param("empname") String empName);
	
	@Query("select gm.gmId from GroupMaster gm where gm.orgId.orgid=:orgid and gm.grCode=:grCode")
	Long getGroupIdByGroupCode(@Param("orgid") long orgid, @Param("grCode") String grCode);
	
	@Modifying
	@Query("Update Employee e set e.empname=:empName, e.updatedBy=:empId, e.updatedDate=:updatedDate where e.empId=:empId")
	void updateEmpNameByEmpId(@Param("empName") String empName, @Param("empId") Long empId, @Param("updatedDate") Date updatedDate);

	@Query("SELECT DISTINCT CASE WHEN e.empmobno IN (:empmobnoList) THEN e.empmobno ELSE '' END AS empmobno, "
			+ " CASE WHEN e.empemail IN (:emailList) THEN e.empemail ELSE '' END AS empemail FROM Employee e "
			+ " WHERE (e.empmobno IN (:empmobnoList) OR e.empemail IN (:emailList)) AND e.isDeleted='0'")
	List<Object[]> validateIsAuthUsersMobilesAndEmails(@Param("empmobnoList") List<String> empmobnoList,
			@Param("emailList") List<String> emailList);

	@Query("select gm.gmId,gm.grCode,gm.grDescEng,gm.grDescReg from GroupMaster gm where gm.orgId.orgid=:orgId and gm.grStatus=:status and gm.dpDeptId =:deptId and  "+
			"gm.gmId in (select distinct emp.gmid from Employee emp)")
			public List<Object[]> findAllRolesForDept(@Param("orgId") Long orgId,@Param("status") String status,@Param("deptId") Long deptId);
			
	@Query("select emp from Employee emp where emp.isDeleted='0' and emp.empmobno=:mobileNo and emp.emploginname=:loginName")
	List<Employee> getActiveEmployeeByEmpMobileNoAndLoginName(@Param("mobileNo") String empMobileNo, @Param("loginName") String empLogName);
}
