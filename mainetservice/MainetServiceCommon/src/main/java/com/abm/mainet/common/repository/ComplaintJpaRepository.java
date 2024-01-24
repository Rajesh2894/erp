
package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.DepartmentComplaint;

public interface ComplaintJpaRepository extends JpaRepository<DepartmentComplaint, Long> {

    @Query("select c.deptCompId,d.dpDeptdesc,d.dpNameMar,d.dpDeptid,c.orgId,d.status from DepartmentComplaint c join c.department d where c.orgId =?1 and c.status = ?2 and d.status is 'A'")
    List<Object[]> findAllCompRecords(Long orgId, String activeFlag);

    @Query("SELECT c FROM ComplaintType c WHERE c.orgId=:orgId AND c.status = 'Y' and c.isActive is true")
    List<ComplaintType> findAllComplaintsByOrg(@Param("orgId") Long orgId);

    @Query("SELECT d FROM DepartmentComplaint d join fetch d.complaintTypes c"
            + " WHERE d.deptCompId = ?1")
    DepartmentComplaint findRecord(Long deptCompId);

    @Modifying
    @Query("update ComplaintType e set e.status = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.compId in ?3")
    @Transactional
    void deleteRecordDetails(String flag, Long empId, List<Long> id);

    @Query("SELECT d FROM DepartmentComplaint d where d.orgId =:orgId and d.status IS 'Y'")
    List<DepartmentComplaint> getcomplainedDepartment(@Param("orgId") Long orgId);

    @Query("SELECT d FROM DepartmentComplaint d where d.department.dpDeptid =:depId and d.orgId =:orgId and d.status IS 'Y'")
    DepartmentComplaint findDepartmentComplaintByDepartmentId(@Param("depId") Long depId, @Param("orgId") Long orgId);

    @Query("SELECT c FROM ComplaintType c where c.compId =:compId")
    ComplaintType findComplaintTypeById(@Param("compId") Long compId);

    @Query("SELECT d FROM DepartmentComplaint d join fetch d.complaintTypes c"
            + " WHERE d.orgId=:orgId AND d.department.dpDeptid=:depId AND d.status IS 'Y' and d.department.status IS 'A'")
    DepartmentComplaint findAllComplaintsByDepartment(@Param("orgId") Long orgId,
            @Param("depId") Long depId);

    @Query("SELECT dept.dpDeptid, dept.dpDeptdesc,dept.dpNameMar FROM DeptOrgMap map, Department dept"
            + " WHERE map.orgid=:orgId AND map.mapStatus=:status AND map.department.dpDeptid=dept.dpDeptid AND "
            + " dept.dpDeptid not in (select c.department.dpDeptid from DepartmentComplaint c where c.department.status = 'A' and c.status='Y' and c.orgId=:orgId )")
    List<Object[]> getNotcomplainedDepartment(@Param("orgId") Long orgId, @Param("status") String status);
    /*D#130421 - check if workflow is active or not for given subtype*/
    @Query(value ="select count(*) from TB_WORKFLOW_REQUEST w, TB_WORKFLOW_MAS m WHERE m.COMP_ID=:compId AND m.WF_ID = w.WORFLOW_TYPE_ID AND w.STATUS = 'PENDING' AND m.WF_STATUS = 'Y'",
    		nativeQuery=true)
    Integer countPendingAppForComplaintType(@Param("compId") Long compId);

    @Modifying
    @Transactional
    @Query("update DepartmentComplaint dc set dc.status ='N', dc.updatedDate = CURRENT_DATE where dc.deptCompId =:deptCompId")
    void inactiveComplaintDepartment(@Param("deptCompId") Long deptCompId);

    @Modifying
    @Transactional
    @Query("update ComplaintType c set c.status ='N', c.updatedDate = CURRENT_DATE where c.departmentComplaint.deptCompId =:deptCompId")
    void inactiveAllComplaintsByComplaintDepartment(@Param("deptCompId") Long deptCompId);

}
