package com.abm.mainet.securitymanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.securitymanagement.domain.DailyIncidentRegister;

@Repository
public interface DailyIncidentRegisterRepository extends JpaRepository<DailyIncidentRegister, Long> {

	
	
	@Query("SELECT DIR FROM DailyIncidentRegister DIR WHERE DIR.date BETWEEN :fromDate AND :toDate AND DIR.orgid=:orgid order by DIR.incidentId desc" )
	List<DailyIncidentRegister> searchIncidentRegister(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("orgid") Long orgid);

	List<DailyIncidentRegister> findByOrgidOrderByIncidentIdDesc(Long orgId);

}
