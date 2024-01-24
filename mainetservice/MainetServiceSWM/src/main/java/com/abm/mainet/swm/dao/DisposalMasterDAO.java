/*
 *
 */
package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.DisposalMaster;

/**
 * The Class DisposalMasterDAOImpl.
 */
/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class DisposalMasterDAO extends AbstractDAO<DisposalMaster> implements IDisposalMasterDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.DisposalMasterDAO#serchDisposalSiteBySiteNumberAndSiteName(java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DisposalMaster> serchDisposalSiteBySiteNumberAndSiteName(Long siteNumber, String status, String siteName,
            Long orgId) {

        Query query = this.createQuery(buildQuery(siteNumber, status, siteName));

        query.setParameter("orgid", orgId);

        if (siteNumber != null) {
            query.setParameter("siteNumber", siteNumber);
        }

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(siteName)) {
            query.setParameter("siteName", siteName);
        }

        return query.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param siteNumber the site number
     * @param siteName the site name
     * @return the string
     */
    private String buildQuery(Long siteNumber, String status, String siteName) {
        StringBuilder disposalSiteSearchQuery = new StringBuilder(
                " SELECT ds FROM DisposalMaster ds WHERE ds.orgid = :orgid ");

        if (siteNumber != null) {
            disposalSiteSearchQuery.append(" AND ds.deId = :siteNumber ");
        }

        if (StringUtils.isNotEmpty(status)) {
            disposalSiteSearchQuery.append(" AND ds.deActive like :status ");
        }

        if (StringUtils.isNotEmpty(siteName)) {
            disposalSiteSearchQuery.append(" AND ds.deName like :siteName ");
        }

        disposalSiteSearchQuery.append(" ORDER BY ds.deId DESC");
        return disposalSiteSearchQuery.toString();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IDisposalMasterDAO#serchDisposalSiteBySiteName(java.lang.Long, java.lang.String,
     * java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DisposalMaster> serchDisposalSiteBySiteName(Long siteNumber, String status, String siteName,
            Long orgId) {

        Query query = this.createQuery(buildQuery2(siteNumber, status, siteName));

        query.setParameter("orgid", orgId);

        if (siteNumber != null) {
            query.setParameter("siteNumber", siteNumber);
        }

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(siteName)) {
            query.setParameter("siteName", siteName);
        }

        return query.getResultList();
    }

    /**
     * Builds the query
     * @param siteNumber
     * @param status
     * @param siteName
     * @return
     */
    private String buildQuery2(Long siteNumber, String status, String siteName) {
        StringBuilder disposalSiteSearchQuery = new StringBuilder(
                " SELECT ds FROM DisposalMaster ds WHERE ds.orgid = :orgid ");

        if (siteNumber != null) {
            disposalSiteSearchQuery.append(" AND ds.deId != :siteNumber ");
        }

        if (StringUtils.isNotEmpty(status)) {
            disposalSiteSearchQuery.append(" AND ds.deActive like :status ");
        }

        if (StringUtils.isNotEmpty(siteName)) {
            disposalSiteSearchQuery.append(" AND ds.deName like :siteName ");
        }

        disposalSiteSearchQuery.append(" ORDER BY ds.deId DESC");
        return disposalSiteSearchQuery.toString();
    }

}
