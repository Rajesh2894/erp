
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetReappropriationMasterEntity;
import com.abm.mainet.account.dto.AccountBudgetReappropriationMasterBean;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AccountBudgetReappropriationMasterServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetReappropriationMasterServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetReappropriationMasterBeanEntity' to 'AccountBudgetReappropriationMasterBean'
     * @param accountBudgetReappropriationMasterEntity
     */
    public AccountBudgetReappropriationMasterBean mapAccountBudgetReappropriationMasterBeanEntityToAccountBudgetReappropriationMasterBean(
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity) {
        if (accountBudgetReappropriationMasterEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterBean = map(
                accountBudgetReappropriationMasterEntity, AccountBudgetReappropriationMasterBean.class);

        if (accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster() != null) {
            accountBudgetReappropriationMasterBean
                    .setPrBudgetCodeid(accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
        }

        // --- Link mapping ( link to Employee )
        if (accountBudgetReappropriationMasterEntity.getEmployee() != null) {
            accountBudgetReappropriationMasterBean
                    .setApprovedBy(accountBudgetReappropriationMasterEntity.getEmployee().getEmpId());
        }

        return accountBudgetReappropriationMasterBean;
    }

    /**
     * Mapping from 'AccountBudgetReappropriationMasterBean' to 'AccountBudgetReappropriationMasterBeanEntity'
     * @param accountBudgetReappropriationMasterBean
     * @param accountBudgetReappropriationMasterEntity
     */
    public void mapAccountBudgetReappropriationMasterBeanToAccountBudgetReappropriationMasterEntity(
            final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterBean,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity) {
        if (accountBudgetReappropriationMasterBean == null) {
            return;
        }

        // --- Generic mapping
        map(accountBudgetReappropriationMasterBean, accountBudgetReappropriationMasterEntity);

        if (accountBudgetReappropriationMasterBean.getDpDeptid() != null) {
            accountBudgetReappropriationMasterEntity.setDepartment(accountBudgetReappropriationMasterBean.getDpDeptid());
        }

        // --- Link mapping ( link : accountBudgetReappropriationMasterBean )
        if (hasLinkToEmployee(accountBudgetReappropriationMasterBean)) {
            final Employee employee = new Employee();
            employee.setEmpId(accountBudgetReappropriationMasterBean.getApprovedBy());
            if (employee.getEmpId() == null) {

            }
            accountBudgetReappropriationMasterEntity.setEmployee(employee);
        } else {
            accountBudgetReappropriationMasterEntity.setEmployee(null);
        }

        if (hasLinkToTbAcBudgetCodeMaster(accountBudgetReappropriationMasterBean)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (accountBudgetReappropriationMasterBean.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(accountBudgetReappropriationMasterBean.getPrBudgetCodeid().longValue());

            accountBudgetReappropriationMasterEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            accountBudgetReappropriationMasterEntity.setTbAcBudgetCodeMaster(null);
        }

    }

    /**
     * Verify that Employee id is valid.
     * @param Employee Employee
     * @return boolean
     */
    private boolean hasLinkToEmployee(final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterBean) {
        if (accountBudgetReappropriationMasterBean.getApprovedBy() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcBudgetCodeMaster(
            final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterBean) {
        if (accountBudgetReappropriationMasterBean.getPrBudgetCodeid() != null) {
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