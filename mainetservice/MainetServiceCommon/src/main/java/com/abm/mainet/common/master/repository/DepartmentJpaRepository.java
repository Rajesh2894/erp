package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Department;

/**
 * Repository : Department.
 */
@Repository
public interface DepartmentJpaRepository extends JpaRepository<Department, Long> {
   //D#154953
    @Query("select tbDepartmentEntity.dpDeptid,tbDepartmentEntity.dpDeptdesc, tbDepartmentEntity.dpNameMar "
    		+ " from Department tbDepartmentEntity where tbDepartmentEntity.dpCheck ='Y'  and tbDepartmentEntity.status='A' "
    		+ " order by tbDepartmentEntity.dpDeptdesc asc")
    List<Object[]> getAllDeptTypeNames();

    @Query("select e.dpDeptcode from Department e  where e.dpDeptid =:dpDeptid")
    String getDeptCode(@Param("dpDeptid") Long dpDeptid);

    @Query("select d.dpDeptid FROM Department d WHERE d.dpDeptcode =:dpDeptcode and d.status ='A' ")
    Long getDepartmentIdByDeptCode(@Param("dpDeptcode") String dpDeptcode);
    
    @Query("select d.dpDeptdesc FROM Department d WHERE d.dpDeptcode =:dpDeptcode and d.status ='A' ")
    String getDepartmentDescByDeptCode(@Param("dpDeptcode") String dpDeptcode);

    List<Department> findByStatus(String status);
    
    @Query("select d FROM Department d WHERE d.dpDeptid =:deptId and d.status ='A' ")
    Department findDeptById(@Param("deptId") Long deptId);
    
    
	@Query("SELECT dp.dpDeptdesc, des.dsgname, dp.dpNameMar, des.dsgnameReg FROM Department dp, Designation des "
			+ "	where dp.dpDeptid=:dpDeptid and des.dsgid=:dsgid ")
	Object[] getDeptAndDesgById(@Param("dpDeptid") Long dpDeptid, @Param("dsgid") Long dsgid);
	

}
