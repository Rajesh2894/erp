/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.asset.domain.AssetPurchaseInformationRev;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class PurchaseInfoMapper {
    public static AssetPurchaseInformation mapToEntity(AssetPurchaseInformationDTO purInfoDto) {

        AssetPurchaseInformation purInfoEntity = new AssetPurchaseInformation();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(purInfoDto.getAssetId());
        BeanUtils.copyProperties(purInfoDto, purInfoEntity);
        if (purInfoDto.getAssetPurchaserId() != null) {
            purInfoEntity.setAssetPurchaserId(purInfoDto.getAssetPurchaserId());
        }
        purInfoEntity.setFromWhomAcquired(purInfoDto.getFromWhomAcquired());
        purInfoEntity.setManufacturer(purInfoDto.getManufacturer());
        purInfoEntity.setPurchaseOrderNo(purInfoDto.getPurchaseOrderNo());
        purInfoEntity.setDateOfAcquisition(purInfoDto.getDateOfAcquisition());
        purInfoEntity.setCostOfAcquisition(purInfoDto.getCostOfAcquisition());
        purInfoEntity.setBookValue(purInfoDto.getInitialBookValue());
        purInfoEntity.setModeOfPayment(purInfoDto.getModeOfPayment());
        purInfoEntity.setCountryOfOrigin1(purInfoDto.getCountryOfOrigin1());
        purInfoEntity.setUpdatedBy(purInfoDto.getUpdatedBy());
        purInfoEntity.setUpdatedDate(purInfoDto.getUpdatedDate());
        purInfoEntity.setCreatedBy(purInfoDto.getCreatedBy());
        purInfoEntity.setCreatedDate(purInfoDto.getCreationDate());
        purInfoEntity.setUpdatedBy(purInfoDto.getUpdatedBy());
        purInfoEntity.setUpdatedDate(purInfoDto.getUpdatedDate());
        purInfoEntity.setLgIpMac(purInfoDto.getLgIpMac());
        purInfoEntity.setLgIpMacUpd(purInfoDto.getLgIpMacUpd());
        purInfoEntity.setAssetId(astInfo);
        purInfoEntity.setPurchaseOrderDate(purInfoDto.getPurchaseOrderDate());
        purInfoEntity.setAstCreationDate(purInfoDto.getAstCreationDate());
        purInfoEntity.setInitialBookDate(purInfoDto.getInitialBookDate());
        purInfoEntity.setWarrantyTillDate(purInfoDto.getWarrantyTillDate());
        return purInfoEntity;

    }

    public static AssetPurchaseInformationRev mapToEntityRev(AssetPurchaseInformationDTO purInfoRevDto) {

        AssetPurchaseInformationRev purInfoEntity = new AssetPurchaseInformationRev();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(purInfoRevDto.getAssetId());
        BeanUtils.copyProperties(purInfoRevDto, purInfoEntity);
        if (purInfoRevDto.getAssetPurchaserId() != null) {
            purInfoEntity.setAssetPurchaserId(purInfoRevDto.getAssetPurchaserId());
        }
        purInfoEntity.setFromWhomAcquired(purInfoRevDto.getFromWhomAcquired());
        purInfoEntity.setManufacturer(purInfoRevDto.getManufacturer());
        purInfoEntity.setPurchaseOrderNo(purInfoRevDto.getPurchaseOrderNo());
        purInfoEntity.setDateOfAcquisition(purInfoRevDto.getDateOfAcquisition());
        purInfoEntity.setCostOfAcquisition(purInfoRevDto.getCostOfAcquisition());
        purInfoEntity.setBookValue(purInfoRevDto.getInitialBookValue());
        purInfoEntity.setModeOfPayment(purInfoRevDto.getModeOfPayment());
        purInfoEntity.setCountryOfOrigin1(purInfoRevDto.getCountryOfOrigin1());
        purInfoEntity.setUpdatedBy(purInfoRevDto.getUpdatedBy());
        purInfoEntity.setUpdatedDate(purInfoRevDto.getUpdatedDate());
        purInfoEntity.setCreatedBy(purInfoRevDto.getCreatedBy());
        purInfoEntity.setCreatedDate(purInfoRevDto.getCreationDate());
        purInfoEntity.setUpdatedBy(purInfoRevDto.getUpdatedBy());
        purInfoEntity.setUpdatedDate(purInfoRevDto.getUpdatedDate());
        purInfoEntity.setLgIpMac(purInfoRevDto.getLgIpMac());
        purInfoEntity.setLgIpMacUpd(purInfoRevDto.getLgIpMacUpd());
        purInfoEntity.setAssetId(astInfo);
        purInfoEntity.setPurchaseOrderDate(purInfoRevDto.getPurchaseOrderDate());
        purInfoEntity.setAstCreationDate(purInfoRevDto.getAstCreationDate());
        purInfoEntity.setInitialBookDate(purInfoRevDto.getInitialBookDate());
        purInfoEntity.setWarrantyTillDate(purInfoRevDto.getWarrantyTillDate());
        return purInfoEntity;

    }

    public static AssetPurchaseInformationDTO mapToDTO(AssetPurchaseInformation purInfoEntity) {

        AssetPurchaseInformationDTO purInfoDto = new AssetPurchaseInformationDTO();
        AssetInformation astInfo = purInfoEntity.getAssetId();
        BeanUtils.copyProperties(purInfoEntity, purInfoDto);
        purInfoDto.setAssetId(astInfo.getAssetId());

        purInfoDto.setAssetPurchaserId(purInfoEntity.getAssetPurchaserId());
        purInfoDto.setFromWhomAcquired(purInfoEntity.getFromWhomAcquired());
        purInfoDto.setManufacturer(purInfoEntity.getManufacturer());
        purInfoDto.setPurchaseOrderNo(purInfoEntity.getPurchaseOrderNo());
        purInfoDto.setDateOfAcquisition(purInfoEntity.getDateOfAcquisition());
        purInfoDto.setCostOfAcquisition(purInfoEntity.getCostOfAcquisition());
        purInfoDto.setInitialBookValue(purInfoEntity.getBookValue());
        purInfoDto.setModeOfPayment(purInfoEntity.getModeOfPayment());
        purInfoDto.setCountryOfOrigin1(purInfoEntity.getCountryOfOrigin1());
        purInfoDto.setUpdatedBy(purInfoEntity.getUpdatedBy());
        purInfoDto.setUpdatedDate(purInfoEntity.getUpdatedDate());
        purInfoDto.setCreatedBy(purInfoEntity.getCreatedBy());
        purInfoDto.setCreationDate(purInfoEntity.getCreatedDate());
        purInfoDto.setUpdatedBy(purInfoEntity.getUpdatedBy());
        purInfoDto.setUpdatedDate(purInfoEntity.getUpdatedDate());
        purInfoDto.setLgIpMac(purInfoEntity.getLgIpMac());
        purInfoDto.setLgIpMacUpd(purInfoEntity.getLgIpMacUpd());
        purInfoDto.setPurchaseOrderDate(purInfoEntity.getPurchaseOrderDate());
        purInfoDto.setAstCreationDate(purInfoEntity.getAstCreationDate());
        purInfoDto.setInitialBookDate(purInfoEntity.getInitialBookDate());
        purInfoDto.setWarrantyTillDate(purInfoEntity.getWarrantyTillDate());
        return purInfoDto;

    }

    // Rev
    public static AssetPurchaseInformationDTO mapToDTORev(AssetPurchaseInformationRev purInfoRevEntity) {

        AssetPurchaseInformationDTO purInfoDto = new AssetPurchaseInformationDTO();
        AssetInformation astInfo = purInfoRevEntity.getAssetId();
        BeanUtils.copyProperties(purInfoRevEntity, purInfoDto);
        purInfoDto.setAssetId(astInfo.getAssetId());
        purInfoDto.setAssetPurchaserId(purInfoRevEntity.getAssetPurchaserId());
        purInfoDto.setFromWhomAcquired(purInfoRevEntity.getFromWhomAcquired());
        purInfoDto.setManufacturer(purInfoRevEntity.getManufacturer());
        purInfoDto.setPurchaseOrderNo(purInfoRevEntity.getPurchaseOrderNo());
        purInfoDto.setDateOfAcquisition(purInfoRevEntity.getDateOfAcquisition());
        purInfoDto.setCostOfAcquisition(purInfoRevEntity.getCostOfAcquisition());
        purInfoDto.setInitialBookValue(purInfoRevEntity.getBookValue());
        purInfoDto.setModeOfPayment(purInfoRevEntity.getModeOfPayment());
        purInfoDto.setCountryOfOrigin1(purInfoRevEntity.getCountryOfOrigin1());
        purInfoDto.setUpdatedBy(purInfoRevEntity.getUpdatedBy());
        purInfoDto.setUpdatedDate(purInfoRevEntity.getUpdatedDate());
        purInfoDto.setCreatedBy(purInfoRevEntity.getCreatedBy());
        purInfoDto.setCreationDate(purInfoRevEntity.getCreatedDate());
        purInfoDto.setUpdatedBy(purInfoRevEntity.getUpdatedBy());
        purInfoDto.setUpdatedDate(purInfoRevEntity.getUpdatedDate());
        purInfoDto.setLgIpMac(purInfoRevEntity.getLgIpMac());
        purInfoDto.setLgIpMacUpd(purInfoRevEntity.getLgIpMacUpd());
        purInfoDto.setPurchaseOrderDate(purInfoRevEntity.getPurchaseOrderDate());
        purInfoDto.setAstCreationDate(purInfoRevEntity.getAstCreationDate());
        purInfoDto.setInitialBookDate(purInfoRevEntity.getInitialBookDate());
        purInfoDto.setWarrantyTillDate(purInfoRevEntity.getWarrantyTillDate());
        return purInfoDto;

    }

    public static AssetPurchaseInformationDTO resetPurchaser(AssetPurchaseInformationDTO purchaseDTO) {
        purchaseDTO.setFromWhomAcquired(null);
        purchaseDTO.setManufacturer(null);
        purchaseDTO.setPurchaseOrderNo(null);
        purchaseDTO.setDateOfAcquisition(null);
        purchaseDTO.setCostOfAcquisition(null);
        purchaseDTO.setInitialBookValue(null);
        purchaseDTO.setModeOfPayment(null);
        purchaseDTO.setCountryOfOrigin1(null);
        purchaseDTO.setUpdatedBy(null);
        purchaseDTO.setUpdatedDate(null);
        purchaseDTO.setCreatedBy(null);
        purchaseDTO.setCreationDate(null);
        purchaseDTO.setUpdatedBy(null);
        purchaseDTO.setUpdatedDate(null);
        purchaseDTO.setLgIpMac(null);
        purchaseDTO.setLgIpMacUpd(null);
        purchaseDTO.setPurchaseOrderDate(null);
        purchaseDTO.setAstCreationDate(null);
        purchaseDTO.setInitialBookDate(null);
        purchaseDTO.setWarrantyTillDate(null);
        purchaseDTO.setLicenseNo(null);
        purchaseDTO.setLicenseDate(null);
        return purchaseDTO;
    }
}
