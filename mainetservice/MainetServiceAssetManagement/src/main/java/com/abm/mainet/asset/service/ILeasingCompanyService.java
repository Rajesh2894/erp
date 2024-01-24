/**
 * 
 */
package com.abm.mainet.asset.service;

import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;

/**
 * @author satish.rathore
 *
 */
public interface ILeasingCompanyService {

    /**
     * this is for save the leasing company information
     * @param dto
     */
    Long saveLeasingInfo(AssetLeasingCompanyDTO dto);

    /**
     * this is for update for the leasing company information
     * @param dto
     */
    void updateLeasingInfo(AssetLeasingCompanyDTO dto);

    public Long saveLeasingInfoRev(final Long assetId, final AssetLeasingCompanyDTO dto);

    
    /**
     * Get asset leasing company information by assetId
     * @param assetId
     */
    AssetLeasingCompanyDTO getLeasingByAssetId(Long assetId);
    
    AssetLeasingCompanyDTO getLeasingRevByAssetId(Long assetId);

    Long updateLeasingInfoByAssetId(Long assetId, AssetLeasingCompanyDTO dto);
}
