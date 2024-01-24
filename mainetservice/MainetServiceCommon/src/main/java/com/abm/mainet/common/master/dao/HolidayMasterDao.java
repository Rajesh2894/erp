package com.abm.mainet.common.master.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.HolidayMaster;
import com.abm.mainet.common.domain.WorkTimeMaster;

public interface HolidayMasterDao {

    WorkTimeMaster saveWorkTimeMaster(WorkTimeMaster workTimeEntity);

    WorkTimeMaster getworkTimeEntity(Long orgId);

    boolean saveDeleteHolidayMaster(long orgid, Long hoId);

    HolidayMaster saveHolidayDetailsList(HolidayMaster holidayMaster);

    List<HolidayMaster> getGridData(Date yearStartDate, Date yearEndDate, long orgid);

    List<HolidayMaster> getHolidayDetailsList(Date startDate, Date endDate, long orgid);

    void deleteHolidayDetailsList(HolidayMaster holidayMasterOld, long l);

    HolidayMaster getHolidayEntity(Long hoId);
    
    List<HolidayMaster> getHolidayDate(Date holidayDate, long orgid);

}
