package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity;
import com.abm.mainet.account.dto.AccountBudgetOpenBalanceBean;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class AccountBudgetOpenBalanceServiceMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public AccountBudgetOpenBalanceServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public AccountBudgetOpenBalanceBean mapTbAcBugopenBalanceEntityToTbAcBugopenBalance(
            final com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity tbAcBugopenBalanceEntity) {
        if (tbAcBugopenBalanceEntity == null) {
            return null;
        }

        final AccountBudgetOpenBalanceBean tbAcBugopenBalance = map(tbAcBugopenBalanceEntity, AccountBudgetOpenBalanceBean.class);

        if (tbAcBugopenBalanceEntity.getTbAcSecondaryheadMaster() != null) {
            tbAcBugopenBalance.setSacHeadId(tbAcBugopenBalanceEntity.getTbAcSecondaryheadMaster().getSacHeadId());
            tbAcBugopenBalance.setAcHeadCode(tbAcBugopenBalanceEntity.getTbAcSecondaryheadMaster().getAcHeadCode());
        }

        if (tbAcBugopenBalanceEntity.getTbAcFunctionMaster() != null) {
            tbAcBugopenBalance.setFunctionId(tbAcBugopenBalanceEntity.getTbAcFunctionMaster().getFunctionId());
            tbAcBugopenBalance.setFunctionCode(tbAcBugopenBalanceEntity.getTbAcFunctionMaster().getFunctionCompcode()
                    + MainetConstants.SEPARATOR + tbAcBugopenBalanceEntity.getTbAcFunctionMaster().getFunctionDesc());

        }

        if (tbAcBugopenBalanceEntity.getTbAcFieldMaster() != null) {
            tbAcBugopenBalance.setFieldId(tbAcBugopenBalanceEntity.getTbAcFieldMaster().getFieldId());
            tbAcBugopenBalance.setFieldCode(tbAcBugopenBalanceEntity.getTbAcFieldMaster().getFieldCompcode()
                    + MainetConstants.SEPARATOR + tbAcBugopenBalanceEntity.getTbAcFieldMaster().getFieldDesc());
        }

        if (tbAcBugopenBalanceEntity.getTbAcFundMaster() != null) {
            tbAcBugopenBalance.setFundId(tbAcBugopenBalanceEntity.getTbAcFundMaster().getFundId());
            tbAcBugopenBalance.setFundCode(tbAcBugopenBalanceEntity.getTbAcFundMaster().getFundCompositecode()
                    + MainetConstants.SEPARATOR + tbAcBugopenBalanceEntity.getTbAcFundMaster().getFundDesc());
        }

        if (tbAcBugopenBalanceEntity.getTbAcPrimaryheadMaster() != null) {
            tbAcBugopenBalance.setPacHeadId(tbAcBugopenBalanceEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId());
            tbAcBugopenBalance.setPrimaryAcHeadCode(tbAcBugopenBalanceEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadCompcode()
                    + MainetConstants.SEPARATOR + tbAcBugopenBalanceEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadDesc());
        }

        if (tbAcBugopenBalanceEntity.getTbComparamDet() != null) {
            tbAcBugopenBalance.setCpdIdDrcr(String.valueOf(tbAcBugopenBalanceEntity.getTbComparamDet().getCpdId()));
        }

        return tbAcBugopenBalance;
    }

    /**
     * Mapping from 'TbAcBugopenBalance' to 'TbAcBugopenBalanceEntity'
     * @param tbAcBugopenBalance
     * @param tbAcBugopenBalanceEntity
     */
    public void mapTbAcBugopenBalanceToTbAcBugopenBalanceEntity(final AccountBudgetOpenBalanceBean tbAcBugopenBalance,
            final AccountBudgetOpenBalanceEntity tbAcBugopenBalanceEntity) {
        if (tbAcBugopenBalance == null) {
            return;
        }

        map(tbAcBugopenBalance, tbAcBugopenBalanceEntity);

        if (hasLinkToTbAcFundMaster(tbAcBugopenBalance)) {
            final AccountFundMasterEntity tbAcFundMaster = new AccountFundMasterEntity();
            tbAcFundMaster.setFundId(tbAcBugopenBalance.getFundId());
            tbAcBugopenBalanceEntity.setTbAcFundMaster(tbAcFundMaster);
        } else {
            tbAcBugopenBalanceEntity.setTbAcFundMaster(null);
        }

        if (hasLinkToTbAcFunctionMaster(tbAcBugopenBalance)) {
            final AccountFunctionMasterEntity tbAcFunctionMaster = new AccountFunctionMasterEntity();
            tbAcFunctionMaster.setFunctionId(tbAcBugopenBalance.getFunctionId());
            tbAcBugopenBalanceEntity.setTbAcFunctionMaster(tbAcFunctionMaster);
        } else {
            tbAcBugopenBalanceEntity.setTbAcFunctionMaster(null);
        }

        if (hasLinkToTbAcFieldMaster(tbAcBugopenBalance)) {
            final AccountFieldMasterEntity tbAcFieldMaster = new AccountFieldMasterEntity();
            tbAcFieldMaster.setFieldId(tbAcBugopenBalance.getFieldId());
            tbAcBugopenBalanceEntity.setTbAcFieldMaster(tbAcFieldMaster);
        } else {
            tbAcBugopenBalanceEntity.setTbAcFieldMaster(null);
        }

        if (hasLinkToTbAcPrimaryheadMaster(tbAcBugopenBalance)) {
            final AccountHeadPrimaryAccountCodeMasterEntity tbAcPrimaryheadMaster = new AccountHeadPrimaryAccountCodeMasterEntity();
            tbAcPrimaryheadMaster.setPrimaryAcHeadId(tbAcBugopenBalance.getPacHeadId());
            tbAcBugopenBalanceEntity.setTbAcPrimaryheadMaster(tbAcPrimaryheadMaster);
        } else {
            tbAcBugopenBalanceEntity.setTbAcPrimaryheadMaster(null);
        }

        if (hasLinkToTbAcSecondaryheadMaster(tbAcBugopenBalance)) {
            final AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster = new AccountHeadSecondaryAccountCodeMasterEntity();
            tbAcSecondaryheadMaster.setSacHeadId(tbAcBugopenBalance.getSacHeadId());
            tbAcBugopenBalanceEntity.setTbAcSecondaryheadMaster(tbAcSecondaryheadMaster);
        } else {
            tbAcBugopenBalanceEntity.setTbAcSecondaryheadMaster(null);
        }

        if (hasLinkToTbComparamDet(tbAcBugopenBalance)) {
            final TbComparamDetEntity tbComparamDet = new TbComparamDetEntity();
            tbComparamDet.setCpdId(Long.valueOf(tbAcBugopenBalance.getCpdIdDrcr()));
            tbAcBugopenBalanceEntity.setTbComparamDet(tbComparamDet);
        } else {
            tbAcBugopenBalanceEntity.setTbComparamDet(null);
        }

    }

    private boolean hasLinkToTbAcFunctionMaster(final AccountBudgetOpenBalanceBean tbAcBugopenBalance) {
        if (tbAcBugopenBalance.getFunctionId() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcFieldMaster(final AccountBudgetOpenBalanceBean tbAcBugopenBalance) {
        if (tbAcBugopenBalance.getFieldId() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcFundMaster(final AccountBudgetOpenBalanceBean tbAcBugopenBalance) {
        if (tbAcBugopenBalance.getFundId() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcPrimaryheadMaster(final AccountBudgetOpenBalanceBean tbAcBugopenBalance) {
        if (tbAcBugopenBalance.getPacHeadId() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcSecondaryheadMaster(final AccountBudgetOpenBalanceBean tbAcBugopenBalance) {
        if (tbAcBugopenBalance.getSacHeadId() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbComparamDet(final AccountBudgetOpenBalanceBean tbAcBugopenBalance) {
        if (tbAcBugopenBalance.getCpdIdDrcr() != null) {
            return true;
        }
        return false;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}