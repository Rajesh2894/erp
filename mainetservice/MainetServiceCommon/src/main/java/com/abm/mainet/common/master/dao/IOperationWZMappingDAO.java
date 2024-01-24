package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.LocationOperationWZMapping;

public interface IOperationWZMappingDAO {

    List<Long> findLocations(Long orgId, Long deptId, Long fLevel, Long sLevel, Long tLevel, Long foLevel, Long fiLevel);

    /**
     * To retrieve LocationMasEntity from organization, department and words-zones levels. codIdOperLevel are optional arguments
     * it can be provide as null.
     * 
     * @param orgId
     * @param deptId
     * @param codIdOperLevel1
     * @param codIdOperLevel2
     * @param codIdOperLevel3
     * @param codIdOperLevel4
     * @param codIdOperLevel5
     * @return
     */
    List<LocationMasEntity> findLocationOperationWZMapping(Long orgId, Long deptId, Long codIdOperLevel1,
            Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5);
    
    List<LocationOperationWZMapping> getLocationOperationWZMappingByLocId(Long locid,Long orgId);

}
