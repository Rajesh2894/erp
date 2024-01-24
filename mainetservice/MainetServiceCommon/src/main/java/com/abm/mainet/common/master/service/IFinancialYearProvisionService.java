package com.abm.mainet.common.master.service;

import com.abm.mainet.common.master.dto.TbFincialyearorgMap;

/**
 * this service is used to push created or updated Financial Year Organization mapping data from MAINet to Other Applications like
 * GRP
 * @author hiren.poriya
 * @Since 24-Jul-2018
 */
public interface IFinancialYearProvisionService {

    /**
     * this service is used to create Financial Year- Organization Mapping to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Financial Year- Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param savedMappingDto
     */
    void createFinYearMapping(TbFincialyearorgMap savedMappingDto);

    /**
     * this service is used to update Financial Year- Organization Mapping to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Financial Year- Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param updatedMappingDto
     */
    void updateFinYearMapping(TbFincialyearorgMap updatedMappingDto);

}
