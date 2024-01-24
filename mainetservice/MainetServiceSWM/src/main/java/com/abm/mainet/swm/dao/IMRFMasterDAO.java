package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.MRFMaster;

/**
 * @author Ajay.Kumar
 *
 */
public interface IMRFMasterDAO {

    /**
     * serchIMRF Center By PlantId And PlantName
     * @param plantId
     * @param plantName
     * @param orgId
     * @return
     */
    List<MRFMaster> serchIMRFCenterByPlantIdAndPlantName(String plantId, String plantName, Long orgId);
    
    List<MRFMaster> serchIMRFCenterByPlantIdAndPlantName(Long orgId, String propertyNo);

    /**
     * serchMRF Center By plant Name
     * @param plantId
     * @param plantName
     * @param orgId
     * @return
     */
    List<MRFMaster> serchMRFCenterByplantName(String plantId, String plantName, Long orgId);
}
