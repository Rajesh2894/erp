/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetValuationDetails;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class EvaluationMapper {

    /**
     * @param dto
     * @return
     */
    public static AssetValuationDetails mapToEntity(AssetValuationDetailsDTO dto) {
        AssetValuationDetails entity = new AssetValuationDetails();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getValuationDetId() != null) {
            entity.setValuationDetId(dto.getValuationDetId());
        }
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreationDate(dto.getCreationDate());
        entity.setLgIpMac(dto.getLgIpMac());
        entity.setOrgId(dto.getOrgId());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setLgIpMacUpd(dto.getLgIpMacUpd());
        entity.setAccumDeprValue(dto.getAccumDeprValue());
        entity.setAssetId(dto.getAssetId());
        entity.setBookDate(dto.getPreviousBookDate());
        entity.setBookValue(dto.getPreviousBookValue());
        entity.setBookEndValue(dto.getCurrentBookValue());
        entity.setBookEndDate(dto.getCurrentBookDate());
        entity.setDeprValue(dto.getDeprValue());
        entity.setBookFinYear(dto.getBookFinYear());
        entity.setGroupId(dto.getGroupId());
        entity.setChangetype(dto.getChangetype());
        entity.setAdditionalCost(dto.getAddCost());
        entity.setBatchNo(dto.getBatchNo());
        return entity;
    }

    public static AssetValuationDetailsDTO mapToDTO(AssetValuationDetails entity) {
        AssetValuationDetailsDTO dto = new AssetValuationDetailsDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setValuationDetId(entity.getValuationDetId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreationDate(entity.getCreationDate());
        dto.setLgIpMac(entity.getLgIpMac());
        dto.setOrgId(entity.getOrgId());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setLgIpMacUpd(entity.getLgIpMacUpd());
        dto.setAccumDeprValue(entity.getAccumDeprValue());
        dto.setAssetId(entity.getAssetId());
        dto.setPreviousBookDate(entity.getBookDate());
        dto.setPreviousBookValue(entity.getBookValue());
        dto.setCurrentBookValue(entity.getBookEndValue());
        dto.setCurrentBookDate(entity.getBookEndDate());
        dto.setDeprValue(entity.getDeprValue());
        dto.setBookFinYear(entity.getBookFinYear());
        dto.setGroupId(entity.getGroupId());
        dto.setChangetype(entity.getChangetype());
        dto.setAddCost(entity.getAdditionalCost());
        dto.setBatchNo(entity.getBatchNo());
        return dto;

    }

}
