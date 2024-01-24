/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetDepreciationChartRev;

/**
 * @author satish.rathore
 *
 */
public interface AssetDepreciationChartRevRepo
        extends PagingAndSortingRepository<AssetDepreciationChartRev, Long>, AssetDepreciationChartRevCustomRepo {


	@Query("select adc from AssetDepreciationChartRev adc where adc.assetDepretnRevId=:assetDepretnRevId")
    AssetDepreciationChartRev findChartRevByAssetId(@Param("assetDepretnRevId") Long assetDepretnRevId);
}
