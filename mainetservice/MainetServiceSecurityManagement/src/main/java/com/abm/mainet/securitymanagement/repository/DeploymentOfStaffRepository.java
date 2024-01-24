package com.abm.mainet.securitymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.DeploymentOfStaff;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;

@Repository
public interface DeploymentOfStaffRepository extends JpaRepository<DeploymentOfStaff, Long>{
	
	@Query("Select cm from ContractualStaffMaster cm where cm.orgid =:orgid")
	List<ContractualStaffMaster> findEmpNameList(@Param("orgid")Long orgId);

	@Query("select ds from DeploymentOfStaff ds where ds.orgid=:orgid order by ds.deplId desc")
	List<DeploymentOfStaff> findByOrgid(@Param("orgid")Long orgId);

	@Query("select ds from DeploymentOfStaff ds where ds.deplSeq=:deplSeq and ds.orgid=:orgid")
	DeploymentOfStaff findByDeplSeq(@Param("deplSeq")String deplSeq,@Param("orgid")Long orgid);

	@Query("select ds from DeploymentOfStaff ds where ds.deplId=:deplId and ds.orgid=:orgid")
	DeploymentOfStaff findByDeplId(@Param("deplId")Long deplId,@Param("orgid")Long orgid);
	
	@Modifying
	@Query("update DeploymentOfStaff ds set ds.wfStatus=:status where ds.deplSeq=:complainNo")
	void updateWfStatus(@Param("complainNo")String complainNo,@Param("status") String status);
	
	@Modifying
	@Query("update DeploymentOfStaff ds set ds.wfStatus=:status where ds.deplId=:deplId and ds.orgid=:orgid")
	void updateWfStatusDepl(@Param("deplId")Long deplId,@Param("status") String status,@Param("orgid")Long orgid);
	
	@Query("select ds from EmployeeScheduling ds where ds.contStaffIdNo =:contStaffIdNo and  ds.vendorId=:vendorId and ds.orgid=:orgid ")
	List<EmployeeScheduling> findStaffListByEmpId(@Param("contStaffIdNo")String contStaffIdNo,@Param("vendorId")Long vendorId,@Param("orgid") Long orgId);
	
}
