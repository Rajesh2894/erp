
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetAllocationEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetAllocationBean;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AccountBudgetAllocationServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetAllocationServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetAllocationEntity' to 'TbAcBudgetallocation'
     * @param AccountBudgetAllocationEntity
     */
    public AccountBudgetAllocationBean mapAccountBudgetAllocationEntityToTbAcBudgetallocation(
            final AccountBudgetAllocationEntity accountBudgetAllocationEntity) {
        if (accountBudgetAllocationEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetAllocationBean tbAcBudgetallocation = map(accountBudgetAllocationEntity,
                AccountBudgetAllocationBean.class);

        if (accountBudgetAllocationEntity.getTbAcBudgetCodeMaster() != null) {
            tbAcBudgetallocation.setPrBudgetCodeid(accountBudgetAllocationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
        }

        // --- Link mapping ( link to TbAcProjectedExpenditure )
        if (accountBudgetAllocationEntity.getTbAcProjectedExpenditure() != null) {
            tbAcBudgetallocation
                    .setPrExpenditureid(accountBudgetAllocationEntity.getTbAcProjectedExpenditure().getPrExpenditureid());
        }
        // --- Link mapping ( link to TbAcProjectedrevenue )
        if (accountBudgetAllocationEntity.getTbAcProjectedrevenue() != null) {
            tbAcBudgetallocation.setPrProjectionid(accountBudgetAllocationEntity.getTbAcProjectedrevenue().getPrProjectionid());
        }

        return tbAcBudgetallocation;
    }

    /**
     * Mapping from 'TbAcBudgetallocation' to 'AccountBudgetAllocationEntity'
     * @param tbAcBudgetallocation
     * @param AccountBudgetAllocationEntity
     */
    public void mapTbAcBudgetallocationToAccountBudgetAllocationEntity(final AccountBudgetAllocationBean tbAcBudgetallocation,
            final AccountBudgetAllocationEntity accountBudgetAllocationEntity) {
        if (tbAcBudgetallocation == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcBudgetallocation, accountBudgetAllocationEntity);

        // --- Link mapping ( link : tbAcProjectedexpenditure )
        if (hasLinkToTbAcBudgetCodeMaster(tbAcBudgetallocation)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (tbAcBudgetallocation.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(tbAcBudgetallocation.getPrBudgetCodeid().longValue());

            accountBudgetAllocationEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            accountBudgetAllocationEntity.setTbAcBudgetCodeMaster(null);
        }

        // --- Link mapping ( link : tbAcBudgetallocation )
        if (hasLinkToTbAcProjectedExpenditure(tbAcBudgetallocation)) {
            final AccountBudgetProjectedExpenditureEntity tbAcProjectedExpenditure = new AccountBudgetProjectedExpenditureEntity();
            tbAcProjectedExpenditure.setPrExpenditureid(tbAcBudgetallocation.getPrExpenditureid());
            accountBudgetAllocationEntity.setTbAcProjectedExpenditure(tbAcProjectedExpenditure);
        } else {
            accountBudgetAllocationEntity.setTbAcProjectedExpenditure(null);
        }

        // --- Link mapping ( link : tbAcBudgetallocation )
        if (hasLinkToTbAcProjectedrevenue(tbAcBudgetallocation)) {
            final AccountBudgetProjectedRevenueEntryEntity tbAcProjectedrevenue = new AccountBudgetProjectedRevenueEntryEntity();
            tbAcProjectedrevenue.setPrProjectionid(tbAcBudgetallocation.getPrProjectionid());
            accountBudgetAllocationEntity.setTbAcProjectedrevenue(tbAcProjectedrevenue);
        } else {
            accountBudgetAllocationEntity.setTbAcProjectedrevenue(null);
        }

    }

    private boolean hasLinkToTbAcBudgetCodeMaster(final AccountBudgetAllocationBean tbAcBudgetallocation) {
        if (tbAcBudgetallocation.getPrBudgetCodeid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbAcProjectedExpenditure id is valid.
     * @param TbAcProjectedExpenditure TbAcProjectedExpenditure
     * @return boolean
     */
    private boolean hasLinkToTbAcProjectedExpenditure(final AccountBudgetAllocationBean tbAcBudgetallocation) {
        if (tbAcBudgetallocation.getPrExpenditureid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbAcProjectedrevenue id is valid.
     * @param TbAcProjectedrevenue TbAcProjectedrevenue
     * @return boolean
     */
    private boolean hasLinkToTbAcProjectedrevenue(final AccountBudgetAllocationBean tbAcBudgetallocation) {
        if (tbAcBudgetallocation.getPrProjectionid() != null) {
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