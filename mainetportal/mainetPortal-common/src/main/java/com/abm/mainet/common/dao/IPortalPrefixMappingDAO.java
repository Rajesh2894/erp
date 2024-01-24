package com.abm.mainet.common.dao;

import com.abm.mainet.common.domain.PortalPrifixMappingMaster;

public interface IPortalPrefixMappingDAO {
    public PortalPrifixMappingMaster getMappedPrefix(String prefixType, Long portalPrefix);
}
