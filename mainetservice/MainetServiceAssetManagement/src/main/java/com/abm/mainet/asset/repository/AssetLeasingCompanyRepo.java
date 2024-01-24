/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetLeasingCompany;

/**
 * @author satish.rathore
 *
 */

public interface AssetLeasingCompanyRepo
        extends PagingAndSortingRepository<AssetLeasingCompany, Long>, AssetLeasingCompanyCustomRepo {

    @Query("select alc from AssetLeasingCompany alc where alc.assetId.orgId=:orgId")
    List<AssetLeasingCompany> findAllLeasingCompanyByOrgId(@Param("orgId") Long orgId);

    @Query("select alc from AssetLeasingCompany alc where alc.assetId.assetId=:assetId")
    AssetLeasingCompany findLeasingByAssetId(@Param("assetId") Long assetId);
}
