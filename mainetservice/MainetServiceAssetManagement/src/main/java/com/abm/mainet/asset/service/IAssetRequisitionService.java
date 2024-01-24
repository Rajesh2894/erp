package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.asset.ui.dto.AssetRequisitionDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface IAssetRequisitionService {

    AssetRequisitionDTO getAstRequisitionDataById(Long astReqId);

    String saveInRequisition(AssetRequisitionDTO assetRequisitionDTO, WorkflowTaskAction workflowActionDto, String sendBackflag,
            Long orgId, String moduleDeptCode, String serviceCodeDeptWise);

    List<AssetRequisitionDTO> fetchSearchData(Long astCategory, Long locId, Long deptId, Long orgId, String moduelDeptCode);

    void updateRequisition(AssetRequisitionDTO assetRequisitionDTO);

	List<AssetInformationDTO> getAstRequisitionById(Long astReqId);
}
