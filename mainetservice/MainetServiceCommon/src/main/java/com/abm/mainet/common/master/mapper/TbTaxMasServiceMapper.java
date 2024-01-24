
package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.master.dto.TaxHeadMapping;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbTaxMasServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbTaxMasServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbTaxMasEntity' to 'TbTaxMas'
     * @param tbTaxMasEntity
     */
    public TbTaxMas mapTbTaxMasEntityToTbTaxMas(final TbTaxMasEntity tbTaxMasEntity) {
        if (tbTaxMasEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbTaxMas tbTaxMas = map(tbTaxMasEntity, TbTaxMas.class);

        return tbTaxMas;
    }

    /**
     * Mapping from 'TbTaxMas' to 'TbTaxMasEntity'
     * @param tbTaxMas
     * @param tbTaxMasEntity
     */
    public void mapTbTaxMasToTbTaxMasEntity(final TbTaxMas tbTaxMas, final TbTaxMasEntity tbTaxMasEntity) {
        if (tbTaxMas == null) {
            return;
        }

        // --- Generic mapping
        map(tbTaxMas, tbTaxMasEntity);

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

    public TaxHeadMapping mapAccountToTax(final TbTaxMasEntity account) {
        if (account == null) {
            return null;
        }
        final TaxHeadMapping tax = map(account, TaxHeadMapping.class);
        return tax;

    }

    public TbTaxMasEntity mapTaxToAccount(final TaxHeadMapping tax) {
        if (tax == null) {
            return null;
        }
        final TbTaxMasEntity account = map(tax, TbTaxMasEntity.class);
        return account;
    }
}