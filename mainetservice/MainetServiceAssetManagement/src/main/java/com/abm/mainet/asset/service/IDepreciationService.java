package com.abm.mainet.asset.service;

import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;

/**
 * @author satish.rathore
 *
 */
public interface IDepreciationService {

    /**
     * this is for save depreciation chart
     * @param dto
     */
    Long saveDepreciation(AssetDepreciationChartDTO dto);

    Long saveDepreciationRev(AssetDepreciationChartDTO dto);

    /**
     * this is for update the depreciation chart
     * @param dto
     */
    void updateDepreciation(AssetDepreciationChartDTO dto);

    Long updateDepreciationByAssetId(Long assetId, AssetDepreciationChartDTO dto);

    /**
     * Get asset depreciation chart information by assetId
     * @param assetId
     */
    AssetDepreciationChartDTO getChartByAssetId(Long assetId);

    AssetDepreciationChartDTO getChartRevByAssetId(Long assetId);

    // D#112592
    AssetDepreciationChartDTO findDeprtChartByAssetId(Long assetId);
}
