/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IAssetValuationService {

    /**
     * Used to add entry in valuation table
     * 
     * @param AssetValuationDetailsDTO
     * @return primary key of valuation table
     */
    public Long addEntry(final AssetValuationDetailsDTO dto);

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId
     * 
     * @param orgId
     * @param assetId
     * @return list of DepreciationAssetDTO
     */
    public List<AssetValuationDetailsDTO> findAllByAssetId(final Long orgId, final Long assetId);

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId
     * 
     * @param orgId
     * @param assetId
     * @return DepreciationAssetDTO
     */
    public AssetValuationDetailsDTO findLatestAssetId(final Long orgId, final Long assetId);

    /**
     * find Asset Valuation Details details by Asset Id between two dates
     * 
     * @param orgId
     * @param assetId
     * @param startDate
     * @param endDate
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    public List<AssetValuationDetailsDTO> findAssetBetweenDates(final Long orgId, final Long assetId,
            final Date startDate, final Date endDate);

    /**
     * find Asset Valuation Details details by Asset Id for a financial year with change type equals to "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param bookFinYear
     * @param changeType
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    public List<AssetValuationDetailsDTO> findAssetInFinYear(final Long orgId, final Long assetId,
            final Long bookFinYear, final String changeType);

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId for change type "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    public AssetValuationDetailsDTO findLatestAssetIdWithChangeType(final Long orgId, final Long assetId,
            final String changeType);

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId for change type other than passed change Type
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    public List<AssetValuationDetailsDTO> checkTransaction(final Long orgId, final Long assetId,
            final String changeType);

    public List<AssetValuationDetailsDTO> findAssetTillDate(final Long orgId, final Long assetId,
            final Date endDate);

    public Date findLatestBookEndDate(final Long orgId, final Long assetId);
}
