package com.abm.mainet.common.integration.dms.service;

import java.util.Date;
import java.util.Locale;

import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.repository.IMetadataRepository;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class DmsMetadataServiceImpl implements IDmsMetadataService {

	private static final Logger LOGGER = Logger.getLogger(DmsMetadataServiceImpl.class);

	@Autowired
	private IMetadataRepository repo;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private EmployeeJpaRepository employeeJpaRepository;

	@Override
	public Long getMaxCount(Long orgid) {
		return repo.getMaxCount(orgid);
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas,
			String url, String workFlowFlag) {
		try {
			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			ApplicationMetadata applicationMetadata = new ApplicationMetadata();
			applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
			applicationMetadata.setApplicationId(workflowActionDto.getApplicationId());
			applicationMetadata.setOrgId(workflowActionDto.getOrgId());
			applicationMetadata.setWorkflowId(workFlowMas.getWfId());
			applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);
			ApplicationSession appSession = ApplicationSession.getInstance();

			/*
			 * Task manager assignment is depends no LDAP integration his check added in
			 * BRm/BPM layer
			 */
			TaskAssignment assignment = new TaskAssignment();

			assignment.setActorId(workflowActionDto.getEmpId().toString());
			assignment.addActorId(workflowActionDto.getEmpId().toString());
			assignment.setOrgId(workflowActionDto.getOrgId());
			assignment.setServiceEventId(-1L);
			String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
			assignment.setServiceEventName(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));

			assignment.setServiceEventNameReg(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));

			assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
			assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
			assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
			assignment.setServiceId(workFlowMas.getService().getSmServiceId());
			assignment.setServiceName(workFlowMas.getService().getSmServiceNameMar());
			assignment.setServiceEventNameReg(workFlowMas.getService().getSmServiceNameMar());
			assignment.setUrl(url);

			/*
			 * Reviewer TaskAssignment has been removed from here,because it will be fetch
			 * on the fly by BPM to Service callback.
			 */

			workflowProcessParameter.setRequesterTaskAssignment(assignment);
			workflowProcessParameter.setApplicationMetadata(applicationMetadata);
			workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.initiateWorkflow(workflowProcessParameter);

		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
		}
		return null;
	}

	@Override
	@Transactional
	public String updateWorkFlowMetadataService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateWfStatus(String requestId, String remark, String decision, Long orgId) {
		repo.updateWfStatus(requestId, remark, decision, orgId);
	}

	//#105966    to save metadata through offline utility
	@Override
	@Transactional
	public boolean saveForm(FileUploadDTO uploadDTO) {

		boolean status = false;
		DmsDocsMetadataDto dmsDocsDto = uploadDTO.getDmsDocsDto();
		dmsDocsDto.setIsActive(MainetConstants.FlagA);

		Organisation org = orgService.getOrganisationById(uploadDTO.getOrgId());
		Employee employee = employeeJpaRepository.findOne(uploadDTO.getUserId());

		Long count = this.getMaxCount(uploadDTO.getOrgId());
		dmsDocsDto.setStorageType(MainetConstants.Dms.DMS);
		uploadDTO.setIdfId(dmsDocsDto.getStorageType() + MainetConstants.WINDOWS_SLASH + count);
		uploadDTO.setStatus(MainetConstants.FlagA);
		uploadDTO.getDmsDocsDto().setLmodDate(new Date());
		uploadDTO.getDmsDocsDto().setLgIpMac(employee.getEmppiservername());
		uploadDTO.getDmsDocsDto().setOrgId(org);
		uploadDTO.getDmsDocsDto().setUserId(employee);
		uploadDTO.getDmsDocsDto().getDmsDocsMetadataDetList().forEach(data -> {
			data.setLmodDate(new Date());
			data.setLgIpMac(employee.getEmppiservername());
			data.setOrgId(org);
			data.setUserId(employee);
		});

		ServiceMaster servMaster = null;
		LookUp lookUp = null;
		try {
			servMaster = serviceMaster.getServiceByShortName(MainetConstants.Dms.MTD, uploadDTO.getOrgId());
			lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(servMaster.getSmProcessId(), org);
			status = true;
		} catch (Exception e1) {
			throw new FrameworkException(e1, "Service Master is not configured");
		}
		String flag = MainetConstants.FlagN;
		if (lookUp != null && !lookUp.getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
			flag = MainetConstants.FlagY;
			try {
				status = false;// Sequence no
				FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
						.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
				String deptCode = departmentService.getDeptCode(servMaster.getTbDepartment().getDpDeptid());
				final Long sequence = ApplicationContextProvider.getApplicationContext()
						.getBean(SeqGenFunctionUtility.class).generateSequenceNo(MainetConstants.CommonConstants.COM,
								MainetConstants.DMS_TABLE, MainetConstants.SEQ_NO, org.getOrgid(),
								MainetConstants.FlagC, financiaYear.getFaYear());
				String seqNo = org.getOrgShortNm() + MainetConstants.WINDOWS_SLASH + deptCode
						+ MainetConstants.WINDOWS_SLASH
						+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence);
				dmsDocsDto.setSeqNo(seqNo);
				dmsDocsDto.setWfStatus(MainetConstants.LQP.STATUS.OPEN);
				status = true;
			} catch (Exception e) {
				throw new FrameworkException(e, "Sequence no. is not generated ");
			}
			if (status) {
				try {
					// workflow start
					status = false;
					WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
					workflowActionDto.setReferenceId(dmsDocsDto.getSeqNo());
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setReferenceId(workflowActionDto.getReferenceId());
					requestDTO.setApplicationId(workflowActionDto.getApplicationId());

					WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
							.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(
									uploadDTO.getOrgId(), servMaster.getTbDepartment().getDpDeptid(),
									servMaster.getSmServiceId(), null, null, null, null, null);
					this.initiateWorkFlowWorksService(prepareWorkFlowTaskAction(dmsDocsDto, uploadDTO), workFlowMas,
							"DmsMetadata.html", MainetConstants.FlagA);
					status = true;
				} catch (Exception e) {
					throw new FrameworkException(e, "Workflow is not initiated successfully");
				}
			}
		}
		if (status) {
			if (flag.equals(MainetConstants.FlagN)) {
				dmsDocsDto.setWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
				dmsDocsDto.setRemark(MainetConstants.MAKER_CHECKER_NA);
			}
			try {
				fileUploadService.doMasterFileUpload(dmsDocsDto.getAttachments(), uploadDTO); // save data
				if (dmsDocsDto.getSeqNo() != null) {
					LOGGER.info("Record saved successfully . Your Application no. is" + dmsDocsDto.getSeqNo());
				} else {
					LOGGER.info("Record saved successfully ");
				}
				status = true;
			} catch (Exception e) {
				throw new FrameworkException(e, "Unable to update records in database");
			}
		}
		return status;
	}

	public WorkflowTaskAction prepareWorkFlowTaskAction(DmsDocsMetadataDto dmsDocsDto, FileUploadDTO fileUploadDTO) {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(fileUploadDTO.getOrgId());
		taskAction.setEmpId(fileUploadDTO.getUserId());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(fileUploadDTO.getUserId());
		taskAction.setReferenceId(dmsDocsDto.getSeqNo());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
		return taskAction;
	}
}
