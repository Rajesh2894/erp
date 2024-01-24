
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetEstimationPreparationEntity;
import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetEstimationPreparationServiceMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetEstimationPreparationServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetEstimationPreparationEntity' to 'AccountBudgetEstimationPreparationBean'
     * @param tbAcBudgetoryEstimateEntity
     */
    public AccountBudgetEstimationPreparationBean mapAccountBudgetEstimationPreparationEntityToAccountBudgetEstimationPreparationBean(
            final AccountBudgetEstimationPreparationEntity tbAcBudgetoryEstimateEntity) {
        if (tbAcBudgetoryEstimateEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetEstimationPreparationBean tbAcBudgetoryEstimate = map(tbAcBudgetoryEstimateEntity,
                AccountBudgetEstimationPreparationBean.class);

        if (tbAcBudgetoryEstimateEntity.getTbAcBudgetCodeMaster() != null) {
            tbAcBudgetoryEstimate.setPrBudgetCodeid(tbAcBudgetoryEstimateEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
        }

        return tbAcBudgetoryEstimate;
    }

    /**
     * Mapping from 'AccountBudgetEstimationPreparationBean' to 'AccountBudgetEstimationPreparationEntity'
     * @param tbAcBudgetoryEstimate
     * @param tbAcBudgetoryEstimateEntity
     */
    public void mapAccountBudgetEstimationPreparationBeanToAccountBudgetEstimationPreparationEntity(
            final AccountBudgetEstimationPreparationBean tbAcBudgetoryEstimate,
            final AccountBudgetEstimationPreparationEntity tbAcBudgetoryEstimateEntity) {
        if (tbAcBudgetoryEstimate == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcBudgetoryEstimate, tbAcBudgetoryEstimateEntity);

        if (hasLinkToTbAcBudgetCodeMaster(tbAcBudgetoryEstimate)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (tbAcBudgetoryEstimate.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(tbAcBudgetoryEstimate.getPrBudgetCodeid().longValue());

            tbAcBudgetoryEstimateEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            tbAcBudgetoryEstimateEntity.setTbAcBudgetCodeMaster(null);
        }

    }

    private boolean hasLinkToTbAcBudgetCodeMaster(final AccountBudgetEstimationPreparationBean tbAcBudgetoryEstimate) {
        if (tbAcBudgetoryEstimate.getPrBudgetCodeid() != null) {
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
