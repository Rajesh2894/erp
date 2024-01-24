package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbAcVendormasterServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbAcVendormasterServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbAcVendormasterEntity' to 'TbAcVendormaster'
     * @param tbAcVendormasterEntity
     */
    public TbAcVendormaster mapTbAcVendormasterEntityToTbAcVendormaster(final TbAcVendormasterEntity tbAcVendormasterEntity) {
        if (tbAcVendormasterEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbAcVendormaster tbAcVendormaster = map(tbAcVendormasterEntity, TbAcVendormaster.class);

        return tbAcVendormaster;
    }

    /**
     * Mapping from 'TbAcVendormaster' to 'TbAcVendormasterEntity'
     * @param tbAcVendormaster
     * @param tbAcVendormasterEntity
     */
    public void mapTbAcVendormasterToTbAcVendormasterEntity(final TbAcVendormaster tbAcVendormaster,
            final TbAcVendormasterEntity tbAcVendormasterEntity) {
        if (tbAcVendormaster == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcVendormaster, tbAcVendormasterEntity);

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