/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author sarojkumar.yadav
 *
 * Service Class for Chart of Depreciation Master
 */
@WebService
public interface IChartOfDepreciationMasterService {

    /**
     * Add record in Chart Of Depreciation Master Entity
     * 
     * @param ChartOfDepreciationMasterDTO
     * @param organisation
     * @return boolean true if record inserted successfully else return false
     */
    public boolean addEntry(final ChartOfDepreciationMasterDTO cdmDTO);

    /**
     * find CDM Master details by groupId(primary key)
     * 
     * @param groupId
     * @param organisation
     * @return ChartOfDepreciationMasterDTO with All details records if found else return null.
     */
    public ChartOfDepreciationMasterDTO findByGroupId(final Long groupId);

    /**
     * find Chart Of Depreciation Master details by orgId
     * 
     * @param orgId
     * @param organisation
     * @return list of ChartOfDepreciationMasterDTO with All details records if found else return null.
     */
    public List<ChartOfDepreciationMasterDTO> findByOrgId(final Long orgId);

    /**
     * search chart Of Depreciation Master Records by name or/and by accountCode or/and by assetClass or/and by frequency with org
     * id.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return List of ChartOfDepreciationMasterDTO for search criteria
     */
    public List<ChartOfDepreciationMasterDTO> findBySearchData(final String accountCode, final Long assetClass,
            final Long frequency, final String name, final Long orgId, String moduleDeptCode);

    /**
     * update chart Of Depreciation Master fields by name or/and by accountCode or/and by assetClass or/and by frequency with
     * primary Key groupId.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return true if record updated successfully else return false
     */
    public boolean updateByGroupId(final ChartOfDepreciationMasterDTO cdmDTO);

    /**
     * check for duplicate name in Chart of Depreciation Master table
     * @param orgId
     * @param name
     * @return true if duplicate entry found else return false
     */
    public boolean checkDuplicateName(final Long orgId, final String name);

    /**
     * find Chart Of Depreciation Master details by orgId and Asset Class
     * @param orgId
     * @param assetClass
     * @return list of lookUp with All details records if found else return null.
     */
    public List<LookUp> findAllByOrgIdAstCls(final Long orgId, final Long assetClass);

    /**
     * Get child level data from a prefix
     * @param orgId
     * @param prefix
     * @return list of LookUp with All details records if found else return null.
     */
    public List<LookUp> getChildByPrefix(final Long orgId, final String cpmPrefix);

    /**
     * find Asset Class by orgId and frequency
     * @param orgId
     * @param frequency
     * @return list of ChartOfDepreciationMasterDTO if found else return null.
     */
    public List<ChartOfDepreciationMasterDTO> getAssetClassByfrequency(final Long orgId, final Long frequency, final Long method);

    /**
     * check for duplicate asset class in Chart of Depreciation Master table
     * @param orgId
     * @param Asset Class
     * @return true if duplicate entry found else return false
     */
    public boolean checkDuplicateAssetClass(final Long orgId, final Long assetClass);

    /**
     * find CDM Master details by asset class in Chart of Depreciation Master table
     * 
     * @param asset class
     * @param organisation
     * @return ChartOfDepreciationMasterDTO with All details records if found else return null.
     */
    public ChartOfDepreciationMasterDTO getAllByAssetClass(final Long orgId, final Long assetClass);

    /**
     * @param groupId
     * @param AssetClass2
     * @return check how many combinations are there for Asset Class and Group Id
     */
    public Long getAssetClassByGroupIdAndAssetClass(final Long groupId, Long AssetClass2);
}
