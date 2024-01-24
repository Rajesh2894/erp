package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.common.domain.Employee;

@Repository
public interface AccoutInvestMentMasterJpaRepository extends JpaRepository<AccountInvestmentMasterEntity, Long> {

	AccountInvestmentMasterEntity findByOrgId(Long orgId);
	
	@Query("SELECT ac FROM  AccountInvestmentMasterEntity ac WHERE ac.orgId=:orgId and ac.status='A'")
	List<AccountInvestmentMasterEntity> findByOnlyOrgId(@Param("orgId") Long orgId);
	
	@Query("SELECT e FROM  Employee e WHERE e.isDeleted='0' and e.organisation.orgid=:orgId and e.empId in(SELECT ac.createdBy FROM  AccountInvestmentMasterEntity ac WHERE ac.orgId=:orgId and ac.status='A')")
	List<Employee> getAllEmployeeAssociatedWithInvestmestMaster(@Param("orgId") Long orgId);
	
}
	                             
