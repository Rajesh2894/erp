package com.abm.mainet.workManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;
import com.abm.mainet.workManagement.domain.TbWmsError;
import com.abm.mainet.workManagement.domain.TbWmsMaterialMaster;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;
import com.abm.mainet.workManagement.repository.MaterialMasterRepository;
import com.abm.mainet.workManagement.repository.WmsErrorRepository;

@Service
public class WmsMaterialMasterServiceImpl implements WmsMaterialMasterService {

    @Autowired
    private MaterialMasterRepository materialMasterRepository;

    @Autowired
    private WmsErrorRepository wmsErrorRepository;

    @Override
    @Transactional
    public void saveUpdateMaterialList(List<WmsMaterialMasterDto> materialMasterListDto, List<Long> deletedMaterialId) {

        TbWmsMaterialMaster entity = new TbWmsMaterialMaster();
        List<TbWmsMaterialMaster> entityList = new ArrayList<>();
        for (WmsMaterialMasterDto wmsMaterialMasterDto : materialMasterListDto) {
            BeanUtils.copyProperties(wmsMaterialMasterDto, entity);
            ScheduleOfRateMstEntity scheduleEntity = new ScheduleOfRateMstEntity();
            scheduleEntity.setSorId(wmsMaterialMasterDto.getSorId());
            entity.setSorId(scheduleEntity);
            entityList.add(entity);
        }

        materialMasterRepository.save(entityList);

        if (deletedMaterialId != null && !deletedMaterialId.isEmpty())
            materialMasterRepository.updateDeletedFlag(deletedMaterialId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsMaterialMasterDto> getMaterialListBySorId(Long sorId) {
        List<WmsMaterialMasterDto> masterDtoList = new ArrayList<WmsMaterialMasterDto>();
        List<TbWmsMaterialMaster> projectMasterEntityList = materialMasterRepository.getMaterialListBySorId(sorId);
        for (TbWmsMaterialMaster tbWmsMaterialMaster : projectMasterEntityList) {
            WmsMaterialMasterDto masterDtoListObject = new WmsMaterialMasterDto();
            BeanUtils.copyProperties(tbWmsMaterialMaster, masterDtoListObject);
            masterDtoList.add(masterDtoListObject);
        }
        return masterDtoList;
    }

    @Override
    @Transactional
    public void deleteMaterialById(Long sorId) {
        materialMasterRepository.deleteMaterialById(sorId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOfRateMstDto> findAllActiveMaterialBySorMas(long orgId) {
        List<ScheduleOfRateMstDto> masterDtoList = new ArrayList<ScheduleOfRateMstDto>();
        List<TbWmsMaterialMaster> projectMasterEntityList = materialMasterRepository
                .findAllActiveMaterialBySorMas(orgId);
        for (TbWmsMaterialMaster tbWmsMaterialMaster : projectMasterEntityList) {
            ScheduleOfRateMstDto masterDtoListObject = new ScheduleOfRateMstDto();
            BeanUtils.copyProperties(tbWmsMaterialMaster.getSorId(), masterDtoListObject);
            masterDtoList.add(masterDtoListObject);
        }

        List<ScheduleOfRateMstDto> noRepeat = new ArrayList<ScheduleOfRateMstDto>();
        for (ScheduleOfRateMstDto event : masterDtoList) {
            boolean isFound = true;
            for (ScheduleOfRateMstDto e : noRepeat) {
                if (e.getSorId().equals(event.getSorId()))
                    isFound = false;
            }
            if (isFound)
                noRepeat.add(event);
        }
        return noRepeat;
    }

    @Override
    @Transactional(readOnly = true)
    public String checkDuplicateExcelData(WmsMaterialMasterDto wmsMaterialMasterDto, long orgid) {
        String duplicateStatus = MainetConstants.IsDeleted.NOT_DELETE;
        List<TbWmsMaterialMaster> entity = materialMasterRepository.checkDuplicateExcelData(
                wmsMaterialMasterDto.getMaTypeId(), wmsMaterialMasterDto.getMaItemNo(), wmsMaterialMasterDto.getSorId(),
                orgid);
        if (entity != null && !entity.isEmpty())
            duplicateStatus = MainetConstants.IsDeleted.DELETE;
        return duplicateStatus;
    }

    @Override
    @Transactional
    public void saveAndDeleteErrorDetails(List<WmsErrorDetails> errorDetails, Long orgId, String masterType) {
        materialMasterRepository.deleteErrorLog(orgId, masterType);
        if (errorDetails != null && !errorDetails.isEmpty()) {
            TbWmsError entity = new TbWmsError();
            List<TbWmsError> entityList = new ArrayList<>();
            for (WmsErrorDetails wmsErrorDetails : errorDetails) {
                BeanUtils.copyProperties(wmsErrorDetails, entity);
                entityList.add(entity);
            }
            wmsErrorRepository.save(entityList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsErrorDetails> getErrorLog(Long orgId, String masterType) {
        List<WmsErrorDetails> details = new ArrayList<WmsErrorDetails>();
        List<TbWmsError> list = materialMasterRepository.getErrorLog(orgId, masterType);
        for (TbWmsError tbWmsError : list) {
            WmsErrorDetails errorDetails = new WmsErrorDetails();
            BeanUtils.copyProperties(tbWmsError, errorDetails);
            details.add(errorDetails);
        }
        return details;
    }

}
