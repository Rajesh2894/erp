package com.abm.mainet.property.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.CommonAppResponseDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.property.dto.PropertyRequestDTO;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

@WebService
public interface MutationService extends Serializable {

    void saveUploadedFile(PropertyTransferMasterDto proAssDto, Long orgId, Employee emp, Long deptId, int langId, Long serviceId);

    List<DocumentDetailsVO> fetchCheckList(PropertyTransferMasterDto dto);

    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId);

    // PropertyTransferMasterDto fetchChargesForMuatation(PropertyTransferMasterDto transferDto);
    PropertyTransferMasterDto fetchChargesForMuatationForMobile(PropertyTransferMasterDto transferDto);

    PropertyTransferMasterDto saveMutation(PropertyTransferMasterDto propTranMstDto);

    PropertyTransferMasterDto callWorkFlowForFreeService(PropertyTransferMasterDto transferDto);

    ProvisionalAssesmentMstDto fetchDetailForMutataion(String propNo, String oldPropNo, Long orgId);

    String saveAuthorizationWithoutEdit(WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp,
            PropertyTransferMasterDto propTranMstDto, boolean isLastAuthorizer, Long deptId, boolean isBeforeLastAuthorizer,
            String clientIpAddress);

    String saveAuthorizationWithEdit(WorkflowTaskAction workFlowActionDto, PropertyTransferMasterDto propTranMstDto, Long orgId,
            Employee emp, Long deptId);

    Map<Long, Double> getLoiChargesAtApproval(PropertyTransferMasterDto transDto, ServiceMaster service, Organisation org,
            ProvisionalAssesmentMstDto assMstDto);

    String saveMutationApproval(WorkflowTaskAction workFlowActionDto, PropertyTransferMasterDto propTranMstDto, Organisation org,
            Employee emp, Long deptId, Map<Long, Double> loiCharges);

    /*
     * task: 26817 pass dto by sharvan
     */
    PropertyTransferMasterDto fetchChargesForMuatation(PropertyTransferMasterDto transferDto,
            ProvisionalAssesmentMstDto prvDto);

    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

    PropertyTransferMasterDto fetchChargesForMuatationForPortal(PropertyTransferMasterDto transferDto);

    List<PropertyTransferMasterDto> getMutationData(String propno, String oldPropNo, Long applicationNo, Long orgId);

    void updateCertificateNo(String certificateNo, Long empId, Long apmApplicationId, Long orgid);

    String mutationCheck(String propertyId);

    PropertyTransferMasterDto getMutationByPropNo(String propno, Long orgId);

    String getWorkflowRequestByAppId(Long apmApplicationId, long orgId);

    public Boolean saveMutationAfterloiPayment(CommonChallanDTO dto);

    ProvisionalAssesmentMstDto fetchMutationDetails(String propNo, String oldPropNo, String flatNo, long orgId);

    PropertyTransferMasterDto getMutationByPropNonFlatNo(String assNo, String flatNo, long orgId);

    List<PropertyTransferMasterDto> getMutationDetails(String propNo, String oldPropNo, Long applicationNo, String flatNo,
            Long orgId);

    ProvisionalAssesmentMstDto fetchDetailForMutationForMobile(PropertyInputDto inputDto);

	public void updateMutationDetails(ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
			PropertyTransferMasterDto propTransferDto, WorkflowTaskAction workflowActionDto, Employee emp,
			Long serviceId, String ownershipPrefixNew, String multipleFlats, Organisation org);

	public boolean generateCertificate(String docPath, Long applicationNo, String certificateNo, Long orgId);
	
	CommonAppResponseDTO updateWorkflowAction(PropertyRequestDTO propertyRequestDTO);
	
	void saveFinalAuthorization(Long orgId, Employee emp, PropertyTransferMasterDto propTranMstDto,
			String clientIpAddress, Long applicationNo);
	
	void updateMutationApproval(Long orgId, Long empId, Long applicationNo);
}
