package com.abm.mainet.audit.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.audit.constant.IAuditConstants;
import com.abm.mainet.audit.dao.IAuditParaEntryDao;
import com.abm.mainet.audit.domain.AuditParaEntryEntity;
import com.abm.mainet.audit.domain.AuditParaEntryHistoryEntity;
import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.repository.IAuditParaEntryRepository;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;

import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class AuditParaEntryServiceImpl implements IAuditParaEntryService {

	@Autowired
	private IAuditParaEntryRepository auditRepository;
	
	@Autowired
	private IAuditParaEntryDao auditDao;
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	@Autowired
	IEmployeeService empService;
	
	@Autowired
	DepartmentService deptService;
	

    @Autowired
    AuditService auditService;
	
	Logger log = Logger.getLogger(AuditParaEntryServiceImpl.class);
	
	@Override
	@Transactional
	public void saveAuditParaEntryService(AuditParaEntryDto auditParaEntryDto,String saveMode)
	{		
		AuditParaEntryEntity entity = new AuditParaEntryEntity();
		BeanUtils.copyProperties(auditParaEntryDto, entity);
		auditRepository.save(entity);
		BeanUtils.copyProperties(entity, auditParaEntryDto);
		AuditParaEntryHistoryEntity history = new AuditParaEntryHistoryEntity();
		history.setHistStatus(saveMode);
		auditService.createHistory(entity, history);
			
	}
	
	@Override
	public void updateHistoryforWorkflow(AuditParaEntryDto dto,String saveMode)
	{
		AuditParaEntryEntity entity = new AuditParaEntryEntity();
		BeanUtils.copyProperties(dto, entity);
		AuditParaEntryHistoryEntity history = new AuditParaEntryHistoryEntity();
		history.setHistStatus(saveMode);
		auditService.createHistory(entity, history);
	}
	
	@Override
    @Transactional(readOnly = true)
	 public List<AuditParaEntryDto> searchAuditParaService(Long auditType, Long auditDeptId,Long orgId, Long auditWard1, Long auditParaStatus, String auditParaCode, Date fromDate, Date toDate) 
	 {
		
		 List<AuditParaEntryDto> lst = new ArrayList<>();
		 List<AuditParaEntryEntity> entityList = auditDao.searchAuditParaEntry(auditType, auditDeptId, orgId, auditWard1, auditParaStatus, auditParaCode, fromDate, toDate);
		 entityList.forEach(entity -> {
			 
			 AuditParaEntryDto dto = new AuditParaEntryDto();
			 if(entity.getAuditWard1() != null) {
				 String lookUpdec = CommonMasterUtility.getHierarchicalLookUp(entity.getAuditWard1(), UserSession.getCurrent().getOrganisation().getOrgid())
			                .getLookUpDesc();
				 dto.setAuditWardDesc(lookUpdec);
			 }
			 final DateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
			 dto.setAuditDateDesc(dateFormat.format(entity.getAuditEntryDate()));
			 BeanUtils.copyProperties(entity, dto);
			 lst.add(dto);
		 });
		return lst;
		
	
	}
	
	@Override
    @Transactional(readOnly = true)
    public AuditParaEntryDto getAuditParaEntryByAuditParaId(Long auditParaId) 
	{
		AuditParaEntryDto dto = new AuditParaEntryDto();
		AuditParaEntryEntity entity = auditRepository.findOne(auditParaId);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
	
	@Override
    @Transactional(readOnly = true)
	public AuditParaEntryDto getAuditParaEntryByAuditParaCodeandOrgId(String auditParaCode, long orgId)
	{
		AuditParaEntryDto dto = new AuditParaEntryDto();
		AuditParaEntryEntity entity = auditRepository.findByAuditParaCodeAndOrgId(auditParaCode, orgId);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
	
	@Override
    @Transactional(readOnly = true)
	public List<AuditParaEntryDto> getAuditParaEntryByOrgId(long orgId)
	{
		List<AuditParaEntryDto> dtoList = new ArrayList<> ();
		
		List<AuditParaEntryEntity> entityList = auditRepository.findByOrgIdOrderByAuditParaIdDesc(orgId);
		entityList.forEach(entity -> {
			 AuditParaEntryDto dto =new AuditParaEntryDto() ;
			 if(entity.getAuditWard1() != null) {
				 String lookUpdec = CommonMasterUtility.getHierarchicalLookUp(entity.getAuditWard1(), UserSession.getCurrent().getOrganisation().getOrgid())
			                .getLookUpDesc();
				 dto.setAuditWardDesc(lookUpdec);
			 }
			 final DateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
			 dto.setAuditDateDesc(dateFormat.format(entity.getAuditEntryDate()));
			 
			 BeanUtils.copyProperties(entity, dto);
			 dtoList.add(dto);
			
		});	
        return dtoList;
    }
	
	@Override
    @Transactional
    public void updateAuditWfStatus(String auditParaCode, String flag) {
		auditRepository.updateAuditWfStatus(auditParaCode,flag);
    }
	
    @Transactional
    public void updateAuditParaSubDoneAndPendingWithID(Long auditParaCode, String subUnitCompDone, String subUnitCompPending) {
		auditRepository.updateAuditParaSubDoneAndPendingWithID(auditParaCode,subUnitCompDone,subUnitCompPending);
    }
    
    @Transactional
    public void updateAuditWfStatusWithParaID(Long auditParaID, String flag) {
		auditRepository.updateAuditWfStatusWithParaID(auditParaID,flag);
    }
    
    @Transactional
    public void updateAuditParaSubUnitWithID(Long auditParaID, Integer subUnitClosed) {
		auditRepository.updateAuditParaSubUnitWithID(auditParaID,subUnitClosed);
    }
	
	@Override
    @Transactional
    public void updateAuditParaStatus(String auditParaCode, Long flag) {
		auditRepository.updateAuditParaStatus(auditParaCode,flag);
    }
	
	@Transactional
    public void updateAuditParaStatusById(Long auditParaID, Long flag) {
		auditRepository.updateAuditParaStatusbyID(auditParaID,flag);
    }
	
	
	@Override
	@Transactional
	public Long initiateWorkFlow(AuditParaEntryDto auditParaEntryDto)
	{
		 /* Initiate Workflow code */
        
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(IAuditConstants.AUDIT_PARA_APPROVAL_SERVICE_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
        
        
    	// Get WorkFlow Entity Object for Audit 
		WorkflowMas workFlowMas = null;
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
					.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
							sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), null, null, auditParaEntryDto.getAuditDeptId(),
							null, null, null, null, null);
		} else {
			workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
					.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
							sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), null, null, null, null, null);
		}
		
		
		Long rval = 0L;
		
			
			rval =initiateWorkFlowAuditService(this.prepareWorkFlowTaskAction(auditParaEntryDto), workFlowMas, "AuditParaEntryApproval.html", MainetConstants.FlagA);
			if (rval == null)
			{
				rval = 0L;
			}
			
			
		
		return  rval;
		
		
	}
	
	
	// WorkFlow task Action is DTO class
	private WorkflowTaskAction prepareWorkFlowTaskAction(AuditParaEntryDto auditParaEntryDto) {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(auditParaEntryDto.getAuditParaCode().toString());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
		taskAction.setApplicationId(auditParaEntryDto.getAuditParaId());
        taskAction.setAttachementId(auditParaEntryDto.getAttacheMentIds());
		return taskAction;
	}
	
	
	
	
	private Long initiateWorkFlowAuditService(WorkflowTaskAction workflowTaskActionDto, WorkflowMas workFlowMas, String url,
	            String workFlowFlag) 
	 {
		 Long flag = 0L;
		 try {
	    	    // Dto class
	            WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();

	            workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
	            ApplicationMetadata applicationMetadata = new ApplicationMetadata();

	            applicationMetadata.setReferenceId(workflowTaskActionDto.getReferenceId());
	            applicationMetadata.setOrgId(workflowTaskActionDto.getOrgId());
	            applicationMetadata.setWorkflowId(workFlowMas.getWfId());
	            applicationMetadata.setPaymentMode(workflowTaskActionDto.getPaymentMode());
	            applicationMetadata.setIsCheckListApplicable(false);
	            applicationMetadata.setApplicationId(workflowTaskActionDto.getApplicationId());

	            /*
	             * Requestor Task Assignment
	             */
	            TaskAssignment assignment = new TaskAssignment();

	            ApplicationSession appSession = ApplicationSession.getInstance();
	            
	            assignment.setActorId(workflowTaskActionDto.getEmpId().toString());
	            assignment.addActorId(workflowTaskActionDto.getEmpId().toString());
	            assignment.setOrgId(workflowTaskActionDto.getOrgId());
	            assignment.setServiceEventId(-1L);
	            String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
	            assignment.setServiceEventName(appSession
	                    .getMessage(reqTaskname, reqTaskname, new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));

	            assignment.setServiceEventNameReg(appSession
	                    .getMessage(reqTaskname, reqTaskname, new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));

	            assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
	            assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
	            assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
	            assignment.setServiceId(workFlowMas.getService().getSmServiceId());
	            assignment.setServiceName(workFlowMas.getService().getSmServiceName());
	            assignment.setServiceNameReg(workFlowMas.getService().getSmServiceNameMar());
	            assignment.setUrl(url);

	            /*
	             * Reviewer TaskAssignment has been removed from here,because it will be fetch on the fly by BPM to Service callback.
	             */

	            workflowProcessParameter.setRequesterTaskAssignment(assignment);
	            workflowProcessParameter.setApplicationMetadata(applicationMetadata);
	            workflowProcessParameter.setWorkflowTaskAction(workflowTaskActionDto);
	            WorkflowTaskActionResponse workFlowResponse = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).initiateWorkflow(workflowProcessParameter);
	           
	            
	            	           	            
	            if (workFlowResponse.getWorkflowRequestId() != null || workFlowResponse.getWorkflowRequestId() != 0L )
	            {	            	    	
	            	log.info("Updating Audit Para Chk for workflow request id ---> " + workFlowResponse.getWorkflowRequestId());
						 auditRepository.updateAuditParaChk(workflowTaskActionDto.getReferenceId(), workFlowResponse.getWorkflowRequestId());
					flag = workFlowResponse.getWorkflowRequestId();            		            	
	            }			  			 
	            
	           

	        } catch (Exception exception) {
	            throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
	        }
	        return flag;

	    }

	    /**
	     * Method Is used for Update Work flow
	     * @param workflowTaskAction
	     * @return string
	     */
	    @Override
	    @Transactional
	    public String updateWorkFlowAuditService(WorkflowTaskAction workflowTaskAction) {
	        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
	        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
	        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
	        try {
	        	ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
	        } catch (Exception exception) {
	            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
	        }
	        return null;
	    }	    
	 
		@Override
		public Long loadStatus(String code, String prefix) {
			
			Organisation org = new Organisation();
			org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			LookUp status = CommonMasterUtility.getValueFromPrefixLookUp(code, prefix,org);
			return status.getLookUpId();
			
		}
		
		public List<LookUp> fetchChiefAuditorDept()
		{
			List<LookUp> chiefAuditorDeptCodeLkp = CommonMasterUtility.getLookUps(IAuditConstants.AUDIT_PARA_CHIEF_AUDITOR_PREFIX,
				    UserSession.getCurrent().getOrganisation()); 
			
			
			return chiefAuditorDeptCodeLkp;
			
		}
		
		
		@Override
		@Transactional(readOnly = true)
		public Map<String, String> getEmpList(String deptId) {

			List<Object[]> empList = null;
			
			Map<String, String> map = new HashMap<>();
			empList = empService.findActiveEmployeeAndDsgByDeptId(Long.parseLong(deptId), UserSession.getCurrent().getOrganisation().getOrgid());
			for (Object[] emp : empList) 
			{
	            map.put(emp[0].toString(), emp[1].toString() + MainetConstants.WHITE_SPACE + emp[3].toString() + MainetConstants.WorksManagement.FW_ARROW + emp[4].toString());
	        }
	        return map;
		}

		@Override
		@Transactional
		public List<Object[]> findWorkFlowTaskByRefId(String refId, Long orgId) {
			return auditRepository.findWorkFlowTaskByRefId(refId, orgId);
		}

		@Override
		@Transactional
		public void updateRemarks(AuditParaEntryDto approvalAuditParaDto) {
			AuditParaEntryEntity entity=new AuditParaEntryEntity();
			BeanUtils.copyProperties(approvalAuditParaDto, entity);
			entityManager.merge(entity);
		}

		@Override
		@Transactional
		public void updateAuditParaStatusAndDate(String auditParaCode, Long flag, Date date) {			
			auditRepository.updateAuditParaStatusAndDate(auditParaCode, flag, date);
		}
		
		@Transactional
		public void updateAuditParaStatusAndDatewithID(Long auditParaCode, Long flag, Date date) {			
			auditRepository.updateAuditParaStatusAndDateWithID(auditParaCode, flag, date);
		}
		
		
		@Transactional
		public List<Object[]> getAuditReportData(Long deptId, Long auditParaYr, String formDate, String toDate, Long orgid) {
			return auditRepository.getAuditReportData(deptId, auditParaYr,formDate, toDate, orgid);
		}


	
	 
	
}
