/**
 *
 */
package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.BeatMaster;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
@Repository
public class BeatMasterDAO extends AbstractDAO<BeatMaster> implements IBeatMasterDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.RouteMasterDAO#serchRouteByRouteTypeAndRouteRegNo(java.lang.String, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BeatMaster> serchRouteByRouteTypeAndRouteNo(Long routeId, String routeName, String status, String routeNo,
            Long orgId) {
        Query query = this.createQuery(buildQuery(routeId, routeName, status, routeNo));

        query.setParameter("orgid", orgId);

        if (null != routeId) {
            query.setParameter("routeId", routeId);
        }

        if (StringUtils.isNotEmpty(routeName)) {
            query.setParameter("routeName", routeName);
        }

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(routeNo)) {
            query.setParameter("routeNo", routeNo);
        }

        return query.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @return the string
     */
    private String buildQuery(Long routeId, String routeName, String status, String routeNo) {
        StringBuilder disposalSiteSearchQuery = new StringBuilder(
                " SELECT rm FROM BeatMaster rm WHERE rm.orgid = :orgid ");

        if (null != routeId) {
            disposalSiteSearchQuery.append(" AND beatId != :routeId");
        }

        if (StringUtils.isNotEmpty(routeName)) {
            disposalSiteSearchQuery.append(" AND rm.beatName = :routeName ");
        }

        if (StringUtils.isNotEmpty(status)) {
            disposalSiteSearchQuery.append(" AND rm.beatActive like :status ");
        }

        if (StringUtils.isNotEmpty(routeNo)) {
            disposalSiteSearchQuery.append(" AND rm.beatNo = :routeNo ");
        }
        disposalSiteSearchQuery.append(" ORDER BY rm.beatId DESC ");
        return disposalSiteSearchQuery.toString();
    }

}
