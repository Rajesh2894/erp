package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.PopulationMasterDTO;

/**
 * The Interface PopulationMasterService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
public interface IPopulationMasterService {

    /**
     * Save population master.
     *
     * @param population the population
     * @return the population master DTO
     */
    PopulationMasterDTO savePopulationMaster(PopulationMasterDTO population);

    /**
     * Save population master.
     *
     * @param populations the populations
     * @return the list
     */
    List<PopulationMasterDTO> savePopulationMaster(List<PopulationMasterDTO> populations);

    /**
     * Update population master.
     *
     * @param population the population
     * @return the population master DTO
     */
    PopulationMasterDTO updatePopulationMaster(PopulationMasterDTO population);

    /**
     * Delete population master.
     *
     * @param populationId the population id
     */
    void deletePopulationMaster(Long populationId, Long empId, String ipMacAdd);

    /**
     * Gets the all population master.
     *
     * @param yerCpdId the yer cpd id
     * @param orgId the org id
     * @return the all population master
     */
    List<PopulationMasterDTO> getAllPopulationMaster(Long yerCpdId, Long orgId);

    /**
     * Gets the population master.
     *
     * @param populationId the population id
     * @return the population master
     */
    PopulationMasterDTO getPopulationMaster(Long populationId);

    /**
     * Find population by year and area.
     *
     * @param yearCpdId the year cpd id
     * @param ward the ward
     * @param zone the zone
     * @param block the block
     * @param route the route
     * @param subRoute the sub route
     * @param orgId the org id
     * @return the list of population found on given criteria
     */
    List<PopulationMasterDTO> findPopulationByYearAndArea(Long yearCpdId, Long ward, Long zone, Long block, Long route,
            Long subRoute, Long orgId);

    /**
     * Method used to checked whether population exists in given area for particular year
     * @param population DTO containing popId(may be null),organization id, Year id, and Area (Prefix up to 5 level)
     * @return population
     */
    boolean validatePopulationMaster(PopulationMasterDTO population);

    /**
     * find Population
     * @param yearCpdId
     * @param ward
     * @param zone
     * @param block
     * @param route
     * @param subRoute
     * @param orgId
     * @return
     */
    List<PopulationMasterDTO> findPopulation(Long yearCpdId, Long ward, Long zone, Long block, Long route, Long subRoute,
            Long orgId);

    /**
     * find Population
     * @return
     */
    List<PopulationMasterDTO> financialyear();

}
