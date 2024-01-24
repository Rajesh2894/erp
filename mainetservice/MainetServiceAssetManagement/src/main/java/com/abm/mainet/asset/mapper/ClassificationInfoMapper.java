/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetClassification;
import com.abm.mainet.asset.domain.AssetClassificationRev;
import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class ClassificationInfoMapper {

    public static AssetClassification mapToEntity(AssetClassificationDTO astClassificationDto) {
        AssetClassification classificationEntity = new AssetClassification();
        AssetInformation astInfo = new AssetInformation();
        BeanUtils.copyProperties(astClassificationDto, classificationEntity);
        astInfo.setAssetId(astClassificationDto.getAssetId());
        if (astClassificationDto.getAssetClassificationId() != null) {
            classificationEntity.setAssetClassificationId(astClassificationDto.getAssetClassificationId());
        }
        classificationEntity.setFunctionalLocationCode(astClassificationDto.getFunctionalLocationCode());
        classificationEntity.setDepartment(astClassificationDto.getDepartment());
        classificationEntity.setGisId(astClassificationDto.getGisId());
        classificationEntity.setCostCenter(astClassificationDto.getCostCenter());
        classificationEntity.setLatitude(astClassificationDto.getLatitude());
        classificationEntity.setLongitude(astClassificationDto.getLongitude());
        classificationEntity.setCreatedBy(astClassificationDto.getCreatedBy());
        classificationEntity.setCreationDate(astClassificationDto.getCreationDate());
        classificationEntity.setUpdatedBy(astClassificationDto.getUpdatedBy());
        classificationEntity.setUpdatedDate(astClassificationDto.getUpdatedDate());
        classificationEntity.setLgIpMacUpd(astClassificationDto.getLgIpMacUpd());
        classificationEntity.setLgIpMac(astClassificationDto.getLgIpMac());
        classificationEntity.setAssetId(astInfo);
        classificationEntity.setLocation(astClassificationDto.getLocation());
        return classificationEntity;

    }

    public static AssetClassificationDTO mapToDTO(AssetClassification classificationEntity) {
        AssetClassificationDTO astClassificationDto = new AssetClassificationDTO();
        BeanUtils.copyProperties(classificationEntity, astClassificationDto);
        AssetInformation astInfo = classificationEntity.getAssetId();
        astClassificationDto.setFunctionalLocationCode(classificationEntity.getFunctionalLocationCode());
        astClassificationDto.setDepartment(classificationEntity.getDepartment());
        astClassificationDto.setGisId(classificationEntity.getGisId());
        astClassificationDto.setCostCenter(classificationEntity.getCostCenter());
        astClassificationDto.setLatitude(classificationEntity.getLatitude());
        astClassificationDto.setLongitude(classificationEntity.getLongitude());
        astClassificationDto.setCreatedBy(classificationEntity.getCreatedBy());
        astClassificationDto.setCreationDate(classificationEntity.getCreationDate());
        astClassificationDto.setUpdatedBy(classificationEntity.getUpdatedBy());
        astClassificationDto.setUpdatedDate(classificationEntity.getUpdatedDate());
        astClassificationDto.setLgIpMacUpd(classificationEntity.getLgIpMacUpd());
        astClassificationDto.setLgIpMac(classificationEntity.getLgIpMac());
        astClassificationDto.setLocation(classificationEntity.getLocation());
        astClassificationDto.setAssetId(astInfo.getAssetId());
        astClassificationDto.setAssetClassificationId(classificationEntity.getAssetClassificationId());
        return astClassificationDto;

    }

    /****
     * Used for Revised classification
     * 
     */

    public static AssetClassificationRev mapToEntityRev(AssetClassificationDTO astClassificationDto) {
        AssetClassificationRev classificationEntity = new AssetClassificationRev();
        AssetInformation astInfo = new AssetInformation();
        BeanUtils.copyProperties(astClassificationDto, classificationEntity);
        astInfo.setAssetId(astClassificationDto.getAssetId());
        if (astClassificationDto.getAssetClassificationId() != null) {
            classificationEntity.setAssetClassificationId(astClassificationDto.getAssetClassificationId());
        }
        classificationEntity.setFunctionalLocationCode(astClassificationDto.getFunctionalLocationCode());
        classificationEntity.setDepartment(astClassificationDto.getDepartment());
        classificationEntity.setGisId(astClassificationDto.getGisId());
        classificationEntity.setCostCenter(astClassificationDto.getCostCenter());
        classificationEntity.setLatitude(astClassificationDto.getLatitude());
        classificationEntity.setLongitude(astClassificationDto.getLongitude());
        classificationEntity.setCreatedBy(astClassificationDto.getCreatedBy());
        classificationEntity.setCreationDate(astClassificationDto.getCreationDate());
        classificationEntity.setUpdatedBy(astClassificationDto.getUpdatedBy());
        classificationEntity.setUpdatedDate(astClassificationDto.getUpdatedDate());
        classificationEntity.setLgIpMacUpd(astClassificationDto.getLgIpMacUpd());
        classificationEntity.setLgIpMac(astClassificationDto.getLgIpMac());
        classificationEntity.setLocation(astClassificationDto.getLocation());
        classificationEntity.setAssetId(astInfo);

        return classificationEntity;

    }

    public static AssetClassificationDTO mapToDTORev(AssetClassificationRev classificationRevEntity) {
        AssetClassificationDTO astClassificationDto = new AssetClassificationDTO();
        BeanUtils.copyProperties(classificationRevEntity, astClassificationDto);
        AssetInformation astInfo = classificationRevEntity.getAssetId();
        astClassificationDto.setFunctionalLocationCode(classificationRevEntity.getFunctionalLocationCode());
        astClassificationDto.setDepartment(classificationRevEntity.getDepartment());
        astClassificationDto.setGisId(classificationRevEntity.getGisId());
        astClassificationDto.setCostCenter(classificationRevEntity.getCostCenter());
        astClassificationDto.setLatitude(classificationRevEntity.getLatitude());
        astClassificationDto.setLongitude(classificationRevEntity.getLongitude());
        astClassificationDto.setCreatedBy(classificationRevEntity.getCreatedBy());
        astClassificationDto.setCreationDate(classificationRevEntity.getCreationDate());
        astClassificationDto.setUpdatedBy(classificationRevEntity.getUpdatedBy());
        astClassificationDto.setUpdatedDate(classificationRevEntity.getUpdatedDate());
        astClassificationDto.setLgIpMacUpd(classificationRevEntity.getLgIpMacUpd());
        astClassificationDto.setLgIpMac(classificationRevEntity.getLgIpMac());
        astClassificationDto.setLocation(classificationRevEntity.getLocation());
        astClassificationDto.setAssetId(astInfo.getAssetId());
        astClassificationDto.setAssetClassificationId(classificationRevEntity.getAssetClassificationId());
        return astClassificationDto;

    }

    public static AssetClassificationDTO resetClassification(AssetClassificationDTO classDTO) {
        classDTO.setFunctionalLocationCode(null);
        classDTO.setDepartment(null);
        classDTO.setGisId(null);
        classDTO.setCostCenter(null);
        classDTO.setLatitude(null);
        classDTO.setLongitude(null);
        classDTO.setCreatedBy(null);
        classDTO.setCreationDate(null);
        classDTO.setUpdatedBy(null);
        classDTO.setUpdatedDate(null);
        classDTO.setLgIpMacUpd(null);
        classDTO.setLgIpMac(null);
        classDTO.setLocation(null);
        classDTO.setSurveyNo(null);
        return classDTO;

    }
}
