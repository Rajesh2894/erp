package com.abm.mainet.legal.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.legal.dto.CourtMasterDTO;

/**
 * This is mainly for CRUD operation
 * @author satish.kadu
 *
 */
@WebService
public interface ICourtMasterService {

    /**
     * This method will save form content in the database
     * @param courtMasterDto
     */
    void saveCourtMaster(CourtMasterDTO courtMasterDto);

    /**
     * This method will help us to edit the content of form
     * @param courtMasterDto
     */
    void updateCourtMaster(CourtMasterDTO courtMasterDto);

    /**
     * This will delete The entry of court
     * @param id
     */
    boolean deleteCourtMaster(CourtMasterDTO courtMasterDto);

    /**
     * This will return active court list
     * @return
     */
    List<CourtMasterDTO> getAllActiveCourtMaster(Long orgid);

    /**
     * This will help us to get Court Details by id
     * @param id
     * @return
     */
    CourtMasterDTO getCourtMasterById(Long id);

    /**
     * This will return All court list
     * @param orgid
     * @return
     */
    List<CourtMasterDTO> getAllCourtMaster(Long orgid);

	String getCourtNameById(Long id);

	List<CourtMasterDTO> getCourtMasterDetailByIds(Long crtId, Long crtType, Long orgid);

	List<Long> getCaseTypeByCourtId(Long crtId, Long orgId);

}
