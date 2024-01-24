package com.abm.mainet.disastermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.disastermanagement.domain.DisasterOccuranceBookEntity;

@Repository
public interface DisasterOccuranceBookRepository extends JpaRepository<DisasterOccuranceBookEntity, Long> {

	@Query("select ob from DisasterOccuranceBookEntity ob where ob.cmplntNo=:cmplntNo and ob.orgid=:orgid order by 1 desc")
	List<DisasterOccuranceBookEntity> findAlloccurrences(@Param("cmplntNo") String cmplntNo,
			@Param("orgid") Long orgId);
}
