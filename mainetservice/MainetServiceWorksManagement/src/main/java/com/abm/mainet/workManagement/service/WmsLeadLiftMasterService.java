package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;

/**
 * @author saiprasad.vengurlekar
 * @Since 05-Dec-2017
 */
public interface WmsLeadLiftMasterService {
    /**
     * this service is used for creating Lead-Lift Master.
     * @param dto
     * @return
     */
    void addLeadLiftMasterEntry(List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDtos);

    /**
     * this service is used for Populating Lead-Lift Master Grid .
     * @param orgid
     * @param sorId
     * @param leLiFlag
     * @return @return List<WmsLeadLiftMasterDto> if record found else return empty list
     */
    List<WmsLeadLiftMasterDto> toCheckLeadLiftEntry(Long sorId, String leLiFlag, Long orgId);

    /**
     * this service is used for search Lead-Lift Master Active Records for organization .
     * @param orgid
     * @return List<WmsLeadLiftMasterDto> for search criteria if record found else return empty list
     */
    List<WmsLeadLiftMasterDto> searchLeadLiftDetails(Long orgId);

    /**
     * this service is used To get Lead-Lift Master Active Records .
     * @param sorId
     * @param leLiFlag
     * @param orgid
     * @return List<WmsLeadLiftMasterDto> if record found else return empty list
     */
    List<WmsLeadLiftMasterDto> editLeadLiftData(Long sorId, String leLiFlag, Long orgId);

    /**
     * this service is used to inactive Lead-Lift Master details by sor id and set active flag to "N"
     * @param sorId
     * @param leLiFlag
     * @param empId
     * @return
     */
    void inactiveLeadLiftMaster(Long sorId, String leLiFlag, Long empId);

    /**
     * this service is used to update Lead-Lift Master form details
     * @param List<WmsLeadLiftMasterDto>
     * @param empId
     * @param ids
     * @return
     */
    void saveAndUpdateMaster(List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDtos, Long empId, String ids);

    /**
     * this service is use to check Duplicate Entries For uploaded ExcelSheet
     * @param dto
     * @param orgid
     * @param leLiFlag
     * @param sorId
     * @return status
     */
    String checkDuplicateExcelData(WmsLeadLiftMasterDto dto, Long orgId, String leLiFlag, Long sorId);

}
