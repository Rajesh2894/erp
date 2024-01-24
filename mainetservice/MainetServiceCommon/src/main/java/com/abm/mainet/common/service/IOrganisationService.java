package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewOrgDetails;

/**
 * @author Swapnil.Pisat
 */
@Transactional(readOnly = true)
public interface IOrganisationService {
    public Organisation getOrganisationById(long orgid);

    /**
     * Get SuperUser {@link Organisation} means Organization.defaultStatus = 'Y'.
     * @return {@link Organisation}
     */
    public Organisation getSuperUserOrganisation();

    public List<Organisation> getAllMunicipalOrganisation(long districtCpdId);

    public List<ViewOrgDetails> getAllMunicipalOrganisationNew(long districtCpdId);

    List<Organisation> findAllActiveOrganization(String orgStatus);
    
    Organisation getActiveOrgByUlbShortCode(String ulbShortCode);
    
    Organisation getActiveOrgByUlbBName(String ulbName);
}
