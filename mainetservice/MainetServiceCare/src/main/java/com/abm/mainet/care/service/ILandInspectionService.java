package com.abm.mainet.care.service;

import java.util.List;

import com.abm.mainet.care.dto.LandInspectionDto;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface ILandInspectionService {

    String saveLandInspectionEntryAndInitiate(LandInspectionDto inspectionDto, List<DocumentDetailsVO> documentList,
            RequestDTO requestDTO, WorkflowTaskAction workflowAction);

    LandInspectionDto getLandInspectionData(Long lnInspId);
}
