/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetClassification;
import com.abm.mainet.asset.domain.AssetClassificationRev;

/**
 * @author satish.rathore
 *
 */
@Repository
public interface AssetClassificationRepo extends
        PagingAndSortingRepository<AssetClassification, Long>, AssetClassificationRepoCustom {

    @Query("select ac from AssetClassification ac where ac.assetId.orgId=:orgId")
    List<AssetClassification> findAllClassificationListByOrgId(@Param("orgId") Long orgId);

    @Query("select ac from AssetClassification ac where ac.assetId.assetId=:assetId")
    AssetClassification findClassByAssetId(@Param("assetId") Long assetId);
    
    @Query("select ac from AssetClassificationRev ac where ac.assetClassfcnIdRev=:assetClassfcnIdRev")
    AssetClassificationRev findClassRevByAssetId(@Param("assetClassfcnIdRev") Long assetClassfcnIdRev);

    @Modifying
    @Query("UPDATE AssetClassification ac SET ac.department =:department where ac.assetId.assetId =:assetId")
	void updateDepartment(@Param("assetId") Long assetId, @Param("department") Long assetDepartment);
    
    @Modifying
    @Query("UPDATE AssetClassification ac SET ac.location =:location where ac.assetId.assetId=:assetId")
    void updateLocation(@Param("assetId") Long assetId, @Param("location") Long location);
    
    @Modifying
    @Query("UPDATE AssetClassification ac SET ac.latitude =:latitude, ac.longitude =:longitude where ac.assetId.assetId=:assetId")
    int updateGeoLocation(@Param("assetId") Long assetId, @Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude);

}
