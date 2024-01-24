/**
 * 
 */
package com.abm.mainet.asset.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.repository.DepreciationReportRepository;
import com.abm.mainet.asset.ui.dto.DepreciationReportDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class DepreciationReportServiceImpl implements IDepreciationReportService {

    @Autowired
    private DepreciationReportRepository reportService;
    @Autowired
    private TbFinancialyearService financialyearService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.service.DepreciationReportService#findByAssetId(java. lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true)
    public List<DepreciationReportDTO> findByAssetId(final Long assetId, final Long orgId) {
        final List<Object[]> objectList = reportService.findByAssetId(assetId, orgId);
        final List<DepreciationReportDTO> dtoList = new ArrayList<>();
        TbFinancialyear financiaYear = null;
        if ((objectList != null) && !objectList.isEmpty()) {
            for (final Object[] array : objectList) {
                final DepreciationReportDTO reportDTO = new DepreciationReportDTO();
                reportDTO.setBookFinYear((Long) array[0]);
                financiaYear = financialyearService.findYearById((Long) array[0], orgId);
                reportDTO.setBookFinYearDesc(
                        Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate()));
                reportDTO.setBookValue((BigDecimal) array[1]);
                reportDTO.setDeprValue((BigDecimal) array[2]);
                reportDTO.setAccumDeprValue((BigDecimal) array[3]);
                reportDTO.setEndBookValue((BigDecimal) array[4]);
                final String changetype = (String) array[5];
                if (changetype.equals(MainetConstants.AssetManagement.ValuationType.NEW.getValue())) {
                    reportDTO.setChangeType(ApplicationSession.getInstance().getMessage("asset.depreciation.assetregister"));
                } else if (changetype.equals(MainetConstants.AssetManagement.ValuationType.RETIRE.getValue())) {
                    reportDTO.setChangeType(ApplicationSession.getInstance().getMessage("asset.depreciation.assetretire"));
                } else if (changetype.equals(MainetConstants.AssetManagement.ValuationType.REVAL.getValue())) {
                    reportDTO.setChangeType(ApplicationSession.getInstance().getMessage("asset.depreciation.assetrevalute"));
                } else if (changetype.equals(MainetConstants.AssetManagement.ValuationType.DPR.getValue())) {
                    reportDTO.setChangeType(ApplicationSession.getInstance().getMessage("asset.depreciation.assetdepreciation"));
                } else {
                    reportDTO.setChangeType(ApplicationSession.getInstance().getMessage("asset.depreciation.assetimprovement"));
                }
                dtoList.add(reportDTO);
            }
            return dtoList;
        }
        return null;
    }
}
