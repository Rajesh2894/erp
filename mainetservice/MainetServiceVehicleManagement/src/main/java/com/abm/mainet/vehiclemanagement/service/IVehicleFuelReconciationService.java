package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDTO;

/**
 * The Interface VehicleFuelReconciationService.
 */
@WebService
public interface IVehicleFuelReconciationService {

    /**
     * Delete.
     *
     * @param inrecId the inrec id
     */
    void delete(Long inrecId, Long empId, String ipMacAdd);

    /**
     * Gets the by id.
     *
     * @param inrecId the inrec id
     * @return the by id
     */
    VehicleFuelReconciationDTO getById(Long inrecId);

    /**
     * Save.
     *
     * @param vehicleFuelReconciationDetails the vehicle fuel reconciation details
     * @return the vehicle fuel reconciation DTO
     */
    VehicleFuelReconciationDTO save(VehicleFuelReconciationDTO vehicleFuelReconciationDetails);

    /**
     * Update.
     *
     * @param vehicleFuelReconciationDetails the vehicle fuel reconciation details
     * @return the vehicle fuel reconciation DTO
     */
    VehicleFuelReconciationDTO update(VehicleFuelReconciationDTO vehicleFuelReconciationDetails);

    /**
     * Search.
     *
     * @param veId the ve id
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return the list
     */
    List<VehicleFuelReconciationDTO> search(Long veId, Date fromDate, Date toDate, Long orgId);

    /**
     * search VendorId By PumpId
     * @param puId
     * @param orgid
     * @return
     */
    Long searchVendorIdByPumpId(Long puId, long orgid);

	boolean isExistingInvoice(Long invoiceNo, long orgid);

	boolean checkIsFuellingReconciled(Long vefId, Long orgId);

}
