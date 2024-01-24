package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.MRFMaster;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class MRFMasterDAO extends AbstractDAO<MRFMaster> implements IMRFMasterDAO {
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IMRFMasterDAO#serchIMRFCenterByPlantIdAndPlantName(java.lang.String, java.lang.String,
     * java.lang.Long)
     */
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IMRFMasterDAO#serchIMRFCenterByPlantIdAndPlantName(java.lang.String, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MRFMaster> serchIMRFCenterByPlantIdAndPlantName(String plantId, String plantName, Long orgId) {

        Query query = this.createQuery(buildQuery(plantId, plantName));
        query.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(plantId)) {
            query.setParameter("plantId", plantId);
        }

        if (StringUtils.isNotEmpty(plantName)) {
            query.setParameter("plantName", plantName);
        }
        return query.getResultList();
    }

    /**
     * @param plantId
     * @param plantName
     * @return
     */
    private String buildQuery(String plantId, String plantName) {
        StringBuilder mrfMasterSearchQuery = new StringBuilder(
                " SELECT mrf FROM MRFMaster mrf WHERE mrf.orgId = :orgId ");

        if (StringUtils.isNotEmpty(plantId)) {
            mrfMasterSearchQuery.append(" AND mrf.mrfPlantId = :plantId ");
        }

        if (StringUtils.isNotEmpty(plantName)) {
            mrfMasterSearchQuery.append(" AND mrf.mrfPlantName like :plantName ");
        }
        mrfMasterSearchQuery.append(" ORDER BY mrf.mrfPlantId DESC");
        return mrfMasterSearchQuery.toString();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IMRFMasterDAO#serchIMRFCenterByPlantIdAndPlantName(java.lang.String, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MRFMaster> serchIMRFCenterByPlantIdAndPlantName(Long orgId, String propertyNo)  {

        Query query = this.createQuery(buildQuery(propertyNo));
        query.setParameter("orgId", orgId);
        
        if (StringUtils.isNotEmpty(propertyNo)) {
            query.setParameter("propertyNo", propertyNo);
        }
        return query.getResultList();
    }

    /**
     * @param plantId
     * @param plantName
     * @return
     */
    private String buildQuery(String propertyNo) {
        StringBuilder mrfMasterSearchQuery = new StringBuilder(
                " SELECT mrf FROM MRFMaster mrf WHERE mrf.orgId = :orgId ");
        
        if (StringUtils.isNotEmpty(propertyNo)) {
            mrfMasterSearchQuery.append(" AND mrf.propertyNo like :propertyNo ");
        }
        mrfMasterSearchQuery.append(" ORDER BY mrf.mrfPlantId DESC");
        return mrfMasterSearchQuery.toString();
    }
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IMRFMasterDAO#serchMRFCenterByplantName(java.lang.String, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MRFMaster> serchMRFCenterByplantName(String plantId, String plantName, Long orgId) {
        Query query = this.createQuery(buildQuery2(plantId, plantName));
        query.setParameter("orgid", orgId);

        if (StringUtils.isNotEmpty(plantId)) {
            query.setParameter("plantId", plantId);
        }

        if (StringUtils.isNotEmpty(plantName)) {
            query.setParameter("plantName", plantName);
        }
        return query.getResultList();
    }

    /**
     * @param plantId
     * @param plantName
     * @return
     */
    private String buildQuery2(String plantId, String plantName) {
        StringBuilder mrfMasterSearchQuery = new StringBuilder(
                " SELECT mrf FROM MRFMaster mrf WHERE mrf.orgId = :orgid ");
        if (StringUtils.isNotEmpty(plantId)) {
            mrfMasterSearchQuery.append(" AND mrf.mrfPlantId = :plantId ");
        }

        if (StringUtils.isNotEmpty(plantName)) {
            mrfMasterSearchQuery.append(" AND mrf.mrfPlantName like :plantName ");
        }
        mrfMasterSearchQuery.append(" ORDER BY mrf.plantId DESC");
        return mrfMasterSearchQuery.toString();
    }

}
