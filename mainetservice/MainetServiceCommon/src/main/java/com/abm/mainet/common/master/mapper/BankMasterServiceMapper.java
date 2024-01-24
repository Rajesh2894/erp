package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class BankMasterServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public BankMasterServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetProjectedRevenueEntryBeanEntity' to 'AccountBudgetProjectedRevenueEntryBean'
     * @param tbAcProjectedrevenueEntity
     */
    public BankMasterDTO mapBankMasterEntityToBankMasterDTO(final BankMasterEntity bankMasterEntity) {
        if (bankMasterEntity == null) {
            return null;
        }

        // --- Generic mapping
        final BankMasterDTO tbBankMasterDTO = map(bankMasterEntity, BankMasterDTO.class);

        return tbBankMasterDTO;
    }

    /**
     * Mapping from 'AccountBudgetProjectedRevenueEntryBean' to 'AccountBudgetProjectedRevenueEntryBeanEntity'
     * @param tbAcProjectedrevenue
     * @param tbAcProjectedrevenueEntity
     */
    public void mapBankMasterDTOToBankMasterEntity(final BankMasterDTO bankMasterDTO, final BankMasterEntity bankMasterEntity) {
        if (bankMasterDTO == null) {
            return;
        }

        // --- Generic mapping
        map(bankMasterDTO, bankMasterEntity);

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
