package com.abm.mainet.asset.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.asset.domain.AssetFunctionalLocation;
import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;

@WebService
public interface IAssetFunctionalLocationMasterService {

    public boolean addFunctionalLocationCode(List<AssetFunctionalLocation> assetFunctionalLocationList);  // not required

    /**
     * returns All AssetFunctionalLocation object List by default use to display parent dropdown values
     * 
     * @return AssetFunctionalLocation List of objects
     */
    public List<AssetFunctionalLocation> retriveFunctionLocationListByOrgId(final Long orgId);

    /**
     * returns All AssetFunctionalLocation object List by default use to display parent dropdown values
     * 
     * @return AssetFunctionalLocation List of objects
     */
    public List<AssetFunctionalLocationDTO> retriveFunctionLocationDtoListByOrgId(final Long orgId);

    /**
     * save AssetFunctionalLocation object present inside the List to database
     * 
     * @return boolean
     */
    public boolean saveFunctionLocationList(List<AssetFunctionalLocation> funcLocCodeList);

    /**
     * update AssetFunctionalLocation object present inside the List to database
     * 
     * @param AssetFunctionalLocation object
     * @return true if successfully saved in database.
     */
    public boolean updateFunctionLocation(AssetFunctionalLocation funcLocCode);

    /**
     * used validate the function location code based onblur function
     * 
     * @param funcLocCode
     * @return true when duplicate
     */
    public boolean isDuplicate(String funcLocCode, Long orgId);

    /**
     * returns search result
     * 
     * @param funcLocCode
     * @param description
     * @param orgId
     * 
     * @return List<AssetFunctionalLocation>
     */
    public List<AssetFunctionalLocation> searchFunLocByLocCodeAndDescriptionAndOrgId(String funcLocCode, String description, Long orgId);

    /**
     * returns search result
     * 
     * @param funcLocId
     * @param orgId
     * 
     * @return AssetFunctionalLocation object corresponding to funcLocId and orgId
     */
    public AssetFunctionalLocation findByFuncLocId(Long funcLocId, Long orgId);
}
