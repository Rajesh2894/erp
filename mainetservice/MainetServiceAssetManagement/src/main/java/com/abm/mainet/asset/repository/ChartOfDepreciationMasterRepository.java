/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.asset.domain.ChartOfDepreciationMasterEntity;

/**
 * @author sarojkumar.yadav
 *
 * Repository Class for Chart of Depreciation Master
 */
public interface ChartOfDepreciationMasterRepository extends
        PagingAndSortingRepository<ChartOfDepreciationMasterEntity, Long>, ChartOfDepreciationMasterRepositoryCustom {
    /**
     * find Chart Of Depreciation Master details by orgId
     * 
     * @param orgId
     * @return list of ChartOfDepreciationMasterEntity with All details records if found else return null.
     */
    @Query("select cdm from ChartOfDepreciationMasterEntity cdm where cdm.orgId=:orgId")
    List<ChartOfDepreciationMasterEntity> findAllDepreciationMasterListByOrgId(@Param("orgId") Long orgId);

    /**
     * check for duplicate name in Chart of Depreciation Master table
     * 
     * @param orgId
     * @param name
     * @return list of ChartOfDepreciationMasterEntity with All details records if found else return null.
     */
    @Query("select cdm from ChartOfDepreciationMasterEntity cdm where cdm.orgId=:orgId and lower(cdm.name)=:name")
    List<ChartOfDepreciationMasterEntity> checkDuplicateName(@Param("orgId") Long orgId, @Param("name") String name);

    /**
     * find Chart Of Depreciation Master details by orgId and Asset Class
     * 
     * @param orgId
     * @param assetClass
     * @return list of object with All details records if found else return null.
     */
    @Query("select cdm.groupId, cdm.name from ChartOfDepreciationMasterEntity cdm where cdm.orgId=:orgId and cdm.assetClass=:assetClass ORDER BY cdm.name ASC")
    List<Object[]> findAllByOrgIdAstCls(@Param("orgId") Long orgId, @Param("assetClass") Long assetClass);

    /**
     * Get child level data from a prefix
     * 
     * @param orgId
     * @param prefix
     * @return list of LookUp with All details records if found else return null.
     */
    @Query("Select vd.codCpdId, vd.codCpdDesc, vd.codCpdDescMar from ViewPrefixDetails vd where vd.orgid=:orgid and vd.cpmPrefix=:cpmPrefix and codCpdParentId is not null")
    List<Object[]> getChildByPrefix(@Param("orgid") Long orgid, @Param("cpmPrefix") String cpmPrefix);

    /**
     * find Asset Class by orgId and frequency
     * 
     * @param orgId
     * @param frequency
     * @return list of Asset Class if found else return null.
     */
    @Query("select cdm from ChartOfDepreciationMasterEntity cdm where cdm.orgId=:orgId and cdm.frequency=:frequency and cdm.depreciationKey=:depreciationKey")
    List<ChartOfDepreciationMasterEntity> getAssetClassByfrequency(@Param("orgId") Long orgId,
            @Param("frequency") Long frequency, @Param("depreciationKey") Long method);

    /**
     * check for duplicate Asset Class in Chart of Depreciation Master table
     * 
     * @param orgId
     * @param asset Class
     * @return list of ChartOfDepreciationMasterEntity with All details records if found else return null.
     */
    @Query("select cdm from ChartOfDepreciationMasterEntity cdm where cdm.orgId=:orgId and cdm.assetClass=:assetClass")
    List<ChartOfDepreciationMasterEntity> getAllByAssetClass(@Param("orgId") Long orgId, @Param("assetClass") Long assetClass);
}