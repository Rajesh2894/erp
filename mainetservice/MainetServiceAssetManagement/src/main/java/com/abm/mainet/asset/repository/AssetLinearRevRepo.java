/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetLinearRev;

/**
 * @author satish.rathore
 *
 */
public interface AssetLinearRevRepo extends PagingAndSortingRepository<AssetLinearRev, Long>, AssetLinearRevCustomRepo {
	@Query("select line from AssetLinearRev line where line.assetLinearRevId=:assetLinearRevId")
	AssetLinearRev findLinearRevByAssetId(@Param("assetLinearRevId") Long assetLinearRevId);
}
