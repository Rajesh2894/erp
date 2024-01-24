/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetInsuranceDetails;
import com.abm.mainet.asset.domain.AssetInsuranceDetailsRev;

/**
 * @author satish.rathore
 *
 */
public interface AssetInsuranceRevRepo extends PagingAndSortingRepository<AssetInsuranceDetailsRev, Long>, AssetInsuranceRevRepoCustom {

 /*   @Query("select aid from AssetInsuranceDetails aid where aid.assetId.orgId=:orgId")
    List<AssetInsuranceDetails> findAllInsuranceDetailsListByOrgId(@Param("orgId") Long orgId);

    @Query("select aid from AssetInsuranceDetails aid where aid.assetId.assetId=:assetId")
    AssetInsuranceDetails findInsuranceByAssetId(@Param("assetId") Long assetId);*/
    
    @Query("select aid from AssetInsuranceDetailsRev aid where aid.assetInsuranceRevId=:assetInsuranceRevId")
    AssetInsuranceDetailsRev findInsuranceRevByAssetId(@Param("assetInsuranceRevId") Long assetInsuranceRevId);
    
    @Query("select aid from AssetInsuranceDetailsRev aid where aid.revGrpId=:revGrpId")
    List<AssetInsuranceDetailsRev> findInsuranceListRevByAssetId(@Param("revGrpId") Long revGrpId);
 /*   
    @Query("select aid from AssetInsuranceDetailsRev aid where aid.assetId.assetId=:assetInsuranceRevId and (aid.revGrpIdentity='N' OR aid.revGrpIdentity='O')")
    List<AssetInsuranceDetailsRev> getAllInsuranceDetailsList(@Param("assetInsuranceRevId") Long assetInsuranceRevId);
*/    
}
