package com.abm.mainet.asset.service;

import com.abm.mainet.common.dto.GeoCoordinatesDTO;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;

/**
 * @author satish.rathore
 *
 */
public interface IClassificationInfoService {
    /**
     * Saves the asset classification information
     * 
     * @param dto asset details to update
     */
    public Long saveClassInfo(final AssetClassificationDTO dto);

    /**
     * Saves the asset classification information
     * 
     * @param dto asset details to update
     */
    public Long saveClassInfoRev(final AssetClassificationDTO dto);

    /**
     * Update the asset classification information
     * 
     * @param dto asset details to update
     */
    public Long updateClassInfo(final Long assetId, final AssetClassificationDTO dto);

    // Rev
    /**
     * save the asset classification information
     * 
     * @param dto asset details to update
     */
    Long saveClassificationRev(final Long assetId, final AssetClassificationDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * Get asset classification information by assetClassificationId
     * 
     * @param assetId
     */
    public AssetClassificationDTO getclassByAssetId(final Long assetClassificationId);

    /**
     * Get asset classification Rev information by assetClassificationId
     * 
     * @param assetClassIdRev
     */
    public AssetClassificationDTO getclassRevById(final Long assetClassIdRev);

    /* public List<LookUp> getCostCenterbyOrgId(Long orgId, int langId); */

    public void updateDepartment(Long assetId, Long assetdepartment);

    void updateLocation(Long id, Long locationId);

    boolean updateGeoInformation(final Long assetId, final GeoCoordinatesDTO geoCoordDto);
}
