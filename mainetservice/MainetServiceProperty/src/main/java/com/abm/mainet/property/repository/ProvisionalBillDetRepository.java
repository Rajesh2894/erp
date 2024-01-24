package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.ProvisionalBillDetEntity;

@Repository
public interface ProvisionalBillDetRepository extends JpaRepository<ProvisionalBillDetEntity, Long> {

	@Query("select a from ProvisionalBillDetEntity a where a.bdBilldetid in (:Ids)")
    List<ProvisionalBillDetEntity> getBillDetails(@Param("Ids") List<Long> id);
}
