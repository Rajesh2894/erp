package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MbOverHeadDetails;

@Repository
public interface MbOverHeadRepository extends CrudRepository<MbOverHeadDetails, Long>{
	
/*	@Modifying
    @Query("update MbOverHeadDetails  where overHeadId in :removeWorkIdList")
    void updateDeletedFlagForOverHeads(@Param("removeWorkIdList") List<Long> overHeadRemoveById);
    */
	
	@Query("select Sum(w.actualAmount) from MbOverHeadDetails w where w.mbMaster.workMbId =:mbMaster")
    BigDecimal getOverheadAmount(@Param("mbMaster") Long mbMaster);

	
	@Query("select w from MbOverHeadDetails w where w.mbMaster.workMbId=:mbId and w.orgId=:orgId")
    List<MbOverHeadDetails> getMbOverHeadDetailsByWorkOrgId(@Param("mbId") Long mbId,@Param("orgId") Long orgId);
	
	@Query("select w from MbOverHeadDetails w where w.overHeadId.overHeadId=:overHeadId and w.orgId=:orgId")
    List<MbOverHeadDetails> getMbOverHeadDetailsByWorkOvId(@Param("overHeadId") Long ovId,@Param("orgId") Long orgId);
}
