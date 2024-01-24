package com.abm.mainet.rnl.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.ReportDTO;

public interface IReportDAO {

    List<ReportDTO> findRevenueReportByDateAndServiceIdAndOrgId(Date toDates, Date fromDates, Long orgId, Long serviceId);

    // D#77282
    Boolean checkEstateRegNoExist(Long esId, String regNo, Long orgId);

    List<EstateBooking> findFreezePropertyByDate(Date fromDate, Date toDate, Long propId, Long orgId);
}
