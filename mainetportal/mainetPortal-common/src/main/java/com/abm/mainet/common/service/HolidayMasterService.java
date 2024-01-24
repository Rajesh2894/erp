package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.WorkTimeMasterDto;

public interface HolidayMasterService {

    WorkTimeMasterDto saveWorkTimeMaster(WorkTimeMasterDto workTimeEntity, String mode,Long orgId,Long userId);

    WorkTimeMasterDto getworkTimeEntity(Long orgId);

    boolean saveDeleteHolidayMaster(Long hoId,Long orgId);

    void saveHolidayDetailsList(List<HolidayMasterDto> holidayMasterList,Long orgId,Long userId);

    List<HolidayMasterDto> getGridData(Date yearStartDate, Date yearEndDate,Long orgId);

    List<HolidayMasterDto> getHolidayDetailsList(Date startDate, Date endDate,Long orgId);

}
