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

import com.abm.mainet.asset.domain.AssetTransfer;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public interface TransferRepo extends PagingAndSortingRepository<AssetTransfer, Long> {

	@Query("select at from AssetTransfer at where at.orgId=:orgId")
	List<AssetTransfer> findAllByOrgId(@Param("orgId") Long orgId);

	@Query("select at from AssetTransfer at where at.assetId.assetId=:assetId")
	AssetTransfer findAllByAssetId(@Param("assetId") Long assetId);
	
	@Modifying
    @Query("UPDATE AssetTransfer ar SET ar.authStatus =:authStatus,ar.authBy=:authBy,ar.authDate=:authDate where ar.orgId=:orgId and ar.assetId.assetId=:assetId and ar.transferId=:transferId")
    void updateAuthStatus(@Param("authStatus") String authStatus, @Param("authBy") Long authBy, @Param("authDate") Date authDate,
    						@Param("assetId") Long assetId, @Param("transferId") Long transferId, @Param("orgId") Long orgId);

}
