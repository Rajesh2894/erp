package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.abm.mainet.common.care.masters.DepartmentCommon;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DeptOrgMap;
import com.abm.mainet.common.workflow.dto.WorkflowTask;
/**
 * Repository : TbDepartment.
 */
@Repository
public interface TbDepartmentJpaRepository extends PagingAndSortingRepository<Department, Long> {

    @Query("select tbDepartmentEntity from Department tbDepartmentEntity where tbDepartmentEntity.dpDeptid = :dpDeptid")
    List<Department> findAllByDeptId(@Param("dpDeptid") Long dpDeptid);

    @Query("select tbDepartmentEntity from Department tbDepartmentEntity where tbDepartmentEntity.dpCheck ='Y' and tbDepartmentEntity.status='A' "
            + "order by tbDepartmentEntity.dpDeptdesc")
    List<Department> findActualDepartment();

    @Query("select deporgMapEntity from DeptOrgMap deporgMapEntity where deporgMapEntity.orgid = :orgId")
    List<DeptOrgMap> findByOrgId(@Param("orgId") Long orgId);

    @Query("select tbDepartmentEntity from Department tbDepartmentEntity where tbDepartmentEntity.dpDeptid in (:dpDeptids)"
            + "  and tbDepartmentEntity.status='A' and tbDepartmentEntity.dpCheck='Y' order by dpDeptdesc asc")
    List<Department> findAllByDeptId(@Param("dpDeptids") List<Long> dpDeptids);

    @Query("select tbDepartmentEntity from Department tbDepartmentEntity where tbDepartmentEntity.dpDeptid in (:dpDeptids)"
            + "  and tbDepartmentEntity.status='A' and tbDepartmentEntity.dpCheck='Y' order by dpNameMar asc")
    List<Department> findAllByDeptIdReg(@Param("dpDeptids") List<Long> dpDeptids);

    @Query("select tbDepartmentEntity  from Department tbDepartmentEntity where "
            + "tbDepartmentEntity.status in('A') order by tbDepartmentEntity.dpDeptdesc asc")
    List<Department> findAllByDeptIdOrgID();
    
    @Query(value = "select DISTINCT d.DP_DEPTID,d.DP_DEPTCODE,d.DP_DEPTDESC,d.DP_NAME_MAR from tb_department d,tb_workflow_task wt where d.DP_DEPTID=wt.DP_DEPTID and wt.WFTASK_ACTORID=:empId and wt.ORGID=:orgId", nativeQuery = true)
    List<Object[]> findAllByEmpIdOrgID(@Param("empId") Long empId,@Param("orgId") Long orgId);

    @Query("select  tbDepartmentEntity  from Department tbDepartmentEntity where "
            + "tbDepartmentEntity.dpDeptid=:department order by tbDepartmentEntity.dpDeptid desc")
    List<Department> searchDeptData(@Param("department") Long department);

    @Query("select  tbDepartmentEntity  from Department tbDepartmentEntity where "
            + "tbDepartmentEntity.dpDeptid=:department and tbDepartmentEntity.dpDeptcode=:deptCode order by tbDepartmentEntity.dpDeptid desc")
    List<Department> searchDeptData(@Param("department") Long department, @Param("deptCode") String deptCode);

    @Query("select  tbDepartmentEntity  from Department tbDepartmentEntity where "
            + "tbDepartmentEntity.dpDeptcode=:deptCode order by tbDepartmentEntity.dpDeptid desc")
    List<Department> searchDeptData(@Param("deptCode") String deptCode);

    @Query("select  count(tbDepartmentEntity.dpDeptid)  from Department tbDepartmentEntity where "
            + "tbDepartmentEntity.dpDeptdesc=:deptName")
    Long validateDepartment(@Param("deptName") String deptName);

    @Query("select dept, depLocMap,organization from Department dept, DeptLocationEntity depLocMap,Organisation organization where dept.dpDeptid = depLocMap.tbDepartment.dpDeptid"
            + " and organization.orgid = depLocMap.tbOrganisation.orgid"
            + " and depLocMap.locId = :locId and  depLocMap.tbOrganisation.orgid = :orgId order by dept.dpDeptdesc")
    List<Object> getDepartmentData(@Param("locId") Long locId, @Param("orgId") Long orgId);

    @Query("SELECT d.dpDeptcode FROM Department d, DeptOrgMap m WHERE d.dpDeptid=:deptId AND d.dpDeptid=m.department.dpDeptid AND m.orgid=:orgId AND m.mapStatus='A'")
    String findDepartmentShortCode(@Param("deptId") long deptId, @Param("orgId") long orgId);

    @Query("SELECT d.dpPrefix FROM Department d, DeptOrgMap m WHERE d.dpDeptid=:deptId AND d.dpDeptid=m.department.dpDeptid AND m.orgid=:orgId AND m.mapStatus='A'")
    String findDepartmentPrefixName(@Param("deptId") long deptId, @Param("orgId") long orgId);

    @Query("SELECT d FROM Department d, DeptOrgMap m WHERE d.dpDeptid=m.department.dpDeptid AND m.orgid=:orgId AND m.mapStatus='A'  order by d.dpDeptid desc")
    List<Department> findDepartmentWithPrefix(@Param("orgId") long orgId);

    @Query("SELECT d.cpmType FROM TbComparamMasEntity d WHERE d.cpmPrefix=:dpPrefix AND d.cpmModuleName=:deptCode")
    String isDpPrefixTypeHerarchy(@Param("deptCode") String deptCode, @Param("dpPrefix") String dpPrefix);

    @Query("SELECT dept.dpDeptid, dept.dpDeptdesc, dept.dpNameMar,dept.dpDeptcode FROM DeptOrgMap map, Department dept"
            + " WHERE map.orgid=:orgId AND map.mapStatus=:status AND map.department.dpDeptid=dept.dpDeptid AND dept.dpDeptdesc IS NOT NULL")
    public List<Object[]> findAllDepartmentByOrganization(@Param("orgId") Long orgId,
            @Param("status") String status);

    @Query("SELECT d.dpDeptdesc FROM Department d WHERE d.dpDeptid=:dpDeptid")
    String fetchDepartmentDescById(@Param("dpDeptid") Long deptId);
    
    @Query("SELECT d.dpNameMar FROM Department d WHERE d.dpDeptid=:dpDeptid")
    String fetchDepartmentDescRegById(@Param("dpDeptid") Long deptId);

    @Query("SELECT d FROM Department d  where d.dpDeptid in (select l.dpDeptId from LocationOperationWZMapping l where l.locationMasEntity.locId = ?1)")
    List<Department> finDeptListForLocation(Long locId);

    @Query("SELECT d.status FROM Department d WHERE d.dpDeptid=:dpDeptid")
    String checkDepartmentActive(@Param("dpDeptid") long deptId);

    @Query("SELECT d FROM Department d WHERE d.dpDeptcode=:dpDeptCode")
    Department findDepartmentByDpCode(@Param("dpDeptCode") String dpDeptCode);

    @Query("SELECT deptorgMap.department FROM DeptOrgMap deptorgMap WHERE "
            + "deptorgMap.department.status='A' AND "
            + "deptorgMap.department.dpCheck='Y' AND "
            + "deptorgMap.mapStatus='A' AND "
            + "deptorgMap.orgid=:orgId "
            + "ORDER BY deptorgMap.department.dpDeptdesc ASC")
    List<Department> findAllByDeptIdOrgIDActive(@Param("orgId") Long orgId);
    
    @Query("SELECT deptorgMap.department FROM DeptOrgMap deptorgMap WHERE "
            + "deptorgMap.department.status='A' AND "
            + "deptorgMap.mapStatus='A' AND "
            + "deptorgMap.orgid=:orgId "
            + "ORDER BY deptorgMap.department.dpDeptdesc ASC")
    List<Department> findAllByDeptIdOrgID(@Param("orgId") Long orgId);

    @Query("SELECT dept FROM DeptOrgMap map, Department dept"
            + " WHERE map.orgid=?1 AND map.mapStatus= ?2 AND map.department.dpDeptid=dept.dpDeptid AND dept.dpDeptcode = ?3")
    Department findDeptByCode(Long orgId, String status, String code);

    @Query("SELECT d FROM Department d, DeptOrgMap m WHERE d.dpDeptid=m.department.dpDeptid AND m.orgid=:orgId AND m.mapStatus='A' and d.dpCheck='Y' order by d.dpDeptid desc")
    Iterable<Department> findAllActualDept(@Param("orgId") long orgid);

    @Query("select tbDptEntity from Department tbDptEntity where "
            + "tbDptEntity.dpDeptid not in "
            + "(select depOrgMap.department.dpDeptid from DeptOrgMap depOrgMap where depOrgMap.orgid=:orgId and depOrgMap.mapStatus='A') "
            + "and tbDptEntity.status='A' "
            + "order by tbDptEntity.dpDeptdesc asc")
    List<Department> findAllNonMappedDepts(@Param("orgId") Long orgId);

    @Query("select tbDptEntity from Department tbDptEntity,DeptOrgMap depOrgMap where "
            + "tbDptEntity.dpDeptid = depOrgMap.department.dpDeptid "
            + "and tbDptEntity.status='A' "
            + "and depOrgMap.orgid=:orgId "
            + "order by tbDptEntity.dpDeptdesc asc")
    List<Department> findMappedDepartments(@Param("orgId") Long orgid);

    @Query("select dept from Department dept,DeptOrgMap depOrgMap where "
            + "dept.dpDeptid = depOrgMap.department.dpDeptid "
            + "and dept.status='A' and depOrgMap.orgid=:orgId "
            + "and dept.dpCheck='Y' "
            + "order by dept.dpDeptdesc asc")
    List<Department> findActualDept(@Param("orgId") Long orgid);

    @Query("select d.dpDeptid FROM Department d WHERE d.dpDeptcode =:deptCode and d.status ='A' ")
    Long getDepartmentIdByDeptCode(@Param("deptCode") String deptCode);

    @Query("SELECT tbDptEntity FROM Department tbDptEntity,DeptOrgMap depOrgMap WHERE  tbDptEntity.dpDeptid = depOrgMap.department.dpDeptid AND  "
            + " depOrgMap.mapStatus='A' AND  depOrgMap.orgid=:orgId  ORDER BY tbDptEntity.dpDeptdesc ASC")
    List<Department> findAllMappedDepartments(@Param("orgId") Long orgid);
    
    @Query(value = "select distinct  dsg.DSGID,dsg.DSGNAME,dsg.DSGNAME_REG from designation dsg\r\n" + 
			"join employee emp on dsg.DSGID = emp.DSGID\r\n" + 
			"join tb_department dpt on emp.DP_DEPTID = dpt.DP_DEPTID\r\n" + 
			"where dpt.DP_DEPTID =:deptId and emp.ORGID=:orgId", nativeQuery = true)
	List<Object[]> getAllDesgBasedOnDept(@Param("deptId") Long deptId, @Param("orgId") Long orgId);
	
	
	@Query("SELECT d.dpDeptid, d.dpDeptdesc, d.dpNameMar FROM Department d  where d.dpDeptid in (select l.tbDepartment.dpDeptid from Employee l where l.tbLocationMas.locId = ?1)")
	List<Object[]> finDeptListForLoc(Long locId);
	
	 @Query(value = "select * from tb_department d where STATUS='A' and DP_CHECK='Y' and DP_DEPTCODE in(select CPD_VALUE from tb_comparam_det where CPM_ID in (select CPM_ID from tb_comparam_mas where CPM_PREFIX='APS'))", nativeQuery = true)
	 List<Department> getAllDeptBasedOnPrefix();
	

}
