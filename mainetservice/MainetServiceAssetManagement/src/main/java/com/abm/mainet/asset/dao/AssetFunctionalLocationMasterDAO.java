package com.abm.mainet.asset.dao;

import java.util.List;

import com.abm.mainet.asset.domain.AssetAnnualPlan;
import com.abm.mainet.asset.domain.AssetFunctionalLocation;
import com.abm.mainet.asset.domain.AssetRequisition;

public interface AssetFunctionalLocationMasterDAO {

    /**
     * returns All AssetFunctionalLocation object List by default use to display parent dropdwn values
     * 
     * @return AssetFunctionalLocation List of objects
     */
    public List<AssetFunctionalLocation> retriveList(final Long orgId); // remove funcLocCode retrive list

    /**
     * save AssetFunctionalLocation object present inside the List to database
     * 
     * @return AssetFunctionalLocation List of objects
     */
    public boolean save(List<AssetFunctionalLocation> funcLocCodeList); /// only save

    /**
     * update AssetFunctionalLocation object present inside the List to database
     * 
     * @return true if successfully saved in database.
     */
    public boolean update(AssetFunctionalLocation funcLocCode); /// only save

    /**
     * used validate the function location code based onblur function
     * 
     * @param funcLocCode
     * @return true when duplicate
     */
    public boolean isDuplicate(String funcLocCode, Long orgId);   // us duplicate

    /**
     * used to filter search results on basis of below given parameters
     * 
     * @param funcLocCode
     * @param description
     * @param orgId
     */
    public List<AssetFunctionalLocation> search(String funcLocCode, String parentFuncLocCode, Long orgId);

    /**
     * returns AssetFunctionalLocation object based on
     * 
     * @param funcLocId
     * @param orgId
     * 
     * @return AssetFunctionalLocation
     */
    public AssetFunctionalLocation findByFuncLocId(Long funcLocId, Long orgId);

    List<AssetRequisition> searchAssetRequisitionData(Long astCategoryId, Long locId, Long deptId, Long orgId,
            String moduleDeptCode);

    List<AssetAnnualPlan> searchAssetAnnualPlanData(Long finYearId, Long deptId, Long locId, Long orgId,
            String moduleDeptCode);

}
