package com.abm.mainet.common.master.service;

import com.abm.mainet.common.master.dto.TbOrganisation;

/**
 * this service is used to push created or updated Organization data from MAINet to Other Applications like
 * GRP
 * @author hiren.poriya
 * @Since 17-Aug-2018
 */
public interface IOrganisationProvisionService {
    /**
     * this service is used to create Organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Organization data push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param savedOrgDto
     */
    void createOrganisation(TbOrganisation savedOrgDto);

    /**
     * this service is used to update Organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Organization data push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param updatedOrgDto
     */
    void updateOrganisation(TbOrganisation updatedOrgDto);

}
