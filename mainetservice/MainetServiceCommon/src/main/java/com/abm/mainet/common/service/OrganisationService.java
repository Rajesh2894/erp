package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewOrgDetails;

/**
 * @author Swapnil.Pisat
 */
@Service
public class OrganisationService implements IOrganisationService {

    @Autowired
    private IOrganisationDAO organisationDAO;

    @Override
    @Transactional
    public Organisation getOrganisationById(final long orgid) {
        return organisationDAO.getOrganisationById(orgid, MainetConstants.STATUS.ACTIVE);
    }

    /**
     * Get SuperUser {@link Organisation} means Organization.defaultStatus = 'Y'.
     * @return List<{@link Organisation}>
     */
    @Override
    @Transactional
    public Organisation getSuperUserOrganisation() {
        final List<Organisation> organisations = organisationDAO.getOrganisations(MainetConstants.Organisation.SUPER_ORG_STATUS,
                MainetConstants.STATUS.ACTIVE);
        return organisations.get(0);
    }

    @Override
    @Transactional
    public List<Organisation> getAllMunicipalOrganisation(final long districtCpdId) {
        final List<Organisation> organisations = organisationDAO.getOrganisations("", MainetConstants.STATUS.ACTIVE,
                districtCpdId);
        return organisations;
    }

    @Override
    public List<ViewOrgDetails> getAllMunicipalOrganisationNew(
            final long districtCpdId) {
        return organisationDAO.getOrganisationsNew(districtCpdId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.service.IOrganisationService#findAllActiveOrganization(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Organisation> findAllActiveOrganization(final String orgStatus) {
        return organisationDAO.findAllActiveOrganization(orgStatus);
    }
    

	@Override
	public Organisation getActiveOrgByUlbShortCode(String ulbShortCode) {
		return organisationDAO.getActiveOrgByUlbShortCode(ulbShortCode, MainetConstants.FlagA);
	}
	
	
	@Override
	public Organisation getActiveOrgByUlbBName(String ulbName) {
		return organisationDAO.getOrganisationByOrgName(ulbName);
	}
}
