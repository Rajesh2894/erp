package com.abm.mainet.asset.mapper;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.asset.domain.AssetPurchaseInformationRev;
import com.abm.mainet.asset.domain.AssetRealEstate;
import com.abm.mainet.asset.domain.AssetRealEstateRev;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;

@Component
public class RealEstateInfoMapper {

	
	/* public static AssetRealEstateRev mapToRealEstateEntityRev(AssetRealEstateInformationDTO astRealEstDto) {

	    	AssetRealEstateRev astRealEstateEntity =	new AssetRealEstateRev();
	    	
	    	if (null != astRealEstDto) {
	        	astRealEstateEntity.setAssessmentNo(astRealEstDto.getAssessmentNo());
	        	astRealEstateEntity.setPropertyRegistrationNo(astRealEstDto.getPropertyRegistrationNo());
	        	astRealEstateEntity.setTaxCode(astRealEstDto.getTaxCode());
	        	astRealEstateEntity.setRealEstateAmount(astRealEstDto.getRealEstAmount());
	        	astRealEstateEntity.setTaxZoneLocation(astRealEstDto.getTaxZoneLocation());
	        	astRealEstateEntity.setMunicipalityName(astRealEstDto.getMunicipalityName());
	        }
	    	
	    	return astRealEstateEntity;
	    }*/
	
	
	   public static AssetRealEstate mapToEntity(AssetRealEstateInformationDTO realEstateInfoRevDto) {

//	        AssetPurchaseInformationRev purInfoEntity = new AssetPurchaseInformationRev();
	        AssetRealEstate realEstateEntity	=	new AssetRealEstate();
	        AssetInformation astInfo = new AssetInformation();
	        astInfo.setAssetId(realEstateInfoRevDto.getAssetId());
	        if (realEstateInfoRevDto.getAssetRealEstId() != null) {
	        	realEstateEntity.setAssetRealStdId(realEstateInfoRevDto.getAssetRealEstId());
	        }
	        
	        if (realEstateInfoRevDto.getAssetRealEstId() != null) {
	        	realEstateEntity.setAssetRealStdId(realEstateInfoRevDto.getAssetRealEstId());
	        }

	        realEstateEntity.setMuncipalityName(realEstateInfoRevDto.getMunicipalityName());
	        realEstateEntity.setPropertyRegistrationNo(realEstateInfoRevDto.getPropertyRegistrationNo());
	        realEstateEntity.setRealEstateAmount(realEstateInfoRevDto.getRealEstAmount());
	        realEstateEntity.setTaxCode(realEstateInfoRevDto.getTaxCode());
	        realEstateEntity.setTaxZoneLocation(realEstateInfoRevDto.getTaxZoneLocation());
	        realEstateEntity.setAssessmentNo(realEstateInfoRevDto.getAssessmentNo());
	        
	        realEstateEntity.setUpdatedBy(realEstateInfoRevDto.getUpdatedBy());
	        realEstateEntity.setUpdatedDate(realEstateInfoRevDto.getUpdatedDate());
	        realEstateEntity.setCreatedBy(realEstateInfoRevDto.getCreatedBy());
	        realEstateEntity.setCreationDate(realEstateInfoRevDto.getCreationDate());
	        realEstateEntity.setUpdatedBy(realEstateInfoRevDto.getUpdatedBy());
	        realEstateEntity.setUpdatedDate(realEstateInfoRevDto.getUpdatedDate());
	        realEstateEntity.setLgIpMac(realEstateInfoRevDto.getLgIpMac());
	        realEstateEntity.setLgIpMacUpd(realEstateInfoRevDto.getLgIpMacUpd());
	        realEstateEntity.setAssetId(astInfo);
//	        purInfoEntity.setPurchaseOrderDate(purInfoRevDto.getPurchaseOrderDate());
//	        purInfoEntity.setAstCreationDate(purInfoRevDto.getAstCreationDate());
	        return realEstateEntity;

	    }
	
	
//Rev
   public static AssetRealEstateRev mapToEntityRev(AssetRealEstateInformationDTO realEstateInfoRevDto) {

//        AssetPurchaseInformationRev purInfoEntity = new AssetPurchaseInformationRev();
        AssetRealEstateRev realEstateEntity	=	new AssetRealEstateRev();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(realEstateInfoRevDto.getAssetId());
        if (realEstateInfoRevDto.getAssetRealEstId() != null) {
        	realEstateEntity.setAssetRealStdRevId(realEstateInfoRevDto.getAssetRealEstId());
        }

        realEstateEntity.setMuncipalityName(realEstateInfoRevDto.getMunicipalityName());
        realEstateEntity.setPropertyRegistrationNo(realEstateInfoRevDto.getPropertyRegistrationNo());
        realEstateEntity.setRealEstateAmount(realEstateInfoRevDto.getRealEstAmount());
        realEstateEntity.setTaxCode(realEstateInfoRevDto.getTaxCode());
        realEstateEntity.setTaxZoneLocation(realEstateInfoRevDto.getTaxZoneLocation());
        realEstateEntity.setAssessmentNo(realEstateInfoRevDto.getAssessmentNo());
        
        realEstateEntity.setUpdatedBy(realEstateInfoRevDto.getUpdatedBy());
        realEstateEntity.setUpdatedDate(realEstateInfoRevDto.getUpdatedDate());
        realEstateEntity.setCreatedBy(realEstateInfoRevDto.getCreatedBy());
        realEstateEntity.setCreationDate(realEstateInfoRevDto.getCreationDate());
        realEstateEntity.setUpdatedBy(realEstateInfoRevDto.getUpdatedBy());
        realEstateEntity.setUpdatedDate(realEstateInfoRevDto.getUpdatedDate());
        realEstateEntity.setLgIpMac(realEstateInfoRevDto.getLgIpMac());
        realEstateEntity.setLgIpMacUpd(realEstateInfoRevDto.getLgIpMacUpd());
        realEstateEntity.setAssetId(astInfo);
//        purInfoEntity.setPurchaseOrderDate(purInfoRevDto.getPurchaseOrderDate());
//        purInfoEntity.setAstCreationDate(purInfoRevDto.getAstCreationDate());
        return realEstateEntity;

    }
	
	public static AssetRealEstateInformationDTO resetRealEstate(AssetRealEstateInformationDTO realEstateDTO) {
		
		realEstateDTO.setAssessmentNo(null);
		realEstateDTO.setPropertyRegistrationNo(null);
		realEstateDTO.setTaxCode(null);
    	realEstateDTO.setRealEstAmount(null);
    	realEstateDTO.setTaxZoneLocation(null);
    	realEstateDTO.setMunicipalityName(null);
        realEstateDTO.setUpdatedBy(null);
        realEstateDTO.setUpdatedDate(null);
        realEstateDTO.setCreatedBy(null);
        realEstateDTO.setCreationDate(null);
        realEstateDTO.setUpdatedBy(null);
        realEstateDTO.setUpdatedDate(null);
        realEstateDTO.setLgIpMac(null);
        realEstateDTO.setLgIpMacUpd(null);
        return realEstateDTO;
    }

    public static AssetRealEstateInformationDTO mapToDTO(AssetRealEstate realEstateEntity) {

    	AssetRealEstateInformationDTO realEstateeDto = new AssetRealEstateInformationDTO();
        AssetInformation astInfo = realEstateEntity.getAssetId();
        realEstateeDto.setAssetId(astInfo.getAssetId());
        realEstateeDto.setAssetRealEstId(realEstateEntity.getAssetRealStdId());
        realEstateeDto.setAssessmentNo(realEstateEntity.getAssessmentNo());
        realEstateeDto.setPropertyRegistrationNo(realEstateEntity.getPropertyRegistrationNo());
        realEstateeDto.setTaxCode(realEstateEntity.getTaxCode());
        realEstateeDto.setRealEstAmount(realEstateEntity.getRealEstateAmount());
        realEstateeDto.setTaxZoneLocation(realEstateEntity.getTaxZoneLocation());
        realEstateeDto.setMunicipalityName(realEstateEntity.getMuncipalityName());
//        realEstateeDto.setAssetPurchaserId(realEstateRevEntity.getAssetPurchaserId());
//        realEstateeDto.setAssetRealEstId(realEstateEntity.getAssetRealStdId());
      
        realEstateeDto.setUpdatedBy(realEstateEntity.getUpdatedBy());
        realEstateeDto.setUpdatedDate(realEstateEntity.getUpdatedDate());
        realEstateeDto.setCreatedBy(realEstateEntity.getCreatedBy());
        realEstateeDto.setCreationDate(realEstateEntity.getCreationDate());
        realEstateeDto.setUpdatedBy(realEstateEntity.getUpdatedBy());
        realEstateeDto.setUpdatedDate(realEstateEntity.getUpdatedDate());
        realEstateeDto.setLgIpMac(realEstateEntity.getLgIpMac());
        realEstateeDto.setLgIpMacUpd(realEstateEntity.getLgIpMacUpd());
        return realEstateeDto;

    }
	
	 // Rev
    public static AssetRealEstateInformationDTO mapToDTORev(AssetRealEstateRev realEstateRevEntity) {

    	AssetRealEstateInformationDTO realEstateeDto = new AssetRealEstateInformationDTO();
        AssetInformation astInfo = realEstateRevEntity.getAssetId();
        realEstateeDto.setAssetId(astInfo.getAssetId());
        realEstateeDto.setAssetRealEstId(realEstateRevEntity.getAssetRealStdId());
        realEstateeDto.setAssessmentNo(realEstateRevEntity.getAssessmentNo());
        realEstateeDto.setPropertyRegistrationNo(realEstateRevEntity.getPropertyRegistrationNo());
        realEstateeDto.setTaxCode(realEstateRevEntity.getTaxCode());
        realEstateeDto.setRealEstAmount(realEstateRevEntity.getRealEstateAmount());
        realEstateeDto.setTaxZoneLocation(realEstateRevEntity.getTaxZoneLocation());
        realEstateeDto.setMunicipalityName(realEstateRevEntity.getMuncipalityName());
        
        
        realEstateeDto.setUpdatedBy(realEstateRevEntity.getUpdatedBy());
        realEstateeDto.setUpdatedDate(realEstateRevEntity.getUpdatedDate());
        realEstateeDto.setCreatedBy(realEstateRevEntity.getCreatedBy());
        realEstateeDto.setCreationDate(realEstateRevEntity.getCreationDate());
        realEstateeDto.setUpdatedBy(realEstateRevEntity.getUpdatedBy());
        realEstateeDto.setUpdatedDate(realEstateRevEntity.getUpdatedDate());
        realEstateeDto.setLgIpMac(realEstateRevEntity.getLgIpMac());
        realEstateeDto.setLgIpMacUpd(realEstateRevEntity.getLgIpMacUpd());
        return realEstateeDto;

    }

	
}
