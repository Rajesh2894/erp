package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetRealEstate;
import com.abm.mainet.asset.domain.AssetRealEstateRev;

@Repository
public interface AssetRealEstateRepo 
extends PagingAndSortingRepository<AssetRealEstate, Long>, AssetRealEstateRepoCustom {

	    @Query("select res from AssetRealEstate res where res.assetId.assetId=:assetId")
	    AssetRealEstate findRealEstateByAssetId(@Param("assetId") Long assetId);

}
