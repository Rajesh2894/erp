/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetServiceInformationRev;

/**
 * @author satish.rathore
 *
 */
@Repository
public interface AssetServiceInformationRevRepo
        extends PagingAndSortingRepository<AssetServiceInformationRev, Long>, AssetServiceInformationRevRepoCustom {


    @Query("select asi from AssetServiceInformationRev asi where asi.revGroupId=:assetServiceIdRev")
    List<AssetServiceInformationRev> findServiceByAssetId(@Param("assetServiceIdRev") Long assetServiceIdRev);
}
