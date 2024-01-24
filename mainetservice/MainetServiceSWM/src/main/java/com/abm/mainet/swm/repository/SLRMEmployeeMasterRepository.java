package com.abm.mainet.swm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.SLRMEmployeeMaster;

@Repository
public interface SLRMEmployeeMasterRepository extends JpaRepository<SLRMEmployeeMaster, Long> {
    
    @Query("select wst from SLRMEmployeeMaster wst where wst.orgid =:orgid and wst.empId =:empId")
    SLRMEmployeeMaster getEmployeeDetails(@Param("orgid") Long orgid , @Param("empId") Long empId); 
    
    @Query(value ="SELECT COUNT(1) FROM tb_sw_employee c WHERE c.orgId =:orgid AND c.SW_EMPMOBNO =:empMobNo AND c.SW_EMPMOBNO not like '0000000000'", nativeQuery = true)
    int checkDuplicateMobileNo(@Param("orgid") Long orgid , @Param("empMobNo") String empMobNo);
 
}
