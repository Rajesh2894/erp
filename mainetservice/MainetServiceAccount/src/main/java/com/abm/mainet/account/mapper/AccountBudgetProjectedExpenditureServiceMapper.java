
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetProjectedExpenditureServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetProjectedExpenditureServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetProjectedExpenditureBeanEntity' to 'AccountBudgetProjectedExpenditureBean'
     * @param tbAcProjectedExpenditureEntity
     */
    public AccountBudgetProjectedExpenditureBean mapAccountBudgetProjectedExpenditureEntityToAccountBudgetProjectedExpenditureBean(
            final AccountBudgetProjectedExpenditureEntity tbAcProjectedExpenditureEntity) {
        if (tbAcProjectedExpenditureEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetProjectedExpenditureBean tbAcProjectedexpenditure = map(tbAcProjectedExpenditureEntity,
                AccountBudgetProjectedExpenditureBean.class);

        if (tbAcProjectedExpenditureEntity.getTbAcBudgetCodeMaster() != null) {
            tbAcProjectedexpenditure
                    .setPrBudgetCodeid(tbAcProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
            tbAcProjectedexpenditure
                    .setPrExpBudgetCode(tbAcProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getPrBudgetCode());
        }

        if (tbAcProjectedExpenditureEntity.getTbDepartment() != null) {
            tbAcProjectedexpenditure.setDpDeptid(tbAcProjectedExpenditureEntity.getTbDepartment().getDpDeptid());
            tbAcProjectedexpenditure.setDpDeptName(tbAcProjectedExpenditureEntity.getTbDepartment().getDpDeptdesc());
           /* tbAcProjectedExpenditureEntity.getTbDepartment().getDpDeptcode()+ MainetConstants.HYPHEN +*/
        }

        return tbAcProjectedexpenditure;
    }

    /**
     * Mapping from 'AccountBudgetProjectedExpenditureBean' to 'AccountBudgetProjectedExpenditureBeanEntity'
     * @param tbAcProjectedexpenditure
     * @param tbAcProjectedExpenditureEntity
     */
    public void mapAccountBudgetProjectedExpenditureBeanToAccountBudgetProjectedExpenditureEntity(
            final AccountBudgetProjectedExpenditureBean tbAcProjectedexpenditure,
            final AccountBudgetProjectedExpenditureEntity tbAcProjectedExpenditureEntity) {
        if (tbAcProjectedexpenditure == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcProjectedexpenditure, tbAcProjectedExpenditureEntity);

        // --- Link mapping ( link : tbAcProjectedexpenditure )
        if (hasLinkToTbAcBudgetCodeMaster(tbAcProjectedexpenditure)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (tbAcProjectedexpenditure.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(tbAcProjectedexpenditure.getPrBudgetCodeid().longValue());

            tbAcProjectedExpenditureEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            tbAcProjectedExpenditureEntity.setTbAcBudgetCodeMaster(null);
        }

        // --- Link mapping ( link : tbAcProjectedexpenditure )
        if (hasLinkToTbDepartment(tbAcProjectedexpenditure)) {
            final Department tbDepartment = new Department();
            tbDepartment.setDpDeptid(tbAcProjectedexpenditure.getDpDeptid());
            tbAcProjectedExpenditureEntity.setTbDepartment(tbDepartment);
        } else {
            tbAcProjectedExpenditureEntity.setTbDepartment(null);
        }
    }

    /**
     * Verify that TbAcFieldMaster id is valid.
     * @param TbAcFieldMaster TbAcFieldMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcBudgetCodeMaster(final AccountBudgetProjectedExpenditureBean tbAcProjectedexpenditure) {
        if (tbAcProjectedexpenditure.getPrBudgetCodeid() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbDepartment(final AccountBudgetProjectedExpenditureBean tbAcProjectedexpenditure) {
        if (tbAcProjectedexpenditure.getDpDeptid() != null) {
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
