/**
 * 
 */
package com.abm.mainet.asset.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetRetire;
import com.abm.mainet.asset.ui.dto.RetirementDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class RetireMapper {

    /**
     * @param dto
     * @return
     */
    public static AssetRetire mapToEntity(RetirementDTO dto) {
        AssetRetire entity = new AssetRetire();
        AssetInformation info = new AssetInformation();
        info.setAssetId(dto.getAssetId());
        if (dto.getRetireId() != null) {
            entity.setRetireId(dto.getRetireId());
        }
        entity.setAssetId(info);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreationDate(dto.getCreationDate());
        entity.setLgIpMac(dto.getLgIpMac());
        entity.setOrgId(dto.getOrgId());
        Date curDate = new Date();
        entity.setDocDate(curDate);
        entity.setPostDate(curDate);
        entity.setDispositionDate(dto.getDispositionDate());
        entity.setDispositionMethod(dto.getDispositionMethod());
        entity.setCapitalChartOfAccount(dto.getCapitalChartOfAccount());
        entity.setCapitalGain(dto.getCapitalGain());
        entity.setCapitalTax(dto.getCapitalTax());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setLgIpMacUpd(dto.getLgIpMacUpd());
        entity.setAddressToWhomSold(dto.getSoldToAddress());
        entity.setPersonToWhomSold(dto.getSoldToName());
        entity.setPayMode(dto.getPayMode());
        entity.setAmount(dto.getAmount());
        entity.setDisOrderNo(dto.getDisOrderNumber());
        entity.setNonfucDate(dto.getNonfucDate());
        entity.setRemarks(dto.getRemarks());
        entity.setDisOrderDate(dto.getDisOrderDate());
        entity.setChequeNo(dto.getChequeNo());
        entity.setChequeDate(dto.getChequeDate());
        entity.setBankId(dto.getBankId());
        entity.setDeptCode(dto.getDeptCode());
        return entity;
    }

    public static RetirementDTO mapToDTO(AssetRetire entity) {
        RetirementDTO dto = new RetirementDTO();
        AssetInformation info = entity.getAssetId();
        dto.setSerialNo(info.getAstCode());
        dto.setAssetId(info.getAssetId());
        dto.setRetireId(entity.getRetireId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreationDate(entity.getCreationDate());
        dto.setLgIpMac(entity.getLgIpMac());
        dto.setOrgId(entity.getOrgId());
        dto.setDocDate(entity.getDocDate());
        dto.setPostDate(entity.getPostDate());
        dto.setDispositionDate(entity.getDispositionDate());
        dto.setDispositionMethod(entity.getDispositionMethod());
        dto.setCapitalChartOfAccount(entity.getCapitalChartOfAccount());
        dto.setCapitalGain(entity.getCapitalGain());
        dto.setCapitalTax(entity.getCapitalTax());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setLgIpMacUpd(entity.getLgIpMacUpd());
        dto.setPayMode(entity.getPayMode());
        dto.setSoldToAddress(entity.getAddressToWhomSold());
        dto.setSoldToName(entity.getPersonToWhomSold());
        dto.setAmount(entity.getAmount());
        dto.setDisOrderNumber(entity.getDisOrderNo());
        dto.setNonfucDate(entity.getNonfucDate());
        dto.setRemarks(entity.getRemarks());
        dto.setDisOrderDate(entity.getDisOrderDate());
        dto.setChequeNo(entity.getChequeNo());
        dto.setChequeDate(entity.getChequeDate());
        dto.setBankId(entity.getBankId());
        dto.setDeptCode(entity.getDeptCode());
        return dto;

    }
}
