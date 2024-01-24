package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.PortalPrifixMappingMaster;

@Repository
public class PortalPrefixMappingDAO extends AbstractDAO<PortalPrifixMappingMaster> implements IPortalPrefixMappingDAO {

    @Override
    public PortalPrifixMappingMaster getMappedPrefix(final String prefixType, final Long portalPrefix) {
        PortalPrifixMappingMaster mappingMaster = null;

        final Query query = createQuery(
                "Select m from PortalPrifixMappingMaster m where  m.prefixType =?1 and m.portalPrefix =?2");
        query.setParameter(1, prefixType);
        query.setParameter(2, portalPrefix);

        @SuppressWarnings("unchecked")
        final List<PortalPrifixMappingMaster> mapping = query.getResultList();
        if ((mapping != null) && !mapping.isEmpty()) {
            mappingMaster = mapping.get(0);
        }

        return mappingMaster;

    }

}
