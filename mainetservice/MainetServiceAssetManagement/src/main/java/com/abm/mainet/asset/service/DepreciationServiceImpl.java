/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetDepreciationChart;
import com.abm.mainet.asset.domain.AssetDepreciationChartRev;
import com.abm.mainet.asset.domain.DepreciationChartHistory;
import com.abm.mainet.asset.mapper.DepreciationChartMapper;
import com.abm.mainet.asset.repository.AssetDepreciationChartRepo;
import com.abm.mainet.asset.repository.AssetDepreciationChartRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;

/**
 * @author satish.rathore
 *
 */
@Service
public class DepreciationServiceImpl implements IDepreciationService {

    private static final Logger LOGGER = Logger.getLogger(DepreciationServiceImpl.class);

    @Autowired
    private AssetDepreciationChartRepo assetDepreciationChart;

    @Autowired
    private AssetDepreciationChartRevRepo assetDepreciationChartRev;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    public Long saveDepreciation(AssetDepreciationChartDTO dto) {
        Long depChartId = null;
        DepreciationChartHistory entityHistory = new DepreciationChartHistory();
        AssetDepreciationChart depChartEntiy = DepreciationChartMapper.mapToEntity(dto);
        try {
            depChartEntiy = assetDepreciationChart.save(depChartEntiy);
            depChartId = depChartEntiy.getAssetDepretnId();
            entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            try {
                auditService.createHistory(depChartEntiy, entityHistory);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry while saving depreciation chart ", ex);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset depreciation chart ", exception);
        }

        return depChartId;

    }

    @Override
    @Transactional
    public Long saveDepreciationRev(AssetDepreciationChartDTO dto) {
        Long idRev = null;
        // DepreciationChartHistory entityHistory = new DepreciationChartHistory();
        AssetDepreciationChartRev depChartEntiyRev = DepreciationChartMapper.mapToEntityRev(dto);
        try {
            depChartEntiyRev = assetDepreciationChartRev.save(depChartEntiyRev);
            idRev = depChartEntiyRev.getAssetDepretnRevId();
            // entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            // auditService.createHistory(depChartEntiy, entityHistory);
        } catch (Exception exception) {
            throw new FrameworkException("Exception occur while saving asset depreciation chart ", exception);
        }

        return idRev;

    }

    @Override
    public void updateDepreciation(AssetDepreciationChartDTO dto) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional(readOnly = true)
    public AssetDepreciationChartDTO getChartByAssetId(Long assetId) {
        AssetDepreciationChartDTO dto = null;
        final AssetDepreciationChart entity = assetDepreciationChart.findChartByAssetId(assetId);
        if (entity != null) {
            dto = DepreciationChartMapper.mapToDTO(entity);
        }
        return dto;
    }

    // D#112592
    @Transactional
    @Override
    public AssetDepreciationChartDTO findDeprtChartByAssetId(Long assetId) {
        AssetDepreciationChartDTO dto = null;
       
        List<AssetDepreciationChart> dprCharts = assetDepreciationChart.findDeprtChartByAssetId(assetId);
        if (!dprCharts.isEmpty()) {
        	AssetDepreciationChart dprChart = dprCharts.stream().filter(depr -> MainetConstants.FlagY.equals(depr.getDeprApplicable()))
        	        .findFirst().orElse(null);
            if (dprChart != null) {
                dto = DepreciationChartMapper.mapToDTO(dprChart);
            }
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public AssetDepreciationChartDTO getChartRevByAssetId(Long assetId) {
        AssetDepreciationChartDTO dto = null;
        final AssetDepreciationChartRev entity = assetDepreciationChartRev.findChartRevByAssetId(assetId);
        // final AssetDepreciationChart entity = assetDepreciationChart.findChartByAssetId(assetId);
        if (entity != null) {
            dto = DepreciationChartMapper.mapToDTORev(entity);
        }
        return dto;
    }

    @Override
    @Transactional
    public Long updateDepreciationByAssetId(final Long assetId, final AssetDepreciationChartDTO dto) {
        Long depChartId = null;
        try {
            DepreciationChartHistory entityHistory = new DepreciationChartHistory();
            if (dto.getAssetDeprChartId() != null) {
                AssetDepreciationChart depChartEntiy = DepreciationChartMapper.mapToEntity(dto);
                assetDepreciationChart.updateDepreciationChart(depChartEntiy);
                entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
                try {
                    auditService.createHistory(depChartEntiy, entityHistory);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry while updating depreciation chart ", ex);
                }
            } else {
                dto.setAssetId(assetId);
                depChartId = saveDepreciation(dto);

            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while updating service depreciation  ", exception);
            throw new FrameworkException("Exception occurs while updating Asset Service depreciation Details ",
                    exception);
        }
        return depChartId;
    }

}
