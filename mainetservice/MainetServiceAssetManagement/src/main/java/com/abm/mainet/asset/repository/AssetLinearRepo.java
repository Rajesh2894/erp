/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetLinear;

/**
 * @author satish.rathore
 *
 */
public interface AssetLinearRepo extends PagingAndSortingRepository<AssetLinear, Long>, AssetLinearCustomRepo {

    @Query("select line from AssetLinear line where line.assetId.orgId=:orgId")
    List<AssetLinear> findAllLinearByOrgId(@Param("orgId") Long orgId);

    @Query("select line from AssetLinear line where line.assetId.assetId=:assetId")
    AssetLinear findLinearByAssetId(@Param("assetId") Long assetId);
}
