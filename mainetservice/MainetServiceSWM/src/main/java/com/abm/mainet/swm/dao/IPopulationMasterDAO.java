package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.PopulationMaster;

/**
 * The Interface PopulationMasterDAO.
 *
 * @author Lalit.Prusti
 */
public interface IPopulationMasterDAO {

    /**
     * Search by year and word zone block.
     *
     * @param yearCpdId the year cpd id
     * @param ward the ward
     * @param zone the zone
     * @param block the block
     * @param route the route
     * @param subRoute the sub route
     * @param orgId the org id
     * @param popId the population master id
     * @return the list which match the serach criteria excluding popId
     */
    List<PopulationMaster> searchByYearAndWordZoneBlock(String status, Long yearCpdId, Long ward, Long zone, Long block,
            Long route,
            Long subRoute, Long orgId, Long popId);

}
