/**
 * 
 */
package com.abm.mainet.asset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetInsuranceDetails;
import com.abm.mainet.asset.domain.AssetInsuranceDetailsRev;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class InsuranceServiceMapper {

    public static AssetInsuranceDetails mapToEntity(AssetInsuranceDetailsDTO assetInsuranceDto) {
        AssetInsuranceDetails insuranceEntity = new AssetInsuranceDetails();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(assetInsuranceDto.getAssetId());
        if (assetInsuranceDto.getAssetInsuranceId() != null) {
            insuranceEntity.setAssetInsuranceId(assetInsuranceDto.getAssetInsuranceId());
        }
        insuranceEntity.setInsuranceNo(assetInsuranceDto.getInsuranceNo());
        insuranceEntity.setInsuranceProvider(assetInsuranceDto.getInsuranceProvider());
        insuranceEntity.setTypeOfInsurance(assetInsuranceDto.getTypeOfInsurance());
        insuranceEntity.setInsuranceRate(assetInsuranceDto.getInsuranceRate());
        insuranceEntity.setInsuranceAmount(assetInsuranceDto.getInsuranceAmount());
        insuranceEntity.setPremiumFrequency(assetInsuranceDto.getPremiumFrequency());
        insuranceEntity.setPremiumValue(assetInsuranceDto.getPremiumValue());
        insuranceEntity.setStatus(assetInsuranceDto.getStatus());
        insuranceEntity.setInsuranceStartDate(assetInsuranceDto.getInsuranceStartDate());
        insuranceEntity.setInsuranceEndDate(assetInsuranceDto.getInsuranceEndDate());
        insuranceEntity.setCostCenter(assetInsuranceDto.getCostCenter());
        insuranceEntity.setRemark(assetInsuranceDto.getRemark());
        insuranceEntity.setCreatedBy(assetInsuranceDto.getCreatedBy());
        insuranceEntity.setCreatedDate(assetInsuranceDto.getCreationDate());
        insuranceEntity.setUpdatedBy(assetInsuranceDto.getUpdatedBy());
        insuranceEntity.setUpdatedDate(assetInsuranceDto.getUpdatedDate());
        insuranceEntity.setLgIpMac(assetInsuranceDto.getLgIpMac());
        insuranceEntity.setLgIpMacUpd(assetInsuranceDto.getLgIpMacUpd());
        insuranceEntity.setAssetId(astInfo);
        return insuranceEntity;

    }

    public static AssetInsuranceDetailsRev mapToEntityRev(AssetInsuranceDetailsDTO assetInsuranceDto) {
        AssetInsuranceDetailsRev insuranceEntity = new AssetInsuranceDetailsRev();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(assetInsuranceDto.getAssetId());
        if (assetInsuranceDto.getAssetInsuranceId() != null) {
            insuranceEntity.setAssetInsuranceId(assetInsuranceDto.getAssetInsuranceId());
        }
        insuranceEntity.setInsuranceNo(assetInsuranceDto.getInsuranceNo());
        insuranceEntity.setInsuranceProvider(assetInsuranceDto.getInsuranceProvider());
        insuranceEntity.setTypeOfInsurance(assetInsuranceDto.getTypeOfInsurance());
        insuranceEntity.setInsuranceRate(assetInsuranceDto.getInsuranceRate());
        insuranceEntity.setInsuranceAmount(assetInsuranceDto.getInsuranceAmount());
        insuranceEntity.setPremiumFrequency(assetInsuranceDto.getPremiumFrequency());
        insuranceEntity.setPremiumValue(assetInsuranceDto.getPremiumValue());
        insuranceEntity.setStatus(assetInsuranceDto.getStatus());
        insuranceEntity.setInsuranceStartDate(assetInsuranceDto.getInsuranceStartDate());
        insuranceEntity.setInsuranceEndDate(assetInsuranceDto.getInsuranceEndDate());
        insuranceEntity.setCostCenter(assetInsuranceDto.getCostCenter());
        insuranceEntity.setRemark(assetInsuranceDto.getRemark());
        insuranceEntity.setCreatedBy(assetInsuranceDto.getCreatedBy());
        insuranceEntity.setCreatedDate(assetInsuranceDto.getCreationDate());
        insuranceEntity.setUpdatedBy(assetInsuranceDto.getUpdatedBy());
        insuranceEntity.setUpdatedDate(assetInsuranceDto.getUpdatedDate());
        insuranceEntity.setLgIpMac(assetInsuranceDto.getLgIpMac());
        insuranceEntity.setLgIpMacUpd(assetInsuranceDto.getLgIpMacUpd());
        insuranceEntity.setAssetId(astInfo);
        insuranceEntity.setRevGrpId(assetInsuranceDto.getRevGrpId());
        insuranceEntity.setRevGrpIdentity(assetInsuranceDto.getRevGrpIdentity());
        return insuranceEntity;

    }

    public static AssetInsuranceDetailsDTO mapToDTO(AssetInsuranceDetails insuranceEntity) {
        AssetInsuranceDetailsDTO assetInsuranceDto = new AssetInsuranceDetailsDTO();
        AssetInformation astInfo = insuranceEntity.getAssetId();
        assetInsuranceDto.setInsuranceNo(insuranceEntity.getInsuranceNo());
        assetInsuranceDto.setInsuranceProvider(insuranceEntity.getInsuranceProvider());
        assetInsuranceDto.setTypeOfInsurance(insuranceEntity.getTypeOfInsurance());
        assetInsuranceDto.setInsuranceRate(insuranceEntity.getInsuranceRate());
        assetInsuranceDto.setInsuranceAmount(insuranceEntity.getInsuranceAmount());
        assetInsuranceDto.setPremiumFrequency(insuranceEntity.getPremiumFrequency());
        assetInsuranceDto.setPremiumValue(insuranceEntity.getPremiumValue());
        assetInsuranceDto.setStatus(insuranceEntity.getStatus());
        assetInsuranceDto.setInsuranceStartDate(insuranceEntity.getInsuranceStartDate());
        assetInsuranceDto.setInsuranceEndDate(insuranceEntity.getInsuranceEndDate());
        assetInsuranceDto.setCostCenter(insuranceEntity.getCostCenter());
        assetInsuranceDto.setRemark(insuranceEntity.getRemark());
        assetInsuranceDto.setCreatedBy(insuranceEntity.getCreatedBy());
        assetInsuranceDto.setCreationDate(insuranceEntity.getCreatedDate());
        assetInsuranceDto.setUpdatedBy(insuranceEntity.getUpdatedBy());
        assetInsuranceDto.setUpdatedDate(insuranceEntity.getUpdatedDate());
        assetInsuranceDto.setLgIpMac(insuranceEntity.getLgIpMac());
        assetInsuranceDto.setLgIpMacUpd(insuranceEntity.getLgIpMacUpd());
        assetInsuranceDto.setAssetId(astInfo.getAssetId());
        assetInsuranceDto.setAssetInsuranceId(insuranceEntity.getAssetInsuranceId());
        return assetInsuranceDto;

    }

    public static AssetInsuranceDetailsDTO mapToDTORev(AssetInsuranceDetailsRev insuranceEntity) {
        AssetInsuranceDetailsDTO assetInsuranceDto = new AssetInsuranceDetailsDTO();
        AssetInformation astInfo = insuranceEntity.getAssetId();
        assetInsuranceDto.setInsuranceNo(insuranceEntity.getInsuranceNo());
        assetInsuranceDto.setInsuranceProvider(insuranceEntity.getInsuranceProvider());
        assetInsuranceDto.setTypeOfInsurance(insuranceEntity.getTypeOfInsurance());
        assetInsuranceDto.setInsuranceRate(insuranceEntity.getInsuranceRate());
        assetInsuranceDto.setInsuranceAmount(insuranceEntity.getInsuranceAmount());
        assetInsuranceDto.setPremiumFrequency(insuranceEntity.getPremiumFrequency());
        assetInsuranceDto.setPremiumValue(insuranceEntity.getPremiumValue());
        assetInsuranceDto.setStatus(insuranceEntity.getStatus());
        assetInsuranceDto.setInsuranceStartDate(insuranceEntity.getInsuranceStartDate());
        assetInsuranceDto.setInsuranceEndDate(insuranceEntity.getInsuranceEndDate());
        assetInsuranceDto.setCostCenter(insuranceEntity.getCostCenter());
        assetInsuranceDto.setRemark(insuranceEntity.getRemark());
        assetInsuranceDto.setCreatedBy(insuranceEntity.getCreatedBy());
        assetInsuranceDto.setCreationDate(insuranceEntity.getCreatedDate());
        assetInsuranceDto.setUpdatedBy(insuranceEntity.getUpdatedBy());
        assetInsuranceDto.setUpdatedDate(insuranceEntity.getUpdatedDate());
        assetInsuranceDto.setLgIpMac(insuranceEntity.getLgIpMac());
        assetInsuranceDto.setLgIpMacUpd(insuranceEntity.getLgIpMacUpd());
        assetInsuranceDto.setAssetId(astInfo.getAssetId());
        assetInsuranceDto.setAssetInsuranceId(insuranceEntity.getAssetInsuranceId());
        assetInsuranceDto.setRevGrpId(insuranceEntity.getRevGrpId());
        assetInsuranceDto.setRevGrpIdentity(insuranceEntity.getRevGrpIdentity());
        return assetInsuranceDto;

    }

    public static AssetInsuranceDetailsDTO resetInsurance(AssetInsuranceDetailsDTO insuranceDTO) {
        insuranceDTO.setInsuranceNo(null);
        insuranceDTO.setInsuranceProvider(null);
        insuranceDTO.setTypeOfInsurance(null);
        insuranceDTO.setInsuranceRate(null);
        insuranceDTO.setInsuranceAmount(null);
        insuranceDTO.setPremiumFrequency(null);
        insuranceDTO.setPremiumValue(null);
        insuranceDTO.setStatus(null);
        insuranceDTO.setInsuranceStartDate(null);
        insuranceDTO.setInsuranceEndDate(null);
        insuranceDTO.setCostCenter(null);
        insuranceDTO.setRemark(null);
        insuranceDTO.setCreatedBy(null);
        insuranceDTO.setCreationDate(null);
        insuranceDTO.setUpdatedBy(null);
        insuranceDTO.setUpdatedDate(null);
        insuranceDTO.setLgIpMac(null);
        insuranceDTO.setLgIpMacUpd(null);
        return insuranceDTO;
    }

    public static List<AssetInsuranceDetailsDTO> mapToDTOList(List<AssetInsuranceDetails> entityList) {
        List<AssetInsuranceDetailsDTO> astInsuDTOList = new ArrayList<AssetInsuranceDetailsDTO>();

        for (int i = 0; i < entityList.size(); i++) {
            AssetInsuranceDetailsDTO dto = mapToDTO(entityList.get(i));
            astInsuDTOList.add(dto);
        }

        return astInsuDTOList;
    }

    public static List<AssetInsuranceDetailsDTO> mapToDTOListRev(List<AssetInsuranceDetailsRev> entityList) {
        List<AssetInsuranceDetailsDTO> astInsuDTOList = new ArrayList<AssetInsuranceDetailsDTO>();

        for (int i = 0; i < entityList.size(); i++) {
            AssetInsuranceDetailsDTO dto = mapToDTORev(entityList.get(i));
            astInsuDTOList.add(dto);
        }

        return astInsuDTOList;
    }

}
