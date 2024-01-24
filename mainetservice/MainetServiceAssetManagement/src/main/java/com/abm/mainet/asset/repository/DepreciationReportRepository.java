/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetValuationDetails;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public interface DepreciationReportRepository extends PagingAndSortingRepository<AssetValuationDetails, Long> {

	/**
	 * find Asset Valuation Details details by Asset Id
	 * 
	 * @param orgId
	 * @param assetId
	 * @return list of object with All details records if found else return null.
	 */
	@Query("select avd.bookFinYear, avd.bookValue, avd.deprValue, avd.accumDeprValue, avd.bookEndValue,avd.changetype from AssetValuationDetails avd where avd.orgId=:orgId and avd.assetId=:assetId order by avd.valuationDetId ASC")
	List<Object[]> findByAssetId(@Param("assetId") Long assetId, @Param("orgId") Long orgId);

}
