package com.abm.mainet.swm.service;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.SolidWasteBillMasterDTO;
import com.abm.mainet.swm.dto.UserChargeCollectionDTO;

/**
 * The Interface BillMasterService.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 19-Jun-2018
 */
@WebService
public interface ISolidWasteBillMasterService {

    /**
     * Delete.
     *
     * @param billId the bill id
     * @return true, if successful
     */
    boolean delete(Long billId, Long empId, String ipMacAdd);

    /**
     * Gets the by id.
     *
     * @param billId the bill id
     * @return the by id
     */
    SolidWasteBillMasterDTO getById(Long billId);

    /**
     * Save.
     *
     * @param billMaster the bill master
     * @return the bill master DTO
     */
    SolidWasteBillMasterDTO save(SolidWasteBillMasterDTO billMaster);

    /**
     * Update.
     *
     * @param billMaster the bill master
     * @return the bill master DTO
     */
    SolidWasteBillMasterDTO update(SolidWasteBillMasterDTO billMaster);
    
    UserChargeCollectionDTO getUserChargeDetails(Long MonthNo);

}
