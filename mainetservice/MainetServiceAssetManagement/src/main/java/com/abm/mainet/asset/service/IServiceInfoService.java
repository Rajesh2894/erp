package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;

public interface IServiceInfoService {
    /**
     * Adds the asset service information to existing list of service
     * @param dto asset details to update
     */
    void addServiceInfo(AssetServiceInformationDTO dto);

    /**
     * Update the asset service information
     * @param id service id for which information is to be updated
     * @param orgId organization to update
     * @param dto asset details to update
     */
    Long updateServiceInfo(final Long id, final List<AssetServiceInformationDTO> dto);
    
    /**
     * Update the asset service information
     * @param id service id for which information is to be updated
     * @param orgId organization to update
     * @param dto asset details to update
     */
    Long updateServiceInfo(final Long id, final AssetServiceInformationDTO dto);

    /**
     * Saves the asset Service Information
     * @param dto
     */
    Long saveServiceInfo(final AssetServiceInformationDTO dto);

    Long saveServiceInfoRev(final List<AssetServiceInformationDTO> dto, Long astId, Long orgId);
    
    /**
     * Get asset service infromation by assetId
     * @param assetId
     */
    public List<AssetServiceInformationDTO> getServiceByAssetId(final Long assetId);

    public List<AssetServiceInformationDTO> getServiceRevByAssetId(final Long assetId);

	Long saveRealEstateInfoRev(AssetRealEstateInformationDTO dto, Long astId, Long orgId);

}
