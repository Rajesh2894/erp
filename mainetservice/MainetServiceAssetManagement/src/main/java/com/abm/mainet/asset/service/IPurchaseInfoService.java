package com.abm.mainet.asset.service;

import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;

public interface IPurchaseInfoService {
    /**
     * Saves the asset purchase information
     * @param dto asset details to update
     */
    public Long savePurchaseInfo(final AssetPurchaseInformationDTO dto);

    /**
     * Update the asset purchase information
     * @param dto asset details to update
     */
    public Long updatePurchaseInfoByAssetId(final Long assetId, final AssetPurchaseInformationDTO dto);
    
    public Long savePurchaseInfoRev(final Long assetId, final AssetPurchaseInformationDTO dto);

    /**
     * Get asset purchase information by assetId
     * @param assetId
     */
    AssetPurchaseInformationDTO getPurchaseByAssetId(Long assetId);
    
    public AssetPurchaseInformationDTO getPurchaseRevByAssetId(Long assetIdRev);

	

	
}
