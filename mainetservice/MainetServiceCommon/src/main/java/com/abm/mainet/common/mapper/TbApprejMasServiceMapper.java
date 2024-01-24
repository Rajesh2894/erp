/*
 * Created on 6 Apr 2016 ( Time 11:38:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbApprejMasEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbApprejMasServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbApprejMasServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbApprejMasEntity' to 'TbApprejMas'
     *
     * @param tbApprejMasEntity
     */
    public TbApprejMas mapTbApprejMasEntityToTbApprejMas(final TbApprejMasEntity tbApprejMasEntity) {
        if (tbApprejMasEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbApprejMas tbApprejMas = map(tbApprejMasEntity, TbApprejMas.class);

        return tbApprejMas;
    }

    /**
     * Mapping from 'TbApprejMas' to 'TbApprejMasEntity'
     *
     * @param tbApprejMas
     * @param tbApprejMasEntity
     */
    public void mapTbApprejMasToTbApprejMasEntity(final TbApprejMas tbApprejMas, final TbApprejMasEntity tbApprejMasEntity) {
        if (tbApprejMas == null) {
            return;
        }

        // --- Generic mapping
        map(tbApprejMas, tbApprejMasEntity);

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