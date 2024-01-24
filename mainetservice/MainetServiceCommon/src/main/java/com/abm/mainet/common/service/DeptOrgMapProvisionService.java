package com.abm.mainet.common.service;

/**
 * This Service is used to push Deptartmen_organisation Mapping data from MAINet to other application like GRP.
 * @author hiren.poriya
 * @Since 19-Jul-2018
 */
public interface DeptOrgMapProvisionService {

    /**
     * this service is used to create Department Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Department-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param savedDeptOrgMapDTO
     */
    // void createDeptOrgMapping(TbDeporgMap savedDeptOrgMapDTO);

    /**
     * this service is used to update Department Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Department-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param updatedMapDTO
     */
    // void updateDeptOrgMapping(TbDeporgMap updatedMapDTO);

}
