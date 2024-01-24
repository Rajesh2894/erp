/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetInformation;

/**
 * @author satish.rathore
 *
 */
public interface AssetDetailsReportRepo
        extends PagingAndSortingRepository<AssetInformation, Long>, AssetDetailsReportRepoCustom {

    @Query("select ai.assetId from AssetInformation ai where ai.orgId=:orgId and ai.assetClass2=:assetClass and lower(ai.astCode)=:assetCode")
    Object getAssetIdByAssetCodeAndClass(@Param("orgId") Long orgId, @Param("assetClass") Long assetClass,
            @Param("assetCode") String assetCode);

    @Query("Select vd.codCpdId from ViewPrefixDetails vd where vd.orgid=:orgId and vd.cpmPrefix=:cpmPrefix and codCpdValue=:codCpdValue")
    Object getPrefixIdByPrefixCode(@Param("orgId") Long orgId, @Param("cpmPrefix") String prefix,
            @Param("codCpdValue") String assetClass);

}
