package com.abm.mainet.property.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface PropertyAuthorizationService extends Serializable {

    void SaveAuthorizationData(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            List<TbBillMas> billMasList, WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long deptId, int langId,
            Long serviceId);

    ProvisionalAssesmentMstDto getAssesmentMstDtoForDisplay(List<ProvisionalAssesmentMstDto> assMasDtoList);

    void saveUploadedFile(ProvisionalAssesmentMstDto proAssDto, Long orgId, Employee emp, Long deptId, int langId,
            Long serviceId);

    Map<Long, BillDisplayDto> getRebete(Long applicationNo, Long orgId, Long deptId);

    void saveNewPropertyRegAuthorization(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long deptId, int langId, Long serviceId);

    void saveNewPropertyRegAuthorizationWithEdit(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto, List<Long> finYearList, WorkflowTaskAction workFlowActionDto, Long orgId,
            Employee emp, Long deptId, int langId, Long serviceId);

    void saveAmalgamationAuthorizationWithEdit(
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, ProvisionalAssesmentMstDto oldProvisionalAssesmentMstDt,
            WorkflowTaskAction workflowActionDto,
            long orgId, Employee empId, Long deptId, int languageId, Long serviceId, List<Long> finYearList, String editflag);

    void callWorkflow(Long apmAppId, WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long serviceId);

    void setUniqueIdentiFromOldBillToNewBill(List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList);

    void SaveAuthorizationWithEditForDES(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto, List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList,
            WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long deptId, int langId, Long serviceId,
            String ipAddress);

    void SaveApprovalData(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long deptId, int langId);

    void sendMail(ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId, Long userId);

    void addInactiveEntryInObjection(ProvisionalAssesmentMstDto proAssDto, Long deptId, Employee emp, String ipAddress,
            String noticePeriod);

    List<Long> SaveApprovalDataWithEdit(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            List<Long> finYearList, List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList,
            WorkflowTaskAction workFlowActionDto, Employee emp, Long deptId, List<BillReceiptPostingDTO> demandLevelRebate,
            BillDisplayDto earlyPayRebate, BillDisplayDto advanceDto, BillDisplayDto surCharge, int langId);

    List<Long> SaveAuthorizationWithEdit(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            List<Long> finYearList, List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList,
            WorkflowTaskAction workFlowActionDto, Employee emp, Long deptId, int langId,
            List<BillReceiptPostingDTO> demandLevelRebate, BillDisplayDto earlyPayRebate, BillDisplayDto advanceDto,
            BillDisplayDto surCharge);

	void setUniqueIdentiFromOldBillToNewBillSkdcl(List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList);

	List<TbBillMas> getBillHistoryByApplicationId(String propNo, Long applicationId);
}
