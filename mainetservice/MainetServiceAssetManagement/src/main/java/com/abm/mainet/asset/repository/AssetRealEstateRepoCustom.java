package com.abm.mainet.asset.repository;

import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.asset.domain.AssetRealEstate;

public interface AssetRealEstateRepoCustom {


	public void updateByAssetId(Long assetRealStdId, AssetRealEstate entity);

}
