/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import com.abm.mainet.asset.domain.ChartOfDepreciationMasterEntity;

/**
 * @author sarojkumar.yadav
 *
 * Custom Repository Class for Chart of Depreciation Master to find the record on search criteria
 */
public interface ChartOfDepreciationMasterRepositoryCustom {

    /**
     * search chart Of Depreciation Master Records by name or/and by accountCode or/and by assetClass or/and by frequency with org
     * id.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return List<ChartOfDepreciationMasterEntity> for search criteria
     */
    public List<ChartOfDepreciationMasterEntity> findByAllSearchData(final String accountCode, final Long assetClass,
            final Long frequency, final String name, final Long orgId, String deptCode);

    /**
     * update chart Of Depreciation Master Entity with primary Key groupId.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return true if record updated successfully else return false
     */
    public boolean updateByGroupId(final ChartOfDepreciationMasterEntity cdmEntity);

    public Long getAssetClassByGroupIdAndAssetClass(final Long groupId, Long assetClass2);
}