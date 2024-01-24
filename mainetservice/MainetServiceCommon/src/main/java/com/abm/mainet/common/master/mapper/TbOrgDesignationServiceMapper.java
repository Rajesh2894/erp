/*
 * Created on 6 Apr 2016 ( Time 17:24:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbOrgDesignationEntity;
import com.abm.mainet.common.master.dto.TbOrgDesignation;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TbOrgDesignationServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public TbOrgDesignationServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'TbOrgDesignationEntity' to 'TbOrgDesignation'
     * @param tbOrgDesignationEntity
     */
    public TbOrgDesignation mapTbOrgDesignationEntityToTbOrgDesignation(final TbOrgDesignationEntity tbOrgDesignationEntity) {
        if (tbOrgDesignationEntity == null) {
            return null;
        }

        // --- Generic mapping
        final TbOrgDesignation tbOrgDesignation = map(tbOrgDesignationEntity, TbOrgDesignation.class);

        // --- Link mapping ( link to TbOrganisation )
        if (tbOrgDesignationEntity.getTbOrganisation() != null) {
            tbOrgDesignation.setOrgid(tbOrgDesignationEntity.getTbOrganisation().getOrgid());
        }
        // --- Link mapping ( link to Designation )
        if (tbOrgDesignationEntity.getDesignation() != null) {
            tbOrgDesignation.setDsgid(tbOrgDesignationEntity.getDesignation().getDsgid());
        }
        return tbOrgDesignation;
    }

    /**
     * Mapping from 'TbOrgDesignation' to 'TbOrgDesignationEntity'
     * @param tbOrgDesignation
     * @param tbOrgDesignationEntity
     */
    public void mapTbOrgDesignationToTbOrgDesignationEntity(final TbOrgDesignation tbOrgDesignation,
            final TbOrgDesignationEntity tbOrgDesignationEntity) {
        if (tbOrgDesignation == null) {
            return;
        }

        // --- Generic mapping
        map(tbOrgDesignation, tbOrgDesignationEntity);

        // --- Link mapping ( link : tbOrgDesignation )
        if (hasLinkToTbOrganisation(tbOrgDesignation)) {
            final Organisation tbOrganisation1 = new Organisation();
            tbOrganisation1.setOrgid(tbOrgDesignation.getOrgid());
            tbOrgDesignationEntity.setTbOrganisation(tbOrganisation1);
        } else {
            tbOrgDesignationEntity.setTbOrganisation(null);
        }

        // --- Link mapping ( link : tbOrgDesignation )
        if (hasLinkToDesignation(tbOrgDesignation)) {
            final Designation designation2 = new Designation();
            designation2.setDsgid(tbOrgDesignation.getDsgid());
            tbOrgDesignationEntity.setDesignation(designation2);
        } else {
            tbOrgDesignationEntity.setDesignation(null);
        }

    }

    /**
     * Verify that TbOrganisation id is valid.
     * @param TbOrganisation TbOrganisation
     * @return boolean
     */
    private boolean hasLinkToTbOrganisation(final TbOrgDesignation tbOrgDesignation) {
        if (tbOrgDesignation.getOrgid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that Designation id is valid.
     * @param Designation Designation
     * @return boolean
     */
    private boolean hasLinkToDesignation(final TbOrgDesignation tbOrgDesignation) {
        if (tbOrgDesignation.getDsgid() != null) {
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