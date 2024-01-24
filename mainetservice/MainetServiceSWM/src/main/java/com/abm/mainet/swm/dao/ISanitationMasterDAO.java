package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.SanitationMaster;

/**
 * The Interface SanitationMasterDAO.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 17-May-2018
 */
public interface ISanitationMasterDAO {

    /**
     *
     * @param number : Toilet Number
     * @param type : Toilet Type
     * @param name : Toilet Name
     * @param ward : Toilet Location
     * @param zone : Toilet Location
     * @param block : Toilet Location
     * @param route : Toilet Location
     * @param subRoute : Toilet Location
     * @param orgId : Organization
     * @return
     */
    List<SanitationMaster> searchToiletLocation(String status, Long number, Long type, String name, Long ward, Long zone,
            Long block, Long route,
            Long subRoute, Long orgId);

}
