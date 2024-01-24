package com.abm.mainet.firemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FireCallRegister;
import com.abm.mainet.firemanagement.domain.OccuranceBookEntity;

@Repository
public interface OccuranceBookRepository extends JpaRepository<OccuranceBookEntity, Long> {

	List<FireCallRegister> findByOrgid(Long orgId);
 
	@Query("select ob from OccuranceBookEntity ob where ob.cmplntNo=:cmplntNo and ob.orgid=:orgid order by 1 desc")
	List<OccuranceBookEntity> findAlloccurrences(@Param("cmplntNo") String cmplntNo,@Param("orgid") Long orgId);
}
