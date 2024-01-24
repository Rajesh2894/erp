package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.AssesmentMastEntity;

public interface PropertyDeletionRepository extends CrudRepository<AssesmentMastEntity, Long> {

    @Query("select count(1) from AssesmentMastEntity a where a.assNo=:propNo and a.orgId=:orgId")
    int vaildatePropertyExistOrNot(@Param("propNo") String propNo, @Param("orgId") long orgId);

    @Transactional
    @Modifying
    int deleteByAssNo(String assNo);

}
