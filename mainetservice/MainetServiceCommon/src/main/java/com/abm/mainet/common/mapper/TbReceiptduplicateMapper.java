/**
 * 
 */
package com.abm.mainet.common.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbReceiptDuplicateEntity;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class TbReceiptduplicateMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public TbReceiptduplicateMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public TbReceiptDuplicateDTO mapTbReceiptDuplicateEntityToTbReceiptDuplicateDTO(TbReceiptDuplicateEntity entity) {
		if (entity == null) {
			return null;
		}
		final TbReceiptDuplicateDTO dto = map(entity, TbReceiptDuplicateDTO.class);

		return dto;
	}

	public TbReceiptDuplicateEntity mapTbReceiptDuplicateDTOToTbReceiptDuplicateEntity(TbReceiptDuplicateDTO dto) {
		if (dto == null) {
			return null;
		}
		final TbReceiptDuplicateEntity entity = map(dto, TbReceiptDuplicateEntity.class);

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
