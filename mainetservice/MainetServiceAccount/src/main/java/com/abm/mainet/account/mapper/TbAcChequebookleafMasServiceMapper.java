package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.TbAcChequebookleafMasEntity;
import com.abm.mainet.account.dto.TbAcChequebookleafMas;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbAcChequebookleafMasServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbAcChequebookleafMasServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbAcChequebookleafMasEntity' to 'TbAcChequebookleafMas'
     * @param tbAcChequebookleafMasEntity
     */
    public TbAcChequebookleafMas mapTbAcChequebookleafMasEntityToTbAcChequebookleafMas(
            final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntity) {
        if (tbAcChequebookleafMasEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbAcChequebookleafMas tbAcChequebookleafMas = map(tbAcChequebookleafMasEntity, TbAcChequebookleafMas.class);

        return tbAcChequebookleafMas;
    }

    /**
     * Mapping from 'TbAcChequebookleafMas' to 'TbAcChequebookleafMasEntity'
     * @param tbAcChequebookleafMas
     * @param tbAcChequebookleafMasEntity
     */
    public void mapTbAcChequebookleafMasToTbAcChequebookleafMasEntity(final TbAcChequebookleafMas tbAcChequebookleafMas,
            final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntity) {
        if (tbAcChequebookleafMas == null) {
            return;
        }

        // --- Generic mapping
        map(tbAcChequebookleafMas, tbAcChequebookleafMasEntity);

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