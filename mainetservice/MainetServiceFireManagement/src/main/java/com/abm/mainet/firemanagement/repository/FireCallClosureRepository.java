package com.abm.mainet.firemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.TbFmComplainClosure;


@Repository
public interface FireCallClosureRepository extends JpaRepository<TbFmComplainClosure, Long> {
	
	//TbFmComplainClosure findByCmplntNoAndOrgid(String complainNo, Long orgid);
	
	@Modifying
    @Query("UPDATE TbFmComplainClosure a SET a.complaintStatus =:status where a.closureId =:closureId")
    void updateComplainStatus(@Param("closureId") Long closureId, @Param("status") String status);

	@Modifying
	@Query("update TbFmComplainClosure d set d.wfStatus=:wfStatus where d.closureId=:closureId and d.orgid=:orgid")  
	void updateWorkFlowStatus(@Param("closureId")Long closureId,@Param("orgid")Long orgId,@Param("wfStatus")String wfStatus);

	@Query("select d from TbFmComplainClosure d where d.cmplntNo=:cmplntNo and d.orgid=:orgid") 
	TbFmComplainClosure findByCmplntNoAndOrgid(@Param("cmplntNo") String complainNo,@Param("orgid") Long orgid); 
	
	@Query("select d from TbFmComplainClosure d where d.closureId=:closerId and d.orgid=:orgid order by d.closureId desc ") 
	TbFmComplainClosure findByCloserId(@Param("closerId") Long closerId,@Param("orgid") Long orgid); 
	
	@Query("select d from TbFmComplainClosure d where d.cmplntId=:cmplntId and d.orgid=:orgid  order by d.closureId desc")   //vi changes
	List<TbFmComplainClosure> findByComId(@Param("cmplntId") Long cmplntId,@Param("orgid") Long orgid); 
	

	@Query("select d from TbFmComplainClosure d where d.cmplntNo=:complainNo and d.orgid=:orgid  order by d.closureId desc")   //vi changes
	List<TbFmComplainClosure> findByComplId(@Param("complainNo") String complainNo,@Param("orgid") Long orgid); 
	
	
	@Query("select d from TbFmComplainClosure d where d.closureId=:closerId and d.orgid=:orgid") 
	TbFmComplainClosure findByClosureId(@Param("closerId") Long closerId,@Param("orgid") Long orgid);

	@Modifying
    @Query("UPDATE TbFmComplainClosure a SET a.hodRemarks =:hodRemarks where a.closureId =:closureId and a.orgid=:orgid")
	void updateFinalCallClosureComment(@Param("closureId")Long closureId,@Param("hodRemarks") String hodRemarks,@Param("orgid") Long orgid);

	/*
	 * @Query("select d from TbFmComplainClosure d where d.closureId=:closerId and d.orgid=:orgid"
	 * ) List<TbFmComplainClosure> findByClosureid(@Param("closerId") Long
	 * closerId,@Param("orgid") Long orgid);
	 */
	
	
	
}




