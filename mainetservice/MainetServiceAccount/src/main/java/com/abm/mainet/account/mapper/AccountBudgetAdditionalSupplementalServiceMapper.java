package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetAdditionalSupplementalEntity;
import com.abm.mainet.account.dto.AccountBudgetAdditionalSupplementalBean;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetAdditionalSupplementalServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetAdditionalSupplementalServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetAdditionalSupplementalBeanEntity' to 'AccountBudgetAdditionalSupplementalBean'
     * @param accountBudgetAdditionalSupplementalEntity
     */
    public AccountBudgetAdditionalSupplementalBean mapAccountBudgetAdditionalSupplementalBeanEntityToAccountBudgetAdditionalSupplementalBean(
            final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity) {
        if (accountBudgetAdditionalSupplementalEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalBean = map(
                accountBudgetAdditionalSupplementalEntity, AccountBudgetAdditionalSupplementalBean.class);

        if (accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster() != null) {
            accountBudgetAdditionalSupplementalBean
                    .setPrBudgetCodeid(accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
        }

        // --- Link mapping ( link to Employee )
        if (accountBudgetAdditionalSupplementalEntity.getEmployee() != null) {
            accountBudgetAdditionalSupplementalBean
                    .setApprovedBy(accountBudgetAdditionalSupplementalEntity.getEmployee().getEmpId());
        }

        return accountBudgetAdditionalSupplementalBean;
    }

    /**
     * Mapping from 'AccountBudgetAdditionalSupplementalBean' to 'AccountBudgetAdditionalSupplementalBeanEntity'
     * @param accountBudgetAdditionalSupplementalBean
     * @param accountBudgetAdditionalSupplementalEntity
     */
    public void mapAccountBudgetAdditionalSupplementalBeanToAccountBudgetAdditionalSupplementalEntity(
            final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalBean,
            final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity) {
        if (accountBudgetAdditionalSupplementalBean == null) {
            return;
        }

        // --- Generic mapping
        map(accountBudgetAdditionalSupplementalBean, accountBudgetAdditionalSupplementalEntity);

        // --- Link mapping ( link : tbAcProjectedexpenditure )
        if (hasLinkToTbAcBudgetCodeMaster(accountBudgetAdditionalSupplementalBean)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (accountBudgetAdditionalSupplementalBean.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(accountBudgetAdditionalSupplementalBean.getPrBudgetCodeid().longValue());

            accountBudgetAdditionalSupplementalEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            accountBudgetAdditionalSupplementalEntity.setTbAcBudgetCodeMaster(null);
        }

        // --- Link mapping ( link : accountBudgetAdditionalSupplementalBean )
        if (hasLinkToEmployee(accountBudgetAdditionalSupplementalBean)) {
            final Employee employee = new Employee();
            employee.setEmpId(accountBudgetAdditionalSupplementalBean.getApprovedBy());
            if (employee.getEmpId() == null) {

            }
            accountBudgetAdditionalSupplementalEntity.setEmployee(employee);
        } else {
            accountBudgetAdditionalSupplementalEntity.setEmployee(null);
        }

    }

    /**
     * Verify that Employee id is valid.
     * @param Employee Employee
     * @return boolean
     */
    private boolean hasLinkToEmployee(final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalBean) {
        if (accountBudgetAdditionalSupplementalBean.getApprovedBy() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcBudgetCodeMaster(
            final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalBean) {
        if (accountBudgetAdditionalSupplementalBean.getPrBudgetCodeid() != null) {
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
