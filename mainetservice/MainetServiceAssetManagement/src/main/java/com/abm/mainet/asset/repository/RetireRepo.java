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

import com.abm.mainet.asset.domain.AssetRetire;
import com.abm.mainet.asset.domain.AssetTransfer;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public interface RetireRepo extends PagingAndSortingRepository<AssetRetire, Long> {

	@Query("select ar from AssetRetire ar where ar.orgId=:orgId")
	List<AssetTransfer> findAllByOrgId(@Param("orgId") Long orgId);

	@Query("select ar from AssetRetire ar where ar.assetId.assetId=:assetId")
	AssetTransfer findAllByAssetId(@Param("assetId") Long assetId);
	
	@Modifying
    @Query("UPDATE AssetRetire ar SET ar.authStatus =:authStatus,ar.authBy=:authBy,ar.authDate=:authDate where ar.orgId=:orgId and ar.assetId.assetId=:assetId and ar.retireId=:retireId")
    void updateAuthStatus(@Param("authStatus") String authStatus, @Param("authBy") Long authBy, @Param("authDate") Date authDate,
    						@Param("assetId") Long assetId, @Param("retireId") Long retireId, @Param("orgId") Long orgId);
	
}
