package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.BeatMaster;

/**
 * The Interface RouteMasterDAO.
 *
 * @author Lalit.Prusti
 */
public interface IBeatMasterDAO {

    /**
     * Serch route by route type and route reg no.
     *
     * @param routeName the route name
     * @param routeNo the route number
     * @param orgId the org id
     * @return the list
     */
    List<BeatMaster> serchRouteByRouteTypeAndRouteNo(Long routeId, String routeName, String status, String routeNo, Long orgId);

}
