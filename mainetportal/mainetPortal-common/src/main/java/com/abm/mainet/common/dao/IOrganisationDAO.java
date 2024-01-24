package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.OrganisationEntity;
import com.abm.mainet.common.domain.ViewOrgDetails;

public interface IOrganisationDAO {

    public abstract Long getOrgid();

    public abstract Organisation getOrganisationById(long orgid, String orgStatus);

    /**
     * To get list of {@link Organisation}
     * @param defaultStatus (Y/N)
     * @param orgStatus (A/I)
     * @return List<{@link Organisation}>
     */
    public abstract List<Organisation> getOrganisations(String defaultStatus, String orgStatus);

    public abstract List<Organisation> getOrganisations(String defaultStatus, String orgStatus,
            long districtCpdId);

    public abstract List<ViewOrgDetails> getOrganisationsNew(long districtCpdId);

    public abstract OrganisationEntity create(OrganisationEntity org);

    List<Organisation> findAllActiveOrganization(String orgStatus);

    Long findCountOfOrg();

    List<Organisation> findAllActiveOrganizationForHomePage(String orgStatus, int langId);

    List<Organisation> findAllActiveOrganizationByOrgId(String orgStatus);

	Organisation getActiveOrgByUlbShortCode(String ulbShortCode, String orgStatus);

	Organisation getOrgByOrgShortCode(String ulbShortCode);

}