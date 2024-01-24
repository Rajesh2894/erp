package com.abm.mainet.bnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.ParentDetailHistory;

@Repository
public interface ParentDetHistRepository extends JpaRepository<ParentDetailHistory, Long>{

	@Query("select b.pdRegUnitId from ParentDetailHistory b where b.pdBrId=:brId")
	Long getRegUnitIdByBrId(@Param("brId") Long brId);
	
}
