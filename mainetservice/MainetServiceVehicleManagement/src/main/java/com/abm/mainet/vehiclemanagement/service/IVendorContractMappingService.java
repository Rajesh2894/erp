/*
 * 
 */
package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.vehiclemanagement.dto.VendorContractMappingDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface VehicleMasterService.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 30-May-2018
 */
public interface IVendorContractMappingService {

    /**
     * Delete vendor contract mapping.
     *
     * @param vendorContractMappingId the vendor contract mapping id
     */
    void delete(Long vendorContractMappingId, Long empId, String ipMacAdd);

    /**
     * Gets the by vendor contract mapping id.
     *
     * @param vendorContractMapping the vendor contract mapping
     * @return the by id
     */
    VendorContractMappingDTO getById(Long vendorContractMapping);

    /**
     * Save vendor contract mapping.
     *
     * @param vendorContractMappingDetails the vendor contract mapping details
     * @return the vendor contract mapping DTO
     */
    VendorContractMappingDTO save(VendorContractMappingDTO vendorContractMappingDetails);

    /**
     * Update vendor contract mapping.
     *
     * @param vendorContractMappingDetails the vendor contract mapping details
     * @return the vendor contract mapping DTO
     */
    VendorContractMappingDTO update(VendorContractMappingDTO vendorContractMappingDetails);

    /**
     * Find contract dept wise.
     *
     * @param orgId the org id
     * @param tbDepartment the tb department
     * @param flag the flag
     * @return the list
     */
    List<ContractMappingDTO> findContractDeptWise(Long orgId, TbDepartment tbDepartment, String flag);

    /**
     * Find contract.
     *
     * @param orgId the org id
     * @param contractNo the contract no
     * @param contactDate the contact date
     * @param tbDepartment the tb department
     * @return the list
     */
    List<ContractMappingDTO> findContract(Long orgId, String contractNo, Date contactDate, TbDepartment tbDepartment);

    /**
     * Find contracts by contract id.
     *
     * @param orgId the org id
     * @param contId the cont id
     * @param tbDepartment the tb department
     * @return the list
     */
    List<ContractMappingDTO> findContractsByContractId(Long orgId, Long contId, TbDepartment tbDepartment);

    /**
     * Save.
     *
     * @param vendorContractMappingDTOList the vendor contract mapping DTO list
     * @return the list
     */
    List<VendorContractMappingDTO> save(List<VendorContractMappingDTO> vendorContractMappingDTOList);

    /**
     * Gets the by id.
     *
     * @param contractId the contract id
     * @param orgId the org id
     * @return the by id
     */
    List<VendorContractMappingDTO> getById(Long contractId, Long orgId);

    /**
     * find Designation By Id
     * @param desigId
     * @return
     */
    String findDesignationById(Long desigId);

    /**
     * validate Contract Mapping
     * @param vendorMappingList
     * @return
     */
    boolean validateContractMapping(List<VendorContractMappingDTO> vendorMappingList);

}
