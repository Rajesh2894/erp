package com.abm.mainet.swm.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.BeatMasterDTO;

/**
 * The Interface RouteMasterService.
 *
 * @author Lalit.Prusti Created Date : 07-May-2018
 */
@WebService
public interface IBeatMasterService {

    /**
     * Gets the route by route id.
     *
     * @param routeId the route id
     * @return the route by route id
     */
    BeatMasterDTO getRouteByRouteId(Long routeId);

    /**
     * Save route.
     *
     * @param routeIdDetails the route id details
     * @return the route master DTO
     */
    BeatMasterDTO saveRoute(BeatMasterDTO routeDetails);

    /**
     * Update route.
     *
     * @param routeIdDetails the route id details
     * @return the route master DTO
     */
    BeatMasterDTO updateRoute(BeatMasterDTO routeDetails, List<Long> areaIds);

    /**
     * Delete route.
     *
     * @param routeId the route id
     */
    void deleteRoute(Long routeId, Long empId, String ipMacAdd);

    /**
     * Search Route by Route Name and Route No
     *
     * @param routeName
     * @param routeNo
     * @param orgId
     * @return
     */
    List<BeatMasterDTO> serchRouteByRouteTypeAndRouteNo(String routeName, String routeNo, Long orgId);

    /**
     * delete Route Details
     * @param status
     * @param empId
     * @param ipMacAdd
     * @param id
     */
    void deleteRouteDetails(String status, Long empId, String ipMacAdd, Long id);

    /**
     * serch Route
     * @param routeName
     * @param routeNo
     * @param orgId
     * @return
     */
    List<BeatMasterDTO> serchRoute(String routeName, String routeNo, Long orgId);

    /**
     * validate Route
     * @param routeMasterDTO
     * @return
     */
    boolean validateRoute(BeatMasterDTO routeMasterDTO);

    /**
     * get All RouteNo
     * @param orgid
     * @return
     */
    List<BeatMasterDTO> getAllRouteNo(long orgid);

    /**
     * serch Route By Orgid
     * @param orgId
     * @return
     */
    List<BeatMasterDTO> serchRouteByOrgid(Long orgId);

}
