package com.abm.mainet.asset.mapper;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetTransfer;
import com.abm.mainet.asset.ui.dto.TransferDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class TransferMapper {

	/**
	 * @param dto
	 * @return
	 */
	public static AssetTransfer mapToEntity(TransferDTO dto) {
		AssetTransfer entity = new AssetTransfer();
		AssetInformation info = new AssetInformation();
		info.setAssetId(dto.getAssetCode());
		if (dto.getTransferId() != null) {
			entity.setTransferId(dto.getTransferId());
		}
		entity.setAssetId(info);
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreationDate(dto.getCreationDate());
		entity.setDocDate(dto.getDocDate());
		entity.setLgIpMac(dto.getLgIpMac());
		entity.setLgIpMacUpd(dto.getLgIpMacUpd());
		entity.setOrgId(dto.getOrgId());
		entity.setPostDate(dto.getPostDate());
		entity.setRemark(dto.getRemark());
		//column name needs to be updated
		entity.setTransferCostCenter(dto.getTransferDepartment());
		entity.setTransferEmployee(dto.getTransferEmployee());
		entity.setTransferLocation(dto.getTransferLocation());
		entity.setTransferType(dto.getTransferType());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setUpdatedDate(dto.getUpdatedDate());
		//D#96358
		entity.setDeptCode(dto.getDeptCode());
		return entity;
	}

	public static TransferDTO mapToDTO(AssetTransfer entity) {
		TransferDTO dto = new TransferDTO();
		AssetInformation info = entity.getAssetId();
		dto.setAssetCode(info.getAssetId());
		dto.setAssetSrNo(info.getAstCode());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreationDate(entity.getCreationDate());
		dto.setDocDate(entity.getDocDate());
		dto.setLgIpMac(entity.getLgIpMac());
		dto.setLgIpMacUpd(entity.getLgIpMacUpd());
		dto.setOrgId(entity.getOrgId());
		dto.setPostDate(entity.getPostDate());
		dto.setRemark(entity.getRemark());
		//column name needs to be updated
		dto.setTransferDepartment(entity.getTransferCostCenter());
		dto.setTransferEmployee(entity.getTransferEmployee());
		dto.setTransferLocation(entity.getTransferLocation());
		dto.setTransferType(entity.getTransferType());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setTransferId(entity.getTransferId());
		dto.setUpdatedDate(entity.getUpdatedDate());
		dto.setDeptCode(entity.getDeptCode());
		return dto;

	}
}
