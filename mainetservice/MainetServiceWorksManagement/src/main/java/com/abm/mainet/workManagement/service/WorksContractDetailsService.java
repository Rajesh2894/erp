package com.abm.mainet.workManagement.service;

import javax.jws.WebService;

import com.abm.mainet.common.master.dto.ContractMastDTO;

/**
 * This service provides methods to fetch contract details
 * 
 * @author Jeetendra.Pal
 *
 */

@WebService
public interface WorksContractDetailsService {

    /**
     * Returns the ContractMastDTO for the works
     * 
     * @param loaNumber
     * @param orgId Returns the ContractMastDTO for the works
     */
    ContractMastDTO getContractDetailsByLoaNumber(Long orgId, String loaNumber);

    /**
     * Return Work Order details
     * @param orgId
     * @param venderId
     * @return
     */
    Object getOrderDetails(Long orgId, Long venderId);

}
