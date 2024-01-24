package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.AsExcessAmtEntity;

@Repository
public interface AsExecssAmtRepository extends JpaRepository<AsExcessAmtEntity, Long> {

    @Query("select a from AsExcessAmtEntity a where a.propNo=:propNo and a.orgid=:orgId and a.excessActive='A' and a.excadjFlag='N'")

    List<AsExcessAmtEntity> getExcessAmtEntByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("select a from AsExcessAmtEntity a where a.propNo=:propNo and a.orgid=:orgId and a.rmRcptid=:rmRcptid and  a.excessActive='A'")

    AsExcessAmtEntity getAdvanceEntryByRecptId(@Param("rmRcptid") Long rmRcptid, @Param("propNo") String propNo,
            @Param("orgId") Long orgId);

    @Modifying
    @Transactional
    @Query("update AsExcessAmtEntity a set a.excessActive='I'  where a.propNo=:propNo and a.orgid=:orgId")
    void inactiveAllAdvPayEnrtyByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Modifying
    @Transactional
    @Query("update AsExcessAmtEntity a set a.excessActive='I'  where a.excessId=:excessId and a.orgid=:orgId ")
    void inactiveAdvPayEnrtyByExcessId(@Param("excessId") long excessId, @Param("orgId") Long orgId);
    
    @Query("select a from AsExcessAmtEntity a where a.propNo=:propNo and a.orgid=:orgId and a.excessActive='A' ORDER BY excessId DESC")

    List<AsExcessAmtEntity> getExcessAmtEntByActivePropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);
	
	
	@Query("select a from AsExcessAmtEntity a where a.propNo=:propNo and a.orgid=:orgId and a.flatNo=:flatNo and a.excessActive='A' and a.excadjFlag='N'")
	List<AsExcessAmtEntity> getExcessAmtEntByPropNoAndFlatNo(@Param("propNo") String propNo, @Param("orgId") Long orgId,
			@Param("flatNo") String flatNo);
	 
}
