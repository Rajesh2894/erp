package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.domain.TbComparamMasEntity;
import com.abm.mainet.common.dto.TbComparamDet;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class TbComparamDetServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbComparamDetServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbComparamDetEntity' to 'TbComparamDet'
     * @param tbComparamDetEntity
     */
    public TbComparamDet mapTbComparamDetEntityToTbComparamDet(TbComparamDetEntity tbComparamDetEntity) {
        if (tbComparamDetEntity == null) {
            return null;
        }

        // --- Generic mapping
        TbComparamDet tbComparamDet = map(tbComparamDetEntity, TbComparamDet.class);

        // --- Link mapping ( link to TbComparamMas )
        if (tbComparamDetEntity.getTbComparamMas() != null) {
            tbComparamDet.setCpmId(tbComparamDetEntity.getTbComparamMas().getCpmId());
        }
        return tbComparamDet;
    }

    /**
     * Mapping from 'TbComparamDet' to 'TbComparamDetEntity'
     * @param tbComparamDet
     * @param tbComparamDetEntity
     */
    public void mapTbComparamDetToTbComparamDetEntity(TbComparamDet tbComparamDet, TbComparamDetEntity tbComparamDetEntity) {
        if (tbComparamDet == null) {
            return;
        }

        // --- Generic mapping
        map(tbComparamDet, tbComparamDetEntity);

        // --- Link mapping ( link : tbComparamDet )
        if (hasLinkToTbComparamMas(tbComparamDet)) {
            TbComparamMasEntity tbComparamMas1 = new TbComparamMasEntity();
            tbComparamMas1.setCpmId(tbComparamDet.getCpmId());
            tbComparamDetEntity.setTbComparamMas(tbComparamMas1);
        } else {
            tbComparamDetEntity.setTbComparamMas(null);
        }

    }

    /**
     * Verify that TbComparamMas id is valid.
     * @param TbComparamMas TbComparamMas
     * @return boolean
     */
    private boolean hasLinkToTbComparamMas(TbComparamDet tbComparamDet) {
        if (tbComparamDet.getCpmId() != null) {
            return true;
        }
        return false;
    }

    protected void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ModelMapper getModelMapper() {

        return modelMapper;
    }

}
