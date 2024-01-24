/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInventoryInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPostingInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;

/**
 * Provides methods to be implemented for information of an asset
 * @author Sarojkumar.Yadav
 *
 */
public interface IInformationService {

    /**
     * Saves the asset information details
     * @param dto asset details to save
     * @return the identifier for this asset
     */
    public Long saveInfo(final AssetInformationDTO dto);

    /**
     * Saves the asset information details
     * @param dto asset details to save
     */
    public void updateInformation(final Long assetId, final AssetInformationDTO dto);

    /**
     * Saves the asset information details
     * @param dto asset details to save
     */
    public Long saveInformationRev(final Long assetId, final AssetInformationDTO dto);

    /**
     * Updates the asset inventory information
     * @param dto asset details to update
     */
    public void updateInventory(final Long assetId, final Long orgId, final AssetInventoryInformationDTO dto);

    /**
     * Updates the specification of an asset
     * @param dto asset details to update
     */
    void updateSpecification(final Long assetId, final Long orgId, final AssetSpecificationDTO dto);

    /**
     * Updates the specification of an asset
     * @param dto asset details to update
     */
    public void updatePosting(final Long assetId, final Long orgId, final AssetPostingInformationDTO dto);

    /**
     * Get asset information by primary key
     * @param Id i.e. primary key assetId
     */
    public AssetInformationDTO getInfoById(final Long id);

    public AssetInformationDTO getInfoByCode(final Long orgId, final String assetCode);

    public AssetInformationDTO getInfoRevById(final Long id);

    /**
     * used validate the Assset Name based onblur function
     * 
     * @param assetName
     * @return true when duplicate
     */
    public boolean isDuplicateName(final Long orgId, final String assetName);

    /**
     * used validate the Serial No code based onblur function
     * 
     * @param assetName
     * @return true when duplicate
     */
    public boolean isDuplicateSerialNo(final Long orgId, final String serialNo, final Long assetId);

    /**
     * Get asset information by using serial No (Unique constriant added)
     * @param Id i.e. assetId
     */
    public AssetInformationDTO getAssetId(Long orgId, Long assetId);

    /**
     * Used to update approval status Flag
     * 
     * @param assetId
     * @param entity
     */
    public boolean updateAppStatusFlag(Long assetId, Long orgId, String appovalStatus, String astAppNo);

    boolean updateAstCode(Long assetId, Long orgId, String astCode);
    
    void updateAssetCode(Long assetId, Long orgId, String astCode);

    void updateGroupRefId(List<Long> assetIds,String groupRefId,String appovalStatus, Long orgId);

    /**
     * Used to update approval status Flag
     * 
     * @param assetId
     * @param entity
     */
    public boolean updateURLParam(Long assetId, Long orgId, String urlParam);

    public Set<String> getReferenceAsset(Long orgId);

    /**
     * Used to get list of asset by matching asset class in an org
     * 
     * @param assetClass
     * @param assetStatus
     * @param orgId
     */
    public List<AssetInformationDTO> getAssetByAssetClass(final Long orgId, final Long assetClass, final Long assetStatus,
            String appovalStatus);

    public void updateEmployee(final Long id, final Long empId);

    @Deprecated
    void updateLocation(Long id, Long locationId);

    public boolean isDuplicateRfIdNo(Long orgId, String rfiId);

    public boolean updateStatusFlag(Long assetId, Long orgId, String appovalStatus, Long statusId, String astAppNo);

    public List<AssetInformationDTO> fetchAssetInfoByStatus(final Long orgId, final Long assetStatus);

    public List<AssetInformationDTO> fetchAssetInfoByIds(Long orgId, Long assetStatusId, Long assetClassId);

    public void updateUrlParamNullById(Long astId, Long orgId);
    
    public List<AssetInformationDTO> findAllInformationListByGroupRefId(String groupRefId,Long orgId);

	Long getAssetCount(Long orgId, Long assetClass2, Long assetStatus);

	List<String> getSerialNo(Long orgId, String assetCode);

	List<String> getAssetCode(Long orgId,Long assetClass2);

	

	Long getAllAssetCount(Long orgId, Long assetClass2, Long assetStatus);

	void upDateEmpDept(Long orgId, String serialNo, String astCode, Long employeeId, String deptCode, Date updatedDate,
			Long location);

	AssetInformationDTO getInfoByCodeAndSerialNo(Long orgId, String assetCode, String serialNo);

	void updateLocationId(Long id, Long location);

	void updateDepartment(Long id, String astAvlstatus);

	void updateAsssetDatahis(Long orgId,Long location,  String serialNo, String astCode, Long employeeId);

	void updateEmpDephis(Long orgId,Long location, String serialNo, String astCode, Long employeeId);
	
	void updateAssetHistory(Long astId, Long orgId,Long employeeId);

	public void updateAssetIdbySerialNo(Long assetRequisitionId, Long orgId, String assestId);

	public AssetInformation getAssetCodeIdentifier(Long orgId, String astCode, String serialNo);

	String findGroupRefIdByAssetAppNo(String astAppNo);
	
	

	

	

	
}