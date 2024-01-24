/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetInsuranceDetails;

/**
 * @author satish.rathore
 *
 */
public interface AssetInsuranceRepo extends PagingAndSortingRepository<AssetInsuranceDetails, Long>, AssetInsuranceCustomRepo {

    @Query("select aid from AssetInsuranceDetails aid where aid.assetId.orgId=:orgId")
    List<AssetInsuranceDetails> findAllInsuranceDetailsListByOrgId(@Param("orgId") Long orgId);

    @Query("select aid from AssetInsuranceDetails aid where aid.assetId.assetId=:assetId")
    AssetInsuranceDetails findInsuranceByAssetId(@Param("assetId") Long assetId);
    
    @Query("select aid from AssetInsuranceDetails aid where aid.assetId.assetId=:assetId")
    List<AssetInsuranceDetails> getAllInsurnaceListByAssetId(@Param("assetId") Long assetId);
}
