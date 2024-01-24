/*
 * Created on 4 May 2016 ( Time 18:09:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.water.domain.InstWiseConsmpEntity;
import com.abm.mainet.water.dto.TbWtInstCsmp;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbWtInstCsmpServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private final ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbWtInstCsmpServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbWtInstCsmpEntity' to 'TbWtInstCsmp'
     * @param tbWtInstCsmpEntity
     */
    public TbWtInstCsmp mapTbWtInstCsmpEntityToTbWtInstCsmp(final InstWiseConsmpEntity tbWtInstCsmpEntity) {
        if (tbWtInstCsmpEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbWtInstCsmp tbWtInstCsmp = map(tbWtInstCsmpEntity, TbWtInstCsmp.class);

        return tbWtInstCsmp;
    }

    /**
     * Mapping from 'TbWtInstCsmp' to 'TbWtInstCsmpEntity'
     * @param tbWtInstCsmp
     * @param tbWtInstCsmpEntity
     */
    public void mapTbWtInstCsmpToTbWtInstCsmpEntity(final TbWtInstCsmp tbWtInstCsmp,
            final InstWiseConsmpEntity tbWtInstCsmpEntity) {
        if (tbWtInstCsmp == null) {
            return;
        }

        // --- Generic mapping
        map(tbWtInstCsmp, tbWtInstCsmpEntity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

}