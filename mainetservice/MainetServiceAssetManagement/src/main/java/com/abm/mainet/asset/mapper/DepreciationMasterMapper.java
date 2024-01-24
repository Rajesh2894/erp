package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.ChartOfDepreciationMasterEntity;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;

@Component
public class DepreciationMasterMapper {

    public static ChartOfDepreciationMasterEntity mapToEntity(ChartOfDepreciationMasterDTO dto) {
        ChartOfDepreciationMasterEntity entity = new ChartOfDepreciationMasterEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setAccountCode(dto.getAccountCode());
        entity.setAssetClass(dto.getAssetClass());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setDepreciationKey(dto.getDepreciationKey());
        entity.setFrequency(dto.getFrequency());
        entity.setLgIpMac(dto.getLgIpMac());
        entity.setLgIpMacUpd(dto.getLgIpMacUpd());
        entity.setName(dto.getName());
        entity.setRemark(dto.getRemark());
        entity.setRate(dto.getRate());
        entity.setOrgId(dto.getOrgId());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        if (dto.getGroupId() != null) {
            entity.setGroupId(dto.getGroupId());
        }
        return entity;
    }

    public static ChartOfDepreciationMasterDTO mapToDTO(ChartOfDepreciationMasterEntity entity) {
        ChartOfDepreciationMasterDTO dto = new ChartOfDepreciationMasterDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setGroupId(entity.getGroupId());
        dto.setAccountCode(entity.getAccountCode());
        dto.setAssetClass(entity.getAssetClass());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDepreciationKey(entity.getDepreciationKey());
        dto.setFrequency(entity.getFrequency());
        dto.setLgIpMac(entity.getLgIpMac());
        dto.setLgIpMacUpd(entity.getLgIpMacUpd());
        dto.setName(entity.getName());
        dto.setRemark(entity.getRemark());
        dto.setRate(entity.getRate());
        dto.setOrgId(entity.getOrgId());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}
