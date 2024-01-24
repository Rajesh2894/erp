/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetServiceInformation;

/**
 * @author satish.rathore
 *
 */
@Repository
public interface AssetServiceInformationRepo
        extends PagingAndSortingRepository<AssetServiceInformation, Long>, AssetServiceInformationRepoCustom {

    @Query("select asi from AssetServiceInformation asi where asi.assetId.orgId=:orgId")
    List<AssetServiceInformation> findAllServiceInformationByOrgId(@Param("orgId") Long orgId);

    @Query("select asi from AssetServiceInformation asi where asi.assetId.assetId=:assetId")
    List<AssetServiceInformation> findServiceByAssetId(@Param("assetId") Long assetId);
}
