
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetReappropriationMasterEntity;
import com.abm.mainet.account.domain.AccountBudgetReappropriationTrMasterEntity;
import com.abm.mainet.account.dto.AccountBudgetReappropriationTrMasterBean;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AccountBudgetReappropriationTrMasterServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetReappropriationTrMasterServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbAcProjectedprovisionadjTr' to 'TbAcProjectedprovisionadjTrEntity'
     * @param tbAcProjectedprovisionadjTr
     * @param tbAcProjectedprovisionadjTrEntity
     */
    public void mapTbAcProjectedprovisionadjTrToTbAcProjectedprovisionadjTrEntity(
            final AccountBudgetReappropriationTrMasterBean tbAcProjectedprovisionadjTr,
            final AccountBudgetReappropriationTrMasterEntity tbAcProjectedprovisionadjTrEntity) {
        if (tbAcProjectedprovisionadjTr == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcProjectedprovisionadjTr, tbAcProjectedprovisionadjTrEntity);

        if (hasLinkToTbAcProjectedprovisionadj(tbAcProjectedprovisionadjTr)) {
            final AccountBudgetReappropriationMasterEntity tbAcProjectedprovisionadj = new AccountBudgetReappropriationMasterEntity();
            tbAcProjectedprovisionadj.setPaAdjid(tbAcProjectedprovisionadjTr.getPaAdjid());
            tbAcProjectedprovisionadjTrEntity.setTbAcProjectedprovisionadj(tbAcProjectedprovisionadj);
        } else {
            tbAcProjectedprovisionadjTrEntity.setTbAcProjectedprovisionadj(null);
        }

        if (hasLinkToTbAcBudgetCodeMaster(tbAcProjectedprovisionadjTr)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (tbAcProjectedprovisionadjTr.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(tbAcProjectedprovisionadjTr.getPrBudgetCodeid().longValue());

            tbAcProjectedprovisionadjTrEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            tbAcProjectedprovisionadjTrEntity.setTbAcBudgetCodeMaster(null);
        }

        if (hasLinkToTbDepartment(tbAcProjectedprovisionadjTr)) {
            final Department tbDepartment = new Department();
            tbDepartment.setDpDeptid(tbAcProjectedprovisionadjTr.getDpDeptid());
            tbAcProjectedprovisionadjTrEntity.setDepartment(tbDepartment.getDpDeptid());
        } else {
            tbAcProjectedprovisionadjTrEntity.setDepartment(null);
        }

    }

    /**
     * Verify that TbAcProjectedprovisionadj id is valid.
     * @param TbAcProjectedprovisionadj TbAcProjectedprovisionadj
     * @return boolean
     */
    private boolean hasLinkToTbAcProjectedprovisionadj(
            final AccountBudgetReappropriationTrMasterBean tbAcProjectedprovisionadjTr) {
        if (tbAcProjectedprovisionadjTr.getPaAdjid() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcBudgetCodeMaster(final AccountBudgetReappropriationTrMasterBean tbAcProjectedprovisionadjTr) {
        if (tbAcProjectedprovisionadjTr.getPrBudgetCodeid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbDepartment id is valid.
     * @param TbDepartment TbDepartment
     * @return boolean
     */
    private boolean hasLinkToTbDepartment(final AccountBudgetReappropriationTrMasterBean tbAcProjectedprovisionadjTr) {
        if (tbAcProjectedprovisionadjTr.getDpDeptid() != null) {
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