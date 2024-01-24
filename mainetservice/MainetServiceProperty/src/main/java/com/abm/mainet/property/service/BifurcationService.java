package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface BifurcationService {

    void saveBifurcationAssessment(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, long orgid, Long empId, Long deptId,
            int languageId, List<Long> finYearList);

    void SaveAuthorizationWithEdit(List<ProvisionalAssesmentMstDto> provAssMstDtoList,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, WorkflowTaskAction workFlowActionDto, long orgId, Employee emp,
            Long deptId, int languageId, Long serviceId, String authEditFlag,List<TbBillMas> billMasList);

    List<Long> saveBifurcation(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline, List<Long> finYearList,
            List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto, BillDisplayDto advanceAmt);

    List<Long> saveBifurcationForSkdcl(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline,
            List<Long> finYearList,
            List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto, BillDisplayDto advanceAmt);

    Map<Long, Long> saveProvisionalAssessmentBifurcation(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
            Long empId, List<Long> finYearList, Long applicationNo);

    void saveMainAssessmentBifurcation(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
            Long empId, List<Long> finYearList, Long applicationNo);

    void SaveAuthorizationForSkdcl(List<ProvisionalAssesmentMstDto> provAssMstDtoList,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, WorkflowTaskAction workFlowActionDto, long orgId, Employee emp,
            Long deptId, int languageId, Long serviceId, String authEditFlag);

}
