package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountLoanMasterEntity;
import com.abm.mainet.common.domain.Employee;

public interface AccountLoanMasterRepository extends JpaRepository<AccountLoanMasterEntity, Long> {
	List<AccountLoanMasterEntity> findByOrgId(Long orgId);
	
	@Query("SELECT e FROM  Employee e WHERE e.isDeleted='0' and e.organisation.orgid=:orgId and e.empId in(SELECT ac.createdBy FROM  AccountLoanMasterEntity ac WHERE ac.orgId=:orgId and ac.lnStatus='A')")
	List<Employee> getAllEmployeeAssociatedWithLoanMaster(@Param("orgId") Long orgId);
	
}
