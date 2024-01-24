package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;
import com.abm.mainet.workManagement.domain.WmsLeadLiftMasterEntity;
import com.abm.mainet.workManagement.domain.WmsLeadLiftMasterEntityHistory;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;
import com.abm.mainet.workManagement.repository.LeadLiftMasterRepository;

@Service
public class WmsLeadLiftMasterServiceImpl implements WmsLeadLiftMasterService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private LeadLiftMasterRepository leadLiftMasterRepository;

    @Autowired
    ScheduleOfRateService scheduleOfRateService;

    // for creating Lead-Lift Master
    @Override
    @Transactional
    public void addLeadLiftMasterEntry(List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDtos) {

        WmsLeadLiftMasterEntity wmMasterEntity;
        ScheduleOfRateMstEntity sorEntity;
        WmsLeadLiftMasterEntityHistory entityHistory;

        for (WmsLeadLiftMasterDto flowDto : wmsLeadLiftMasterDtos) {

            wmMasterEntity = new WmsLeadLiftMasterEntity();
            BeanUtils.copyProperties(flowDto, wmMasterEntity);
            sorEntity = new ScheduleOfRateMstEntity();
            sorEntity.setSorId(flowDto.getSorId());
            wmMasterEntity.setSorMaster(sorEntity);
            wmMasterEntity.setLeLiActive(MainetConstants.MENU.Y);
            leadLiftMasterRepository.save(wmMasterEntity);
            // create history for Added Lead-Lift Master form entries
            entityHistory = new WmsLeadLiftMasterEntityHistory();
            entityHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            entityHistory.setSorId(wmMasterEntity.getSorMaster().getSorId());
            auditService.createHistory(wmMasterEntity, entityHistory);
        }
    }

    // for search Lead-Lift Master Active Records for organization
    @Override
    @Transactional(readOnly = true)
    public List<WmsLeadLiftMasterDto> searchLeadLiftDetails(Long orgId) {

        List<WmsLeadLiftMasterDto> wmLeadLiftMasterDtos;
        List<Object> dataList = leadLiftMasterRepository.getAllActiveLeadLiftRecords(orgId);
        wmLeadLiftMasterDtos = distinctRecords(dataList);
        return wmLeadLiftMasterDtos;
    }

    // for Populating Lead-Lift Master Grid
    @Override
    @Transactional(readOnly = true)
    public List<WmsLeadLiftMasterDto> toCheckLeadLiftEntry(Long sorId, String leLiFlag, Long orgId) {

        List<WmsLeadLiftMasterDto> dtoList;
        // to Get Distinct Entries For Searched Lead-Lift Master depending Upon sorId,leLiFlag & organization
        List<Object> dataList = leadLiftMasterRepository.toCheckLeadLiftEntry(sorId, leLiFlag, orgId);
        dtoList = distinctRecords(dataList);
        return dtoList;
    }

    // To get Lead-Lift Master Active Records
    @Override
    @Transactional(readOnly = true)
    public List<WmsLeadLiftMasterDto> editLeadLiftData(Long sorId, String leLiFlag, Long orgId) {
        List<WmsLeadLiftMasterEntity> masEntityList = leadLiftMasterRepository.editLeadLiftData(sorId, leLiFlag, orgId);
        List<WmsLeadLiftMasterDto> dtoList = new ArrayList<>();
        if (!masEntityList.isEmpty()) {
            masEntityList.forEach(entity -> {
                WmsLeadLiftMasterDto flowDto = new WmsLeadLiftMasterDto();
                BeanUtils.copyProperties(entity, flowDto);
                flowDto.setSorId(entity.getSorMaster().getSorId());
                dtoList.add(flowDto);
            });
        }

        return dtoList;
    }

    // to inactive Lead-Lift Master details by sor id,RateType(leLiFlag) and set active flag to "N"
    @Override
    public void inactiveLeadLiftMaster(Long sorId, String leLiFlag, Long empId) {

        leadLiftMasterRepository.inactiveLeadLiftMas(sorId, empId, leLiFlag);
    }

    // used to update Lead-Lift Master form details
    @Override
    @Transactional
    public void saveAndUpdateMaster(List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDtos, Long empId, String ids) {
        WmsLeadLiftMasterEntity wmMasterEntity;
        ScheduleOfRateMstEntity sorEntity;
        WmsLeadLiftMasterEntityHistory entityHistory;
        List<Long> removeIds = null;
        for (WmsLeadLiftMasterDto flowDto : wmsLeadLiftMasterDtos) {

            wmMasterEntity = new WmsLeadLiftMasterEntity();
            BeanUtils.copyProperties(flowDto, wmMasterEntity);
            sorEntity = new ScheduleOfRateMstEntity();
            sorEntity.setSorId(flowDto.getSorId());
            wmMasterEntity.setSorMaster(sorEntity);
            wmMasterEntity.setUpdatedBy(empId);
            wmMasterEntity.setUpdatedDate(new Date());
            wmMasterEntity.setLgIpMac(Utility.getMacAddress());
            wmMasterEntity.setLeLiActive(MainetConstants.MENU.Y);
            leadLiftMasterRepository.save(wmMasterEntity);
            // create history for updated Lead-Lift Master form entries
            entityHistory = new WmsLeadLiftMasterEntityHistory();
            entityHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            entityHistory.setSorId(wmMasterEntity.getSorMaster().getSorId());
            auditService.createHistory(wmMasterEntity, entityHistory);
        }

        if (ids != null && !ids.isEmpty()) {
            removeIds = new ArrayList<>();
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeIds.add(Long.valueOf(id));
            }
        }
        if (removeIds != null && !removeIds.isEmpty()) {
            leadLiftMasterRepository.inactiveEntityRecords(empId, removeIds);
            // create history for deleted Lead-Lift Master form entries
            Iterable<WmsLeadLiftMasterEntity> masEntityList = leadLiftMasterRepository.findAll(removeIds);
            for (WmsLeadLiftMasterEntity wmsLeadLiftMasterEntity : masEntityList) {
                entityHistory = new WmsLeadLiftMasterEntityHistory();
                entityHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                entityHistory.setSorId(wmsLeadLiftMasterEntity.getSorMaster().getSorId());
                auditService.createHistory(wmsLeadLiftMasterEntity, entityHistory);
                entityHistory.setLeLiActive(MainetConstants.N_FLAG);
            }
        }

    }

    // To Set The Properties Regarding the Grid Details
    public List<WmsLeadLiftMasterDto> distinctRecords(List<Object> dataList) {
        List<WmsLeadLiftMasterDto> dtoList = new ArrayList<>();
        WmsLeadLiftMasterDto flowDto = null;
        ScheduleOfRateMstDto scheduleOfRateMstDto;
        if (!dataList.isEmpty()) {
            for (Object data : dataList) {
                Object[] obj = (Object[]) data;
                Long Id = (Long) obj[0];
                String flag = (String) obj[1];

                flowDto = new WmsLeadLiftMasterDto();
                if (flag.equals(MainetConstants.FlagL)) {
                    flowDto.setLeLiFlag(MainetConstants.LEAD);
                } else {
                    flowDto.setLeLiFlag(MainetConstants.LIFT);
                }

                flowDto.setSorId(Id);
                scheduleOfRateMstDto = scheduleOfRateService.findSORMasterWithDetailsBySorId(Id);
                flowDto.setSorName(CommonMasterUtility.getNonHierarchicalLookUpObject(scheduleOfRateMstDto.getSorCpdId())
                        .getDescLangFirst());
                flowDto.setSorFromDate(scheduleOfRateMstDto.getSorFromDate());
                flowDto.setSorToDate(scheduleOfRateMstDto.getSorToDate());
                dtoList.add(flowDto);

            }
        }
        return dtoList;
    }

    // to check Duplicate Entries For uploaded ExcelSheet
    @Override
    public String checkDuplicateExcelData(WmsLeadLiftMasterDto dto, Long orgId, String leLiFlag, Long sorId) {
        String valid = MainetConstants.Y_FLAG;
        BigDecimal leLiFrom = dto.getLeLiFrom();
        BigDecimal leLiTo = dto.getLeLiTo();
        String slabFlag = dto.getLeLiSlabFlg();
        // to check Previous if Entries Present For uploaded ExcelSheet
        List<WmsLeadLiftMasterEntity> masEntityList = leadLiftMasterRepository.editLeadLiftData(sorId, leLiFlag, orgId);
        if (!masEntityList.isEmpty()) {
            if (slabFlag.equals(MainetConstants.Y_FLAG)) {
                for (WmsLeadLiftMasterEntity entity : masEntityList) {
                    if (entity.getLeLiFrom().compareTo(leLiFrom) == 0 || entity.getLeLiTo().compareTo(leLiTo) == 0) {
                        valid = MainetConstants.N_FLAG;
                        break;
                    }
                }
            } else {
                for (WmsLeadLiftMasterEntity entity : masEntityList) {
                    if (entity.getLeLiTo().compareTo(leLiTo) == 0) {
                        valid = MainetConstants.N_FLAG;
                        break;
                    }
                }
            }
        }
        return valid;
    }

}
