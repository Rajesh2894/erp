/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetRevaluation;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public interface RevaluationRepo extends PagingAndSortingRepository<AssetRevaluation, Long> {

	@Query("select ar from AssetRevaluation ar where ar.orgId=:orgId")
	List<AssetRevaluation> findAllByOrgId(@Param("orgId") Long orgId);

	@Query("select ar from AssetRevaluation ar where ar.assetId.assetId=:assetId")
	List<AssetRevaluation> findAllByAssetId(@Param("assetId") Long assetId);
	
	@Modifying
    @Query("UPDATE AssetRevaluation ar SET ar.authStatus =:authStatus,ar.authBy=:authBy,ar.authDate=:authDate where ar.orgId=:orgId and ar.assetId.assetId=:assetId and ar.revaluationId=:revaluationId")
    void updateAuthStatus(@Param("authStatus") String authStatus, @Param("authBy") Long authBy, @Param("authDate") Date authDate,
    						@Param("assetId") Long assetId, @Param("revaluationId") Long revaluationId, @Param("orgId") Long orgId);
	
}
