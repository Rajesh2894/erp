package com.abm.mainet.common.master.service;

import com.abm.mainet.common.dto.HolidayMasterDto;

/**
 * this service is used to push created or updated Holiday data from MAINet to Other Applications like GRP
 * @author hiren.poriya
 * @Since 24-Jul-2018
 */
public interface IHolidayProvisionService {

    /**
     * this service is used to create Holidays to GRP environment if GRP posting flag is 'Y'. if yes than only Location data push
     * from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param HolidayMasterDto
     */
    void createHoliday(HolidayMasterDto holidayMasterCreate);

    /**
     * this service is used to create Holidays to GRP environment if GRP posting flag is 'Y'. if yes than only Location data push
     * from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param HolidayMasterDto
     */
    void updateHoliday(HolidayMasterDto holidayDto);

}
