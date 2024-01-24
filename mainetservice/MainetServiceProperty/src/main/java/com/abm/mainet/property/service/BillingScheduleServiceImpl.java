/**
 * 
 */
package com.abm.mainet.property.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.BillingScheduleMstEntity;
import com.abm.mainet.property.dto.BillingScheduleDto;
import com.abm.mainet.property.dto.BillingShedualGridResponse;
import com.abm.mainet.property.repository.BillingScheduleRepository;

/**
 * @author hiren.poriya
 * @since 21-Nov-2017
 */

@Service
public class BillingScheduleServiceImpl implements BillingScheduleService {

    @Autowired
    private BillingScheduleRepository billScheduleRepository;

    @Autowired
    private TbFinancialyearService financialyearService;

    /*
     * create bill schedule method (non-Javadoc)
     * @see com.abm.mainet.property.service.BillingScheduleService#createBillingSchedule(com.abm.mainet.property.dto.
     * BillingScheduleDto, java.util.List)
     */
    @Override
    public boolean saveBillingSchedule(BillingScheduleDto billingScheduleDto, List<BillingScheduleDto> billSchDtoList,
            List<Long> finYearIdList, Organisation organisation) {
        BillingScheduleMstEntity billEntity = null;
        int freq = Integer.parseInt(CommonMasterUtility.getNonHierarchicalLookUpObject(
                billingScheduleDto.getAsBillFrequency(), organisation).getLookUpCode());
        List<FinancialYear> financialYear = financialyearService.findAllFinancialYearById(finYearIdList);
        for (FinancialYear finYear : financialYear) {
            List<BillingScheduleDetailEntity> billdetailList = new ArrayList<>(0);
            billEntity = new BillingScheduleMstEntity();
            getBillSchMas(billingScheduleDto, billEntity, finYear);
            Date fromDate = finYear.getFaFromDate();
            getSchBillDet(billingScheduleDto, billSchDtoList, billEntity, freq, billdetailList, fromDate);
            billEntity.setBillScheduleDetail(billdetailList);
            billScheduleRepository.save(billEntity);
        }
        return true;
    }

    private void getBillSchMas(BillingScheduleDto billingScheduleDto, BillingScheduleMstEntity billEntity,
            FinancialYear finYear) {
        billEntity.setTbFinancialyear(finYear);
        billEntity.setOrgId(billingScheduleDto.getOrgId());
        billEntity.setAsBillFrequency(billingScheduleDto.getAsBillFrequency());
        billEntity.setCreatedDate(new Date());
        billEntity.setCreatedBy(billingScheduleDto.getCreatedBy());
        billEntity.setLgIpMac(Utility.getMacAddress());
        billEntity.setAsBillStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
    }

    private void getSchBillDet(BillingScheduleDto billingScheduleDto, List<BillingScheduleDto> billSchDtoList,
            BillingScheduleMstEntity billEntity, int freq, List<BillingScheduleDetailEntity> billdetailList,
            Date fromDate) {
        BillingScheduleDetailEntity billdetail = null;
        for (BillingScheduleDto billSchdto : billSchDtoList) {
            billdetail = new BillingScheduleDetailEntity();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            billdetail.setBillFromDate(cal.getTime());
            cal.add(Calendar.MONTH, +freq - 1);
            cal.set(Calendar.DAY_OF_MONTH,
                    cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            billdetail.setBillToDate(cal.getTime());
            cal.add(Calendar.MONTH, +1);
            cal.set(Calendar.DAY_OF_MONTH,
                    cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            fromDate = cal.getTime();
            billdetail.setBillScheduleMas(billEntity);
            billdetail.setNoOfDays(billSchdto.getNoOfDay());
            billdetail.setCalFromDate(billSchdto.getCalculateFrom());
            billdetail.setOrgid(billingScheduleDto.getOrgId());
            billdetail.setUserId(billingScheduleDto.getCreatedBy());
            billdetail.setLmoddate(new Date());
            billdetail.setStatus(MainetConstants.STATUS.ACTIVE);
            billdetailList.add(billdetail);
        }
    }

    /*
     * Used to find all record of bill schedule to show in form grid. (non-Javadoc)
     * @see com.abm.mainet.property.service.BillingScheduleService#findAllRecords(java.lang.Long)
     */
    @Override
    public List<BillingShedualGridResponse> findAllRecords(Long orgId) {
        BillingShedualGridResponse gridData = null;
        List<BillingShedualGridResponse> gridList = new ArrayList<>();
        try {
            List<BillingScheduleMstEntity> entityList = billScheduleRepository.findAllRecords(orgId);
            if (!entityList.isEmpty()) {
                for (BillingScheduleMstEntity entity : entityList) {
                    gridData = new BillingShedualGridResponse();
                    gridData.setId(entity.getAsBillScheid());
                    gridData.setFinancialYear(Utility.getFinancialYearFromDate(entity.getTbFinancialyear().getFaFromDate()));
                    gridData.setBillingFreq(CommonMasterUtility.findLookUpDesc(MainetConstants.Property.BillSchedule.BSC, orgId,
                            entity.getAsBillFrequency()));
                    gridList.add(gridData);
                }
            }
        } catch (Exception ex) {
            throw new FrameworkException("Exception occured while finding All Schedule record : " + ex);
        }
        return gridList;
    }

    /*
     * Used to find bill schedule record by its primary key. (non-Javadoc)
     * @see com.abm.mainet.property.service.BillingScheduleService#findById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public BillingScheduleDto findById(Long billId) {
        BillingScheduleDto billDto = null;
        BillingScheduleMstEntity entity = billScheduleRepository.findOne(billId);
        if (entity != null) {
            billDto = new BillingScheduleDto();
            billDto.setAsBillScheid(billId);
            billDto.setAsBillFrequency(entity.getAsBillFrequency());
            billDto.setOldBillFreq(entity.getAsBillFrequency());
            billDto.setOrgId(entity.getOrgId());
            billDto.setTbFinancialyears(String.valueOf(entity.getTbFinancialyear().getFaYear()));
        }
        return billDto;
    }

    @Override
    public List<BillingScheduleDto> getDueDateDetail(Long billId, Organisation organisation) {
        List<BillingScheduleDetailEntity> schDetList = billScheduleRepository.getBillScheduleDetBySchId(billId,
                organisation.getOrgid());
        List<BillingScheduleDto> billSchDtoList = new LinkedList<>();
        schDetList.forEach(schDet -> {
            BillingScheduleDto billSchDto = new BillingScheduleDto();
            billSchDto.setBillFromDate(schDet.getBillFromDate());
            billSchDto.setBillFromMonth(new SimpleDateFormat("MMMM").format(schDet.getBillFromDate()));
            billSchDto.setBillToDate(schDet.getBillToDate());
            billSchDto.setBillToMonth(new SimpleDateFormat("MMMM").format(schDet.getBillToDate()));
            billSchDto.setCalculateFrom(schDet.getCalFromDate());
            billSchDto.setNoOfDay(schDet.getNoOfDays());
            billSchDtoList.add(billSchDto);
        });
        return billSchDtoList;
    }

    /*
     * update Bill schedule record (non-Javadoc)
     * @see com.abm.mainet.property.service.BillingScheduleService#updateBillingSchedule(com.abm.mainet.property.dto.
     * BillingScheduleDto)
     */
    @Override
    public boolean updateBillingSchedule(BillingScheduleDto billScheduleDto, List<BillingScheduleDto> billSchDtoList) {
        try {
            billScheduleRepository.updateOldDetailsRecord(billScheduleDto.getAsBillScheid(), billScheduleDto.getOrgId());
            BillingScheduleMstEntity billEntity = billScheduleRepository.findBillSchById(billScheduleDto.getOrgId(),
                    billScheduleDto.getAsBillScheid());
            if (billEntity != null) {
                List<BillingScheduleDetailEntity> billdetailList = new ArrayList<>(0);
                billEntity.setAsBillFrequency(billScheduleDto.getAsBillFrequency());
                billEntity.setUpdatedDate(new Date());
                billEntity.setUpdatedBy(billScheduleDto.getUpdatedBy());
                billEntity.setLgIpMacUpd(Utility.getMacAddress());
                BillingScheduleDetailEntity billdetail = null;
                for (BillingScheduleDto billSchdto : billSchDtoList) {
                    billdetail = new BillingScheduleDetailEntity();
                    billdetail.setBillScheduleMas(billEntity);
                    billdetail.setBillFromDate(billSchdto.getBillFromDate());
                    billdetail.setBillToDate(billSchdto.getBillToDate());
                    billdetail.setCalFromDate(billSchdto.getCalculateFrom());
                    billdetail.setNoOfDays(billSchdto.getNoOfDay());
                    billdetail.setOrgid(billScheduleDto.getOrgId());
                    billdetail.setUserId(billScheduleDto.getCreatedBy());
                    billdetail.setLmoddate(new Date());
                    billdetail.setStatus(MainetConstants.STATUS.ACTIVE);
                    billdetailList.add(billdetail);
                }
                billEntity.setBillScheduleDetail(billdetailList);
                billScheduleRepository.save(billEntity);
            }
        } catch (Exception ex) {
            throw new FrameworkException("Exception occured while updating bill schedule details : " + ex);
        }
        return true;
    }

    /*
     * Inactive billing schedule (non-Javadoc)
     * @see com.abm.mainet.property.service.BillingScheduleService#BillSchedule(java.lang.Long)
     */
    @Override
    public void deleteBillSchedule(Long billId, Long orgId) {
        billScheduleRepository.deleteBillScheduleDet(billId, orgId);
        billScheduleRepository.deleteBillScheduleMas(billId, orgId);
    }

    @Override
    public List<FinancialYear> findAllFinYearNotMapInBillSchByOrgId(final Long orgId) {
        return billScheduleRepository.findAllFinYearNotMapInBillSchByOrgId(orgId);
    }

    @Override
    public BillingScheduleDetailEntity getSchedulebySchFromDate(Long orgId, Date schFromDate) {
        return billScheduleRepository.getScheduleBySchFromDate(orgId, schFromDate);
    }

    @Override
    public BillingScheduleDetailEntity getSchDetailByScheduleId(Long schDetId, Long orgId) {
        return billScheduleRepository.getSchDetailByScheduleId(schDetId, orgId);
    }

    @Override
    public List<Object[]> getAllBillScheduleFromDate(Date startDate, Date currDate,
            @Param("orgid") Long orgid) {
        return billScheduleRepository.getAllBillScheduleFromDate(startDate, currDate, orgid);
    }

    @Override
    public List<BillingScheduleDetailEntity> getNextScheduleFromLastPayDet(Long orgId, Long schDetId) {
        return billScheduleRepository.getFinanceYearListFromLastPayment(schDetId, orgId);
    }

    @Override
    public List<Object[]> getBillscheduleByOrgid(Long orgId) {
        return billScheduleRepository.getBillscheduleByOrgid(orgId);
    }

    /**
     * frequency-1 for schedule end date +1 for next schedule Start date
     */
    @Override
    public List<BillingScheduleDto> createSchedule(Long schFreq, List<BillingScheduleDto> billSchDtoUpdList,
            Organisation organisation) {
        List<BillingScheduleDto> billSchDtoList = new LinkedList<>();
        int frequency = Integer.parseInt(CommonMasterUtility.getNonHierarchicalLookUpObject(
                schFreq, organisation).getLookUpCode());
        Date fromDate = null;
        if (!billSchDtoUpdList.isEmpty()) {
            if (billSchDtoUpdList.get(0).getBillFromDate() != null) {
                fromDate = billSchDtoUpdList.get(0).getBillFromDate();
            }
        } else {
            FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
            fromDate = financialYear.getFaFromDate();
        }
        for (int i = frequency; i <= 12; i = i + frequency) {
            BillingScheduleDto billSchDto = new BillingScheduleDto();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            billSchDto.setBillFromDate(cal.getTime());
            billSchDto.setBillFromMonth(new SimpleDateFormat("MMMM").format(fromDate));
            cal.add(Calendar.MONTH, +frequency - 1);
            cal.set(Calendar.DAY_OF_MONTH,
                    cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            billSchDto.setBillToDate(cal.getTime());
            billSchDto.setBillToMonth(new SimpleDateFormat("MMMM").format(cal.getTime()));
            cal.add(Calendar.MONTH, +1);
            cal.set(Calendar.DAY_OF_MONTH,
                    cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            fromDate = cal.getTime();
            billSchDtoList.add(billSchDto);
        }
        return billSchDtoList;
    }

    @Override
    @Transactional
    public List<BillingScheduleDetailEntity> getSchListFromGivenDateTillCurDate(Date schDate, Long orgId, Date currentDate) {
        return billScheduleRepository.getSchListFromGivenDateTillCurDate(schDate, orgId, currentDate);
    }

    @Override
    @Transactional
    public List<BillingScheduleDetailEntity> getSchListFromschIdTillCurDate(Long schDetId, Long orgId, Date currentDate) {
        return billScheduleRepository.getSchListFromschIdTillCurDate(schDetId, orgId, currentDate);
    }

    @Override
    public List<BillingScheduleDetailEntity> getSchedulebyOrgId(Long orgId) {
        return billScheduleRepository.getSchedulebyOrgId(orgId);
    }

}
