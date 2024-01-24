package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.SchemeMaster;

@Repository
public interface SchemeMasterRepository extends CrudRepository<SchemeMaster, Long> {

    /**
     * used to check Duplicate Scheme Code
     * 
     * @param wmSchCode
     * @param orgId
     * @return
     */
    @Query("SELECT sm FROM SchemeMaster sm where UPPER(sm.wmSchCode) = :wmSchCode and sm.orgId= :orgId")
    List<SchemeMaster> checkDuplicateSchemeCode(@Param("wmSchCode") String wmSchCode, @Param("orgId") Long orgId);

    /**
     * method used for inactive child records of scheme master details
     * 
     * @param updatedBy
     * @param schDActive
     * @param schDetId
     */
    @Modifying
    @Query("UPDATE  SchemeMastDetails smd set smd.schDActive = 'N', smd.updatedBy = :updatedBy  WHERE smd.schDetId in (:schDetId) ")
    void inactiveSchemeMasterChildRecords(@Param("updatedBy") Long updatedBy,
            @Param("schDetId") List<Long> removeChildIds);

}
