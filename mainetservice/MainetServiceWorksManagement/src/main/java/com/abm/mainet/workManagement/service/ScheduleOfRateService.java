package com.abm.mainet.workManagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
public interface ScheduleOfRateService {

    /**
     * this service is used for creating schedule rate.
     * @param dto
     * @return
     */
    ScheduleOfRateMstDto createSchedule(ScheduleOfRateMstDto dto);

    /**
     * update Schedule of rate master form details
     * @param mstDto
     * @param removeChildIds
     */
    void updateSchedule(ScheduleOfRateMstDto mstDto, List<Long> removeChildIds);

    /**
     * it is used to get all active Schedule of Rate Master Details List
     * @param orgid
     * @return List<ScheduleOfRateMstDto> if record found else return empty list
     */
    List<ScheduleOfRateMstDto> getAllActiveScheduleRateMstList(long orgid);

    /**
     * this service is used search SOR Active Records by Sor Type or Sor name or sor from date or sor to date with org id.
     * @param orgid
     * @param sorType
     * @param sorName
     * @param sorFromDate
     * @param sorToDate
     * @return List<ScheduleOfRateMstDto> for search criteria if record found else return empty list
     */
    List<ScheduleOfRateMstDto> searchSorRecords(long orgid, Long sorNameId, Date sorFromDate, Date sorToDate);

    /**
     * used to inactive SOR master details by sor id and set active flag to "N"
     * @param empId
     * @param sorId
     */
    void inactiveSorMaster(Long sorId, Long empId);

    /**
     * find SOR Master details by sorId(primary key)
     * @param sorId
     * @param organisation
     * @return ScheduleOfRateMstDto with All child details records if found else return null.
     */
    ScheduleOfRateMstDto findSORMasterWithDetailsBySorId(Long sorId);

    /**
     * find All SOR Names by orgId
     * @param orgid
     * @return
     */
    List<Object[]> findAllSorNamesByOrgId(long orgid);

    /**
     * it is used to get all active and inactive Schedule of Rate Master Details List
     * @param orgid
     * @return List<ScheduleOfRateMstDto> if record found else return empty list
     */
    List<ScheduleOfRateMstDto> getAllScheduleRateMstList(long orgid);

    /**
     * get Existing Active SOR Type by SOR name Id
     * @param sorCpdId
     * @param orgId
     * @return if records present than return entity else return null;
     */
    ScheduleOfRateMstEntity findExistingActiveSorType(Long sorCpdId, Long orgId);

    /**
     * it is used to get all active Schedule of Rate Item Details List
     * @param orgid
     * @return List<ScheduleOfRateDetDto> if record found else return empty list
     */
    List<ScheduleOfRateDetDto> getAllActiveSorDetails(long orgId);

    /**
     * this service is used to find item details by using SOR details primary key sordId
     * @param sordId
     * @return
     */
    ScheduleOfRateDetDto findSorItemDetailsBySordId(long sordId);

    /**
     * used to get SOR Details ByI temCode and SOR Id
     * 
     * @param orgId
     * @param itemCode
     * @param sorId
     * @return
     */
    ScheduleOfRateDetDto getSorDetailsByItemCode(Long orgId, String itemCode, Long sorId);

    /**
     * used to get sor items by chapter id
     * @param chapterValue
     * @param sorId
     * @param orgId
     * @return
     */
    List<ScheduleOfRateDetDto> getAllItemsListByChapterId(Long chapterValue, Long sorId, long orgId);

	
	public ArrayList getChapterList(Long orgId, Long sorId);

}
