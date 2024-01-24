package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.DisposalMaster;

/**
 * The Interface DisposalMasterDAO.
 *
 * @author Lalit.Prusti
 */

public interface IDisposalMasterDAO {
    /**
     * Serch disposal site by site number and site name.
     *
     * @param siteNumber the site number
     * @param siteName the site name
     * @param orgId the org id
     * @return the list
     */
    List<DisposalMaster> serchDisposalSiteBySiteNumberAndSiteName(Long siteNumber, String status, String siteName, Long orgId);

    /**
     * serch Disposal Site By SiteName
     * @param si teNumber
     * @param status
     * @param siteName
     * @param orgId
     * @return
     */
    List<DisposalMaster> serchDisposalSiteBySiteName(Long siteNumber, String status, String siteName, Long orgId);

}
