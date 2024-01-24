package com.abm.mainet.workManagement.roadcutting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.roadcutting.domain.RoadCuttingEntity;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface RoadCuttingRepository extends JpaRepository<RoadCuttingEntity, Long> {

    RoadCuttingEntity findByOrgIdAndApmApplicationId(Long orgId, Long apmApplicationId);
    
    @Query("SELECT j.dpDeptid,j.dpDeptdesc,j.dpNameMar,k.smServiceName,k.smServiceNameMar,k.smServiceId FROM Department j,ServiceMaster k,DeptOrgMap m "
    		+"where k.smServiceName like '%No Objection Certificate%' and j.dpDeptid=k.tbDepartment.dpDeptid and "
    		+"m.department.dpDeptid=j.dpDeptid and " 
    		+"m.orgid=k.orgid and "
    		+"k.smServActive =:flag and " 
    		+"m.orgid=:orgId" )
    List<Object[]> getServiceAndDeptName(@Param("orgId") Long orgId,@Param("flag") Long flag);
    
    
}
