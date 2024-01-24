package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.TbAcPayToBankEntity;
import com.abm.mainet.account.dto.TbAcPayToBank;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbAcPayToBankServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbAcPayToBankServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbAcPayToBankEntity' to 'TbAcPayToBank'
     * @param tbAcPayToBankEntity
     */
    public TbAcPayToBank mapTbAcPayToBankEntityToTbAcPayToBank(final TbAcPayToBankEntity tbAcPayToBankEntity) {
        if (tbAcPayToBankEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbAcPayToBank tbAcPayToBank = map(tbAcPayToBankEntity, TbAcPayToBank.class);

        // --- Link mapping ( link to TbAcSecondaryheadMaster )
        if (tbAcPayToBankEntity.getSecondaryAccountCodeMaster() != null) {
            tbAcPayToBank.setSacHeadId(tbAcPayToBankEntity.getSecondaryAccountCodeMaster().getSacHeadId());
            tbAcPayToBank.setAcHeadCode(tbAcPayToBankEntity.getSecondaryAccountCodeMaster().getAcHeadCode());
        }

        return tbAcPayToBank;
    }

    /**
     * Mapping from 'TbAcPayToBank' to 'TbAcPayToBankEntity'
     * @param tbAcPayToBank
     * @param tbAcPayToBankEntity
     */
    public void mapTbAcPayToBankToTbAcPayToBankEntity(final TbAcPayToBank tbAcPayToBank,
            final TbAcPayToBankEntity tbAcPayToBankEntity) {
        if (tbAcPayToBank == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcPayToBank, tbAcPayToBankEntity);

        // --- Link mapping ( link : tbAcBudgetCode )
        if (hasLinkToTbAcSecondaryheadMaster(tbAcPayToBank)) {
            final AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster = new AccountHeadSecondaryAccountCodeMasterEntity();
            if (tbAcPayToBank.getSacHeadId() == null) {

            }
            tbAcSecondaryheadMaster.setSacHeadId(tbAcPayToBank.getSacHeadId().longValue());

            tbAcPayToBankEntity.setSecondaryAccountCodeMaster(tbAcSecondaryheadMaster);
        } else {
            tbAcPayToBankEntity.setSecondaryAccountCodeMaster(null);
        }

    }

    /**
     * Verify that TbAcSecondaryheadMaster id is valid.
     * @param TbAcSecondaryheadMaster TbAcSecondaryheadMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcSecondaryheadMaster(final TbAcPayToBank tbAcPayToBank) {
        if (tbAcPayToBank.getSacHeadId() != null) {
            return true;
        }
        return false;
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