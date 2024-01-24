/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import com.abm.mainet.asset.domain.AssetInformation;

/**
 * Custom Repository Class for Asset information to update information about asset
 * 
 * @author sarojkumar.yadav
 *
 */
public interface AssetInformationRepoCustom {

    /**
     * update Asset information Entity with primary Key AssetId.
     * 
     * @param AssetInformation entity
     */
    public void updateByAssetId(final Long assetId, final AssetInformation entity);

    /**
     * Used to update approval status Flag
     * 
     * @param serialNo
     * @param orgId
     * @param appovalStatus
     */
    public boolean updateAppStatusFlag(Long serialNo, Long orgId, String appovalStatus, String astAppNo);

    boolean updateAstCode(Long assetId, Long orgId, String astCode);

    /**
     * Used to update url parameter
     * 
     * @param assetId
     * @param orgId
     * @param urlParam
     */
    public boolean updateURLParam(Long assetId, Long orgId, String urlParam);

    public boolean updateStatusFlag(Long serialNo, Long orgId, String appovalStatus, Long statusId, String astAppNo);

    public List<AssetInformation> checkDuplicateSerialNo(Long orgId, String serialNo, Long assetId);

}
