package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.repository.WorkEstimateReportRepository;

/**
 * @author vishwajeet.kumar
 * @since 1 March 2018
 */
@Service
public class WorkEstimateReportServiceImpl implements WorkEstimateReportService {

    @Autowired
    public WorkEstimateReportRepository repository;

    /**
     * Used to find find Abstract Sheet Report
     * 
     * @param workId
     * @param orgId
     * @return List<WorkEstimateMasterDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> findAbstractSheetReport(Long workId,Long deptId,Long workType,Long orgId) {

        List<WorkEstimateMasterDto> estimateMasterDtosList = new ArrayList<>();
        List<Object[]> estimateMastersEntity = repository.findAbstractReportSheet(workId,deptId,workType,orgId);

        for (Object[] masterEntity : estimateMastersEntity) {

            BigDecimal amount = null;
            WorkEstimateMasterDto masterDto = new WorkEstimateMasterDto();

            masterDto.setSordCategory((Long) masterEntity[0]);
            masterDto.setSordSubCategory((String) masterEntity[1]);
            masterDto.setSorDIteamNo((String) masterEntity[2]);
            masterDto.setSorDDescription((String) masterEntity[3]);

            if (masterEntity[4] != null) {
                masterDto.setSorIteamUnitDesc(
                        CommonMasterUtility.getCPDDescription((Long) masterEntity[4], MainetConstants.BLANK));
            }

            masterDto.setWorkEstimQuantity((BigDecimal) masterEntity[5]);
            masterDto.setRate((BigDecimal) masterEntity[6]);

            if (masterDto.getWorkEstimQuantity() != null && masterDto.getRate() != null) {
                amount = masterDto.getWorkEstimQuantity().multiply(masterDto.getRate());
            }
            if (amount != null) {
                masterDto.setWorkEstimAmount(amount.setScale(2, RoundingMode.HALF_EVEN));
            }
            estimateMasterDtosList.add(masterDto);
        }
        return estimateMasterDtosList;
    }

    /**
     * Used to find Measurement Report
     * 
     * @param workId
     * @param orgId
     * @return List<WorkEstimateMasterDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> findMeasurementReport(Long workId, Long orgId) {
        List<WorkEstimateMasterDto> estimateMasterDtosList = new ArrayList<>();

        List<Object[]> estimateMastersEntity = repository.findMeasurementReportSheet(workId, orgId);
        WorkEstimateMasterDto masterDto = null;

        WorkEstimateMeasureDetailsDto measurementDetails = null;

        Map<Long, WorkEstimateMasterDto> map = new LinkedHashMap<>();

        if (estimateMastersEntity != null && !estimateMastersEntity.isEmpty()) {

            for (Object[] masterEntity : estimateMastersEntity) {

                Long workEstemId = (Long) masterEntity[0];
                masterDto = map.get(workEstemId);

                if (masterDto == null) {

                    masterDto = new WorkEstimateMasterDto();
                    masterDto.setSorDIteamNo((String) masterEntity[1]);
                    masterDto.setSorDDescription((String) masterEntity[2]);
                    map.put(workEstemId, masterDto);
                }

                measurementDetails = new WorkEstimateMeasureDetailsDto();
                String calculatedType = (String) masterEntity[3];
                if (calculatedType != null) {
                    if (calculatedType.equals(MainetConstants.FlagC)) {
                        measurementDetails.setMeMentType(MainetConstants.WorksManagement.CALCULATED);
                    } else if (calculatedType.equals(MainetConstants.FlagD)) {
                        measurementDetails.setMeMentType(MainetConstants.WorksManagement.DIRECT);
                    } else if (calculatedType.equals(MainetConstants.FlagF)) {
                        measurementDetails.setMeMentType(MainetConstants.WorksManagement.FORMULA);
                    }
                }
                measurementDetails.setMeMentNumber((Long) masterEntity[4]);
                measurementDetails.setMeMentLength((BigDecimal) masterEntity[5]);
                measurementDetails.setMeMentBreadth((BigDecimal) masterEntity[6]);
                measurementDetails.setMeMentHeight((BigDecimal) masterEntity[7]);
                measurementDetails.setMeValue((BigDecimal) masterEntity[8]);
                measurementDetails.setMeMentFormula((String) masterEntity[9]);
                measurementDetails.setMeMentToltal((BigDecimal) masterEntity[10]);
                masterDto.addMeasurementDto(measurementDetails);
            }
            estimateMasterDtosList.addAll(map.values());
        }

        return estimateMasterDtosList;

    }

}
