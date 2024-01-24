package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.WasteRateMasterDTO;

/**
 * @author Ajay.Kumar
 *
 */
public interface IWasteRateMasterService {

    /**
     * save
     * @param wasteRateMasterdto
     */
    void save(WasteRateMasterDTO wasteRateMasterdto);

    /**
     * update
     * @param wasteRateMasterdto
     */
    void update(WasteRateMasterDTO wasteRateMasterdto);

    /**
     * get All waste Rate
     * @return
     */
    List<WasteRateMasterDTO> getAllwasteRate();

    /**
     * get All Rate
     * @param orgid
     * @return
     */
    List<WasteRateMasterDTO> getAllRate(Long orgid);

    /**
     * get Waste Rate ById
     * @param id
     * @return
     */
    WasteRateMasterDTO getWasteRateById(Long id);

    /**
     * validate Entry WasteType
     * @param wasteRateMasterdto
     * @return
     */
    boolean validateEntryWasteType(WasteRateMasterDTO wasteRateMasterdto);

    Long getPrefixLevel(String prefix, Long orgId);

}
