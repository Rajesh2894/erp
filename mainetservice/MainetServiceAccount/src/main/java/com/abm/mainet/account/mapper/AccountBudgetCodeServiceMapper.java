
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeBean;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * @author prasad.kancharla
 *
 */

@Component
public class AccountBudgetCodeServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountBudgetCodeServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetCodeBeanEntity' to 'AccountBudgetCodeBean'
     * @param tbAcBudgetCodeEntity
     */
    public AccountBudgetCodeBean mapAccountBudgetCodeEntityToAccountBudgetCodeBean(
            final AccountBudgetCodeEntity tbAcBudgetCodeEntity) {
        if (tbAcBudgetCodeEntity == null) {
            return null;
        }

        // --- Generic mapping
        final AccountBudgetCodeBean tbAcBudgetCode = map(tbAcBudgetCodeEntity, AccountBudgetCodeBean.class);

        // --- Link mapping ( link to TbAcFieldMaster )
        if (tbAcBudgetCodeEntity.getTbAcFieldMaster() != null) {
            tbAcBudgetCode.setFieldId(tbAcBudgetCodeEntity.getTbAcFieldMaster().getFieldId());
            tbAcBudgetCode.setFieldCode(tbAcBudgetCodeEntity.getTbAcFieldMaster().getFieldCompcode() + MainetConstants.SEPARATOR
                    + tbAcBudgetCodeEntity.getTbAcFieldMaster().getFieldDesc());
        }

        // --- Link mapping ( link to TbFinancialyear )
        if (tbAcBudgetCodeEntity.getTbDepartment() != null) {
            tbAcBudgetCode.setDpDeptid(tbAcBudgetCodeEntity.getTbDepartment().getDpDeptid());
            tbAcBudgetCode.setDpDeptName(tbAcBudgetCodeEntity.getTbDepartment().getDpDeptcode() + MainetConstants.HYPHEN
                    + tbAcBudgetCodeEntity.getTbDepartment().getDpDeptdesc());
        }

        // --- Link mapping ( link to TbAcFundMaster )
        if (tbAcBudgetCodeEntity.getTbAcFundMaster() != null) {
            tbAcBudgetCode.setFundId(tbAcBudgetCodeEntity.getTbAcFundMaster().getFundId());
            tbAcBudgetCode.setFundCode(tbAcBudgetCodeEntity.getTbAcFundMaster().getFundCompositecode() + MainetConstants.SEPARATOR
                    + tbAcBudgetCodeEntity.getTbAcFundMaster().getFundDesc());
        }
        // --- Link mapping ( link to TbAcSecondaryheadMaster )
        if (tbAcBudgetCodeEntity.getTbAcSecondaryheadMaster() != null) {
            tbAcBudgetCode.setSacHeadId(tbAcBudgetCodeEntity.getTbAcSecondaryheadMaster().getSacHeadId());
            tbAcBudgetCode.setSacHeadCode(tbAcBudgetCodeEntity.getTbAcSecondaryheadMaster().getSacHeadCode()
                    + MainetConstants.SEPARATOR + tbAcBudgetCodeEntity.getTbAcSecondaryheadMaster().getSacHeadDesc());
        }
        // --- Link mapping ( link to TbAcFunctionMaster )
        if (tbAcBudgetCodeEntity.getTbAcFunctionMaster() != null) {
            tbAcBudgetCode.setFunctionId(tbAcBudgetCodeEntity.getTbAcFunctionMaster().getFunctionId());
            tbAcBudgetCode.setFunctionCode(tbAcBudgetCodeEntity.getTbAcFunctionMaster().getFunctionCompcode()
                    + MainetConstants.SEPARATOR + tbAcBudgetCodeEntity.getTbAcFunctionMaster().getFunctionDesc());
        }

        // --- Link mapping ( link to TbAcSecondaryheadMaster )
        if (tbAcBudgetCodeEntity.getTbAcPrimaryheadMaster() != null) {
            tbAcBudgetCode.setPacHeadId(tbAcBudgetCodeEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId());
            tbAcBudgetCode.setPacHeadCode(tbAcBudgetCodeEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadCompcode()
                    + MainetConstants.SEPARATOR + tbAcBudgetCodeEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadDesc());
        }

        return tbAcBudgetCode;
    }

    /**
     * Mapping from 'AccountBudgetCodeBean' to 'AccountBudgetCodeBeanEntity'
     * @param tbAcBudgetCode
     * @param tbAcBudgetCodeEntity
     */
    public void mapAccountBudgetCodeBeanToAccountBudgetCodeBeanEntity(final AccountBudgetCodeBean tbAcBudgetCode,
            final AccountBudgetCodeEntity tbAcBudgetCodeEntity) {
        if (tbAcBudgetCode == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcBudgetCode, tbAcBudgetCodeEntity);

        // --- Link mapping ( link : tbAcBudgetCode )
        if (hasLinkToTbAcFieldMaster(tbAcBudgetCode)) {
            final AccountFieldMasterEntity tbAcFieldMaster = new AccountFieldMasterEntity();
            if (tbAcBudgetCode.getFieldId() == null) {
            }
            tbAcFieldMaster.setFieldId(tbAcBudgetCode.getFieldId().longValue());

            tbAcBudgetCodeEntity.setTbAcFieldMaster(tbAcFieldMaster);
        } else {
            tbAcBudgetCodeEntity.setTbAcFieldMaster(null);
        }

        // --- Link mapping ( link : tbAcBudgetCode )
        if (hasLinkToTbDepartment(tbAcBudgetCode)) {
            final Department tbDepartment = new Department();
            tbDepartment.setDpDeptid(tbAcBudgetCode.getDpDeptid());
            tbAcBudgetCodeEntity.setTbDepartment(tbDepartment);
        } else {
            tbAcBudgetCodeEntity.setTbDepartment(null);
        }

        // --- Link mapping ( link : tbAcBudgetCode )
        if (hasLinkToTbAcFundMaster(tbAcBudgetCode)) {
            final AccountFundMasterEntity tbAcFundMaster = new AccountFundMasterEntity();
            if (tbAcBudgetCode.getFundId() == null) {

            }
            tbAcFundMaster.setFundId(tbAcBudgetCode.getFundId().longValue());

            tbAcBudgetCodeEntity.setTbAcFundMaster(tbAcFundMaster);
        } else {
            tbAcBudgetCodeEntity.setTbAcFundMaster(null);
        }

        // --- Link mapping ( link : tbAcBudgetCode )
        if (hasLinkToTbAcSecondaryheadMaster(tbAcBudgetCode)) {
            final AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster = new AccountHeadSecondaryAccountCodeMasterEntity();
            if (tbAcBudgetCode.getSacHeadId() == null) {

            }
            tbAcSecondaryheadMaster.setSacHeadId(tbAcBudgetCode.getSacHeadId().longValue());

            tbAcBudgetCodeEntity.setTbAcSecondaryheadMaster(tbAcSecondaryheadMaster);
        } else {
            tbAcBudgetCodeEntity.setTbAcSecondaryheadMaster(null);
        }

        // --- Link mapping ( link : tbAcBudgetCode )
        if (hasLinkToTbAcFunctionMaster(tbAcBudgetCode)) {
            final AccountFunctionMasterEntity tbAcFunctionMaster = new AccountFunctionMasterEntity();
            if (tbAcBudgetCode.getFunctionId() == null) {

            }
            tbAcFunctionMaster.setFunctionId(tbAcBudgetCode.getFunctionId().longValue());

            tbAcBudgetCodeEntity.setTbAcFunctionMaster(tbAcFunctionMaster);
        } else {
            tbAcBudgetCodeEntity.setTbAcFunctionMaster(null);
        }

        // --- Link mapping ( link : tbAcSecondaryheadMaster )
        if (hasLinkToTbAcPrimaryheadMaster(tbAcBudgetCode)) {
            final AccountHeadPrimaryAccountCodeMasterEntity tbAcPrimaryheadMaster = new AccountHeadPrimaryAccountCodeMasterEntity();
            if (tbAcBudgetCode.getPacHeadId() == null) {

            }
            tbAcPrimaryheadMaster.setPrimaryAcHeadId(tbAcBudgetCode.getPacHeadId().longValue());
            tbAcBudgetCodeEntity.setTbAcPrimaryheadMaster(tbAcPrimaryheadMaster);
        } else {
            tbAcBudgetCodeEntity.setTbAcPrimaryheadMaster(null);
        }

    }

    /**
     * Verify that TbAcFieldMaster id is valid.
     * @param TbAcFieldMaster TbAcFieldMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcFieldMaster(final AccountBudgetCodeBean tbAcBudgetCode) {
        if (tbAcBudgetCode.getFieldId() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbFinancialyear id is valid.
     * @param TbFinancialyear TbFinancialyear
     * @return boolean
     */
    private boolean hasLinkToTbDepartment(final AccountBudgetCodeBean tbAcBudgetCode) {
        if (tbAcBudgetCode.getDpDeptid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbAcFundMaster id is valid.
     * @param TbAcFundMaster TbAcFundMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcFundMaster(final AccountBudgetCodeBean tbAcBudgetCode) {
        if (tbAcBudgetCode.getFundId() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbAcSecondaryheadMaster id is valid.
     * @param TbAcSecondaryheadMaster TbAcSecondaryheadMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcSecondaryheadMaster(final AccountBudgetCodeBean tbAcBudgetCode) {
        if (tbAcBudgetCode.getSacHeadId() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbAcFunctionMaster id is valid.
     * @param TbAcFunctionMaster TbAcFunctionMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcFunctionMaster(final AccountBudgetCodeBean tbAcBudgetCode) {
        if (tbAcBudgetCode.getFunctionId() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbAcPrimaryheadMaster id is valid.
     * @param TbAcPrimaryheadMaster TbAcPrimaryheadMaster
     * @return boolean
     */
    private boolean hasLinkToTbAcPrimaryheadMaster(final AccountBudgetCodeBean tbAcBudgetCode) {
        if (tbAcBudgetCode.getPacHeadId() != null) {
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
