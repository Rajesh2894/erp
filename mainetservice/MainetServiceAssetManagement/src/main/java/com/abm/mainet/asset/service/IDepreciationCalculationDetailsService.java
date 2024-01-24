/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.AstSchedulerMasterDTO;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IDepreciationCalculationDetailsService {

    /**
     * Used to get list of asset by asset class and orgId
     * 
     * @param assetId
     * @return DepreciationAssetDTO
     */
    public List<AssetValuationDetailsDTO> getAssetWithDepreciation(final Long orgId, final ChartOfDepreciationMasterDTO cdmDTO,
            final Long assetstatus);

    /**
     * Used to calculate depreciation for each asset
     * 
     * @param AssetValuationDetailsDTO
     */
    public void calculateDepreciation(final AssetValuationDetailsDTO dto, final Integer months, FinancialYear financiaYear);

    public AstSchedulerMasterDTO getAssetWithDepreciation(final Long orgId,
            final String assetCode, final AuditDetailsDTO auditDTO, final Date assetDateField);

    public VoucherPostExternalDTO accountVoucherPostingByAsset(final Long orgId,
            ChartOfDepreciationMasterDTO dto, final List<AssetValuationDetailsDTO> valuationDtoList, final Date assetDateField);

    public AstSchedulerMasterDTO getAssetDepreciationByClass(final Long orgId, final Long assetClass,
            final AuditDetailsDTO auditDTO, final Date assetDateField);

}
