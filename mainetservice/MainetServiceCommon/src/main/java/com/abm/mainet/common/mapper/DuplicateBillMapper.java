/**
 * 
 */
package com.abm.mainet.common.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.DuplicateBillEntity;
import com.abm.mainet.common.dto.DuplicateBillDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class DuplicateBillMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public DuplicateBillMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * To map DuplicateBill DTO To DuplicateBillEntity
	 * 
	 * @param Duplicate
	 *            Bill Entity
	 * @return DuplicateBillDTO
	 */

	public DuplicateBillDTO mapToDuplicateBillDTO(DuplicateBillEntity entity) {
		if (entity == null) {
			return null;
		}
		final DuplicateBillDTO dto = map(entity, DuplicateBillDTO.class);

		return dto;
	}

	/**
	 * To map Duplicate Bill Entity To DuplicateBillDTO
	 * 
	 * @param DuplicateBill
	 *            DTO
	 * @return Duplicate Bill Entity
	 */
	public DuplicateBillEntity mapToDuplicateBillEntity(DuplicateBillDTO dto) {
		if (dto == null) {
			return null;
		}
		final DuplicateBillEntity entity = map(dto, DuplicateBillEntity.class);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.common.utility.AbstractServiceMapper#getModelMapper()
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
}
