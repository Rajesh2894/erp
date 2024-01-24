/**
 * 
 */
package com.abm.mainet.asset.repository;

import com.abm.mainet.asset.domain.AssetInsuranceDetails;

/**
 * @author satish.rathore
 *
 */
public interface AssetInsuranceCustomRepo {

    public void updateInsurancesInfo(AssetInsuranceDetails entity);
}
