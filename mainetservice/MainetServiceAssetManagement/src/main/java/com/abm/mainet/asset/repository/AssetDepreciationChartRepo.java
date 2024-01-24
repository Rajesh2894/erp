/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetDepreciationChart;

/**
 * @author satish.rathore
 *
 */
public interface AssetDepreciationChartRepo
        extends PagingAndSortingRepository<AssetDepreciationChart, Long>, AssetDepreciationChartCustomRepo {

    @Query("select adc from AssetDepreciationChart adc where adc.assetId.orgId=:orgId")
    List<AssetDepreciationChart> findAllDepreciationChartListByOrgId(@Param("orgId") Long orgId);

    @Query("select adc from AssetDepreciationChart adc where adc.assetId.assetId=:assetId")
    AssetDepreciationChart findChartByAssetId(@Param("assetId") Long assetId);

    @Query("select adc from AssetDepreciationChart adc where adc.assetId.assetId=:assetId ")
    List<AssetDepreciationChart> findDeprtChartByAssetId(@Param("assetId") Long assetId);

}
