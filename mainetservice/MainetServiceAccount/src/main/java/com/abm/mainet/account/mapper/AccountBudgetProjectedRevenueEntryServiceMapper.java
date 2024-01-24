
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AccountBudgetProjectedRevenueEntryServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetProjectedRevenueEntryServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetProjectedRevenueEntryBeanEntity' to 'AccountBudgetProjectedRevenueEntryBean'
     * @param tbAcProjectedrevenueEntity
     */
    public AccountBudgetProjectedRevenueEntryBean mapAccountBudgetProjectedRevenueEntryBeanEntityToAccountBudgetProjectedRevenueEntryBean(
            final AccountBudgetProjectedRevenueEntryEntity tbAcProjectedrevenueEntity) {
        if (tbAcProjectedrevenueEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetProjectedRevenueEntryBean tbAcProjectedrevenue = map(tbAcProjectedrevenueEntity,
                AccountBudgetProjectedRevenueEntryBean.class);

        if (tbAcProjectedrevenueEntity.getTbAcBudgetCodeMaster() != null) {
            tbAcProjectedrevenue.setPrBudgetCodeid(tbAcProjectedrevenueEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
            tbAcProjectedrevenue.setPrRevBudgetCode(tbAcProjectedrevenueEntity.getTbAcBudgetCodeMaster().getPrBudgetCode());
        }

        // --- Link mapping ( link to TbFinancialyear )
        if (tbAcProjectedrevenueEntity.getTbDepartment() != null) {
            tbAcProjectedrevenue.setDpDeptid(tbAcProjectedrevenueEntity.getTbDepartment().getDpDeptid());
            tbAcProjectedrevenue.setDpDeptName(tbAcProjectedrevenueEntity.getTbDepartment().getDpDeptdesc()); /*tbAcProjectedrevenueEntity.getTbDepartment().getDpDeptcode()+ MainetConstants.HYPHEN + */
        }

        return tbAcProjectedrevenue;
    }

    /**
     * Mapping from 'AccountBudgetProjectedRevenueEntryBean' to 'AccountBudgetProjectedRevenueEntryBeanEntity'
     * @param tbAcProjectedrevenue
     * @param tbAcProjectedrevenueEntity
     */
    public void mapAccountBudgetProjectedRevenueEntryBeanToAccountBudgetProjectedRevenueEntryBeanEntity(
            final AccountBudgetProjectedRevenueEntryBean tbAcProjectedrevenue,
            final AccountBudgetProjectedRevenueEntryEntity tbAcProjectedrevenueEntity) {
        if (tbAcProjectedrevenue == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcProjectedrevenue, tbAcProjectedrevenueEntity);

        // --- Link mapping ( link : tbAcProjectedrevenue )
        if (hasLinkToTbAcBudgetCodeMaster(tbAcProjectedrevenue)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (tbAcProjectedrevenue.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(tbAcProjectedrevenue.getPrBudgetCodeid().longValue());

            tbAcProjectedrevenueEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            tbAcProjectedrevenueEntity.setTbAcBudgetCodeMaster(null);
        }

        // --- Link mapping ( link : tbAcProjectedrevenue )
        if (hasLinkToTbDepartment(tbAcProjectedrevenue)) {
            final Department tbDepartment = new Department();
            tbDepartment.setDpDeptid(tbAcProjectedrevenue.getDpDeptid());
            tbAcProjectedrevenueEntity.setTbDepartment(tbDepartment);
        } else {
            tbAcProjectedrevenueEntity.setTbDepartment(null);
        }

    }

    private boolean hasLinkToTbAcBudgetCodeMaster(final AccountBudgetProjectedRevenueEntryBean tbAcProjectedrevenue) {
        if (tbAcProjectedrevenue.getPrBudgetCodeid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbFinancialyear id is valid.
     * @param TbFinancialyear TbFinancialyear
     * @return boolean
     */
    private boolean hasLinkToTbDepartment(final AccountBudgetProjectedRevenueEntryBean tbAcProjectedrevenue) {
        if (tbAcProjectedrevenue.getDpDeptid() != null) {
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