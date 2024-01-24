package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetailsHistory;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.repository.WorksMeasurementSheetRepository;

@Service
public class WorksMeasurementSheetServiceImpl implements WorksMeasurementSheetService {

    private static final Logger LOGGER = Logger.getLogger(WorksMeasurementSheetServiceImpl.class);

    @Autowired
    private WorksMeasurementSheetRepository worksMeasurementSheetRepository;

    @Override
    @Transactional
    public void saveUpdateEstimateMeasureDetails(List<WorkEstimateMeasureDetailsDto> measureDetailsList,
            List<Long> removeIds, String approvalFlag) {
        WorkEstimateMeasureDetails entity = null;
        List<WorkEstimateMeasureDetails> entityList = new ArrayList<>();
        List<Object> historyList = new ArrayList<>();
        for (WorkEstimateMeasureDetailsDto dro : measureDetailsList) {
            entity = new WorkEstimateMeasureDetails();
            BeanUtils.copyProperties(dro, entity);
            entityList.add(entity);
        }

        worksMeasurementSheetRepository.save(entityList);

      //  if (approvalFlag != null && approvalFlag.equals(MainetConstants.WorksManagement.APPROVAL)) {

            entityList.forEach(e -> {
                WorkEstimateMeasureDetailsHistory entityDetailsHist = new WorkEstimateMeasureDetailsHistory();
                BeanUtils.copyProperties(e, entityDetailsHist);
                historyList.add(entityDetailsHist);
            });
            try {
                ApplicationContextProvider.getApplicationContext().getBean(AuditService.class)
                        .createHistoryForListObj(historyList);
            } catch (Exception exception) {
                throw new FrameworkException("Esxception occured when create history of work Measurement Details Service  ", exception);
            }
       // }

        if (removeIds != null && !removeIds.isEmpty())
            worksMeasurementSheetRepository.updateDeletedFlag(removeIds);

    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEId) {
        return worksMeasurementSheetRepository.calculateTotalEstimatedAmountByWorkId(workEId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMeasureDetailsDto> getWorkEstimateDetailsByWorkEId(Long WorkEId) {
        List<WorkEstimateMeasureDetails> listEntity = worksMeasurementSheetRepository
                .getWorkEstimateDetailsByWorkEId(WorkEId);
        List<WorkEstimateMeasureDetailsDto> dtoList = new ArrayList<>();
        if (!listEntity.isEmpty()) {
            listEntity.forEach(entity -> {
                WorkEstimateMeasureDetailsDto obj = new WorkEstimateMeasureDetailsDto();
                BeanUtils.copyProperties(entity, obj);
                dtoList.add(obj);
            });
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMeasureDetailsDto> getAuditMeasuremnetByWorkEId(Long WorkEId) {
        List<WorkEstimateMeasureDetailsHistory> listEntity = worksMeasurementSheetRepository
                .getAuditMeasuremnetByWorkEId(WorkEId);
        List<WorkEstimateMeasureDetailsDto> dtoList = new ArrayList<>();
        listEntity.forEach(entity -> {
            WorkEstimateMeasureDetailsDto obj = new WorkEstimateMeasureDetailsDto();
            BeanUtils.copyProperties(entity, obj);
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional
    public void updateUtilizationNoByMeId(Long nosUtilize, Long meMentId) {
        worksMeasurementSheetRepository.updateUtilizationNoByMeId(nosUtilize, meMentId);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkEstimateMeasureDetailsDto findByMeasureDetailsId(Long meMentId) {
        WorkEstimateMeasureDetailsDto flowDto = null;
        WorkEstimateMeasureDetails entity = worksMeasurementSheetRepository.findMeasureDetailsById(meMentId);
        if (entity != null) {
            flowDto = new WorkEstimateMeasureDetailsDto();
            BeanUtils.copyProperties(entity, flowDto);
        }
        return flowDto;
    }

}
