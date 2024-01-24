/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetLinear;
import com.abm.mainet.asset.domain.AssetLinearRev;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class LinearAssetMapper {
    public static AssetLinear mapToEntity(AssetLinearDTO astLinearDto) {
        AssetLinear astLinearEntity = new AssetLinear();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(astLinearDto.getAssetId());
        if (astLinearDto.getAssetLinearId() != null) {
            astLinearEntity.setAssetLinearId(astLinearDto.getAssetLinearId());
        }
        astLinearEntity.setStartPoint(astLinearDto.getStartPoint());
        astLinearEntity.setEndPoint(astLinearDto.getEndPoint());
        astLinearEntity.setLength(astLinearDto.getLength());
        astLinearEntity.setTypeOffset1(astLinearDto.getTypeOffset1());
        astLinearEntity.setTypeOffset2(astLinearDto.getTypeOffset2());
        astLinearEntity.setOffset1(astLinearDto.getOffset1());
        astLinearEntity.setOffset2(astLinearDto.getOffset2());
        astLinearEntity.setMarkCode(astLinearDto.getMarkCode());
        astLinearEntity.setMarkDesc(astLinearDto.getMarkDesc());
        astLinearEntity.setMarkType(astLinearDto.getMarkType());
        astLinearEntity.setStartPoint(astLinearDto.getStartPoint());
        astLinearEntity.setEndPoint(astLinearDto.getEndPoint());
        astLinearEntity.setUom(astLinearDto.getUom());
        astLinearEntity.setCreatedBy(astLinearDto.getCreatedBy());
        astLinearEntity.setCreationDate(astLinearDto.getCreationDate());
        astLinearEntity.setUpdatedBy(astLinearDto.getUpdatedBy());
        astLinearEntity.setUpdatedDate(astLinearDto.getUpdatedDate());
        astLinearEntity.setLgIpMac(astLinearDto.getLgIpMac());
        astLinearEntity.setLgIpMacUpd(astLinearDto.getLgIpMacUpd());
        astLinearEntity.setAssetId(astInfo);
        astLinearEntity.setLengthUnit(astLinearDto.getLengthUnit());
        astLinearEntity.setOffset2Value(astLinearDto.getOffset2Value());
        astLinearEntity.setOffset1Value(astLinearDto.getOffset1Value());
        astLinearEntity.setGridStartPoint(astLinearDto.getGridStartPoint());
        astLinearEntity.setGridEndPoint(astLinearDto.getGridEndPoint());
        return astLinearEntity;

    }
    
    public static AssetLinearRev mapToEntityRev(AssetLinearDTO astLinearDto) {
        AssetLinearRev astLinearEntity = new AssetLinearRev();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(astLinearDto.getAssetId());
        if (astLinearDto.getAssetLinearId() != null) {
            astLinearEntity.setAssetLinearId(astLinearDto.getAssetLinearId());
        }
        astLinearEntity.setStartPoint(astLinearDto.getStartPoint());
        astLinearEntity.setEndPoint(astLinearDto.getEndPoint());
        astLinearEntity.setLength(astLinearDto.getLength());
        astLinearEntity.setTypeOffset1(astLinearDto.getTypeOffset1());
        astLinearEntity.setTypeOffset2(astLinearDto.getTypeOffset2());
        astLinearEntity.setOffset1(astLinearDto.getOffset1());
        astLinearEntity.setOffset2(astLinearDto.getOffset2());
        astLinearEntity.setMarkCode(astLinearDto.getMarkCode());
        astLinearEntity.setMarkDesc(astLinearDto.getMarkDesc());
        astLinearEntity.setMarkType(astLinearDto.getMarkType());
        astLinearEntity.setStartPoint(astLinearDto.getStartPoint());
        astLinearEntity.setEndPoint(astLinearDto.getEndPoint());
        astLinearEntity.setUom(astLinearDto.getUom());
        astLinearEntity.setCreatedBy(astLinearDto.getCreatedBy());
        astLinearEntity.setCreationDate(astLinearDto.getCreationDate());
        astLinearEntity.setUpdatedBy(astLinearDto.getUpdatedBy());
        astLinearEntity.setUpdatedDate(astLinearDto.getUpdatedDate());
        astLinearEntity.setLgIpMac(astLinearDto.getLgIpMac());
        astLinearEntity.setLgIpMacUpd(astLinearDto.getLgIpMacUpd());
        astLinearEntity.setAssetId(astInfo);
        astLinearEntity.setLengthUnit(astLinearDto.getLengthUnit());
        astLinearEntity.setOffset2Value(astLinearDto.getOffset2Value());
        astLinearEntity.setOffset1Value(astLinearDto.getOffset1Value());
        astLinearEntity.setGridStartPoint(astLinearDto.getGridStartPoint());
        astLinearEntity.setGridEndPoint(astLinearDto.getGridEndPoint());
        return astLinearEntity;

    }

    public static AssetLinearDTO mapToDTO(AssetLinear astLinearEntity) {
        AssetLinearDTO astLinearDto = new AssetLinearDTO();
        AssetInformation astInfo = astLinearEntity.getAssetId();
        astLinearDto.setAssetId(astInfo.getAssetId());
        astLinearDto.setAssetLinearId(astLinearEntity.getAssetLinearId());
        astLinearDto.setStartPoint(astLinearEntity.getStartPoint());
        astLinearDto.setEndPoint(astLinearEntity.getEndPoint());
        astLinearDto.setLength(astLinearEntity.getLength());
        astLinearDto.setTypeOffset1(astLinearEntity.getTypeOffset1());
        astLinearDto.setTypeOffset2(astLinearEntity.getTypeOffset2());
        astLinearDto.setOffset1(astLinearEntity.getOffset1());
        astLinearDto.setOffset2(astLinearEntity.getOffset2());
        astLinearDto.setMarkCode(astLinearEntity.getMarkCode());
        astLinearDto.setMarkDesc(astLinearEntity.getMarkDesc());
        astLinearDto.setMarkType(astLinearEntity.getMarkType());
        astLinearDto.setStartPoint(astLinearEntity.getStartPoint());
        astLinearDto.setEndPoint(astLinearEntity.getEndPoint());
        astLinearDto.setUom(astLinearEntity.getUom());
        astLinearDto.setCreatedBy(astLinearEntity.getCreatedBy());
        astLinearDto.setCreationDate(astLinearEntity.getCreationDate());
        astLinearDto.setUpdatedBy(astLinearEntity.getUpdatedBy());
        astLinearDto.setUpdatedDate(astLinearEntity.getUpdatedDate());
        astLinearDto.setLgIpMac(astLinearEntity.getLgIpMac());
        astLinearDto.setLgIpMacUpd(astLinearEntity.getLgIpMacUpd());
        astLinearDto.setLengthUnit(astLinearEntity.getLengthUnit());
        astLinearDto.setOffset2Value(astLinearEntity.getOffset2Value());
        astLinearDto.setOffset1Value(astLinearEntity.getOffset1Value());
        astLinearDto.setGridStartPoint(astLinearEntity.getGridStartPoint());
        astLinearDto.setGridEndPoint(astLinearEntity.getGridEndPoint());
        return astLinearDto;

    }
    
    public static AssetLinearDTO mapToDTORev(AssetLinearRev astLinearEntity) {
        AssetLinearDTO astLinearDto = new AssetLinearDTO();
        AssetInformation astInfo = astLinearEntity.getAssetId();
        astLinearDto.setAssetId(astInfo.getAssetId());
        astLinearDto.setAssetLinearId(astLinearEntity.getAssetLinearId());
        astLinearDto.setStartPoint(astLinearEntity.getStartPoint());
        astLinearDto.setEndPoint(astLinearEntity.getEndPoint());
        astLinearDto.setLength(astLinearEntity.getLength());
        astLinearDto.setTypeOffset1(astLinearEntity.getTypeOffset1());
        astLinearDto.setTypeOffset2(astLinearEntity.getTypeOffset2());
        astLinearDto.setOffset1(astLinearEntity.getOffset1());
        astLinearDto.setOffset2(astLinearEntity.getOffset2());
        astLinearDto.setMarkCode(astLinearEntity.getMarkCode());
        astLinearDto.setMarkDesc(astLinearEntity.getMarkDesc());
        astLinearDto.setMarkType(astLinearEntity.getMarkType());
        astLinearDto.setStartPoint(astLinearEntity.getStartPoint());
        astLinearDto.setEndPoint(astLinearEntity.getEndPoint());
        astLinearDto.setUom(astLinearEntity.getUom());
        astLinearDto.setCreatedBy(astLinearEntity.getCreatedBy());
        astLinearDto.setCreationDate(astLinearEntity.getCreationDate());
        astLinearDto.setUpdatedBy(astLinearEntity.getUpdatedBy());
        astLinearDto.setUpdatedDate(astLinearEntity.getUpdatedDate());
        astLinearDto.setLgIpMac(astLinearEntity.getLgIpMac());
        astLinearDto.setLgIpMacUpd(astLinearEntity.getLgIpMacUpd());
        astLinearDto.setLengthUnit(astLinearEntity.getLengthUnit());
        astLinearDto.setOffset2Value(astLinearEntity.getOffset2Value());
        astLinearDto.setOffset1Value(astLinearEntity.getOffset1Value());
        astLinearDto.setGridStartPoint(astLinearEntity.getGridStartPoint());
        astLinearDto.setGridEndPoint(astLinearEntity.getGridEndPoint());
        return astLinearDto;

    }

    public static AssetLinearDTO resetLinear(AssetLinearDTO lineDTO) {
    	lineDTO.setStartPoint(null);
    	lineDTO.setEndPoint(null);
    	lineDTO.setLength(null);
    	lineDTO.setTypeOffset1(null);
    	lineDTO.setTypeOffset2(null);
    	lineDTO.setOffset1(null);
    	lineDTO.setOffset2(null);
    	lineDTO.setMarkCode(null);
    	lineDTO.setMarkDesc(null);
    	lineDTO.setMarkType(null);
    	lineDTO.setStartPoint(null);
    	lineDTO.setEndPoint(null);
    	lineDTO.setUom(null);
    	lineDTO.setCreatedBy(null);
    	lineDTO.setCreationDate(null);
    	lineDTO.setUpdatedBy(null);
    	lineDTO.setUpdatedDate(null);
    	lineDTO.setLgIpMac(null);
    	lineDTO.setLgIpMacUpd(null);
    	lineDTO.setLengthUnit(null);
    	lineDTO.setOffset2Value(null);
    	lineDTO.setOffset1Value(null);
    	lineDTO.setGridStartPoint(null);
    	lineDTO.setGridEndPoint(null);
		return lineDTO;
	}
}
