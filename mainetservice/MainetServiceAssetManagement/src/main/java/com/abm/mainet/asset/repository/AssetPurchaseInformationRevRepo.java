/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetPurchaseInformationRev;

/**
 * @author satish.rathore
 *
 */
public interface AssetPurchaseInformationRevRepo extends PagingAndSortingRepository<AssetPurchaseInformationRev, Long>, AssetPurchaseInformationRevRepoCustom {


	
	 @Query("select purch from AssetPurchaseInformationRev purch where purch.assetPurchaserRevId=:assetPurchaserRevId")
	 AssetPurchaseInformationRev findPurchaseRevByAssetId(@Param("assetPurchaserRevId") Long assetPurchaserRevId);
}
