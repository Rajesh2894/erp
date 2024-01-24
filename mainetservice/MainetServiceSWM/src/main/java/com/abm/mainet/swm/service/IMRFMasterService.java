package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.MRFMasterDto;

/**
 * IMRFMasterService
 * 
 * @author Ajay.Kumar
 *
 */
@WebService
public interface IMRFMasterService {

    /**
     * save
     * @param mRFMasterDtoDetails
     * @return
     */
    MRFMasterDto save(MRFMasterDto mRFMasterDtoDetails);

    /**
     * serch MRF Master By PlantId And Plantname
     * @param plantId
     * @param plantname
     * @param orgId
     * @return
     */
    List<MRFMasterDto> serchMRFMasterByPlantIdAndPlantname(String plantId, String plantname, Long orgId);

    /**
     * get PlantName By PlantId
     * @param plantId
     * @return
     */
    MRFMasterDto getPlantNameByPlantId(Long plantId);

    /**
     * update
     * @param mRFMasterDtoDetails
     * @return
     */
    MRFMasterDto update(MRFMasterDto mRFMasterDtoDetails);

    /**
     * delete MRF Master Dto
     * @param plantId
     * @param empId
     * @param ipMacAdd
     */
    void deleteMRFMasterDto(Long plantId, Long empId, String ipMacAdd);

    /**
     * find Day Month Wise MrfCenter
     * @param orgId
     * @param mrfId
     * @param fromDate
     * @param toDate
     * @return
     */
    MRFMasterDto findDayMonthWiseMrfCenter(Long orgId, Long mrfId, Date fromDate, Date toDate);

    /**
     * serch Mrf Center
     * @param plantId
     * @param plantname
     * @param orgId
     * @return
     */
    List<MRFMasterDto> serchMrfCenter(String plantId, String plantname, Long orgId);

    
    /**
     * serch Mrf Center
     * @param plantId
     * @param plantname
     * @param orgId
     * @param propertyNo
     * @return
     */
    List<MRFMasterDto> serchMrfCenter(Long orgId, String propertyNo);
    
    boolean getPropertyDetailsByPropertyNumber(String propertyNo);
    
    /**
     * down load Mrf Site Images
     * @param mRFMastercenter
     * @return
     */
    List<MRFMasterDto> downloadMrfSiteImages(List<MRFMasterDto> mRFMastercenter);

    /**
     * validate MRF Master
     * @param mRFMasterDto
     * @return
     */
    boolean validateMRFMaster(MRFMasterDto mRFMasterDto);

}
