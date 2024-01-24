package com.abm.mainet.materialmgmt.mapper;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.materialmgmt.domain.ExpiryItemsDetEntity;
import com.abm.mainet.materialmgmt.domain.ExpiryItemsEntity;
import com.abm.mainet.materialmgmt.dto.ExpiryItemDetailsDto;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;

@Component
public class ExpiryItemsMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public ExpiryItemsMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public ExpiryItemsEntity mapExpiryItemsDtoToExpiryItemsEntity(ExpiryItemsDto expiryItemsDto) {
		if (expiryItemsDto == null) {
			return null;
		}
		ExpiryItemsEntity entity = map(expiryItemsDto, ExpiryItemsEntity.class);

		ExpiryItemsDetEntity expiryItemsDet = null;
		for (ExpiryItemDetailsDto expiryItemDetailsDto : expiryItemsDto.getExpiryItemDetailsDtoList()) {
			expiryItemsDet = new ExpiryItemsDetEntity();
			expiryItemsDet.setExpiryItemId(expiryItemDetailsDto.getExpiryItemId());
			expiryItemsDet.setStoreId(entity.getStoreId());
			expiryItemsDet.setBinLocation(expiryItemDetailsDto.getBinLocation());
			expiryItemsDet.setItemId(expiryItemDetailsDto.getItemId());
			expiryItemsDet.setTransactionId(expiryItemDetailsDto.getTransactionId());
			expiryItemsDet.setItemNo(expiryItemDetailsDto.getItemNo());
			expiryItemsDet.setQuantity(expiryItemDetailsDto.getQuantity());
			expiryItemsDet.setFlag(expiryItemDetailsDto.getFlag());
			expiryItemsDet.setDisposedFlag(expiryItemDetailsDto.getDisposedFlag());
			expiryItemsDet.setRemarks(expiryItemDetailsDto.getRemarks());
			expiryItemsDet.setOrgId(expiryItemDetailsDto.getOrgId());
			expiryItemsDet.setUserId(expiryItemDetailsDto.getUserId());
			expiryItemsDet.setLangId(expiryItemDetailsDto.getLangId());
			expiryItemsDet.setUpdatedBy(expiryItemDetailsDto.getUpdatedBy());
			expiryItemsDet.setUpdatedDate(expiryItemDetailsDto.getUpdatedDate());
			expiryItemsDet.setLmodDate(expiryItemDetailsDto.getLmodDate());
			expiryItemsDet.setLgIpMac(expiryItemDetailsDto.getLgIpMac());
			expiryItemsDet.setLgIpMacUpd(expiryItemDetailsDto.getLgIpMacUpd());
			entity.addSExpiryItemsDetMapping(expiryItemsDet);
		}
		return entity;
	}

	public ExpiryItemsDto mapExpiryItemsEntityToExpiryItemsDto(ExpiryItemsEntity expiryItemsEntity) {
		if (expiryItemsEntity == null) {
			return null;
		}
		ExpiryItemsDto entitydto = map(expiryItemsEntity, ExpiryItemsDto.class);
		List<ExpiryItemDetailsDto> ExpiryItemDetailsDtoList = new ArrayList<ExpiryItemDetailsDto>();
		ExpiryItemDetailsDto expiryItemsDetdto = null;
		for (ExpiryItemsDetEntity expiryItemsDetEntity : expiryItemsEntity.getExpiryItemsDetEntity()) {
			expiryItemsDetdto = new ExpiryItemDetailsDto();
			expiryItemsDetdto.setExpiryItemId(expiryItemsDetEntity.getExpiryItemId());
			expiryItemsDetdto.setStoreId(expiryItemsDetEntity.getStoreId());
			expiryItemsDetdto.setBinLocation(expiryItemsDetEntity.getBinLocation());
			expiryItemsDetdto.setItemId(expiryItemsDetEntity.getItemId());
			expiryItemsDetdto.setTransactionId(expiryItemsDetEntity.getTransactionId());
			expiryItemsDetdto.setItemNo(expiryItemsDetEntity.getItemNo());
			expiryItemsDetdto.setQuantity(expiryItemsDetEntity.getQuantity());
			expiryItemsDetdto.setFlag(expiryItemsDetEntity.getFlag());
			expiryItemsDetdto.setDisposedFlag(expiryItemsDetEntity.getDisposedFlag());
			expiryItemsDetdto.setRemarks(expiryItemsDetEntity.getRemarks());
			expiryItemsDetdto.setOrgId(expiryItemsDetEntity.getOrgId());
			expiryItemsDetdto.setUserId(expiryItemsDetEntity.getUserId());
			expiryItemsDetdto.setLangId(expiryItemsDetEntity.getLangId());
			expiryItemsDetdto.setUpdatedBy(expiryItemsDetEntity.getUpdatedBy());
			expiryItemsDetdto.setUpdatedDate(expiryItemsDetEntity.getUpdatedDate());
			expiryItemsDetdto.setLmodDate(expiryItemsDetEntity.getLmodDate());
			expiryItemsDetdto.setLgIpMac(expiryItemsDetEntity.getLgIpMac());
			expiryItemsDetdto.setLgIpMacUpd(expiryItemsDetEntity.getLgIpMacUpd());
			ExpiryItemDetailsDtoList.add(expiryItemsDetdto);
		}
		entitydto.setExpiryItemDetailsDtoList(ExpiryItemDetailsDtoList);
		return entitydto;
	}
}
