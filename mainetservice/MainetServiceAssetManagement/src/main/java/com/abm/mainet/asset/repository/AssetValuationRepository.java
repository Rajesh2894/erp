/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.AssetValuationDetails;

/**
 * @author sarojkumar.yadav
 *
 */
public interface AssetValuationRepository
        extends PagingAndSortingRepository<AssetValuationDetails, Long>, AssetValuationRepositoryCustom {

    /**
     * find Asset Valuation Details details by orgId
     * 
     * @param orgId
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId")
    List<AssetValuationDetails> findAllByOrgId(@Param("orgId") Long orgId);

    /**
     * find Asset Valuation Details details by Asset Id
     * 
     * @param orgId
     * @param assetId
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId and  avd.assetId=:assetId order by 1 DESC")
    List<AssetValuationDetails> findAllByAssetId(@Param("orgId") Long orgId, @Param("assetId") Long assetId);

    /**
     * find Asset Valuation Details details by Asset Id between two dates
     * 
     * @param orgId
     * @param assetId
     * @param startDate
     * @param endDate
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId and avd.assetId=:assetId and avd.bookEndDate between :startDate and :endDate order by 1 desc")
    List<AssetValuationDetails> findAssetBetweenDates(@Param("orgId") Long orgId, @Param("assetId") Long assetId,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * find Asset Valuation Details details by Asset Id for a financial year with change type equals to "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param bookFinYear
     * @param changeType
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId and avd.assetId=:assetId and avd.bookFinYear=:bookFinYear and avd.changetype=:changeType")
    List<AssetValuationDetails> findAssetInFinYear(@Param("orgId") Long orgId, @Param("assetId") Long assetId,
            @Param("bookFinYear") Long bookFinYear, @Param("changeType") String changeType);

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId for change type "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId and avd.assetId=:assetId and avd.changetype=:changeType order by 1 DESC")
    public List<AssetValuationDetails> findLatestAssetIdWithChangeType(@Param("orgId") Long orgId,
            @Param("assetId") Long assetId, @Param("changeType") String changeType);

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId for change type other than passed change Type
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId and avd.assetId=:assetId and avd.changetype not like :changeType")
    public List<AssetValuationDetails> checkTransaction(@Param("orgId") Long orgId, @Param("assetId") Long assetId,
            @Param("changeType") String changeType);

    /**
     * it will gives you all the records till date
     * @param orgId
     * @param assetId
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("select avd from AssetValuationDetails avd where avd.orgId=:orgId and avd.assetId=:assetId and avd.bookEndDate <= :endDate order by 1 desc")
    List<AssetValuationDetails> findAssetTillDate(@Param("orgId") Long orgId, @Param("assetId") Long assetId,
            @Param("endDate") Date endDate);

}
