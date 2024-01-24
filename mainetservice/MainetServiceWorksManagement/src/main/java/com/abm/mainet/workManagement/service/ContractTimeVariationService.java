package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.common.master.dto.ContractDetailDTO;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface ContractTimeVariationService {

    /**
     * update Contract Time Variation
     * @param contractDetDTO
     */
    void updateContractTimeVariation(ContractDetailDTO contractDetDTO);

    /**
     * delete Variation File By Id
     * @param variationFileById
     * @param empId
     */
    void deleteVariationFileById(List<Long> variationFileById, Long empId);
}
