package com.abm.mainet.legal.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.legal.domain.AdvocateMaster;
import com.abm.mainet.legal.dto.AdvocateEducationDetailDTO;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;

/**
 * This is mainly for CRUD operation
 * @author Lalit.Prusti
 *
 */

@WebService
public interface IAdvocateMasterService {
    /**
     * This method will save form content in the database
     * @param AdvocateMasterDto
     */
    void saveAdvocateMaster(AdvocateMasterDTO AdvocateMasterDto);

    /**
     * This method will help us to edit the content of form
     * @param AdvocateMasterDto
     */
    void updateAdvocateMaster(AdvocateMasterDTO AdvocateMasterDto);

    /**
     * This will delete The entry of Advocate
     * @param id
     */
    boolean deleteAdvocateMaster(AdvocateMasterDTO AdvocateMasterDto);

    /**
     * This will return active Advocate list
     * @return
     */
    List<AdvocateMasterDTO> getAllAdvocateMaster(Long orgId);

    /**
     * This will return active Advocate list for current Organization
     * @param orgid
     * @return
     */
    List<AdvocateMasterDTO> getAllAdvocateMasterByOrgid(Long orgid);

    /**
     * This will help us to get Advocate Details by id
     * @param id
     * @return
     */
    AdvocateMasterDTO getAdvocateMasterById(Long id);

    /**
     * This method validate the given property already exist in DB or not
     * @param mobile
     * @param email
     * @param pancard
     * @param aadhar
     * @param advId
     * @param orgid
     * @return
     */
   // List<AdvocateMasterDTO> validateAdvocateMaster(String mobile, String email, String pancard, String aadhar, Long advId, Long orgid);

	List<AdvocateMasterDTO> getAdvocateMasterDetails(Long advId, Long crtId, String barCouncilNo, String advStatus,Long orgId);

	List<AdvocateMasterDTO> validateAdvocateMaster(AdvocateMasterDTO advocateMasterDTO);

	AdvocateMasterDTO saveAdvocateMasterData(AdvocateMasterDTO advocateMasterDto);

	AdvocateMasterDTO findAdvDetByAppId(Long orgid, Long applicationId);

	void saveDetails(AdvocateMasterDTO advocateMasterDTO);

	List<AdvocateEducationDetailDTO> findEducationDetById(Long advId, Long orgid);

}
