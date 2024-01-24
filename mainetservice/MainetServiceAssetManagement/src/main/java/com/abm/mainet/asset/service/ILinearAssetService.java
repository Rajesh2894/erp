/**
 * 
 */
package com.abm.mainet.asset.service;

import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;

/**
 * @author satish.rathore
 *
 */
public interface ILinearAssetService {

    /**
     * this is for save linear asset
     * @param dto
     */
	Long saveLinearInfo(AssetLinearDTO dto);
    
    Long saveLinearInfoRev(AssetLinearDTO dto);

    /**
     * this is for update linear asset
     * @param dto
     */
    void updateLinearInfo(AssetLinearDTO dto);

    Long updateLinearInfoByAssetId(Long assetId, AssetLinearDTO dto);

    /**
     * Get asset linear information by assetId
     * @param assetId
     */
    AssetLinearDTO getLinearByAssetId(Long assetId);

    AssetLinearDTO getLinearRevByAssetId(Long assetId);
}
