
package com.abm.mainet.materialmgmt.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.materialmgmt.domain.ItemMasterConversionEntity;
import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;
import com.abm.mainet.materialmgmt.repository.ItemMasterConversionRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterConversionDTO;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;

@Component
public class ItemMasterServiceMapper extends AbstractServiceMapper {

	@Resource
	private ItemMasterConversionRepository itemMasterConversionRepository;

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;

	/**
	 * Constructor.
	 */
	public ItemMasterServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ItemMasterDTO' to 'ItemMasterEntity'
	 */
	public void mapItemMasterDtoToItemMasterEntity(final ItemMasterDTO itemMasterDto,
			final ItemMasterEntity itemMasterEntity, final Organisation org,final int langId,final Employee emp) {

		final List<ItemMasterConversionDTO> itemMasterConversionList = itemMasterDto.getItemMasterConversionDtoList();
		ItemMasterConversionEntity itemMasterConversionEntity = null;
		final List<ItemMasterConversionEntity> itemConversionEntityList = new ArrayList<>();
		map(itemMasterDto, itemMasterEntity);

		if ((itemMasterConversionList != null) && !itemMasterConversionList.isEmpty()) {

			for (final ItemMasterConversionDTO itemMasterConversionDto : itemMasterDto
					.getItemMasterConversionDtoList()) {

				// CREATE MODE
				if (itemMasterConversionDto.getConvId() == null) {

					itemMasterConversionEntity = new ItemMasterConversionEntity();
					itemMasterConversionEntity.setConvUom(itemMasterConversionDto.getConvUom());
					itemMasterConversionEntity.setCreatedDate(new Date());
					itemMasterConversionEntity.setLangId(langId);
					itemMasterConversionEntity.setLgIpMac(org.getLgIpMac());
					itemMasterConversionEntity.setLgIpMacUpd(org.getLgIpMacUpd());
					itemMasterConversionEntity.setOrgId(org.getOrgid());
					itemMasterConversionEntity.setStatus(itemMasterDto.getStatus());
					itemMasterConversionEntity.setUnits(itemMasterConversionDto.getUnits());
					itemMasterConversionEntity.setUpdatedBy(org.getUpdatedBy());
					itemMasterConversionEntity.setUserId(emp.getUserId());
					itemMasterConversionEntity.setUpdatedDate(new Date());
					itemMasterConversionEntity.setItemMasterEntity(itemMasterEntity);
				}
				// UPDATE MODE
				else {
					Long convId = itemMasterConversionDto.getConvId();
					itemMasterConversionEntity = itemMasterConversionRepository.findItemMasterDetailsByConvId(convId);
					if (null != itemMasterConversionEntity) {
						itemMasterConversionEntity.setConvUom((itemMasterConversionDto.getConvUom()));
						itemMasterConversionEntity.setUnits(itemMasterConversionDto.getUnits());
						itemMasterConversionEntity.setUpdatedDate(new Date());
						itemMasterConversionEntity.setUpdatedBy(emp.getUpdatedBy());
						itemMasterConversionEntity.setStatus(itemMasterDto.getStatus());
					}
				}
				itemConversionEntityList.add(itemMasterConversionEntity);

			}

		}
		if (null != itemMasterEntity.getItemMasterConversionEntity()
				&& itemMasterEntity.getItemMasterConversionEntity().size() > 0) {
			itemMasterEntity.getItemMasterConversionEntity().clear();
		}

		itemMasterEntity.setItemMasterConversionEntity(itemConversionEntityList);

	}

	/**
	 * Mapping from 'ItemMasterEntity' to 'ItemMasterDTO'
	 */
	public ItemMasterDTO mapItemMasterEntityToItemMasterDTO(final ItemMasterEntity itemMasterEntity) {

		ItemMasterConversionDTO itemMasterConversionDTO = null;
		if (itemMasterEntity == null) {
			return null;
		}

		// --- Generic mapping
		final ItemMasterDTO itemMasterDTO = map(itemMasterEntity, ItemMasterDTO.class);

		List<ItemMasterConversionDTO> itemMasterConversionDtoList = new ArrayList<>();

		if (itemMasterEntity.getItemMasterConversionEntity() != null
				&& itemMasterEntity.getItemMasterConversionEntity().size() > 0) {
			for (final ItemMasterConversionEntity itemMasterConversionEntity : itemMasterEntity
					.getItemMasterConversionEntity()) {

				itemMasterConversionDTO = new ItemMasterConversionDTO();
				itemMasterConversionDTO.setConvId(itemMasterConversionEntity.getConvId());
				itemMasterConversionDTO.setItemId(itemMasterConversionEntity.getItemMasterEntity().getItemId());
				itemMasterConversionDTO.setConvUom(itemMasterConversionEntity.getConvUom());
				itemMasterConversionDTO.setLmodDate(new Date());
				itemMasterConversionDTO.setLangId(itemMasterConversionEntity.getLangId());
				itemMasterConversionDTO.setLgIpMac(itemMasterConversionEntity.getLgIpMac());
				itemMasterConversionDTO.setLgIpMacUpd(itemMasterConversionEntity.getLgIpMacUpd());
				itemMasterConversionDTO.setOrgId(itemMasterConversionEntity.getOrgId());
				itemMasterConversionDTO.setStatus(itemMasterConversionEntity.getStatus());
				itemMasterConversionDTO.setUnits(itemMasterConversionEntity.getUnits());
				itemMasterConversionDTO.setUpdatedBy(itemMasterConversionEntity.getUpdatedBy());
				itemMasterConversionDTO.setUserId(itemMasterConversionEntity.getUserId());
				itemMasterConversionDTO.setUpdatedDate(itemMasterConversionEntity.getUpdatedDate());
				itemMasterConversionDtoList.add(itemMasterConversionDTO);
			}

			itemMasterDTO.setItemMasterConversionDtoList(itemMasterConversionDtoList);

		}

		return itemMasterDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
}
