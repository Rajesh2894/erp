package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountTDSTaxHeadsEntity;
import com.abm.mainet.account.dto.AccountTDSTaxHeadsBean;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AccountTDSTaxHeadsServiceMapper extends
        AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountTDSTaxHeadsServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(
                MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbAcTdsTaxheadsEntity' to 'TbAcTdsTaxheads'
     *
     * @param tbAcTdsTaxheadsEntity
     */
    public AccountTDSTaxHeadsBean mapTbAcTdsTaxheadsEntityToTbAcTdsTaxheads(
            final AccountTDSTaxHeadsEntity tbAcTdsTaxheadsEntity) {
        if (tbAcTdsTaxheadsEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountTDSTaxHeadsBean tbAcTdsTaxheads = map(tbAcTdsTaxheadsEntity, AccountTDSTaxHeadsBean.class);
        // --- Link mapping ( link to TbComparamDet )
        if (tbAcTdsTaxheadsEntity.getTbComparamDet() != null) {
            tbAcTdsTaxheads.setCpdIdDeductionType(tbAcTdsTaxheadsEntity.getTbComparamDet().getCpdId());
        }

        if (tbAcTdsTaxheadsEntity.getTbComparamDet().getCpdId() != null) {
            tbAcTdsTaxheads
                    .setTdsDescription(CommonMasterUtility.getNonHierarchicalLookUpObject(tbAcTdsTaxheadsEntity.getTbComparamDet()
                            .getCpdId()).getLookUpDesc());
        }

        if (tbAcTdsTaxheadsEntity.getBudgetCode() != null) {
            tbAcTdsTaxheads.setBudgetCodeId(tbAcTdsTaxheadsEntity.getBudgetCode().getPrBudgetCodeid());
        }

        return tbAcTdsTaxheads;
    }

    /**
     * Mapping from 'TbAcTdsTaxheads' to 'TbAcTdsTaxheadsEntity'
     *
     * @param tbAcTdsTaxheads
     * @param tbAcTdsTaxheadsEntity
     */
    public void mapTbAcTdsTaxheadsToTbAcTdsTaxheadsEntity(
            final AccountTDSTaxHeadsBean tbAcTdsTaxheads,
            final AccountTDSTaxHeadsEntity tbAcTdsTaxheadsEntity) {
        if (tbAcTdsTaxheads == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcTdsTaxheads, tbAcTdsTaxheadsEntity);

        // --- Link mapping ( link : tbAcTdsTaxheads )
        if (hasLinkToTbComparamDet(tbAcTdsTaxheads)) {
            final TbComparamDetEntity tbComparamDet3 = new TbComparamDetEntity();
            tbComparamDet3.setCpdId(tbAcTdsTaxheads.getCpdIdDeductionType());
            tbAcTdsTaxheadsEntity.setTbComparamDet(tbComparamDet3);
        } else {
            tbAcTdsTaxheadsEntity.setTbComparamDet(null);
        }

        if (hasLinkToAccountBudgetCode(tbAcTdsTaxheads)) {
            final AccountBudgetCodeEntity budgetCodeEntity = new AccountBudgetCodeEntity();
            budgetCodeEntity.setprBudgetCodeid(tbAcTdsTaxheads.getBudgetCodeId());
            tbAcTdsTaxheadsEntity.setBudgetCode(budgetCodeEntity);
        } else {
            tbAcTdsTaxheadsEntity.setBudgetCode(null);
        }

    }

    private boolean hasLinkToAccountBudgetCode(final AccountTDSTaxHeadsBean tbAcTdsTaxheads) {
        if (tbAcTdsTaxheads.getBudgetCodeId() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbComparamDet id is valid.
     *
     * @param TbComparamDet TbComparamDet
     * @return boolean
     */
    private boolean hasLinkToTbComparamDet(
            final AccountTDSTaxHeadsBean tbAcTdsTaxheads) {
        if (tbAcTdsTaxheads.getCpdIdDeductionType() != null) {
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