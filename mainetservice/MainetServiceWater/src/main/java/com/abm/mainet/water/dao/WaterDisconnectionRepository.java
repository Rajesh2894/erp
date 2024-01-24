package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.water.domain.TBWaterDisconnection;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;

/**
 * @author Jeetendra.Pal
 *
 */
public interface WaterDisconnectionRepository /* extends CrudRepository<TBWaterDisconnection, Long> */ {

    /**
     * @param requestDTO
     * @return
     */
    WaterDisconnectionResponseDTO getConnAccessibleForDisConnectionDetails(
            WaterDeconnectionRequestDTO requestDTO);

    /**
     * @param requestDTO
     * @return
     */
    WaterDisconnectionResponseDTO saveDisconnectionDetails(
            WaterDeconnectionRequestDTO requestDTO);

    /**
     * Gets the applicant details.
     *
     * @param applicationId the application id
     * @param orgId the org id
     * @return the applicant details
     */
    TbKCsmrInfoMH getApplicantDetails(long applicationId, long orgId);

    /**
     * @param applicationId
     * @param orgId
     * @return
     */
    TBWaterDisconnection getDisconnectionAppDetails(long applicationId, long orgId);

    List<WaterReconnectionResponseDTO> getDisconnectionDetails(Long userId, Long orgId, String connectionNo);

    /**
     * @param entity
     * @return
     */
    boolean updateDisconnectionDetails(TBWaterDisconnection entity);

    String QUERY_CONNECTION_BY_CONNECTION_NO = "select am from TbKCsmrInfoMH am  WHERE am.orgId = :orgId and  am.csCcn = :csCcn ";
    String QUERY_CONNECTION_DETAIL = "select am.csIdn, am.csName, am.csCcn, am.applicationNo  from TbKCsmrInfoMH am  WHERE am.orgId = :orgId ";// and
                                                                                                                                               // am.csCcn
                                                                                                                                               // =
                                                                                                                                               // :csCcn
    String QUERY_APPLICATION_DETAIL = "select am from TbKCsmrInfoMH am  WHERE am.orgId = :orgId and  am.csIdn = :csIdn";
    String QUERY_DISCONNECTION_DETAIL = "select am.csIdn from TBWaterDisconnection am  WHERE am.orgId = :orgId and  am.apmApplicationId = :applicationNo";
    String QUERY_DISCONNECTION_APPLICATION_DETAIL = "select am from TBWaterDisconnection am  WHERE am.orgId = :orgId and  am.apmApplicationId = :applicationNo";
    String QUERY_DISCONNECTION_APPLICATION_DETAILByCsIdn = "select am from TBWaterDisconnection am  WHERE  am.discId in (select max(am.discId) from TBWaterDisconnection am  WHERE am.orgId = :orgId and am.csIdn = :csIdn)";

    /**
     * @param csIdn
     * @param orgid
     * @return
     */
    Date getDisconnectionDate(Long csIdn, long orgid);

    Long getPlumIdByApplicationId(Long applicationId, Long orgId);

    TBWaterDisconnection getDisconnectionAppDetailsByCsIdn(long csIdn, long orgId);

    WaterDisconnectionResponseDTO getConnForDisConnectionDetails(final WaterDeconnectionRequestDTO requestDTO);
}
