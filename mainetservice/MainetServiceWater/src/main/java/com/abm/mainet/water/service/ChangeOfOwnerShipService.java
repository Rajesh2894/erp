package com.abm.mainet.water.service;

import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.ResponseDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.ChangeOfOwnershipDTO;
import com.abm.mainet.water.ui.model.ChangeOfOwnerShipModel;

/**
 * @author Rahul.Yadav
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface ChangeOfOwnerShipService {

    ChangeOfOwnerResponseDTO fetchAndVelidatetConnectionData(String connectionNo, long orgnId);

    /**
     * @param requestDTO
     * @return
     */
    ResponseDTO saveChangeData(ChangeOfOwnerRequestDTO requestDTO);

    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationid, Long serviceId, Long orgId);

    /**
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     * @throws CloneNotSupportedException
     */
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId,
            Long orgId) throws CloneNotSupportedException;

    ChangeOfOwnershipDTO findWaterConnectionChangeOfOwnerDetail(long applicationId);

    ApplicantDetailDTO populateChangeOfOwnerShipApplicantInfo(ApplicantDetailDTO dto, TbCfcApplicationMstEntity cfcEntity,
            ChangeOfOwnershipDTO ownerDTO, CFCApplicationAddressEntity addressEntity);

    void saveFormDataModifiedByDepartment(ChangeOfOwnerShipModel model);

    /**
     * @param applicationId
     * @param userSession
     * @return
     */
    ChangeOfOwnershipDTO initializeChangeOfOwnerExecutionData(long applicationId, UserSession userSession);

    /**
     * this is final execution for Change of Owner service, old ownership will be handed to the new owner for water connection
     * @param dto
     * @return
     */
    boolean executeChangeOfOwnership(ChangeOfOwnershipDTO dto);

	void initiateWorkFlowForFreeService(ChangeOfOwnerRequestDTO requestDTO);
	
	ChangeOfOwnerRequestDTO getOwnerDetailsByAppId(Long applicationId, Long orgId);

}
