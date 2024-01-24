/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetClassificationRev;

/**
 * @author 
 *
 */
@Repository
public interface AssetClassificationRevRepo extends
        PagingAndSortingRepository<AssetClassificationRev, Long>, AssetClassificationRevRepoCustom {

/*    @Query("select ac from AssetClassification ac where ac.assetId.orgId=:orgId")
    List<AssetClassification> findAllClassificationListByOrgId(@Param("orgId") Long orgId);

    @Query("select ac from AssetClassification ac where ac.assetId.assetId=:assetId")
    AssetClassification findClassByAssetId(@Param("assetId") Long assetId);*/
    
    @Query("select ac from AssetClassificationRev ac where ac.assetId.assetId=:assetId")
    AssetClassificationRev findClassRevByAssetId(@Param("assetId") Long assetId);

}
