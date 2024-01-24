package com.abm.mainet.lqp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.lqp.dao.ILegislativeDao;
import com.abm.mainet.lqp.domain.QueryRegistrationMaster;
import com.abm.mainet.lqp.domain.QueryRegistrationMasterHistory;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;
import com.abm.mainet.lqp.repository.QueryRegistrationRepository;

@Service
public class QueryRegistrationServiceImpl implements IQueryRegistrationService {

    private static final Logger LOGGER = Logger.getLogger(QueryRegistrationServiceImpl.class);

    @Autowired
    private ILegislativeDao legislativeDao;

    @Autowired
    private QueryRegistrationRepository registrationRepository;

    @Autowired
    private ILegislativeWorkflowService legislativeWorkflowService;

    @Autowired
    AuditService auditService;

    @Autowired
    QueryRegistrationRepository queryRegistrationRepository;
    
    @Resource
    private EmployeeJpaRepository employeeJpaRepository;
    
    @Autowired
    private IFileUploadService fileUpload;

    @Override
    public List<QueryRegistrationMasterDto> searchQueryRegisterMasterData(Long deptId, Long questionTypeId, String questionId,
            Date fromDate, Date toDate, Long orgId) {
        List<QueryRegistrationMasterDto> queryDataDtoS = new ArrayList<>();
        List<QueryRegistrationMaster> entities = legislativeDao.searchQueryRegisterMasterData(deptId, questionTypeId, questionId,
                fromDate, toDate, orgId);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        entities.forEach(entity -> {

            QueryRegistrationMasterDto dto = new QueryRegistrationMasterDto();
            BeanUtils.copyProperties(entity, dto);
            // getting JSON error when search filter
            dto.setDepartment(null);
            dto.setEmployee(null);
            dto.setQuestionType(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(entity.getQuestionTypeId(), organisation)
                    .getLookUpDesc());
            queryDataDtoS.add(dto);
        });

        return queryDataDtoS;
    }

    @Override
    @Transactional
    public Boolean queryRegistrationAndInitiateWorkflow(QueryRegistrationMasterDto registrationMasterDto,
            WorkflowTaskAction workflowActionDto, String sendBackflag, Long orgId, WorkflowMas workFlowMas,
            List<DocumentDetailsVO> attachmentList,Long deleteFileId) {
        QueryRegistrationMaster queryRegMas = new QueryRegistrationMaster();
        BeanUtils.copyProperties(registrationMasterDto, queryRegMas);
        try {
        	
        	if(attachmentList != null && !attachmentList.isEmpty()) {
            	queryRegMas.setIsAttachDoc(MainetConstants.Y_FLAG);
            }else
            	queryRegMas.setIsAttachDoc(MainetConstants.N_FLAG);
        	
            queryRegMas = registrationRepository.save(queryRegMas);
            
          //upload documents
            if(attachmentList != null && !attachmentList.isEmpty())
            	uploadDocument(attachmentList,deleteFileId,queryRegMas);
            
            // insert in history table
            QueryRegistrationMasterHistory history = new QueryRegistrationMasterHistory();
            history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(queryRegMas, history);
            // initiate Workflow
            return initiateQueryRegMakerCheckerWorkflow(workflowActionDto, queryRegMas.getQuestionIdWF(), orgId,
                    sendBackflag, workFlowMas);

        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving query answer ", exception);
            throw new FrameworkException("Exception occur while saving query answer ", exception);
        }
    }

    public boolean initiateQueryRegMakerCheckerWorkflow(WorkflowTaskAction workflowActionDto, String questionIdGenerated,
            Long orgId, String workflowflag, WorkflowMas workFlowMas) {
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.LQP.SERVICE_CODE.LQE, orgId);
        // Code related to work flow
        WorkflowTaskAction prepareWorkFlowTaskAction = null;
        prepareWorkFlowTaskAction = workflowActionDto;
        prepareWorkFlowTaskAction.setReferenceId(questionIdGenerated);
        WorkflowTaskActionResponse response = legislativeWorkflowService.makerCheckerWorkFlowLQPService(
                prepareWorkFlowTaskAction,
                workFlowMas.getWfId(), MainetConstants.LQP.LQE_URL, workflowflag, sm.getSmShortdesc());
        if (response != null) {
            return true;
        } else {
            return false;
        }

        // It updates the flag if and only if task is created in workflow
        /*
         * if (response != null) { infoService.updateAppStatusFlag(assetId, orgId,
         * MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING); }
         */
    }

    @Override
    public List<QueryRegistrationMasterDto> fetchQueryRegisterMasterDataByOrgId(Long orgId) {
        List<QueryRegistrationMasterDto> queryRegistrationMasterDTOs = new ArrayList<>();
        List<String> statusList = new ArrayList<>();
        statusList.add(MainetConstants.LQP.STATUS.OPEN);
        statusList.add(MainetConstants.LQP.STATUS.REOPEN);
        List<QueryRegistrationMaster> dataList = 
        		queryRegistrationRepository.findByOrgIdAndStatusIn(orgId,statusList);
        dataList.forEach(queryRegistrationMaster -> {
            QueryRegistrationMasterDto queryRegistrationMasterDto = new QueryRegistrationMasterDto();
            BeanUtils.copyProperties(queryRegistrationMaster, queryRegistrationMasterDto);
            queryRegistrationMasterDTOs.add(queryRegistrationMasterDto);
        });
        return queryRegistrationMasterDTOs;
    }

    public QueryRegistrationMasterDto getQueryRegisterMasterDataByQuestId(String qustnId) {
        QueryRegistrationMasterDto queryRegistrationMasterDto = new QueryRegistrationMasterDto();
        QueryRegistrationMaster queryRegistrationMaster = queryRegistrationRepository.findByQuestionId(qustnId);
        if (queryRegistrationMaster != null) {
            BeanUtils.copyProperties(queryRegistrationMaster, queryRegistrationMasterDto);
        }
        return queryRegistrationMasterDto;
    }

    @Override
    public QueryRegistrationMasterDto getQueryRegisterMasterDataByQuestRegId(Long qustnRegId) {
        QueryRegistrationMasterDto queryRegistrationMasterDto = new QueryRegistrationMasterDto();
        QueryRegistrationMaster queryRegistrationMaster = queryRegistrationRepository.findOne(qustnRegId);
        if (queryRegistrationMaster != null) {
            BeanUtils.copyProperties(queryRegistrationMaster, queryRegistrationMasterDto);
            // Lazy load
            queryRegistrationMasterDto.setDepartment(queryRegistrationMaster.getDepartment());
            queryRegistrationMasterDto.setEmployee(queryRegistrationMaster.getEmployee());
            queryRegistrationMasterDto.setDeptId(queryRegistrationMaster.getDepartment().getDpDeptid());
            queryRegistrationMasterDto.setEmpId(queryRegistrationMaster.getEmployee().getEmpId());
        }
        return queryRegistrationMasterDto;
    }
    
    @Override
    public List<Employee> fetchEmpDetailList(String referenceId,Long orgId) {
    	List<Employee> empList = null;
    	List<Long> empIdList = new ArrayList<>();
    	//get empid(actorId) to send sms&EMail after initiation of workflow
    	String actorId = registrationRepository.getActorIds(referenceId);
    	if(!StringUtils.isEmpty(actorId)) {
    		String[] empIds = actorId.split(MainetConstants.operator.COMMA);
    		for (String empId : empIds) {
    			if (!StringUtils.isEmpty(empId))
    				empIdList.add(Long.valueOf(empId));
    		}
    		if (!CollectionUtils.isEmpty(empIdList))
    			empList = employeeJpaRepository.getEmpDetailListByEmpIdList(orgId, empIdList);
        	return empList;
    	}else 
    		LOGGER.info("actorId not found for sending SMS and Email for referenceId->"+referenceId);
    		return empList;
    }
    
    @Override
    public void uploadDocument(List<DocumentDetailsVO> attachmentList, Long deleteFileId,QueryRegistrationMaster queryRegistrationMaster) {
    	//preparing dto for fileupload attachment
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        fileUploadDTO.setStatus(MainetConstants.FlagA);
        fileUploadDTO.setDepartmentName(MainetConstants.LQP.LQP_DEPT_CODE);
        fileUploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        
        // file upload code set here
	    if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId == null) {
		fileUploadDTO.setIdfId(MainetConstants.LQP.SERVICE_CODE.LQE + MainetConstants.FILE_PATH_SEPARATOR + queryRegistrationMaster.getQuestionRegId());
		fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
	    } else if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId != null) {
		fileUploadDTO.setIdfId(MainetConstants.LQP.SERVICE_CODE.LQE + MainetConstants.FILE_PATH_SEPARATOR + queryRegistrationMaster.getQuestionRegId());
		fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
		List<Long> deletedDocFiles = new ArrayList<>();
		deletedDocFiles.add(deleteFileId);
		ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class)
			.updateRecord(deletedDocFiles, queryRegistrationMaster.getUpdatedBy(), MainetConstants.FlagD);
	    }
    }
}
