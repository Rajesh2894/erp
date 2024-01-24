package com.abm.mainet.bnd.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.bnd.domain.MedicalMaster;
import com.abm.mainet.bnd.domain.MedicalMasterCorrection;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterCorrDTO;
import com.abm.mainet.bnd.dto.MedicalMasterCorrectionDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.ui.model.DeathRegCorrectionModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
@WebService
public interface IdeathregCorrectionService {

	List<TbDeathregDTO> getDeathRegisteredAppliDetail(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod,String drDeceasedname, Long orgId);
	public List<TbDeathregDTO> getDeathRegDataByStatus(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod,String drDeceasedname, Long orgId);

	public TbDeathregDTO getDeathById(Long drID);
	
	public long CalculateNoOfDays(TbDeathregDTO tbDeathregDTO);

	public Map<String,Object> saveDeathCorrData(TbDeathregDTO tbDeathregDTO,DeathRegCorrectionModel deathCorrModel);
	
	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url, String workFlowFlag);

	List<TbDeathregDTO> getDeathRegCorrApplnData(Long applnId, Long orgId);

	List<TbBdDeathregCorrDTO> getDeathRegisteredAppliDetailFromApplnId(Long applnId, Long orgId);

	List<TbDeathregDTO> getDeathRegApplnData(Long drId, Long orgId);

	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	void updateDeathApproveStatus(TbBdDeathregCorrDTO tbBdDeathregCorrDTO, String status, String lastDecision);

	void updateDeathWorkFlowStatus(Long drId,String taskNamePrevious,Long orgId);

	List<MedicalMasterDTO> getDeathRegApplnDataFromMedicalCorr(Long drId, Long orgId);

	List<MedicalMasterCorrectionDTO> getDeathRegApplnDataFromMedicalMasCorr(Long drCorrId, Long orgId);

	List<DeceasedMasterCorrDTO> getDeathRegApplnDataFromDecaseCorr(Long drCorrId, Long orgId);
	void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathRegCorrectionModel deathCorrModel);

    List<TbDeathregDTO> getDeathRegDataForPortal(TbDeathregDTO tbDeathregDTO);
    
    public TbDeathregDTO getDeathRegDetailById(Long drID);
    
	//List<CemeteryMasterDTO> getDeathRegApplnDataFromCemetryCorr(Long drId, Long orgId);
		
    TbDeathregDTO saveDeathCorrectionDataForPortal(TbDeathregDTO tbDeathregDTO);
	
    List<TbDeathregDTO> getDeathDataForCorr(TbDeathregDTO tbDeathregDTO);
    
    public TbDeathregDTO getDeathByApplId(Long applicationId,Long orgId);
    
    public void updateNoOfIssuedCopy(Long drId,Long orgId,String DeathWFStatus);
    
	boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, Long smServiceId);
	
	public String executeFinalWorkflowAction(WorkflowTaskAction taskAction, Long smServiceId, TbDeathregDTO tbDeathregDTO,
			TbBdDeathregCorrDTO tbBdDeathregCorrDTO);
	WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId);
	
	void updateDeathRemark(Long drId, String deathRegremark, Long orgId);
	void updateDeathCorrectionRemark(Long drCorrId, String corrAuthRemark,Long orgId);
	List<TbDeathregDTO> getDeathRegAppliDetailForDataEntry(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod, String drDeceasedname, Long orgId);
	List<TbDeathregDTO> getDeathRegAppliDetailForDataEntryTemp(String drCertNo, Long applnId, String year,
			String drRegno, Date drDod, String drDeceasedname, Long orgId);
}
