/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;

import com.abm.mainet.asset.ui.dto.AssetRegisterUploadDto;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterUploadDto;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;

/**
 * @author satish.rathore
 *
 */
public class AssetExcelUploadMapper {

    public static AssetInformationDTO mapUploadToInfo(AssetRegisterUploadDto dto) {
        AssetInformationDTO astInfoDto = new AssetInformationDTO();
        BeanUtils.copyProperties(dto, astInfoDto);
        astInfoDto.setAssetName(dto.getAssetName().trim());
        astInfoDto.setSerialNo(dto.getSerialNo().trim());
        astInfoDto.setDetails(dto.getDiscription().trim());
        astInfoDto.setNoOfSimilarAsset(dto.getNoOfUnit());
        astInfoDto.setPurpose(dto.getPurpose().trim());
        astInfoDto.setAssetModelIdentifier(dto.getModelIdent());
        astInfoDto.setAcquisitionMethod(dto.getAcquisiMethodId());
        astInfoDto.setAssetClass1(dto.getAssetClassId());
        astInfoDto.setAssetClass2(dto.getAssetClass2Id());
        astInfoDto.setAssetGroup(dto.getAssetGroupId());
        astInfoDto.setAssetType(dto.getAssetTypeId());
        astInfoDto.setAssetStatus(dto.getAssetStatus());
        astInfoDto.setAstCode(dto.getGeneratedAstCode());
        astInfoDto.setAppovalStatus(dto.getAssetAppStatus());
        astInfoDto.setAssetSpecificationDTO(mapUploadToSpeci(dto));
        return astInfoDto;

    }
    
    public static AssetInformationDTO mapITAssetUploadToInfo(ITAssetRegisterUploadDto dto) {
        AssetInformationDTO astInfoDto = new AssetInformationDTO();
        BeanUtils.copyProperties(dto, astInfoDto);
        astInfoDto.setSerialNo((dto.getSerialNo()!=null) ? dto.getSerialNo().trim() : dto.getSerialNo());
        astInfoDto.setAssetModelIdentifier(dto.getAssetModelIdentifier());
        astInfoDto.setAcquisitionMethod(dto.getAcquisiMethodId());
        astInfoDto.setAssetClass1(dto.getAssetClassId());
        astInfoDto.setAssetClass2(dto.getAssetClass2Id());
        astInfoDto.setAssetStatus(dto.getAssetStatus());
        astInfoDto.setAstCode(dto.getGeneratedAstCode());
        astInfoDto.setAppovalStatus(dto.getAssetAppStatus());
        astInfoDto.setRamSize(dto.getRamSizeId());
        astInfoDto.setHardDiskSize(dto.getHardDiskSizeId());
        astInfoDto.setScreenSize(dto.getScreenSizeId());
        astInfoDto.setProcessor(dto.getProcessorId());
        astInfoDto.setOsName(dto.getOsNameId());
        return astInfoDto;

    }

    public static AssetPurchaseInformationDTO mapUploadToPurchese(AssetRegisterUploadDto dto) {
        AssetPurchaseInformationDTO astPurDto = new AssetPurchaseInformationDTO();
        BeanUtils.copyProperties(dto, astPurDto);
        astPurDto.setInitialBookValue(dto.getWrittenDownValueTillDate());
        astPurDto.setFromWhomAcquired(dto.getVenderId());
        astPurDto.setManufacturer(dto.getManufacturer());
        astPurDto.setPurchaseOrderNo(dto.getInvoiceNo());
        astPurDto.setDateOfAcquisition(dto.getDateofAcquisition());
        astPurDto.setCostOfAcquisition(dto.getCostOfAcquisition());
        astPurDto.setInitialBookDate(dto.getWrittenDownDate());
        return astPurDto;

    }
    
    public static AssetPurchaseInformationDTO mapITAssetUploadToPurchese(ITAssetRegisterUploadDto dto) {
        AssetPurchaseInformationDTO astPurDto = new AssetPurchaseInformationDTO();
        BeanUtils.copyProperties(dto, astPurDto);
        astPurDto.setDateOfAcquisition(dto.getDateOfAcquisition());
        astPurDto.setCostOfAcquisition(dto.getCostOfAcquisition());
        astPurDto.setModeOfPayment(dto.getModeOfPaymentId());
        astPurDto.setFromWhomAcquired(dto.getVenderId());
        astPurDto.setCountryOfOrigin1(dto.getCountryOfOrigin1Id());
        return astPurDto;

    }

    public static AssetClassificationDTO mapUploadToClass(AssetRegisterUploadDto dto) {
        AssetClassificationDTO astClassDto = new AssetClassificationDTO();
        BeanUtils.copyProperties(dto, astClassDto);
        astClassDto.setLatitude(dto.getLatitude());
        astClassDto.setLongitude(dto.getLongitude());
        astClassDto.setDepartment(dto.getDepId());
        astClassDto.setGisId(dto.getGisId());
        astClassDto.setLocation(dto.getLocationId());
        astClassDto.setSurveyNo(dto.getSurveyNo());
        return astClassDto;

    }

    public static AssetDepreciationChartDTO mapUploadToDepre(AssetRegisterUploadDto dto) {
        AssetDepreciationChartDTO astdepChat = null;
        if (dto.getDeprApplicable().equalsIgnoreCase("YES")) {
            astdepChat = new AssetDepreciationChartDTO();
            BeanUtils.copyProperties(dto, astdepChat);
            astdepChat.setChartOfDepre(dto.getAcquiModeId());
            astdepChat.setOriUseYear(dto.getLifeInYear());
            astdepChat.setSalvageValue(dto.getSalvageValue());
            astdepChat.setDeprApplicable(true);
            astdepChat.setInitialAccumDepreAmount(dto.getAccuDepreciationPrevious());
            astdepChat.setInitialAccumulDeprDate(dto.getLastDepreciationDate());
        }
        return astdepChat;

    }

    public static AssetSpecificationDTO mapUploadToSpeci(AssetRegisterUploadDto dto) {
        AssetSpecificationDTO astSpeciDto = new AssetSpecificationDTO();
        BeanUtils.copyProperties(dto, astSpeciDto);
        astSpeciDto.setLength(dto.getLength());
        astSpeciDto.setArea(dto.getTotalArea());
        astSpeciDto.setCarpet(dto.getCarpetArea());
        astSpeciDto.setHeight(dto.getHeight());
        astSpeciDto.setVolume(dto.getVolume());
        astSpeciDto.setBreadth(dto.getBreadth());
        astSpeciDto.setNoOfFloor(dto.getNoOfFloor());
        astSpeciDto.setAreaValue(dto.getAreaUnit());
        astSpeciDto.setBreadthValue(dto.getBreadthUnit());
        astSpeciDto.setLengthValue(dto.getLengthUnit());
        astSpeciDto.setHeightValue(dto.getHeightUnit());
        astSpeciDto.setCarpetValue(dto.getCarpetUnit());
        astSpeciDto.setVolumeValue(dto.getVolumeUnit());
        return astSpeciDto;

    }
    public static AssetServiceInformationDTO mapITAssetUploadToServiceInfo(ITAssetRegisterUploadDto dto) {
    	AssetServiceInformationDTO astSerInfoDto = new AssetServiceInformationDTO();
        BeanUtils.copyProperties(dto, astSerInfoDto);
        astSerInfoDto.setServiceProvider(dto.getServiceProvider());
        astSerInfoDto.setServiceStartDate(dto.getServiceStartDate());
        astSerInfoDto.setWarrenty(dto.getWarrenty());
        astSerInfoDto.setServiceExpiryDate(dto.getServiceExpiryDate());
        astSerInfoDto.setServiceDescription(dto.getServiceDescription());
        return astSerInfoDto;

    }
}
