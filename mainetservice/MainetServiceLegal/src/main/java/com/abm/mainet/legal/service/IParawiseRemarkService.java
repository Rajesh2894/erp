package com.abm.mainet.legal.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.legal.dto.ParawiseRemarkDTO;

/**
 * This is mainly for CRUD operation. This service used to given Remark on Legal Document paragraph/section wise from
 * @author Lalit.Prusti
 *
 */

@WebService
public interface IParawiseRemarkService {
    /**
     * This method will save form content in the database
     * @param parawiseRemarkDto
     */
    void saveParawiseRemark(ParawiseRemarkDTO parawiseRemarkDto);

    /**
     * This method will save or update all content in the database
     * @param parawiseRemarkDto
     */
    void saveAllParawiseRemark(List<ParawiseRemarkDTO> parawiseRemarkDto, List<Long> removeParawiseIdsList);

    /**
     * This method will help us to edit the content of form
     * @param parawiseRemarkDto
     */
    void updateParawiseRemark(ParawiseRemarkDTO parawiseRemarkDto);

    /**
     * This will delete The entry of ParawiseRemark
     * @param id
     */
    boolean deleteParawiseRemark(ParawiseRemarkDTO ParawiseRemarkDto);

    /**
     * This will return active ParawiseRemark list
     * @return
     */
    List<ParawiseRemarkDTO> getAllParawiseRemark(Long orgid, Long caseId);

    List<ParawiseRemarkDTO> getAllParawiseRemark(Long caseId);

    /**
     * This will help us to get ParawiseRemark Details by id
     * @param id
     * @return
     */
    ParawiseRemarkDTO getParawiseRemarkById(Long id);

}
