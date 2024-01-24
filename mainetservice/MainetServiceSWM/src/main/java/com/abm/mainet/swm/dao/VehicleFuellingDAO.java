package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.VehicleFuelling;

/**
 * The Class VehicleFuellingDAOImpl.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
@Repository
public class VehicleFuellingDAO extends AbstractDAO<VehicleFuelling> implements IVehicleFuellingDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.VehicleFuelingDAO#serchVehicleByVehicleTypeAndVehicleRegNo(java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleFuelling> searchVehicleFuelling(Long vehicleType, Long pumpId, Date fromDate,
            Date toDate, Long orgId) {
        Query query = this.createQuery(buildQuery(vehicleType, pumpId, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }

        if (pumpId != null) {
            query.setParameter("pumpId", pumpId);
        }

        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IVehicleFuellingDAO#getVehicleFuellingByAdviceDateAndPumpId(java.lang.Long, java.util.Date,
     * java.util.Date, java.lang.Long, java.lang.Boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleFuelling> getVehicleFuellingByAdviceDateAndPumpId(Long pumpId, Date fromDate,
            Date toDate, Long orgId, Boolean paid) {
        Query query = this.createQuery(buildAdviceQuery(pumpId, fromDate, toDate, paid));

        query.setParameter("orgid", orgId);

        if (pumpId != null) {
            query.setParameter("pumpId", pumpId);
        }

        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }

        return query.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param vehicleType the vehicle type
     * @param pumpId the vehicle Id
     * @return the string
     */
    private String buildQuery(Long vehicleType, Long pumpId, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT vf FROM VehicleFuelling vf WHERE vf.orgid = :orgid ");

        if (vehicleType != null) {
            searchQuery.append(" AND vf.veVetype = :vehicleType ");
        }

        if (pumpId != null) {
            searchQuery.append(" AND vf.puId = :pumpId ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND vf.vefDate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND vf.vefDate <= :toDate ");
        }

        searchQuery.append(" ORDER BY vf.vefId DESC ");

        return searchQuery.toString();
    }

    private String buildAdviceQuery(Long pumpId, Date fromDate, Date toDate, Boolean paid) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT vf FROM VehicleFuelling vf WHERE vf.orgid = :orgid ");

        if (pumpId != null) {
            searchQuery.append(" AND vf.puId = :pumpId ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND vf.vefDmdate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND vf.vefDmdate <= :toDate ");
        }
        if (paid) {
            searchQuery.append(" AND vf.vefId  NOT IN (SELECT d.vefId FROM VehicleFuelReconciationDet d WHERE d.orgid = :orgid)");
        } else {
            searchQuery.append(" AND vf.vefId  IN (SELECT d.vefId FROM VehicleFuelReconciationDet d WHERE d.orgid = :orgid)");
        }

        return searchQuery.toString();
    }
}
