
package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbTaxAcMappingEntity;
import com.abm.mainet.common.master.dto.TbTaxAcMappingBean;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbTaxAcMappingServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbTaxAcMappingServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbTaxBudgetCodeEntity' to 'TbTaxBudgetCodeBean'
     * @param tbTaxBudgetcodeEntity
     */
    public TbTaxAcMappingBean mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(final TbTaxAcMappingEntity tbTaxBudgetcodeEntity) {
        if (tbTaxBudgetcodeEntity == null) {
            return null;
        }
        final TbTaxAcMappingBean taxBudgetcodeBean = map(tbTaxBudgetcodeEntity, TbTaxAcMappingBean.class);
        return taxBudgetcodeBean;
    }

    /**
     * Mapping from 'TbTaxBudgetCodeBean' to 'TbTaxBudgetCodeEntity'
     * @param taxBudgetcodeBean
     * @param tbTaxBudgetcodeEntity
     */
    public void mapTbTaxBudgetCodeBeanToTbTaxBudgetCodeEntity(final TbTaxAcMappingBean taxBudgetcodeBean,
            final TbTaxAcMappingEntity tbTaxBudgetcodeEntity) {
        if (taxBudgetcodeBean == null) {
            return;
        }
        map(taxBudgetcodeBean, tbTaxBudgetcodeEntity);

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