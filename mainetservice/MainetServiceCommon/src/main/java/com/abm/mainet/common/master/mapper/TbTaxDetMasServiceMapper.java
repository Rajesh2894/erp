/*
 * Created on 22 Apr 2016 ( Time 13:02:34 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.master.dto.TbTaxDetMas;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbTaxDetMasServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbTaxDetMasServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbTaxDetMasEntity' to 'TbTaxDetMas'
     * @param tbTaxDetMasEntity
     */
    public TbTaxDetMas mapTbTaxDetMasEntityToTbTaxDetMas(final TbTaxDetMasEntity tbTaxDetMasEntity) {
        if (tbTaxDetMasEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbTaxDetMas tbTaxDetMas = map(tbTaxDetMasEntity, TbTaxDetMas.class);

        // --- Link mapping ( link to TbTaxMas )
        if (tbTaxDetMasEntity.getTbTaxMas() != null) {
            tbTaxDetMas.setTmTaxid(tbTaxDetMasEntity.getTbTaxMas().getTaxId());
        }
        return tbTaxDetMas;
    }

    /**
     * Mapping from 'TbTaxDetMas' to 'TbTaxDetMasEntity'
     * @param tbTaxDetMas
     * @param tbTaxDetMasEntity
     */
    public void mapTbTaxDetMasToTbTaxDetMasEntity(final TbTaxDetMas tbTaxDetMas, final TbTaxDetMasEntity tbTaxDetMasEntity) {
        if (tbTaxDetMas == null) {
            return;
        }

        // --- Generic mapping
        map(tbTaxDetMas, tbTaxDetMasEntity);

        // --- Link mapping ( link : tbTaxDetMas )
        if (hasLinkToTbTaxMas(tbTaxDetMas)) {
            final TbTaxMasEntity tbTaxMas1 = new TbTaxMasEntity();
            tbTaxMas1.setTaxId(tbTaxDetMas.getTmTaxid());
            tbTaxDetMasEntity.setTbTaxMas(tbTaxMas1);
        } else {
            tbTaxDetMasEntity.setTbTaxMas(null);
        }

    }

    /**
     * Verify that TbTaxMas id is valid.
     * @param TbTaxMas TbTaxMas
     * @return boolean
     */
    private boolean hasLinkToTbTaxMas(final TbTaxDetMas tbTaxDetMas) {
        if (tbTaxDetMas.getTmTaxid() != null) {
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