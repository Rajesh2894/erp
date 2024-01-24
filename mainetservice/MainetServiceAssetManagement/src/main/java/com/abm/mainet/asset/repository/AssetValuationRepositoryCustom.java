/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.Date;

/**
 * @author satish.rathore
 *
 */
public interface AssetValuationRepositoryCustom {

    Date findLatestBookEndDate(final Long orgId, final Long assetId);

}
