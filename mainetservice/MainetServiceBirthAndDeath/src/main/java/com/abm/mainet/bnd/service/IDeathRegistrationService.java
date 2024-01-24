package com.abm.mainet.bnd.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathRegdraftDto;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.ui.model.DeathRegistrationModel;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
@WebService
public interface IDeathRegistrationService {

	public List<TbDeathregDTO> getAllDeathReg(Long orgId);

	public TbDeathregDTO getDeathRegById(Long drId);

	public void deleteDeathRegById(Long drId);

	public Map<String,Object> saveDeathRegDet(TbDeathregDTO tbDeathregDTO, DeathRegistrationModel tbDeathregModel);

	public TbDeathregDTO getDeathRegApplnDetails(String certno, Long regNo, String year);

	public long CalculateNoOfDays(TbDeathregDTO tbDeathregDTO);
	
	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url, String workFlowFlag);

	String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction);
	
	void updateDeathApproveStatus(TbDeathregDTO tbDeathregDTO, String status, String lastDecision);
	
	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	//void updateDeathWorkFlowStatus(Long drId,String taskNamePrevious,Long orgId,String authRemark);
	
	Boolean checkEmployeeRole(UserSession ses);

	public TbDeathregDTO saveDeathRegDetOnApproval(TbDeathregDTO tbDeathregDTO);
	
	List<TbDeathRegdraftDto> getDeathRegisteredAppliDetail(Long applnId, Date drDod,Long orgId);

	public List<TbDeathRegdraftDto> getAllDeathRegdraft(Long orgid);

	public TbDeathregDTO saveDeathRegDraft(TbDeathregDTO tbDeathregDTO);
	
	public TbDeathregDTO getTbDeathregDTOFromDraftDTO(TbDeathRegdraftDto tbDeathRegdraftDto);
	
	public TbDeathRegdraftDto getDeathById(Long drDraftId);
	
	void updatNoOfcopyStatus(Long brId,Long orgId,Long bdId,Long noOfCopies);

	void updateDeathWorkFlowStatus(Long drId, String taskNamePrevious, Long orgId, String drStatus);

	public String updateDeathRegCorrApprove(TbBdDeathregCorrDTO tbBdDeathregCorrDTO, String lastDecision,
			String status);

	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathRegistrationModel tbDeathregModel);

	public boolean checkregnoByregno(String drRegno, Long orgId, Long drDraftId);

	//public void initializeWorkFlowForFreeService(TbDeathregDTO requestDto);

	public void smsAndEmailApproval(TbDeathregDTO tbDeathregDTO, String decision);
	
	/*public void saveDeathExcelData(TbDeathregUploadDTO deathMasterUploadDto, Long orgId, int langId);	xls code */

	Map<Long, Double> getLoiCharges(Long orgId, int noOfCopies, Long apmApplicationId,ServiceMaster serviceMas);

	TbDeathregDTO saveDataEntryDeathRegDet(TbDeathregDTO tbDeathregDTO);

	void initializeWorkFlowForFreeService(TbDeathregDTO requestDto, ServiceMaster serviceMas);

	TbDeathregDTO saveDeathRegDetOnApprovalTemp(TbDeathregDTO tbDeathregDTO);
	
}
