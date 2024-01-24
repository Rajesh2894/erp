package com.abm.mainet.common.service;

import com.abm.mainet.common.master.dto.TbLocationMas;

/**
 * this service is used to push created or updated Location data from MAINet to Other Applications like GRP
 * @author hiren.poriya
 * @Since 21-Jul-2018
 */
public interface ILocationMasProvisionService {

    /**
     * this service is used to create Location to GRP environment if GRP posting flag is 'Y'. if yes than only Location data push
     * from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param TbLocationMas DTO
     */
    void createLocation(TbLocationMas createdLocationMasDto);

    /**
     * this service is used to update Location with organization to GRP environment if GRP posting flag is 'Y'. if yes than only
     * Location data push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param TbLocationMas DTO
     */
    void updateLocation(TbLocationMas updatedLocatioDto);
}
