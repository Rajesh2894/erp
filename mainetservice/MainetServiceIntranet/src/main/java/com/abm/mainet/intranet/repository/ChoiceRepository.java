package com.abm.mainet.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.intranet.domain.Choice;


@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
	
	@Query("select d from Choice d where d.orgid=:orgid order by 1 desc")
	Choice getPollByIdFromChoice(@Param("orgid")Long orgid);
	
}
