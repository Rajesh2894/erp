package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface IAssetAnnualPlanService {

    AssetAnnualPlanDTO getAstAnnualPlanDataById(Long astAnnualPlanId);

    public String saveInAssetInventoryPlan(AssetAnnualPlanDTO annualPlanDTO, WorkflowTaskAction workflowActionDto,
            Long orgId, String moduleDeptCode, String serviceCodeDeptWise);

    List<AssetAnnualPlanDTO> fetchAnnualPlanSearchData(Long finYearId, Long deptId, Long locId, Long orgId,
            String moduleDeptCode);

    void updateAnnualPlan(AssetAnnualPlanDTO annualPlanDTO);

}
