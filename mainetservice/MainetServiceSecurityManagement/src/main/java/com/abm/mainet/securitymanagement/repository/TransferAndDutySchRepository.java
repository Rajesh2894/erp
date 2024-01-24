package com.abm.mainet.securitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.TransferAndDutySchedulingOfStaff;

@Repository
public interface TransferAndDutySchRepository extends JpaRepository<TransferAndDutySchedulingOfStaff, Long>{

	@Query("select cm from ContractualStaffMaster cm where cm.contStaffIdNo=:contStaffIdNo and cm.orgid=:orgid")
	ContractualStaffMaster findEmpByEmpId(@Param("contStaffIdNo")String contStaffIdNo,@Param("orgid")Long orgid);	
}
