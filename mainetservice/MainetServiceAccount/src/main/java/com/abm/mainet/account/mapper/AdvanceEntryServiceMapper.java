package com.abm.mainet.account.mapper;

import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class AdvanceEntryServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AdvanceEntryServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AdvanceEntryDTOEntity' to 'AdvanceEntryDTO'
     * @param tbAcBudgetCodeEntity
     */
    public AdvanceEntryDTO mapAdvanceEntryEntityToAdvanceEntryDTO(final AdvanceEntryEntity advanceEntryEntity) {
        if (advanceEntryEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AdvanceEntryDTO tbAcBudgetCode = map(advanceEntryEntity, AdvanceEntryDTO.class);

        return tbAcBudgetCode;
    }

    /**
     * Mapping from 'AccountBudgetCodeBean' to 'AccountBudgetCodeBeanEntity'
     * @param tbAcBudgetCode
     * @param tbAcBudgetCodeEntity
     * @throws ParseException
     */
    public void mapAdvanceEntryDTOToAdvanceEntryEntity(final AdvanceEntryDTO advanceEntryDTO,
            final AdvanceEntryEntity advanceEntryEntity)
            throws ParseException {
        if (advanceEntryDTO == null) {
            return;
        }

        // --- Generic mapping
        map(advanceEntryDTO, advanceEntryEntity);
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
