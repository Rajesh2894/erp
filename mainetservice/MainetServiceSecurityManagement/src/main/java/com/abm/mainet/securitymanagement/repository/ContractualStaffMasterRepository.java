package com.abm.mainet.securitymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;

@Repository
public interface ContractualStaffMasterRepository extends JpaRepository<ContractualStaffMaster, Long> {
	
	List<ContractualStaffMaster> findByOrgid(Long orgid);

	@Query("select d from Designation d")
	List<Designation> findDesignNameById();

	@Query("select count(sm) from ContractualStaffMaster sm where sm.vendorId=:vendorId AND sm.contStaffIdNo=:contStaffIdNo AND sm.status='A' AND sm.orgid=:orgId")
	Long checkDuplicateEmployeeId(@Param("vendorId")Long vendorId,@Param("contStaffIdNo")String contStaffIdNo, @Param("orgId")Long orgId);
	
	@Query("select cm from ContractualStaffMaster cm where cm.vendorId=:vendorId and cm.empType=:empType and cm.orgid=:orgid and cm.status='A' ORDER BY cm.contStaffName")
	List<ContractualStaffMaster> getStaffNameByVendorIdAndType(@Param("vendorId")Long vendorId,@Param("empType")String empType, @Param("orgid")Long orgid);
	
	@Query("select sm from ContractualStaffMaster sm where sm.contStaffMob=:contStaffMob AND sm.orgid=:orgId")
	List<ContractualStaffMaster> checkDuplicateMobileNo(@Param("contStaffMob")String contStaffMob, @Param("orgId")Long orgId);
}
