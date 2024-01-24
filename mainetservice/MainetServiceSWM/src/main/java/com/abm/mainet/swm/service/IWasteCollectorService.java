/**
 * 
 */
package com.abm.mainet.swm.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@WebService
public interface IWasteCollectorService {

    CollectorResDTO saveCnDApplicantForm(CollectorReqDTO collectorReqDTO, CommonChallanDTO offline, String serviceName, Long taskId, WorkflowTaskAction wrkflwTskAction);

    String getComParamDetById(Long cpdId, Long orgId);

    WSResponseDTO initializeModel(WSRequestDTO requestDTO, Long orgId);

    WSResponseDTO getChecklist(WSRequestDTO requestDTO, Long orgId);

    WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO, Long orgId);

    List<ChargeDetailDTO> getApplicableCharges(WSRequestDTO requestDTO, Long vehicleId, Long orgId);

    CollectorReqDTO getApplicationDetailsByApplicationId(Long applicationId, Long orgId);

    String updateProcess(WorkflowTaskAction departmentAction, Long taskId, String string, String flagu, String deptWasteCollectorShortCode, CollectorReqDTO collectorReqDTO, String decision);

}
