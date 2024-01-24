package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetRealEstateRev;

@Repository
public interface AssetRealEstateRevRepo 
extends PagingAndSortingRepository<AssetRealEstateRev, Long>, AssetRealEstateRevRepoCustom {

	 @Query("select rs from AssetRealEstateRev rs where rs.assetRealStdRevId=:assetRealStdRevId")
	 AssetRealEstateRev findRealEstateInfoRevByAssetId(@Param("assetRealStdRevId") Long assetRealStdRevId);
}
