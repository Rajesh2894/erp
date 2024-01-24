
package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class DesignationServiceMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public DesignationServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public DesignationBean mapDesignationEntityToDesignation(final Designation designationEntity) {
        if (designationEntity == null) {
            return null;
        }

        final DesignationBean designation = map(designationEntity, DesignationBean.class);

        if (designationEntity.getTbLocationMas() != null) {
            designation.setLocId(designationEntity.getTbLocationMas().getLocId());
        }
        return designation;
    }

    public void mapDesignationToDesignationEntity(final DesignationBean designation, final Designation designationEntity) {
        if (designation == null) {
            return;
        }

        map(designation, designationEntity);
        if (hasLinkToTbLocationMas(designation)) {
            final LocationMasEntity tbLocationMas1 = new LocationMasEntity();
            tbLocationMas1.setLocId(designation.getLocId());
            designationEntity.setTbLocationMas(tbLocationMas1);
        } else {
            designationEntity.setTbLocationMas(null);
        }

    }

    private boolean hasLinkToTbLocationMas(final DesignationBean designation) {
        if (designation.getLocId() != null) {
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