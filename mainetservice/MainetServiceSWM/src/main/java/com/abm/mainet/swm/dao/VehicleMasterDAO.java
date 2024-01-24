package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.VehicleMaster;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class VehicleMasterDAO extends AbstractDAO<VehicleMaster> implements IVehicleMasterDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.VehicleMasterDAO#serchVehicleByVehicleTypeAndVehicleRegNo(java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleMaster> searchVehicleByVehicleTypeAndVehicleRegNo(Long veId, Long vehicleType, String status,
            String vehicleRegNo,
            Long orgId) {
        Query query = this.createQuery(buildQuery(veId, vehicleType, status, vehicleRegNo));

        query.setParameter("orgid", orgId);

        if (veId != null) {
            query.setParameter("veId", veId);
        }

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(vehicleRegNo)) {
            query.setParameter("vehicleRegNo", vehicleRegNo);
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
    private String buildQuery(Long veId, Long vehicleType, String status, String vehicleRegNo) {
        StringBuilder disposalSiteSearchQuery = new StringBuilder(
                " SELECT vm FROM VehicleMaster vm WHERE vm.orgid = :orgid ");

        if (veId != null) {
            disposalSiteSearchQuery.append(" AND vm.veId != :veId ");
        }

        if (vehicleType != null) {
            disposalSiteSearchQuery.append(" AND vm.veVetype = :vehicleType ");
        }

        if (StringUtils.isNotEmpty(status)) {
            disposalSiteSearchQuery.append(" AND vm.veActive like :status ");
        }

        if (StringUtils.isNotEmpty(vehicleRegNo)) {
            disposalSiteSearchQuery.append(" AND vm.veNo = :vehicleRegNo ");
        }

        disposalSiteSearchQuery.append(" ORDER BY vm.veId DESC ");

        return disposalSiteSearchQuery.toString();
    }

}
