package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.CaseEntry;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface CaseEntryRepository extends JpaRepository<CaseEntry, Long> {
	@Query("SELECT ce FROM CaseEntry ce WHERE (ce.orgid = :orgId OR ce.concernedUlb  = :orgId )")
	List<CaseEntry> findByOrgid(@Param("orgId") Long orgid);
	
	List<CaseEntry> findByCseCaseStatusIdAndOrgid(Long cseCaseStatusId,Long orgid);


	@Query("SELECT cd FROM CaseEntry cd WHERE cd.cseSuitNo=:cseSuitNo")
	CaseEntry getCaseEntryByCaseNumber(@Param("cseSuitNo") String cseSuitNo);
	
	
	@Query("select case when count(cd)>0 THEN true ELSE false END from CaseEntry cd where  cd.cseSuitNo =:cseSuitNo  AND cd.orgid=:orgid")
	Boolean checkCasePresentOrNot(@Param("cseSuitNo") String cseSuitNo, @Param("orgid") Long orgid);

	@Query("SELECT ce FROM CaseEntry ce WHERE ce.advId=:advId and ce.orgid =:orgId ")
	List<CaseEntry> getCaseDetailsByAdvId(@Param("advId") Long advId,@Param("orgId") Long orgId);
	
	@Query("SELECT ce FROM CaseEntry ce WHERE ce.cseRefsuitNo=:cseRefsuitNo and ce.orgid =:orgId ")
	List<CaseEntry> getCaseDetailsByRefCaseNumber(@Param("cseRefsuitNo") String advId,@Param("orgId") Long orgId);
	
	@Query("SELECT ce.cseSuitNo FROM CaseEntry ce WHERE (ce.orgid = :orgId OR ce.concernedUlb  = :orgId )")
	List<String> findSuitNoByOrgid(@Param("orgId") Long orgid);

	

}
