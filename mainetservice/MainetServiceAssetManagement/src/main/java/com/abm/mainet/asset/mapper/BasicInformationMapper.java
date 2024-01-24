package com.abm.mainet.asset.mapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetInformationRev;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInventoryInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPostingInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;
import com.abm.mainet.common.utility.CommonMasterUtility;

@Component
public class BasicInformationMapper {
    public static AssetInformation mapToInfoEntity(AssetInformationDTO astInfo) {
        AssetInformation astEntity = new AssetInformation();
        BeanUtils.copyProperties(astInfo, astEntity);
        if (astInfo.getAssetId() != null) {
            astEntity.setAssetId(astInfo.getAssetId());
        }
        astEntity.setAcquisitionMethod(astInfo.getAcquisitionMethod());
        astEntity.setAssetParentIdentifier(astInfo.getAssetParentIdentifier());
        astEntity.setAssetClass2(astInfo.getAssetClass2());
        astEntity.setAssetClass1(astInfo.getAssetClass1());
        astEntity.setAssetGroup(astInfo.getAssetGroup());
        astEntity.setAssetModelIdentifier(astInfo.getAssetModelIdentifier());
        astEntity.setAssetName(astInfo.getAssetName());
        if (astInfo.getAssetStatus() != null) {
            astEntity.setAssetStatus(astInfo.getAssetStatus());
        } else {
            Organisation org = new Organisation();
            org.setOrgid(astInfo.getOrgId());
            astEntity.setAssetStatus(CommonMasterUtility
                    .getLookUpFromPrefixLookUpValue("A", MainetConstants.AssetManagement.ASSET_STATUS_PREFIX, 1, org)
                    .getLookUpId());
        }
        astEntity.setAssetType(astInfo.getAssetType());
        astEntity.setBrandName(astInfo.getBrandName());
        astEntity.setDetails(astInfo.getDetails());
        astEntity.setInvestmentReason(astInfo.getInvestmentReason());
        astEntity.setNoOfSimilarAsset(astInfo.getNoOfSimilarAsset());
        astEntity.setOrgId(astInfo.getOrgId());
        astEntity.setRemark(astInfo.getRemark());
        if (StringUtils.isNotEmpty(astInfo.getRfiId())) {
            astEntity.setRfiId(astInfo.getRfiId());
        } else {
            astEntity.setRfiId(null);
        }
        if (astInfo.getBarcodeNo() != null) {
            astEntity.setBarcodeNo(astInfo.getBarcodeNo());
        }
        astEntity.setSerialNo(astInfo.getSerialNo());
        astEntity.setNoOfSimilarAsset(astInfo.getNoOfSimilarAsset());
        astEntity.setCreatedBy(astInfo.getCreatedBy());
        astEntity.setUpdatedBy(astInfo.getUpdatedBy());
        astEntity.setLgIpMac(astInfo.getLgIpMac());
        astEntity.setLgIpMacUpd(astInfo.getLgIpMacUpd());
        astEntity.setCreationDate(astInfo.getCreationDate());
        astEntity.setUpdatedDate(astInfo.getUpdatedDate());
        astEntity.setOrgId(astInfo.getOrgId());
        astEntity.setRegisterDetail(astInfo.getRegisterDetail());
        astEntity.setPurpose(astInfo.getPurpose());
        astEntity.setAstCode(astInfo.getAstCode());
        astEntity.setAppovalStatus(astInfo.getAppovalStatus());
        astEntity.setHardDiskSize(astInfo.getHardDiskSize());
        astEntity.setScreenSize(astInfo.getScreenSize());
        astEntity.setOsName(astInfo.getOsName());
        astEntity.setProcessor(astInfo.getProcessor());
        astEntity.setRamSize(astInfo.getRamSize());
        astEntity.setGroupRefId(astInfo.getGroupRefId());
        mapToSpecEntity(astInfo.getAssetSpecificationDTO(), astEntity);
        mapToInventEntity(astInfo.getAssetInventoryInfoDTO(), astEntity);
        mapToPostingEntity(astInfo.getAssetPostingInfoDTO(), astEntity);
        return astEntity;

    }

    public static AssetInformationRev mapToInfoEntityRev(AssetInformationDTO astInfo) {
        AssetInformationRev astEntityRev = new AssetInformationRev();
        BeanUtils.copyProperties(astInfo, astEntityRev);
        if (astInfo.getAssetId() != null) {
            astEntityRev.setAssetId(astInfo.getAssetId());
        }
        astEntityRev.setAcquisitionMethod(astInfo.getAcquisitionMethod());
        astEntityRev.setAssetParentIdentifier(astInfo.getAssetParentIdentifier());
        astEntityRev.setAssetClass2(astInfo.getAssetClass2());
        astEntityRev.setAssetClass1(astInfo.getAssetClass1());
        astEntityRev.setAssetGroup(astInfo.getAssetGroup());
        astEntityRev.setAssetModelIdentifier(astInfo.getAssetModelIdentifier());
        astEntityRev.setAssetName(astInfo.getAssetName());
        astEntityRev.setAssetStatus(astInfo.getAssetStatus());
        astEntityRev.setAssetType(astInfo.getAssetType());
        astEntityRev.setBrandName(astInfo.getBrandName());
        astEntityRev.setDetails(astInfo.getDetails());
        astEntityRev.setInvestmentReason(astInfo.getInvestmentReason());
        astEntityRev.setNoOfSimilarAsset(astInfo.getNoOfSimilarAsset());
        astEntityRev.setOrgId(astInfo.getOrgId());
        astEntityRev.setRemark(astInfo.getRemark());
        if (StringUtils.isNotEmpty(astInfo.getRfiId())) {
            astEntityRev.setRfiId(astInfo.getRfiId());
        }
        if (astInfo.getBarcodeNo() != null) {
            astEntityRev.setBarcodeNo(astInfo.getBarcodeNo());
        }
        astEntityRev.setSerialNo(astInfo.getSerialNo());
        astEntityRev.setNoOfSimilarAsset(astInfo.getNoOfSimilarAsset());
        astEntityRev.setCreatedBy(astInfo.getCreatedBy());
        astEntityRev.setUpdatedBy(astInfo.getUpdatedBy());
        astEntityRev.setLgIpMac(astInfo.getLgIpMac());
        astEntityRev.setLgIpMacUpd(astInfo.getLgIpMacUpd());
        astEntityRev.setCreationDate(astInfo.getCreationDate());
        astEntityRev.setUpdatedDate(astInfo.getUpdatedDate());
        astEntityRev.setOrgId(astInfo.getOrgId());
        astEntityRev.setRegisterDetail(astInfo.getRegisterDetail());
        astEntityRev.setPurpose(astInfo.getPurpose());
        astEntityRev.setAssetCode(astInfo.getAstCode());
        mapToSpecEntityRev(astInfo.getAssetSpecificationDTO(), astEntityRev);
        mapToInventEntityRev(astInfo.getAssetInventoryInfoDTO(), astEntityRev);
        mapToPostingEntityRev(astInfo.getAssetPostingInfoDTO(), astEntityRev);
        return astEntityRev;

    }

    public static void mapToSpecEntity(AssetSpecificationDTO specInfo, AssetInformation astEntity) {

        if (null != specInfo) {// specification dto
            astEntity.setArea(specInfo.getArea());
            astEntity.setBreadth(specInfo.getBreadth());
            astEntity.setHeight(specInfo.getHeight());
            astEntity.setLength(specInfo.getLength());
            astEntity.setVolume(specInfo.getVolume());
            astEntity.setWeight(specInfo.getWeight());
            astEntity.setWidth(specInfo.getWidth());
            astEntity.setBreadthValue(specInfo.getBreadthValue());
            astEntity.setWeightValue(specInfo.getWeightValue());
            astEntity.setLengthValue(specInfo.getLengthValue());
            astEntity.setHeightValue(specInfo.getHeightValue());
            astEntity.setVolumeValue(specInfo.getVolumeValue());
            astEntity.setWidthValue(specInfo.getWidthValue());
            astEntity.setAreaValue(specInfo.getAreaValue());
            astEntity.setCarpet(specInfo.getCarpet());
            astEntity.setCarpetValue(specInfo.getCarpetValue());
            astEntity.setNoOfFloor(specInfo.getNoOfFloor());
        }

    }

    public static void mapToSpecEntityRev(AssetSpecificationDTO specInfo, AssetInformationRev astEntityRev) {

        if (null != specInfo) {// specification dto
            astEntityRev.setArea(specInfo.getArea());
            astEntityRev.setBreadth(specInfo.getBreadth());
            astEntityRev.setHeight(specInfo.getHeight());
            astEntityRev.setLength(specInfo.getLength());
            astEntityRev.setVolume(specInfo.getVolume());
            astEntityRev.setWeight(specInfo.getWeight());
            astEntityRev.setWidth(specInfo.getWidth());
            astEntityRev.setBreadthValue(specInfo.getBreadthValue());
            astEntityRev.setWeightValue(specInfo.getWeightValue());
            astEntityRev.setLengthValue(specInfo.getLengthValue());
            astEntityRev.setHeightValue(specInfo.getHeightValue());
            astEntityRev.setVolumeValue(specInfo.getVolumeValue());
            astEntityRev.setWidthValue(specInfo.getWidthValue());
            astEntityRev.setAreaValue(specInfo.getAreaValue());
            astEntityRev.setCarpet(specInfo.getCarpet());
            astEntityRev.setCarpetValue(specInfo.getCarpetValue());
            astEntityRev.setNoOfFloor(specInfo.getNoOfFloor());
        }

    }

    public static void mapToInventEntity(AssetInventoryInformationDTO aiiInfo, AssetInformation astEntity) {

        if (null != aiiInfo) {// invenotry dto
            astEntity.setInventoryNo(aiiInfo.getInventoryNo());
            astEntity.setInventoryNote(aiiInfo.getInventoryNote());
            astEntity.setLastInventoryOn(aiiInfo.getLastInventoryOn());
            astEntity.setIncludeAssetInventoryLst(aiiInfo.getIncludeAssetInventoryLst());
        }
    }

    public static void mapToInventEntityRev(AssetInventoryInformationDTO aiiInfo, AssetInformationRev astEntityRev) {

        if (null != aiiInfo) {// invenotry dto
            astEntityRev.setInventoryNo(aiiInfo.getInventoryNo());
            astEntityRev.setInventoryNote(aiiInfo.getInventoryNote());
            astEntityRev.setLastInventoryOn(aiiInfo.getLastInventoryOn());
            astEntityRev.setIncludeAssetInventoryLst(aiiInfo.getIncludeAssetInventoryLst());
        }
    }

    public static void mapToPostingEntity(AssetPostingInformationDTO pstInfo, AssetInformation astEntity) {
        if (null != pstInfo) {// posting dto
            astEntity.setAcquisitionYear(pstInfo.getAcquisitionYear());
            astEntity.setCustodian(pstInfo.getCustodian());
            astEntity.setCapitalizeOn(pstInfo.getCapitalizeOn());
            astEntity.setOrderOn(pstInfo.getOrderOn());
            astEntity.setFirstAcquisitionOn(pstInfo.getFirstAcquisitionOn());
            astEntity.setEmployeeId(pstInfo.getEmployeeId());
            astEntity.setLocation(pstInfo.getLocation());
        }
    }

    public static void mapToPostingEntityRev(AssetPostingInformationDTO pstInfo, AssetInformationRev astEntityRev) {
        if (null != pstInfo) {// posting dto
            astEntityRev.setAcquisitionYear(pstInfo.getAcquisitionYear());
            astEntityRev.setCustodian(pstInfo.getCustodian());
            astEntityRev.setCapitalizeOn(pstInfo.getCapitalizeOn());
            astEntityRev.setOrderOn(pstInfo.getOrderOn());
            astEntityRev.setFirstAcquisitionOn(pstInfo.getFirstAcquisitionOn());
            astEntityRev.setEmployeeId(pstInfo.getEmployeeId());
            astEntityRev.setLocation(pstInfo.getLocation());
        }
    }

    public static AssetInformationDTO mapToInfoDTO(AssetInformation astEntity) {
        AssetInformationDTO astInfo = new AssetInformationDTO();
        BeanUtils.copyProperties(astEntity, astInfo);
        AssetSpecificationDTO specDTO = new AssetSpecificationDTO();
        AssetPostingInformationDTO postDTO = new AssetPostingInformationDTO();
        AssetInventoryInformationDTO invetDTO = new AssetInventoryInformationDTO();
        astInfo.setAssetSpecificationDTO(specDTO);
        astInfo.setAssetPostingInfoDTO(postDTO);
        astInfo.setAssetInventoryInfoDTO(invetDTO);
        astInfo.setAcquisitionMethod(astEntity.getAcquisitionMethod());
        astInfo.setAssetParentIdentifier(astEntity.getAssetParentIdentifier());
        astInfo.setAssetClass1(Long.valueOf(astEntity.getAssetClass1()));
        astInfo.setAssetClass2(Long.valueOf(astEntity.getAssetClass2()));
        astInfo.setAssetGroup(astEntity.getAssetGroup());
        astInfo.setAssetId(astEntity.getAssetId());
        astInfo.setAssetModelIdentifier(astEntity.getAssetModelIdentifier());
        astInfo.setAssetName(astEntity.getAssetName());
        astInfo.setAssetStatus(astEntity.getAssetStatus());
        astInfo.setAssetType(astEntity.getAssetType());
        astInfo.setBarcodeNo(astEntity.getBarcodeNo());
        astInfo.setBrandName(astEntity.getBrandName());
        astInfo.setDetails(astEntity.getDetails());
        astInfo.setInvestmentReason(astEntity.getInvestmentReason());
        astInfo.setNoOfSimilarAsset(astEntity.getNoOfSimilarAsset());
        astInfo.setOrgId(astEntity.getOrgId());
        astInfo.setRemark(astEntity.getRemark());
        astInfo.setRfiId(astEntity.getRfiId());
        astInfo.setSerialNo(astEntity.getSerialNo());
        astInfo.setNoOfSimilarAsset(astEntity.getNoOfSimilarAsset());
        astInfo.setCreatedBy(astEntity.getCreatedBy());
        astInfo.setUpdatedBy(astEntity.getUpdatedBy());
        astInfo.setLgIpMac(astEntity.getLgIpMac());
        astInfo.setLgIpMacUpd(astEntity.getLgIpMacUpd());
        astInfo.setCreationDate(astEntity.getCreationDate());
        astInfo.setUpdatedDate(astEntity.getUpdatedDate());
        astInfo.setOrgId(astEntity.getOrgId());
        astInfo.setAppovalStatus(astEntity.getAppovalStatus());
        astInfo.setRegisterDetail(astEntity.getRegisterDetail());
        astInfo.setPurpose(astEntity.getPurpose());
        astInfo.setUrlParam(astEntity.getUrlParam());
        astInfo.setAstCode(astEntity.getAstCode());
        astInfo.setAstAppNo(astEntity.getAstAppNo());
        astInfo.setRoadName(astEntity.getRoadName());
        astInfo.setPincode(astEntity.getPincode());
        mapToSpecDTO(astEntity, astInfo.getAssetSpecificationDTO());
        mapToInventDTO(astEntity, astInfo.getAssetInventoryInfoDTO());
        mapToPostingDTO(astEntity, astInfo.getAssetPostingInfoDTO());
        return astInfo;

    }

    public static AssetInformationDTO mapToInfoDTORev(AssetInformationRev astEntityRev) {
        AssetInformationDTO astInfo = new AssetInformationDTO();
        BeanUtils.copyProperties(astEntityRev, astInfo);
        AssetSpecificationDTO specDTO = new AssetSpecificationDTO();
        AssetPostingInformationDTO postDTO = new AssetPostingInformationDTO();
        AssetInventoryInformationDTO invetDTO = new AssetInventoryInformationDTO();
        astInfo.setAssetSpecificationDTO(specDTO);
        astInfo.setAssetPostingInfoDTO(postDTO);
        astInfo.setAssetInventoryInfoDTO(invetDTO);
        astInfo.setAcquisitionMethod(astEntityRev.getAcquisitionMethod());
        astInfo.setAssetParentIdentifier(astEntityRev.getAssetParentIdentifier());
        astInfo.setAssetClass1(Long.valueOf(astEntityRev.getAssetClass1()));
        astInfo.setAssetClass2(Long.valueOf(astEntityRev.getAssetClass2()));
        astInfo.setAssetGroup(astEntityRev.getAssetGroup());
        astInfo.setAssetId(astEntityRev.getAssetId());
        astInfo.setAssetModelIdentifier(astEntityRev.getAssetModelIdentifier());
        astInfo.setAssetName(astEntityRev.getAssetName());
        astInfo.setAssetStatus(astEntityRev.getAssetStatus());
        astInfo.setAssetType(astEntityRev.getAssetType());
        astInfo.setBarcodeNo(astEntityRev.getBarcodeNo());
        astInfo.setBrandName(astEntityRev.getBrandName());
        astInfo.setDetails(astEntityRev.getDetails());
        astInfo.setInvestmentReason(astEntityRev.getInvestmentReason());
        astInfo.setNoOfSimilarAsset(astEntityRev.getNoOfSimilarAsset());
        astInfo.setOrgId(astEntityRev.getOrgId());
        astInfo.setRemark(astEntityRev.getRemark());
        astInfo.setRfiId(astEntityRev.getRfiId());
        astInfo.setSerialNo(astEntityRev.getSerialNo());
        astInfo.setNoOfSimilarAsset(astEntityRev.getNoOfSimilarAsset());
        astInfo.setCreatedBy(astEntityRev.getCreatedBy());
        astInfo.setUpdatedBy(astEntityRev.getUpdatedBy());
        astInfo.setLgIpMac(astEntityRev.getLgIpMac());
        astInfo.setLgIpMacUpd(astEntityRev.getLgIpMacUpd());
        astInfo.setCreationDate(astEntityRev.getCreationDate());
        astInfo.setUpdatedDate(astEntityRev.getUpdatedDate());
        astInfo.setOrgId(astEntityRev.getOrgId());
        astInfo.setAppovalStatus(astEntityRev.getAppovalStatus());
        astInfo.setRegisterDetail(astEntityRev.getRegisterDetail());
        astInfo.setPurpose(astEntityRev.getPurpose());
        astInfo.setUrlParam(astEntityRev.getUrlParam());
        astInfo.setAstCode(astEntityRev.getAssetCode());
        astInfo.setAstAppNo(astEntityRev.getAstAppNo());
        astInfo.setRoadName(astEntityRev.getRoadName());
        astInfo.setPincode(astEntityRev.getPincode());
        mapToSpecDTORev(astEntityRev, astInfo.getAssetSpecificationDTO());
        mapToInventDTORev(astEntityRev, astInfo.getAssetInventoryInfoDTO());
        mapToPostingDTORev(astEntityRev, astInfo.getAssetPostingInfoDTO());
        return astInfo;

    }

    public static void mapToSpecDTO(AssetInformation astEntity, AssetSpecificationDTO specInfo) {
        specInfo.setArea(astEntity.getArea());
        specInfo.setBreadth(astEntity.getBreadth());
        specInfo.setHeight(astEntity.getHeight());
        specInfo.setLength(astEntity.getLength());
        specInfo.setVolume(astEntity.getVolume());
        specInfo.setWeight(astEntity.getWeight());
        specInfo.setWidth(astEntity.getWidth());
        specInfo.setBreadthValue(astEntity.getBreadthValue());
        specInfo.setWeightValue(astEntity.getWeightValue());
        specInfo.setLengthValue(astEntity.getLengthValue());
        specInfo.setHeightValue(astEntity.getHeightValue());
        specInfo.setVolumeValue(astEntity.getVolumeValue());
        specInfo.setWidthValue(astEntity.getWidthValue());
        specInfo.setAreaValue(astEntity.getAreaValue());
        specInfo.setCarpet(astEntity.getCarpet());
        specInfo.setCarpetValue(astEntity.getCarpetValue());
        specInfo.setNoOfFloor(astEntity.getNoOfFloor());
    }

    // ------For Rev
    public static void mapToSpecDTORev(AssetInformationRev astEntityRev, AssetSpecificationDTO specInfo) {
        specInfo.setArea(astEntityRev.getArea());
        specInfo.setBreadth(astEntityRev.getBreadth());
        specInfo.setHeight(astEntityRev.getHeight());
        specInfo.setLength(astEntityRev.getLength());
        specInfo.setVolume(astEntityRev.getVolume());
        specInfo.setWeight(astEntityRev.getWeight());
        specInfo.setWidth(astEntityRev.getWidth());
        specInfo.setBreadthValue(astEntityRev.getBreadthValue());
        specInfo.setWeightValue(astEntityRev.getWeightValue());
        specInfo.setLengthValue(astEntityRev.getLengthValue());
        specInfo.setHeightValue(astEntityRev.getHeightValue());
        specInfo.setVolumeValue(astEntityRev.getVolumeValue());
        specInfo.setWidthValue(astEntityRev.getWidthValue());
        specInfo.setAreaValue(astEntityRev.getAreaValue());
        specInfo.setCarpet(astEntityRev.getCarpet());
        specInfo.setCarpetValue(astEntityRev.getCarpetValue());
        specInfo.setNoOfFloor(astEntityRev.getNoOfFloor());
    }

    public static void mapToInventDTO(AssetInformation astEntity, AssetInventoryInformationDTO aiiInfo) {
        aiiInfo.setInventoryNo(astEntity.getInventoryNo());
        aiiInfo.setInventoryNote(astEntity.getInventoryNote());
        aiiInfo.setLastInventoryOn(astEntity.getLastInventoryOn());
        aiiInfo.setIncludeAssetInventoryLst(astEntity.getIncludeAssetInventoryLst());
    }

    public static void mapToInventDTORev(AssetInformationRev astEntityRev, AssetInventoryInformationDTO aiiInfo) {
        aiiInfo.setInventoryNo(astEntityRev.getInventoryNo());
        aiiInfo.setInventoryNote(astEntityRev.getInventoryNote());
        aiiInfo.setLastInventoryOn(astEntityRev.getLastInventoryOn());
        aiiInfo.setIncludeAssetInventoryLst(astEntityRev.getIncludeAssetInventoryLst());
    }

    public static void mapToPostingDTO(AssetInformation astEntity, AssetPostingInformationDTO pstInfo) {
        pstInfo.setAcquisitionYear(astEntity.getAcquisitionYear());
        pstInfo.setCustodian(astEntity.getCustodian());
        pstInfo.setCapitalizeOn(astEntity.getCapitalizeOn());
        pstInfo.setOrderOn(astEntity.getOrderOn());
        pstInfo.setFirstAcquisitionOn(astEntity.getFirstAcquisitionOn());
        pstInfo.setEmployeeId(astEntity.getEmployeeId());
        pstInfo.setLocation(astEntity.getLocation());
    }

    public static void mapToPostingDTORev(AssetInformationRev astEntityRev, AssetPostingInformationDTO pstInfo) {
        pstInfo.setAcquisitionYear(astEntityRev.getAcquisitionYear());
        pstInfo.setCustodian(astEntityRev.getCustodian());
        pstInfo.setCapitalizeOn(astEntityRev.getCapitalizeOn());
        pstInfo.setOrderOn(astEntityRev.getOrderOn());
        pstInfo.setFirstAcquisitionOn(astEntityRev.getFirstAcquisitionOn());
        pstInfo.setEmployeeId(astEntityRev.getEmployeeId());
        pstInfo.setLocation(astEntityRev.getLocation());
    }

    public static AssetInformationDTO resetAssetInformation(AssetInformationDTO infoDTO) {
        infoDTO.setAcquisitionMethod(null);
        infoDTO.setAssetParentIdentifier(null);
        infoDTO.setAssetClass1(null);
        infoDTO.setAssetClass2(null);
        infoDTO.setAssetGroup(null);
        infoDTO.setAssetModelIdentifier(null);
        infoDTO.setAssetName(null);
        infoDTO.setAssetStatus(null);
        infoDTO.setAssetType(null);
        infoDTO.setBarcodeNo(null);
        infoDTO.setBrandName(null);
        infoDTO.setDetails(null);
        infoDTO.setInvestmentReason(null);
        infoDTO.setNoOfSimilarAsset(null);
        infoDTO.setOrgId(null);
        infoDTO.setRemark(null);
        infoDTO.setRfiId(null);
        infoDTO.setSerialNo(null);
        infoDTO.setNoOfSimilarAsset(null);
        infoDTO.setCreatedBy(null);
        infoDTO.setUpdatedBy(null);
        infoDTO.setLgIpMac(null);
        infoDTO.setLgIpMacUpd(null);
        infoDTO.setCreationDate(null);
        infoDTO.setUpdatedDate(null);
        infoDTO.setOrgId(null);
        infoDTO.setAppovalStatus(null);
        infoDTO.setRegisterDetail(null);
        infoDTO.setPurpose(null);
        infoDTO.setUrlParam(null);
        infoDTO.setAstCode(null);
        resetSpecDTO(infoDTO.getAssetSpecificationDTO());
        resetInventDTO(infoDTO.getAssetInventoryInfoDTO());
        resetPostingDTO(infoDTO.getAssetPostingInfoDTO());
        return infoDTO;
    }

    public static void resetSpecDTO(AssetSpecificationDTO specInfo) {
        specInfo.setArea(null);
        specInfo.setBreadth(null);
        specInfo.setHeight(null);
        specInfo.setLength(null);
        specInfo.setVolume(null);
        specInfo.setWeight(null);
        specInfo.setWidth(null);
        specInfo.setBreadthValue(null);
        specInfo.setWeightValue(null);
        specInfo.setLengthValue(null);
        specInfo.setHeightValue(null);
        specInfo.setVolumeValue(null);
        specInfo.setWidthValue(null);
        specInfo.setAreaValue(null);
        specInfo.setCarpet(null);
        specInfo.setCarpetValue(null);
        specInfo.setNoOfFloor(null);
    }

    public static void resetInventDTO(AssetInventoryInformationDTO aiiInfo) {
        aiiInfo.setInventoryNo(null);
        aiiInfo.setInventoryNote(null);
        aiiInfo.setLastInventoryOn(null);
        aiiInfo.setIncludeAssetInventoryLst(null);
    }

    public static void resetPostingDTO(AssetPostingInformationDTO pstInfo) {
        pstInfo.setAcquisitionYear(null);
        pstInfo.setCustodian(null);
        pstInfo.setCapitalizeOn(null);
        pstInfo.setOrderOn(null);
        pstInfo.setFirstAcquisitionOn(null);
        pstInfo.setEmployeeId(null);
        pstInfo.setLocation(null);
    }
}