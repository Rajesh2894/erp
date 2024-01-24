/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetInformationRev;

/**
 * @author satish.rathore
 *
 */
public interface AssetInformationRevRepo extends PagingAndSortingRepository<AssetInformationRev, Long>, AssetInformationRevRepoCustom {

    @Query("select ai from AssetInformationRev ai where ai.assetRevId=:assetRevId ")
    AssetInformationRev getAssetRevId(@Param("assetRevId") Long assetRevId);
}
