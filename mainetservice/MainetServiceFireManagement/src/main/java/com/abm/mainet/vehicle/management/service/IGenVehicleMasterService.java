/*package com.abm.mainet.vehicle.management.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.firemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;



*//**
 * The Interface VehicleMasterService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 *//*

@WebService
public interface IGenVehicleMasterService {

    *//**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @param orgId the org id
     * @return the list
     *//*
    List<GenVehicleMasterDTO> searchVehicleByVehicleTypeAndVehicleRegNo(Long vehicleType, String vehicleRegNo,Long deptId,Long location, Long orgId);
    
    List<GenVehicleMasterDTO> fetchVeNoByVehicleTypeIdAndDeptId(Long vehicleType,Long deptId, Long orgId);
    
    

    *//**
     * Gets the vehicle by vehicle id.
     *
     * @param vehicleId the vehicle id
     * @return the vehicle by vehicle id
     *//*
    GenVehicleMasterDTO getVehicleByVehicleId(Long vehicleId);

    *//**
     * Save vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     *//*
    GenVehicleMasterDTO saveVehicle(GenVehicleMasterDTO vehicleIdDetails);

    *//**
     * Update vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     *//*
    GenVehicleMasterDTO updateVehicle(GenVehicleMasterDTO vehicleIdDetails);

    *//**
     * Delete vehicle.
     *
     * @param vehicleId the vehicle id
     *//*
    void deleteVehicle(Long vehicleId, Long empId, String ipMacAdd);

    *//**
     * searchVehicle
     * @param vehicleType
     * @param vehicleRegNo
     * @param orgId
     * @return
     *//*
    List<GenVehicleMasterDTO> searchVehicle(Long vehicleType, String vehicleRegNo,Long deptId,Long location, Long orgId);

    *//**
     * validate Vehiclen Master
     * @param vehicle
     * @return
     *//*
    boolean validateVehiclenMaster(GenVehicleMasterDTO vehicle);

    *//**
     * search Scheduled Vehicle By orgId
     * @param orgId
     * @return
     *//*
    List<GenVehicleMasterDTO> searchScheduledVehicleByorgId(Long orgId);
    
    List<GenVehicleMasterDTO> findAll(Long orgid);

	List<Object[]> getAllVehicles(Long orgId);
	

	 public List<Object[]> getVehicleDetails(Long orgid); 

}
*/