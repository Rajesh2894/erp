/**
 * 
 */
package com.abm.mainet.asset.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetRevaluation;
import com.abm.mainet.asset.ui.dto.RevaluationDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class RevaluationMapper {

	/**
	 * @param dto
	 * @return
	 */
	public static AssetRevaluation mapToEntity(RevaluationDTO dto) {
		AssetRevaluation entity = new AssetRevaluation();
		AssetInformation info = new AssetInformation();
		info.setAssetId(dto.getAssetId());
		if (dto.getRevaluationId() != null) {
			entity.setRevaluationId(dto.getRevaluationId());
		}
		entity.setAssetId(info);
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreationDate(dto.getCreationDate());
		entity.setLgIpMac(dto.getLgIpMac());
		entity.setOrgId(dto.getOrgId());
		Date curDate = new Date();
		if(dto.getDocDate() != null) {
			entity.setDocDate(dto.getDocDate());
		}
		entity.setPostDate(curDate);
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setUpdatedDate(dto.getUpdatedDate());
		entity.setLgIpMacUpd(dto.getLgIpMacUpd());
		entity.setAmount(dto.getNewAmount());
		entity.setRemarks(dto.getRemarks());
		entity.setImpCost(dto.getImpCost());
		entity.setImpDesc(dto.getImpDesc());
		entity.setImpType(dto.getImpType());
		entity.setUpdUsefulLife(dto.getUpdUsefulLife());
		entity.setOrigUsefulLife(dto.getOrigUsefulLife());
		entity.setPayAdviceNo(dto.getPayAdviceNo());
		return entity;
	}

	public static RevaluationDTO mapToDTO(AssetRevaluation entity) {
		RevaluationDTO dto = new RevaluationDTO();
		AssetInformation info = entity.getAssetId();
		dto.setRevaluationId(entity.getRevaluationId());
		dto.setAssetId(info.getAssetId());
		dto.setSerialNo(info.getAstCode());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreationDate(entity.getCreationDate());
		dto.setLgIpMac(entity.getLgIpMac());
		dto.setOrgId(entity.getOrgId());
		dto.setDocDate(entity.getDocDate());
		dto.setPostDate(entity.getPostDate());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setUpdatedDate(entity.getUpdatedDate());
		dto.setLgIpMacUpd(entity.getLgIpMacUpd());
		dto.setRemarks(entity.getRemarks());
		dto.setNewAmount(entity.getAmount());
		dto.setImpCost(entity.getImpCost());
		dto.setImpDesc(entity.getImpDesc());
		dto.setImpType(entity.getImpType());
		dto.setUpdUsefulLife(entity.getUpdUsefulLife());
		dto.setOrigUsefulLife(entity.getOrigUsefulLife());
		dto.setPayAdviceNo(entity.getPayAdviceNo());
		return dto;

	}
}
