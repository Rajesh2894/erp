package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.Vigilance;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public interface VigilanceRepository extends CrudRepository<Vigilance, Long>, VigilanceRepositoryCustome {

    /**
     * find Inspection By Reference number
     * @param referenceNo
     * @param orgId
     * @return list of Inspection Details
     */
    @Query("Select vg from Vigilance vg where vg.referenceNumber =:referenceNo and vg.orgId=:orgId and vg.status ='Y' ")
    List<Vigilance> findInspectionByReference(@Param("referenceNo") String referenceNo, @Param("orgId") Long orgId);

    
    /**
     * find Inspection By Reference number
     * @param projectId
     * @param workId
     * @param orgid
     * @return list of Inspection Details
     */
    @Query("Select v from Vigilance v where v.projId =:projectId and v.workId=:workId and v.orgId=:orgId")
	List<Vigilance> findInspectionByProjIdAndWorkId(@Param("projectId") Long projectId, @Param("workId") Long workId, @Param("orgId") Long orgid);
}