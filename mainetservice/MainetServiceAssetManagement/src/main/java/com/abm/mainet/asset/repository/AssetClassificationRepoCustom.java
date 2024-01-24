/**
 * 
 */
package com.abm.mainet.asset.repository;

import com.abm.mainet.asset.domain.AssetClassification;

/**
 * @author sarojkumar.yadav
 *
 */
public interface AssetClassificationRepoCustom {

	/**
	 * update Asset Classification Entity by assetClassificationId.
	 * 
	 * @param AssetClassification
	 *            entity
	 */
	public void updateByAssetId(final Long assetClassificationId,final AssetClassification entity);

}
