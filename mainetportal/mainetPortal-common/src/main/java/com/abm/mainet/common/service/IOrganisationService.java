package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.OrganisationEntity;
import com.abm.mainet.common.domain.ViewOrgDetails;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.TbOrganisationRest;
import com.abm.mainet.common.util.ApplicationSession;

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

    Long findCountOfActiveOrg();

    public OrganisationEntity create(OrganisationEntity org);

    public void createDefault(OrganisationEntity org, ApplicationSession appSession, TbOrganisationRest tbOrgRest);

    public GroupMaster createDefaultGroup(Organisation org, GroupMaster groupMst);

    List<Organisation> findAllActiveOrganizationForHomePage(String orgStatus, int langId);

    Map<String, Set<Organisation>> findAllActiveOrganizationByULBOrgId(String orgStatus, int langId);

	public List<OrganisationDTO> getAllOrganisationActiveWorkflow(String deptCode);

	public Organisation getActiveOrgByUlbShortCode(String ulbShortCode);

	Organisation getOrgByOrgShortCode(String ulbShortCode);
}
