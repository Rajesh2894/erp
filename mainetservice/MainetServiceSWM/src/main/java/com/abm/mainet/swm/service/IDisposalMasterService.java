package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.DisposalMasterDTO;

/**
 * The Interface DisposalMasterService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@WebService
public interface IDisposalMasterService {

    /**
     * Serch disposal site by site number and site name.
     *
     * @param siteNumber the site number
     * @param siteName the site name
     * @param orgId the org id
     * @return the list
     */
    List<DisposalMasterDTO> serchDisposalSiteBySiteNumberAndSiteName(Long siteNumber, String siteName, Long orgId);

    /**
     * Gets the disposal site by site number.
     *
     * @param siteNumber the site number
     * @return the disposal site by site number
     */
    DisposalMasterDTO getDisposalSiteBySiteNumber(Long siteNumber);

    /**
     * Save disposal site.
     *
     * @param siteDetails the site details
     * @return the disposal master DTO
     */
    DisposalMasterDTO saveDisposalSite(DisposalMasterDTO siteDetails);

    /**
     * Update disposal site.
     *
     * @param siteDetails the site details
     * @return the disposal master DTO
     */
    DisposalMasterDTO updateDisposalSite(DisposalMasterDTO siteDetails);

    /**
     * Delete disposal site.
     *
     * @param siteNumber the site number
     */
    void deleteDisposalSite(Long siteNumber, Long empId, String ipMacAdd);

    /**
     * find Day Month Wise Dumping
     * @param orgId
     * @param deName
     * @param fromDate
     * @param toDate
     * @return
     */
    DisposalMasterDTO findDayMonthWiseDumping(Long orgId, Long deName, Date fromDate, Date toDate);

    /**
     * serch Disposal Site
     * @param siteNumber
     * @param siteName
     * @param orgId
     * @return
     */
    List<DisposalMasterDTO> serchDisposalSite(Long siteNumber, String siteName, Long orgId);

    /**
     * download Disposal Site Images
     * @param disposalsites
     * @return
     */
    List<DisposalMasterDTO> downloadDisposalSiteImages(List<DisposalMasterDTO> disposalsites);

    /**
     * validate Disposal Master
     * @param disposalMasterDTO
     * @return
     */
    boolean validateDisposalMaster(DisposalMasterDTO disposalMasterDTO);

}
