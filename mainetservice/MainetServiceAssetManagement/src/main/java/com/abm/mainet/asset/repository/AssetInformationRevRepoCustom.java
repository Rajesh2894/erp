/**
 * 
 */
package com.abm.mainet.asset.repository;

import com.abm.mainet.asset.domain.AssetInformation;

/**
 * Custom Repository Class for Asset information to update information about
 * asset
 * 
 * @author sarojkumar.yadav
 *
 */
public interface AssetInformationRevRepoCustom {

	/**
	 * update Asset information Entity with primary Key AssetId.
	 * 
	 * @param AssetInformation
	 *            entity
	 */
//	public void updateByAssetId(final Long assetId, final AssetInformation entity);

	
	/**
     * Used to update approval status Flag
     * 
     * @param serialNo
     * @param orgId
     * @param appovalStatus
     */
//	public boolean updateAppStatusFlag(String serialNo, Long orgId, String appovalStatus);

	/**
     * Used to update url parameter
     * 
     * @param serialNo
     * @param orgId
     * @param urlParam
     */
//	public boolean updateURLParam(String serialNo, Long orgId, String urlParam);


}
