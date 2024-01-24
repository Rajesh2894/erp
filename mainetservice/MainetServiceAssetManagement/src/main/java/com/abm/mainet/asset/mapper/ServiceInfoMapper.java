/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetRealEstateRev;
import com.abm.mainet.asset.domain.AssetServiceInformation;
import com.abm.mainet.asset.domain.AssetServiceInformationRev;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class ServiceInfoMapper {

    public static AssetServiceInformation mapToServiceEntity(AssetServiceInformationDTO serviceInfoDto) {

        AssetServiceInformation astServInfoEntity = new AssetServiceInformation();
        BeanUtils.copyProperties(serviceInfoDto, astServInfoEntity);
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(serviceInfoDto.getAssetId());
        if (serviceInfoDto.getAssetServiceId() != null) {
            astServInfoEntity.setAssetServiceId(serviceInfoDto.getAssetServiceId());
        }
        astServInfoEntity.setServiceNo(serviceInfoDto.getServiceNo());
        astServInfoEntity.setServiceProvider(serviceInfoDto.getServiceProvider());
        astServInfoEntity.setServiceStartDate(serviceInfoDto.getServiceStartDate());
        astServInfoEntity.setServiceExpiryDate(serviceInfoDto.getServiceExpiryDate());
        astServInfoEntity.setServiceAmount(serviceInfoDto.getAmount());
        astServInfoEntity.setWarrenty(serviceInfoDto.getWarrenty());
        astServInfoEntity.setCostCenter(serviceInfoDto.getCostCenter());
        astServInfoEntity.setServiceContent(serviceInfoDto.getServiceContent());
        astServInfoEntity.setServiceDescription(serviceInfoDto.getServiceDescription());
        astServInfoEntity.setCreatedBy(serviceInfoDto.getCreatedBy());
        astServInfoEntity.setCreatedDate(serviceInfoDto.getCreationDate());
        astServInfoEntity.setUpdatedBy(serviceInfoDto.getUpdatedBy());
        astServInfoEntity.setLgIpMac(serviceInfoDto.getLgIpMac());
        astServInfoEntity.setUpdatedDate(serviceInfoDto.getUpdatedDate());
        astServInfoEntity.setLgIpMacUpd(serviceInfoDto.getLgIpMacUpd());
        // mapToRealEstateEntity(serviceInfoDto.getAssetRealEstateInfoDTO(), astServInfoEntity);
        astServInfoEntity.setAssetId(astInfo);
        return astServInfoEntity;

    }

    public static AssetServiceInformationRev mapToServiceEntityRev(AssetServiceInformationDTO serviceInfoDto) {

        AssetServiceInformationRev astServInfoEntity = new AssetServiceInformationRev();
        BeanUtils.copyProperties(serviceInfoDto, astServInfoEntity);
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(serviceInfoDto.getAssetId());
        if (serviceInfoDto.getAssetServiceId() != null) {
            astServInfoEntity.setAssetServiceId(serviceInfoDto.getAssetServiceId());
        }
        astServInfoEntity.setServiceNo(serviceInfoDto.getServiceNo());
        astServInfoEntity.setServiceProvider(serviceInfoDto.getServiceProvider());
        astServInfoEntity.setServiceStartDate(serviceInfoDto.getServiceStartDate());
        astServInfoEntity.setServiceExpiryDate(serviceInfoDto.getServiceExpiryDate());
        astServInfoEntity.setServiceAmount(serviceInfoDto.getAmount());
        astServInfoEntity.setWarrenty(serviceInfoDto.getWarrenty());
        astServInfoEntity.setCostCenter(serviceInfoDto.getCostCenter());
        astServInfoEntity.setServiceContent(serviceInfoDto.getServiceContent());
        astServInfoEntity.setServiceDescription(serviceInfoDto.getServiceDescription());
        astServInfoEntity.setCreatedBy(serviceInfoDto.getCreatedBy());
        astServInfoEntity.setCreatedDate(serviceInfoDto.getCreationDate());
        astServInfoEntity.setUpdatedBy(serviceInfoDto.getUpdatedBy());
        astServInfoEntity.setLgIpMac(serviceInfoDto.getLgIpMac());
        astServInfoEntity.setUpdatedDate(serviceInfoDto.getUpdatedDate());
        astServInfoEntity.setLgIpMacUpd(serviceInfoDto.getLgIpMacUpd());
        astServInfoEntity.setRevGroupId(serviceInfoDto.getRevGroupId());
        astServInfoEntity.setRevGrpIdentity(serviceInfoDto.getRevGrpIdentity());
        // mapToRealEstateEntityRev(serviceInfoDto.getAssetRealEstateInfoDTO(), astServInfoEntity);
        astServInfoEntity.setAssetId(astInfo);
        return astServInfoEntity;

    }

    /*
     * public static void mapToRealEstateEntity(AssetRealEstateInformationDTO astRealEstDto, AssetServiceInformation
     * astServInfoEntity) { if (null != astRealEstDto) { astServInfoEntity.setAssessmentNo(astRealEstDto.getAssessmentNo());
     * astServInfoEntity.setPropertyRegistrationNo(astRealEstDto.getPropertyRegistrationNo());
     * astServInfoEntity.setTaxCode(astRealEstDto.getTaxCode());
     * astServInfoEntity.setRealEstateAmount(astRealEstDto.getRealEstAmount());
     * astServInfoEntity.setTaxZoneLocation(astRealEstDto.getTaxZoneLocation());
     * astServInfoEntity.setMunicipalityName(astRealEstDto.getMunicipalityName()); } }
     */
    public static AssetRealEstateRev mapToRealEstateEntityRev(AssetRealEstateInformationDTO astRealEstDto) {

        AssetRealEstateRev astRealEstateEntity = new AssetRealEstateRev();
        BeanUtils.copyProperties(astRealEstDto, astRealEstateEntity);
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(astRealEstDto.getAssetId());

        if (null != astRealEstDto) {
            astRealEstateEntity.setAssessmentNo(astRealEstDto.getAssessmentNo());
            astRealEstateEntity.setPropertyRegistrationNo(astRealEstDto.getPropertyRegistrationNo());
            astRealEstateEntity.setTaxCode(astRealEstDto.getTaxCode());
            astRealEstateEntity.setRealEstateAmount(astRealEstDto.getRealEstAmount());
            astRealEstateEntity.setTaxZoneLocation(astRealEstDto.getTaxZoneLocation());
            astRealEstateEntity.setMunicipalityName(astRealEstDto.getMunicipalityName());
            astRealEstateEntity.setAssetId(astInfo);
        }

        return astRealEstateEntity;
    }

    public static AssetServiceInformationDTO mapToServiceDTO(AssetServiceInformation astServInfoEntity) {

        AssetServiceInformationDTO serviceInfoDto = new AssetServiceInformationDTO();
        AssetRealEstateInformationDTO realEstDTO = new AssetRealEstateInformationDTO();
        BeanUtils.copyProperties(astServInfoEntity, serviceInfoDto);
        AssetInformation astInfo = astServInfoEntity.getAssetId();
        serviceInfoDto.setAssetRealEstateInfoDTO(realEstDTO);
        serviceInfoDto.setAssetId(astInfo.getAssetId());
        serviceInfoDto.setAssetServiceId(astServInfoEntity.getAssetServiceId());
        serviceInfoDto.setServiceNo(astServInfoEntity.getServiceNo());
        serviceInfoDto.setServiceProvider(astServInfoEntity.getServiceProvider());
        serviceInfoDto.setServiceStartDate(astServInfoEntity.getServiceStartDate());
        serviceInfoDto.setServiceExpiryDate(astServInfoEntity.getServiceExpiryDate());
        serviceInfoDto.setAmount(astServInfoEntity.getServiceAmount());
        serviceInfoDto.setWarrenty(astServInfoEntity.getWarrenty());
        serviceInfoDto.setCostCenter(astServInfoEntity.getCostCenter());
        serviceInfoDto.setServiceContent(astServInfoEntity.getServiceContent());
        serviceInfoDto.setServiceDescription(astServInfoEntity.getServiceDescription());
        serviceInfoDto.setCreatedBy(astServInfoEntity.getCreatedBy());
        serviceInfoDto.setCreationDate(astServInfoEntity.getCreatedDate());
        serviceInfoDto.setUpdatedBy(astServInfoEntity.getUpdatedBy());
        serviceInfoDto.setLgIpMac(astServInfoEntity.getLgIpMac());
        serviceInfoDto.setUpdatedDate(astServInfoEntity.getUpdatedDate());
        serviceInfoDto.setLgIpMacUpd(astServInfoEntity.getLgIpMacUpd());
        // mapToRealEstateDTO(astServInfoEntity, serviceInfoDto.getAssetRealEstateInfoDTO());
        return serviceInfoDto;

    }

    public static AssetServiceInformationDTO mapToServiceDTORev(AssetServiceInformationRev astServInfoEntity) {

        AssetServiceInformationDTO serviceInfoDto = new AssetServiceInformationDTO();
        BeanUtils.copyProperties(astServInfoEntity, serviceInfoDto);
        AssetRealEstateInformationDTO realEstDTO = new AssetRealEstateInformationDTO();
        AssetInformation astInfo = astServInfoEntity.getAssetId();
        serviceInfoDto.setAssetRealEstateInfoDTO(realEstDTO);
        serviceInfoDto.setAssetId(astInfo.getAssetId());
        serviceInfoDto.setAssetServiceId(astServInfoEntity.getAssetServiceId());
        serviceInfoDto.setServiceNo(astServInfoEntity.getServiceNo());
        serviceInfoDto.setServiceProvider(astServInfoEntity.getServiceProvider());
        serviceInfoDto.setServiceStartDate(astServInfoEntity.getServiceStartDate());
        serviceInfoDto.setServiceExpiryDate(astServInfoEntity.getServiceExpiryDate());
        serviceInfoDto.setAmount(astServInfoEntity.getServiceAmount());
        serviceInfoDto.setWarrenty(astServInfoEntity.getWarrenty());
        serviceInfoDto.setCostCenter(astServInfoEntity.getCostCenter());
        serviceInfoDto.setServiceContent(astServInfoEntity.getServiceContent());
        serviceInfoDto.setServiceDescription(astServInfoEntity.getServiceDescription());
        serviceInfoDto.setCreatedBy(astServInfoEntity.getCreatedBy());
        serviceInfoDto.setCreationDate(astServInfoEntity.getCreatedDate());
        serviceInfoDto.setUpdatedBy(astServInfoEntity.getUpdatedBy());
        serviceInfoDto.setLgIpMac(astServInfoEntity.getLgIpMac());
        serviceInfoDto.setUpdatedDate(astServInfoEntity.getUpdatedDate());
        serviceInfoDto.setLgIpMacUpd(astServInfoEntity.getLgIpMacUpd());
        mapToRealEstateDTORev(astServInfoEntity, serviceInfoDto.getAssetRealEstateInfoDTO());
        return serviceInfoDto;

    }

    /*
     * public static void mapToRealEstateDTO(AssetServiceInformation astServInfoEntity, AssetRealEstateInformationDTO
     * astRealEstDto) { astRealEstDto.setAssessmentNo(astServInfoEntity.getAssessmentNo());
     * astRealEstDto.setPropertyRegistrationNo(astServInfoEntity.getPropertyRegistrationNo());
     * astRealEstDto.setTaxCode(astServInfoEntity.getTaxCode());
     * astRealEstDto.setRealEstAmount(astServInfoEntity.getRealEstateAmount());
     * astRealEstDto.setTaxZoneLocation(astServInfoEntity.getTaxZoneLocation());
     * astRealEstDto.setMunicipalityName(astServInfoEntity.getMunicipalityName()); }
     */

    public static void mapToRealEstateDTORev(AssetServiceInformationRev astServInfoEntity,
            AssetRealEstateInformationDTO astRealEstDto) {
        // astRealEstDto.setAssessmentNo(astServInfoEntity.getAssessmentNo());
        // astRealEstDto.setPropertyRegistrationNo(astServInfoEntity.getPropertyRegistrationNo());
        // astRealEstDto.setTaxCode(astServInfoEntity.getTaxCode());
        astRealEstDto.setRealEstAmount(astServInfoEntity.getRealEstateAmount());
        astRealEstDto.setTaxZoneLocation(astServInfoEntity.getTaxZoneLocation());
        astRealEstDto.setMunicipalityName(astServInfoEntity.getMunicipalityName());

    }

    public static AssetServiceInformationDTO resetServiceInformation(AssetServiceInformationDTO serviceDTO) {
        serviceDTO.setServiceNo(null);
        serviceDTO.setServiceProvider(null);
        serviceDTO.setServiceStartDate(null);
        serviceDTO.setServiceExpiryDate(null);
        serviceDTO.setAmount(null);
        serviceDTO.setWarrenty(null);
        serviceDTO.setCostCenter(null);
        serviceDTO.setServiceContent(null);
        serviceDTO.setServiceDescription(null);
        serviceDTO.setCreatedBy(null);
        serviceDTO.setCreationDate(null);
        serviceDTO.setUpdatedBy(null);
        serviceDTO.setLgIpMac(null);
        serviceDTO.setUpdatedDate(null);
        serviceDTO.setLgIpMacUpd(null);
        serviceDTO.setManCatId(null);
        serviceDTO.setManTypeId(null);
        serviceDTO.setManDate(null);
        resetRealEstateInfo(serviceDTO.getAssetRealEstateInfoDTO());
        return serviceDTO;
    }

    public static void resetRealEstateInfo(AssetRealEstateInformationDTO estateDTO) {
        estateDTO.setAssessmentNo(null);
        estateDTO.setPropertyRegistrationNo(null);
        estateDTO.setTaxCode(null);
        estateDTO.setRealEstAmount(null);
        estateDTO.setTaxZoneLocation(null);
        estateDTO.setMunicipalityName(null);
    }
}
