/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.asset.domain.AssetPurchaseInformationRev;

/**
 * @author satish.rathore
 *
 */
public interface AssetPurchaseInformationRepo extends PagingAndSortingRepository<AssetPurchaseInformation, Long>, AssetPurchaseInformationRepoCustom {

    @Query("select purch from AssetPurchaseInformation purch where purch.assetId.orgId=:orgId")
    List<AssetPurchaseInformation> findAllAssetPurchaseInformationByOrgId(@Param("orgId") Long orgId);

    @Query("select purch from AssetPurchaseInformation purch where purch.assetId.assetId=:assetId")
    AssetPurchaseInformation findPurchaseByAssetId(@Param("assetId") Long assetId);
}
