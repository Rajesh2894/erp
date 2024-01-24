package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewOrgDetails;

public interface IOrganisationDAO {

    public abstract Organisation getOrganisationById(long orgid, String orgStatus);

    /**
     * To get list of {@link Organisation}
     * @param defaultStatus (Y/N)
     * @param orgStatus (A/I)
     * @return List<{@link Organisation}>
     */
    public abstract List<Organisation> getOrganisations(String defaultStatus, String orgStatus);

    /**
     * To get list of {@link Organisation}
     * @param defaultStatus (Y/N)
     * @param orgStatus (A/I)
     * @param districtCpdId ('DIS' prefix CpdId)
     * @return List<{@link Organisation}>
     */
    public abstract List<Organisation> getOrganisations(String defaultStatus, String orgStatus,
            long districtCpdId);

    public abstract List<ViewOrgDetails> getOrganisationsNew(long districtCpdId);

    List<Organisation> findAllActiveOrganization(String orgStatus);

    public Organisation getOrganisationByShortName(String orgShortName);
    
    Organisation getActiveOrgByUlbShortCode(String ulbShortCode, String orgStatus);

	List<Object[]> getOrganizationActiveWithWorkflow(Long deptId);
	
	Organisation getOrganisationByOrgName(String orgName);
}