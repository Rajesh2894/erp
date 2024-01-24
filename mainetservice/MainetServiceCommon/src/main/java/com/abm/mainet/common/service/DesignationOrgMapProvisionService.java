package com.abm.mainet.common.service;

import com.abm.mainet.common.master.dto.TbOrgDesignation;

/**
 * this service is used to push created or updated Designation-Organization Mapping data from MAINet to Other Applications like
 * GRP.
 * @author hiren.poriya
 * @Since 20-Jul-2018
 */
public interface DesignationOrgMapProvisionService {

    /**
     * this service is used to create Designation Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Designation-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param createdDesigOrgMapDto
     */
    void createDesigOrgMapping(TbOrgDesignation createdDesigOrgMapDto);

    /**
     * this service is used to update Designation Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Designation-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param updatedDesigOrgMapDto
     */
    void updateDesigOrgMapping(TbOrgDesignation updatedDesigOrgMapDto);

}
