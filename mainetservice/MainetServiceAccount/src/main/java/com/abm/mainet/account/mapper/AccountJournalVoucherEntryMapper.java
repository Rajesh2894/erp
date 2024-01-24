package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.common.model.mapping.AbstractModelMapper;

@Component
public class AccountJournalVoucherEntryMapper extends AbstractModelMapper {

    private ModelMapper modelMapper;

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountJournalVoucherEntryMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public AccountVoucherEntryEntity mapDtoToEntity(final AccountJournalVoucherEntryBean dto) {
        if (dto == null) {
            return null;
        }

        final AccountVoucherEntryEntity entity = map(dto, AccountVoucherEntryEntity.class);
        if (entity.getVouSubtypeCpdId() == null) {
            if (dto.getVoucherSubType() != null) {
                entity.setVouSubtypeCpdId(dto.getVoucherSubType());
            }
        }
        return entity;
    }

    public AccountJournalVoucherEntryBean mapEntityToDto(final AccountVoucherEntryEntity entity) {
        if (entity == null) {
            return null;
        }

        final AccountJournalVoucherEntryBean dto = map(entity, AccountJournalVoucherEntryBean.class);

        return dto;
    }

}
