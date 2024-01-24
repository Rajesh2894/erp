package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;

public interface IInsuranceService {
    /**
     * Adds the asset insurance information to existing list of service
     * @param dto asset details to update
     */
    void addInsuranceInfo(AssetInsuranceDetailsDTO dto);

    /**
     * Update the asset insurance information
     * @param id insurance id for which information is to be updated
     * @param dto asset details to update
     */
    Long updateInsuranceInfo(Long id, AssetInsuranceDetailsDTO dto);
    
    Long updateInsuranceInfoRev(Long id, AssetInsuranceDetailsDTO dto);

    /**
     * This is for saving the insurance details
     * @param dto
     */
    Long saveInsurance(AssetInsuranceDetailsDTO dto);
    
    Long saveInsuranceRev(final List<AssetInsuranceDetailsDTO> dtoList);
    
    List<AssetInsuranceDetailsDTO> getAllInsuranceDetailsList(final Long assetId,final AssetInsuranceDetailsDTO dto);
    

    /**
     * Get asset insurance information by assetId
     * @param assetId
     */
    AssetInsuranceDetailsDTO getInsuranceByAssetId(Long assetId);
    
    List<AssetInsuranceDetailsDTO> getInsuranceListByAssetId(Long assetId);
    
    AssetInsuranceDetailsDTO getInsuranceRevByAssetId(Long assetId);
    
    List<AssetInsuranceDetailsDTO> getInsuranceListRevByGroupId(Long assetId);

}
