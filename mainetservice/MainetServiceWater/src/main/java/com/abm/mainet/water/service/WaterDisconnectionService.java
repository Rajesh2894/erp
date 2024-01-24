package com.abm.mainet.water.service;

import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface WaterDisconnectionService.
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface WaterDisconnectionService {

    /**
     * Gets the connection details.
     *
     * @param requestDTO the request dto
     * @return the connection details
     */
    WaterDisconnectionResponseDTO getConnectionsAvailableForDisConnection(
            WaterDeconnectionRequestDTO requestDTO);

    /**
     * Save disconnection details.
     *
     * @param requestDTO the request dto
     * @return the water disconnection response dto
     */
    WaterDisconnectionResponseDTO saveDisconnectionDetails(
            WaterDeconnectionRequestDTO requestDTO);

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
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId,
            Long orgId);

    /**
     * @param orgid
     * @param applicationId
     * @return
     */
    WaterDeconnectionRequestDTO getAppDetailsForDisconnection(long orgId, long applicationId);

    /**
     * @param licence
     * @return
     */
    Long validateOtherUlbLicence(String licence);

    /**
     * @param requestDTO
     * @param scrutinyLableValueDTO
     * @return
     */
	/*
	 * boolean updateDisconnectionDetails( WaterDeconnectionRequestDTO requestDTO,
	 * ScrutinyLableValueDTO scrutinyLableValueDTO);
	 */
    boolean updateDisconnectionDetails(WaterDeconnectionRequestDTO requestDTO,
			ScrutinyLableValueDTO scrutinyLableValueDTO, Organisation organisation);

    /**
     * @param orgId
     * @param applicationId
     * @return
     */
    TBWaterDisconnectionDTO getDisconnectionAppDetails(long orgId, long applicationId);

    /**
     * @param requestDTO
     * @return
     */
    boolean updateDisconnectionDetails(WaterDeconnectionRequestDTO requestDTO);

    /**
     * @param applicationId
     * @param orgId
     * @return
     */
    TbKCsmrInfoMH getAppDetailsAppliedForDisConnection(Long applicationId, Long orgId);

    void initiateWorkflowForFreeService(WaterDeconnectionRequestDTO requestDTO);

    WaterDeconnectionRequestDTO getDisconnectionDetailsForDashboardViewByAppId(Long applicationId, Long orgId);

    TbWtBillMasEntity getWaterBillDues(Long csIdn, Long orgId);

    Long getPlumIdByApplicationId(Long applicationId, Long orgId);

    TBWaterDisconnectionDTO getDisconnectionAppDetailsByCsIdn(final long orgId, final Long csIdn);

    WaterDisconnectionResponseDTO getConnectionsForDisConnection(final WaterDeconnectionRequestDTO requestDTO);

    String getWorkflowRequestByAppId(Long apmApplicationId, Long orgId);
}
