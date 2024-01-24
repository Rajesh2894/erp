package com.abm.mainet.vehiclemanagement.service;

import java.util.List;

import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;

/**
 * The Interface PumpMasterService.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 04-May-2018
 */
public interface IPumpMasterService {

    /**
     * Gets the pump by pump id.
     *
     * @param PumpId the pump id
     * @return the pump by pump id
     */
    PumpMasterDTO getPumpByPumpId(Long pumpId);

    /**
     * Save pump master.
     *
     * @param PumpIdDetails the pump id details
     * @return the pump master DTO
     */
	PumpMasterDTO savePumpMaster(PumpMasterDTO pumpIdDetails, List<Long> removeIds);

    /**
     * Update pump master.
     *
     * @param PumpIdDetails the pump id details
     * @return the pump master DTO
     */
    PumpMasterDTO updatePumpMaster(PumpMasterDTO pumpIdDetails);

    /**
     * Delete pump master.
     *
     * @param PumpId the pump id
     */
    void deletePumpMaster(Long pumpId, Long empId, String ipMacAdd);

    /**
     *
     * @param pumpType
     * @param pumpName
     * @return List Of Pump matching with search criteria
     */
    List<PumpMasterDTO> serchPumpMasterByPumpType(Long pumpType, String puPumpname, Long orgId);

    /**
     * getAllPumpMaster
     * @param pumpType
     * @param orgId
     * @return
     */
    List<PumpMasterDTO> getAllPumpMaster(Long pumpType, Long orgId);

    /**
     * @param poumpMasterDTO
     * @return
     */
    boolean validatePumpMaster(PumpMasterDTO poumpMasterDTO);

    /**
     * serchPumpMaster
     * @param pumpType
     * @param puPumpname
     * @param orgId
     * @return
     */
    List<PumpMasterDTO> serchPumpMaster(Long pumpType, String puPumpname, Long orgId);

	List<PumpMasterDTO> getPumpIdAndNamesListByVeID(Long vehId, Long orgId);


}
