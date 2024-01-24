package com.abm.mainet.asset.service;

import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;

public interface IRealEstateInfoService {
	 /**
     * Saves the asset RealEstate information
     * @param dto asset details to update
     */
    public Long saveRealEstate(final AssetRealEstateInformationDTO dto);

    /**
     * Update the asset purchase information
     * @param dto asset details to update
     */
    public Long updateRealEstateByAssetId(final Long assetId, final AssetRealEstateInformationDTO dto);
    
    public Long saveRealEstateRev(final Long assetId, final AssetRealEstateInformationDTO dto);

    /**
     * Get asset purchase information by assetId
     * @param assetId
     */
    AssetRealEstateInformationDTO getRealEstateInfoByAssetId(Long assetId);
    
    public AssetRealEstateInformationDTO getRealEstateInfoRevByAssetId(Long assetIdRev);

}
