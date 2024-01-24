/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;

/**
 * @author satish.rathore
 *
 */
public interface AssetDetailsReportRepoCustom {

    List<Object[]> findAssetInfo(final Long assetGroup, final Long assetType, final Long assetClass1,
            final Long assetClass2, final Long orgId);

    List<Object[]> getAssetValuation(Long assetId, Long faYearId, Long orgId);

    List<Object[]> getRetireValue(Long assetId, Long orgId);

    List<Object[]> registerOfMovableReport(Long assetClass1,
            Long orgId, Long faYearId, Long prefixId);

    List<Object[]> getLandByAssetId(final String serialNo, final Long faYearId, final Long orgId);

    public List<AssetInformationDTO> getAssetCodeByCategory(Long assetClass, Long orgId);

}
