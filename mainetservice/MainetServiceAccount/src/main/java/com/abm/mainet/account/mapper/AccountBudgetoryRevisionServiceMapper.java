
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetoryRevisionEntity;
import com.abm.mainet.account.dto.AccountBudgetoryRevisionBean;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetoryRevisionServiceMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetoryRevisionServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetoryRevisionEntity' to 'AccountBudgetoryRevisionBean'
     * @param tbAcBudgetoryRevisionEntity
     */
    public AccountBudgetoryRevisionBean mapAccountBudgetoryRevisionEntityToAccountBudgetoryRevisionBean(
            final AccountBudgetoryRevisionEntity tbAcBudgetoryRevisionEntity) {
        if (tbAcBudgetoryRevisionEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetoryRevisionBean tbAcBudgetoryRevision = map(tbAcBudgetoryRevisionEntity,
                AccountBudgetoryRevisionBean.class);

        if (tbAcBudgetoryRevisionEntity.getTbAcBudgetCodeMaster() != null) {
            tbAcBudgetoryRevision.setPrBudgetCodeid(tbAcBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
        }

        return tbAcBudgetoryRevision;
    }

    /**
     * Mapping from 'AccountBudgetoryRevisionBean' to 'AccountBudgetoryRevisionEntity'
     * @param tbAcBudgetoryRevision
     * @param tbAcBudgetoryRevisionEntity
     */
    public void mapAccountBudgetoryRevisionBeanToAccountBudgetoryRevisionEntity(
            final AccountBudgetoryRevisionBean tbAcBudgetoryRevision,
            final AccountBudgetoryRevisionEntity tbAcBudgetoryRevisionEntity) {
        if (tbAcBudgetoryRevision == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcBudgetoryRevision, tbAcBudgetoryRevisionEntity);

        if (hasLinkToTbAcBudgetCodeMaster(tbAcBudgetoryRevision)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (tbAcBudgetoryRevision.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(tbAcBudgetoryRevision.getPrBudgetCodeid().longValue());

            tbAcBudgetoryRevisionEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            tbAcBudgetoryRevisionEntity.setTbAcBudgetCodeMaster(null);
        }

    }

    private boolean hasLinkToTbAcBudgetCodeMaster(final AccountBudgetoryRevisionBean tbAcBudgetoryRevision) {
        if (tbAcBudgetoryRevision.getPrBudgetCodeid() != null) {
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
