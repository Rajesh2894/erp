package com.abm.mainet.bnd.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.BirthReceiptDTO;
import com.abm.mainet.bnd.dto.BirthRegDraftDto;
import com.abm.mainet.bnd.dto.BirthRegistrationCorrDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.ParentDetailDTO;
import com.abm.mainet.bnd.ui.model.BirthCorrectionModel;
import com.abm.mainet.bnd.ui.model.BirthRegistrationModel;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@WebService
public interface IBirthRegService {

	public BirthRegistrationDTO saveBirthRegDet(BirthRegistrationDTO requestDTO, BirthRegistrationModel birthRegistrationModel);

	public BirthRegistrationDTO getBirthRegApplnDetails(String certno, Long regNo, String year, String brApplicationId,
			Long orgId);

	public BirthRegistrationDTO saveBirthCorrectionDet(BirthRegistrationDTO birthRegDto, BirthCorrectionModel birthCorrectionModel);

	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto);

	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetail(String brCertNo, String brRegNo, String year,
			Date brDob, String brChildName, String applicationId, Long orgId);
	
	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetails(BirthRegistrationDTO birthCertificateDto);
	
	public BirthRegistrationDTO saveBirthCorrectionDets(BirthRegistrationDTO birthRegDto);

	Boolean checkEmployeeRole(UserSession ses);

	// String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto,
	// WorkflowMas workFlowMas, String url, String workFlowFlag);

	List<BirthRegistrationCorrDTO> getBirthRegisteredAppliDetailFromApplnId(Long applnId, Long orgId);

	List<BirthRegistrationDTO> getBirthRegApplnData(Long brId, Long orgId);

	List<ParentDetailDTO> getParentDtlApplnData(Long brId, Long orgId);

	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	public void updateBirthApproveStatus(BirthRegistrationCorrDTO tbBirthregcorrDTO, String status,
			String lastDecision);

	public void updateBirthApproveStatusBR(BirthRegistrationDTO birthRegDTO, String status, String lastDecision);

	void updateBirthWorkFlowStatusBR(Long brId, String taskNamePrevious, Long orgId, String brStatus);

	void updateBirthWorkFlowStatus(Long brId, String taskNamePrevious, Long orgId);

	// public void updateBirthWorkFlowStatus(Long drId, String taskNamePrevious,
	// Long orgId);

	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
			String workFlowFlag);

	public BirthRegistrationDTO saveBirthRegDraft(BirthRegistrationDTO birthRegDto);

	public List<BirthRegDraftDto> getAllBirthRegdraft(Long orgId);

	public List<BirthRegDraftDto> getBirthRegDraftAppliDetail(Long applnId, Date brDob, Long orgId);

	public BirthRegDraftDto getBirthById(Long brDraftId);

	public BirthRegistrationDTO getBirthRegDTOFromDraftDTO(BirthRegDraftDto birthById);

	public BirthRegistrationDTO saveBirthRegDetOnApproval(BirthRegistrationDTO requestDTO);

	public BirthRegistrationDTO getBirthByID(Long brId);
	
	public BirthRegistrationDTO getBirthByIDs(Long brId);

	// List<BirthRegistrationDTO> getBirthRegDataByStatus(String certNo, String
	// brRegNo, String year, Date brDob,
	// String brChildName, String applicnId, Long orgId);

	public String updateBirthRegCorrApprove(BirthRegistrationCorrDTO tbBirthregcorrDTO, String lastDecision,
			String status);

	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, BirthRegistrationModel birthRegModel);

	public void setAndSavebirthcorrChallanDtoOffLine(CommonChallanDTO offline, BirthCorrectionModel birthCorrModel);

	public void saveBirthRegDet(BirthRegistrationDTO birthRegDto);
	
	//public void initializeWorkFlowForFreeService(BirthRegistrationDTO requestDto);
   public boolean checkregnoByregno(String drRegno, Long orgId, Long drDraftId);
   
	 public BirthRegistrationDTO getBirthByIDPortal(Long brId);
	 
	 public BirthRegistrationDTO saveInclusionOfChildPortal(BirthRegistrationDTO birthRegDto);
	 
		public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode);
		
		public List<BirthRegistrationDTO> getBirthRegiDetailForCorr(BirthRegistrationDTO birthRegDto);

		public void smsAndEmailApproval(BirthRegistrationDTO birthRegDto, String lastDecision);

		public RequestDTO getApplicantDetailsByApplNoAndOrgId(Long applNo, Long orgId);
		
		public HospitalMasterDTO getHospitalById(Long hiId);
		
		public BirthRegistrationDTO getBirthByApplId(Long applicationId,Long orgId);

		void initializeWorkFlowForFreeService(BirthRegistrationDTO requestDto, ServiceMaster serviceMas);

	public String executeFinalWorkflowAction(WorkflowTaskAction taskAction, Long serviceId,
			BirthRegistrationDTO birthRegDto, BirthRegistrationCorrDTO tbBirthregcorrDTO);


	LinkedHashMap<String, Object> getTaxMasterByTaxCodeFromPortal(Long orgId, String taxCode);
	
	void updateBirthRemark(Long brId, String birthRegremark, Long orgId);

	void updateBirthCorrectionRemark(Long brCorrId, String corrAuthRemark, Long orgId);

	BirthRegistrationDTO saveDataEntryBirthRegDet(BirthRegistrationDTO requestDTO);

	List<BirthRegistrationDTO> getBirthRegDetailFordataEntry(String certNo, String brRegNo, String year, Date brDob,
			String brChildName, String applicnId, Long orgId);

	List<BirthRegistrationDTO> getBirthRegDetailFordataEntryTemp(String certNo, String brRegNo, String year, Date brDob,
			String brChildName, String applicnId, Long orgId);

	BirthRegistrationDTO saveBirthRegTempDetOnApproval(BirthRegistrationDTO requestDTO);
	
	List<BirthReceiptDTO> getBirethReceiptData(BirthReceiptDTO receiptData);

	String getBirthAppn(Long brId, Long orgId);
}
