package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;

public interface WmsMaterialMasterService {

    /**
     * save Update Material List
     * @param materialMasterListDto
     * @param removeIds
     */
    void saveUpdateMaterialList(List<WmsMaterialMasterDto> materialMasterListDto, List<Long> removeIds);

    /**
     * get Material List By SorId
     * @param sorId
     * @return
     */
    List<WmsMaterialMasterDto> getMaterialListBySorId(Long sorId);

    /**
     * delete Material By Id
     * @param sorId
     */
    void deleteMaterialById(Long sorId);

    /**
     * find All Active Material By Sor Mas
     * @param orgId
     * @return
     */
    List<ScheduleOfRateMstDto> findAllActiveMaterialBySorMas(long orgId);

    /**
     * check Duplicate Excel Data
     * @param wmsMaterialMasterDto
     * @param orgid
     * @return
     */
    String checkDuplicateExcelData(WmsMaterialMasterDto wmsMaterialMasterDto, long orgid);

    /**
     * save And Delete Error Details
     * @param errorDetails
     * @param orgId
     * @param masterType
     */
    void saveAndDeleteErrorDetails(List<WmsErrorDetails> errorDetails, Long orgId, String masterType);

    /**
     * get Error Log
     * @param orgId
     * @param masterType
     * @return
     */
    List<WmsErrorDetails> getErrorLog(Long orgId, String masterType);

}
