/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetLeasingCompany;
import com.abm.mainet.asset.domain.AssetLeasingCompanyRev;

/**
 * @author satish.rathore
 *
 */

public interface AssetLeasingCompanyRevRepo
        extends PagingAndSortingRepository<AssetLeasingCompanyRev, Long>, AssetLeasingCompanyRevCustomRepo {

	
	 @Query("select alc from AssetLeasingCompanyRev alc where alc.assetLeasingRevID=:assetLeasingRevID")
	    AssetLeasingCompanyRev findLeasingByAssetId(@Param("assetLeasingRevID") Long assetLeasingRevID);
}
