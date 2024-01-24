/**
 * 
 */
package com.abm.mainet.asset.repository;

import com.abm.mainet.asset.domain.AssetClassificationRev;

/**
 * @author sarojkumar.yadav
 *
 */
public interface AssetClassificationRevRepoCustom {

//------Rev
	/**
	 * update Asset Classification Entity by assetClassificationId.
	 * 
	 * @param AssetClassification
	 *            entity
	 */
	public void updateByAssetIdRev(final Long assetClassificationId,final AssetClassificationRev entity);


}
