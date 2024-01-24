package com.abm.mainet.water.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.water.domain.ChangeOfUsage;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ChangeOfUsageService.
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface ChangeOfUsageService {
    /**
     * Gets the word zone block by application id.
     *
     * @param applicationid the applicationid
     * @param serviceId the service id
     * @param orgId the org id
     * @return the word zone block by application id
     */
    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationid, Long serviceId, Long orgId);

    /**
     * Gets the loi charges.
     *
     * @param applicationId the application id
     * @param serviceId the service id
     * @param orgId the org id
     * @return the loi charges
     */
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId);

    /**
     * Save or update change of uses.
     *
     * @param changeOfUsesDTO the change of uses dto
     * @return the change of usage dto
     */
    ChangeOfUsageDTO saveChangeOfUses(ChangeOfUsageDTO changeOfUsesDTO);

    /**
     * Gets the change of uses.
     *
     * @param orgId the org id
     * @param applicationId the application id
     * @return the change of uses
     */
    ChangeOfUsageDTO getChangeOfUses(long orgId, long applicationId);

    /**
     * Approve change of uses.
     *
     * @param changeOfUsesDTO the change of uses dto
     * @return true, if successful
     */
    boolean approveChangeOfUses(ChangeOfUsageDTO changeOfUsesDTO);

    /**
     * Save or update change of uses.
     *
     * @param requestDTO the request dto
     * @return the change of usage dto
     */
    ChangeOfUsageDTO saveOrUpdateChangeOfUses(ChangeOfUsageRequestDTO requestDTO);

    /**
     * Gets the applicant data.
     *
     * @param applicationId the application id
     * @param orgid the orgid
     * @return the applicant data
     */
    ChangeOfUsageRequestDTO getApplicantData(Long applicationId, Long orgid);

    /**
     * @param applicationId
     * @param orgId
     * @return
     */
    TbKCsmrInfoMH getConnDetailsByChangeOfUsageConnId(Long applicationId, Long orgId);

    /**
     * @param requestDTO
     * @param scrutinyLableValueDTO
     * @return
     */
    boolean updateChangeOfUsageDetails(ChangeOfUsageRequestDTO requestDTO, ScrutinyLableValueDTO scrutinyLableValueDTO);

    void initiateWorkflowForFreeService(ChangeOfUsageRequestDTO requestDTO);

    public List<Long> getChangeOfUsageApplicationId(Long orgId, Long csIdn);

	Long getPlumIdByApplicationId(Long applicationId, Long orgId);

	List<ChangeOfUsage> getChangedUsageDetails(Long orgid, String changedFlag);

}
