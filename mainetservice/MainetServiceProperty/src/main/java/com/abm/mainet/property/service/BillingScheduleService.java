/**
 * 
 */
package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.BillingScheduleDto;
import com.abm.mainet.property.dto.BillingShedualGridResponse;

/**
 * @author hiren.poriya
 * @since 21-Nov-2017
 */
public interface BillingScheduleService {

    /**
     * used for showing all record of current organization to show in from grid.
     * @param orgId
     * @return
     */
    List<BillingShedualGridResponse> findAllRecords(Long orgId);

    /**
     * find bill schedule details by ID
     * @param billId
     * @return
     */
    BillingScheduleDto findById(Long billId);

    /**
     * update bill schedule data.
     * @param billScheduleDto
     */
    boolean updateBillingSchedule(BillingScheduleDto billScheduleDto, List<BillingScheduleDto> billSchDtoList);

    /**
     * Inactive current bill schedule data
     * @param billId
     */
    void deleteBillSchedule(Long billId, Long orgId);

    List<FinancialYear> findAllFinYearNotMapInBillSchByOrgId(Long orgId);

    List<Object[]> getAllBillScheduleFromDate(Date startDate, Date currDate, Long orgid);

    BillingScheduleDetailEntity getSchDetailByScheduleId(Long schDetId, Long orgId);

    BillingScheduleDetailEntity getSchedulebySchFromDate(Long orgId, Date schFromDate);

    List<BillingScheduleDetailEntity> getNextScheduleFromLastPayDet(Long orgId, Long schDetId);

    boolean saveBillingSchedule(BillingScheduleDto billingScheduleDto, List<BillingScheduleDto> billSchDtoList,
            List<Long> finYearIdList, Organisation organisation);

    List<BillingScheduleDto> getDueDateDetail(Long billId, Organisation organisation);

    List<BillingScheduleDto> createSchedule(Long schFreq, List<BillingScheduleDto> billSchDtoUpdList,
            Organisation organisation);

    List<Object[]> getBillscheduleByOrgid(Long orgId);

    List<BillingScheduleDetailEntity> getSchListFromGivenDateTillCurDate(Date schDate, Long orgId, Date currentDate);

    List<BillingScheduleDetailEntity> getSchListFromschIdTillCurDate(Long schDetId, Long orgId, Date currentDate);

    List<BillingScheduleDetailEntity> getSchedulebyOrgId(Long orgId);

}
