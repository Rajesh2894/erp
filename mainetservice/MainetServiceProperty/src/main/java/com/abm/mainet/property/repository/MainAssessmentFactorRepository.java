package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;

@Repository
public interface MainAssessmentFactorRepository extends JpaRepository<AssesmentFactorDetailEntity, Long> {
    @Query("SELECT c.proAssfId FROM AssesmentFactorDetailEntity c WHERE c.mnAssId =:mnAssId")
    Long fetchAssFactorIdByAssId(@Param("mnAssId") AssesmentMastEntity assNo);

    @Query("SELECT c.proAssfId FROM AssesmentFactorDetailEntity c WHERE c.mnAssdId =:mnAssdId")
    List<Long> fetchAssFactorIdListByAssdId(@Param("mnAssdId") AssesmentDetailEntity mnAssdId);

    @Modifying
    @Query("delete from AssesmentFactorDetailEntity c WHERE c.mnAssdId =:mnAssdId")
    void deleteAssFactor(@Param("mnAssdId") AssesmentDetailEntity mnAssdId);
}
