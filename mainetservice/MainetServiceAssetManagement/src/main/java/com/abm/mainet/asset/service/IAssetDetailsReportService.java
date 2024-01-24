/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.asset.ui.dto.AssetDetailsReportDto;
import com.abm.mainet.asset.ui.dto.AssetInformationReportDto;
import com.abm.mainet.asset.ui.dto.ReportDetailsListDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;

/**
 * @author satish.rathore
 *
 */
public interface IAssetDetailsReportService {

    AssetDetailsReportDto findDetailReport(final Long assetGroup, final Long assetType, final Long assetClass1,
            final Long assetClass2, final Long orgId, final Integer langId);

    List<ReportDetailsListDTO> registerOfMovableReport(Long assetClass1, Long orgId, Long faYearId, Long prefixId);

    public Long getImmovableAssetIdByAssetCode(final Long orgId, final Long assetClass, final String assetCode);

    public List<ReportDetailsListDTO> registerImmovableReport(final Long assetId, final Long orgId,
            final Integer langId, final Date startDate, final Date endDate);

    public Long getLandAssetIdByAssetCode(final Long orgId, final Long assetClass, final String assetCode);

    public List<ReportDetailsListDTO> registerLandReport(final Long assetId, final Long orgId, final Integer langId,
            final Date startDate, final Date endDate);

    public Long getPrefixIdByPrefixCode(final Long orgId, final String prefix, final String assetClass);

    public AssetInformationReportDto getPrimaryDetails(final Long assetId, final Long orgId, Integer langId);

    public List<AssetInformationDTO> getAssetCodeByCategory(final Long assetClass, final Long orgId);
}
