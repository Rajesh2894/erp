
package com.abm.mainet.cfc.scrutiny.ui.model;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.cfc.scrutiny.dto.ViewCFCScrutinyLabelValue;
import com.abm.mainet.cfc.scrutiny.service.ScrutinyService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IDmsService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;


/**
 * @author Rajendra.Bhujbal
 *
 */
@Component
@Scope("session")
public class ScrutinyLabelModel extends AbstractModel {

    private static final long serialVersionUID = -2765855207220786044L;

    private static final Logger logger = Logger.getLogger(ScrutinyLabelModel.class);

    @Resource
    private ScrutinyService scrutinyService;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Resource
    private ServiceMasterService serviceMasterService;
    
    @Resource
    private TbLoiMasService tbLoiMasService;

    @Autowired
    private IDmsService dmsService;
    
    @Autowired
	private DepartmentService departmentService;
    
    @Autowired
	private CommonService commonService;
    
    @Autowired
	private WorkFlowTypeRepository workFlowTypeRepository;

    private List<CFCAttachment> scrutinyDocs = new ArrayList<>(3);

    private ScrutinyLabelDTO scrutinyLabelDTO;

    private String queryString;

    private String errorMsg;

    private boolean flag;

    private final String jbpm = ApplicationSession.getInstance().getMessage("configuration.company.JBPM");

    private String wokflowDecision;
    
    private String loiGenFlag;
    
    private String loiNo;
    
    private String loiAmt;
    
    private Long slLabelId;
    
    private Long levels;
    
    private String refId = "";
    
    @Autowired
	private IChecklistSearchService checklistSearchService;
    
    @Autowired
	private ISMSAndEmailService iSMSAndEmailService;
    
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;
    
    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;
    
    @Autowired
    IWorkflowTypeDAO workflowTypeDAO;
    
    @Autowired
    private IWorkflowRequestService workflowRequestService;

    public void populateScrutinyViewData(final long applicationId, final long serviceId, final UserSession userSession) {

        scrutinyDocs.clear();
        final Long gmId = UserSession.getCurrent().getEmployee().getGmid();
        scrutinyLabelDTO = scrutinyService.populateScrutinyLabelData(applicationId,
                UserSession.getCurrent().getEmployee().getEmpId(), gmId, UserSession.getCurrent().getOrganisation().getOrgid(),
                serviceId, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getScrutinyCommonParamMap().get(MainetConstants.SCRUTINY_COMMON_PARAM.REFERENCE_ID),null);
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.FINYEAR,
                getUserSession().getFinancialPeriodShortForm());
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.LANGID,
                getUserSession().getLanguageId() + MainetConstants.BLANK);
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.USERID,
                getUserSession().getEmployee().getEmpId() + MainetConstants.BLANK);
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.ORGID,
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.BLANK);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
        TbLoiMas loiData= tbLoiMasService.findloiByApplicationIdAndOrgId(applicationId,UserSession.getCurrent().getOrganisation().getOrgid());
        if(loiData!=null)
        setLoiGenFlag(MainetConstants.Y_FLAG);
    }
    }

    public List<Long> getLabelList(final long labelId) {

        final List<Long> labelList = new ArrayList<>(0);

        final Long desgId = getLegalScrDesg();

        if (!scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().isEmpty() && (desgId > 0L)) {
            for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : scrutinyLabelDTO.getDesgWiseScrutinyLabelMap()
                    .get(desgId)) {
                if (viewCFCScrutinyLabelValue.getSlLabelId() > labelId) {
                    labelList.add(viewCFCScrutinyLabelValue.getSlLabelId());
                }
            }
        }

        return labelList;
    }

    /**
     * @param labelId
     * @param labelValue
     * @return
     */
    public Object getLabelValueByQuery(final long labelId, final String labelValue) {

        String query = getTableClauseQuery(labelId);

        Object obj = MainetConstants.BLANK;

        if (query == null) {
            return labelValue;
        }

        for (final Map.Entry<String, String> paramMap : getUserSession().getScrutinyCommonParamMap().entrySet()) {
            if (query.contains(":PARAMETER." + paramMap.getKey())) {
                query = query.replace(":PARAMETER." + paramMap.getKey(), paramMap.getValue());
            }
        }

        final String dbval = scrutinyService.getValueByLabelQuery(query);
        if ((dbval != null) && !dbval.isEmpty()) {
            obj = dbval;

        } else {
            obj = labelValue;
        }

        return obj;
    }

    private String getTableClauseQuery(final long lableId) {

        final Long desgId = getLegalScrDesg();

        if (!scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().isEmpty() && (desgId > 0L)) {
            for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : scrutinyLabelDTO.getDesgWiseScrutinyLabelMap()
                    .get(desgId)) {
                if (viewCFCScrutinyLabelValue.getSlLabelId() == lableId) {
                    getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID,
                            viewCFCScrutinyLabelValue.getApplicationId() + MainetConstants.BLANK);

                    return viewCFCScrutinyLabelValue.getSlPreValidation();
                }
            }
        }

        return null;
    }

    private Long getLegalScrDesg() {
        final String gmId = scrutinyLabelDTO.getRoleId();

        final Long dsgId = Long.valueOf(gmId);

        if (scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().containsKey(dsgId) || scrutinyLabelDTO.getDesgWiseFieldLabelMap().containsKey(dsgId)) {
            return dsgId;
        }
        return 0L;
    }

    public boolean submitScrutinyLabels() {
        List<Long> attachmentId = new ArrayList<>(0);
        List<CFCAttachment> scrutinyDocumnet = uploadScrutinyDoc(getDirectry(), scrutinyLabelDTO, getFileNetClient());
        if (scrutinyDocumnet != null && !scrutinyDocumnet.isEmpty()) {
            for (CFCAttachment docs : scrutinyDocumnet) {
                attachmentId.add(docs.getAttId());
            }
        }
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) {
			return frwdToDepartmentScrutinyInChecker(attachmentId);
		} else {
			final boolean saveflag = saveScrutinyLabelValue(true, attachmentId);
			if (saveflag) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_SUBMIT));
				return true;
			} else {
				setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_FAIL));
				return false;
			}
		}
    }

	private boolean frwdToDepartmentScrutinyInChecker(List<Long> attachmentId) {//This method is written to initiate parallel workflow while forward to department decision
		UserTaskDTO currentTask = iWorkflowTaskService.findByTaskId(this.getTaskId());
		String stopAtCheckerLevelFlag=ApplicationSession.getInstance().getMessage("stop.checker.level");
		if (MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT.equals(this.getWokflowDecision())) {
			
			for (int i = 0; i < getForwardToDepartment().size(); i++) {
				initiateDeptWiseWorkflow(Long.valueOf(this.getForwardToDepartment().get(i)), Long.valueOf(this.getForwardToServiceId().get(i)), currentTask);
			}
		}
		if (MainetConstants.TOSTOPLEVEL == currentTask.getCurentCheckerLevel() && MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT.equals(this.getWokflowDecision()) 
				&& MainetConstants.RnLCommon.Y_FLAG.equals(stopAtCheckerLevelFlag)) {
			setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_SUBMIT));
			return true;
		} else {
			final boolean saveflag = saveScrutinyLabelValue(true, attachmentId);
			if (saveflag) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_SUBMIT));
				return true;
			} else {
				setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_FAIL));
				return false;
			}
		}
	}

    public boolean saveScrutinyLabels() {
        List<Long> attachmentId = new ArrayList<>(0);
        List<CFCAttachment> scrutinyDocumnet = uploadScrutinyDoc(getDirectry(), scrutinyLabelDTO, getFileNetClient());
        if (scrutinyDocumnet != null && !scrutinyDocumnet.isEmpty()) {
            for (CFCAttachment docs : scrutinyDocumnet) {
                attachmentId.add(docs.getAttId());
            }
        }
        final boolean saveflag = saveScrutinyLabelValue(false, null);

        if (!saveflag) {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_SAVE));
            return true;
        } else {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_FAIL));
            return false;
        }

    }

    /**
     * @param labelId
     * @param labelValue
     * @param appId
     * @return
     */
    public boolean saveLableValue(final long labelId, final String labelValue, final long level) {

        boolean saveFlag = false;
        if ((labelId != 0) && (level != 0)) {
            final ScrutinyLableValueDTO dto = new ScrutinyLableValueDTO();
            dto.setApplicationId(Long.valueOf(getScrutinyLabelDTO().getApplicationId()));
            dto.setLableId(labelId);
            dto.setLableValue(labelValue);
            dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            dto.setLevel(level);
            dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

            saveFlag = scrutinyService.saveScrutinyValueBylabelId(dto);
        }
        return saveFlag;
    }
    
    public boolean saveLableRemark(final long labelId, final String remark, final long level) {

        boolean saveFlag = false;
        if ((labelId != 0) && (level != 0)) {
            final ScrutinyLableValueDTO dto = new ScrutinyLableValueDTO();
            dto.setApplicationId(Long.valueOf(getScrutinyLabelDTO().getApplicationId()));
            dto.setLableId(labelId);
            //dto.setLableValue(getScrutinyLabelDTO());
            dto.setResolutionComments(remark);
            dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            dto.setLevel(level);
            dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

            saveFlag = scrutinyService.saveScrutinyValueBylabelId(dto);
        }
        return saveFlag;
    }

    public boolean sendTobackEmpl() {

        try {
            final ScrutinyLabelDTO labelDTO = scrutinyLabelDTO;
            if (labelDTO != null) {
                final Organisation orgid = UserSession.getCurrent().getOrganisation();
                final Employee empId = UserSession.getCurrent().getEmployee();
                
                UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(this.getTaskId());

                String processName = serviceMasterService.getProcessName(Long.valueOf(scrutinyLabelDTO.getSmServiceId()),
                        orgid.getOrgid());
                if (processName != null) {
                    WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
                    WorkflowTaskAction workflowAction = new WorkflowTaskAction();
                    workflowAction.setTaskId(this.getTaskId());
                    workflowAction.setApplicationId(Long.valueOf(scrutinyLabelDTO.getApplicationId()));
                    workflowAction.setDateOfAction(new Date());
                    workflowAction.setOrgId(orgid.getOrgid());
					if (Utility.isEnvPrefixAvailable(orgid, MainetConstants.ENV_TCP)) {
						sendBack_ApplicantScrutinyInChecker(userTaskdto, workflowAction);
					}
					workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
                    workflowAction.setEmpId(empId.getEmpId());
                    workflowAction.setModifiedBy(empId.getEmpId());
                    workflowAction.setEmpType(empId.getEmplType());
                    workflowAction.setEmpName(empId.getEmpname());
                    workflowAction.setCreatedBy(empId.getEmpId());
                    workflowAction.setCreatedDate(new Date());
                    workflowdto
                            .setProcessName(processName);
                    try {
                        workflowdto.setWorkflowTaskAction(workflowAction);
                        workflowExecutionService.updateWorkflow(workflowdto);
                    } catch (final Exception e) {
                        throw new FrameworkException("Exception in Scrutiny for jbpm workflow : " + e.getMessage(),
                                e);
                    }
                }

            }
            setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.SENDTOBACK));
            return true;
        } catch (final Exception exception) {

            logger.error("Exception Occur in sendTobackEmpl ()", exception);

        }
        setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_FAIL));
        return false;
    }

	private void sendBack_ApplicantScrutinyInChecker(UserTaskDTO userTaskdto, WorkflowTaskAction workflowAction) {//This method is written to set values while send back because we are putting Scrutiny for in maker checker process
		workflowAction.setIsFinalApproval(false);
		workflowAction.setIsObjectionAppealApplicable(false);
		if(MainetConstants.WorkFlow.Decision.SEND_BACK_TO_APPLICANT.equals(this.getWokflowDecision())){
			workflowAction.setSendBackToGroup(0);
			workflowAction.setSendBackToLevel(0);
			workflowAction.setComments(this.getScrutinyDecisionRemark());
		}
		if(MainetConstants.WorkFlow.Decision.SEND_BACK.equals(this.getWokflowDecision())){
			workflowAction.setSendBackToGroup(1);
			workflowAction.setSendBackToLevel((int) (userTaskdto.getCurentCheckerLevel() - 1));
			workflowAction.setComments(this.getScrutinyDecisionRemark());
		}
	}
    
    public boolean approvedByApplicant() {

        try {
            	final Organisation orgid = UserSession.getCurrent().getOrganisation();
                final Employee empId = UserSession.getCurrent().getEmployee();
                
                UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(this.getTaskId());

                String processName = serviceMasterService.getProcessName(userTaskdto.getServiceId(),
                        orgid.getOrgid());
                if (processName != null) {
                    WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
                    WorkflowTaskAction workflowAction = new WorkflowTaskAction();
                    workflowAction.setTaskId(this.getTaskId());
                    workflowAction.setApplicationId(userTaskdto.getApplicationId());
                    workflowAction.setDateOfAction(new Date());
                    workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
                    workflowAction.setOrgId(orgid.getOrgid());
					workflowAction.setIsFinalApproval(false);
					workflowAction.setIsObjectionAppealApplicable(false);
					workflowAction.setComments(this.getScrutinyDecisionRemark());
                    workflowAction.setEmpId(empId.getEmpId());
                    workflowAction.setModifiedBy(empId.getEmpId());
                    workflowAction.setEmpType(empId.getEmplType());
                    workflowAction.setEmpName(empId.getEmpname());
                    workflowAction.setCreatedBy(empId.getEmpId());
                    workflowAction.setCreatedDate(new Date());
                    workflowdto
                            .setProcessName(processName);
                    try {
                        workflowdto.setWorkflowTaskAction(workflowAction);
                        workflowExecutionService.updateWorkflow(workflowdto);
                    } catch (final Exception e) {
                        throw new FrameworkException("Exception in Scrutiny for jbpm workflow : " + e.getMessage(),
                                e);
                    }
                }

            
            setSuccessMessage(ApplicationSession.getInstance().getMessage("Application Approved Successfully"));
            return true;
        } catch (final Exception exception) {

            logger.error("Exception Occur in sendTobackEmpl ()", exception);

        }
        setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_FAIL));
        return false;
    }
    
    public boolean forwardForClarification() {

        try {
            	//final Organisation orgid = UserSession.getCurrent().getOrganisation();
                final Employee emp = UserSession.getCurrent().getEmployee();
        		Date date = new Date();
                UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(this.getTaskId());
                WorkflowTaskAction workFlowActionDto = new WorkflowTaskAction();
                
                if(MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION.equalsIgnoreCase(userTaskdto.getDeptNameReg())
        				&& !MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION.equalsIgnoreCase(this.getWokflowDecision())){
        			// update ki query
        			userTaskdto.setTaskStatus(MainetConstants.WorkFlow.Status.COMPLETED);
        			userTaskdto.setModifiedBy(Long.valueOf(userTaskdto.getActorId()));
        			workFlowActionDto.setDecision(this.getWokflowDecision());
        			workFlowActionDto.setComments(this.getScrutinyDecisionRemark());
        			workflowTypeDAO.updateTbWorkflowTask(userTaskdto);
        			workflowTypeDAO.updateTbWorkflowAction(userTaskdto, workFlowActionDto);
        			setSuccessMessage(ApplicationSession.getInstance().getMessage("Application Approved Successfully"));
        		}else{
        			WorkflowRequest workflowRequest = new WorkflowRequest();
        			if(null != userTaskdto.getReferenceId()){
        				workflowRequest = workflowRequestService
                                .getWorkflowRequestByAppIdOrRefId(null, userTaskdto.getReferenceId(), userTaskdto.getOrgId());
        			}else{
        				workflowRequest = workflowRequestService
                                .findByApplicationId(userTaskdto.getApplicationId(), userTaskdto.getWorkflowId());
        			}
        			userTaskdto.setWorkflowReqId(workflowRequest.getId());
        			String comments = this.getScrutinyDecisionRemark();
        			String taskActorId = userTaskdto.getActorId();
        			String deptNameReg = null;
        			String serviceDesc = null;
        			if(null != userTaskdto.getServiceNameReg()){
        				serviceDesc = userTaskdto.getServiceNameReg();
        			}
        			if(null == userTaskdto.getDeptNameReg() || MainetConstants.WorkFlow.FORWARD_FOR_CLARIFICATION_PARENT.equals(userTaskdto.getDeptNameReg())){
        				deptNameReg = "";
        			}
        			
        			final String array[] = this.getForwardToEmployee().split(MainetConstants.operator.COMMA);
        			logger.error(array);
        			
        			final String array1[] = userTaskdto.getActorId().split(MainetConstants.operator.COMMA);
        			logger.error(array1);
        			List<String> childTaskId = new ArrayList<>();
        			if(null != userTaskdto.getServiceName()){
        				String childTaskId1[] = userTaskdto.getServiceName().split(MainetConstants.operator.COMMA);
        				for(String arr : childTaskId1){
            				childTaskId.add(arr);
            			}
        			}
        				
        			/*This for loop is for to whom we are forwarding for clarification*/
        			for (String actId : array) {
        				String randomValue= new DecimalFormat("0000").format(new Random().nextInt(9999));
        				Long taskId = Long.valueOf(randomValue);
        				String wftaskId1 = workflowTypeDAO.getWftaskid();
        				Long wfTaskId = Long.valueOf(wftaskId1);
        				childTaskId.add(taskId.toString());
        				userTaskdto.setActorId(actId);
        				
        					if(null == userTaskdto.getDeptName() || !userTaskdto.getDeptName().isEmpty()){
        						userTaskdto.setDeptName(null);
        					}
        					workFlowActionDto.setDecision(MainetConstants.WorkFlow.Status.PENDING);
        					userTaskdto.setDeptNameReg(MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION);
        					workFlowActionDto.setDateOfAction(date);
        					workFlowActionDto.setCreatedDate(date);
        					workFlowActionDto.setModifiedDate(date);
        					userTaskdto.setServiceNameReg(taskActorId);
        					userTaskdto.setServiceName(null);
        					workFlowActionDto.setComments(null);
        					
        				workflowTypeDAO.insertIntoTbWorkflowTask(userTaskdto, workFlowActionDto, taskId, wfTaskId);
        				workflowTypeDAO.insertIntoTbWorkflowAction(userTaskdto, workFlowActionDto, taskId, wfTaskId);
        			}
        			
        			String wftaskId1 = workflowTypeDAO.getWftaskid();
        			Long wfTaskId = Long.valueOf(wftaskId1);
        			int count=0;
        			for (String actIdTask : array1) {
        				String randomValue= new DecimalFormat("0000").format(new Random().nextInt(9999));
        				Long taskId = Long.valueOf(randomValue);
        				
        				
        				
        				if (count == 0) {
        					workFlowActionDto.setComments(comments);
        					workFlowActionDto.setEmpId(emp.getEmpId());
        					workflowTypeDAO.updateTbWorkflowTaskForHistory(userTaskdto, taskId);
        					workflowTypeDAO.updateTbWorkflowActionForHistory(userTaskdto, workFlowActionDto, taskId);
        					
        					userTaskdto.setActorId(taskActorId);
        					taskId = this.getTaskId();
        					if(null == deptNameReg || !deptNameReg.isEmpty()){
        						userTaskdto.setDeptNameReg(MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION);
        					}else{
        						userTaskdto.setDeptNameReg(MainetConstants.WorkFlow.FORWARD_FOR_CLARIFICATION_PARENT);
        					}
        					workFlowActionDto.setDateOfAction(userTaskdto.getDateOfAssignment());
        					workFlowActionDto.setComments(null);
        					workFlowActionDto.setDecision(MainetConstants.WorkFlow.Status.PENDING);
        					workFlowActionDto.setCreatedDate(userTaskdto.getDateOfAssignment());
        					workFlowActionDto.setModifiedDate(userTaskdto.getCreatedDate());
        					userTaskdto.setDeptName(this.getForwardToEmployee());
        					userTaskdto.setServiceName(childTaskId.stream().map(Object::toString).collect(Collectors.joining(",")));
							userTaskdto.setServiceNameReg(serviceDesc);
								
        					workflowTypeDAO.insertIntoTbWorkflowTask(userTaskdto, workFlowActionDto, taskId, wfTaskId);
        					count++;
        				}
        				else{
        					
        					taskId = this.getTaskId();
        					userTaskdto.setDeptNameReg(null);
        					workFlowActionDto.setDateOfAction(userTaskdto.getDateOfAssignment());
        					workFlowActionDto.setComments(null);
        					workFlowActionDto.setDecision(MainetConstants.WorkFlow.Status.PENDING);
        					workFlowActionDto.setCreatedDate(userTaskdto.getDateOfAssignment());
        					workFlowActionDto.setModifiedDate(userTaskdto.getCreatedDate());
        				}
        				userTaskdto.setActorId(actIdTask);
        				workflowTypeDAO.insertIntoTbWorkflowAction(userTaskdto, workFlowActionDto, taskId, wfTaskId);
        				
        			}
        			setSuccessMessage(ApplicationSession.getInstance().getMessage("Application Send Back For Clarification Successfully"));
        		}

            
            
            return true;
        } catch (final Exception exception) {

            logger.error("Exception Occur in sendTobackEmpl ()", exception);

        }
        setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.SCRUTINY_COMMON_PARAM.PROCCESS_FAIL));
        return false;
    }
    
    public boolean initiateDeptWiseWorkflow(Long deptId, Long serviceId, UserTaskDTO currentTask) {
    	String serviceCode = serviceMasterService.fetchServiceShortCode(serviceId, UserSession.getCurrent().getOrganisation().getOrgid());
		String refid = serviceCode + currentTask.getCurentCheckerLevel() + "_" + this.getScrutinyLabelDTO().getApplicationId();
		//LOGGER.info("Reference Id is: "+refid);
		this.setRefId(refid);
		
		Employee employee = UserSession.getCurrent().getEmployee();
		/*ServiceMaster service = serviceMasterService.getServiceByShortName("ADA",
				UserSession.getCurrent().getOrganisation().getOrgid());*/
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(employee.getEmpname());
		applicantDto.setServiceId(serviceId);
		applicantDto.setDepartmentId(deptId);
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(employee.getEmpId());
		//applicantDto.setDwzid1(requestDto.getCodWard1());
		applicationMetaData.setApplicationId(Long.valueOf(this.getScrutinyLabelDTO().getApplicationId()));
		applicationMetaData.setReferenceId(refId);
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		applicationMetaData.setIsLoiApplicable(false);
		applicationMetaData.setIsApprReqInObjection("Y");
		applicationMetaData.setIsScrutinyApplicable(false);
		List<Object[]> ddzList = workFlowTypeRepository.findKhrsColumnsByApplicationNoAndOrgId(currentTask.getApplicationId(), currentTask.getOrgId());
		for (Object[] ddz : ddzList) {
			applicantDto.setDwzid1(ddz.length > 0 ? Long.valueOf(ddz[0].toString())  : null);
			applicantDto.setDwzid2(ddz.length > 1 ? Long.valueOf(ddz[1].toString()) : null);
			applicantDto.setDwzid3(ddz.length > 2 ? Long.valueOf(ddz[2].toString()) : null);
			applicantDto.setDwzid4(ddz.length > 3 ? Long.valueOf(ddz[3].toString()) : null);
		}
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
		
		//this.setSuccessMessage("Scrutiny initiated");
		return true;
	}

    private boolean saveScrutinyLabelValue(final boolean updateFlag, List<Long> attachmentId) {

        boolean saveFlag = false;
        ServiceMaster sm=null;
		if (scrutinyLabelDTO.getSmServiceId() != null) {
			sm = serviceMasterService.getServiceMaster(scrutinyLabelDTO.getSmServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
        try {
            final Long desgId = getLegalScrDesg();

             List<ViewCFCScrutinyLabelValue> list = scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().get(desgId);
            //This is related to TCP Project
            final List<ViewCFCScrutinyLabelValue> fieldlist = scrutinyLabelDTO.getDesgWiseFieldLabelMap().get(desgId);
            if(fieldlist!=null && !fieldlist.isEmpty()) {
            	if(list==null) {
            		list=new ArrayList<>();
            	}
            	list.addAll(fieldlist);
            }
            if ((list != null) && (!list.isEmpty())) {
            	
				if (sm != null && MainetConstants.Property.BMC.equals(sm.getSmShortdesc())) {
					Employee emp = UserSession.getCurrent().getEmployee();
					auditService.saveDataForProperty(scrutinyLabelDTO.getApplicationId(),
							scrutinyLabelDTO.getSmServiceId(), getWokflowDecision(),
							UserSession.getCurrent().getOrganisation().getOrgid(), emp.getEmpId(), emp.getLgIpMac(),
							list.get(list.size() - 1).getLevels());
				}		

            }
            saveFlag = scrutinyService.saveCompleteScrutinyLabel(list, getUserSession(), scrutinyLabelDTO, updateFlag,
                    this.getTaskId(), attachmentId, getWokflowDecision(), getScrutinyDecisionRemark());
        } catch (final Exception exception) {

            logger.error("Exception Occur in saveScrutinyLabelValue() because there is scrutiny label master there is no question added for every role or employee which is define in workflow", exception);

        }
        // 126164 to trigger sms and email on scrutiny approval
		if (saveFlag == true && scrutinyLabelDTO.getSmServiceId() != null) {			
			String deptcode = sm.getTbDepartment().getDpDeptcode();
			if (deptcode.equals(MainetConstants.TradeLicense.MARKET_LICENSE)) {
				//Defect #178024 setting LOI amt and no. for SMS service
				if(sm.getSmFeesSchedule().longValue() != 0) {
			        TbLoiMas loiData= tbLoiMasService.findloiByApplicationIdAndOrgId(Long.valueOf(getScrutinyLabelDTO().getApplicationId()),UserSession.getCurrent().getOrganisation().getOrgid());
			       if(loiData!=null) {
			    	setLoiNo(loiData.getLoiNo());
			        setLoiAmt(loiData.getLoiAmount().toString());
			       }
				}
				if(this.getWokflowDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)){
					scrutinyService.updateStatusFlagByRefId(scrutinyLabelDTO.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid(), deptcode, UserSession.getCurrent().getEmployee().getEmpId());
				}
				sendSmsEmail(scrutinyLabelDTO);
			}
		}
		
		if (true == saveFlag && null != scrutinyLabelDTO.getSmServiceId()){
			String deptCode = sm.getTbDepartment().getDpDeptcode();
			if (MainetConstants.RoadCuttingConstant.DEPT_CODE.equals(deptCode)){
				sendSmsEmail(scrutinyLabelDTO);
			}
		}
        
        return saveFlag;
    }
    
	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public String getLoiAmt() {
		return loiAmt;
	}

	public void setLoiAmt(String loiAmt) {
		this.loiAmt = loiAmt;
	}

	// 126164
	private void sendSmsEmail(ScrutinyLabelDTO dto) {
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		
		ServiceMaster serviceMaster=null;
		if (dto.getSmServiceId() != null) {
			serviceMaster = serviceMasterService.getServiceMaster(dto.getSmServiceId(),
					org.getOrgid());
		}
		String deptCode = serviceMaster.getTbDepartment().getDpDeptcode();
		smsDto.setMobnumber(dto.getMobNo());
		smsDto.setAppNo(dto.getApplicationId());
		smsDto.setAppName(dto.getApplicantName());
		smsDto.setServName(dto.getServiceName());
		smsDto.setEmail(dto.getEmail());
		if(MainetConstants.RoadCuttingConstant.RCP.equals(dto.getServiceShortCode()) 
				|| MainetConstants.RoadCuttingConstant.RCW.equals(dto.getServiceShortCode())){
		smsDto.setReferenceNo(dto.getRefNo());
		smsDto.setRemarks(getScrutinyDecisionRemark());
		}
		//Defect #178024
		if(loiNo != null && loiAmt != null) {
			smsDto.setLoiNo(loiNo);
			smsDto.setLoiAmt(loiAmt);
		}
		String url = "ApplicationAuthorization.html";
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = UserSession.getCurrent().getLanguageId();
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		if (this.getWokflowDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			smsDto.setDecision(this.getWokflowDecision());
			iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, url,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, smsDto, org, langId);
		} else if (this.getWokflowDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			smsDto.setDecision(this.getWokflowDecision());
			iSMSAndEmailService.sendEmailSMS(deptCode, url,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED_MSG, smsDto, org, langId);
		}
	}

    public String getDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + "SCRUTINY" + File.separator
                + scrutinyLabelDTO.getServiceName() + File.separator + scrutinyLabelDTO.getApplicationId()
                + File.separator + UserSession.getCurrent().getEmployee().getEmpId();
    }

    private List<CFCAttachment> uploadScrutinyDoc(final String directoryTree, final ScrutinyLabelDTO dto,
            final FileNetApplicationClient fileNetApplicationClient) {

        String directoryPath = null;
        Map<String, String> fileMap = null;
        boolean isDMS = false;
        boolean isFolderExist = false;
        if (MainetConstants.Common_Constant.YES
                .equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
            isDMS = true;
        }

        final List<CFCAttachment> scrutinyDocs = new ArrayList<>(0);

        CFCAttachment scrutinyDoc = null;
        final Date date = new Date();

        List<File> list = null;
        File file = null;
        String tempDirPath = MainetConstants.BLANK;
        Iterator<File> setFilesItr = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());
            
            setFilesItr = entry.getValue().iterator();

            while (setFilesItr.hasNext()) {
                tempDirPath = MainetConstants.operator.EMPTY;

                file = setFilesItr.next();

                scrutinyDoc = new CFCAttachment();

                scrutinyDoc.setAttDate(new Date());
                scrutinyDoc.setApplicationId(Long.parseLong(dto.getApplicationId()));

                scrutinyDoc.setLgIpMac(Utility.getMacAddress());

                scrutinyDoc.setLmodate(date);

                scrutinyDoc.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

				if ((this.scrutinyDocs != null)
                        && (this.scrutinyDocs.size() >= FileUploadUtility.getCurrent().getFileMap().size())) {
                    scrutinyDoc.setClmRemark(this.scrutinyDocs.get(entry.getKey().intValue()).getClmRemark());
                }

                final Long desgId = getLegalScrDesg();
                final List<ViewCFCScrutinyLabelValue> viewCFCScrutinyLabelValue = scrutinyLabelDTO.getDesgWiseScrutinyLabelMap()
                        .get(desgId);
                if ((viewCFCScrutinyLabelValue != null) && (viewCFCScrutinyLabelValue.size() != 0)) {
                    scrutinyDoc.setSmScrutinyId(viewCFCScrutinyLabelValue.get(0).getScrutinyId());
                    scrutinyDoc.setSmScrutinyLevel(viewCFCScrutinyLabelValue.get(0).getLevels());
                }
                
                scrutinyDoc.setServiceId(dto.getSmServiceId());

                scrutinyDoc.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());

                scrutinyDoc.setUpdatedDate(date);

                scrutinyDoc.setAttBy(UserSession.getCurrent().getEmployee().getEmpname());

                scrutinyDoc.setAttFname(file.getName());

                scrutinyDoc.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

                scrutinyDoc.setGmId(UserSession.getCurrent().getEmployee().getGmid());

                scrutinyDoc.setClmAprStatus(MainetConstants.Common_Constant.YES);

                for (final FileUploadClass fileUploadClass : FileUploadUtility.getCurrent().getFileUploadSet()) {
                    if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                        boolean pathStatus = true;

                        if (pathStatus) {
                            tempDirPath = directoryTree + MainetConstants.FILE_PATH_SEPARATOR + fileUploadClass.getFolderName();

                            scrutinyDoc.setAttPath(tempDirPath);

                            pathStatus = false;
                        }

                        scrutinyDocs.add(scrutinyDoc);
                    }
                }
                try {
                    if (!isDMS) {
                        fileNetApplicationClient.uploadFileList(list, tempDirPath);
                    } else {
                        if (directoryPath == null) {
                            directoryPath = tempDirPath;
                        }
                        final Base64 base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        fileMap = dmsService.createDocument(directoryPath, bytestring,
                                file.getName(), isFolderExist,null,null);
                        if (fileMap != null) {
                            isFolderExist = true;
                            directoryPath = fileMap.get("FOLDER_PATH");
                            scrutinyDoc.setDmsFolderPath(fileMap.get("FOLDER_PATH"));
                            scrutinyDoc.setDmsDocId(fileMap.get("DOC_ID"));
                            scrutinyDoc.setDmsDocName(fileMap.get("FILE_NAME"));
                            if ((scrutinyDoc.getDmsDocVersion() != null) && !scrutinyDoc.getDmsDocVersion().isEmpty()) {
                                Integer ver = Integer.valueOf(scrutinyDoc.getDmsDocVersion());
                                ver = ver + 1;
                                scrutinyDoc.setDmsDocVersion(String.valueOf(ver));
                            } else {
                                scrutinyDoc.setDmsDocVersion("1");
                            }
                        } else {
                            throw new RuntimeException();
                        }
                    }
                } catch (final Exception e) {
                    logger.error(MainetConstants.BLANK, e);
                }
            }
        }
        scrutinyService.saveAllScrutinyDoc(scrutinyDocs);
        try {
            final String path = FileUploadUtility.getCurrent().getExistingFolderPath();
            if (path != null) {
                final File cacheFolderStructure = new File(FileUploadUtility.getCurrent().getExistingFolderPath());

                FileUtils.deleteDirectory(cacheFolderStructure);
            }
        } catch (final Exception e) {
            logger.error(MainetConstants.BLANK, e);
        }

        return scrutinyDocs;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(final String queryString) {
        this.queryString = queryString;
    }

    public ScrutinyLabelDTO getScrutinyLabelDTO() {
        return scrutinyLabelDTO;
    }

    public void setScrutinyLabelDTO(final ScrutinyLabelDTO scrutinyLabelDTO) {
        this.scrutinyLabelDTO = scrutinyLabelDTO;
    }

    public List<CFCAttachment> getScrutinyDocs() {
        return scrutinyDocs;
    }

    public void setScrutinyDocs(final List<CFCAttachment> scrutinyDocs) {
        this.scrutinyDocs = scrutinyDocs;
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(final boolean flag) {
        this.flag = flag;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @param labelId
     * @param level
     * @return
     */
    public String getOpenFormValue(final long labelId, final long level) {

        final Long desgId = getLegalScrDesg();

        if ((!scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().isEmpty()|| !scrutinyLabelDTO.getDesgWiseFieldLabelMap().isEmpty()) && (desgId > 0L)) {
        	   String value =null;
        	 if(!scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().isEmpty())  {
            for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : scrutinyLabelDTO.getDesgWiseScrutinyLabelMap()
                    .get(desgId)) {
                if (viewCFCScrutinyLabelValue.getSlLabelId() == labelId) {
                     value = viewCFCScrutinyLabelValue.getSlValidationText();
                }
            }
        	 }
            if(!scrutinyLabelDTO.getDesgWiseFieldLabelMap().isEmpty()) {
            for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : scrutinyLabelDTO.getDesgWiseFieldLabelMap()
                    .get(desgId)) {
                if (viewCFCScrutinyLabelValue.getSlLabelId() == labelId) {
                     value = viewCFCScrutinyLabelValue.getSlValidationText();
                }
            }
            }
            return value;
        }

        return null;
    }

    public String getWokflowDecision() {
        return wokflowDecision;
    }

    public void setWokflowDecision(String wokflowDecision) {
        this.wokflowDecision = wokflowDecision;
    }
    
    public Boolean isScrutinyLoiGenerated() {
    	final List<ViewCFCScrutinyLabelValue> list = scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().get(getLegalScrDesg());
    	if(!CollectionUtils.isEmpty(list)) {
    	Boolean flag=scrutinyService.checkLoiGeneratedOrNot(scrutinyLabelDTO, getLegalScrDesg(), list);
    	if(!flag) {
    		addValidationError(getAppSession().getMessage("loi.paid.validation"));
    		return false;
    	}
    }
    	return true;
    }
    
    public Boolean isScrutinyMetered() {
    	final List<ViewCFCScrutinyLabelValue> list = scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().get(getLegalScrDesg());
    	Boolean flag=scrutinyService.checkMeteredOrNot(scrutinyLabelDTO, getLegalScrDesg(), list);
    	if(!flag) {
    		addValidationError(getAppSession().getMessage("water.meter.status"));
    		return false;
    	}
    	return true;
    }

	public String getLoiGenFlag() {
		return loiGenFlag;
	}

	public void setLoiGenFlag(String loiGenFlag) {
		this.loiGenFlag = loiGenFlag;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getLevels() {
		return levels;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
